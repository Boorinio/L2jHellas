package com.l2jhellas.gameserver.handlers.chathandlers;

import com.l2jhellas.gameserver.handler.IChatHandler;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.CreatureSay;

public class ChatAlliance implements IChatHandler
{
	private static final int[] COMMAND_IDS =
	{
		9
	};
	
	@Override
	public void handleChat(int type, L2PcInstance activeChar, String target, String text)
	{
		if (activeChar.getClan() != null)
		{
			CreatureSay cs = new CreatureSay(activeChar.getObjectId(), type, activeChar.getName(), text);
			activeChar.getClan().broadcastToOnlineAllyMembers(cs);
		}
	}
	
	@Override
	public int[] getChatTypeList()
	{
		return COMMAND_IDS;
	}
}