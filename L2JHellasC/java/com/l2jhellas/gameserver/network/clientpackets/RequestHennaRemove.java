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
package com.l2jhellas.gameserver.network.clientpackets;

import com.l2jhellas.gameserver.model.L2HennaInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.InventoryUpdate;
import com.l2jhellas.gameserver.network.serverpackets.ItemList;

/**
 * format cd
 */
public final class RequestHennaRemove extends L2GameClientPacket
{
	private static final String _C__0XBF_RequestHennaRemove = "[C] 0xbf RequestHennaRemove";

	private int _symbolId;
	
	@Override
	protected void readImpl()
	{
		_symbolId = readD();
	}
	
	@Override
	protected void runImpl()
	{
		final L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar == null)
			return;
		
		for (int i = 1; i <= 3; i++)
		{
			L2HennaInstance henna = activeChar.getHenna(i);
			if (henna != null && henna.getSymbolId() == _symbolId)
			{
				if (activeChar.getAdena() >= (henna.getPrice() / 5))
				{
					activeChar.removeHenna(i);
					activeChar.sendPacket(new InventoryUpdate());
					activeChar.sendPacket(new ItemList(activeChar, false));
					break;
				}
				activeChar.sendPacket(SystemMessageId.YOU_NOT_ENOUGH_ADENA);
			}
		}
	}

	@Override
	public String getType()
	{
		return _C__0XBF_RequestHennaRemove;
	}
}