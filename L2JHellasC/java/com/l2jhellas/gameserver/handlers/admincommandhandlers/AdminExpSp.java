package com.l2jhellas.gameserver.handlers.admincommandhandlers;

import java.util.StringTokenizer;
import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jhellas.util.IllegalPlayerAction;
import com.l2jhellas.util.Util;

public class AdminExpSp implements IAdminCommandHandler
{
	private static Logger _log = Logger.getLogger(AdminExpSp.class.getName());
	
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_add_exp_sp_to_character",
		"admin_add_exp_sp",
		"admin_remove_exp_sp"
	};

	private static L2PcInstance player;
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_add_exp_sp"))
		{
			try
			{
				String val = command.substring(16);
				if (!adminAddExpSp(activeChar, val))
					activeChar.sendMessage("Usage: //add_exp_sp exp sp");
			}
			catch (StringIndexOutOfBoundsException e)
			{ // Case of missing parameter
				activeChar.sendMessage("Usage: //add_exp_sp exp sp");
			}
		}
		else if (command.startsWith("admin_remove_exp_sp"))
		{
			try
			{
				String val = command.substring(19);
				if (!adminRemoveExpSP(activeChar, val))
					activeChar.sendMessage("Usage: //remove_exp_sp exp sp");
			}
			catch (StringIndexOutOfBoundsException e)
			{ // Case of missing parameter
				activeChar.sendMessage("Usage: //remove_exp_sp exp sp");
			}
		}
		addExpSp(activeChar);
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	private static void addExpSp(L2PcInstance activeChar)
	{
		L2Object target = activeChar.getTarget();
		player = null;
		if (target != null & target instanceof L2PcInstance)
			player = (L2PcInstance) target;
		else
		{
			player = activeChar;
		}
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setFile("data/html/admin/expsp.htm");
		adminReply.replace("%name%", player.getName());
		adminReply.replace("%level%", String.valueOf(player.getLevel()));
		adminReply.replace("%xp%", String.valueOf(player.getExp()));
		adminReply.replace("%sp%", String.valueOf(player.getSp()));
		adminReply.replace("%class%", player.getTemplate().className);
		activeChar.sendPacket(adminReply);
	}
	
	private static boolean adminAddExpSp(L2PcInstance activeChar, String ExpSp)
	{
		L2Object target = activeChar.getTarget();
		L2PcInstance player = null;
		if (target != null && target instanceof L2PcInstance)
		{
			player = (L2PcInstance) target;
		}
		else
		{
			player = activeChar;
		}
		StringTokenizer st = new StringTokenizer(ExpSp);
		if (st.countTokens() != 2)
		{
			return false;
		}
		String exp = st.nextToken();
		String sp = st.nextToken();
		long expval = 0;
		int spval = 0;
		try
		{
			expval = Long.parseLong(exp);
			spval = Integer.parseInt(sp);
		}
		catch (Exception e)
		{
			return false;
		}
		
		if (Config.GM_EDIT && (expval != 0 || spval != 0) && !player.isGM())
		{
			// Warn the player about his inmediate ban.
			player.sendMessage("A GM tried to edit you in " + expval + " exp points and " + spval + " sp points.You will both be banned.");
			Util.handleIllegalPlayerAction(player, "The player " + player.getName() + " has been edited. BANNED!!", IllegalPlayerAction.PUNISH_KICKBAN);
			
			// Warn the GM about his inmediate ban.
			activeChar.sendMessage("You tried to edit " + player.getName() + " by " + expval + " exp points and " + spval + ". You will both be banned.Q_Q");
			Util.handleIllegalPlayerAction(activeChar, "GM " + activeChar.getName() + " tried to edit " + player.getName() + ". BANNED!!", IllegalPlayerAction.PUNISH_KICKBAN);
			
			_log.info(AdminExpSp.class.getSimpleName() + ": GM " + activeChar.getName() + " tried to edit " + player.getName() + ". They both have been Banned. Q_Q");
		}
		else if (expval != 0 || spval != 0)
		{
			// Common character information
			player.sendMessage("Admin is adding you " + expval + " xp and " + spval + " sp.");
			player.addExpAndSp(expval, spval);
			// Admin information
			activeChar.sendMessage("Added " + expval + " xp and " + spval + " sp to " + player.getName() + ".");
			_log.warning(AdminExpSp.class.getSimpleName() + ": GM: " + activeChar.getName() + "(" + activeChar.getObjectId() + ") added " + expval + " xp and " + spval + " sp to " + player.getObjectId() + ".");
		}
		return true;
	}
	
	private static boolean adminRemoveExpSP(L2PcInstance activeChar, String ExpSp)
	{
		L2Object target = activeChar.getTarget();
		L2PcInstance player = null;
		if (target != null && target instanceof L2PcInstance)
		{
			player = (L2PcInstance) target;
		}
		else
		{
			player = activeChar;
		}
		StringTokenizer st = new StringTokenizer(ExpSp);
		if (st.countTokens() != 2)
			return false;
		String exp = st.nextToken();
		String sp = st.nextToken();
		long expval = 0;
		int spval = 0;
		try
		{
			expval = Long.parseLong(exp);
			spval = Integer.parseInt(sp);
		}
		catch (Exception e)
		{
			return false;
		}
		if (expval != 0 || spval != 0)
		{
			// Common character information
			player.sendMessage("Admin is removing you " + expval + " xp and " + spval + " sp.");
			player.removeExpAndSp(expval, spval);
			// Admin information
			activeChar.sendMessage("Removed " + expval + " xp and " + spval + " sp from " + player.getName() + ".");
			_log.warning(AdminExpSp.class.getSimpleName() + ": GM: " + activeChar.getName() + "(" + activeChar.getObjectId() + ") removed " + expval + " xp and " + spval + " sp from " + player.getObjectId() + ".");
		}
		return true;
	}
}