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
package com.l2jhellas.gameserver.templates;

import com.l2jhellas.gameserver.emum.L2EtcItemType;

public final class L2EtcItem extends L2Item
{
	/**
	 * Constructor for EtcItem.
	 * 
	 * @see L2Item constructor
	 * @param type
	 *        : L2EtcItemType designating the type of object Etc
	 * @param set
	 *        : StatsSet designating the set of couples (key,value) for description of the Etc
	 */
	public L2EtcItem(L2EtcItemType type, StatsSet set)
	{
		super(type, set);
	}

	/**
	 * Returns the type of Etc Item
	 * 
	 * @return L2EtcItemType
	 */
	@Override
	public L2EtcItemType getItemType()
	{
		return (L2EtcItemType) super._type;
	}

	/**
	 * Returns if the item is consumable
	 * 
	 * @return boolean
	 */
	@Override
	public final boolean isConsumable()
	{
		return ((getItemType() == L2EtcItemType.SHOT) || (getItemType() == L2EtcItemType.POTION)); // || (type == L2EtcItemType.SCROLL));
	}

	/**
	 * Returns the ID of the Etc item after applying the mask.
	 * 
	 * @return int : ID of the EtcItem
	 */
	@Override
	public int getItemMask()
	{
		return getItemType().mask();
	}
}