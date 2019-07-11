package com.l2jhellas.gameserver.model;

import java.util.ArrayList;
import java.util.List;

import com.l2jhellas.gameserver.model.L2ItemInstance.ItemLocation;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public class PcFreight extends ItemContainer
{
	private final L2PcInstance _owner; // This is the L2PcInstance that owns this Freight;
	private int _activeLocationId;
	
	public PcFreight(L2PcInstance owner)
	{
		_owner = owner;
	}
	
	@Override
	public L2PcInstance getOwner()
	{
		return _owner;
	}
	
	@Override
	public ItemLocation getBaseLocation()
	{
		return ItemLocation.FREIGHT;
	}
	
	public void setActiveLocation(int locationId)
	{
		_activeLocationId = locationId;
	}
	
	public int getactiveLocation()
	{
		return _activeLocationId;
	}
	
	@Override
	public int getSize()
	{
		int size = 0;
		for (L2ItemInstance item : _items)
		{
			if (item.getEquipSlot() == 0 || _activeLocationId == 0 || item.getEquipSlot() == _activeLocationId)
				size++;
		}
		return size;
	}
	
	@Override
	public L2ItemInstance[] getItems()
	{
		List<L2ItemInstance> list = new ArrayList<>();
		for (L2ItemInstance item : _items)
		{
			if (item.getEquipSlot() == 0 || item.getEquipSlot() == _activeLocationId)
				list.add(item);
		}
		
		return list.toArray(new L2ItemInstance[list.size()]);
	}
	
	@Override
	public L2ItemInstance getItemByItemId(int itemId)
	{
		for (L2ItemInstance item : _items)
			if ((item.getItemId() == itemId) && (item.getEquipSlot() == 0 || _activeLocationId == 0 || item.getEquipSlot() == _activeLocationId))
				return item;
		
		return null;
	}
	
	@Override
	protected void addItem(L2ItemInstance item)
	{
		super.addItem(item);
		if (_activeLocationId > 0)
			item.setLocation(item.getLocation(), _activeLocationId);
	}
	
	@Override
	public void restore()
	{
		int locationId = _activeLocationId;
		_activeLocationId = 0;
		super.restore();
		_activeLocationId = locationId;
	}
	
	@Override
	public boolean validateCapacity(int slots)
	{
		return (getSize() + slots <= _owner.getFreightLimit());
	}
}