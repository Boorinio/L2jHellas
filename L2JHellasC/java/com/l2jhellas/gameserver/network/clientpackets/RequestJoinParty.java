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
import com.l2jhellas.gameserver.model.L2Party;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.entity.engines.CTF;
import com.l2jhellas.gameserver.model.entity.engines.DM;
import com.l2jhellas.gameserver.model.entity.engines.TvT;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.AskJoinParty;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;

/**
 * sample<BR>
 * 29<BR>
 * 42 00 00 10<BR>
 * 01 00 00 00<BR>
 * format cdd
 */
public final class RequestJoinParty extends L2GameClientPacket
{
	private static Logger _log = Logger.getLogger(RequestJoinParty.class.getName());
	private static final String _C__29_REQUESTJOINPARTY = "[C] 29 RequestJoinParty";

	private String _name;
	private int _itemDistribution;

	@Override
	protected void readImpl()
	{
		_name = readS();
		_itemDistribution = readD();
	}

	@Override
	protected void runImpl()
	{
		L2PcInstance requestor = getClient().getActiveChar();
		L2PcInstance target = L2World.getPlayer(_name);

		if (requestor == null)
			return;

		if (target == null)
		{
			requestor.sendPacket(SystemMessageId.TARGET_IS_INCORRECT);
			return;
		}

		if (target.isInParty())
		{
			SystemMessage msg = SystemMessage.getSystemMessage(SystemMessageId.S1_IS_ALREADY_IN_PARTY);
			msg.addString(target.getName());
			requestor.sendPacket(msg);
			return;
		}

		// Good and Evil can't join same party
		if (target.isevil() && requestor.isgood())
		{
			requestor.sendPacket(SystemMessageId.INCORRECT_TARGET);
			return;
		}

		if (target.isgood() && requestor.isevil())
		{
			requestor.sendPacket(SystemMessageId.INCORRECT_TARGET);
			return;
		}

		if (target == requestor)
		{
			requestor.sendPacket(SystemMessageId.INCORRECT_TARGET);
			return;
		}

		if (target.isCursedWeaponEquiped() || requestor.isCursedWeaponEquiped())
		{
			requestor.sendPacket(SystemMessageId.INCORRECT_TARGET);
			return;
		}

		if (((TvT._started && !Config.TVT_ALLOW_INTERFERENCE) || (CTF._started && !Config.CTF_ALLOW_INTERFERENCE) || (DM._started && !Config.DM_ALLOW_INTERFERENCE) && !requestor.isGM()))
		{
			if ((target._inEventTvT && !requestor._inEventTvT) || (!target._inEventTvT && requestor._inEventTvT))
			{
				requestor.sendPacket(SystemMessageId.INCORRECT_TARGET);
				return;
			}
			if ((target._inEventCTF && !requestor._inEventCTF) || (!target._inEventCTF && requestor._inEventCTF))
			{
				requestor.sendPacket(SystemMessageId.INCORRECT_TARGET);
				return;
			}
			if ((target._inEventDM && !requestor._inEventDM) || (!target._inEventDM && requestor._inEventDM))
			{
				requestor.sendPacket(SystemMessageId.INCORRECT_TARGET);
				return;
			}
			else if ((target._inEventVIP && !requestor._inEventVIP) || (!target._inEventVIP && requestor._inEventVIP))
			{
				requestor.sendPacket(SystemMessageId.INCORRECT_TARGET);
				return;
			}
		}

		if (target.isInJail() || requestor.isInJail())
		{
			SystemMessage sm = SystemMessage.sendString("Player is in Jail");
			requestor.sendPacket(sm);
			return;
		}

		if (target.getClient().isDetached())
		{
			requestor.sendMessage("Player is in offline mode.");
			return;
		}

		if (target.getClient().isDetached())
		{
			requestor.sendMessage("Player is in offline mode.");
			return;
		}

		if (target.isInOlympiadMode() || requestor.isInOlympiadMode())
			return;

		if (target.isInDuel() || requestor.isInDuel())
			return;

		if (!requestor.isInParty()) // Asker has no party
		{
			createNewParty(target, requestor);
		}
		else
		// Asker is in party
		{
			if (requestor.getParty().isInDimensionalRift())
				requestor.sendMessage("You can't invite a player when in Dimensional Rift.");
			else
				addTargetToParty(target, requestor);
		}
	}

	/**
	 * @param client
	 * @param itemDistribution
	 * @param target
	 * @param requestor
	 */
	private void addTargetToParty(L2PcInstance target, L2PcInstance requestor)
	{
		SystemMessage msg;

		// summary of people already in party and people that get invitation
		if (requestor.getParty().getMemberCount() + requestor.getParty().getPendingInvitationNumber() >= 9)
		{
			requestor.sendPacket(SystemMessageId.PARTY_FULL);
			return;
		}

		if (!requestor.getParty().isLeader(requestor))
		{
			requestor.sendPacket(SystemMessageId.ONLY_LEADER_CAN_INVITE);
			return;
		}

		if (!target.isProcessingRequest())
		{
			requestor.onTransactionRequest(target);
			target.sendPacket(new AskJoinParty(requestor.getName(), _itemDistribution));
			requestor.getParty().increasePendingInvitationNumber();

			if (Config.DEBUG)
				_log.fine("sent out a party invitation to:" + target.getName());

			msg = SystemMessage.getSystemMessage(SystemMessageId.YOU_INVITED_S1_TO_PARTY);
			msg.addString(target.getName());
			requestor.sendPacket(msg);
		}
		else
		{
			msg = SystemMessage.getSystemMessage(SystemMessageId.S1_IS_BUSY_TRY_LATER);
			requestor.sendPacket(msg);

			if (Config.DEBUG)
				_log.warning(requestor.getName() + " already received a party invitation");
		}
		msg = null;
	}

	/**
	 * @param client
	 * @param itemDistribution
	 * @param target
	 * @param requestor
	 */
	private void createNewParty(L2PcInstance target, L2PcInstance requestor)
	{
		SystemMessage msg;

		if (!target.isProcessingRequest())
		{
			requestor.setParty(new L2Party(requestor, _itemDistribution));

			requestor.onTransactionRequest(target);
			target.sendPacket(new AskJoinParty(requestor.getName(), _itemDistribution));
			requestor.getParty().increasePendingInvitationNumber();

			if (Config.DEBUG)
				_log.fine("sent out a party invitation to:" + target.getName());

			msg = SystemMessage.getSystemMessage(SystemMessageId.YOU_INVITED_S1_TO_PARTY);
			msg.addString(target.getName());
			requestor.sendPacket(msg);
		}
		else
		{
			msg = SystemMessage.getSystemMessage(SystemMessageId.S1_IS_BUSY_TRY_LATER);
			msg.addString(target.getName());
			requestor.sendPacket(msg);

			if (Config.DEBUG)
				_log.warning(requestor.getName() + " already received a party invitation");
		}
	}

	@Override
	public String getType()
	{
		return _C__29_REQUESTJOINPARTY;
	}
}