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
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastMap;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.GameTimeController;
import com.l2jhellas.gameserver.model.L2Spawn;
import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2RaidBossInstance;

/**
 * @author godson
 */
public class DayNightSpawnManager
{
	private static Logger _log = Logger.getLogger(DayNightSpawnManager.class.getName());

	private static DayNightSpawnManager _instance;
	private static Map<L2Spawn, L2Npc> _dayCreatures;
	private static Map<L2Spawn, L2Npc> _nightCreatures;
	private static Map<L2Spawn, L2RaidBossInstance> _bosses;

	// private static int _currentState; // 0 = Day, 1 = Night

	public static DayNightSpawnManager getInstance()
	{
		if (_instance == null)
			_instance = new DayNightSpawnManager();
		return _instance;
	}

	public void reload()
	{
		cleanUp();
		_instance = new DayNightSpawnManager();
	}
	
	private DayNightSpawnManager()
	{
		_dayCreatures = new FastMap<L2Spawn, L2Npc>();
		_nightCreatures = new FastMap<L2Spawn, L2Npc>();
		_bosses = new FastMap<L2Spawn, L2RaidBossInstance>();

		_log.log(Level.INFO, getClass().getSimpleName() + ": Day/Night handler initialised.");
	}

	public void addDayCreature(L2Spawn spawnDat)
	{
		if (_dayCreatures.containsKey(spawnDat))
		{
			_log.log(Level.WARNING, getClass().getName() + ": Spawn already added into day map.");
			return;
		}
		else
			_dayCreatures.put(spawnDat, null);
	}

	public void addNightCreature(L2Spawn spawnDat)
	{
		if (_nightCreatures.containsKey(spawnDat))
		{
			_log.log(Level.WARNING, getClass().getName() + ": Spawn already added into night map.");
			return;
		}
		else
			_nightCreatures.put(spawnDat, null);
	}

	/**
	 * Spawn Day Creatures, and Unspawn Night Creatures
	 */
	public void spawnDayCreatures()
	{
		spawnCreatures(_nightCreatures, _dayCreatures, "night", "day");
	}

	/**
	 * Spawn Night Creatures, and Unspawn Day Creatures
	 */
	public void spawnNightCreatures()
	{
		spawnCreatures(_dayCreatures, _nightCreatures, "day", "night");
	}

	/**
	 * Manage Spawn/Respawn
	 * Arg 1 : Map with L2NpcInstance must be unspawned
	 * Arg 2 : Map with L2NpcInstance must be spawned
	 * Arg 3 : String for log info for unspawned L2NpcInstance
	 * Arg 4 : String for log info for spawned L2NpcInstance
	 */
	private void spawnCreatures(Map<L2Spawn, L2Npc> UnSpawnCreatures, Map<L2Spawn, L2Npc> SpawnCreatures, String UnspawnLogInfo, String SpawnLogInfo)
	{
		try
		{
			if (UnSpawnCreatures.size() != 0)
			{
				int i = 0;
				for (L2Npc dayCreature : UnSpawnCreatures.values())
				{
					if (dayCreature == null)
						continue;

					dayCreature.getSpawn().stopRespawn();
					dayCreature.deleteMe();
					i++;
				}
				_log.log(Level.INFO, getClass().getSimpleName() + ": Deleted " + i + " " + UnspawnLogInfo + " creatures.");
			}

			int i = 0;
			L2Npc creature = null;
			for (L2Spawn spawnDat : SpawnCreatures.keySet())
			{
				if (SpawnCreatures.get(spawnDat) == null)
				{
					creature = spawnDat.doSpawn();
					if (creature == null)
						continue;

					SpawnCreatures.remove(spawnDat);
					SpawnCreatures.put(spawnDat, creature);
					creature.setCurrentHp(creature.getMaxHp());
					creature.setCurrentMp(creature.getMaxMp());
					creature = SpawnCreatures.get(spawnDat);
					creature.getSpawn().startRespawn();
				}
				else
				{
					creature = SpawnCreatures.get(spawnDat);
					if (creature == null)
						continue;

					creature.getSpawn().startRespawn();
					creature.setCurrentHp(creature.getMaxHp());
					creature.setCurrentMp(creature.getMaxMp());
					creature.spawnMe();
				}

				i++;
			}

			_log.log(Level.INFO, getClass().getSimpleName() + ": Spawning " + i + " " + SpawnLogInfo + " creatures");
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Could not spawn day/night creatures." + e);
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	private void changeMode(int mode)
	{
		if (_nightCreatures.size() == 0 && _dayCreatures.size() == 0)
			return;

		switch (mode)
		{
			case 0:
				spawnDayCreatures();
				specialNightBoss(0);
			break;
			case 1:
				spawnNightCreatures();
				specialNightBoss(1);
			break;
			default:
				_log.log(Level.WARNING, getClass().getName() + ": Wrong mode sent");
			break;
		}
	}

	public void notifyChangeMode()
	{
		try
		{
			if (GameTimeController.getInstance().isNowNight())
				changeMode(1);
			else
				changeMode(0);
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Could not notify change mode." + e);
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	public void cleanUp()
	{
		_nightCreatures.clear();
		_dayCreatures.clear();
		_bosses.clear();
	}

	private void specialNightBoss(int mode)
	{
		try
		{
			for (L2Spawn spawn : _bosses.keySet())
			{
				L2RaidBossInstance boss = _bosses.get(spawn);

				if (boss == null && mode == 1)
				{
					boss = (L2RaidBossInstance) spawn.doSpawn();
					RaidBossSpawnManager.getInstance().notifySpawnNightBoss(boss);
					_bosses.remove(spawn);
					_bosses.put(spawn, boss);
					continue;
				}

				if (boss == null && mode == 0)
					continue;

				if (boss.getNpcId() == 25328 && boss.getRaidStatus().equals(RaidBossSpawnManager.StatusEnum.ALIVE))
					handleHellmans(boss, mode);
				return;
			}
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Could not spawn day/night special bosses." + e);
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	private void handleHellmans(L2RaidBossInstance boss, int mode)
	{
		switch (mode)
		{
			case 0:
				boss.deleteMe();
				_log.log(Level.INFO, getClass().getSimpleName() + ": Deleting Hellman Raidboss!");
			break;
			case 1:
				boss.spawnMe();
				_log.log(Level.INFO, getClass().getSimpleName() + ": Spawning Hellman Raidboss!");
			break;
		}
	}

	public L2RaidBossInstance handleBoss(L2Spawn spawnDat)
	{
		if (_bosses.containsKey(spawnDat))
			return _bosses.get(spawnDat);

		if (GameTimeController.getInstance().isNowNight())
		{
			L2RaidBossInstance raidboss = (L2RaidBossInstance) spawnDat.doSpawn();
			_bosses.put(spawnDat, raidboss);

			return raidboss;
		}
		else
			_bosses.put(spawnDat, null);

		return null;
	}
}