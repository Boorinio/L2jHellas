package com.l2jhellas.gameserver.handler.admincommandhandlers;

import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public class AdminDeport implements IAdminCommandHandler
{
   private static String[] _adminCommands =
   {
                "admin_deport"
   };

   @Override
   public boolean useAdminCommand(String command, L2PcInstance activeChar)
   {

         if(activeChar.getTarget() instanceof L2PcInstance)
         {
                if(command.startsWith("admin_deport"))
                {
                       ((L2PcInstance) activeChar.getTarget()).teleToLocation(82698, 148638, -3473); // Location Giran
                }
         }
         return false;

   }

   @Override
   public String[] getAdminCommandList()
   {
         return _adminCommands;
   }
}