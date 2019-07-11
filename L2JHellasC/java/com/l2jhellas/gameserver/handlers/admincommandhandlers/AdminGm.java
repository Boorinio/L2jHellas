package com.l2jhellas.gameserver.handlers.admincommandhandlers;

import java.util.logging.Logger;

import com.l2jhellas.gameserver.datatables.xml.AdminData;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public class AdminGm implements IAdminCommandHandler
{
	protected static final Logger _log = Logger.getLogger(AdminGm.class.getName());
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_gm"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.equals("admin_gm"))
		{
			handleGm(activeChar);
		}
		
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	private static void handleGm(L2PcInstance activeChar)
	{
		if (activeChar.isGM())
		{
			AdminData.getInstance().deleteGm(activeChar);
			activeChar.tempAc = activeChar.getAccessLevel().getLevel();
			activeChar.setAccessLevel(0);
			
			activeChar.sendMessage("You no longer have GM status.");
			_log.config(AdminGm.class.getName() + ": GM: " + activeChar.getName() + "(" + activeChar.getObjectId() + ") turned his GM status off");
		}
		else
		{
			AdminData.getInstance().addGm(activeChar, false);
			activeChar.setAccessLevel(activeChar.tempAc);
			
			activeChar.sendMessage("You now have GM status.");
			_log.config(AdminGm.class.getName() + ": GM: " + activeChar.getName() + "(" + activeChar.getObjectId() + ") turned his GM status on");
		}
	}
}