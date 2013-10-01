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

public abstract class PathNode
{
	private final int _neighborsIdx;
	private PathNode[] _neighbors;
	private PathNode _parent;
	private short _cost;

	protected PathNode(int neighborsIdx)
	{
		_neighborsIdx = neighborsIdx;
	}

	public final void setParent(PathNode p)
	{
		_parent = p;
	}

	public final void setCost(int cost)
	{
		_cost = (short) cost;
	}

	public final void attachNeighbors()
	{
		_neighbors = PathFinding.getInstance().readNeighbors(this, _neighborsIdx);
	}

	public final PathNode[] getNeighbors()
	{
		return _neighbors;
	}

	public final PathNode getParent()
	{
		return _parent;
	}

	public final short getCost()
	{
		return _cost;
	}

	public abstract int getX();

	public abstract int getY();

	public abstract short getZ();

	public abstract void setZ(short z);

	public abstract int getNodeX();

	public abstract int getNodeY();

	@Override
	public final int hashCode()
	{
		return hash((getNodeX() << 20) + (getNodeY() << 8) + getZ());
	}

	@Override
	public final boolean equals(Object obj)
	{
		if (!(obj instanceof PathNode))
			return false;

		PathNode n = (PathNode) obj;

		return getNodeX() == n.getNodeX() && getNodeY() == n.getNodeY() && getZ() == n.getZ();
	}

	public final int hash(int h)
	{
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}
}