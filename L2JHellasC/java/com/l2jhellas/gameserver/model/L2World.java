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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javolution.util.FastList;
import javolution.util.FastMap;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.datatables.xml.AdminData;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.L2Playable;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PetInstance;
import com.l2jhellas.util.Point3D;
import com.l2jhellas.util.object.L2ObjectMap;

public final class L2World
{
	private static Logger _log = Logger.getLogger(L2World.class.getName());

	/*
	 * biteshift, defines number of regions
	 * note, shifting by 15 will result in regions corresponding to map tiles
	 * shifting by 12 divides one tile to 8x8 regions
	 */
	public static final int SHIFT_BY = 12;

	/** Map dimensions */
	public static final int MAP_MIN_X = -131072;
	public static final int MAP_MAX_X = 228608;
	public static final int MAP_MIN_Y = -262144;
	public static final int MAP_MAX_Y = 262144;

	/** calculated offset used so top left region is 0,0 */
	public static final int OFFSET_X = Math.abs(MAP_MIN_X >> SHIFT_BY);
	public static final int OFFSET_Y = Math.abs(MAP_MIN_Y >> SHIFT_BY);

	/** number of regions */
	private static final int REGIONS_X = (MAP_MAX_X >> SHIFT_BY) + OFFSET_X;
	private static final int REGIONS_Y = (MAP_MAX_Y >> SHIFT_BY) + OFFSET_Y;

	//private FastMap<String, L2PcInstance> _allGms;

	/** HashMap(String Player name, L2PcInstance) containing all the players in game */
	private static Map<String, L2PcInstance> _allPlayers;

	private static Map<String, L2PcInstance> _allgoodPlayers;
	private static Map<String, L2PcInstance> _allevilPlayers;

	/** L2ObjectHashMap(L2Object) containing all visible objects */
	private static L2ObjectMap<L2Object> _allObjects;

	/** List with the pets instances and their owner id */
	private static FastMap<Integer, L2PetInstance> _petsInstance;

	private static L2WorldRegion[][] _worldRegions;

	/**
	 * Constructor of L2World.<BR>
	 * <BR>
	 */
	private L2World()
	{

	}

	public static void init()
	{
		//_allGms     = new FastMap<String, L2PcInstance>();
		_allPlayers = new FastMap<String, L2PcInstance>().setShared(true);
		_allgoodPlayers = new FastMap<String, L2PcInstance>().setShared(true);
		_allevilPlayers = new FastMap<String, L2PcInstance>().setShared(true);
		_petsInstance = new FastMap<Integer, L2PetInstance>().setShared(true);
		_allObjects = L2ObjectMap.createL2ObjectMap();

		initRegions();
	}

	public static void forceObject(L2Object object)
	{
		_allObjects.put(object);
	}

	/**
	 * Add L2Object object in _allObjects.<BR>
	 * <BR>
	 * <B><U> Example of use </U> :</B><BR>
	 * <BR>
	 * <li>Withdraw an item from the warehouse, create an item</li> <li>Spawn a L2Character (PC, NPC, Pet)</li><BR>
	 */
	public static void storeObject(L2Object object)
	{
		if (_allObjects.get(object.getObjectId()) != null)
		{
			if (Config.DEBUG)
			{
				_log.warning("[L2World] objectId " + object.getObjectId() + " already exist in OID map!");
			}
			return;
		}

		_allObjects.put(object);
	}

	public long timeStoreObject(L2Object object)
	{
		long time = System.currentTimeMillis();
		_allObjects.put(object);
		time -= System.currentTimeMillis();
		return time;
	}

	/**
	 * Remove L2Object object from _allObjects of L2World.<BR>
	 * <BR>
	 * <B><U> Example of use </U> :</B><BR>
	 * <BR>
	 * <li>Delete item from inventory, transfer Item from inventory to warehouse</li> <li>Crystallize item</li> <li>Remove NPC/PC/Pet from the world</li><BR>
	 * 
	 * @param object
	 *        L2Object to remove from _allObjects of L2World
	 */
	public static void removeObject(L2Object object)
	{
		_allObjects.remove(object);  // suggestion by whatev
		//IdFactory.getInstance().releaseId(object.getObjectId());
	}

	public static void removeObjects(List<L2Object> list)
	{
		for (L2Object o : list)
		{
			_allObjects.remove(o);  // suggestion by whatev
			//IdFactory.getInstance().releaseId(object.getObjectId());
		}
	}

	public void removeObjects(L2Object[] objects)
	{
		for (L2Object o : objects)
		{
			_allObjects.remove(o);  // suggestion by whatev
			//IdFactory.getInstance().releaseId(object.getObjectId());
		}
	}

	public long timeRemoveObject(L2Object object)
	{
		long time = System.currentTimeMillis();
		_allObjects.remove(object);
		time -= System.currentTimeMillis();
		return time;
	}

	/**
	 * Return the L2Object object that belongs to an ID or null if no object found.<BR>
	 * <BR>
	 * <B><U> Example of use </U> :</B><BR>
	 * <BR>
	 * <li>Client packets : Action, AttackRequest, RequestJoinParty, RequestJoinPledge...</li><BR>
	 * 
	 * @param oID
	 *        Identifier of the L2Object
	 */
	public static L2Object findObject(int oID)
	{
		return _allObjects.get(oID);
	}

	public long timeFindObject(int objectID)
	{
		long time = System.currentTimeMillis();
		_allObjects.get(objectID);
		time -= System.currentTimeMillis();
		return time;
	}

	/**
	 * Added by Tempy - 08 Aug 05
	 * Allows easy retrieval of all visible objects in world.
	 * -- do not use that function, its unsafe!
	 * 
	 * @deprecated
	 */
	@Deprecated
	public final static L2ObjectMap<L2Object> getAllVisibleObjects()
	{
		return _allObjects;
	}

	/**
	 * Get the count of all visible objects in world.<br>
	 * <br>
	 * 
	 * @return count off all L2World objects
	 */
	public final static int getAllVisibleObjectsCount()
	{
		return _allObjects.size();
	}

	/**
	 * Return a table containing all GMs.<BR>
	 * <BR>
	 */
	public List<L2PcInstance> getAllGMs()
	{
		return AdminData.getInstance().getAllGms(true);
	}

	/**
	 * Return a collection containing all players in game.<BR>
	 * <BR>
	 * <FONT COLOR=#FF0000><B> <U>Caution</U> : Read-only, please! </B></FONT><BR>
	 * <BR>
	 */
	public static Collection<L2PcInstance> getAllPlayers()
	{
		return _allPlayers.values();
	}

	public static Collection<L2PcInstance> getAllgoodPlayers()
	{
		return _allgoodPlayers.values();
	}

	public static Collection<L2PcInstance> getAllevilPlayers()
	{
		return _allevilPlayers.values();
	}

	/**
	 * Return how many players are online.<BR>
	 * <BR>
	 * 
	 * @return number of online players.
	 */
	public static int getAllPlayersCount()
	{
		return _allPlayers.size();
	}

	public int getAllgoodPlayersCount()
	{
		return _allgoodPlayers.size();
	}

	public int getAllevilPlayersCount()
	{
		return _allevilPlayers.size();
	}
	/**
	 * @param name Name of the player to get Instance
	 * @return the player instance corresponding to the given name.
	 */
	public static L2PcInstance getPlayer(String name)
	{
		return _allPlayers.get(name.toLowerCase());
	}
	
	/**
	 * @param playerObjId Object ID of the player to get Instance
	 * @return the player instance corresponding to the given object ID.
	 */
	public static L2PcInstance getPlayer(int playerObjId)
	{
		return _allPlayers.get(Integer.valueOf(playerObjId));
	}
	/**
	 * Return a collection containing all pets in game.<BR>
	 * <BR>
	 * <FONT COLOR=#FF0000><B> <U>Caution</U> : Read-only, please! </B></FONT><BR>
	 * <BR>
	 */
	public Collection<L2PetInstance> getAllPets()
	{
		return _petsInstance.values();
	}

	/**
	 * Return the pet instance from the given ownerId.<BR>
	 * <BR>
	 * 
	 * @param ownerId
	 *        ID of the owner
	 */
	public static L2PetInstance getPet(int ownerId)
	{
		return _petsInstance.get(new Integer(ownerId));
	}

	/**
	 * Add the given pet instance from the given ownerId.<BR>
	 * <BR>
	 * 
	 * @param ownerId
	 *        ID of the owner
	 * @param pet
	 *        L2PetInstance of the pet
	 */
	public static L2PetInstance addPet(int ownerId, L2PetInstance pet)
	{
		return _petsInstance.put(new Integer(ownerId), pet);
	}

	/**
	 * Remove the given pet instance.<BR>
	 * <BR>
	 * 
	 * @param ownerId
	 *        ID of the owner
	 */
	public static void removePet(int ownerId)
	{
		_petsInstance.remove(new Integer(ownerId));
	}

	/**
	 * Remove the given pet instance.<BR>
	 * <BR>
	 * 
	 * @param pet
	 *        the pet to remove
	 */
	public void removePet(L2PetInstance pet)
	{
		_petsInstance.values().remove(pet);
	}

	/**
	 * Add a L2Object in the world.<BR>
	 * <BR>
	 * <B><U> Concept</U> :</B><BR>
	 * <BR>
	 * L2Object (including L2PcInstance) are identified in <B>_visibleObjects</B> of his current L2WorldRegion and in <B>_knownObjects</B> of other surrounding L2Characters <BR>
	 * L2PcInstance are identified in <B>_allPlayers</B> of L2World, in <B>_allPlayers</B> of his current L2WorldRegion and in <B>_knownPlayer</B> of other surrounding L2Characters <BR>
	 * <BR>
	 * <B><U> Actions</U> :</B><BR>
	 * <BR>
	 * <li>Add the L2Object object in _allPlayers* of L2World</li> <li>Add the L2Object object in _gmList** of GmListTable</li> <li>Add object in _knownObjects and _knownPlayer* of
	 * all surrounding L2WorldRegion L2Characters</li><BR>
	 * <li>If object is a L2Character, add all surrounding L2Object in its _knownObjects and all surrounding L2PcInstance in its _knownPlayer</li><BR>
	 * <I>* only if object is a L2PcInstance</I><BR>
	 * <I>** only if object is a GM L2PcInstance</I><BR>
	 * <BR>
	 * <FONT COLOR=#FF0000><B> <U>Caution</U> : This method DOESN'T ADD the object in _visibleObjects and _allPlayers* of L2WorldRegion (need synchronisation)</B></FONT><BR>
	 * <FONT COLOR=#FF0000><B> <U>Caution</U> : This method DOESN'T ADD the object to _allObjects and _allPlayers* of L2World (need synchronisation)</B></FONT><BR>
	 * <BR>
	 * <B><U> Example of use </U> :</B><BR>
	 * <BR>
	 * <li>Drop an Item</li> <li>Spawn a L2Character</li> <li>Apply Death Penalty of a L2PcInstance</li><BR>
	 * <BR>
	 * 
	 * @param object
	 *        L2object to add in the world
	 * @param newregion
	 *        L2WorldRegion in wich the object will be add (not used)
	 * @param dropper
	 *        L2Character who has dropped the object (if necessary)
	 */
	public static void addVisibleObject(L2Object object, L2WorldRegion newRegion, L2Character dropper)
	{
		// If selected L2Object is a L2PcIntance, add it in L2ObjectHashSet(L2PcInstance) _allPlayers of L2World
		// XXX TODO: this code should be obsoleted by protection in putObject func...
		if (object instanceof L2PcInstance)
		{
			L2PcInstance player = (L2PcInstance) object;

			if (!player.isTeleporting())
			{
				L2PcInstance tmp = _allPlayers.get(player.getName().toLowerCase());
				if (tmp != null)
				{
					_log.warning("Duplicate character? Closing both characters: (" + player.getName() + ")");
					player.closeNetConnection();
					tmp.closeNetConnection();
					return;
				}
				_allPlayers.put(player.getName().toLowerCase(), player);
			}
			// l2jhellas Faction Good vs Evil
			// If selected L2Object is a good or evil Faction Added as Known Object
			if (Config.MOD_GVE_ENABLE_FACTION)
			{
				if (((L2PcInstance) object).isgood())
				{
					_allgoodPlayers.put(player.getName().toLowerCase(), player);
				}

				if (((L2PcInstance) object).isevil())
				{
					_allevilPlayers.put(player.getName().toLowerCase(), player);
				}
			}
			_allPlayers.put(player.getName().toLowerCase(), player);
		}

		// Get all visible objects contained in the _visibleObjects of L2WorldRegions
		// in a circular area of 2000 units
		FastList<L2Object> visibles = getVisibleObjects(object, 2000);
		if (Config.DEBUG)
		{
			_log.finest("objects in range:" + visibles.size());
		}

		// tell the player about the surroundings
		// Go through the visible objects contained in the circular area
		for (L2Object visible : visibles)
		{
			// Add the object in L2ObjectHashSet(L2Object) _knownObjects of the visible L2Character according to conditions :
			//   - L2Character is visible
			//   - object is not already known
			//   - object is in the watch distance
			// If L2Object is a L2PcInstance, add L2Object in L2ObjectHashSet(L2PcInstance) _knownPlayer of the visible L2Character
			visible.getKnownList().addKnownObject(object, dropper);

			// Add the visible L2Object in L2ObjectHashSet(L2Object) _knownObjects of the object according to conditions
			// If visible L2Object is a L2PcInstance, add visible L2Object in L2ObjectHashSet(L2PcInstance) _knownPlayer of the object
			object.getKnownList().addKnownObject(visible, dropper);
		}
	}

	/**
	 * Add the L2PcInstance to _allPlayers of L2World.<BR>
	 * <BR>
	 */
	public static void addToAllPlayers(L2PcInstance cha)
	{
		_allPlayers.put(cha.getName().toLowerCase(), cha);
	}

	/**
	 * Remove the L2PcInstance from _allPlayers of L2World.<BR>
	 * <BR>
	 * <B><U> Example of use </U> :</B><BR>
	 * <BR>
	 * <li>Remove a player from the visible objects</li><BR>
	 */
	public static void removeFromAllPlayers(L2PcInstance cha)
	{
		_allPlayers.remove(cha.getName().toLowerCase());
	}

	/**
	 * Remove a L2Object from the world.<BR>
	 * <BR>
	 * <B><U> Concept</U> :</B><BR>
	 * <BR>
	 * L2Object (including L2PcInstance) are identified in <B>_visibleObjects</B> of his current L2WorldRegion and in <B>_knownObjects</B> of other surrounding L2Characters <BR>
	 * L2PcInstance are identified in <B>_allPlayers</B> of L2World, in <B>_allPlayers</B> of his current L2WorldRegion and in <B>_knownPlayer</B> of other surrounding L2Characters <BR>
	 * <BR>
	 * <B><U> Actions</U> :</B><BR>
	 * <BR>
	 * <li>Remove the L2Object object from _allPlayers* of L2World</li> <li>Remove the L2Object object from _visibleObjects and _allPlayers* of L2WorldRegion</li> <li>Remove the
	 * L2Object object from _gmList** of GmListTable</li> <li>Remove object from _knownObjects and _knownPlayer* of all surrounding L2WorldRegion L2Characters</li><BR>
	 * <li>If object is a L2Character, remove all L2Object from its _knownObjects and all L2PcInstance from its _knownPlayer</li><BR>
	 * <BR>
	 * <FONT COLOR=#FF0000><B> <U>Caution</U> : This method DOESN'T REMOVE the object from _allObjects of L2World</B></FONT><BR>
	 * <BR>
	 * <I>* only if object is a L2PcInstance</I><BR>
	 * <I>** only if object is a GM L2PcInstance</I><BR>
	 * <BR>
	 * <B><U> Example of use </U> :</B><BR>
	 * <BR>
	 * <li>Pickup an Item</li> <li>Decay a L2Character</li><BR>
	 * <BR>
	 * 
	 * @param object
	 *        L2object to remove from the world
	 * @param oldregion
	 *        L2WorldRegion in wich the object was before removing
	 */
	public static void removeVisibleObject(L2Object object, L2WorldRegion oldRegion)
	{
		if (object == null)
			return;

		//removeObject(object);

		if (oldRegion != null)
		{
			// Remove the object from the L2ObjectHashSet(L2Object) _visibleObjects of L2WorldRegion
			// If object is a L2PcInstance, remove it from the L2ObjectHashSet(L2PcInstance) _allPlayers of this L2WorldRegion
			oldRegion.removeVisibleObject(object);

			// Go through all surrounding L2WorldRegion L2Characters
			for (L2WorldRegion reg : oldRegion.getSurroundingRegions())
			{
				for (L2Object obj : reg.getVisibleObjects())
				{
					// Remove the L2Object from the L2ObjectHashSet(L2Object) _knownObjects of the surrounding L2WorldRegion L2Characters
					// If object is a L2PcInstance, remove the L2Object from the L2ObjectHashSet(L2PcInstance) _knownPlayer of the surrounding L2WorldRegion L2Characters
					// If object is targeted by one of the surrounding L2WorldRegion L2Characters, cancel ATTACK and cast
					if (obj != null && obj.getKnownList() != null)
					{
						obj.getKnownList().removeKnownObject(object);
					}

					// Remove surrounding L2WorldRegion L2Characters from the L2ObjectHashSet(L2Object) _KnownObjects of object
					// If surrounding L2WorldRegion L2Characters is a L2PcInstance, remove it from the L2ObjectHashSet(L2PcInstance) _knownPlayer of object
					// TODO Delete this line if all the stuff is done by the next line object.removeAllKnownObjects()
					if (object.getKnownList() != null)
					{
						object.getKnownList().removeKnownObject(obj);
					}
				}
			}

			// If object is a L2Character :
			// Remove all L2Object from L2ObjectHashSet(L2Object) containing all L2Object detected by the L2Character
			// Remove all L2PcInstance from L2ObjectHashSet(L2PcInstance) containing all player ingame detected by the L2Character
			object.getKnownList().removeAllKnownObjects();

			// If selected L2Object is a L2PcIntance, remove it from L2ObjectHashSet(L2PcInstance) _allPlayers of L2World
			if (object instanceof L2PcInstance)
			{
				if (!((L2PcInstance) object).isTeleporting())
				{
					removeFromAllPlayers((L2PcInstance) object);
				}

				// If selected L2Object is a GM L2PcInstance, remove it from Set(L2PcInstance) _gmList of GmListTable
				//if (((L2PcInstance)object).isGM())
				//GmListTable.getInstance().deleteGm((L2PcInstance)object);
			}

		}
	}

	/**
	 * Return all visible objects of the L2WorldRegion object's and of its surrounding L2WorldRegion.<BR>
	 * <BR>
	 * <B><U> Concept</U> :</B><BR>
	 * <BR>
	 * All visible object are identified in <B>_visibleObjects</B> of their current L2WorldRegion <BR>
	 * All surrounding L2WorldRegion are identified in <B>_surroundingRegions</B> of the selected L2WorldRegion in order to scan a large area around a L2Object<BR>
	 * <BR>
	 * <B><U> Example of use </U> :</B><BR>
	 * <BR>
	 * <li>Find Close Objects for L2Character</li><BR>
	 * 
	 * @param object
	 *        L2object that determine the current L2WorldRegion
	 */
	public static FastList<L2Object> getVisibleObjects(L2Object object)
	{
		L2WorldRegion reg = object.getWorldRegion();

		if (reg == null)
			return null;

		// Create an FastList in order to contain all visible L2Object
		FastList<L2Object> result = new FastList<L2Object>();

		// Create a FastList containing all regions around the current region
		FastList<L2WorldRegion> _regions = reg.getSurroundingRegions();

		// Go through the FastList of region
		for (int i = 0; i < _regions.size(); i++)
		{
			// Go through visible objects of the selected region
			for (L2Object _object : _regions.get(i).getVisibleObjects())
			{
				if (_object == null)
				{
					continue;
				}
				if (_object.equals(object))
				{
					continue;   // skip our own character
				}
				if (!_object.isVisible())
				{
					continue;   // skip dying objects
				}

				result.add(_object);
			}
		}

		return result;
	}

	/**
	 * Return all visible objects of the L2WorldRegions in the circular area (radius) centered on the object.<BR>
	 * <BR>
	 * <B><U> Concept</U> :</B><BR>
	 * <BR>
	 * All visible object are identified in <B>_visibleObjects</B> of their current L2WorldRegion <BR>
	 * All surrounding L2WorldRegion are identified in <B>_surroundingRegions</B> of the selected L2WorldRegion in order to scan a large area around a L2Object<BR>
	 * <BR>
	 * <B><U> Example of use </U> :</B><BR>
	 * <BR>
	 * <li>Define the aggrolist of monster</li> <li>Define visible objects of a L2Object</li> <li>Skill : Confusion...</li><BR>
	 * 
	 * @param object
	 *        L2object that determine the center of the circular area
	 * @param radius
	 *        Radius of the circular area
	 */
	public static FastList<L2Object> getVisibleObjects(L2Object object, int radius)
	{
		if (object == null || !object.isVisible())
			return new FastList<L2Object>();

		int x = object.getX();
		int y = object.getY();
		int sqRadius = radius * radius;

		// Create an FastList in order to contain all visible L2Object
		FastList<L2Object> result = new FastList<L2Object>();

		// Create an FastList containing all regions around the current region
		FastList<L2WorldRegion> _regions = object.getWorldRegion().getSurroundingRegions();

		// Go through the FastList of region
		for (int i = 0; i < _regions.size(); i++)
		{
			// Go through visible objects of the selected region
			for (L2Object _object : _regions.get(i).getVisibleObjects())
			{
				if (_object == null)
				{
					continue;
				}
				if (_object.equals(object))
				{
					continue;   // skip our own character
				}

				int x1 = _object.getX();
				int y1 = _object.getY();

				double dx = x1 - x;
				//if (dx > radius || -dx > radius)
				//  continue;
				double dy = y1 - y;
				//if (dy > radius || -dy > radius)
				//  continue;

				// If the visible object is inside the circular area
				// add the object to the FastList result
				if (dx * dx + dy * dy < sqRadius)
				{
					result.add(_object);
				}
			}
		}

		return result;
	}

	/**
	 * Return all visible objects of the L2WorldRegions in the spheric area (radius) centered on the object.<BR>
	 * <BR>
	 * <B><U> Concept</U> :</B><BR>
	 * <BR>
	 * All visible object are identified in <B>_visibleObjects</B> of their current L2WorldRegion <BR>
	 * All surrounding L2WorldRegion are identified in <B>_surroundingRegions</B> of the selected L2WorldRegion in order to scan a large area around a L2Object<BR>
	 * <BR>
	 * <B><U> Example of use </U> :</B><BR>
	 * <BR>
	 * <li>Define the target list of a skill</li> <li>Define the target list of a polearm attack</li><BR>
	 * <BR>
	 * 
	 * @param object
	 *        L2object that determine the center of the circular area
	 * @param radius
	 *        Radius of the spheric area
	 */
	public FastList<L2Object> getVisibleObjects3D(L2Object object, int radius)
	{
		if (object == null || !object.isVisible())
			return new FastList<L2Object>();

		int x = object.getX();
		int y = object.getY();
		int z = object.getZ();
		int sqRadius = radius * radius;

		// Create an FastList in order to contain all visible L2Object
		FastList<L2Object> result = new FastList<L2Object>();

		// Create an FastList containing all regions around the current region
		FastList<L2WorldRegion> _regions = object.getWorldRegion().getSurroundingRegions();

		// Go through visible object of the selected region
		for (int i = 0; i < _regions.size(); i++)
		{
			for (L2Object _object : _regions.get(i).getVisibleObjects())
			{
				if (_object == null)
				{
					continue;
				}
				if (_object.equals(object))
				{
					continue;   // skip our own character
				}

				int x1 = _object.getX();
				int y1 = _object.getY();
				int z1 = _object.getZ();

				long dx = x1 - x;
				//if (dx > radius || -dx > radius)
				//  continue;
				long dy = y1 - y;
				//if (dy > radius || -dy > radius)
				//  continue;
				long dz = z1 - z;

				if (dx * dx + dy * dy + dz * dz < sqRadius)
				{
					result.add(_object);
				}
			}
		}

		return result;
	}

	/**
	 * Return all visible players of the L2WorldRegion object's and of its surrounding L2WorldRegion.<BR>
	 * <BR>
	 * <B><U> Concept</U> :</B><BR>
	 * <BR>
	 * All visible object are identified in <B>_visibleObjects</B> of their current L2WorldRegion <BR>
	 * All surrounding L2WorldRegion are identified in <B>_surroundingRegions</B> of the selected L2WorldRegion in order to scan a large area around a L2Object<BR>
	 * <BR>
	 * <B><U> Example of use </U> :</B><BR>
	 * <BR>
	 * <li>Find Close Objects for L2Character</li><BR>
	 * 
	 * @param object
	 *        L2object that determine the current L2WorldRegion
	 */
	public static FastList<L2Playable> getVisiblePlayable(L2Object object)
	{
		L2WorldRegion reg = object.getWorldRegion();

		if (reg == null)
			return null;

		// Create an FastList in order to contain all visible L2Object
		FastList<L2Playable> result = new FastList<L2Playable>();

		// Create a FastList containing all regions around the current region
		FastList<L2WorldRegion> _regions = reg.getSurroundingRegions();

		// Go through the FastList of region
		for (int i = 0; i < _regions.size(); i++)
		{
			// Create an Iterator to go through the visible L2Object of the L2WorldRegion
			Iterator<L2Playable> _playables = _regions.get(i).iterateAllPlayers();

			// Go through visible object of the selected region
			while (_playables.hasNext())
			{
				L2Playable _object = _playables.next();

				if (_object == null)
				{
					continue;
				}

				if (_object.equals(object))
				{
					continue;   // skip our own character
				}

				if (!_object.isVisible())
				{
					continue;   // skip dying objects
				}

				result.add(_object);
			}
		}

		return result;
	}

	/**
	 * Calculate the current L2WorldRegions of the object according to its position (x,y).<BR>
	 * <BR>
	 * <B><U> Example of use </U> :</B><BR>
	 * <BR>
	 * <li>Set position of a new L2Object (drop, spawn...)</li> <li>Update position of a L2Object after a movement</li><BR>
	 * 
	 * @param Point3D
	 *        point position of the object
	 */
	public static L2WorldRegion getRegion(Point3D point)
	{
		return _worldRegions[(point.getX() >> SHIFT_BY) + OFFSET_X][(point.getY() >> SHIFT_BY) + OFFSET_Y];
	}

	public static L2WorldRegion getRegion(int x, int y)
	{
		return _worldRegions[(x >> SHIFT_BY) + OFFSET_X][(y >> SHIFT_BY) + OFFSET_Y];
	}

	/**
	 * Returns the whole 2d array containing the world regions
	 * used by ZoneData.java to setup zones inside the world regions
	 * 
	 * @return
	 */
	public static L2WorldRegion[][] getAllWorldRegions()
	{
		return _worldRegions;
	}

	/**
	 * Check if the current L2WorldRegions of the object is valid according to its position (x,y).<BR>
	 * <BR>
	 * <B><U> Example of use </U> :</B><BR>
	 * <BR>
	 * <li>Init L2WorldRegions</li><BR>
	 * 
	 * @param x
	 *        X position of the object
	 * @param y
	 *        Y position of the object
	 * @return True if the L2WorldRegion is valid
	 */
	private static boolean validRegion(int x, int y)
	{
		return (x >= 0 && x <= REGIONS_X && y >= 0 && y <= REGIONS_Y);
	}

	/**
	 * Init each L2WorldRegion and their surrounding table.<BR>
	 * <BR>
	 * <B><U> Concept</U> :</B><BR>
	 * <BR>
	 * All surrounding L2WorldRegion are identified in <B>_surroundingRegions</B> of the selected L2WorldRegion in order to scan a large area around a L2Object<BR>
	 * <BR>
	 * <B><U> Example of use </U> :</B><BR>
	 * <BR>
	 * <li>Constructor of L2World</li><BR>
	 */
	private static void initRegions()
	{
		_log.info("L2World: Setting up World Regions");

		_worldRegions = new L2WorldRegion[REGIONS_X + 1][REGIONS_Y + 1];

		for (int i = 0; i <= REGIONS_X; i++)
		{
			for (int j = 0; j <= REGIONS_Y; j++)
			{
				_worldRegions[i][j] = new L2WorldRegion(i, j);
			}
		}

		for (int x = 0; x <= REGIONS_X; x++)
		{
			for (int y = 0; y <= REGIONS_Y; y++)
			{
				for (int a = -1; a <= 1; a++)
				{
					for (int b = -1; b <= 1; b++)
					{
						if (validRegion(x + a, y + b))
						{
							_worldRegions[x + a][y + b].addSurroundingRegion(_worldRegions[x][y]);
						}
					}
				}
			}
		}
		_log.info("L2World: (" + REGIONS_X + " by " + REGIONS_Y + ") World Region Grid set up.");
	}

	/**
	 * Deleted all spawns in the world.
	 */
	public synchronized static void deleteVisibleNpcSpawns()
	{
		_log.info("Deleting all visible NPC's.");
		for (int i = 0; i <= REGIONS_X; i++)
		{
			for (int j = 0; j <= REGIONS_Y; j++)
			{
				_worldRegions[i][j].deleteVisibleNpcSpawns();
			}
		}
		_log.info("All visible NPC's deleted.");
	}
}