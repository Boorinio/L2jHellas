package com.l2jhellas.gameserver.instancemanager;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.controllers.GameTimeController;
import com.l2jhellas.gameserver.model.L2Spawn;
import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2RaidBossInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class DayNightSpawnManager
{
	private static Logger _log = Logger.getLogger(DayNightSpawnManager.class.getName());
	
	private final List<L2Spawn> _dayCreatures = new ArrayList<>();
	private final List<L2Spawn> _nightCreatures = new ArrayList<>();
	private final Map<L2Spawn, L2RaidBossInstance> _bosses = new ConcurrentHashMap<>();
	
	public static DayNightSpawnManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	protected DayNightSpawnManager()
	{
		
	}
	
	public void addDayCreature(L2Spawn spawnDat)
	{
		_dayCreatures.add(spawnDat);
	}
	
	public void addNightCreature(L2Spawn spawnDat)
	{
		_nightCreatures.add(spawnDat);
	}
	
	public void spawnDayCreatures()
	{
		spawnCreatures(_nightCreatures, _dayCreatures, "night", "day");
	}
	
	public void spawnNightCreatures()
	{
		spawnCreatures(_dayCreatures, _nightCreatures, "day", "night");
	}
	
	private static void spawnCreatures(List<L2Spawn> unSpawnCreatures, List<L2Spawn> spawnCreatures, String UnspawnLogInfo, String SpawnLogInfo)
	{
		try
		{
			if (!unSpawnCreatures.isEmpty())
			{
				int i = 0;
				for (L2Spawn spawn : unSpawnCreatures)
				{
					if (spawn == null)
						continue;
					
					spawn.stopRespawn();
					L2Npc last = spawn.getLastSpawn();
					if (last != null)
					{
						last.deleteMe();
						i++;
					}
				}
				_log.info(DayNightSpawnManager.class.getSimpleName() + ": Removed " + i + " " + UnspawnLogInfo + " creatures");
			}
			
			int i = 0;
			for (L2Spawn spawnDat : spawnCreatures)
			{
				if (spawnDat == null)
					continue;
				
				spawnDat.startRespawn();
				spawnDat.doSpawn();
				i++;
			}
			
			_log.info(DayNightSpawnManager.class.getSimpleName() + ": Spawned " + i + " " + SpawnLogInfo + " creatures");
		}
		catch (Exception e)
		{
			_log.info(DayNightSpawnManager.class.getSimpleName() + "Error while spawning creatures: " + e.getMessage());
		}
	}
	
	private void changeMode(int mode)
	{
		
		if (_nightCreatures.isEmpty() && _dayCreatures.isEmpty() && _bosses.isEmpty())
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
				_log.warning(DayNightSpawnManager.class.getName() + ": Wrong mode sent");
				break;
		}
	}
	
	public void notifyChangeMode()
	{
		try
		{
			if (GameTimeController.getInstance().isNight())
				changeMode(1);
			else
				changeMode(0);
		}
		catch (Exception e)
		{
			_log.warning(DayNightSpawnManager.class.getName() + ": Could not notify change mode.");
			if (Config.DEVELOPER)
				e.printStackTrace();
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
			for (Map.Entry<L2Spawn, L2RaidBossInstance> infoEntry : _bosses.entrySet())
			{
				L2RaidBossInstance boss = infoEntry.getValue();
				if (boss == null)
				{
					if (mode == 1)
					{
						final L2Spawn spawn = infoEntry.getKey();
						
						boss = (L2RaidBossInstance) spawn.doSpawn();
						RaidBossSpawnManager.getInstance().notifySpawnNightBoss(boss);
						
						_bosses.put(spawn, boss);
					}
					continue;
				}
				
				if (boss.getNpcId() == 25328 && boss.getRaidStatus().equals(RaidBossSpawnManager.StatusEnum.ALIVE))
					handleHellmans(boss, mode);
				
				return;
			}
		}
		catch (Exception e)
		{
			_log.info(DayNightSpawnManager.class.getSimpleName() + " Error while specialNoghtBoss(): " + e.getMessage());
		}
	}
	
	private static void handleHellmans(L2RaidBossInstance boss, int mode)
	{
		switch (mode)
		{
			case 0:
				boss.deleteMe();
				_log.info(DayNightSpawnManager.class.getSimpleName() + ": Deleting Hellman Raidboss!");
				break;
			case 1:
				boss.spawnMe();
				_log.info(DayNightSpawnManager.class.getSimpleName() + ": Spawning Hellman Raidboss!");
				break;
		}
	}
	
	public L2RaidBossInstance handleBoss(L2Spawn spawnDat)
	{
		if (_bosses.containsKey(spawnDat))
			return _bosses.get(spawnDat);
		
		if (GameTimeController.getInstance().isNight())
		{
			L2RaidBossInstance raidboss = (L2RaidBossInstance) spawnDat.doSpawn();
			_bosses.put(spawnDat, raidboss);
			
			return raidboss;
		}
		
		_bosses.put(spawnDat, null);
		
		return null;
	}
	
	private static class SingletonHolder
	{
		protected static final DayNightSpawnManager _instance = new DayNightSpawnManager();
	}
}