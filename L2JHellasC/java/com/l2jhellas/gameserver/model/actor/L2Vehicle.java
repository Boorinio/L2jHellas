package com.l2jhellas.gameserver.model.actor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.ai.CtrlIntention;
import com.l2jhellas.gameserver.ai.L2BoatAI;
import com.l2jhellas.gameserver.ai.L2CharacterAI;
import com.l2jhellas.gameserver.controllers.GameTimeController;
import com.l2jhellas.gameserver.datatables.xml.MapRegionTable;
import com.l2jhellas.gameserver.instancemanager.ZoneManager;
import com.l2jhellas.gameserver.model.L2CharPosition;
import com.l2jhellas.gameserver.model.L2ItemInstance;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.Location;
import com.l2jhellas.gameserver.model.VehiclePathPoint;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.actor.stat.VehicleStat;
import com.l2jhellas.gameserver.model.zone.ZoneId;
import com.l2jhellas.gameserver.model.zone.ZoneRegion;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.InventoryUpdate;
import com.l2jhellas.gameserver.network.serverpackets.L2GameServerPacket;
import com.l2jhellas.gameserver.network.serverpackets.OnVehicleCheckLocation;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;
import com.l2jhellas.gameserver.network.serverpackets.VehicleDeparture;
import com.l2jhellas.gameserver.network.serverpackets.VehicleInfo;
import com.l2jhellas.gameserver.network.serverpackets.VehicleStarted;
import com.l2jhellas.gameserver.templates.L2CharTemplate;
import com.l2jhellas.gameserver.templates.L2Weapon;
import com.l2jhellas.util.Util;

public class L2Vehicle extends L2Character
{
	protected int _dockId = 0;
	protected final List<L2PcInstance> _passengers = new ArrayList<>();
	private Runnable _engine = null;
	
	protected VehiclePathPoint[] _currentPath = null;
	protected int _runState = 0;
	
	public L2Vehicle(int objectId, L2CharTemplate template)
	{
		super(objectId, template);
		setAI(new L2BoatAI(new AIAccessor()));
	}
	
	@Override
	public boolean isFlying()
	{
		return true;
	}
	
	public boolean isBoat()
	{
		return false;
	}
	
	public boolean canBeControlled()
	{
		return _engine == null;
	}
	
	public void registerEngine(Runnable r)
	{
		_engine = r;
	}
	
	public void runEngine(int delay)
	{
		if (_engine != null)
			ThreadPoolManager.getInstance().scheduleGeneral(_engine, delay);
	}
	
	private int _moveSpeed = 0;
	private int _rotationSpeed = 0;
	
    @Override
    public void setXYZ(int x, int y, int z)
    {
    	super.setXYZ(x, y, z);
    	updatePeopleInTheBoat(x, y, z);
    }
    
	@Override
	public int getMoveSpeed()
	{
		return _moveSpeed;
	}
	
	public final void setMoveSpeed(int speed)
	{
		_moveSpeed = speed;
	}
	
	public final int getRotationSpeed()
	{
		return _rotationSpeed;
	}
	
	public final void setRotationSpeed(int speed)
	{
		_rotationSpeed = speed;
	}
	
	public void executePath(VehiclePathPoint[] path)
	{
		_runState = 0;
		_currentPath = path;
		
		if (_currentPath != null && _currentPath.length > 0)
		{
			final VehiclePathPoint point = _currentPath[0];
			
			if (point.getMoveSpeed() > 0)
				getStat().setMoveSpeed(point.getMoveSpeed());
			if (point.getRotationSpeed() > 0)
				getStat().setRotationSpeed(point.getRotationSpeed());
			
			getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new L2CharPosition(point.getX(), point.getY(), point.getZ(), 0));
			return;
		}
		getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
	}
	
	@Override
	public boolean moveToNextRoutePoint()
	{
		_move = null;
		
		if (_currentPath != null)
		{
			_runState++;
			if (_runState < _currentPath.length)
			{
				final VehiclePathPoint point = _currentPath[_runState];
				if (!isMovementDisabled())
				{
					if (point.getMoveSpeed() == 0)
					{
						teleToLocation(point.getX(), point.getY(), point.getZ(), false);
						_currentPath = null;
					}
					else
					{
						if (point.getMoveSpeed() > 0)
							getStat().setMoveSpeed(point.getMoveSpeed());
						if (point.getRotationSpeed() > 0)
							getStat().setRotationSpeed(point.getRotationSpeed());
						
						MoveData m = new MoveData();
						m.disregardingGeodata = false;
						m.onGeodataPathIndex = -1;
						m._xDestination = point.getX();
						m._yDestination = point.getY();
						m._zDestination = point.getZ();
						m._heading = 0;
						
						final double dx = point.getX() - getX();
						final double dy = point.getY() - getY();
						final double distance = Math.sqrt(dx * dx + dy * dy);
						
						if (distance > 1) // vertical movement heading check
							setHeading(Util.calculateHeadingFrom(getX(), getY(), point.getX(), point.getY()));
						
						m._moveStartTime = GameTimeController.getInstance().getGameTicks();
						_move = m;
						
						GameTimeController.getInstance().registerMovingObject(this);
						broadcastPacket(new VehicleDeparture(this));
						return true;
					}
				}
			}
			else
				_currentPath = null;
		}
		
		runEngine(10);
		return false;
	}

	@Override
	public final VehicleStat getStat()
	{
		if (super.getStat() == null || !(super.getStat() instanceof VehicleStat))
		{
			setStat(new VehicleStat(this));
		}
		return (VehicleStat) super.getStat();
	}

	public boolean isInDock()
	{
		return _dockId > 0;
	}
	
	public int getDockId()
	{
		return _dockId;
	}
	
	public void setInDock(int d)
	{
		_dockId = d;
	}
	
	public void oustPlayers()
	{
		// Use iterator because oustPlayer will try to remove player from _passengers
		final Iterator<L2PcInstance> iter = _passengers.iterator();
		while (iter.hasNext())
		{
			L2PcInstance player = iter.next();
			if (player != null)
				oustPlayer(player);
			
			iter.remove();
		}
	}
	
	public void oustPlayer(L2PcInstance player)
	{
		player.setVehicle(null);
		player.setInVehiclePosition(null);
		removePassenger(player);
		
		player.setInsideZone(ZoneId.PEACE, false);
		player.sendPacket(SystemMessageId.EXIT_PEACEFUL_ZONE);
		
		final Location loc = MapRegionTable.getInstance().getTeleToLocation(this, MapRegionTable.TeleportWhereType.TOWN);
		
		if (player.isOnline()==1)
			player.teleToLocation(loc.getX(), loc.getY(), loc.getZ(), false);
		else
			player.setXYZInvisible(loc.getX(), loc.getY(), loc.getZ()); // disconnects handling
	}
	
	public boolean addPassenger(L2PcInstance player)
	{
		if (player == null || _passengers.contains(player))
			return false;
		
		// already in other vehicle
		if (player.getVehicle() != null && player.getVehicle() != this)
			return false;
		
		_passengers.add(player);
		
		player.setInsideZone(ZoneId.PEACE, true);
		player.sendPacket(SystemMessageId.ENTER_PEACEFUL_ZONE);
		
		return true;
	}
	
	public void removePassenger(L2PcInstance player)
	{
		
		try
		{
			_passengers.remove(player);
		}
		catch (Exception e)
		{
		}
	}
	
	public boolean isEmpty()
	{
		return _passengers.isEmpty();
	}
	
	public List<L2PcInstance> getPassengers()
	{
		return _passengers;
	}
	
	public void broadcastToPassengers(L2GameServerPacket sm)
	{
		for (L2PcInstance player : _passengers)
		{
			if (player != null)
				player.sendPacket(sm);
		}
	}
	
	public void payForRide(int itemId, int count, int oustX, int oustY, int oustZ)
	{
		L2World.getInstance().forEachVisibleObjectInRange(this, L2PcInstance.class, 1000, player ->
		{
			if (player.isInBoat() && player.getBoat() == this)
			{
				if (itemId > 0)
				{
					final L2ItemInstance ticket = player.getInventory().getItemByItemId(itemId);
					
					if (ticket == null || player.getInventory().destroyItem("Boat", ticket.getItemId(), count, player, this) == null)
					{
						player.teleToLocation(oustX, oustY, oustZ, true);
						player.sendPacket(SystemMessageId.NOT_CORRECT_BOAT_TICKET);
						return;
					}
					
					final InventoryUpdate iu = new InventoryUpdate();
					if (ticket.getCount() == 0)
						iu.addRemovedItem(ticket);
					else
						iu.addModifiedItem(ticket);
					
					player.sendPacket(iu);
					
					if (count > 1)
						player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.S2_S1_DISAPPEARED).addItemName(itemId).addItemNumber(count));
					else
						player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.S1_DISAPPEARED).addItemName(itemId));

				}
				addPassenger(player);
			}
		});
	}
	
	@Override
	public boolean updatePosition()
	{
		final boolean result = super.updatePosition();
		
		for (L2PcInstance player : _passengers)
		{
			if (player != null && player.getVehicle() == this)
			{
				player.setXYZ(getX(), getY(), getZ());
				
				if(player.getPet()!=null)
					player.getPet().setXYZ(getX(), getY(), getZ());

				player.revalidateZone(false);
			}
		}
		return result;
	}
	
	@Override
	public void teleToLocation(int x, int y, int z, boolean randomOffset)
	{
		if (isMoving())
			stopMove(null);
		
		setIsTeleporting(true);
		
		getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
		
		for (L2PcInstance player : _passengers)
		{
			if (player != null)
				player.teleToLocation(x, y, z, randomOffset);
		}
		
		decayMe();
		setXYZ(x, y, z);
		
		onTeleported();
		revalidateZone(true);
	}
	
	@Override
	public void stopMove(L2CharPosition pos)
	{
		_move = null;
		if (pos != null)
		{
			setXYZ(pos.x, pos.y, pos.z);
			setHeading(pos.heading);
			revalidateZone(true);
		}
		
		broadcastPacket(new VehicleStarted(this, 0));
		broadcastPacket(new VehicleInfo(this));
	}
	
	@Override
	public void deleteMe()
	{
		_engine = null;
		
		if (isMoving())
			stopMove(null);
		
		oustPlayers();
		
		final ZoneRegion oldZoneRegion = ZoneManager.getInstance().getRegion(this);
		
		decayMe();
		
		oldZoneRegion.removeFromZones(this);
		
		super.deleteMe();
	}
	
	@Override
	public void updateAbnormalEffect()
	{
	}
	
	@Override
	public L2ItemInstance getActiveWeaponInstance()
	{
		return null;
	}
	
	@Override
	public L2Weapon getActiveWeaponItem()
	{
		return null;
	}
	
	@Override
	public L2ItemInstance getSecondaryWeaponInstance()
	{
		return null;
	}
	
	@Override
	public L2Weapon getSecondaryWeaponItem()
	{
		return null;
	}
	
	@Override
	public int getLevel()
	{
		return 0;
	}
	
	@Override
	public boolean isAutoAttackable(L2Character attacker)
	{
		return false;
	}
	
	@Override
	public void setAI(L2CharacterAI newAI)
	{
		if (_ai == null)
			_ai = newAI;
	}

	public class AIAccessor extends L2Character.AIAccessor
	{
		public AIAccessor()
		{
		}
		
		@Override
		public void detachAI()
		{
			
		}
		
		public L2Vehicle getVehicle()
		{
			return L2Vehicle.this;
		}
		
	}	
	
	protected void updatePeopleInTheBoat(int x, int y, int z)
	{
		for(L2PcInstance player : _passengers)
		{
			if (player != null && player.getVehicle() == this)
			{
			    player.setXYZ(x, y, z);
				player.revalidateZone(false);
				
				if(player.getPet()!=null)
				{
					player.getPet().setXYZ(getX(), getY(), getZ());
					player.getPet().revalidateZone(false);
				}
			}
		}
		
		broadcastToPassengers(new OnVehicleCheckLocation(this));
	}
	
	@Override
	public void sendInfo(L2PcInstance activeChar)
	{
		activeChar.sendPacket(new VehicleInfo(this));
	}
	
}