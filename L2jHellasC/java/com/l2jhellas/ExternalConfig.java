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
import java.util.logging.Logger;

import javolution.util.FastList;
import javolution.util.FastMap;
import Extensions.RankSystem.Rank;

import com.l2jhellas.gameserver.util.L2Properties;
import com.l2jhellas.gameserver.util.StringUtil;

public final class ExternalConfig
{
	private static final Logger _log = Logger.getLogger(ExternalConfig.class.getName());
	// --------------------------------------------------
	// L2J Property File Definitions
	// --------------------------------------------------
	public static final String Rank_Config = "./config/Mods/Rank PvP System.ini";
	public static final String Vote_Config = "./config/Mods/Vote System.ini";
	public static final String Automation_Config = "./config/Mods/Automatation.ini";
	public static final String Smart_CB = "./config/Mods/Smart Community Board.ini";
	public static final String Custom_Npc = "./config/Mods/Custom Npcs.ini";

	// --------------------------------------------------
	// L2J Variable Definitions
	// --------------------------------------------------
	
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

	/* CUSTOM PVP/RANK/REWARD SYSTEM */
	public static boolean CUSTOM_PVP_ENABLED;
	public static boolean CUSTOM_PVP_LEGAL_COUNTER_ALTT_ENABLED;
	public static boolean CUSTOM_PVP_REWARD_ENABLED;
	public static int CUSTOM_PVP_REWARD_PROTECTION;
	public static int CUSTOM_PVP_REWARD_ID;
	public static int CUSTOM_PVP_REWARD_AMOUNT;
	public static int CUSTOM_PVP_REWARD_MIN_LVL;
	public static boolean CUSTOM_PVP_REWARD_PK_KILLER_AWARD;
	public static boolean CUSTOM_PVP_REWARD_PK_MODE_ENABLED;
	public static boolean CUSTOM_PVP_RANK_ENABLED;
	public static int CUSTOM_PVP_RANK_MIN_LVL;
	/** FastMap (rankId, Rank) */
	public static FastMap<Integer, Rank> CUSTOM_PVP_RANKS = new FastMap<Integer, Rank>();
	public static boolean CUSTOM_PVP_RANK_REWARD_ENABLED;
	public static int CUSTOM_PVP_RANK_PROTECTION;
	public static boolean CUSTOM_PVP_RANK_KILL_POINTS_DOWN_ENABLED;
	public static FastList<Integer> CUSTOM_PVP_RANK_KILL_POINTS_DOWN = new FastList<Integer>();
	public static boolean CUSTOM_PVP_RANK_SHOUT_INFO;
	public static boolean CUSTOM_PVP_RANK_PK_KILLER_AWARD;
	public static boolean CUSTOM_PVP_RANK_PK_MODE_ENABLED;
	public static boolean CUSTOM_PVP_WAR_ENABLED;
	public static double CUSTOM_PVP_WAR_RP_RATIO;
	public static FastList<Integer> CUSTOM_PVP_RESTRICTED_ZONES_IDS = new FastList<Integer>();
	public static int CUSTOM_PVP_LEGAL_MIN_LVL;
	public static boolean CUSTOM_PVP_LEGAL_PK_KILLER_AWARD;
	public static boolean CUSTOM_PVP_LEGAL_PK_MODE_ENABLED;
	public static int CUSTOM_PVP_PROTECTION_RESET;
	public static boolean CUSTOM_PVP_INFO_COMMAND_ENABLED;
	public static boolean CUSTOM_PVP_INFO_COMMAND_ON_DEATH_ENABLED;
	public static boolean CUSTOM_PVP_DEATH_MANAGER_DETAILS_ENABLED;
	public static boolean CUSTOM_PVP_DEATH_MANAGER_SHOW_ITEMS;
	public static FastList<Integer> CUSTOM_PVP_DEATH_MANAGER_RESTRICTED_ZONES_IDS = new FastList<Integer>();
	public static boolean CUSTOM_PVP_RANK_POINTS_REWARD_ENABLED;
	public static FastMap<Integer, Double> CUSTOM_PVP_RANK_POINTS_BONUS_ZONES;
	public static boolean CUSTOM_PVP_ANTI_FARM_CLAN_ALLY_ENABLED;
	public static boolean CUSTOM_PVP_ANTI_FARM_PARTY_ENABLED;
	public static boolean CUSTOM_PVP_ANTI_FARM_IP_ENABLED;
	public static int PVP_TABLE_UPDATE_INTERVAL;
	public static int TOP_TABLE_UPDATE_INTERVAL;
	public static boolean COMMUNITY_BOARD_TOP_LIST_ENABLED;
	public static FastList<Integer> RANK_PVP_RESTRICTED_ZONES_IDS = new FastList<Integer>();
	public static int RANK_PVP_LEGAL_MIN_LVL;
	public static boolean RANK_PVP_LEGAL_PK_KILLER_AWARD;
	public static boolean RANK_PVP_LEGAL_PK_MODE_ENABLED;
	public static int RANK_PVP_PROTECTION_RESET;
	public static boolean RANK_PVP_INFO_COMMAND_ENABLED;
	public static boolean RANK_PVP_INFO_COMMAND_ON_DEATH_ENABLED;
	public static boolean RANK_PVP_DEATH_MANAGER_DETAILS_ENABLED;
	public static FastList<Integer> RANK_PVP_DEATH_MANAGER_RESTRICTED_ZONES_IDS = new FastList<Integer>();
	public static boolean RANK_PVP_POINTS_REWARD_ENABLED;
	/** Images required client side textures (default: FALSE) */
	public static boolean RANK_PVP_IMAGES_ENABLED;
	public static boolean RANK_PVP_ANTI_FARM_CLAN_ALLY_ENABLED;
	public static boolean RANK_PVP_ANTI_FARM_PARTY_ENABLED;
	public static boolean RANK_PVP_ANTI_FARM_IP_ENABLED;

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
	public static String EMAIL_USER;
	public static String EMAIL_PASS;
	public static boolean NPCBUFFER_FEATURE_ENABLED;
	public static int NPCBUFFER_STATIC_BUFF_COST;
	public static boolean NPC_NOBLES_ENABLE;
	public static int NPC_NOBLESS_ID1;
	public static int NPC_NOBLESS_QUANTITY1;
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
			CUSTOM_PVP_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpEnabled", "false"));
			CUSTOM_PVP_LEGAL_COUNTER_ALTT_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpLegalCounterAltTEnabled", "false"));
			CUSTOM_PVP_REWARD_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpRewardEnabled", "false"));
			CUSTOM_PVP_REWARD_PROTECTION = Integer.parseInt(ccSettings.getProperty("CustomPvpRewardProtection", "1"));
			CUSTOM_PVP_REWARD_ID = Integer.parseInt(ccSettings.getProperty("CustomPvpRewardItemId", "6392"));
			CUSTOM_PVP_REWARD_AMOUNT = Integer.parseInt(ccSettings.getProperty("CustomPvpRewardAmmount", "1"));
			CUSTOM_PVP_REWARD_MIN_LVL = Integer.parseInt(ccSettings.getProperty("CustomPvpRewardMinLvl", "76"));
			CUSTOM_PVP_REWARD_PK_KILLER_AWARD = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpRewardPkKillerAward", "true"));
			CUSTOM_PVP_REWARD_PK_MODE_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpRewardPkModeEnabled", "false"));
			CUSTOM_PVP_RANK_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpRankEnabled", "false"));
			CUSTOM_PVP_RANK_MIN_LVL = Integer.parseInt(ccSettings.getProperty("CustomPvpRankMinLvl", "76"));
			
			CUSTOM_PVP_RANK_REWARD_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpRankRewardEnabled", "false"));
			
			// set rank's:
			String id1[] = ccSettings.getProperty("CustomPvpRankName", "").split(",");
			String id2[] = ccSettings.getProperty("CustomPvpRankMinPoints", "").split(",");
			String id3[] = ccSettings.getProperty("CustomPvpRankPointsForKill", "").split(",");
			String id4[] = ccSettings.getProperty("CustomPvpRankRewardIds", "").split(",");
			String id5[] = ccSettings.getProperty("CustomPvpRankRewardAmounts", "").split(",");
			
			if (CUSTOM_PVP_ENABLED || CUSTOM_PVP_REWARD_ENABLED || CUSTOM_PVP_RANK_ENABLED)
			{
				if (id1.length != id2.length || id1.length != id3.length || id1.length != id4.length || id1.length != id5.length)
				{
					_log.info("ERROR: Custom PvP System Config: Arrays sizes should be the same!");
					
					_log.info("CUSTOM_PVP_RANK_NAME           :" + id1.length);
					_log.info("CUSTOM_PVP_RANK_MIN_POINTS     :" + id2.length);
					_log.info("CUSTOM_PVP_RANK_POINTS_FOR_KILL:" + id3.length);
					_log.info("CUSTOM_PVP_RANK_REWARD_IDS     :" + id4.length);
					_log.info("CUSTOM_PVP_RANK_REWARD_AMOUNTS :" + id5.length);
					
				}
				else
				{
					for (int i = 0; i < id1.length; i++)
					{
						Rank rank = new Rank();
						
						rank.setName(id1[i]);
						rank.setMinPoints(Long.parseLong(id2[i]));
						rank.setPointsForKill(Integer.parseInt(id3[i]));
						rank.setRewardId(Integer.parseInt(id4[i]));
						rank.setRewardAmount(Integer.parseInt(id5[i]));
						
						rank.setId(id1.length - i);
						
						CUSTOM_PVP_RANKS.put(id1.length - i, rank);
						
						// _log.info("rankId:"+(id1.length -
						// i)+", rankName:"+id1[i]+", rankMinPoints:"+id2[i]+", rankPointsForKill:"+id3[i]+", rankRewardId:"+id4[i]+", rankRewardCount:"+id5[i]);
					}
				}
			}
			
			CUSTOM_PVP_RANK_PROTECTION = Integer.parseInt(ccSettings.getProperty("CustomPvpRankProtection", "0"));
			CUSTOM_PVP_RANK_KILL_POINTS_DOWN_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpRankKillPointsDownEnabled", "false"));
			CUSTOM_PVP_RANK_KILL_POINTS_DOWN = new FastList<Integer>();
			for (String id : ccSettings.getProperty("CustomPvpRankKillPointsDown", "").split(","))
			{
				CUSTOM_PVP_RANK_KILL_POINTS_DOWN.add(Integer.parseInt(id));
			}
			CUSTOM_PVP_RANK_SHOUT_INFO = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpRankShoutInfo", "false"));
			CUSTOM_PVP_RANK_PK_KILLER_AWARD = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpRankPkKillerAward", "false"));
			CUSTOM_PVP_RANK_PK_MODE_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpRankPkModeEnabled", "false"));
			CUSTOM_PVP_WAR_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpWarEnabled", "false"));
			if (CUSTOM_PVP_WAR_ENABLED)
			{
				CUSTOM_PVP_WAR_RP_RATIO = Double.parseDouble(ccSettings.getProperty("CustomPvpWarRPRatio", "1.0"));
			}
			else
			{
				CUSTOM_PVP_WAR_RP_RATIO = 1.0;
			}
			
			int i = 0;
			String tempStr = ccSettings.getProperty("CustomPvpRestrictedZonesIds");
			if (tempStr != null && tempStr.length() > 0)
			{
				for (String rZoneId : tempStr.split(","))
				{
					try
					{
						CUSTOM_PVP_RESTRICTED_ZONES_IDS.add(i, Integer.parseInt(rZoneId));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					i++;
				}
			}
			CUSTOM_PVP_LEGAL_MIN_LVL = Integer.parseInt(ccSettings.getProperty("CustomPvpLegalMinLvl", "0"));
			CUSTOM_PVP_LEGAL_PK_KILLER_AWARD = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpLegalPkKillerAward", "true"));
			CUSTOM_PVP_LEGAL_PK_MODE_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpLegalPkModeEnabled", "false"));
			CUSTOM_PVP_PROTECTION_RESET = Integer.parseInt(ccSettings.getProperty("CustomPvpProtectionReset", "0"));
			CUSTOM_PVP_INFO_COMMAND_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpInfoCommandEnabled", "true"));
			
			CUSTOM_PVP_INFO_COMMAND_ON_DEATH_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpInfoCommandShowOnDeathEnabled", "true"));
			CUSTOM_PVP_DEATH_MANAGER_DETAILS_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpDeathManagerDetailsEnabled", "true"));
			CUSTOM_PVP_DEATH_MANAGER_SHOW_ITEMS = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpDeathManagerShowItems", "true"));
			
			i = 0;
			tempStr = ccSettings.getProperty("CustomPvpDeathManagerRestrictedZonesIds");
			if (tempStr != null && tempStr.length() > 0)
			{
				for (String rZoneId : tempStr.split(","))
				{
					try
					{
						CUSTOM_PVP_DEATH_MANAGER_RESTRICTED_ZONES_IDS.add(i, Integer.parseInt(rZoneId));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					i++;
				}
			}
			
			String[] propertySplit = ccSettings.getProperty("CustomPvpRankPointsBonusZones", "").split(";");
			CUSTOM_PVP_RANK_POINTS_BONUS_ZONES = new FastMap<Integer, Double>(propertySplit.length);
			if (propertySplit.length > 0)
			{
				for (String skill : propertySplit)
				{
					
					String[] skillSplit = skill.split(",");
					if (skillSplit.length != 2)
					{
						// _log.warning(StringUtil.concat("[CustomPvpRankPointsBonusArea]: invalid config property -> CustomPvpRankPointsBonusArea \"",
						// skill, "\""));
					}
					else
					{
						try
						{
							
							CUSTOM_PVP_RANK_POINTS_BONUS_ZONES.put(Integer.parseInt(skillSplit[0]), Double.parseDouble(skillSplit[1]));
						}
						catch (NumberFormatException nfe)
						{
							if (!skill.isEmpty())
							{
								_log.warning(StringUtil.concat("[CustomPvpRankPointsBonusArea]: invalid config property -> \"", skillSplit[0], "\"", skillSplit[1]));
							}
						}
					}
				}
			}
			CUSTOM_PVP_RANK_POINTS_REWARD_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpRankPointsRewardEnabled", "true"));
			
			CUSTOM_PVP_ANTI_FARM_CLAN_ALLY_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpAntiFarmClanAllyEnabled", "true"));
			CUSTOM_PVP_ANTI_FARM_PARTY_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpAntiFarmPartyEnabled", "true"));
			CUSTOM_PVP_ANTI_FARM_IP_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("CustomPvpAntiFarmIpEnabled", "true"));
			
			PVP_TABLE_UPDATE_INTERVAL = Integer.parseInt(ccSettings.getProperty("PvpTableUpdateInterval", "10000"));
			if (PVP_TABLE_UPDATE_INTERVAL < 10000)
			{
				PVP_TABLE_UPDATE_INTERVAL = 10000;
			}
			
			TOP_TABLE_UPDATE_INTERVAL = Integer.parseInt(ccSettings.getProperty("TopTableUpdateInterval", "60000"));
			if (TOP_TABLE_UPDATE_INTERVAL < 60000)
			{
				TOP_TABLE_UPDATE_INTERVAL = 60000;
			}
			
			COMMUNITY_BOARD_TOP_LIST_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("CommunityBoardTopListEnabled", "true"));
		}
		catch (Exception e)
		{
			_log.warning("Config: " + e.getMessage());
			throw new Error("Failed to Load " + Rank_Config + " File.");
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
		}
		catch (Exception e)
		{
			_log.warning("Config: " + e.getMessage());
			throw new Error("Failed to Load " + Automation_Config + " File.");
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
			_log.warning("Config: " + e.getMessage());
			throw new Error("Failed to Load " + Vote_Config + " File.");
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
			_log.warning("Config: " + e.getMessage());
			throw new Error("Failed to Load " + Smart_CB + " File.");
		}
		
		// Load Custom Npc's Config file (if exists)
		final File CustomNpc = new File(Custom_Npc);
		try (InputStream is = new FileInputStream(CustomNpc))
		{
			L2Properties CustomNpcs = new L2Properties();
			CustomNpcs.load(is);
			/* Noblesse Manager */
			SHOW_NPC_CREST = Boolean.parseBoolean(CustomNpcs.getProperty("ShowNpcCrest", "False"));
			EMAIL_USER = CustomNpcs.getProperty("EmailUsername", "null");
			EMAIL_PASS = CustomNpcs.getProperty("EmailPassword", "null");
			NPCBUFFER_FEATURE_ENABLED = Boolean.valueOf(CustomNpcs.getProperty("NPCBufferEnabled", "False"));
			NPCBUFFER_STATIC_BUFF_COST = Integer.parseInt(CustomNpcs.getProperty("NPCBufferStaticCostPerBuff", "-1"));
			ALLOW_CLASS_MASTER = Boolean.valueOf(CustomNpcs.getProperty("AllowClassMaster", "False"));
			ALLOW_REMOTE_CLASS_MASTER = Boolean.valueOf(CustomNpcs.getProperty("AllowRemoteClassMaster", "False"));
			NPC_NOBLES_ENABLE = Boolean.parseBoolean(CustomNpcs.getProperty("NobleManager", "false"));
			NPC_NOBLESS_ID1 = Integer.parseInt(CustomNpcs.getProperty("NobleID", "57"));
			NPC_NOBLESS_QUANTITY1 = Integer.parseInt(CustomNpcs.getProperty("NobleQuantity", "10000"));
			
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
			_log.warning("Config: " + e.getMessage());
			throw new Error("Failed to Load " + Custom_Npc + " File.");
		}

	}
}