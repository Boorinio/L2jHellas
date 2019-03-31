package com.l2jhellas.gameserver.network.serverpackets;

public class ExDuelEnd extends L2GameServerPacket
{
	private static final String _S__FE_4E_EXDUELEND = "[S] FE:4E ExDuelEnd";
	private final int _unk1;
	
	public ExDuelEnd(int unk1)
	{
		_unk1 = unk1;
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0xfe);
		writeH(0x4e);
		writeD(_unk1);
	}
	
	@Override
	public String getType()
	{
		return _S__FE_4E_EXDUELEND;
	}
}