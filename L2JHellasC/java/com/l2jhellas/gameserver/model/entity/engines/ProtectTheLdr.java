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
package com.l2jhellas.gameserver.model.entity.engines;

import java.util.List;

import javolution.util.FastList;
import javolution.util.FastSet;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.Announcements;
import com.l2jhellas.gameserver.datatables.sql.NpcTable;
import com.l2jhellas.gameserver.model.L2Skill;
import com.l2jhellas.gameserver.model.L2Spawn;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2NpcInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.skills.SkillTable;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;

/**
 * @author Boorinio
 */
public class ProtectTheLdr
{
	public static List<L2PcInstance> _Team1 = new FastList<L2PcInstance>();
	public static List<L2PcInstance> _Team2 = new FastList<L2PcInstance>();
	public static int team1x = -19790;
	public static int team1y = -21023;
	public static int team1z = -3025;
	private static int leader1 = 36007;
	public static int team2x = -21780, team2y = -20976, team2z = -3027, leader2 = 36008;
	private static int leader2x = -21717, leader2y = -20859, leader2z = -3027;
	private static int leader1x = -19846, leader1y = -20859, leader1z = -3027;
	public static boolean ProtectisRunning, alaksokolies;
	private static FastSet<L2NpcInstance> _leaders = new FastSet<L2NpcInstance>();
	
	public static void startevent()
	{
		ZodiacMain.ZodiacRegisterActive = true;
		Announcements.getInstance().announceToAll("ProtectTheLeader Event has Started!");
		Announcements.getInstance().announceToAll("Type .join to enter or .leave to leave!");
		int minutes = Config.TIME_TO_REGISTER;
		Announcements.getInstance().announceToAll("You have " + minutes + " minutes to register!");
		waitSecs(minutes / 2 * 60);
		Announcements.getInstance().announceToAll("You have " + minutes / 2 + " minutes to register!");
		waitSecs(minutes / 2 * 60);
		ZodiacMain.ZodiacRegisterActive = false;
		ProtectisRunning = true;
		Announcements.getInstance().announceToAll("Registrations are now over!");
		shufflePlayers();
		teleportplayers();
		L2NpcInstance spawn1 = null;
		L2NpcInstance spawn2 = null;
		spawn1 = addSpawn(leader1, leader1x, leader1y, leader1z);
		spawn2 = addSpawn(leader2, leader2x, leader2y, leader2z);
		_leaders.add(spawn1);
		_leaders.add(spawn2);
		Announcements.getInstance().announceToAll("Go kill the enemy's Leader rb!");
	}
	
	public static void team1wins()
	{
		Announcements.getInstance().announceToAll("Team 1 won team's 2 leader is dead!");
		for (L2PcInstance member : _Team1)
		{
			
			member.sendMessage("Congratulations! The enemy leader is dead!");
			member.addItem("Reward", Config.ZODIAC_REWARD, Config.ZODIAC_REWARD_COUN, member, true);
		}
		cleanthemess();
	}
	
	public static void team2wins()
	{
		Announcements.getInstance().announceToAll("Team 2 won team's 1 leader is dead!");
		for (L2PcInstance member : _Team2)
		{
			
			member.sendMessage("Congratulations! The enemy leader is dead!");
			member.addItem("Reward", Config.ZODIAC_REWARD, Config.ZODIAC_REWARD_COUN, member, true);
		}
		cleanthemess();
	}
	
	public static void cleanthemess()
	{
		for (L2PcInstance member : _Team1)
		{
			member.getAppearance().setNameColor(0xFFFFFF);
			member.setTitle("");
			member.broadcastUserInfo();
			member.isinZodiac = false;
			member.teleToLocation(82743, 148219, -3470);
			
		}
		for (L2PcInstance member : _Team2)
		{
			member.getAppearance().setNameColor(0xFFFFFF);
			member.setTitle("");
			member.broadcastUserInfo();
			member.isinZodiac = false;
			member.teleToLocation(82743, 148219, -3470);
		}
		for (L2NpcInstance leader : _leaders)
		{
			leader.deleteMe();
			
		}
		ProtectisRunning = false;
	}
	
	public static void teleportplayers()
	{
		for (L2PcInstance member : _Team1)
		{
			member.teleToLocation(team1x, team1y, team1z);
		}
		for (L2PcInstance member : _Team2)
		{
			member.teleToLocation(team2x, team2y, team2z);
		}
	}
	
	public static void shufflePlayers()
	{
		for (L2PcInstance player : L2World.getAllPlayers())
		{
			if (player.isinZodiac)
			{
				if (alaksokolies)
				{
					_Team1.add(player);
					player.getAppearance().setNameColor(0xFF0000);
					player.setTitle("Team1");
					player.broadcastUserInfo();
					alaksokolies = false;
				}
				else
				{
					_Team2.add(player);
					player.getAppearance().setNameColor(0x0000FF);
					player.setTitle("Team2");
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
	
	public static void OnRevive(L2PcInstance player)
	{
		player.getStatus().setCurrentHp(player.getMaxHp());
		player.getStatus().setCurrentMp(player.getMaxMp());
		player.getStatus().setCurrentCp(player.getMaxCp());
		L2Skill skill;
		skill = SkillTable.getInstance().getInfo(1323, 1);
		skill.getEffects(player, player);
		if (ProtectTheLdr._Team1.contains(player))
		{
			player.teleToLocation(ProtectTheLdr.team1x, ProtectTheLdr.team1y, ProtectTheLdr.team1z);
		}
		if (ProtectTheLdr._Team2.contains(player))
		{
			player.teleToLocation(ProtectTheLdr.team2x, ProtectTheLdr.team2y, ProtectTheLdr.team2z);
		}
	}
}