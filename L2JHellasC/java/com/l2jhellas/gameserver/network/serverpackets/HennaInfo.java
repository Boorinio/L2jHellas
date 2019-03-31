package com.l2jhellas.gameserver.network.serverpackets;

import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.templates.L2Henna;

public final class HennaInfo extends L2GameServerPacket
{
	private static final String _S__E4_HennaInfo = "[S] E4 HennaInfo";
	
	private final L2PcInstance _activeChar;
	private final L2Henna[] _hennas = new L2Henna[3];
	private int _count = 0;
	
	public HennaInfo(L2PcInstance player)
	{
		_activeChar = player;
		_count = 0;
		
		for (int i = 0; i < 3; i++)
		{
			L2Henna henna = _activeChar.getHenna(i + 1);
			if (henna != null)
				_hennas[_count++] = henna;
		}
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0xe4);
		
		writeC(_activeChar.getHennaStatINT()); // equip INT
		writeC(_activeChar.getHennaStatSTR()); // equip STR
		writeC(_activeChar.getHennaStatCON()); // equip CON
		writeC(_activeChar.getHennaStatMEN()); // equip MEM
		writeC(_activeChar.getHennaStatDEX()); // equip DEX
		writeC(_activeChar.getHennaStatWIT()); // equip WIT
		
		// Henna slots
		int classId = _activeChar.getClassId().level();
		if (classId == 1)
			writeD(2);
		else if (classId > 1)
			writeD(3);
		else
			writeD(0);
		
		writeD(_count); // size
		for (int i = 0; i < _count; i++)
		{
			writeD(_hennas[i].getSymbolId());
			writeD(_hennas[i].isForThisClass(_activeChar) ? _hennas[i].getSymbolId() : 0);
		}
	}
	
	@Override
	public String getType()
	{
		return _S__E4_HennaInfo;
	}
}