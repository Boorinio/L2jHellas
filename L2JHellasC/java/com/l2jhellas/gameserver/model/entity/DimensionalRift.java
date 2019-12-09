package com.l2jhellas.gameserver.model.entity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.instancemanager.DimensionalRiftManager;
import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.group.party.L2Party;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.util.Rnd;

public class DimensionalRift
{
	protected byte _type;
	protected L2Party _party;
	protected ArrayList<Byte> _completedRooms = new ArrayList<>();
	private static final long seconds_5 = 5000L;
	// private static final int MILLISECONDS_IN_MINUTE = 60000;
	protected byte jumps_current = 0;
	
	private Timer teleporterTimer;
	private TimerTask teleporterTimerTask;
	private Timer spawnTimer;
	private TimerTask spawnTimerTask;
	
	protected byte _choosenRoom = -1;
	private boolean _hasJumped = false;
	protected ArrayList<L2PcInstance> deadPlayers = new ArrayList<>();
	protected ArrayList<L2PcInstance> revivedInWaitingRoom = new ArrayList<>();
	private boolean isBossRoom = false;
	
	public DimensionalRift(L2Party party, byte type, byte room)
	{
		_type = type;
		_party = party;
		_choosenRoom = room;
		int[] coords = getRoomCoord(room);
		party.setDimensionalRift(this);
		for (L2PcInstance p : party.getPartyMembers())
			p.teleToLocation(coords[0], coords[1], coords[2]);
		createSpawnTimer(_choosenRoom);
		createTeleporterTimer(true);
	}
	
	public byte getType()
	{
		return _type;
	}
	
	public byte getCurrentRoom()
	{
		return _choosenRoom;
	}
	
	protected void createTeleporterTimer(final boolean reasonTP)
	{
		if (teleporterTimerTask != null)
		{
			teleporterTimerTask.cancel();
			teleporterTimerTask = null;
		}
		
		if (teleporterTimer != null)
		{
			teleporterTimer.cancel();
			teleporterTimer = null;
		}
		
		teleporterTimer = new Timer();
		teleporterTimerTask = new TimerTask()
		{
			@Override
			public void run()
			{
				if (_choosenRoom > -1)
					DimensionalRiftManager.getInstance().getRoom(_type, _choosenRoom).unspawn();
				
				if (reasonTP && jumps_current < getMaxJumps() && _party.getMemberCount() > deadPlayers.size())
				{
					jumps_current++;
					
					_completedRooms.add(_choosenRoom);
					_choosenRoom = -1;
					
					for (L2PcInstance p : _party.getPartyMembers())
						if (!revivedInWaitingRoom.contains(p))
							teleportToNextRoom(p);
					createTeleporterTimer(true);
					createSpawnTimer(_choosenRoom);
				}
				else
				{
					for (L2PcInstance p : _party.getPartyMembers())
						if (!revivedInWaitingRoom.contains(p))
							teleportToWaitingRoom(p);
					killRift();
					cancel();
				}
			}
		};
		
		if (reasonTP)
			teleporterTimer.schedule(teleporterTimerTask, calcTimeToNextJump()); // Teleporter task, 8-10 minutes
		else
			teleporterTimer.schedule(teleporterTimerTask, seconds_5); // incorrect party member invited.
	}
	
	public void createSpawnTimer(final byte room)
	{
		if (spawnTimerTask != null)
		{
			spawnTimerTask.cancel();
			spawnTimerTask = null;
		}
		
		if (spawnTimer != null)
		{
			spawnTimer.cancel();
			spawnTimer = null;
		}
		
		spawnTimer = new Timer();
		spawnTimerTask = new TimerTask()
		{
			@Override
			public void run()
			{
				DimensionalRiftManager.getInstance().getRoom(_type, room).spawn();
			}
		};
		
		spawnTimer.schedule(spawnTimerTask, Config.RIFT_SPAWN_DELAY);
	}
	
	public void partyMemberInvited()
	{
		createTeleporterTimer(false);
	}
	
	public void partyMemberExited(L2PcInstance player)
	{
		if (deadPlayers.contains(player))
			deadPlayers.remove(player);
		
		if (revivedInWaitingRoom.contains(player))
			revivedInWaitingRoom.remove(player);
		
		if ((_party.getMemberCount() < Config.RIFT_MIN_PARTY_SIZE) || (_party.getMemberCount() == 1))
		{
			for (L2PcInstance p : _party.getPartyMembers())
				teleportToWaitingRoom(p);
			killRift();
		}
	}
	
	public void manualTeleport(L2PcInstance player, L2Npc npc)
	{
		if (!player.isInParty() || !player.getParty().isInDimensionalRift())
			return;
		
		if (player.getObjectId() != player.getParty().getPartyLeaderOID())
		{
			DimensionalRiftManager.getInstance().showHtmlFile(player, "data/html/seven_signs/rift/NotPartyLeader.htm", npc);
			return;
		}
		
		if (_hasJumped)
		{
			DimensionalRiftManager.getInstance().showHtmlFile(player, "data/html/seven_signs/rift/AlreadyTeleported.htm", npc);
			return;
		}
		
		_hasJumped = true;
		
		DimensionalRiftManager.getInstance().getRoom(_type, _choosenRoom).unspawn();
		_completedRooms.add(_choosenRoom);
		_choosenRoom = -1;
		
		for (L2PcInstance p : _party.getPartyMembers())
			teleportToNextRoom(p);
		
		createSpawnTimer(_choosenRoom);
		createTeleporterTimer(true);
	}
	
	public void manualExitRift(L2PcInstance player, L2Npc npc)
	{
		if (!player.isInParty() || !player.getParty().isInDimensionalRift())
			return;
		
		if (player.getObjectId() != player.getParty().getPartyLeaderOID())
		{
			DimensionalRiftManager.getInstance().showHtmlFile(player, "data/html/seven_signs/rift/NotPartyLeader.htm", npc);
			return;
		}
		
		for (L2PcInstance p : player.getParty().getPartyMembers())
			teleportToWaitingRoom(p);
		killRift();
	}
	
	protected void teleportToNextRoom(L2PcInstance player)
	{
		if (_choosenRoom == -1)
		{// Do not tp in the same room a second time
			do
				_choosenRoom = (byte) Rnd.get(1, 9);
			while (_completedRooms.contains(_choosenRoom));
		}
		
		checkBossRoom(_choosenRoom);
		int[] coords = getRoomCoord(_choosenRoom);
		player.teleToLocation(coords[0], coords[1], coords[2]);
	}
	
	protected void teleportToWaitingRoom(L2PcInstance player)
	{
		DimensionalRiftManager.getInstance().teleportToWaitingRoom(player);
	}
	
	public void killRift()
	{
		_completedRooms = null;
		
		if (_party != null)
			_party.setDimensionalRift(null);
		
		_party = null;
		revivedInWaitingRoom = null;
		deadPlayers = null;
		DimensionalRiftManager.getInstance().getRoom(_type, _choosenRoom).unspawn();
		DimensionalRiftManager.getInstance().killRift(this);
	}
	
	public Timer getTeleportTimer()
	{
		return teleporterTimer;
	}
	
	public TimerTask getTeleportTimerTask()
	{
		return teleporterTimerTask;
	}
	
	public Timer getSpawnTimer()
	{
		return spawnTimer;
	}
	
	public TimerTask getSpawnTimerTask()
	{
		return spawnTimerTask;
	}
	
	public void setTeleportTimer(Timer t)
	{
		teleporterTimer = t;
	}
	
	public void setTeleportTimerTask(TimerTask tt)
	{
		teleporterTimerTask = tt;
	}
	
	public void setSpawnTimer(Timer t)
	{
		spawnTimer = t;
	}
	
	public void setSpawnTimerTask(TimerTask st)
	{
		spawnTimerTask = st;
	}
	
	private long calcTimeToNextJump()
	{
		int time = Rnd.get(Config.RIFT_AUTO_JUMPS_TIME_MIN, Config.RIFT_AUTO_JUMPS_TIME_MAX) * 1000;
		
		if (isBossRoom)
			return (long) (time * Config.RIFT_BOSS_ROOM_TIME_MUTIPLY);

		return time;
	}
	
	public void memberDead(L2PcInstance player)
	{
		if (!deadPlayers.contains(player))
			deadPlayers.add(player);
	}
	
	public void memberRessurected(L2PcInstance player)
	{
		if (deadPlayers.contains(player))
			deadPlayers.remove(player);
	}
	
	public void usedTeleport(L2PcInstance player)
	{
		if (!revivedInWaitingRoom.contains(player))
			revivedInWaitingRoom.add(player);
		
		if (!deadPlayers.contains(player))
			deadPlayers.add(player);
		
		if (_party.getMemberCount() - revivedInWaitingRoom.size() < Config.RIFT_MIN_PARTY_SIZE)
		{
			// int pcm = _party.getMemberCount();
			// int rev = revivedInWaitingRoom.size();
			// int min = Config.RIFT_MIN_PARTY_SIZE;
			
			for (L2PcInstance p : _party.getPartyMembers())
				if (!revivedInWaitingRoom.contains(p))
					teleportToWaitingRoom(p);
			killRift();
		}
	}
	
	public ArrayList<L2PcInstance> getDeadMemberList()
	{
		return deadPlayers;
	}
	
	public ArrayList<L2PcInstance> getRevivedAtWaitingRoom()
	{
		return revivedInWaitingRoom;
	}
	
	public void checkBossRoom(byte room)
	{
		isBossRoom = DimensionalRiftManager.getInstance().getRoom(_type, room).isBossRoom();
	}
	
	public int[] getRoomCoord(byte room)
	{
		return DimensionalRiftManager.getInstance().getRoom(_type, room).getTeleportCoords();
	}
	
	public byte getMaxJumps()
	{
		if ((Config.RIFT_MAX_JUMPS <= 8) && (Config.RIFT_MAX_JUMPS >= 1))
			return (byte) Config.RIFT_MAX_JUMPS;

		return 4;
	}
}