package com.l2jhellas.gameserver.instancemanager;

import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.zone.type.L2OlympiadStadiumZone;

import java.util.ArrayList;
import java.util.List;

public class OlympiadStadiaManager
{
	
	private final List<L2OlympiadStadiumZone> _olympiadStadias = new ArrayList<>();
	
	public OlympiadStadiaManager()
	{
	}
	
	public void addStadium(L2OlympiadStadiumZone arena)
	{
		_olympiadStadias.add(arena);
	}
	
	public void clearStadium()
	{
		_olympiadStadias.clear();
	}
	
	public final L2OlympiadStadiumZone getStadium(L2Character character)
	{
		for (L2OlympiadStadiumZone temp : _olympiadStadias)
			if (temp.isCharacterInZone(character))
				return temp;
		
		return null;
	}
	
	@Deprecated
	public final L2OlympiadStadiumZone getOlympiadStadiumById(int olympiadStadiumId)
	{
		for (L2OlympiadStadiumZone temp : _olympiadStadias)
			if (temp.getStadiumId() == olympiadStadiumId)
				return temp;
		return null;
	}
	
	private static OlympiadStadiaManager _instance;
	
	public static final OlympiadStadiaManager getInstance()
	{
		if (_instance == null)
		{
			_instance = new OlympiadStadiaManager();
		}
		return _instance;
	}
}