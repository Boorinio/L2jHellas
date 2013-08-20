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

import java.nio.BufferUnderflowException;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.TaskPriority;
import com.l2jhellas.gameserver.ai.CtrlIntention;
import com.l2jhellas.gameserver.model.L2CharPosition;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.ActionFailed;
import com.l2jhellas.gameserver.network.serverpackets.EnchantResult;
import com.l2jhellas.gameserver.network.serverpackets.PartyMemberPosition;
import com.l2jhellas.gameserver.network.serverpackets.StopMove;
import com.l2jhellas.gameserver.templates.L2WeaponType;
import com.l2jhellas.util.IllegalPlayerAction;
import com.l2jhellas.util.Util;

@SuppressWarnings("unused")
public class MoveBackwardToLocation extends L2GameClientPacket
{
	// cdddddd
	private int _targetX;
	private int _targetY;
	private int _targetZ;
	private int _originX;
	private int _originY;
	private int _originZ;
	private int _moveMovement;

	// For geodata
	private int _curX;
	private int _curY;
	private int _curZ;

	public TaskPriority getPriority()
	{
		return TaskPriority.PR_HIGH;
	}

	private static final String _C__01_MOVEBACKWARDTOLOC = "[C] 01 MoveBackwardToLoc";

	@Override
	protected void readImpl()
	{
		_targetX = readD();
		_targetY = readD();
		_targetZ = readD();
		_originX = readD();
		_originY = readD();
		_originZ = readD();
		
		if(_buf.hasRemaining())
		try
		{
			_moveMovement = readD(); // is 0 if cursor keys are used 1 if mouse is used
		}
		catch (BufferUnderflowException e)
		{
			if (Config.KICK_L2WALKER)
			{
				L2PcInstance activeChar = getClient().getActiveChar();
				activeChar.sendPacket(SystemMessageId.HACKING_TOOL);
				activeChar.sendPacket(ActionFailed.STATIC_PACKET);
				try
				{
					Thread.sleep(5000);
				}
				catch (InterruptedException ie)
				{
				}
				finally
				{
					Util.handleIllegalPlayerAction(activeChar, "Player " + activeChar.getName() + " trying to use L2Walker!", IllegalPlayerAction.PUNISH_KICK);
					activeChar.closeNetConnection();
				}
			}
		}
	}

	@Override
	protected void runImpl()
	{
		L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar == null)
			return;

		// Like L2OFF movements prohibited when char is sitting
        if (activeChar.isSitting())
        {
            getClient().sendPacket(ActionFailed.STATIC_PACKET);
            return;
        }
        
        // Like L2OFF movements prohibited when char is teleporting
        if (activeChar.isTeleporting())
        {
        	activeChar.sendPacket(ActionFailed.STATIC_PACKET);
            return;
        }
        
        // Like L2OFF the enchant window will close
        if (activeChar.getActiveEnchantItem() != null)
        {
            activeChar.sendPacket(new EnchantResult(0));
            activeChar.setActiveEnchantItem(null);
        }
        
        if (_targetX == _originX && _targetY == _originY && _targetZ == _originZ)
        {
            activeChar.sendPacket(new StopMove(activeChar));
            return;
        }
        
		_curX = activeChar.getX();
		_curY = activeChar.getY();
		_curZ = activeChar.getZ();

		if (activeChar.isInBoat())
		{
			activeChar.setInBoat(false);
		}
		
		if (activeChar.getTeleMode() > 0)
		{
			if (activeChar.getTeleMode() == 1)
				activeChar.setTeleMode(0);
			activeChar.sendPacket(ActionFailed.STATIC_PACKET);
			activeChar.teleToLocation(_targetX, _targetY, _targetZ, false);
			return;
		}

		if (activeChar.getActiveEnchantItem() != null)
		{
			activeChar.setActiveEnchantItem(null);
			activeChar.sendPacket(EnchantResult.CANCELLED);
			activeChar.sendPacket(SystemMessageId.ENCHANT_SCROLL_CANCELLED);
		}

		if (_moveMovement == 0 && Config.GEODATA < 1) // cursor movement without geodata is disabled
		{
			activeChar.sendPacket(ActionFailed.STATIC_PACKET);
		}
		else if (activeChar.isAttackingNow() && activeChar.getActiveWeaponItem() != null && (activeChar.getActiveWeaponItem().getItemType() == L2WeaponType.BOW))
		{
			activeChar.sendPacket(ActionFailed.STATIC_PACKET);
		}
		else
		{
			double dx = _targetX - _curX;
			double dy = _targetY - _curY;
			// Can't move if character is confused, or trying to move a huge distance
			if (activeChar.isOutOfControl() || ((dx * dx + dy * dy) > 98010000)) // 9900*9900
			{
				activeChar.sendPacket(ActionFailed.STATIC_PACKET);
				return;
			}
			
			// This is to avoid exploit with Hit + Fast movement
            if((activeChar.isMoving() && activeChar.isAttackingNow()))
            {
                activeChar.sendPacket(ActionFailed.STATIC_PACKET);
                return;
            }
            
			activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new L2CharPosition(_targetX, _targetY, _targetZ, 0));

			if (activeChar.getParty() != null)
				activeChar.getParty().broadcastToPartyMembers(activeChar, new PartyMemberPosition(activeChar));
			
			activeChar.sendPacket(ActionFailed.STATIC_PACKET);
		}
	}

	@Override
	public String getType()
	{
		return _C__01_MOVEBACKWARDTOLOC;
	}
}