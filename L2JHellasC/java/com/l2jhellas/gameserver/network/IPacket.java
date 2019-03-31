package com.l2jhellas.gameserver.network;

import com.l2jhellas.gameserver.network.L2GameClient.GameClientState;
import com.l2jhellas.mmocore.network.ReceivablePacket;

import java.util.Set;

public interface IPacket
{
	public ReceivablePacket<L2GameClient> getPacket();
	
	public int getPacketId();
	
	public Set<GameClientState> getState();
}