package com.l2jhellas.gameserver.network.serverpackets;

import com.l2jhellas.gameserver.model.L2ItemInstance;
import com.l2jhellas.gameserver.model.L2Object;

public class SpawnItemPoly extends L2GameServerPacket
{
	private static final String _S__15_SPAWNITEM = "[S] 15 SpawnItem";
	private int _objectId;
	private int _itemId;
	private int _x, _y, _z;
	private int _stackable, _count;
	
	public SpawnItemPoly(L2Object object)
	{
		if (object instanceof L2ItemInstance)
		{
			L2ItemInstance item = (L2ItemInstance) object;
			_objectId = object.getObjectId();
			_itemId = object.getPoly().getPolyId();
			_x = item.getX();
			_y = item.getY();
			_z = item.getZ();
			_stackable = item.isStackable() ? 0x01 : 0x00;
			_count = item.getCount();
		}
		else
		{
			_objectId = object.getObjectId();
			_itemId = object.getPoly().getPolyId();
			_x = object.getX();
			_y = object.getY();
			_z = object.getZ();
			_stackable = 0x00;
			_count = 1;
		}
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0x0b);
		writeD(_objectId);
		writeD(_itemId);
		
		writeD(_x);
		writeD(_y);
		writeD(_z);
		// only show item count if it is a stackable item
		writeD(_stackable);
		writeD(_count);
		writeD(0x00); // c2
	}
	
	@Override
	public String getType()
	{
		return _S__15_SPAWNITEM;
	}
}