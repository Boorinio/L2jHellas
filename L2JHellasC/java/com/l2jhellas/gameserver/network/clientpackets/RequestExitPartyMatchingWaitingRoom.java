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

import com.l2jhellas.Config;

/**
 * Format: (ch)
 * this is just a trigger : no data
 * 
 * @author -Wooden-
 */
public final class RequestExitPartyMatchingWaitingRoom extends L2GameClientPacket
{
	private static final String _C__D0_17_REQUESTEXITPARTYMATCHINGWAITINGROOM = "[C] D0:17 RequestExitPartyMatchingWaitingRoom";

	@Override
	protected void readImpl()
	{
		// trigger
	}

	@Override
	protected void runImpl()
	{
		// TODO
		if (Config.DEBUG)
			_log.config(RequestExitPartyMatchingWaitingRoom.class.getName() + ": C5: RequestExitPartyMatchingWaitingRoom");
	}

	@Override
	public String getType()
	{
		return _C__D0_17_REQUESTEXITPARTYMATCHINGWAITINGROOM;
	}
}