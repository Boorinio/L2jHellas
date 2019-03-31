package com.l2jhellas.gameserver.network.serverpackets;

public class ExDuelReady extends L2GameServerPacket
{
	private static final String _S__FE_4C_EXDUELREADY = "[S] FE:4C ExDuelReady";
	private final int _unk1;
	
	public ExDuelReady(int unk1)
	{
		_unk1 = unk1;
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0xfe);
		writeH(0x4c);
		writeD(_unk1);
	}
	
	@Override
	public String getType()
	{
		return _S__FE_4C_EXDUELREADY;
	}
}