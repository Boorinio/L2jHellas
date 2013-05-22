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
import com.l2jhellas.gameserver.model.entity.engines.QuizEvent;

/**
 * @author Pauler
 */
public class QuizCmd implements IVoicedCommandHandler
{

	public static final String[] VOICED_COMMANDS =
	{
		"quiz"
	};

	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if (command.equalsIgnoreCase(VOICED_COMMANDS[0])) // quiz
		{
			if (QuizEvent.isRunning() && !target.equalsIgnoreCase("") && target != null)
			{
				QuizEvent.checkAnswer(target, activeChar);
				activeChar.sendMessage("Your answer has been submitted.");
			}
			else
			{
				activeChar.sendMessage("Quiz event is not running right now.");
			}
		}
		return false;
	}

	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
}