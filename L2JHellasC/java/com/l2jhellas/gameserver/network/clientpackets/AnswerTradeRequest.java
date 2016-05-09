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
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.ActionFailed;
import com.l2jhellas.gameserver.network.serverpackets.SendTradeDone;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;

public final class AnswerTradeRequest extends L2GameClientPacket
{
	private static final String _C__40_ANSWERTRADEREQUEST = "[C] 40 AnswerTradeRequest";

	private int _response;

	@Override
	protected void readImpl()
	{
		_response = readD();
	}

	@Override
	protected void runImpl()
	{
		L2PcInstance player = getClient().getActiveChar();
		L2PcInstance player2 = player.getActiveRequester();
		if (player == null || player2 == null)
			return;
		if (!player.getAccessLevel().allowTransaction())
		{
			player.sendMessage("Transactions are disabled for your Access Level.");
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		// MODS Faction Good vs Evil
		if (Config.MOD_GVE_ENABLE_FACTION && player2.isevil() && player.isgood())
		{
			player.sendMessage("You can't trade with different Faction.");
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		if (player2.isgood() && player.isevil() && Config.MOD_GVE_ENABLE_FACTION)
		{
			player.sendMessage("You can't trade with different Faction.");
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}

		L2PcInstance partner = player.getActiveRequester();
		if ((partner == null) || (L2World.getInstance().findObject(partner.getObjectId()) == null))
		{
			// Trade partner not found, cancel trade
			player.sendPacket(new SendTradeDone(0));
			SystemMessage msg = SystemMessage.getSystemMessage(SystemMessageId.TARGET_IS_NOT_FOUND_IN_THE_GAME);
			player.sendPacket(msg);
			player.setActiveRequester(null);
			player.setAllowTrade(true);
			partner.setAllowTrade(true);
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}

		if ((_response == 1) && !partner.isRequestExpired())
		{
			player.startTrade(partner);
			partner.setAllowTrade(true);
			player.setAllowTrade(true);
		}
		else
		{
			SystemMessage msg = SystemMessage.getSystemMessage(SystemMessageId.S1_DENIED_TRADE_REQUEST);
			msg.addString(player.getName());
			partner.sendPacket(msg);
			player.sendPacket(ActionFailed.STATIC_PACKET);
			player.setAllowTrade(true);
		}

		// Clears requesting status
		player.setActiveRequester(null);
		partner.onTransactionResponse();
	}

	@Override
	public String getType()
	{
		return _C__40_ANSWERTRADEREQUEST;
	}
}