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
package com.l2jhellas.gameserver.network;

import java.nio.ByteBuffer;

import com.l2jhellas.mmocore.network.IClientFactory;
import com.l2jhellas.mmocore.network.IMMOExecutor;
import com.l2jhellas.mmocore.network.IPacketHandler;
import com.l2jhellas.mmocore.network.MMOConnection;
import com.l2jhellas.mmocore.network.ReceivablePacket;

/**
 * @author KenM. Rework AbsolutePower
 */
public final class L2GamePacketHandler implements IPacketHandler<L2GameClient>, IClientFactory<L2GameClient>, IMMOExecutor<L2GameClient>
{
	
	@Override
	public ReceivablePacket<L2GameClient> handlePacket(ByteBuffer buf, L2GameClient client)
	{
		if (client.dropPacket())
			return null;
		
		int opcode = buf.get() & 0xFF;
		
		ReceivablePacket<L2GameClient> packet = null;
		
		if(opcode  == 0xd0)
		{
			int id2 = -1;
			
			if (buf.remaining() >= 2)
				id2 = buf.getShort() & 0xffff;

			if(id2 <= DoubleOpcodePackets.PACKET_ARRAY.length && DoubleOpcodePackets.PACKET_ARRAY[id2] !=null && DoubleOpcodePackets.PACKET_ARRAY[id2].isInRightState(client.getState()))
				packet = DoubleOpcodePackets.PACKET_ARRAY[id2].getPacket();
			else
				client.onUnknownPacket();
		}
		else
		{	
		    if(opcode <= Packets.PACKET_ARRAY.length)
			{
			   if(opcode== 157 || opcode == 202)
				  packet = Packets.GameGuardReply.getPacket();
			   else if(Packets.PACKET_ARRAY[opcode] !=null && Packets.PACKET_ARRAY[opcode].isInRightState(client.getState()))
				  packet = Packets.PACKET_ARRAY[opcode].getPacket();
			   else
				   client.onUnknownPacket();
			}
		}
		
        return packet;	
	}

	// impl
	@Override
	public L2GameClient create(MMOConnection<L2GameClient> con)
	{
		return new L2GameClient(con);
	}


	@Override
	public void execute(ReceivablePacket<L2GameClient> rp)
	{
		rp.getClient().execute(rp);
	}
}