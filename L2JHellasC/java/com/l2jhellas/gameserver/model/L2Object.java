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
package com.l2jhellas.gameserver.model;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.idfactory.IdFactory;
import com.l2jhellas.gameserver.instancemanager.ItemsOnGroundManager;
import com.l2jhellas.gameserver.instancemanager.MercTicketManager;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.L2Playable;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.actor.poly.ObjectPoly;
import com.l2jhellas.gameserver.model.actor.position.ObjectPosition;
import com.l2jhellas.gameserver.model.quest.QuestState;
import com.l2jhellas.gameserver.model.zone.ZoneId;
import com.l2jhellas.gameserver.network.serverpackets.ActionFailed;
import com.l2jhellas.gameserver.network.serverpackets.DeleteObject;
import com.l2jhellas.gameserver.network.serverpackets.GetItem;

/**
 * Mother class of all objects in the world wich ones is it possible
 * to interact (PC, NPC, Item...)<BR>
 * <BR>
 * L2Object :<BR>
 * <BR>
 * <li>L2Character</li> <li>L2ItemInstance</li> <li>L2Potion</li>
 */

public abstract class L2Object
{
	private boolean _isVisible; // Object visibility
	private String _name;
	private int _objectId; // Object identifier
	private ObjectPoly _poly;
	private ObjectPosition _position;

	// Objects can only see objects in same instancezone, instance 0 is normal world -1 the all seeing world
	private int _instanceId = 0;

	public L2Object(int objectId)
	{
		_objectId = objectId;
	}

	public void onAction(L2PcInstance player)
	{
		player.sendPacket(ActionFailed.STATIC_PACKET);
	}
	
	public void onActionShift(L2PcInstance player)
	{
		player.sendPacket(ActionFailed.STATIC_PACKET);
	}
	
	public void onForcedAttack(L2PcInstance player)
	{
		player.sendPacket(ActionFailed.STATIC_PACKET);
	}

	/** Engine parameters */
	public boolean _inEventTvT = false;
	public boolean _inEventCTF = false;
	public boolean _inEventVIP = false;
	public boolean _inEventDM = false;

	/**
	 * Do Nothing.<BR>
	 * <BR>
	 * <B><U> Overridden in </U> :</B><BR>
	 * <BR>
	 * <li>L2GuardInstance : Set the home location of its L2GuardInstance</li> <li>L2Attackable : Reset the Spoiled flag</li><BR>
	 * <BR>
	 */
	public void onSpawn()
	{
	}

	// =========================================================
	// Position - Should remove to fully move to L2ObjectPosition
	public void setXYZ(int x, int y, int z)
	{
		getPosition().setXYZ(x, y, z);
	}

	public final void setXYZInvisible(int x, int y, int z)
	{
		getPosition().setXYZInvisible(x, y, z);
	}

	public final int getX()
	{
		return getPosition().getX();
	}

	public final int getY()
	{
		return getPosition().getY();
	}

	public final int getZ()
	{
		return getPosition().getZ();
	}

	/**
	 * Remove a L2Object from the world.<BR>
	 * <BR>
	 * <B><U> Actions</U> :</B><BR>
	 * <BR>
	 * <li>Remove the L2Object from the world</li><BR>
	 * <BR>
	 * <FONT COLOR=#FF0000><B> <U>Caution</U> : This method DOESN'T REMOVE the object from _allObjects of L2World </B></FONT><BR>
	 * <FONT COLOR=#FF0000><B> <U>Caution</U> : This method DOESN'T SEND Server->Client packets to players</B></FONT><BR>
	 * <BR>
	 * <B><U> Assert </U> :</B><BR>
	 * <BR>
	 * <li>_worldRegion != null <I>(L2Object is visible at the beginning)</I></li><BR>
	 * <BR>
	 * <B><U> Example of use </U> :</B><BR>
	 * <BR>
	 * <li>Delete NPC/PC or Unsummon</li><BR>
	 * <BR>
	 */
	public void decayMe()
	{
		final L2WorldRegion reg = getWorldRegion();

		synchronized (this)
		{
			_isVisible = false;
			getPosition().setWorldRegion(null);
		}

		L2World.getInstance().removeVisibleObject(this, reg);
		L2World.getInstance().removeObject(this);
		
		if (Config.SAVE_DROPPED_ITEM)
			ItemsOnGroundManager.getInstance().removeObject(this);
	}

	/**
	 * Remove a L2ItemInstance from the world and send server->client GetItem packets.<BR>
	 * <BR>
	 * <B><U> Actions</U> :</B><BR>
	 * <BR>
	 * <li>Send a Server->Client Packet GetItem to player that pick up and its _knowPlayers member</li> <li>Remove the L2Object from the world</li><BR>
	 * <BR>
	 * <FONT COLOR=#FF0000><B> <U>Caution</U> : This method DOESN'T REMOVE the object from _allObjects of L2World </B></FONT><BR>
	 * <BR>
	 * <B><U> Assert </U> :</B><BR>
	 * <BR>
	 * <li>this instanceof L2ItemInstance</li> <li>_worldRegion != null <I>(L2Object is visible at the beginning)</I></li><BR>
	 * <BR>
	 * <B><U> Example of use </U> :</B><BR>
	 * <BR>
	 * <li>Do Pickup Item : PCInstance and Pet</li><BR>
	 * <BR>
	 * 
	 * @param player
	 *        Player that pick up the item
	 */
	public final void pickupMe(L2Character player) // NOTE: Should move this function into L2ItemInstance because it does not apply to L2Character
	{
		
		final L2WorldRegion reg = getWorldRegion();

		// Create a server->client GetItem packet to pick up the L2ItemInstance
		GetItem gi = new GetItem((L2ItemInstance) this, player.getObjectId());
		player.broadcastPacket(gi);

		synchronized (this)
		{
			_isVisible = false;
			getPosition().setWorldRegion(null);
		}
		// if this item is a mercenary ticket, remove the spawns!
		if (this instanceof L2ItemInstance)
		{
			int itemId = ((L2ItemInstance) this).getItemId();
			if (MercTicketManager.getInstance().getTicketCastleId(itemId) > 0)
			{
				MercTicketManager.getInstance().removeTicket((L2ItemInstance) this);
				ItemsOnGroundManager.getInstance().removeObject(this);
			}
			if (itemId == 57 || itemId == 6353)
			{
				L2PcInstance actor = player.getActingPlayer();
				if (actor != null)
				{
					QuestState qs = actor.getQuestState("255_Tutorial");
					if (qs != null)
						qs.getQuest().notifyEvent("CE" + itemId + "", null, actor);
				}
			}
		}

		// this can synchronize on others instancies, so it's out of
		// synchronized, to avoid deadlocks
		// Remove the L2ItemInstance from the world
		L2World.getInstance().removeVisibleObject(this, reg);

		setIsVisible(false);
	}

	public void refreshID()
	{
		L2World.getInstance().removeObject(this);
		IdFactory.getInstance().releaseId(getObjectId());
		_objectId = IdFactory.getInstance().getNextId();
	}

	/**
	 * Init the position of a L2Object spawn and add it in the world as a visible object.<BR>
	 * <BR>
	 * <B><U> Actions</U> :</B><BR>
	 * <BR>
	 * <li>Set the x,y,z position of the L2Object spawn and update its _worldregion</li> <li>Add the L2Object spawn in the _allobjects of L2World</li> <li>Add the L2Object spawn to
	 * _visibleObjects of its L2WorldRegion</li> <li>Add the L2Object spawn in the world as a <B>visible</B> object</li><BR>
	 * <BR>
	 * <B><U> Assert </U> :</B><BR>
	 * <BR>
	 * <li>_worldRegion == null <I>(L2Object is invisible at the beginning)</I></li><BR>
	 * <BR>
	 * <B><U> Example of use </U> :</B><BR>
	 * <BR>
	 * <li>Create Door</li> <li>Spawn : Monster, Minion, CTs, Summon...</li><BR>
	 */
	public final void spawnMe()
	{
		synchronized (this)
		{
			// Set the x,y,z position of the L2Object spawn and update its _worldregion
			_isVisible = true;
			getPosition().setWorldRegion(L2World.getInstance().getRegion(getPosition().getWorldPosition()));

			// Add the L2Object spawn in the _allobjects of L2World
			L2World.getInstance().storeObject(this);

			// Add the L2Object spawn to _visibleObjects and if necessary to _allplayers of its L2WorldRegion
			getPosition().getWorldRegion().addVisibleObject(this);
		}

		// this can synchronize on others instancies, so it's out of
		// synchronized, to avoid deadlocks
		// Add the L2Object spawn in the world as a visible object
		L2World.getInstance().addVisibleObject(this, getWorldRegion());
		
		onSpawn();
	}

	public final void spawnMe(int x, int y, int z)
	{
		synchronized (this)
		{
			if (x > L2World.WORLD_X_MAX)
				x = L2World.WORLD_X_MAX - 5000;
			if (x < L2World.WORLD_X_MIN)
				x = L2World.WORLD_X_MIN + 5000;
			if (y > L2World.WORLD_Y_MAX)
				y = L2World.WORLD_Y_MAX - 5000;
			if (y < L2World.WORLD_Y_MIN)
				y = L2World.WORLD_Y_MIN + 5000;
			if (z > L2World.WORLD_Z_MAX)
				z = L2World.WORLD_Z_MAX - 1000;
			if (z < L2World.WORLD_Z_MIN)
				z = L2World.WORLD_Z_MIN + 1000;

			setXYZ(x, y, z);
		}
		
		// Spawn and update its _worldregion
		spawnMe();
		
		_isVisible = true;
	}

	public void toggleVisible()
	{
		if (isVisible())
			decayMe();
		else
			spawnMe();
	}

	public boolean isInSurroundingRegion(L2Object worldObject)
	{
		if (worldObject == null)
			return false;

		final L2WorldRegion worldRegion1 = worldObject.getWorldRegion();
		
		if (worldRegion1 == null)
			return false;
		
		final L2WorldRegion worldRegion2 = getWorldRegion();
		
		if (worldRegion2 == null)
			return false;
		
		return worldRegion1.isSurroundingRegion(worldRegion2);
	}
	
	public boolean isAttackable()
	{
		return false;
	}

	public abstract boolean isAutoAttackable(L2Character attacker);

	public boolean isMarker()
	{
		return false;
	}

	/**
	 * Return the visibility state of the L2Object. <BR>
	 * <BR>
	 * <B><U> Concept</U> :</B><BR>
	 * <BR>
	 * A L2Object is visible if <B>__IsVisible</B>=true and <B>_worldregion</B>!=null <BR>
	 * <BR>
	 */
	public final boolean isVisible()
	{
		 return _isVisible;
	}

	public final void setIsVisible(boolean value)
	{
		_isVisible = value;
		
		if (!_isVisible)
		{
			getPosition().setWorldRegion(null);

			final DeleteObject deletePacket = new DeleteObject(this);
			
			L2World.getInstance().forEachVisibleObject(this, L2PcInstance.class, player ->
			{
				player.sendPacket(deletePacket);
			});
					
		}

		broadcastInfo();
	}

	public final String getName()
	{
		return _name;
	}

	public final void setName(String value)
	{
		_name = value;
	}

	public final int getObjectId()
	{
		return _objectId;
	}

	public final ObjectPoly getPoly()
	{
		if (_poly == null)
			_poly = new ObjectPoly(this);
		return _poly;
	}

	public final ObjectPosition getPosition()
	{
		if (_position == null)
			_position = new ObjectPosition(this);
		return _position;
	}

	/**
	 * returns reference to region this object is in
	 */
	public L2WorldRegion getWorldRegion()
	{
		return getPosition().getWorldRegion();
	}

	/**
	 * @return The id of the instance zone the object is in - id 0 is global
	 *         since everything like dropped items, mobs, players can be in a instanciated area, it must be in l2object
	 */
	public int getInstanceId()
	{
		return _instanceId;
	}

	/**
	 * @param instanceId
	 *        The id of the instance zone the object is in - id 0 is global
	 */
	public void setInstanceId(int instanceId)
	{
		_instanceId = instanceId;
		
		if (_isVisible && !(this instanceof L2PcInstance))
		{
			decayMe();
			spawnMe();
		}
	}
	
	public int getHeading()
	{
		return getPosition().getHeading();
	}
	
	@Override
	public String toString()
	{
		return "" + getObjectId();
	}

	public boolean isInFunEvent()
	{
		L2PcInstance player = getActingPlayer();

		return (player == null ? false : player.isInFunEvent());
	}

	public L2PcInstance getActingPlayer()
	{
		return null;
	}
	
	/**
	 * Verify if object is instance of L2Playable.
	 * @return {@code true} if object is instance of L2Playable, {@code false} otherwise
	 */
	public boolean isPlayable()
	{
		return this instanceof L2Playable;
	}
	
	public boolean isPlayer()
	{
		return this instanceof L2PcInstance;
	}

	public abstract void sendInfo(L2PcInstance activeChar);
	
	/**
	 * Broadcasts describing info to known players.
	 */
	public void broadcastInfo()
	{
		L2World.getInstance().forEachVisibleObject(this, L2PcInstance.class, player ->
		{
			if (isVisible())
				sendInfo(player);
		});
	}

	public final double calculateDistance(int x, int y, int z, boolean includeZAxis, boolean squared)
	{
		final double distance = Math.pow(x - getX(), 2) + Math.pow(y - getY(), 2) + (includeZAxis ? Math.pow(z - getZ(), 2) : 0);
		return (squared) ? distance : Math.sqrt(distance);
	}

	public final double calculateDistance(L2Object loc, boolean includeZAxis, boolean squared)
	{
		return calculateDistance(loc.getX(), loc.getY(), loc.getZ(), includeZAxis, squared);
	}

	public boolean isInsideZone(ZoneId zone)
	{
		return false;
	}
}