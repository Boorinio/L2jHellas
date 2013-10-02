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
package com.l2jhellas.gameserver.handler.admincommandhandlers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.Shutdown;
import com.l2jhellas.gameserver.controllers.GameTimeController;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * This class handles following admin commands:
 * - server_shutdown [sec] = shows menu or shuts down server in sec seconds
 */
public class AdminShutdown implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{/** @formatter:off */
		"admin_server_shutdown",
		"admin_server_restart",
		"admin_server_abort"
	};/** @formatter:on */

	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_server_shutdown"))
		{
			try
			{
				int val = Integer.parseInt(command.substring(22));
				serverShutdown(activeChar, val, false);
			}
			catch (StringIndexOutOfBoundsException e)
			{
				sendHtmlForm(activeChar);
			}
		}
		else if (command.startsWith("admin_server_restart"))
		{
			try
			{
				int val = Integer.parseInt(command.substring(21));
				serverShutdown(activeChar, val, true);
			}
			catch (StringIndexOutOfBoundsException e)
			{
				sendHtmlForm(activeChar);
			}
		}
		else if (command.startsWith("admin_server_abort"))
		{
			serverAbort(activeChar);
		}

		return true;
	}

	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}

	private void sendHtmlForm(L2PcInstance activeChar)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		int t = GameTimeController.getInstance().getGameTime();
		int h = t / 60;
		int m = t % 60;
		SimpleDateFormat format = new SimpleDateFormat("h:mm a");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, h);
		cal.set(Calendar.MINUTE, m);
		adminReply.setFile("data/html/admin/shutdown.htm");
		adminReply.replace("%count%", String.valueOf(L2World.getAllPlayersCount()));
		adminReply.replace("%used%", String.valueOf(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
		adminReply.replace("%xp%", String.valueOf(Config.RATE_XP));
		adminReply.replace("%sp%", String.valueOf(Config.RATE_SP));
		adminReply.replace("%adena%", String.valueOf(Config.RATE_DROP_ADENA));
		adminReply.replace("%drop%", String.valueOf(Config.RATE_DROP_ITEMS));
		adminReply.replace("%time%", String.valueOf(format.format(cal.getTime())));
		activeChar.sendPacket(adminReply);
	}

	private void serverShutdown(L2PcInstance activeChar, int seconds, boolean restart)
	{
		Shutdown.getInstance().startShutdown(activeChar, null, seconds, restart);
	}

	private void serverAbort(L2PcInstance activeChar)
	{
		Shutdown.getInstance().abort(activeChar);
	}
}