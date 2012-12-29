/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package Extensions.RankSystem;

import com.l2jhellas.ExternalConfig;
import com.l2jhellas.gameserver.handler.IUserCommandHandler;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author Masterio
 */
public class IUserCommandHandlerPvpInfo implements IUserCommandHandler
{
	private static final int[] COMMAND_IDS =
	{
		ExternalConfig.CUSTOM_PVP_INFO_USER_COMMAND_ID
	};
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.l2jhellas.gameserver.handler.IUserCommandHandler#useUserCommand(int,
	 * com.l2jhellas.gameserver.model.actor.instance.L2PcInstance)
	 */
	@Override
	public boolean useUserCommand(int id, L2PcInstance activeChar)
	{
		if (id != COMMAND_IDS[0])
		{
			return false;
		}
		
		L2PcInstance playerTarget = null;
		
		if (activeChar == null)
			return false;
		
		if (activeChar.getTarget() != null && activeChar.getTarget() instanceof L2PcInstance)
		{
			playerTarget = (L2PcInstance) activeChar.getTarget();
		}
		else
		{
			playerTarget = activeChar;
			activeChar.sendMessage("PvP Status executed on self!");
		}
		
		RankPvpSystemPlayerInfo playerInfo = new RankPvpSystemPlayerInfo();
		
		if (playerTarget != null)
		{
			playerInfo.sendPlayerResponse(activeChar, playerTarget);
		}
		else
		{
			playerInfo.sendPlayerResponse(activeChar, activeChar);
		}
		
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.l2jhellas.gameserver.handler.IUserCommandHandler#getUserCommandList()
	 */
	@Override
	public int[] getUserCommandList()
	{
		return COMMAND_IDS;
	}
}