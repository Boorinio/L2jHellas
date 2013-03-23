package com.l2jhellas.shield.antibot;

import com.l2jhellas.ExternalConfig;
import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public class AntiAfk
{
	public static void getInstance()
	{
		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			@Override
			public void run()
			{
				antiafksystem();
			}
		}, 60 * 1000 * 10);
	}
	
	static void antiafksystem()
	{
		for (L2PcInstance player : L2World.getInstance().getAllPlayers())
		{
			player.botx = player.getX();
			player.boty = player.getY();
			player.botz = player.getZ();
		}
		waitSecs(60 * ExternalConfig.MINUTES_AFK_PLAYERS);
		for (L2PcInstance player : L2World.getInstance().getAllPlayers())
		{
			if (player.botx == player.getX() && player.boty == player.getY() && player.botz == player.getZ())
			{
				player.sendMessage("BB bot or afk player!");
				player.closeNetConnection();
			}
		}
		waitSecs(60 * ExternalConfig.MINUTES_AFK_PLAYERS);
		antiafksystem();
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