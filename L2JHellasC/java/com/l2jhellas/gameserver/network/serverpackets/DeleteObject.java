package com.l2jhellas.gameserver.network.serverpackets;

import com.l2jhellas.gameserver.model.L2Object;

public class DeleteObject extends L2GameServerPacket
{
	private static final String _S__1E_DELETEOBJECT = "[S] 12 DeleteObject";
	private final int _objectId;
	
	public DeleteObject(L2Object obj)
	{
		_objectId = obj.getObjectId();
	}
	
	public DeleteObject(int obj)
	{
		_objectId = obj;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0x12);
		writeD(_objectId);
		writeD(0x00); // c2
	}
	
	@Override
	public String getType()
	{
		return _S__1E_DELETEOBJECT;
	}
}