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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastMap;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.model.L2TeleportLocation;
import com.l2jhellas.util.database.L2DatabaseFactory;

public class TeleportLocationTable
{
	private static Logger _log = Logger.getLogger(TeleportLocationTable.class.getName());

	private static TeleportLocationTable _instance;

	private Map<Integer, L2TeleportLocation> _teleports;

	public static TeleportLocationTable getInstance()
	{
		if (_instance == null)
		{
			_instance = new TeleportLocationTable();
		}
		return _instance;
	}

	private TeleportLocationTable()
	{
		reload();
	}

	public void reload()
	{
		_teleports = new FastMap<Integer, L2TeleportLocation>();

		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
				PreparedStatement statement = con.prepareStatement("SELECT Description, id, loc_x, loc_y, loc_z, price, fornoble FROM teleport"))
		{
			ResultSet rset = statement.executeQuery();
			L2TeleportLocation teleport;

			while (rset.next())
			{
				teleport = new L2TeleportLocation();

				teleport.setTeleId(rset.getInt("id"));
				teleport.setLocX(rset.getInt("loc_x"));
				teleport.setLocY(rset.getInt("loc_y"));
				teleport.setLocZ(rset.getInt("loc_z"));
				teleport.setPrice(rset.getInt("price"));
				teleport.setIsForNoble(rset.getInt("fornoble") == 1);

				_teleports.put(teleport.getTeleId(), teleport);
			}

			_log.log(Level.INFO, getClass().getSimpleName() + ": Loaded " + _teleports.size() + " Teleport Location Templates.");
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": error while creating teleport table " + e);
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}

		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
				PreparedStatement statement = con.prepareStatement("SELECT Description, id, loc_x, loc_y, loc_z, price, fornoble FROM custom_teleport"))
		{
			ResultSet rset = statement.executeQuery();
			L2TeleportLocation teleport;
			int _cTeleCount = _teleports.size();
			while (rset.next())
			{
				teleport = new L2TeleportLocation();
				teleport.setTeleId(rset.getInt("id"));
				teleport.setLocX(rset.getInt("loc_x"));
				teleport.setLocY(rset.getInt("loc_y"));
				teleport.setLocZ(rset.getInt("loc_z"));
				teleport.setPrice(rset.getInt("price"));
				teleport.setIsForNoble(rset.getInt("fornoble") == 1);
				_teleports.put(teleport.getTeleId(), teleport);
			}
			_cTeleCount = _teleports.size() - _cTeleCount;
			if (_cTeleCount > 0)
				_log.log(Level.INFO, getClass().getSimpleName() + ": Loaded " + _cTeleCount + " Custom Teleport Location Templates.");
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": error while creating custom teleport table " + e);
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param template
	 *        id
	 * @return
	 */
	public L2TeleportLocation getTemplate(int id)
	{
		return _teleports.get(id);
	}
}