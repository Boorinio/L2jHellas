/*
 * Copyright (C) 2004-2015 L2J Unity
 * 
 * This file is part of L2J Unity.
 * 
 * L2J Unity is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J Unity is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jhellas.gameserver.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.ai.CtrlIntention;
import com.l2jhellas.gameserver.datatables.sql.SpawnTable;
import com.l2jhellas.gameserver.instancemanager.ZoneManager;
import com.l2jhellas.gameserver.model.actor.L2Attackable;
import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.L2Playable;
import com.l2jhellas.gameserver.model.zone.L2ZoneType;

public final class L2WorldRegion
{
	private static final Logger _log = LoggerFactory.getLogger(L2WorldRegion.class);
	
	/** Map containing visible objects in this world region. */
	private final Map<Integer, L2Object> _visibleObjects = new ConcurrentHashMap<>();
	private final int _regionX;
	private final int _regionY;
	private final int _regionZ;
	private boolean _active = false;
	private ScheduledFuture<?> _neighborsTask = null;
	
	public L2WorldRegion(int regionX, int regionY, int regionZ)
	{
		_regionX = regionX;
		_regionY = regionY;
		_regionZ = regionZ;
		
		// default a newly initialized region to inactive, unless always on is specified
		_active = Config.GRIDS_ALWAYS_ON;
	}
	
	/** Task of AI notification */
	public class NeighborsTask implements Runnable
	{
		private final boolean _isActivating;
		
		public NeighborsTask(boolean isActivating)
		{
			_isActivating = isActivating;
		}
		
		@Override
		public void run()
		{
			if (_isActivating)
			{
				// for each neighbor, if it's not active, activate.
				forEachSurroundingRegion(w ->
				{
					w.setActive(true);
					return true;
				});
			}
			else
			{
				if (areNeighborsEmpty())
				{
					setActive(false);
				}
				
				// check and deactivate
				forEachSurroundingRegion(w ->
				{
					if (w.areNeighborsEmpty())
					{
						w.setActive(false);
					}
					return true;
				});
				
			}
		}
	}
	
	private void switchAI(boolean isOn)
	{
		if (_visibleObjects == null)
		{
			return;
		}

		if (!isOn)
		{
			Collection<L2Object> vObj = _visibleObjects.values();
			for (L2Object o : vObj)
			{
				if (o instanceof L2Attackable)
				{
					L2Attackable mob = (L2Attackable) o;
					
					// Set target to null and cancel Attack or Cast
					mob.setTarget(null);
					
					// Stop movement
					mob.stopMove(null);
					
					// Stop all active skills effects in progress on the L2Character
					mob.stopAllEffects();
					
					mob.clearAggroList();
					mob.getAttackByList().clear();
					
					// stop the ai tasks
					if (mob.hasAI())
					{
						mob.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
						mob.getAI().stopAITask();
					}
				}
			}
		}
		else
		{
			Collection<L2Object> vObj = _visibleObjects.values();
			
			for (L2Object o : vObj)
			{
				if (o instanceof L2Attackable)
				{
					((L2Attackable) o).getStatus().startHpMpRegeneration();
				}
				else if (o instanceof L2Npc)
				{
					((L2Npc) o).startRandomAnimationTimer();
				}
			}		
		}
		
	}
	
	public boolean isActive()
	{
		return _active;
	}
	
	public boolean areNeighborsEmpty()
	{
		return !forEachSurroundingRegion(w ->
		{
			return !(w.isActive() && w.getVisibleObjects().values().stream().anyMatch(L2Object::isPlayable));
		});
	}
	
	/**
	 * this function turns this region's AI and geodata on or off
	 * @param value
	 */
	public void setActive(boolean value)
	{
		if (_active == value)
		{
			return;
		}
		
		_active = value;
		
		// turn the AI on or off to match the region's activation.
		switchAI(value);
		
		_log.debug("{} Grid {}", (value ? "Starting" : "Stoping"), getName());
	}
	
	/**
	 * Immediately sets self as active and starts a timer to set neighbors as active this timer is to avoid turning on neighbors in the case when a person just teleported into a region and then teleported out immediately...there is no reason to activate all the neighbors in that case.
	 */
	private void startActivation()
	{
		// first set self to active and do self-tasks...
		setActive(true);
		
		// if the timer to deactivate neighbors is running, cancel it.
		synchronized (this)
		{
			if (_neighborsTask != null)
			{
				_neighborsTask.cancel(true);
				_neighborsTask = null;
			}
			
			// then, set a timer to activate the neighbors
			_neighborsTask = ThreadPoolManager.getInstance().scheduleGeneral(new NeighborsTask(true), 1000 * Config.GRID_NEIGHBOR_TURNON_TIME);
		}
	}
	
	/**
	 * starts a timer to set neighbors (including self) as inactive this timer is to avoid turning off neighbors in the case when a person just moved out of a region that he may very soon return to. There is no reason to turn self & neighbors off in that case.
	 */
	private void startDeactivation()
	{
		// if the timer to activate neighbors is running, cancel it.
		synchronized (this)
		{
			if (_neighborsTask != null)
			{
				_neighborsTask.cancel(true);
				_neighborsTask = null;
			}
			
			// start a timer to "suggest" a deactivate to self and neighbors.
			// suggest means: first check if a neighbor has L2PcInstances in it. If not, deactivate.
			_neighborsTask = ThreadPoolManager.getInstance().scheduleGeneral(new NeighborsTask(false), 1000 * Config.GRID_NEIGHBOR_TURNOFF_TIME);
		}
	}
	
	/**
	 * Add the L2Object in the L2ObjectHashSet(L2Object) _visibleObjects containing L2Object visible in this L2L2WorldRegion <BR>
	 * If L2Object is a L2PcInstance, Add the L2PcInstance in the L2ObjectHashSet(L2PcInstance) _allPlayable containing L2PcInstance of all player in game in this L2L2WorldRegion <BR>
	 * Assert : object.getCurrentL2WorldRegion() == this
	 * @param object
	 */
	public void addVisibleObject(L2Object object)
	{
		if (object == null)
			return;
		
		_visibleObjects.put(object.getObjectId(), object);

		if (object instanceof L2Playable)
		{
			// if this is the first player to enter the region, activate self & neighbors
			if (!isActive() && (!Config.GRIDS_ALWAYS_ON))
			{
				startActivation();
			}
		}
	}
	
	/**
	 * Remove the L2Object from the L2ObjectHashSet(L2Object) _visibleObjects in this L2L2WorldRegion. If L2Object is a L2PcInstance, remove it from the L2ObjectHashSet(L2PcInstance) _allPlayable of this L2L2WorldRegion <BR>
	 * Assert : object.getCurrentL2WorldRegion() == this || object.getCurrentL2WorldRegion() == null
	 * @param object
	 */
	public void removeVisibleObject(L2Object object)
	{
		if (object == null)
			return;
		
		_visibleObjects.remove(object.getObjectId());
		
		if (object instanceof L2Playable)
		{
			if (areNeighborsEmpty() && !Config.GRIDS_ALWAYS_ON)
			{
				startDeactivation();
			}
		}
	}
	
	public Map<Integer, L2Object> getVisibleObjects()
	{
		return _visibleObjects != null ? _visibleObjects : Collections.emptyMap();
	}
	
	public String getName()
	{
		return "(" + _regionX + ", " + _regionY + ", " + _regionZ + ")";
	}
	
	/**
	 * Deleted all spawns in the world.
	 */
	public void deleteVisibleNpcSpawns()
	{
		if (_visibleObjects == null)
		{
			return;
		}
		Collection<L2Object> vL2Npc = _visibleObjects.values();
		for (L2Object obj : vL2Npc)
		{
			if (obj instanceof L2Npc)
			{
				L2Npc target = (L2Npc) obj;
				target.deleteMe();
				L2Spawn spawn = target.getSpawn();
				if (spawn != null)
				{
					spawn.stopRespawn();
					SpawnTable.getInstance().deleteSpawn(spawn, false);
				}
			}
		}
	}
	
	public boolean forEachSurroundingRegion(Predicate<L2WorldRegion> p)
	{
		for (int x = _regionX - 1; x <= (_regionX + 1); x++)
		{
			for (int y = _regionY - 1; y <= (_regionY + 1); y++)
			{
				for (int z = _regionZ - 1; z <= (_regionZ + 1); z++)
				{
					if (L2World.validRegion(x, y, z))
					{
						final L2WorldRegion L2WorldRegion = L2World.getInstance().getAllWorldRegions()[x][y][z];
						if (!p.test(L2WorldRegion))
						{
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	public int getRegionX()
	{
		return _regionX;
	}
	
	public int getRegionY()
	{
		return _regionY;
	}
	
	public int getRegionZ()
	{
		return _regionZ;
	}
	public void removeZone(L2ZoneType zone)
	{
		ZoneManager.getInstance().getRegion(getRegionX(),getRegionY()).removeZone(zone);;
	}
	
	public void addZone(L2ZoneType zone)
	{
		ZoneManager.getInstance().getRegion(getRegionX(),getRegionY()).addZone(zone);;
	}

	public boolean isSurroundingRegion(L2WorldRegion region)
	{
		return (region != null) && (getRegionX() >= (region.getRegionX() - 1)) && (getRegionX() <= (region.getRegionX() + 1)) && (getRegionY() >= (region.getRegionY() - 1)) && (getRegionY() <= (region.getRegionY() + 1)) && (getRegionZ() >= (region.getRegionZ() - 1)) && (getRegionZ() <= (region.getRegionZ() + 1));
	}
	
	public List<L2ZoneType> getZones()
	{
		return ZoneManager.getInstance().getRegion(getRegionX(),getRegionY()).getZones();
	}
}