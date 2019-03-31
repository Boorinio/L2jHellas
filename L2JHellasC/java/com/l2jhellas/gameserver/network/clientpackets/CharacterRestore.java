package com.l2jhellas.gameserver.network.clientpackets;

import com.l2jhellas.gameserver.network.serverpackets.CharSelectInfo;

public final class CharacterRestore extends L2GameClientPacket
{
	private static final String _C__62_CHARACTERRESTORE = "[C] 62 CharacterRestore";
	
	// cd
	private int _charSlot;
	
	@Override
	protected void readImpl()
	{
		_charSlot = readD();
	}
	
	@Override
	protected void runImpl()
	{
		getClient().markRestoredChar(_charSlot);
		final CharSelectInfo csi = new CharSelectInfo(getClient().getAccountName(), getClient().getSessionId().playOkID1, 0);
		sendPacket(csi);
		getClient().setCharSelectSlot(csi.getCharacterSlots());
	}
	
	@Override
	public String getType()
	{
		return _C__62_CHARACTERRESTORE;
	}
}