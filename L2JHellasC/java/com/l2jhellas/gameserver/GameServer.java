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

import Extensions.IpCatcher;
import Extensions.AchievmentsEngine.AchievementsManager;
import Extensions.Balancer.BalanceLoad;
import Extensions.RankSystem.RankLoader;
import Extensions.Vote.VoteManager;
import Extensions.Vote.VoteRewardHopzone;
import Extensions.Vote.VoteRewardTopzone;

import com.L2JHellasInfo;
import com.PackRoot;
import com.l2jhellas.Config;
import com.l2jhellas.Server;
import com.l2jhellas.gameserver.cache.CrestCache;
import com.l2jhellas.gameserver.cache.HtmCache;
import com.l2jhellas.gameserver.communitybbs.Manager.ForumsBBSManager;
import com.l2jhellas.gameserver.controllers.GameTimeController;
import com.l2jhellas.gameserver.controllers.RecipeController;
import com.l2jhellas.gameserver.controllers.TradeController;
import com.l2jhellas.gameserver.datatables.EventDroplist;
import com.l2jhellas.gameserver.datatables.LevelUpData;
import com.l2jhellas.gameserver.datatables.csv.ExtractableItemsData;
import com.l2jhellas.gameserver.datatables.sql.BuffTemplateTable;
import com.l2jhellas.gameserver.datatables.sql.CharNameTable;
import com.l2jhellas.gameserver.datatables.sql.ClanTable;
import com.l2jhellas.gameserver.datatables.sql.HennaTreeTable;
import com.l2jhellas.gameserver.datatables.sql.ItemTable;
import com.l2jhellas.gameserver.datatables.sql.MapRegionTable;
import com.l2jhellas.gameserver.datatables.sql.NpcBufferSkillIdsTable;
import com.l2jhellas.gameserver.datatables.sql.PcColorTable;
import com.l2jhellas.gameserver.datatables.sql.PolymporphTable;
import com.l2jhellas.gameserver.datatables.sql.SpawnTable;
import com.l2jhellas.gameserver.datatables.xml.AdminData;
import com.l2jhellas.gameserver.datatables.xml.ArmorSetsData;
import com.l2jhellas.gameserver.datatables.xml.AugmentationData;
import com.l2jhellas.gameserver.datatables.xml.CharTemplateData;
import com.l2jhellas.gameserver.datatables.xml.DoorData;
import com.l2jhellas.gameserver.datatables.xml.ExperienceData;
import com.l2jhellas.gameserver.datatables.xml.FishTable;
import com.l2jhellas.gameserver.datatables.xml.HelperBuffData;
import com.l2jhellas.gameserver.datatables.xml.HennaData;
import com.l2jhellas.gameserver.datatables.xml.MultisellData;
import com.l2jhellas.gameserver.datatables.xml.NpcData;
import com.l2jhellas.gameserver.datatables.xml.NpcWalkerRoutesData;
import com.l2jhellas.gameserver.datatables.xml.PetData;
import com.l2jhellas.gameserver.datatables.xml.RecipeData;
import com.l2jhellas.gameserver.datatables.xml.SkillSpellbookData;
import com.l2jhellas.gameserver.datatables.xml.SkillTreeData;
import com.l2jhellas.gameserver.datatables.xml.StaticObjData;
import com.l2jhellas.gameserver.datatables.xml.SummonItemsData;
import com.l2jhellas.gameserver.datatables.xml.TeleportLocationData;
import com.l2jhellas.gameserver.datatables.xml.ZoneData;
import com.l2jhellas.gameserver.geodata.GeoData;
import com.l2jhellas.gameserver.geodata.geoeditorcon.GeoEditorListener;
import com.l2jhellas.gameserver.geodata.pathfinding.PathFinding;
import com.l2jhellas.gameserver.handler.AutoAnnouncementHandler;
import com.l2jhellas.gameserver.idfactory.IdFactory;
import com.l2jhellas.gameserver.instancemanager.AuctionManager;
import com.l2jhellas.gameserver.instancemanager.AwayManager;
import com.l2jhellas.gameserver.instancemanager.BoatManager;
import com.l2jhellas.gameserver.instancemanager.CastleManager;
import com.l2jhellas.gameserver.instancemanager.CastleManorManager;
import com.l2jhellas.gameserver.instancemanager.ClanHallManager;
import com.l2jhellas.gameserver.instancemanager.CoupleManager;
import com.l2jhellas.gameserver.instancemanager.CrownManager;
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
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.entity.Hero;
import com.l2jhellas.gameserver.model.entity.engines.EventHandlerCtf;
import com.l2jhellas.gameserver.model.entity.engines.EventHandlerTvT;
import com.l2jhellas.gameserver.model.entity.engines.Hitman;
import com.l2jhellas.gameserver.model.entity.engines.QuizEvent;
import com.l2jhellas.gameserver.model.entity.engines.ZodiacMain;
import com.l2jhellas.gameserver.model.entity.olympiad.Olympiad;
import com.l2jhellas.gameserver.model.entity.olympiad.OlympiadGameManager;
import com.l2jhellas.gameserver.network.L2GameClient;
import com.l2jhellas.gameserver.network.L2GamePacketHandler;
import com.l2jhellas.gameserver.scripting.L2ScriptEngineManager;
import com.l2jhellas.gameserver.skills.HeroSkillTable;
import com.l2jhellas.gameserver.skills.NobleSkillTable;
import com.l2jhellas.gameserver.skills.SkillTable;
import com.l2jhellas.gameserver.taskmanager.TaskManager;
import com.l2jhellas.mmocore.network.SelectorConfig;
import com.l2jhellas.mmocore.network.SelectorThread;
import com.l2jhellas.shield.antibot.AntiAfk;
import com.l2jhellas.shield.antibot.AntiBot;
import com.l2jhellas.status.Status;
import com.l2jhellas.util.DynamicExtension;
import com.l2jhellas.util.FloodProtector;
import com.l2jhellas.util.Util;
import com.l2jhellas.util.database.L2DatabaseFactory;
import com.l2jhellas.util.hexid.HexId;
import com.l2jhellas.util.ip.GameServerIP;
import com.l2jhellas.util.ip.IPConfigData;

public class GameServer
{
	private static final Logger _log = Logger.getLogger(GameServer.class.getName());
	public static boolean Loaded = false;
	private final SelectorThread<L2GameClient> _selectorThread;
	public static boolean _instanceOk = false;
	public static GameServer gameServer;
	private final LoginServerThread _loginThread;
	private static Status _statusServer;
	public static final Calendar dateTimeServerStarted = Calendar.getInstance();
	public Gui gui;


	public SelectorThread<L2GameClient> getSelectorThread()
	{
		return _selectorThread;
	}

	public GameServer() throws Exception
	{
		long serverLoadStart = System.currentTimeMillis();
		gameServer = this;
		ThreadPoolManager.getInstance();

		Util.printSection("Chache");
		// Call to load caches
		HtmCache.getInstance();
		CrestCache.load();

		Util.printSection("World");
		L2World.init();
		MapRegionTable.getInstance();
		Announcements.getInstance();
		AutoAnnouncementHandler.getInstance();
		AutoSpawnHandler.getInstance();
		DayNightSpawnManager.getInstance().notifyChangeMode();
		AutoChatHandler.getInstance();
		Universe.getInstance();
		FloodProtector.getInstance();
		StaticObjData.getInstance();
		TeleportLocationData.getInstance();
		GameTimeController.getInstance();
		CharNameTable.getInstance();
		DuelManager.getInstance();

		Util.printSection("Skills");
		if (!SkillTable.getInstance().isInitialized())
		{
			_log.log(Level.WARNING, getClass().getSimpleName() + ": Could not find the extraced files. Please Check Your Data.");
		}
		SkillTreeData.getInstance();
		SkillSpellbookData.getInstance();
		NobleSkillTable.getInstance();
		HeroSkillTable.getInstance();
		NpcBufferSkillIdsTable.getInstance();

		Util.printSection("Items");
		if (!ItemTable.getInstance().isInitialized())
		{
			_log.log(Level.WARNING, getClass().getSimpleName() + ": Could not find the extraced files. Please Check Your Data.");
		}
		ArmorSetsData.getInstance();
		ExtractableItemsData.getInstance();
		SummonItemsData.getInstance();
		if (Config.ALLOWFISHING)
		{
			FishTable.getInstance();
		}

		Util.printSection("Npc");
		NpcData.getInstance();
		if (Config.ALLOW_NPC_WALKERS)
		{
			NpcWalkerRoutesData.load();
		}

		Util.printSection("Characters");
		if (Config.COMMUNITY_TYPE.equals("Full"))
		{
			ForumsBBSManager.getInstance();
		}
		ExperienceData.getInstance();
		ClanTable.getInstance();
		CharTemplateData.getInstance();
		LevelUpData.getInstance();
		CrownManager.getInstance();
		AdminData.getInstance();
		HennaData.getInstance();
		HennaTreeTable.getInstance();
		HelperBuffData.getInstance();
		BuffTemplateTable.getInstance();

		Util.printSection("Geodata");
		GeoData.getInstance();
		if (Config.GEODATA == 2)
		{
			PathFinding.getInstance();
		}

		Util.printSection("Economy");
		TradeController.getInstance();
		MultisellData.getInstance();

		Util.printSection("Clan Halls");
		ClanHallManager.getInstance();
		AuctionManager.getInstance();

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
			_log.log(Level.INFO, getClass().getSimpleName() + ": Spawns disabled by config.");
		}
		if (!Config.ALT_DEV_NO_RB)
		{
			RaidBossSpawnManager.getInstance();
			GrandBossManager.getInstance();
			RaidBossPointsManager.init();
		}
		else
		{
			_log.log(Level.INFO, getClass().getSimpleName() + ": RaidBoss disabled by config.");
		}

		Util.printSection("Dimensional Rift");
		DimensionalRiftManager.getInstance();

		Util.printSection("Misc");
		RecipeData.getInstance();
		RecipeController.getInstance();
		EventDroplist.getInstance();
		AugmentationData.getInstance();
		MonsterRace.getInstance();
		MercTicketManager.getInstance();
		PetitionManager.getInstance();
		CursedWeaponsManager.getInstance();
		FourSepulchersManager.getInstance();
		PetData.getInstance().loadPetsData();
		if (Config.ACCEPT_GEOEDITOR_CONN)
		{
			GeoEditorListener.getInstance();
		}
		if (Config.SAVE_DROPPED_ITEM)
		{
			ItemsOnGroundManager.getInstance();
		}
		if (Config.AUTODESTROY_ITEM_AFTER > 0 || Config.HERB_AUTO_DESTROY_TIME > 0)
		{
			ItemsAutoDestroy.getInstance();
		}
		DoorData.getInstance();
		BoatManager.getInstance();

		Util.printSection("Tasks");
		TaskManager.getInstance();

		Util.printSection("Manor");
		L2Manor.getInstance();
		CastleManorManager.getInstance();

		Util.printSection("Seven Signs");
		SevenSignsFestival.getInstance();
		SevenSigns.getInstance().spawnSevenSignsNPC();// Spawn the Orators/Preachers if in the Seal Validation period.

		Util.printSection("Olympiad System");
		OlympiadGameManager.getInstance();
		Olympiad.getInstance();
		Hero.getInstance();

		Util.printSection("Scripts");
		QuestManager.getInstance();
		
		if (!Config.ALT_DEV_NO_SCRIPT)
		{
			try
			{
				final File scripts = new File("./data/scripts.cfg");
				L2ScriptEngineManager.getInstance().executeScriptList(scripts);
			}
			catch (IOException ioe)
			{
				_log.severe("Failed loading scripts.cfg, no script going to be loaded.");
			}
			QuestManager.getInstance().report();
		}
		else
		{
			_log.log(Level.INFO, getClass().getSimpleName() + ": Scripts are disabled by Config.");
		}


		Util.printSection("Customs");
		// we could add general custom config?
		AchievementsManager.getInstance();
		PcColorTable.getInstance();
		PolymporphTable.getInstance();
		Hitman.start();
		VoteManager.load();
		if (Config.ALLOW_TOPZONE_VOTE_REWARD)
			VoteRewardTopzone.LoadTopZone();
		if (Config.ALLOW_HOPZONE_VOTE_REWARD)
			VoteRewardHopzone.LoadHopZone();
		// Rank System.
		RankLoader.load();

		if (Config.ZODIAC_ENABLE)
		{
			ZodiacMain.ZodiacIn();
		}
		if (Config.ALLOW_CTF_AUTOEVENT)
		{
			new EventHandlerCtf().startHandler();
		}
		if (Config.TVT_ALLOW_AUTOEVENT)
		{
			new EventHandlerTvT().startHandler();
		}
		if (Config.ENABLED_QUIZ_EVENT)
		{
			QuizEvent.getInstance();
		}
		if (Config.ALLOW_AWAY_STATUS)
		{
			AwayManager.getInstance();
		}
		BalanceLoad.LoadEm();
		if (Config.ALLOW_SEQURITY_QUE)
		{
			AntiBot.getInstance();
		}
		if (Config.ALLOW_ANTI_AFK)
		{
			AntiAfk.getInstance();
		}
		if (Config.RESTART_BY_TIME_OF_DAY)
		{
			_log.log(Level.INFO, "Restart System: Auto Restart System is Enabled.");
			Restart.getInstance().StartCalculationOfNextRestartTime();
		}
		else
		{
			_log.log(Level.INFO, "Restart System: Auto Restart System is Disabled.");
		}
		if (Config.MOD_ALLOW_WEDDING)
		{
			CoupleManager.getInstance();
		}
		
		Util.printSection("Dynamic Extensions");
		// initialize the dynamic extension loader
		try
		{
			DynamicExtension.getInstance();
		}
		catch (Exception ex)
		{
			_log.log(Level.INFO, getClass().getSimpleName() + ": Dynamic Extension initials", ex);
			if (Config.DEVELOPER)
			{
				ex.printStackTrace();
			}
		}
		IpCatcher.ipsLoad();
		// run garbage collector
		System.gc();

		Util.printSection("Game Server Info");
		if (Config.ENABLE_GUI)
			gui = new Gui();
		Runtime.getRuntime().addShutdownHook(Shutdown.getInstance());
		_log.log(Level.INFO, getClass().getSimpleName() + ": IdFactory: ObjectID's created: " + IdFactory.getInstance().size());
		if (!IdFactory.getInstance().isInitialized())
		{
			_log.log(Level.INFO, getClass().getSimpleName() + ": Could not read object IDs from DB. Please Check Your Data.");
			_log.log(Level.WARNING, getClass().getSimpleName() + ": Could not initialize the ID factory.");
		}
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
				_log.log(Level.SEVERE, getClass().getSimpleName() + ": WARNING: The GameServer bind address is invalid, using all avaliable IPs. Reason: " + e1.getMessage());
				if (Config.DEVELOPER)
				{
					e1.printStackTrace();
				}
			}
		}
		try
		{
			_selectorThread.openServerSocket(bindAddress, Config.PORT_GAME);
		}
		catch (IOException e)
		{
			_log.log(Level.SEVERE, getClass().getSimpleName() + ": FATAL: Failed to open server socket. Reason: " + e.getMessage());
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
			System.exit(1);
		}

		_selectorThread.start();
		Util.printRuntimeInfo();
		_log.log(Level.INFO, getClass().getSimpleName() + ": Maximum Users On: " + Config.MAXIMUM_ONLINE_USERS);
		long serverLoadEnd = System.currentTimeMillis();
		_log.log(Level.INFO, getClass().getSimpleName() + ": Server Started in: " + ((serverLoadEnd - serverLoadStart) / 1000) + " seconds");

		Util.printSection("Telnet");
		if (Config.IS_TELNET_ENABLED)
		{
			_statusServer = new Status(Server.serverMode);
			_statusServer.start();
		}
		else
		{
			_log.log(Level.INFO, getClass().getSimpleName() + ": Telnet is disabled by config.");
		}

		_loginThread = LoginServerThread.getInstance();
		_loginThread.start();
	}

	public static void main(String[] args) throws Exception
	{
		Server.serverMode = Server.MODE_GAMESERVER;
		// Pack Root
		PackRoot.load();
		
		// Local Constants
		final String LOG_FOLDER = "log"; // Name of folder for log file
		final String LOG_NAME = "./config/Others/log.cfg"; // Name of log file

		if (Config.USE_SAY_FILTER)
		{
			new File(PackRoot.DATAPACK_ROOT, "config/Others/ChatFilter.txt").createNewFile();
		}
		;
		/*** Main ***/
		// Create directories
		File logFolder = new File(PackRoot.DATAPACK_ROOT, LOG_FOLDER);
		logFolder.mkdir();
		File clans = new File(PackRoot.DATAPACK_ROOT, "data/clans");
		clans.mkdir();
		File crests = new File(PackRoot.DATAPACK_ROOT, "data/crests");
		crests.mkdir();
		File pathnode = new File(PackRoot.DATAPACK_ROOT, "data/pathnode");
		pathnode.mkdir();
		File geodata = new File(PackRoot.DATAPACK_ROOT, "data/geodata");
		geodata.mkdir();
		File donates = new File(PackRoot.DATAPACK_ROOT, "data/donates");
		donates.mkdir();

		// Create input stream for log file -- or store file data into memory
		InputStream is = new FileInputStream(new File(LOG_NAME));
		LogManager.getLogManager().readConfiguration(is);
		is.close();
		
		// HexID part 1 (file)
		HexId.load();
		
		// IP Config
		Util.printSection("Network");
		IPConfigData.load();
		GameServerIP.load();

		Util.printSection("Configs");
		Config.load();

		Util.printSection("Script Engine");
		if (!Config.ALT_DEV_NO_SCRIPT)
		{
			L2ScriptEngineManager.getInstance();
		}

		Util.printSection("General Info");
		Util.printGeneralSystemInfo();

		Util.printSection("DataBase");
		L2DatabaseFactory.getInstance();
		
		// HexID part 2 (database must be load after driver) 
		HexId.storeDB();

		Util.printSection("Team");
		L2JHellasInfo.showInfo();

		gameServer = new GameServer();
	}
}