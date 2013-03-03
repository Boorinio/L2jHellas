package com.l2jhellas.shield.antibot;

import javolution.text.TextBuilder;

import com.l2jhellas.ExternalConfig;
import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jhellas.util.Rnd;

public class AntiBot
{
	static int[] epiloges =
	{
	Rnd.get(-100, 100), Rnd.get(100), Rnd.get(-100, 100), Rnd.get(100),
	};
	static int option = Rnd.get(0, 7);
	
	public static void getInstance()
	{
		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			@Override
			public void run()
			{
				startantibot();
			}
		}, 60 * 1000 * ExternalConfig.SECURITY_QUE_TIME);
	}
	
	public static void startantibot()
	{
		
		for (L2PcInstance player : L2World.getInstance().getAllPlayers())
		{
			showHtmlWindow(player);
			player.sendMessage("You have 2 minutes to vote");
		}
		waitSecs(120);
		for (L2PcInstance player : L2World.getInstance().getAllPlayers())
		{
			if (!player.PassedProt)
			{
				player.sendMessage("BB bot or afk player!");
				player.closeNetConnection();
			}
		}
		for (L2PcInstance player : L2World.getInstance().getAllPlayers())
		{
			player.PassedProt = false;
		}
		waitSecs(ExternalConfig.SECURITY_QUE_TIME);
		startantibot();
	}
	
	public static void showHtmlWindow(L2PcInstance activeChar)
	{
		NpcHtmlMessage nhm = new NpcHtmlMessage(5);
		TextBuilder tb = new TextBuilder("");
		tb.append("<html><head><title>Antiboting system</title></head><body>");
		tb.append("<center>");
		tb.append("If you don't answer correctly or you don't answer at all<br>");
		tb.append("You will be kicked from the game within 2 minutes<br>");
		if (option == 1)
			tb.append("What year do we have?<br>");
		if (option == 2)
			tb.append("10+10=?<br>");
		if (option == 3)
			tb.append("7+14=?<br>");
		if (option == 4)
			tb.append("2+2=?<br>");
		if (option == 5)
			tb.append("Are you a bot?<br>");
		if (option == 6)
			tb.append("3+72=?<br>");
		if (option == 7)
			tb.append("9+5=?<br>");
		tb.append("Answers:");
		if (option == 1)
		{
			tb.append("<button value=\"" + epiloges[1] + "\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"" + epiloges[2] + "\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"" + epiloges[3] + "\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"2013\" action=\"bypass -h SecondAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			
		}
		if (option == 2)
		{
			tb.append("<button value=\"" + epiloges[1] + "\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"" + epiloges[2] + "\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"20\" action=\"bypass -h SecondAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"" + epiloges[3] + "\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
		}
		if (option == 3)
		{
			tb.append("<button value=\"" + epiloges[1] + "\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"21\" action=\"bypass -h SecondAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"" + epiloges[2] + "\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"" + epiloges[3] + "\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
		}
		if (option == 4)
		{
			tb.append("<button value=\"4\" action=\"bypass -h SecondAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"" + epiloges[1] + "\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"" + epiloges[2] + "\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"" + epiloges[3] + "\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
		}
		if (option == 5)
		{
			tb.append("<button value=\"Yes\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"What?\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"Maybe\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"No\" action=\"bypass -h SecondAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
		}
		if (option == 6)
		{
			tb.append("<button value=\"" + epiloges[1] + "\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"" + epiloges[2] + "\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"75\" action=\"bypass -h SecondAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"" + epiloges[3] + "\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
		}
		if (option == 7)
		{
			tb.append("<button value=\"" + epiloges[1] + "\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"14\" action=\"bypass -h SecondAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"" + epiloges[2] + "\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
			tb.append("<button value=\"" + epiloges[3] + "\" action=\"bypass -h FirstAnswer\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"><br>");
		}
		tb.append("<font color=\"FF6600\">By Boorinio!</font>");
		tb.append("</center>");
		tb.append("</body></html>");
		nhm.setHtml(tb.toString());
		activeChar.sendPacket(nhm);
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
}