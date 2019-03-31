package com.l2jhellas.gameserver.network.clientpackets;

import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public final class RequestRecipeShopManageQuit extends L2GameClientPacket
{
	private static final String _C__B3_RequestRecipeShopManageQuit = "[C] b2 RequestRecipeShopManageQuit";
	
	@Override
	protected void readImpl()
	{
		// trigger
	}
	
	@Override
	protected void runImpl()
	{
		L2PcInstance player = getClient().getActiveChar();
		if (player == null)
			return;
		
		player.setPrivateStoreType(L2PcInstance.STORE_PRIVATE_NONE);
		player.broadcastUserInfo();
		player.standUp();
	}
	
	@Override
	public String getType()
	{
		return _C__B3_RequestRecipeShopManageQuit;
	}
}