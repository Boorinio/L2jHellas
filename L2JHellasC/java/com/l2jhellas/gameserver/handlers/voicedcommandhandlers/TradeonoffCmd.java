package com.l2jhellas.gameserver.handlers.voicedcommandhandlers;

import com.l2jhellas.gameserver.handler.IVoicedCommandHandler;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public class TradeonoffCmd implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"tradeoff",
		"tradeon"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if (command.startsWith(VOICED_COMMANDS[0]) || command.startsWith(VOICED_COMMANDS[1]))
		{
			if (activeChar.getTradeRefusal())
			{
				activeChar.setTradeRefusal(false);
				activeChar.sendMessage("Trade refusal is disabled.");
			}
			else
			{
				activeChar.setTradeRefusal(true);
				activeChar.sendMessage("Trade refusal is enabled.");
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