package Extensions.RaidEvent;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.datatables.sql.ItemTable;
import com.l2jhellas.gameserver.datatables.sql.NpcData;
import com.l2jhellas.gameserver.datatables.sql.SpawnTable;
import com.l2jhellas.gameserver.instancemanager.RaidBossSpawnManager;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.L2Spawn;
import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2EventBufferInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2EventManagerInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.ItemList;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jhellas.gameserver.network.serverpackets.StatusUpdate;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;
import com.l2jhellas.util.database.L2DatabaseFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class L2RaidEvent
{
	// Local Variables Definition
	// --------------------------
	protected static final Logger _log = Logger.getLogger(L2RaidEvent.class.getName());
	
	private static L2PcInstance _player;
	private static L2Spawn _npcSpawn = null;
	
	private static L2Npc _lastNpcSpawn = null;
	
	public static int exp = 0;
	
	public static int sp = 0;
	
	public static int _eventType;
	
	private static int _eventMobs = 0;
	
	private static int _rewardLevel;
	
	private static int _locX, _locY, _locZ, _pX, _pY, _pZ;
	
	private static int _npcX, _npcY, _npcZ;
	
	private static int _first_id, _first_ammount, _second_id, _second_ammount, _event_ammount;
	
	private static int _points;
	
	private static int _npcId;
	
	private static int _npcAm;
	
	private static int _bufflist;
	
	private static L2Object _effector;
	
	public static List<L2PcInstance> _participatingPlayers = new ArrayList<>();
	
	public static List<L2PcInstance> _awaitingplayers = new ArrayList<>();
	
	public static List<L2Npc> _eventMobList = new ArrayList<>();
	
	private static EventState _state = EventState.INACTIVE;
	
	enum EventState
	{
		INACTIVE,
		STARTING,
		STARTED,
		PARTICIPATING,
		REWARDING,
		INACTIVATING
	}
	
	public L2RaidEvent(L2PcInstance player, int type, int points, int npcId, int npcAm, int bufflist, int rewardLevel, L2Object effector, List<L2PcInstance> participatingPlayers)
	{
		// Define the actual coordinates of the Player.
		_player = player;
		_pX = player.getClientX();
		_pY = player.getClientY();
		_pZ = player.getClientZ();
		_eventType = type;
		_points = points;
		_npcId = npcId;
		_npcAm = npcAm;
		_bufflist = bufflist;
		_rewardLevel = rewardLevel;
		_effector = effector;
		_participatingPlayers = participatingPlayers;
	}
	
	public void init()
	{
		setState(EventState.STARTING);
		
		if (!L2EventManagerInstance.addEvent())
			return;
		
		if (!setCoords(_player))
		{
			L2EventManagerInstance.removeEvent();
			return;
		}
		
		if (Config.DEBUG)
			_log.warning(L2RaidEvent.class.getName() + ": RaidEngine [setCoords]: Players: " + _locX + ", " + _locY + ", " + _locZ);
		
		setInEvent(_player);
		
		startEvent(_player, _npcId, _npcAm);
		
		buffEventMembers(_player, _points, _bufflist, _effector);
		return;
	}
	
	private static boolean setCoords(L2PcInstance player)
	{
		int _ce = L2EventManagerInstance._currentEvents;
		if (_ce == 0 || (_ce > Config.RAID_SYSTEM_MAX_EVENTS))
		{
			String reason = null;
			if (_ce == 0)
				reason = "Current Events = 0.";
			else if (_ce > Config.RAID_SYSTEM_MAX_EVENTS)
				reason = "Too many Events going on";
			player.sendMessage("Raid Engines [setCoords()]: Error while setting spawn positions for players and Monsters. Reason: " + reason);
			return false;
		}
		
		loadSpawns(_ce);
		return true;
	}
	
	private synchronized static void setInEvent(L2PcInstance player)
	{
		
		if (_eventType != 1 && _eventType != 2 && _eventType != 3)
		{
			player.sendMessage("Debug: Error in The event type [Function: setInEvent]");
			_log.warning(L2RaidEvent.class.getName() + ": Event Manager: Error! Event not defined! [Function setInEvent]");
			return;
		}
		for (L2PcInstance member : _participatingPlayers)
		{
			if (member == null)
				continue;
			switch (_eventType)
			{
				case 1:
				{
					member.inSoloEvent = true;
					break;
				}
				case 2:
				{
					member.inClanEvent = true;
					break;
				}
				case 3:
				{
					member.inPartyEvent = true;
					break;
				}
				default:
					return;
			}
			member.sendMessage("Event Manager: You are now enroled in a " + L2EventChecks.eType(_eventType) + " Type of Event.");
		}
	}
	
	private synchronized static void buffEventMembers(L2PcInstance player, int eventPoints, int buffList, L2Object efector)
	{
		if (!player.inPartyEvent && !player.inSoloEvent && !player.inSoloEvent)
		{
			player.sendMessage("Debug: Error During buff players");
			return;
		}
		if (_eventType == 1)
		{
			int previousPoints = player.getEventPoints();
			if (Config.RAID_SYSTEM_GIVE_BUFFS)
				L2EventBufferInstance.makeBuffs(player, buffList, efector, false);
			player.setEventPoints(player.getEventPoints() - eventPoints);
			player.sendMessage("Event Manager: " + eventPoints + " Event Points have Been used. " + "You had " + previousPoints + " and now you have " + player.getEventPoints() + "Event Points.");
		}
		
		// TODO: Check if the distance of other clan members is important upon member buffing.
		if (_eventType == 2)
		{
			
			int cmCount = _participatingPlayers.size();
			
			int individualPrice = eventPoints / cmCount;
			
			for (L2PcInstance member : _participatingPlayers)
			{
				
				int previousPoints = player.getEventPoints();
				if (member == null)
					continue;
				
				if (Config.RAID_SYSTEM_GIVE_BUFFS)
					L2EventBufferInstance.makeBuffs(member, buffList, efector, false);
				previousPoints = member.getEventPoints();
				if (individualPrice > member.getEventPoints())
				{
					
					member.setEventPoints(0);
					NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
					StringBuilder replyMSG = new StringBuilder("<html><body>");
					replyMSG.append("<tr><td>A total of " + eventPoints + " points have been deduced from your party TOTAL Event Point Score.</td></tr><br>");
					replyMSG.append("<tr><td>You didn't have enough Event Points, so we've used all of your points.</td></tr><br>");
					replyMSG.append("<tr><td>You had " + previousPoints + ", and we needed " + individualPrice + " points.</td></tr><br><br><br>");
					replyMSG.append("</body></html>");
					adminReply.setHtml(replyMSG.toString());
					member.sendPacket(adminReply);
				}
				else
				{
					member.setEventPoints(member.getEventPoints() - individualPrice);
					NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
					StringBuilder replyMSG = new StringBuilder("<html><body>");
					replyMSG.append("<tr><td>A total of " + eventPoints + " points have been deduced from your party TOTAL Event Point Score.</td></tr><br>");
					replyMSG.append("<tr><td>You had " + previousPoints + ", and now you have " + (previousPoints - individualPrice) + " points.</td></tr><br><br><br>");
					replyMSG.append("</body></html>");
					adminReply.setHtml(replyMSG.toString());
					member.sendPacket(adminReply);
				}
			}
		}
		
		if (_eventType == 3)
		{
			int pmCount = player.getParty().getMemberCount();
			int individualPrice = eventPoints / pmCount;
			
			for (L2PcInstance member : _participatingPlayers)
			{
				if (member == null)
					continue;
				if (Config.RAID_SYSTEM_GIVE_BUFFS)
					L2EventBufferInstance.makeBuffs(member, buffList, efector, false);
				member.inPartyEvent = true;
				if (individualPrice > member.getEventPoints())
				{
					int previousPoints = member.getEventPoints();
					member.setEventPoints(0);
					NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
					StringBuilder replyMSG = new StringBuilder("<html><body>");
					replyMSG.append("<tr><td>A total of " + eventPoints + " points have been deduced from your party TOTAL Event Point Score.</td></tr><br>");
					replyMSG.append("<tr><td>You didn't have enough Event Points, so we've used all of your points.</td></tr><br>");
					replyMSG.append("<tr><td>You had " + previousPoints + ", and we needed " + individualPrice + " points.</td></tr><br><br><br>");
					
					replyMSG.append("</body></html>");
					adminReply.setHtml(replyMSG.toString());
					member.sendPacket(adminReply);
				}
				else
				{
					int previousPoints = member.getEventPoints();
					member.setEventPoints(member.getEventPoints() - individualPrice);
					NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
					StringBuilder replyMSG = new StringBuilder("<html><body>");
					replyMSG.append("<tr><td>A total of " + eventPoints + " points have been deduced from your party TOTAL Event Point Score.</td></tr><br>");
					replyMSG.append("<tr><td>You had " + previousPoints + ", and now you have " + (previousPoints - individualPrice) + " points.</td></tr><br><br><br>");
					
					replyMSG.append("</body></html>");
					adminReply.setHtml(replyMSG.toString());
					member.sendPacket(adminReply);
				}
			}
		}
	}
	
	private static void startEvent(L2PcInstance player, int npcId, int ammount)
	{
		if (player == null)
			return;
		int currentEvents = L2EventManagerInstance._currentEvents;
		if (currentEvents >= Config.RAID_SYSTEM_MAX_EVENTS)
			return;
		if (currentEvents == 0)
			return;
		setState(EventState.STARTED);
		
		doTeleport(player, _locX, _locY, _locZ, 10, false);
		
		spawnMonster(npcId, 60, ammount, _npcX, _npcY, _npcZ);
	}
	
	private static void doTeleport(L2PcInstance player, final int cox, final int coy, final int coz, int delay, final boolean removeBuffs)
	{
		for (final L2PcInstance member : _participatingPlayers)
		{
			// TODO: Have a look again to this mess.
			member.sendMessage("You will be teleported in 10 seconds.");
			
			ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
			{
				@Override
				public void run()
				{
					if (removeBuffs)
					{
						member.stopAllEffects();
						member.updateEffectIcons();
						member.broadcastUserInfo();
					}
					member.teleToLocation(cox, coy, coz);
				}
			}, 10000);
		}
	}
	
	private static void spawnMonster(int monsterId, int respawnDelay, int mobCount, int locX, int locY, int locZ)
	{
		L2NpcTemplate template;
		int monsterTemplate = monsterId;
		template = NpcData.getInstance().getTemplate(monsterTemplate);
		if (template == null)
			return;
		_eventMobs = mobCount;
		
		if (mobCount > 1)
		{
			int n = 1;
			while (n <= mobCount)
			{
				try
				{
					L2Spawn spawn = new L2Spawn(template);
					// TODO: Add support for different spawning zones.
					spawn.setLocx(locX);
					spawn.setLocy(locY);
					spawn.setLocz(locZ);
					spawn.setAmount(1);
					spawn.setHeading(0);
					spawn.setRespawnDelay(respawnDelay);
					if (RaidBossSpawnManager.getInstance().getValidTemplate(spawn.getNpcid()) != null)
						RaidBossSpawnManager.getInstance().addNewSpawn(spawn, 0, template.getStatsSet().getDouble("baseHpMax"), template.getStatsSet().getDouble("baseMpMax"), false);
					else
						SpawnTable.getInstance().addNewSpawn(spawn, false);
					spawn.init();
					
					// TODO: Change the Mob statistics according on Event
					_lastNpcSpawn = spawn.getLastSpawn();
					_npcSpawn = spawn;
					_lastNpcSpawn.isPrivateEventMob = true;
					if (Config.CHAMPION_ENABLE)
						_lastNpcSpawn.setChampion(false);
					_lastNpcSpawn.setTitle("Event Monster");
					
					_npcSpawn.stopRespawn();
					_eventMobList.add(_lastNpcSpawn);
					n++;
				}
				catch (Exception e)
				{
					_log.warning(L2RaidEvent.class.getSimpleName() + ": L2EventManager: Exception Upon MULTIPLE NPC SPAWN.");
					e.printStackTrace();
				}
			}
			setState(EventState.PARTICIPATING);
		}
		else
		{
			try
			{
				L2Spawn spawn = new L2Spawn(template);
				spawn.setLocx(locX);
				spawn.setLocy(locY);
				spawn.setLocz(locZ);
				spawn.setAmount(1);
				spawn.setHeading(0);
				spawn.setRespawnDelay(respawnDelay);
				if (RaidBossSpawnManager.getInstance().getValidTemplate(spawn.getNpcid()) != null)
					RaidBossSpawnManager.getInstance().addNewSpawn(spawn, 0, template.getStatsSet().getDouble("baseHpMax"), template.getStatsSet().getDouble("baseMpMax"), false);
				else
					SpawnTable.getInstance().addNewSpawn(spawn, false);
				spawn.init();
				_lastNpcSpawn = spawn.getLastSpawn();
				_npcSpawn = spawn;
				_lastNpcSpawn.isPrivateEventMob = true;
				_lastNpcSpawn.setChampion(false);
				_lastNpcSpawn.setTitle("Event Monster");
				_npcSpawn.stopRespawn();
				_eventMobList.add(_lastNpcSpawn);
			}
			catch (Exception e)
			{
				_log.warning(L2RaidEvent.class.getSimpleName() + ": L2EventManager: Exception Upon SINGLE NPC SPAWN.");
				e.printStackTrace();
			}
			setState(EventState.PARTICIPATING);
		}
		new RaidFightManager();
	}
	
	private static void unSpawnNPC()
	{
		if (_lastNpcSpawn != null && _npcSpawn != null)
		{
			_lastNpcSpawn.deleteMe();
			_npcSpawn.stopRespawn();
			_npcSpawn = null;
			_lastNpcSpawn = null;
		}
		else
		{
			_log.warning(L2RaidEvent.class.getSimpleName() + ": L2EventManager: Exception Upon NPC UNSPAWN.");
		}
	}
	
	public static void onPlayerDeath(L2PcInstance player)
	{
		
		if (player != null)
		{
			player.abortCast();
			player.abortAttack();
			player.sendMessage("Raid Engine: You will be revived now!");
			player.teleToLocation(_locX, _locY, _locZ, false);
			player.doRevive();
			player.setCurrentHp(player.getMaxHp());
			player.setCurrentMp(player.getMaxMp());
			player.setCurrentCp(player.getMaxCp());
		}
	}
	
	public static boolean checkPossibleReward()
	{
		if (_eventMobs > 1)
		{
			_eventMobs = _eventMobs - 1;
			return false;
		}
		else if (_eventMobs < 1)
		{
			_eventMobs = 0;
			return false;
		}
		else if (_eventMobs == 0)
		{
			setState(EventState.REWARDING);
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	public static void chooseReward(L2PcInstance player)
	{
		if (_eventMobs == 1)
			_eventMobs = 0;
		else
			return;
		loadData(_rewardLevel);
		
		if (_eventType == 1)
		{
			handOutItems(player, _first_id, _first_ammount, _second_id, _second_ammount, _event_ammount);
			
			unSpawnNPC();
			clearFromEvent(player);
			
			doTeleport(player, _pX, _pY, _pZ, 10, true);
			if (L2EventManagerInstance._currentEvents != 0)
				L2EventManagerInstance._currentEvents = L2EventManagerInstance._currentEvents - 1;
		}
		
		if (_eventType == 2)
		{
			for (L2PcInstance member : _participatingPlayers)
			{
				if (member != null)
				{
					handOutItems(member, _first_id, _first_ammount, _second_id, _second_ammount, _event_ammount);
					doTeleport(member, _pX, _pY, _pZ, 10, true);
				}
			}
			unSpawnNPC();
			clearFromEvent(player);
			if (L2EventManagerInstance._currentEvents != 0)
				L2EventManagerInstance._currentEvents = L2EventManagerInstance._currentEvents - 1;
		}
		
		if (_eventType == 3)
		{
			if (player.getParty() != null)
			{
				for (L2PcInstance member : _participatingPlayers)
				{
					handOutItems(member, _first_id, _first_ammount, _second_id, _second_ammount, _event_ammount);
					doTeleport(member, _pX, _pY, _pZ, 10, true);
				}
				unSpawnNPC();
				clearFromEvent(player);
				if (L2EventManagerInstance._currentEvents != 0)
					L2EventManagerInstance._currentEvents = L2EventManagerInstance._currentEvents - 1;
			}
			else
			{
				player.sendMessage("You don't have a party anymore?! Well then the rewards go for you only.");
				
				handOutItems(player, _first_id, _first_ammount, _second_id, _second_ammount, _event_ammount);
				
				unSpawnNPC();
				clearFromEvent(player);
				
				doTeleport(player, _pX, _pY, _pZ, 10, true);
				if (L2EventManagerInstance._currentEvents != 0)
					L2EventManagerInstance._currentEvents = L2EventManagerInstance._currentEvents - 1;
				return;
			}
			unSpawnNPC();
			clearFromEvent(player);
			if (L2EventManagerInstance._currentEvents != 0)
				L2EventManagerInstance._currentEvents = L2EventManagerInstance._currentEvents - 1;
		}
		return;
	}
	
	public static void expHandOut()
	{
		exp += exp;
		sp += sp;
	}
	
	private synchronized static void clearFromEvent(L2PcInstance player)
	{
		setState(EventState.INACTIVATING);
		if (_eventType != 1 && _eventType != 2 && _eventType != 3)
			return;
		if (_eventType == 1)
		{
			player.inSoloEvent = false;
		}
		if (_eventType == 2)
		{
			if (_participatingPlayers.size() != 0)
			{
				for (L2PcInstance member : _participatingPlayers)
				{
					if (member != null)
						member.inClanEvent = false;
				}
				// Clear Clan Members from event.
				if (_participatingPlayers.size() != 0)
					_participatingPlayers.clear();
			}
		}
		if (_eventType == 3)
		{
			if (player.getParty() != null)
			{
				player.inPartyEvent = false;
				for (L2PcInstance member : _participatingPlayers)
				{
					if (member != null)
						member.inPartyEvent = false;
				}
			}
			else
				player.inPartyEvent = false;
		}
		setState(EventState.INACTIVE);
	}
	
	private static void handOutItems(L2PcInstance player, int item1, int ammount1, int item2, int ammount2, int eventPoints)
	{
		boolean hasItem1 = false;
		boolean hasItem2 = false;
		boolean hasEventPoints = false;
		if (item1 == 0 && item2 == 0 && eventPoints == 0)
			return;
		if (item1 != 0)
			hasItem1 = true;
		if (item2 != 0)
			hasItem2 = true;
		if (eventPoints != 0)
			hasEventPoints = true;
		if (hasItem1)
		{
			player.addItem("Event", item1, ammount1, player, true);
		}
		if (hasItem2)
		{
			player.addItem("Event", item2, ammount2, player, true);
		}
		if (hasEventPoints)
		{
			player.setEventPoints(player.getEventPoints() + eventPoints);
			SystemMessage smp;
			smp = SystemMessage.getSystemMessage(SystemMessageId.EARNED_S2_S1_S);
			smp.addString("Event Points ");
			smp.addNumber(2);
			player.sendPacket(smp);
		}
		StatusUpdate su = new StatusUpdate(player.getObjectId());
		su.addAttribute(StatusUpdate.CUR_LOAD, player.getCurrentLoad());
		player.sendPacket(su);
		player.sendPacket(new ItemList(player, true));
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		StringBuilder replyMSG = new StringBuilder("<html><body>");
		replyMSG.append("<tr><td>You won the event!</td></tr><br>");
		replyMSG.append("<tr><td>You have Earned:</td></tr><br>");
		if (hasItem1)
		{
			String item1name = ItemTable.getInstance().createDummyItem(item1).getItemName();
			replyMSG.append("<tr><td>- " + ammount1 + " " + item1name + ".</td></tr><br>");
		}
		if (hasItem2)
		{
			String item2name = ItemTable.getInstance().createDummyItem(item2).getItemName();
			replyMSG.append("<tr><td>- " + ammount2 + " " + item2name + ".</td></tr><br>");
		}
		if (hasEventPoints)
		{
			replyMSG.append("<tr><td>- " + eventPoints + " Event Points.</td></tr><br>");
		}
		replyMSG.append("<br><tr><td>Congratulations!!</td></tr><br><br><br>");
		replyMSG.append("</body></html>");
		adminReply.setHtml(replyMSG.toString());
		player.sendPacket(adminReply);
	}
	
	public static void hardFinish()
	{
		for (L2Npc eventMob : _eventMobList)
		{
			eventMob.decayMe();
			eventMob.deleteMe();
			L2EventManagerInstance._currentEvents -= 1;
		}
		_log.warning(L2RaidEvent.class.getSimpleName() + ": Raid Engines: All the Members from the Event are now dead or Have Left The event. Event Finished.");
	}
	
	private static void loadData(int prizePackage)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT first_prize_id, first_prize_ammount, second_prize_id, second_prize_ammount, event_points_ammount FROM raid_prizes WHERE prize_package_id = '" + prizePackage + "'");
			ResultSet rset = statement.executeQuery();
			while (rset.next())
			{
				_first_id = rset.getInt("first_prize_id");
				_first_ammount = rset.getInt("first_prize_ammount");
				_second_id = rset.getInt("second_prize_id");
				_second_ammount = rset.getInt("second_prize_ammount");
				_event_ammount = rset.getInt("event_points_ammount");
			}
			rset.close();
			statement.close();
		}
		catch (SQLException e)
		{
			_log.severe("Error While loading Raids prizes.");
			if (Config.DEVELOPER)
				e.printStackTrace();
		}
	}
	
	private static void setState(EventState state)
	{
		synchronized (_state)
		{
			_state = state;
		}
	}
	
	public static boolean isInactive()
	{
		boolean isInactive;
		
		synchronized (_state)
		{
			isInactive = _state == EventState.INACTIVE;
		}
		
		return isInactive;
	}
	
	public static boolean isInactivating()
	{
		boolean isInactivating;
		
		synchronized (_state)
		{
			isInactivating = _state == EventState.INACTIVATING;
		}
		
		return isInactivating;
	}
	
	public static boolean isParticipating()
	{
		boolean isParticipating;
		
		synchronized (_state)
		{
			isParticipating = _state == EventState.PARTICIPATING;
		}
		
		return isParticipating;
	}
	
	public static boolean isStarting()
	{
		boolean isStarting;
		
		synchronized (_state)
		{
			isStarting = _state == EventState.STARTING;
		}
		
		return isStarting;
	}
	
	public static boolean isStarted()
	{
		boolean isStarted;
		
		synchronized (_state)
		{
			isStarted = _state == EventState.STARTED;
		}
		
		return isStarted;
	}
	
	public static boolean isRewarding()
	{
		boolean isRewarding;
		
		synchronized (_state)
		{
			isRewarding = _state == EventState.REWARDING;
		}
		
		return isRewarding;
	}
	
	public static void sysMsgToAllParticipants(String message)
	{
		for (L2PcInstance player : _participatingPlayers)
		{
			if (player != null)
				player.sendMessage(message);
		}
	}
	
	private static void loadSpawns(int eventNum)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT raid_locX, raid_locY, raid_locZ, player_locX, player_locY, player_locZ FROM raid_event_spawnlist WHERE id = '" + eventNum + "'");
			ResultSet rset = statement.executeQuery();
			while (rset.next())
			{
				_npcX = rset.getInt("raid_locX");
				_npcY = rset.getInt("raid_locY");
				_npcZ = rset.getInt("raid_locZ");
				_locX = rset.getInt("player_locX");
				_locY = rset.getInt("player_locY");
				_locZ = rset.getInt("player_locZ");
			}
			rset.close();
			statement.close();
		}
		catch (SQLException e)
		{
			_log.severe("Error While loading Raids Spawn Positions.");
			if (Config.DEVELOPER)
				e.printStackTrace();
		}
	}
}