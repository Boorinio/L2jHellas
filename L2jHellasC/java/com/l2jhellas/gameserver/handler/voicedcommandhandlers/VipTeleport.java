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

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.handler.IVoicedCommandHandler;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public class VipTeleport implements IVoicedCommandHandler
{
	private static final String[] _voicedCommands =
	{
		"areavip",
	};
	
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if (command.equalsIgnoreCase("areavip"))
		{
			if(activeChar.atEvent)
			{
				activeChar.sendMessage("Voce esta em um Evento.");
				return false;
			}
			else if(activeChar.isInDuel())
			{
				activeChar.sendMessage("Voce esta em Duel.");
				return false;
			}
			else if(activeChar.isInOlympiadMode())
			{
 				activeChar.sendMessage("Voce esta na Olympiad");
				return false;
			}
			else if(activeChar.isInCombat())
			{
				activeChar.sendMessage("Voce nao pode teleportar no Modo de Combate");
				return false;
			}
			else if(activeChar.getParty().isInDimensionalRift())
			{
				activeChar.sendMessage("Voce esta em um Dimensional Rift");
				return false;
			}
			else if (activeChar.isFestivalParticipant())
			{
				activeChar.sendMessage("Voce esta em um festival.");
				return false;
			}
			else if (activeChar.isInJail())
			{
				activeChar.sendMessage("Voce esta na Jail.");
				return false;
			}
			else if (activeChar.inObserverMode())
			{
				activeChar.sendMessage("Voce esta em Observer Mode.");
				return false;
			}
			else if (activeChar.isDead())
			{
				activeChar.sendMessage("Voce esta Morto! Nao e possivel teleportar.");
				return false;
			}
			else if (activeChar.isFakeDeath())
			{
				activeChar.sendMessage("Voce esta morto? Levante-se!");
				return false;
			}
			else if (activeChar._donator = false)
			{
				activeChar.sendMessage("E necessario ser um Doador para Teleportar.");
				return false;
			}

			activeChar.teleToLocation(Config.VIP_X, Config.VIP_Y, Config.VIP_Z);
			activeChar.sendMessage("Voce foi teleportado para a Area Vip!");
		}
		return true;
	}
	
	public String[] getVoicedCommandList()
	{
		return _voicedCommands;
	}
}