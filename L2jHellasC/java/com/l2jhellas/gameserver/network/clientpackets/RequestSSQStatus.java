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

import com.l2jhellas.gameserver.SevenSigns;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.SSQStatus;

/**
 * Seven Signs Record Update Request
 *
 * packet type id 0xc7
 * format: cc
 *
 * @author Tempy
 */
public final class RequestSSQStatus extends L2GameClientPacket
{
	private static final String _C__C7_RequestSSQStatus = "[C] C7 RequestSSQStatus";

	private int _page;

	@Override
	protected void readImpl()
	{
		_page = readC();
	}

	@Override
	protected void runImpl()
	{
		L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar == null)
		    return;

        if ((SevenSigns.getInstance().isSealValidationPeriod() || SevenSigns.getInstance().isCompResultsPeriod()) && _page == 4)
            return;

		SSQStatus ssqs = new SSQStatus(activeChar, _page);
		activeChar.sendPacket(ssqs);
	}

	/* (non-Javadoc)
	 * @see com.l2jhellas.gameserver.clientpackets.ClientBasePacket#getType()
	 */
	@Override
	public String getType()
	{
		return _C__C7_RequestSSQStatus;
	}
}
