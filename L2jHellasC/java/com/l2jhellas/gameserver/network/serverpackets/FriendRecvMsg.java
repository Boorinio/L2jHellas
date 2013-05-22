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
 * Send Private (Friend) Message<BR>
 * Format: c dSSS<BR>
 * d: Unknown<BR>
 * S: Sending Player<BR>
 * S: Receiving Player<BR>
 * S: Message<BR>
 * <BR>
 * 
 * @author Tempy
 */
public class FriendRecvMsg extends L2GameServerPacket
{
	private static final String _S__FD_FRIENDRECVMSG = "[S] FD FriendRecvMsg";

	private final String _sender, _receiver, _message;

	public FriendRecvMsg(String sender, String reciever, String message)
	{
		_sender = sender;
		_receiver = reciever;

		_message = message;
	}

	@Override
	protected final void writeImpl()
	{
		writeC(0xfd);

		writeD(0); // ??
		writeS(_receiver);
		writeS(_sender);
		writeS(_message);
	}

	@Override
	public String getType()
	{
		return _S__FD_FRIENDRECVMSG;
	}
}