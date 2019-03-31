package com.l2jhellas.gameserver.network.serverpackets;

import com.l2jhellas.gameserver.model.L2ShortCut;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public class ShortCutRegister extends L2GameServerPacket
{
	private static final String _S__56_SHORTCUTREGISTER = "[S] 44 ShortCutRegister";
	
	private final L2ShortCut _shortcut;
	private final L2PcInstance _player;
	
	public ShortCutRegister(L2PcInstance player, L2ShortCut sc)
	{
		_shortcut = sc;
		_player = player;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0x44);
		
		writeD(_shortcut.getType());
		writeD(_shortcut.getSlot() + _shortcut.getPage() * 12); // C4 Client
		
		if (_shortcut.getType() == L2ShortCut.TYPE_ITEM)
		{
			writeD(_shortcut.getId());
			writeD(_shortcut.getCharacterType());
			writeD(0x00); // SharedReuseGroup
			writeD(0x00); // Remaining time
			writeD(0x00); // Cooldown time
			writeD(_player.WriteAugmentation(_shortcut)); // Augmentation
		}
		else if (_shortcut.getType() == L2ShortCut.TYPE_SKILL)
		{
			writeD(_shortcut.getId());
			writeD(_shortcut.getLevel());
			writeC(0x00); // C5
			writeD(_shortcut.getCharacterType());
		}
		else
		{
			writeD(_shortcut.getId());
			writeD(_shortcut.getCharacterType());
		}
	}
	
	@Override
	public String getType()
	{
		return _S__56_SHORTCUTREGISTER;
	}
}