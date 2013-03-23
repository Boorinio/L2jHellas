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

import java.util.logging.Logger;

import javolution.text.TextBuilder;

import com.l2jhellas.ExternalConfig;
import com.l2jhellas.gameserver.datatables.sql.ItemTable;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jhellas.util.ServerSideImage;

/**
 * @author Masterio
 */
public class RankPvpSystemPlayerInfo
{
	private static final Logger _log = Logger.getLogger(RankPvpSystemPlayerInfo.class.getName());

	public void sendPlayerResponse(L2PcInstance player, L2PcInstance playerTarget)
	{
		NpcHtmlMessage n = new NpcHtmlMessage(0);
		n.setHtml(playerResponseHtml(player, playerTarget).toString());
		player.sendPacket(n);
	}

	private TextBuilder playerResponseHtml(L2PcInstance player, L2PcInstance playerTarget)
	{
		TextBuilder tb = new TextBuilder();

		// get PvP object with target. (for get how many times he killed player):
		Pvp pvp1 = new Pvp();
		Pvp pvp2 = new Pvp();
		if (!player.equals(playerTarget))
		{
			pvp1 = PvpTable.getInstance().getPvp(playerTarget.getObjectId(), player.getObjectId()); // pvp: target - player
			pvp2 = PvpTable.getInstance().getPvp(player.getObjectId(), playerTarget.getObjectId()); // pvp: player - target
		}

		// get target PvpStats:
		PvpStats targetPvpStats = PvpTable.getInstance().getPvpStats(playerTarget.getObjectId());

		tb.append("<html><title>" + playerTarget.getName() + " PvP Status</title><body>");

		tb.append(rankImgTableHtml(player, targetPvpStats));
		if (player.equals(playerTarget))
		{
			tb.append(expBelt(player, targetPvpStats));
		}
		else
		{
			// span
			tb.append("<br>");
		}

		// about player target:
		tb.append("<center><table border=0 cellspacing=0 cellpadding=0>");

		// name [level]
		tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Name [lvl]</font></td>");
		tb.append("<td width=135 height=22 align=left>");
		tb.append("<font color=ffa000>" + playerTarget.getName() + " [" + playerTarget.getLevel() + "]</font>");
		tb.append("</td></tr>");

		// current class
		tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Current class</font></td>");
		tb.append("<td width=135 height=22 align=left>");
		tb.append("<font color=ffa000>" + RankPvpSystemUtil.getClassName(playerTarget.getClassId().getId()) + "</font>");
		tb.append("</td></tr>");

		// main class
		if (playerTarget.getBaseClass() != playerTarget.getClassId().getId())
		{
			tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Main class</font></td>");
			tb.append("<td width=135 height=22 align=left>");
			tb.append("<font color=ffa000>" + RankPvpSystemUtil.getClassName(playerTarget.getBaseClass()) + "</font>");
			tb.append("</td></tr>");
		}

		// nobles
		tb.append("<tr><td width=135 height=22 align=rigth><font color=ae9977>Nobles</font></td>");
		tb.append("<td width=135 height=22 align=left>");
		if (playerTarget.isNoble())
		{
			tb.append("<font color=ffa000>Yes</font>");
		}
		else
		{
			tb.append("<font color=808080>No</font>");
		}
		tb.append("</td></tr>");

		// hero
		tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Hero</font></td>");
		tb.append("<td width=135 height=22 align=left>");
		if (playerTarget.isHero())
		{
			tb.append("<font color=ffa000>Yes</font>");
		}
		else
		{
			tb.append("<font color=808080>No</font>");
		}
		tb.append("</td></tr>");

		// clan
		if (player.isDead() && !player.equals(playerTarget))
		{
			tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Clan</font></td>");
			tb.append("<td width=135 height=22 align=left>");
			if (playerTarget.getClan() != null)
			{
				tb.append("<font color=ffa000>" + playerTarget.getClan().getName() + "</font>");
			}
			else
			{
				tb.append("<font color=808080>No clan</font>");
			}
			tb.append("</td></tr>");
		}

		// span
		tb.append("<tr><td width=135 HEIGHT=1><img src=\"L2UI.Squaregray\" width=\"135\" height=\"1\"></img></td><td width=135 HEIGHT=1><img src=\"L2UI.Squaregray\" width=\"135\" height=\"1\"></img></td></tr>");
		tb.append("<tr><td width=135 height=12></td><td width=135 height=12></td></tr>");

		if (ExternalConfig.CUSTOM_PVP_RANK_ENABLED)
		{

			tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Rank</font></td>");
			tb.append("<td width=135 height=22 align=left>");
			tb.append("<font color=ffff00>" + targetPvpStats.getRank().getName() + "</font>");
			tb.append("</td></tr>");

			tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Rank Points</font></td>");
			tb.append("<td width=135 height=22 align=left>");
			tb.append("<font color=ffa000>" + targetPvpStats.getTotalRankPoints() + "</font>");
			tb.append("</td></tr>");

		}

		if (ExternalConfig.TOTAL_KILLS_IN_PVPINFO_ENABLED)
		{
			// legal/total kills:
			tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Legal/Total Kills</font></td>");
			tb.append("<td width=135 height=22 align=left>");
			tb.append("<font color=ffa000>" + targetPvpStats.getTotalKillsLegal() + " / " + targetPvpStats.getTotalKills() + "</font>");
			tb.append("</td></tr>");
		}
		else
		{
			// legal kills:
			tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Legal Kills</font></td>");
			tb.append("<td width=135 height=22 align=left>");
			tb.append("<font color=ffa000>" + targetPvpStats.getTotalKillsLegal() + "</font>");
			tb.append("</td></tr>");
		}

		if (ExternalConfig.CUSTOM_PVP_WAR_ENABLED)
		{

			if (ExternalConfig.TOTAL_KILLS_IN_PVPINFO_ENABLED)
			{
				// war legal/total kills:
				tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Legal/Total War Kills</font></td>");
				tb.append("<td width=135 height=22 align=left>");
				tb.append("<font color=2080D0>" + targetPvpStats.getTotalWarKillsLegal() + " / " + targetPvpStats.getTotalWarKills() + "</font>");
				tb.append("</td></tr>");
			}
			else
			{
				// war legal kills:
				tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Legal War Kills</font></td>");
				tb.append("<td width=135 height=22 align=left>");
				tb.append("<font color=2080D0>" + targetPvpStats.getTotalWarKillsLegal() + "</font>");
				tb.append("</td></tr>");
			}
		}

		// span
		tb.append("<tr><td width=135 HEIGHT=1><img src=\"L2UI.Squaregray\" width=\"135\" height=\"1\"></img></td><td width=135 HEIGHT=1><img src=\"L2UI.Squaregray\" width=\"135\" height=\"1\"></img></td></tr>");
		tb.append("<tr><td width=135 height=12></td><td width=135 height=12></td></tr>");

		if (ExternalConfig.CUSTOM_PVP_RANK_ENABLED)
		{

			tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>RP for kill</font></td>");
			tb.append("<td width=135 height=22 align=left>");
			tb.append("<font color=ffa000>" + targetPvpStats.getRank().getPointsForKill() + "</font>");
			tb.append("</td></tr>");

			if (ExternalConfig.CUSTOM_PVP_RANK_REWARD_ENABLED)
			{
				tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Rank Reward</font></td>");
				tb.append("<td width=135 height=22 align=left>");
				tb.append("<font color=ffa000>" + targetPvpStats.getRank().getRewardAmount() + "x " + ItemTable.getInstance().getTemplate(targetPvpStats.getRank().getRewardId()).getName() + "</font>");
				tb.append("</td></tr>");
			}

		}

		if (ExternalConfig.CUSTOM_PVP_REWARD_ENABLED)
		{
			tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>PvP Reward</font></td>");
			tb.append("<td width=135 height=22 align=left>");
			tb.append("<font color=ffa000>" + ExternalConfig.CUSTOM_PVP_REWARD_AMOUNT + "x " + ItemTable.getInstance().getTemplate(ExternalConfig.CUSTOM_PVP_REWARD_ID).getName() + "</font>");
			tb.append("</td></tr>");
		}

		if (!player.equals(playerTarget) && ExternalConfig.CUSTOM_PVP_PROTECTION_RESET > 0)
		{
			long sys_time = System.currentTimeMillis();
			if (ExternalConfig.CUSTOM_PVP_PROTECTION_RESET > 0 && (sys_time - (1000 * 60 * ExternalConfig.CUSTOM_PVP_PROTECTION_RESET) < pvp2.getKillTime()))
			{ // show time to legal kill
				if (ExternalConfig.CUSTOM_PVP_REWARD_ENABLED && ExternalConfig.CUSTOM_PVP_RANK_ENABLED)
				{
					tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>RP/Reward Protection</font></td>");
				}
				else if (ExternalConfig.CUSTOM_PVP_RANK_ENABLED)
				{
					tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Rank Points Protection</font></td>");
				}
				else if (ExternalConfig.CUSTOM_PVP_REWARD_ENABLED)
				{
					tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Reward Protection</font></td>");
				}
				tb.append("<td width=135 height=22 align=left>");
				tb.append("<font color=FFFF00>" + RankPvpSystem.calculateTimeToString(sys_time, pvp2.getKillTime()) + "</font>");
				tb.append("</td></tr>");
			}
			else
			{
				if (ExternalConfig.CUSTOM_PVP_REWARD_ENABLED && ExternalConfig.CUSTOM_PVP_RANK_ENABLED)
				{
					tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>RP/Reward Protection</font></td>");
				}
				else if (ExternalConfig.CUSTOM_PVP_RANK_ENABLED)
				{
					tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Rank Points Protection</font></td>");
				}
				else if (ExternalConfig.CUSTOM_PVP_REWARD_ENABLED)
				{
					tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Reward Protection</font></td>");
				}
				tb.append("<td width=135 height=22 align=left>");
				tb.append("<font color=00FF00>OFF</font>");
				tb.append("</td></tr>");
			}
		}

		tb.append("</table>");

		if (!player.equals(playerTarget))
		{
			tb.append("<table border=0 cellspacing=0 cellpadding=0>");

			// span
			tb.append("<tr><td width=135 HEIGHT=1><img src=\"L2UI.Squaregray\" width=\"135\" height=\"1\"></img></td><td width=135 HEIGHT=1><img src=\"L2UI.Squaregray\" width=\"135\" height=\"1\"></img></td></tr>");
			tb.append("<tr><td width=135 height=12></td><td width=135 height=12></td></tr>");

			if (ExternalConfig.TOTAL_KILLS_ON_ME_IN_PVPINFO_ENABLED)
			{
				// legal/total kills on me:
				tb.append("<tr><td width=135 height=22 align=left>");
				tb.append("<font color=ae9977>Legal/Total Kills on Me</font>");
				tb.append("</td>");
				tb.append("<td width=135 height=22 align=left>");
				tb.append("<font color=FF00FF>" + pvp1.getKillsLegal() + " / " + pvp1.getKills() + "</font>");
				tb.append("</td></tr>");
			}
			else
			{
				// legal kills on me:
				tb.append("<tr><td width=135 height=22 align=left>");
				tb.append("<font color=ae9977>Legal Kills on Me</font>");
				tb.append("</td>");
				tb.append("<td width=135 height=22 align=left>");
				tb.append("<font color=FF00FF>" + pvp1.getKillsLegal() + "</font>");
				tb.append("</td></tr>");
			}

			tb.append("</table>");
		}

		if (player.equals(playerTarget) && ExternalConfig.CUSTOM_PVP_RANK_POINTS_REWARD_ENABLED)
		{
			if (ExternalConfig.CUSTOM_PVP_RANK_POINTS_REWARD_ENABLED)
			{
				player._RankPvpSystemPointsReward = new RankPvpSystemPointsReward(player);
			}

			if (player._RankPvpSystemPointsReward != null && player._RankPvpSystemPointsReward.getRankRewardsCount() > 0)
			{
				// button Get Reward:
				tb.append("<table border=0 cellspacing=0 cellpadding=0>");

				// span
				tb.append("<tr><td width=270 HEIGHT=1><img src=\"L2UI.Squaregray\" width=\"270\" height=\"1\"></img></td></tr>");
				tb.append("<tr><td width=270 height=12></td></tr>");

				tb.append("<tr><td width=270 align=center><button value=\"Get Reward\" action=\"bypass -h _cprs_reward\" back=\"l2ui_ch3.smallbutton2_down\" width=65 height=20 fore=\"l2ui_ch3.smallbutton2\"></td></tr>");

				tb.append("</table>");
			}
		}

		if (ExternalConfig.CUSTOM_PVP_DEATH_MANAGER_DETAILS_ENABLED && player._RankPvpSystemDeathMgr != null && player.isDead() && playerTarget.getObjectId() == player._RankPvpSystemDeathMgr.getKiller().getObjectId())
		{ // playerTarget is not real target its handler to current killer. //getKiller() store last killer.
			// button show equipment:
			tb.append("<table border=0 cellspacing=0 cellpadding=0>");

			// span
			tb.append("<tr><td width=270 HEIGHT=1><img src=\"L2UI.Squaregray\" width=\"270\" height=\"1\"></img></td></tr>");
			tb.append("<tr><td width=270 height=12></td></tr>");

			tb.append("<tr><td width=270 align=center><button value=\"Details\" action=\"bypass -h _cprs_equip\" back=\"l2ui_ch3.smallbutton2_down\" width=65 height=20 fore=\"l2ui_ch3.smallbutton2\"></td></tr>");

			tb.append("</table>");
		}
		else
		{
			player._RankPvpSystemDeathMgr = null;
		}

		tb.append("</center></body></html>");

		return tb;
	}

	/**
	 * Generate HTML table for images.
	 * 
	 * @param player
	 * @param targetPvpStats
	 * @return
	 */
	private TextBuilder rankImgTableHtml(L2PcInstance player, PvpStats targetPvpStats)
	{
		TextBuilder tb = new TextBuilder();
		if (targetPvpStats.getRank().getId() > 0)
		{
			tb.append("<table cellpadding=0 cellspacing=0 border=0 width=292 height=60 width=292>");
			// rank image
			tb.append("<tr><td width=60 height=60>");
			tb.append(ServerSideImage.putImgHtmlTag(player, (50200 + targetPvpStats.getRank().getId()), "custom_pvp_system/rank/rank_" + targetPvpStats.getRank().getId(), 60, 60).toString());
			// rank label
			tb.append("</td><td width=232 height=60 align=left>");
			tb.append(ServerSideImage.putImgHtmlTag(player, (50300 + targetPvpStats.getRank().getId()), "custom_pvp_system/rank_name/rank_name_" + targetPvpStats.getRank().getId(), 232, 60).toString());
			tb.append("</td></tr>");
			tb.append("</table>");
		}
		else
		{
			_log.info("dds param:" + targetPvpStats.getRank().getId());
		}
		return tb;
	}

	private TextBuilder expBelt(L2PcInstance player, PvpStats targetPvpStats)
	{
		int percent = calculatePercent(targetPvpStats);

		TextBuilder tb = new TextBuilder();

		// percent belt
		tb.append("<table border=0 cellspacing=0 cellpadding=0>");

		tb.append("<tr>");
		tb.append("<td width=292 height=20 align=left>");
		if (percent >= 0)
		{
			tb.append(ServerSideImage.putImgHtmlTag(player, (50000 + percent), "custom_pvp_system/exp/exp_" + percent, 292, 20).toString());
		}
		else
		{
			_log.info("percent: " + percent);
		}
		tb.append("</td>");
		tb.append("</tr>");
		tb.append("<tr>");
		tb.append("<td width=292 height=18></td>");
		tb.append("</tr>");

		tb.append("</table>");

		return tb;
	}

	private int calculatePercent(PvpStats targetPvpStats)
	{
		long nextRP = 0;
		long minRP = targetPvpStats.getRank().getMinPoints();
		long currentRP = targetPvpStats.getTotalRankPoints();
		int percent = 0;

		int rankId = targetPvpStats.getRank().getId();
		if (ExternalConfig.CUSTOM_PVP_RANKS.containsKey(rankId + 1))
		{ // check if next rank exists
			nextRP = ExternalConfig.CUSTOM_PVP_RANKS.get(rankId + 1).getMinPoints();
		}

		if (nextRP >= minRP)
		{
			double a = (currentRP - minRP);
			double b = (nextRP - minRP);
			double calc = (a / b) * 100;
			percent = (int) Math.floor(calc);
		}
		else
		{
			percent = 100;
		}

		// _log.info("NRP:"+nextRP+", CRP:"+currentRP+", MRP:"+minRP+", %:"+ percent);

		return percent;
	}
}
