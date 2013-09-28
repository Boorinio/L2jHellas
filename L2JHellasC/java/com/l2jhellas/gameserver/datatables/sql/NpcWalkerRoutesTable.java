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
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastList;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.model.L2NpcWalkerNode;
import com.l2jhellas.util.database.L2DatabaseFactory;

/**
 * Main Table to Load Npc Walkers Routes and Chat SQL Table.<br>
 */
public class NpcWalkerRoutesTable
{
	protected static final Logger _log = Logger.getLogger(NpcWalkerRoutesTable.class.getName());

	private static NpcWalkerRoutesTable _instance;

	private final static FastList<L2NpcWalkerNode> _routes = new FastList<L2NpcWalkerNode>();

	public static NpcWalkerRoutesTable getInstance()
	{
		if (_instance == null)
		{
			_instance = new NpcWalkerRoutesTable();
		}

		return _instance;
	}
	
	public static void reload()
	{
		_routes.clear();
		_instance = null;
		getInstance();
	}
	
	private NpcWalkerRoutesTable()
	{
	}

	public void load()
	{
		_routes.clear();
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT route_id, npc_id, move_point, chatText, move_x, move_y, move_z, delay, running FROM walker_routes");
			ResultSet rset = statement.executeQuery();
			L2NpcWalkerNode route;
			while (rset.next())
			{
				route = new L2NpcWalkerNode();
				route.setRouteId(rset.getInt("route_id"));
				route.setNpcId(rset.getInt("npc_id"));
				route.setMovePoint(rset.getString("move_point"));
				route.setChatText(rset.getString("chatText"));

				route.setMoveX(rset.getInt("move_x"));
				route.setMoveY(rset.getInt("move_y"));
				route.setMoveZ(rset.getInt("move_z"));
				route.setDelay(rset.getInt("delay"));
				route.setRunning(rset.getBoolean("running"));

				_routes.add(route);
			}

			rset.close();
			statement.close();

			_log.log(Level.INFO, getClass().getSimpleName() + ": Loaded " + _routes.size() + " Npc Walker Routes.");
			rset.close();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Error while loading Npc Walkers Routes: " + e);
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	public FastList<L2NpcWalkerNode> getRouteForNpc(int id)
	{
		FastList<L2NpcWalkerNode> _return = new FastList<L2NpcWalkerNode>();

		for (FastList.Node<L2NpcWalkerNode> n = _routes.head(), end = _routes.tail(); (n = n.getNext()) != end;)
		{
			if (n.getValue().getNpcId() == id)
			{
				_return.add(n.getValue());
			}
		}
		return _return;
	}
}