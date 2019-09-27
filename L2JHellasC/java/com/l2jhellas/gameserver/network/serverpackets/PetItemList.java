package com.l2jhellas.gameserver.network.serverpackets;

import com.l2jhellas.gameserver.model.L2ItemInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PetInstance;

public class PetItemList extends L2GameServerPacket
{
	private static final String _S__cb_PETITEMLIST = "[S] b2  PetItemList";
	private final L2ItemInstance[] _items;
	
	public PetItemList(L2PetInstance character)
	{
		_items = character.getInventory().getItems();
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0xB2);

		writeH(_items.length);
		
		for (L2ItemInstance temp : _items)
		{
			writeH(temp.getItem().getType1());// item type1
			writeD(temp.getObjectId());
			writeD(temp.getItemId());
			writeD(temp.getCount());
			writeH(temp.getItem().getType2());// item type2
			writeH(temp.getCustomType1());
			writeH(temp.isEquipped() ? 0x01 : 0x00);
			writeD(temp.getItem().getBodyPart());
			writeH(temp.getEnchantLevel()); // enchant level
			writeH(temp.getCustomType2());
		}
	}
	
	@Override
	public String getType()
	{
		return _S__cb_PETITEMLIST;
	}
}