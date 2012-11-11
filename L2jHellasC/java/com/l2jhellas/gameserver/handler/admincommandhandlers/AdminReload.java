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
package com.l2jhellas.gameserver.handler.admincommandhandlers;

import java.util.StringTokenizer;

import javolution.text.TextBuilder;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.TradeController;
import com.l2jhellas.gameserver.cache.HtmCache;
import com.l2jhellas.gameserver.datatables.ItemTable;
import com.l2jhellas.gameserver.datatables.NpcTable;
import com.l2jhellas.gameserver.datatables.NpcWalkerRoutesTable;
import com.l2jhellas.gameserver.datatables.SkillTable;
import com.l2jhellas.gameserver.datatables.TeleportLocationTable;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.instancemanager.Manager;
import com.l2jhellas.gameserver.instancemanager.QuestManager;
import com.l2jhellas.gameserver.model.L2Multisell;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * @author KidZor
 */
public class AdminReload implements IAdminCommandHandler
{
	private static final String[]	ADMIN_COMMANDS	={ "admin_reload" };

	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{

		if (command.startsWith("admin_reload"))
		{
			sendReloadPage(activeChar);
			StringTokenizer st = new StringTokenizer(command);
			st.nextToken();

			try
			{
				String type = st.nextToken();

				if(type.equals("multisell"))
				{
					L2Multisell.getInstance().reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("multisell reloaded");
				}
				else if(type.startsWith("teleport"))
				{
					TeleportLocationTable.getInstance().reloadAll();
					sendReloadPage(activeChar);
					activeChar.sendMessage("teleport location table reloaded");
				}
				else if(type.startsWith("skill"))
				{
					SkillTable.getInstance().reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("skills reloaded");
				}
				else if(type.equals("npc"))
				{
					NpcTable.getInstance().reloadAllNpc();
					sendReloadPage(activeChar);
					activeChar.sendMessage("npcs reloaded");
				}
				else if(type.startsWith("htm"))
				{
					HtmCache.getInstance().reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Cache[HTML]: " + HtmCache.getInstance().getMemoryUsage()  + " megabytes on " + HtmCache.getInstance().getLoadedFiles() + " files loaded");
				}
				else if(type.startsWith("item"))
				{
					ItemTable.getInstance().reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Item templates reloaded");
				}
				else if(type.startsWith("instancemanager"))
				{
					Manager.reloadAll();
					sendReloadPage(activeChar);
					activeChar.sendMessage("All instance manager has been reloaded");
				}
				else if(type.startsWith("npcwalkers"))
				{
					NpcWalkerRoutesTable.getInstance().load();
					sendReloadPage(activeChar);
					activeChar.sendMessage("All NPC walker routes have been reloaded");
					
				}
				else if (type.equals("quests"))
				{
					String folder = "quests";
					QuestManager.getInstance().reload(folder);
					sendReloadPage(activeChar);
					activeChar.sendMessage("Quests Reloaded.");
				}
				else if (type.equals("configs"))
				{
					Config.load();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Server Config Reloaded.");
				}
				else if(type.equals("tradelist"))
				{
					TradeController.reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("TradeList Table reloaded.");
				}
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Usage:  //reload <type>");
			}
		}
		return true;
	}

	/**
	 * send reload page
	 * @param admin
	 */
	private void sendReloadPage(L2PcInstance player)
	{
        NpcHtmlMessage menu = new NpcHtmlMessage(5);
        TextBuilder replyMSG = new TextBuilder("<html><center><body>");
        replyMSG.append("<img src=L2Font-e.replay_logo-e width=258 height=60><br>");
        replyMSG.append("<img src=L2UI_CH3.herotower_deco width=256 height=32>");
        replyMSG.append("<font color=AAAAAA>Reload Menu<br>");
        replyMSG.append("<a action=\"bypass -h admin_reload multisell\">Reload Multisell</a><br1>");
        replyMSG.append("<a action=\"bypass -h admin_reload teleport\">Reload Teleports</a><br1>");
        replyMSG.append("<a action=\"bypass -h admin_reload skill\">Reload Skills</a><br1>");
        replyMSG.append("<a action=\"bypass -h admin_reload npc\">Reload Npcs</a><br1>");
        replyMSG.append("<a action=\"bypass -h admin_reload htm\">Reload Html</a><br1>");
        replyMSG.append("<a action=\"bypass -h admin_reload item\">Reload Item Tabels</a><br1>");
        replyMSG.append("<a action=\"bypass -h admin_reload npcwalkers\">Reload Npc Walkers</a><br1>");
        replyMSG.append("<a action=\"bypass -h admin_reload configs\">Reload Configs</a><br1>");
        replyMSG.append("<a action=\"bypass -h admin_reload tradelist\">Reload Trade Lists</a><br1>");
        replyMSG.append("<a action=\"bypass -h admin_reload instancemanager\">Reload Instance Managers</a><br1>");
        replyMSG.append("</font></body></center></html>");
        menu.setHtml(replyMSG.toString());
        player.sendPacket(menu);
	}

	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}
