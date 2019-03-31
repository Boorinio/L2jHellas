package com.l2jhellas.gameserver.model.actor.instance;

import com.l2jhellas.gameserver.ai.L2BoatAI;
import com.l2jhellas.gameserver.model.L2CharPosition;
import com.l2jhellas.gameserver.model.Location;
import com.l2jhellas.gameserver.model.actor.L2Vehicle;
import com.l2jhellas.gameserver.network.serverpackets.OnVehicleCheckLocation;
import com.l2jhellas.gameserver.network.serverpackets.VehicleDeparture;
import com.l2jhellas.gameserver.network.serverpackets.VehicleInfo;
import com.l2jhellas.gameserver.network.serverpackets.VehicleStarted;
import com.l2jhellas.gameserver.templates.L2CharTemplate;

import java.util.logging.Logger;

public class L2BoatInstance extends L2Vehicle
{
	protected static final Logger _logBoat = Logger.getLogger(L2BoatInstance.class.getName());
	public float boatSpeed = getMoveSpeed();
	
	public L2BoatInstance(int objectId, L2CharTemplate template)
	{
		super(objectId, template);
		setAI(new L2BoatAI(new AIAccessor()));
	}
	
	@Override
	public boolean isBoat()
	{
		return true;
	}
	
	@Override
	public boolean isFlying()
	{
		return true;
	}
	
	@Override
	public boolean moveToNextRoutePoint()
	{
		final boolean result = super.moveToNextRoutePoint();
		if (result)
			broadcastPacket(new VehicleDeparture(this));
		
		return result;
	}
	
	@Override
	public void oustPlayer(L2PcInstance player)
	{
		super.oustPlayer(player);
		
		final Location loc = getOustLoc();
		if (player.isOnline() == 1)
			player.teleToLocation(loc.getX(), loc.getY(), loc.getZ(), false);
		else
			player.setXYZInvisible(loc.getX(), loc.getY(), loc.getZ()); // disconnects handling
	}
	
	@Override
	public void stopMove(L2CharPosition pos)
	{
		super.stopMove(pos);
		
		broadcastPacket(new VehicleStarted(this, 0));
		broadcastPacket(new VehicleInfo(this));
	}
	
	public void updatePeopleInTheBoat(int x, int y, int z)
	{
		updatePosition();
		broadcastToPassengers(new OnVehicleCheckLocation(this, x, y, z));
	}
	
	@Override
	public void sendInfo(L2PcInstance activeChar)
	{
		activeChar.sendPacket(new VehicleInfo(this));
		
		if (isMoving())
			activeChar.sendPacket(new VehicleDeparture(this));
	}
	
}
