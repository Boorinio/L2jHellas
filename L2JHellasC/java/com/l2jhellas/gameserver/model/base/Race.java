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
package com.l2jhellas.gameserver.model.base;

/**
 * This class defines all races<BR>
 * (human, elf, darkelf, orc, dwarf)<BR>
 * that a player can chose.
 */
public enum Race
{
	
	human(1),
	elf(1.5),
	darkelf(1.5),
	orc(0.9),
	dwarf(0.8);

	private final double _breathMultiplier;
	
	private Race(double breathMultiplier)
	{
		_breathMultiplier = breathMultiplier;
	}
	
	/**
	 * @return the breath multiplier.
	 */
	public double getBreathMultiplier()
	{
		return _breathMultiplier;
	}
}