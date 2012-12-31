/* This program is free software; you can redistribute it and/or modify
  * it under the terms of the GNU General Public License as published by
  * the Free Software Foundation; either version 2, or (at your option)
  * any later version.
  *
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  *
  * You should have received a copy of the GNU General Public License
  * along with this program; if not, write to the Free Software
  * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
  * 02111-1307, USA.
  *
  * http://www.gnu.org/copyleft/gpl.html
  */
 package com.l2jhellas.gameserver.handler.voicedcommandhandlers;
 
 import com.l2jhellas.gameserver.handler.IVoicedCommandHandler;
 import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
 import com.l2jhellas.gameserver.model.entity.ChaosEvent;
 
 /**
  *
  * @author  Anarchy
  */
 public class ChaosCmd implements IVoicedCommandHandler
 {
     private static final String[] VOICED_COMMANDS = { "joinchaos", "leavechaos" };
     
 	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
 	{
 		
 		ChaosEvent chaos = new ChaosEvent();
 		
 		if (command.startsWith("joinchaos"))
 		{
 			if (ChaosEvent._isChaosActive)
 			{
 				chaos.registerToChaos(activeChar);
 				return true;
 			}
 			else
 			{
 				activeChar.sendMessage("Chaos Event is not currently active.");
 				return false;
 			}
 		}
 		if (command.startsWith("leavechaos"))
 		{
 			if (ChaosEvent._isChaosActive)
 			{
 				chaos.removeFromChaos(activeChar);
 				return true;
 			}
 			else
 			{
 				activeChar.sendMessage("Chaos Event is not currently active.");
 				return false;
 			}
 		}
 		return true;
 	}
 	
 	public String[] getVoicedCommandList()
 	{
 		return VOICED_COMMANDS;
 	}	
 }