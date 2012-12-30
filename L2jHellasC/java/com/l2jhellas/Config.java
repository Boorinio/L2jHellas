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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javolution.util.FastList;
import javolution.util.FastMap;

import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.util.StringUtil;
import com.l2jhellas.shield.antiflood.FloodProtectorConfig;

/**
 * This class contains global server configuration.
 * It has static final fields initialized from configuration files.
 * It's initialized at the very begin of startup, and later JIT will optimize
 * away debug/unused code.
 * 
 * @author l2jhellas
 */
public final class Config
{
	protected static final Logger _log = Logger.getLogger(Config.class.getName());
	
	/********************************
	 * GameServer Config locations *
	 ********************************/
	// Main Folder
	public static final String ALT_SETTINGS_FILE = "./config/Main/Altsettings.ini";
	public static final String CLANHALL_CONFIG_FILE = "./config/Main/Clanhall.ini";
	public static final String GEO_FILE = "./config/Main/Geo.ini";
	public static final String OPTIONS_FILE = "./config/Main/Options.ini";
	public static final String ID_CONFIG_FILE = "./config/Main/IDfactory.ini";
	public static final String SEVENSIGNS_FILE = "./config/Main/Sevensigns.ini";
	public static final String SIEGE_CONFIGURATION_FILE = "./config/Main/Siege.ini";
	public static final String CHAT_FILTER_FILE = "./config/Main/ChatFilter.txt";
	public static final String RATES_CONFIG_FILE = "./config/Main/Rates.ini";
	public static final String OTHER_CONFIG_FILE = "./config/Main/Other.ini";
	public static final String FLOOD_PROTECTORS_FILE = "./config/Main/AntiFlood.ini";
	// Mods Folder
	public static final String OLYMPIAD_FILE = "./config/Mods/OlySettings.ini";
	public static final String L2JHellas_CONFIG_FILE = "./config/Mods/L2JHellas.ini";
	public static final String CHAMPION_FILE = "./config/Mods/Champions.ini";
	public static final String EVENT_CONFIG_FILE = "./config/Mods/Event.ini";
	public static final String GRANDBOSS_CONFIG_FILE = "./config/Mods/GrandBoss.ini";
	// Admin Folder
	public static final String GM_ACCESS_FILE = "./config/Admin/GMAccess.ini";
	public static final String ADMIN_CONFIG_FILE = "./config/Admin/Admin.ini";
	public static final String COMMAND_PRIVILEGES_FILE = "./config/Admin/Privileges.ini";
	// Version Folder
	public static final String SERVER_VERSION_FILE = "./config/Version/L2J Hellas Version.ini";
	// Network Folder
	public static final String CONFIGURATION_FILE = "./config/Network/Gameserver.ini";
	public static final String MMOCORE_CONFIG_FILE = "./config/Network/mmocore.ini";
	public static final String HEXID_FILE = "./config/Network/hexid.txt";
	// Telnet
	public static final String TELNET_FILE = "./config/Telnet.ini";
	
	/*********************************
	 * LoginServer Config locations *
	 *********************************/
	public static final String LOGIN_CONFIGURATION_FILE = "./config/Network/LoginServer.ini";
	
	// ================================
	// GameServer && LoginServer
	// ================================
	// ALT_SETTINGS_FILE
	public static boolean BANKING2_SYSTEM_ENABLED;
	/** Banking system */
	public static int BANKING2_SYSTEM_GOLDBARS;
	/** Banking system */
	public static int BANKING2_SYSTEM_ADENA;
	/** Banking system */
	public static boolean RESTORE_EFFECTS_ON_SUBCLASS_CHANGE;
	/** Restore Effects On Sub Change? */
	public static boolean AUTO_LOOT;
	/** Auto pick up items */
	public static boolean AUTO_LOOT_RAID;
	/** Auto pick up for Raids */
	public static boolean AUTO_LOOT_BOSS;
	/** Auto pick up for Raids */
	public static boolean AUTO_LOOT_HERBS;
	/** Auto pick up herbs */
	public static boolean AUTO_LEARN_SKILLS;
	/** Auto skill learning */
	public static boolean CHECK_SKILLS_ON_ENTER;
	public static String ALLOWED_SKILLS; // List of Skills that are allowed for
											// all Classes
	public static FastList<Integer> ALLOWED_SKILLS_LIST = new FastList<Integer>();
	public static boolean LIFE_CRYSTAL_NEEDED;
	/** Life Crystal needed to learn clan skill */
	public static boolean SP_BOOK_NEEDED;
	/** Spell Book needed to learn skill */
	public static boolean ES_SP_BOOK_NEEDED;
	/** Spell Book needet to enchant skill */
	public static boolean ALT_GAME_SKILL_LEARN;
	/** Alternative game skill learning */
	public static int ALT_PARTY_RANGE;
	/** Party range, where your hits increase gained XP */
	public static int ALT_PARTY_RANGE2;
	/** Party range, where you get xp */
	public static double ALT_WEIGHT_LIMIT;
	/** Weight limit multiplier - default 1 */
	public static boolean ALT_GAME_DELEVEL;
	/** Loss of XP on death */
	public static boolean ALT_GAME_MAGICFAILURES;
	/** Magic dmg failures */
	public static boolean ALT_GAME_CANCEL_BOW;
	/** Cancel attack bow by hit */
	public static boolean ALT_GAME_CANCEL_CAST;
	/** Cancel cast by hit */
	public static boolean ALT_GAME_SHIELD_BLOCKS;
	/** Alternative shield defence */
	public static int ALT_PERFECT_SHLD_BLOCK;
	/** Alternative Perfect shield defence rate */
	public static boolean ALT_GAME_MOB_ATTACK_AI;
	/** Alternative game mob ATTACK AI */
	public static boolean ALT_MOB_AGRO_IN_PEACEZONE;
	/** Mobs are agro in peace zone */
	public static boolean ALT_GAME_FREIGHTS;
	/** Freights can be withdrawed from any village */
	public static int ALT_GAME_FREIGHT_PRICE;
	/** Sets the price value for each freightened item */
	public static float ALT_GAME_EXPONENT_XP;
	/** Alternative eXperience Point rewards */
	public static float ALT_GAME_EXPONENT_SP;
	/** Alternative Skill Point rewards */
	public static boolean ALT_GAME_TIREDNESS;
	/** Alternative game - use tiredness, instead of CP */
	public static boolean ALT_GAME_KARMA_PLAYER_CAN_BE_KILLED_IN_PEACEZONE;
	/** Allow player with karma to be killed in peace zone ? */
	public static boolean ALT_GAME_KARMA_PLAYER_CAN_SHOP;
	/** Allow player with karma to shop ? */
	public static boolean ALT_GAME_KARMA_PLAYER_CAN_TELEPORT;
	/** Allow player with karma to use SOE or Return skill ? */
	public static boolean ALT_GAME_KARMA_PLAYER_CAN_USE_GK;
	/** Allow player with karma to use gatekeepers ? */
	public static boolean ALT_GAME_KARMA_PLAYER_CAN_TRADE;
	/** Allow player with karma to trade ? */
	public static boolean ALT_GAME_KARMA_PLAYER_CAN_USE_WAREHOUSE;
	/** Allow player with karma to use warehouse ? */
	public static boolean ALT_GAME_FREE_TELEPORT;
	/** Allow free teleporting around the world. */
	public static boolean ALT_RECOMMEND;
	/** Disallow recommend character twice or more a day ? */
	public static boolean ALT_PLAYER_PROTECTION;
	/** Disallow recommend character twice or more a day ? */
	public static int ALT_PLAYER_PROTECTION_LEVEL;
	/** Allow Players Level Difference Protection ? */
	public static boolean IS_CRAFTING_ENABLED;
	/** Crafting Enabled? */
	public static int DWARF_RECIPE_LIMIT;
	/** Dwarf Recipebook limits */
	public static int COMMON_RECIPE_LIMIT;
	/** Common Recipebook limits */
	public static boolean ALT_GAME_CREATION;
	/** Alternative game crafting */
	public static double ALT_GAME_CREATION_SPEED;
	/**
	 * Alternative crafting speed mutiplier - default 0 (fastest but still not
	 * instant)
	 */
	public static double ALT_GAME_CREATION_XP_RATE;
	/** Alternative crafting XP rate multiplier - default 1 */
	public static double ALT_GAME_CREATION_SP_RATE;
	/** Alternative crafting SP rate multiplier - default 1 */
	public static boolean ALT_BLACKSMITH_USE_RECIPES;
	/** Blacksmith use recipes to craft - default True */
	public static boolean ALT_GAME_SUBCLASS_WITHOUT_QUESTS;
	/** Allow sub-class addition without quest completion. */
	public static byte MAX_SUBCLASS;
	/** Max subclasses could be took */
	public static int MAX_PATK_SPEED;
	/** Max Atk. Speed */
	public static int MAX_MATK_SPEED;
	/** Max Cst. Speed */
	public static int MAX_PATACK;
	/** Max. P attack Speed */
	public static boolean DISABLE_GRADE_PENALTY;
	/** Grade penalty option */
	public static boolean DISABLE_WEIGHT_PENALTY;
	/** Weight penalty option */
	public static boolean CASTLE_SHIELD;
	/**
	 * Castle Shield can be equiped by all clan members if they own a castle. -
	 * default True
	 */
	public static boolean CLANHALL_SHIELD;
	/**
	 * Clan Hall Shield can be equiped by all clan members if they own a clan
	 * hall. - default True
	 */
	public static boolean APELLA_ARMORS;
	/**
	 * Apella armors can be equiped only by clan members if their class is Baron
	 * or higher - default True
	 */
	public static boolean OATH_ARMORS;
	/** Clan Oath Armors can be equiped only by clan members - default True */
	public static boolean CASTLE_CROWN;
	/** Castle Crown can be equiped only by castle lord - default True */
	public static boolean CASTLE_CIRCLETS;
	/**
	 * Castle Circlets can be equiped only by clan members if they own a castle
	 * - default True
	 */
	public static int ALT_CLAN_JOIN_DAYS;
	/** Number of days before joining a new clan */
	public static int ALT_CLAN_CREATE_DAYS;
	/** Number of days before creating a new clan */
	public static int ALT_CLAN_DISSOLVE_DAYS;
	/** Number of days it takes to dissolve a clan */
	public static int ALT_ALLY_JOIN_DAYS_WHEN_LEAVED;
	/**
	 * Number of days before joining a new alliance when clan voluntarily leave
	 * an alliance
	 */
	public static int ALT_ALLY_JOIN_DAYS_WHEN_DISMISSED;
	/**
	 * Number of days before joining a new alliance when clan was dismissed from
	 * an alliance
	 */
	public static int ALT_ACCEPT_CLAN_DAYS_WHEN_DISMISSED;
	/**
	 * Number of days before accepting a new clan for alliance when clan was
	 * dismissed from an alliance
	 */
	public static int ALT_CREATE_ALLY_DAYS_WHEN_DISSOLVED;
	/** Number of days before creating a new alliance when dissolved an alliance */
	public static int ALT_MAX_NUM_OF_CLANS_IN_ALLY;
	/** Maximum number of clans in ally */
	public static int ALT_CLAN_MEMBERS_FOR_WAR;
	/** Number of members needed to request a clan war */
	public static int ALT_REPUTATION_SCORE_PER_KILL;
	/** Clan reputation score config */
	public static boolean ALT_GAME_NEW_CHAR_ALWAYS_IS_NEWBIE;
	/** All new characters always are newbies. */
	public static boolean ALT_MEMBERS_CAN_WITHDRAW_FROM_CLANWH;
	/** Clan members with see privilege can also withdraw from clan warehouse. */
	public static boolean REMOVE_CASTLE_CIRCLETS;
	/** Remove Castle circlets after clan lose his castle? - default True */
	public static int ALT_MANOR_REFRESH_TIME;
	/** Manor Refresh Starting time */
	public static int ALT_MANOR_REFRESH_MIN;
	/** Manor Refresh Min */
	public static int ALT_MANOR_APPROVE_TIME;
	/** Manor Next Period Approve Starting time */
	public static int ALT_MANOR_APPROVE_MIN;
	/** Manor Next Period Approve Min */
	public static int ALT_MANOR_MAINTENANCE_PERIOD;
	/** Manor Maintenance Time */
	public static boolean ALT_MANOR_SAVE_ALL_ACTIONS;
	/** Manor Save All Actions */
	public static int ALT_MANOR_SAVE_PERIOD_RATE;
	/** Manor Save Period Rate */
	public static int ALT_LOTTERY_PRIZE;
	/** Initial Lottery prize */
	public static int ALT_LOTTERY_TICKET_PRICE;
	/** Lottery Ticket Price */
	public static float ALT_LOTTERY_5_NUMBER_RATE;
	/**
	 * What part of jackpot amount should receive characters who pick 5 wining
	 * numbers
	 */
	public static float ALT_LOTTERY_4_NUMBER_RATE;
	/**
	 * What part of jackpot amount should receive characters who pick 4 wining
	 * numbers
	 */
	public static float ALT_LOTTERY_3_NUMBER_RATE;
	/**
	 * What part of jackpot amount should receive characters who pick 3 wining
	 * numbers
	 */
	public static int ALT_LOTTERY_2_AND_1_NUMBER_PRIZE;
	/**
	 * How much adena receive characters who pick two or less of the winning
	 * number
	 */
	public static boolean ALT_DEV_NO_QUESTS;
	/** Don't load quests */
	public static boolean ALT_DEV_NO_SPAWNS;
	/** Don't load spawns */
	public static int FS_TIME_ATTACK;
	/** 4 sepulschers config */
	public static int FS_TIME_COOLDOWN;
	/** 4 sepulschers config */
	public static int FS_TIME_ENTRY;
	/** 4 sepulschers config */
	public static int FS_TIME_WARMUP;
	/** 4 sepulschers config */
	public static int FS_PARTY_MEMBER_COUNT;
	/** 4 seps config */
	public static int RIFT_MIN_PARTY_SIZE;
	/** Minimum siz e of a party that may enter dimensional rift */
	public static int RIFT_SPAWN_DELAY;
	/**
	 * Time in ms the party has to wait until the mobs spawn when entering a
	 * room
	 */
	public static int RIFT_MAX_JUMPS;
	/** Amount of random rift jumps before party is ported back */
	public static int RIFT_AUTO_JUMPS_TIME_MIN;
	/** Random time between two jumps in dimensional rift - in seconds */
	public static int RIFT_AUTO_JUMPS_TIME_MAX;
	/** Random time between two jumps in dimensional rift - in seconds */
	public static float RIFT_BOSS_ROOM_TIME_MUTIPLY;
	/** Time multiplier for boss room */
	public static int RIFT_ENTER_COST_RECRUIT;
	/** Dimensional Fragment cost for entering rift */
	public static int RIFT_ENTER_COST_SOLDIER;
	/** Dimensional Fragment cost for entering rift */
	public static int RIFT_ENTER_COST_OFFICER;
	/** Dimensional Fragment cost for entering rift */
	public static int RIFT_ENTER_COST_CAPTAIN;
	/** Dimensional Fragment cost for entering rift */
	public static int RIFT_ENTER_COST_COMMANDER;
	/** Dimensional Fragment cost for entering rift */
	public static int RIFT_ENTER_COST_HERO;
	/** Dimensional Fragment cost for entering rift */
	public static int MAX_CHAT_LENGTH;
	/** Max chat length */
	public static int CRUMA_TOWER_LEVEL_RESTRICT;
	/** LvL allowed in Cruma Tower */
	public static boolean ALT_RESPAWN_POINT;
	/** Custom respawn point */
	public static int ALT_RESPAWN_POINT_X;
	/** Custom respawn point */
	public static int ALT_RESPAWN_POINT_Y;
	/** Custom respawn point */
	public static int ALT_RESPAWN_POINT_Z;
	/** Custom respawn point */
	public static float DANCE_TIME_MULTIPLIER;
	/** Dance time multiplier */
	public static float BUFF_TIME_MULTIPLIER;
	/** Buff time multiplier */
	public static float SPIRIT_TIME_MULTIPLIER;
	/** Spirit time multiplier */
	public static byte BUFFS_MAX_AMOUNT;
	/** Max amount of buffs */
	public static boolean ALT_GAME_VIEWNPC;
	/** View npc stats/drop by shift-cliking it for nongm-players */
	public static boolean USE_3D_MAP;
	/** Use 3D Map ? */
	public static boolean ALLOW_HIT_OWNER;
	/** Allows summons/pets hit its' master */
	public static byte FRONT_BLOW_SUCCESS;
	/** Daggers skills success rate config */
	public static byte BACK_BLOW_SUCCESS;
	/** Daggers skills success rate config */
	public static byte SIDE_BLOW_SUCCESS;
	/** Daggers skills success rate config */
	// MMocore
	public static int MMO_SELECTOR_SLEEP_TIME;
	public static int MMO_MAX_SEND_PER_PASS;
	public static int MMO_MAX_READ_PER_PASS;
	public static int MMO_HELPER_BUFFER_COUNT;
	public static int MMO_IO_SELECTOR_THREAD_COUNT;
	// GRAND BOSS SETTINGS
	public static int Antharas_Wait_Time;
	public static int Valakas_Wait_Time;
	public static int Interval_Of_Antharas_Spawn;
	public static int Random_Of_Antharas_Spawn;
	public static int Interval_Of_Valakas_Spawn;
	public static int Interval_Of_Core_Spawn;
	public static int Random_Of_Core_Spawn;
	public static int Interval_Of_Orfen_Spawn;
	public static int Random_Of_Orfen_Spawn;
	public static int Interval_Of_QueenAnt_Spawn;
	public static int Random_Of_QueenAnt_Spawn;
	public static int Interval_Of_Zaken_Spawn;
	public static int Random_Of_Zaken_Spawn;
	public static int Interval_Of_Sailren_Spawn;
	public static int Random_Of_Sailren_Spawn;
	
	// CLANHALL_CONFIG_FILE
	public static long CH_TELE_FEE_RATIO;
	/** Teleports costs */
	public static int CH_TELE1_FEE;
	/** Teleports costs */
	public static int CH_TELE2_FEE;
	/** Teleports costs */
	public static int CH_TELE3_FEE;
	/** Teleports costs */
	public static int CH_TELE4_FEE;
	/** Teleports costs */
	public static long CH_SUPPORT_FEE_RATIO;
	/** Buffs costs */
	public static int CH_SUPPORT1_FEE;
	/** Buffs costs */
	public static int CH_SUPPORT2_FEE;
	/** Buffs costs */
	public static int CH_SUPPORT3_FEE;
	/** Buffs costs */
	public static int CH_SUPPORT4_FEE;
	/** Buffs costs */
	public static int CH_SUPPORT5_FEE;
	/** Buffs costs */
	public static int CH_SUPPORT6_FEE;
	/** Buffs costs */
	public static int CH_SUPPORT7_FEE;
	/** Buffs costs */
	public static int CH_SUPPORT8_FEE;
	/** Buffs costs */
	public static int CH_SUPPORT9_FEE;
	/** Buffs costs */
	public static long CH_MPREG_FEE_RATIO;
	/** MpReg costs */
	public static int CH_MPREG1_FEE;
	/** MpReg costs */
	public static int CH_MPREG2_FEE;
	/** MpReg costs */
	public static int CH_MPREG3_FEE;
	/** MpReg costs */
	public static int CH_MPREG4_FEE;
	/** MpReg costs */
	public static int CH_MPREG5_FEE;
	/** MpReg costs */
	public static long CH_HPREG_FEE_RATIO;
	/** HpReg costs */
	public static int CH_HPREG1_FEE;
	/** HpReg costs */
	public static int CH_HPREG2_FEE;
	/** HpReg costs */
	public static int CH_HPREG3_FEE;
	/** HpReg costs */
	public static int CH_HPREG4_FEE;
	/** HpReg costs */
	public static int CH_HPREG5_FEE;
	/** HpReg costs */
	public static int CH_HPREG6_FEE;
	/** HpReg costs */
	public static int CH_HPREG7_FEE;
	/** HpReg costs */
	public static int CH_HPREG8_FEE;
	/** HpReg costs */
	public static int CH_HPREG9_FEE;
	/** HpReg costs */
	public static int CH_HPREG10_FEE;
	/** HpReg costs */
	public static int CH_HPREG11_FEE;
	/** HpReg costs */
	public static int CH_HPREG12_FEE;
	/** HpReg costs */
	public static int CH_HPREG13_FEE;
	/** HpReg costs */
	public static long CH_EXPREG_FEE_RATIO;
	/** Exp Regain costs */
	public static int CH_EXPREG1_FEE;
	/** Exp Regain costs */
	public static int CH_EXPREG2_FEE;
	/** Exp Regain costs */
	public static int CH_EXPREG3_FEE;
	/** Exp Regain costs */
	public static int CH_EXPREG4_FEE;
	/** Exp Regain costs */
	public static int CH_EXPREG5_FEE;
	/** Exp Regain costs */
	public static int CH_EXPREG6_FEE;
	/** Exp Regain costs */
	public static int CH_EXPREG7_FEE;
	/** Exp Regain costs */
	public static long CH_ITEM_FEE_RATIO;
	/** Items recovery costs */
	public static int CH_ITEM1_FEE;
	/** Items recovery costs */
	public static int CH_ITEM2_FEE;
	/** Items recovery costs */
	public static int CH_ITEM3_FEE;
	/** Items recovery costs */
	public static long CH_CURTAIN_FEE_RATIO;
	/** Interier costs */
	public static int CH_CURTAIN1_FEE;
	/** Interier costs */
	public static int CH_CURTAIN2_FEE;
	/** Interier costs */
	public static long CH_FRONT_FEE_RATIO;
	/** Interier costs */
	public static int CH_FRONT1_FEE;
	/** Interier costs */
	public static int CH_FRONT2_FEE;
	/** Interier costs */
	
	// COMMAND_PRIVILEGES_FILE --> empty - cheking elsewere.
	
	// SERVER_VERSION_FILE
	public static String SERVER_VERSION;
	/** Server version */
	public static String SERVER_BUILD_DATE;
	/** Date of server build */
	
	// GM_ACCESS_FILE
	public static int GM_ACCESSLEVEL;
	/** General GM access level */
	public static int GM_MIN;
	/** General GM Minimal AccessLevel */
	public static int GM_ALTG_MIN_LEVEL;
	/** Minimum privileges level for a GM to do Alt+G */
	public static int GM_ANNOUNCE;
	/** General GM AccessLevel to change announcements */
	public static int GM_BAN;
	/** General GM AccessLevel can /ban /unban */
	public static int GM_BAN_CHAT;
	/** General GM AccessLevel can /ban /unban for chat */
	public static int GM_CREATE_ITEM;
	/** General GM AccessLevel can /create_item and /gmshop */
	public static int GM_DELETE;
	/** General GM AccessLevel can /delete */
	public static int GM_KICK;
	/** General GM AccessLevel can /kick /disconnect */
	public static int GM_MENU;
	/** General GM AccessLevel for access to GMMenu */
	public static int GM_GODMODE;
	/** General GM AccessLevel to use god mode command */
	public static int GM_CHAR_EDIT;
	/** General GM AccessLevel with character edit rights */
	public static int GM_CHAR_EDIT_OTHER;
	/** General GM AccessLevel with edit rights for other characters */
	public static int GM_CHAR_VIEW;
	/** General GM AccessLevel with character view rights */
	public static int GM_NPC_EDIT;
	/** General GM AccessLevel with NPC edit rights */
	public static int GM_NPC_VIEW;
	/** General GM AccessLevel with NPC view rights */
	public static int GM_TELEPORT;
	/** General GM AccessLevel to teleport to any location */
	public static int GM_TELEPORT_OTHER;
	/** General GM AccessLevel to teleport character to any location */
	public static int GM_RESTART;
	/** General GM AccessLevel to restart server */
	public static int GM_MONSTERRACE;
	/** General GM AccessLevel for MonsterRace */
	public static int GM_RIDER;
	/** General GM AccessLevel to ride Wyvern */
	public static int GM_ESCAPE;
	/** General GM AccessLevel to unstuck without 5min delay */
	public static int GM_FIXED;
	/** General GM AccessLevel to resurect fixed after death */
	public static int GM_CREATE_NODES;
	/** General GM AccessLevel to create Path Nodes */
	public static int GM_ENCHANT;
	/** General GM AccessLevel with Enchant rights */
	public static int GM_DOOR;
	/** General GM AccessLevel to close/open Doors */
	public static int GM_RES;
	/** General GM AccessLevel with Resurrection rights */
	public static int GM_PEACEATTACK;
	/** General GM AccessLevel to attack in the peace zone */
	public static int GM_HEAL;
	/** General GM AccessLevel to heal */
	public static int GM_UNBLOCK;
	/** General GM AccessLevel to unblock IPs detected as hack IPs */
	public static int GM_CACHE;
	/** General GM AccessLevel to use Cache commands */
	public static int GM_TALK_BLOCK;
	/** General GM AccessLevel to use test&st commands */
	public static int GM_TEST;
	/** General GM AccessLevel to use //reload command */
	public static boolean GM_DISABLE_TRANSACTION;
	/** Disable transaction on AccessLevel **/
	public static int GM_TRANSACTION_MIN;
	/** GM transactions disabled from this range */
	public static int GM_TRANSACTION_MAX;
	/** GM transactions disabled to this range */
	public static int GM_CAN_GIVE_DAMAGE;
	/** Minimum level to allow a GM giving damage */
	public static int GM_DONT_TAKE_EXPSP;
	/** Minimum level to don't give Exp/Sp in party */
	public static int GM_DONT_TAKE_AGGRO;
	/** Minimum level to don't take aggro */
	
	// ADMIN_FILE
	public static boolean ALT_PRIVILEGES_ADMIN;
	/** Alternative privileges for admin */
	public static boolean ALT_PRIVILEGES_SECURE_CHECK;
	/** Alternative secure check privileges */
	public static int ALT_PRIVILEGES_DEFAULT_LEVEL;
	/** Alternative default level for privileges */
	public static boolean GM_HERO_AURA;
	/** Place an aura around the GM ? */
	public static boolean GM_STARTUP_INVULNERABLE;
	/** Set the GM invulnerable at startup ? */
	public static boolean GM_STARTUP_INVISIBLE;
	/** Set the GM invisible at startup ? */
	public static boolean GM_STARTUP_SILENCE;
	/** Set silence to GM at startup ? */
	public static boolean GM_STARTUP_AUTO_LIST;
	/** Add GM in the GM list at startup ? */
	public static String GM_ADMIN_MENU_STYLE;
	/** Change the way admin panel is shown */
	public static boolean PETITIONING_ALLOWED;
	/** Allow petition ? */
	public static int MAX_PETITIONS_PER_PLAYER;
	/** Maximum number of petitions per player */
	public static int MAX_PETITIONS_PENDING;
	/** Maximum number of petitions pending */
	public static boolean GM_NAME_COLOR_ENABLED;
	/** GM and ADM Name Color Here by Trinix */
	public static int ADMIN_NAME_COLOR;
	public static int GM_NAME_COLOR;
	public static boolean GM_TITLE_COLOR_ENABLED; // TODO Gm color here
	public static int ADMIN_TITLE_COLOR;
	public static int GM_TITLE_COLOR;
	
	// ID_CONFIG_FILE
	public static ObjectMapType MAP_TYPE;
	/** Type of map object */
	public static ObjectSetType SET_TYPE;
	/** Type of set object */
	public static IdFactoryType IDFACTORY_TYPE;
	/** ID Factory type */
	public static boolean BAD_ID_CHECKING;
	/** Check for bad ID ? */
	
	// l2jhellas_CONFIG_FILE
	public static boolean MOD_ALLOW_WEDDING;
	/** Wedding system */
	public static int MOD_WEDDING_PRICE;
	/** Wedding system */
	public static boolean MOD_WEDDING_PUNISH_INFIDELITY;
	/** Wedding system */
	public static boolean MOD_WEDDING_TELEPORT;
	/** Wedding system */
	public static int MOD_WEDDING_TELEPORT_PRICE;
	/** Wedding system */
	public static int MOD_WEDDING_TELEPORT_DURATION;
	/** Wedding system */
	public static boolean MOD_WEDDING_SAMESEX;
	/** Wedding system */
	public static boolean MOD_WEDDING_FORMALWEAR;
	/** Wedding system */
	public static int MOD_WEDDING_DIVORCE_COSTS;
	/** Wedding system */
	public static boolean MOD_WEDDING_ANNOUNCE;
	/** Announce The Wedding? */
	/* ----------------------------------------------------------- */
	public static boolean BANKING_SYSTEM_ENABLED;
	/** Banking system */
	public static int BANKING_SYSTEM_GOLDBARS;
	/** Banking system */
	public static int BANKING_SYSTEM_ADENA;
	/** Banking system */
	public static int BANKING_SYSTEM_ITEM;
	/** Mod by NsStop - item for banking */
	/* ----------------------------------------------------------- */
	public static boolean BANKINGALTERNATE_SYSTEM_ENABLED;
	/** Banking system */
	public static int BANKINGALTERNATE_SYSTEM_GOLDBARS;
	/** Banking system */
	public static int BANKINGALTERNATE_SYSTEM_ADENA;
	/** Banking system */
	public static boolean CHAR_TITLE;
	/** This enables starting title. */
	public static String ADD_CHAR_TITLE;
	/** This is the new players title. */
	public static boolean GM_ANNOUNCER_NAME;
	/** show name for announcer */
	public static boolean KICK_L2WALKER;
	/** L2WalkerProtection */
	public static boolean ALLOW_CREATE_LVL;
	/** Custom starting lvl */
	public static int CUSTOM_START_LVL;
	/** Custom starting lvl */
	public static boolean ALLOW_HERO_SUBSKILL;
	/** Hero skills stack for subs */
	public static boolean HERO_CUSTOM_ITEMS;
	public static int HERO_COUNT;
	/** How many times it needs to be hero in row */
	public static int GM_OVER_ENCHANT;
	/** Protection from corrupt GM */
	public static boolean ALLOW_LOW_LEVEL_TRADE;
	/** 76 lvl trading only */
	public static boolean SHOW_HTML_WELCOME;
	public static boolean SHOW_HTML_GM_WELCOME;
	/** Show HTML welcome screen on login */
	public static int RUN_SPD_BOOST;
	/** Increase Running speed by ... */
	public static int MAX_RUN_SPEED;
	/** Max Running speed */
	public static int MAX_PCRIT_RATE;
	/** Max Physical critical rate */
	public static int MAX_MCRIT_RATE;
	/** Max Magic critical rate */
	public static int ALT_MAX_EVASION;
	/** Max Evasion rate */
	public static float ALT_DAGGER_DMG_VS_HEAVY;
	/** Damage for dagger skills VS heavy */
	public static float ALT_DAGGER_DMG_VS_ROBE;
	/** Damage for dagger skills VS robe */
	public static float ALT_DAGGER_DMG_VS_LIGHT;
	/** Damage for dagger skills VS light */
	public static boolean ONLINE_VOICE_ALLOW;
	/** Custom voice comands */
	public static boolean ALLOW_CLAN_LEADER_COMMAND;
	/** Custom voice comands */
	public static boolean ALLOW_VERSION_COMMAND;
	/** Custom voice comands */
	public static boolean ALLOW_STAT_COMMAND;
	/** Custom voice comands */
	public static boolean ALT_GAME_FLAGGED_PLAYER_CAN_USE_GK;
	/** Custom voice comands */
	public static boolean ALLOW_TVTCMDS_COMMAND;
	/** Custom voice comands */
	public static boolean ALLOW_PLAYERS_REFUSAL;
	/** Custom voice comands */
	public static boolean ALLOW_INFO_COMMAND;
	/** Custom voice comands */
	public static boolean ALLOW_TRADEOFF_COMMAND;
	/** Custom voice comands */
	public static boolean ALT_SUBCLASS_SKILLS;
	/** Skills stuck in all subs */
	public static boolean DONATOR_NAME_COLOR_ENABLED;
	/** KidZor: Name Color System of Donator enable */
	public static int DONATOR_NAME_COLOR;
	/** KidZor: Color of donator name */
	public static boolean DONATOR_TITLE_COLOR_ENABLED;
	/** Donator Title Color Enable */
	public static int DONATOR_TITLE_COLOR;
	/** Color of Title Donator */
	public static boolean ALLOW_VIPTELEPORT_COMMAND;
	/** vicfelipe: Area vip Command */
	public static int VIP_X;
	public static int VIP_Y;
	public static int VIP_Z;
	public static boolean WELCOME_TEXT_FOR_DONATOR_ENABLED;
	/** WELCOME_TEXT_FOR_DONATOR ENABLED */
	public static String WELCOME_TEXT_FOR_DONATOR_1;
	/** Welcome text for donators 1 */
	public static String WELCOME_TEXT_FOR_DONATOR_2;
	/** Welcome text for donators 2 */
	public static boolean PVPEXPSP_SYSTEM;
	/** Enable Exp/Sp award on PvP */
	public static int ADD_EXP;
	/** Add Exp At Pvp! */
	public static int ADD_SP;
	/** Add Sp At Pvp! */
	public static String ABORT_RR;
	/** Server name on shutdown/restart */
	public static boolean SPAWN_CHAR;
	/** Custom created char spawn spot */
	public static int SPAWN_X;
	/** X Coordinate of the SPAWN_CHAR setting. */
	public static int SPAWN_Y;
	/** Y Coordinate of the SPAWN_CHAR setting. */
	public static int SPAWN_Z;
	/** Z Coordinate of the SPAWN_CHAR setting. */
	public static int CHAT_FILTER_PUNISHMENT_PARAM1;
	public static int CHAT_FILTER_PUNISHMENT_PARAM2;
	public static boolean USE_SAY_FILTER;
	public static String CHAT_FILTER_CHARS;
	public static String CHAT_FILTER_PUNISHMENT;
	public static ArrayList<String> FILTER_LIST = new ArrayList<String>();
	public static boolean MOD_GVE_ENABLE_FACTION;
	/** Good vs Evil system - GVE */
	public static int PRIMAR_X;
	/** Good vs Evil system - GVE */
	public static int PRIMAR_Y;
	/** Good vs Evil system - GVE */
	public static int PRIMAR_Z;
	/** Good vs Evil system - GVE */
	public static int GOODX;
	/** Good vs Evil system - GVE */
	public static int GOODY;
	/** Good vs Evil system - GVE */
	public static int GOODZ;
	/** Good vs Evil system - GVE */
	public static int EVILX;
	/** Good vs Evil system - GVE */
	public static int EVILY;
	/** Good vs Evil system - GVE */
	public static int EVILZ;
	/** Good vs Evil system - GVE */
	public static String MOD_GVE_NAME_TEAM_GOOD;
	/** Good vs Evil system - GVE */
	public static String MOD_GVE_NAME_TEAM_EVIL;
	/** Good vs Evil system - GVE */
	public static int MOD_GVE_COLOR_NAME_GOOD;
	/** Good vs Evil system - GVE */
	public static int MOD_GVE_COLOR_NAME_EVIL;
	/** Good vs Evil system - GVE */
	public static boolean MOD_GVE_GET_ADENA_BY_PVP;
	/** Good vs Evil system - GVE */
	public static int MOD_GVE_AMMOUNT_ADENA_BY_PVP;
	/** Good vs Evil system - GVE */
	public static boolean MOD_GVE_ACTIVE_ANIM_SS;
	/** Good vs Evil system - GVE */
	public static boolean ALLOW_CHAR_KILL_PROTECT;
	/** Protects low lvl chars from being pked */
	public static boolean ALLOW_AWAY_STATUS;
	/** Away system */
	public static boolean AWAY_PEACE_ZONE;
	/** Away system */
	public static boolean AWAY_ALLOW_INTERFERENCE;
	/** Away system */
	public static boolean AWAY_PLAYER_TAKE_AGGRO;
	/** Away system */
	public static int AWAY_TITLE_COLOR;
	/** Away system */
	public static int AWAY_TIMER;
	/** Away system */
	public static int BACK_TIMER;
	/** Away system */
	public static int DUEL_COORD_X;
	/** Party dueling */
	public static int DUEL_COORD_Y;
	/** Party dueling */
	public static int DUEL_COORD_Z;
	/** Party dueling */
	public static boolean ALLOW_DAGGERS_WEAR_HEAVY;
	/** Can daggers wear heavy armor? */
	public static boolean ALLOW_ARCHERS_WEAR_HEAVY;
	/** Can archers wear heavy armor? */
	public static boolean CLAN_LEADER_COLOR_ENABLED;
	/** Clan leader name color */
	public static int CLAN_LEADER_COLOR;
	/** Clan leader name color */
	public static int CLAN_LEADER_COLOR_CLAN_LEVEL;
	/** Advanced Privated Message System */
	public static boolean ENABLED_MESSAGE_SYSTEM;
	/** Clan leader name color */
	public static enum ClanLeaderColored /** Clan leader name color */
	{
		name, title
	}
	
	public static boolean ALLOW_USE_HERO_ITEM_ON_SUBCLASS;
	public static int SOUL_CRYSTAL_BREAK_CHANCE;
	public static int SOUL_CRYSTAL_LEVEL_CHANCE;
	public static int ALTERNATIVE_ENCHANT_VALUE;
	public static boolean MAX_LVL_AFTER_SUB;
	
	// CHAMPION_FILE
	public static int CHAMPION_FREQUENCY;
	/** Frequency of spawn */
	public static int CHAMPION_HP;
	/** Hp multiplier */
	public static boolean CHAMPION_PASSIVE;
	public static String CHAMPION_TITLE;
	public static int CHAMPION_ADENA;
	/** Adena/Sealstone reward multiplier */
	public static int CHAMPION_REWARDS;
	/** Drop/Spoil reward multiplier */
	public static int CHAMPION_EXP_SP;
	/** Exp/Sp reward multiplier */
	public static int CHAMPION_MIN_LEVEL;
	/** Champion Minimum Level */
	public static int CHAMPION_MAX_LEVEL;
	/** Champion Maximum Level */
	public static int CHAMPION_SPCL_CHANCE;
	/** Chance in % to drop an special reward item. */
	public static int CHAMPION_SPCL_ITEM;
	/** Item ID that drops from Champs. */
	public static int CHAMPION_SPCL_QTY;
	/** Amount of special champ drop items. */
	public static int CHAMPION_SPCL_LVL_DIFF;
	/**
	 * Level diff with mob level is more this value - don't drop an special
	 * reward item.
	 */
	public static float CHAMPION_HP_REGEN;
	/** Hp.reg multiplier */
	public static float CHAMPION_ATK;
	/** P.Atk & M.Atk multiplier */
	public static float CHAMPION_SPD_ATK;
	/** Attack speed multiplier */
	public static boolean CHAMPION_BOSS;
	/** Bosses can be champions */
	public static boolean CHAMPION_MINIONS;
	/** set Minions to champions when leader champion */
	public static boolean CHAMPION_ENABLE;
	/** Enable or Disable */
	
	// GEO_FILE
	public static boolean ACCEPT_GEOEDITOR_CONN;
	/** Allow/disallow GeoData Editor connection */
	public static int COORD_SYNCHRONIZE;
	/**
	 * 0 - no; 1 - Client -> Server; 2 - Server -> Client; 3 - Client <->
	 * Server; -1 - z axis
	 */
	public static int GEODATA;
	/** GeoData 0/1/2 */
	public static boolean FORCE_GEODATA;
	/** Force loading GeoData to psychical memory */
	
	// OLYMPIAD_FILE
	public static int OLY_START_TIME;
	/** Olympiad Competition Starting time */
	public static int OLY_MIN;
	/** Olympiad Minutes */
	public static long OLY_CPERIOD;
	/** Olympiad Competition Period */
	public static long OLY_BATTLE;
	/** Olympiad Battle Period */
	public static long OLY_BWAIT;
	/** Olympiad Battle Wait */
	public static long OLY_IWAIT;
	/** Olympiad Inital Wait */
	public static long OLY_WPERIOD;
	/** Olympaid Weekly Period */
	public static long OLY_VPERIOD;
	/** Olympaid Validation Period */
	public static boolean OLY_SAME_IP;
	/** Oly same ip protection */
	public static int OLY_ENCHANT_LIMIT;
	/** Olympiad max enchant limitation */
	public static FastList<Integer> OLY_RESTRICTED_ITEMS_LIST = new FastList<Integer>();
	/** Items IDs, that can't be used in oly */
	
	// OPTIONS_FILE
	public static boolean TEST_SERVER;
	/** Set if this server is a test server used for development */
	public static boolean SERVER_LIST_TESTSERVER;
	/** Display test server in the list of servers ? */
	public static boolean EVERYBODY_HAS_ADMIN_RIGHTS;
	/** For test servers - everybody has admin rights */
	public static boolean SERVER_LIST_BRACKET;
	/** Displays [] in front of server name ? */
	public static boolean SERVER_LIST_CLOCK;
	/** Displays a clock next to the server name ? */
	public static boolean SERVER_GMONLY;
	/** Set the server as gm only at startup ? */
	public static int ZONE_TOWN;
	/**
	 * 0 - Towns Peace; 1 - Towns in Siege war zones (for participants); 2 -
	 * Towns war all time
	 */
	public static String DEFAULT_GLOBAL_CHAT;
	/** Global chat state */
	public static String DEFAULT_TRADE_CHAT;
	/** Trade chat state */
	public static int DEFAULT_PUNISH;
	/** Default punishment for illegal actions */
	public static int DEFAULT_PUNISH_PARAM;
	/** Parameter for default punishment */
	public static boolean BYPASS_VALIDATION;
	/** Bypass exploit protection ? */
	public static boolean GAMEGUARD_ENFORCE;
	/** Enforce gameguard query on character login ? */
	public static boolean GAMEGUARD_PROHIBITACTION;
	/**
	 * Don't allow player to perform trade,talk with npc and move until
	 * gameguard reply received ?
	 */
	public static int DELETE_DAYS;
	/** Period in days after which character is deleted */
	public static boolean ALLOW_DISCARDITEM;
	/** Allow Discard item ? */
	public static int AUTODESTROY_ITEM_AFTER;
	/** Time after which item will auto-destroy */
	public static int HERB_AUTO_DESTROY_TIME;
	/** Auto destroy herb time */
	public static String PROTECTED_ITEMS;
	/** List of items that will not be destroyed (seperated by ",") */
	public static List<Integer> LIST_PROTECTED_ITEMS = new FastList<Integer>();
	/** List of items that will not be destroyed */
	public static boolean DESTROY_DROPPED_PLAYER_ITEM;
	/** Auto destroy nonequipable items dropped by players */
	public static boolean DESTROY_EQUIPABLE_PLAYER_ITEM;
	/** Auto destroy equipable items dropped by players */
	public static boolean SAVE_DROPPED_ITEM;
	/** Save items on ground for restoration on server restart */
	public static boolean EMPTY_DROPPED_ITEM_TABLE_AFTER_LOAD;
	/** Empty table ItemsOnGround after load all items */
	public static int SAVE_DROPPED_ITEM_INTERVAL;
	/** Time interval to save into db items on ground */
	public static boolean CLEAR_DROPPED_ITEM_TABLE;
	/** Clear all items stored in ItemsOnGround table */
	public static boolean AUTODELETE_INVALID_QUEST_DATA;
	/** Auto-delete invalid quest data ? */
	public static boolean PRECISE_DROP_CALCULATION;
	/** Accept precise drop calculation ? */
	public static boolean MULTIPLE_ITEM_DROP;
	/** Accept multi-items drop ? */
	public static boolean FORCE_INVENTORY_UPDATE;
	/** Force full item inventory packet to be sent for any item chang */
	public static boolean LAZY_CACHE;
	/** HTML cache enabled/disabled */
	public static int MAX_DRIFT_RANGE;
	/** Maximum range mobs can randomly go from spawn point */
	public static int MIN_NPC_ANIMATION;
	/** Minimal time between 2 animations of a NPC */
	public static int MAX_NPC_ANIMATION;
	/** Maximal time between 2 animations of a NPC */
	public static int MIN_MONSTER_ANIMATION;
	/** Minimal time between animations of a MONSTER */
	public static int MAX_MONSTER_ANIMATION;
	/** Maximal time between animations of a MONSTER */
	public static boolean SERVER_NEWS;
	/** Show "data/html/servnews.htm" whenever a character enters world. */
	public static boolean SHOW_NPC_LVL;
	/** Show L2Monster level and aggro ? */
	public static boolean ACTIVATE_POSITION_RECORDER;
	/** Activate position recorder ? */
	public static boolean ALLOW_WAREHOUSE;
	/** Allow warehouse ? */
	public static boolean WAREHOUSE_CACHE;
	/** Allow warehouse cache? */
	public static int WAREHOUSE_CACHE_TIME;
	/** How long store WH datas */
	public static boolean ALLOW_FREIGHT;
	/** Allow freight ? */
	public static boolean ALLOW_WEAR;
	/** Allow wear ? (try on in shop) */
	public static int WEAR_DELAY;
	/** Duration of the try on after which items are taken back */
	public static int WEAR_PRICE;
	/** Price of the try on of one item */
	public static boolean ALLOW_LOTTERY;
	/** Allow lottery ? */
	public static boolean ALLOW_RACE;
	/** Allow race ? */
	public static boolean ALLOW_WATER;
	/** Allow water ? */
	public static boolean ALLOW_RENTPET;
	/** Allow rent pet ? */
	public static boolean ALLOWFISHING;
	/** Allow fishing ? */
	public static boolean ALLOW_BOAT;
	/** Allow boat ? */
	public static boolean ALLOW_CURSED_WEAPONS;
	/** Allow cursed weapons ? */
	public static boolean ALLOW_MANOR;
	/** Allow Manor system */
	public static boolean ALLOW_NPC_WALKERS;
	
	/** Allow WALKER NPC */
	public static enum L2WalkerAllowed /**
	 * Enumeration describing values for
	 * Allowing the use of L2Walker client
	 */
	{
		True, False, GM
	}
	
	public static L2WalkerAllowed ALLOW_L2WALKER_CLIENT;
	/** Allow the use of L2Walker client ? */
	public static int L2WALKER_REVISION;
	/** Revision of L2Walker */
	public static boolean AUTOBAN_L2WALKER_ACC;
	/** Auto-ban client that use L2Walker ? */
	public static boolean GM_EDIT;
	/** GM Edit allowed on Non Gm players? */
	public static boolean ONLY_GM_ITEMS_FREE;
	/** Only GM buy items for free */
	public static boolean RAID_DISABLE_CURSE;
	/** Allow RaidBoss Petrified if player have +9 lvl to RB */
	public static boolean LOG_CHAT;
	/** Logging Chat Window */
	public static boolean LOG_ITEMS;
	/** Logging Item Window */
	public static boolean GMAUDIT;
	/** GM Audit ? */
	public static String COMMUNITY_TYPE;
	/** Community board */
	public static String BBS_DEFAULT;
	/** ??? */
	public static boolean SHOW_LEVEL_COMMUNITYBOARD;
	/** Show level of the community board ? */
	public static boolean SHOW_STATUS_COMMUNITYBOARD;
	/** Show status of the community board ? */
	public static int NAME_PAGE_SIZE_COMMUNITYBOARD;
	/** Size of the name page on the community board */
	public static int NAME_PER_ROW_COMMUNITYBOARD;
	/** Name per row on community board */
	public static int THREAD_P_EFFECTS;
	/** Thread pool size effect */
	public static int THREAD_P_GENERAL;
	/** Thread pool size general */
	public static int GENERAL_PACKET_THREAD_CORE_SIZE;
	/** Packet max thread */
	public static int IO_PACKET_THREAD_CORE_SIZE;
	public static int GENERAL_THREAD_CORE_SIZE;
	/** General max thread */
	public static int AI_MAX_THREAD;
	/** AI max thread */
	public static int PACKET_LIFETIME;
	/** Time after which a packet is considered as lost */
	public static boolean GRIDS_ALWAYS_ON;
	/** Grid Options */
	public static int GRID_NEIGHBOR_TURNON_TIME;
	/** Grid Options */
	public static int GRID_NEIGHBOR_TURNOFF_TIME;
	/** Grid Options */
	public static int FLOODPROTECTOR_INITIALSIZE;
	
	// OTHER_CONFIG_FILE
	public static int STARTING_ADENA;
	/** Amount of adenas when starting a new character */
	public static int STARTING_ANCIENT;
	/** Amount of ancient adenas when starting a new character */
	public static int WYVERN_SPEED;
	/** Speed of Weverns */
	public static int STRIDER_SPEED;
	/** Speed of Striders */
	public static boolean ALLOW_WYVERN_UPGRADER;
	/** Allow Wyvern Upgrader ? */
	public static boolean EFFECT_CANCELING;
	/** Cancels lesser buff effect */
	public static boolean ALLOW_GUARDS;
	/** Disable the use of guards against agressive monsters ? */
	public static boolean DEEPBLUE_DROP_RULES;
	/** Deep Blue Mobs' Drop Rules Enabled */
	public static int INVENTORY_MAXIMUM_NO_DWARF;
	/** Maximum inventory slots limits for non dwarf characters */
	public static int INVENTORY_MAXIMUM_DWARF;
	/** Maximum inventory slots limits for dwarf characters */
	public static int INVENTORY_MAXIMUM_GM;
	/** Maximum inventory slots limits for GM */
	public static int WAREHOUSE_SLOTS_NO_DWARF;
	/** Maximum inventory slots limits for non dwarf warehouse */
	public static int WAREHOUSE_SLOTS_DWARF;
	/** Maximum inventory slots limits for dwarf warehouse */
	public static int WAREHOUSE_SLOTS_CLAN;
	/** Maximum inventory slots limits for clan warehouse */
	public static int FREIGHT_SLOTS;
	/** Maximum inventory slots limits for freight */
	public static boolean ENABLE_DWARF_ENCHANT_BONUS;
	/** Dwarf enchant bonus configs */
	public static int DWARF_ENCHANT_MIN_LEVEL;
	/** Dwarf enchant bonus configs */
	public static int DWARF_ENCHANT_BONUS;
	/** Dwarf enchant bonus configs */
	public static double HP_REGEN_MULTIPLIER;
	/** Multiplier for character HP regeneration */
	public static double MP_REGEN_MULTIPLIER;
	/** Mutilplier for character MP regeneration */
	public static double CP_REGEN_MULTIPLIER;
	/** Multiplier for character CP regeneration */
	public static double RAID_HP_REGEN_MULTIPLIER;
	/** Multiplier for Raid boss HP regeneration */
	public static double RAID_MP_REGEN_MULTIPLIER;
	/** Mulitplier for Raid boss MP regeneration */
	public static double RAID_P_DEFENCE_MULTIPLIER;
	/** Multiplier for Raid boss physical defense multiplier */
	public static double RAID_M_DEFENCE_MULTIPLIER;
	/** Multiplier for Raid boss magical defense multiplier */
	public static float RAID_MIN_RESPAWN_MULTIPLIER;
	/** Mulitplier for Raid boss minimum time respawn */
	public static float RAID_MAX_RESPAWN_MULTIPLIER;
	/** Mulitplier for Raid boss maximum time respawn */
	public static double RAID_MINION_RESPAWN_TIMER;
	/** Raid Boss Minin Spawn Timer */
	public static int PLAYER_SPAWN_PROTECTION;
	/** Player Spawn Protection */
	public static int UNSTUCK_INTERVAL;
	/** unstuck casting time */
	public static String PARTY_XP_CUTOFF_METHOD;
	/**
	 * Define Party XP cutoff point method - Possible values: level and
	 * percentage
	 */
	public static double PARTY_XP_CUTOFF_PERCENT;
	/** Define the cutoff point value for the "percentage" method */
	public static int PARTY_XP_CUTOFF_LEVEL;
	/** Define the cutoff point value for the "level" method */
	public static double RESPAWN_RESTORE_CP;
	/** Percent CP is restore on respawn */
	public static double RESPAWN_RESTORE_HP;
	/** Percent HP is restore on respawn */
	public static double RESPAWN_RESTORE_MP;
	/** Percent MP is restore on respawn */
	public static boolean RESPAWN_RANDOM_ENABLED;
	/** Allow randomizing of the respawn point in towns. */
	public static int RESPAWN_RANDOM_MAX_OFFSET;
	/** The maximum offset from the base respawn point to allow. */
	public static int MAX_PVTSTORE_SLOTS_DWARF;
	/** Maximum number of available slots for pvt stores (sell/buy) - Dwarves */
	public static int MAX_PVTSTORE_SLOTS_OTHER;
	/** Maximum number of available slots for pvt stores (sell/buy) - Others */
	public static boolean STORE_SKILL_COOLTIME;
	/** Store skills cooltime on char exit/relogin */
	public static String PET_RENT_NPC;
	/** List of NPCs that rent pets (seperated by ",") */
	public static List<Integer> LIST_PET_RENT_NPC = new FastList<Integer>();
	/** List of NPCs that rent pets */
	public static boolean ANNOUNCE_MAMMON_SPAWN;
	/** Announce mammon spawn location */
	public static boolean JAIL_IS_PVP;
	/** Jail is pvp zone */
	public static boolean JAIL_DISABLE_CHAT;
	/** Chat disabled in jail */
	public static int DEATH_PENALTY_CHANCE;
	/** Death Penalty chance */
	public static int AUGMENT_BASESTAT;
	/** Augument */
	public static int AUGMENT_SKILL;
	/** Augument */
	public static boolean AUGMENT_EXCLUDE_NOTDONE;
	/** Augument */
	
	// ANTI_FLOOD_FILE
	public static final FloodProtectorConfig FLOOD_PROTECTOR_USE_ITEM = new FloodProtectorConfig("UseItemFloodProtector");
	public static final FloodProtectorConfig FLOOD_PROTECTOR_ROLL_DICE = new FloodProtectorConfig("RollDiceFloodProtector");
	public static final FloodProtectorConfig FLOOD_PROTECTOR_FIREWORK = new FloodProtectorConfig("FireworkFloodProtector");
	public static final FloodProtectorConfig FLOOD_PROTECTOR_ITEM_PET_SUMMON = new FloodProtectorConfig("ItemPetSummonFloodProtector");
	public static final FloodProtectorConfig FLOOD_PROTECTOR_HERO_VOICE = new FloodProtectorConfig("HeroVoiceFloodProtector");
	public static final FloodProtectorConfig FLOOD_PROTECTOR_GLOBAL_CHAT = new FloodProtectorConfig("GlobalChatFloodProtector");
	public static final FloodProtectorConfig FLOOD_PROTECTOR_SUBCLASS = new FloodProtectorConfig("SubclassFloodProtector");
	public static final FloodProtectorConfig FLOOD_PROTECTOR_DROP_ITEM = new FloodProtectorConfig("DropItemFloodProtector");
	public static final FloodProtectorConfig FLOOD_PROTECTOR_SERVER_BYPASS = new FloodProtectorConfig("ServerBypassFloodProtector");
	public static final FloodProtectorConfig FLOOD_PROTECTOR_MULTISELL = new FloodProtectorConfig("MultiSellFloodProtector");
	public static final FloodProtectorConfig FLOOD_PROTECTOR_TRANSACTION = new FloodProtectorConfig("TransactionFloodProtector");
	
	// PVP_PK_CONFIG_FILE
	public static boolean LEAVE_BUFFS_ON_DIE;
	public static boolean ALLOW_POTS_IN_PVP;
	/** Allow pots in PvP */
	public static boolean ALLOW_SOE_IN_PVP;
	/** Allow SOE in PvP */
	public static boolean PVP_PK_TITLE;
	public static String PVP_TITLE_PREFIX;
	public static String PK_TITLE_PREFIX;
	public static boolean PVP_COLOR_SYSTEM_ENABLED;
	public static int PVP_AMOUNT1;
	public static int PVP_AMOUNT2;
	public static int PVP_AMOUNT3;
	public static int PVP_AMOUNT4;
	public static int PVP_AMOUNT5;
	public static int NAME_COLOR_FOR_PVP_AMOUNT1;
	public static int NAME_COLOR_FOR_PVP_AMOUNT2;
	public static int NAME_COLOR_FOR_PVP_AMOUNT3;
	public static int NAME_COLOR_FOR_PVP_AMOUNT4;
	public static int NAME_COLOR_FOR_PVP_AMOUNT5;
	public static boolean PK_COLOR_SYSTEM_ENABLED;
	public static int PK_AMOUNT1;
	public static int PK_AMOUNT2;
	public static int PK_AMOUNT3;
	public static int PK_AMOUNT4;
	public static int PK_AMOUNT5;
	public static int TITLE_COLOR_FOR_PK_AMOUNT1;
	public static int TITLE_COLOR_FOR_PK_AMOUNT2;
	public static int TITLE_COLOR_FOR_PK_AMOUNT3;
	public static int TITLE_COLOR_FOR_PK_AMOUNT4;
	public static int TITLE_COLOR_FOR_PK_AMOUNT5;
	public static boolean CUSTOM_MSG_ALLOWED;
	/** Custom messages after PvP/PK */
	public static boolean ALLOW_PVP_REWARD;
	/** PvP is rewarded */
	public static int PVP_REWARD_ITEM;
	/** PvP is rewarded */
	public static int PVP_REWARD_COUNT;
	/** PvP is rewarded */
	public static boolean ALLOW_PK_REWARD;
	/** PK is rewarded */
	public static int PK_REWARD_ITEM;
	/** PK is rewarded */
	public static int PK_REWARD_COUNT;
	/** PK is rewarded */
	public static boolean DEFAULT_PK_SYSTEM;
	/** Use Default PK system */
	public static boolean CUSTOM_PK_SYSTEM;
	/** Custom PK system (no karma + pvp) */
	public static int KARMA_MIN_KARMA;
	/** Minimum karma gain/loss */
	public static int KARMA_MAX_KARMA;
	/** Maximum karma gain/loss */
	public static int KARMA_XP_DIVIDER;
	/**
	 * Number to divide the xp recieved by, to calculate karma lost on xp
	 * gain/lost
	 */
	public static int KARMA_LOST_BASE;
	/** The Minimum Karma lost if 0 karma is to be removed */
	public static boolean KARMA_DROP_GM;
	/** Can a GM drop item ? */
	public static String KARMA_NONDROPPABLE_PET_ITEMS;
	/** List of pet items that cannot be dropped (seperated by ",") when PVP */
	public static List<Integer> KARMA_LIST_NONDROPPABLE_PET_ITEMS = new FastList<Integer>();
	/** List of pet items that cannot be dropped when PVP */
	public static String KARMA_NONDROPPABLE_ITEMS;
	/** List of items that cannot be dropped (seperated by ",") when PVP */
	public static List<Integer> KARMA_LIST_NONDROPPABLE_ITEMS = new FastList<Integer>();
	/** List of items that cannot be dropped when PVP */
	public static int KARMA_PK_LIMIT;
	/** Minimum PK required to drop */
	public static boolean KARMA_AWARD_PK_KILL;
	/** Should award a pvp point for killing a player with karma ? */
	public static int PVP_NORMAL_TIME;
	/**
	 * Duration (in ms) while a player stay in PVP mode after hitting an
	 * innocent
	 */
	public static int PVP_PVP_TIME;
	/**
	 * Duration (in ms) while a player stay in PVP mode after hitting a purple
	 * player
	 */
	public static boolean ANNOUNCE_PVP_KILL;
	/** Announce PvP */
	public static boolean ANNOUNCE_PK_KILL;
	/** Announce PK */
	public static boolean CUSTOM_MSG_ON_PVP;
	
	// HITMAN_CONFIG_FILE
	public static boolean ENABLE_HITMAN_EVENT;
	public static boolean HITMAN_TAKE_KARMA;
	
	// TVT_CONFIG_FILE
	public static boolean TVT_AUTO_STARTUP_ON_BOOT;
	public static boolean TVT_ALLOW_INTERFERENCE;
	public static boolean TVT_ALLOW_POTIONS;
	public static boolean TVT_ALLOW_SUMMON;
	public static boolean TVT_ON_START_REMOVE_ALL_EFFECTS;
	public static boolean TVT_ON_START_UNSUMMON_PET;
	public static boolean TVT_REVIVE_RECOVERY;
	public static boolean TVT_ANNOUNCE_TEAM_STATS;
	public static boolean TVT_CLOSE_COLISEUM_DOORS;
	public static boolean TVT_ALLOW_ENEMY_HEALING;
	public static boolean TVT_ALLOW_TEAM_CASTING;
	public static boolean TVT_ALLOW_TEAM_ATTACKING;
	public static boolean TVT_ANNOUNCE_REGISTRATION_LOC_NPC;
	public static boolean TVT_ANNOUNCE_SIGNUPS;
	public static boolean TVT_JOIN_CURSED;
	public static boolean TVT_PRICE_NO_KILLS;
	public static boolean TVT_AURA;
	public static String TVT_EVEN_TEAMS;
	public static boolean TVT_ANNOUNCE_REWARD;
	public static int FIRST_TVT_DELAY;
	public static long TVT_REVIVE_DELAY;
	
	// EVENT_FILE
	public static String CTF_EVEN_TEAMS;
	public static boolean CTF_ALLOW_INTERFERENCE;
	public static boolean CTF_ALLOW_POTIONS;
	public static boolean CTF_ALLOW_SUMMON;
	public static boolean CTF_ON_START_REMOVE_ALL_EFFECTS;
	public static boolean CTF_ON_START_UNSUMMON_PET;
	public static boolean CTF_ANNOUNCE_TEAM_STATS;
	public static boolean CTF_ANNOUNCE_REWARD;
	public static boolean CTF_JOIN_CURSED;
	public static boolean CTF_REVIVE_RECOVERY;
	public static long CTF_REVIVE_DELAY;
	
	/* Raid Event Engine */
	public static boolean RAID_SYSTEM_ENABLED;
	public static int RAID_SYSTEM_MAX_EVENTS;
	public static boolean RAID_SYSTEM_GIVE_BUFFS;
	public static boolean RAID_SYSTEM_RESURRECT_PLAYER;
	public static int RAID_SYSTEM_FIGHT_TIME;
	
	// DM_CONFIG_FILE
	public static boolean DM_ALLOW_INTERFERENCE;
	public static boolean DM_ALLOW_POTIONS;
	public static boolean DM_ALLOW_SUMMON;
	public static boolean DM_ON_START_REMOVE_ALL_EFFECTS;
	public static boolean DM_ON_START_UNSUMMON_PET;
	public static boolean ARENA_ENABLED;
	public static int ARENA_INTERVAL;
	public static int ARENA_REWARD_ID;
	public static int ARENA_REWARD_COUNT;
	public static boolean FISHERMAN_ENABLED;
	public static int FISHERMAN_INTERVAL;
	public static int FISHERMAN_REWARD_ID;
	public static int FISHERMAN_REWARD_COUNT;
	public static long DM_REVIVE_DELAY;
	
	// VIP_CONFIG_FILE
	public static boolean VIP_ALLOW_INTERFERENCE;
	public static boolean VIP_ALLOW_POTIONS;
	public static boolean VIP_ON_START_REMOVE_ALL_EFFECTS;
	public static int VIP_MIN_LEVEL;
	public static int VIP_MAX_LEVEL;
	public static int VIP_MIN_PARTICIPANTS;
	
	// Premium Settings
	public static boolean USE_PREMIUMSERVICE;
	public static float PREMIUM_RATE_XP;
	public static float PREMIUM_RATE_SP;
	public static Map<Integer, Integer> PR_RATE_DROP_ITEMS_ID;
	public static float PREMIUM_RATE_DROP_SPOIL;
	public static float PREMIUM_RATE_DROP_ITEMS;
	public static float PREMIUM_RATE_DROP_QUEST;
	public static float PREMIUM_RATE_DROP_ITEMS_BY_RAID;

	// RATES_CONFIG_FILE
	public static float RATE_XP;
	/** Rate for eXperience Point rewards */
	public static float RATE_SP;
	/** Rate for Skill Point rewards */
	public static float RATE_PARTY_XP;
	/** Rate for party eXperience Point rewards */
	public static float RATE_PARTY_SP;
	/** Rate for party Skill Point rewards */
	public static float RATE_DROP_ADENA;
	/** Rate for drop adena */
	public static float RATE_CONSUMABLE_COST;
	/** Rate for cost of consumable */
	public static float RATE_DROP_ITEMS;
	/** Rate for dropped items */
	public static float RATE_DROP_SPOIL;
	/** Rate for spoiled items */
	public static int RATE_DROP_MANOR;
	/** Rate for manored items */
	public static float RATE_DROP_QUEST;
	/** Rate for quest items */
	public static float RATE_QUESTS_REWARD;
	/** Rate for Quest rewards (XP and SP) */
	public static float RATE_KARMA_EXP_LOST;
	/** Rate for karma and experience lose */
	public static float RATE_SIEGE_GUARDS_PRICE;
	/** Rate siege guards prices */
	public static int PLAYER_DROP_LIMIT;
	/** Limit for player drop */
	public static int PLAYER_RATE_DROP;
	/** Rate for drop */
	public static int PLAYER_RATE_DROP_ITEM;
	/** Rate for player's item drop */
	public static int PLAYER_RATE_DROP_EQUIP;
	/** Rate for player's equipment drop */
	public static int PLAYER_RATE_DROP_EQUIP_WEAPON;
	/** Rate for player's equipment and weapon drop */
	public static int KARMA_DROP_LIMIT;
	/** Karma drop limit */
	public static int KARMA_RATE_DROP;
	/** Karma drop rate */
	public static int KARMA_RATE_DROP_ITEM;
	/** Karma drop rate for item */
	public static int KARMA_RATE_DROP_EQUIP;
	/** Karma drop rate for equipment */
	public static int KARMA_RATE_DROP_EQUIP_WEAPON;
	/** Karma drop rate for equipment and weapon */
	public static float PET_XP_RATE;
	/** Rate for experience rewards of the pet */
	public static int PET_FOOD_RATE;
	/** Rate for food consumption of the pet */
	public static float SINEATER_XP_RATE;
	/** Rate for experience rewards of the Sin Eater */
	public static float RATE_DROP_COMMON_HERBS;
	/** Rate Common herbs */
	public static float RATE_DROP_MP_HP_HERBS;
	/** Rate MP/HP herbs */
	public static float RATE_DROP_GREATER_HERBS;
	/** Rate Common herbs */
	public static float RATE_DROP_SUPERIOR_HERBS;
	/** Rate Common herbs */
	public static float RATE_DROP_SPECIAL_HERBS;
	/** Rate Common herbs */
	public static float RATE_DROP_ITEMS_BY_RAID;
	/** Rate for raids dropped items */
	public static Map<Integer, Integer> ENCHANT_CHANCE_WEAPON_LIST;
	public static Map<Integer, Integer> ENCHANT_CHANCE_ARMOR_LIST;
	public static Map<Integer, Integer> ENCHANT_CHANCE_JEWELRY_LIST;
	public static Map<Integer, Integer> BLESSED_ENCHANT_CHANCE_WEAPON_LIST;
	public static Map<Integer, Integer> BLESSED_ENCHANT_CHANCE_ARMOR_LIST;
	public static Map<Integer, Integer> BLESSED_ENCHANT_CHANCE_JEWELRY_LIST;
	public static int ENCHANT_CHANCE_WEAPON;
	/** Chance that a Enchant Scroll will have to enchant over safe limit */
	public static int ENCHANT_CHANCE_ARMOR;
	/** Chance that a Enchant Scroll will have to enchant over safe limit */
	public static int ENCHANT_CHANCE_JEWELRY;
	/** Chance that a Enchant Scroll will have to enchant over safe limit */
	public static int ENCHANT_CHANCE_WEAPON_CRYSTAL;
	/** Chance that a Crystal Scroll will have to enchant over safe limit */
	public static int ENCHANT_CHANCE_ARMOR_CRYSTAL;
	/** Chance that a Crystal Scroll will have to enchant over safe limit */
	public static int ENCHANT_CHANCE_JEWELRY_CRYSTAL;
	/** Chance that a Crystal Scroll will have to enchant over safe limit */
	public static int ENCHANT_CHANCE_WEAPON_BLESSED;
	/** Chance that a Blessed Scroll will have to enchant over safe limit */
	public static int ENCHANT_CHANCE_ARMOR_BLESSED;
	/** Chance that a Blessed Scroll will have to enchant over safe limit */
	public static int ENCHANT_CHANCE_JEWELRY_BLESSED;
	/** Chance that a Blessed Scroll will have to enchant over safe limit */
	public static int ENCHANT_MAX_ALLOWED_WEAPON;
	/** Max possible enchant by player */
	public static int ENCHANT_MAX_ALLOWED_ARMOR;
	/** Max possible enchant by player */
	public static int ENCHANT_MAX_ALLOWED_JEWELRY;
	/** Max possible enchant by player */
	public static int ENCHANT_MAX_WEAPON;
	/** Max possible enchant by player */
	public static int ENCHANT_MAX_ARMOR;
	/** Max possible enchant by player */
	public static int ENCHANT_MAX_JEWELRY;
	/** Max possible enchant by player */
	public static int ENCHANT_SAFE_MAX;
	/** Maximum level of safe enchantment for normal items */
	public static int ENCHANT_SAFE_MAX_FULL;
	/** Maximum level of safe enchantment for full body armor */
	public static boolean DROP_MULTI_ADENA;
	
	// CONFIGURATION_FILE
	public static String GAMESERVER_HOSTNAME;
	/** Hostname of the Game Server */
	public static int PORT_GAME;
	/** Game Server ports */
	public static String EXTERNAL_HOSTNAME;
	/** External Hostname */
	public static String INTERNAL_HOSTNAME;
	/** Internal Hostname */
	public static int GAME_SERVER_LOGIN_PORT;
	/** Game Server login port */
	public static String GAME_SERVER_LOGIN_HOST;
	/** Game Server login Host */
	public static int REQUEST_ID;
	/** ID for request to the server */
	public static boolean ACCEPT_ALTERNATE_ID;
	/** Accept alternate ID for server ? */
	public static String DATABASE_DRIVER;
	/** Driver to access to database */
	public static String DATABASE_URL;
	/** Path to access to database */
	public static String DATABASE_LOGIN;
	/** Database login */
	public static String DATABASE_PASSWORD;
	/** Database password */
	public static int DATABASE_MAX_CONNECTIONS;
	/** Maximum number of connections to the database */
	public static File DATAPACK_ROOT;
	/** Datapack root directory */
	public static String CNAME_TEMPLATE;
	/** Character name template */
	public static String PET_NAME_TEMPLATE;
	/** Pet name template */
	public static int MAX_CHARACTERS_NUMBER_PER_ACCOUNT;
	/** Maximum number of characters per account */
	public static int MAXIMUM_ONLINE_USERS;
	/** Maximum number of players allowed to play simultaneously on server */
	public static boolean ALLOW_DUALBOX;
	/**
	 * Allow to log in with more than one
	 * account per ip
	 */
	public static int MIN_PROTOCOL_REVISION;
	/** Minimal protocol revision */
	public static int MAX_PROTOCOL_REVISION;
	/** Maximal protocol revision */
	public static boolean ENABLE_PACKET_PROTECTION;
	/** Unknown Packet handler protection */
	public static int MAX_UNKNOWN_PACKETS;
	/** Unknown Packet handler protection */
	public static int UNKNOWN_PACKETS_PUNISHMENT;
	/** Unknown Packet handler protection */
	
	// SEVENSIGNS_FILE
	public static boolean ALT_GAME_REQUIRE_CASTLE_DAWN;
	/** Player must be in a castle-owning clan or ally to sign up for Dawn. */
	public static boolean ALT_GAME_REQUIRE_CLAN_CASTLE;
	/**
	 * Alternative gaming - allow clan-based castle ownage check rather than
	 * ally-based.
	 */
	public static int ALT_FESTIVAL_MIN_PLAYER;
	/** Minimum number of player to participate in SevenSigns Festival */
	public static int ALT_MAXIMUM_PLAYER_CONTRIB;
	/** Maximum of player contrib during Festival */
	public static long ALT_FESTIVAL_MANAGER_START;
	/** Festival Manager start time. */
	public static long ALT_FESTIVAL_LENGTH;
	/** Festival Length */
	public static long ALT_FESTIVAL_CYCLE_LENGTH;
	/** Festival Cycle Length */
	public static long ALT_FESTIVAL_FIRST_SPAWN;
	/** Festival First Spawn */
	public static long ALT_FESTIVAL_FIRST_SWARM;
	/** Festival First Swarm */
	public static long ALT_FESTIVAL_SECOND_SPAWN;
	/** Festival Second Spawn */
	public static long ALT_FESTIVAL_SECOND_SWARM;
	/** Festival Second Swarm */
	public static long ALT_FESTIVAL_CHEST_SPAWN;
	/** Festival Chest Spawn */
	
	// SIEGE_CONFIGURATION_FILE empty -> cheking elsewere.
	
	// HEXID_FILE
	public static int SERVER_ID;
	/** Server ID used with the HexID */
	public static byte[] HEX_ID;
	/** Hexadecimal ID of the game server */
	
	// LOGIN_CONFIGURATION_FILE some of info in CONFIGURATION_FILE
	public static String LOGIN_BIND_ADDRESS;
	/** Login Server bind ip */
	public static int PORT_LOGIN;
	/** Login Server port */
	public static int LOGIN_TRY_BEFORE_BAN;
	/** Number of login tries before IP ban gets activated, default 10 */
	public static int LOGIN_BLOCK_AFTER_BAN;
	/** Number of seconds the IP ban will last, default 10 minutes */
	public static boolean ACCEPT_NEW_GAMESERVER;
	/** Accept new game server ? */
	public static boolean SHOW_LICENCE;
	/**
	 * Show licence or not just after login (if False, will directly go to the
	 * Server List
	 */
	public static boolean AUTO_CREATE_ACCOUNTS;
	/** Allow auto-create account ? */
	public static int IP_UPDATE_TIME;
	/** Time between 2 updates of IP */
	public static boolean ASSERT;
	/** Enable/disable assertions */
	public static boolean DEVELOPER;
	/** Enable/disable code 'in progress' */
	public static boolean FORCE_GGAUTH;
	/** Force GameGuard authorization in loginserver */
	public static boolean FLOOD_PROTECTION;
	/** Login flood protection */
	public static int FAST_CONNECTION_LIMIT;
	/** Login flood protection */
	public static int NORMAL_CONNECTION_TIME;
	/** Login flood protection */
	public static int FAST_CONNECTION_TIME;
	/** Login flood protection */
	public static int MAX_CONNECTION_PER_IP;
	/** Login flood protection */
	
	// TELNET_FILE
	public static boolean IS_TELNET_ENABLED;
	
	/** Is telnet enabled ? */
	public static enum IdFactoryType /** Enumeration for type of ID Factory */
	{
		Compaction, BitSet, Stack
	}
	
	public static enum ObjectMapType /** Enumeration for type of maps object */
	{
		L2ObjectHashMap, WorldObjectMap
	}
	
	public static enum ObjectSetType /** Enumeration for type of set object */
	{
		L2ObjectHashSet, WorldObjectSet
	}
	
	// StatusPort and ListOfHosts checked elsewere.
	
	// Found in more than one file
	public static boolean DEBUG;
	/** Enable/disable debugging */
	
	// Variables doesn't found in any .ini file. If added look at
	// altsettings.ini bottom
	public static int MAX_ITEM_IN_PACKET;
	public static boolean CHECK_KNOWN;
	public static int NEW_NODE_ID;
	public static int SELECTED_NODE_ID;
	public static int LINKED_NODE_ID;
	public static String NEW_NODE_TYPE;
	public static boolean RESERVE_HOST_ON_LOGIN = false;
	
	/**
	 * This class initializes all global variables for configuration.<br>
	 * If key doesn't appear in ini file, a default value is setting on
	 * by this class.
	 * 
	 * @see CONFIGURATION_FILE (ini file) for configuring your server.
	 */
	public static void load()
	{
		if (Server.serverMode == Server.MODE_GAMESERVER)
		{
			// Try to load ALT_SETTINGS_FILE (if exist)
			try
			{
				Properties altSettings = new Properties();
				InputStream is = new FileInputStream(new File(ALT_SETTINGS_FILE));
				altSettings.load(is);
				is.close();
				
				RESTORE_EFFECTS_ON_SUBCLASS_CHANGE = Boolean.parseBoolean(altSettings.getProperty("RestoreEffectsOnSub", "False"));
				AUTO_LOOT = Boolean.parseBoolean(altSettings.getProperty("AutoLoot", "False"));
				AUTO_LOOT_RAID = Boolean.parseBoolean(altSettings.getProperty("AutoLootRaid", "False"));
				BANKING2_SYSTEM_ENABLED = Boolean.parseBoolean(altSettings.getProperty("Banking2Enabled", "True"));
				BANKING2_SYSTEM_GOLDBARS = Integer.parseInt(altSettings.getProperty("Banking2GoldbarCount", "10000000"));
				BANKING2_SYSTEM_ADENA = Integer.parseInt(altSettings.getProperty("Banking2AdenaCount", "1"));
				AUTO_LOOT_BOSS = Boolean.parseBoolean(altSettings.getProperty("AutoLootBoss", "False"));
				AUTO_LOOT_HERBS = Boolean.parseBoolean(altSettings.getProperty("AutoLootHerbs", "False"));
				AUTO_LEARN_SKILLS = Boolean.parseBoolean(altSettings.getProperty("AutoLearnSkills", "False"));
				CHECK_SKILLS_ON_ENTER = Boolean.parseBoolean(altSettings.getProperty("CheckSkillsOnEnter", "False"));
				ALLOWED_SKILLS = altSettings.getProperty("AllowedSkills", "541,542,543,544,545,546,547,548,549,550,551,552,553,554,555,556,557,558,617,618,619");
				ALLOWED_SKILLS_LIST = new FastList<Integer>();
				for (String id : ALLOWED_SKILLS.trim().split(","))
				{
					ALLOWED_SKILLS_LIST.add(Integer.parseInt(id.trim()));
				}
				LIFE_CRYSTAL_NEEDED = Boolean.parseBoolean(altSettings.getProperty("LifeCrystalNeeded", "True"));
				SP_BOOK_NEEDED = Boolean.parseBoolean(altSettings.getProperty("SpBookNeeded", "True"));
				ES_SP_BOOK_NEEDED = Boolean.parseBoolean(altSettings.getProperty("EnchantSkillSpBookNeeded", "True"));
				ALT_GAME_SKILL_LEARN = Boolean.parseBoolean(altSettings.getProperty("AltGameSkillLearn", "False"));
				ALT_PARTY_RANGE = Integer.parseInt(altSettings.getProperty("AltPartyRange", "1600"));
				ALT_PARTY_RANGE2 = Integer.parseInt(altSettings.getProperty("AltPartyRange2", "1400"));
				ALT_WEIGHT_LIMIT = Double.parseDouble(altSettings.getProperty("AltWeightLimit", "1"));
				ALT_GAME_DELEVEL = Boolean.parseBoolean(altSettings.getProperty("Delevel", "True"));
				ALT_GAME_MAGICFAILURES = Boolean.parseBoolean(altSettings.getProperty("MagicFailures", "False"));
				ALT_GAME_CANCEL_BOW = altSettings.getProperty("AltGameCancelByHit", "Cast").equalsIgnoreCase("bow") || altSettings.getProperty("AltGameCancelByHit", "Cast").equalsIgnoreCase("all");
				ALT_GAME_CANCEL_CAST = altSettings.getProperty("AltGameCancelByHit", "Cast").equalsIgnoreCase("cast") || altSettings.getProperty("AltGameCancelByHit", "Cast").equalsIgnoreCase("all");
				ALT_GAME_SHIELD_BLOCKS = Boolean.parseBoolean(altSettings.getProperty("AltShieldBlocks", "False"));
				ALT_PERFECT_SHLD_BLOCK = Integer.parseInt(altSettings.getProperty("AltPerfectShieldBlockRate", "10"));
				ALT_GAME_MOB_ATTACK_AI = Boolean.parseBoolean(altSettings.getProperty("AltGameMobAttackAI", "False"));
				ALT_MOB_AGRO_IN_PEACEZONE = Boolean.parseBoolean(altSettings.getProperty("AltMobAgroInPeaceZone", "True"));
				ALT_GAME_FREIGHTS = Boolean.parseBoolean(altSettings.getProperty("AltGameFreights", "False"));
				ALT_GAME_FREIGHT_PRICE = Integer.parseInt(altSettings.getProperty("AltGameFreightPrice", "1000"));
				ALT_GAME_EXPONENT_XP = Float.parseFloat(altSettings.getProperty("AltGameExponentXp", "0."));
				ALT_GAME_EXPONENT_SP = Float.parseFloat(altSettings.getProperty("AltGameExponentSp", "0."));
				ALT_GAME_TIREDNESS = Boolean.parseBoolean(altSettings.getProperty("AltGameTiredness", "False"));
				ALT_GAME_KARMA_PLAYER_CAN_BE_KILLED_IN_PEACEZONE = Boolean.valueOf(altSettings.getProperty("AltKarmaPlayerCanBeKilledInPeaceZone", "False"));
				ALT_GAME_KARMA_PLAYER_CAN_SHOP = Boolean.valueOf(altSettings.getProperty("AltKarmaPlayerCanShop", "True"));
				ALT_GAME_KARMA_PLAYER_CAN_USE_GK = Boolean.valueOf(altSettings.getProperty("AltKarmaPlayerCanUseGK", "False"));
				ALT_GAME_KARMA_PLAYER_CAN_TELEPORT = Boolean.valueOf(altSettings.getProperty("AltKarmaPlayerCanTeleport", "True"));
				ALT_GAME_KARMA_PLAYER_CAN_TRADE = Boolean.valueOf(altSettings.getProperty("AltKarmaPlayerCanTrade", "True"));
				ALT_GAME_KARMA_PLAYER_CAN_USE_WAREHOUSE = Boolean.valueOf(altSettings.getProperty("AltKarmaPlayerCanUseWareHouse", "True"));
				ALT_GAME_FREE_TELEPORT = Boolean.parseBoolean(altSettings.getProperty("AltFreeTeleporting", "False"));
				ALT_RECOMMEND = Boolean.parseBoolean(altSettings.getProperty("AltRecommend", "False"));
				ALT_PLAYER_PROTECTION = Boolean.parseBoolean(altSettings.getProperty("AltPlayerProtection", "False"));
				ALT_PLAYER_PROTECTION_LEVEL = Integer.parseInt(altSettings.getProperty("AltPlayerProtectionLevel", "0"));
				IS_CRAFTING_ENABLED = Boolean.parseBoolean(altSettings.getProperty("CraftingEnabled", "True"));
				DWARF_RECIPE_LIMIT = Integer.parseInt(altSettings.getProperty("DwarfRecipeLimit", "50"));
				COMMON_RECIPE_LIMIT = Integer.parseInt(altSettings.getProperty("CommonRecipeLimit", "50"));
				ALT_GAME_CREATION = Boolean.parseBoolean(altSettings.getProperty("AltGameCreation", "False"));
				ALT_GAME_CREATION_SPEED = Double.parseDouble(altSettings.getProperty("AltGameCreationSpeed", "1"));
				ALT_GAME_CREATION_XP_RATE = Double.parseDouble(altSettings.getProperty("AltGameCreationRateXp", "1"));
				ALT_GAME_CREATION_SP_RATE = Double.parseDouble(altSettings.getProperty("AltGameCreationRateSp", "1"));
				ALT_BLACKSMITH_USE_RECIPES = Boolean.parseBoolean(altSettings.getProperty("AltBlacksmithUseRecipes", "True"));
				ALT_GAME_SUBCLASS_WITHOUT_QUESTS = Boolean.parseBoolean(altSettings.getProperty("AltSubClassWithoutQuests", "False"));
				MAX_SUBCLASS = Byte.parseByte(altSettings.getProperty("MaxSubclass", "3"));
				MAX_PATK_SPEED = Integer.parseInt(altSettings.getProperty("MaxPAtkSpeed", "1800"));
				MAX_MATK_SPEED = Integer.parseInt(altSettings.getProperty("MaxMAtkSpeed", "2000"));
				MAX_PATACK = Integer.parseInt(altSettings.getProperty("Max_Patack", "500"));
				DISABLE_GRADE_PENALTY = Boolean.parseBoolean(altSettings.getProperty("DisableGradePenalty", "False"));
				DISABLE_WEIGHT_PENALTY = Boolean.parseBoolean(altSettings.getProperty("DisableWeightPenalty", "False"));
				CASTLE_SHIELD = Boolean.parseBoolean(altSettings.getProperty("CastleShieldRestriction", "True"));
				CLANHALL_SHIELD = Boolean.parseBoolean(altSettings.getProperty("ClanHallShieldRestriction", "True"));
				APELLA_ARMORS = Boolean.parseBoolean(altSettings.getProperty("ApellaArmorsRestriction", "True"));
				OATH_ARMORS = Boolean.parseBoolean(altSettings.getProperty("OathArmorsRestriction", "True"));
				CASTLE_CROWN = Boolean.parseBoolean(altSettings.getProperty("CastleLordsCrownRestriction", "True"));
				CASTLE_CIRCLETS = Boolean.parseBoolean(altSettings.getProperty("CastleCircletsRestriction", "True"));
				ALT_CLAN_JOIN_DAYS = Integer.parseInt(altSettings.getProperty("DaysBeforeJoinAClan", "5"));
				ALT_CLAN_CREATE_DAYS = Integer.parseInt(altSettings.getProperty("DaysBeforeCreateAClan", "10"));
				ALT_CLAN_DISSOLVE_DAYS = Integer.parseInt(altSettings.getProperty("DaysToPassToDissolveAClan", "7"));
				ALT_ALLY_JOIN_DAYS_WHEN_LEAVED = Integer.parseInt(altSettings.getProperty("DaysBeforeJoinAllyWhenLeaved", "1"));
				ALT_ALLY_JOIN_DAYS_WHEN_DISMISSED = Integer.parseInt(altSettings.getProperty("DaysBeforeJoinAllyWhenDismissed", "1"));
				ALT_ACCEPT_CLAN_DAYS_WHEN_DISMISSED = Integer.parseInt(altSettings.getProperty("DaysBeforeAcceptNewClanWhenDismissed", "1"));
				ALT_CREATE_ALLY_DAYS_WHEN_DISSOLVED = Integer.parseInt(altSettings.getProperty("DaysBeforeCreateNewAllyWhenDissolved", "10"));
				ALT_MAX_NUM_OF_CLANS_IN_ALLY = Integer.parseInt(altSettings.getProperty("AltMaxNumOfClansInAlly", "3"));
				ALT_CLAN_MEMBERS_FOR_WAR = Integer.parseInt(altSettings.getProperty("AltClanMembersForWar", "15"));
				ALT_REPUTATION_SCORE_PER_KILL = Integer.parseInt(altSettings.getProperty("ReputationScorePerKill", "5"));
				ALT_GAME_NEW_CHAR_ALWAYS_IS_NEWBIE = Boolean.parseBoolean(altSettings.getProperty("AltNewCharAlwaysIsNewbie", "False"));
				ALT_MEMBERS_CAN_WITHDRAW_FROM_CLANWH = Boolean.parseBoolean(altSettings.getProperty("AltMembersCanWithdrawFromClanWH", "False"));
				REMOVE_CASTLE_CIRCLETS = Boolean.parseBoolean(altSettings.getProperty("RemoveCastleCirclets", "True"));
				ALT_MANOR_REFRESH_TIME = Integer.parseInt(altSettings.getProperty("AltManorRefreshTime", "20"));
				ALT_MANOR_REFRESH_MIN = Integer.parseInt(altSettings.getProperty("AltManorRefreshMin", "00"));
				ALT_MANOR_APPROVE_TIME = Integer.parseInt(altSettings.getProperty("AltManorApproveTime", "6"));
				ALT_MANOR_APPROVE_MIN = Integer.parseInt(altSettings.getProperty("AltManorApproveMin", "00"));
				ALT_MANOR_MAINTENANCE_PERIOD = Integer.parseInt(altSettings.getProperty("AltManorMaintenancePeriod", "360000"));
				ALT_MANOR_SAVE_ALL_ACTIONS = Boolean.parseBoolean(altSettings.getProperty("AltManorSaveAllActions", "False"));
				ALT_MANOR_SAVE_PERIOD_RATE = Integer.parseInt(altSettings.getProperty("AltManorSavePeriodRate", "2"));
				ALT_LOTTERY_PRIZE = Integer.parseInt(altSettings.getProperty("AltLotteryPrize", "50000"));
				ALT_LOTTERY_TICKET_PRICE = Integer.parseInt(altSettings.getProperty("AltLotteryTicketPrice", "2000"));
				ALT_LOTTERY_5_NUMBER_RATE = Float.parseFloat(altSettings.getProperty("AltLottery5NumberRate", "0.6"));
				ALT_LOTTERY_4_NUMBER_RATE = Float.parseFloat(altSettings.getProperty("AltLottery4NumberRate", "0.2"));
				ALT_LOTTERY_3_NUMBER_RATE = Float.parseFloat(altSettings.getProperty("AltLottery3NumberRate", "0.2"));
				ALT_LOTTERY_2_AND_1_NUMBER_PRIZE = Integer.parseInt(altSettings.getProperty("AltLottery2and1NumberPrize", "200"));
				ALT_DEV_NO_QUESTS = Boolean.parseBoolean(altSettings.getProperty("AltDevNoQuests", "False"));
				ALT_DEV_NO_SPAWNS = Boolean.parseBoolean(altSettings.getProperty("AltDevNoSpawns", "False"));
				FS_TIME_ATTACK = Integer.parseInt(altSettings.getProperty("TimeOfAttack", "50"));
				FS_TIME_COOLDOWN = Integer.parseInt(altSettings.getProperty("TimeOfCoolDown", "5"));
				FS_TIME_ENTRY = Integer.parseInt(altSettings.getProperty("TimeOfEntry", "3"));
				FS_TIME_WARMUP = Integer.parseInt(altSettings.getProperty("TimeOfWarmUp", "2"));
				FS_PARTY_MEMBER_COUNT = Integer.parseInt(altSettings.getProperty("NumberOfNecessaryPartyMembers", "4"));
				if (FS_TIME_ATTACK <= 0)
					FS_TIME_ATTACK = 50;
				if (FS_TIME_COOLDOWN <= 0)
					FS_TIME_COOLDOWN = 5;
				if (FS_TIME_ENTRY <= 0)
					FS_TIME_ENTRY = 3;
				if (FS_TIME_ENTRY <= 0)
					FS_TIME_ENTRY = 3;
				if (FS_TIME_ENTRY <= 0)
					FS_TIME_ENTRY = 3;
				RIFT_MIN_PARTY_SIZE = Integer.parseInt(altSettings.getProperty("RiftMinPartySize", "5"));
				RIFT_MAX_JUMPS = Integer.parseInt(altSettings.getProperty("MaxRiftJumps", "4"));
				RIFT_SPAWN_DELAY = Integer.parseInt(altSettings.getProperty("RiftSpawnDelay", "10000"));
				RIFT_AUTO_JUMPS_TIME_MIN = Integer.parseInt(altSettings.getProperty("AutoJumpsDelayMin", "480"));
				RIFT_AUTO_JUMPS_TIME_MAX = Integer.parseInt(altSettings.getProperty("AutoJumpsDelayMax", "600"));
				RIFT_BOSS_ROOM_TIME_MUTIPLY = Float.parseFloat(altSettings.getProperty("BossRoomTimeMultiply", "1.5"));
				RIFT_ENTER_COST_RECRUIT = Integer.parseInt(altSettings.getProperty("RecruitCost", "18"));
				RIFT_ENTER_COST_SOLDIER = Integer.parseInt(altSettings.getProperty("SoldierCost", "21"));
				RIFT_ENTER_COST_OFFICER = Integer.parseInt(altSettings.getProperty("OfficerCost", "24"));
				RIFT_ENTER_COST_CAPTAIN = Integer.parseInt(altSettings.getProperty("CaptainCost", "27"));
				RIFT_ENTER_COST_COMMANDER = Integer.parseInt(altSettings.getProperty("CommanderCost", "30"));
				RIFT_ENTER_COST_HERO = Integer.parseInt(altSettings.getProperty("HeroCost", "33"));
				MAX_CHAT_LENGTH = Integer.parseInt(altSettings.getProperty("MaxChatLength", "105"));
				CRUMA_TOWER_LEVEL_RESTRICT = Integer.parseInt(altSettings.getProperty("CrumaTowerLevelRestrict", "56"));
				ALT_RESPAWN_POINT = Boolean.parseBoolean(altSettings.getProperty("AllowCustomRespawnPoint", "False"));
				ALT_RESPAWN_POINT_X = Integer.parseInt(altSettings.getProperty("CustomRespawnPointX", "0"));
				ALT_RESPAWN_POINT_Y = Integer.parseInt(altSettings.getProperty("CustomRespawnPointY", "0"));
				ALT_RESPAWN_POINT_Z = Integer.parseInt(altSettings.getProperty("CustomRespawnPointZ", "0"));
				DANCE_TIME_MULTIPLIER = Float.parseFloat(altSettings.getProperty("DanceTimeMultiplier", "1"));
				SPIRIT_TIME_MULTIPLIER = Float.parseFloat(altSettings.getProperty("SpiritMultiplier", "1"));
				BUFF_TIME_MULTIPLIER = Float.parseFloat(altSettings.getProperty("BuffMultiplier", "1"));
				BUFFS_MAX_AMOUNT = Byte.parseByte(altSettings.getProperty("MaxBuffAmount", "24"));
				ALT_GAME_VIEWNPC = Boolean.parseBoolean(altSettings.getProperty("AltGameViewNpc", "False"));
				USE_3D_MAP = Boolean.valueOf(altSettings.getProperty("Use3DMap", "False"));
				ALLOW_HIT_OWNER = Boolean.valueOf(altSettings.getProperty("AllowHitOwner", "True"));
				FRONT_BLOW_SUCCESS = Byte.parseByte(altSettings.getProperty("FrontBlow", "40"));
				BACK_BLOW_SUCCESS = Byte.parseByte(altSettings.getProperty("BackBlow", "60"));
				SIDE_BLOW_SUCCESS = Byte.parseByte(altSettings.getProperty("SideBlow", "50"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + ALT_SETTINGS_FILE + " File.");
			}
			
			// Try to load CLANHALL_CONFIG_FILE (if exist)
			try
			{
				
				Properties clanhallSettings = new Properties();
				InputStream is = new FileInputStream(new File(CLANHALL_CONFIG_FILE));
				clanhallSettings.load(is);
				is.close();
				CH_TELE_FEE_RATIO = Long.valueOf(clanhallSettings.getProperty("ClanHallTeleportFunctionFeeRation", "86400000"));
				CH_TELE1_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallTeleportFunctionFeeLvl1", "86400000"));
				CH_TELE2_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallTeleportFunctionFeeLvl2", "86400000"));
				CH_TELE3_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallTeleportFunctionFeeLvl3", "86400000"));
				CH_TELE4_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallTeleportFunctionFeeLvl4", "86400000"));
				CH_SUPPORT_FEE_RATIO = Long.valueOf(clanhallSettings.getProperty("ClanHallSupportFunctionFeeRation", "86400000"));
				CH_SUPPORT1_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallSupportFeeLvl1", "86400000"));
				CH_SUPPORT2_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallSupportFeeLvl2", "86400000"));
				CH_SUPPORT3_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallSupportFeeLvl3", "86400000"));
				CH_SUPPORT4_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallSupportFeeLvl4", "86400000"));
				CH_SUPPORT5_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallSupportFeeLvl5", "86400000"));
				CH_SUPPORT6_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallSupportFeeLvl6", "86400000"));
				CH_SUPPORT7_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallSupportFeeLvl7", "86400000"));
				CH_SUPPORT8_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallSupportFeeLvl8", "86400000"));
				CH_SUPPORT9_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallSupportFeeLvl9", "86400000"));
				CH_MPREG_FEE_RATIO = Long.valueOf(clanhallSettings.getProperty("ClanHallMpRegenerationFunctionFeeRation", "86400000"));
				CH_MPREG1_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallMpRegenerationFeeLvl1", "86400000"));
				CH_MPREG2_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallMpRegenerationFeeLvl2", "86400000"));
				CH_MPREG3_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallMpRegenerationFeeLvl3", "86400000"));
				CH_MPREG4_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallMpRegenerationFeeLvl4", "86400000"));
				CH_MPREG5_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallMpRegenerationFeeLvl5", "86400000"));
				CH_HPREG_FEE_RATIO = Long.valueOf(clanhallSettings.getProperty("ClanHallHpRegenerationFunctionFeeRation", "86400000"));
				CH_HPREG1_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallHpRegenerationFeeLvl1", "86400000"));
				CH_HPREG2_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallHpRegenerationFeeLvl2", "86400000"));
				CH_HPREG3_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallHpRegenerationFeeLvl3", "86400000"));
				CH_HPREG4_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallHpRegenerationFeeLvl4", "86400000"));
				CH_HPREG5_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallHpRegenerationFeeLvl5", "86400000"));
				CH_HPREG6_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallHpRegenerationFeeLvl6", "86400000"));
				CH_HPREG7_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallHpRegenerationFeeLvl7", "86400000"));
				CH_HPREG8_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallHpRegenerationFeeLvl8", "86400000"));
				CH_HPREG9_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallHpRegenerationFeeLvl9", "86400000"));
				CH_HPREG10_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallHpRegenerationFeeLvl10", "86400000"));
				CH_HPREG11_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallHpRegenerationFeeLvl11", "86400000"));
				CH_HPREG12_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallHpRegenerationFeeLvl12", "86400000"));
				CH_HPREG13_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallHpRegenerationFeeLvl13", "86400000"));
				CH_EXPREG_FEE_RATIO = Long.valueOf(clanhallSettings.getProperty("ClanHallExpRegenerationFunctionFeeRation", "86400000"));
				CH_EXPREG1_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallExpRegenerationFeeLvl1", "86400000"));
				CH_EXPREG2_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallExpRegenerationFeeLvl2", "86400000"));
				CH_EXPREG3_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallExpRegenerationFeeLvl3", "86400000"));
				CH_EXPREG4_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallExpRegenerationFeeLvl4", "86400000"));
				CH_EXPREG5_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallExpRegenerationFeeLvl5", "86400000"));
				CH_EXPREG6_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallExpRegenerationFeeLvl6", "86400000"));
				CH_EXPREG7_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallExpRegenerationFeeLvl7", "86400000"));
				CH_ITEM_FEE_RATIO = Long.valueOf(clanhallSettings.getProperty("ClanHallItemCreationFunctionFeeRation", "86400000"));
				CH_ITEM1_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallItemCreationFunctionFeeLvl1", "86400000"));
				CH_ITEM2_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallItemCreationFunctionFeeLvl2", "86400000"));
				CH_ITEM3_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallItemCreationFunctionFeeLvl3", "86400000"));
				CH_CURTAIN_FEE_RATIO = Long.valueOf(clanhallSettings.getProperty("ClanHallCurtainFunctionFeeRation", "86400000"));
				CH_CURTAIN1_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallCurtainFunctionFeeLvl1", "86400000"));
				CH_CURTAIN2_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallCurtainFunctionFeeLvl2", "86400000"));
				CH_FRONT_FEE_RATIO = Long.valueOf(clanhallSettings.getProperty("ClanHallFrontPlatformFunctionFeeRation", "86400000"));
				CH_FRONT1_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallFrontPlatformFunctionFeeLvl1", "86400000"));
				CH_FRONT2_FEE = Integer.valueOf(clanhallSettings.getProperty("ClanHallFrontPlatformFunctionFeeLvl2", "86400000"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + CLANHALL_CONFIG_FILE + " File.");
			}
			
			// Try to load SERVER_VERSION_FILE (if exist)
			try
			{
				Properties serverVersion = new Properties();
				InputStream is = new FileInputStream(new File(SERVER_VERSION_FILE));
				serverVersion.load(is);
				is.close();
				
				SERVER_VERSION = serverVersion.getProperty("version", "Unsupported Custom Version.");
				SERVER_BUILD_DATE = serverVersion.getProperty("builddate", "Undefined Date.");
			}
			catch (Exception e)
			{
				SERVER_VERSION = "Unsupported Custom Version.";
				SERVER_BUILD_DATE = "Undefined Date.";
			}
			
			// Try to load GM_ACCESS_FILE (if exist)
			try
			{
				Properties gmSettings = new Properties();
				InputStream is = new FileInputStream(new File(GM_ACCESS_FILE));
				gmSettings.load(is);
				is.close();
				
				GM_ACCESSLEVEL = Integer.parseInt(gmSettings.getProperty("GMAccessLevel", "100"));
				GM_MIN = Integer.parseInt(gmSettings.getProperty("GMMinLevel", "100"));
				GM_ALTG_MIN_LEVEL = Integer.parseInt(gmSettings.getProperty("GMCanAltG", "100"));
				GM_ANNOUNCE = Integer.parseInt(gmSettings.getProperty("GMCanAnnounce", "100"));
				GM_BAN = Integer.parseInt(gmSettings.getProperty("GMCanBan", "100"));
				GM_BAN_CHAT = Integer.parseInt(gmSettings.getProperty("GMCanBanChat", "100"));
				GM_CREATE_ITEM = Integer.parseInt(gmSettings.getProperty("GMCanShop", "100"));
				GM_DELETE = Integer.parseInt(gmSettings.getProperty("GMCanDelete", "100"));
				GM_KICK = Integer.parseInt(gmSettings.getProperty("GMCanKick", "100"));
				GM_MENU = Integer.parseInt(gmSettings.getProperty("GMMenu", "100"));
				GM_GODMODE = Integer.parseInt(gmSettings.getProperty("GMGodMode", "100"));
				GM_CHAR_EDIT = Integer.parseInt(gmSettings.getProperty("GMCanEditChar", "100"));
				GM_CHAR_EDIT_OTHER = Integer.parseInt(gmSettings.getProperty("GMCanEditCharOther", "100"));
				GM_CHAR_VIEW = Integer.parseInt(gmSettings.getProperty("GMCanViewChar", "100"));
				GM_NPC_EDIT = Integer.parseInt(gmSettings.getProperty("GMCanEditNPC", "100"));
				GM_NPC_VIEW = Integer.parseInt(gmSettings.getProperty("GMCanViewNPC", "100"));
				GM_TELEPORT = Integer.parseInt(gmSettings.getProperty("GMCanTeleport", "100"));
				GM_TELEPORT_OTHER = Integer.parseInt(gmSettings.getProperty("GMCanTeleportOther", "100"));
				GM_RESTART = Integer.parseInt(gmSettings.getProperty("GMCanRestart", "100"));
				GM_MONSTERRACE = Integer.parseInt(gmSettings.getProperty("GMMonsterRace", "100"));
				GM_RIDER = Integer.parseInt(gmSettings.getProperty("GMRider", "100"));
				GM_ESCAPE = Integer.parseInt(gmSettings.getProperty("GMFastUnstuck", "100"));
				GM_FIXED = Integer.parseInt(gmSettings.getProperty("GMResurectFixed", "100"));
				GM_CREATE_NODES = Integer.parseInt(gmSettings.getProperty("GMCreateNodes", "100"));
				GM_ENCHANT = Integer.parseInt(gmSettings.getProperty("GMEnchant", "100"));
				GM_DOOR = Integer.parseInt(gmSettings.getProperty("GMDoor", "100"));
				GM_RES = Integer.parseInt(gmSettings.getProperty("GMRes", "100"));
				GM_PEACEATTACK = Integer.parseInt(gmSettings.getProperty("GMPeaceAttack", "100"));
				GM_HEAL = Integer.parseInt(gmSettings.getProperty("GMHeal", "100"));
				GM_UNBLOCK = Integer.parseInt(gmSettings.getProperty("GMUnblock", "100"));
				GM_CACHE = Integer.parseInt(gmSettings.getProperty("GMCache", "100"));
				GM_TALK_BLOCK = Integer.parseInt(gmSettings.getProperty("GMTalkBlock", "100"));
				GM_TEST = Integer.parseInt(gmSettings.getProperty("GMTest", "100"));
				
				String gmTrans = gmSettings.getProperty("GMDisableTransaction", "False");
				if (!gmTrans.equalsIgnoreCase("False"))
				{
					String[] params = gmTrans.split(",");
					GM_DISABLE_TRANSACTION = true;
					GM_TRANSACTION_MIN = Integer.parseInt(params[0]);
					GM_TRANSACTION_MAX = Integer.parseInt(params[1]);
				}
				else
				{
					GM_DISABLE_TRANSACTION = false;
				}
				GM_CAN_GIVE_DAMAGE = Integer.parseInt(gmSettings.getProperty("GMCanGiveDamage", "90"));
				GM_DONT_TAKE_AGGRO = Integer.parseInt(gmSettings.getProperty("GMDontTakeAggro", "90"));
				GM_DONT_TAKE_EXPSP = Integer.parseInt(gmSettings.getProperty("GMDontGiveExpSp", "90"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + GM_ACCESS_FILE + " File.");
			}
			
			// Try to load ADMIN_CONFIG_FILE (if exist)
			try
			{
				Properties AdminSettings = new Properties();
				InputStream is = new FileInputStream(new File(ADMIN_CONFIG_FILE));
				AdminSettings.load(is);
				is.close();
				
				ALT_PRIVILEGES_ADMIN = Boolean.parseBoolean(AdminSettings.getProperty("AltPrivilegesAdmin", "False"));
				ALT_PRIVILEGES_SECURE_CHECK = Boolean.parseBoolean(AdminSettings.getProperty("AltPrivilegesSecureCheck", "True"));
				ALT_PRIVILEGES_DEFAULT_LEVEL = Integer.parseInt(AdminSettings.getProperty("AltPrivilegesDefaultLevel", "100"));
				GM_HERO_AURA = Boolean.parseBoolean(AdminSettings.getProperty("GMHeroAura", "True"));
				GM_STARTUP_INVULNERABLE = Boolean.parseBoolean(AdminSettings.getProperty("GMStartupInvulnerable", "True"));
				GM_STARTUP_INVISIBLE = Boolean.parseBoolean(AdminSettings.getProperty("GMStartupInvisible", "True"));
				GM_STARTUP_SILENCE = Boolean.parseBoolean(AdminSettings.getProperty("GMStartupSilence", "False"));
				GM_STARTUP_AUTO_LIST = Boolean.parseBoolean(AdminSettings.getProperty("GMStartupAutoList", "False"));
				GM_ADMIN_MENU_STYLE = AdminSettings.getProperty("GMAdminMenuStyle", "modern");
				PETITIONING_ALLOWED = Boolean.parseBoolean(AdminSettings.getProperty("PetitioningAllowed", "True"));
				MAX_PETITIONS_PER_PLAYER = Integer.parseInt(AdminSettings.getProperty("MaxPetitionsPerPlayer", "5"));
				MAX_PETITIONS_PENDING = Integer.parseInt(AdminSettings.getProperty("MaxPetitionsPending", "25"));
				GM_NAME_COLOR_ENABLED = Boolean.parseBoolean(AdminSettings.getProperty("GMNameColorEnabled", "True"));
				ADMIN_NAME_COLOR = Integer.decode("0x" + AdminSettings.getProperty("AdminNameColor", "0000FF"));
				GM_NAME_COLOR = Integer.decode("0x" + AdminSettings.getProperty("GMNameColor", "00FF00"));
				GM_TITLE_COLOR_ENABLED = Boolean.parseBoolean(AdminSettings.getProperty("GMTitleColorEnabled", "False"));
				ADMIN_TITLE_COLOR = Integer.decode("0x" + AdminSettings.getProperty("AdminTitleColor", "00FF00"));
				GM_TITLE_COLOR = Integer.decode("0x" + AdminSettings.getProperty("GMTitleColor", "FFFF00"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + ADMIN_CONFIG_FILE + " File.");
			}
			
			// Try to load ID_CONFIG_FILE (if exist)
			try
			{
				Properties idSettings = new Properties();
				InputStream is = new FileInputStream(new File(ID_CONFIG_FILE));
				idSettings.load(is);
				is.close();
				
				MAP_TYPE = ObjectMapType.valueOf(idSettings.getProperty("L2Map", "WorldObjectMap"));
				SET_TYPE = ObjectSetType.valueOf(idSettings.getProperty("L2Set", "WorldObjectSet"));
				IDFACTORY_TYPE = IdFactoryType.valueOf(idSettings.getProperty("IDFactory", "Compaction"));
				BAD_ID_CHECKING = Boolean.valueOf(idSettings.getProperty("BadIdChecking", "True"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + ID_CONFIG_FILE + " File.");
			}
			
			// Try to load L2JHellas_CONFIG_FILE (if exist)
			try
			{
				Properties L2JHellasSettings = new Properties();
				InputStream is = new FileInputStream(new File(L2JHellas_CONFIG_FILE));
				L2JHellasSettings.load(is);
				is.close();
				
				DROP_MULTI_ADENA = Boolean.parseBoolean(L2JHellasSettings.getProperty("MultiAdenaDrop", "False"));
				CHAMPION_PASSIVE = Boolean.parseBoolean(L2JHellasSettings.getProperty("ChampionPassive", "False"));
				CHAMPION_TITLE = L2JHellasSettings.getProperty("ChampionTitle", "Champion").trim();
				CHAMPION_ENABLE = Boolean.parseBoolean(L2JHellasSettings.getProperty("ChampionEnable", "False"));
				CHAMPION_FREQUENCY = Integer.parseInt(L2JHellasSettings.getProperty("ChampionFrequency", "0"));
				CHAMPION_HP = Integer.parseInt(L2JHellasSettings.getProperty("ChampionHp", "7"));
				CHAMPION_HP_REGEN = Float.parseFloat(L2JHellasSettings.getProperty("ChampionHpRegen", "1"));
				CHAMPION_REWARDS = Integer.parseInt(L2JHellasSettings.getProperty("ChampionRewards", "8"));
				CHAMPION_ADENA = Integer.parseInt(L2JHellasSettings.getProperty("ChampionAdenasRewards", "1"));
				CHAMPION_ATK = Float.parseFloat(L2JHellasSettings.getProperty("ChampionAtk", "1"));
				CHAMPION_SPD_ATK = Float.parseFloat(L2JHellasSettings.getProperty("ChampionSpdAtk", "1"));
				CHAMPION_EXP_SP = Integer.parseInt(L2JHellasSettings.getProperty("ChampionExpSp", "8"));
				CHAMPION_BOSS = Boolean.parseBoolean(L2JHellasSettings.getProperty("ChampionBoss", "False"));
				CHAMPION_MIN_LEVEL = Integer.parseInt(L2JHellasSettings.getProperty("ChampionMinLevel", "20"));
				CHAMPION_MAX_LEVEL = Integer.parseInt(L2JHellasSettings.getProperty("ChampionMaxLevel", "60"));
				CHAMPION_MINIONS = Boolean.parseBoolean(L2JHellasSettings.getProperty("ChampionMinions", "False"));
				CHAMPION_SPCL_CHANCE = Integer.parseInt(L2JHellasSettings.getProperty("ChampionSpecialItemChance", "0"));
				CHAMPION_SPCL_ITEM = Integer.parseInt(L2JHellasSettings.getProperty("ChampionSpecialItemID", "6393"));
				CHAMPION_SPCL_QTY = Integer.parseInt(L2JHellasSettings.getProperty("ChampionSpecialItemAmount", "1"));
				CHAMPION_SPCL_LVL_DIFF = Integer.parseInt(L2JHellasSettings.getProperty("ChampionSpecialItemLevelDiff", "0"));
				LEAVE_BUFFS_ON_DIE = Boolean.parseBoolean(L2JHellasSettings.getProperty("LeaveBuffsOnDie", "True"));
				ALLOW_POTS_IN_PVP = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowPotsInPvP", "True"));
				ALLOW_SOE_IN_PVP = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowSoEInPvP", "True"));
				PVP_PK_TITLE = Boolean.parseBoolean(L2JHellasSettings.getProperty("PvpPkTitle", "False"));
				PVP_TITLE_PREFIX = L2JHellasSettings.getProperty("PvPTitlePrefix", " ");
				PK_TITLE_PREFIX = L2JHellasSettings.getProperty("PkTitlePrefix", " | ");
				PVP_COLOR_SYSTEM_ENABLED = Boolean.parseBoolean(L2JHellasSettings.getProperty("EnablePvPColorSystem", "False"));
				PVP_AMOUNT1 = Integer.parseInt(L2JHellasSettings.getProperty("PvpAmount1", "20"));
				PVP_AMOUNT2 = Integer.parseInt(L2JHellasSettings.getProperty("PvpAmount2", "50"));
				PVP_AMOUNT3 = Integer.parseInt(L2JHellasSettings.getProperty("PvpAmount3", "100"));
				PVP_AMOUNT4 = Integer.parseInt(L2JHellasSettings.getProperty("PvpAmount4", "200"));
				PVP_AMOUNT5 = Integer.parseInt(L2JHellasSettings.getProperty("PvpAmount5", "500"));
				NAME_COLOR_FOR_PVP_AMOUNT1 = Integer.decode("0x" + L2JHellasSettings.getProperty("ColorForAmount1", "FFFF00"));
				NAME_COLOR_FOR_PVP_AMOUNT2 = Integer.decode("0x" + L2JHellasSettings.getProperty("ColorForAmount2", "00FF00"));
				NAME_COLOR_FOR_PVP_AMOUNT3 = Integer.decode("0x" + L2JHellasSettings.getProperty("ColorForAmount3", "0000FF"));
				NAME_COLOR_FOR_PVP_AMOUNT4 = Integer.decode("0x" + L2JHellasSettings.getProperty("ColorForAmount4", "F66F66"));
				NAME_COLOR_FOR_PVP_AMOUNT5 = Integer.decode("0x" + L2JHellasSettings.getProperty("ColorForAmount4", "FF0000"));
				PK_COLOR_SYSTEM_ENABLED = Boolean.parseBoolean(L2JHellasSettings.getProperty("EnablePkColorSystem", "False"));
				PK_AMOUNT1 = Integer.parseInt(L2JHellasSettings.getProperty("PkAmount1", "20"));
				PK_AMOUNT2 = Integer.parseInt(L2JHellasSettings.getProperty("PkAmount2", "50"));
				PK_AMOUNT3 = Integer.parseInt(L2JHellasSettings.getProperty("PkAmount3", "100"));
				PK_AMOUNT4 = Integer.parseInt(L2JHellasSettings.getProperty("PkAmount4", "200"));
				PK_AMOUNT5 = Integer.parseInt(L2JHellasSettings.getProperty("PkAmount5", "500"));
				TITLE_COLOR_FOR_PK_AMOUNT1 = Integer.decode("0x" + L2JHellasSettings.getProperty("TitleForAmount1", "FFFF00"));
				TITLE_COLOR_FOR_PK_AMOUNT2 = Integer.decode("0x" + L2JHellasSettings.getProperty("TitleForAmount2", "00FF00"));
				TITLE_COLOR_FOR_PK_AMOUNT3 = Integer.decode("0x" + L2JHellasSettings.getProperty("TitleForAmount3", "0000FF"));
				TITLE_COLOR_FOR_PK_AMOUNT4 = Integer.decode("0x" + L2JHellasSettings.getProperty("TitleForAmount4", "F66F66"));
				TITLE_COLOR_FOR_PK_AMOUNT5 = Integer.decode("0x" + L2JHellasSettings.getProperty("TitleForAmount5", "FF0000"));
				CUSTOM_MSG_ALLOWED = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowCustomPvPMessage", "False"));
				ALLOW_PVP_REWARD = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowPvpRewardSystem", "False"));
				PVP_REWARD_ITEM = Integer.parseInt(L2JHellasSettings.getProperty("PvpRewardItem", "57"));
				PVP_REWARD_COUNT = Integer.parseInt(L2JHellasSettings.getProperty("PvpRewardAmount", "1"));
				ALLOW_PK_REWARD = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowPkRewardSystem", "False"));
				PK_REWARD_ITEM = Integer.parseInt(L2JHellasSettings.getProperty("PkRewardItem", "57"));
				PK_REWARD_COUNT = Integer.parseInt(L2JHellasSettings.getProperty("PkRewardAmount", "1"));
				DEFAULT_PK_SYSTEM = Boolean.parseBoolean(L2JHellasSettings.getProperty("UseDefaultSystem", "True"));
				CUSTOM_PK_SYSTEM = Boolean.parseBoolean(L2JHellasSettings.getProperty("UseCostumSystem", "False"));
				KARMA_MIN_KARMA = Integer.parseInt(L2JHellasSettings.getProperty("MinKarma", "240"));
				KARMA_MAX_KARMA = Integer.parseInt(L2JHellasSettings.getProperty("MaxKarma", "10000"));
				KARMA_XP_DIVIDER = Integer.parseInt(L2JHellasSettings.getProperty("XPDivider", "260"));
				KARMA_LOST_BASE = Integer.parseInt(L2JHellasSettings.getProperty("BaseKarmaLost", "0"));
				KARMA_DROP_GM = Boolean.parseBoolean(L2JHellasSettings.getProperty("CanGMDropEquipment", "False"));
				KARMA_NONDROPPABLE_PET_ITEMS = L2JHellasSettings.getProperty("ListOfPetItems", "2375,3500,3501,3502,4422,4423,4424,4425,6648,6649,6650");
				KARMA_LIST_NONDROPPABLE_PET_ITEMS = new FastList<Integer>();
				for (String id : KARMA_NONDROPPABLE_PET_ITEMS.split(","))
				{
					KARMA_LIST_NONDROPPABLE_PET_ITEMS.add(Integer.parseInt(id));
				}
				KARMA_NONDROPPABLE_ITEMS = L2JHellasSettings.getProperty("ListOfNonDroppableItems", "57,1147,425,1146,461,10,2368,7,6,2370,2369,6842,6611,6612,6613,6614,6615,6616,6617,6618,6619,6620,6621");
				KARMA_LIST_NONDROPPABLE_ITEMS = new FastList<Integer>();
				for (String id : KARMA_NONDROPPABLE_ITEMS.split(","))
				{
					KARMA_LIST_NONDROPPABLE_ITEMS.add(Integer.parseInt(id));
				}
				KARMA_PK_LIMIT = Integer.parseInt(L2JHellasSettings.getProperty("MinimumPKRequiredToDrop", "5"));
				KARMA_AWARD_PK_KILL = Boolean.parseBoolean(L2JHellasSettings.getProperty("AwardPKKillPVPPoint", "True"));
				PVP_NORMAL_TIME = Integer.parseInt(L2JHellasSettings.getProperty("PvPVsNormalTime", "15000"));
				PVP_PVP_TIME = Integer.parseInt(L2JHellasSettings.getProperty("PvPVsPvPTime", "30000"));
				ANNOUNCE_PVP_KILL = Boolean.parseBoolean(L2JHellasSettings.getProperty("AnnouncePvPKill", "False"));
				ANNOUNCE_PK_KILL = Boolean.parseBoolean(L2JHellasSettings.getProperty("AnnouncePkKill", "False"));
				CUSTOM_MSG_ON_PVP = Boolean.parseBoolean(L2JHellasSettings.getProperty("PvPCustomMessages", "False"));
				
				MOD_ALLOW_WEDDING = Boolean.valueOf(L2JHellasSettings.getProperty("AllowWedding", "False"));
				MOD_WEDDING_PRICE = Integer.parseInt(L2JHellasSettings.getProperty("WeddingPrice", "250000000"));
				MOD_WEDDING_PUNISH_INFIDELITY = Boolean.parseBoolean(L2JHellasSettings.getProperty("WeddingPunishInfidelity", "True"));
				MOD_WEDDING_TELEPORT = Boolean.parseBoolean(L2JHellasSettings.getProperty("WeddingTeleport", "True"));
				MOD_WEDDING_TELEPORT_PRICE = Integer.parseInt(L2JHellasSettings.getProperty("WeddingTeleportPrice", "50000"));
				MOD_WEDDING_TELEPORT_DURATION = Integer.parseInt(L2JHellasSettings.getProperty("WeddingTeleportDuration", "60"));
				MOD_WEDDING_SAMESEX = Boolean.parseBoolean(L2JHellasSettings.getProperty("WeddingAllowSameSex", "False"));
				MOD_WEDDING_FORMALWEAR = Boolean.parseBoolean(L2JHellasSettings.getProperty("WeddingFormalWear", "True"));
				MOD_WEDDING_DIVORCE_COSTS = Integer.parseInt(L2JHellasSettings.getProperty("WeddingDivorceCosts", "20"));
				MOD_WEDDING_ANNOUNCE = Boolean.parseBoolean(L2JHellasSettings.getProperty("AnnounceWeddings", "True"));
				BANKING_SYSTEM_ENABLED = Boolean.parseBoolean(L2JHellasSettings.getProperty("BankingEnabled", "False"));
				BANKING_SYSTEM_GOLDBARS = Integer.parseInt(L2JHellasSettings.getProperty("BankingGoldbarCount", "1"));
				BANKING_SYSTEM_ADENA = Integer.parseInt(L2JHellasSettings.getProperty("BankingAdenaCount", "500000000"));
				BANKING_SYSTEM_ITEM = Integer.parseInt(L2JHellasSettings.getProperty("BankingItemId", "3470"));
				CHAR_TITLE = Boolean.parseBoolean(L2JHellasSettings.getProperty("CharTitle", "False"));
				ADD_CHAR_TITLE = L2JHellasSettings.getProperty("CharAddTitle", "l2jhellas");
				GM_ANNOUNCER_NAME = Boolean.parseBoolean(L2JHellasSettings.getProperty("AnnounceGmName", "False"));
				KICK_L2WALKER = Boolean.parseBoolean(L2JHellasSettings.getProperty("L2WalkerProtection", "False"));
				ALLOW_CREATE_LVL = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowCustomStartLvl", "False"));
				CUSTOM_START_LVL = Integer.parseInt(L2JHellasSettings.getProperty("CustomStartLvl", "1"));
				ALLOW_HERO_SUBSKILL = Boolean.parseBoolean(L2JHellasSettings.getProperty("CustomHeroSubSkill", "False"));
				HERO_CUSTOM_ITEMS = Boolean.parseBoolean(L2JHellasSettings.getProperty("EnableHeroCustomItem", "False"));
				HERO_COUNT = Integer.parseInt(L2JHellasSettings.getProperty("HeroCount", "1"));
				GM_OVER_ENCHANT = Integer.parseInt(L2JHellasSettings.getProperty("GMOverEnchant", "0"));
				ALLOW_LOW_LEVEL_TRADE = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowLowLevelTrade", "True"));
				SHOW_HTML_WELCOME = Boolean.parseBoolean(L2JHellasSettings.getProperty("ShowWelcomeHTML", "False"));
				SHOW_HTML_GM_WELCOME = Boolean.parseBoolean(L2JHellasSettings.getProperty("ShowWelcomeGMHTML", "False"));
				RUN_SPD_BOOST = Integer.parseInt(L2JHellasSettings.getProperty("RunSpeedBoost", "0"));
				MAX_RUN_SPEED = Integer.parseInt(L2JHellasSettings.getProperty("MaxRunSpeed", "500"));
				MAX_PCRIT_RATE = Integer.parseInt(L2JHellasSettings.getProperty("MaxPCritRate", "500"));
				MAX_MCRIT_RATE = Integer.parseInt(L2JHellasSettings.getProperty("MaxMCritRate", "300"));
				ALT_MAX_EVASION = Integer.parseInt(L2JHellasSettings.getProperty("MaxEvasion", "200"));
				ALT_DAGGER_DMG_VS_HEAVY = Float.parseFloat(L2JHellasSettings.getProperty("DaggerVSHeavy", "2.50"));
				ALT_DAGGER_DMG_VS_ROBE = Float.parseFloat(L2JHellasSettings.getProperty("DaggerVSRobe", "1.80"));
				ALT_DAGGER_DMG_VS_LIGHT = Float.parseFloat(L2JHellasSettings.getProperty("DaggerVSLight", "2.00"));
				SOUL_CRYSTAL_BREAK_CHANCE = Integer.parseInt(L2JHellasSettings.getProperty("SoulCrystalBreakChance", "10"));
				SOUL_CRYSTAL_LEVEL_CHANCE = Integer.parseInt(L2JHellasSettings.getProperty("SoulCrystalLevelChance", "32"));
				ONLINE_VOICE_ALLOW = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowOnlineCommand", "False"));
				ALTERNATIVE_ENCHANT_VALUE = Integer.parseInt(L2JHellasSettings.getProperty("AlternativeEnchantValue", "1"));
				ALLOW_CLAN_LEADER_COMMAND = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowClanLeaderCommand", "False"));
				ALLOW_VERSION_COMMAND = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowVersionCommand", "False"));
				ALLOW_STAT_COMMAND = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowStatCommand", "False"));
				ALT_GAME_FLAGGED_PLAYER_CAN_USE_GK = Boolean.parseBoolean(L2JHellasSettings.getProperty("AltFlaggedPlayerCanUseGK", "False"));
				ALLOW_TVTCMDS_COMMAND = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowTvtCommands", "False"));
				ALLOW_PLAYERS_REFUSAL = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowPlayersRefusal", "False"));
				ALLOW_INFO_COMMAND = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowVoiceInfoCommand", "False"));
				ALLOW_TRADEOFF_COMMAND = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowTradeoffCommand", "False"));
				ALT_SUBCLASS_SKILLS = Boolean.parseBoolean(L2JHellasSettings.getProperty("AltSubClassSkills", "False"));
				DONATOR_NAME_COLOR_ENABLED = Boolean.parseBoolean(L2JHellasSettings.getProperty("DonatorNameColorEnabled", "False"));
				DONATOR_NAME_COLOR = Integer.decode("0x" + L2JHellasSettings.getProperty("DonatorColorName", "00FFFF"));
				DONATOR_TITLE_COLOR_ENABLED = Boolean.parseBoolean(L2JHellasSettings.getProperty("DonatorTitleColorEnabled", "False"));
				DONATOR_TITLE_COLOR = Integer.decode("0x" + L2JHellasSettings.getProperty("DonatorColorTitle", "0000FF"));
				ALLOW_VIPTELEPORT_COMMAND = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowVipTeleportCommand", "False"));
				VIP_X = Integer.parseInt(L2JHellasSettings.getProperty("LocationX", "0"));
				VIP_Y = Integer.parseInt(L2JHellasSettings.getProperty("LocationY", "0"));
				VIP_Z = Integer.parseInt(L2JHellasSettings.getProperty("LocationZ", "0"));
				WELCOME_TEXT_FOR_DONATOR_ENABLED = Boolean.parseBoolean(L2JHellasSettings.getProperty("WelcomeTextForDonators", "True"));
				WELCOME_TEXT_FOR_DONATOR_1 = L2JHellasSettings.getProperty("WelcomeDonatorText1", "Bienvenido:");
				WELCOME_TEXT_FOR_DONATOR_2 = L2JHellasSettings.getProperty("WelcomeDonatorText2", "a Tu Servidor!");
				PVPEXPSP_SYSTEM = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowAddExpSpAtPvP", "False"));
				ADD_EXP = Integer.parseInt(L2JHellasSettings.getProperty("AddExpAtPvp", "0"));
				ADD_SP = Integer.parseInt(L2JHellasSettings.getProperty("AddSpAtPvp", "0"));
				ABORT_RR = L2JHellasSettings.getProperty("AbortRestart", "L2JHellas");
				SPAWN_CHAR = Boolean.parseBoolean(L2JHellasSettings.getProperty("CustomSpawn", "False"));
				SPAWN_X = Integer.parseInt(L2JHellasSettings.getProperty("SpawnX", ""));
				SPAWN_Y = Integer.parseInt(L2JHellasSettings.getProperty("SpawnY", ""));
				SPAWN_Z = Integer.parseInt(L2JHellasSettings.getProperty("SpawnZ", ""));
				USE_SAY_FILTER = Boolean.parseBoolean(L2JHellasSettings.getProperty("UseChatFilter", "False"));
				CHAT_FILTER_CHARS = L2JHellasSettings.getProperty("ChatFilterChars", "***");
				CHAT_FILTER_PUNISHMENT = L2JHellasSettings.getProperty("ChatFilterPunishment", "off");
				CHAT_FILTER_PUNISHMENT_PARAM1 = Integer.parseInt(L2JHellasSettings.getProperty("ChatFilterPunishmentParam1", "1"));
				CHAT_FILTER_PUNISHMENT_PARAM2 = Integer.parseInt(L2JHellasSettings.getProperty("ChatFilterPunishmentParam2", "1"));
				MOD_GVE_ENABLE_FACTION = Boolean.valueOf(L2JHellasSettings.getProperty("EnableFaction", "False"));
				PRIMAR_X = Integer.parseInt(L2JHellasSettings.getProperty("PrimarBaseX", "139990"));
				PRIMAR_Y = Integer.parseInt(L2JHellasSettings.getProperty("PrimarBaseY", "-124423"));
				PRIMAR_Z = Integer.parseInt(L2JHellasSettings.getProperty("PrimarBaseZ", "-1903"));
				GOODX = Integer.parseInt(L2JHellasSettings.getProperty("GoodBaseX", "-84318"));
				GOODY = Integer.parseInt(L2JHellasSettings.getProperty("GoodBaseY", "244579"));
				GOODZ = Integer.parseInt(L2JHellasSettings.getProperty("GoodBaseZ", "-3730"));
				EVILX = Integer.parseInt(L2JHellasSettings.getProperty("EvilBaseX", "-44836"));
				EVILY = Integer.parseInt(L2JHellasSettings.getProperty("EvilBaseY", "-112524"));
				EVILZ = Integer.parseInt(L2JHellasSettings.getProperty("EvilBaseZ", "-235"));
				MOD_GVE_NAME_TEAM_GOOD = L2JHellasSettings.getProperty("NameTeamGood", "Angels");
				MOD_GVE_NAME_TEAM_EVIL = L2JHellasSettings.getProperty("NameTeamEvil", "Demons");
				MOD_GVE_COLOR_NAME_GOOD = Integer.decode("0x" + L2JHellasSettings.getProperty("ColorNameGood", "00FF00"));
				MOD_GVE_COLOR_NAME_EVIL = Integer.decode("0x" + L2JHellasSettings.getProperty("ColorNameEvil", "FF0000"));
				MOD_GVE_GET_ADENA_BY_PVP = Boolean.parseBoolean(L2JHellasSettings.getProperty("PlayerGetAdenaByPvP", "False"));
				MOD_GVE_AMMOUNT_ADENA_BY_PVP = Integer.parseInt(L2JHellasSettings.getProperty("AmmountAdenaGetByPvP", "1"));
				MOD_GVE_ACTIVE_ANIM_SS = Boolean.parseBoolean(L2JHellasSettings.getProperty("ActiveAnimeSS", "False"));
				ALLOW_CHAR_KILL_PROTECT = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowLowLvlProtect", "False"));
				ALLOW_AWAY_STATUS = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowAwayStatus", "False"));
				AWAY_PEACE_ZONE = Boolean.parseBoolean(L2JHellasSettings.getProperty("AwayOnlyInPeaceZone", "False"));
				AWAY_ALLOW_INTERFERENCE = Boolean.parseBoolean(L2JHellasSettings.getProperty("AwayAllowInterference", "False"));
				AWAY_PLAYER_TAKE_AGGRO = Boolean.parseBoolean(L2JHellasSettings.getProperty("AwayPlayerTakeAggro", "False"));
				AWAY_TITLE_COLOR = Integer.decode("0x" + L2JHellasSettings.getProperty("AwayTitleColor", "0000FF"));
				AWAY_TIMER = Integer.parseInt(L2JHellasSettings.getProperty("AwayTimer", "30"));
				BACK_TIMER = Integer.parseInt(L2JHellasSettings.getProperty("BackTimer", "30"));
				DUEL_COORD_X = Integer.parseInt(L2JHellasSettings.getProperty("DuelCoordinateX", "149319"));
				DUEL_COORD_Y = Integer.parseInt(L2JHellasSettings.getProperty("DuelCoordinateY", "46710"));
				DUEL_COORD_Z = Integer.parseInt(L2JHellasSettings.getProperty("DuelCoordinateZ", "-3413"));
				ALLOW_DAGGERS_WEAR_HEAVY = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowDaggersUseHeavy", "True"));
				ALLOW_ARCHERS_WEAR_HEAVY = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowArchersUseHeavy", "True"));
				CLAN_LEADER_COLOR_ENABLED = Boolean.parseBoolean(L2JHellasSettings.getProperty("ClanLeaderNameColorEnabled", "True"));
				CLAN_LEADER_COLOR = Integer.decode("0x" + L2JHellasSettings.getProperty("ClanLeaderColor", "00BFFF"));
				CLAN_LEADER_COLOR_CLAN_LEVEL = Integer.parseInt(L2JHellasSettings.getProperty("ClanLeaderColorAtClanLevel", "1"));
				ENABLED_MESSAGE_SYSTEM = Boolean.parseBoolean(L2JHellasSettings.getProperty("EnableMessageSystem", "False"));
				if (USE_SAY_FILTER)
				{
					try
					{
						LineNumberReader lnr = new LineNumberReader(new BufferedReader(new FileReader(new File(CHAT_FILTER_FILE))));
						String line = null;
						while ((line = lnr.readLine()) != null)
						{
							if (line.trim().length() == 0 || line.startsWith("#"))
								continue;
							FILTER_LIST.add(line.trim());
						}
						_log.info("Chat Filter: Loaded " + FILTER_LIST.size() + " words ");
					}
					catch (Exception e)
					{
						e.printStackTrace();
						throw new Error("Failed to Load " + CHAT_FILTER_FILE + " File.");
					}
				}
				ALLOW_USE_HERO_ITEM_ON_SUBCLASS = Boolean.parseBoolean(L2JHellasSettings.getProperty("AllowUseHeroItemOnSub", "False"));
				MAX_LVL_AFTER_SUB = Boolean.parseBoolean(L2JHellasSettings.getProperty("MaxLvLAfterSub", "False"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + L2JHellas_CONFIG_FILE + " File.");
			}
			
			// Try to load OLYMPIAD_FILE (if exist)
			try
			{
				Properties OlySettings = new Properties();
				InputStream is = new FileInputStream(new File(OLYMPIAD_FILE));
				OlySettings.load(is);
				is.close();
				
				OLY_START_TIME = Integer.parseInt(OlySettings.getProperty("OlyStartTime", "18"));
				OLY_MIN = Integer.parseInt(OlySettings.getProperty("OlyMin", "00"));
				OLY_CPERIOD = Long.parseLong(OlySettings.getProperty("OlyCPeriod", "21600000"));
				OLY_BATTLE = Long.parseLong(OlySettings.getProperty("OlyBattle", "360000"));
				OLY_BWAIT = Long.parseLong(OlySettings.getProperty("OlyBWait", "600000"));
				OLY_IWAIT = Long.parseLong(OlySettings.getProperty("OlyIWait", "300000"));
				OLY_WPERIOD = Long.parseLong(OlySettings.getProperty("OlyWPeriod", "604800000"));
				OLY_VPERIOD = Long.parseLong(OlySettings.getProperty("OlyVPeriod", "86400000"));
				OLY_SAME_IP = Boolean.parseBoolean(OlySettings.getProperty("OlySameIp", "True"));
				OLY_ENCHANT_LIMIT = Integer.parseInt(OlySettings.getProperty("OlyMaxEnchant", "-1"));
				OLY_RESTRICTED_ITEMS_LIST = new FastList<Integer>();
				for (String id : OlySettings.getProperty("OlyRestrictedItems", "0").split(","))
				{
					OLY_RESTRICTED_ITEMS_LIST.add(Integer.parseInt(id));
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + L2JHellas_CONFIG_FILE + " File.");
			}
			
			// Try to load GEO_FILE (if exist)
			try
			{
				Properties geoSettings = new Properties();
				InputStream is = new FileInputStream(new File(GEO_FILE));
				geoSettings.load(is);
				is.close();
				
				ACCEPT_GEOEDITOR_CONN = Boolean.parseBoolean(geoSettings.getProperty("AcceptGeoeditorConn", "False"));
				COORD_SYNCHRONIZE = Integer.parseInt(geoSettings.getProperty("CoordSynchronize", "-1"));
				GEODATA = Integer.parseInt(geoSettings.getProperty("GeoData", "0"));
				FORCE_GEODATA = Boolean.parseBoolean(geoSettings.getProperty("ForceGeoData", "True"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + GEO_FILE + " File.");
			}
			
			// Try to load MMOCORE_CONFIG_FILE (if exist)
			try
			{
				Properties mmoSettings = new Properties();
				InputStream is = new FileInputStream(new File(MMOCORE_CONFIG_FILE));
				mmoSettings.load(is);
				
				MMO_SELECTOR_SLEEP_TIME = Integer.parseInt(mmoSettings.getProperty("SleepTime", "20"));
				MMO_IO_SELECTOR_THREAD_COUNT = Integer.parseInt(mmoSettings.getProperty("IOSelectorThreadCount", "2"));
				MMO_MAX_SEND_PER_PASS = Integer.parseInt(mmoSettings.getProperty("MaxSendPerPass", "12"));
				MMO_MAX_READ_PER_PASS = Integer.parseInt(mmoSettings.getProperty("MaxReadPerPass", "12"));
				MMO_HELPER_BUFFER_COUNT = Integer.parseInt(mmoSettings.getProperty("HelperBufferCount", "20"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + MMOCORE_CONFIG_FILE + " File.");
			}
			
			// Try to load OPTIONS_FILE (if exist)
			try
			{
				Properties optionsSettings = new Properties();
				InputStream is = new FileInputStream(new File(OPTIONS_FILE));
				optionsSettings.load(is);
				is.close();
				
				TEST_SERVER = Boolean.parseBoolean(optionsSettings.getProperty("TestServer", "False"));
				SERVER_LIST_TESTSERVER = Boolean.parseBoolean(optionsSettings.getProperty("ListTestServer", "False"));
				EVERYBODY_HAS_ADMIN_RIGHTS = Boolean.parseBoolean(optionsSettings.getProperty("EverybodyHasAdminRights", "False"));
				SERVER_LIST_BRACKET = Boolean.valueOf(optionsSettings.getProperty("ServerListBrackets", "False"));
				SERVER_LIST_CLOCK = Boolean.valueOf(optionsSettings.getProperty("ServerListClock", "False"));
				SERVER_GMONLY = Boolean.valueOf(optionsSettings.getProperty("ServerGMOnly", "False"));
				ZONE_TOWN = Integer.parseInt(optionsSettings.getProperty("ZoneTown", "0"));
				DEFAULT_GLOBAL_CHAT = optionsSettings.getProperty("GlobalChat", "GLOBAL");
				DEFAULT_TRADE_CHAT = optionsSettings.getProperty("TradeChat", "ON");
				DEFAULT_PUNISH = Integer.parseInt(optionsSettings.getProperty("DefaultPunish", "2"));
				DEFAULT_PUNISH_PARAM = Integer.parseInt(optionsSettings.getProperty("DefaultPunishParam", "0"));
				BYPASS_VALIDATION = Boolean.valueOf(optionsSettings.getProperty("BypassValidation", "True"));
				GAMEGUARD_ENFORCE = Boolean.valueOf(optionsSettings.getProperty("GameGuardEnforce", "False"));
				GAMEGUARD_PROHIBITACTION = Boolean.valueOf(optionsSettings.getProperty("GameGuardProhibitAction", "False"));
				DELETE_DAYS = Integer.parseInt(optionsSettings.getProperty("DeleteCharAfterDays", "7"));
				ALLOW_DISCARDITEM = Boolean.valueOf(optionsSettings.getProperty("AllowDiscardItem", "True"));
				AUTODESTROY_ITEM_AFTER = Integer.parseInt(optionsSettings.getProperty("AutoDestroyDroppedItemAfter", "0"));
				HERB_AUTO_DESTROY_TIME = Integer.parseInt(optionsSettings.getProperty("AutoDestroyHerbTime", "15")) * 1000;
				PROTECTED_ITEMS = optionsSettings.getProperty("ListOfProtectedItems");
				LIST_PROTECTED_ITEMS = new FastList<Integer>();
				for (String id : PROTECTED_ITEMS.split(","))
				{
					LIST_PROTECTED_ITEMS.add(Integer.parseInt(id));
				}
				DESTROY_DROPPED_PLAYER_ITEM = Boolean.valueOf(optionsSettings.getProperty("DestroyPlayerDroppedItem", "True"));
				DESTROY_EQUIPABLE_PLAYER_ITEM = Boolean.valueOf(optionsSettings.getProperty("DestroyEquipableItem", "False"));
				SAVE_DROPPED_ITEM = Boolean.valueOf(optionsSettings.getProperty("SaveDroppedItem", "False"));
				EMPTY_DROPPED_ITEM_TABLE_AFTER_LOAD = Boolean.valueOf(optionsSettings.getProperty("EmptyDroppedItemTableAfterLoad", "False"));
				SAVE_DROPPED_ITEM_INTERVAL = Integer.parseInt(optionsSettings.getProperty("SaveDroppedItemInterval", "0")) * 60000;
				CLEAR_DROPPED_ITEM_TABLE = Boolean.valueOf(optionsSettings.getProperty("ClearDroppedItemTable", "False"));
				AUTODELETE_INVALID_QUEST_DATA = Boolean.valueOf(optionsSettings.getProperty("AutoDeleteInvalidQuestData", "False"));
				PRECISE_DROP_CALCULATION = Boolean.valueOf(optionsSettings.getProperty("PreciseDropCalculation", "True"));
				MULTIPLE_ITEM_DROP = Boolean.valueOf(optionsSettings.getProperty("MultipleItemDrop", "True"));
				FORCE_INVENTORY_UPDATE = Boolean.valueOf(optionsSettings.getProperty("ForceInventoryUpdate", "False"));
				LAZY_CACHE = Boolean.valueOf(optionsSettings.getProperty("LazyCache", "False"));
				MAX_DRIFT_RANGE = Integer.parseInt(optionsSettings.getProperty("MaxDriftRange", "300"));
				MIN_NPC_ANIMATION = Integer.parseInt(optionsSettings.getProperty("MinNPCAnimation", "10"));
				MAX_NPC_ANIMATION = Integer.parseInt(optionsSettings.getProperty("MaxNPCAnimation", "20"));
				MIN_MONSTER_ANIMATION = Integer.parseInt(optionsSettings.getProperty("MinMonsterAnimation", "5"));
				MAX_MONSTER_ANIMATION = Integer.parseInt(optionsSettings.getProperty("MaxMonsterAnimation", "20"));
				SERVER_NEWS = Boolean.valueOf(optionsSettings.getProperty("ShowServerNews", "False"));
				SHOW_NPC_LVL = Boolean.valueOf(optionsSettings.getProperty("ShowNpcLevel", "False"));
				ACTIVATE_POSITION_RECORDER = Boolean.valueOf(optionsSettings.getProperty("ActivatePositionRecorder", "False"));
				ALLOW_WAREHOUSE = Boolean.valueOf(optionsSettings.getProperty("AllowWarehouse", "True"));
				WAREHOUSE_CACHE = Boolean.valueOf(optionsSettings.getProperty("WarehouseCache", "False"));
				WAREHOUSE_CACHE_TIME = Integer.parseInt(optionsSettings.getProperty("WarehouseCacheTime", "15"));
				ALLOW_FREIGHT = Boolean.valueOf(optionsSettings.getProperty("AllowFreight", "True"));
				ALLOW_WEAR = Boolean.valueOf(optionsSettings.getProperty("AllowWear", "False"));
				WEAR_DELAY = Integer.parseInt(optionsSettings.getProperty("WearDelay", "5"));
				WEAR_PRICE = Integer.parseInt(optionsSettings.getProperty("WearPrice", "10"));
				ALLOW_LOTTERY = Boolean.valueOf(optionsSettings.getProperty("AllowLottery", "False"));
				ALLOW_RACE = Boolean.valueOf(optionsSettings.getProperty("AllowRace", "False"));
				ALLOW_WATER = Boolean.valueOf(optionsSettings.getProperty("AllowWater", "False"));
				ALLOW_RENTPET = Boolean.valueOf(optionsSettings.getProperty("AllowRentPet", "False"));
				ALLOWFISHING = Boolean.valueOf(optionsSettings.getProperty("AllowFishing", "False"));
				ALLOW_BOAT = Boolean.valueOf(optionsSettings.getProperty("AllowBoat", "False"));
				ALLOW_CURSED_WEAPONS = Boolean.valueOf(optionsSettings.getProperty("AllowCursedWeapons", "False"));
				ALLOW_MANOR = Boolean.parseBoolean(optionsSettings.getProperty("AllowManor", "False"));
				ALLOW_NPC_WALKERS = Boolean.valueOf(optionsSettings.getProperty("AllowNpcWalkers", "True"));
				ALLOW_L2WALKER_CLIENT = L2WalkerAllowed.valueOf(optionsSettings.getProperty("AllowL2Walker", "False"));
				L2WALKER_REVISION = Integer.parseInt(optionsSettings.getProperty("L2WalkerRevision", "537"));
				AUTOBAN_L2WALKER_ACC = Boolean.valueOf(optionsSettings.getProperty("AutobanL2WalkerAcc", "False"));
				GM_EDIT = Boolean.valueOf(optionsSettings.getProperty("GMEdit", "False"));
				ONLY_GM_ITEMS_FREE = Boolean.valueOf(optionsSettings.getProperty("OnlyGMItemsFree", "True"));
				RAID_DISABLE_CURSE = Boolean.parseBoolean(optionsSettings.getProperty("DisableRaidCurse", "False"));
				LOG_CHAT = Boolean.valueOf(optionsSettings.getProperty("LogChat", "False"));
				LOG_ITEMS = Boolean.valueOf(optionsSettings.getProperty("LogItems", "False"));
				GMAUDIT = Boolean.valueOf(optionsSettings.getProperty("GMAudit", "False"));
				COMMUNITY_TYPE = optionsSettings.getProperty("CommunityType", "old").toLowerCase();
				BBS_DEFAULT = optionsSettings.getProperty("BBSDefault", "_bbshome");
				SHOW_LEVEL_COMMUNITYBOARD = Boolean.valueOf(optionsSettings.getProperty("ShowLevelOnCommunityBoard", "False"));
				SHOW_STATUS_COMMUNITYBOARD = Boolean.valueOf(optionsSettings.getProperty("ShowStatusOnCommunityBoard", "True"));
				NAME_PAGE_SIZE_COMMUNITYBOARD = Integer.parseInt(optionsSettings.getProperty("NamePageSizeOnCommunityBoard", "50"));
				NAME_PER_ROW_COMMUNITYBOARD = Integer.parseInt(optionsSettings.getProperty("NamePerRowOnCommunityBoard", "5"));
				THREAD_P_EFFECTS = Integer.parseInt(optionsSettings.getProperty("ThreadPoolSizeEffects", "6"));
				THREAD_P_GENERAL = Integer.parseInt(optionsSettings.getProperty("ThreadPoolSizeGeneral", "15"));
				GENERAL_PACKET_THREAD_CORE_SIZE = Integer.parseInt(optionsSettings.getProperty("GeneralPacketThreadCoreSize", "4"));
				IO_PACKET_THREAD_CORE_SIZE = Integer.parseInt(optionsSettings.getProperty("UrgentPacketThreadCoreSize", "2"));
				GENERAL_THREAD_CORE_SIZE = Integer.parseInt(optionsSettings.getProperty("GeneralThreadCoreSize", "4"));
				AI_MAX_THREAD = Integer.parseInt(optionsSettings.getProperty("AiMaxThread", "10"));
				PACKET_LIFETIME = Integer.parseInt(optionsSettings.getProperty("PacketLifeTime", "0"));
				GRIDS_ALWAYS_ON = Boolean.parseBoolean(optionsSettings.getProperty("GridsAlwaysOn", "False"));
				GRID_NEIGHBOR_TURNON_TIME = Integer.parseInt(optionsSettings.getProperty("GridNeighborTurnOnTime", "30"));
				GRID_NEIGHBOR_TURNOFF_TIME = Integer.parseInt(optionsSettings.getProperty("GridNeighborTurnOffTime", "300"));
				FLOODPROTECTOR_INITIALSIZE = Integer.parseInt(optionsSettings.getProperty("FloodProtectorInitialSize", "50"));
				
				// ---------------------------------------------------
				// Configuration values not found in config files
				// ---------------------------------------------------
				
				CHECK_KNOWN = Boolean.valueOf(optionsSettings.getProperty("CheckKnownList", "False"));
				NEW_NODE_ID = Integer.parseInt(optionsSettings.getProperty("NewNodeId", "7952"));
				SELECTED_NODE_ID = Integer.parseInt(optionsSettings.getProperty("NewNodeId", "7952"));
				LINKED_NODE_ID = Integer.parseInt(optionsSettings.getProperty("NewNodeId", "7952"));
				NEW_NODE_TYPE = optionsSettings.getProperty("NewNodeType", "npc");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + OPTIONS_FILE + " File.");
			}
			
			// Try to load OTHER_CONFIG_FILE (if exist)
			try
			{
				Properties otherSettings = new Properties();
				InputStream is = new FileInputStream(new File(OTHER_CONFIG_FILE));
				otherSettings.load(is);
				is.close();
				
				STARTING_ADENA = Integer.parseInt(otherSettings.getProperty("StartingAdena", "100"));
				STARTING_ANCIENT = Integer.parseInt(otherSettings.getProperty("StartingAncientAdena", "100"));
				WYVERN_SPEED = Integer.parseInt(otherSettings.getProperty("WyvernSpeed", "100"));
				STRIDER_SPEED = Integer.parseInt(otherSettings.getProperty("StriderSpeed", "80"));
				ALLOW_WYVERN_UPGRADER = Boolean.valueOf(otherSettings.getProperty("AllowWyvernUpgrader", "False"));
				EFFECT_CANCELING = Boolean.valueOf(otherSettings.getProperty("CancelLesserEffect", "True"));
				ALLOW_GUARDS = Boolean.valueOf(otherSettings.getProperty("AllowGuards", "False"));
				DEEPBLUE_DROP_RULES = Boolean.parseBoolean(otherSettings.getProperty("UseDeepBlueDropRules", "True"));
				INVENTORY_MAXIMUM_NO_DWARF = Integer.parseInt(otherSettings.getProperty("MaximumSlotsForNoDwarf", "80"));
				INVENTORY_MAXIMUM_DWARF = Integer.parseInt(otherSettings.getProperty("MaximumSlotsForDwarf", "100"));
				INVENTORY_MAXIMUM_GM = Integer.parseInt(otherSettings.getProperty("MaximumSlotsForGMPlayer", "250"));
				WAREHOUSE_SLOTS_NO_DWARF = Integer.parseInt(otherSettings.getProperty("MaximumWarehouseSlotsForNoDwarf", "100"));
				WAREHOUSE_SLOTS_DWARF = Integer.parseInt(otherSettings.getProperty("MaximumWarehouseSlotsForDwarf", "120"));
				WAREHOUSE_SLOTS_CLAN = Integer.parseInt(otherSettings.getProperty("MaximumWarehouseSlotsForClan", "150"));
				FREIGHT_SLOTS = Integer.parseInt(otherSettings.getProperty("MaximumFreightSlots", "20"));
				ENABLE_DWARF_ENCHANT_BONUS = Boolean.parseBoolean(otherSettings.getProperty("EnableDwarfEnchantBonus", "False"));
				DWARF_ENCHANT_MIN_LEVEL = Integer.parseInt(otherSettings.getProperty("DwarfEnchantMinLevel", "80"));
				DWARF_ENCHANT_BONUS = Integer.parseInt(otherSettings.getProperty("DwarfEncahntBonus", "15"));
				HP_REGEN_MULTIPLIER = Double.parseDouble(otherSettings.getProperty("HpRegenMultiplier", "100")) / 100;
				MP_REGEN_MULTIPLIER = Double.parseDouble(otherSettings.getProperty("MpRegenMultiplier", "100")) / 100;
				CP_REGEN_MULTIPLIER = Double.parseDouble(otherSettings.getProperty("CpRegenMultiplier", "100")) / 100;
				RAID_HP_REGEN_MULTIPLIER = Double.parseDouble(otherSettings.getProperty("RaidHpRegenMultiplier", "100")) / 100;
				RAID_MP_REGEN_MULTIPLIER = Double.parseDouble(otherSettings.getProperty("RaidMpRegenMultiplier", "100")) / 100;
				RAID_P_DEFENCE_MULTIPLIER = Double.parseDouble(otherSettings.getProperty("RaidPhysicalDefenceMultiplier", "100")) / 100;
				RAID_M_DEFENCE_MULTIPLIER = Double.parseDouble(otherSettings.getProperty("RaidMagicalDefenceMultiplier", "100")) / 100;
				RAID_MIN_RESPAWN_MULTIPLIER = Float.parseFloat(otherSettings.getProperty("RaidMinRespawnMultiplier", "1.0"));
				RAID_MAX_RESPAWN_MULTIPLIER = Float.parseFloat(otherSettings.getProperty("RaidMaxRespawnMultiplier", "1.0"));
				RAID_MINION_RESPAWN_TIMER = Integer.parseInt(otherSettings.getProperty("RaidMinionRespawnTime", "300000"));
				PLAYER_SPAWN_PROTECTION = Integer.parseInt(otherSettings.getProperty("PlayerSpawnProtection", "0"));
				UNSTUCK_INTERVAL = Integer.parseInt(otherSettings.getProperty("UnstuckInterval", "300"));
				PARTY_XP_CUTOFF_METHOD = otherSettings.getProperty("PartyXpCutoffMethod", "percentage");
				PARTY_XP_CUTOFF_PERCENT = Double.parseDouble(otherSettings.getProperty("PartyXpCutoffPercent", "3."));
				PARTY_XP_CUTOFF_LEVEL = Integer.parseInt(otherSettings.getProperty("PartyXpCutoffLevel", "30"));
				RESPAWN_RESTORE_CP = Double.parseDouble(otherSettings.getProperty("RespawnRestoreCP", "0")) / 100;
				RESPAWN_RESTORE_HP = Double.parseDouble(otherSettings.getProperty("RespawnRestoreHP", "70")) / 100;
				RESPAWN_RESTORE_MP = Double.parseDouble(otherSettings.getProperty("RespawnRestoreMP", "70")) / 100;
				RESPAWN_RANDOM_ENABLED = Boolean.parseBoolean(otherSettings.getProperty("RespawnRandomInTown", "False"));
				RESPAWN_RANDOM_MAX_OFFSET = Integer.parseInt(otherSettings.getProperty("RespawnRandomMaxOffset", "50"));
				MAX_PVTSTORE_SLOTS_DWARF = Integer.parseInt(otherSettings.getProperty("MaxPvtStoreSlotsDwarf", "5"));
				MAX_PVTSTORE_SLOTS_OTHER = Integer.parseInt(otherSettings.getProperty("MaxPvtStoreSlotsOther", "4"));
				STORE_SKILL_COOLTIME = Boolean.parseBoolean(otherSettings.getProperty("StoreSkillCooltime", "True"));
				PET_RENT_NPC = otherSettings.getProperty("ListPetRentNpc", "30827");
				LIST_PET_RENT_NPC = new FastList<Integer>();
				for (String id : PET_RENT_NPC.split(","))
				{
					LIST_PET_RENT_NPC.add(Integer.parseInt(id));
				}
				ANNOUNCE_MAMMON_SPAWN = Boolean.parseBoolean(otherSettings.getProperty("AnnounceMammonSpawn", "True"));
				JAIL_IS_PVP = Boolean.valueOf(otherSettings.getProperty("JailIsPvp", "True"));
				JAIL_DISABLE_CHAT = Boolean.valueOf(otherSettings.getProperty("JailDisableChat", "True"));
				DEATH_PENALTY_CHANCE = Integer.parseInt(otherSettings.getProperty("DeathPenaltyChance", "20"));
				AUGMENT_BASESTAT = Integer.parseInt(otherSettings.getProperty("AugmentBasestat", "1"));
				AUGMENT_SKILL = Integer.parseInt(otherSettings.getProperty("AugmentSkill", "11"));
				AUGMENT_EXCLUDE_NOTDONE = Boolean.parseBoolean(otherSettings.getProperty("AugmentExcludeNotdone", "False"));
				MAX_ITEM_IN_PACKET = Math.max(INVENTORY_MAXIMUM_NO_DWARF, Math.max(INVENTORY_MAXIMUM_DWARF, INVENTORY_MAXIMUM_GM));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + OTHER_CONFIG_FILE + " File.");
			}
			
			// Try to load FLOOD_PROTECTORS_FILE (if exist)
			try
			{
				Properties antiflood = new Properties();
				InputStream is = new FileInputStream(new File(FLOOD_PROTECTORS_FILE));
				antiflood.load(is);
				is.close();
				loadFloodProtectorConfigs(antiflood);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + FLOOD_PROTECTORS_FILE + " File.");
			}
			
			// Try to load EVENT_CONFIG_FILE (if exist)
			try
			{
				Properties EventSettings = new Properties();
				InputStream is = new FileInputStream(new File(EVENT_CONFIG_FILE));
				EventSettings.load(is);
				is.close();
				
				VIP_ALLOW_INTERFERENCE = Boolean.parseBoolean(EventSettings.getProperty("VIPAllowInterference", "false"));
				VIP_ALLOW_POTIONS = Boolean.parseBoolean(EventSettings.getProperty("VIPAllowPotions", "false"));
				VIP_ON_START_REMOVE_ALL_EFFECTS = Boolean.parseBoolean(EventSettings.getProperty("VIPOnStartRemoveAllEffects", "true"));
				VIP_MIN_LEVEL = Integer.parseInt(EventSettings.getProperty("VIPMinLevel", "1"));
				if (VIP_MIN_LEVEL < 1)
					VIP_MIN_LEVEL = 1; // can't be set less then lvl 1
				VIP_MAX_LEVEL = Integer.parseInt(EventSettings.getProperty("VIPMaxLevel", "85"));
				if (VIP_MAX_LEVEL < VIP_MIN_LEVEL)
					VIP_MAX_LEVEL = VIP_MIN_LEVEL + 1; // can't be set less then
														// Min Level
				VIP_MIN_PARTICIPANTS = Integer.parseInt(EventSettings.getProperty("VIPMinParticipants", "10"));
				if (VIP_MIN_PARTICIPANTS < 10)
					VIP_MIN_PARTICIPANTS = 10; // can't be set less then lvl 10
				
				FIRST_TVT_DELAY = Integer.parseInt(EventSettings.getProperty("FirstEventDelay", "10"));
				TVT_AURA = Boolean.parseBoolean(EventSettings.getProperty("TvTAura", "true"));
				TVT_JOIN_CURSED = Boolean.parseBoolean(EventSettings.getProperty("TvTJoinWithCursedWeapon", "true"));
				TVT_PRICE_NO_KILLS = Boolean.parseBoolean(EventSettings.getProperty("TvTPriceNoKills", "false"));
				TVT_ALLOW_ENEMY_HEALING = Boolean.parseBoolean(EventSettings.getProperty("TvTAllowEnemyHealing", "false"));
				TVT_ALLOW_TEAM_CASTING = Boolean.parseBoolean(EventSettings.getProperty("TvTAllowTeamCasting", "false"));
				TVT_ALLOW_TEAM_ATTACKING = Boolean.parseBoolean(EventSettings.getProperty("TvTAllowTeamAttacking", "false"));
				TVT_CLOSE_COLISEUM_DOORS = Boolean.parseBoolean(EventSettings.getProperty("TvTCloseColiseumDoors", "false"));
				TVT_AUTO_STARTUP_ON_BOOT = Boolean.parseBoolean(EventSettings.getProperty("TvTAutoStartUpOnBoot", "false"));
				TVT_ALLOW_INTERFERENCE = Boolean.parseBoolean(EventSettings.getProperty("TvTAllowInterference", "false"));
				TVT_ALLOW_POTIONS = Boolean.parseBoolean(EventSettings.getProperty("TvTAllowPotions", "false"));
				TVT_ALLOW_SUMMON = Boolean.parseBoolean(EventSettings.getProperty("TvTAllowSummon", "false"));
				TVT_ON_START_REMOVE_ALL_EFFECTS = Boolean.parseBoolean(EventSettings.getProperty("TvTOnStartRemoveAllEffects", "true"));
				TVT_ON_START_UNSUMMON_PET = Boolean.parseBoolean(EventSettings.getProperty("TvTOnStartUnsummonPet", "true"));
				TVT_REVIVE_RECOVERY = Boolean.parseBoolean(EventSettings.getProperty("TvTReviveRecovery", "false"));
				TVT_ANNOUNCE_TEAM_STATS = Boolean.parseBoolean(EventSettings.getProperty("TvTAnnounceTeamStats", "false"));
				TVT_EVEN_TEAMS = EventSettings.getProperty("TvTEvenTeams", "BALANCE");
				TVT_ANNOUNCE_SIGNUPS = Boolean.parseBoolean(EventSettings.getProperty("TvTAnnounceSignUp", "false"));
				TVT_ANNOUNCE_REGISTRATION_LOC_NPC = Boolean.parseBoolean(EventSettings.getProperty("TvTAnnounceLocNpc", "true"));
				TVT_ANNOUNCE_TEAM_STATS = Boolean.parseBoolean(EventSettings.getProperty("TvTAnnounceTeamStats", "false"));
				TVT_ANNOUNCE_REWARD = Boolean.parseBoolean(EventSettings.getProperty("TvTAnnounceReward", "false"));
				TVT_PRICE_NO_KILLS = Boolean.parseBoolean(EventSettings.getProperty("TvTPriceNoKills", "false"));
				TVT_JOIN_CURSED = Boolean.parseBoolean(EventSettings.getProperty("TvTJoinWithCursedWeapon", "true"));
				TVT_REVIVE_DELAY = Long.parseLong(EventSettings.getProperty("TVTReviveDelay", "20000"));
				if (TVT_REVIVE_DELAY < 1000)
					TVT_REVIVE_DELAY = 1000; // can't be set less then 1 second
					
				ENABLE_HITMAN_EVENT = Boolean.parseBoolean(EventSettings.getProperty("EnableHitmanEvent", "False"));
				HITMAN_TAKE_KARMA = Boolean.parseBoolean(EventSettings.getProperty("HitmansTakekarma", "True"));
				
				DM_ALLOW_INTERFERENCE = Boolean.parseBoolean(EventSettings.getProperty("DMAllowInterference", "false"));
				DM_ALLOW_POTIONS = Boolean.parseBoolean(EventSettings.getProperty("DMAllowPotions", "false"));
				DM_ALLOW_SUMMON = Boolean.parseBoolean(EventSettings.getProperty("DMAllowSummon", "false"));
				DM_ON_START_REMOVE_ALL_EFFECTS = Boolean.parseBoolean(EventSettings.getProperty("DMOnStartRemoveAllEffects", "true"));
				DM_ON_START_UNSUMMON_PET = Boolean.parseBoolean(EventSettings.getProperty("DMOnStartUnsummonPet", "true"));
				DM_REVIVE_DELAY = Long.parseLong(EventSettings.getProperty("DMReviveDelay", "20000"));
				if (DM_REVIVE_DELAY < 1000)
					DM_REVIVE_DELAY = 1000; // can't be set less then 1 second
					
				CTF_EVEN_TEAMS = EventSettings.getProperty("CTFEvenTeams", "BALANCE");
				CTF_ALLOW_INTERFERENCE = Boolean.parseBoolean(EventSettings.getProperty("CTFAllowInterference", "false"));
				CTF_ALLOW_POTIONS = Boolean.parseBoolean(EventSettings.getProperty("CTFAllowPotions", "false"));
				CTF_ALLOW_SUMMON = Boolean.parseBoolean(EventSettings.getProperty("CTFAllowSummon", "false"));
				CTF_ON_START_REMOVE_ALL_EFFECTS = Boolean.parseBoolean(EventSettings.getProperty("CTFOnStartRemoveAllEffects", "true"));
				CTF_ON_START_UNSUMMON_PET = Boolean.parseBoolean(EventSettings.getProperty("CTFOnStartUnsummonPet", "true"));
				CTF_ANNOUNCE_TEAM_STATS = Boolean.parseBoolean(EventSettings.getProperty("CTFAnnounceTeamStats", "false"));
				CTF_ANNOUNCE_REWARD = Boolean.parseBoolean(EventSettings.getProperty("CTFAnnounceReward", "false"));
				CTF_JOIN_CURSED = Boolean.parseBoolean(EventSettings.getProperty("CTFJoinWithCursedWeapon", "true"));
				CTF_REVIVE_RECOVERY = Boolean.parseBoolean(EventSettings.getProperty("CTFReviveRecovery", "false"));
				CTF_REVIVE_DELAY = Long.parseLong(EventSettings.getProperty("CTFReviveDelay", "20000"));
				if (CTF_REVIVE_DELAY < 1000)
					CTF_REVIVE_DELAY = 1000; // can't be set less then 1 second
				RAID_SYSTEM_ENABLED = Boolean.parseBoolean(EventSettings.getProperty("RaidEnginesEnabled", "false"));
				RAID_SYSTEM_GIVE_BUFFS = Boolean.parseBoolean(EventSettings.getProperty("RaidGiveBuffs", "true"));
				RAID_SYSTEM_RESURRECT_PLAYER = Boolean.parseBoolean(EventSettings.getProperty("RaidResurrectPlayer", "true"));
				RAID_SYSTEM_MAX_EVENTS = Integer.parseInt(EventSettings.getProperty("RaidMaxNumEvents", "3"));
				RAID_SYSTEM_FIGHT_TIME = Integer.parseInt(EventSettings.getProperty("RaidSystemFightTime", "60"));
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + EVENT_CONFIG_FILE + " File.");
			}
			
			// Try to load RATES_CONFIG_FILE (if exist)
			try
			{
				Properties ratesSettings = new Properties();
				InputStream is = new FileInputStream(new File(RATES_CONFIG_FILE));
				ratesSettings.load(is);
				is.close();
				
				// Premium Service
				USE_PREMIUMSERVICE = Boolean.parseBoolean(ratesSettings.getProperty("UsePremiumServices", "False"));
				PREMIUM_RATE_XP = Float.parseFloat(ratesSettings.getProperty("PremiumRateXp", "2"));
				PREMIUM_RATE_SP = Float.parseFloat(ratesSettings.getProperty("PremiumRateSp", "2"));
				PREMIUM_RATE_DROP_SPOIL = Float.parseFloat(ratesSettings.getProperty("PremiumRateDropSpoil", "2"));
				PREMIUM_RATE_DROP_ITEMS = Float.parseFloat(ratesSettings.getProperty("PremiumRateDropItems", "2"));
				PREMIUM_RATE_DROP_QUEST = Float.parseFloat(ratesSettings.getProperty("PremiumRateDropQuest", "2"));
				PREMIUM_RATE_DROP_ITEMS_BY_RAID = Float.parseFloat(ratesSettings.getProperty("PremiumRateRaidDropItems", "2"));
				String[] propertySplitPRRateDropItemsID = ratesSettings.getProperty("PremiumRateRaidDropItemsIDs", "").split(";");
				PR_RATE_DROP_ITEMS_ID = new FastMap<Integer, Integer>(propertySplitPRRateDropItemsID.length);
				
				// For Premium Service
				if (propertySplitPRRateDropItemsID.length > 1)
				{
					for (String PremiumIDs : propertySplitPRRateDropItemsID)
					{
						String[] PremSplit = PremiumIDs.split(",");
						if (PremSplit.length != 2)
							_log.warning(StringUtil.concat("[PremiumRate]: invalid config property -> EnchantList \"", PremiumIDs, "\""));
						else
						{
							try
							{
								PR_RATE_DROP_ITEMS_ID.put(Integer.valueOf(PremSplit[0]), Integer.valueOf(PremSplit[1]));
							}
							catch (NumberFormatException nfe)
							{
								if (!PremiumIDs.isEmpty())
									_log.warning(StringUtil.concat("[PremiumRate]: invalid config property -> EnchantList \"", PremSplit[0], "\"", PremSplit[1]));
							}
						}
					}
				}

				RATE_XP = Float.parseFloat(ratesSettings.getProperty("RateXp", "1"));
				RATE_SP = Float.parseFloat(ratesSettings.getProperty("RateSp", "1"));
				RATE_PARTY_XP = Float.parseFloat(ratesSettings.getProperty("RatePartyXp", "1"));
				RATE_PARTY_SP = Float.parseFloat(ratesSettings.getProperty("RatePartySp", "1"));
				RATE_DROP_ADENA = Float.parseFloat(ratesSettings.getProperty("RateDropAdena", "1"));
				RATE_CONSUMABLE_COST = Float.parseFloat(ratesSettings.getProperty("RateConsumableCost", "1"));
				RATE_DROP_ITEMS = Float.parseFloat(ratesSettings.getProperty("RateDropItems", "1"));
				RATE_DROP_SPOIL = Float.parseFloat(ratesSettings.getProperty("RateDropSpoil", "1"));
				RATE_DROP_MANOR = Integer.parseInt(ratesSettings.getProperty("RateDropManor", "1"));
				RATE_DROP_QUEST = Float.parseFloat(ratesSettings.getProperty("RateDropQuest", "1"));
				RATE_QUESTS_REWARD = Float.parseFloat(ratesSettings.getProperty("RateQuestsReward", "1"));
				RATE_KARMA_EXP_LOST = Float.parseFloat(ratesSettings.getProperty("RateKarmaExpLost", "1"));
				RATE_SIEGE_GUARDS_PRICE = Float.parseFloat(ratesSettings.getProperty("RateSiegeGuardsPrice", "1"));
				PLAYER_DROP_LIMIT = Integer.parseInt(ratesSettings.getProperty("PlayerDropLimit", "0"));
				PLAYER_RATE_DROP = Integer.parseInt(ratesSettings.getProperty("PlayerRateDrop", "0"));
				PLAYER_RATE_DROP_ITEM = Integer.parseInt(ratesSettings.getProperty("PlayerRateDropItem", "0"));
				PLAYER_RATE_DROP_EQUIP = Integer.parseInt(ratesSettings.getProperty("PlayerRateDropEquip", "0"));
				PLAYER_RATE_DROP_EQUIP_WEAPON = Integer.parseInt(ratesSettings.getProperty("PlayerRateDropEquipWeapon", "0"));
				KARMA_DROP_LIMIT = Integer.parseInt(ratesSettings.getProperty("KarmaDropLimit", "10"));
				KARMA_RATE_DROP = Integer.parseInt(ratesSettings.getProperty("KarmaRateDrop", "70"));
				KARMA_RATE_DROP_ITEM = Integer.parseInt(ratesSettings.getProperty("KarmaRateDropItem", "50"));
				KARMA_RATE_DROP_EQUIP = Integer.parseInt(ratesSettings.getProperty("KarmaRateDropEquip", "40"));
				KARMA_RATE_DROP_EQUIP_WEAPON = Integer.parseInt(ratesSettings.getProperty("KarmaRateDropEquipWeapon", "10"));
				PET_XP_RATE = Float.parseFloat(ratesSettings.getProperty("PetXpRate", "1"));
				PET_FOOD_RATE = Integer.parseInt(ratesSettings.getProperty("PetFoodRate", "1"));
				SINEATER_XP_RATE = Float.parseFloat(ratesSettings.getProperty("SinEaterXpRate", "1"));
				RATE_DROP_COMMON_HERBS = Float.parseFloat(ratesSettings.getProperty("RateCommonHerbs", "15"));
				RATE_DROP_MP_HP_HERBS = Float.parseFloat(ratesSettings.getProperty("RateHpMpHerbs", "10"));
				RATE_DROP_GREATER_HERBS = Float.parseFloat(ratesSettings.getProperty("RateGreaterHerbs", "4"));
				RATE_DROP_SUPERIOR_HERBS = Float.parseFloat(ratesSettings.getProperty("RateSuperiorHerbs", "0.8")) * 10;
				RATE_DROP_SPECIAL_HERBS = Float.parseFloat(ratesSettings.getProperty("RateSpecialHerbs", "0.2")) * 10;
				RATE_DROP_ITEMS_BY_RAID = Float.parseFloat(ratesSettings.getProperty("RateRaidDropItems", "1"));
				ENCHANT_CHANCE_WEAPON = Integer.parseInt(ratesSettings.getProperty("EnchantChanceWeapon", "60"));
				ENCHANT_CHANCE_ARMOR = Integer.parseInt(ratesSettings.getProperty("EnchantChanceArmor", "60"));
				ENCHANT_CHANCE_JEWELRY = Integer.parseInt(ratesSettings.getProperty("EnchantChanceJewelry", "60"));
				ENCHANT_CHANCE_WEAPON_CRYSTAL = Integer.parseInt(ratesSettings.getProperty("EnchantChanceWeaponCrystal", "70"));
				ENCHANT_CHANCE_ARMOR_CRYSTAL = Integer.parseInt(ratesSettings.getProperty("EnchantChanceArmorCrystal", "70"));
				ENCHANT_CHANCE_JEWELRY_CRYSTAL = Integer.parseInt(ratesSettings.getProperty("EnchantChanceJewelryCrystal", "70"));
				ENCHANT_CHANCE_WEAPON_BLESSED = Integer.parseInt(ratesSettings.getProperty("EnchantChanceWeaponBlessed", "80"));
				ENCHANT_CHANCE_ARMOR_BLESSED = Integer.parseInt(ratesSettings.getProperty("EnchantChanceArmorBlessed", "80"));
				ENCHANT_CHANCE_JEWELRY_BLESSED = Integer.parseInt(ratesSettings.getProperty("EnchantChanceJewelryBlessed", "80"));
				ENCHANT_MAX_ALLOWED_WEAPON = Integer.parseInt(ratesSettings.getProperty("EnchantMaxAllowedWeapon", "65535"));
				ENCHANT_MAX_ALLOWED_ARMOR = Integer.parseInt(ratesSettings.getProperty("EnchantMaxAllowedArmor", "65535"));
				ENCHANT_MAX_ALLOWED_JEWELRY = Integer.parseInt(ratesSettings.getProperty("EnchantMaxAllowedJewelry", "65535"));
				ENCHANT_MAX_WEAPON = Integer.parseInt(ratesSettings.getProperty("EnchantMaxWeapon", "25"));
				ENCHANT_MAX_ARMOR = Integer.parseInt(ratesSettings.getProperty("EnchantMaxArmor", "25"));
				ENCHANT_MAX_JEWELRY = Integer.parseInt(ratesSettings.getProperty("EnchantMaxJewelry", "25"));
				ENCHANT_SAFE_MAX = Integer.parseInt(ratesSettings.getProperty("EnchantSafeMax", "3"));
				ENCHANT_SAFE_MAX_FULL = Integer.parseInt(ratesSettings.getProperty("EnchantSafeMaxFull", "4"));
				String[] propertySplitWeapon = ratesSettings.getProperty("EnchantChanceWeaponList", "").split(";");
				String[] propertySplitArmor = ratesSettings.getProperty("EnchantChanceArmorList", "").split(";");
				String[] propertySplitJewelry = ratesSettings.getProperty("EnchantChanceJewelryList", "").split(";");
				String[] propertySplitBlessedWeapon = ratesSettings.getProperty("BlessedEnchantChanceWeaponList", "").split(";");
				String[] propertySplitBlessedArmor = ratesSettings.getProperty("BlessedEnchantChanceArmorList", "").split(";");
				String[] propertySplitBlessedJewelry = ratesSettings.getProperty("BlessedEnchantChanceJewelryList", "").split(";");
				ENCHANT_CHANCE_WEAPON_LIST = new FastMap<Integer, Integer>(propertySplitWeapon.length);
				ENCHANT_CHANCE_ARMOR_LIST = new FastMap<Integer, Integer>(propertySplitArmor.length);
				ENCHANT_CHANCE_JEWELRY_LIST = new FastMap<Integer, Integer>(propertySplitJewelry.length);
				BLESSED_ENCHANT_CHANCE_WEAPON_LIST = new FastMap<Integer, Integer>(propertySplitBlessedWeapon.length);
				BLESSED_ENCHANT_CHANCE_ARMOR_LIST = new FastMap<Integer, Integer>(propertySplitBlessedArmor.length);
				BLESSED_ENCHANT_CHANCE_JEWELRY_LIST = new FastMap<Integer, Integer>(propertySplitBlessedJewelry.length);
				if (propertySplitWeapon.length > 1)
				{
					for (String enchant : propertySplitWeapon)
					{
						String[] enchantSplit = enchant.split(",");
						if (enchantSplit.length != 2)
							_log.warning(StringUtil.concat("[CustomEnchantSystem]: invalid config property -> EnchantList \"", enchant, "\""));
						else
						{
							try
							{
								ENCHANT_CHANCE_WEAPON_LIST.put(Integer.valueOf(enchantSplit[0]), Integer.valueOf(enchantSplit[1]));
							}
							catch (NumberFormatException nfe)
							{
								if (!enchant.isEmpty())
									_log.warning(StringUtil.concat("[CustomEnchantSystem]: invalid config property -> EnchantList \"", enchantSplit[0], "\"", enchantSplit[1]));
							}
						}
					}
				}
				if (propertySplitArmor.length > 1)
				{
					for (String enchant : propertySplitArmor)
					{
						String[] enchantSplit = enchant.split(",");
						if (enchantSplit.length != 2)
							_log.warning(StringUtil.concat("[CustomEnchantSystem]: invalid config property -> EnchantList \"", enchant, "\""));
						else
						{
							try
							{
								ENCHANT_CHANCE_ARMOR_LIST.put(Integer.valueOf(enchantSplit[0]), Integer.valueOf(enchantSplit[1]));
							}
							catch (NumberFormatException nfe)
							{
								if (!enchant.isEmpty())
									_log.warning(StringUtil.concat("[CustomEnchantSystem]: invalid config property -> EnchantList \"", enchantSplit[0], "\"", enchantSplit[1]));
							}
						}
					}
				}
				if (propertySplitJewelry.length > 1)
				{
					for (String enchant : propertySplitJewelry)
					{
						String[] enchantSplit = enchant.split(",");
						if (enchantSplit.length != 2)
							_log.warning(StringUtil.concat("[CustomEnchantSystem]: invalid config property -> EnchantList \"", enchant, "\""));
						else
						{
							try
							{
								ENCHANT_CHANCE_JEWELRY_LIST.put(Integer.valueOf(enchantSplit[0]), Integer.valueOf(enchantSplit[1]));
							}
							catch (NumberFormatException nfe)
							{
								if (!enchant.isEmpty())
									_log.warning(StringUtil.concat("[CustomEnchantSystem]: invalid config property -> EnchantList \"", enchantSplit[0], "\"", enchantSplit[1]));
							}
						}
					}
				}
				if (propertySplitBlessedWeapon.length > 1)
				{
					for (String enchant : propertySplitBlessedWeapon)
					{
						String[] enchantSplit = enchant.split(",");
						if (enchantSplit.length != 2)
							_log.warning(StringUtil.concat("[CustomEnchantSystem]: invalid config property -> EnchantList \"", enchant, "\""));
						else
						{
							try
							{
								BLESSED_ENCHANT_CHANCE_WEAPON_LIST.put(Integer.valueOf(enchantSplit[0]), Integer.valueOf(enchantSplit[1]));
							}
							catch (NumberFormatException nfe)
							{
								if (!enchant.isEmpty())
									_log.warning(StringUtil.concat("[CustomEnchantSystem]: invalid config property -> EnchantList \"", enchantSplit[0], "\"", enchantSplit[1]));
							}
						}
					}
				}
				if (propertySplitBlessedArmor.length > 1)
				{
					for (String enchant : propertySplitBlessedArmor)
					{
						String[] enchantSplit = enchant.split(",");
						if (enchantSplit.length != 2)
							_log.warning(StringUtil.concat("[CustomEnchantSystem]: invalid config property -> EnchantList \"", enchant, "\""));
						else
						{
							try
							{
								BLESSED_ENCHANT_CHANCE_ARMOR_LIST.put(Integer.valueOf(enchantSplit[0]), Integer.valueOf(enchantSplit[1]));
							}
							catch (NumberFormatException nfe)
							{
								if (!enchant.isEmpty())
									_log.warning(StringUtil.concat("[CustomEnchantSystem]: invalid config property -> EnchantList \"", enchantSplit[0], "\"", enchantSplit[1]));
							}
						}
					}
				}
				if (propertySplitBlessedJewelry.length > 1)
				{
					for (String enchant : propertySplitBlessedJewelry)
					{
						String[] enchantSplit = enchant.split(",");
						if (enchantSplit.length != 2)
							_log.warning(StringUtil.concat("[CustomEnchantSystem]: invalid config property -> EnchantList \"", enchant, "\""));
						else
						{
							try
							{
								BLESSED_ENCHANT_CHANCE_JEWELRY_LIST.put(Integer.valueOf(enchantSplit[0]), Integer.valueOf(enchantSplit[1]));
							}
							catch (NumberFormatException nfe)
							{
								if (!enchant.isEmpty())
									_log.warning(StringUtil.concat("[CustomEnchantSystem]: invalid config property -> EnchantList \"", enchantSplit[0], "\"", enchantSplit[1]));
							}
						}
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + RATES_CONFIG_FILE + " File.");
			}
			
			// Try to load CONFIGURATION_FILE (if exist)
			try
			{
				Properties serverSettings = new Properties();
				InputStream is = new FileInputStream(new File(CONFIGURATION_FILE));
				serverSettings.load(is);
				is.close();
				
				DEBUG = Boolean.parseBoolean(serverSettings.getProperty("Debug", "False"));
				GAMESERVER_HOSTNAME = serverSettings.getProperty("GameserverHostname");
				PORT_GAME = Integer.parseInt(serverSettings.getProperty("GameserverPort", "7777"));
				EXTERNAL_HOSTNAME = serverSettings.getProperty("ExternalHostname", "*");
				INTERNAL_HOSTNAME = serverSettings.getProperty("InternalHostname", "*");
				GAME_SERVER_LOGIN_PORT = Integer.parseInt(serverSettings.getProperty("LoginPort", "9014"));
				GAME_SERVER_LOGIN_HOST = serverSettings.getProperty("LoginHost", "127.0.0.1");
				REQUEST_ID = Integer.parseInt(serverSettings.getProperty("RequestServerID", "0"));
				ACCEPT_ALTERNATE_ID = Boolean.parseBoolean(serverSettings.getProperty("AcceptAlternateID", "True"));
				DATABASE_DRIVER = serverSettings.getProperty("Driver", "com.mysql.jdbc.Driver");
				DATABASE_URL = serverSettings.getProperty("URL", "jdbc:mysql://localhost/l2jdb");
				DATABASE_LOGIN = serverSettings.getProperty("Login", "root");
				DATABASE_PASSWORD = serverSettings.getProperty("Password", "");
				DATABASE_MAX_CONNECTIONS = Integer.parseInt(serverSettings.getProperty("MaximumDbConnections", "10"));
				DATAPACK_ROOT = new File(serverSettings.getProperty("DatapackRoot", ".")).getCanonicalFile();
				CNAME_TEMPLATE = serverSettings.getProperty("CnameTemplate", ".*");
				PET_NAME_TEMPLATE = serverSettings.getProperty("PetNameTemplate", ".*");
				MAX_CHARACTERS_NUMBER_PER_ACCOUNT = Integer.parseInt(serverSettings.getProperty("CharMaxNumber", "0"));
				MAXIMUM_ONLINE_USERS = Integer.parseInt(serverSettings.getProperty("MaximumOnlineUsers", "100"));
				MIN_PROTOCOL_REVISION = Integer.parseInt(serverSettings.getProperty("MinProtocolRevision", "660"));
				MAX_PROTOCOL_REVISION = Integer.parseInt(serverSettings.getProperty("MaxProtocolRevision", "665"));
				ALLOW_DUALBOX = Boolean.parseBoolean(serverSettings.getProperty("AllowDualBox", "True"));
				ENABLE_PACKET_PROTECTION = Boolean.parseBoolean(serverSettings.getProperty("PacketProtection", "False"));
				MAX_UNKNOWN_PACKETS = Integer.parseInt(serverSettings.getProperty("UnknownPacketsBeforeBan", "5"));
				UNKNOWN_PACKETS_PUNISHMENT = Integer.parseInt(serverSettings.getProperty("UnknownPacketsPunishment", "2"));
				if (MIN_PROTOCOL_REVISION > MAX_PROTOCOL_REVISION)
				{
					throw new Error("MinProtocolRevision is bigger than MaxProtocolRevision in server configuration file.");
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + CONFIGURATION_FILE + " File.");
			}
			
			// Try to load GRANDBOSS_CONFIG_FILE (if exist)
			try
			{
				Properties grandbossSettings = new Properties();
				InputStream is = new FileInputStream(new File(GRANDBOSS_CONFIG_FILE));
				grandbossSettings.load(is);
				is.close();
				
				Antharas_Wait_Time = Integer.parseInt(grandbossSettings.getProperty("AntharasWaitTime", "10"));
				if (Antharas_Wait_Time < 3 || Antharas_Wait_Time > 60)
					Antharas_Wait_Time = 10;
				Antharas_Wait_Time = Antharas_Wait_Time * 60000;
				Interval_Of_Antharas_Spawn = Integer.parseInt(grandbossSettings.getProperty("IntervalOfAntharasSpawn", "192"));
				if (Interval_Of_Antharas_Spawn < 1 || Interval_Of_Antharas_Spawn > 192)
					Interval_Of_Antharas_Spawn = 192;
				Interval_Of_Antharas_Spawn = Interval_Of_Antharas_Spawn * 3600000;
				Random_Of_Antharas_Spawn = Integer.parseInt(grandbossSettings.getProperty("RandomOfAntharasSpawn", "145"));
				if (Random_Of_Antharas_Spawn < 1 || Random_Of_Antharas_Spawn > 192)
					Random_Of_Antharas_Spawn = 145;
				Random_Of_Antharas_Spawn = Random_Of_Antharas_Spawn * 3600000;
				Interval_Of_Core_Spawn = Integer.parseInt(grandbossSettings.getProperty("IntervalOfCoreSpawn", "27"));
				if (Interval_Of_Core_Spawn < 1 || Interval_Of_Core_Spawn > 192)
					Interval_Of_Core_Spawn = 27;
				Interval_Of_Core_Spawn = Interval_Of_Core_Spawn * 3600000;
				Random_Of_Core_Spawn = Integer.parseInt(grandbossSettings.getProperty("RandomOfCoreSpawn", "47"));
				if (Random_Of_Core_Spawn < 1 || Random_Of_Core_Spawn > 192)
					Random_Of_Core_Spawn = 47;
				Random_Of_Core_Spawn = Random_Of_Core_Spawn * 3600000;
				Interval_Of_Orfen_Spawn = Integer.parseInt(grandbossSettings.getProperty("IntervalOfOrfenSpawn", "28"));
				if (Interval_Of_Orfen_Spawn < 1 || Interval_Of_Orfen_Spawn > 192)
					Interval_Of_Orfen_Spawn = 28;
				Interval_Of_Orfen_Spawn = Interval_Of_Orfen_Spawn * 3600000;
				Random_Of_Orfen_Spawn = Integer.parseInt(grandbossSettings.getProperty("RandomOfOrfenSpawn", "41"));
				if (Random_Of_Orfen_Spawn < 1 || Random_Of_Orfen_Spawn > 192)
					Random_Of_Orfen_Spawn = 41;
				Random_Of_Orfen_Spawn = Random_Of_Orfen_Spawn * 3600000;
				Interval_Of_QueenAnt_Spawn = Integer.parseInt(grandbossSettings.getProperty("IntervalOfQueenAntSpawn", "19"));
				if (Interval_Of_QueenAnt_Spawn < 1 || Interval_Of_QueenAnt_Spawn > 192)
					Interval_Of_QueenAnt_Spawn = 19;
				Interval_Of_QueenAnt_Spawn = Interval_Of_QueenAnt_Spawn * 3600000;
				Random_Of_QueenAnt_Spawn = Integer.parseInt(grandbossSettings.getProperty("RandomOfQueenAntSpawn", "35"));
				if (Random_Of_QueenAnt_Spawn < 1 || Random_Of_QueenAnt_Spawn > 192)
					Random_Of_QueenAnt_Spawn = 35;
				Random_Of_QueenAnt_Spawn = Random_Of_QueenAnt_Spawn * 3600000;
				Interval_Of_Zaken_Spawn = Integer.parseInt(grandbossSettings.getProperty("IntervalOfZakenSpawn", "19"));
				if (Interval_Of_Zaken_Spawn < 1 || Interval_Of_Zaken_Spawn > 192)
					Interval_Of_Zaken_Spawn = 19;
				Interval_Of_Zaken_Spawn = Interval_Of_Zaken_Spawn * 3600000;
				Random_Of_Zaken_Spawn = Integer.parseInt(grandbossSettings.getProperty("RandomOfZakenSpawn", "35"));
				if (Random_Of_Zaken_Spawn < 1 || Random_Of_Zaken_Spawn > 192)
					Random_Of_Zaken_Spawn = 35;
				Random_Of_Zaken_Spawn = Random_Of_Zaken_Spawn * 3600000;
				Interval_Of_Sailren_Spawn = Integer.parseInt(grandbossSettings.getProperty("IntervalOfSailrenSpawn", "12"));
				if (Interval_Of_Sailren_Spawn < 1 || Interval_Of_Sailren_Spawn > 192)
					Interval_Of_Sailren_Spawn = 12;
				Interval_Of_Sailren_Spawn = Interval_Of_Sailren_Spawn * 3600000;
				Random_Of_Sailren_Spawn = Integer.parseInt(grandbossSettings.getProperty("RandomOfSailrenSpawn", "24"));
				if (Random_Of_Sailren_Spawn < 1 || Random_Of_Sailren_Spawn > 192)
					Random_Of_Sailren_Spawn = 24;
				Random_Of_Sailren_Spawn = Random_Of_Sailren_Spawn * 3600000;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + GRANDBOSS_CONFIG_FILE + " File.");
			}
			
			// Try to load SEVENSIGNS_FILE (if exist)
			try
			{
				Properties SevenSettings = new Properties();
				InputStream is = new FileInputStream(new File(SEVENSIGNS_FILE));
				SevenSettings.load(is);
				is.close();
				
				ALT_GAME_REQUIRE_CASTLE_DAWN = Boolean.parseBoolean(SevenSettings.getProperty("AltRequireCastleForDawn", "False"));
				ALT_GAME_REQUIRE_CLAN_CASTLE = Boolean.parseBoolean(SevenSettings.getProperty("AltRequireClanCastle", "False"));
				ALT_FESTIVAL_MIN_PLAYER = Integer.parseInt(SevenSettings.getProperty("AltFestivalMinPlayer", "5"));
				ALT_MAXIMUM_PLAYER_CONTRIB = Integer.parseInt(SevenSettings.getProperty("AltMaxPlayerContrib", "1000000"));
				ALT_FESTIVAL_MANAGER_START = Long.parseLong(SevenSettings.getProperty("AltFestivalManagerStart", "120000"));
				ALT_FESTIVAL_LENGTH = Long.parseLong(SevenSettings.getProperty("AltFestivalLength", "1080000"));
				ALT_FESTIVAL_CYCLE_LENGTH = Long.parseLong(SevenSettings.getProperty("AltFestivalCycleLength", "2280000"));
				ALT_FESTIVAL_FIRST_SPAWN = Long.parseLong(SevenSettings.getProperty("AltFestivalFirstSpawn", "120000"));
				ALT_FESTIVAL_FIRST_SWARM = Long.parseLong(SevenSettings.getProperty("AltFestivalFirstSwarm", "300000"));
				ALT_FESTIVAL_SECOND_SPAWN = Long.parseLong(SevenSettings.getProperty("AltFestivalSecondSpawn", "540000"));
				ALT_FESTIVAL_SECOND_SWARM = Long.parseLong(SevenSettings.getProperty("AltFestivalSecondSwarm", "720000"));
				ALT_FESTIVAL_CHEST_SPAWN = Long.parseLong(SevenSettings.getProperty("AltFestivalChestSpawn", "900000"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + SEVENSIGNS_FILE + " File.");
			}
			
			// Try to load HEXID_FILE (if exist)
			try
			{
				Properties Settings = new Properties();
				InputStream is = new FileInputStream(HEXID_FILE);
				Settings.load(is);
				is.close();
				
				SERVER_ID = Integer.parseInt(Settings.getProperty("ServerID"));
				HEX_ID = new BigInteger(Settings.getProperty("HexID"), 16).toByteArray();
			}
			catch (Exception e)
			{
				_log.warning("I cant fint HexID file (" + HEXID_FILE + "). Hopefully login will give us one.");
			}
			
			// Try to load TELNET_FILE (if exist)
			try
			{
				Properties telnetSettings = new Properties();
				InputStream is = new FileInputStream(new File(TELNET_FILE));
				telnetSettings.load(is);
				is.close();
				
				IS_TELNET_ENABLED = Boolean.valueOf(telnetSettings.getProperty("EnableTelnet", "False"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + TELNET_FILE + " File.");
			}
		}
		else if (Server.serverMode == Server.MODE_LOGINSERVER)
		{
			// Try to load LOGIN_CONFIGURATION_FILE (if exist)
			try
			{
				Properties serverSettings = new Properties();
				InputStream is = new FileInputStream(new File(LOGIN_CONFIGURATION_FILE));
				serverSettings.load(is);
				is.close();
				
				GAME_SERVER_LOGIN_HOST = serverSettings.getProperty("LoginHostname", "*");
				GAME_SERVER_LOGIN_PORT = Integer.parseInt(serverSettings.getProperty("LoginPort", "9013"));
				LOGIN_BIND_ADDRESS = serverSettings.getProperty("LoginserverHostname", "*");
				PORT_LOGIN = Integer.parseInt(serverSettings.getProperty("LoginserverPort", "2106"));
				DEBUG = Boolean.parseBoolean(serverSettings.getProperty("Debug", "False"));
				DEVELOPER = Boolean.parseBoolean(serverSettings.getProperty("Developer", "False"));
				ASSERT = Boolean.parseBoolean(serverSettings.getProperty("Assert", "False"));
				ACCEPT_NEW_GAMESERVER = Boolean.parseBoolean(serverSettings.getProperty("AcceptNewGameServer", "True"));
				REQUEST_ID = Integer.parseInt(serverSettings.getProperty("RequestServerID", "0"));
				ACCEPT_ALTERNATE_ID = Boolean.parseBoolean(serverSettings.getProperty("AcceptAlternateID", "True"));
				LOGIN_TRY_BEFORE_BAN = Integer.parseInt(serverSettings.getProperty("LoginTryBeforeBan", "10"));
				LOGIN_BLOCK_AFTER_BAN = Integer.parseInt(serverSettings.getProperty("LoginBlockAfterBan", "600"));
				GM_MIN = Integer.parseInt(serverSettings.getProperty("GMMinLevel", "100"));
				DATAPACK_ROOT = new File(serverSettings.getProperty("DatapackRoot", ".")).getCanonicalFile();
				INTERNAL_HOSTNAME = serverSettings.getProperty("InternalHostname", "localhost");
				EXTERNAL_HOSTNAME = serverSettings.getProperty("ExternalHostname", "localhost");
				DATABASE_DRIVER = serverSettings.getProperty("Driver", "com.mysql.jdbc.Driver");
				DATABASE_URL = serverSettings.getProperty("URL", "jdbc:mysql://localhost/l2jdb");
				DATABASE_LOGIN = serverSettings.getProperty("Login", "root");
				DATABASE_PASSWORD = serverSettings.getProperty("Password", "");
				DATABASE_MAX_CONNECTIONS = Integer.parseInt(serverSettings.getProperty("MaximumDbConnections", "10"));
				SHOW_LICENCE = Boolean.parseBoolean(serverSettings.getProperty("ShowLicence", "True"));
				IP_UPDATE_TIME = Integer.parseInt(serverSettings.getProperty("IpUpdateTime", "15"));
				FORCE_GGAUTH = Boolean.parseBoolean(serverSettings.getProperty("ForceGGAuth", "False"));
				AUTO_CREATE_ACCOUNTS = Boolean.parseBoolean(serverSettings.getProperty("AutoCreateAccounts", "False"));
				FLOOD_PROTECTION = Boolean.parseBoolean(serverSettings.getProperty("EnableFloodProtection", "True"));
				FAST_CONNECTION_LIMIT = Integer.parseInt(serverSettings.getProperty("FastConnectionLimit", "15"));
				NORMAL_CONNECTION_TIME = Integer.parseInt(serverSettings.getProperty("NormalConnectionTime", "700"));
				FAST_CONNECTION_TIME = Integer.parseInt(serverSettings.getProperty("FastConnectionTime", "350"));
				MAX_CONNECTION_PER_IP = Integer.parseInt(serverSettings.getProperty("MaxConnectionPerIP", "50"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + CONFIGURATION_FILE + " File.");
			}
			
			// Try to load TELNET_FILE (if exist)
			try
			{
				Properties telnetSettings = new Properties();
				InputStream is = new FileInputStream(new File(TELNET_FILE));
				telnetSettings.load(is);
				is.close();
				
				IS_TELNET_ENABLED = Boolean.valueOf(telnetSettings.getProperty("EnableTelnet", "False"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + TELNET_FILE + " File.");
			}
			
			// Try to load MMOCORE_CONFIG_FILE (if exist)
			try
			{
				Properties mmoSettings = new Properties();
				InputStream is = new FileInputStream(new File(MMOCORE_CONFIG_FILE));
				mmoSettings.load(is);
				
				MMO_SELECTOR_SLEEP_TIME = Integer.parseInt(mmoSettings.getProperty("SleepTime", "20"));
				MMO_IO_SELECTOR_THREAD_COUNT = Integer.parseInt(mmoSettings.getProperty("IOSelectorThreadCount", "2"));
				MMO_MAX_SEND_PER_PASS = Integer.parseInt(mmoSettings.getProperty("MaxSendPerPass", "12"));
				MMO_MAX_READ_PER_PASS = Integer.parseInt(mmoSettings.getProperty("MaxReadPerPass", "12"));
				MMO_HELPER_BUFFER_COUNT = Integer.parseInt(mmoSettings.getProperty("HelperBufferCount", "20"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Error("Failed to Load " + MMOCORE_CONFIG_FILE + " File.");
			}
			
		}
		
		else
		{
			_log.severe("Could not Load Config: server mode was not set");
		}
		
	}
	
	/**
	 * Set a new value to a game parameter from the admin console.
	 * 
	 * @param pName
	 *        (String) : name of the parameter to change
	 * @param pValue
	 *        (String) : new value of the parameter
	 * @return boolean : True if modification has been made
	 * @link useAdminCommand
	 */
	public static boolean setParameterValue(String pName, String pValue)
	{
		// Server settings
		if (pName.equalsIgnoreCase("PremiumRateXp"))
			PREMIUM_RATE_XP = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("PremiumRateSp"))
			PREMIUM_RATE_SP = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("PremiumRateDropSpoil"))
			PREMIUM_RATE_DROP_SPOIL = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("PremiumRateDropItems"))
			PREMIUM_RATE_DROP_ITEMS = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("PremiumRateDropQuest"))
			PREMIUM_RATE_DROP_QUEST = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("PremiumRateRaidDropItems"))
			PREMIUM_RATE_DROP_ITEMS_BY_RAID = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("RateXp"))
			RATE_XP = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("RateSp"))
			RATE_SP = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("RatePartyXp"))
			RATE_PARTY_XP = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("RatePartySp"))
			RATE_PARTY_SP = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("RateQuestsReward"))
			RATE_QUESTS_REWARD = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("RateDropAdena"))
			RATE_DROP_ADENA = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("RateConsumableCost"))
			RATE_CONSUMABLE_COST = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("RateDropItems"))
			RATE_DROP_ITEMS = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("RateDropSpoil"))
			RATE_DROP_SPOIL = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("RateDropManor"))
			RATE_DROP_MANOR = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("RateDropQuest"))
			RATE_DROP_QUEST = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("RateKarmaExpLost"))
			RATE_KARMA_EXP_LOST = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("RateSiegeGuardsPrice"))
			RATE_SIEGE_GUARDS_PRICE = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("PlayerDropLimit"))
			PLAYER_DROP_LIMIT = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("PlayerRateDrop"))
			PLAYER_RATE_DROP = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("PlayerRateDropItem"))
			PLAYER_RATE_DROP_ITEM = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("PlayerRateDropEquip"))
			PLAYER_RATE_DROP_EQUIP = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("PlayerRateDropEquipWeapon"))
			PLAYER_RATE_DROP_EQUIP_WEAPON = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("KarmaDropLimit"))
			KARMA_DROP_LIMIT = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("KarmaRateDrop"))
			KARMA_RATE_DROP = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("KarmaRateDropItem"))
			KARMA_RATE_DROP_ITEM = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("KarmaRateDropEquip"))
			KARMA_RATE_DROP_EQUIP = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("KarmaRateDropEquipWeapon"))
			KARMA_RATE_DROP_EQUIP_WEAPON = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("AutoDestroyDroppedItemAfter"))
			AUTODESTROY_ITEM_AFTER = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("DestroyPlayerDroppedItem"))
			DESTROY_DROPPED_PLAYER_ITEM = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("DestroyEquipableItem"))
			DESTROY_EQUIPABLE_PLAYER_ITEM = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("SaveDroppedItem"))
			SAVE_DROPPED_ITEM = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("EmptyDroppedItemTableAfterLoad"))
			EMPTY_DROPPED_ITEM_TABLE_AFTER_LOAD = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("SaveDroppedItemInterval"))
			SAVE_DROPPED_ITEM_INTERVAL = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("ClearDroppedItemTable"))
			CLEAR_DROPPED_ITEM_TABLE = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("PreciseDropCalculation"))
			PRECISE_DROP_CALCULATION = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("MultipleItemDrop"))
			MULTIPLE_ITEM_DROP = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("CoordSynchronize"))
			COORD_SYNCHRONIZE = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("DeleteCharAfterDays"))
			DELETE_DAYS = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("AllowDiscardItem"))
			ALLOW_DISCARDITEM = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AllowFreight"))
			ALLOW_FREIGHT = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AllowWarehouse"))
			ALLOW_WAREHOUSE = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AllowWear"))
			ALLOW_WEAR = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("WearDelay"))
			WEAR_DELAY = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("WearPrice"))
			WEAR_PRICE = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("AllowWater"))
			ALLOW_WATER = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AllowRentPet"))
			ALLOW_RENTPET = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AllowBoat"))
			ALLOW_BOAT = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AllowCursedWeapons"))
			ALLOW_CURSED_WEAPONS = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AllowManor"))
			ALLOW_MANOR = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("BypassValidation"))
			BYPASS_VALIDATION = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("CommunityType"))
			COMMUNITY_TYPE = pValue.toLowerCase();
		else if (pName.equalsIgnoreCase("BBSDefault"))
			BBS_DEFAULT = pValue;
		else if (pName.equalsIgnoreCase("ShowLevelOnCommunityBoard"))
			SHOW_LEVEL_COMMUNITYBOARD = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("ShowStatusOnCommunityBoard"))
			SHOW_STATUS_COMMUNITYBOARD = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("NamePageSizeOnCommunityBoard"))
			NAME_PAGE_SIZE_COMMUNITYBOARD = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("NamePerRowOnCommunityBoard"))
			NAME_PER_ROW_COMMUNITYBOARD = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("ShowServerNews"))
			SERVER_NEWS = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("ShowNpcLevel"))
			SHOW_NPC_LVL = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("ForceInventoryUpdate"))
			FORCE_INVENTORY_UPDATE = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AutoDeleteInvalidQuestData"))
			AUTODELETE_INVALID_QUEST_DATA = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("MaximumOnlineUsers"))
			MAXIMUM_ONLINE_USERS = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("PacketProtection"))
			ENABLE_PACKET_PROTECTION = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("UnknownPacketsBeforeBan"))
			MAX_UNKNOWN_PACKETS = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("UnknownPacketsPunishment"))
			UNKNOWN_PACKETS_PUNISHMENT = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("ZoneTown"))
			ZONE_TOWN = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("CheckKnownList"))
			CHECK_KNOWN = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("ReputationScorePerKill"))
			ALT_REPUTATION_SCORE_PER_KILL = Integer.parseInt(pValue);
		
		// Other settings
		else if (pName.equalsIgnoreCase("UseDeepBlueDropRules"))
			DEEPBLUE_DROP_RULES = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AllowGuards"))
			ALLOW_GUARDS = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("CancelLesserEffect"))
			EFFECT_CANCELING = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("WyvernSpeed"))
			WYVERN_SPEED = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("StriderSpeed"))
			STRIDER_SPEED = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("MaximumSlotsForNoDwarf"))
			INVENTORY_MAXIMUM_NO_DWARF = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("MaximumSlotsForDwarf"))
			INVENTORY_MAXIMUM_DWARF = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("MaximumSlotsForGMPlayer"))
			INVENTORY_MAXIMUM_GM = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("MaximumWarehouseSlotsForNoDwarf"))
			WAREHOUSE_SLOTS_NO_DWARF = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("MaximumWarehouseSlotsForDwarf"))
			WAREHOUSE_SLOTS_DWARF = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("MaximumWarehouseSlotsForClan"))
			WAREHOUSE_SLOTS_CLAN = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("MaximumFreightSlots"))
			FREIGHT_SLOTS = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("HpRegenMultiplier"))
			HP_REGEN_MULTIPLIER = Double.parseDouble(pValue);
		else if (pName.equalsIgnoreCase("MpRegenMultiplier"))
			MP_REGEN_MULTIPLIER = Double.parseDouble(pValue);
		else if (pName.equalsIgnoreCase("CpRegenMultiplier"))
			CP_REGEN_MULTIPLIER = Double.parseDouble(pValue);
		else if (pName.equalsIgnoreCase("RaidHpRegenMultiplier"))
			RAID_HP_REGEN_MULTIPLIER = Double.parseDouble(pValue);
		else if (pName.equalsIgnoreCase("RaidMpRegenMultiplier"))
			RAID_MP_REGEN_MULTIPLIER = Double.parseDouble(pValue);
		else if (pName.equalsIgnoreCase("RaidPhysicalDefenceMultiplier"))
			RAID_P_DEFENCE_MULTIPLIER = Double.parseDouble(pValue) / 100;
		else if (pName.equalsIgnoreCase("RaidMagicalDefenceMultiplier"))
			RAID_M_DEFENCE_MULTIPLIER = Double.parseDouble(pValue) / 100;
		else if (pName.equalsIgnoreCase("RaidMinionRespawnTime"))
			RAID_MINION_RESPAWN_TIMER = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("StartingAdena"))
			STARTING_ADENA = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("StartingAncientAdena"))
			STARTING_ANCIENT = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("UnstuckInterval"))
			UNSTUCK_INTERVAL = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("PlayerSpawnProtection"))
			PLAYER_SPAWN_PROTECTION = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("PartyXpCutoffMethod"))
			PARTY_XP_CUTOFF_METHOD = pValue;
		else if (pName.equalsIgnoreCase("PartyXpCutoffPercent"))
			PARTY_XP_CUTOFF_PERCENT = Double.parseDouble(pValue);
		else if (pName.equalsIgnoreCase("PartyXpCutoffLevel"))
			PARTY_XP_CUTOFF_LEVEL = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("RespawnRestoreCP"))
			RESPAWN_RESTORE_CP = Double.parseDouble(pValue) / 100;
		else if (pName.equalsIgnoreCase("RespawnRestoreHP"))
			RESPAWN_RESTORE_HP = Double.parseDouble(pValue) / 100;
		else if (pName.equalsIgnoreCase("RespawnRestoreMP"))
			RESPAWN_RESTORE_MP = Double.parseDouble(pValue) / 100;
		else if (pName.equalsIgnoreCase("MaxPvtStoreSlotsDwarf"))
			MAX_PVTSTORE_SLOTS_DWARF = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("MaxPvtStoreSlotsOther"))
			MAX_PVTSTORE_SLOTS_OTHER = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("StoreSkillCooltime"))
			STORE_SKILL_COOLTIME = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AnnounceMammonSpawn"))
			ANNOUNCE_MAMMON_SPAWN = Boolean.valueOf(pValue);
		
		// Alternative settings
		else if (pName.equalsIgnoreCase("AltGameTiredness"))
			ALT_GAME_TIREDNESS = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltGameCreation"))
			ALT_GAME_CREATION = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltGameCreationSpeed"))
			ALT_GAME_CREATION_SPEED = Double.parseDouble(pValue);
		else if (pName.equalsIgnoreCase("AltGameCreationXpRate"))
			ALT_GAME_CREATION_XP_RATE = Double.parseDouble(pValue);
		else if (pName.equalsIgnoreCase("AltGameCreationSpRate"))
			ALT_GAME_CREATION_SP_RATE = Double.parseDouble(pValue);
		else if (pName.equalsIgnoreCase("AltWeightLimit"))
			ALT_WEIGHT_LIMIT = Double.parseDouble(pValue);
		else if (pName.equalsIgnoreCase("AltBlacksmithUseRecipes"))
			ALT_BLACKSMITH_USE_RECIPES = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltGameSkillLearn"))
			ALT_GAME_SKILL_LEARN = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("RemoveCastleCirclets"))
			REMOVE_CASTLE_CIRCLETS = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltGameCancelByHit"))
		{
			ALT_GAME_CANCEL_BOW = pValue.equalsIgnoreCase("bow") || pValue.equalsIgnoreCase("all");
			ALT_GAME_CANCEL_CAST = pValue.equalsIgnoreCase("cast") || pValue.equalsIgnoreCase("all");
		}
		else if (pName.equalsIgnoreCase("AltShieldBlocks"))
			ALT_GAME_SHIELD_BLOCKS = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltPerfectShieldBlockRate"))
			ALT_PERFECT_SHLD_BLOCK = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("Delevel"))
			ALT_GAME_DELEVEL = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("MagicFailures"))
			ALT_GAME_MAGICFAILURES = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltGameMobAttackAI"))
			ALT_GAME_MOB_ATTACK_AI = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltMobAgroInPeaceZone"))
			ALT_MOB_AGRO_IN_PEACEZONE = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltGameExponentXp"))
			ALT_GAME_EXPONENT_XP = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("AltGameExponentSp"))
			ALT_GAME_EXPONENT_SP = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("AltGameFreights"))
			ALT_GAME_FREIGHTS = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltGameFreightPrice"))
			ALT_GAME_FREIGHT_PRICE = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("AltPartyRange"))
			ALT_PARTY_RANGE = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("AltPartyRange2"))
			ALT_PARTY_RANGE2 = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("CraftingEnabled"))
			IS_CRAFTING_ENABLED = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("LifeCrystalNeeded"))
			LIFE_CRYSTAL_NEEDED = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("SpBookNeeded"))
			SP_BOOK_NEEDED = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AutoLoot"))
			AUTO_LOOT = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AutoLootHerbs"))
			AUTO_LOOT_HERBS = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltKarmaPlayerCanBeKilledInPeaceZone"))
			ALT_GAME_KARMA_PLAYER_CAN_BE_KILLED_IN_PEACEZONE = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltFlaggedPlayerCanUseGK"))
			ALT_GAME_FLAGGED_PLAYER_CAN_USE_GK = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("AltKarmaPlayerCanShop"))
			ALT_GAME_KARMA_PLAYER_CAN_SHOP = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltKarmaPlayerCanUseGK"))
			ALT_GAME_KARMA_PLAYER_CAN_USE_GK = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltKarmaPlayerCanTeleport"))
			ALT_GAME_KARMA_PLAYER_CAN_TELEPORT = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltKarmaPlayerCanTrade"))
			ALT_GAME_KARMA_PLAYER_CAN_TRADE = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltKarmaPlayerCanUseWareHouse"))
			ALT_GAME_KARMA_PLAYER_CAN_USE_WAREHOUSE = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltRequireCastleForDawn"))
			ALT_GAME_REQUIRE_CASTLE_DAWN = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltRequireClanCastle"))
			ALT_GAME_REQUIRE_CLAN_CASTLE = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltFreeTeleporting"))
			ALT_GAME_FREE_TELEPORT = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltSubClassSkills"))
			ALT_SUBCLASS_SKILLS = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("MaxPAtkSpeed"))
			MAX_PATK_SPEED = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("MaxMAtkSpeed"))
			MAX_MATK_SPEED = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("AllowLowLevelTrade"))
			ALLOW_LOW_LEVEL_TRADE = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("AllowPotsInPvP"))
			ALLOW_POTS_IN_PVP = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("DisableGradePenalty"))
			DISABLE_GRADE_PENALTY = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("DisableWeightPenalty"))
			DISABLE_WEIGHT_PENALTY = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltSubClassWithoutQuests"))
			ALT_GAME_SUBCLASS_WITHOUT_QUESTS = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltNewCharAlwaysIsNewbie"))
			ALT_GAME_NEW_CHAR_ALWAYS_IS_NEWBIE = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AltMembersCanWithdrawFromClanWH"))
			ALT_MEMBERS_CAN_WITHDRAW_FROM_CLANWH = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("DwarfRecipeLimit"))
			DWARF_RECIPE_LIMIT = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("CommonRecipeLimit"))
			COMMON_RECIPE_LIMIT = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("CastleShieldRestriction"))
			CASTLE_SHIELD = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("ClanHallShieldRestriction"))
			CLANHALL_SHIELD = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("ApellaArmorsRestriction"))
			APELLA_ARMORS = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("OathArmorsRestriction"))
			OATH_ARMORS = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("CastleLordsCrownRestriction"))
			CASTLE_CROWN = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("CastleCircletsRestriction"))
			CASTLE_CIRCLETS = Boolean.valueOf(pValue);
		
		// ChampionMobs MOD
		else if (pName.equalsIgnoreCase("ChampionSpecialItemLevelDiff"))
			CHAMPION_SPCL_LVL_DIFF = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("ChampionEnable"))
			CHAMPION_ENABLE = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("ChampionFrequency"))
			CHAMPION_FREQUENCY = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("ChampionHp"))
			CHAMPION_HP = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("ChampionHpRegen"))
			CHAMPION_HP_REGEN = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("ChampionAtk"))
			CHAMPION_ATK = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("ChampionSpdAtk"))
			CHAMPION_SPD_ATK = Float.parseFloat(pValue);
		else if (pName.equalsIgnoreCase("ChampionRewards"))
			CHAMPION_REWARDS = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("ChampionAdenasRewards"))
			CHAMPION_ADENA = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("ChampionExpSp"))
			CHAMPION_EXP_SP = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("ChampionBoss"))
			CHAMPION_BOSS = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("ChampionMinLevel"))
			CHAMPION_MIN_LEVEL = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("ChampionMaxLevel"))
			CHAMPION_MAX_LEVEL = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("ChampionMinions"))
			CHAMPION_MINIONS = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("ChampionSpecialItemChance"))
			CHAMPION_SPCL_CHANCE = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("ChampionSpecialItemID"))
			CHAMPION_SPCL_ITEM = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("ChampionSpecialItemAmount"))
			CHAMPION_SPCL_QTY = Integer.parseInt(pValue);
		
		// MOD Wedding System
		else if (pName.equalsIgnoreCase("AllowWedding"))
			MOD_ALLOW_WEDDING = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("WeddingPrice"))
			MOD_WEDDING_PRICE = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("AutoLootRaid"))
			AUTO_LOOT_RAID = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("WeddingPunishInfidelity"))
			MOD_WEDDING_PUNISH_INFIDELITY = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("WeddingTeleport"))
			MOD_WEDDING_TELEPORT = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("WeddingTeleportPrice"))
			MOD_WEDDING_TELEPORT_PRICE = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("WeddingTeleportDuration"))
			MOD_WEDDING_TELEPORT_DURATION = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("WeddingAllowSameSex"))
			MOD_WEDDING_SAMESEX = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("WeddingFormalWear"))
			MOD_WEDDING_FORMALWEAR = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("WeddingDivorceCosts"))
			MOD_WEDDING_DIVORCE_COSTS = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("AddExpAtPvp"))
			ADD_EXP = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("AddSpAtPvp"))
			ADD_SP = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("AbortRestart"))
			ABORT_RR = (pValue);
		
		// Faction Good vs Evil
		else if (pName.equalsIgnoreCase("EnableFaction"))
			MOD_GVE_ENABLE_FACTION = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("PlayerGetAdenaByPvP"))
			MOD_GVE_GET_ADENA_BY_PVP = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AmmountAdenaGetByPvP"))
			MOD_GVE_AMMOUNT_ADENA_BY_PVP = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("ActiveAnimSS"))
			MOD_GVE_ACTIVE_ANIM_SS = Boolean.valueOf(pValue);
		
		// Event Settings
		else if (pName.equalsIgnoreCase("CTFEvenTeams"))
			CTF_EVEN_TEAMS = pValue;
		else if (pName.equalsIgnoreCase("CTFAllowInterference"))
			CTF_ALLOW_INTERFERENCE = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("CTFAllowPotions"))
			CTF_ALLOW_POTIONS = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("CTFAllowSummon"))
			CTF_ALLOW_SUMMON = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("CTFOnStartRemoveAllEffects"))
			CTF_ON_START_REMOVE_ALL_EFFECTS = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("CTFOnStartUnsummonPet"))
			CTF_ON_START_UNSUMMON_PET = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("CTFReviveDelay"))
			CTF_REVIVE_DELAY = Long.parseLong(pValue);
		else if (pName.equalsIgnoreCase("CTFEvenTeams"))
			CTF_EVEN_TEAMS = pValue;
		else if (pName.equalsIgnoreCase("TvTAllowInterference"))
			TVT_ALLOW_INTERFERENCE = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("TvTAllowPotions"))
			TVT_ALLOW_POTIONS = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("TvTAllowSummon"))
			TVT_ALLOW_SUMMON = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("TvTOnStartRemoveAllEffects"))
			TVT_ON_START_REMOVE_ALL_EFFECTS = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("TvTOnStartUnsummonPet"))
			TVT_ON_START_UNSUMMON_PET = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("TvTEvenTeams"))
			TVT_EVEN_TEAMS = pValue;
		else if (pName.equalsIgnoreCase("DMAllowInterference"))
			DM_ALLOW_INTERFERENCE = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("DMAllowPotions"))
			DM_ALLOW_POTIONS = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("DMAllowSummon"))
			DM_ALLOW_SUMMON = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("DMOnStartRemoveAllEffects"))
			DM_ON_START_REMOVE_ALL_EFFECTS = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("DMOnStartUnsummonPet"))
			DM_ON_START_UNSUMMON_PET = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("VIPAllowInterference"))
			VIP_ALLOW_INTERFERENCE = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("VIPAllowPotions"))
			VIP_ALLOW_POTIONS = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("VIPOnStartRemoveAllEffects"))
			VIP_ON_START_REMOVE_ALL_EFFECTS = Boolean.parseBoolean(pValue);
		else if (pName.equalsIgnoreCase("VIPMinLevel"))
			VIP_MIN_LEVEL = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("VIPMaxLevel"))
			VIP_MAX_LEVEL = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("VIPMinParticipants"))
			VIP_MIN_PARTICIPANTS = Integer.parseInt(pValue);
		
		// Rate Settings
		else if (pName.equalsIgnoreCase("EnchantChanceWeapon"))
			ENCHANT_CHANCE_WEAPON = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("EnchantChanceArmor"))
			ENCHANT_CHANCE_ARMOR = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("EnchantChanceJewelry"))
			ENCHANT_CHANCE_JEWELRY = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("EnchantMaxWeapon"))
			ENCHANT_MAX_WEAPON = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("EnchantMaxArmor"))
			ENCHANT_MAX_ARMOR = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("EnchantMaxJewelry"))
			ENCHANT_MAX_JEWELRY = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("EnchantSafeMax"))
			ENCHANT_SAFE_MAX = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("EnchantSafeMaxFull"))
			ENCHANT_SAFE_MAX_FULL = Integer.parseInt(pValue);
		
		// PvP Settings
		else if (pName.equalsIgnoreCase("MinKarma"))
			KARMA_MIN_KARMA = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("MaxKarma"))
			KARMA_MAX_KARMA = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("XPDivider"))
			KARMA_XP_DIVIDER = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("BaseKarmaLost"))
			KARMA_LOST_BASE = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("CanGMDropEquipment"))
			KARMA_DROP_GM = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AwardPKKillPVPPoint"))
			KARMA_AWARD_PK_KILL = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("MinimumPKRequiredToDrop"))
			KARMA_PK_LIMIT = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("PvPVsNormalTime"))
			PVP_NORMAL_TIME = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("PvPVsPvPTime"))
			PVP_PVP_TIME = Integer.parseInt(pValue);
		else if (pName.equalsIgnoreCase("AnnouncePvPKill"))
			ANNOUNCE_PVP_KILL = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("AnnouncePkKill"))
			ANNOUNCE_PK_KILL = Boolean.valueOf(pValue);
		else if (pName.equalsIgnoreCase("GlobalChat"))
			DEFAULT_GLOBAL_CHAT = pValue;
		else if (pName.equalsIgnoreCase("TradeChat"))
			DEFAULT_TRADE_CHAT = pValue;
		else if (pName.equalsIgnoreCase("MenuStyle"))
			GM_ADMIN_MENU_STYLE = pValue;
		else
			return false;
		return true;
		
	}
	
	private static void loadFloodProtectorConfigs(final Properties properties)
	{
		loadFloodProtectorConfig(properties, FLOOD_PROTECTOR_USE_ITEM, "UseItem", "4");
		loadFloodProtectorConfig(properties, FLOOD_PROTECTOR_ROLL_DICE, "RollDice", "42");
		loadFloodProtectorConfig(properties, FLOOD_PROTECTOR_FIREWORK, "Firework", "42");
		loadFloodProtectorConfig(properties, FLOOD_PROTECTOR_ITEM_PET_SUMMON, "ItemPetSummon", "16");
		loadFloodProtectorConfig(properties, FLOOD_PROTECTOR_HERO_VOICE, "HeroVoice", "100");
		loadFloodProtectorConfig(properties, FLOOD_PROTECTOR_GLOBAL_CHAT, "GlobalChat", "5");
		loadFloodProtectorConfig(properties, FLOOD_PROTECTOR_SUBCLASS, "Subclass", "20");
		loadFloodProtectorConfig(properties, FLOOD_PROTECTOR_DROP_ITEM, "DropItem", "10");
		loadFloodProtectorConfig(properties, FLOOD_PROTECTOR_SERVER_BYPASS, "ServerBypass", "5");
		loadFloodProtectorConfig(properties, FLOOD_PROTECTOR_MULTISELL, "MultiSell", "1");
		loadFloodProtectorConfig(properties, FLOOD_PROTECTOR_TRANSACTION, "Transaction", "10");
	}
	
	private static void loadFloodProtectorConfig(final Properties properties, final FloodProtectorConfig config, final String configString, final String defaultInterval)
	{
		config.FLOOD_PROTECTION_INTERVAL = Integer.parseInt(properties.getProperty(StringUtil.concat("FloodProtector", configString, "Interval"), defaultInterval));
		config.LOG_FLOODING = Boolean.parseBoolean(properties.getProperty(StringUtil.concat("FloodProtector", configString, "LogFlooding"), "False"));
		config.PUNISHMENT_LIMIT = Integer.parseInt(properties.getProperty(StringUtil.concat("FloodProtector", configString, "PunishmentLimit"), "0"));
		config.PUNISHMENT_TYPE = properties.getProperty(StringUtil.concat("FloodProtector", configString, "PunishmentType"), "none");
		config.PUNISHMENT_TIME = Integer.parseInt(properties.getProperty(StringUtil.concat("FloodProtector", configString, "PunishmentTime"), "0"));
	}
	
	/**
	 * Allow the player to use L2Walker ?
	 * 
	 * @param player
	 *        (L2PcInstance) : Player trying to use L2Walker
	 * @return boolean : True if (L2Walker allowed as a general rule) or
	 *         (L2Walker client allowed for GM and player is a GM)
	 */
	public static boolean allowL2Walker(L2PcInstance player)
	{
		return (ALLOW_L2WALKER_CLIENT == L2WalkerAllowed.True || (ALLOW_L2WALKER_CLIENT == L2WalkerAllowed.GM && player != null && player.isGM()));
	}

	// it has no instances
	private Config()
	{
	}

	/**
	 * Save hexadecimal ID of the server in the ini file.
	 * 
	 * @param string
	 *        (String) : hexadecimal ID of the server to store
	 * @see HEXID_FILE
	 * @see saveHexid(String string, String fileName)
	 * @link LoginServerThread
	 */
	public static void saveHexid(int serverId, String string)
	{
		Config.saveHexid(serverId, string, HEXID_FILE);
	}

	/**
	 * Save hexadecimal ID of the server in the ini file.
	 * 
	 * @param hexId
	 *        (String) : hexadecimal ID of the server to store
	 * @param fileName
	 *        (String) : name of the ini file
	 */
	public static void saveHexid(int serverId, String hexId, String fileName)
	{
		try
		{
			Properties hexSetting = new Properties();
			File file = new File(fileName);
			// Create a new empty file only if it doesn't exist
			file.createNewFile();
			OutputStream out = new FileOutputStream(file);
			hexSetting.setProperty("ServerID", String.valueOf(serverId));
			hexSetting.setProperty("HexID", hexId);
			hexSetting.store(out, "the hexID to auth into login");
			out.close();
		}
		catch (Exception e)
		{
			_log.warning("Failed to save hex id to " + fileName + " File.");
			e.printStackTrace();
		}
	}

	/**
	 * Loads all Filter Words
	 */
	public static String FILTER_FILE = "./config/chatfilter.txt";
	
	// ==============================================================
	public static void loadFilter()
	{
		try
		{
			LineNumberReader lnr = new LineNumberReader(new BufferedReader(new FileReader(new File(FILTER_FILE))));
			String line = null;
			while ((line = lnr.readLine()) != null)
			{
				if (line.trim().length() == 0 || line.startsWith("#"))
					continue;

				FILTER_LIST.add(line.trim());
			}
			_log.info("Loaded " + FILTER_LIST.size() + " Filter Words.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Error("Failed to Load " + FILTER_FILE + " File.");
		}
	}
	
	/**
	 * Clear all buffered filter words on memory.
	 */
	public static void unallocateFilterBuffer()
	{
		_log.info("Cleaning Chat Filter..");
		FILTER_LIST.clear();
	}
}
