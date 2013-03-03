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

/**
 * @author Boorinio
 */
public class PeloponnesianWar
{
	private static boolean alaksokolies = true;
	public static boolean PeloRunning;
	private static int i, countA, countS;
	private static List<L2PcInstance> _participants = new FastList<L2PcInstance>();
	private static List<L2PcInstance> _athenians = new FastList<L2PcInstance>();
	private static List<L2PcInstance> _spartans = new FastList<L2PcInstance>();
	private static FastSet<L2NpcInstance> _protectors = new FastSet<L2NpcInstance>();
	private static int athenianx = 72923;
	private static int atheniany = 142764;
	private static int athenianz = -3773;
	private static int spartanx = -87891;
	private static int spartany = 142198;
	private static int spartanz = -3646;
	private static int protectors = 30627;
	private static int[] protectorsx =
	{
	72931, 72995, 73011, 73070, -87921, -87998, -87908, -87836,
	};
	private static int[] protectorsy =
	{
	143270, 143250, 142272, 142282, 142707, 142679, 141758, 141777,
	};
	private static int[] protectorsz =
	{
	-3773, -3773, -3773, -3773, -3646, -3646, -3646, -3646,
	};
	
	public static void startevent()
	
	{
		PeloRunning = true;
		ZodiacMain.ZodiacRegisterActive = true;
		Announcements.getInstance().announceToAll("Peloponnesian War Event has Started!");
		Announcements.getInstance().announceToAll("Type .join to enter or .leave to leave!");
		Announcements.getInstance().announceToAll("You have 10 minutes to register!");
		waitSecs(300);
		Announcements.getInstance().announceToAll("You have 5 minutes to register!");
		waitSecs(180);
		Announcements.getInstance().announceToAll("You have 2 minutes to register!");
		waitSecs(60);
		Announcements.getInstance().announceToAll("You have 1 minute to register!");
		waitSecs(60);
		Announcements.getInstance().announceToAll("Registrations closed!");
		ZodiacMain.ZodiacRegisterActive = false;
		for (L2PcInstance player : L2World.getInstance().getAllPlayers())
		{
			if (player.isinZodiac)
			{
				_participants.add(player);
				if (alaksokolies)
				{
					_spartans.add(player);
					player.setTitle("Spartan");
					player.getAppearance().setNameColor(0x0000FF);
					player.broadcastUserInfo();
					alaksokolies = false;
				}
				else
				{
					_athenians.add(player);
					player.setTitle("Athenian");
					player.getAppearance().setNameColor(0xFF0000);
					player.broadcastUserInfo();
					alaksokolies = true;
				}
				
			}
		}
		spawnProtectors();
		teleportplayers();
		Announcements.getInstance().announceToAll("Civil War round started!");
		Announcements.getInstance().announceToAll("You have 3 minutes the one who survive will face the opponent race!!");
		waitSecs(30);
		for (L2PcInstance finals : _athenians)
		{
			if (finals.isinZodiac)
				countA++;
		}
		for (L2PcInstance finals : _spartans)
		{
			if (finals.isinZodiac)
				countS++;
		}
		if (countA > 1)
		{
			Announcements.getInstance().announceToAll("Athenians cheated and tried to send more than two players Spartans won!");
			reward(false);
		}
		else if (countS > 1)
		{
			Announcements.getInstance().announceToAll("Spartans cheated and tried to send more than two players Athenians won!");
			reward(true);
		}
		else
		{
			finalround();
			Announcements.getInstance().announceToAll("Spartan and Athenian representative have 2 minutes to face each other");
			waitSecs(30);
			checkwhowon();
		}
		cleanthemess();
	}
	
	public static void cleanthemess()
	{
		for (L2PcInstance participant : _participants)
		{
			if (participant.isinZodiac)
			{
				participant.setTitle("");
				participant.getAppearance().setNameColor(0xFFFFFF);
				participant.broadcastUserInfo();
				participant.teleToLocation(82698, 148638, -3473);
			}
			participant.isinZodiac = false;
		}
		for (L2NpcInstance protectors : _protectors)
		{
			protectors.deleteMe();
		}
		PeloRunning = false;
		_protectors.clear();
		_participants.clear();
		_athenians.clear();
		_spartans.clear();
	}
	
	private static void reward(boolean Athenians)
	{
		if (Athenians)
		{
			for (L2PcInstance athenians : _athenians)
			{
				
				athenians.addItem("Reward", Config.ZODIAC_REWARD, Config.ZODIAC_REWARD_COUN, athenians, true);
				
			}
		}
		else
		{
			for (L2PcInstance spartans : _spartans)
			{
				
				spartans.addItem("Reward", Config.ZODIAC_REWARD, Config.ZODIAC_REWARD_COUN, spartans, true);
				
			}
		}
	}
	
	public static void checkwhowon()
	{
		for (L2PcInstance winner : _participants)
		{
			if (winner.isinZodiac && _athenians.contains(winner))
			{
				Announcements.getInstance().announceToAll("Athenians won!");
				reward(true);
			}
			else if (winner.isinZodiac && _spartans.contains(winner))
			{
				Announcements.getInstance().announceToAll("Spartans won!");
				reward(false);
			}
		}
	}
	
	public static void finalround()
	{
		for (L2PcInstance athenian : _athenians)
		{
			if (athenian.isinZodiac)
			{
				athenian.sendMessage("You are the best athenian you will face the best spartan");
				athenian.setTitle("FinalAthenian");
				athenian.setCurrentHp(athenian.getMaxHp());
				athenian.setCurrentMp(athenian.getMaxMp());
				athenian.setCurrentCp(athenian.getMaxCp());
				athenian.addItem("Reward", Config.ZODIAC_REWARD, Config.ZODIAC_REWARD_COUN, athenian, true);
				athenian.broadcastUserInfo();
				athenian.teleToLocation(-21754, -20955, -3026);
			}
		}
		for (L2PcInstance spartan : _spartans)
		{
			if (spartan.isinZodiac)
			{
				spartan.sendMessage("You are the best spartan you will face the best athenian");
				spartan.setTitle("FinalSpartan");
				spartan.setCurrentHp(spartan.getMaxHp());
				spartan.setCurrentMp(spartan.getMaxMp());
				spartan.setCurrentCp(spartan.getMaxCp());
				spartan.addItem("Reward", Config.ZODIAC_REWARD, Config.ZODIAC_REWARD_COUN, spartan, true);
				spartan.broadcastUserInfo();
				spartan.teleToLocation(-19821, -20988, -3026);
			}
		}
	}
	
	public static void teleportplayers()
	{
		for (L2PcInstance player : _participants)
		{
			if (_athenians.contains(player))
			{
				player.teleToLocation(athenianx, atheniany, athenianz);
			}
			else
			{
				player.teleToLocation(spartanx, spartany, spartanz);
			}
		}
	}
	
	public static void spawnProtectors()
	{
		L2NpcInstance protector = null;
		for (i = 0; i < 8; i++)
		{
			protector = addSpawn(protectors, protectorsx[i], protectorsy[i], protectorsz[i]);
			_protectors.add(protector);
		}
	}
	
	public static void RemoveAthenian(String name)
	{
		_athenians.remove(name);
		
	}
	
	public static void RemoveSpartan(String name)
	{
		_spartans.remove(name);
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
