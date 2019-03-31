package com.l2jhellas.gameserver.model;

import com.l2jhellas.gameserver.templates.L2NpcTemplate.AIType;

import java.util.ArrayList;

// This Data is for NPC Attributes and AI relate stuffs...
// Still need to finish...Update later...
public class L2NpcAIData
{
	
	// Basic AI
	private int _primary_attack;
	private int _skill_chance;
	private int _canMove;
	private int _soulshot;
	private int _spiritshot;
	private int _soulshotchance;
	private int _spiritshotchance;
	private int _ischaos;
	private String _clan = null;
	private int _clanRange;
	private String _enemyClan = null;
	private int _enemyRange;
	// private int _baseShldRate;
	// private int _baseShldDef;
	private int _dodge;
	private int _longrangeskill;
	private int _shortrangeskill;
	private int _longrangechance;
	private int _shortrangechance;
	private int _switchrangechance;
	private AIType _aiType = AIType.FIGHTER;
	
	// Skill AI
	public ArrayList<L2Skill> _buffskills;
	public ArrayList<L2Skill> _negativeskills;
	public ArrayList<L2Skill> _debuffskills;
	public ArrayList<L2Skill> _atkskills;
	public ArrayList<L2Skill> _rootskills;
	public ArrayList<L2Skill> _stunskills;
	public ArrayList<L2Skill> _sleepskills;
	public ArrayList<L2Skill> _paralyzeskills;
	public ArrayList<L2Skill> _fossilskills;
	public ArrayList<L2Skill> _floatskills;
	public ArrayList<L2Skill> _immobiliseskills;
	public ArrayList<L2Skill> _healskills;
	public ArrayList<L2Skill> _resskills;
	public ArrayList<L2Skill> _dotskills;
	public ArrayList<L2Skill> _cotskills;
	public ArrayList<L2Skill> _universalskills;
	public ArrayList<L2Skill> _manaskills;
	
	// --------------------------------------------------------------------------------------------------------------
	// Setting....
	// --------------------------------------------------------------------------------------------------------------
	public void setPrimaryAttack(int primaryattack)
	{
		_primary_attack = primaryattack;
	}
	
	public void setSkillChance(int skill_chance)
	{
		_skill_chance = skill_chance;
	}
	
	public void setCanMove(int canMove)
	{
		_canMove = canMove;
	}
	
	public void setSoulShot(int soulshot)
	{
		_soulshot = soulshot;
	}
	
	public void setSpiritShot(int spiritshot)
	{
		_spiritshot = spiritshot;
	}
	
	public void setSoulShotChance(int soulshotchance)
	{
		_soulshotchance = soulshotchance;
	}
	
	public void setSpiritShotChance(int spiritshotchance)
	{
		_spiritshotchance = spiritshotchance;
	}
	
	public void setShortRangeSkill(int shortrangeskill)
	{
		_shortrangeskill = shortrangeskill;
	}
	
	public void setShortRangeChance(int shortrangechance)
	{
		_shortrangechance = shortrangechance;
	}
	
	public void setLongRangeSkill(int longrangeskill)
	{
		_longrangeskill = longrangeskill;
	}
	
	public void setLongRangeChance(int longrangechance)
	{
		_shortrangechance = longrangechance;
	}
	
	public void setSwitchRangeChance(int switchrangechance)
	{
		_switchrangechance = switchrangechance;
	}
	
	public void setIsChaos(int ischaos)
	{
		_ischaos = ischaos;
	}
	
	public void setClan(String clan)
	{
		if (clan != null && !clan.equals("") && !clan.equalsIgnoreCase("null"))
			_clan = clan.intern();
	}
	
	public void setClanRange(int clanRange)
	{
		_clanRange = clanRange;
	}
	
	public void setEnemyClan(String enemyClan)
	{
		if (enemyClan != null && !enemyClan.equals("") && !enemyClan.equalsIgnoreCase("null"))
			_enemyClan = enemyClan.intern();
	}
	
	public void setEnemyRange(int enemyRange)
	{
		_enemyRange = enemyRange;
	}
	
	public void setDodge(int dodge)
	{
		_dodge = dodge;
	}
	
	public void setAi(String ai)
	{
		if (ai.equalsIgnoreCase("archer"))
			_aiType = AIType.ARCHER;
		else if (ai.equalsIgnoreCase("balanced"))
			_aiType = AIType.BALANCED;
		else if (ai.equalsIgnoreCase("mage"))
			_aiType = AIType.MAGE;
		else if (ai.equalsIgnoreCase("healer"))
			_aiType = AIType.HEALER;
		else if (ai.equalsIgnoreCase("corpse"))
			_aiType = AIType.CORPSE;
		else
			_aiType = AIType.FIGHTER;
	}
	
	// --------------------------------------------------------------------------------------------------------------
	// Data Recall....
	// --------------------------------------------------------------------------------------------------------------
	public int getPrimaryAttack()
	{
		return _primary_attack;
	}
	
	public int getSkillChance()
	{
		return _skill_chance;
	}
	
	public int getCanMove()
	{
		return _canMove;
	}
	
	public int getSoulShot()
	{
		return _soulshot;
	}
	
	public int getSpiritShot()
	{
		return _spiritshot;
	}
	
	public int getSoulShotChance()
	{
		return _soulshotchance;
	}
	
	public int getSpiritShotChance()
	{
		return _spiritshotchance;
	}
	
	public int getShortRangeSkill()
	{
		return _shortrangeskill;
	}
	
	public int getShortRangeChance()
	{
		return _shortrangechance;
	}
	
	public int getLongRangeSkill()
	{
		return _longrangeskill;
	}
	
	public int getLongRangeChance()
	{
		return _longrangechance;
	}
	
	public int getSwitchRangeChance()
	{
		return _switchrangechance;
	}
	
	public int getIsChaos()
	{
		return _ischaos;
	}
	
	public String getClan()
	{
		return _clan;
	}
	
	public int getClanRange()
	{
		return _clanRange;
	}
	
	public String getEnemyClan()
	{
		return _enemyClan;
	}
	
	public int getEnemyRange()
	{
		return _enemyRange;
	}
	
	public int getDodge()
	{
		return _dodge;
	}
	
	public AIType getAiType()
	{
		return _aiType;
	}
	
	public void addBuffSkill(L2Skill skill)
	{
		if (_buffskills == null)
			_buffskills = new ArrayList<>();
		_buffskills.add(skill);
	}
	
	public void addHealSkill(L2Skill skill)
	{
		if (_healskills == null)
			_healskills = new ArrayList<>();
		_healskills.add(skill);
	}
	
	public void addResSkill(L2Skill skill)
	{
		if (_resskills == null)
			_resskills = new ArrayList<>();
		_resskills.add(skill);
	}
	
	public void addAtkSkill(L2Skill skill)
	{
		if (_atkskills == null)
			_atkskills = new ArrayList<>();
		_atkskills.add(skill);
	}
	
	public void addDebuffSkill(L2Skill skill)
	{
		if (_debuffskills == null)
			_debuffskills = new ArrayList<>();
		_debuffskills.add(skill);
	}
	
	public void addRootSkill(L2Skill skill)
	{
		if (_rootskills == null)
			_rootskills = new ArrayList<>();
		_rootskills.add(skill);
	}
	
	public void addSleepSkill(L2Skill skill)
	{
		if (_sleepskills == null)
			_sleepskills = new ArrayList<>();
		_sleepskills.add(skill);
	}
	
	public void addStunSkill(L2Skill skill)
	{
		if (_stunskills == null)
			_stunskills = new ArrayList<>();
		_stunskills.add(skill);
	}
	
	public void addParalyzeSkill(L2Skill skill)
	{
		if (_paralyzeskills == null)
			_paralyzeskills = new ArrayList<>();
		_paralyzeskills.add(skill);
	}
	
	public void addFloatSkill(L2Skill skill)
	{
		if (_floatskills == null)
			_floatskills = new ArrayList<>();
		_floatskills.add(skill);
	}
	
	public void addFossilSkill(L2Skill skill)
	{
		if (_fossilskills == null)
			_fossilskills = new ArrayList<>();
		_fossilskills.add(skill);
	}
	
	public void addNegativeSkill(L2Skill skill)
	{
		if (_negativeskills == null)
			_negativeskills = new ArrayList<>();
		_negativeskills.add(skill);
	}
	
	public void addImmobiliseSkill(L2Skill skill)
	{
		if (_immobiliseskills == null)
			_immobiliseskills = new ArrayList<>();
		_immobiliseskills.add(skill);
	}
	
	public void addDOTSkill(L2Skill skill)
	{
		if (_dotskills == null)
			_dotskills = new ArrayList<>();
		_dotskills.add(skill);
	}
	
	public void addUniversalSkill(L2Skill skill)
	{
		if (_universalskills == null)
			_universalskills = new ArrayList<>();
		_universalskills.add(skill);
	}
	
	public void addCOTSkill(L2Skill skill)
	{
		if (_cotskills == null)
			_cotskills = new ArrayList<>();
		_cotskills.add(skill);
	}
	
	public void addManaHealSkill(L2Skill skill)
	{
		if (_manaskills == null)
			_manaskills = new ArrayList<>();
		_manaskills.add(skill);
	}
	
	// --------------------------------------------------------------------
	public boolean hasBuffSkill()
	{
		if (_buffskills != null && _buffskills.size() > 0)
			return true;
		return false;
	}
	
	public boolean hasHealSkill()
	{
		if (_healskills != null && _healskills.size() > 0)
			return true;
		return false;
	}
	
	public boolean hasResSkill()
	{
		if (_resskills != null && _resskills.size() > 0)
			return true;
		return false;
	}
	
	public boolean hasAtkSkill()
	{
		if (_atkskills != null && _atkskills.size() > 0)
			return true;
		return false;
	}
	
	public boolean hasDebuffSkill()
	{
		if (_debuffskills != null && _debuffskills.size() > 0)
			return true;
		return false;
	}
	
	public boolean hasRootSkill()
	{
		if (_rootskills != null && _rootskills.size() > 0)
			return true;
		return false;
	}
	
	public boolean hasSleepSkill()
	{
		if (_sleepskills != null && _sleepskills.size() > 0)
			return true;
		return false;
	}
	
	public boolean hasStunSkill()
	{
		if (_stunskills != null && _stunskills.size() > 0)
			return true;
		return false;
	}
	
	public boolean hasParalyzeSkill()
	{
		if (_paralyzeskills != null && _paralyzeskills.size() > 0)
			return true;
		return false;
	}
	
	public boolean hasFloatSkill()
	{
		if (_floatskills != null && _floatskills.size() > 0)
			return true;
		return false;
	}
	
	public boolean hasFossilSkill()
	{
		if (_fossilskills != null && _fossilskills.size() > 0)
			return true;
		return false;
	}
	
	public boolean hasNegativeSkill()
	{
		if (_negativeskills != null && _negativeskills.size() > 0)
			return true;
		return false;
	}
	
	public boolean hasImmobiliseSkill()
	{
		if (_immobiliseskills != null && _immobiliseskills.size() > 0)
			return true;
		return false;
	}
	
	public boolean hasDOTSkill()
	{
		if (_dotskills != null && _dotskills.size() > 0)
			return true;
		return false;
	}
	
	public boolean hasUniversalSkill()
	{
		if (_universalskills != null && _universalskills.size() > 0)
			return true;
		return false;
	}
	
	public boolean hasCOTSkill()
	{
		if (_cotskills != null && _cotskills.size() > 0)
			return true;
		return false;
	}
	
	public boolean hasManaHealSkill()
	{
		if (_manaskills != null && _manaskills.size() > 0)
			return true;
		return false;
	}
}