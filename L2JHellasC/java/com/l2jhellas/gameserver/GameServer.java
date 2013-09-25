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

import Extensions.IpCatcher;
import Extensions.AchievmentsEngine.AchievementsManager;
import Extensions.Balancer.BalanceLoad;
import Extensions.RankSystem.CharacterRankRewardTable;
import Extensions.RankSystem.PvpTable;
import Extensions.RankSystem.RankRewardTable;
import Extensions.RankSystem.TopTable;
import Extensions.RankSystem.Util.ServerSideImage;
import Extensions.Vote.VoteManager;

import com.L2JHellasInfo;
import com.l2jhellas.Config;
import com.l2jhellas.Server;
import com.l2jhellas.gameserver.cache.CrestCache;
import com.l2jhellas.gameserver.cache.HtmCache;
import com.l2jhellas.gameserver.communitybbs.Manager.ForumsBBSManager;
import com.l2jhellas.gameserver.datatables.EventDroplist;
import com.l2jhellas.gameserver.datatables.LevelUpData;
import com.l2jhellas.gameserver.datatables.csv.DoorTable;
import com.l2jhellas.gameserver.datatables.csv.ExtractableItemsData;
import com.l2jhellas.gameserver.datatables.csv.RecipeData;
import com.l2jhellas.gameserver.datatables.csv.StaticObjects;
import com.l2jhellas.gameserver.datatables.csv.SummonItemsData;
import com.l2jhellas.gameserver.datatables.sql.BuffTemplateTable;
import com.l2jhellas.gameserver.datatables.sql.CharNameTable;
import com.l2jhellas.gameserver.datatables.sql.ClanTable;
import com.l2jhellas.gameserver.datatables.sql.HennaTreeTable;
import com.l2jhellas.gameserver.datatables.sql.ItemTable;
import com.l2jhellas.gameserver.datatables.sql.MapRegionTable;
import com.l2jhellas.gameserver.datatables.sql.MaxCheatersTable;
import com.l2jhellas.gameserver.datatables.sql.NpcBufferSkillIdsTable;
import com.l2jhellas.gameserver.datatables.sql.NpcTable;
import com.l2jhellas.gameserver.datatables.sql.NpcWalkerRoutesTable;
import com.l2jhellas.gameserver.datatables.sql.PcColorTable;
import com.l2jhellas.gameserver.datatables.sql.SpawnTable;
import com.l2jhellas.gameserver.datatables.sql.TeleportLocationTable;
import com.l2jhellas.gameserver.datatables.xml.AdminTable;
import com.l2jhellas.gameserver.datatables.xml.ArmorSetsTable;
import com.l2jhellas.gameserver.datatables.xml.AugmentationData;
import com.l2jhellas.gameserver.datatables.xml.CharTemplateTable;
import com.l2jhellas.gameserver.datatables.xml.ExperienceData;
import com.l2jhellas.gameserver.datatables.xml.FishTable;
import com.l2jhellas.gameserver.datatables.xml.HelperBuffTable;
import com.l2jhellas.gameserver.datatables.xml.HennaTable;
import com.l2jhellas.gameserver.datatables.xml.L2Multisell;
import com.l2jhellas.gameserver.datatables.xml.PetDataTable;
import com.l2jhellas.gameserver.datatables.xml.SkillSpellbookTable;
import com.l2jhellas.gameserver.datatables.xml.SkillTreeTable;
import com.l2jhellas.gameserver.datatables.xml.ZoneData;
import com.l2jhellas.gameserver.geodata.GeoData;
import com.l2jhellas.gameserver.geodata.geoeditorcon.GeoEditorListener;
import com.l2jhellas.gameserver.geodata.pathfinding.PathFinding;
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
import com.l2jhellas.gameserver.instancemanager.RaidBossPointsManager;
import com.l2jhellas.gameserver.instancemanager.RaidBossSpawnManager;
import com.l2jhellas.gameserver.instancemanager.SiegeManager;
import com.l2jhellas.gameserver.instancemanager.SiegeReward;
import com.l2jhellas.gameserver.model.AutoChatHandler;
import com.l2jhellas.gameserver.model.AutoSpawnHandler;
import com.l2jhellas.gameserver.model.L2Manor;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.entity.Hero;
import com.l2jhellas.gameserver.model.entity.engines.Hitman;
import com.l2jhellas.gameserver.model.entity.engines.QuizEvent;
import com.l2jhellas.gameserver.model.entity.engines.ZodiacMain;
import com.l2jhellas.gameserver.model.entity.olympiad.Olympiad;
import com.l2jhellas.gameserver.model.entity.olympiad.OlympiadGameManager;
import com.l2jhellas.gameserver.network.L2GameClient;
import com.l2jhellas.gameserver.network.L2GamePacketHandler;
import com.l2jhellas.gameserver.scripting.CompiledScriptCache;
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
		CrestCache.getInstance();

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
		StaticObjects.getInstance();
		TeleportLocationTable.getInstance();
		GameTimeController.getInstance();
		CharNameTable.getInstance();
		DuelManager.getInstance();

		Util.printSection("Skills");
		if (!SkillTable.getInstance().isInitialized())
		{
			_log.log(Level.WARNING, getClass().getSimpleName() + ": Could not find the extraced files. Please Check Your Data.");
		}
		SkillTreeTable.getInstance();
		SkillSpellbookTable.getInstance();
		NobleSkillTable.getInstance();
		HeroSkillTable.getInstance();
		NpcBufferSkillIdsTable.getInstance();

		Util.printSection("Items");
		if (!ItemTable.getInstance().isInitialized())
		{
			_log.log(Level.WARNING, getClass().getSimpleName() + ": Could not find the extraced files. Please Check Your Data.");
		}
		ArmorSetsTable.getInstance();
		ExtractableItemsData.getInstance();
		SummonItemsData.getInstance();
		if (Config.ALLOWFISHING)
		{
			FishTable.getInstance();
		}

		Util.printSection("Npc");
		if (Config.ALLOW_NPC_WALKERS)
		{
			NpcWalkerRoutesTable.getInstance().load();
		}
		if (!NpcTable.getInstance().isInitialized())
		{
			_log.log(Level.WARNING, getClass().getSimpleName() + ": Could not find the extraced files. Please Check Your Data.");
		}

		Util.printSection("Characters");
		if (Config.COMMUNITY_TYPE.equals("Full"))
		{
			ForumsBBSManager.getInstance();
		}
		ExperienceData.getInstance();
		ClanTable.getInstance();
		CharTemplateTable.getInstance();
		LevelUpData.getInstance();
		CrownManager.getInstance();
		AdminTable.getInstance();
		HennaTable.getInstance();
		HennaTreeTable.getInstance();
		HelperBuffTable.getInstance();
		BuffTemplateTable.getInstance();

		Util.printSection("Geodata");
		GeoData.getInstance();
		if (Config.GEODATA == 2)
		{
			PathFinding.getInstance();
		}

		Util.printSection("Economy");
		TradeController.getInstance();
		L2Multisell.getInstance();

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
		EventDroplist.getInstance();
		AugmentationData.getInstance();
		MonsterRace.getInstance();
		MercTicketManager.getInstance();
		PetitionManager.getInstance();
		CursedWeaponsManager.getInstance();
		FourSepulchersManager.getInstance();
		PetDataTable.getInstance().loadPetsData();
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
		DoorTable.getInstance();
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

		Util.printSection("Handlers");
		AdminCommandHandler.getInstance();
		ChatHandler.getInstance();
		ItemHandler.getInstance();
		SkillHandler.getInstance();
		UserCommandHandler.getInstance();
		VoicedCommandHandler.getInstance();
		Util.printSection("Scripts");
		if (!Config.ALT_DEV_NO_SCRIPT)
		{
			try
			{
				_log.log(Level.INFO, getClass().getSimpleName() + ": Loading Scripts.");
				File scripts = new File(Config.DATAPACK_ROOT, "data/scripts.cfg");
				L2ScriptEngineManager.getInstance().executeScriptList(scripts);
			}
			catch (IOException ioe)
			{
				_log.log(Level.SEVERE, getClass().getSimpleName() + ": Failed to load!!" + ioe);
				if (Config.DEVELOPER)
				{
					ioe.printStackTrace();
				}
			}
			try
			{
				CompiledScriptCache compiledScriptCache = L2ScriptEngineManager.getInstance().getCompiledScriptCache();
				if (compiledScriptCache == null)
				{
					_log.log(Level.INFO, getClass().getSimpleName() + ": The Cache of scripts is disabled.");
				}
				else
				{
					compiledScriptCache.purge();

					if (compiledScriptCache.isModified())
					{
						compiledScriptCache.save();
						_log.log(Level.INFO, getClass().getSimpleName() + ": The script hasn't updated the Cache.");
					}
					else
					{
						_log.log(Level.INFO, getClass().getSimpleName() + ": The script has been updated Cache.");
					}
				}
			}
			catch (IOException e)
			{
				_log.log(Level.SEVERE, getClass().getSimpleName() + ": Failed to load Cache Script.", e);
				if (Config.DEVELOPER)
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			_log.log(Level.INFO, getClass().getSimpleName() + ": Scripts are disabled by Config.");
		}

		Util.printSection("Customs");
		// we could add general custom config?
		AchievementsManager.getInstance();
		PcColorTable.getInstance();
		MaxCheatersTable.getInstance();
		Hitman.start();
		VoteManager.load();
		// Rank System.
		PvpTable.getInstance();
		CharacterRankRewardTable.getInstance();
		RankRewardTable.getInstance();
		TopTable.getInstance();
		ServerSideImage.getInstance().load();

		if (Config.ZODIAC_ENABLE)
		{
			ZodiacMain.ZodiacIn();
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
		// Local Constants
		final String LOG_FOLDER = "log"; // Name of folder for log file
		final String LOG_NAME = "./config/Others/log.cfg"; // Name of log file

		if (Config.USE_SAY_FILTER)
		{
			new File(Config.DATAPACK_ROOT, "config/Others/ChatFilter.txt").createNewFile();
		}
		;
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
		File donates = new File(Config.DATAPACK_ROOT, "data/donates");
		donates.mkdir();

		// Create input stream for log file -- or store file data into memory
		InputStream is = new FileInputStream(new File(LOG_NAME));
		LogManager.getLogManager().readConfiguration(is);
		is.close();
		
		// IP Config
		Util.printSection("Network");
		IPConfigData.load();
		
		Util.printSection("Configs");
		Config.load();
		_log.log(Level.INFO, "Configs Loaded.");

		Util.printSection("Script Engine");
		if (!Config.ALT_DEV_NO_SCRIPT)
		{
			L2ScriptEngineManager.getInstance();
		}

		Util.printSection("General Info");
		Util.printGeneralSystemInfo();

		Util.printSection("DataBase");
		L2DatabaseFactory.getInstance();

		Util.printSection("Team");
		L2JHellasInfo.showInfo();

		gameServer = new GameServer();
	}
}