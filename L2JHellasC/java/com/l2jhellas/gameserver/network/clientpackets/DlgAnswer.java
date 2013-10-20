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
import com.l2jhellas.gameserver.handler.admincommandhandlers.AdminRecallAll;
import com.l2jhellas.gameserver.model.actor.instance.L2EventManagerInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;

/**
 * @author Dezmond_snz
 *         Format: cddd
 */
public final class DlgAnswer extends L2GameClientPacket
{
	private static Logger _log = Logger.getLogger(DlgAnswer.class.getName());
	private static final String _C__C5_DLGANSWER = "[C] C5 DlgAnswer";
	private int _messageId, _answer, _unk;

	@Override
	protected void readImpl()
	{
		_messageId = readD();
		_answer = readD();
		_unk = readD();
	}

	@Override
	public void runImpl()
	{
		final L2PcInstance player = getClient().getActiveChar();
		if (Config.DEBUG)
			_log.fine(getType() + ": Answer acepted. Message ID " + _messageId + ", asnwer " + _answer + ", unknown field " + _unk);

		if (_messageId == SystemMessageId.RESSURECTION_REQUEST_BY_S1.getId())
			player.reviveAnswer(_answer);
		else if (_messageId == SystemMessageId.S1_WISHES_TO_SUMMON_YOU_FROM_S2_DO_YOU_ACCEPT.getId())
			if(AdminRecallAll.isAdminSummoning == true && _answer==1)
				player.teleToLocation(AdminRecallAll.x, AdminRecallAll.y, AdminRecallAll.z, false);
			else
				player.teleportAnswer(_answer, _unk);
		else if (_messageId == SystemMessageId.WOULD_YOU_LIKE_TO_OPEN_THE_GATE.getId())
			player.gatesAnswer(_answer, 1);
		else if (_messageId == SystemMessageId.WOULD_YOU_LIKE_TO_CLOSE_THE_GATE.getId())
			player.gatesAnswer(_answer, 0);
		else if (_messageId == 614 && player.awaitingAnswer && Config.MOD_ALLOW_WEDDING)
		{
			player.EngageAnswer(_answer);
			player.awaitingAnswer = false;
		}
		else if (_messageId == 614 && L2EventManagerInstance._awaitingplayers.contains(player))
		{
			player.setRaidAnswear(_answer);
			L2EventManagerInstance._awaitingplayers.remove(player);
		}
	}

	@Override
	public String getType()
	{
		return _C__C5_DLGANSWER;
	}
}