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
import com.l2jhellas.gameserver.GameTimeController;
import com.l2jhellas.gameserver.network.L2GameClient;
import com.l2jhellas.gameserver.network.serverpackets.ActionFailed;
import com.l2jhellas.gameserver.network.serverpackets.L2GameServerPacket;
import com.l2jserver.mmocore.network.ReceivablePacket;

/**
 * Packets received by the game server from clients
 * @author  KenM
 */
public abstract class L2GameClientPacket extends ReceivablePacket<L2GameClient>
{
	protected static final Logger _log = Logger.getLogger(L2GameClientPacket.class.getName());

	protected int _opcode = 0;

	@Override
	protected boolean read()
	{
		//System.out.println(this.getType());
		try
		{
			readImpl();
			return true;
		}
		catch (Throwable t)
		{
			_log.severe("Client: "+getClient().toString()+" - Failed reading: "+getType()+" - l2jhellas Server Version: "+Config.SERVER_VERSION);
			t.printStackTrace();
		}
		return false;
	}

	protected abstract void readImpl();

	@Override
	public void run()
	{
		try
		{
			if (GameTimeController.getGameTicks() - getClient().packetsSentStartTick > 10)
			{
				getClient().packetsSentStartTick = GameTimeController.getGameTicks();
				getClient().packetsSentInSec = 0;
			}
			else
			{
				getClient().packetsSentInSec++;
				if (getClient().packetsSentInSec > 12) 
				{
					if (getClient().packetsSentInSec < 100)
						sendPacket(new ActionFailed()); 
					return;
				}
			}
			
		 	if (GameTimeController.getGameTicks() - getClient().packetsSentStartTick > 10) 
		 	{ 
		 	  getClient().packetsSentStartTick = GameTimeController.getGameTicks(); 
		 	  getClient().packetsSentInSec = 0; 
		 	} 
		 	else 
		 	{ 
		 	  getClient().packetsSentInSec++; 
		 	  if (getClient().packetsSentInSec > 12) return; 
		 	}
			
			runImpl();
            if (this instanceof MoveBackwardToLocation || this instanceof AttackRequest || this instanceof RequestMagicSkillUse)
            	// could include pickup and talk too, but less is better
            {
            	// Removes onspawn protection - player has faster computer than
            	// average
            	if (getClient().getActiveChar() != null)
            		getClient().getActiveChar().onActionRequest();
            }
		}
		catch (Throwable t)
		{
			_log.severe("Client: "+getClient().toString()+" - Failed running: "+getType()+" - l2jhellas Server Version: "+Config.SERVER_VERSION);
			t.printStackTrace();
		}
	}
	
	protected abstract void runImpl();
	
	protected final void sendPacket(L2GameServerPacket gsp)
	{
		getClient().sendPacket(gsp);
	}
	
	/**
	 * @return A String with this packet name for debuging purposes
	 */
	public abstract String getType();
}
