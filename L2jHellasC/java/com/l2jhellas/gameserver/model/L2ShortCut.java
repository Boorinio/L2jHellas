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
package com.l2jhellas.gameserver.model;

public class L2ShortCut
{
	public final static int TYPE_ITEM = 1;
	public final static int TYPE_SKILL = 2;
	public final static int TYPE_ACTION = 3;
	public final static int TYPE_MACRO = 4;
	public final static int TYPE_RECIPE = 5;

	private final int _slot;
	private final int _page;
	private final int _type;
	private final int _id;
	private final int _level;
	/** Augmented Item */
	private L2Augmentation _augmentation = null;

	public L2ShortCut(int slotId, int pageId, int shortcutType, int shortcutId, int shortcutLevel, int unknown)
	{
		_slot = slotId;
		_page = pageId;
		_type = shortcutType;
		_id = shortcutId;
		_level = shortcutLevel;
		//_unk = unknown;
	}

	public int getId()
	{
		return _id;
	}

	public int getLevel()
	{
		return _level;
	}

	public int getPage()
	{
		return _page;
	}

	public int getSlot()
	{
		return _slot;
	}

	public int getType()
	{
		return _type;
	}
	
	/**
	 * Returns whether this item is augmented or not
	 *
	 * @return true if augmented
	 */
	public boolean isAugmented()
	{
		return _augmentation == null ? false : true;
	}
	
	/**
	 * Returns the augmentation object for this item
	 *
	 * @return augmentation
	 */
	public L2Augmentation getAugmentation()
	{
		return _augmentation;
	}
}