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

/**
 * @author Dezmond_snz
 *         Format: cdddsdd
 */
public class ConfirmDlg extends L2GameServerPacket
{
	private static final String _S__ED_CONFIRMDLG = "[S] ed ConfirmDlg";
	private final int _requestId;
	private final String _name;

	public ConfirmDlg(int requestId, String requestorName)
	{
		_requestId = requestId;
		_name = requestorName;
	}

	@Override
	protected final void writeImpl()
	{
		writeC(0xed);
		writeD(_requestId);
		writeD(0x02); // ??
		writeD(0x00); // ??
		writeS(_name);
		writeD(0x01); // ??
		writeD(0x00); // ??
	}

	@Override
	public String getType()
	{
		return _S__ED_CONFIRMDLG;
	}
}