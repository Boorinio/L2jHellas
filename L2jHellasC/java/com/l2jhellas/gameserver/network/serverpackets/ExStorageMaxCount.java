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

/**
 * Format: (ch)ddddddd<BR>
 * d: Number of Inventory Slots<BR>
 * d: Number of Warehouse Slots<BR>
 * d: Number of Freight Slots (unconfirmed) (200 for a low level dwarf)<BR>
 * d: Private Sell Store Slots (unconfirmed) (4 for a low level dwarf)<BR>
 * d: Private Buy Store Slots (unconfirmed) (5 for a low level dwarf)<BR>
 * d: Dwarven Recipe Book Slots<BR>
 * d: Normal Recipe Book Slots<BR>
 * 
 * @author -Wooden-<BR>
 */
public class ExStorageMaxCount extends L2GameServerPacket
{
	private static final String _S__FE_2E_EXSTORAGEMAXCOUNT = "[S] FE:2E ExStorageMaxCount";
	private final L2PcInstance _activeChar;
	private final int _inventory;
	private final int _warehouse;
	private final int _freight;
	private final int _privateSell;
	private final int _privateBuy;
	private final int _receipeD;
	private final int _recipe;

	public ExStorageMaxCount(L2PcInstance character)
	{
		_activeChar = character;
		_inventory = _activeChar.GetInventoryLimit();
		_warehouse = _activeChar.GetWareHouseLimit();
		_privateSell = _activeChar.GetPrivateSellStoreLimit();
		_privateBuy = _activeChar.GetPrivateBuyStoreLimit();
		_freight = _activeChar.GetFreightLimit();
		_receipeD = _activeChar.GetDwarfRecipeLimit();
		_recipe = _activeChar.GetCommonRecipeLimit();
	}

	@Override
	protected void writeImpl()
	{
		writeC(0xfe);
		writeH(0x2e);

		writeD(_inventory);
		writeD(_warehouse);
		writeD(_freight);
		writeD(_privateSell);
		writeD(_privateBuy);
		writeD(_receipeD);
		writeD(_recipe);
	}

	@Override
	public String getType()
	{
		return _S__FE_2E_EXSTORAGEMAXCOUNT;
	}
}