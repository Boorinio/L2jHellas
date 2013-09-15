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

import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

/**
 * Format: ch<BR>
 * Trigger packet
 * 
 * @author KenM
 */
public class ExShowVariationCancelWindow extends L2GameServerPacket
{
	private static final String _S__FE_51_EXSHOWVARIATIONCANCELWINDOW = "[S] FE:51 ExShowVariationCancelWindow";

	boolean _safety = true;

	public ExShowVariationCancelWindow(L2PcInstance player, L2Npc npc)
	{
		if (player == null)
			_safety = false;
		if (npc == null)
			_safety = false;
	}

	@Override
	protected void writeImpl()
	{
		writeC(0xfe);
		writeH(0x51);
	}

	@Override
	public String getType()
	{
		return _S__FE_51_EXSHOWVARIATIONCANCELWINDOW;
	}
}