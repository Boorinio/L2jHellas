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
import java.util.logging.Logger;

import javolution.util.FastList;

import com.l2jhellas.gameserver.util.L2Properties;


public final class ExternalConfig
{
	private static final Logger _log = Logger.getLogger(ExternalConfig.class.getName());
	// --------------------------------------------------
	// L2J Property File Definitions
	// --------------------------------------------------
	public static final String Rank_Config = "./config/Mods/Rank PvP System.ini";
	public static final String Vote_Config = "./config/Mods/Vote System.ini";
	public static final String Automation_Config = "./config/Mods/Automatation.ini";
	// --------------------------------------------------
	// L2J Variable Definitions
	// --------------------------------------------------
	
	/*Automation Config System*/
	 public static boolean ALLOW_SERVER_RESTART_COMMAND;
	 public static int     VOTES_NEEDED_FOR_RESTART;
	 public static boolean RESTART_BY_TIME_OF_DAY;
	 public static int RESTART_SECONDS;
	 public static String[] RESTART_INTERVAL_BY_TIME_OF_DAY; 
	 public static boolean LOGIN_SERVER_SCHEDULE_RESTART;
	 public static long LOGIN_SERVER_SCHEDULE_RESTART_TIME;
	/* CUSTOM PVP/RANK/REWARD SYSTEM */
	public static boolean RANK_ENABLED;
	public static boolean RANK_PVP_LEGAL_COUNTER_ALTT_ENABLED;
	public static boolean RANK_REWARD_ENABLED;
	public static int RANK_PVP_REWARD_PROTECTION;
	public static int RANK_PVP_REWARD_ID;
	public static int RANK_PVP_REWARD_AMOUNT;
	public static int RANK_PVP_REWARD_MIN_LVL;
	public static boolean RANK_PVP_REWARD_PK_KILLER_AWARD;
	public static boolean RANK_PVP_REWARD_PK_MODE_ENABLED;
	public static boolean RANK_PVP_ENABLED;
	public static int RANK_PVP_MIN_LVL;
	public static FastList<String> RANK_PVP_NAME = new FastList<String>();
	public static FastList<Integer> RANK_PVP_MIN_POINTS = new FastList<Integer>();
	public static FastList<Integer> RANK_PVP_POINTS_FOR_KILL = new FastList<Integer>();
	public static boolean RANK_PVP_REWARD_ENABLED;
	public static FastList<Integer> RANK_PVP_REWARD_IDS = new FastList<Integer>();
	public static FastList<Integer> RANK_PVP_REWARD_AMOUNTS = new FastList<Integer>();
	public static int RANK_PVP_PROTECTION;
	public static boolean RANK_PVP_KILL_POINTS_DOWN_ENABLED;
	public static FastList<Integer> RANK_PVP_KILL_POINTS_DOWN = new FastList<Integer>();
	public static boolean RANK_PVP_SHOUT_INFO;
	public static boolean RANK_PVP_PK_KILLER_AWARD;
	public static boolean RANK_PVP_PK_MODE_ENABLED;
	public static boolean RANK_PVP_WAR_ENABLED;
	public static double RANK_PVP_WAR_RP_RATIO;
	
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
			RANK_PVP_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPvpEnabled", "false"));
			RANK_PVP_LEGAL_COUNTER_ALTT_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPvpLegalCounterAltTEnabled", "false"));
			RANK_PVP_REWARD_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPvpRewardEnabled", "false"));
			RANK_PVP_REWARD_PROTECTION = Integer.parseInt(ccSettings.getProperty("RankPvpRewardProtection", "1"));
			RANK_PVP_REWARD_ID = Integer.parseInt(ccSettings.getProperty("RankPvpRewardItemId", "6392"));
			RANK_PVP_REWARD_AMOUNT = Integer.parseInt(ccSettings.getProperty("RankPvpRewardAmmount", "1"));
			RANK_PVP_REWARD_MIN_LVL = Integer.parseInt(ccSettings.getProperty("RankPvpRewardMinLvl", "76"));
			RANK_PVP_REWARD_PK_KILLER_AWARD = Boolean.parseBoolean(ccSettings.getProperty("RankPvpRewardPkKillerAward", "true"));
			RANK_PVP_REWARD_PK_MODE_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPvpRewardPkModeEnabled", "false"));
			RANK_PVP_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPvpEnabled", "false"));
			RANK_PVP_MIN_LVL = Integer.parseInt(ccSettings.getProperty("RankPvpMinLvl", "76"));
			RANK_PVP_NAME = new FastList<String>();
			for (String id : ccSettings.getProperty("RankPvpName", "").split(","))
			{
				RANK_PVP_NAME.add(id);
			}
			RANK_PVP_MIN_POINTS = new FastList<Integer>();
			for (String id : ccSettings.getProperty("RankPvpMinPoints", "").split(","))
			{
				RANK_PVP_MIN_POINTS.add(Integer.parseInt(id));
			}
			RANK_PVP_POINTS_FOR_KILL = new FastList<Integer>();
			for (String id : ccSettings.getProperty("RankPvpPointsForKill", "").split(","))
			{
				RANK_PVP_POINTS_FOR_KILL.add(Integer.parseInt(id));
			}
			RANK_PVP_REWARD_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPvpRewardEnabled", "false"));
			RANK_PVP_REWARD_IDS = new FastList<Integer>();
			for (String id : ccSettings.getProperty("RankPvpRewardIds", "").split(","))
			{
				RANK_PVP_REWARD_IDS.add(Integer.parseInt(id));
			}
			RANK_PVP_REWARD_AMOUNTS = new FastList<Integer>();
			for (String id : ccSettings.getProperty("RankPvpRewardAmounts", "").split(","))
			{
				RANK_PVP_REWARD_AMOUNTS.add(Integer.parseInt(id));
			}
			RANK_PVP_PROTECTION = Integer.parseInt(ccSettings.getProperty("RankPvpProtection", "0"));
			RANK_PVP_KILL_POINTS_DOWN_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPvpKillPointsDownEnabled", "false"));
			RANK_PVP_KILL_POINTS_DOWN = new FastList<Integer>();
			for (String id : ccSettings.getProperty("RankPvpKillPointsDown", "").split(","))
			{
				RANK_PVP_KILL_POINTS_DOWN.add(Integer.parseInt(id));
			}
			RANK_PVP_SHOUT_INFO = Boolean.parseBoolean(ccSettings.getProperty("RankPvpShoutInfo", "false"));
			RANK_PVP_PK_KILLER_AWARD = Boolean.parseBoolean(ccSettings.getProperty("RankPvpPkKillerAward", "false"));
			RANK_PVP_PK_MODE_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPvpPkModeEnabled", "false"));
			RANK_PVP_WAR_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPvpWarEnabled", "false"));
			RANK_PVP_WAR_RP_RATIO = Double.parseDouble(ccSettings.getProperty("RankPvpWarRPRatio", "1.0"));
			
			int i = 0;
			String tempStr = ccSettings.getProperty("RankPvpRestrictedZonesIds");
			if (tempStr != null && tempStr.length() > 0)
			{
				for (String rZoneId : tempStr.split(","))
				{
					try
					{
						RANK_PVP_RESTRICTED_ZONES_IDS.add(i, Integer.parseInt(rZoneId));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					i++;
				}
			}
			RANK_PVP_LEGAL_MIN_LVL = Integer.parseInt(ccSettings.getProperty("RankPvpLegalMinLvl", "0"));
			RANK_PVP_LEGAL_PK_KILLER_AWARD = Boolean.parseBoolean(ccSettings.getProperty("RankPvpLegalPkKillerAward", "true"));
			RANK_PVP_LEGAL_PK_MODE_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPvpLegalPkModeEnabled", "false"));
			RANK_PVP_PROTECTION_RESET = Integer.parseInt(ccSettings.getProperty("RankPvpProtectionReset", "0"));
			RANK_PVP_INFO_COMMAND_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPvpInfoCommandEnabled", "true"));
			if (RANK_PVP_ENABLED || RANK_PVP_REWARD_ENABLED || RANK_PVP_ENABLED)
			{
				if (RANK_PVP_NAME.size() != RANK_PVP_MIN_POINTS.size() || RANK_PVP_NAME.size() != RANK_PVP_POINTS_FOR_KILL.size() || RANK_PVP_NAME.size() != RANK_PVP_POINTS_FOR_KILL.size() || RANK_PVP_NAME.size() != RANK_PVP_REWARD_IDS.size() || RANK_PVP_NAME.size() != RANK_PVP_REWARD_AMOUNTS.size())
				{
					_log.info("Custom PvP System: Arrays sizes should be the same.");
					_log.info("RANK_PVP_NAME           :" + RANK_PVP_NAME.size());
					_log.info("RANK_PVP_MIN_POINTS     :" + RANK_PVP_MIN_POINTS.size());
					_log.info("RANK_PVP_POINTS_FOR_KILL:" + RANK_PVP_POINTS_FOR_KILL.size());
					_log.info("RANK_PVP_REWARD_IDS     :" + RANK_PVP_REWARD_IDS.size());
					_log.info("RANK_PVP_REWARD_AMOUNTS :" + RANK_PVP_REWARD_AMOUNTS.size());
				}
			}
			RANK_PVP_INFO_COMMAND_ON_DEATH_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPvpInfoCommandShowOnDeathEnabled", "true"));
			RANK_PVP_DEATH_MANAGER_DETAILS_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPvpDeathManagerDetailsEnabled", "true"));
			
			i = 0;
			tempStr = ccSettings.getProperty("RankPvpDeathManagerRestrictedZonesIds");
			if (tempStr != null && tempStr.length() > 0)
			{
				for (String rZoneId : tempStr.split(","))
				{
					try
					{
						RANK_PVP_DEATH_MANAGER_RESTRICTED_ZONES_IDS.add(i, Integer.parseInt(rZoneId));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					i++;
				}
			}
			RANK_PVP_POINTS_REWARD_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPvpPointsRewardEnabled", "true"));
			
			RANK_PVP_IMAGES_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPvpImagesEnabled", "true"));
			
			RANK_PVP_ANTI_FARM_CLAN_ALLY_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPvpAntiFarmClanAllyEnabled", "true"));
			RANK_PVP_ANTI_FARM_PARTY_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPvpAntiFarmPartyEnabled", "true"));
			RANK_PVP_ANTI_FARM_IP_ENABLED = Boolean.parseBoolean(ccSettings.getProperty("RankPvpAntiFarmIpEnabled", "true"));
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
					VOTES_NEEDED_FOR_RESTART     = Integer.parseInt(AutoSettings.getProperty("VotesNeededForRestart", "20"));
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
	}
}