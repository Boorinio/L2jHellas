package com.l2jhellas.shield.antiflood;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public final class FloodProtectors
{
	
	private final FloodProtectorAction _useItem;
	
	private final FloodProtectorAction _rollDice;
	
	private final FloodProtectorAction _firework;
	
	private final FloodProtectorAction _itemPetSummon;
	
	private final FloodProtectorAction _heroVoice;
	
	private final FloodProtectorAction _globalChat;
	
	private final FloodProtectorAction _subclass;
	
	private final FloodProtectorAction _dropItem;
	
	private final FloodProtectorAction _serverBypass;
	
	private final FloodProtectorAction _multiSell;
	
	private final FloodProtectorAction _transaction;
	
	private final FloodProtectorAction _social;
	
	public FloodProtectors(final L2PcInstance player)
	{
		super();
		_useItem = new FloodProtectorAction(player, Config.FLOOD_PROTECTOR_USE_ITEM);
		_rollDice = new FloodProtectorAction(player, Config.FLOOD_PROTECTOR_ROLL_DICE);
		_firework = new FloodProtectorAction(player, Config.FLOOD_PROTECTOR_FIREWORK);
		_itemPetSummon = new FloodProtectorAction(player, Config.FLOOD_PROTECTOR_ITEM_PET_SUMMON);
		_heroVoice = new FloodProtectorAction(player, Config.FLOOD_PROTECTOR_HERO_VOICE);
		_globalChat = new FloodProtectorAction(player, Config.FLOOD_PROTECTOR_GLOBAL_CHAT);
		_subclass = new FloodProtectorAction(player, Config.FLOOD_PROTECTOR_SUBCLASS);
		_dropItem = new FloodProtectorAction(player, Config.FLOOD_PROTECTOR_DROP_ITEM);
		_serverBypass = new FloodProtectorAction(player, Config.FLOOD_PROTECTOR_SERVER_BYPASS);
		_multiSell = new FloodProtectorAction(player, Config.FLOOD_PROTECTOR_MULTISELL);
		_transaction = new FloodProtectorAction(player, Config.FLOOD_PROTECTOR_TRANSACTION);
		_social = new FloodProtectorAction(player, Config.FLOOD_PROTECTOR_SOCIALACTION);
	}
	
	public FloodProtectorAction getUseItem()
	{
		return _useItem;
	}
	
	public FloodProtectorAction getRollDice()
	{
		return _rollDice;
	}
	
	public FloodProtectorAction getFirework()
	{
		return _firework;
	}
	
	public FloodProtectorAction getItemPetSummon()
	{
		return _itemPetSummon;
	}
	
	public FloodProtectorAction getHeroVoice()
	{
		return _heroVoice;
	}
	
	public FloodProtectorAction getGlobalChat()
	{
		return _globalChat;
	}
	
	public FloodProtectorAction getSubclass()
	{
		return _subclass;
	}
	
	public FloodProtectorAction getDropItem()
	{
		return _dropItem;
	}
	
	public FloodProtectorAction getServerBypass()
	{
		return _serverBypass;
	}
	
	public FloodProtectorAction getMultiSell()
	{
		return _multiSell;
	}
	
	public FloodProtectorAction getTransaction()
	{
		return _transaction;
	}
	
	public FloodProtectorAction getSocial()
	{
		return _social;
	}
}