package com.l2jhellas.gameserver.network.serverpackets;

import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.actor.item.L2ItemInstance;

public class GMViewItemList extends L2GameServerPacket
{
	private static final String _S__AD_GMVIEWITEMLIST = "[S] 94 GMViewItemList";
	private final L2ItemInstance[] _items;
	private final L2PcInstance _cha;
	private final String _playerName;
	
	public GMViewItemList(L2PcInstance cha)
	{
		_items = cha.getInventory().getItems();
		_playerName = cha.getName();
		_cha = cha;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0x94);
		writeS(_playerName);
		writeD(_cha.getInventoryLimit()); // inventory limit
		writeH(0x01); // show window ??
		writeH(_items.length);
		
		for (L2ItemInstance temp : _items)
		{
			if (temp == null || temp.getItem() == null)
				continue;
			
			writeH(temp.getItem().getType1());
			
			writeD(temp.getObjectId());
			writeD(temp.getItemId());
			writeD(temp.getCount());
			writeH(temp.getItem().getType2());
			writeH(temp.getCustomType1());
			writeH(temp.isEquipped() ? 0x01 : 0x00);
			writeD(temp.getItem().getBodyPart());
			writeH(temp.getEnchantLevel());
			writeH(temp.getCustomType2());
			writeD((temp.isAugmented()) ? temp.getAugmentation().getAugmentationId() : 0x00);
			writeD(temp.getMana());
		}
	}
	
	@Override
	public String getType()
	{
		return _S__AD_GMVIEWITEMLIST;
	}
}