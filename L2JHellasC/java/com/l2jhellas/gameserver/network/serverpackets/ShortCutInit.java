package com.l2jhellas.gameserver.network.serverpackets;

import com.l2jhellas.gameserver.model.L2ShortCut;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public class ShortCutInit extends L2GameServerPacket
{
	private static final String _S__57_SHORTCUTINIT = "[S] 45 ShortCutInit";
	
	private L2ShortCut[] _shortCuts;
	private L2PcInstance _activeChar;
	
	public ShortCutInit(L2PcInstance activeChar)
	{
		_activeChar = activeChar;
		
		if (_activeChar == null)
			return;
		
		_shortCuts = _activeChar.getAllShortCuts();
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0x45);
		writeD(_shortCuts.length);
		
		for (L2ShortCut sc : _shortCuts)
		{
			writeD(sc.getType());
			writeD(sc.getSlot() + sc.getPage() * 12);
			writeD(sc.getId());
			
			if (sc.getType() == L2ShortCut.TYPE_ITEM)
			{
				writeD(sc.getCharacterType());
				writeD(0x00); // SharedReuseGroup
				writeD(0x00); // Remaining time
				writeD(0x00); // Cooldown time
				writeD(_activeChar.WriteAugmentation(sc));
			}
			else if (sc.getType() == L2ShortCut.TYPE_SKILL)
			{
				writeD(sc.getLevel());
				writeC(0x00); // C5
				writeD(0x01); // C6
			}
			else
			{
				writeD(0x01); // C6
			}
		}
	}
	
	@Override
	public String getType()
	{
		return _S__57_SHORTCUTINIT;
	}
}