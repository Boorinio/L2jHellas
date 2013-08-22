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
import com.l2jhellas.gameserver.model.actor.instance.L2EventManagerInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;

/**
 * @author Dezmond_snz
 *         Format: cddd
 */
public final class DlgAnswer extends L2GameClientPacket
{
	private static Logger _log = Logger.getLogger(DlgAnswer.class.getName());
	private static final String _C__C5_DLGANSWER = "[C] C5 DlgAnswer";

	private int _messageId;
	private int _answer, _unk;

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
		if (Config.DEBUG)
			_log.fine(getType() + ": Answer acepted. Message ID " + _messageId + ", asnwer " + _answer + ", unknown field " + _unk);

		if (_messageId == SystemMessageId.RESSURECTION_REQUEST_BY_S1.getId())
			getClient().getActiveChar().reviveAnswer(_answer);
		else if (_messageId == 614 && getClient().getActiveChar().awaitingAnswer && Config.MOD_ALLOW_WEDDING)
		{
			getClient().getActiveChar().EngageAnswer(_answer);
			getClient().getActiveChar().awaitingAnswer = false;
		}
		else if (_messageId == 614 && L2EventManagerInstance._awaitingplayers.contains(getClient().getActiveChar()))
		{
			getClient().getActiveChar().setRaidAnswear(_answer);
			L2EventManagerInstance._awaitingplayers.remove(getClient().getActiveChar());
		}
	}

	@Override
	public String getType()
	{
		return _C__C5_DLGANSWER;
	}
}