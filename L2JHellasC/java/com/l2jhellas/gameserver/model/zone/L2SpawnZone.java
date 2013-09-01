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

import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public abstract class L2SpawnZone extends L2ZoneType
{
	private static int[] _coords = new int[3];
	
	public static final void STADIUMSADD() 
	{
		addStadiums(-20814, -21189, -3030);
		addStadiums(-120324, -225077, -3331);
		addStadiums(-102495, -209023, -3331);
		addStadiums(-120156, -207378, -3331);
		addStadiums(-87628, -225021, -3331);
		addStadiums(-81705, -213209, -3331);
		addStadiums(-87593, -207339, -3331);
		addStadiums(-93709, -218304, -3331);
		addStadiums(-77157, -218608, -3331);
		addStadiums(-69682, -209027, -3331);
		addStadiums(-76887, -201256, -3331);
		addStadiums(-109985, -218701, -3331);
		addStadiums(-126367, -218228, -3331);
		addStadiums(-109629, -201292, -3331);
		addStadiums(-87523, -240169, -3331);
		addStadiums(-81748, -245950, -3331);
		addStadiums(-77123, -251473, -3331);
		addStadiums(-69778, -241801, -3331);
		addStadiums(-76754, -234014, -3331);
		addStadiums(-93742, -251032, -3331);
		addStadiums(-87466, -257752, -3331);
		addStadiums(-114413, -213241, -3331);
	}
		
	public static void addStadiums(int x, int y, int z)
	{
		_coords[0] = x;
		_coords[1] = y;
		_coords[2] = z;
	}
	
	public int[] getCoordinates()
	{
		return _coords;
	}
	
	public L2SpawnZone(int id)
	{
		super(id);
	}
	
	public void addSpectator(int id, L2PcInstance spec)
	{
		if(spec!=null)
		spec.enterOlympiadObserverMode(getCoordinates()[0], getCoordinates()[1], getCoordinates()[2], id);
	}
}