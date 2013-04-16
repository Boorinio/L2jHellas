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
package com.l2jhellas.gameserver.communitybbs.Manager;

import java.io.File;
import java.util.StringTokenizer;

import com.l2jhellas.Config;
import com.l2jhellas.ExternalConfig;
import com.l2jhellas.gameserver.GameTimeController;
import com.l2jhellas.gameserver.cache.HtmCache;
import com.l2jhellas.gameserver.communitybbs.CastleStatus;
import com.l2jhellas.gameserver.communitybbs.ClanList;
import com.l2jhellas.gameserver.communitybbs.GrandBossList;
import com.l2jhellas.gameserver.communitybbs.HeroeList;
import com.l2jhellas.gameserver.communitybbs.RaidList;
import com.l2jhellas.gameserver.communitybbs.TopPlayers;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.ShowBoard;

public class TopBBSManager extends BaseBBSManager
{

	private TopBBSManager()
	{
	}

	@Override
	public void parsecmd(String command, L2PcInstance activeChar)
	{
		String path = "data/html/CommunityBoard/";
		String filepath = "";
		String content = "";

		if (command.equals("_bbstop") | command.equals("_bbshome"))
		{
			filepath = path + "index.htm";
			content = HtmCache.getInstance().getHtm(filepath);
			separateAndSend(content, activeChar);
		}
		else if (command.startsWith("_bbstop;"))
		{
			StringTokenizer st = new StringTokenizer(command, ";");
			st.nextToken();
			String file = st.nextToken();
			filepath = path + file + ".htm";
			File filecom = new File(filepath);

			if (!(filecom.exists()))
			{
				content = "<html><body><br><br><center>The command " + command + " points to file(" + filepath + ") that NOT exists.</center></body></html>";
				separateAndSend(content, activeChar);
				return;
			}
			content = HtmCache.getInstance().getHtm(filepath);

			if (content.isEmpty())
				content = "<html><body><br><br><center>Content Empty: The command " + command + " points to an invalid or empty html file(" + filepath + ").</center></body></html>";

			switch (file)
			{
				case "toppvp":
					TopPlayers pvp = new TopPlayers(file);
					content = content.replaceAll("%toppvp%", pvp.loadTopList());
				break;
				case "toppk":
					TopPlayers pk = new TopPlayers(file);
					content = content.replaceAll("%toppk%", pk.loadTopList());
				break;
				case "toprbrank":
					TopPlayers raid = new TopPlayers(file);
					content = content.replaceAll("%toprbrank%", raid.loadTopList());
				break;
				case "topadena":
					TopPlayers adena = new TopPlayers(file);
					content = content.replaceAll("%topadena%", adena.loadTopList());
				break;
				case "toponline":
					TopPlayers online = new TopPlayers(file);
					content = content.replaceAll("%toponline%", online.loadTopList());
				break;
				case "heroes":
					HeroeList hr = new HeroeList();
					content = content.replaceAll("%heroelist%", hr.loadHeroeList());
				break;
				case "castle":
					CastleStatus status = new CastleStatus();
					content = content.replaceAll("%castle%", status.loadCastleList());
				break;
				case "boss":
					GrandBossList gb = new GrandBossList();
					content = content.replaceAll("%gboss%", gb.loadGrandBossList());
				break;
				case "stats":
					content = content.replace("%online%", Integer.toString(L2World.getInstance().getAllPlayersCount()));
					content = content.replace("%servercapacity%", Integer.toString(Config.MAXIMUM_ONLINE_USERS));
					content = content.replace("%serverruntime%", getServerRunTime());
					if (ExternalConfig.ALLOW_REAL_ONLINE_STATS)
						content = content.replace("%serveronline%", getRealOnline());
					else
						content = content.replace("%serveronline%", "");
				break;
				default:
				break;

			}
			if (file.startsWith("clan"))
			{
				int cid = Integer.parseInt(file.substring(4));
				ClanList cl = new ClanList(cid);
				content = content.replaceAll("%clanlist%", cl.loadClanList());
			}
			if (file.startsWith("raid"))
			{
				String rfid = file.substring(4);
				RaidList rd = new RaidList(rfid);
				content = content.replaceAll("%raidlist%", rd.loadRaidList());
			}
			if (content.isEmpty())
			{
				content = "<html><body><br><br><center>404 :File not found or empty: " + filepath + " your command is " + command + "</center></body></html>";
			}
			separateAndSend(content, activeChar);
		}
		else
		{
			ShowBoard sb = new ShowBoard("<html><body><br><br><center>the command: " + command + " is not implemented yet</center><br><br></body></html>", "101");
			activeChar.sendPacket(sb);
			activeChar.sendPacket(new ShowBoard(null, "102"));
			activeChar.sendPacket(new ShowBoard(null, "103"));
		}
	}

	@Override
	public void parsewrite(String ar1, String ar2, String ar3, String ar4, String ar5, L2PcInstance activeChar)
	{
		// TODO Auto-generated method stub
	}

	private static TopBBSManager _instance = new TopBBSManager();

	/**
	 * @return
	 */
	public static TopBBSManager getInstance()
	{
		return _instance;
	}

	public String getServerRunTime()
	{
		int timeSeconds = (GameTimeController.getGameTicks() - 36000) / 10;
		String timeResult = "";
		if (timeSeconds >= 86400)
			timeResult = Integer.toString(timeSeconds / 86400) + " Days " + Integer.toString((timeSeconds % 86400) / 3600) + " hours";
		else
			timeResult = Integer.toString(timeSeconds / 3600) + " Hours " + Integer.toString((timeSeconds % 3600) / 60) + " mins";
		return timeResult;
	}

	public String getRealOnline()
	{
		int counter = 0;
		for (L2PcInstance onlinePlayer : L2World.getInstance().getAllPlayers())
		{
			if (onlinePlayer.isOnline() == 1 && onlinePlayer.getClient() != null && !onlinePlayer.getClient().isDetached())
			{
				counter++;
			}
		}
		String realOnline = "<tr><td fixwidth=11></td><td FIXWIDTH=280>Players Active</td><td FIXWIDTH=470><font color=26e600>" + counter + "</font></td></tr>" + "<tr><td fixwidth=11></td><td FIXWIDTH=280>Players Shops</td><td FIXWIDTH=470><font color=26e600>" + (L2World.getInstance().getAllPlayersCount() - counter) + "</font></td></tr>";
		return realOnline;
	}
}
