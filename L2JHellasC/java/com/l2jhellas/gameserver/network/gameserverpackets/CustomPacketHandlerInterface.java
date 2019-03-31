package com.l2jhellas.gameserver.network.gameserverpackets;

import com.l2jhellas.gameserver.network.L2GameClient;
import com.l2jhellas.gameserver.network.clientpackets.L2GameClientPacket;

import java.nio.ByteBuffer;

public interface CustomPacketHandlerInterface
{
	
	public L2GameClientPacket handlePacket(ByteBuffer data, L2GameClient client);
}
