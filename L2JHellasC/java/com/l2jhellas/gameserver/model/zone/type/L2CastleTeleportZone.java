package com.l2jhellas.gameserver.model.zone.type;

import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.zone.L2ZoneType;
import com.l2jhellas.gameserver.model.zone.ZoneId;
import com.l2jhellas.util.Rnd;

public class L2CastleTeleportZone extends L2ZoneType
{
	private final int[] _spawnLoc;
	private int _castleId;
	
	public L2CastleTeleportZone(int id)
	{
		super(id);
		
		_spawnLoc = new int[5];
	}
	
	@Override
	public void setParameter(String name, String value)
	{
		if (name.equals("castleId"))
			_castleId = Integer.parseInt(value);
		else if (name.equals("spawnMinX"))
			_spawnLoc[0] = Integer.parseInt(value);
		else if (name.equals("spawnMaxX"))
			_spawnLoc[1] = Integer.parseInt(value);
		else if (name.equals("spawnMinY"))
			_spawnLoc[2] = Integer.parseInt(value);
		else if (name.equals("spawnMaxY"))
			_spawnLoc[3] = Integer.parseInt(value);
		else if (name.equals("spawnZ"))
			_spawnLoc[4] = Integer.parseInt(value);
		else
			super.setParameter(name, value);
	}
	
	@Override
	protected void onEnter(L2Character character)
	{
		character.setInsideZone(ZoneId.NO_SUMMON_FRIEND, true);
	}
	
	@Override
	protected void onExit(L2Character character)
	{
		character.setInsideZone(ZoneId.NO_SUMMON_FRIEND, false);
	}
	
	@Override
	public void onDieInside(L2Character character)
	{
	}
	
	@Override
	public void onReviveInside(L2Character character)
	{
	}
	
	public void oustAllPlayers()
	{
		if (_characterList.isEmpty())
			return;
		
		for (L2PcInstance player : getKnownTypeInside(L2PcInstance.class))
		{
			if (player.isOnline() == 1)
				player.teleToLocation(Rnd.get(_spawnLoc[0], _spawnLoc[1]), Rnd.get(_spawnLoc[2], _spawnLoc[3]), _spawnLoc[4], false);
		}
	}
	
	public int getCastleId()
	{
		return _castleId;
	}
	
	public int[] getSpawn()
	{
		return _spawnLoc;
	}
}