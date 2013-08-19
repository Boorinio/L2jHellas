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
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.text.TextBuilder;
import Extensions.RankSystem.PvpStats;
import Extensions.RankSystem.PvpTable;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.Announcements;
import com.l2jhellas.gameserver.SevenSigns;
import com.l2jhellas.gameserver.TaskPriority;
import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.communitybbs.Manager.RegionBBSManager;
import com.l2jhellas.gameserver.datatables.sql.MapRegionTable;
import com.l2jhellas.gameserver.datatables.xml.AdminTable;
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
import com.l2jhellas.gameserver.model.quest.Quest;
import com.l2jhellas.gameserver.model.quest.QuestState;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.ActionFailed;
import com.l2jhellas.gameserver.network.serverpackets.Die;
import com.l2jhellas.gameserver.network.serverpackets.EtcStatusUpdate;
import com.l2jhellas.gameserver.network.serverpackets.ExStorageMaxCount;
import com.l2jhellas.gameserver.network.serverpackets.FriendList;
import com.l2jhellas.gameserver.network.serverpackets.GameGuardQuery;
import com.l2jhellas.gameserver.network.serverpackets.HennaInfo;
import com.l2jhellas.gameserver.network.serverpackets.InventoryUpdate;
import com.l2jhellas.gameserver.network.serverpackets.ItemList;
import com.l2jhellas.gameserver.network.serverpackets.LeaveWorld;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jhellas.gameserver.network.serverpackets.PledgeShowMemberListAll;
import com.l2jhellas.gameserver.network.serverpackets.PledgeShowMemberListDeleteAll;
import com.l2jhellas.gameserver.network.serverpackets.PledgeShowMemberListUpdate;
import com.l2jhellas.gameserver.network.serverpackets.PledgeSkillList;
import com.l2jhellas.gameserver.network.serverpackets.PledgeStatusChanged;
import com.l2jhellas.gameserver.network.serverpackets.QuestList;
import com.l2jhellas.gameserver.network.serverpackets.ShortCutInit;
import com.l2jhellas.gameserver.network.serverpackets.SignsSky;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;
import com.l2jhellas.gameserver.network.serverpackets.UserInfo;
import com.l2jhellas.gameserver.templates.L2EtcItemType;
import com.l2jhellas.shield.antibot.AntiBot;
import com.l2jhellas.util.Base64;
import com.l2jhellas.util.FloodProtector;
import com.l2jhellas.util.Util;
import com.l2jhellas.util.database.L2DatabaseFactory;

/**
 * Enter World Packet Handler
 * packet format rev656 cbdddd
 */
public class EnterWorld extends L2GameClientPacket
{
	private static Logger _log = Logger.getLogger(EnterWorld.class.getName());
	
	public static Vector<L2PcInstance> _onlineplayers = new Vector<L2PcInstance>();
	private static final String _C__03_ENTERWORLD = "[C] 03 EnterWorld";
	
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
		final L2PcInstance activeChar = getClient().getActiveChar();
		
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
		// Register in flood protector
		FloodProtector.getInstance().registerNewPlayer(activeChar.getObjectId());
		
		Quest.playerEnter(activeChar);
		loadTutorial(activeChar);	
		
        activeChar.checks();
	
		sendPacket(new ShortCutInit(activeChar));
		activeChar.sendSkillList();		
		activeChar.sendPacket(new HennaInfo(activeChar));		
		activeChar.sendPacket(new InventoryUpdate());	
		activeChar.sendPacket(new ItemList(activeChar, false));
		sendPacket(new FriendList(activeChar));		
		activeChar.sendPacket(new QuestList());   
        sendPacket(new UserInfo(activeChar));
		activeChar.broadcastUserInfo();
		activeChar.broadcastTitleInfo();
		activeChar.sendPacket(new EtcStatusUpdate(activeChar));
		
		SystemMessage sm = new SystemMessage(SystemMessageId.WELCOME_TO_LINEAGE);
		sendPacket(sm);	
		
		activeChar.spawnMe(activeChar.getX(), activeChar.getY(), activeChar.getZ());
				
		// engage and notify Partner
		if (Config.MOD_ALLOW_WEDDING)
		{
			engage(activeChar);
			notifyPartner(activeChar, activeChar.getPartnerId());
		}	
		
		// Expand Skill
		ExStorageMaxCount esmc = new ExStorageMaxCount(activeChar);
		activeChar.sendPacket(esmc);
		
		Quest.playerEnter(activeChar);
		// check player skills
		if (Config.CHECK_SKILLS_ON_ENTER && !Config.ALT_GAME_SKILL_LEARN && !Config.ALT_SUBCLASS_SKILLS)
		{
			activeChar.checkAllowedSkills();
		}
		
		// Account Manager
		if (Config.ALLOW_ACCOUNT_MANAGER)
		{
			if (!L2AccountManagerInstance.hasSubEmail(activeChar))
			{
				ThreadPoolManager.getInstance().scheduleGeneral(new entermail(activeChar), 20000);
			}
		}
		
		_onlineplayers.add(activeChar);
				
		setPledgeClass(activeChar);
		
		// add char to online characters
		activeChar.setOnlineStatus(true);
		
		notifyFriends(activeChar);
				
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
			notifyClanMembers(activeChar);
			notifySponsorOrApprentice(activeChar);
			
			for (Siege siege : SiegeManager.getInstance().getSieges())
			{
				if (!siege.getIsInProgress())
				{
					continue;
				}
				if (siege.checkIsAttacker(activeChar.getClan()))
				{
					activeChar.setSiegeState((byte) 1);
				}
				else if (siege.checkIsDefender(activeChar.getClan()))
				{
					activeChar.setSiegeState((byte) 2);
				}
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
			activeChar.sendPacket(new PledgeShowMemberListAll(activeChar.getClan(), activeChar));
			activeChar.sendPacket(new PledgeStatusChanged(activeChar.getClan()));
		}
		
		if (!activeChar.isGM() && activeChar.getSiegeState() < 2 && activeChar.isInsideZone(L2Character.ZONE_SIEGE))
		{
			// Attacker or spectator logging in to a siege zone. Actually should
			// be checked for inside castle only?
			activeChar.teleToLocation(MapRegionTable.TeleportWhereType.Town);
			activeChar.sendMessage("You have been teleported to the nearest town due to you being in siege zone.");
		}
		
		RegionBBSManager.getInstance().changeCommunityBoard();
		
		if (Config.GAMEGUARD_ENFORCE)
		{
			activeChar.sendPacket(new GameGuardQuery());
		}
			
		activeChar.sendPacket(ActionFailed.STATIC_PACKET); //just to avoid target issues
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
				{
					cha.setMarried(true);
				}
				
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
			partner = (L2PcInstance) L2World.findObject(cha.getPartnerId());
			
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
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
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
				
				friend = L2World.getPlayer(friendName);
				
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
			_log.log(Level.WARNING, getClass().getName() + ": Could not restore friend data: ", e);
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
			clan.broadcastClanStatus();
			clan.getClanMember(activeChar.getName()).setPlayerInstance(activeChar);
			SystemMessage msg = new SystemMessage(SystemMessageId.CLAN_MEMBER_S1_LOGGED_IN);
			msg.addString(activeChar.getName());
			clan.broadcastToOtherOnlineMembers(msg, activeChar);
			msg = null;
			clan.broadcastToOtherOnlineMembers(new PledgeShowMemberListUpdate(activeChar), activeChar);
			if (clan.isNoticeEnabled())
			{
				sendPacket(new NpcHtmlMessage(1,
/** @formatter:off */
						"<html><title>Clan Announcements</title><body>" +
						"<br><center>" +
						"<font color=\"CCAA00\">" + activeChar.getClan().getName() +
						"</font> <font color=\"6655FF\">Clan Alert Message</font></center><br>" +
						"<img src=\"L2UI.SquareWhite\" width=270 height=1><br>" +
						activeChar.getClan().getNotice().replaceAll("\r\n", "<br>") +
						"</body></html>"));
						/** @formatter:on */
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
			L2PcInstance sponsor = (L2PcInstance) L2World.findObject(activeChar.getSponsor());
			
			if (sponsor != null)
			{
				SystemMessage msg = new SystemMessage(SystemMessageId.YOUR_APPRENTICE_S1_HAS_LOGGED_IN);
				msg.addString(activeChar.getName());
				sponsor.sendPacket(msg);
			}
		}
		else if (activeChar.getApprentice() != 0)
		{
			L2PcInstance apprentice = (L2PcInstance) L2World.findObject(activeChar.getApprentice());
			
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
		{
			pledgeClass = activeChar.getClan().getClanMember(activeChar.getObjectId()).calculatePledgeClass(activeChar);
		}
		
		if (activeChar.isNoble() && pledgeClass < 5)
		{
			pledgeClass = 5;
		}
		
		if (activeChar.isHero())
		{
			pledgeClass = 8;
		}
		
		activeChar.setPledgeClass(pledgeClass);
	}
	
	/**
	 * TODO nightwolf remove from here
	 * 
	 * @param player
	 */
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
	private void loadTutorial(L2PcInstance player)
	{
		QuestState qs = player.getQuestState("255_Tutorial");
		if (qs != null)
			qs.getQuest().notifyEvent("UC", null, player);
	}
}