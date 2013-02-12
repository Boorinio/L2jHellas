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
import com.l2jhellas.gameserver.GmListTable;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;

public class AdminDonator implements IAdminCommandHandler
{
	private static String[] _adminCommands =
	{
		"admin_setdonator",
	};
	protected static final Logger _log = Logger.getLogger(AdminDonator.class.getName());

	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_setdonator"))
		{
			L2Object target = activeChar.getTarget();
			L2PcInstance player = null;
			SystemMessage sm = new SystemMessage(SystemMessageId.S1_S2);
			if (target instanceof L2PcInstance)
			{
				player = (L2PcInstance) target;
			}
			else
			{
				player = activeChar;
			}

			if (player.isDonator())
			{
				player.setDonator(false);
				player.updateNameTitleColor();
				sm.addString("You are no longer a server donator.");
				GmListTable.broadcastMessageToGMs("GM " + activeChar.getName() + " removed donator stat of player" + target.getName());
				Connection connection = null;
				try
				{
					connection = L2DatabaseFactory.getInstance().getConnection();

					PreparedStatement statement = connection.prepareStatement("SELECT obj_Id FROM characters where char_name=?");
					statement.setString(1, target.getName());
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
						connection.close();
						return false;
					}

					statement = connection.prepareStatement("UPDATE characters SET donator=0 WHERE obj_Id=?");
					statement.setInt(1, objId);
					statement.execute();
					statement.close();
					connection.close();
				}
				catch (Exception e)
				{
					_log.log(Level.WARNING, getClass().getName() + ": could not set donator stats of char:" + e);
					if (Config.DEVELOPER)
					{
						e.printStackTrace();
					}
				}
				finally
				{
					try
					{
						connection.close();
					}
					catch (Exception e)
					{
					}
				}
			}
			else
			{
				player.setDonator(true);
				player.updateNameTitleColor();
				sm.addString("You are now a server donator, congratulations!");
				GmListTable.broadcastMessageToGMs("GM " + activeChar.getName() + " has given donator stat for player " + target.getName() + ".");
				Connection connection = null;
				try
				{
					connection = L2DatabaseFactory.getInstance().getConnection();

					PreparedStatement statement = connection.prepareStatement("SELECT obj_Id FROM characters where char_name=?");
					statement.setString(1, target.getName());
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
						connection.close();
						return false;
					}

					statement = connection.prepareStatement("UPDATE characters SET donator=1 WHERE obj_Id=?");
					statement.setInt(1, objId);
					statement.execute();
					statement.close();
					connection.close();
				}
				catch (Exception e)
				{
					_log.log(Level.WARNING, getClass().getName() + ": could not set donator stats of char:" + e);
					if (Config.DEVELOPER)
					{
						e.printStackTrace();
					}
				}
				finally
				{
					try
					{
						connection.close();
					}
					catch (Exception e)
					{
					}
				}

			}
			player.sendPacket(sm);
			player.broadcastUserInfo();
			if (player.isDonator() == true)
			{
			}
		}
		return false;
	}

	@Override
	public String[] getAdminCommandList()
	{
		return _adminCommands;
	}
}
