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

import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.TradeList;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.ActionFailed;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;

public final class TradeDone extends L2GameClientPacket
{
	private static Logger _log = Logger.getLogger(TradeDone.class.getName());
	private static final String _C__17_TRADEDONE = "[C] 17 TradeDone";

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
		if (player == null)
			return;

		TradeList trade = player.getActiveTradeList();
		if (trade == null)
		{
			_log.warning("player.getTradeList == null in " + getType() + " for player " + player.getName());
			return;
		}

		if(player.getActiveEnchantItem() !=null || player.getActiveWarehouse()!=null  || trade.getPartner().getActiveEnchantItem() != null || trade.getPartner().getActiveWarehouse() != null)
		{
			player.sendMessage("You can't trade item if: you are enchanting,got active warehouse,active trade");
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		if (trade.isLocked())
			return;

		if (_response == 1)
		{
			if ((trade.getPartner() == null) || (L2World.findObject(trade.getPartner().getObjectId()) == null))
			{
				// Trade partner not found, cancel trade
				player.cancelActiveTrade();
				SystemMessage msg = SystemMessage.getSystemMessage(SystemMessageId.TARGET_IS_NOT_FOUND_IN_THE_GAME);
				player.sendPacket(msg);
				msg = null;
				return;
			}

			if (!player.getAccessLevel().allowTransaction())
			{
				player.cancelActiveTrade();
				player.sendMessage("Transactions are disabled for your Access Level.");
				return;
			}

			trade.confirm();
		}
		else
			player.cancelActiveTrade();
	}

	@Override
	public String getType()
	{
		return _C__17_TRADEDONE;
	}
}