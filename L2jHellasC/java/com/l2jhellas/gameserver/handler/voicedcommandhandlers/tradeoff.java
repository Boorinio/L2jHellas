/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jhellas.gameserver.handler.voicedcommandhandlers;

import com.l2jhellas.gameserver.handler.IVoicedCommandHandler;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author Intrepid
 *
 */

public class tradeoff implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS = 
	{ 
		"tradeoff",
		"tradeon"
	};
	
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if ((command.startsWith("tradeoff")))
		{
			activeChar.setTradeRefusal(true);
			activeChar.sendMessage("Trade refusal enabled");
		}
		if ((command.startsWith("tradeon")))
		{
			activeChar.setTradeRefusal(false);
			activeChar.sendMessage("Trade refusal disabled");
		}
		return true;
	}
	
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
}
