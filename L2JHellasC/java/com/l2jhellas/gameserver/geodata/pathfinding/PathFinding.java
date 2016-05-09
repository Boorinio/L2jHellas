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
package com.l2jhellas.gameserver.geodata.pathfinding;

import java.util.ArrayList;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.geodata.GeoData;
import com.l2jhellas.gameserver.geodata.pathfinding.cellnodes.CellPathFinding;
import com.l2jhellas.gameserver.geodata.pathfinding.geonodes.GeoPathFinding;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.util.ArrayUtils;
import com.l2jhellas.util.geodata.L2Arrays;
import com.l2jhellas.util.geodata.L2Collections;
import com.l2jhellas.util.geodata.L2FastSet;
import com.l2jhellas.util.pathfind.BinaryNodeHeap;
import com.l2jhellas.util.pathfind.CellNodeMap;

/**
 * @author -Nemesiss-
 */
public abstract class PathFinding
{
	public static PathFinding getInstance()
	{
		if (!Config.GEODATA_CELLFINDING)
			return GeoPathFinding.getInstance(); //Higher Memory Usage, Smaller Cpu Usage
		return CellPathFinding.getInstance(); // Cell pathfinding, calculated directly from geodata files
	}

	public abstract PathNode[] findPath(int x, int y, int z, int tx, int ty, int tz);

	public abstract PathNode[] readNeighbors(PathNode n, int idx);

	public final PathNode[] search(PathNode start, PathNode end)
	{
		// The simplest grid-based pathfinding.
		// Drawback is not having higher cost for diagonal movement (means funny routes)
		// Could be optimized e.g. not to calculate backwards as far as forwards.

		// List of Visited Nodes
		L2FastSet<PathNode> visited = L2Collections.newL2FastSet();

		// List of Nodes to Visit
		L2FastSet<PathNode> to_visit = L2Collections.newL2FastSet();
		to_visit.add(start);
		try
		{
			int i = 0;
			while (i < 800)
			{
				if (to_visit.isEmpty())
				{
					// No Path found
					return null;
				}

				PathNode node = to_visit.removeFirst();

				if (node.equals(end)) //path found!
					return constructPath(node);
				i++;
				visited.add(node);
				node.attachNeighbors();
				PathNode[] neighbors = node.getNeighbors();
				if (neighbors == null)
					continue;
				for (PathNode n : neighbors)
				{
					if (!visited.contains(n) && !to_visit.contains(n))
					{
						n.setParent(node);
						to_visit.add(n);
					}
				}
			}
			//No Path found
			return null;
		}
		finally
		{
			L2Collections.recycle(visited);
			L2Collections.recycle(to_visit);
		}
	}

	public final PathNode[] searchByClosest(PathNode start, PathNode end)
	{
		// Note: This is the version for cell-based calculation, harder
		// on cpu than from block-based pathnode files. However produces better routes.

		// Always continues checking from the closest to target non-blocked
		// node from to_visit list. There's extra length in path if needed
		// to go backwards/sideways but when moving generally forwards, this is extra fast
		// and accurate. And can reach insane distances (try it with 8000 nodes..).
		// Minimum required node count would be around 300-400.
		// Generally returns a bit (only a bit) more intelligent looking routes than
		// the basic version. Not a true distance image (which would increase CPU
		// load) level of intelligence though.

		// List of Visited Nodes
		CellNodeMap known = CellNodeMap.newInstance();
		// List of Nodes to Visit
		ArrayList<PathNode> to_visit = L2Collections.newArrayList();
		to_visit.add(start);
		known.add(start);
		try
		{
			int targetx = end.getNodeX();
			int targety = end.getNodeY();
			int targetz = end.getZ();
			int dx, dy, dz;
			boolean added;
			int i = 0;
			while (i < 3500)
			{
				if (to_visit.isEmpty())
				{
					// No Path found
					return null;
				}

				PathNode node = to_visit.remove(0);

				i++;

				node.attachNeighbors();
				if (node.equals(end))
				{
					//path found! note that node z coordinate is updated only in attach
					//to improve performance (alternative: much more checks)
					//System.out.println("path found, i:"+i);
					return constructPath(node);
				}

				PathNode[] neighbors = node.getNeighbors();
				if (neighbors == null)
					continue;
				for (PathNode n : neighbors)
				{
					if (!known.contains(n))
					{
						added = false;
						n.setParent(node);
						dx = targetx - n.getNodeX();
						dy = targety - n.getNodeY();
						dz = targetz - n.getZ();
						n.setCost(dx * dx + dy * dy + dz / 2 * dz/* +n.getCost() */);
						for (int index = 0; index < to_visit.size(); index++)
						{
							// supposed to find it quite early..
							if (to_visit.get(index).getCost() > n.getCost())
							{
								to_visit.add(index, n);
								added = true;
								break;
							}
						}
						if (!added)
							to_visit.add(n);
						known.add(n);
					}
				}
			}
			//No Path found
			//System.out.println("no path found");
			return null;
		}
		finally
		{
			CellNodeMap.recycle(known);
			L2Collections.recycle(to_visit);
		}
	}

	public final PathNode[] searchByClosest2(PathNode start, PathNode end)
	{
		// Always continues checking from the closest to target non-blocked
		// node from to_visit list. There's extra length in path if needed
		// to go backwards/sideways but when moving generally forwards, this is extra fast
		// and accurate. And can reach insane distances (try it with 800 nodes..).
		// Minimum required node count would be around 300-400.
		// Generally returns a bit (only a bit) more intelligent looking routes than
		// the basic version. Not a true distance image (which would increase CPU
		// load) level of intelligence though.

		// List of Visited Nodes
		L2FastSet<PathNode> visited = L2Collections.newL2FastSet();
		// List of Nodes to Visit
		ArrayList<PathNode> to_visit = L2Collections.newArrayList();
		to_visit.add(start);
		try
		{
			int targetx = end.getNodeX();
			int targety = end.getNodeY();
			int dx, dy;
			boolean added;
			int i = 0;
			while (i < 550)
			{
				if (to_visit.isEmpty())
				{
					// No Path found
					return null;
				}

				PathNode node = to_visit.remove(0);

				if (node.equals(end)) //path found!
				{
					return constructPath2(node);
				}
				i++;
				visited.add(node);
				node.attachNeighbors();
				PathNode[] neighbors = node.getNeighbors();
				if (neighbors == null)
					continue;
				for (PathNode n : neighbors)
				{
					if (!visited.contains(n) && !to_visit.contains(n))
					{
						added = false;
						n.setParent(node);
						dx = targetx - n.getNodeX();
						dy = targety - n.getNodeY();
						n.setCost(dx * dx + dy * dy);
						for (int index = 0; index < to_visit.size(); index++)
						{
							// supposed to find it quite early..
							if (to_visit.get(index).getCost() > n.getCost())
							{
								to_visit.add(index, n);
								added = true;
								break;
							}
						}
						if (!added)
							to_visit.add(n);
					}
				}
			}
			//No Path found
			return null;
		}
		finally
		{
			L2Collections.recycle(visited);
			L2Collections.recycle(to_visit);
		}
	}

	public final PathNode[] searchAStar(PathNode start, PathNode end)
	{
		// Not operational yet?
		int start_x = start.getX();
		int start_y = start.getY();
		int end_x = end.getX();
		int end_y = end.getY();
		//List of Visited Nodes
		L2FastSet<PathNode> visited = L2Collections.newL2FastSet();//TODO! Add limit to cfg

		// List of Nodes to Visit
		BinaryNodeHeap to_visit = BinaryNodeHeap.newInstance();
		to_visit.add(start);
		try
		{
			int i = 0;
			while (i < 800)//TODO! Add limit to cfg
			{
				if (to_visit.isEmpty())
				{
					// No Path found
					return null;
				}

				PathNode node;
				try
				{
					node = to_visit.removeFirst();
				}
				catch (Exception e)
				{
					// No Path found
					if (Config.DEBUG)
						e.printStackTrace();

					return null;
				}
				if (node.equals(end)) //path found!
					return constructPath(node);
				visited.add(node);
				node.attachNeighbors();
				for (PathNode n : node.getNeighbors())
				{
					if (!visited.contains(n) && !to_visit.contains(n))
					{
						i++;
						n.setParent(node);
						n.setCost(Math.abs(start_x - n.getNodeX()) + Math.abs(start_y - n.getNodeY()) + Math.abs(end_x - n.getNodeX()) + Math.abs(end_y - n.getNodeY()));
						to_visit.add(n);
					}
				}
			}
			//No Path found
			return null;
		}
		finally
		{
			L2Collections.recycle(visited);
			BinaryNodeHeap.recycle(to_visit);
		}
	}

	public final PathNode[] constructPath(PathNode node)
	{
		ArrayList<PathNode> tmp = L2Collections.newArrayList();

		while (node.getParent() != null)
		{
			tmp.add(node);

			node = node.getParent();
		}

		PathNode[] path = tmp.toArray(new PathNode[tmp.size()]);

		L2Collections.recycle(tmp);

		ArrayUtils.reverse(path);

		for (int lastValid = 0; lastValid < path.length - 1;)
		{
			final PathNode lastValidNode = path[lastValid];

			int low = lastValid;
			int high = path.length - 1;

			while (low < high)
			{
				final int mid = ((low + high) >> 1) + 1;
				final PathNode midNode = path[mid];
				final int delta = mid - lastValid;
				final int deltaNodeX = Math.abs(midNode.getNodeX() - lastValidNode.getNodeX());
				final int deltaNodeY = Math.abs(midNode.getNodeY() - lastValidNode.getNodeY());

				if (delta <= 1)
				{
					low = mid;
				}
				else if (delta % 2 == 0 && deltaNodeX == delta / 2 && deltaNodeY == delta / 2)
				{
					low = mid;
				}
				else if (deltaNodeX == delta || deltaNodeY == delta)
				{
					low = mid;
				}
				else if (GeoData.getInstance().canMoveFromToTarget(lastValidNode.getX(), lastValidNode.getY(), lastValidNode.getZ(), midNode.getX(), midNode.getY(), midNode.getZ()))
				{
					low = mid;
				}
				else
				{
					high = mid - 1;
				}
			}

			final int nextValid = low;

			for (int i = lastValid + 1; i < nextValid; i++)
				path[i] = null;

			lastValid = nextValid;
		}

		return L2Arrays.compact(path);
	}

	public final PathNode[] constructPath2(PathNode node)
	{
		ArrayList<PathNode> tmp = L2Collections.newArrayList();
		int previousdirectionx = -1000;
		int previousdirectiony = -1000;
		int directionx;
		int directiony;
		while (node.getParent() != null)
		{
			// only add a new route point if moving direction changes
			directionx = node.getNodeX() - node.getParent().getNodeX();
			directiony = node.getNodeY() - node.getParent().getNodeY();
			if (directionx != previousdirectionx || directiony != previousdirectiony)
			{
				previousdirectionx = directionx;
				previousdirectiony = directiony;
				tmp.add(node);
			}
			node = node.getParent();
		}

		PathNode[] path = tmp.toArray(new PathNode[tmp.size()]);

		L2Collections.recycle(tmp);

		ArrayUtils.reverse(path);

		return path;
	}

	/**
	 * Convert geodata position to pathnode position
	 * 
	 * @param geo_pos
	 * @return pathnode position
	 */
	public final short getNodePos(int geo_pos)
	{
		return (short) (geo_pos >> 3); //OK?
	}

	/**
	 * Convert node position to pathnode block position
	 * 
	 * @param node_pos
	 * @return pathnode block position (0...255)
	 */
	public final short getNodeBlock(int node_pos)
	{
		return (short) (node_pos % 256);
	}

	public final byte getRegionX(int node_pos)
	{
		return (byte) ((node_pos >> 8) + 16);
	}

	public final byte getRegionY(int node_pos)
	{
		return (byte) ((node_pos >> 8) + 10);
	}

	public final short getRegionOffset(byte rx, byte ry)
	{
		return (short) ((rx << 5) + ry);
	}

	/**
	 * Convert pathnode x to World x position
	 * 
	 * @param node_x
	 * @return
	 */
	public final int calculateWorldX(short node_x)
	{
		return L2World.WORLD_X_MIN + node_x * 128 + 48;
	}

	/**
	 * Convert pathnode y to World y position
	 * 
	 * @param node_y
	 * @return
	 */
	public final int calculateWorldY(short node_y)
	{
		return L2World.WORLD_Y_MIN + node_y * 128 + 48;
	}
}