package com.l2jhellas.gameserver.datatables;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.l2jhellas.gameserver.DateRange;

public class EventDroplist
{
	private static EventDroplist _instance;
	
	private final List<DateDrop> _allNpcDateDrops;
	
	public static EventDroplist getInstance()
	{
		if (_instance == null)
		{
			_instance = new EventDroplist();
		}
		return _instance;
	}
	
	public class DateDrop
	{
		
		public DateRange dateRange;
		
		public int[] items;
		
		public int min;
		
		public int max;
		
		public int chance;
	}
	
	private EventDroplist()
	{
		_allNpcDateDrops = new ArrayList<>();
	}
	
	public void addGlobalDrop(int[] items, int[] count, int chance, DateRange range)
	{
		DateDrop date = new DateDrop();
		
		date.dateRange = range;
		date.items = items;
		date.min = count[0];
		date.max = count[1];
		date.chance = chance;
		
		_allNpcDateDrops.add(date);
	}
	
	public List<DateDrop> getAllDrops()
	{
		List<DateDrop> list = new ArrayList<>();
		
		for (DateDrop drop : _allNpcDateDrops)
		{
			Date currentDate = new Date();
			if (drop.dateRange.isWithinRange(currentDate))
			{
				list.add(drop);
			}
		}
		return list;
	}
}