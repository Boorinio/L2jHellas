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
package com.l2jhellas.gameserver.network.clientpackets;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;
import java.util.logging.Logger;

import javolution.text.TextBuilder;

import com.l2jhellas.Base64;
import com.l2jhellas.Config;
import com.l2jhellas.ExternalConfig;
import com.l2jhellas.L2DatabaseFactory;
import com.l2jhellas.gameserver.Announcements;
import com.l2jhellas.gameserver.GmListTable;
import com.l2jhellas.gameserver.SevenSigns;
import com.l2jhellas.gameserver.TaskPriority;
import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.cache.HtmCache;
import com.l2jhellas.gameserver.communitybbs.Manager.RegionBBSManager;
import com.l2jhellas.gameserver.datatables.sql.AdminCommandAccessRights;
import com.l2jhellas.gameserver.datatables.sql.MapRegionTable;
import com.l2jhellas.gameserver.instancemanager.CastleManager;
import com.l2jhellas.gameserver.instancemanager.ClanHallManager;
import com.l2jhellas.gameserver.instancemanager.CoupleManager;
import com.l2jhellas.gameserver.instancemanager.CrownManager;
import com.l2jhellas.gameserver.instancemanager.DimensionalRiftManager;
import com.l2jhellas.gameserver.instancemanager.PetitionManager;
import com.l2jhellas.gameserver.instancemanager.SiegeManager;
import com.l2jhellas.gameserver.instancemanager.SiegeReward;
import com.l2jhellas.gameserver.model.L2Character;
import com.l2jhellas.gameserver.model.L2Clan;
import com.l2jhellas.gameserver.model.L2Effect;
import com.l2jhellas.gameserver.model.L2ItemInstance;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2AccountManagerInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2ClassMasterInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.base.ClassLevel;
import com.l2jhellas.gameserver.model.base.PlayerClass;
import com.l2jhellas.gameserver.model.entity.Castle;
import com.l2jhellas.gameserver.model.entity.ClanHall;
import com.l2jhellas.gameserver.model.entity.Couple;
import com.l2jhellas.gameserver.model.entity.Hero;
import com.l2jhellas.gameserver.model.entity.L2Event;
import com.l2jhellas.gameserver.model.entity.Olympiad;
import com.l2jhellas.gameserver.model.entity.Siege;
import com.l2jhellas.gameserver.model.entity.engines.CTF;
import com.l2jhellas.gameserver.model.entity.engines.DM;
import com.l2jhellas.gameserver.model.entity.engines.Hitman;
import com.l2jhellas.gameserver.model.entity.engines.TvT;
import com.l2jhellas.gameserver.model.entity.engines.VIP;
import com.l2jhellas.gameserver.model.quest.Quest;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.Die;
import com.l2jhellas.gameserver.network.serverpackets.EtcStatusUpdate;
import com.l2jhellas.gameserver.network.serverpackets.ExStorageMaxCount;
import com.l2jhellas.gameserver.network.serverpackets.FriendList;
import com.l2jhellas.gameserver.network.serverpackets.GameGuardQuery;
import com.l2jhellas.gameserver.network.serverpackets.HennaInfo;
import com.l2jhellas.gameserver.network.serverpackets.ItemList;
import com.l2jhellas.gameserver.network.serverpackets.LeaveWorld;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jhellas.gameserver.network.serverpackets.PledgeShowMemberListAll;
import com.l2jhellas.gameserver.network.serverpackets.PledgeShowMemberListUpdate;
import com.l2jhellas.gameserver.network.serverpackets.PledgeSkillList;
import com.l2jhellas.gameserver.network.serverpackets.PledgeStatusChanged;
import com.l2jhellas.gameserver.network.serverpackets.QuestList;
import com.l2jhellas.gameserver.network.serverpackets.ShortCutInit;
import com.l2jhellas.gameserver.network.serverpackets.SignsSky;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;
import com.l2jhellas.gameserver.network.serverpackets.UserInfo;
import com.l2jhellas.gameserver.templates.L2EtcItemType;
import com.l2jhellas.util.FloodProtector;
import com.l2jhellas.util.Util;

/**
 * Enter World Packet Handler
 * packet format rev656 cbdddd
 */
public class EnterWorld extends L2GameClientPacket
{

	public static Vector<L2PcInstance> _onlineplayers = new Vector<L2PcInstance>();

	private static final String _C__03_ENTERWORLD = "[C] 03 EnterWorld";
	private static Logger _log = Logger.getLogger(EnterWorld.class.getName());

	public TaskPriority getPriority()
	{
		return TaskPriority.PR_URGENT;
	}

	@Override
	protected void readImpl()
	{
		// this is just a trigger packet. it has no content
	}

	class entermail implements Runnable
	{
		private final L2PcInstance p;

		public entermail(L2PcInstance player)
		{
			p = player;
		}

		@Override
		public void run()
		{
			subhtml(p);
		}
	}

	@Override
	protected void runImpl()
	{
		L2PcInstance activeChar = getClient().getActiveChar();

		if (activeChar == null)
		{
			_log.warning("EnterWorld failed! activeChar is null...");
			getClient().closeNow();
			return;
		}

		// Send Macro List
		activeChar.getMacroses().sendUpdate();

		// Send Item List
		sendPacket(new ItemList(activeChar, false));

		// Send gg check (even if we are not going to check for reply)
		activeChar.queryGameGuard();

		// Send Shortcuts
		sendPacket(new ShortCutInit(activeChar));

		activeChar.sendSkillList();

		activeChar.sendPacket(new HennaInfo(activeChar));

		sendPacket(new UserInfo(activeChar));

		Quest.playerEnter(activeChar);
		activeChar.sendPacket(new QuestList());

		// Register in flood protector
		FloodProtector.getInstance().registerNewPlayer(activeChar.getObjectId());

		if (L2World.getInstance().findObject(activeChar.getObjectId()) != null)
		{
			if (Config.DEBUG)
				_log.warning("User already exist in OID map! User " + activeChar.getName() + " is character clone");
			// activeChar.closeNetConnection();
		}

		for (L2ItemInstance i : activeChar.getInventory().getItems())
		{
			if (i.getItemType() != L2EtcItemType.PET_COLLAR)
			{
				if (!activeChar.isGM())
				{
					if (i.getEnchantLevel() > Config.ENCHANT_MAX_ALLOWED_WEAPON || i.getEnchantLevel() > Config.ENCHANT_MAX_ALLOWED_ARMOR || i.getEnchantLevel() > Config.ENCHANT_MAX_ALLOWED_JEWELRY)
					{
						// Delete Item Over enchanted
						activeChar.getInventory().destroyItem(null, i, activeChar, null);
						// Message to Player
						activeChar.sendMessage("[Server]:You have Items over enchanted you will be kikked!");
						// If Audit is only a Kick, with this the player goes in
						// Jail for 1.200 minutes
						activeChar.setPunishLevel(L2PcInstance.PunishLevel.JAIL, 1200);
						// Punishment e log in audit
						Util.handleIllegalPlayerAction(activeChar, "Player " + activeChar.getName() + " have item Overenchanted ", Config.DEFAULT_PUNISH);
						// Log in console
						_log.info("Overenchanted item {" + i + "} has been removed from " + activeChar.getName() + ".");
					}
				}
			}
		}

		if (activeChar.isGM())
		{
			if (Config.GM_STARTUP_INVULNERABLE && AdminCommandAccessRights.getInstance().hasAccess("admin_invul", activeChar.getAccessLevel()))
			 	activeChar.setIsInvul(true);

			if (Config.GM_STARTUP_INVISIBLE && AdminCommandAccessRights.getInstance().hasAccess("admin_invisible", activeChar.getAccessLevel()))
			 	activeChar.getAppearance().setInvisible();

			if (Config.GM_STARTUP_SILENCE && AdminCommandAccessRights.getInstance().hasAccess("admin_silence", activeChar.getAccessLevel()))
			 	activeChar.setMessageRefusal(true);

			if (Config.GM_STARTUP_AUTO_LIST && AdminCommandAccessRights.getInstance().hasAccess("admin_gmliston", activeChar.getAccessLevel()))
			 	GmListTable.getInstance().addGm(activeChar, false);
			else
			 	GmListTable.getInstance().addGm(activeChar, true);

			if (Config.GM_TITLE_COLOR_ENABLED)
			{
				if (activeChar.getAccessLevel().getLevel() >= 100)
					activeChar.getAppearance().setTitleColor(Config.ADMIN_TITLE_COLOR);
				else if (activeChar.getAccessLevel().getLevel() >= 75)
					activeChar.getAppearance().setTitleColor(Config.GM_TITLE_COLOR);
			}
		}
		if(Config.RAID_SYSTEM_ENABLED)
		{
			activeChar.inClanEvent = false;
			activeChar.inPartyEvent = false;
			activeChar.inSoloEvent = false;
		}

		if (activeChar.isDonator() && Config.DONATOR_NAME_COLOR_ENABLED)
		{
			activeChar.getAppearance().setNameColor(Config.DONATOR_NAME_COLOR);
		}

		if (activeChar.isDonator() && Config.DONATOR_TITLE_COLOR_ENABLED)
		{
			activeChar.getAppearance().setTitleColor(Config.DONATOR_TITLE_COLOR);
		}

		if (activeChar.isDonator() && Config.WELCOME_TEXT_FOR_DONATOR_ENABLED)
		{
			activeChar.sendMessage(Config.WELCOME_TEXT_FOR_DONATOR_1 + " " + activeChar.getName() + " " + Config.WELCOME_TEXT_FOR_DONATOR_2);
		}

		// Apply color settings to clan leader when entering
		if (activeChar.getClan() != null && activeChar.isClanLeader() && Config.CLAN_LEADER_COLOR_ENABLED && activeChar.getClan().getLevel() >= Config.CLAN_LEADER_COLOR_CLAN_LEVEL)
		{
			activeChar.getAppearance().setTitleColor(Config.CLAN_LEADER_COLOR);
		}

		// restore info about chat ban
		activeChar.checkBanChat(false);

		// check for crowns
		CrownManager.getInstance().checkCrowns(activeChar);

		if ((Config.PVP_COLOR_SYSTEM_ENABLED))
			activeChar.updatePvPColor(activeChar.getPvpKills());

		if ((Config.PK_COLOR_SYSTEM_ENABLED))
			activeChar.updatePkColor(activeChar.getPkKills());

		if (Config.ANNOUNCE_HERO_LOGIN && activeChar.isHero())
			{
			       Announcements.getInstance().announceToAll("Hero: "+activeChar.getName()+" has been logged in.");
			}

		if (Config.ANNOUNCE_CASTLE_LORDS)
			{
				L2Clan clan = activeChar.getClan();

			    if (clan != null)
				 {
				  if (clan.getHasCastle() > 0)
					{
					 Castle castle = CastleManager.getInstance().getCastleById(clan.getHasCastle());
					 if ((castle != null) && (activeChar.getObjectId() == clan.getLeaderId()))
					  Announcements.getInstance().announceToAll("Lord " + activeChar.getName() + " Ruler Of " + castle.getName() + " Castle is Now Online!");
				    }
				 }
			}


		if (Config.PLAYER_SPAWN_PROTECTION > 0)
			activeChar.setProtection(true);
		activeChar.spawnMe(activeChar.getX(), activeChar.getY(), activeChar.getZ());

		if (L2Event.active && L2Event.connectionLossData.containsKey(activeChar.getName()) && L2Event.isOnEvent(activeChar))
			L2Event.restoreChar(activeChar);
		else if (L2Event.connectionLossData.containsKey(activeChar.getName()))
			L2Event.restoreAndTeleChar(activeChar);

		if (SevenSigns.getInstance().isSealValidationPeriod())
			sendPacket(new SignsSky());

		// buff and status icons
		if (Config.STORE_SKILL_COOLTIME)
			activeChar.restoreEffects();

		activeChar.sendPacket(new EtcStatusUpdate(activeChar));

		// engage and notify Partner
		if (Config.MOD_ALLOW_WEDDING)
		{
			engage(activeChar);
			notifyPartner(activeChar, activeChar.getPartnerId());
		}

		if (activeChar.getAllEffects() != null)
		{
			for (L2Effect e : activeChar.getAllEffects())
			{
				if (e.getEffectType() == L2Effect.EffectType.HEAL_OVER_TIME)
				{
					activeChar.stopEffects(L2Effect.EffectType.HEAL_OVER_TIME);
					activeChar.removeEffect(e);
				}

				if (e.getEffectType() == L2Effect.EffectType.COMBAT_POINT_HEAL_OVER_TIME)
				{
					activeChar.stopEffects(L2Effect.EffectType.COMBAT_POINT_HEAL_OVER_TIME);
					activeChar.removeEffect(e);
				}
			}
		}

		// apply augmentation boni for equipped items
		for (L2ItemInstance temp : activeChar.getInventory().getAugmentedItems())
			if (temp != null && temp.isEquipped())
				temp.getAugmentation().applyBoni(activeChar);

		// Expand Skill
		ExStorageMaxCount esmc = new ExStorageMaxCount(activeChar);
		activeChar.sendPacket(esmc);

		sendPacket(new FriendList(activeChar));

		SevenSigns.getInstance().sendCurrentPeriodMsg(activeChar);
		Announcements.getInstance().showAnnouncements(activeChar);

		// l2jhellas Faction Good vs Evil
		// Welcome for evil
		if (activeChar.isevil() && Config.MOD_GVE_ENABLE_FACTION)
		{
			activeChar.getAppearance().setNameColor(Config.MOD_GVE_COLOR_NAME_EVIL);
			activeChar.sendMessage("Welcome " + activeChar.getName() + " u are fighting for " + Config.MOD_GVE_NAME_TEAM_EVIL + "  Faction");
		}
		// If Enable Faction Base = true teleport evil to his village principal
		if (activeChar.isevil() && Config.MOD_GVE_ENABLE_FACTION)
		{
			activeChar.teleToLocation(Config.EVILX, Config.EVILY, Config.EVILZ, true);
			activeChar.sendMessage("You have been teleported Back to your Faction Base");
		}
		// Welcome for good
		if (activeChar.isgood() && Config.MOD_GVE_ENABLE_FACTION)
		{
			activeChar.getAppearance().setNameColor(Config.MOD_GVE_COLOR_NAME_GOOD);
			activeChar.sendMessage("Welcome " + activeChar.getName() + " u are fighting for " + Config.MOD_GVE_NAME_TEAM_GOOD + " Faction");
		}
		// If Enable Faction Base = true teleport good to his village principal
		if (activeChar.isgood() && Config.MOD_GVE_ENABLE_FACTION)
		{
			activeChar.teleToLocation(Config.GOODX, Config.GOODY, Config.GOODZ, true);
			activeChar.sendMessage("You have been teleported Back to your Faction Base");
		}

		Quest.playerEnter(activeChar);
		// check player skills
		if (Config.CHECK_SKILLS_ON_ENTER && !Config.ALT_GAME_SKILL_LEARN)
			activeChar.checkAllowedSkills();
		PetitionManager.getInstance().checkPetitionMessages(activeChar);

		// Account Manager
		if(ExternalConfig.ALLOW_ACCOUNT_MANAGER)
		{
		if (!L2AccountManagerInstance.hasSubEmail(activeChar))
			ThreadPoolManager.getInstance().scheduleGeneral(new entermail(activeChar), 20000);
		}

		if (activeChar.getFirstEffect(426) != null || activeChar.getFirstEffect(427) != null)
		{
			activeChar.stopSkillEffects(426);
			activeChar.stopSkillEffects(427);
			activeChar.updateEffectIcons();
			activeChar.broadcastUserInfo();
		}

		if (activeChar.getPremiumService() == 1)
		{
			// activeChar.sendPacket(new
			// ExBrPremiumState(activeChar.getObjectId(), 1));
			activeChar.setDonator(true);
		}
		else
		{
			// activeChar.sendPacket(new
			// ExBrPremiumState(activeChar.getObjectId(), 0));
			activeChar.setDonator(false);
		}

		// send user info again .. just like the real client
		// sendPacket(ui);

		if (activeChar.getClanId() != 0 && activeChar.getClan() != null)
		{
			sendPacket(new PledgeShowMemberListAll(activeChar.getClan(), activeChar));
			sendPacket(new PledgeStatusChanged(activeChar.getClan()));
		}

		if (SiegeReward.ACTIVATED_SYSTEM && !SiegeReward.REWARD_ACTIVE_MEMBERS_ONLY)
		{
			SiegeReward.getInstance().processWorldEnter(activeChar);
		}

		if (activeChar.isAlikeDead())
		{
			// no broadcast needed since the player will already spawn dead to
			// others
			sendPacket(new Die(activeChar));
		}

		if (Config.ALLOW_WATER)
			activeChar.checkWaterState();

		if (Config.SHOW_HTML_WELCOME)
		{
			String Welcome_Path = "data/html/welcomeP.htm";
			File mainText = new File(Config.DATAPACK_ROOT, Welcome_Path);
			if (mainText.exists())
			{
				NpcHtmlMessage html = new NpcHtmlMessage(1);
				html.setFile(Welcome_Path);
				html.replace("%name%", activeChar.getName());
				sendPacket(html);
			}
		}

		if (Config.SHOW_HTML_GM_WELCOME && (activeChar.getAccessLevel().getLevel() > 0 || activeChar.isGM()))
		{
			String Welcome_Path = "data/html/welcomeGM.htm";
			File mainText = new File(Config.DATAPACK_ROOT, Welcome_Path);
			if (mainText.exists())
			{
				NpcHtmlMessage html = new NpcHtmlMessage(1);
				html.setFile(Welcome_Path);
				html.replace("%name%", activeChar.getName());
				sendPacket(html);
			}
		}

		_onlineplayers.add(activeChar);

		if (Config.ENABLED_MESSAGE_SYSTEM) {

			Connection con = null;

			int results = 0;

			try {

				con = L2DatabaseFactory.getInstance().getConnection();

				PreparedStatement statement = con.prepareStatement("SELECT * FROM mails WHERE `to`=?");

				statement.setString(1, activeChar.getName());

				ResultSet result = statement.executeQuery();

				while (result.next()) {

					results++;

				}

			}catch(Exception e) {

				e.printStackTrace();

			}

			activeChar.sendMessage("You have " + results + " messages.");

		}

		if (Config.ENABLE_HITMAN_EVENT)
			Hitman.getInstance().onEnterWorld(activeChar);
		if (Hero.getInstance().getHeroes() != null && Hero.getInstance().getHeroes().containsKey(activeChar.getObjectId()))
			activeChar.setHero(true);

		setPledgeClass(activeChar);

		// add char to online characters
		activeChar.setOnlineStatus(true);

		notifyFriends(activeChar);
		notifyClanMembers(activeChar);
		notifySponsorOrApprentice(activeChar);

		activeChar.onPlayerEnter();

		if (Olympiad.getInstance().playerInStadia(activeChar))
		{
			activeChar.teleToLocation(MapRegionTable.TeleportWhereType.Town);
			activeChar.sendMessage("You have been teleported to the nearest town due to you being in an Olympiad Stadium");
		}

		if (DimensionalRiftManager.getInstance().checkIfInRiftZone(activeChar.getX(), activeChar.getY(), activeChar.getZ(), false))
		{
			DimensionalRiftManager.getInstance().teleportToWaitingRoom(activeChar);
		}
		if (activeChar.getClanJoinExpiryTime() > System.currentTimeMillis())
		{
			activeChar.sendPacket(new SystemMessage(SystemMessageId.CLAN_MEMBERSHIP_TERMINATED));
		}

		if (activeChar.getClan() != null)
		{
			activeChar.sendPacket(new PledgeSkillList(activeChar.getClan()));

			for (Siege siege : SiegeManager.getInstance().getSieges())
			{
				if (!siege.getIsInProgress())
					continue;
				if (siege.checkIsAttacker(activeChar.getClan()))
					activeChar.setSiegeState((byte) 1);
				else if (siege.checkIsDefender(activeChar.getClan()))
					activeChar.setSiegeState((byte) 2);
			}
			// Add message at connection if clanHall not paid.
			// Possibly this is custom...
			ClanHall clanHall = ClanHallManager.getInstance().getClanHallByOwner(activeChar.getClan());
			if (clanHall != null)
			{
				if (!clanHall.getPaid())
				{
					activeChar.sendPacket(new SystemMessage(SystemMessageId.PAYMENT_FOR_YOUR_CLAN_HALL_HAS_NOT_BEEN_MADE_PLEASE_MAKE_PAYMENT_TO_YOUR_CLAN_WAREHOUSE_BY_S1_TOMORROW));
				}
			}
		}

		if (!activeChar.isGM() && activeChar.getSiegeState() < 2 && activeChar.isInsideZone(L2Character.ZONE_SIEGE))
		{
			// Attacker or spectator logging in to a siege zone. Actually should
			// be checked for inside castle only?
			activeChar.teleToLocation(MapRegionTable.TeleportWhereType.Town);
			activeChar.sendMessage("You have been teleported to the nearest town due to you being in siege zone");
		}

		RegionBBSManager.getInstance().changeCommunityBoard();

		if (Config.GAMEGUARD_ENFORCE)
			activeChar.sendPacket(new GameGuardQuery());

		if (TvT._savePlayers.contains(activeChar.getName()))
			TvT.addDisconnectedPlayer(activeChar);

		if (CTF._savePlayers.contains(activeChar.getName()))
			CTF.addDisconnectedPlayer(activeChar);

		if (DM._savePlayers.contains(activeChar.getName()))
			DM.addDisconnectedPlayer(activeChar);

		if (VIP._savePlayers.contains(activeChar.getName()))
			VIP.addDisconnectedPlayer(activeChar);

		if (ExternalConfig.ALLOW_REMOTE_CLASS_MASTER)
		{
			ClassLevel lvlnow = PlayerClass.values()[activeChar.getClassId().getId()].getLevel();
			if (activeChar.getLevel() >= 20 && lvlnow == ClassLevel.First)
				L2ClassMasterInstance.ClassMaster.onAction(activeChar);
			else if (activeChar.getLevel() >= 40 && lvlnow == ClassLevel.Second)
				L2ClassMasterInstance.ClassMaster.onAction(activeChar);
			else if (activeChar.getLevel() >= 76 && lvlnow == ClassLevel.Third)
				L2ClassMasterInstance.ClassMaster.onAction(activeChar);
		}

		if (!Config.ALLOW_DUALBOX && activeChar != null)
		{
			String thisip = activeChar.getClient().getConnection().getInetAddress().getHostAddress();
			Collection<L2PcInstance> allPlayers = L2World.getInstance().getAllPlayers();
			L2PcInstance[] players = allPlayers.toArray(new L2PcInstance[allPlayers.size()]);
			for (L2PcInstance player : players)
			{
				if (player.getClient().getConnection().getInetAddress().getHostAddress() == null)
					return;
				String ip = player.getClient().getConnection().getInetAddress().getHostAddress();
				if (thisip.equals(ip) && activeChar != player && player != null)
				{
					player.sendMessage("I'm sorry, but multibox is not allowed here.");
					player.sendPacket(new LeaveWorld());
					player.logout();
				}
			}
		}
	}

	/**
	 * @param activeChar
	 */
	private void engage(L2PcInstance cha)
	{
		int _chaid = cha.getObjectId();

		for (Couple cl : CoupleManager.getInstance().getCouples())
		{
			if (cl.getPlayer1Id() == _chaid || cl.getPlayer2Id() == _chaid)
			{
				if (cl.getMaried())
					cha.setMarried(true);

				cha.setCoupleId(cl.getId());

				if (cl.getPlayer1Id() == _chaid)
				{
					cha.setPartnerId(cl.getPlayer2Id());
				}
				else
				{
					cha.setPartnerId(cl.getPlayer1Id());
				}
			}
		}
	}

	/**
	 * @param activeChar
	 *        partnerid
	 */
	private void notifyPartner(L2PcInstance cha, int partnerId)
	{
		if (cha.getPartnerId() != 0)
		{
			L2PcInstance partner;
			partner = (L2PcInstance) L2World.getInstance().findObject(cha.getPartnerId());

			if (partner != null)
			{
				partner.sendMessage("Your Partner has logged in.");
			}

			partner = null;
		}
	}

	/**
	 * @param activeChar
	 */
	private void notifyFriends(L2PcInstance cha)
	{
		Connection con = null;

		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement;
			statement = con.prepareStatement("SELECT friend_name FROM character_friends WHERE char_id=?");
			statement.setInt(1, cha.getObjectId());
			ResultSet rset = statement.executeQuery();

			L2PcInstance friend;
			String friendName;

			SystemMessage sm = new SystemMessage(SystemMessageId.FRIEND_S1_HAS_LOGGED_IN);
			sm.addString(cha.getName());

			while (rset.next())
			{
				friendName = rset.getString("friend_name");

				friend = L2World.getInstance().getPlayer(friendName);

				if (friend != null) // friend logged in.
				{
					friend.sendPacket(new FriendList(friend));
					friend.sendPacket(sm);
				}
			}
			sm = null;

			rset.close();
			statement.close();
		}
		catch (Exception e)
		{
			_log.warning("could not restore friend data:" + e);
		}
		finally
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{
			}
		}
	}

	/**
	 * @param activeChar
	 */
	private void notifyClanMembers(L2PcInstance activeChar)
	{
		L2Clan clan = activeChar.getClan();
		if (clan != null)
		{
			clan.getClanMember(activeChar.getName()).setPlayerInstance(activeChar);
			SystemMessage msg = new SystemMessage(SystemMessageId.CLAN_MEMBER_S1_LOGGED_IN);
			msg.addString(activeChar.getName());
			clan.broadcastToOtherOnlineMembers(msg, activeChar);
			msg = null;
			clan.broadcastToOtherOnlineMembers(new PledgeShowMemberListUpdate(activeChar), activeChar);
			if (clan.isNoticeEnabled())
			{
				sendPacket(new NpcHtmlMessage(1, "<html><title>Clan Announcements</title><body><br><center><font color=\"CCAA00\">" + activeChar.getClan().getName() + "</font> <font color=\"6655FF\">Clan Alert Message</font></center><br>" + "<img src=\"L2UI.SquareWhite\" width=270 height=1><br>" + activeChar.getClan().getNotice().replaceAll("\r\n", "<br>") + "</body></html>"));
			}
		}
	}

	/**
	 * @param activeChar
	 */
	private void notifySponsorOrApprentice(L2PcInstance activeChar)
	{
		if (activeChar.getSponsor() != 0)
		{
			L2PcInstance sponsor = (L2PcInstance) L2World.getInstance().findObject(activeChar.getSponsor());

			if (sponsor != null)
			{
				SystemMessage msg = new SystemMessage(SystemMessageId.YOUR_APPRENTICE_S1_HAS_LOGGED_IN);
				msg.addString(activeChar.getName());
				sponsor.sendPacket(msg);
			}
		}
		else if (activeChar.getApprentice() != 0)
		{
			L2PcInstance apprentice = (L2PcInstance) L2World.getInstance().findObject(activeChar.getApprentice());

			if (apprentice != null)
			{
				SystemMessage msg = new SystemMessage(SystemMessageId.YOUR_SPONSOR_S1_HAS_LOGGED_IN);
				msg.addString(activeChar.getName());
				apprentice.sendPacket(msg);
			}
		}
	}

	/**
	 * @param string
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unused")
	private String getText(String string)
	{
		try
		{
			String result = new String(Base64.decode(string), "UTF-8");
			return result;
		}
		catch (UnsupportedEncodingException e)
		{
			// huh, UTF-8 is not supported? :)
			return null;
		}
	}

	@Override
	public String getType()
	{
		return _C__03_ENTERWORLD;
	}

	private void setPledgeClass(L2PcInstance activeChar)
	{
		int pledgeClass = 0;
		if (activeChar.getClan() != null)
			pledgeClass = activeChar.getClan().getClanMember(activeChar.getObjectId()).calculatePledgeClass(activeChar);

		if (activeChar.isNoble() && pledgeClass < 5)
			pledgeClass = 5;

		if (activeChar.isHero())
			pledgeClass = 8;

		activeChar.setPledgeClass(pledgeClass);
	}

	public void subhtml(L2PcInstance player)
	{
		TextBuilder tb = new TextBuilder();
		NpcHtmlMessage html = new NpcHtmlMessage(1);

		tb.append("<html><head><title>Submit your Email</title></head>");
		tb.append("<body>");
		tb.append("<center>");
		tb.append("<table width=\"250\" cellpadding=\"5\" bgcolor=\"000000\">");
		tb.append("<tr>");
		tb.append("<td width=\"45\" valign=\"top\" align=\"center\"><img src=\"L2ui_ch3.menubutton4\" width=\"38\" height=\"38\"></td>");
		tb.append("<td valign=\"top\"><font color=\"FF6600\">Email submitance</font>");
		tb.append("<br1><font color=\"00FF00\">" + player.getName() + "</font>, please submit here your REAL email address.</td></tr></table></center>");
		tb.append("<center>");
		tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center><br>");
		tb.append("</center>");
		tb.append("<table width=\"350\" cellpadding=\"5\" bgcolor=\"000000\">");
		tb.append("<tr>");
		tb.append("<td width=\"45\" valign=\"top\" align=\"center\"><img src=\"Icon.etc_old_key_i02\" width=\"32\" height=\"32\"></td>");
		tb.append("<td valign=\"top\">Please enter your Email:<multiedit var=\"email1\" width=180 height=15>");
		tb.append("<br1>We need this to be your real one.</td>");
		tb.append("</tr>");
		tb.append("</table>");
		tb.append("<br>");
		tb.append("<center>");
		tb.append("<button value=\"Submit\" action=\"bypass -h submitemail $email1\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\">");
		tb.append("</center>");
		tb.append("<center>");
		tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
		tb.append("</center>");
		tb.append("</body></html>");

		html.setHtml(tb.toString());
		player.sendPacket(html);
	}

	public static void warnAllPlayers()
	{
		for (L2PcInstance player : _onlineplayers)
		{
			String file = "data/html/chaos/warning.htm";
			String html = HtmCache.getInstance().getHtm(file);
			NpcHtmlMessage warning = new NpcHtmlMessage(1);
			warning.setHtml(html);

			player.sendPacket(warning);
		}
	}

}
