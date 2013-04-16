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
package com.l2jhellas.gameserver.handler;

import java.util.Map;
import java.util.logging.Level;
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
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminChaos;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminClanFull;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminCreateItem;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminCursedWeapons;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminDMEngine;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminDelete;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminDeport;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminDisconnect;
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
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminMassHero;
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
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminRecallAll;
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
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminTvTEngine;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminUnblockIp;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminVIPEngine;
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminZone;

public class AdminCommandHandler
{
	private static Logger _log = Logger.getLogger(AdminCommandHandler.class.getName());
	private final Map<Integer, IAdminCommandHandler> _datatable;

	public static AdminCommandHandler getInstance()
	{
		return SingletonHolder._instance;
	}

	private AdminCommandHandler()
	{
		_datatable = new FastMap<Integer, IAdminCommandHandler>();
		registerAdminCommandHandler(new AdminAdmin());
		registerAdminCommandHandler(new AdminAnnouncements());
		registerAdminCommandHandler(new AdminAutoAnnouncements());
        registerAdminCommandHandler(new AdminBan());
        registerAdminCommandHandler(new AdminBanChat());
        registerAdminCommandHandler(new AdminBBS());
        registerAdminCommandHandler(new AdminCache());
        registerAdminCommandHandler(new AdminChangeAccessLevel());
        registerAdminCommandHandler(new AdminCreateItem());
        registerAdminCommandHandler(new AdminCursedWeapons());
        registerAdminCommandHandler(new AdminDelete());
        registerAdminCommandHandler(new AdminDeport());
        registerAdminCommandHandler(new AdminDisconnect());
        registerAdminCommandHandler(new AdminDoorControl());
        registerAdminCommandHandler(new AdminEditChar());
        registerAdminCommandHandler(new AdminEditNpc());
        registerAdminCommandHandler(new AdminEffects());
        registerAdminCommandHandler(new AdminEnchant());
        registerAdminCommandHandler(new AdminEventEngine());
        registerAdminCommandHandler(new AdminFightCalculator());
		registerAdminCommandHandler(new AdminExpSp());
        registerAdminCommandHandler(new AdminGeodata());
        registerAdminCommandHandler(new AdminGeoEditor());
		registerAdminCommandHandler(new AdminGm());
		registerAdminCommandHandler(new AdminGmChat());
        registerAdminCommandHandler(new AdminHeal());
		registerAdminCommandHandler(new AdminHelpPage());
		registerAdminCommandHandler(new AdminInvul());
		registerAdminCommandHandler(new AdminKick());
		registerAdminCommandHandler(new AdminKill());
        registerAdminCommandHandler(new AdminLevel());
        registerAdminCommandHandler(new AdminLogin());
        registerAdminCommandHandler(new AdminMammon());
        registerAdminCommandHandler(new AdminManor());
        registerAdminCommandHandler(new AdminMassHero());
        registerAdminCommandHandler(new AdminMenu());
        registerAdminCommandHandler(new AdminMobGroup());
        registerAdminCommandHandler(new AdminMonsterRace());
        registerAdminCommandHandler(new AdminPathNode());
        registerAdminCommandHandler(new AdminPetition());
        registerAdminCommandHandler(new AdminPForge());
		registerAdminCommandHandler(new AdminPledge());
        registerAdminCommandHandler(new AdminPolymorph());
        registerAdminCommandHandler(new AdminQuest());
        registerAdminCommandHandler(new AdminRecallAll());
        registerAdminCommandHandler(new AdminRepairChar());
        registerAdminCommandHandler(new AdminRes());
        registerAdminCommandHandler(new AdminRideWyvern());
		registerAdminCommandHandler(new AdminShop());
		registerAdminCommandHandler(new AdminShutdown());
		registerAdminCommandHandler(new AdminSiege());
		registerAdminCommandHandler(new AdminSkill());
		registerAdminCommandHandler(new AdminSpawn());
		registerAdminCommandHandler(new AdminTarget());
		registerAdminCommandHandler(new AdminTeleport());
        registerAdminCommandHandler(new AdminUnblockIp());
        registerAdminCommandHandler(new AdminZone());
		registerAdminCommandHandler(new AdminDonator());
		registerAdminCommandHandler(new AdminCTFEngine());
		registerAdminCommandHandler(new AdminDMEngine());
		registerAdminCommandHandler(new AdminTvTEngine());
		registerAdminCommandHandler(new AdminVIPEngine());
		registerAdminCommandHandler(new AdminHero());
		registerAdminCommandHandler(new AdminNoble());
		registerAdminCommandHandler(new Balancer());
		registerAdminCommandHandler(new AdminPremium());
		registerAdminCommandHandler(new AdminReload());
		if (Config.ENABLED_CHAOS_EVENT)
			registerAdminCommandHandler(new AdminChaos());
		registerAdminCommandHandler(new AdminClanFull());

		_log.log(Level.INFO, getClass().getSimpleName() + ": Loaded " + size() + " Handlers in total.");
	}

	public void registerAdminCommandHandler(IAdminCommandHandler handler)
	{
		String[] ids = handler.getAdminCommandList();
		for (int i = 0; i < ids.length; i++)
		{
			if (Config.DEBUG)
				_log.log(Level.CONFIG, getClass().getName() + ": Adding handler for command " + ids[i]);
			_datatable.put(ids[i].hashCode(), handler);
		}
	}

	public IAdminCommandHandler getAdminCommandHandler(String adminCommand)
	{
		String command = adminCommand;

		if (adminCommand.indexOf(" ") != -1)
			command = adminCommand.substring(0, adminCommand.indexOf(" "));

		if (Config.DEBUG)
			_log.log(Level.CONFIG, getClass().getName() + ": getting handler for command: " + command + " -> " + (_datatable.get(command.hashCode()) != null));
		return _datatable.get(command.hashCode());
	}

	public int size()
	{
		return _datatable.size();
	}

	@SuppressWarnings("synthetic-access")
	private static class SingletonHolder
	{
		protected static final AdminCommandHandler _instance = new AdminCommandHandler();
	}
}