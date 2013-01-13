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
package com.l2jhellas.gameserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import Extensions.Balancer.BalanceLoad;
import Extensions.RankSystem.CharacterRankRewardTable;
import Extensions.RankSystem.PvpTable;
import Extensions.RankSystem.RankRewardTable;
import Extensions.RankSystem.TopTable;
import Extensions.Vote.VoteMain;

import com.l2jhellas.Config;
import com.l2jhellas.ExternalConfig;
import com.l2jhellas.L2DatabaseFactory;
import com.l2jhellas.Server;
import com.l2jhellas.gameserver.cache.CrestCache;
import com.l2jhellas.gameserver.cache.HtmCache;
import com.l2jhellas.gameserver.communitybbs.Manager.ForumsBBSManager;
import com.l2jhellas.gameserver.datatables.AccessLevels;
import com.l2jhellas.gameserver.datatables.ArmorSetsTable;
import com.l2jhellas.gameserver.datatables.AugmentationData;
import com.l2jhellas.gameserver.datatables.BuffTemplateTable;
import com.l2jhellas.gameserver.datatables.CharNameTable;
import com.l2jhellas.gameserver.datatables.CharTemplateTable;
import com.l2jhellas.gameserver.datatables.ClanTable;
import com.l2jhellas.gameserver.datatables.DoorTable;
import com.l2jhellas.gameserver.datatables.EventDroplist;
import com.l2jhellas.gameserver.datatables.ExtractableItemsData;
import com.l2jhellas.gameserver.datatables.FishTable;
import com.l2jhellas.gameserver.datatables.HelperBuffTable;
import com.l2jhellas.gameserver.datatables.HennaTable;
import com.l2jhellas.gameserver.datatables.HennaTreeTable;
import com.l2jhellas.gameserver.datatables.HeroSkillTable;
import com.l2jhellas.gameserver.datatables.ItemTable;
import com.l2jhellas.gameserver.datatables.LevelUpData;
import com.l2jhellas.gameserver.datatables.MapRegionTable;
import com.l2jhellas.gameserver.datatables.MaxCheatersTable;
import com.l2jhellas.gameserver.datatables.NobleSkillTable;
import com.l2jhellas.gameserver.datatables.NpcTable;
import com.l2jhellas.gameserver.datatables.NpcWalkerRoutesTable;
import com.l2jhellas.gameserver.datatables.PcColorTable;
import com.l2jhellas.gameserver.datatables.SkillSpellbookTable;
import com.l2jhellas.gameserver.datatables.SkillTable;
import com.l2jhellas.gameserver.datatables.SkillTreeTable;
import com.l2jhellas.gameserver.datatables.SpawnTable;
import com.l2jhellas.gameserver.datatables.StaticObjects;
import com.l2jhellas.gameserver.datatables.SummonItemsData;
import com.l2jhellas.gameserver.datatables.TeleportLocationTable;
import com.l2jhellas.gameserver.datatables.ZoneData;
import com.l2jhellas.gameserver.geoeditorcon.GeoEditorListener;
import com.l2jhellas.gameserver.handler.AdminCommandHandler;
import com.l2jhellas.gameserver.handler.AutoAnnouncementHandler;
import com.l2jhellas.gameserver.handler.ChatHandler;
import com.l2jhellas.gameserver.handler.ItemHandler;
import com.l2jhellas.gameserver.handler.SkillHandler;
import com.l2jhellas.gameserver.handler.UserCommandHandler;
import com.l2jhellas.gameserver.handler.VoicedCommandHandler;
import com.l2jhellas.gameserver.idfactory.IdFactory;
import com.l2jhellas.gameserver.instancemanager.AuctionManager;
import com.l2jhellas.gameserver.instancemanager.AwayManager;
import com.l2jhellas.gameserver.instancemanager.BoatManager;
import com.l2jhellas.gameserver.instancemanager.CastleManager;
import com.l2jhellas.gameserver.instancemanager.CastleManorManager;
import com.l2jhellas.gameserver.instancemanager.ClanHallManager;
import com.l2jhellas.gameserver.instancemanager.CoupleManager;
import com.l2jhellas.gameserver.instancemanager.CursedWeaponsManager;
import com.l2jhellas.gameserver.instancemanager.DayNightSpawnManager;
import com.l2jhellas.gameserver.instancemanager.DimensionalRiftManager;
import com.l2jhellas.gameserver.instancemanager.DuelManager;
import com.l2jhellas.gameserver.instancemanager.FourSepulchersManager;
import com.l2jhellas.gameserver.instancemanager.GrandBossManager;
import com.l2jhellas.gameserver.instancemanager.ItemsOnGroundManager;
import com.l2jhellas.gameserver.instancemanager.MercTicketManager;
import com.l2jhellas.gameserver.instancemanager.PetitionManager;
import com.l2jhellas.gameserver.instancemanager.QuestManager;
import com.l2jhellas.gameserver.instancemanager.RaidBossPointsManager;
import com.l2jhellas.gameserver.instancemanager.RaidBossSpawnManager;
import com.l2jhellas.gameserver.instancemanager.SiegeManager;
import com.l2jhellas.gameserver.instancemanager.SiegeReward;
import com.l2jhellas.gameserver.model.AutoChatHandler;
import com.l2jhellas.gameserver.model.AutoSpawnHandler;
import com.l2jhellas.gameserver.model.L2Manor;
import com.l2jhellas.gameserver.model.L2Multisell;
import com.l2jhellas.gameserver.model.L2PetDataTable;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.entity.Hero;
import com.l2jhellas.gameserver.model.entity.Hitman;
import com.l2jhellas.gameserver.model.entity.Olympiad;
import com.l2jhellas.gameserver.model.entity.QuizEvent;
import com.l2jhellas.gameserver.network.L2GameClient;
import com.l2jhellas.gameserver.network.L2GamePacketHandler;
import com.l2jhellas.gameserver.pathfinding.PathFinding;
import com.l2jhellas.gameserver.script.faenor.FaenorScriptEngine;
import com.l2jhellas.gameserver.scripting.CompiledScriptCache;
import com.l2jhellas.gameserver.scripting.L2ScriptEngineManager;
import com.l2jhellas.gameserver.taskmanager.TaskManager;
import com.l2jhellas.gameserver.util.DynamicExtension;
import com.l2jhellas.status.Status;
import com.l2jhellas.util.Util;
import com.l2jserver.mmocore.network.SelectorConfig;
import com.l2jserver.mmocore.network.SelectorThread;

public class GameServer
{
	private static final Logger _log = Logger.getLogger(GameServer.class.getName());
	private final SelectorThread<L2GameClient> _selectorThread;
	public static boolean _instanceOk = false;
	public static GameServer gameServer;
	private static ClanHallManager _cHManager;
	private final Shutdown _shutdownHandler;
	private final DoorTable _doorTable;
	private final SevenSigns _sevenSignsEngine;
	private final AutoChatHandler _autoChatHandler;
	private final AutoSpawnHandler _autoSpawnHandler;
	private final LoginServerThread _loginThread;
	
	private static Status _statusServer;
	@SuppressWarnings("unused")
	private final ThreadPoolManager _threadpools;
	
	public static final Calendar dateTimeServerStarted = Calendar.getInstance();
	
	public long getUsedMemoryMB()
	{
		return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576; // 1024 * 1024 = 1048576;
	}
	
	public SelectorThread<L2GameClient> getSelectorThread()
	{
		return _selectorThread;
	}
	
	public ClanHallManager getCHManager()
	{
		return _cHManager;
	}
	
	public GameServer() throws Exception
	{
		long serverLoadStart = System.currentTimeMillis();
		gameServer = this;
		_threadpools = ThreadPoolManager.getInstance();
		
		Util.printSection("Chache");
		// Call to load caches
		HtmCache.getInstance();
		CrestCache.getInstance();
		
		if (Config.USE_SAY_FILTER)
			new File(Config.DATAPACK_ROOT, "config/Others/ChatFilter.txt").createNewFile();
		
		Util.printSection("Script Engine");
		if (!Config.ALT_DEV_NO_SCRIPT)
		{
			L2ScriptEngineManager.getInstance();
		}
		
		// ===========================================================================
		Util.printSection("World");
		L2World.getInstance();
		MapRegionTable.getInstance();
		Announcements.getInstance();
		AutoAnnouncementHandler.getInstance();
		Universe.getInstance();
		if (!IdFactory.getInstance().isInitialized())
		{
			_log.info("Could not read object IDs from DB. Please Check Your Data.");
			throw new Exception("Could not initialize the ID factory");
		}
		StaticObjects.getInstance();
		TeleportLocationTable.getInstance();
		GameTimeController.getInstance();
		CharNameTable.getInstance();
		DuelManager.getInstance();
		
		Util.printSection("Skills");
		if (!SkillTable.getInstance().isInitialized())
		{
			_log.info("Could not find the extraced files. Please Check Your Data.");
			throw new Exception("Could not initialize the skill table");
		}
		SkillTreeTable.getInstance();
		SkillSpellbookTable.getInstance();
		NobleSkillTable.getInstance();
		HeroSkillTable.getInstance();
		_log.info("Skills: All skills loaded.");
		
		Util.printSection("Items");
		if (!ItemTable.getInstance().isInitialized())
		{
			_log.info("Could not find the extraced files. Please Check Your Data.");
			throw new Exception("Could not initialize the item table");
		}
		ArmorSetsTable.getInstance();
		ExtractableItemsData.getInstance();
		SummonItemsData.getInstance();
		if (Config.ALLOWFISHING)
			FishTable.getInstance();
		_log.info("Items: All items loaded.");
		
		Util.printSection("Npc");
		if (Config.ALLOW_NPC_WALKERS)
			NpcWalkerRoutesTable.getInstance().load();
		if (!NpcTable.getInstance().isInitialized())
		{
			_log.info("Could not find the extraced files. Please Check Your Data.");
			throw new Exception("Could not initialize the npc table");
		}
		_log.info("NPC: All Npc's loaded.");
		
		Util.printSection("Characters");
		if (Config.COMMUNITY_TYPE.equals("Full"))
		{
			ForumsBBSManager.getInstance();
		}
		ClanTable.getInstance();
		CharTemplateTable.getInstance();
		LevelUpData.getInstance();
		AccessLevels.getInstance();
		GmListTable.getInstance();
		if (!HennaTable.getInstance().isInitialized())
		{
			throw new Exception("Could not initialize the Henna Table");
		}
		if (!HennaTreeTable.getInstance().isInitialized())
		{
			throw new Exception("Could not initialize the Henna Tree Table");
		}
		if (!HelperBuffTable.getInstance().isInitialized())
		{
			throw new Exception("Could not initialize the Helper Buff Table");
		}
		BuffTemplateTable.getInstance();
		_log.info("Characters: All Data loaded.");
		
		Util.printSection("Geodata");
		GeoData.getInstance();
		if (Config.GEODATA == 2)
			PathFinding.getInstance();
		
		Util.printSection("Economy");
		TradeController.getInstance();
		L2Multisell.getInstance();
		_log.info("Multisell: loaded.");
		
		Util.printSection("Clan Halls");
		ClanHallManager.getInstance();
		AuctionManager.getInstance();
		_log.info("Clan Halls: loaded.");
		
		Util.printSection("Zone");
		ZoneData.getInstance();
		
		Util.printSection("Castles");
		CastleManager.getInstance();
		SiegeManager.getInstance();
		SiegeReward.getInstance();
		
		// util inside spawntable cause unknown problem -.-
		if (!Config.ALT_DEV_NO_SPAWNS)
		{
			SpawnTable.getInstance();
		}
		else
		{
			_log.info("Spawn: disable load.");
		}
		if (!Config.ALT_DEV_NO_RB)
		{
			RaidBossSpawnManager.getInstance();
			GrandBossManager.getInstance();
			RaidBossPointsManager.init();
		}
		else
		{
			_log.info("RaidBoss: disable load.");
		}
		DayNightSpawnManager.getInstance().notifyChangeMode();
		_log.info("Spawn Data: loaded.");
		
		Util.printSection("Dimensional Rift");
		DimensionalRiftManager.getInstance();
		
		Util.printSection("Misc");
		RecipeController.getInstance();
		EventDroplist.getInstance();
		AugmentationData.getInstance();
		MonsterRace.getInstance();
		MercTicketManager.getInstance();
		PetitionManager.getInstance();
		CursedWeaponsManager.getInstance();
		TaskManager.getInstance();
		L2PetDataTable.getInstance().loadPetsData();
		if (Config.ACCEPT_GEOEDITOR_CONN)
			GeoEditorListener.getInstance();
		if (Config.SAVE_DROPPED_ITEM)
			ItemsOnGroundManager.getInstance();
		if (Config.AUTODESTROY_ITEM_AFTER > 0 || Config.HERB_AUTO_DESTROY_TIME > 0)
			ItemsAutoDestroy.getInstance();
		
		Util.printSection("Manor");
		L2Manor.getInstance();
		CastleManorManager.getInstance();
		_log.info("Manor System: loaded.");
		
		Util.printSection("Boat");
		BoatManager.getInstance();
		_log.info("BoatManager: loaded.");
		
		Util.printSection("Doors");
		_doorTable = DoorTable.getInstance();
		_doorTable.parseData();
		try
		{
			_doorTable.getDoor(24190001).openMe();
			_doorTable.getDoor(24190002).openMe();
			_doorTable.getDoor(24190003).openMe();
			_doorTable.getDoor(24190004).openMe();
			_doorTable.getDoor(23180001).openMe();
			_doorTable.getDoor(23180002).openMe();
			_doorTable.getDoor(23180003).openMe();
			_doorTable.getDoor(23180004).openMe();
			_doorTable.getDoor(23180005).openMe();
			_doorTable.getDoor(23180006).openMe();
			_doorTable.checkAutoOpen();
		}
		catch (NullPointerException e)
		{
			_log.warning("You have errors on Door.csv!");
			if (Config.DEBUG)
				e.printStackTrace();
		}
		_log.info("Door Data: loaded.");
		
		System.gc();
		
		Util.printSection("Four Sepulchers");
		FourSepulchersManager.getInstance();
		
		Util.printSection("Seven Signs");
		_sevenSignsEngine = SevenSigns.getInstance();
		_autoSpawnHandler = AutoSpawnHandler.getInstance();
		_autoChatHandler = AutoChatHandler.getInstance();
		SevenSignsFestival.getInstance();
		_sevenSignsEngine.spawnSevenSignsNPC();// Spawn the Orators/Preachers if in the Seal Validation period.
		_log.info("Seven Signs: loaded.");
		
		Util.printSection("Olympiad System");
		Olympiad.getInstance();
		Hero.getInstance();
		_log.info("Olympiad System: loaded.");
		_log.info("");// avoid oly game started
		
		System.gc();
		
		Util.printSection("Handlers");
		ItemHandler.getInstance();
		SkillHandler.getInstance();
		AdminCommandHandler.getInstance();
		UserCommandHandler.getInstance();
		VoicedCommandHandler.getInstance();
		ChatHandler.getInstance();
		_log.info("AutoChatHandler : Loaded " + _autoChatHandler.size() + " handlers in total.");
		_log.info("AutoSpawnHandler : Loaded " + _autoSpawnHandler.size() + " handlers in total.");
		_log.info("VoicedCommandHandler: Loaded " + VoicedCommandHandler.getInstance().size() + " handlers in total.");
		_log.info("Handler Data: loaded.");
		
		Util.printSection("Quests");
		if (!Config.ALT_DEV_NO_QUESTS)
		{
			QuestManager.getInstance();
			QuestManager.getInstance().report();
		}
		else
			_log.info("Quests: Disabled by Config.");
		
		Util.printSection("Scripts");
		if (!Config.ALT_DEV_NO_SCRIPT)
		{
			try
			{
				_log.info("Loading Scripts");
				File scripts = new File(Config.DATAPACK_ROOT, "data/scripts.cfg");
				L2ScriptEngineManager.getInstance().executeScriptList(scripts);
			}
			catch (IOException ioe)
			{
				_log.severe("Failed to load!!");
			}
			try
			{
				CompiledScriptCache compiledScriptCache = L2ScriptEngineManager.getInstance().getCompiledScriptCache();
				if (compiledScriptCache == null)
				{
					_log.info("The Cache of scripts is disabled.");
				}
				else
				{
					compiledScriptCache.purge();
					
					if (compiledScriptCache.isModified())
					{
						compiledScriptCache.save();
						_log.info("The script hasn't updated the Cache.");
					}
					else
					{
						_log.info("The script has been updated Cache.");
					}
				}
			}
			catch (IOException e)
			{
				_log.log(Level.SEVERE, "Failed to load Cache Script.", e);
			}
			FaenorScriptEngine.getInstance();
		}
		else
		{
			_log.info("Scripts: Disabled by Config.");
		}
		
		Util.printSection("Customs");
		// we could add general custom config?
		PcColorTable.getInstance();
		MaxCheatersTable.getInstance();
		Hitman.start();
		VoteMain.load();
		PvpTable.getInstance();
		CharacterRankRewardTable.getInstance();
		RankRewardTable.getInstance();
		TopTable.getInstance();
		if (Config.ENABLED_QUIZ_EVENT)
			QuizEvent.getInstance();
		if (Config.ALLOW_AWAY_STATUS)
		{
			_log.info("Away System");
			AwayManager.getInstance();
		}
		BalanceLoad.loadBalance();
		if (ExternalConfig.RESTART_BY_TIME_OF_DAY)
		{
			_log.info("[Restart System]: Auto Restart System is Enabled ");
			Restart.getInstance().StartCalculationOfNextRestartTime();
		}
		else
		{
			_log.info("[Restart System]: Auto Restart System is Disabled ");
		}
		if (Config.MOD_ALLOW_WEDDING)
			CoupleManager.getInstance();
		
		_shutdownHandler = Shutdown.getInstance();
		Runtime.getRuntime().addShutdownHook(_shutdownHandler);
		
		Util.printSection("Dynamic Extensions");
		// initialize the dynamic extension loader
		try
		{
			DynamicExtension.getInstance();
		}
		catch (Exception ex)
		{
			_log.log(Level.WARNING, "Dynamic Extension initials", ex);
		}
		
		System.gc();
		
		Util.printSection("Info");
		_log.info("Maximum Numbers of Connected Players: " + Config.MAXIMUM_ONLINE_USERS);
		// maxMemory is the upper limit the jvm can use, totalMemory the size of
		// the current allocation pool, freeMemory the unused memory in the
		// allocation pool
		long freeMem = (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1048576; // 1024 * 1024 = 1048576;
		long totalMem = Runtime.getRuntime().maxMemory() / 1048576;
		_log.info("GameServer Initiated, Used Memory " + freeMem + " Mb from total " + totalMem + " Mb");
		_loginThread = LoginServerThread.getInstance();
		_loginThread.start();
		Util.printRuntimeInfo();
		
		Util.printSection("Game Server");
		_log.config("IdFactory: ObjectID's created: " + IdFactory.getInstance().size());
		
		final SelectorConfig sc = new SelectorConfig();
		sc.MAX_READ_PER_PASS = Config.MMO_MAX_READ_PER_PASS;
		sc.MAX_SEND_PER_PASS = Config.MMO_MAX_SEND_PER_PASS;
		sc.SLEEP_TIME = Config.MMO_SELECTOR_SLEEP_TIME;
		sc.HELPER_BUFFER_COUNT = Config.MMO_HELPER_BUFFER_COUNT;
		final L2GamePacketHandler gph = new L2GamePacketHandler();
		_selectorThread = new SelectorThread<L2GameClient>(sc, gph, gph, gph, null);
		InetAddress bindAddress = null;
		if (!Config.GAMESERVER_HOSTNAME.equals("*"))
		{
			try
			{
				bindAddress = InetAddress.getByName(Config.GAMESERVER_HOSTNAME);
			}
			catch (UnknownHostException e1)
			{
				_log.severe("WARNING: The GameServer bind address is invalid, using all avaliable IPs. Reason: " + e1.getMessage());
				if (Config.DEVELOPER)
					e1.printStackTrace();
			}
		}
		try
		{
			_selectorThread.openServerSocket(bindAddress, Config.PORT_GAME);
		}
		catch (IOException e)
		{
			_log.severe("FATAL: Failed to open server socket. Reason: " + e.getMessage());
			if (Config.DEVELOPER)
				e.printStackTrace();
			System.exit(1);
		}
		
		_selectorThread.start();
		_log.config("Maximum Users On: " + Config.MAXIMUM_ONLINE_USERS);
		long serverLoadEnd = System.currentTimeMillis();
		_log.info("Server Started at: " + ((serverLoadEnd - serverLoadStart) / 1000) + " seconds");
	}
	
	public static void main(String[] args) throws Exception
	{
		Server.serverMode = Server.MODE_GAMESERVER;
		// Local Constants
		final String LOG_FOLDER = "log"; // Name of folder for log file
		final String LOG_NAME = "./config/Others/log.cfg"; // Name of log file
		
		/*** Main ***/
		// Create directories
		File logFolder = new File(Config.DATAPACK_ROOT, LOG_FOLDER);
		logFolder.mkdir();
		File clans = new File(Config.DATAPACK_ROOT, "data/clans");
		clans.mkdir();
		File crests = new File(Config.DATAPACK_ROOT, "data/crests");
		crests.mkdir();
		File pathnode = new File(Config.DATAPACK_ROOT, "data/pathnode");
		pathnode.mkdir();
		File geodata = new File(Config.DATAPACK_ROOT, "data/geodata");
		geodata.mkdir();

		// Create input stream for log file -- or store file data into memory
		InputStream is = new FileInputStream(new File(LOG_NAME));
		LogManager.getLogManager().readConfiguration(is);
		is.close();
		
		Util.printSection("General Info");
		Util.printGeneralSystemInfo();
		
		Util.printSection("Configs");
		Config.load();
		ExternalConfig.load();
		_log.info("Configs Loaded.");
		
		Util.printSection("DataBase");
		L2DatabaseFactory.getInstance();
		_log.info("Database Loaded.");
		
		Util.printSection("Team");
		L2JHellasInfo.showInfo();
		
		gameServer = new GameServer();
		
		Util.printSection("Telnet");
		if (Config.IS_TELNET_ENABLED)
		{
			_statusServer = new Status(Server.serverMode);
			_statusServer.start();
		}
		else
		{
			_log.info("Telnet is disabled by config.");
		}
	}
}