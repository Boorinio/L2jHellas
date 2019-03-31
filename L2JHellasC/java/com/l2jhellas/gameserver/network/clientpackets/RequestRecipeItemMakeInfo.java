package com.l2jhellas.gameserver.network.clientpackets;

import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.RecipeItemMakeInfo;

public final class RequestRecipeItemMakeInfo extends L2GameClientPacket
{
	private static final String _C__AE_REQUESTRECIPEITEMMAKEINFO = "[C] AE RequestRecipeItemMakeInfo";
	
	private int _id;
	private L2PcInstance _activeChar;
	
	@Override
	protected void readImpl()
	{
		_id = readD();
		_activeChar = getClient().getActiveChar();
	}
	
	@Override
	protected void runImpl()
	{
		RecipeItemMakeInfo response = new RecipeItemMakeInfo(_id, _activeChar);
		sendPacket(response);
	}
	
	@Override
	public String getType()
	{
		return _C__AE_REQUESTRECIPEITEMMAKEINFO;
	}
}