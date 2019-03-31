package com.l2jhellas.gameserver.handlers.chathandlers;

import com.l2jhellas.gameserver.handler.IChatHandler;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.CreatureSay;

public class ChatParty implements IChatHandler
{
	private static final int[] COMMAND_IDS =
	{
		3
	};
	
	@Override
	public void handleChat(int type, L2PcInstance activeChar, String target, String text)
	{
		if (activeChar.isInParty())
		{
			CreatureSay cs = new CreatureSay(activeChar.getObjectId(), type, activeChar.getName(), text);
			
			activeChar.getParty().broadcastCSToPartyMembers(cs, activeChar);
		}
	}
	
	@Override
	public int[] getChatTypeList()
	{
		return COMMAND_IDS;
	}
}