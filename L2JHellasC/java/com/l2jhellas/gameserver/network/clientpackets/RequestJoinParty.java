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
import com.l2jhellas.gameserver.model.BlockList;
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
		final L2PcInstance requestor = getClient().getActiveChar();
		if (requestor == null)
			return;
		
		final L2PcInstance target = L2World.getPlayer(_name);
		if (target == null)
		{
			requestor.sendPacket(SystemMessageId.FIRST_SELECT_USER_TO_INVITE_TO_PARTY);
			return;
		}
		
		if (BlockList.isBlocked(target, requestor))
		{
			requestor.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.S1_HAS_ADDED_YOU_TO_IGNORE_LIST).addPcName(target));
			return;
		}
		
		if (target.equals(requestor) || target.isCursedWeaponEquiped() || requestor.isCursedWeaponEquiped() || target.getAppearance().getInvisible())
		{
			requestor.sendPacket(SystemMessageId.YOU_HAVE_INVITED_THE_WRONG_TARGET);
			return;
		}

		if (Config.MOD_GVE_ENABLE_FACTION)
		{
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
		
		if (target.isInParty())
		{
			requestor.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.S1_IS_ALREADY_IN_PARTY).addPcName(target));
			return;
		}
		
		if (target.getClient().isDetached())
		{
			requestor.sendMessage("The player you tried to invite is in offline mode.");
			return;
		}
		
		if (target.isInJail() || requestor.isInJail())
		{
			requestor.sendMessage("The player you tried to invite is currently jailed.");
			return;
		}
		
		if (target.isInOlympiadMode() || requestor.isInOlympiadMode())
			return;
		
		if (!requestor.isInParty())
			createNewParty(target, requestor);
		else
		{
			if (!requestor.getParty().isInDimensionalRift())
				addTargetToParty(target, requestor);
		}
	}
	
	/**
	 * @param target
	 * @param requestor
	 */
	private static void addTargetToParty(L2PcInstance target, L2PcInstance requestor)
	{
		final L2Party party = requestor.getParty();
		if (party == null)
			return;
		
		if (!party.isLeader(requestor))
		{
			requestor.sendPacket(SystemMessageId.ONLY_LEADER_CAN_INVITE);
			return;
		}
		
		if (party.getMemberCount() >= 9)
		{
			requestor.sendPacket(SystemMessageId.PARTY_FULL);
			return;
		}
		
		if (party.getPendingInvitation() && !party.isInvitationRequestExpired())
		{
			requestor.sendPacket(SystemMessageId.WAITING_FOR_ANOTHER_REPLY);
			return;
		}
		
		if (!target.isProcessingRequest())
		{
			requestor.onTransactionRequest(target);
			
			// in case a leader change has happened, use party's mode
			target.sendPacket(new AskJoinParty(requestor.getName(), party.getLootDistribution()));
			party.setPendingInvitation(true);
			
			requestor.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.YOU_INVITED_S1_TO_PARTY).addPcName(target));
			if (Config.DEBUG)
				_log.fine("Sent out a party invitation to " + target.getName());
		}
		else
		{
			requestor.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.S1_IS_BUSY_TRY_LATER).addPcName(target));
			if (Config.DEBUG)
				_log.warning(requestor.getName() + " already received a party invitation");
		}
	}
	
	/**
	 * @param target
	 * @param requestor
	 */
	private void createNewParty(L2PcInstance target, L2PcInstance requestor)
	{
		if (!target.isProcessingRequest())
		{
			requestor.setParty(new L2Party(requestor, _itemDistribution));
			
			requestor.onTransactionRequest(target);
			target.sendPacket(new AskJoinParty(requestor.getName(), _itemDistribution));
			requestor.getParty().setPendingInvitation(true);
			
			if (Config.DEBUG)
				_log.fine("Sent out a party invitation to " + target.getName());
			
			requestor.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.YOU_INVITED_S1_TO_PARTY).addPcName(target));
		}
		else
		{
			requestor.sendPacket(SystemMessageId.WAITING_FOR_ANOTHER_REPLY);
			
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