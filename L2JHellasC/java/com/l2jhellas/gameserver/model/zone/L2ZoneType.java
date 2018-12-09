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
package com.l2jhellas.gameserver.model.zone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.quest.Quest;
import com.l2jhellas.gameserver.model.quest.QuestEventType;
import com.l2jhellas.gameserver.network.serverpackets.L2GameServerPacket;

/**
 * Abstract base class for any zone type
 * Handles basic operations
 * 
 * @author durgus
 */
public abstract class L2ZoneType
{
	
protected static final Logger _log = Logger.getLogger(L2ZoneType.class.getName());
	
	private final int _id;
	protected L2ZoneForm _zone;
	protected final Map<Integer, L2Character> _characterList = new ConcurrentHashMap<>();

	private Map<QuestEventType, List<Quest>> _questEvents;
	
	protected L2ZoneType(int id)
	{
		_id = id;
	}
	
	/**
	 * @return Returns the id.
	 */
	public int getId()
	{
		return _id;
	}
	
	/**
	 * Setup new parameters for this zone
	 * @param name parameter name.
	 * @param value new parameter value.
	 */
	public void setParameter(String name, String value)
	{
		_log.info(getClass().getSimpleName() + ": Unknown parameter - " + name + " in zone: " + getId());
	}
	
	/**
	 * @param character The character to test.
	 * @return True if the given character is affected by this zone.
	 */
	protected boolean isAffected(L2Character character)
	{
		// Overriden in children classes.
		return true;
	}
	
	/**
	 * Set the zone for this L2ZoneType Instance
	 * @param zone
	 */
	public void setZone(L2ZoneForm zone)
	{
		if (_zone != null)
			throw new IllegalStateException("Zone already set");
		_zone = zone;
	}
	
	/**
	 * @return this zone form.
	 */
	public L2ZoneForm getZone()
	{
		return _zone;
	}
	
	/**
	 * @param x
	 * @param y
	 * @return true if the given coordinates are within zone's plane
	 */
	public boolean isInsideZone(int x, int y)
	{
		return _zone.isInsideZone(x, y, _zone.getHighZ());
	}
	
	/**
	 * @param x
	 * @param y
	 * @param z
	 * @return true if the given coordinates are within the zone
	 */
	public boolean isInsideZone(int x, int y, int z)
	{
		return _zone.isInsideZone(x, y, z);
	}
	
	/**
	 * @param object check object's X/Y positions.
	 * @return true if the given object is inside the zone.
	 */
	public boolean isInsideZone(L2Object object)
	{
		return isInsideZone(object.getX(), object.getY(), object.getZ());
	}
	
	public double getDistanceToZone(int x, int y)
	{
		return getZone().getDistanceToZone(x, y);
	}
	
	public double getDistanceToZone(L2Object object)
	{
		return getZone().getDistanceToZone(object.getX(), object.getY());
	}
	
	public void revalidateInZone(L2Character character)
	{
		// If the character can't be affected by this zone return
		if (!isAffected(character))
			return;
		
		// If the object is inside the zone...
		if (isInsideZone(character))
		{
			// Was the character not yet inside this zone?
			if (!_characterList.containsKey(character.getObjectId()))
			{
				// Notify to scripts.
				final List<Quest> quests = getQuestByEvent(QuestEventType.ON_ENTER_ZONE);
				if (quests != null)
				{
					for (Quest quest : quests)
						quest.notifyEnterZone(character, this);
				}
				
				// Register player.
				_characterList.put(character.getObjectId(), character);
				
				// Notify Zone implementation.
				onEnter(character);
			}
		}
		else
			removeCharacter(character);
	}
	
	/**
	 * Removes a character from the zone.
	 */
	public void removeCharacter(L2Character character)
	{
		// Was the character inside this zone?
		if (_characterList.containsKey(character.getObjectId()))
		{
			// Notify to scripts.
			final List<Quest> quests = getQuestByEvent(QuestEventType.ON_EXIT_ZONE);
			if (quests != null)
			{
				for (Quest quest : quests)
					quest.notifyExitZone(character, this);
			}
			
			// Unregister player.
			_characterList.remove(character.getObjectId());
			
			// Notify Zone implementation.
			onExit(character);
		}
	}
	
	/**
	 * @param character The character to test.
	 * @return True if the character is in the zone.
	 */
	public boolean isCharacterInZone(L2Character character)
	{
		return character!=null && _characterList.containsKey(character.getObjectId()) || isInsideZone(character.getX(), character.getY(), character.getZ());
	}
	
	protected abstract void onEnter(L2Character character);
	
	protected abstract void onExit(L2Character character);
	
	public abstract void onDieInside(L2Character character);
	
	public abstract void onReviveInside(L2Character character);

	public Collection<L2Character> getCharactersInside()
	{
		return _characterList.values();
	}
	
	/**
	 * @param <A>
	 * @param type
	 * @return a list of given instances within this zone.
	 */
	
	@SuppressWarnings("unchecked")
	public final <A> List<A> getKnownTypeInside(Class<A> type)
	{
		List<A> result = new ArrayList<>();
		
		for (L2Object obj : _characterList.values())
		{
			if (type.isAssignableFrom(obj.getClass()))
				result.add((A) obj);
		}
		return result;
	}
	
	public void addQuestEvent(QuestEventType QuestEventType, Quest quest)
	{
		if (_questEvents == null)
			_questEvents = new HashMap<>();
		
		List<Quest> eventList = _questEvents.get(QuestEventType);
		if (eventList == null)
		{
			eventList = new ArrayList<>();
			eventList.add(quest);
			_questEvents.put(QuestEventType, eventList);
		}
		else
		{
			eventList.remove(quest);
			eventList.add(quest);
		}
	}
	
	public List<Quest> getQuestByEvent(QuestEventType QuestEventType)
	{
		return (_questEvents == null) ? null : _questEvents.get(QuestEventType);
	}
	
	/**
	 * Broadcasts packet to all players inside the zone
	 * @param packet The packet to use.
	 */
	public void broadcastPacket(L2GameServerPacket packet)
	{
		   if(!getKnownTypeInside(L2PcInstance.class).isEmpty())
		   {
			   for(L2PcInstance players: getKnownTypeInside(L2PcInstance.class))
			   {
				   players.sendPacket(packet);
			   }
		   }
	}
	
	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "[" + _id + "]";
	}
	
	public void visualizeZone(int z)
	{
		getZone().visualizeZone(z);
	}
}