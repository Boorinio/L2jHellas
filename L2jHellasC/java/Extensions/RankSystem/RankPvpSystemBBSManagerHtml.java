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

import javolution.text.TextBuilder;
import javolution.util.FastMap;

import com.l2jhellas.ExternalConfig;
import com.l2jhellas.gameserver.cache.HtmCache;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author Masterio
 */
public final class RankPvpSystemBBSManagerHtml
{
	private static final int width_table = 610;
	private static final int width_index = 80;
	private static final int width_name = 170;
	private static final int width_level = 60;
	private static final int width_class = 150;
	private static final int width_col5 = 150;

	public String prepareHtmlResponse(L2PcInstance activeChar, int page)
	{

		String content = null;

		content = HtmCache.getInstance().getHtm("data/html/CommunityBoard/pvp/pvp_top.htm");
		if (content == null)
		{
			content = "<html><body><br><br><center>404 :File Not found!' </center></body></html>";
		}
		else
		{
			content = content.replace("%header%", headerHtml(page));
			if (ExternalConfig.RANKS_ENABLED)
			{
				content = content.replace("%button_1%", nextButton(page));
				content = content.replace("%button_2%", previousButton(page));
			}
			else
			{
				content = content.replace("%button_1%", " ");
				content = content.replace("%button_2%", " ");
			}
			content = content.replace("%top_10_list%", topListHtml(activeChar, page).toString());
			content = content.replace("%refresh_time%", getRefreshTime());
		}
		return content;
	}

	private String headerHtml(int page)
	{
		if (!TopTable.getInstance().isUpdating())
		{
			if (page == 1)
			{
				return "TOP 10 Rank Point Gatherers";
			}

			return "TOP 10 PvP Killers";
		}

		return "TOP 10";
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
		}
		else
		{
			tb.append("<td FIXWIDTH=" + width_col5 + " height=24 align=center><font color=AE9977>Total Legal Kills</font></td>");
		}
		tb.append("</tr>");
		tb.append("</table>");
		return tb;
	}

	private TextBuilder topListHtml(L2PcInstance activeChar, int page)
	{

		TextBuilder tb = new TextBuilder();
		TextBuilder tb2 = new TextBuilder();
		tb2.append(playerInfoHeaderHtml());

		tb.append("<table border=0 cellspacing=0 cellpadding=2 width=" + width_table + " bgcolor=000000>");

		if (!TopTable.getInstance().isUpdating())
		{

			tb.append("<tr><td width=" + width_table + ">");
			tb.append(topListHeaderHtml(page));
			tb.append("</td></tr>");
			tb.append("<tr>");
			tb.append("<td FIXWIDTH=" + width_table + " HEIGHT=1><img src=\"L2UI.Squaregray\" width=\"" + width_table + "\" height=\"1\"></td>");
			tb.append("</tr>");

			boolean isEmpty = true;
			boolean playerInfo = false;
			int pos = 0;

			if (page == 0)
			{
				for (FastMap.Entry<Integer, TopField> e = TopTable.getInstance().getTopKillsTable().head(), end = TopTable.getInstance().getTopKillsTable().tail(); (e = e.getNext()) != end;)
				{
					pos++;

					if (activeChar.getObjectId() == e.getValue().getCharacterId())
					{
						// Player position and data:
						tb2.append(itemHtml(pos, e.getValue().getCharacterName(), e.getValue().getCharacterLevel(), RankPvpSystemUtil.getClassName(e.getValue().getCharacterBaseClassId()), e.getValue().getCharacterPoints()));
						playerInfo = true;
					}

					if (pos <= 10)
					{
						tb.append(itemHtml(pos, e.getValue().getCharacterName(), e.getValue().getCharacterLevel(), RankPvpSystemUtil.getClassName(e.getValue().getCharacterBaseClassId()), e.getValue().getCharacterPoints()));

						if (isEmpty)
						{
							isEmpty = false;
						}
					}
					else if (pos > 10 && playerInfo)
					{
						break;
					}
				}
			}
			else
			{
				for (FastMap.Entry<Integer, TopField> e = TopTable.getInstance().getTopGatherersTable().head(), end = TopTable.getInstance().getTopGatherersTable().tail(); (e = e.getNext()) != end;)
				{
					pos++;

					if (activeChar.getObjectId() == e.getValue().getCharacterId())
					{
						// Player position and data:
						tb2.append(itemHtml(pos, e.getValue().getCharacterName(), e.getValue().getCharacterLevel(), RankPvpSystemUtil.getClassName(e.getValue().getCharacterBaseClassId()), e.getValue().getCharacterPoints()));
						playerInfo = true;
					}

					if (pos <= 10)
					{
						tb.append(itemHtml(pos, e.getValue().getCharacterName(), e.getValue().getCharacterLevel(), RankPvpSystemUtil.getClassName(e.getValue().getCharacterBaseClassId()), e.getValue().getCharacterPoints()));

						if (isEmpty)
						{
							isEmpty = false;
						}
					}
					else if (pos > 10 && playerInfo)
					{
						break;
					}

				}
			}

			if (!playerInfo)
			{
				tb2.append("<tr>");
				if (ExternalConfig.COMMUNITY_BOARD_TOP_LIST_IGNORE_TIME_LIMIT > 0)
				{
					tb2.append("<td FIXWIDTH=" + width_table + " HEIGHT=26 align=center><table cellpadding=2 width=" + width_table + "><tr><td align=center>You're out of 500, or you did not kill anyone or even killed more than " + Math.round((double) ExternalConfig.COMMUNITY_BOARD_TOP_LIST_IGNORE_TIME_LIMIT / 86400000) + " days ago.</td></tr></table></td>");
				}
				else
				{
					tb2.append("<td FIXWIDTH=" + width_table + " HEIGHT=26 align=center><table cellpadding=2 width=" + width_table + "><tr><td align=center>You're out of 500, or you did not kill anyone.</td></tr></table></td>");
				}
				tb2.append("</tr>");
			}

			if (isEmpty)
			{
				tb.append("<tr>");
				tb.append("<td FIXWIDTH=" + width_table + " HEIGHT=26 align=center>List clear</td>");
				tb.append("</tr>");
			}

			tb.append(tb2);

		}
		else
		{ // if is updating:
			tb.append("<tr><td FIXWIDTH=" + width_table + " HEIGHT=130></td></tr>");
			tb.append("<tr><td FIXWIDTH=" + width_table + " HEIGHT=26 align=center><font color=FF8000>Updating... try again for few seconds</font></td></tr>");
			tb.append("<tr><td FIXWIDTH=" + width_table + " HEIGHT=12></td></tr>");
			if (page == 1)
			{
				tb.append("<tr><td FIXWIDTH=" + width_table + " HEIGHT=26 align=center><button value=\"Refresh\" action=\"bypass _bbscprs;1\" width=65 height=20 back=\"l2ui_ch3.smallbutton2_down\" fore=\"l2ui_ch3.smallbutton2\"></td></tr>");
			}
			else
			{
				tb.append("<tr><td FIXWIDTH=" + width_table + " HEIGHT=26 align=center><button value=\"Refresh\" action=\"bypass _bbscprs;0\" width=65 height=20 back=\"l2ui_ch3.smallbutton2_down\" fore=\"l2ui_ch3.smallbutton2\"></td></tr>");
			}
			tb.append("<tr><td FIXWIDTH=" + width_table + " HEIGHT=130></td></tr>");
			tb.append("</tr>");
		}

		tb.append("</table>");
		return tb;
	}

	private TextBuilder itemHtml(int indexNo, String name, int lvl, String className, long col5)
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
		tb.append("<td WIDTH=" + width_table + " align=center>Player Position</td>");
		tb.append("</tr>");
		tb.append("</table>");

		tb.append("</td></tr>");

		tb.append("<tr><td height=12></td></tr>");

		return tb;
	}

	private String nextButton(int page)
	{
		if (!TopTable.getInstance().isUpdating())
		{
			if (page == 0)
			{
				return "<button value=\">>\" action=\"bypass _bbscprs;1\" width=65 height=20 back=\"Button_DF_Calculator_Down\" fore=\"Button_DF_Calculator\">";
			}

			return "&nbsp;";
		}

		return "&nbsp;";
	}

	private String previousButton(int page)
	{
		if (!TopTable.getInstance().isUpdating())
		{
			if (page == 1)
			{
				return "<button value=\"<<\" action=\"bypass _bbscprs;0\" width=65 height=20 back=\"Button_DF_Calculator_Down\" fore=\"Button_DF_Calculator\">";
			}

			return "&nbsp;";
		}

		return "&nbsp;";
	}

	private String getRefreshTime()
	{
		if (ExternalConfig.TOP_TABLE_UPDATE_INTERVAL >= 60000)
		{
			double time = (double) ExternalConfig.TOP_TABLE_UPDATE_INTERVAL / 60000;
			return ((int) Math.floor(time)) + " minutes";
		}

		return "less than 1 minute";

	}
}
