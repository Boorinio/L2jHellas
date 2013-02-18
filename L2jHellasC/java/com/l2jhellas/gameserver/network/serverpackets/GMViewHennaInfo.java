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

import com.l2jhellas.gameserver.model.L2HennaInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public class GMViewHennaInfo extends L2GameServerPacket
{
	private final L2PcInstance _activeChar;
	private final L2HennaInstance[] _hennas = new L2HennaInstance[3];
	private final int _count;

	public GMViewHennaInfo(L2PcInstance activeChar)
	{
		this._activeChar = activeChar;

		int j = 0;
		for (int i = 0; i < 3; i++)
		{
			L2HennaInstance h = this._activeChar.getHenna(i + 1);
			if (h != null)
				this._hennas[(j++)] = h;
		}
		this._count = j;
	}

	@Override
	protected void writeImpl()
	{
		writeC(234);

		writeC(this._activeChar.getHennaStatINT());
		writeC(this._activeChar.getHennaStatSTR());
		writeC(this._activeChar.getHennaStatCON());
		writeC(this._activeChar.getHennaStatMEN());
		writeC(this._activeChar.getHennaStatDEX());
		writeC(this._activeChar.getHennaStatWIT());

		writeD(3);

		writeD(this._count);
		for (int i = 0; i < this._count; i++)
		{
			writeD(this._hennas[i].getSymbolId());
			writeD(1);
		}
	}

	@Override
	public String getType()
	{
		return "[S] 0xea GMHennaInfo";
	}
}