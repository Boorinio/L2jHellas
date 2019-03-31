package com.l2jhellas.gameserver.network.clientpackets;

import com.l2jhellas.gameserver.model.L2ShortCut;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.ShortCutRegister;

public final class RequestShortCutReg extends L2GameClientPacket
{
	private static final String _C__33_REQUESTSHORTCUTREG = "[C] 33 RequestShortCutReg";
	
	private int _type;
	private int _id;
	private int _slot;
	private int _page;
	private int _unk;
	
	@Override
	protected void readImpl()
	{
		_type = readD();
		int slot = readD();
		_id = readD();
		_unk = readD();
		
		_slot = slot % 12;
		_page = slot / 12;
	}
	
	@Override
	protected void runImpl()
	{
		final L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar == null)
			return;
		
		if (_page > 11 || _page < 0)
			return;
		
		final int level = activeChar.getSkillLevel(_id);
		
		final L2ShortCut sc = new L2ShortCut(_slot, _page, _type, _id, level, _unk);
		activeChar.sendPacket(new ShortCutRegister(activeChar, sc));
		activeChar.registerShortCut(sc);
	}
	
	@Override
	public String getType()
	{
		return _C__33_REQUESTSHORTCUTREG;
	}
}