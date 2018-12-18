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

import com.l2jhellas.gameserver.model.L2CommandChannel;
import com.l2jhellas.gameserver.model.L2Party;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;

/**
 * @author -Wooden-
 *         D0 0F 00 5A 00 77 00 65 00 72 00 67 00 00 00
 */
public final class RequestExOustFromMPCC extends L2GameClientPacket
{
	private static final String _C__D0_0F_REQUESTEXOUSTFROMMPCC = "[C] D0:0F RequestExOustFromMPCC";
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
		
		final L2PcInstance target = L2World.getInstance().getPlayer(_name);
		
		if (target == null)
		{
			activeChar.sendPacket(SystemMessageId.TARGET_CANT_FOUND);
			return;
		}
		
		if (activeChar.equals(target))
		{
			activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
			return;
		}
		
		final L2Party reqpart = activeChar.getParty();
		final L2Party targetParty = target.getParty();
		
		if (reqpart == null || targetParty == null)
		{
			activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
			return;
		}
		
		final L2CommandChannel requestorChannel = reqpart.getCommandChannel();
		
		if (requestorChannel == null || !requestorChannel.isLeader(activeChar))
		{
			activeChar.sendPacket(SystemMessageId.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
			return;
		}
		
		if (!requestorChannel.removeParty(targetParty))
		{
			activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
			return;
		}
		
		targetParty.getCommandChannel().broadcastToChannelMembers(SystemMessage.getSystemMessage(SystemMessageId.DISMISSED_FROM_COMMAND_CHANNEL));
		
		if (reqpart.isInCommandChannel())
			reqpart.getCommandChannel().broadcastToChannelMembers(SystemMessage.getSystemMessage(SystemMessageId.S1_PARTY_DISMISSED_FROM_COMMAND_CHANNEL).addCharName(targetParty.getLeader()));
	}

	@Override
	public String getType()
	{
		return _C__D0_0F_REQUESTEXOUSTFROMMPCC;
	}
}