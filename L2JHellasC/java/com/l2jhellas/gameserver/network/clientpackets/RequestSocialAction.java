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
import com.l2jhellas.gameserver.ai.CtrlIntention;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.util.Util;

public class RequestSocialAction extends L2GameClientPacket
{
	// private static Logger _log = Logger.getLogger(RequestSocialAction.class.getName());
	private static final String _C__1B_REQUESTSOCIALACTION = "[C] 1B RequestSocialAction";

	// format cd
	private int _actionId;

	@Override
	protected void readImpl()
	{
		_actionId = readD();
	}

	@Override
	protected void runImpl()
	{
		final L2PcInstance activeChar = getClient().getActiveChar();
		
		if (activeChar == null)
			return;

		// Flood protect
		if (!activeChar.getAntiFlood().getSocial().tryPerformAction("Social"))
			return;
		
		// You cannot do anything else while fishing
		if (activeChar.isFishing())
		{
			activeChar.sendPacket(SystemMessageId.CANNOT_DO_WHILE_FISHING_3);
			return;
		}
		
		// check if its the actionId is allowed
		if (_actionId < 2 || _actionId > 13)
		{
			Util.handleIllegalPlayerAction(activeChar, "Warning!! Character " + activeChar.getName() + " of account " + activeChar.getAccountName() + " requested an internal Social Action.", Config.DEFAULT_PUNISH);
			return;
		}

		if (activeChar.isInStoreMode() || activeChar.getActiveRequester() != null || activeChar.isAlikeDead() || activeChar.getAI().getIntention() != CtrlIntention.AI_INTENTION_ACTIVE)
			return;

		activeChar.broadcastSocialActionInRadius(_actionId);

	}

	@Override
	public String getType()
	{
		return _C__1B_REQUESTSOCIALACTION;
	}
}