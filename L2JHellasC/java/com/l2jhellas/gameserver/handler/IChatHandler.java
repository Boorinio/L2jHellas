package com.l2jhellas.gameserver.handler;

import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public interface IChatHandler
{
	public void handleChat(int type, L2PcInstance activeChar, String target, String text);
	
	public int[] getChatTypeList();
}