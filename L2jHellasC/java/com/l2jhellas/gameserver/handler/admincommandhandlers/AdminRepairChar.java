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
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.L2DatabaseFactory;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.GMAudit;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

/**
 * This class handles following admin commands: - delete = deletes target
 *
 * @version $Revision: 1.1.2.6.2.3 $ $Date: 2005/04/11 10:05:59 $
 */
public class AdminRepairChar implements IAdminCommandHandler
{
	private static Logger _log = Logger.getLogger(AdminRepairChar.class.getName());

	private static final String[] ADMIN_COMMANDS =
	{
	"admin_restore", "admin_repair"
	};


	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		String target = (activeChar.getTarget() != null ? activeChar.getTarget().getName() : "no-target");
		GMAudit.auditGMAction(activeChar.getName(), command, target, "");

		handleRepair(command);
		return true;
	}

	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}

	private void handleRepair(String command)
	{
		String[] parts = command.split(" ");
		if (parts.length != 2)
		{
			return;
		}

		String cmd = "UPDATE characters SET x=-84318, y=244579, z=-3730 WHERE char_name=?";
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement(cmd);
			statement.setString(1, parts[1]);
			statement.execute();

			statement = con.prepareStatement("SELECT obj_Id FROM characters where char_name=?");
			statement.setString(1, parts[1]);
			ResultSet rset = statement.executeQuery();
			int objId = 0;
			if (rset.next())
			{
				objId = rset.getInt(1);
			}

			rset.close();
			statement.close();

			if (objId == 0)
			{
				con.close();
				return;
			}

			// need test
			try
			{
				con = L2DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("DELETE FROM character_shortcuts WHERE char_obj_id=?");
				statement.setInt(1, objId);
				statement.execute();
				statement.close();
			}
			catch (Exception e)
			{
				_log.log(Level.WARNING, getClass().getName() + ": could not delete characters shortcuts:" + e);
				if (Config.DEVELOPER)
				{
					e.printStackTrace();
				}
			}
			try
			{
				con = L2DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("UPDATE items SET loc=\"INVENTORY\" WHERE owner_id=?");
				statement.setInt(1, objId);
				statement.execute();
				statement.close();
				con.close();
			}
			catch (Exception e)
			{
				_log.log(Level.WARNING, getClass().getName() + ": could not delete characters inventory items:" + e);
				if (Config.DEVELOPER)
				{
					e.printStackTrace();
				}
			}
			// /need test
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "could not repair char:", e);
		}
		finally
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{
			}
		}
	}
}
