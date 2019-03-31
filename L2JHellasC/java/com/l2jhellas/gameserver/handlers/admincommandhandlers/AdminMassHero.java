package com.l2jhellas.gameserver.handlers.admincommandhandlers;

import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public class AdminMassHero implements IAdminCommandHandler
{
	private static String[] ADMIN_COMMANDS =
	{
		"admin_masshero"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (activeChar == null)
			return false;
		
		if (command.startsWith(ADMIN_COMMANDS[0])) // admin_masshero
		{
			for (L2PcInstance player : L2World.getInstance().getAllPlayers().values())
			{

				if (!player.isHero() || !player.isInOlympiadMode())
				{
					player.setHero(true);
					player.sendMessage("Admin is rewarding all online players with Hero Status.");
					player.broadcastSocialActionInRadius(16);
					player.broadcastUserInfo();
				}
				
			}
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}