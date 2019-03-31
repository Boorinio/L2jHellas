package com.l2jhellas.gameserver.network.serverpackets;

import com.l2jhellas.gameserver.model.TradeList.TradeItem;

public class TradeUpdateItems extends L2GameServerPacket
{
	private final TradeItem _item;
	private final int _count;
	
	public TradeUpdateItems(TradeItem item, int count)
	{
		_item = item;
		_count = count;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0x74);
		writeH(1);
		
		writeH((_count > 0 && _item.getItem().isStackable()) ? 3 : 2);
		writeH(_item.getItem().getType1());
		writeD(_item.getObjectId());
		writeD(_item.getItem().getItemId());
		writeD(_count);
		writeH(_item.getItem().getType2());
		writeD(_item.getItem().getBodyPart());
		writeH(_item.getEnchant());
	}
}