package com.l2jhellas.gameserver.network.serverpackets;

public final class KeyPacket extends L2GameServerPacket
{
	private static final String _S__01_KEYPACKET = "[S] 01 KeyPacket";
	
	private final byte[] _key;
	
	public KeyPacket(byte[] key)
	{
		_key = key;
	}
	
	@Override
	public void writeImpl()
	{
		writeC(0x00);
		writeC(0x01);
		writeB(_key);
		writeD(0x01);
		writeD(0x01);
	}
	
	@Override
	public String getType()
	{
		return _S__01_KEYPACKET;
	}
}