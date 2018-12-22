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

public class ValidateLocationInVehicle extends L2GameServerPacket
{
	private static final String _S__73_ValidateLocationInVehicle = "[S] 73 ValidateLocationInVehicle";
	private final L2Character _activeChar;
	private final int _boatId;

	/**
	 * 0x73 ValidateLocationInVehicle hdd
	 * 
	 * @param _characters
	 */
	public ValidateLocationInVehicle(L2Character player,int boatId)
	{
		_activeChar = player;
		_boatId = boatId;
	}

	@Override
	protected final void writeImpl()
	{
		writeC(0x73);
		writeD(_activeChar.getObjectId());
		writeD(_boatId);
		writeD(_activeChar.getX());
		writeD(_activeChar.getY());
		writeD(_activeChar.getZ());
		writeD(_activeChar.getHeading());
	}

	@Override
	public String getType()
	{
		return _S__73_ValidateLocationInVehicle;
	}
}