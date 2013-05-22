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

import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public class RecipeShopMsg extends L2GameServerPacket
{
	private static final String _S__DB_RecipeShopMsg = "[S] db RecipeShopMsg";
	private final L2PcInstance _activeChar;

	public RecipeShopMsg(L2PcInstance player)
	{
		_activeChar = player;
	}

	@Override
	protected final void writeImpl()
	{
		writeC(0xdb);
		writeD(_activeChar.getObjectId());
		writeS(_activeChar.getCreateList().getStoreName());// _activeChar.getTradeList().getSellStoreName());
	}

	@Override
	public String getType()
	{
		return _S__DB_RecipeShopMsg;
	}
}