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

import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.model.L2Character;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.ActionFailed;

public final class Action extends L2GameClientPacket
{
	private static Logger _log = Logger.getLogger(Action.class.getName());
	private static final String ACTION__C__04 = "[C] 04 Action";

	// cddddc
	private int _objectId;
	private int _originX;
	private int _originY;
	private int _originZ;
	private int _actionId;

	@Override
	protected void readImpl()
	{
		_objectId = readD(); // Target object Identifier
		_originX = readD();
		_originY = readD();
		_originZ = readD();
		_actionId = readC(); // Action identifier : 0-Simple click, 1-Shift click
	}

	@Override
	protected void runImpl()
	{
		if (Config.DEBUG)
		{
			_log.fine("Action:" + _actionId);
			_log.fine("oid:" + _objectId);
			_log.fine("x,y,z :" + _originX + ", " + _originY + ", " + _originZ);
		}

		// Get the current L2PcInstance of the player
		L2PcInstance activeChar = getClient().getActiveChar();

		if (activeChar == null)
			return;

		L2Object obj;

		if (activeChar.getTargetId() == _objectId)
			obj = activeChar.getTarget();
		else
			obj = L2World.findObject(_objectId);

		// If object requested does not exist, add warn msg into logs
		if (obj == null)
		{
			// pressing e.g. pickup many times quickly would get you here
			getClient().sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}

		// Check if the target is valid, if the player haven't a shop or isn't the requester of a transaction (ex : FriendInvite, JoinAlly, JoinParty...)
		if ((activeChar.getPrivateStoreType() == 0) && (activeChar.getActiveRequester() == null))
		{
			switch (_actionId)
			{
				case 0:
					obj.onAction(activeChar);
				break;
				case 1:
					if (obj instanceof L2Character && ((L2Character) obj).isAlikeDead())
						obj.onAction(activeChar);
					else
						obj.onActionShift(getClient());
				break;
				default:
					// Invalid action detected (probably client cheating), log this
					_log.warning("Character: " + activeChar.getName() + " requested invalid action: " + _actionId);
					getClient().sendPacket(ActionFailed.STATIC_PACKET);
				break;
			}
		}
		else
			// Actions prohibited when in trade
			getClient().sendPacket(ActionFailed.STATIC_PACKET);
	}

	@Override
	public String getType()
	{
		return ACTION__C__04;
	}
}