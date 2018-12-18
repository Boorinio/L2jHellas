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
package com.l2jhellas.gameserver.instancemanager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.l2jhellas.gameserver.idfactory.IdFactory;
import com.l2jhellas.gameserver.model.L2Effect;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.entity.Duel;
import com.l2jhellas.gameserver.network.serverpackets.L2GameServerPacket;

public class DuelManager
{
	private final Map<Integer, Duel> _duels = new ConcurrentHashMap<>();
	
	protected DuelManager()
	{
	}
	
	public Duel getDuel(int duelId)
	{
		return _duels.get(duelId);
	}

	public void addDuel(L2PcInstance playerA, L2PcInstance playerB, boolean isPartyDuel)
	{
		if (playerA == null || playerB == null)
			return;
		
		final int duelId = IdFactory.getInstance().getNextId();

		_duels.put(duelId, new Duel(playerA, playerB, isPartyDuel, duelId));
	}

	public void removeDuel(int duelId)
	{
		IdFactory.getInstance().releaseId(duelId);
		_duels.remove(duelId);
	}

	public void doSurrender(L2PcInstance player)
	{
		if (player == null || !player.isInDuel())
			return;
		
		final Duel duel = getDuel(player.getDuelId());
		
		if (duel != null)
			duel.doSurrender(player);
	}

	public void onPlayerDefeat(L2PcInstance player)
	{
		if (player == null || !player.isInDuel())
			return;
		
		final Duel duel = getDuel(player.getDuelId());
		
		if (duel != null)
			duel.onPlayerDefeat(player);
	}

	public void onBuff(L2PcInstance player, L2Effect buff)
	{
		if (player == null || !player.isInDuel() || buff == null)
			return;
		
		final Duel duel = getDuel(player.getDuelId());
		
		if (duel != null)
			duel.onBuff(player, buff);
	}

	public void onPartyEdit(L2PcInstance player)
	{
		if (player == null || !player.isInDuel())
			return;
		
		final Duel duel = getDuel(player.getDuelId());
		
		if (duel != null)
			duel.onPartyEdit();
	}

	public void broadcastToOppositeTeam(L2PcInstance player, L2GameServerPacket packet)
	{
		if (player == null || !player.isInDuel())
			return;
		
		final Duel duel = getDuel(player.getDuelId());
		
		if (duel == null)
			return;
		
		if (duel.getPlayerA() == player)
			duel.broadcastToTeam2(packet);
		
		else if (duel.getPlayerB() == player)
			duel.broadcastToTeam1(packet);
		
		else if (duel.isPartyDuel())
		{
			if (duel.getPlayerA().getParty() != null && duel.getPlayerA().getParty().containsPlayer(player))
				duel.broadcastToTeam2(packet);
			
			else if (duel.getPlayerB().getParty() != null && duel.getPlayerB().getParty().containsPlayer(player))
				duel.broadcastToTeam1(packet);
		}
	}
	
	public static final DuelManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final DuelManager INSTANCE = new DuelManager();
	}
}