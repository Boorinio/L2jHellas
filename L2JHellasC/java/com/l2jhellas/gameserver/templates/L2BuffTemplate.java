package com.l2jhellas.gameserver.templates;

import com.l2jhellas.gameserver.emum.ClassRace;
import com.l2jhellas.gameserver.model.L2Skill;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.skills.SkillTable;

public class L2BuffTemplate
{
	
	private final int _templateId;
	
	private final String _templateName;
	
	private final int _skillId;
	
	private final int _skillOrder;
	
	private final L2Skill _skill;
	
	private int _skillLevel;
	
	private final boolean _forceCast;
	
	private final int _minLevel;
	
	private final int _maxLevel;
	
	private final int _faction;
	
	private final int _race;
	
	private final int _class;
	
	private final int _adena;
	
	private final int _points;
	
	public L2BuffTemplate(StatsSet set)
	{
		_templateId = set.getInteger("id");
		_templateName = set.getString("name");
		_skillId = set.getInteger("skillId");
		_skillLevel = set.getInteger("skillLevel");
		_skillOrder = set.getInteger("skillOrder");
		
		if (_skillLevel == 0)
			_skillLevel = SkillTable.getInstance().getMaxLevel(_skillId, _skillLevel);
		
		_skill = SkillTable.getInstance().getInfo(_skillId, _skillLevel);
		
		_forceCast = (set.getInteger("forceCast") == 1);
		_minLevel = set.getInteger("minLevel");
		_maxLevel = set.getInteger("maxLevel");
		_race = set.getInteger("race");
		_class = set.getInteger("class");
		_faction = set.getInteger("faction");
		_adena = set.getInteger("adena");
		_points = set.getInteger("points");
	}
	
	public int getId()
	{
		return _templateId;
	}
	
	public String getName()
	{
		return _templateName;
	}
	
	public int getSkillId()
	{
		return _skillId;
	}
	
	public int getSkillOrder()
	{
		return _skillOrder;
	}
	
	public int getSkillLevel()
	{
		return _skillLevel;
	}
	
	public L2Skill getSkill()
	{
		return _skill;
	}
	
	public int getMinLevel()
	{
		return _minLevel;
	}
	
	public int getMaxLevel()
	{
		return _maxLevel;
	}
	
	public int getFaction()
	{
		return _faction;
	}
	
	public int getAdenaPrice()
	{
		return _adena;
	}
	
	public int getPointsPrice()
	{
		return _points;
	}
	
	public boolean forceCast()
	{
		return _forceCast;
	}
	
	public boolean checkLevel(L2PcInstance player)
	{
		return ((_minLevel == 0 || player.getLevel() >= _minLevel) && (_maxLevel == 0 || player.getLevel() <= _maxLevel));
	}
	
	public boolean checkRace(L2PcInstance player)
	{
		boolean cond = false;
		if (_race == 0 || _race == 31)
			return true;
		if ((player.getRace() == ClassRace.HUMAN) && (_race & 16) != 0)
			cond = true;
		if ((player.getRace() == ClassRace.ELF) && (_race & 8) != 0)
			cond = true;
		if ((player.getRace() == ClassRace.DARK_ELF) && (_race & 4) != 0)
			cond = true;
		if ((player.getRace() == ClassRace.ORC) && (_race & 2) != 0)
			cond = true;
		if ((player.getRace() == ClassRace.DWARF) && (_race & 1) != 0)
			cond = true;
		return cond;
	}
	
	public boolean checkClass(L2PcInstance player)
	{
		return ((_class == 0 || _class == 3) || (_class == 1 && !player.isMageClass()) || (_class == 2 && player.isMageClass()));
	}
	
	public boolean checkFaction(L2PcInstance player)
	{
		return true;
		// return ((_faction == 0 ||player.getFaction = _faction)
	}
	
	public boolean checkPrice(L2PcInstance player)
	{
		return ((_adena == 0 || player.getInventory().getAdena() >= _adena) && (_points == 0 || (player.getEventPoints() >= _points)));
	}
	
	public boolean checkPoints(L2PcInstance player)
	{
		if (player.getEventPoints() >= _points)
			return true;
		return false;
	}
	
	public boolean checkPlayer(L2PcInstance player)
	{
		return (checkLevel(player) && checkRace(player) && checkClass(player) && checkFaction(player));
	}
}