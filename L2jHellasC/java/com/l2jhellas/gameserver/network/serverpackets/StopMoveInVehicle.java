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
 * @author Maktakien
 */
public class StopMoveInVehicle extends L2GameServerPacket
{
	private final L2PcInstance _activeChar;
	private final int _boatId;

	/**
	 * @param player
	 * @param boatid
	 */
	public StopMoveInVehicle(L2PcInstance player, int boatid)
	{
		_activeChar = player;
		_boatId = boatid;
	}

	@Override
	protected void writeImpl()
	{
		writeC(0x72);
		writeD(_activeChar.getObjectId());
		writeD(_boatId);
		writeD(_activeChar.getInBoatPosition().getX());
		writeD(_activeChar.getInBoatPosition().getY());
		writeD(_activeChar.getInBoatPosition().getZ());
		writeD(_activeChar.getPosition().getHeading());
	}

	@Override
	public String getType()
	{
		return "[S] 72 StopMoveInVehicle";
	}
}