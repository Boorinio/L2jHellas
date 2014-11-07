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
package handlers.chathandlers;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.handler.IChatHandler;
import com.l2jhellas.gameserver.model.BlockList;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.CreatureSay;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;

/**
 * @author KidZor
 */
public class ChatTell implements IChatHandler
{
	private static final int[] COMMAND_IDS =
	{
		2
	};

	@Override
	public void handleChat(int type, L2PcInstance activeChar, String target, String text)
	{

		// Return if player is chat banned
		if (activeChar.isChatBanned())
		{
			activeChar.sendMessage("You are currently banned from chat.");
			return;
		}

		// return if player is in jail
		if (Config.JAIL_DISABLE_CHAT && activeChar.isInJail())
		{
			activeChar.sendMessage("You are currently in jail and cannot chat.");
			return;
		}

		// Return if no target is set
		if (target == null)
			return;

		CreatureSay cs = new CreatureSay(activeChar.getObjectId(), type, activeChar.getName(), text);
		L2PcInstance receiver = null;

		receiver = L2World.getPlayer(target);

		if (receiver != null && !BlockList.isBlocked(receiver, activeChar))
		{
			if (Config.JAIL_DISABLE_CHAT && receiver.isInJail())
			{
				activeChar.sendMessage("Player is in jail.");
				return;
			}

			if (receiver.isAway())
			{
				receiver.sendPacket(new CreatureSay(activeChar.getObjectId(), type, activeChar.getName(), text));
				activeChar.sendPacket(new CreatureSay(activeChar.getObjectId(), type, "->" + receiver.getName(), text));
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_S2);
				sm.addString(target + " is Away try again later.");
				activeChar.sendPacket(sm);
			}

			if (receiver.isChatBanned())
			{
				activeChar.sendMessage("Player is chat banned.");
				return;
			}
			if (!receiver.getMessageRefusal())
			{
				receiver.sendPacket(cs);
				activeChar.sendPacket(new CreatureSay(activeChar.getObjectId(), type, "->" + receiver.getName(), text));
			}
			else
			{
				activeChar.sendPacket(SystemMessageId.THE_PERSON_IS_IN_MESSAGE_REFUSAL_MODE);
			}
		}
		else
		{
			SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_IS_NOT_ONLINE);
			sm.addString(target);
			activeChar.sendPacket(sm);
			sm = null;
		}
	}

	@Override
	public int[] getChatTypeList()
	{
		return COMMAND_IDS;
	}
}