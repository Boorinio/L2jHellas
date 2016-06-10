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
package com.l2jhellas.gameserver.model.actor.knownlist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.L2Summon;
import com.l2jhellas.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PetInstance;
import com.l2jhellas.gameserver.network.serverpackets.CharInfo;
import com.l2jhellas.gameserver.network.serverpackets.GetOnVehicle;
import com.l2jhellas.gameserver.network.serverpackets.NpcInfo;
import com.l2jhellas.gameserver.network.serverpackets.PetInfo;
import com.l2jhellas.gameserver.network.serverpackets.PetItemList;
import com.l2jhellas.gameserver.network.serverpackets.PrivateStoreMsgBuy;
import com.l2jhellas.gameserver.network.serverpackets.PrivateStoreMsgSell;
import com.l2jhellas.gameserver.network.serverpackets.RecipeShopMsg;
import com.l2jhellas.gameserver.network.serverpackets.RelationChanged;
import com.l2jhellas.util.Util;

public class CharKnownList extends ObjectKnownList
{
	private Map<Integer, L2PcInstance> _knownPlayers;
	private Map<Integer, Integer> _knownRelations;

	public CharKnownList(L2Character activeChar)
	{
		super(activeChar);
	}

	@Override
	public boolean addKnownObject(L2Object object)
	{
		return addKnownObject(object, null);
	}

	@Override
	public boolean addKnownObject(L2Object object, L2Character dropper)
	{
		if (!super.addKnownObject(object, dropper) || !object.isVisible())
			return false;
		
		if (object instanceof L2PcInstance)
		{
			getKnownPlayers().putIfAbsent(object.getObjectId(), (L2PcInstance) object);
			getKnownRelations().putIfAbsent(object.getObjectId(), -1);
		}
		return true;
	}

	/**
	 * Return True if the L2PcInstance is in _knownPlayer of the L2Character.<BR>
	 * <BR>
	 * 
	 * @param player
	 *        The L2PcInstance to search in _knownPlayer
	 */
	public final boolean knowsThePlayer(L2PcInstance player)
	{
		return getActiveChar() == player || getKnownPlayers().containsKey(player.getObjectId());
	}

	/** Remove all L2Object from _knownObjects and _knownPlayer of the L2Character then cancel Attak or Cast and notify AI. */
	@Override
	public final void removeAllKnownObjects()
	{
		super.removeAllKnownObjects();
		getKnownPlayers().clear();
		getKnownRelations().clear();

		// Set _target of the L2Character to null
		// Cancel Attack or Cast
		getActiveChar().setTarget(null);

		// Cancel AI Task
		if (getActiveChar().hasAI())
			getActiveChar().setAI(null);
	}

	@Override
	public boolean removeKnownObject(L2Object object)
	{
		if (!super.removeKnownObject(object))
			return false;
		if (object instanceof L2PcInstance)
		{
			getKnownPlayers().remove(object.getObjectId());
			getKnownRelations().remove(object.getObjectId());
		}
		// If object is targeted by the L2Character, cancel Attack or Cast
		if (object == getActiveChar().getTarget())
			getActiveChar().setTarget(null);

		return true;	
	}

	public L2Character getActiveChar()
	{
		return (L2Character) super.getActiveObject();
	}

	@Override
	public int getDistanceToForgetObject(L2Object object)
	{
		return 0;
	}

	@Override
	public int getDistanceToWatchObject(L2Object object)
	{
		return 0;
	}

	public Collection<L2Character> getKnownCharacters()
	{
		ArrayList<L2Character> result = new ArrayList<L2Character>();

		for (L2Object obj : getKnownObjects().values())
		{
			if ((obj != null) && obj instanceof L2Character && obj.isVisible())
				result.add((L2Character) obj);
		}

		return result;
	}

	public Collection<L2Character> getKnownCharactersInRadius(long radius)
	{
		ArrayList<L2Character> result = new ArrayList<L2Character>();

		for (L2Object obj : getKnownObjects().values())
		{
			if (obj instanceof L2PcInstance && obj.isVisible())
			{
				if (Util.checkIfInRange((int) radius, getActiveChar(), obj, true))
					result.add((L2PcInstance) obj);
			}
			else if (obj instanceof L2MonsterInstance && obj.isVisible())
			{
				if (Util.checkIfInRange((int) radius, getActiveChar(), obj, true))
					result.add((L2MonsterInstance) obj);
			}
			else if (obj instanceof L2Npc && obj.isVisible())
			{
				if (Util.checkIfInRange((int) radius, getActiveChar(), obj, true))
					result.add((L2Npc) obj);
			}
		}

		return result;
	}

	public final Map<Integer, L2PcInstance> getKnownPlayers()
	{
		if (_knownPlayers == null)
			_knownPlayers = new ConcurrentHashMap<>();
		return _knownPlayers;
	}

	public final Map<Integer, Integer> getKnownRelations()
	{
		if (_knownRelations == null)
			_knownRelations = new ConcurrentHashMap<>();
		return _knownRelations;
	}

	public final Collection<L2PcInstance> getKnownPlayersInRadius(long radius)
	{
		ArrayList<L2PcInstance> result = new ArrayList<L2PcInstance>();

		for (L2PcInstance player : getKnownPlayers().values())
			if (player.isVisible() && Util.checkIfInRange((int) radius, getActiveChar(), player, true))
				result.add(player);

		return result;
	}
	
	public void refreshInfos()
	{
		Collection<L2Object> objects = getKnownObjects().values();
		for (L2Object object : objects)
		{
			if (object instanceof L2PcInstance && ((L2PcInstance) object).inObserverMode() || !object.isVisible())
				continue;
			
			sendInfoFrom(object);
		}
	}
	
	private final void sendInfoFrom(L2Object object)
	{
		// get player
		L2Character player = (L2Character) getActiveChar();

            if (object instanceof L2Summon)
			{
				L2Summon summon = (L2Summon) object;

				// Check if the L2PcInstance is the owner of the Pet
				if (getActiveChar().equals(summon.getOwner()))
				{
					getActiveChar().sendPacket(new PetInfo(summon, 0));
					// The PetInfo packet wipes the PartySpelled (list of active spells' icons). Re-add them
					summon.updateEffectIcons(true);
					if (summon instanceof L2PetInstance)
					{
						getActiveChar().sendPacket(new PetItemList((L2PetInstance) summon));
					}
				}
				else
					getActiveChar().sendPacket(new NpcInfo(summon, getActiveChar()));
			}
	        
			else if (object instanceof L2PcInstance)
			{
				if (((L2PcInstance) object).inObserverMode())
					return;
				
				L2PcInstance otherPlayer = (L2PcInstance) object;
				if (otherPlayer.isInBoat())
				{
					otherPlayer.getPosition().setWorldPosition(otherPlayer.getBoat().getPosition().getWorldPosition());
					getActiveChar().sendPacket(new CharInfo(otherPlayer));
					int relation = otherPlayer.getRelation(getActiveChar().getActingPlayer());
					if (otherPlayer.getKnownList().getKnownRelations().get(getActiveChar().getObjectId()) != null && otherPlayer.getKnownList().getKnownRelations().get(getActiveChar().getObjectId()) != relation)
						getActiveChar().sendPacket(new RelationChanged(otherPlayer, relation, getActiveChar().isAutoAttackable(otherPlayer)));
					getActiveChar().sendPacket(new GetOnVehicle(otherPlayer.getObjectId(), otherPlayer.getBoat().getObjectId(), otherPlayer.getInVehiclePosition()));		
				}
				else
				{
					getActiveChar().sendPacket(new CharInfo(otherPlayer));
					int relation = otherPlayer.getRelation(getActiveChar().getActingPlayer());
					if (otherPlayer.getKnownList().getKnownRelations().get(getActiveChar().getObjectId()) != null && otherPlayer.getKnownList().getKnownRelations().get(getActiveChar().getObjectId()) != relation)
						getActiveChar().sendPacket(new RelationChanged(otherPlayer, relation, getActiveChar().isAutoAttackable(otherPlayer)));
				}

				if (otherPlayer.getPrivateStoreType() == L2PcInstance.STORE_PRIVATE_SELL)
					getActiveChar().sendPacket(new PrivateStoreMsgSell(otherPlayer));
				else if (otherPlayer.getPrivateStoreType() == L2PcInstance.STORE_PRIVATE_BUY)
					getActiveChar().sendPacket(new PrivateStoreMsgBuy(otherPlayer));
				else if (otherPlayer.getPrivateStoreType() == L2PcInstance.STORE_PRIVATE_MANUFACTURE)
					getActiveChar().sendPacket(new RecipeShopMsg(otherPlayer));
			}
			
			if (object instanceof L2Character)
			{
				// Update the state of the L2Character object client side by sending Server->Client packet MoveToPawn/MoveToLocation and AutoAttackStart to the L2PcInstance
				L2Character obj = (L2Character) object;
				if (obj.hasAI())
					obj.getAI().describeStateToPlayer(player.getActingPlayer());
			}
		}
}