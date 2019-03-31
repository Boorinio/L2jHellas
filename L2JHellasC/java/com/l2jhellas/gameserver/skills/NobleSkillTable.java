package com.l2jhellas.gameserver.skills;

import com.l2jhellas.gameserver.model.L2Skill;

public class NobleSkillTable
{
	private static NobleSkillTable _instance;
	private static L2Skill[] _nobleSkills;
	
	private NobleSkillTable()
	{
		_nobleSkills = new L2Skill[8];
		_nobleSkills[0] = SkillTable.getInstance().getInfo(1323, 1);
		_nobleSkills[1] = SkillTable.getInstance().getInfo(325, 1);
		_nobleSkills[2] = SkillTable.getInstance().getInfo(326, 1);
		_nobleSkills[3] = SkillTable.getInstance().getInfo(327, 1);
		_nobleSkills[4] = SkillTable.getInstance().getInfo(1324, 1);
		_nobleSkills[5] = SkillTable.getInstance().getInfo(1325, 1);
		_nobleSkills[6] = SkillTable.getInstance().getInfo(1326, 1);
		_nobleSkills[7] = SkillTable.getInstance().getInfo(1327, 1);
	}
	
	public static NobleSkillTable getInstance()
	{
		if (_instance == null)
			_instance = new NobleSkillTable();
		return _instance;
	}
	
	public static void reload()
	{
		_instance = null;
		getInstance();
	}
	
	public L2Skill[] GetNobleSkills()
	{
		return _nobleSkills;
	}
	
	public static boolean isNobleSkill(int skillid)
	{
		Integer[] _NobleSkillsId = new Integer[]
		{
			1323,
			325,
			326,
			327,
			1324,
			1325,
			1326,
			1327
		};
		
		for (int id : _NobleSkillsId)
		{
			if (id == skillid)
				return true;
		}
		
		return false;
	}
}