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
package com.l2jhellas.gameserver.network.serverpackets;

import com.l2jhellas.gameserver.model.L2ShortCut;

/**
 * sample
 * 56
 * 01000000 04000000 dd9fb640 01000000
 * 56
 * 02000000 07000000 38000000 03000000 01000000
 * 56
 * 03000000 00000000 02000000 01000000
 * format dd d/dd/d d
 */
public class ShortCutRegister extends L2GameServerPacket
{
	private static final String _S__56_SHORTCUTREGISTER = "[S] 44 ShortCutRegister";

	private final L2ShortCut _shortcut;

	/**
	 * Register new skill shortcut
	 * 
	 * @param slot
	 * @param type
	 * @param typeId
	 * @param level
	 * @param dat2
	 */
	public ShortCutRegister(L2ShortCut shortcut)
	{
		_shortcut = shortcut;
	}

	@Override
	protected final void writeImpl()
	{
		writeC(0x44);
		
		writeD(_shortcut.getType());
		writeD(_shortcut.getSlot() + _shortcut.getPage() * 12); // C4 Client
		
		if(_shortcut.getType() ==L2ShortCut.TYPE_ITEM)
		{
			writeD(_shortcut.getId());
			writeD(_shortcut.getCharacterType());
			writeD(0x00); //SharedReuseGroup
			writeD(0x00); // Remaining time
			writeD(0x00); // Cooldown time
			writeD(0x00); // Augmentation
		}
		else if(_shortcut.getType() == L2ShortCut.TYPE_SKILL)
		{
			writeD(_shortcut.getId());
			writeD(_shortcut.getLevel());
			writeC(0x00); // C5
			writeD(_shortcut.getCharacterType());
		}
		else
		{
			writeD(_shortcut.getId());
			writeD(_shortcut.getCharacterType());
		}
	}

	@Override
	public String getType()
	{
		return _S__56_SHORTCUTREGISTER;
	}
}