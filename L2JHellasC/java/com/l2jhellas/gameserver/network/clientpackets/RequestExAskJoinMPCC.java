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
import com.l2jhellas.gameserver.model.L2CommandChannel;
import com.l2jhellas.gameserver.model.L2Party;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.ActionFailed;
import com.l2jhellas.gameserver.network.serverpackets.ExAskJoinMPCC;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;

/**
 * Format: (ch) S
 * 
 * @author chris_00
 *         D0 0D 00 5A 00 77 00 65 00 72 00 67 00 00 00
 */
public final class RequestExAskJoinMPCC extends L2GameClientPacket
{
	private static final String _C__D0_0D_REQUESTEXASKJOINMPCC = "[C] D0:0D RequestExAskJoinMPCC";
	private String _name;

	@Override
	protected void readImpl()
	{
		_name = readS();
	}

	@Override
	protected void runImpl()
	{
		if(_name.isEmpty())
			return;
		
		final L2PcInstance activeChar = getClient().getActiveChar();
		
		if (activeChar == null)
			return;
		
		if(activeChar.isOutOfControl())
		{
			activeChar.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		final L2PcInstance target = L2World.getInstance().getPlayer(_name);
		
		if (target == null)
			return;
		
		final L2Party requestorParty = activeChar.getParty();
		
		if (requestorParty == null)
			return;
		
		final L2Party targetParty = target.getParty();
		
		if (targetParty == null || requestorParty.equals(targetParty))
			return;
						
		
		if (!requestorParty.isLeader(activeChar))
		{
			activeChar.sendPacket(SystemMessageId.CANNOT_INVITE_TO_COMMAND_CHANNEL);
			return;
		}
		
		final L2CommandChannel requestorChannel = requestorParty.getCommandChannel();
		
		if (requestorChannel != null && !requestorChannel.isLeader(activeChar))
		{
			activeChar.sendPacket(SystemMessageId.CANNOT_INVITE_TO_COMMAND_CHANNEL);
			return;
		}
		
		final L2CommandChannel targetChannel = targetParty.getCommandChannel();
		
		if (targetChannel != null)
		{
			activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.S1_ALREADY_MEMBER_OF_COMMAND_CHANNEL).addCharName(target));
			return;
		}
		
		final L2Clan requestorClan = activeChar.getClan();
		
		if (requestorClan == null || requestorClan.getLeaderId() != activeChar.getObjectId() || requestorClan.getLevel() < 5 || activeChar.getSkill(391) == null)
		{
			activeChar.sendPacket(SystemMessageId.COMMAND_CHANNEL_ONLY_BY_LEVEL_5_CLAN_LEADER_PARTY_LEADER);
			return;
		}

		final L2PcInstance targetLeader = targetParty.getLeader();
		
		if (!targetLeader.isProcessingRequest())
		{
			activeChar.onTransactionRequest(targetLeader);
			targetLeader.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.COMMAND_CHANNEL_CONFIRM_FROM_S1).addCharName(activeChar));
			targetLeader.sendPacket(new ExAskJoinMPCC(activeChar.getName()));
		}
		else
			activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.S1_IS_BUSY_TRY_LATER).addCharName(targetLeader));

	}

	@Override
	public String getType()
	{
		return _C__D0_0D_REQUESTEXASKJOINMPCC;
	}
}