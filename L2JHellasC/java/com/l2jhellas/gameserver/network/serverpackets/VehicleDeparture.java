package com.l2jhellas.gameserver.network.serverpackets;

import com.l2jhellas.gameserver.model.actor.instance.L2BoatInstance;

public class VehicleDeparture extends L2GameServerPacket
{
	// Store parameters because they can be changed during broadcast
	private final int _objId, _x, _y, _z, _moveSpeed, _rotationSpeed;
	
	public VehicleDeparture(L2BoatInstance boat)
	{
		_objId = boat.getObjectId();
		_x = boat.getXdestination();
		_y = boat.getYdestination();
		_z = boat.getZdestination();
		_moveSpeed = (int) boat.getStat().getMoveSpeed();
		_rotationSpeed = boat.getRotationSpeed();
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0x5A);
		writeD(_objId);
		writeD(_moveSpeed);
		writeD(_rotationSpeed);
		writeD(_x);
		writeD(_y);
		writeD(_z);
	}
}