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
package com.l2jhellas;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastList;
import javolution.util.FastMap;
import Extensions.RankSystem.Rank;

import com.l2jhellas.util.L2Properties;
import com.l2jhellas.util.StringUtil;

public final class ExternalConfig
{
	private static Logger _log = Logger.getLogger(ExternalConfig.class.getName());
	// --------------------------------------------------
	// File Definitions
	// --------------------------------------------------
	public static final String Rank_Config = "./config/Mods/Rank PvP System.ini";
	public static final String Vote_Config = "./config/Mods/Vote System.ini";
	public static final String Automation_Config = "./config/Mods/Automatation.ini";
	public static final String Smart_CB = "./config/Mods/Smart Community Board.ini";
	public static final String Custom_Npc = "./config/Mods/Custom Npcs.ini";

	public static final String RCON_CONFIG_FILE = "./config/Admin/Rcon.ini";
	// --------------------------------------------------
	// Variable Definitions
	// --------------------------------------------------

	/* RCON_FILE */
	public static boolean ENABLED_RCON;
	public static int RCON_PORT;
	public static String RCON_PASSWORD;

	/* Smart Community Board Definitions */
	public static int TOP_PLAYER_ROW_HEIGHT;
	public static int TOP_PLAYER_RESULTS;
	public static int RAID_LIST_ROW_HEIGHT;
	public static int RAID_LIST_RESULTS;
	public static boolean RAID_LIST_SORT_ASC;
	public static boolean ALLOW_REAL_ONLINE_STATS;
	public static String ALLOW_CLASS_MASTERSCB;
	public static String CLASS_MASTERS_PRICECB;
	public static int CLASS_MASTERS_PRICE_ITEMCB;
	public static int[] CLASS_MASTERS_PRICE_LISTCB = new int[4];
	public static ArrayList<Integer> ALLOW_CLASS_MASTERS_LISTCB = new ArrayList<>();

	/* Automation Config System */
	public static boolean ALLOW_SERVER_RESTART_COMMAND;
	public static int VOTES_NEEDED_FOR_RESTART;
	public static boolean RESTART_BY_TIME_OF_DAY;
	public static int RESTART_SECONDS;
	public static String[] RESTART_INTERVAL_BY_TIME_OF_DAY;
	public static boolean LOGIN_SERVER_SCHEDULE_RESTART;
	public static long LOGIN_SERVER_SCHEDULE_RESTART_TIME;
	public static boolean ALLOW_PRIVATE_ANTI_BOT;
	public static boolean ALLOW_SEQURITY_QUE;
	public static boolean ALLOW_ANTI_AFK;
	public static int ENCHANT_BOT_CHANCE;
	public static int MINUTES_AFK_PLAYERS;
	public static int SECURITY_QUE_TIME;

	/* CUSTOM PVP/RANK/REWARD SYSTEM */
	public static boolean RANK_PVP_SYSTEM_ENABLED;
	public static int LEGAL_KILL_MIN_LVL;
	public static boolean LEGAL_COUNTER_ALTT_ENABLED;
	public static boolean LEGAL_KILL_FOR_PK_KILLER_ENABLED;
	public static boolean LEGAL_KILL_FOR_INNOCENT_KILL_ENABLED;
	public static int PROTECTION_TIME_RESET;
	public static int LEGAL_KILL_PROTECTION;
	public static int DAILY_LEGAL_KILL_PROTECTION;

	// PvP Reward:
	public static boolean PVP_REWARD_ENABLED;
	public static int PVP_REWARD_ID;
	public static int PVP_REWARD_AMOUNT;
	public static int PVP_REWARD_MIN_LVL;
	public static boolean PVP_REWARD_FOR_PK_KILLER_ENABLED;
	public static boolean PVP_REWARD_FOR_INNOCENT_KILL_ENABLED;

	// Ranks:
	/** FastMap &lt;rankId, Rank&gt; - store all Ranks as Rank objects. */
	public static FastMap<Integer, Rank> RANKS = new FastMap<Integer, Rank>();

	public static boolean RANKS_ENABLED;
	public static int RANK_POINTS_MIN_LVL;
	public static boolean RANK_POINTS_CUT_ENABLED;
	public static boolean RANK_REWARD_ENABLED;

	public static boolean RANK_POINTS_DOWN_COUNT_ENABLED;
	public static FastList<Integer> RANK_POINTS_DOWN_AMOUNTS = new FastList<Integer>();

	public static boolean RANK_SHOUT_INFO_ON_KILL_ENABLED;
	public static boolean RANK_SHOUT_BONUS_INFO_ON_KILL_ENABLED;
	public static boolean RANK_REWARD_FOR_PK_KILLER_ENABLED;
	public static boolean RANK_REWARD_FOR_INNOCENT_KILL_ENABLED;

	public static boolean RANK_POINTS_REWARD_ENABLED;

	// War Kills:
	public static boolean WAR_KILLS_ENABLED;
	public static double WAR_RANK_POINTS_RATIO;

	// Combo Kill:
	public static boolean COMBO_KILL_ENABLED;
	public static boolean COMBO_KILL_PROTECTION_WITH_LEGAL_KILL_ENABLED;
	public static boolean COMBO_KILL_PROTECTION_NO_REPEAT_ENABLED;

	public static FastMap<Integer, String> COMBO_KILL_LOCAL_AREA_MESSAGES = new FastMap<Integer, String>();
	public static FastMap<Integer, String> COMBO_KILL_GLOBAL_AREA_MESSAGES = new FastMap<Integer, String>();

	public static boolean COMBO_KILL_ALT_MESSAGES_ENABLED;
	public static String COMBO_KILL_ALT_MESSAGE;
	public static int COMBO_KILL_ALT_GLOBAL_MESSAGE_LVL;

	public static boolean COMBO_KILL_DEFEAT_MESSAGE_ENABLED;
	public static int COMBO_KILL_DEFEAT_MESSAGE_MIN_LVL;
	public static String COMBO_KILL_DEFEAT_MESSAGE;

	public static int COMBO_KILL_RESETER;
	public static boolean COMBO_KILL_RANK_POINTS_RATIO_ENABLED;
	public static FastMap<Integer, Double> COMBO_KILL_RANK_POINTS_RATIO = new FastMap<Integer, Double>();

	public static boolean COMBO_KILL_ON_EVENTS_ENABLED;

	// Title & Nick Color:
	public static boolean NICK_COLOR_ENABLED;
	public static boolean TITLE_COLOR_ENABLED;

	// Zones:
	public static FastList<Integer> ALLOWED_ZONES_IDS = new FastList<Integer>();
	public static FastList<Integer> RESTRICTED_ZONES_IDS = new FastList<Integer>();
	public static FastList<Integer> DEATH_MANAGER_RESTRICTED_ZONES_IDS = new FastList<Integer>();
	public static FastMap<Integer, Double> RANK_POINTS_BONUS_ZONES_IDS = new FastMap<Integer, Double>();

	// pvpinfo command, death manager:
	public static boolean PVP_INFO_COMMAND_ENABLED;
	public static boolean PVP_INFO_USER_COMMAND_ENABLED;
	public static int PVP_INFO_USER_COMMAND_ID;

	public static boolean PVP_INFO_COMMAND_ON_DEATH_ENABLED;
	public static boolean DEATH_MANAGER_DETAILS_ENABLED;
	public static boolean DEATH_MANAGER_SHOW_ITEMS_ENABLED;

	public static boolean TOTAL_KILLS_IN_SHOUT_ENABLED;
	public static boolean TOTAL_KILLS_IN_PVPINFO_ENABLED;
	public static boolean TOTAL_KILLS_ON_ME_IN_PVPINFO_ENABLED;

	// Anti-Farm:
	public static boolean ANTI_FARM_CLAN_ALLY_ENABLED;
	public static boolean ANTI_FARM_PARTY_ENABLED;
	public static boolean ANTI_FARM_IP_ENABLED;

	// Community Board:
	public static boolean COMMUNITY_BOARD_TOP_LIST_ENABLED;
	public static long COMMUNITY_BOARD_TOP_LIST_IGNORE_TIME_LIMIT;

	// Database:
	public static long PVP_TABLE_UPDATE_INTERVAL;
	public static long TOP_TABLE_UPDATE_INTERVAL;

	public static boolean DATABASE_CLEANER_ENABLED;
	public static long DATABASE_CLEANER_REPEAT_TIME;

	// Image:
	public static int IMAGE_PREFIX;

	// Button style:
	public static final String BUTTON_UP = "Button_DF_Calculator";
	public static final String BUTTON_DOWN = "Button_DF_Calculator_Down";
	public static final String BUTTON_W = "65";
	public static final String BUTTON_H = "20";

	/* Vote Manager */
	public static String VOTE_LINK_HOPZONE;
	public static String VOTE_LINK_TOPZONE;
	public static int VOTE_REWARD_ID1;
	public static int VOTE_REWARD_ID2;
	public static int VOTE_REWARD_ID3;
	public static int VOTE_REWARD_ID4;
	public static int VOTE_REWARD_AMOUNT1;
	public static int VOTE_REWARD_AMOUNT2;
	public static int VOTE_REWARD_AMOUNT3;
	public static int VOTE_REWARD_AMOUNT4;
	public static int SECS_TO_VOTE;
	public static int EXTRA_REW_VOTE_AM;

	/* Npc Config */
	public static boolean SHOW_NPC_CREST;
	public static boolean ALLOW_CLASS_MASTER;
	public static boolean ALLOW_REMOTE_CLASS_MASTER;
	public static boolean ALLOW_ACCOUNT_MANAGER;
	public static String EMAIL_USER;
	public static String EMAIL_PASS;
	public static boolean PROTECTOR_PLAYER_PK;
	public static boolean PROTECTOR_PLAYER_PVP;
	public static int PROTECTOR_RADIUS_ACTION;
	public static int PROTECTOR_SKILLID;
	public static int PROTECTOR_SKILLLEVEL;
	public static int PROTECTOR_SKILLTIME;
	public static boolean SEND_MESSAGE;
	public static String PROTECTOR_MESSAGE;
	public static boolean NPCBUFFER_FEATURE_ENABLED;
	public static int NPCBUFFER_STATIC_BUFF_COST;
	public static boolean NPC_NOBLES_ENABLE;
	public static int NPC_NOBLESS_ID;
	public static int NPC_NOBLESS_QUANTITY;
	public static int BOSS_RESPAWN_NPC_ID;
	public static int[] BOSS_RESPAWN_INFO;
	public static boolean RAID_INFO_SHOW_TIME;

	/**
	 * This class initializes all global variables for configuration.<br>
	 * If the key doesn't appear in properties file, a default value is set by
	 * this class.
	 */
	public static void load()
	{
		// Load Rank PvP System Config file (if exists)
		final File cc = new File(Rank_Config);
		try
		{
			InputStream is = new FileInputStream(cc);
			L2Properties ccSettings = new L2Properties();
			ccSettings.load(is);

			/* CUSTOM PVP/RANK/REWARD SYSTEM */
			RANK_PVP_SYSTEM_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPvpSystemEnabled", "false"));
			LEGAL_COUNTER_ALTT_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("LegalCounterAltTEnabled", "false"));
			PVP_REWARD_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("PvpRewardEnabled", "false"));

			DATABASE_CLEANER_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("DatabaseCleanerEnabled", "false"));
			DATABASE_CLEANER_REPEAT_TIME = Integer.parseInt(ccSettings.getProperty("DatabaseCleanerRepeatTime", "0"));
			if (DATABASE_CLEANER_REPEAT_TIME <= 0)
			{
				DATABASE_CLEANER_ENABLED = false;
			}
			else
			{
				DATABASE_CLEANER_REPEAT_TIME *= 86400000;
			}

			PVP_REWARD_ID = Integer.parseInt(ccSettings.getProperty("PvpRewardId", "6392"));
			PVP_REWARD_AMOUNT = Integer.parseInt(ccSettings.getProperty("PvpRewardAmmount", "1"));
			PVP_REWARD_MIN_LVL = Integer.parseInt(ccSettings.getProperty("PvpRewardMinLvl", "76"));
			PVP_REWARD_FOR_PK_KILLER_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("PvpRewardForPkKillerEnabled", "true"));
			PVP_REWARD_FOR_INNOCENT_KILL_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("PvpRewardForInnocentKillEnabled", "false"));
			RANKS_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RanksEnabled", "false"));
			RANK_POINTS_MIN_LVL = Integer.parseInt(ccSettings.getProperty("RankPointsMinLvl", "76"));
			RANK_POINTS_CUT_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPointsCutEnabled", "true"));

			RANK_REWARD_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankRewardEnabled", "false"));

			// set rank's:
			String id1[] = ccSettings.getProperty("RankNames", "").split(",");
			String id2[] = ccSettings.getProperty("RankMinPoints", "").split(",");
			String id3[] = ccSettings.getProperty("RankPointsForKill", "").split(",");
			String id4[] = ccSettings.getProperty("RankRewardIds", "").split(",");
			String id5[] = ccSettings.getProperty("RankRewardAmounts", "").split(",");
			String id6[] = ccSettings.getProperty("NickColors", "").split(",");
			String id7[] = ccSettings.getProperty("TitleColors", "").split(",");

			if (RANK_PVP_SYSTEM_ENABLED || RANK_REWARD_ENABLED || RANKS_ENABLED)
			{
				if (id1.length != id2.length || id1.length != id3.length || id1.length != id4.length || id1.length != id5.length || id1.length != id6.length || id1.length != id7.length)
				{
					_log.info("ERROR: Rank PvP System Config: Arrays sizes should be the same!");

					_log.info("RANK_NAMES          :" + id1.length);
					_log.info("RANK_MIN_POINTS     :" + id2.length);
					_log.info("RANK_POINTS_FOR_KILL:" + id3.length);
					_log.info("RANK_REWARD_IDS     :" + id4.length);
					_log.info("RANK_REWARD_AMOUNTS :" + id5.length);
					_log.info("RANK_NICK_COLORS    :" + id6.length);
					_log.info("RANK_TITLE_COLORS   :" + id7.length);

				}
				else
				{
					for (int i = 0; i < id1.length; i++)
					{
						Rank rank = new Rank();

						rank.setId(id1.length - i);
						rank.setName(id1[i]);
						rank.setMinPoints(Long.parseLong(id2[i]));
						rank.setPointsForKill(Integer.parseInt(id3[i]));
						rank.setRewardId(Integer.parseInt(id4[i]));
						rank.setRewardAmount(Integer.parseInt(id5[i]));
						rank.setNickColor(Integer.decode("0x" + id6[i]));
						rank.setTitleColor(Integer.decode("0x" + id7[i]));

						RANKS.put(id1.length - i, rank);
					}
				}
			}

			NICK_COLOR_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("NickColorEnabled", "false"));
			TITLE_COLOR_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("TitleColorEnabled", "false"));

			RANK_POINTS_DOWN_COUNT_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPointsDownCountEnabled", "false"));
			RANK_POINTS_DOWN_AMOUNTS = new FastList<Integer>();
			for (String id : ccSettings.getProperty("RankPointsDownAmounts", "").split(","))
			{
				RANK_POINTS_DOWN_AMOUNTS.add(Integer.parseInt(id));
			}

			RANK_SHOUT_INFO_ON_KILL_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankShoutInfoOnKillEnabled", "false"));
			RANK_SHOUT_BONUS_INFO_ON_KILL_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankShoutBonusInfoOnKillEnabled", "false"));
			RANK_REWARD_FOR_PK_KILLER_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankRewardForPkKillerEnabled", "false"));
			RANK_REWARD_FOR_INNOCENT_KILL_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankRewardForInnocentKillEnabled", "false"));
			WAR_KILLS_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("WarKillsEnabled", "false"));
			if (WAR_KILLS_ENABLED)
			{
				WAR_RANK_POINTS_RATIO = Double.parseDouble(ccSettings.getProperty("WarRankPointsRatio", "1.0"));
			}
			else
			{
				WAR_RANK_POINTS_RATIO = 1.0;
			}

			COMBO_KILL_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("ComboKillEnabled", "false"));
			COMBO_KILL_PROTECTION_WITH_LEGAL_KILL_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("ComboKillProtectionWithLegalKillEnabled", "false"));
			COMBO_KILL_PROTECTION_NO_REPEAT_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("ComboKillProtectionNoRepeatEnabled", "false"));

			String propertyValue = ccSettings.getProperty("ComboKillLocalAreaMessages");
			if (propertyValue != null && propertyValue.length() > 0)
			{

				String[] propertySplit = propertyValue.split(";");
				if (propertySplit.length > 0)
				{
					for (String skill : propertySplit)
					{

						String[] skillSplit = skill.split(",");
						if (skillSplit.length != 2)
						{
							// _log.warning(StringUtil.concat("[RankPvpRankPointsBonusArea]: invalid config property -> RankPvpRankPointsBonusArea \"", skill, "\""));
						}
						else
						{
							try
							{
								COMBO_KILL_LOCAL_AREA_MESSAGES.put(Integer.parseInt(skillSplit[0]), skillSplit[1]);
							}
							catch (NumberFormatException nfe)
							{
								if (!skill.isEmpty())
								{
									_log.warning(StringUtil.concat("[ComboKillLocalAreaMessages]: invalid config property -> \"", skillSplit[0], "\"", skillSplit[1]));
								}
							}
						}
					}
				}
			}

			propertyValue = ccSettings.getProperty("ComboKillGlobalAreaMessages", "");
			if (propertyValue != null && propertyValue.length() > 0)
			{

				String[] propertySplit = ccSettings.getProperty("ComboKillGlobalAreaMessages").split(";");
				if (propertySplit.length > 0)
				{
					for (String skill : propertySplit)
					{

						String[] skillSplit = skill.split(",");
						if (skillSplit.length != 2)
						{
							// _log.warning(StringUtil.concat("[RankPvpRankPointsBonusArea]: invalid config property -> RankPvpRankPointsBonusArea \"", skill, "\""));
						}
						else
						{
							try
							{
								COMBO_KILL_GLOBAL_AREA_MESSAGES.put(Integer.parseInt(skillSplit[0]), skillSplit[1]);
							}
							catch (NumberFormatException nfe)
							{
								if (!skill.isEmpty())
								{
									_log.warning(StringUtil.concat("[ComboKillGlobalAreaMessages]: invalid config property -> \"", skillSplit[0], "\"", skillSplit[1]));
								}
							}
						}
					}
				}
			}

			COMBO_KILL_ALT_MESSAGES_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("ComboKillAltMessagesEnabled", "false"));
			COMBO_KILL_ALT_MESSAGE = ccSettings.getProperty("ComboKillAltMessage", "%killer% have %combo_level% Combo kills!");
			COMBO_KILL_ALT_GLOBAL_MESSAGE_LVL = Integer.parseInt(ccSettings.getProperty("ComboKillAltGlobalMessageMinLvl", "0"));

			COMBO_KILL_DEFEAT_MESSAGE_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("ComboKillDefeatMessageEnabled", "true"));
			COMBO_KILL_DEFEAT_MESSAGE_MIN_LVL = Integer.parseInt(ccSettings.getProperty("ComboKillDefeatMessageMinComboLvl", "0"));
			COMBO_KILL_DEFEAT_MESSAGE = ccSettings.getProperty("ComboKillDefeatMessage", "%killer% is defeated with %combo_level% combo lvl!!!");

			COMBO_KILL_RESETER = Integer.parseInt(ccSettings.getProperty("ComboKillReseter", "0"));
			COMBO_KILL_RANK_POINTS_RATIO_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("ComboKillRankPointsRatioEnabled", "false"));

			propertyValue = ccSettings.getProperty("ComboKillRankPointsRatio", "");
			if (propertyValue != null && propertyValue.length() > 0)
			{

				String[] propertySplit = ccSettings.getProperty("ComboKillRankPointsRatio").split(";");
				if (propertySplit.length > 0)
				{
					for (String skill : propertySplit)
					{

						String[] skillSplit = skill.split(",");
						if (skillSplit.length != 2)
						{
							// _log.warning(StringUtil.concat("[RankPvpRankPointsBonusArea]: invalid config property -> RankPvpRankPointsBonusArea \"", skill, "\""));
						}
						else
						{
							try
							{
								COMBO_KILL_RANK_POINTS_RATIO.put(Integer.parseInt(skillSplit[0]), Double.parseDouble(skillSplit[1]));
							}
							catch (NumberFormatException nfe)
							{
								if (!skill.isEmpty())
								{
									_log.warning(StringUtil.concat("[ComboKillRankPointsRatio]: invalid config property -> \"", skillSplit[0], "\"", skillSplit[1]));
								}
							}
						}
					}
				}
			}

			COMBO_KILL_ON_EVENTS_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("ComboKillOnEventsEnabled", "false"));

			// additional security for combo kill system:
			if (COMBO_KILL_LOCAL_AREA_MESSAGES.size() == 0 && COMBO_KILL_GLOBAL_AREA_MESSAGES.size() == 0)
			{
				COMBO_KILL_ENABLED = false;
			}

			int i = 0;
			String tempStr = ccSettings.getProperty("AllowedZonesIds");
			if (tempStr != null && tempStr.length() > 0)
			{
				for (String rZoneId : tempStr.split(","))
				{
					try
					{
						ALLOWED_ZONES_IDS.add(i, Integer.parseInt(rZoneId));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					i++;
				}
			}

			i = 0;
			tempStr = ccSettings.getProperty("RestrictedZonesIds");
			if (tempStr != null && tempStr.length() > 0)
			{
				for (String rZoneId : tempStr.split(","))
				{
					try
					{
						RESTRICTED_ZONES_IDS.add(i, Integer.parseInt(rZoneId));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					i++;
				}
			}
			LEGAL_KILL_MIN_LVL = Integer.parseInt(ccSettings.getProperty("LegalKillMinLvl", "1"));
			LEGAL_KILL_FOR_PK_KILLER_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("LegalKillForPkKillerEnabled", "true"));
			LEGAL_KILL_FOR_INNOCENT_KILL_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("LegalKillForInnocentKillerEnabled", "false"));
			PROTECTION_TIME_RESET = Integer.parseInt(ccSettings.getProperty("ProtectionTimeReset", "0"));

			LEGAL_KILL_PROTECTION = Integer.parseInt(ccSettings.getProperty("LegalKillProtection", "0"));
			DAILY_LEGAL_KILL_PROTECTION = Integer.parseInt(ccSettings.getProperty("DailyLegalKillProtection", "0"));

			PVP_INFO_COMMAND_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("PvpInfoCommandEnabled", "true"));
			PVP_INFO_USER_COMMAND_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("PvpInfoUserCommandEnabled", "false"));
			PVP_INFO_USER_COMMAND_ID = Integer.parseInt(ccSettings.getProperty("PvpInfoUserCommandId", "114"));

			PVP_INFO_COMMAND_ON_DEATH_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("PvpInfoCommandShowOnDeathEnabled", "true"));
			DEATH_MANAGER_DETAILS_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("DeathManagerDetailsEnabled", "true"));
			DEATH_MANAGER_SHOW_ITEMS_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("DeathManagerShowItemsEnabled", "true"));

			i = 0;
			tempStr = ccSettings.getProperty("DeathManagerRestrictedZonesIds");
			if (tempStr != null && tempStr.length() > 0)
			{
				for (String rZoneId : tempStr.split(","))
				{
					try
					{
						DEATH_MANAGER_RESTRICTED_ZONES_IDS.add(i, Integer.parseInt(rZoneId));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					i++;
				}
			}

			propertyValue = ccSettings.getProperty("RankPointsBonusZonesIds", "");
			if (propertyValue != null && propertyValue.length() > 0)
			{

				String[] propertySplit = ccSettings.getProperty("RankPointsBonusZonesIds", "").split(";");
				if (propertySplit.length > 0)
				{
					for (String skill : propertySplit)
					{

						String[] skillSplit = skill.split(",");
						if (skillSplit.length != 2)
						{
							// _log.warning(StringUtil.concat("[RankPvpRankPointsBonusArea]: invalid config property -> RankPvpRankPointsBonusArea \"", skill, "\""));
						}
						else
						{
							try
							{

								RANK_POINTS_BONUS_ZONES_IDS.put(Integer.parseInt(skillSplit[0]), Double.parseDouble(skillSplit[1]));
							}
							catch (NumberFormatException nfe)
							{
								if (!skill.isEmpty())
								{
									_log.warning(StringUtil.concat("[RankPvpRankPointsBonusArea]: invalid config property -> \"", skillSplit[0], "\"", skillSplit[1]));
								}
							}
						}
					}
				}
			}

			TOTAL_KILLS_IN_SHOUT_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("TotalKillsInShoutEnabled", "true"));
			TOTAL_KILLS_IN_PVPINFO_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("TotalKillsInPvpInfoEnabled", "true"));
			TOTAL_KILLS_ON_ME_IN_PVPINFO_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("TotalKillsOnMeInPvpInfoEnabled", "true"));

			RANK_POINTS_REWARD_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPointsRewardEnabled", "true"));

			ANTI_FARM_CLAN_ALLY_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("AntiFarmClanAllyEnabled", "true"));
			ANTI_FARM_PARTY_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("AntiFarmPartyEnabled", "true"));
			ANTI_FARM_IP_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("AntiFarmIpEnabled", "true"));

			PVP_TABLE_UPDATE_INTERVAL = (Integer.parseInt(ccSettings.getProperty("PvpTableUpdateInterval", "1")) * 60000);
			if (PVP_TABLE_UPDATE_INTERVAL < 1)
			{
				PVP_TABLE_UPDATE_INTERVAL = 60000;
			}

			TOP_TABLE_UPDATE_INTERVAL = (Integer.parseInt(ccSettings.getProperty("TopTableUpdateInterval", "60")) * 60000);
			if (TOP_TABLE_UPDATE_INTERVAL < 10)
			{
				TOP_TABLE_UPDATE_INTERVAL = 3600000;
			}

			COMMUNITY_BOARD_TOP_LIST_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("CommunityBoardTopListEnabled", "true"));
			COMMUNITY_BOARD_TOP_LIST_IGNORE_TIME_LIMIT = Integer.parseInt(ccSettings.getProperty("CommunityBoardTopListIgnoreTimeLimit", "0"));
			if (COMMUNITY_BOARD_TOP_LIST_IGNORE_TIME_LIMIT > 0)
			{
				COMMUNITY_BOARD_TOP_LIST_IGNORE_TIME_LIMIT *= 86400000;
			}

			IMAGE_PREFIX = Integer.parseInt(ccSettings.getProperty("ImagePrefix", "1"));

		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "Config: Failed to Load " + Rank_Config + " File.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}

		// Load Vote System Config file (if exists)
		final File Auto = new File(Automation_Config);
		try
		{
			InputStream is = new FileInputStream(Auto);
			L2Properties AutoSettings = new L2Properties();
			AutoSettings.load(is);

			ALLOW_SERVER_RESTART_COMMAND = Boolean.parseBoolean(AutoSettings.getProperty("AllowServerRestartCommand", "False"));
			VOTES_NEEDED_FOR_RESTART = Integer.parseInt(AutoSettings.getProperty("VotesNeededForRestart", "20"));
			RESTART_BY_TIME_OF_DAY = Boolean.parseBoolean(AutoSettings.getProperty("EnableRestartSystem", "false"));
			RESTART_SECONDS = Integer.parseInt(AutoSettings.getProperty("RestartSeconds", "360"));
			RESTART_INTERVAL_BY_TIME_OF_DAY = (AutoSettings.getProperty("RestartByTimeOfDay", "23:59").split(","));
			LOGIN_SERVER_SCHEDULE_RESTART = Boolean.parseBoolean(AutoSettings.getProperty("LoginRestartSchedule", "False"));
			LOGIN_SERVER_SCHEDULE_RESTART_TIME = Long.parseLong(AutoSettings.getProperty("LoginRestartTime", "24"));
			ALLOW_SEQURITY_QUE = Boolean.valueOf(AutoSettings.getProperty("AllowSecurityQuestion", "True"));
			SECURITY_QUE_TIME = Integer.parseInt(AutoSettings.getProperty("Securitytime", "20"));
			ALLOW_ANTI_AFK = Boolean.valueOf(AutoSettings.getProperty("AllowAntiAfk", "True"));
			MINUTES_AFK_PLAYERS = Integer.parseInt(AutoSettings.getProperty("AntiAfkMinutes", "20"));
			ALLOW_PRIVATE_ANTI_BOT = Boolean.valueOf(AutoSettings.getProperty("AllowPrivateAntiBot", "False"));
			ENCHANT_BOT_CHANCE = Integer.parseInt(AutoSettings.getProperty("PrivateBotChance", "15"));

		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "Config: Failed to Load " + Automation_Config + " File.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}

		// Load Vote System Config file (if exists)
		final File Vote = new File(Vote_Config);
		try
		{
			InputStream is = new FileInputStream(Vote);
			L2Properties VoteSettings = new L2Properties();
			VoteSettings.load(is);

			VOTE_LINK_HOPZONE = VoteSettings.getProperty("HopzoneUrl", "null");
			VOTE_LINK_TOPZONE = VoteSettings.getProperty("TopzoneUrl", "null");
			VOTE_REWARD_ID1 = Integer.parseInt(VoteSettings.getProperty("VoteRewardId1", "300"));
			VOTE_REWARD_ID2 = Integer.parseInt(VoteSettings.getProperty("VoteRewardId2", "300"));
			VOTE_REWARD_ID3 = Integer.parseInt(VoteSettings.getProperty("VoteRewardId3", "300"));
			VOTE_REWARD_ID4 = Integer.parseInt(VoteSettings.getProperty("VoteRewardId4", "300"));
			VOTE_REWARD_AMOUNT1 = Integer.parseInt(VoteSettings.getProperty("VoteRewardAmount1", "300"));
			VOTE_REWARD_AMOUNT2 = Integer.parseInt(VoteSettings.getProperty("VoteRewardAmount2", "300"));
			VOTE_REWARD_AMOUNT3 = Integer.parseInt(VoteSettings.getProperty("VoteRewardAmount3", "300"));
			VOTE_REWARD_AMOUNT4 = Integer.parseInt(VoteSettings.getProperty("VoteRewardAmount4", "300"));
			SECS_TO_VOTE = Integer.parseInt(VoteSettings.getProperty("SecondsToVote", "20"));
			EXTRA_REW_VOTE_AM = Integer.parseInt(VoteSettings.getProperty("ExtraRewVoteAm", "20"));
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "Config: Failed to Load " + Vote_Config + " File.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}

		// Load Smart CB Config file (if exists)
		final File smartcb = new File(Smart_CB);
		try (InputStream is = new FileInputStream(smartcb))
		{
			L2Properties smartCB = new L2Properties();
			smartCB.load(is);
			TOP_PLAYER_ROW_HEIGHT = Integer.parseInt(smartCB.getProperty("TopPlayerRowHeight", "19"));
			TOP_PLAYER_RESULTS = Integer.parseInt(smartCB.getProperty("TopPlayerResults", "20"));
			RAID_LIST_ROW_HEIGHT = Integer.parseInt(smartCB.getProperty("RaidListRowHeight", "18"));
			RAID_LIST_RESULTS = Integer.parseInt(smartCB.getProperty("RaidListResults", "20"));
			RAID_LIST_SORT_ASC = Boolean.parseBoolean(smartCB.getProperty("RaidListSortAsc", "True"));
			ALLOW_REAL_ONLINE_STATS = Boolean.parseBoolean(smartCB.getProperty("AllowRealOnlineStats", "True"));
			ALLOW_CLASS_MASTERSCB = smartCB.getProperty("AllowClassMastersCB", "0");
			CLASS_MASTERS_PRICE_ITEMCB = Integer.parseInt(smartCB.getProperty("ClassMastersPriceItemCB", "57"));
			if ((ALLOW_CLASS_MASTERSCB.length() != 0) && (!ALLOW_CLASS_MASTERSCB.equals("0")))
			{
				for (String id : ALLOW_CLASS_MASTERSCB.split(","))
				{
					ALLOW_CLASS_MASTERS_LISTCB.add(Integer.valueOf(Integer.parseInt(id)));
				}
			}
			CLASS_MASTERS_PRICECB = smartCB.getProperty("ClassMastersPriceCB", "0,0,0");
			if (CLASS_MASTERS_PRICECB.length() >= 5)
			{
				int level = 0;
				for (String id : CLASS_MASTERS_PRICECB.split(","))
				{
					CLASS_MASTERS_PRICE_LISTCB[level] = Integer.parseInt(id);
					level++;
				}
			}
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "Config: Failed to Load " + Smart_CB + " File.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}

		// Load Custom Npc's Config file (if exists)
		final File CustomNpc = new File(Custom_Npc);
		try (InputStream is = new FileInputStream(CustomNpc))
		{
			L2Properties CustomNpcs = new L2Properties();
			CustomNpcs.load(is);
			/* Noblesse Manager */
			SHOW_NPC_CREST = Boolean.parseBoolean(CustomNpcs.getProperty("ShowNpcCrest", "False"));
			ALLOW_ACCOUNT_MANAGER = Boolean.parseBoolean(CustomNpcs.getProperty("AllowAccManager", "False"));
			EMAIL_USER = CustomNpcs.getProperty("EmailUsername", "null");
			EMAIL_PASS = CustomNpcs.getProperty("EmailPassword", "null");
			PROTECTOR_PLAYER_PK = Boolean.parseBoolean(CustomNpcs.getProperty("ProtectorPlayerPK", "false"));
			PROTECTOR_PLAYER_PVP = Boolean.parseBoolean(CustomNpcs.getProperty("ProtectorPlayerPVP", "false"));
			PROTECTOR_RADIUS_ACTION = Integer.parseInt(CustomNpcs.getProperty("ProtectorRadiusAction", "500"));
			PROTECTOR_SKILLID = Integer.parseInt(CustomNpcs.getProperty("ProtectorSkillId", "1069"));
			PROTECTOR_SKILLLEVEL = Integer.parseInt(CustomNpcs.getProperty("ProtectorSkillLevel", "42"));
			PROTECTOR_SKILLTIME = Integer.parseInt(CustomNpcs.getProperty("ProtectorSkillTime", "800"));
			SEND_MESSAGE = Boolean.parseBoolean(CustomNpcs.getProperty("SendMessage", "false"));
			PROTECTOR_MESSAGE = CustomNpcs.getProperty("ProtectorMessage", "Protector, not spawnkilling here, go read the rules !!!");
			NPCBUFFER_FEATURE_ENABLED = Boolean.valueOf(CustomNpcs.getProperty("NPCBufferEnabled", "False"));
			NPCBUFFER_STATIC_BUFF_COST = Integer.parseInt(CustomNpcs.getProperty("NPCBufferStaticCostPerBuff", "-1"));
			ALLOW_CLASS_MASTER = Boolean.valueOf(CustomNpcs.getProperty("AllowClassMaster", "False"));
			ALLOW_REMOTE_CLASS_MASTER = Boolean.valueOf(CustomNpcs.getProperty("AllowRemoteClassMaster", "False"));
			NPC_NOBLES_ENABLE = Boolean.parseBoolean(CustomNpcs.getProperty("NobleManager", "false"));
			NPC_NOBLESS_ID = Integer.parseInt(CustomNpcs.getProperty("NobleID", "57"));
			NPC_NOBLESS_QUANTITY = Integer.parseInt(CustomNpcs.getProperty("NobleQuantity", "10000"));

			/* Boss Info Npc */
			String[] notenchantable = CustomNpcs.getProperty("BossList", "29028,29019,29020,29045,29022,29001,29014,29006").split(",");
			BOSS_RESPAWN_INFO = new int[notenchantable.length];
			for (int i = 0; i < notenchantable.length; i++)
			{
				BOSS_RESPAWN_INFO[i] = Integer.parseInt(notenchantable[i]);
			}
			Arrays.sort(BOSS_RESPAWN_INFO);
			RAID_INFO_SHOW_TIME = Boolean.parseBoolean(CustomNpcs.getProperty("InfoShowTime", "False"));
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "Config: Failed to Load " + Custom_Npc + " File.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}

		// Try to load RCON_CONFIG_FILE (if exist)
		try
		{
			Properties RconSettings = new Properties();
			InputStream is = new FileInputStream(new File(RCON_CONFIG_FILE));
			RconSettings.load(is);
			is.close();

			ENABLED_RCON = Boolean.parseBoolean(RconSettings.getProperty("EnableRconTool", "False"));
			RCON_PORT = Integer.parseInt(RconSettings.getProperty("RconPort", "7779"));
			RCON_PASSWORD = RconSettings.getProperty("RconPassword", "123123");

		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "Config: Failed to Load " + RCON_CONFIG_FILE + " File.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}
}