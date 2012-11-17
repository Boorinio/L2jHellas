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

import com.l2jhellas.gameserver.cache.HtmCache;
import com.l2jhellas.gameserver.communitybbs.Manager.BaseBBSManager;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.ShowBoard;

public class RankPvpSystemBBSManager extends BaseBBSManager
{
	@Override
	public void parsecmd(String command, L2PcInstance activeChar)
	{
		if (command.equals("_bbstop"))
		{
			String content = HtmCache.getInstance().getHtm("data/html/CommunityBoard/index.htm");
			
			if (content == null)
			{
				content = "<html><body><br><br><center>404 :File Not found: 'data/html/CommunityBoard/index.htm' </center></body></html>";
			}

			separateAndSend(content, activeChar);
			content = null;
		}
		else if (command.equals("_bbshome"))
		{
			String content = HtmCache.getInstance().getHtm("data/html/CommunityBoard/index.htm");
			
			if (content == null)
			{
				content = "<html><body><br><br><center>404 :File Not found: 'data/html/CommunityBoard/index.htm' </center></body></html>";
			}

			separateAndSend(content, activeChar);
			content = null;
		}
		else if (command.startsWith("_bbscprs;"))
		{
			int page = 0;
			try
			{
				page = Integer.parseInt(command.substring(9));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				page = 0;
			}
			
			RankPvpSystemBBSManagerHtml htmlPrepare = new RankPvpSystemBBSManagerHtml();
			
			separateAndSend(htmlPrepare.prepareHtmlResponse(activeChar, page), activeChar);
		}
		else
		{
			ShowBoard sb = new ShowBoard("<html><body><br><br><center>the command: " + command + " is not implemented yet</center><br><br></body></html>", "101");
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