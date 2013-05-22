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
import com.l2jhellas.gameserver.model.BlockList;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.ActionFailed;
import com.l2jhellas.gameserver.network.serverpackets.SendTradeRequest;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;
import com.l2jhellas.util.Util;

public final class TradeRequest extends L2GameClientPacket
{
	private static final String TRADEREQUEST__C__15 = "[C] 15 TradeRequest";

	private int _objectId;

	@Override
	protected void readImpl()
	{
		_objectId = readD();
	}

	@Override
	protected void runImpl()
	{
		L2PcInstance player = getClient().getActiveChar();
		if (player == null)
			return;

		if (!player.getAccessLevel().allowTransaction())
		{
			player.sendMessage("Transactions are disabled for your Access Level.");
			sendPacket(new ActionFailed());
			return;
		}

		L2Object target = L2World.getInstance().findObject(_objectId);
		if ((target == null) || !player.getKnownList().knowsObject(target) || !(target instanceof L2PcInstance) || (target.getObjectId() == player.getObjectId()))
		{
			player.sendPacket(new SystemMessage(SystemMessageId.TARGET_IS_INCORRECT));
			return;
		}

		L2PcInstance partner = (L2PcInstance) target;

		if (partner.isInOlympiadMode() || player.isInOlympiadMode())
		{
			player.sendMessage("You or your target cant request trade in Olympiad mode.");
			return;
		}

		// Faction Good vs Evil
		L2PcInstance player2 = (L2PcInstance) target;
		if (player2.isevil() && player.isgood() && Config.MOD_GVE_ENABLE_FACTION)
		{
			player.sendMessage("You Can't Trade with Different Faction.");
			return;
		}

		if (player2.isgood() && player.isevil() && Config.MOD_GVE_ENABLE_FACTION)
		{
			player.sendMessage("You can't trade with different faction.");
			return;
		}

		// Alt game - Karma punishment
		if (!Config.ALT_GAME_KARMA_PLAYER_CAN_TRADE && ((player.getKarma() > 0) || (partner.getKarma() > 0)))
		{
			player.sendMessage("Chaotic players can't use Trade.");
			return;
		}

		if ((player.getPrivateStoreType() != 0) || (partner.getPrivateStoreType() != 0))
		{
			player.sendPacket(new SystemMessage(SystemMessageId.CANNOT_TRADE_DISCARD_DROP_ITEM_WHILE_IN_SHOPMODE));
			return;
		}

		if (!Config.ALLOW_LOW_LEVEL_TRADE)
			if ((player.getLevel() <= 76) || (player.getLevel() > 1))
			{
				player.sendMessage("You can't trade with a lower level character.");
			}

		if (player.isProcessingTransaction())
		{
			player.sendPacket(new SystemMessage(SystemMessageId.ALREADY_TRADING));
			return;
		}

		if (partner.isProcessingRequest() || partner.isProcessingTransaction())
		{
			SystemMessage sm = new SystemMessage(SystemMessageId.S1_IS_BUSY_TRY_LATER);
			sm.addString(partner.getName());
			player.sendPacket(sm);
			return;
		}

		if (partner.getTradeRefusal())
		{
			player.sendMessage("Target is in trade refusal mode.");
			return;
		}

		if (Util.calculateDistance(player, partner, true) > 150)
		{
			SystemMessage sm = new SystemMessage(SystemMessageId.TARGET_TOO_FAR);
			player.sendPacket(sm);
			return;
		}

		if (BlockList.isBlocked(partner, player))
		{
			player.sendMessage("Target has added you in his/her blocklist.");
			return;
		}

		if (partner.getAllowTrade() == false)
		{
			player.sendMessage("Target is not allowed to receive more than one trade request at the same time.");
			return;
		}
		partner.setAllowTrade(false);
		player.setAllowTrade(false);
		if (Util.calculateDistance(player, partner, true) > 150)
		{
			SystemMessage sm = new SystemMessage(SystemMessageId.TARGET_TOO_FAR);
			player.sendPacket(sm);
			return;
		}
		player.onTransactionRequest(partner);
		partner.sendPacket(new SendTradeRequest(player.getObjectId()));
		SystemMessage sm = new SystemMessage(SystemMessageId.REQUEST_S1_FOR_TRADE);
		sm.addString(partner.getName());
		player.sendPacket(sm);
	}

	@Override
	public String getType()
	{
		return TRADEREQUEST__C__15;
	}
}