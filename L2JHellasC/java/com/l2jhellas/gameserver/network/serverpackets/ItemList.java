package com.l2jhellas.gameserver.network.serverpackets;

import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.actor.item.L2ItemInstance;

public class ItemList extends L2GameServerPacket
{
	private static Logger _log = Logger.getLogger(ItemList.class.getName());
	private static final String _S__27_ITEMLIST = "[S] 1b ItemList";
	private final L2ItemInstance[] _items;
	private final boolean _showWindow;
	
	public ItemList(L2PcInstance cha, boolean showWindow)
	{
		_items = cha.getInventory().getItems();
		_showWindow = showWindow;
		if (Config.DEBUG)
		{
			showDebug();
		}
	}
	
	public ItemList(L2ItemInstance[] items, boolean showWindow)
	{
		_items = items;
		_showWindow = showWindow;
		if (Config.DEBUG)
		{
			showDebug();
		}
	}
	
	private void showDebug()
	{
		for (L2ItemInstance temp : _items)
		{
			_log.fine("item:" + temp.getItem().getItemName() + " type1:" + temp.getItem().getType1() + " type2:" + temp.getItem().getType2());
		}
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0x1b);
		writeH(_showWindow ? 0x01 : 0x00);
		
		int count = _items.length;
		writeH(count);
		
		for (L2ItemInstance temp : _items)
		{
			if ((temp == null) || (temp.getItem() == null))
				continue;
			
			writeH(temp.getItem().getType1()); // item type1
			
			writeD(temp.getObjectId());
			writeD(temp.getItemId());
			writeD(temp.getCount());
			writeH(temp.getItem().getType2()); // item type2
			writeH(temp.getCustomType1()); // item type3
			writeH(temp.isEquipped() ? 0x01 : 0x00);
			writeD(temp.getItem().getBodyPart());
			
			writeH(temp.getEnchantLevel()); // enchant level
			// race tickets
			writeH(temp.getCustomType2()); // item type3
			
			if (temp.isAugmented())
				writeD(temp.getAugmentation().getAugmentationId());
			else
				writeD(0x00);
			
			writeD(temp.getMana());
		}
	}
	
	@Override
	public String getType()
	{
		return _S__27_ITEMLIST;
	}
}