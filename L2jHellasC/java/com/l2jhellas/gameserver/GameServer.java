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
import Extensions.Vote.VoteMain;

import com.l2jhellas.Config;
import com.l2jhellas.ExternalConfig;
import com.l2jhellas.L2DatabaseFactory;
import com.l2jhellas.Server;
import com.l2jhellas.gameserver.cache.CrestCache;
import com.l2jhellas.gameserver.cache.HtmCache;
import com.l2jhellas.gameserver.communitybbs.Manager.ForumsBBSManager;
import com.l2jhellas.gameserver.datatables.ArmorSetsTable;
import com.l2jhellas.gameserver.datatables.AugmentationData;
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
import com.l2jhellas.gameserver.instancemanager.ItemsOnGroundManager;
import com.l2jhellas.gameserver.instancemanager.MercTicketManager;
import com.l2jhellas.gameserver.instancemanager.PetitionManager;
import com.l2jhellas.gameserver.instancemanager.QuestManager;
import com.l2jhellas.gameserver.instancemanager.RaidBossPointsManager;
import com.l2jhellas.gameserver.instancemanager.RaidBossSpawnManager;
import com.l2jhellas.gameserver.instancemanager.SiegeManager;
import com.l2jhellas.gameserver.model.AutoChatHandler;
import com.l2jhellas.gameserver.model.AutoSpawnHandler;
import com.l2jhellas.gameserver.model.L2Manor;
import com.l2jhellas.gameserver.model.L2PetDataTable;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.entity.Hero;
import com.l2jhellas.gameserver.model.entity.Hitman;
import com.l2jhellas.gameserver.model.entity.Olympiad;
import com.l2jhellas.gameserver.network.L2GameClient;
import com.l2jhellas.gameserver.network.L2GamePacketHandler;
import com.l2jhellas.gameserver.pathfinding.geonodes.GeoPathFinding;
import com.l2jhellas.gameserver.script.faenor.FaenorScriptEngine;
import com.l2jhellas.gameserver.scripting.CompiledScriptCache;
import com.l2jhellas.gameserver.scripting.L2ScriptEngineManager;
import com.l2jhellas.gameserver.taskmanager.TaskManager;
import com.l2jhellas.gameserver.util.DynamicExtension;
import com.l2jhellas.status.Status;
import com.l2jserver.mmocore.network.SelectorConfig;
import com.l2jserver.mmocore.network.SelectorThread;

/**
 * This class ...
 * 
 * @version $Revision: 1.29.2.15.2.19 $ $Date: 2005/04/05 19:41:23 $
 */
public class GameServer
{
	private static final Logger _log = Logger.getLogger(GameServer.class.getName());
	private final SelectorThread<L2GameClient> _selectorThread;
	private final SkillTable _skillTable;
	private final ItemTable _itemTable;
	private final NpcTable _npcTable;
	private final HennaTable _hennaTable;
	private final IdFactory _idFactory;
	public static boolean _instanceOk = false;
	public static GameServer gameServer;
	private static ClanHallManager _cHManager;
	private final Shutdown _shutdownHandler;
	private final DoorTable _doorTable;
	private final SevenSigns _sevenSignsEngine;
	private final AutoChatHandler _autoChatHandler;
	private final AutoSpawnHandler _autoSpawnHandler;
	private final LoginServerThread _loginThread;
	private final HelperBuffTable _helperBuffTable;

	private static Status _statusServer;
	@SuppressWarnings("unused")
	private final ThreadPoolManager _threadpools;
	
	public static final Calendar dateTimeServerStarted = Calendar.getInstance();
	
	public long getUsedMemoryMB()
	{
		return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576; // 1024
																									// *
																									// 1024
																									// =
																									// 1048576;
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
		_log.finest("used mem:" + getUsedMemoryMB() + "MB");

		_idFactory = IdFactory.getInstance();
		if (!_idFactory.isInitialized())
		{
			_log.severe("Did not found the files, please check!!.");
			throw new Exception("Failled to initialize ID factory");
		}
		
		_threadpools = ThreadPoolManager.getInstance();

		new File(Config.DATAPACK_ROOT, "data/clans").mkdirs();
		new File(Config.DATAPACK_ROOT, "data/crests").mkdirs();

		// Show L2J-Hellas Info
		L2JHellasInfo.showInfo();
		
		// load script engines
		L2ScriptEngineManager.getInstance();
		
		// start game time control early
		GameTimeController.getInstance();

		// keep the references of Singletons to prevent garbage collection
		CharNameTable.getInstance();

		_itemTable = ItemTable.getInstance();
		if (!_itemTable.isInitialized())
		{
			_log.severe("Not found the files extracted. Please fix.");
			throw new Exception("Failled to initialize *items*");
		}

		ExtractableItemsData.getInstance();
		SummonItemsData.getInstance();
		TradeController.getInstance();
		_skillTable = SkillTable.getInstance();
		if (!_skillTable.isInitialized())
		{
			_log.severe("Not found the files extracted. Please fix.");
			throw new Exception("Failled to initialize *skills*");
		}
		
		if (Config.ALLOW_NPC_WALKERS)
			NpcWalkerRoutesTable.getInstance().load();
		RecipeController.getInstance();
		SkillTreeTable.getInstance();
		ArmorSetsTable.getInstance();
		FishTable.getInstance();
		L2PetDataTable.getInstance().loadPetsData();
		SkillSpellbookTable.getInstance();
		CharTemplateTable.getInstance();
		NobleSkillTable.getInstance();
		HeroSkillTable.getInstance();
		PcColorTable.getInstance();
		
		// Call to load caches
		if (Config.USE_SAY_FILTER)
			new File("config/Main/ChatFilter.txt").createNewFile();
		new File("pathnode").mkdirs();
		HtmCache.getInstance();
		CrestCache.getInstance();
		ClanTable.getInstance();
		_npcTable = NpcTable.getInstance();

		if (!_npcTable.isInitialized())
		{
			_log.severe("Not found the files extracted. Please fix..");
			throw new Exception("Not able to initialize the *npcs*");
		}

		_hennaTable = HennaTable.getInstance();

		if (!_hennaTable.isInitialized())
			throw new Exception("Not able to initialize the *Dyes*");

		HennaTreeTable.getInstance();

		if (!_hennaTable.isInitialized())
			throw new Exception("Not able to initialize the *Dyes*");
		
		_helperBuffTable = HelperBuffTable.getInstance();
		
		if (!_helperBuffTable.isInitialized())
			throw new Exception("Not able to initialize the o *Buffer Helper*");
		
		GeoData.getInstance();
		if (Config.GEODATA == 2)
			GeoPathFinding.getInstance();
		
		CastleManager.getInstance();
		SiegeManager.getInstance();
		
		// Load clan hall data before zone data
		_cHManager = ClanHallManager.getInstance();
		
		TeleportLocationTable.getInstance();
		LevelUpData.getInstance();
		L2World.getInstance();
		MaxCheatersTable.getInstance();
		ZoneData.getInstance();
		RaidBossPointsManager.init();
		SpawnTable.getInstance();
		RaidBossSpawnManager.getInstance();
		DayNightSpawnManager.getInstance().notifyChangeMode();
		DimensionalRiftManager.getInstance();
		Announcements.getInstance();
		MapRegionTable.getInstance();
		EventDroplist.getInstance();
		L2Manor.getInstance();

		/** Load Manager */
		CastleManager.getInstance();
		SiegeManager.getInstance();
		AuctionManager.getInstance();
		BoatManager.getInstance();
		CastleManorManager.getInstance();
		MercTicketManager.getInstance();
		PetitionManager.getInstance();
		QuestManager.getInstance();
		Hitman.start();
		VoteMain.load();
		
		try
		{
			_log.info("Loading Scripts");
			File scripts = new File(Config.DATAPACK_ROOT + "/data/scripts.cfg");
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
		QuestManager.getInstance().report();
		AugmentationData.getInstance();
		if (Config.SAVE_DROPPED_ITEM)
			ItemsOnGroundManager.getInstance();
		if (Config.AUTODESTROY_ITEM_AFTER > 0 || Config.HERB_AUTO_DESTROY_TIME > 0)
			ItemsAutoDestroy.getInstance();
		MonsterRace.getInstance();

		_doorTable = DoorTable.getInstance();
		_doorTable.parseData();
		StaticObjects.getInstance();

		_sevenSignsEngine = SevenSigns.getInstance();
		SevenSignsFestival.getInstance();
		_autoSpawnHandler = AutoSpawnHandler.getInstance();
		_autoChatHandler = AutoChatHandler.getInstance();
		
		// Spawn the Orators/Preachers if in the Seal Validation period.
		_sevenSignsEngine.spawnSevenSignsNPC();
		
		
		if(ExternalConfig.RESTART_BY_TIME_OF_DAY)
		{
		_log.info("[Restart System]: Auto Restart System is Enabled ");
		Restart.getInstance().StartCalculationOfNextRestartTime();
		}
		else
		{
		_log.info("[Restart System]: Auto Restart System is Disabled ");
		}
	    System.gc();
		
		// Engines
		Olympiad.getInstance();
		Hero.getInstance();
		FaenorScriptEngine.getInstance();
		CursedWeaponsManager.getInstance();

		_log.config("AutoChatHandler: Loaded " + _autoChatHandler.size() + " handlers in total.");
		_log.config("AutoSpawnHandler: Loaded " + _autoSpawnHandler.size() + " handlers in total.");

		// Handlers
		AdminCommandHandler.getInstance();
		ChatHandler.getInstance();
		ItemHandler.getInstance();
		SkillHandler.getInstance();
		UserCommandHandler.getInstance();
		VoicedCommandHandler.getInstance();
		AutoAnnouncementHandler.getInstance();
		
		if (Config.MOD_ALLOW_WEDDING)
			CoupleManager.getInstance();
		
		TaskManager.getInstance();
		GmListTable.getInstance();
		
		Universe.getInstance();

		if (Config.ACCEPT_GEOEDITOR_CONN)
			GeoEditorListener.getInstance();

		_shutdownHandler = Shutdown.getInstance();
		Runtime.getRuntime().addShutdownHook(_shutdownHandler);
		
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
			_log.warning("Contem erros on Door.csv. Check door.csv");
			if (Config.DEBUG)
				e.printStackTrace();
		}
		ForumsBBSManager.getInstance();
		_log.config("IdFactory: ObjectID's criados: " + IdFactory.getInstance().size());
		
		// initialize the dynamic extension loader
		try
		{
			DynamicExtension.getInstance();
		}
		catch (Exception ex)
		{
			_log.log(Level.WARNING, "Dynamic Extension initials", ex);
		}
		
		if (Config.ALLOW_AWAY_STATUS)
		{
			_log.info("Away System");
			AwayManager.getInstance();
		}
		// Balancer
		BalanceLoad.loadBalance();

		System.gc();
		// maxMemory is the upper limit the jvm can use, totalMemory the size of
		// the current allocation pool, freeMemory the unused memory in the
		// allocation pool
		long freeMem = (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1048576; // 1024
																																				// *
																																				// 1024
																																				// =
																																				// 1048576;
		long totalMem = Runtime.getRuntime().maxMemory() / 1048576;
		_log.info("GameServer Initiated, Used Memory " + freeMem + " Mb from total " + totalMem + " Mb");
		_loginThread = LoginServerThread.getInstance();
		_loginThread.start();
		
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
		final String LOG_NAME = "./log.cfg"; // Name of log file

		/*** Main ***/
		// Create log folder
		File logFolder = new File(Config.DATAPACK_ROOT, LOG_FOLDER);
		logFolder.mkdir();

		// Create input stream for log file -- or store file data into memory
		InputStream is = new FileInputStream(new File(LOG_NAME));
		LogManager.getLogManager().readConfiguration(is);
		is.close();

		// Initialize config
		Config.load();
		ExternalConfig.load();
		L2DatabaseFactory.getInstance();
		gameServer = new GameServer();
		
		if (Config.IS_TELNET_ENABLED)
		{
			_statusServer = new Status(Server.serverMode);
			_statusServer.start();
		}
		else
		{
			System.out.println("Telnet Has Been Disabled.");
		}
		System.out.println("Have fun using L2JHellas Report any bugs to us!");
	}
}