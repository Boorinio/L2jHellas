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
package com.l2jhellas.util.pathfind;

import java.util.Arrays;

import com.l2jhellas.gameserver.geodata.pathfinding.PathNode;
import com.l2jhellas.util.geodata.L2FastSet;
import com.l2jhellas.util.geodata.ObjectPool;

public final class BinaryNodeHeap
{
	protected final PathNode[] _list = new PathNode[800 + 1];
	protected final L2FastSet<PathNode> _set = new L2FastSet<PathNode>();
	protected int _size = 0;

	protected BinaryNodeHeap()
	{
	}

	public void add(PathNode n)
	{
		_size++;
		int pos = _size;
		_list[pos] = n;
		_set.add(n);
		while (pos != 1)
		{
			int p2 = pos / 2;
			if (_list[pos].getCost() <= _list[p2].getCost())
			{
				PathNode temp = _list[p2];
				_list[p2] = _list[pos];
				_list[pos] = temp;
				pos = p2;
			}
			else
				break;
		}
	}

	public PathNode removeFirst()
	{
		PathNode first = _list[1];
		_list[1] = _list[_size];
		_list[_size] = null;
		_size--;
		int pos = 1;
		int cpos;
		int dblcpos;
		PathNode temp;
		while (true)
		{
			cpos = pos;
			dblcpos = cpos * 2;
			if ((dblcpos + 1) <= _size)
			{
				if (_list[cpos].getCost() >= _list[dblcpos].getCost())
					pos = dblcpos;
				if (_list[pos].getCost() >= _list[dblcpos + 1].getCost())
					pos = dblcpos + 1;
			}
			else if (dblcpos <= _size)
			{
				if (_list[cpos].getCost() >= _list[dblcpos].getCost())
					pos = dblcpos;
			}

			if (cpos != pos)
			{
				temp = _list[cpos];
				_list[cpos] = _list[pos];
				_list[pos] = temp;
			}
			else
				break;
		}
		_set.remove(first);
		return first;
	}

	public boolean contains(PathNode n)
	{
		if (_size == 0)
			return false;

		return _set.contains(n);
	}

	public boolean isEmpty()
	{
		return _size == 0;
	}

	public static BinaryNodeHeap newInstance()
	{
		return POOL.get();
	}

	public static void recycle(BinaryNodeHeap heap)
	{
		POOL.store(heap);
	}

	private static final ObjectPool<BinaryNodeHeap> POOL = new ObjectPool<BinaryNodeHeap>()
	{
		@Override
		protected void reset(BinaryNodeHeap heap)
		{
			Arrays.fill(heap._list, null);
			heap._set.clear();
			heap._size = 0;
		}

		@Override
		protected BinaryNodeHeap create()
		{
			return new BinaryNodeHeap();
		}
	};
}