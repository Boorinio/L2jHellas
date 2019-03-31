package com.l2jhellas.gameserver.network.serverpackets;

import com.l2jhellas.gameserver.model.actor.instance.L2BoatInstance;

public class OnVehicleCheckLocation extends L2GameServerPacket
{
	private final L2BoatInstance _boat;
	private final int _x;
	private final int _y;
	private final int _z;
	
	public OnVehicleCheckLocation(L2BoatInstance instance, int x, int y, int z)
	{
		_boat = instance;
		_x = x;
		_y = y;
		_z = z;
	}
	
	@Override
	protected void writeImpl()
	{
		
		writeC(0x5b);
		writeD(_boat.getObjectId());
		writeD(_x);
		writeD(_y);
		writeD(_z);
		writeD(_boat.getHeading());
	}
	
	@Override
	public String getType()
	{
		return null;
	}
}