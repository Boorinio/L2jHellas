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


import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.zone.L2ZoneType;
import com.l2jhellas.gameserver.model.zone.ZoneId;
import com.l2jhellas.gameserver.network.serverpackets.NpcInfo;
import com.l2jhellas.gameserver.network.serverpackets.ServerObjectInfo;

public class L2WaterZone extends L2ZoneType
{
	public L2WaterZone(int id)
	{
		super(id);
	}
	
	@Override
	protected void onEnter(L2Character character)
	{
		character.setInsideZone(ZoneId.WATER, true);
		
		if (character instanceof L2PcInstance)
			((L2PcInstance) character).broadcastUserInfo();
		else if (character instanceof L2Npc)
		{
			for (L2PcInstance player : L2World.getInstance().getVisibleObjects(character, L2PcInstance.class))
			{
				if(player!=null)
				{
				if (character.getRunSpeed() == 0)
					player.sendPacket(new ServerObjectInfo((L2Npc) character, player));
				else
					player.sendPacket(new NpcInfo((L2Npc) character, player));
				}
			}
		}
	}
	
	@Override
	protected void onExit(L2Character character)
	{
		character.setInsideZone(ZoneId.WATER, false);
		
		// TODO: update to only send speed status when that packet is known
		if (character instanceof L2PcInstance)
			((L2PcInstance) character).broadcastUserInfo();
		else if (character instanceof L2Npc)
		{
			for (L2PcInstance player : L2World.getInstance().getVisibleObjects(character, L2PcInstance.class))
			{
				if(player!=null)
				{
				if (character.getRunSpeed() == 0)
					player.sendPacket(new ServerObjectInfo((L2Npc) character, player));
				else
					player.sendPacket(new NpcInfo((L2Npc) character, player));
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
	
	public int getWaterZ()
	{
		return getZone().getHighZ();
	}
}