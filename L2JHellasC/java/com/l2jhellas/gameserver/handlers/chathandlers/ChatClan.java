package com.l2jhellas.gameserver.handlers.chathandlers;

import com.l2jhellas.gameserver.handler.IChatHandler;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.CreatureSay;

public class ChatClan implements IChatHandler
{
	private static final int[] COMMAND_IDS =
	{
		4
	};
	
	@Override
	public void handleChat(int type, L2PcInstance activeChar, String target, String text)
	{
		if (activeChar.getClan() != null)
		{
			CreatureSay cs = new CreatureSay(activeChar.getObjectId(), type, activeChar.getName(), text);
			activeChar.getClan().broadcastCSToOnlineMembers(cs, activeChar);
		}
	}
	
	@Override
	public int[] getChatTypeList()
	{
		return COMMAND_IDS;
	}
}