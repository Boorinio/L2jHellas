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
package handlers.admincommandhandlers;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.geodata.pathfinding.PathNode;
import com.l2jhellas.gameserver.geodata.pathfinding.geonodes.GeoPathFinding;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public class AdminPathNode implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{/** @formatter:off */
		"admin_pn_info",
		"admin_show_path",
		"admin_path_debug",
		"admin_show_pn",
		"admin_find_path"
	};/** @formatter:on */

	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.equals("admin_pn_info"))
		{

		}
		else if (command.equals("admin_show_path"))
		{

		}
		else if (command.equals("admin_path_debug"))
		{

		}
		else if (command.equals("admin_show_pn"))
		{

		}
		else if (command.equals("admin_find_path"))
		{
			if (Config.GEODATA < 2)
			{
				activeChar.sendMessage("PathFinding has not been enabled.");
				return true;
			}
			if (activeChar.getTarget() != null)
			{
				int gx = (activeChar.getX() - L2World.MAP_MIN_X) >> 4;
				int gy = (activeChar.getY() - L2World.MAP_MIN_Y) >> 4;
				int gtx = (activeChar.getTarget().getX() - L2World.MAP_MIN_X) >> 4;
				int gty = (activeChar.getTarget().getY() - L2World.MAP_MIN_Y) >> 4;
				PathNode[] path = GeoPathFinding.getInstance().findPath(gx, gy, (short) activeChar.getZ(), gtx, gty, (short) activeChar.getTarget().getZ());
				if (path == null)
				{
					activeChar.sendMessage("No Route!");
					return true;
				}
				for (PathNode a : path)
				{
					activeChar.sendMessage("x:" + a.getX() + " y:" + a.getY() + " z:" + a.getZ());
				}
			}
			else
				activeChar.sendMessage("No Target!");
		}
		return true;
	}

	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}