package com.l2jhellas.gameserver.handlers.admincommandhandlers;

import com.l2jhellas.gameserver.communitybbs.Manager.RegionBBSManager;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;

import java.util.logging.Logger;

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
		{
			disconnectCharacter(activeChar);
		}
		
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	private static void disconnectCharacter(L2PcInstance activeChar)
	{
		L2Object target = activeChar.getTarget();
		L2PcInstance player = null;
		if (target instanceof L2PcInstance)
		{
			player = (L2PcInstance) target;
		}
		else
		{
			return;
		}
		
		if (player.getObjectId() == activeChar.getObjectId())
		{
			SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_S2);
			sm.addString("You cannot logout your character.");
			activeChar.sendPacket(sm);
		}
		else
		{
			SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_S2);
			sm.addString("Character " + player.getName() + " disconnected from server.");
			activeChar.sendPacket(sm);
			
			_log.warning(AdminDisconnect.class.getSimpleName() + ": " + player.getName() + " kicked from server.");
			
			RegionBBSManager.getInstance().changeCommunityBoard();
			
			player.closeNetConnection(true);
		}
	}
}