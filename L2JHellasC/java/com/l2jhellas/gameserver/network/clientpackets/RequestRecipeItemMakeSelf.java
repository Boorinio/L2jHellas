package com.l2jhellas.gameserver.network.clientpackets;

import com.l2jhellas.gameserver.controllers.RecipeController;
import com.l2jhellas.gameserver.emum.StoreType;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public final class RequestRecipeItemMakeSelf extends L2GameClientPacket
{
	private static final String _C__AF_REQUESTRECIPEITEMMAKESELF = "[C] AF RequestRecipeItemMakeSelf";
	
	private int _id;
	
	@Override
	protected void readImpl()
	{
		_id = readD();
	}
	
	@Override
	protected void runImpl()
	{
		L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar == null)
			return;
		
		if (activeChar.getPrivateStoreType() != StoreType.NONE)
		{
			activeChar.sendMessage("Cannot make items while trading.");
			return;
		}
		
		if (activeChar.isInCraftMode())
		{
			activeChar.sendMessage("Currently in craft mode.");
			return;
		}
		
		RecipeController.getInstance().requestMakeItem(activeChar, _id);
	}
	
	@Override
	public String getType()
	{
		return _C__AF_REQUESTRECIPEITEMMAKESELF;
	}
}