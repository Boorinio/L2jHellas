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
package handlers;

import handlers.admincommandhandlers.AdminAdmin;
import handlers.admincommandhandlers.AdminAnnouncements;
import handlers.admincommandhandlers.AdminAutoAnnouncements;
import handlers.admincommandhandlers.AdminBBS;
import handlers.admincommandhandlers.AdminBan;
import handlers.admincommandhandlers.AdminBanChat;
import handlers.admincommandhandlers.AdminCTFEngine;
import handlers.admincommandhandlers.AdminCache;
import handlers.admincommandhandlers.AdminChangeAccessLevel;
import handlers.admincommandhandlers.AdminClanFull;
import handlers.admincommandhandlers.AdminCreateItem;
import handlers.admincommandhandlers.AdminCursedWeapons;
import handlers.admincommandhandlers.AdminDMEngine;
import handlers.admincommandhandlers.AdminDelete;
import handlers.admincommandhandlers.AdminDeport;
import handlers.admincommandhandlers.AdminDisconnect;
import handlers.admincommandhandlers.AdminDonator;
import handlers.admincommandhandlers.AdminDoorControl;
import handlers.admincommandhandlers.AdminEditChar;
import handlers.admincommandhandlers.AdminEditNpc;
import handlers.admincommandhandlers.AdminEffects;
import handlers.admincommandhandlers.AdminEnchant;
import handlers.admincommandhandlers.AdminEventEngine;
import handlers.admincommandhandlers.AdminExpSp;
import handlers.admincommandhandlers.AdminFightCalculator;
import handlers.admincommandhandlers.AdminGeoEditor;
import handlers.admincommandhandlers.AdminGeodata;
import handlers.admincommandhandlers.AdminGm;
import handlers.admincommandhandlers.AdminGmChat;
import handlers.admincommandhandlers.AdminHeal;
import handlers.admincommandhandlers.AdminHelpPage;
import handlers.admincommandhandlers.AdminHero;
import handlers.admincommandhandlers.AdminInvul;
import handlers.admincommandhandlers.AdminKick;
import handlers.admincommandhandlers.AdminKill;
import handlers.admincommandhandlers.AdminLevel;
import handlers.admincommandhandlers.AdminLogin;
import handlers.admincommandhandlers.AdminMammon;
import handlers.admincommandhandlers.AdminManor;
import handlers.admincommandhandlers.AdminMassHero;
import handlers.admincommandhandlers.AdminMenu;
import handlers.admincommandhandlers.AdminMobGroup;
import handlers.admincommandhandlers.AdminMonsterRace;
import handlers.admincommandhandlers.AdminNoble;
import handlers.admincommandhandlers.AdminPForge;
import handlers.admincommandhandlers.AdminPathNode;
import handlers.admincommandhandlers.AdminPetition;
import handlers.admincommandhandlers.AdminPledge;
import handlers.admincommandhandlers.AdminPolymorph;
import handlers.admincommandhandlers.AdminPremium;
import handlers.admincommandhandlers.AdminQuest;
import handlers.admincommandhandlers.AdminReload;
import handlers.admincommandhandlers.AdminRepairChar;
import handlers.admincommandhandlers.AdminRes;
import handlers.admincommandhandlers.AdminRideWyvern;
import handlers.admincommandhandlers.AdminShop;
import handlers.admincommandhandlers.AdminShutdown;
import handlers.admincommandhandlers.AdminSiege;
import handlers.admincommandhandlers.AdminSkill;
import handlers.admincommandhandlers.AdminSpawn;
import handlers.admincommandhandlers.AdminTarget;
import handlers.admincommandhandlers.AdminTeleport;
import handlers.admincommandhandlers.AdminTest;
import handlers.admincommandhandlers.AdminTvTEngine;
import handlers.admincommandhandlers.AdminUnblockIp;
import handlers.admincommandhandlers.AdminWho;
import handlers.admincommandhandlers.AdminZone;
import handlers.chathandlers.ChatAll;
import handlers.chathandlers.ChatAlliance;
import handlers.chathandlers.ChatClan;
import handlers.chathandlers.ChatHeroVoice;
import handlers.chathandlers.ChatParty;
import handlers.chathandlers.ChatPartyRoomAll;
import handlers.chathandlers.ChatPartyRoomCommander;
import handlers.chathandlers.ChatPetition;
import handlers.chathandlers.ChatShout;
import handlers.chathandlers.ChatTell;
import handlers.chathandlers.ChatTrade;
import handlers.skillhandlers.BalanceLife;
import handlers.skillhandlers.BeastFeed;
import handlers.skillhandlers.Blow;
import handlers.skillhandlers.Charge;
import handlers.skillhandlers.CombatPointHeal;
import handlers.skillhandlers.Continuous;
import handlers.skillhandlers.CpDam;
import handlers.skillhandlers.Craft;
import handlers.skillhandlers.DeluxeKey;
import handlers.skillhandlers.Disablers;
import handlers.skillhandlers.DrainSoul;
import handlers.skillhandlers.Fishing;
import handlers.skillhandlers.FishingSkill;
import handlers.skillhandlers.GetPlayer;
import handlers.skillhandlers.GiveSp;
import handlers.skillhandlers.Harvest;
import handlers.skillhandlers.Heal;
import handlers.skillhandlers.ManaHeal;
import handlers.skillhandlers.Manadam;
import handlers.skillhandlers.Mdam;
import handlers.skillhandlers.Pdam;
import handlers.skillhandlers.Recall;
import handlers.skillhandlers.Resurrect;
import handlers.skillhandlers.SiegeFlag;
import handlers.skillhandlers.Sow;
import handlers.skillhandlers.Spoil;
import handlers.skillhandlers.StrSiegeAssault;
import handlers.skillhandlers.SummonFriend;
import handlers.skillhandlers.SummonTreasureKey;
import handlers.skillhandlers.Sweep;
import handlers.skillhandlers.TakeCastle;
import handlers.skillhandlers.Unlock;
import handlers.usercommandhandlers.ChannelDelete;
import handlers.usercommandhandlers.ChannelLeave;
import handlers.usercommandhandlers.ChannelListUpdate;
import handlers.usercommandhandlers.ClanPenalty;
import handlers.usercommandhandlers.ClanWarsList;
import handlers.usercommandhandlers.DisMount;
import handlers.usercommandhandlers.Escape;
import handlers.usercommandhandlers.Loc;
import handlers.usercommandhandlers.Mount;
import handlers.usercommandhandlers.OlympiadStat;
import handlers.usercommandhandlers.PartyInfo;
import handlers.usercommandhandlers.PvpInfo;
import handlers.usercommandhandlers.Time;
import handlers.voicedcommandhandlers.AwayCmd;
import handlers.voicedcommandhandlers.BankingCmd;
import handlers.voicedcommandhandlers.CastleCmd;
import handlers.voicedcommandhandlers.ClCmd;
import handlers.voicedcommandhandlers.MailCmd;
import handlers.voicedcommandhandlers.OnlinePlayersCmd;
import handlers.voicedcommandhandlers.PMonoffCmd;
import handlers.voicedcommandhandlers.PremiumCmd;
import handlers.voicedcommandhandlers.PvpInfoCmd;
import handlers.voicedcommandhandlers.QuizCmd;
import handlers.voicedcommandhandlers.ServerRestartVoteCmd;
import handlers.voicedcommandhandlers.SetCmd;
import handlers.voicedcommandhandlers.StatCmd;
import handlers.voicedcommandhandlers.TradeonoffCmd;
import handlers.voicedcommandhandlers.VersionCmd;
import handlers.voicedcommandhandlers.VipTeleportCmd;
import handlers.voicedcommandhandlers.VoiceInfoCmd;
import handlers.voicedcommandhandlers.WeddingCmd;
import handlers.voicedcommandhandlers.ZodiacRegistrationCmd;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import Extensions.Balancer.Balancer;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.handler.AdminCommandHandler;
import com.l2jhellas.gameserver.handler.ChatHandler;
import com.l2jhellas.gameserver.handler.IHandler;
import com.l2jhellas.gameserver.handler.SkillHandler;
import com.l2jhellas.gameserver.handler.UserCommandHandler;
import com.l2jhellas.gameserver.handler.VoicedCommandHandler;

/**
 * Master handler.
 */
public class MasterHandler
{
	private static final Logger _log = Logger.getLogger(MasterHandler.class.getName());
	
	private static final IHandler<?, ?>[] _loadInstances =
	{
		AdminCommandHandler.getInstance(),
		ChatHandler.getInstance(),
		SkillHandler.getInstance(),
		UserCommandHandler.getInstance(),
		VoicedCommandHandler.getInstance(),
	//	TargetHandler.getInstance(),
	//	TelnetHandler.getInstance(),
	};
	
	private static final Class<?>[][] _handlers =
	{
		{
			// Admin Command Handlers
			AdminAdmin.class,
			AdminAnnouncements.class,
			AdminAutoAnnouncements.class,
			AdminBan.class,
			AdminBanChat.class,
			AdminBBS.class,
			AdminCache.class,
			AdminChangeAccessLevel.class,
			AdminClanFull.class,
			AdminCreateItem.class,
			AdminCursedWeapons.class,
			AdminDelete.class,
			AdminDeport.class,
			AdminDisconnect.class,
			AdminDoorControl.class,
			AdminEditChar.class,
			AdminEditNpc.class,
			AdminEffects.class,
			AdminEnchant.class,
			AdminEventEngine.class,
			AdminFightCalculator.class,
			AdminExpSp.class,
			AdminGeodata.class,
			AdminGeoEditor.class,
			AdminGm.class,
			AdminGmChat.class,
			AdminHeal.class,
			AdminHelpPage.class,
			AdminInvul.class,
			AdminKick.class,
			AdminKill.class,
			AdminLevel.class,
			AdminLogin.class,
			AdminMammon.class,
			AdminManor.class,
			AdminMassHero.class,
			AdminMenu.class,
			AdminMobGroup.class,
			AdminMonsterRace.class,
			AdminPathNode.class,
			AdminPetition.class,
			AdminPForge.class,
			AdminPledge.class,
			AdminPolymorph.class,
			AdminQuest.class,
			AdminRepairChar.class,
			AdminRes.class,
			AdminRideWyvern.class,
			AdminShop.class,
			AdminShutdown.class,
			AdminSiege.class,
			AdminSkill.class,
			AdminSpawn.class,
			AdminTarget.class,
			AdminTeleport.class,
			AdminTest.class,
			AdminUnblockIp.class,
			AdminZone.class,
			AdminDonator.class,
			AdminCTFEngine.class,
			AdminDMEngine.class,
			AdminTvTEngine.class,
			AdminHero.class,
			AdminNoble.class,
			Balancer.class,
			AdminPremium.class,
			AdminReload.class,
			//AdminWalker.class,
			AdminWho.class,
		},
		{
			// Chat Handlers
			ChatAll.class,
			ChatAlliance.class,
			//ChatBattlefield.class,
			ChatClan.class,
			ChatHeroVoice.class,
			ChatParty.class,
			//ChatPartyMatchRoom.class,
			ChatPartyRoomAll.class,
			ChatPartyRoomCommander.class,
			ChatPetition.class,
			ChatShout.class,
			ChatTell.class,
			ChatTrade.class,
		},
		{
			// Skill Handlers
			BalanceLife.class,
			BeastFeed.class,
			Blow.class,
			Charge.class,
			CombatPointHeal.class,
			Continuous.class,
			CpDam.class,
			Craft.class,
			DeluxeKey.class,
			Disablers.class,
			DrainSoul.class,
			Fishing.class,
			FishingSkill.class,
			GetPlayer.class,
			GiveSp.class,
			Harvest.class,
			Heal.class,
			Manadam.class,
			ManaHeal.class,
			Mdam.class,
			Pdam.class,
			Recall.class,
			Resurrect.class,
			SiegeFlag.class,
			Sow.class,
			Spoil.class,
			StrSiegeAssault.class,
			SummonFriend.class,
			SummonTreasureKey.class,
			Sweep.class,
			TakeCastle.class,
			Unlock.class,
		},
		{
			// User Command Handlers
			ClanPenalty.class,
			ClanWarsList.class,
			DisMount.class,
			Escape.class,
			Loc.class,
			Mount.class,
			PartyInfo.class,
			Time.class,
			OlympiadStat.class,
			ChannelLeave.class,
			ChannelDelete.class,
			ChannelListUpdate.class,
			(Config.RANK_PVP_SYSTEM_ENABLED && Config.PVP_INFO_USER_COMMAND_ENABLED && Config.PVP_INFO_COMMAND_ENABLED ? PvpInfo.class : null),
		},
		{
			// Voiced Command Handlers
			(Config.ALLOW_AWAY_STATUS ? AwayCmd.class : null),
			(Config.BANKING_SYSTEM_ENABLED ? BankingCmd.class : null),
			CastleCmd.class,
			(Config.ALLOW_CLAN_LEADER_COMMAND ? ClCmd.class : null),
			(Config.ENABLED_MESSAGE_SYSTEM ? MailCmd.class : null),
			(Config.ONLINE_VOICE_ALLOW ? OnlinePlayersCmd.class : null),
			(Config.ALLOW_PLAYERS_REFUSAL ? PMonoffCmd.class : null),
			PremiumCmd.class,
			(Config.RANK_PVP_SYSTEM_ENABLED && Config.PVP_INFO_COMMAND_ENABLED && Config.RANK_PVP_SYSTEM_ENABLED && !Config.PVP_INFO_USER_COMMAND_ENABLED ? PvpInfoCmd.class : null),
			(Config.ENABLED_QUIZ_EVENT ? QuizCmd.class : null),
			(Config.ALLOW_SERVER_RESTART_COMMAND ? ServerRestartVoteCmd.class : null),
			SetCmd.class,
			(Config.ALLOW_STAT_COMMAND ? StatCmd.class : null),
			(Config.ALLOW_TRADEOFF_COMMAND ? TradeonoffCmd.class : null),
			(Config.ALLOW_VERSION_COMMAND ? VersionCmd.class : null),
			(Config.ALLOW_VIPTELEPORT_COMMAND ? VipTeleportCmd.class : null),
			(Config.ALLOW_INFO_COMMAND ? VoiceInfoCmd.class : null),
			(Config.MOD_ALLOW_WEDDING ? WeddingCmd.class : null),
			(Config.ZODIAC_ENABLE ? ZodiacRegistrationCmd.class : null),
		},
		{
			// Telnet Handlers
		//	ChatsHandler.class,
		//	DebugHandler.class,
		//	HelpHandler.class,
		//	PlayerHandler.class,
		//	ReloadHandler.class,
		//	ServerHandler.class,
		//	StatusHandler.class,
		//	ThreadHandler.class,
		},
	};
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Map<IHandler<?, ?>, Method> registerHandlerMethods = new HashMap<>();
		for (IHandler<?, ?> loadInstance : _loadInstances)
		{
			registerHandlerMethods.put(loadInstance, null);
			for (Method method : loadInstance.getClass().getMethods())
			{
				if (method.getName().equals("registerHandler") && !method.isBridge())
				{
					registerHandlerMethods.put(loadInstance, method);
				}
			}
		}
		
		registerHandlerMethods.entrySet().stream().filter(e -> e.getValue() == null).forEach(e ->
		{
			_log.log(Level.WARNING, "Failed loading handlers of: " + e.getKey().getClass().getSimpleName() + " seems registerHandler function does not exist.");
		});
		
		for (Class<?> classes[] : _handlers)
		{
			for (Class<?> c : classes)
			{
				if (c == null)
				{
					continue; // Disabled handler
				}
				
				try
				{
					Object handler = c.newInstance();
					for (Entry<IHandler<?, ?>, Method> entry : registerHandlerMethods.entrySet())
					{
						if ((entry.getValue() != null) && entry.getValue().getParameterTypes()[0].isInstance(handler))
						{
							entry.getValue().invoke(entry.getKey(), handler);
						}
					}
				}
				catch (Exception e)
				{
					_log.log(Level.WARNING, "Failed loading handler: " + c.getSimpleName(), e);
					continue;
				}
			}
		}
		
		for (IHandler<?, ?> loadInstance : _loadInstances)
		{
			_log.log(Level.INFO, loadInstance.getClass().getSimpleName() + ": Loaded " + loadInstance.size() + " Handlers.");
		}
	}
}