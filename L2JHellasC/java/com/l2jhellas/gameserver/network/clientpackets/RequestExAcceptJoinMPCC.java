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
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;

/**
 * @author -Wooden-
 */
public final class RequestExAcceptJoinMPCC extends L2GameClientPacket
{
	private static final String _C__D0_0E_REQUESTEXASKJOINMPCC = "[C] D0:0E RequestExAcceptJoinMPCC";
	private int _response;

	/**
	 * @param buf
	 * @param client
	 */
	@Override
	protected void readImpl()
	{
		_response = readD();
	}

	@Override
	protected void runImpl()
	{
		final L2PcInstance player = getClient().getActiveChar();
		
		if (player == null)
			return;
		
		final L2PcInstance requestor = player.getActiveRequester();
		
		if (requestor == null)
			return;
				
		final L2Party PartyRequestor = requestor.getParty();
		
		if (PartyRequestor == null)
			return;
		
		final L2Party targetParty = player.getParty();
		
		if (targetParty == null)
			return;
		
		if (_response == 1)
		{
			L2CommandChannel channel = PartyRequestor.getCommandChannel();
			
			if (channel == null)
			{
				if (!requestor.destroyItemByItemId("CCCreation", 8871, 1, player, true))
					return;
				
				channel = new L2CommandChannel(requestor);
			}
			else
				channel.addParty(targetParty);
		}
		else
		{
			requestor.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.S1_DECLINED_CHANNEL_INVITATION).addCharName(player));
		}
		
		player.setActiveRequester(null);
		requestor.onTransactionResponse();
	}

	@Override
	public String getType()
	{
		return _C__D0_0E_REQUESTEXASKJOINMPCC;
	}
}