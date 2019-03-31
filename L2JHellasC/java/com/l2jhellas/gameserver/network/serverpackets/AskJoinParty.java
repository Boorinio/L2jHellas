package com.l2jhellas.gameserver.network.serverpackets;

public class AskJoinParty extends L2GameServerPacket
{
	private static final String _S__4B_ASKJOINPARTY_0X4B = "[S] 39 AskJoinParty 0x4b";
	
	private final String _requestorName;
	private final int _itemDistribution;
	
	public AskJoinParty(String requestorName, int itemDistribution)
	{
		_requestorName = requestorName;
		_itemDistribution = itemDistribution;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0x39);
		writeS(_requestorName);
		writeD(_itemDistribution);
	}
	
	@Override
	public String getType()
	{
		return _S__4B_ASKJOINPARTY_0X4B;
	}
}