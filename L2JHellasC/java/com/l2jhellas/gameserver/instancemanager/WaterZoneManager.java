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

import com.l2jhellas.gameserver.model.zone.type.L2WaterZone;

/**
 * Author AbsolutePower
 */
public class WaterZoneManager
{
	private List<L2WaterZone> _waterZones = new ArrayList<L2WaterZone>();

	public WaterZoneManager()
	{
		
	}

	public void addWaterZone(L2WaterZone fishingZone)
	{
		_waterZones.add(fishingZone);
	}

	public void clearWaterZone()
	{
		_waterZones.clear();
	}
	
	public List<L2WaterZone> getAllWaterZones()
	{
		return _waterZones;
	}

	public static WaterZoneManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final WaterZoneManager _instance = new WaterZoneManager();
	}
}