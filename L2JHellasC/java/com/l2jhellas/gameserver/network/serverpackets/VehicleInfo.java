package com.l2jhellas.gameserver.network.serverpackets;

import com.l2jhellas.gameserver.model.actor.L2Vehicle;
import com.l2jhellas.gameserver.model.actor.instance.L2BoatInstance;

public class VehicleInfo extends L2GameServerPacket
{
	// Store some parameters here because they can be changed during broadcast
	private final int _objId, _x, _y, _z, _heading;
	
	public VehicleInfo(L2BoatInstance boat)
	{
		_objId = boat.getObjectId();
		_x = boat.getX();
		_y = boat.getY();
		_z = boat.getZ();
		_heading = boat.getHeading();
	}
	
	public VehicleInfo(L2Vehicle boat)
	{
		_objId = boat.getObjectId();
		_x = boat.getX();
		_y = boat.getY();
		_z = boat.getZ();
		_heading = boat.getHeading();
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0x59);
		writeD(_objId);
		writeD(_x);
		writeD(_y);
		writeD(_z);
		writeD(_heading);
	}
}