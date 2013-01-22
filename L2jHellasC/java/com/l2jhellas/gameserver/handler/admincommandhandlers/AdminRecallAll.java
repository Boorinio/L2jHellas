package com.l2jhellas.gameserver.handler.admincommandhandlers;

import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;



public class AdminRecallAll implements IAdminCommandHandler
{
 private static final String[] ADMIN_COMMANDS = { "admin_recallall" };

 private void teleportTo(L2PcInstance activeChar, int x, int y, int z)
 {

 activeChar.teleToLocation(x, y, z, false);

 }
 @Override
public boolean useAdminCommand(String command, L2PcInstance activeChar)
 {
 if (command.startsWith("admin_recallall"))
 {
 for(L2PcInstance players :L2World.getInstance().getAllPlayers())
 {
 teleportTo(players, activeChar.getX(), activeChar.getY(), activeChar.getZ());
 }
 }
 return false;
 }
 @Override
public String[] getAdminCommandList()
 {
 return ADMIN_COMMANDS;
 }
}