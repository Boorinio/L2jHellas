/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jhellas.gameserver.model.zone.type;

import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.datatables.sql.MapRegionTable;
import com.l2jhellas.gameserver.model.L2Character;
import com.l2jhellas.gameserver.model.L2Summon;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PlayableInstance;
import com.l2jhellas.gameserver.model.entity.olympiad.OlympiadGameTask;
import com.l2jhellas.gameserver.model.zone.L2SpawnZone;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.ExOlympiadMatchEnd;
import com.l2jhellas.gameserver.network.serverpackets.ExOlympiadUserInfo;
import com.l2jhellas.gameserver.network.serverpackets.L2GameServerPacket;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;

/**
 * An olympiad stadium
 */
public class L2OlympiadStadiumZone extends L2SpawnZone
{
	private int _stadiumId;

	OlympiadGameTask _task = null;
	
	public L2OlympiadStadiumZone(int id)
	{
		super(id);
	}
	public final void registerTask(OlympiadGameTask task)
	{
		_task = task;
	}
	@Override
	public void setParameter(String name, String value)
	{
		if (name.equals("stadiumId"))
		{
			_stadiumId = Integer.parseInt(value);
		}
		else
			super.setParameter(name, value);
	}
	@Override
	protected void onEnter(L2Character character)
	{
		character.setInsideZone(L2Character.ZONE_PVP, true);
		if (_task != null)
		{
			if (_task.isBattleStarted())
			{
				character.setInsideZone(L2Character.ZONE_PVP, true);
				if (character instanceof L2PcInstance)
				{
					character.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.ENTERED_COMBAT_ZONE));
					_task.getGame().sendOlympiadInfo(character);
				}
			}
		}
		
		if (character instanceof L2PlayableInstance)
		{
			final L2PcInstance player = character.getActingPlayer();
			if (player != null)
			{
				// only participants, observers and GMs allowed
				if (!player.isGM() && !player.isInOlympiadMode() && !player.inObserverMode())
					ThreadPoolManager.getInstance().executeTask(new KickPlayer(player));
			}
		}
	}

	@Override
	protected void onExit(L2Character character)
	{
		character.setInsideZone(L2Character.ZONE_PVP, false);
		if (_task != null)
		{
			if (_task.isBattleStarted())
			{
				character.setInsideZone(L2Character.ZONE_PVP, false);
				if (character instanceof L2PcInstance)
				{
					character.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.LEFT_COMBAT_ZONE));
					character.sendPacket(ExOlympiadMatchEnd.STATIC_PACKET);
				}
			}
		}
	}

	@Override
	public void onDieInside(L2Character character)
	{
	}

	@Override
	public void onReviveInside(L2Character character)
	{
	}

	/**
	 * Returns this zones stadium id (if any)
	 * 
	 * @return
	 */
	public int getStadiumId()
	{
		return _stadiumId;
	}
	public final void updateZoneStatusForCharactersInside()
	{
		if (_task == null)
			return;
		
		final boolean battleStarted = _task.isBattleStarted();
		final SystemMessage sm;
		if (battleStarted)
			sm = SystemMessage.getSystemMessage(SystemMessageId.ENTERED_COMBAT_ZONE);
		else
			sm = SystemMessage.getSystemMessage(SystemMessageId.LEFT_COMBAT_ZONE);
		
		for (L2Character character : _characterList.values())
		{
			if (character == null)
				continue;
			
			if (battleStarted)
			{
				character.setInsideZone(L2Character.ZONE_PVP, true);
				if (character instanceof L2PcInstance)
					character.sendPacket(sm);
			}
			else
			{
				character.setInsideZone(L2Character.ZONE_PVP, false);
				if (character instanceof L2PcInstance)
				{
					character.sendPacket(sm);
					character.sendPacket(ExOlympiadMatchEnd.STATIC_PACKET);
				}
			}
		}
	}
	private static final class KickPlayer implements Runnable
	{
		private L2PcInstance _player;
		
		public KickPlayer(L2PcInstance player)
		{
			_player = player;
		}
		
		@Override
		public void run()
		{
			if (_player != null)
			{
				final L2Summon summon = _player.getPet();
				if (summon != null)
					summon.unSummon(_player);
				
				_player.teleToLocation(MapRegionTable.TeleportWhereType.Town);
				_player = null;
			}
		}
	}
	
	public final void broadcastStatusUpdate(L2PcInstance player)
	{
		final ExOlympiadUserInfo packet = new ExOlympiadUserInfo(player);
		for (L2PcInstance plyr : getPlayersInside())
		{
			if (plyr.inObserverMode() || plyr.getOlympiadSide() != player.getOlympiadSide())
				plyr.sendPacket(packet);
		}
	}
	
	public final void broadcastPacketToObservers(L2GameServerPacket packet)
	{
		for (L2PcInstance player : getPlayersInside())
		{
			if (player.inObserverMode())
				player.sendPacket(packet);
		}
	}
}