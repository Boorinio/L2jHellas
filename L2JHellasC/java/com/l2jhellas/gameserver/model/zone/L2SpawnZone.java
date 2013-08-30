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
package com.l2jhellas.gameserver.model.zone;

import java.util.List;

import com.l2jhellas.gameserver.model.Location;
import com.l2jhellas.util.Rnd;

/**
 * Abstract zone with spawn locations
 * @author DS
 */
public abstract class L2SpawnZone extends L2ZoneType
{
	private static List<Location> _spawnLocs = null;
   
 	protected static final int[][] STADIUMS =
	{
	{
	-20814, -21189, -3030
	},
	{
	-120324, -225077, -3331
	},
	{
	-102495, -209023, -3331
	},
	{
	-120156, -207378, -3331
	},
	{
	-87628, -225021, -3331
	},
	{
	-81705, -213209, -3331
	},
	{
	-87593, -207339, -3331
	},
	{
	-93709, -218304, -3331
	},
	{
	-77157, -218608, -3331
	},
	{
	-69682, -209027, -3331
	},
	{
	-76887, -201256, -3331
	},
	{
	-109985, -218701, -3331
	},
	{
	-126367, -218228, -3331
	},
	{
	-109629, -201292, -3331
	},
	{
	-87523, -240169, -3331
	},
	{
	-81748, -245950, -3331
	},
	{
	-77123, -251473, -3331
	},
	{
	-69778, -241801, -3331
	},
	{
	-76754, -234014, -3331
	},
	{
	-93742, -251032, -3331
	},
	{
	-87466, -257752, -3331
	},
	{
	-114413, -213241, -3331
	}
	};
 	
    public static int[] getRandomStadium()
    {
            return STADIUMS[Rnd.get(STADIUMS.length)];
    }
    
	public L2SpawnZone(int id)
	{
		super(id);
	}
	
	public final static void addSpawn()
	{
	    final int x = getRandomStadium()[0];
	    final int y = getRandomStadium()[1];
	    final int z = getRandomStadium()[2];
	        
		_spawnLocs.add(new Location(x, y, z));
	}

	public final List<Location> getSpawns()
	{
		return _spawnLocs;
	}
	
	public final Location getSpawnLoc()
	{
		return _spawnLocs.get(Rnd.get(_spawnLocs.size()));
	}
}