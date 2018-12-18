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
package com.l2jhellas.gameserver.instancemanager;

import java.util.ArrayList;
import java.util.List;

import com.l2jhellas.gameserver.model.zone.type.L2FishingZone;

public class FishingZoneManager
{
	private List<L2FishingZone> _fishingZones = new ArrayList<L2FishingZone>();

	public FishingZoneManager()
	{
		
	}

	public void addFishingZone(L2FishingZone fishingZone)
	{
		_fishingZones.add(fishingZone);
	}

	public void clearFishingZone()
	{
		_fishingZones.clear();
	}
	
	public final L2FishingZone isInsideFishingZone(int x, int y, int z)
	{
		for (L2FishingZone temp : _fishingZones)
			if (temp.isInsideZone(x, y, temp.getWaterZ() - 10))
				return temp;
		
		return null;
	}
	
	private static FishingZoneManager _instance;

	public static final FishingZoneManager getInstance()
	{
		if (_instance == null)
			_instance = new FishingZoneManager();
		
		return _instance;
	}
}