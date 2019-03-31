package com.l2jhellas.gameserver.model;

import com.l2jhellas.gameserver.holder.IntIntHolder;

import java.util.List;

public class L2ExtractableProductItem
{
	private final List<IntIntHolder> _items;
	private final double _chance;
	
	public L2ExtractableProductItem(List<IntIntHolder> items, double chance)
	{
		_items = items;
		_chance = chance;
	}
	
	public List<IntIntHolder> getItems()
	{
		return _items;
	}
	
	public double getChance()
	{
		return _chance;
	}
}