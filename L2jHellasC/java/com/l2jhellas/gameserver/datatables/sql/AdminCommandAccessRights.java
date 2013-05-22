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
package com.l2jhellas.gameserver.datatables.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastMap;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.model.L2AccessLevel;
import com.l2jhellas.gameserver.model.L2AdminCommandAccessRight;
import com.l2jhellas.util.database.L2DatabaseFactory;

/**
 * @author FBIagent
 */
public class AdminCommandAccessRights
{
	private static Logger _log = Logger.getLogger(AdminCommandAccessRights.class.getName());

	private Map<String, L2AdminCommandAccessRight> _adminCommandAccessRights;

	/**
	 * Returns the one and only instance of this class<br>
	 * <br>
	 * 
	 * @return AdminCommandAccessRights: the one and only instance of this class<br>
	 */
	public static AdminCommandAccessRights getInstance()
	{
		return SingletonHolder._instance;
	}

	/** The access rights<br> */
	private AdminCommandAccessRights()
	{
		loadAdminCommandAccessRights();
	}

	/**
	 * Loads admin command access rights from database<br>
	 */
	private void loadAdminCommandAccessRights()
	{
		_adminCommandAccessRights = new FastMap<String, L2AdminCommandAccessRight>();

		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM admin_command_access_rights");
			ResultSet rset = stmt.executeQuery();
			String adminCommand = null;
			String accessLevels = null;

			while (rset.next())
			{
				adminCommand = rset.getString("adminCommand");
				accessLevels = rset.getString("accessLevels");
				_adminCommandAccessRights.put(adminCommand, new L2AdminCommandAccessRight(adminCommand, accessLevels));
			}
			rset.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Error loading from database:" + e);
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}

		_log.log(Level.INFO, getClass().getSimpleName() + ": Loaded " + _adminCommandAccessRights.size() + " from database.");
	}

	public boolean hasAccess(String adminCommand, L2AccessLevel accessLevel)
	{
		if (accessLevel.getLevel() == AccessLevels._masterAccessLevelNum)
			return true;

		L2AdminCommandAccessRight acar = _adminCommandAccessRights.get(adminCommand);

		if (acar == null)
		{
			_log.log(Level.INFO, getClass().getSimpleName() + ": No rights defined for admin command " + adminCommand + ".");
			return false;
		}

		return acar.hasAccess(accessLevel);
	}

	public void reloadAdminCommandAccessRights()
	{
		loadAdminCommandAccessRights();
	}

	@SuppressWarnings("synthetic-access")
	private static class SingletonHolder
	{
		protected static final AdminCommandAccessRights _instance = new AdminCommandAccessRights();
	}
}