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
 *
 *
 */
public class Res implements IVoicedCommandHandler
{
    private static final String[] VOICED_COMMANDS = { "res" };

    public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
    {   
        if (command.equalsIgnoreCase("res"))
        {
              if (!activeChar.isAlikeDead())
              {
                 activeChar.sendMessage("You cannot be ressurected while alive.");
                 return false;
              }
           if(activeChar.isInOlympiadMode())
           {
              activeChar.sendMessage("You cannot use this feature during olympiad.");
             return false;
           }
           if (activeChar.inObserverMode())
           {
        	   activeChar.sendMessage("You Cannot Use This While You Are On Observer's Mode");
        	return false;   
           }
           if (activeChar.atEvent)
           {
        	   activeChar.sendMessage("You Cannot Use This On An Event.");
        	   return false;        	
           }
           if (activeChar.isFestivalParticipant())
           {
        	   activeChar.sendMessage("You Cannot Use This While You Are On A Festival");
           return false;
           }
           if(activeChar.getInventory().getItemByItemId(3470) == null)
           {
              activeChar.sendMessage("You need 1 or more Gold Bars to use the ressurection system.");
             return false;
           }
           
           
           
              activeChar.sendMessage("You have been ressurected!");
            activeChar.getInventory().destroyItemByItemId("RessSystem", 3470, 1, activeChar, activeChar.getTarget());
              activeChar.doRevive();
              activeChar.broadcastUserInfo();
              activeChar.sendMessage("One GoldBar has dissapeared! Thank you!");
        }
       return true;
    }
    public String[] getVoicedCommandList()
    {
        return VOICED_COMMANDS;
    }

}

       
