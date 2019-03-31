package com.l2jhellas.gameserver.network.serverpackets;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.model.L2ItemInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PetInstance;

import java.util.logging.Logger;

public class PetItemList extends L2GameServerPacket
{
	private static Logger _log = Logger.getLogger(PetItemList.class.getName());
	private static final String _S__cb_PETITEMLIST = "[S] b2  PetItemList";
	private final L2PetInstance _activeChar;
	
	public PetItemList(L2PetInstance character)
	{
		_activeChar = character;
		if (Config.DEBUG)
		{
			L2ItemInstance[] items = _activeChar.getInventory().getItems();
			for (L2ItemInstance temp : items)
			{
				_log.fine("item:" + temp.getItem().getItemName() + " type1:" + temp.getItem().getType1() + " type2:" + temp.getItem().getType2());
			}
		}
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0xB2);
		
		L2ItemInstance[] items = _activeChar.getInventory().getItems();
		int count = items.length;
		writeH(count);
		
		for (L2ItemInstance temp : items)
		{
			writeH(temp.getItem().getType1());// item type1
			writeD(temp.getObjectId());
			writeD(temp.getItemId());
			writeD(temp.getCount());
			writeH(temp.getItem().getType2());// item type2
			writeH(0xff);// ?
			if (temp.isEquipped())
			{
				writeH(0x01);
			}
			else
			{
				writeH(0x00);
			}
			writeD(temp.getItem().getBodyPart());
			// slot 0006-lr.ear 0008-neck 0030-lr.finger 0040-head 0080-?? 0100-l.hand 0200-gloves 0400-chest 0800-pants 1000-feet 2000-?? 4000-r.hand 8000-r.hand
			// writeH(temp.getItem().getBodyPart());
			// slot 0006-lr.ear 0008-neck 0030-lr.finger 0040-head 0080-?? 0100-l.hand 0200-gloves 0400-chest 0800-pants 1000-feet 2000-?? 4000-r.hand 8000-r.hand
			writeH(temp.getEnchantLevel()); // enchant level
			writeH(0x00);// ?
		}
	}
	
	@Override
	public String getType()
	{
		return _S__cb_PETITEMLIST;
	}
}