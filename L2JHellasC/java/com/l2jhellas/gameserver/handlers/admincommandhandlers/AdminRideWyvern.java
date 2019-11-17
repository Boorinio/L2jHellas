package com.l2jhellas.gameserver.handlers.admincommandhandlers;

import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.Ride;
import com.l2jhellas.gameserver.network.serverpackets.SetupGauge;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;

public class AdminRideWyvern implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_ride_wyvern",
		"admin_ride_strider",
		"admin_unride_wyvern",
		"admin_unride_strider",
		"admin_unride"
	};
	private int _petRideId;
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_ride"))
		{
			if (activeChar.isMounted() || activeChar.getPet() != null)
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_S2);
				sm.addString("Already Have a Pet or Mounted.");
				activeChar.sendPacket(sm);
				return false;
			}
			if (command.startsWith("admin_ride_wyvern"))
				_petRideId = 12621;
			else if (command.startsWith("admin_ride_strider"))
				_petRideId = 12526;
			else
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_S2);
				sm.addString("Command '" + command + "' not recognized");
				activeChar.sendPacket(sm);
				return false;
			}
			
			if (activeChar.isMounted())
			{
				final Ride dismount = new Ride(activeChar.getObjectId(), Ride.ACTION_DISMOUNT, 0);
				activeChar.sendPacket(new SetupGauge(3, 0, 0));
				activeChar.setMountType(0);
				activeChar.setMountObjectID(0);
				activeChar.broadcastPacket(dismount);
				activeChar.broadcastUserInfo();
			}
			
			if (activeChar.getPet() != null)
				activeChar.getPet().unSummon(activeChar);
			
			if (!activeChar.disarmWeapons())
				return false;
			
			final Ride RideMount = new Ride(activeChar.getObjectId(), Ride.ACTION_MOUNT, _petRideId);
			activeChar.sendPacket(RideMount);
			activeChar.broadcastPacket(RideMount);
			activeChar.setMountType(RideMount.getMountType());
			activeChar.setMountObjectID(_petRideId);
			activeChar.broadcastUserInfo();
		}
		else if (command.equals("admin_unride"))
		{
			final Ride dismount = new Ride(activeChar.getObjectId(), Ride.ACTION_DISMOUNT, 0);
			activeChar.sendPacket(new SetupGauge(3, 0, 0));
			activeChar.setMountType(0);
			activeChar.setMountObjectID(0);
			activeChar.broadcastPacket(dismount);
			activeChar.broadcastUserInfo();
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}