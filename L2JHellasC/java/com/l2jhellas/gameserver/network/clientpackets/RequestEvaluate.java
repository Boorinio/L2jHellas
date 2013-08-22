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

import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;
import com.l2jhellas.gameserver.network.serverpackets.UserInfo;

public final class RequestEvaluate extends L2GameClientPacket
{
	private static final String _C__B9_REQUESTEVALUATE = "[C] B9 RequestEvaluate";

	@SuppressWarnings("unused")
	private int _targetId;

	@Override
	protected void readImpl()
	{
		_targetId = readD();
	}

	@Override
	protected void runImpl()
	{
		SystemMessage sm;
		L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar == null)
			return;

		if (!(activeChar.getTarget() instanceof L2PcInstance))
		{
			sm = SystemMessage.getSystemMessage(SystemMessageId.TARGET_IS_INCORRECT);
			activeChar.sendPacket(sm);
			sm = null;
			return;
		}

		if (activeChar.getLevel() < 10)
		{
			sm = SystemMessage.getSystemMessage(SystemMessageId.ONLY_LEVEL_SUP_10_CAN_RECOMMEND);
			activeChar.sendPacket(sm);
			sm = null;
			return;
		}

		if (activeChar.getTarget() == activeChar)
		{
			sm = SystemMessage.getSystemMessage(SystemMessageId.YOU_CANNOT_RECOMMEND_YOURSELF);
			activeChar.sendPacket(sm);
			sm = null;
			return;
		}

		if (activeChar.getRecomLeft() <= 0)
		{
			sm = SystemMessage.getSystemMessage(SystemMessageId.NO_MORE_RECOMMENDATIONS_TO_HAVE);
			activeChar.sendPacket(sm);
			sm = null;
			return;
		}

		L2PcInstance target = (L2PcInstance) activeChar.getTarget();

		if (target.getRecomHave() >= 255)
		{
			sm = SystemMessage.getSystemMessage(SystemMessageId.YOUR_TARGET_NO_LONGER_RECEIVE_A_RECOMMENDATION);
			activeChar.sendPacket(sm);
			sm = null;
			return;
		}

		if (!activeChar.canRecom(target))
		{
			sm = SystemMessage.getSystemMessage(SystemMessageId.THAT_CHARACTER_IS_RECOMMENDED);
			activeChar.sendPacket(sm);
			sm = null;
			return;
		}

		activeChar.giveRecom(target);

		sm = SystemMessage.getSystemMessage(SystemMessageId.YOU_HAVE_RECOMMENDED_S1_YOU_HAVE_S2_RECOMMENDATIONS_LEFT);
		sm.addString(target.getName());
		sm.addNumber(activeChar.getRecomLeft());
		activeChar.sendPacket(sm);

		sm = SystemMessage.getSystemMessage(SystemMessageId.YOU_HAVE_BEEN_RECOMMENDED_BY_S1);
		sm.addString(activeChar.getName());
		target.sendPacket(sm);
		sm = null;

		activeChar.sendPacket(new UserInfo(activeChar));
		target.broadcastUserInfo();
	}

	@Override
	public String getType()
	{
		return _C__B9_REQUESTEVALUATE;
	}
}