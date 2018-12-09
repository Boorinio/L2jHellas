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

import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.entity.olympiad.Olympiad;
import com.l2jhellas.gameserver.model.entity.olympiad.OlympiadGameManager;
import com.l2jhellas.gameserver.model.entity.olympiad.OlympiadGameTask;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jhellas.util.StringUtil;

public final class RequestOlympiadMatchList extends L2GameClientPacket
{
	private static final String _C__D0_13_REQUESTOLYMPIADMATCHLIST = "[C] D0:13 RequestOlympiadMatchList";

	@Override
	protected void readImpl()
	{
		// trigger packet
	}

	@Override
	protected void runImpl()
	{
		final L2PcInstance activeChar = getClient().getActiveChar();
		
		if (activeChar == null || !activeChar.inObserverMode())
			return;
		
		int is = 0;	
		final StringBuilder sb = new StringBuilder(1500);
		
		for (OlympiadGameTask task : OlympiadGameManager.getInstance().getOlympiadTasks())
		{
			StringUtil.append(sb, "<tr><td fixwidth=10><a action=\"bypass arenachange ", is, "\">", ++is, "</a></td><td fixwidth=80>");
			
			if (task.isGameStarted())
			{
				if (task.isInTimerTime())
					StringUtil.append(sb, "&$907;");
				
				else if (task.isBattleStarted())
					StringUtil.append(sb, "&$829;");
				
				else
					StringUtil.append(sb, "&$908;");
					
				StringUtil.append(sb, "</td><td>", task.getGame().getPlayerNames()[0], "&nbsp; / &nbsp;", task.getGame().getPlayerNames()[1]);
			}
			else
				StringUtil.append(sb, "&$906;", "</td><td>&nbsp;"); // Initial State
				
			StringUtil.append(sb, "</td><td><font color=\"aaccff\"></font></td></tr>");
		}
		
		final NpcHtmlMessage html = new NpcHtmlMessage(0);	
		html.setFile(Olympiad.OLYMPIAD_HTML_PATH + "olympiad_arena_observe_list.htm");
		html.replace("%list%", sb.toString());
		
		activeChar.sendPacket(html);
	}

	@Override
	public String getType()
	{
		return _C__D0_13_REQUESTOLYMPIADMATCHLIST;
	}
}