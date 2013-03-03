package com.l2jhellas.gameserver.model.entity.engines;

import java.util.List;

import javolution.util.FastList;
import javolution.util.FastSet;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.Announcements;
import com.l2jhellas.gameserver.datatables.sql.NpcTable;
import com.l2jhellas.gameserver.model.L2Spawn;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2NpcInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;
import com.l2jhellas.util.Rnd;

public class TreasureChest
{
	public static boolean TreasureRunning;
	private static List<L2PcInstance> _players = new FastList<L2PcInstance>();
	private static FastSet<L2NpcInstance> _Npcs = new FastSet<L2NpcInstance>();
	private static int x = 87377, y = 20459, z = -5270, i;
	
	public static void registration()
	{
		ZodiacMain.ZodiacRegisterActive = true;
		Announcements.getInstance().announceToAll("TreasureChest Event has Started!");
		Announcements.getInstance().announceToAll("Type .join to enter or .leave to leave!");
		Announcements.getInstance().announceToAll("You have 10 minutes to register!");
		waitSecs(300);
		Announcements.getInstance().announceToAll("You have 5 minutes to register!");
		waitSecs(180);
		Announcements.getInstance().announceToAll("You have 2 minutes to register!");
		waitSecs(60);
		Announcements.getInstance().announceToAll("You have 1 minute to register!");
		waitSecs(60);
		for (L2PcInstance players : L2World.getInstance().getAllPlayers())
		{
			
			if (players.isinZodiac)
			{
				
				_players.add(players);
				
			}
		}
		if (_players != null)
			startevent();
		else
			Announcements.getInstance().announceToAll("Event was cancelled cause no one registered!");
		
	}
	
	public static void startevent()
	{
		ZodiacMain.ZodiacRegisterActive = false;
		TreasureRunning = true;
		for (L2PcInstance players : _players)
		{
			players.teleToLocation(x, y, z, true);
			players.sendMessage("Kill as many chest as you can!");
		}
		L2NpcInstance npcs = null;
		for (i = 0; i < 40; i++)
		{
			npcs = addSpawn(18286, x + Rnd.get(-750, +750), y + Rnd.get(-750, +750), z);
			_Npcs.add(npcs);
		}
	}
	
	public static void LuckyOne(L2PcInstance killer)
	{
		if (Rnd.get(40) == 2)
		{
			Announcements.getInstance().announceToAll(killer + " killed the lucky chest!");
			killer.addItem("Reward", Config.ZODIAC_REWARD, Config.ZODIAC_REWARD_COUN, killer, true);
			cleanthemess();
		}
	}
	
	public static void cleanthemess()
	{
		for (L2PcInstance players : _players)
		{
			players.teleToLocation(83225, 148068, -3430, true);
		}
		for (L2NpcInstance npc : _Npcs)
		{
			npc.deleteMe();
		}
		TreasureRunning = false;
		_players.clear();
		_Npcs.clear();
	}
	
	public static void waitSecs(int i)
	{
		try
		{
			Thread.sleep(i * 1000);
		}
		catch (InterruptedException ie)
		{
			ie.printStackTrace();
		}
	}
	
	private static L2NpcInstance addSpawn(int npcId, int x, int y, int z)
	{
		L2NpcInstance result = null;
		try
		{
			L2NpcTemplate template = NpcTable.getInstance().getTemplate(npcId);
			if (template != null)
			{
				L2Spawn spawn = new L2Spawn(template);
				spawn.setInstanceId(0);
				spawn.setHeading(1);
				spawn.setLocx(x);
				spawn.setLocy(y);
				spawn.setLocz(z);
				spawn.stopRespawn();
				result = spawn.spawnOne();
				return result;
			}
		}
		catch (Exception e1)
		{
		}
		return null;
	}
}