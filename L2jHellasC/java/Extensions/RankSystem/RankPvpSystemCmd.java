/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 * http://www.gnu.org/copyleft/gpl.html
 */
package Extensions.RankSystem;

import com.l2jhellas.gameserver.handler.IVoicedCommandHandler;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author Masterio
 */
public class RankPvpSystemCmd implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"pvpinfo"
	};

	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		L2PcInstance playerTarget = null;
		
		if (activeChar == null)
			return false;
		
		if (activeChar.getTarget() != null && activeChar.getTarget() instanceof L2PcInstance)
		{
			playerTarget = (L2PcInstance) activeChar.getTarget();
		}
		else
		{
			activeChar.setTarget(activeChar);
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

	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}

}