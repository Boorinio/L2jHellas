/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jhellas.gameserver.network.clientpackets;


import com.l2jhellas.Config;
import com.l2jhellas.gameserver.TaskPriority;
import com.l2jhellas.gameserver.ai.CtrlIntention;
import com.l2jhellas.gameserver.datatables.xml.MapRegionTable;
import com.l2jhellas.gameserver.geodata.GeoEngine;
import com.l2jhellas.gameserver.geodata.geoeditorcon.GeoEditorListener;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.zone.ZoneId;
import com.l2jhellas.gameserver.network.serverpackets.CharMoveToLocation;
import com.l2jhellas.gameserver.network.serverpackets.GetOnVehicle;
import com.l2jhellas.gameserver.network.serverpackets.PartyMemberPosition;
import com.l2jhellas.gameserver.network.serverpackets.ValidateLocation;

public class ValidatePosition extends L2GameClientPacket
{
	private static final String _C__48_VALIDATEPOSITION = "[C] 48 ValidatePosition";

	public TaskPriority getPriority() 
	{ 
		return TaskPriority.PR_HIGH; 
	}

	private int _x;
	private int _y;
	private int _z;
	private int _heading;
	private int _boatObjId;

	@Override
	protected void readImpl()
	{
		_x  = readD();
		_y  = readD();
		_z  = readD();
		_heading  = readD();
		_boatObjId  = readD();
	}

	@Override
	protected void runImpl()
	{
		final L2PcInstance activeChar = getClient().getActiveChar();
		
		if (activeChar == null || activeChar.isTeleporting() || activeChar.inObserverMode()) 
			return;

		if ((activeChar.getX() == 0) && (activeChar.getY() == 0) && (activeChar.getZ() == 0))
		{
			tsekarepos(activeChar);
			return;
		}

		
		if (Config.COORD_SYNCHRONIZE > 0)
		{
			activeChar.setClientX(_x);
			activeChar.setClientY(_y);
			activeChar.setClientZ(_z);
			activeChar.setClientHeading(_heading);
			int realX = activeChar.getX();
			int realY = activeChar.getY();
		
			
			//int realZ = activeChar.getZ();
			double dx = _x - realX;
			double dy = _y - realY;
			double diffSq = Math.sqrt(dx*dx + dy*dy);
			//double dz = realZ - _z;

	        if(!activeChar.isFlying() && !activeChar.isInsideZone(ZoneId.WATER))
            {
	
	        	//temporary fix for -> (((if))) holes found.
				if (_z < -15000 || _z > 15000)
				{
					activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
					activeChar.setTarget(activeChar);
					tsekarepos(activeChar);
				}
				
				if (activeChar.isFalling(_z))
					return;
            }

			if (diffSq > 0 && diffSq < 1000) // if too large, messes observation
			{
				if ((Config.COORD_SYNCHRONIZE & 1) == 1 && (!activeChar.isMoving() || !activeChar.validateMovementHeading(_heading))) // Heading changed on client = possible obstacle
				{
					if (Config.DEVELOPER) System.out.println(activeChar.getName() + ": Synchronizing position Client --> Server" + (activeChar.isMoving()?" (collision)":" (stay sync)"));
					if (diffSq < 50) // 50*50 - attack won't work fluently if even small differences are corrected
					{
						activeChar.setXYZ(realX, realY, _z);
					}
					else
					{
						activeChar.setXYZ(_x, _y, _z);
					}
					activeChar.setHeading(_heading);
				}
				else if ((Config.COORD_SYNCHRONIZE & 2) == 2 && diffSq > activeChar.getStat().getMoveSpeed()) // more than can be considered to be result of latency
				{
					if (Config.DEVELOPER)
						System.out.println(activeChar.getName() + ": Synchronizing position Server --> Client");
					

					if (activeChar.isInBoat())
					{
						activeChar.setHeading(_heading);
						activeChar.sendPacket(new GetOnVehicle(activeChar.getObjectId(),_boatObjId, activeChar.getInBoatPosition()));
						return;
					}	
					
					activeChar.broadcastPacket(new CharMoveToLocation(activeChar));
				}
			}
			activeChar.setLastClientPosition(_x, _y, _z);
			activeChar.setLastServerPosition(activeChar.getX(), activeChar.getY(), activeChar.getZ());
		}
		else if (Config.COORD_SYNCHRONIZE == -1)
		{
			

			activeChar.setClientX(_x);
			activeChar.setClientY(_y);
			activeChar.setClientZ(_z);
			activeChar.setClientHeading(_heading); // No real need to validate heading.
			int realX = activeChar.getX();
			int realY = activeChar.getY();
			//int realZ = activeChar.getZ();
			double dx = _x - realX;
			double dy = _y - realY;
			double diffSq = (dx*dx + dy*dy);
			
	        if(!activeChar.isFlying() && !activeChar.isInsideZone(ZoneId.WATER))
            {
	        	//temporary fix for -> (((if))) holes found.
				if (_z < -15000 || _z > 15000)
				{
					activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
					activeChar.setTarget(activeChar);
					tsekarepos(activeChar);
				}
				
				if (activeChar.isFalling(_z))
					return;
            }
	        
			if (diffSq < 250000)
			{
				activeChar.setXYZ(realX,realY,_z);
			}
			if (diffSq > 1000)
			{
				if (activeChar.isInBoat())
				{
					activeChar.setHeading(_heading);
					activeChar.sendPacket(new GetOnVehicle(activeChar.getObjectId(),_boatObjId, activeChar.getInBoatPosition()));
					return;
				}	
				
		        activeChar.broadcastPacket(new CharMoveToLocation(activeChar));
				
			}
		}

	    if (activeChar.getParty() != null)
			activeChar.getParty().broadcastToPartyMembers(activeChar, new PartyMemberPosition(activeChar.getParty()));

		if (Config.ACCEPT_GEOEDITOR_CONN)
		{
			if (GeoEditorListener.getInstance().getThread() != null  && GeoEditorListener.getInstance().getThread().isWorking()  && GeoEditorListener.getInstance().getThread().isSend(activeChar))
			{
				GeoEditorListener.getInstance().getThread().sendGmPosition(_x,_y,(short)_z);
			}
		}
	}

	@Override
	public String getType()
	{
		return _C__48_VALIDATEPOSITION;
	}

	@Deprecated
	public boolean equal(ValidatePosition pos)
	{
		return _x == pos._x && _y == pos._y && _z == pos._z && _heading == pos._heading;
	}

	//temporary fix for -> (((if))) holes found.
	private void tsekarepos(L2PcInstance activeChar)
	{
		int realX = activeChar.getX();
		int realY = activeChar.getY();
		int realZ = activeChar.getZ();
		if (realX != 0 && realY != 0 && realZ != 0)
		{
			if (GeoEngine.getNSWE(activeChar.getX(), activeChar.getY(), activeChar.getZ()) == 15)
			{
				activeChar.setXYZ(realX, realY, GeoEngine.getHeight(realX, realY, realZ));
				activeChar.broadcastPacket(new ValidateLocation(activeChar));
			}
			else
			{
				activeChar.teleToLocation(MapRegionTable.TeleportWhereType.TOWN);
			}
		}
		else if (activeChar.getClientX() != 0 && activeChar.getClientY() != 0 && activeChar.getClientZ() != 0)
		{
			if (GeoEngine.getNSWE(activeChar.getClientX(), activeChar.getClientY(), activeChar.getClientZ()) == 15)
			{
				activeChar.setXYZ(activeChar.getClientX(), activeChar.getClientY(), GeoEngine.getHeight(activeChar.getClientX(), activeChar.getClientY(), activeChar.getClientZ()));
				activeChar.broadcastPacket(new ValidateLocation(activeChar));
			}
			else
			{
				activeChar.teleToLocation(MapRegionTable.TeleportWhereType.TOWN);
			}
		}
		else
		{
			activeChar.teleToLocation(MapRegionTable.TeleportWhereType.TOWN);
		}
	}
}