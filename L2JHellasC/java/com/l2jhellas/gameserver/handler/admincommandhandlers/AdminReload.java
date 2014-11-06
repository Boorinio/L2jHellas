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

import java.io.File;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.script.ScriptException;

import Extensions.Balancer.BalanceLoad;

import com.PackRoot;
import com.l2jhellas.Config;
import com.l2jhellas.gameserver.SevenSigns;
import com.l2jhellas.gameserver.cache.CrestCache;
import com.l2jhellas.gameserver.cache.HtmCache;
import com.l2jhellas.gameserver.controllers.TradeController;
import com.l2jhellas.gameserver.datatables.LevelUpData;
import com.l2jhellas.gameserver.datatables.csv.ExtractableItemsData;
import com.l2jhellas.gameserver.datatables.sql.BufferSkillsTable;
import com.l2jhellas.gameserver.datatables.sql.ItemTable;
import com.l2jhellas.gameserver.datatables.sql.PcColorTable;
import com.l2jhellas.gameserver.datatables.sql.SpawnTable;
import com.l2jhellas.gameserver.datatables.xml.AdminData;
import com.l2jhellas.gameserver.datatables.xml.AugmentationData;
import com.l2jhellas.gameserver.datatables.xml.DoorData;
import com.l2jhellas.gameserver.datatables.xml.MultisellData;
import com.l2jhellas.gameserver.datatables.xml.NpcData;
import com.l2jhellas.gameserver.datatables.xml.NpcWalkerRoutesData;
import com.l2jhellas.gameserver.datatables.xml.SkillSpellbookData;
import com.l2jhellas.gameserver.datatables.xml.SkillTreeData;
import com.l2jhellas.gameserver.datatables.xml.SummonItemsData;
import com.l2jhellas.gameserver.datatables.xml.TeleportLocationData;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.instancemanager.CursedWeaponsManager;
import com.l2jhellas.gameserver.instancemanager.DayNightSpawnManager;
import com.l2jhellas.gameserver.instancemanager.Manager;
import com.l2jhellas.gameserver.instancemanager.QuestManager;
import com.l2jhellas.gameserver.instancemanager.RaidBossSpawnManager;
import com.l2jhellas.gameserver.instancemanager.ZoneManager;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jhellas.gameserver.scripting.L2ScriptEngineManager;
import com.l2jhellas.gameserver.skills.HeroSkillTable;
import com.l2jhellas.gameserver.skills.NobleSkillTable;
import com.l2jhellas.gameserver.skills.SkillTable;
import com.l2jhellas.util.Util;

/**
 * @author KidZor
 */
public class AdminReload implements IAdminCommandHandler
{
	private static final Logger _log = Logger.getLogger(AdminReload.class.getName());
	
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
			if (!st.hasMoreTokens())
			{
				activeChar.sendMessage("You need to specify a type to reload!");
				activeChar.sendMessage("Usage: //reload <multisell|teleport|skill|(npc|npcs)|(htm|html)|(item|items)|access|instancemanager|npcwalkers|quests|configs|tradelist|pccolor|crest|(cw|cursed)|levelupdata|summonitems|balancer|extitems|nobleskill|heroskill|skilltrees|spellbooks>");
				return false;
			}
			
			final String type = st.nextToken();
			try
			{
				if (type.equals("multisell"))
				{
					MultisellData.getInstance().reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Multisells has been reloaded.");
				}
				else if (type.startsWith("doors"))
				{
					DoorData.getInstance().reloadAll();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Door data reloaded.");
				}
				else if (type.startsWith("teleport"))
				{
					TeleportLocationData.getInstance().reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Teleport locations has been reloaded.");
				}
				else if (type.equals("npc") || type.equals("npcs"))
				{
					NpcData.getInstance().reloadAllNpc();
					activeChar.sendMessage("All NPCs have been reloaded.");
					sendReloadPage(activeChar);
				}
				else if (type.equals("respawn_npc") || type.equals("respawn_npcs"))
				{
					for (L2PcInstance player : L2World.getAllPlayers())
					{
						player.sendPacket(SystemMessageId.NPC_SERVER_NOT_OPERATING);
					}
					RaidBossSpawnManager.getInstance().cleanUp();
					DayNightSpawnManager.getInstance().cleanUp();
					L2World.deleteVisibleNpcSpawns();
					AdminData.getInstance().broadcastMessageToGMs("NPC Unspawn completed!");
					// make sure all spawns are deleted
					RaidBossSpawnManager.getInstance().cleanUp();
					DayNightSpawnManager.getInstance().cleanUp();
					L2World.deleteVisibleNpcSpawns();
					// now respawn all
					NpcData.getInstance().reloadAllNpc();
					SpawnTable.getInstance().reloadAll();
					RaidBossSpawnManager.getInstance().reloadBosses();
					SevenSigns.getInstance().spawnSevenSignsNPC();
					AdminData.getInstance().broadcastMessageToGMs("NPC Respawn completed!");
					activeChar.sendMessage("All NPCs have been reloaded.");
					sendReloadPage(activeChar);
				}
				else if (type.equals("zone"))
				{
					ZoneManager.getInstance().reload();
					sendReloadPage(activeChar);
					AdminData.getInstance().broadcastMessageToGMs("Zones can not be reloaded in this version.");
				}
				else if (type.equals("html") || type.equals("htm"))
				{
					HtmCache.getInstance().reload(PackRoot.DATAPACK_ROOT);
					activeChar.sendMessage("Cache[HTML]: " + HtmCache.getInstance().getMemoryUsage() + " MB on " + HtmCache.getInstance().getLoadedFiles() + " file(s) loaded.");
					sendReloadPage(activeChar);
				}
				else if (type.equals("path"))
				{
					try
					{
						String path = command.split(" ")[1];
						HtmCache.getInstance().reloadPath(new File(PackRoot.DATAPACK_ROOT, path));
						activeChar.sendMessage("Cache[HTML]: " + HtmCache.getInstance().getMemoryUsage() + " MB in " + HtmCache.getInstance().getLoadedFiles() + " file(s) loaded.");
					}
					catch (Exception e)
					{
						activeChar.sendMessage("Usage: //reload path <path>");
					}
					sendReloadPage(activeChar);
				}
				else if (type.equals("file"))
				{
					try
					{
						String path = command.split(" ")[1];
						if (HtmCache.getInstance().loadFile(new File(PackRoot.DATAPACK_ROOT, path)) != null)
						{
							activeChar.sendMessage("Cache[HTML]: file was loaded.");
						}
						else
						{
							activeChar.sendMessage("Cache[HTML]: file can't be loaded.");
						}
					}
					catch (Exception e)
					{
						activeChar.sendMessage("Usage: //reload file <relative_path/file>");
					}
					sendReloadPage(activeChar);
				}
				else if (command.startsWith("crest_cache"))
				{
					CrestCache.reload();
					activeChar.sendMessage("Cache[Crest]: " + String.format("%.3f", CrestCache.getInstance().getMemoryUsage()) + " megabytes on " + CrestCache.getInstance().getLoadedFiles() + " files loaded.");
					sendReloadPage(activeChar);
				}

				else if (type.startsWith("item") || type.startsWith("items"))
				{
					ItemTable.getInstance().reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Item table has been reloaded.");
				}
				else if (type.startsWith("access"))
				{
					AdminData.getInstance().reload();
					activeChar.sendMessage("Access Rights have been reloaded.");
				}
				else if (type.startsWith("instancemanager"))
				{
					Manager.reloadAll();
					sendReloadPage(activeChar);
					activeChar.sendMessage("All instance managers has been reloaded.");
				}
				else if (type.startsWith("npcwalkers"))
				{
					NpcWalkerRoutesData.reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("All NPC walker routes have been reloaded.");
				}
				else if (type.startsWith("quests"))
				{
					QuestManager.getInstance().reloadAllQuests();
					QuestManager.getInstance().report();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Quests has been reloaded.");
				}
				else if (type.equals("configs") || type.equals("config"))
				{
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
				else if (type.equals("cw") || type.equals("cursed"))
				{
					CursedWeaponsManager.reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Cursed Weapons has been reloaded.");
				}
				else if (type.equals("levelupdata"))
				{
					LevelUpData.reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Level up Data has been reloaded.");
				}
				else if (type.equals("summonitems"))
				{
					SummonItemsData.reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Summon Items has been reloaded.");
				}
				else if (type.equals("balancer"))
				{
					BalanceLoad.LoadEm();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Balance stats for classes has been reloaded.");
				}
				else if (type.startsWith("skill"))
				{
					SkillTable.reload();
					BufferSkillsTable.getInstance().reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("All skills has been reloaded.");
				}
				else if (type.equals("nobleskill"))
				{
					NobleSkillTable.reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Noble Skill table has been reloaded.");
				}
				else if (type.equals("heroskill"))
				{
					HeroSkillTable.reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Hero Skill table has been reloaded.");
				}
				else if (type.equals("sktrees"))
				{
					SkillTreeData.reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Skill Tree table has been reloaded.");
				}
				else if (type.equals("spellbooks"))
				{
					SkillSpellbookData.reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Spellbooks Table has been reloaded.");
				}
				else if (type.equals("extitems"))
				{
					ExtractableItemsData.reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Extractable Items has been reloaded.");
				}
				else if (type.equals("augment"))
				{
					AugmentationData.reload();
					sendReloadPage(activeChar);
					activeChar.sendMessage("Augmentation Data has been reloaded.");
				}
				else if (type.startsWith("handler"))
				{
					File file = new File(L2ScriptEngineManager.SCRIPT_FOLDER, "handlers/MasterHandler.java");
					try
					{
						L2ScriptEngineManager.getInstance().executeScript(file);
						activeChar.sendMessage("All handlers have been reloaded");
					}
					catch (ScriptException e)
					{
						L2ScriptEngineManager.reportScriptFileError(file, e);
						activeChar.sendMessage("There was an error while loading handlers.");
					}
					sendReloadPage(activeChar);
				}
			}
			catch (Exception e)
			{
				activeChar.sendMessage("An error occured while reloading " + type + " !");
				activeChar.sendMessage("Usage: //reload <multisell|teleport|skill|(npc|npcs)|(htm|html)|(item|items)|access|instancemanager|npcwalkers|quests|configs|tradelist|pccolor|crest|(cw|cursed)|levelupdata|summonitems|balancer|extitems|nobleskill|heroskill|skilltrees|spellbooks>");
				_log.log(Level.WARNING, "An error occured while reloading " + type + ": " + e.getMessage(), e);
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
		player.sendPacket(new NpcHtmlMessage(1, html));
		Util.printSection("Reload");
	}

	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}