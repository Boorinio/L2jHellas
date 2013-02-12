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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.L2DatabaseFactory;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public class AdminPremium implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
	"admin_premium_menu", "admin_premium_add1", "admin_premium_add2", "admin_premium_add3", "admin_premium_add4", "admin_premium_add5"
	};

	private static final String UPDATE_PREMIUMSERVICE = "UPDATE account_premium SET premium_service=?,enddate=? WHERE account_name=?";
	private static final Logger _log = Logger.getLogger(AdminPremium.class.getName());

	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.equals("admin_premium_menu"))
		{
			AdminHelpPage.showHelpPage(activeChar, "premium_menu.htm");
		}
		else if (command.startsWith("admin_premium_add1"))
		{
			try
			{
				String val = command.substring(19);
				addPremiumServices(1, val);
				AdminHelpPage.showHelpPage(activeChar, "premium_menu.htm");
			}
			catch (StringIndexOutOfBoundsException e)
			{
				activeChar.sendMessage("Premium: problem adding premium.");
			}
		}
		else if (command.startsWith("admin_premium_add2"))
		{
			try
			{
				String val = command.substring(19);
				addPremiumServices(2, val);
				AdminHelpPage.showHelpPage(activeChar, "premium_menu.htm");
			}
			catch (StringIndexOutOfBoundsException e)
			{
				activeChar.sendMessage("Premium: problem adding premium.");
			}
		}
		else if (command.startsWith("admin_premium_add3"))
		{
			try
			{
				String val = command.substring(19);
				addPremiumServices(3, val);
				AdminHelpPage.showHelpPage(activeChar, "premium_menu.htm");
			}
			catch (StringIndexOutOfBoundsException e)
			{
				activeChar.sendMessage("Premium: problem adding premium.");
			}
		}
		else if (command.startsWith("admin_premium_add4"))
		{
			try
			{
				String val = command.substring(19);
				addPremiumServices(4, val);
				AdminHelpPage.showHelpPage(activeChar, "premium_menu.htm");
			}
			catch (StringIndexOutOfBoundsException e)
			{
				activeChar.sendMessage("Premium: problem adding premium.");
			}
		}
		else if (command.startsWith("admin_premium_add5"))
		{
			try
			{
				String val = command.substring(19);
				addPremiumServices(5, val);
				AdminHelpPage.showHelpPage(activeChar, "premium_menu.htm");
			}
			catch (StringIndexOutOfBoundsException e)
			{
				activeChar.sendMessage("Premium: problem adding premium.");
			}
		}
		return true;
	}

	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}

	private void addPremiumServices(int Hours, String AccName)
	{
		Connection con = null;
		try
		{
			Calendar finishtime = Calendar.getInstance();
			finishtime.setTimeInMillis(System.currentTimeMillis());
			finishtime.set(Calendar.SECOND, 0);
			finishtime.add(Calendar.HOUR, Hours);

			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement(UPDATE_PREMIUMSERVICE);
			statement.setInt(1, 1);
			statement.setLong(2, finishtime.getTimeInMillis());
			statement.setString(3, AccName);
			statement.execute();
			statement.close();
		}
		catch (SQLException e)
		{
			_log.log(Level.WARNING, getClass().getName() + " Could not increase data." + e);
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
		finally
		{
			try
			{
				if (con != null)
					con.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
}
