package com.l2jhellas.fake.roboto;

import java.util.List;
import java.util.stream.Collectors;

import com.l2jhellas.Config;
import com.l2jhellas.fake.roboto.helpers.FakeHelpers;
import com.l2jhellas.gameserver.datatables.xml.MapRegionTable.TeleportWhereType;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.zone.ZoneId;

public enum FakePlayerManager
{
	INSTANCE;

	private FakePlayerManager()
	{

	}

	public void initialise()
	{
		FakePlayerNameManager.INSTANCE.initialise();
		FakePlayerTaskManager.INSTANCE.initialise();
	}

	public FakePlayer spawnPlayer(int x, int y, int z)
	{
		FakePlayer activeChar = FakeHelpers.createRandomFakePlayer();

		L2World.getInstance().addToAllPlayers(activeChar);
		
		activeChar.spawnMe(x, y, z);
		activeChar.onPlayerEnter();
		
		if (Config.PLAYER_SPAWN_PROTECTION > 0)
			activeChar.setProtection(true);

		if ((!activeChar.isInSiege() || activeChar.getSiegeState() < 2) && activeChar.isInsideZone(ZoneId.SIEGE))
			activeChar.teleToLocation(TeleportWhereType.TOWN);

		activeChar.heal();
		return activeChar;
	}

	public void despawnFakePlayer(int objectId)
	{
		L2PcInstance player = L2World.getInstance().getPlayer(objectId);
		if (player instanceof FakePlayer)
		{
			FakePlayer fakePlayer = (FakePlayer) player;
			fakePlayer.despawnPlayer();
		}
	}

	public int getFakePlayersCount()
	{
		return getFakePlayers().size();
	}


	public List<FakePlayer> getFakePlayers()
	{
	  return L2World.getInstance().getAllPlayers().values().stream().filter(x -> x instanceof FakePlayer).map(x -> (FakePlayer) x).collect(Collectors.toList());
	}
}
