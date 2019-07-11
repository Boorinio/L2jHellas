package com.l2jhellas.gameserver.handlers.admincommandhandlers;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.instancemanager.CastleManager;
import com.l2jhellas.gameserver.instancemanager.CastleManorManager;
import com.l2jhellas.gameserver.instancemanager.CastleManorManager.CropProcure;
import com.l2jhellas.gameserver.instancemanager.CastleManorManager.SeedProduction;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.entity.Castle;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;

public class AdminManor implements IAdminCommandHandler
{
	private static final String[] _adminCommands =
	{
		"admin_manor",
		"admin_manor_approve",
		"admin_manor_setnext",
		"admin_manor_reset",
		"admin_manor_setmaintenance",
		"admin_manor_save",
		"admin_manor_disable"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		StringTokenizer st = new StringTokenizer(command);
		command = st.nextToken();
		
		if (command.equals("admin_manor"))
		{
			showMainPage(activeChar);
		}
		else if (command.equals("admin_manor_setnext"))
		{
			CastleManorManager.getInstance().setNextPeriod();
			CastleManorManager.getInstance().setNewManorRefresh();
			CastleManorManager.getInstance().updateManorRefresh();
			activeChar.sendMessage("Manor System: set to next period");
			showMainPage(activeChar);
		}
		else if (command.equals("admin_manor_approve"))
		{
			CastleManorManager.getInstance().approveNextPeriod();
			CastleManorManager.getInstance().setNewPeriodApprove();
			CastleManorManager.getInstance().updatePeriodApprove();
			activeChar.sendMessage("Manor System: next period approved");
			showMainPage(activeChar);
		}
		else if (command.equals("admin_manor_reset"))
		{
			int castleId = 0;
			try
			{
				castleId = Integer.parseInt(st.nextToken());
			}
			catch (Exception e)
			{
			}
			
			if (castleId > 0)
			{
				Castle castle = CastleManager.getInstance().getCastleById(castleId);
				castle.setCropProcure(new ArrayList<CropProcure>(), CastleManorManager.PERIOD_CURRENT);
				castle.setCropProcure(new ArrayList<CropProcure>(), CastleManorManager.PERIOD_NEXT);
				castle.setSeedProduction(new ArrayList<SeedProduction>(), CastleManorManager.PERIOD_CURRENT);
				castle.setSeedProduction(new ArrayList<SeedProduction>(), CastleManorManager.PERIOD_NEXT);
				if (Config.ALT_MANOR_SAVE_ALL_ACTIONS)
				{
					castle.saveCropData();
					castle.saveSeedData();
				}
				activeChar.sendMessage("Manor data for " + castle.getName() + " was nulled");
			}
			else
			{
				for (Castle castle : CastleManager.getInstance().getCastles())
				{
					castle.setCropProcure(new ArrayList<CropProcure>(), CastleManorManager.PERIOD_CURRENT);
					castle.setCropProcure(new ArrayList<CropProcure>(), CastleManorManager.PERIOD_NEXT);
					castle.setSeedProduction(new ArrayList<SeedProduction>(), CastleManorManager.PERIOD_CURRENT);
					castle.setSeedProduction(new ArrayList<SeedProduction>(), CastleManorManager.PERIOD_NEXT);
					if (Config.ALT_MANOR_SAVE_ALL_ACTIONS)
					{
						castle.saveCropData();
						castle.saveSeedData();
					}
				}
				activeChar.sendMessage("Manor data was nulled");
			}
			showMainPage(activeChar);
		}
		else if (command.equals("admin_manor_setmaintenance"))
		{
			boolean mode = CastleManorManager.getInstance().isUnderMaintenance();
			CastleManorManager.getInstance().setUnderMaintenance(!mode);
			if (mode)
				activeChar.sendMessage("Manor System: not under maintenance");
			else
				activeChar.sendMessage("Manor System: under maintenance");
			showMainPage(activeChar);
		}
		else if (command.equals("admin_manor_save"))
		{
			CastleManorManager.getInstance().save();
			activeChar.sendMessage("Manor System: all data saved");
			showMainPage(activeChar);
		}
		else if (command.equals("admin_manor_disable"))
		{
			boolean mode = CastleManorManager.getInstance().isDisabled();
			CastleManorManager.getInstance().setDisabled(!mode);
			if (mode)
				activeChar.sendMessage("Manor System: enabled");
			else
				activeChar.sendMessage("Manor System: disabled");
			showMainPage(activeChar);
		}
		
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return _adminCommands;
	}
	
	private static String formatTime(long millis)
	{
		String s = "";
		int secs = (int) millis / 1000;
		int mins = secs / 60;
		secs -= mins * 60;
		int hours = mins / 60;
		mins -= hours * 60;
		
		if (hours > 0)
			s += hours + ":";
		s += mins + ":";
		s += secs;
		return s;
	}
	
	private static void showMainPage(L2PcInstance activeChar)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		StringBuilder replyMSG = new StringBuilder("<html><body>");
		
		replyMSG.append("<center><font color=\"LEVEL\"> [Manor System] </font></center><br>");
		replyMSG.append("<table width=\"100%\"><tr><td>");
		replyMSG.append("Disabled: " + (CastleManorManager.getInstance().isDisabled() ? "yes" : "no") + "</td><td>");
		replyMSG.append("Under Maintenance: " + (CastleManorManager.getInstance().isUnderMaintenance() ? "yes" : "no") + "</td></tr><tr><td>");
		replyMSG.append("Time to refresh: " + formatTime(CastleManorManager.getInstance().getMillisToManorRefresh()) + "</td><td>");
		replyMSG.append("Time to approve: " + formatTime(CastleManorManager.getInstance().getMillisToNextPeriodApprove()) + "</td></tr>");
		replyMSG.append("</table>");
		
		replyMSG.append("<center><table><tr><td>");
		replyMSG.append("<button value=\"Set Next\" action=\"bypass -h admin_manor_setnext\" width=110 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td><td>");
		replyMSG.append("<button value=\"Approve Next\" action=\"bypass -h admin_manor_approve\" width=110 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td></tr><tr><td>");
		replyMSG.append("<button value=\"" + (CastleManorManager.getInstance().isUnderMaintenance() ? "Set normal" : "Set mainteance") + "\" action=\"bypass -h admin_manor_setmaintenance\" width=110 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td><td>");
		replyMSG.append("<button value=\"" + (CastleManorManager.getInstance().isDisabled() ? "Enable" : "Disable") + "\" action=\"bypass -h admin_manor_disable\" width=110 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td></tr><tr><td>");
		replyMSG.append("<button value=\"Refresh\" action=\"bypass -h admin_manor\" width=110 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td><td>");
		replyMSG.append("<button value=\"Back\" action=\"bypass -h admin_admin\" width=110 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td></tr>");
		replyMSG.append("</table></center>");
		
		replyMSG.append("<br><center>Castle Information:<table width=\"100%\">");
		replyMSG.append("<tr><td></td><td>Current Period</td><td>Next Period</td></tr>");
		
		for (Castle c : CastleManager.getInstance().getCastles())
		{
			replyMSG.append("<tr><td>" + c.getName() + "</td>" + "<td>" + c.getManorCost(CastleManorManager.PERIOD_CURRENT) + "a</td>" + "<td>" + c.getManorCost(CastleManorManager.PERIOD_NEXT) + "a</td>" + "</tr>");
		}
		
		replyMSG.append("</table><br>");
		
		replyMSG.append("</body></html>");
		
		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}
}