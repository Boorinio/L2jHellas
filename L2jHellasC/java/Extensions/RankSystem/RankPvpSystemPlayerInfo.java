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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javolution.text.TextBuilder;

import com.l2jhellas.ExternalConfig;
import com.l2jhellas.L2DatabaseFactory;
import com.l2jhellas.gameserver.datatables.ItemTable;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * @author Masterio
 */
public class RankPvpSystemPlayerInfo
{
	
	// private static final boolean DEBUG_MODE = false;
	
	private int _rankPosition = -1; // DO NOT CHANGE IT!
	
	public void sendPlayerResponse(L2PcInstance player, L2PcInstance playerTarget)
	{
		NpcHtmlMessage n = new NpcHtmlMessage(0);
		n.setHtml(playerResponseHtml(player, playerTarget).toString());
		player.sendPacket(n);
	}
	
	private TextBuilder playerResponseHtml(L2PcInstance player, L2PcInstance playerTarget)
	{
		TextBuilder tb = new TextBuilder();

		int target_kills = 0;
		// int target_kills_today = 0;
		int target_kills_legal = 0;
		// int target_kills_today_legal = 0;
		long target_rank_points = 0;
		// long target_rank_points_today = 0;
		long target_kill_time = 0;
		int target_killer_kills = 0;
		int target_killer_kills_legal = 0;
		int killer_kills_today_calc = 0; // with time protection reset.
		
		int war_kills_legal = 0;
		int war_kills = 0;

		long sys_time = System.currentTimeMillis();
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rset = null;
		
		try
		{
			long time = sys_time - ExternalConfig.RANK_PVP_PROTECTION_RESET * 60 * 1000;
			
			con = L2DatabaseFactory.getInstance().getConnection();
			statement = con.prepareStatement("CALL CPS_pvpinfo(?,?,?)");
			statement.setInt(1, player.getObjectId());
			statement.setInt(2, playerTarget.getObjectId());
			statement.setLong(3, time);

			rset = statement.executeQuery();
			
			while (rset.next())
			{
				target_kills = rset.getInt("col1");
				// target_kills_today = rset.getInt("col2");
				target_kills_legal = rset.getInt("col3");
				// target_kills_today_legal = rset.getInt("col4");
				target_rank_points = rset.getLong("col5");
				// target_rank_points_today = rset.getLong("col6");
				target_kill_time = rset.getLong("victim_kill_time");
				target_killer_kills = rset.getInt("victim_killer_kills");
				target_killer_kills_legal = rset.getInt("victim_killer_kills_legal");
				killer_kills_today_calc = rset.getInt("killer_kills_today");
				
				war_kills_legal = rset.getInt("war_kills_legal");
				war_kills = rset.getInt("war_kills");

				break;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				if (statement != null)
				{
					statement.close();
					statement = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		int tab_pointer = this.getRankId(target_rank_points);
		
		tb.append("<html><title>" + playerTarget.getName() + " PvP Status</title><body>");
		
		// Ranks images, Exp belt.
		_rankPosition = -1;
		if (ExternalConfig.RANK_PVP_NAME.size() > 0)
		{
			_rankPosition = tab_pointer;
		}
		
		tb.append(rankImgTableHtml(player));
		if (player.equals(playerTarget))
		{
			tb.append(expBelt(player, target_rank_points));
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
		
		// span
		tb.append("<tr><td width=135 HEIGHT=1><img src=\"L2UI.Squaregray\" width=\"135\" height=\"1\"></img></td><td width=135 HEIGHT=1><img src=\"L2UI.Squaregray\" width=\"135\" height=\"1\"></img></td></tr>");
		tb.append("<tr><td width=135 height=12></td><td width=135 height=12></td></tr>");
		
		if (ExternalConfig.RANK_PVP_ENABLED && tab_pointer >= 0)
		{ // TODO check conditions, maybe I'll add some.

			tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Rank</font></td>");
			tb.append("<td width=135 height=22 align=left>");
			tb.append("<font color=ffff00>" + ExternalConfig.RANK_PVP_NAME.get(tab_pointer) + "</font>");
			tb.append("</td></tr>");

			tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Rank Points</font></td>");
			tb.append("<td width=135 height=22 align=left>");
			tb.append("<font color=ffa000>" + target_rank_points + "</font>");
			tb.append("</td></tr>");
			
		}

		// legal/total kills
		tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Legal/Total Kills</font></td>");
		tb.append("<td width=135 height=22 align=left>");
		tb.append("<font color=ffa000>" + target_kills_legal + " / " + target_kills + "</font>");
		tb.append("</td></tr>");
		
		if (ExternalConfig.RANK_PVP_WAR_ENABLED)
		{
			// war legal/total kills
			tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Legal/Total War Kills</font></td>");
			tb.append("<td width=135 height=22 align=left>");
			tb.append("<font color=2080D0>" + war_kills_legal + " / " + war_kills + "</font>");
			tb.append("</td></tr>");
		}
		
		// span
		tb.append("<tr><td width=135 HEIGHT=1><img src=\"L2UI.Squaregray\" width=\"135\" height=\"1\"></img></td><td width=135 HEIGHT=1><img src=\"L2UI.Squaregray\" width=\"135\" height=\"1\"></img></td></tr>");
		tb.append("<tr><td width=135 height=12></td><td width=135 height=12></td></tr>");
		
		if (ExternalConfig.RANK_PVP_ENABLED && tab_pointer >= 0)
		{
			
			tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>RP for kill</font></td>");
			tb.append("<td width=135 height=22 align=left>");
			tb.append("<font color=ffa000>" + getPointsForKill(killer_kills_today_calc, target_rank_points) + "</font>");
			tb.append("</td></tr>");
			
			if (ExternalConfig.RANK_PVP_REWARD_ENABLED)
			{
				tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Rank Reward</font></td>");
				tb.append("<td width=135 height=22 align=left>");
				tb.append("<font color=ffa000>" + ExternalConfig.RANK_PVP_REWARD_AMOUNTS.get(tab_pointer) + "x " + ItemTable.getInstance().getTemplate(ExternalConfig.RANK_PVP_REWARD_IDS.get(tab_pointer)).getName() + "</font>");
				tb.append("</td></tr>");
			}
			
		}
		
		if (ExternalConfig.RANK_PVP_REWARD_ENABLED)
		{ // TODO check conditions, maybe I'll add some.
			tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>PvP Reward</font></td>");
			tb.append("<td width=135 height=22 align=left>");
			tb.append("<font color=ffa000>" + ExternalConfig.RANK_PVP_REWARD_AMOUNT + "x " + ItemTable.getInstance().getTemplate(ExternalConfig.RANK_PVP_REWARD_ID).getName() + "</font>");
			tb.append("</td></tr>");
		}
		
		if (!player.equals(playerTarget) && ExternalConfig.RANK_PVP_PROTECTION_RESET > 0 && (ExternalConfig.RANK_PVP_REWARD_PROTECTION > 0 || ExternalConfig.RANK_PVP_PROTECTION > 0))
		{
			if (ExternalConfig.RANK_PVP_PROTECTION_RESET > 0 && (sys_time - (1000 * 60 * ExternalConfig.RANK_PVP_PROTECTION_RESET) < target_kill_time))
			{ // show time to legal kill
				if (ExternalConfig.RANK_PVP_REWARD_PROTECTION > 0 && ExternalConfig.RANK_PVP_PROTECTION > 0)
				{
					tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>RP/Reward Protection</font></td>");
				}
				else if (ExternalConfig.RANK_PVP_PROTECTION > 0)
				{
					tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Rank Points Protection</font></td>");
				}
				else if (ExternalConfig.RANK_PVP_REWARD_PROTECTION > 0)
				{
					tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Reward Protection</font></td>");
				}
				tb.append("<td width=135 height=22 align=left>");
				tb.append("<font color=FFFF00>" + RankPvpSystem.calculateTimeToString(sys_time, target_kill_time) + "</font>");
				tb.append("</td></tr>");
			}
			else
			{
				if (ExternalConfig.RANK_PVP_REWARD_PROTECTION > 0 && ExternalConfig.RANK_PVP_PROTECTION > 0)
				{
					tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>RP/Reward Protection</font></td>");
				}
				else if (ExternalConfig.RANK_PVP_PROTECTION > 0)
				{
					tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>Rank Points Protection</font></td>");
				}
				else if (ExternalConfig.RANK_PVP_REWARD_PROTECTION > 0)
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
			// about player
			tb.append("<table border=0 cellspacing=0 cellpadding=0>");
			
			// span
			tb.append("<tr><td width=135 HEIGHT=1><img src=\"L2UI.Squaregray\" width=\"135\" height=\"1\"></img></td><td width=135 HEIGHT=1><img src=\"L2UI.Squaregray\" width=\"135\" height=\"1\"></img></td></tr>");
			tb.append("<tr><td width=135 height=12></td><td width=135 height=12></td></tr>");
			
			// name
			tb.append("<tr><td width=135 height=22 align=left>");
			tb.append("<font color=ae9977>Legal/Total Kills on Me</font>");
			tb.append("</td>");
			tb.append("<td width=135 height=22 align=left>");
			tb.append("<font color=FF00FF>" + target_killer_kills_legal + " / " + target_killer_kills + "</font>");
			tb.append("</td></tr>");
			
			tb.append("</table>");
		}
		
		if (player.equals(playerTarget) && ExternalConfig.RANK_PVP_POINTS_REWARD_ENABLED)
		{
			if (ExternalConfig.RANK_PVP_POINTS_REWARD_ENABLED)
			{
				player._RankPvpSystemPointsReward = new RankPvpSystemPointsReward(player);
			}
			
			if (player._RankPvpSystemPointsReward != null && player._RankPvpSystemPointsReward._rankRewardsCount > 0)
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
		
		if (ExternalConfig.RANK_PVP_DEATH_MANAGER_DETAILS_ENABLED && player._RankPvpSystemDeathMgr != null && player.isDead() && playerTarget.getObjectId() == player._RankPvpSystemDeathMgr.getKiller().getObjectId())
		{ // playerTarget is not real target its handler to current killer.
			// //getKiller() store last killer.
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
	 * Special method used for obtain position of rank in property array.<br>
	 * Positions: [0 .. (size()-1)].
	 * 
	 * @param rank_points
	 * @return
	 */
	private int getRankId(long rank_points)
	{
		if (ExternalConfig.RANK_PVP_MIN_POINTS.size() > 0 && ExternalConfig.RANK_PVP_NAME.size() > 0 && ExternalConfig.RANK_PVP_MIN_POINTS.size() == ExternalConfig.RANK_PVP_NAME.size())
		{
			if (rank_points >= ExternalConfig.RANK_PVP_MIN_POINTS.get(0))
			{
				return 0;
			}
			for (int i = ExternalConfig.RANK_PVP_MIN_POINTS.size() - 1; i >= 0; i--)
			{
				if (rank_points < ExternalConfig.RANK_PVP_MIN_POINTS.get(i))
				{
					return i;
				}
			}
		}
		return -1;
	}
	
	/**
	 * Calculate rank points awarded for kill victim.
	 * IMPORTANT: because method executed before kill the target kills_today
	 * starts from 0 not from 1.
	 * 
	 * @param kills_today
	 *        - killer kills_today (mysql::sum(kills_today) ... WHERE victim_id
	 *        = victimId()).
	 * @param rank_points
	 *        - victim total rank_points (mysql::sum(rank_points)).
	 * @return Rank points for kill victim.
	 */
	private int getPointsForKill(int kills_today, long rank_points)
	{
		// if(kills_today < ExternalConfig.RANK_PVP_PROTECTION ||
		// ExternalConfig.RANK_PVP_PROTECTION == 0){
		if (ExternalConfig.RANK_PVP_KILL_POINTS_DOWN_ENABLED)
		{
			for (int i = 0; i < ExternalConfig.RANK_PVP_KILL_POINTS_DOWN.size(); i++)
			{
				if (i + 1 == kills_today)
				{
					return ExternalConfig.RANK_PVP_KILL_POINTS_DOWN.get(i);
				}
			}
		}
		else
		{
			if (ExternalConfig.RANK_PVP_MIN_POINTS.size() > 0 && ExternalConfig.RANK_PVP_POINTS_FOR_KILL.size() > 0 && ExternalConfig.RANK_PVP_MIN_POINTS.size() == ExternalConfig.RANK_PVP_POINTS_FOR_KILL.size())
			{
				if (rank_points >= ExternalConfig.RANK_PVP_MIN_POINTS.get(0))
				{
					return ExternalConfig.RANK_PVP_POINTS_FOR_KILL.get(0);
				}
				for (int i = ExternalConfig.RANK_PVP_MIN_POINTS.size() - 1; i >= 0; i--)
				{
					if (rank_points < ExternalConfig.RANK_PVP_MIN_POINTS.get(i))
					{
						return ExternalConfig.RANK_PVP_POINTS_FOR_KILL.get(i);
					}
				}
			}
		}
		// }
		return 0;
	}
	
	/**
	 * Generate HTML table for images.
	 * 
	 * @param player
	 * @return
	 */
	private TextBuilder rankImgTableHtml(L2PcInstance player)
	{
		TextBuilder tb = new TextBuilder();
		if (ExternalConfig.RANK_PVP_IMAGES_ENABLED)
		{
			tb.append("<table cellpadding=0 cellspacing=0 border=0 width=292 height=60 width=292>");
			
			int rank = ExternalConfig.RANK_PVP_NAME.size() - (_rankPosition);
			// rank image
			tb.append("<tr><td width=60 height=60>");
			tb.append(ServerSideImage.putImgHtmlTag(player, (50200 + rank), "custom_pvp_system/rank/rank_" + rank, 60, 60).toString());
			// rank label
			tb.append("</td><td width=232 height=60 align=left>");
			tb.append(ServerSideImage.putImgHtmlTag(player, (50300 + rank), "custom_pvp_system/rank_name/rank_name_" + rank, 232, 60).toString());
			tb.append("</td></tr>");

			tb.append("</table>");
		}
		return tb;
	}
	
	private TextBuilder expBelt(L2PcInstance player, long currentRP)
	{
		int percent = calculatePercent(currentRP);

		TextBuilder tb = new TextBuilder();
		
		// percent belt
		tb.append("<table border=0 cellspacing=0 cellpadding=0>");
		
		if (ExternalConfig.RANK_PVP_IMAGES_ENABLED)
		{
			tb.append("<tr>");
			tb.append("<td width=292 height=20 align=left>");
			tb.append(ServerSideImage.putImgHtmlTag(player, (50000 + percent), "custom_pvp_system/exp/exp_" + percent, 292, 20).toString());
			tb.append("</td>");
			tb.append("</tr>");
			tb.append("<tr>");
			tb.append("<td width=292 height=18></td>");
			tb.append("</tr>");
		}
		else
		{
			// exp info
			tb.append("<tr><td width=135 height=22 align=left><font color=ae9977>PvP Exp</font></td>");
			tb.append("<td width=135 height=22 align=left>");
			tb.append("<font color=ffa000>" + percent + "%</font>");
			tb.append("</td></tr>");
		}
		tb.append("</table>");

		return tb;
	}
	
	private int calculatePercent(long currentRP)
	{
		int nextRankPoints = 0;
		int minRankPoints = 0;
		int percent = 0;
		
		for (int i = ExternalConfig.RANK_PVP_MIN_POINTS.size() - 1; i >= 0; i--)
		{
			if (currentRP >= ExternalConfig.RANK_PVP_MIN_POINTS.get(i))
			{
				minRankPoints = ExternalConfig.RANK_PVP_MIN_POINTS.get(i);
			}
			else
			{
				nextRankPoints = ExternalConfig.RANK_PVP_MIN_POINTS.get(i);
				break;
			}
		}
		
		if (nextRankPoints >= minRankPoints)
		{ // TODO zmienione na >= nie sprawdzone
			double a = (currentRP - minRankPoints);
			double b = (nextRankPoints - minRankPoints);
			double calc = (a / b) * 100;
			percent = (int) Math.floor(calc);
		}
		else
		{
			percent = 100;
		}
		
		// _log.info("NRP:"+nextRankPoints+", CRP:"+currentRP+", MRP:"+minRankPoints+", %:"+
		// percent);
		
		return percent;
	}
}