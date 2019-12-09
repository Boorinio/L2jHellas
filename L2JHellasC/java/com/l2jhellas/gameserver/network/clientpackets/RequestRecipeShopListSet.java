package com.l2jhellas.gameserver.network.clientpackets;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.emum.ZoneId;
import com.l2jhellas.gameserver.emum.player.StoreType;
import com.l2jhellas.gameserver.model.L2ManufactureItem;
import com.l2jhellas.gameserver.model.L2ManufactureList;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.RecipeShopMsg;
import com.l2jhellas.gameserver.taskmanager.AttackStanceTaskManager;

public final class RequestRecipeShopListSet extends L2GameClientPacket
{
	private static final String _C__B2_RequestRecipeShopListSet = "[C] b2 RequestRecipeShopListSet";
	
	private int _count;
	private int[] _items; // count*2
	
	@Override
	protected void readImpl()
	{
		_count = readD();
		if (_count <= 0 || _count * 8 > _buf.remaining() || _count > Config.MAX_ITEM_IN_PACKET)
			_count = 0;
		_items = new int[_count * 2];
		for (int x = 0; x < _count; x++)
		{
			int recipeID = readD();
			_items[x * 2 + 0] = recipeID;
			int cost = readD();
			_items[x * 2 + 1] = cost;
		}
	}
	
	@Override
	protected void runImpl()
	{
		L2PcInstance player = getClient().getActiveChar();
		
		if (player == null)
			return;
			
		if (_count == 0)
		{
			player.sendPacket(SystemMessageId.NO_RECIPES_REGISTERED);
			player.setPrivateStoreType(StoreType.NONE);
			player.broadcastUserInfo();
			return;	
		}
		
		if (player.isInsideZone(ZoneId.NO_STORE))
		{
			player.sendPacket(SystemMessageId.NO_PRIVATE_STORE_HERE);
			return;
		}
		
		if (player.isSitting() && !player.isInStoreMode())
		    return;
		
		if (player.isAlikeDead() || player.isMounted() || player.isProcessingRequest())
			 return;

		if (player.isInDuel()  || player.isCastingNow() || AttackStanceTaskManager.getInstance().isInAttackStance(player) || player.isInOlympiadMode())
		{
			player.sendPacket(SystemMessageId.CANT_OPERATE_PRIVATE_STORE_DURING_COMBAT);
			return;
		}

		L2ManufactureList createList = new L2ManufactureList();
			
		for (int x = 0; x < _count; x++)
		{
			int recipeID = _items[x * 2 + 0];
			int cost = _items[x * 2 + 1];
			createList.add(new L2ManufactureItem(recipeID, cost));
		}
		
		createList.setStoreName(player.getCreateList() != null ? player.getCreateList().getStoreName() : "");
		player.setCreateList(createList);
			
		player.setPrivateStoreType(StoreType.MANUFACTURE);
		player.sitDown();
		player.broadcastUserInfo();
		player.broadcastPacket(new RecipeShopMsg(player));

	}
	
	@Override
	public String getType()
	{
		return _C__B2_RequestRecipeShopListSet;
	}
}