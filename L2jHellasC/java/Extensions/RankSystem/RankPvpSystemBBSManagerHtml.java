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
import java.util.Calendar;

import javolution.text.TextBuilder;

import com.l2jhellas.ExternalConfig;
import com.l2jhellas.L2DatabaseFactory;
import com.l2jhellas.gameserver.cache.HtmCache;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author Masterio
 */
public final class RankPvpSystemBBSManagerHtml
{
	/*
	 * define static fields for H5 CB Style
	 * private static final int width_table = 770;
	 * private static final int width_index = 80;
	 * private static final int width_name = 180;
	 * private static final int width_level = 60;
	 * private static final int width_class = 150;
	 * private static final int width_col5 = 150;
	 * private static final int width_col6 = 150;
	 */
	private static final int width_table = 610;
	private static final int width_index = 40;
	private static final int width_name = 150;
	private static final int width_level = 40;
	private static final int width_class = 140;
	private static final int width_col5 = 120;
	private static final int width_col6 = 120;
	
	public String prepareHtmlResponse(L2PcInstance activeChar, int page)
	{
		// calendar variable require for CommBoard //
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		long date_from = calendar.getTimeInMillis();
		
		String content = null;
		
		content = HtmCache.getInstance().getHtm("data/html/CommunityBoard/pvp/pvp_top.htm");
		if (content == null)
		{
			content = "<html><body><br><br><center>404 :File Not found!' </center></body></html>";
		}
		else
		{
			content = content.replace("%header%", headerHtml(page));
			if (ExternalConfig.RANK_PVP_ENABLED)
			{
				content = content.replace("%button_1%", nextButton(page));
				content = content.replace("%button_2%", previousButton(page));
			}
			else
			{
				content = content.replace("%button_1%", " ");
				content = content.replace("%button_2%", " ");
			}
			content = content.replace("%top_10_list%", topListHtml(activeChar, page, date_from).toString());
			// content = content.replace("%player_data%",
			// playerInfoHtml(activeChar, page, date_from).toString());
		}
		return content;
	}
	
	private String headerHtml(int page)
	{
		if (page == 1)
		{
			return "TOP 10 Rank Point Gatherers";
		}
		
		return "TOP 10 PvP Killers";
	}
	
	private TextBuilder topListHeaderHtml(int page)
	{
		TextBuilder tb = new TextBuilder();
		tb.append("<table border=0 cellspacing=0 cellpadding=4 width=" + width_table + " height=24>");
		tb.append("<tr>");
		tb.append("<td FIXWIDTH=" + width_index + " height=24 align=center></td>");
		tb.append("<td FIXWIDTH=" + width_name + " height=24 align=center><font color=AE9977>Name</font></td>");
		tb.append("<td FIXWIDTH=" + width_level + " height=24 align=center><font color=AE9977>Lv</font></td>");
		tb.append("<td FIXWIDTH=" + width_class + " height=24 align=center><font color=AE9977>Main Class</font></td>");
		if (page == 1)
		{
			tb.append("<td FIXWIDTH=" + width_col5 + " height=24 align=center><font color=AE9977>Total Rank Points</font></td>");
			tb.append("<td FIXWIDTH=" + width_col6 + " height=24 align=center><font color=AE9977>Rank Points Today</font></td>");
		}
		else
		{
			tb.append("<td FIXWIDTH=" + width_col5 + " height=24 align=center><font color=AE9977>Total Legal Kills</font></td>");
			tb.append("<td FIXWIDTH=" + width_col6 + " height=24 align=center><font color=AE9977>Legal Kills Today</font></td>");
		}
		tb.append("</tr>");
		tb.append("</table>");
		return tb;
	}
	
	private TextBuilder topListHtml(L2PcInstance activeChar, int page, long date_from)
	{
		TextBuilder tb = new TextBuilder();
		tb.append("<table border=0 cellspacing=0 cellpadding=2 width=" + width_table + ">");
		
		tb.append("<tr><td width=" + width_table + ">");
		tb.append(topListHeaderHtml(page));
		tb.append("</td></tr>");
		tb.append("<tr>");
		tb.append("<td FIXWIDTH=" + width_table + " HEIGHT=1><img src=\"L2UI.Squaregray\" width=\"" + width_table + "\" height=\"1\"></td>");
		tb.append("</tr>");

		boolean isEmpty = true;
		
		// for store activeChar position/info
		int killer_id = 0;
		int level = 0;
		long col5 = 0;
		long col6 = 0;
		String char_name = "";
		int base_class = 0;
		int killer_idx = 0;
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rset = null;
		
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			statement = con.prepareStatement("CALL CPS_BBS_top(?,?)"); // query
																		// update
																		// points
			statement.setInt(1, page);
			statement.setLong(2, date_from);
			
			rset = statement.executeQuery();

			int idx = 0;
			
			while (rset.next())
			{
				idx++; // counts current player position.
				
				if (activeChar.getObjectId() == rset.getInt("killer_id"))
				{ // store killer position and data.
					killer_id = rset.getInt("killer_id");
					level = rset.getInt("level");
					col5 = rset.getLong("col5");
					col6 = rset.getLong("col6");
					char_name = rset.getString("char_name");
					base_class = rset.getInt("base_class");
					killer_idx = idx;
				}
				
				if (idx <= 10)
				{
					tb.append(itemHtml(idx, rset.getString("char_name"), rset.getInt("level"), RankPvpSystemUtil.getClassName(rset.getInt("base_class")), rset.getLong("col5"), rset.getLong("col6")));
					
					if (isEmpty)
					{
						isEmpty = false;
					}
				}
				else if (idx > 10 && killer_id > 0)
				{
					break;
				}
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
		
		if (isEmpty)
		{
			tb.append("<tr>");
			tb.append("<td FIXWIDTH=" + width_table + " HEIGHT=22 align=center>List clear</td>");
			tb.append("</tr>");
		}

		// Player position and data:
		tb.append(playerInfoHeaderHtml());
		if (killer_id > 0)
		{
			tb.append(itemHtml(killer_idx, char_name, level, RankPvpSystemUtil.getClassName(base_class), col5, col6));
		}
		else
		{
			tb.append("<tr>");
			tb.append("<td FIXWIDTH=" + width_table + " HEIGHT=22 align=center><table cellpadding=2 width=" + width_table + "><tr><td align=center>You have not kill yet</td></tr></table></td>");
			tb.append("</tr>");
		}
		
		tb.append("</table>");
		return tb;
	}
	
	private TextBuilder itemHtml(int indexNo, String name, int lvl, String className, long col5, long col6)
	{
		TextBuilder tb = new TextBuilder();
		
		tb.append("<tr><td width=" + width_table + " height=22>");
		
		tb.append("<table border=0 cellspacing=0 cellpadding=2 width=" + width_table + ">");
		tb.append("<tr>");
		
		// indexNo
		tb.append("<td width=" + width_index + " height=20 align=center><font color=AE9977>" + indexNo + "</font></td>");
		// name
		tb.append("<td width=" + width_name + " height=20 align=left>" + name + "</td>");
		// level
		tb.append("<td width=" + width_level + " height=20 align=center>" + lvl + "</td>");
		// className
		tb.append("<td width=" + width_class + " height=20 align=center>" + className + "</td>");
		// col5, kills or points
		tb.append("<td width=" + width_col5 + " height=20 align=center>" + col5 + "</td>");
		// col6
		tb.append("<td width=" + width_col6 + " height=20 align=center>" + col6 + "</td>");
		
		tb.append("</tr>");
		tb.append("</table>");
		
		tb.append("</td></tr>");
		tb.append("<tr>");
		tb.append("<td FIXWIDTH=" + width_table + " HEIGHT=1><img src=\"L2UI.Squaregray\" width=\"" + width_table + "\" height=\"1\"></td>");
		tb.append("</tr>");
		
		return tb;
	}
	
	private TextBuilder playerInfoHeaderHtml()
	{
		TextBuilder tb = new TextBuilder();
		
		tb.append("<tr><td width=" + width_table + " height=26>");
		
		tb.append("<table border=0 cellspacing=0 cellpadding=2 width=" + width_table + " height=26 bgcolor=FF0000>");
		tb.append("<tr>");
		tb.append("<td WIDTH=" + width_table + " align=center>PLAYER POSITION</td>");
		tb.append("</tr>");
		tb.append("</table>");
		
		tb.append("</td></tr>");
		
		tb.append("<tr><td height=12></td></tr>");
		
		return tb;
	}
	
	private String nextButton(int page)
	{
		if (page == 1)
		{
			return "<button value=\">>\" action=\"bypass _bbscprs;0\" back=\"l2ui_ch3.smallbutton2_down\" width=65 height=20 fore=\"l2ui_ch3.smallbutton2\">";
		}
		
		return "<button value=\">>\" action=\"bypass _bbscprs;1\" back=\"l2ui_ch3.smallbutton2_down\" width=65 height=20 fore=\"l2ui_ch3.smallbutton2\">";
		
	}
	
	private String previousButton(int page)
	{
		if (page == 1)
		{
			return "<button value=\"<<\" action=\"bypass _bbscprs;0\" back=\"l2ui_ch3.smallbutton2_down\" width=65 height=20 fore=\"l2ui_ch3.smallbutton2\">";
		}
		
		return "<button value=\"<<\" action=\"bypass _bbscprs;1\" back=\"l2ui_ch3.smallbutton2_down\" width=65 height=20 fore=\"l2ui_ch3.smallbutton2\">";
		
	}
}