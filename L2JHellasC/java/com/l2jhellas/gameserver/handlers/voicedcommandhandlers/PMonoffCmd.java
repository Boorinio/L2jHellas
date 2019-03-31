package com.l2jhellas.gameserver.handlers.voicedcommandhandlers;

import com.l2jhellas.gameserver.handler.IVoicedCommandHandler;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;

public class PMonoffCmd implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"pmoff",
		"pmon"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if (command.startsWith(VOICED_COMMANDS[0]) || command.startsWith(VOICED_COMMANDS[1]))
		{
			if (activeChar.getMessageRefusal())
			{
				activeChar.setMessageRefusal(false);
				activeChar.sendPacket(SystemMessageId.MESSAGE_ACCEPTANCE_MODE);
			}
			else
			{
				activeChar.setMessageRefusal(true);
				activeChar.sendPacket(SystemMessageId.MESSAGE_REFUSAL_MODE);
			}
		}
		return true;
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
}