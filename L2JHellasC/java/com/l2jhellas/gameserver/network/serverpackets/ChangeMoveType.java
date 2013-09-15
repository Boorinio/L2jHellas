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

import com.l2jhellas.gameserver.model.actor.L2Character;

/**
 * sample<BR>
 * 0000: 3e 2a 89 00 4c 01 00 00 00 .|...<BR>
 * format dd
 */
public class ChangeMoveType extends L2GameServerPacket
{
	private static final String _S__3E_CHANGEMOVETYPE = "[S] 3E ChangeMoveType";
	public static final int WALK = 0;
	public static final int RUN = 1;

	private final int _charObjId;
	private final boolean _running;

	public ChangeMoveType(L2Character character)
	{
		_charObjId = character.getObjectId();
		_running = character.isRunning();
	}

	@Override
	protected final void writeImpl()
	{
		writeC(0x2e);
		writeD(_charObjId);
		writeD(_running ? RUN : WALK);
		writeD(0); // c2
	}

	@Override
	public String getType()
	{
		return _S__3E_CHANGEMOVETYPE;
	}
}