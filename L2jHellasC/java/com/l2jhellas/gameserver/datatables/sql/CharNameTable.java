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
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastMap;

import com.l2jhellas.Config;
import com.l2jhellas.L2DatabaseFactory;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public class CharNameTable
{
	private static Logger _log = Logger.getLogger(CharNameTable.class.getName());

	private final Map<Integer, String> _chars;
	private final Map<Integer, Integer> _accessLevels;

	private CharNameTable()
	{
		_chars = new FastMap<Integer, String>();
		_accessLevels = new FastMap<Integer, Integer>();
	}

	public static CharNameTable getInstance()
	{
		return SingletonHolder._instance;
	}

	public final void addName(L2PcInstance player)
	{
		if (player != null)
		{
			addName(player.getObjectId(), player.getName());
			_accessLevels.put(player.getObjectId(), player.getAccessLevel().getLevel());
		}
	}

	private final void addName(int objId, String name)
	{
		if (name != null)
		{
			if (!name.equalsIgnoreCase(_chars.get(objId)))
				_chars.put(objId, name);
		}
	}

	public final void removeName(int objId)
	{
		_chars.remove(objId);
		_accessLevels.remove(objId);
	}

	public final int getIdByName(String name)
	{
		if (name == null || name.isEmpty())
			return -1;

		Iterator<Entry<Integer, String>> it = _chars.entrySet().iterator();

		Map.Entry<Integer, String> pair;
		while (it.hasNext())
		{
			pair = it.next();
			if (pair.getValue().equalsIgnoreCase(name))
				return pair.getKey();
		}

		int id = -1;
		Connection con = null;
		PreparedStatement statement = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			statement = con.prepareStatement("SELECT obj_Id,accesslevel FROM characters WHERE char_name=?");
			statement.setString(1, name);
			ResultSet rset = statement.executeQuery();

			while (rset.next())
			{
				id = rset.getInt(1);
				rset.getInt(2);
			}
			rset.close();
			statement.close();
		}
		catch (SQLException e)
		{
			_log.log(Level.WARNING, getClass().getName() + " Could not check existing char name: " + e);
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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

		if (id > 0)
		{
			_chars.put(id, name);
			return id;
		}

		return -1; // not found
	}

	public final String getNameById(int id)
	{
		if (id <= 0)
			return null;

		String name = _chars.get(id);
		if (name != null)
			return name;

		int accessLevel = 0;
		Connection con = null;
		PreparedStatement statement = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			statement = con.prepareStatement("SELECT char_name,accesslevel FROM characters WHERE obj_Id=?");
			statement.setInt(1, id);
			ResultSet rset = statement.executeQuery();
			while (rset.next())
			{
				name = rset.getString(1);
				accessLevel = rset.getInt(2);
			}
			rset.close();
			statement.close();
		}
		catch (SQLException e)
		{
			_log.log(Level.WARNING, getClass().getName() + " Could not check existing char id: " + e);
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
		if (name != null && !name.isEmpty())
		{
			_chars.put(id, name);
			_accessLevels.put(id, accessLevel);
			return name;
		}

		return null; // not found
	}

	public final int getAccessLevelById(int objectId)
	{
		if (getNameById(objectId) != null)
			return _accessLevels.get(objectId);
		else
			return 0;
	}

	public boolean doesCharNameExist(String name)
	{
		boolean result = true;
		Connection con = null;

		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT account_name FROM characters WHERE char_name=?");
			statement.setString(1, name);
			ResultSet rset = statement.executeQuery();
			result = rset.next();
			rset.close();
			statement.close();
		}
		catch (SQLException e)
		{
			_log.log(Level.WARNING, getClass().getName() + " could not check existing charname:" + e);
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
		return result;
	}

	public int accountCharNumber(String account)
	{
		Connection con = null;
		int number = 0;

		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT COUNT(char_name) FROM characters WHERE account_name=?");
			statement.setString(1, account);
			ResultSet rset = statement.executeQuery();
			while (rset.next())
			{
				number = rset.getInt(1);
			}
			rset.close();
			statement.close();
		}
		catch (SQLException e)
		{
			_log.log(Level.WARNING, getClass().getName() + " could not check existing char number:" + e);
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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

		return number;
	}

	@SuppressWarnings("synthetic-access")
	private static class SingletonHolder
	{
		protected static final CharNameTable _instance = new CharNameTable();
	}
}