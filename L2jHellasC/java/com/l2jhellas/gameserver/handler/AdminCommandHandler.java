/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jhellas.gameserver.handler;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javolution.util.FastMap;
import Extensions.Balancer.Balancer;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminAdmin;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminAnnouncements;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminAutoAnnouncements;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminBBS;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminBan;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminBanChat;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminCTFEngine;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminCache;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminChangeAccessLevel;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminCreateItem;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminCursedWeapons;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminDMEngine;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminDelete;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminDonator;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminDoorControl;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminEditChar;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminEditNpc;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminEffects;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminEnchant;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminEventEngine;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminExpSp;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminFightCalculator;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminGeoEditor;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminGeodata;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminGm;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminGmChat;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminHeal;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminHelpPage;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminHero;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminInvul;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminKick;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminKill;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminLevel;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminLogin;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminMammon;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminManor;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminMenu;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminMobGroup;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminMonsterRace;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminNoble;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminPForge;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminPathNode;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminPetition;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminPledge;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminPolymorph;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminPremium;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminQuest;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminReload;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminRepairChar;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminRes;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminRideWyvern;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminShop;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminShutdown;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminSiege;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminSkill;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminSpawn;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminTarget;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminTeleport;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminTest;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminTvTEngine;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminUnblockIp;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminVIPEngine;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminZone;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

/**
 * This class ...
 * 
 * @version $Revision: 1.1.4.5 $ $Date: 2005/03/27 15:30:09 $
 */
public class AdminCommandHandler
{
	private static Logger _log = Logger.getLogger(AdminCommandHandler.class.getName());
	
	private static AdminCommandHandler _instance;
	
	private final Map<String, IAdminCommandHandler> _datatable;
	
	// Alt privileges setting
	private static Logger _priviLog = Logger.getLogger("AltPrivilegesAdmin");
	private static FastMap<String, Integer> _privileges;
	
	public static AdminCommandHandler getInstance()
	{
		if (_instance == null)
		{
			_instance = new AdminCommandHandler();
		}
		return _instance;
	}
	
	private AdminCommandHandler()
	{
		_datatable = new FastMap<String, IAdminCommandHandler>();
		registerAdminCommandHandler(new AdminAdmin());
		registerAdminCommandHandler(new AdminInvul());
		registerAdminCommandHandler(new AdminDelete());
		registerAdminCommandHandler(new AdminKill());
		registerAdminCommandHandler(new AdminTarget());
		registerAdminCommandHandler(new AdminShop());
		registerAdminCommandHandler(new AdminAnnouncements());
		registerAdminCommandHandler(new AdminAutoAnnouncements());
		registerAdminCommandHandler(new AdminCreateItem());
		registerAdminCommandHandler(new AdminHeal());
		registerAdminCommandHandler(new AdminHelpPage());
		registerAdminCommandHandler(new AdminShutdown());
		registerAdminCommandHandler(new AdminSpawn());
		registerAdminCommandHandler(new AdminSkill());
		registerAdminCommandHandler(new AdminExpSp());
		registerAdminCommandHandler(new AdminEventEngine());
		registerAdminCommandHandler(new AdminGmChat());
		registerAdminCommandHandler(new AdminEditChar());
		registerAdminCommandHandler(new AdminGm());
		registerAdminCommandHandler(new AdminTeleport());
		registerAdminCommandHandler(new AdminRepairChar());
		registerAdminCommandHandler(new AdminChangeAccessLevel());
		registerAdminCommandHandler(new AdminBan());
		registerAdminCommandHandler(new AdminPolymorph());
		registerAdminCommandHandler(new AdminBanChat());
		registerAdminCommandHandler(new AdminKick());
		registerAdminCommandHandler(new AdminMonsterRace());
		registerAdminCommandHandler(new AdminEditNpc());
		registerAdminCommandHandler(new AdminFightCalculator());
		registerAdminCommandHandler(new AdminMenu());
		registerAdminCommandHandler(new AdminSiege());
		registerAdminCommandHandler(new AdminPathNode());
		registerAdminCommandHandler(new AdminPetition());
		registerAdminCommandHandler(new AdminPForge());
		registerAdminCommandHandler(new AdminBBS());
		registerAdminCommandHandler(new AdminEffects());
		registerAdminCommandHandler(new AdminDoorControl());
		registerAdminCommandHandler(new AdminTest());
		registerAdminCommandHandler(new AdminEnchant());
		registerAdminCommandHandler(new AdminMobGroup());
		registerAdminCommandHandler(new AdminRes());
		registerAdminCommandHandler(new AdminMammon());
		registerAdminCommandHandler(new AdminUnblockIp());
		registerAdminCommandHandler(new AdminPledge());
		registerAdminCommandHandler(new AdminRideWyvern());
		registerAdminCommandHandler(new AdminLogin());
		registerAdminCommandHandler(new AdminCache());
		registerAdminCommandHandler(new AdminLevel());
		registerAdminCommandHandler(new AdminQuest());
		registerAdminCommandHandler(new AdminZone());
		registerAdminCommandHandler(new AdminCursedWeapons());
		registerAdminCommandHandler(new AdminGeodata());
		registerAdminCommandHandler(new AdminGeoEditor());
		registerAdminCommandHandler(new AdminManor());
		registerAdminCommandHandler(new AdminReload());
		registerAdminCommandHandler(new AdminDonator());
		registerAdminCommandHandler(new AdminCTFEngine());
		registerAdminCommandHandler(new AdminDMEngine());
		registerAdminCommandHandler(new AdminTvTEngine());
		registerAdminCommandHandler(new AdminVIPEngine());
		registerAdminCommandHandler(new AdminHero());
		registerAdminCommandHandler(new AdminNoble());
		registerAdminCommandHandler(new Balancer());
		registerAdminCommandHandler(new AdminPremium());
		_log.config("AdminCommandHandler: Loaded " + _datatable.size() + " handlers.");
		
	}
	
	public void registerAdminCommandHandler(IAdminCommandHandler handler)
	{
		String[] ids = handler.getAdminCommandList();
		for (int i = 0; i < ids.length; i++)
		{
			if (Config.DEBUG)
				_log.fine("Adding handler for command " + ids[i]);
			_datatable.put(ids[i], handler);
		}
	}
	
	public IAdminCommandHandler getAdminCommandHandler(String adminCommand)
	{
		String command = adminCommand;
		if (adminCommand.indexOf(" ") != -1)
		{
			command = adminCommand.substring(0, adminCommand.indexOf(" "));
		}
		if (Config.DEBUG)
			_log.fine("getting handler for command: " + command + " -> " + (_datatable.get(command) != null));
		return _datatable.get(command);
	}
	
	/**
	 * @return
	 */
	public int size()
	{
		return _datatable.size();
	}
	
	public final boolean checkPrivileges(L2PcInstance player, String adminCommand)
	{
		// Only a GM can execute a admin command
		if (!player.isGM())
			return false;
		
		// Skip special privileges handler?
		if (!Config.ALT_PRIVILEGES_ADMIN || Config.EVERYBODY_HAS_ADMIN_RIGHTS)
			return true;
		
		if (_privileges == null)
			_privileges = new FastMap<String, Integer>();
		
		String command = adminCommand;
		if (adminCommand.indexOf(" ") != -1)
		{
			command = adminCommand.substring(0, adminCommand.indexOf(" "));
		}
		
		// The command not exists
		if (!_datatable.containsKey(command))
			return false;
		
		int requireLevel = 0;
		
		if (!_privileges.containsKey(command))
		{
			// Try to loaded the command config
			boolean isLoaded = false;
			
			try
			{
				Properties Settings = new Properties();
				InputStream is = new FileInputStream(Config.COMMAND_PRIVILEGES_FILE);
				Settings.load(is);
				is.close();
				
				String stringLevel = Settings.getProperty(command);
				
				if (stringLevel != null)
				{
					isLoaded = true;
					requireLevel = Integer.parseInt(stringLevel);
				}
			}
			catch (Exception e)
			{
			}
			
			// Secure level?
			if (!isLoaded)
			{
				if (Config.ALT_PRIVILEGES_SECURE_CHECK)
				{
					_priviLog.info("The command '" + command + "' haven't got a entry in the configuration file. The command cannot be executed!!");
					return false;
				}
				
				requireLevel = Config.ALT_PRIVILEGES_DEFAULT_LEVEL;
			}
			
			_privileges.put(command, requireLevel);
		}
		else
		{
			requireLevel = _privileges.get(command);
		}
		
		if (player.getAccessLevel() < requireLevel)
		{
			_priviLog.warning("<GM>" + player.getName() + ": have not access level to execute the command '" + command + "'");
			return false;
		}
		
		return true;
	}
}
