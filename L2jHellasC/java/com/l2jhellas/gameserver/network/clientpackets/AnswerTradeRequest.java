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

/**
 * This class ...
 *
 * @version $Revision: 1.5.4.2 $ $Date: 2005/03/27 15:29:30 $
 */
public final class AnswerTradeRequest extends L2GameClientPacket
{
	private static final String _C__40_ANSWERTRADEREQUEST = "[C] 40 AnswerTradeRequest";
	//private static Logger _log = Logger.getLogger(AnswerTradeRequest.class.getName());

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
        if (player == null) return;

        if (!player.getAccessLevel().allowTransaction())
        {
        	player.sendMessage("Transactions are disabled for your Access Level.");
            sendPacket(new ActionFailed());
            return;
        }
             // MODS Faction Good vs Evil
                L2PcInstance player2 = player.getActiveRequester();
                if (player2.isevil() && player.isgood()
                && Config.MOD_GVE_ENABLE_FACTION)
                {
                player.sendMessage("You Cant Trade with Different Faction");
                return;
                }
                if (player2.isgood() && player.isevil()
                && Config.MOD_GVE_ENABLE_FACTION)
                {
                player.sendMessage("You Cant Trade with Different Faction");
                return;
                }
         

        L2PcInstance partner = player.getActiveRequester();
        if (partner == null || L2World.getInstance().findObject(partner.getObjectId()) == null)
        {
            // Trade partner not found, cancel trade
			player.sendPacket(new SendTradeDone(0));
            SystemMessage msg = new SystemMessage(SystemMessageId.TARGET_IS_NOT_FOUND_IN_THE_GAME);
            player.sendPacket(msg);
			player.setActiveRequester(null);
			player.setAllowTrade(true); 
			partner.setAllowTrade(true);
			player.sendPacket(new ActionFailed());
            return;
        }

        if (_response == 1 && !partner.isRequestExpired())  
        {  
        	player.startTrade(partner);  
        	partner.setAllowTrade(true);  
        	player.setAllowTrade(true);  
        }
		else
		{
			SystemMessage msg = new SystemMessage(SystemMessageId.S1_DENIED_TRADE_REQUEST);
			msg.addString(player.getName());
			partner.sendPacket(msg);
			player.sendPacket(new ActionFailed());
			player.setAllowTrade(true);
		}

		// Clears requesting status
		player.setActiveRequester(null);
		partner.onTransactionResponse();
	}

	/* (non-Javadoc)
	 * @see com.l2jhellas.gameserver.clientpackets.ClientBasePacket#getType()
	 */
	@Override
	public String getType()
	{
		return _C__40_ANSWERTRADEREQUEST;
	}
}
