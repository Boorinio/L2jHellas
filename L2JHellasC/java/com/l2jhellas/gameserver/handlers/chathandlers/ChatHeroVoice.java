package com.l2jhellas.gameserver.handlers.chathandlers;

import com.l2jhellas.gameserver.handler.IChatHandler;
import com.l2jhellas.gameserver.model.BlockList;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.CreatureSay;

public class ChatHeroVoice implements IChatHandler
{
	private static final int[] COMMAND_IDS =
	{
		17
	};
	
	@Override
	public void handleChat(int type, L2PcInstance activeChar, String target, String text)
	{
		if (activeChar.isHero() || activeChar.isGM())
		{
			if (!activeChar.getAntiFlood().getHeroVoice().tryPerformAction("hero voice") && !activeChar.isGM())
			{
				activeChar.sendMessage("Action failed. Heroes are only able to speak in the global channel once every 10 seconds.");
				return;
			}
			
			CreatureSay cs = new CreatureSay(activeChar.getObjectId(), type, activeChar.getName(), text);
			for (L2PcInstance player : L2World.getInstance().getAllPlayers().values())
				if (!BlockList.isBlocked(player, activeChar))
					player.sendPacket(cs);
		}
	}
	
	@Override
	public int[] getChatTypeList()
	{
		return COMMAND_IDS;
	}
}