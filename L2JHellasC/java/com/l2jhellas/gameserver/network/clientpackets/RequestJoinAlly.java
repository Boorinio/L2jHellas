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

import com.l2jhellas.gameserver.model.L2Clan;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.AskJoinAlly;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;

public final class RequestJoinAlly extends L2GameClientPacket
{
	private static final String _C__82_REQUESTJOINALLY = "[C] 82 RequestJoinAlly";

	private int _id;

	@Override
	protected void readImpl()
	{
		_id = readD();
	}

	@Override
	protected void runImpl()
	{
		final L2PcInstance activeChar = getClient().getActiveChar();
		
		if (activeChar == null)
			return;
		
		if (activeChar.getClan() == null)
		{
			activeChar.sendPacket(SystemMessageId.YOU_ARE_NOT_A_CLAN_MEMBER);
			return;
		}
		
		final L2PcInstance target = L2World.getInstance().getPlayer(_id);
		
		if(target==null)
		{
			activeChar.sendPacket(SystemMessageId.TARGET_CANT_FOUND);
			return;
		}
		
		final L2Clan clan = activeChar.getClan();
		
		if (!clan.checkAllyJoinCondition(activeChar, target))
			return;
		if (!activeChar.getRequest().setRequest(target, this))
			return;

		SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S2_ALLIANCE_LEADER_OF_S1_REQUESTED_ALLIANCE);
		sm.addString(activeChar.getClan().getAllyName());
		sm.addString(activeChar.getName());
		target.sendPacket(sm);
		sm = null;
		AskJoinAlly aja = new AskJoinAlly(activeChar.getObjectId(), activeChar.getClan().getAllyName());
		target.sendPacket(aja);
		return;
	}

	@Override
	public String getType()
	{
		return _C__82_REQUESTJOINALLY;
	}
}