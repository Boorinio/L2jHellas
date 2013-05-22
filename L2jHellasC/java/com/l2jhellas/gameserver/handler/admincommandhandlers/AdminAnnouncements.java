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

import java.util.StringTokenizer;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.Announcements;
import com.l2jhellas.gameserver.handler.AutoAnnouncementHandler;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

/**
 * This class handles following admin commands:
 * - announce text = announces text to all players
 * - list_announcements = show menu
 * - reload_announcements = reloads announcements from txt file
 * - announce_announcements = announce all stored announcements to all players
 * - add_announcement text = adds text to startup announcements
 * - del_announcement id = deletes announcement with respective id
 */
public class AdminAnnouncements implements IAdminCommandHandler
{

	private static final String[] ADMIN_COMMANDS =
	{/** @formatter:off */
		"admin_list_announcements",
		"admin_reload_announcements",
		"admin_announce_announcements",
		"admin_add_announcement",
		"admin_del_announcement",
		"admin_announce",
		"admin_announce_menu"
	};/** @formatter:on */

	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.equals("admin_list_announcements"))
		{
			Announcements.getInstance().listAnnouncements(activeChar);
		}
		else if (command.equals("admin_reload_announcements"))
		{
			Announcements.getInstance().loadAnnouncements();
			Announcements.getInstance().listAnnouncements(activeChar);
		}
		else if (command.startsWith("admin_announce_menu"))
		{
			Announcements sys = new Announcements();
			sys.handleAnnounce(command, 20);
			Announcements.getInstance().listAnnouncements(activeChar);
		}
		else if (command.equals("admin_announce_announcements"))
		{
			for (L2PcInstance player : L2World.getInstance().getAllPlayers())
			{
				Announcements.getInstance().showAnnouncements(player);
			}
			Announcements.getInstance().listAnnouncements(activeChar);
		}
		else if (command.startsWith("admin_add_announcement"))
		{
			// FIXME the player can send only 16 chars (if you try to send more
			// it sends null), remove this function or not?
			if (!command.equals("admin_add_announcement"))
			{
				try
				{
					String val = command.substring(23);
					Announcements.getInstance().addAnnouncement(val);
					Announcements.getInstance().listAnnouncements(activeChar);
				}
				catch (StringIndexOutOfBoundsException e)
				{
				}
			}
		}
		else if (command.startsWith("admin_del_announcement"))
		{
			try
			{
				int val = new Integer(command.substring(23)).intValue();
				Announcements.getInstance().delAnnouncement(val);
				Announcements.getInstance().listAnnouncements(activeChar);
			}
			catch (StringIndexOutOfBoundsException e)
			{
			}
		}
		else if (command.startsWith("admin_announce"))
		{
			if (Config.GM_ANNOUNCER_NAME)
			{
				command += " [" + activeChar.getName() + "]";
			}
			Announcements.getInstance().handleAnnounce(command, 15);
		}
		else if (command.equals("admin_list_autoannouncements"))
		{
			AutoAnnouncementHandler.getInstance().listAutoAnnouncements(activeChar);
		}
		else if (command.startsWith("admin_add_autoannouncement"))
		{
			// FIXME the player can send only 16 chars (if you try to send more
			// it sends null), remove this function or not?
			if (!command.equals("admin_add_autoannouncement"))
			{
				try
				{
					StringTokenizer st = new StringTokenizer(command.substring(27));
					int delay = Integer.parseInt(st.nextToken().trim());
					String autoAnnounce = st.nextToken();
					if (delay > 30)
					{
						while (st.hasMoreTokens())
						{
							autoAnnounce = autoAnnounce + " " + st.nextToken();
						};
						AutoAnnouncementHandler.getInstance().registerAnnouncment(autoAnnounce, delay);
						AutoAnnouncementHandler.getInstance().listAutoAnnouncements(activeChar);
					}
				}
				catch (StringIndexOutOfBoundsException e)
				{
				}
			}
		}
		else if (command.startsWith("admin_del_autoannouncement"))
		{
			try
			{
				int val = new Integer(command.substring(27)).intValue();
				AutoAnnouncementHandler.getInstance().removeAnnouncement(val);
				AutoAnnouncementHandler.getInstance().listAutoAnnouncements(activeChar);
			}
			catch (StringIndexOutOfBoundsException e)
			{
			}
		}
		else if (command.startsWith("admin_autoannounce"))
		{
			AutoAnnouncementHandler.getInstance().listAutoAnnouncements(activeChar);
		}
		return true;
	}

	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}