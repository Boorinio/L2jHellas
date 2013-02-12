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

import Extensions.Balancer.BalanceLoad;

import com.l2jhellas.Config;
import com.l2jhellas.ExternalConfig;
import com.l2jhellas.gameserver.TradeController;
import com.l2jhellas.gameserver.cache.CrestCache;
import com.l2jhellas.gameserver.cache.HtmCache;
import com.l2jhellas.gameserver.datatables.LevelUpData;
import com.l2jhellas.gameserver.datatables.csv.ExtractableItemsData;
import com.l2jhellas.gameserver.datatables.csv.SummonItemsData;
import com.l2jhellas.gameserver.datatables.sql.ItemTable;
import com.l2jhellas.gameserver.datatables.sql.NpcTable;
import com.l2jhellas.gameserver.datatables.sql.NpcWalkerRoutesTable;
import com.l2jhellas.gameserver.datatables.sql.PcColorTable;
import com.l2jhellas.gameserver.datatables.sql.SkillSpellbookTable;
import com.l2jhellas.gameserver.datatables.sql.SkillTreeTable;
import com.l2jhellas.gameserver.datatables.sql.TeleportLocationTable;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.instancemanager.CursedWeaponsManager;
import com.l2jhellas.gameserver.instancemanager.Manager;
import com.l2jhellas.gameserver.instancemanager.QuestManager;
import com.l2jhellas.gameserver.model.L2Multisell;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jhellas.gameserver.skills.HeroSkillTable;
import com.l2jhellas.gameserver.skills.NobleSkillTable;
import com.l2jhellas.gameserver.skills.SkillTable;

/**
 * @author KidZor
 */
public class AdminReload implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_reload"
	};

	@Override
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

				if (type.equals("multisell"))
				{
					L2Multisell.getInstance().reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Multisells has been reloaded.");
				}
				else if (type.startsWith("teleport"))
				{
					TeleportLocationTable.getInstance().reloadAll();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Teleport location table reloaded.");
				}
				else if (type.startsWith("skill"))
				{
					SkillTable.getInstance().reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("All skills has been reloaded.");
				}
				else if (type.equals("npc"))
				{
					NpcTable.getInstance().reloadAllNpc();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Npcs has been reloaded.");
				}
				else if (type.startsWith("htm"))
				{
					HtmCache.getInstance().reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Cache[HTML]: " + HtmCache.getInstance().getMemoryUsage() + " megabytes on " + HtmCache.getInstance().getLoadedFiles() + " files loaded");
				}
				else if (type.startsWith("item"))
				{
					ItemTable.getInstance().reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Item templates has been reloaded.");
				}
				else if (type.startsWith("instancemanager"))
				{
					Manager.reloadAll();
					sendReloadPage(activeChar);
					activeChar.sendMessage("All instance manager has been reloaded.");
				}
				else if (type.startsWith("npcwalkers"))
				{
					NpcWalkerRoutesTable.getInstance().load();
					sendReloadPage(activeChar);
					activeChar.sendMessage("All NPC walker routes have been reloaded.");
				}
				else if (type.startsWith("quests"))
				{
					String folder = "quests";
					QuestManager.getInstance().reload(folder);
					sendReloadPage(activeChar);
					activeChar.sendMessage("Quests has been Reloaded.");
				}
				else if (type.equals("configs"))
				{
					ExternalConfig.load();
					Config.load();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Server Configs has been Reloaded.");
				}
				else if (type.equals("tradelist"))
				{
					TradeController.reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("TradeList Table has been reloaded.");
				}
				else if (type.equals("pccolor"))
				{
					PcColorTable.process(activeChar);
					sendReloadPage(activeChar);
					activeChar.sendMessage("PcColor Table has been reloaded.");
				}
				else if (type.startsWith("crest"))
				{
					CrestCache.getInstance();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Crest Table has been reloaded.");
				}
				else if (type.equals("cw"))
				{
					CursedWeaponsManager.getInstance().reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Cursed Weapons has been reloaded.");
				}
				else if (type.equals("levelupdata"))
				{
					LevelUpData.getInstance();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Level up Data has been reloaded.");
				}
				else if (type.equals("summonitems"))
				{
					SummonItemsData.getInstance();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Summon Items has been reloaded.");
				}
				else if (type.equals("balancer"))
				{
					BalanceLoad.loadBalance();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Balance stats for classes has been reloaded.");
				}
				else if (type.equals("nobleskill"))
				{
					NobleSkillTable.getInstance();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Noble Skill table has been reloaded.");
				}
				else if (type.equals("heroskill"))
				{
					HeroSkillTable.getInstance();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Hero Skill table has been reloaded.");
				}
				else if (type.equals("skilltrees"))
				{
					SkillTreeTable.getInstance();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Skill Tree table has been reloaded.");
				}
				else if (type.equals("spellbooks"))
				{
					SkillSpellbookTable.getInstance();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Spellbooks Table has been reloaded.");
				}
				else if (type.equals("extitems"))
				{
					ExtractableItemsData.getInstance();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Extractable items has been reloaded.");
				}
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Usage: //reload <type>");
			}
		}
		return true;
	}

	/**
	 * send reload page
	 *
	 * @param admin
	 */
	private void sendReloadPage(L2PcInstance player)
	{
		String html = HtmCache.getInstance().getHtm("data/html/admin/reload_menu.htm");
		if (html == null)
		{
			html = "<html><body><br><br><center><font color=LEVEL>404:</font> File Not Found</center></body></html>";
		}
		player.sendPacket(new NpcHtmlMessage(1, html));
	}

	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}
