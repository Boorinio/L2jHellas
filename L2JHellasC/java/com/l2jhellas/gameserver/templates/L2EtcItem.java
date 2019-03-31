package com.l2jhellas.gameserver.templates;

import com.l2jhellas.gameserver.emum.L2EtcItemType;

public final class L2EtcItem extends L2Item
{
	
	public L2EtcItem(L2EtcItemType type, StatsSet set)
	{
		super(type, set);
	}
	
	@Override
	public L2EtcItemType getItemType()
	{
		return (L2EtcItemType) super._type;
	}
	
	@Override
	public final boolean isConsumable()
	{
		return ((getItemType() == L2EtcItemType.SHOT) || (getItemType() == L2EtcItemType.POTION)); // || (type == L2EtcItemType.SCROLL));
	}
	
	@Override
	public int getItemMask()
	{
		return getItemType().mask();
	}
}