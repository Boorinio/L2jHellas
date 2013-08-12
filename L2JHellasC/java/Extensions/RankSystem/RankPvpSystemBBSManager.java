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
package Extensions.RankSystem;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.communitybbs.Manager.BaseBBSManager;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.ShowBoard;

public class RankPvpSystemBBSManager extends BaseBBSManager
{
	@Override
	public void parsecmd(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("_bbsrps:") && Config.COMMUNITY_BOARD_TOP_LIST_ENABLED)
		{
			int page = 0;
			try
			{

				page = Integer.parseInt(command.split(":", 2)[1].trim());

			}
			catch (Exception e)
			{
				e.printStackTrace();
				page = 0;
			}

			separateAndSend(RankPvpSystemBBSHtm.prepareHtmResponse(activeChar, page), activeChar);
		}
		else
		{
			ShowBoard sb = null;
			if (command.startsWith("_bbsrps:") && !Config.COMMUNITY_BOARD_TOP_LIST_ENABLED)
			{
				sb = new ShowBoard("<html><body><br><br><center>Community Board Top List is disabled in config file</center><br><br></body></html>", "101");
			}
			else
			{
				sb = new ShowBoard("<html><body><br><br><center>the command: " + command + " is not implemented yet</center><br><br></body></html>", "101");
			}

			activeChar.sendPacket(sb);
			sb = null;
			activeChar.sendPacket(new ShowBoard(null, "102"));
			activeChar.sendPacket(new ShowBoard(null, "103"));
		}
	}

	@Override
	public void parsewrite(String ar1, String ar2, String ar3, String ar4, String ar5, L2PcInstance activeChar)
	{
		//
	}

	private static RankPvpSystemBBSManager _instance = new RankPvpSystemBBSManager();

	/**
	 * @return
	 */
	public static RankPvpSystemBBSManager getInstance()
	{
		return _instance;
	}
}
