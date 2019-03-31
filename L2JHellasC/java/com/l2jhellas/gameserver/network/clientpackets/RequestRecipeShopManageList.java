package com.l2jhellas.gameserver.network.clientpackets;

import com.l2jhellas.gameserver.model.L2ManufactureList;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.ActionFailed;
import com.l2jhellas.gameserver.network.serverpackets.RecipeShopManageList;

public final class RequestRecipeShopManageList extends L2GameClientPacket
{
	private static final String _C__B0_RequestRecipeShopManageList = "[C] b0 RequestRecipeShopManageList";
	
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
		
		// Player shouldn't be able to set stores if he/she is alike dead (dead or fake death)
		if (player.isAlikeDead())
		{
			sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		if (player.getPrivateStoreType() != 0)
		{
			player.setPrivateStoreType(L2PcInstance.STORE_PRIVATE_NONE);
			player.broadcastUserInfo();
			if (player.isSitting())
				player.standUp();
		}
		if (player.getCreateList() == null)
		{
			player.setCreateList(new L2ManufactureList());
		}
		
		player.sendPacket(new RecipeShopManageList(player, true));
		
	}
	
	@Override
	public String getType()
	{
		return _C__B0_RequestRecipeShopManageList;
	}
}