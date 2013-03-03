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
/**
 * @author Boorinio
 */
package com.l2jhellas.gameserver.model.entity.engines;

import java.util.List;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.Announcements;
import com.l2jhellas.gameserver.datatables.csv.DoorTable;
import com.l2jhellas.gameserver.datatables.sql.NpcTable;
import com.l2jhellas.gameserver.model.L2Spawn;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2NpcInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;

import javolution.util.FastList;
import javolution.util.FastSet;

public class CastleWars
{
	private static List<L2PcInstance> _defenders = new FastList<L2PcInstance>();
	private static List<L2PcInstance> _attackers = new FastList<L2PcInstance>();
	private static FastSet<L2NpcInstance> _flags = new FastSet<L2NpcInstance>();
	public static boolean isFinished;
	public static boolean CastleWarsRunning;
	private static boolean alaksokolies = false;
	private static int i;
	public static int flagskilled = 0;
	public static int defendersx = 77566;
	public static int defendersy = -152128;
	public static int defendersz = -545;
	public static int attackersx = 84303;
	public static int attackersy = -140727;
	public static int attackersz = -1568;
	public static int[] flagslocx =
	{
	77545, 76663, 78446,
	};
	private static int[] flagslocy =
	{
	-149937, -154522, -154524,
	};
	private static int[] flagslocz =
	{
	345, 128, 235,
	};
	
	public static void openRegi()
	{
		ZodiacMain.ZodiacRegisterActive=true;
		Announcements.getInstance().announceToAll("CastleWars Event has Started!");
		Announcements.getInstance().announceToAll("Type .join to enter or .leave to leave!");
		Announcements.getInstance().announceToAll("You have 10 minutes to register!");
		waitSecs(300);
		Announcements.getInstance().announceToAll("You have 5 minutes to register!");
		waitSecs(180);
		Announcements.getInstance().announceToAll("You have 2 minutes to register!");
		waitSecs(60);
		Announcements.getInstance().announceToAll("You have 1 minute to register!");
		waitSecs(60);
		CastleWarsRunning = true;
		ZodiacMain.ZodiacRegisterActive=false;
		isFinished = false;
		stopRegi();
		preparecastle();
		shufflePlayers();
		teleportThem();
		if (!isFinished)
		Announcements.getInstance().announceToAll("You have 10 minutes until the event is over!");
		waitSecs(300);
		if (!isFinished)
		Announcements.getInstance().announceToAll("You have 5 minutes until the event is over!");
		waitSecs(180);
		if (!isFinished)
		Announcements.getInstance().announceToAll("You have 2 minutes until the event is over!");
		waitSecs(60);
		if (!isFinished)
		Announcements.getInstance().announceToAll("You have 1 minute until the event is over!");
		waitSecs(60);
		if (!isFinished)
		{
			defendersWin();
			
		}
		
	}
	
	public static void preparecastle()
	{
		DoorTable.getInstance().getDoor(22130001).openMe();
		DoorTable.getInstance().getDoor(22130002).openMe();
		DoorTable.getInstance().getDoor(22130003).openMe();
		DoorTable.getInstance().getDoor(22130004).openMe();
		DoorTable.getInstance().getDoor(22130005).openMe();
		DoorTable.getInstance().getDoor(22130010).openMe();
		DoorTable.getInstance().getDoor(22130007).openMe();
		DoorTable.getInstance().getDoor(22130011).openMe();
		DoorTable.getInstance().getDoor(22130009).openMe();
		DoorTable.getInstance().getDoor(22130008).openMe();
		DoorTable.getInstance().getDoor(22130010).openMe();
		DoorTable.getInstance().getDoor(22130006).openMe();
		L2NpcInstance flags = null;
		for (i = 0; i < 3; i++)
		{
			flags = addSpawn(36006, flagslocx[i], flagslocy[i], flagslocz[i]);
			_flags.add(flags);
		}
	}
	
	public static void cleanevent()
	{
		for (L2PcInstance defender : _defenders)
		{
			defender.getAppearance().setNameColor(0xFFFFFF);
			defender.setTitle("");
			defender.broadcastUserInfo();
			defender.teleToLocation(82724, 148307, -3469);
			defender.isinZodiac = false;
		}
		for (L2PcInstance attacker : _attackers)
		{
			attacker.getAppearance().setNameColor(0xFFFFFF);
			attacker.setTitle("");
			attacker.broadcastUserInfo();
			attacker.teleToLocation(82724, 148307, -3469);
			attacker.isinZodiac = false;
		}
		for (L2NpcInstance flags : _flags)
		{
			flags.deleteMe();
			
		}
		flagskilled = 0;
		_flags.clear();
		_defenders.clear();
		_attackers.clear();
		DoorTable.getInstance().getDoor(22130001).closeMe();
		DoorTable.getInstance().getDoor(22130002).closeMe();
		DoorTable.getInstance().getDoor(22130003).closeMe();
		DoorTable.getInstance().getDoor(22130004).closeMe();
		DoorTable.getInstance().getDoor(22130005).closeMe();
		DoorTable.getInstance().getDoor(22130010).closeMe();
		DoorTable.getInstance().getDoor(22130007).closeMe();
		DoorTable.getInstance().getDoor(22130011).closeMe();
		DoorTable.getInstance().getDoor(22130009).closeMe();
		DoorTable.getInstance().getDoor(22130008).closeMe();
		DoorTable.getInstance().getDoor(22130010).closeMe();
		DoorTable.getInstance().getDoor(22130006).closeMe();
	}
	
	public static void defendersWin()
	{
		Announcements.getInstance().announceToAll("Defenders Won the event they protected the flags!");
		for (L2PcInstance defender : _defenders)
		{
			defender.sendMessage("Congratulations! Here is a reward for your effort!");
			defender.addItem("Reward", Config.ZODIAC_REWARD, Config.ZODIAC_REWARD_COUN, defender, true);
		}
		isFinished = true;
		cleanevent();
	}
	public static boolean isattacker(L2PcInstance player)
	{
		
		if(_attackers.contains(player.getName()))
		{
			return true;
		}
		else
			return false;
		
	}
	public static boolean isdefender(L2PcInstance player)
	{
		
		if(_defenders.contains(player.getName()))
		{
			return true;
		}
		else
			return false;
		
	}
	public static void attackersWin()
	{
		if (flagskilled == 3)
		{
			Announcements.getInstance().announceToAll("Attackers Won the event they killed all the flags!");
			for (L2PcInstance attacker : _attackers)
			{
				attacker.sendMessage("Congratulations! Here is a reward for your effort!");
				attacker.addItem("Reward", Config.ZODIAC_REWARD, Config.ZODIAC_REWARD_COUN,attacker, true);
			}
			isFinished = true;
			cleanevent();
		}
	}
	
	public static void teleportThem()
	{
		for (L2PcInstance defender : _defenders)
		{
			defender.teleToLocation(defendersx, defendersy, defendersz);
		}
		for (L2PcInstance attacker : _attackers)
		{
			attacker.teleToLocation(attackersx, attackersy, attackersz);
		}
	}
	
	public static void stopRegi()
	{
		Announcements.getInstance().announceToAll("Registrations are now over!");
	}
	
	public static void shufflePlayers()
	{
		for (L2PcInstance player : L2World.getInstance().getAllPlayers())
		{
			if (player.isinZodiac)
			{
				if (alaksokolies)
				{
					_defenders.add(player);
					player.getAppearance().setNameColor(0xFF0000);
					player.setTitle("Defender");
					player.broadcastUserInfo();
					alaksokolies = false;
				}
				else
				{
					_attackers.add(player);
					player.getAppearance().setNameColor(0x0000FF);
					player.setTitle("Attacker");
					player.broadcastUserInfo();
					alaksokolies = true;
				}
			}
		}
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