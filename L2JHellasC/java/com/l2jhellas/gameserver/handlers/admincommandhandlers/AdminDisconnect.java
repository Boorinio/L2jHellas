package com.l2jhellas.gameserver.handlers.admincommandhandlers;

import java.util.logging.Logger;

import com.l2jhellas.gameserver.communitybbs.Manager.RegionBBSManager;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;

public class AdminDisconnect implements IAdminCommandHandler
{
	protected static final Logger _log = Logger.getLogger(AdminDisconnect.class.getName());
	
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_character_disconnect"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.equals("admin_character_disconnect"))
			disconnectCharacter(activeChar);
		
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	private static void disconnectCharacter(L2PcInstance activeChar)
	{	
		final L2PcInstance player = activeChar.getTarget().getActingPlayer();	
		if (player == null)
		{
			activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
			return;
		}
		
		if (player.getObjectId() == activeChar.getObjectId())
			activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
		else
		{						
			player.closeNetConnection(true);		
			RegionBBSManager.getInstance().changeCommunityBoard();
			activeChar.sendMessage("Character " + player.getName() + " disconnected from server.");
		}
	}
}