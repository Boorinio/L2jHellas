package com.l2jhellas.gameserver.model.entity.engines;

import java.util.ArrayList;
import java.util.List;

import javolution.text.TextBuilder;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.Announcements;
import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * @author Boorinio
 */
public class ZodiacMain
{
	public static List<String> Ips = new ArrayList<String>();
	public static boolean ZodiacRegisterActive;
	public static int i, max;
	public static int[] count =
	{
	0, 0, 0, 0, 0
	};
	public static boolean voting;
	
	public static void ZodiacIn()
	{
		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			@Override
			public void run()
			{
				startVoting();
			}
		}, 60 * 1000 * Config.INITIAL_START);
	}
	
	public static void startVoting()
	{
		voting = true;
		for (L2PcInstance players : L2World.getInstance().getAllPlayers())
		{
			showHtmlWindow(players);
		}
		switch (Config.ZODIAC_VOTE_MINUTES)
		{
			case 10:
				Announcements.getInstance().announceToAll("You have 10 minutes to vote for the event you like!");
				waitSecs(300);
				Announcements.getInstance().announceToAll("You have 5 minutes to vote for the event you like!");
				waitSecs(120);
				Announcements.getInstance().announceToAll("You have 3 minutes to vote for the event you like!");
				waitSecs(120);
				Announcements.getInstance().announceToAll("You have 1 minute to vote for the event you like!");
				waitSecs(60);
				voting = false;
				endit();
			break;
			case 9:
				Announcements.getInstance().announceToAll("You have 9 minutes to vote for the event you like!");
				waitSecs(240);
				Announcements.getInstance().announceToAll("You have 5 minutes to vote for the event you like!");
				waitSecs(120);
				Announcements.getInstance().announceToAll("You have 3 minutes to vote for the event you like!");
				waitSecs(120);
				Announcements.getInstance().announceToAll("You have 1 minute to vote for the event you like!");
				waitSecs(60);
				voting = false;
				endit();
			break;
			case 8:
				Announcements.getInstance().announceToAll("You have 8 minutes to vote for the event you like!");
				waitSecs(180);
				Announcements.getInstance().announceToAll("You have 5 minutes to vote for the event you like!");
				waitSecs(120);
				Announcements.getInstance().announceToAll("You have 3 minutes to vote for the event you like!");
				waitSecs(120);
				Announcements.getInstance().announceToAll("You have 1 minute to vote for the event you like!");
				waitSecs(60);
				voting = false;
				endit();
			break;
			case 7:
				Announcements.getInstance().announceToAll("You have 7 minutes to vote for the event you like!");
				waitSecs(120);
				Announcements.getInstance().announceToAll("You have 5 minutes to vote for the event you like!");
				waitSecs(120);
				Announcements.getInstance().announceToAll("You have 3 minutes to vote for the event you like!");
				waitSecs(120);
				Announcements.getInstance().announceToAll("You have 1 minute to vote for the event you like!");
				waitSecs(60);
				voting = false;
				endit();
			break;
			case 6:
				Announcements.getInstance().announceToAll("You have 6 minutes to vote for the event you like!");
				waitSecs(180);
				Announcements.getInstance().announceToAll("You have 3 minutes to vote for the event you like!");
				waitSecs(120);
				Announcements.getInstance().announceToAll("You have 1 minute to vote for the event you like!");
				waitSecs(60);
				voting = false;
				endit();
			break;
			case 5:
				Announcements.getInstance().announceToAll("You have 5 minutes to vote for the event you like!");
				waitSecs(120);
				Announcements.getInstance().announceToAll("You have 3 minutes to vote for the event you like!");
				waitSecs(120);
				Announcements.getInstance().announceToAll("You have 1 minute to vote for the event you like!");
				waitSecs(60);
				voting = false;
				endit();
			break;
			case 4:
				waitSecs(60);
				Announcements.getInstance().announceToAll("You have 3 minutes to vote for the event you like!");
				waitSecs(120);
				Announcements.getInstance().announceToAll("You have 1 minute to vote for the event you like!");
				waitSecs(60);
				voting = false;
				endit();
			break;
			case 3:
				Announcements.getInstance().announceToAll("You have 3 minutes to vote for the event you like!");
				waitSecs(120);
				Announcements.getInstance().announceToAll("You have 1 minute to vote for the event you like!");
				waitSecs(60);
				voting = false;
				endit();
			break;
			case 2:
				Announcements.getInstance().announceToAll("You have 2 minutes to vote for the event you like!");
				waitSecs(60);
				Announcements.getInstance().announceToAll("You have 1 minute to vote for the event you like!");
				waitSecs(60);
				voting = false;
				endit();
			break;
			case 1:
				Announcements.getInstance().announceToAll("You have 1 minute to vote for the event you like!");
				waitSecs(60);
				voting = false;
				endit();
			break;
		
		}
	}
	
	public static void showHtmlWindow(L2PcInstance activeChar)
	{
		NpcHtmlMessage nhm = new NpcHtmlMessage(5);
		TextBuilder tb = new TextBuilder("");
		tb.append("<html><head><title>Zodiac Event Voting</title></head><body>");
		tb.append("<center>");
		tb.append("<td valign=\"top\"><font color=\"FF6600\">Vote for the event you want to play!</font>");
		tb.append("<br>");
		tb.append("<img src=\"SSQ_dungeon_T.SSQ_fire1_e013\" width=256 height=64>");
		tb.append("<br1>");
		tb.append("<img src=\"L2UI.SquareWhite\" width=194 height=1>");
		tb.append("<table bgcolor=333333 width=204>");
		tb.append("<tr>");
		tb.append("<td><center><button value=\"CaptureThem\" action=\"bypass -h CaptureThem\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"></center></td>");
		tb.append("</tr>");
		tb.append("	<tr>");
		tb.append("<td><center><button value=\"Peloponnesian\" action=\"bypass -h PeloponnesianWar\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"></center></td>");
		tb.append("</tr>");
		tb.append("<tr>");
		tb.append("<td><center><button value=\"CastleWars\" action=\"bypass -h CastleWars\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"></center></td>");
		tb.append("</tr>");
		tb.append("<tr>");
		tb.append("<td><center><button value=\"ProtectTheLdr\" action=\"bypass -h ProtectTheLdr\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"></center></td>");
		tb.append("</tr>");
		tb.append("<tr>");
		tb.append("<td><center><button value=\"TreasureChest\" action=\"bypass -h TreasureChest\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"></center></td>");
		tb.append("</tr>");
		tb.append("</table>");
		tb.append("<font color=\"FF6600\">By Boorinio!</font>");
		tb.append("</center>");
		tb.append("</body></html>");
		nhm.setHtml(tb.toString());
		activeChar.sendPacket(nhm);
	}
	
	public static void showFinalWindow(L2PcInstance player)
	{
		
		NpcHtmlMessage nhm = new NpcHtmlMessage(5);
		TextBuilder tb = new TextBuilder("");
		tb.append("<html><title>Zodiac Event Engine</title><body>Current Votes:<br>");
		tb.append("Current Votes:<br><br>");
		tb.append("Peloponnesian War: " + count[0] + "<br>");
		tb.append("Capture Them: " + count[1] + "<br>");
		tb.append("Castle Wars: " + count[2] + "<br>");
		tb.append("Treasure Chests: " + count[4] + "<br>");
		tb.append("Protect The Leader: " + count[3] + "<br><br>");
		tb.append("</body></html>");
		nhm.setHtml(tb.toString());
		player.sendPacket(nhm);
	}
	
	public static void endit()
	{
		max = -1;
		for (i = 0; i < 5; i++)
		{
			if (count[i] > max && !(count[i]==0))
				max = i;
		}
		if (max == 0)
		{
			Announcements.getInstance().announceToAll("Peloponnesian Event has the most votes!");
			Announcements.getInstance().announceToAll("In 10 seconds the registration will open!");
			waitSecs(10);
			PeloponnesianWar.startevent();
		}
		else if (max == 1)
		{
			Announcements.getInstance().announceToAll("CaptureThem Event has the most votes!");
			Announcements.getInstance().announceToAll("In 10 seconds the registration will open!");
			waitSecs(10);
			CaptureThem.openRegistration();
		}
		else if (max == 2)
		{
			Announcements.getInstance().announceToAll("CastleWars Event has the most votes!");
			Announcements.getInstance().announceToAll("In 10 seconds the registration will open!");
			waitSecs(10);
			CastleWars.openRegi();
		}
		else if (max == 3)
		{
			Announcements.getInstance().announceToAll("ProtectTheLeader Event has the most votes!");
			Announcements.getInstance().announceToAll("In 10 seconds the registration will open!");
			waitSecs(10);
			ProtectTheLdr.startevent();
		}
		else if (max == 4)
		{
			Announcements.getInstance().announceToAll("Treasure Chest Event has the most votes!");
			Announcements.getInstance().announceToAll("In 10 seconds the registration will open!");
			waitSecs(10);
			TreasureChest.registration();
		}
		else if(max == -1)
		{
			Announcements.getInstance().announceToAll("No votes event canceled!");
		}
		max = -1;
		for (i = 0; i < 5; i++)
		{
			count[i] = 0;
		}
		waitSecs(Config.BETWEEN_EVENTS * 60);
		startVoting();
	}
	
	public static boolean isEligible(L2PcInstance player, String ip)
	{
		
		if (player.isinZodiac)
		{
			player.sendMessage("You are Already in Zodiac");
			return false;
		}
		if (!ZodiacRegisterActive)
		{
			player.sendMessage("Zodiac's Registrations are offline!");
			return false;
		}
		if (player.getLevel() < 76)
		{
			player.sendMessage("You are lower than 76 lvl");
			return false;
		}
		if (player.isInJail())
		{
			player.sendMessage("You are in jail...daah");
			return false;
		}
		if (player.isInOlympiadMode())
		{
			player.sendMessage("You are in olympiad mode");
			return false;
		}
		if (hasbots(ip) && Config.SAME_IP_ZODIAC)
		{
			player.sendMessage("You have already joinned with that ip");
			return false;
		}
		return true;
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
	
	public static boolean hasbots(String ip)
	{
		if (Ips.contains(ip))
		{
			return true;
		}
		else
			return false;
	}
	
}