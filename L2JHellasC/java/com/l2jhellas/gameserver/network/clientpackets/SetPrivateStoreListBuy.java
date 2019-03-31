package com.l2jhellas.gameserver.network.clientpackets;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.model.TradeList;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.PrivateStoreManageListBuy;
import com.l2jhellas.gameserver.network.serverpackets.PrivateStoreMsgBuy;

public final class SetPrivateStoreListBuy extends L2GameClientPacket
{
	private static final String _C__91_SETPRIVATESTORELISTBUY = "[C] 91 SetPrivateStoreListBuy";
	
	private int _count;
	private int[] _items; // count * 3
	
	@Override
	protected void readImpl()
	{
		_count = readD();
		if (_count <= 0 || _count * 12 > _buf.remaining() || _count > Config.MAX_ITEM_IN_PACKET)
		{
			_count = 0;
			_items = null;
			return;
		}
		_items = new int[_count * 3];
		for (int x = 0; x < _count; x++)
		{
			int itemId = readD();
			_items[x * 3 + 0] = itemId;
			readH();// TODO analyze this
			readH();// TODO analyze this
			long cnt = readD();
			if (cnt > Integer.MAX_VALUE || cnt < 0)
			{
				_count = 0;
				_items = null;
				return;
			}
			_items[x * 3 + 1] = (int) cnt;
			int price = readD();
			_items[x * 3 + 2] = price;
		}
	}
	
	@Override
	protected void runImpl()
	{
		L2PcInstance player = getClient().getActiveChar();
		if (player == null)
			return;
		
		if (!player.getAccessLevel().allowTransaction())
		{
			player.sendMessage("Transactions are disabled for your Access Level.");
			return;
		}
		
		TradeList tradeList = player.getBuyList();
		tradeList.clear();
		
		int cost = 0;
		for (int i = 0; i < _count; i++)
		{
			int itemId = _items[i * 3 + 0];
			int count = _items[i * 3 + 1];
			int price = _items[i * 3 + 2];
			
			tradeList.addItemByItemId(itemId, count, price);
			cost += count * price;
		}
		
		if (_count <= 0)
		{
			player.setPrivateStoreType(L2PcInstance.STORE_PRIVATE_NONE);
			player.broadcastUserInfo();
			return;
		}
		
		// Check maximum number of allowed slots for pvt shops
		if (_count > player.getPrivateBuyStoreLimit())
		{
			player.sendPacket(new PrivateStoreManageListBuy(player));
			player.sendPacket(SystemMessageId.YOU_HAVE_EXCEEDED_QUANTITY_THAT_CAN_BE_INPUTTED);
			return;
		}
		
		// Check for available funds
		if (cost > player.getAdena() || cost <= 0)
		{
			player.sendPacket(new PrivateStoreManageListBuy(player));
			player.sendPacket(SystemMessageId.THE_PURCHASE_PRICE_IS_HIGHER_THAN_MONEY);
			return;
		}
		
		player.sitDown();
		player.setPrivateStoreType(L2PcInstance.STORE_PRIVATE_BUY);
		player.broadcastUserInfo();
		player.broadcastPacket(new PrivateStoreMsgBuy(player));
	}
	
	@Override
	public String getType()
	{
		return _C__91_SETPRIVATESTORELISTBUY;
	}
}