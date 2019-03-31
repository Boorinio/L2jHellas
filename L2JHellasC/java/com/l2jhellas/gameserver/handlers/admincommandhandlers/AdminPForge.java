package com.l2jhellas.gameserver.handlers.admincommandhandlers;

import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.AdminForgePacket;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;

import java.util.StringTokenizer;

public class AdminPForge implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_forge",
		"admin_forge2",
		"admin_forge3"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.equals("admin_forge"))
			showMainPage(activeChar);
		else if (command.startsWith("admin_forge2"))
		{
			try
			{
				StringTokenizer st = new StringTokenizer(command);
				st.nextToken();
				String format = st.nextToken();
				showPage2(activeChar, format);
			}
			catch (Exception ex)
			{
				activeChar.sendMessage("Usage: //forge2 format");
			}
		}
		else if (command.startsWith("admin_forge3"))
		{
			try
			{
				StringTokenizer st = new StringTokenizer(command);
				st.nextToken();
				String format = st.nextToken();
				boolean broadcast = false;
				
				if (format.toLowerCase().equals("broadcast"))
				{
					format = st.nextToken();
					broadcast = true;
				}
				
				AdminForgePacket sp = new AdminForgePacket();
				for (int i = 0; i < format.length(); i++)
				{
					String val = st.nextToken();
					if (val.toLowerCase().equals("$objid"))
					{
						val = String.valueOf(activeChar.getObjectId());
					}
					else if (val.toLowerCase().equals("$tobjid"))
					{
						val = String.valueOf(activeChar.getTarget().getObjectId());
					}
					else if (val.toLowerCase().equals("$bobjid"))
					{
						if (activeChar.getBoat() != null)
						{
							val = String.valueOf(activeChar.getBoat().getObjectId());
						}
					}
					else if (val.toLowerCase().equals("$clanid"))
					{
						val = String.valueOf(activeChar.getCharId());
					}
					else if (val.toLowerCase().equals("$allyid"))
					{
						val = String.valueOf(activeChar.getAllyId());
					}
					else if (val.toLowerCase().equals("$tclanid"))
					{
						val = String.valueOf(((L2PcInstance) activeChar.getTarget()).getCharId());
					}
					else if (val.toLowerCase().equals("$tallyid"))
					{
						val = String.valueOf(((L2PcInstance) activeChar.getTarget()).getAllyId());
					}
					else if (val.toLowerCase().equals("$x"))
					{
						val = String.valueOf(activeChar.getX());
					}
					else if (val.toLowerCase().equals("$y"))
					{
						val = String.valueOf(activeChar.getY());
					}
					else if (val.toLowerCase().equals("$z"))
					{
						val = String.valueOf(activeChar.getZ());
					}
					else if (val.toLowerCase().equals("$heading"))
					{
						val = String.valueOf(activeChar.getHeading());
					}
					else if (val.toLowerCase().equals("$tx"))
					{
						val = String.valueOf(activeChar.getTarget().getX());
					}
					else if (val.toLowerCase().equals("$ty"))
					{
						val = String.valueOf(activeChar.getTarget().getY());
					}
					else if (val.toLowerCase().equals("$tz"))
					{
						val = String.valueOf(activeChar.getTarget().getZ());
					}
					else if (val.toLowerCase().equals("$theading"))
					{
						val = String.valueOf(((L2PcInstance) activeChar.getTarget()).getHeading());
					}
					
					sp.addPart(format.getBytes()[i], val);
				}
				
				if (broadcast)
					activeChar.broadcastPacket(sp);
				else
					activeChar.sendPacket(sp);
				
				showPage3(activeChar, format, command);
			}
			catch (Exception ex)
			{
				activeChar.sendMessage("Usage: //forge or //forge2 format");
			}
		}
		return true;
	}
	
	private static void showMainPage(L2PcInstance activeChar)
	{
		AdminHelpPage.showHelpPage(activeChar, "pforge1.htm");
	}
	
	private static void showPage2(L2PcInstance activeChar, String format)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(0);
		adminReply.setFile("data/html/admin/pforge2.htm");
		adminReply.replace("%format%", format);
		
		StringBuilder replyMSG = new StringBuilder();
		for (int i = 0; i < format.length(); i++)
			replyMSG.append(format.charAt(i) + " : <edit var=\"v" + i + "\" width=100><br1>");
		adminReply.replace("%valueditors%", replyMSG.toString());
		replyMSG.setLength(0);
		
		for (int i = 0; i < format.length(); i++)
			replyMSG.append(" \\$v" + i);
		adminReply.replace("%send%", replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}
	
	private static void showPage3(L2PcInstance activeChar, String format, String command)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(0);
		adminReply.setFile("data/html/admin/pforge3.htm");
		adminReply.replace("%format%", format);
		adminReply.replace("%command%", command);
		activeChar.sendPacket(adminReply);
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}