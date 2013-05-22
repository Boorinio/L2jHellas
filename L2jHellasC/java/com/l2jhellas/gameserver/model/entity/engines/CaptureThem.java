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

import java.io.File;
import java.util.List;

import javolution.util.FastList;
import javolution.util.FastSet;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.Announcements;
import com.l2jhellas.gameserver.datatables.csv.DoorTable;
import com.l2jhellas.gameserver.datatables.sql.NpcTable;
import com.l2jhellas.gameserver.model.L2Skill;
import com.l2jhellas.gameserver.model.L2Spawn;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2NpcInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jhellas.gameserver.skills.SkillTable;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;

/**
 * @author Boorinio
 */
public class CaptureThem
{
	public static boolean CaptureThemRunning;
	private static int i;
	private static List<L2PcInstance> _players = new FastList<L2PcInstance>();
	private static FastSet<L2NpcInstance> _flags = new FastSet<L2NpcInstance>();
	private static int flagId = 36006;
	private static int MostPoints = 0;
	public static L2PcInstance MostPointsPlayer;
	private static int[] flagsx =
	{
	149506, 149412, 150025, 148741, 149999, 150110, 150121, 149550, 149235,
	};
	private static int[] flagsy =
	{
	47471, 46144, 46670, 46750, 46221, 46464, 46872, 47105, 47871,
	};
	private static int flagsz = -3413;

	public static void openRegistration()
	{
		CaptureThemRunning = true;
		ZodiacMain.ZodiacRegisterActive = true;
		Announcements.getInstance().announceToAll("CaptureThem Event has Started!");
		Announcements.getInstance().announceToAll("Type .join to enter or .leave to leave!");
		Announcements.getInstance().announceToAll("You have 10 minutes to register!");
		waitSecs(300);
		Announcements.getInstance().announceToAll("You have 5 minutes to register!");
		waitSecs(180);
		Announcements.getInstance().announceToAll("You have 2 minutes to register!");
		waitSecs(60);
		Announcements.getInstance().announceToAll("You have 1 minute to register!");
		waitSecs(60);
		stopRegistration();
	}

	public static void stopRegistration()
	{
		Announcements.getInstance().announceToAll("CaptureThem Registration is Over!");
		for (L2PcInstance players : L2World.getInstance().getAllPlayers())
		{
			ZodiacMain.ZodiacRegisterActive = false;
			if (players.isinZodiac)
			{
				String Capture_Path = "data/html/zodiac/CaptureThem.htm";
				//TODO check this if work like that
				/* File mainText = */new File(Config.DATAPACK_ROOT, Capture_Path);
				NpcHtmlMessage html = new NpcHtmlMessage(1);
				html.setFile(Capture_Path);
				players.sendPacket(html);
				_players.add(players);

			}
		}
		waitSecs(20);
		if (_players != null)
			StartEvent();
		else
		{
			Announcements.getInstance().announceToAll("The event was canceled due to lack of players!");
			CaptureThemRunning = false;
		}
	}

	public static void StartEvent()
	{
		DoorTable.getInstance().getDoor(24190001).closeMe();
		DoorTable.getInstance().getDoor(24190002).closeMe();
		DoorTable.getInstance().getDoor(24190003).closeMe();
		DoorTable.getInstance().getDoor(24190004).closeMe();
		L2NpcInstance flags = null;
		for (i = 0; i < 9; i++)
		{
			flags = addSpawn(flagId, flagsx[i], flagsy[i], flagsz);
			_flags.add(flags);
		}
		for (L2PcInstance players : _players)
		{
			players.ZodiacPoints = 0;
			players.getAppearance().setNameColor(0xFF0000);
			players.setTitle("CaptureThem");
			L2Skill skill;
			skill = SkillTable.getInstance().getInfo(1323, 1);
			skill.getEffects(players, players);
			players.broadcastUserInfo();
			players.teleToLocation(149527, 46684, -3413);
			players.sendMessage("The Event has Started! And will finish in 10 minutes");

		}
		Announcements.getInstance().announceToAll("You have 10 minutes until the event is over!");
		waitSecs(300);
		Announcements.getInstance().announceToAll("You have 5 minutes until the event is over!");
		waitSecs(180);
		Announcements.getInstance().announceToAll("You have 2 minutes until the event is over!");
		waitSecs(60);
		Announcements.getInstance().announceToAll("You have 1 minute until the event is over!");
		waitSecs(60);
		StopClean();

	}

	public static void StopClean()
	{
		for (L2PcInstance players : _players)
		{
			if (players.ZodiacPoints > MostPoints)
			{
				MostPointsPlayer = players;
				MostPoints = players.ZodiacPoints;
			}
		}
		if (MostPointsPlayer != null)
		{
			MostPointsPlayer.addItem("Reward", Config.ZODIAC_REWARD, Config.ZODIAC_REWARD_COUN, MostPointsPlayer, true);
			Announcements.getInstance().announceToAll("Winner of the event " + MostPointsPlayer + " With " + MostPoints + " Points!");
		}
		for (L2PcInstance players : _players)
		{
			players.getAppearance().setNameColor(0xFFFFFF);
			players.setTitle("");
			players.broadcastUserInfo();
			players.teleToLocation(82698, 148638, -3473);
			players.broadcastUserInfo();
			players.isinZodiac = false;
			players.sendMessage("The Event is officially finished!");
		}
		for (L2NpcInstance flags : _flags)
		{
			flags.deleteMe();

		}
		DoorTable.getInstance().getDoor(24190001).openMe();
		DoorTable.getInstance().getDoor(24190002).openMe();
		DoorTable.getInstance().getDoor(24190003).openMe();
		DoorTable.getInstance().getDoor(24190004).openMe();
		_players.clear();
		_flags.clear();
		CaptureThemRunning = false;

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