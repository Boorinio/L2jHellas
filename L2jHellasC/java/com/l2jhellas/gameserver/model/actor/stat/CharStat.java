/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jhellas.gameserver.model.actor.stat;

import Extensions.Balancer.BalancerMain;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.model.L2Character;
import com.l2jhellas.gameserver.model.L2Skill;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.skills.Calculator;
import com.l2jhellas.gameserver.skills.Env;
import com.l2jhellas.gameserver.skills.Stats;

public class CharStat
{
	private final L2Character _activeChar;
	private long _exp = 0;
	private int _sp = 0;
	private byte _level = 1;

	private int _fire = 0;
	private int _water = 0;
	private int _wind = 0;
	private int _earth = 0;
	private int _holy = 0;
	private int _dark = 0;

	public CharStat(L2Character activeChar)
	{
		_activeChar = activeChar;
	}

	/**
	 * Calculate the new value of the state with modifiers that will be applied
	 * on the targeted L2Character.<BR>
	 * <BR>
	 * <B><U> Concept</U> :</B><BR>
	 * <BR>
	 * A L2Character owns a table of Calculators called <B>_calculators</B>.
	 * Each Calculator (a calculator per state) own a table of Func object. A
	 * Func object is a mathematic function that permit to calculate the
	 * modifier of a state (ex : REGENERATE_HP_RATE...) : <BR>
	 * <BR>
	 * FuncAtkAccuracy -> Math.sqrt(_player.getDEX())*6+_player.getLevel()<BR>
	 * <BR>
	 * When the calc method of a calculator is launched, each mathematic
	 * function is called according to its priority <B>_order</B>. Indeed, Func
	 * with lowest priority order is executed firsta and Funcs with the same
	 * order are executed in unspecified order. The result of the calculation is
	 * stored in the value property of an Env class instance.<BR>
	 * <BR>
	 *
	 * @param stat
	 *        The stat to calculate the new value with modifiers
	 * @param init
	 *        The initial value of the stat before applying modifiers
	 * @param target
	 *        The L2Charcater whose properties will be used in the
	 *        calculation (ex : CON, INT...)
	 * @param skill
	 *        The L2Skill whose properties will be used in the calculation
	 *        (ex : Level...)
	 */
	public final double calcStat(Stats stat, double init, L2Character target, L2Skill skill)
	{
		if (_activeChar == null)
			return init;

		int id = stat.ordinal();

		Calculator c = _activeChar.getCalculators()[id];

		// If no Func object found, no modifier is applied
		if (c == null || c.size() == 0)
			return init;

		// Create and init an Env object to pass parameters to the Calculator
		Env env = new Env();
		env.player = _activeChar;
		env.target = target;
		env.skill = skill;
		env.value = init;

		// Launch the calculation
		c.calc(env);
		// avoid some troubles with negative stats (some stats should never be
		// negative)
		if (env.value <= 0 && ((stat == Stats.MAX_HP) || (stat == Stats.MAX_MP) || (stat == Stats.MAX_CP) || (stat == Stats.MAGIC_DEFENCE) || (stat == Stats.POWER_DEFENCE) || (stat == Stats.POWER_ATTACK) || (stat == Stats.MAGIC_ATTACK) || (stat == Stats.POWER_ATTACK_SPEED) || (stat == Stats.MAGIC_ATTACK_SPEED) || (stat == Stats.SHIELD_DEFENCE) || (stat == Stats.STAT_CON) || (stat == Stats.STAT_DEX) || (stat == Stats.STAT_INT) || (stat == Stats.STAT_MEN) || (stat == Stats.STAT_STR) || (stat == Stats.STAT_WIT)))
		{
			env.value = 1;
		}

		return env.value;
	}

	/**
	 * Return the Accuracy (base+modifier) of the L2Character in function of the
	 * Weapon Expertise Penalty.
	 */
	public int getAccuracy()
	{
		if (_activeChar == null)
			return 0;

		double val = (calcStat(Stats.ACCURACY_COMBAT, 0, null, null) / _activeChar.getWeaponExpertisePenalty());
		if (_activeChar instanceof L2PcInstance)
		{
			switch (((L2PcInstance) _activeChar).getClassId().getId())
			{
				case 88:
					return (int) val + BalancerMain.accplus88;
				case 89:
					return (int) val + BalancerMain.accplus89;
				case 90:
					return (int) val + BalancerMain.accplus90;
				case 91:
					return (int) val + BalancerMain.accplus91;
				case 92:
					return (int) val + BalancerMain.accplus92;
				case 93:
					return (int) val + BalancerMain.accplus93;
				case 94:
					return (int) val + BalancerMain.accplus94;
				case 95:
					return (int) val + BalancerMain.accplus95;
				case 96:
					return (int) val + BalancerMain.accplus96;
				case 97:
					return (int) val + BalancerMain.accplus97;
				case 98:
					return (int) val + BalancerMain.accplus98;
				case 99:
					return (int) val + BalancerMain.accplus99;
				case 100:
					return (int) val + BalancerMain.accplus100;
				case 101:
					return (int) val + BalancerMain.accplus101;
				case 102:
					return (int) val + BalancerMain.accplus102;
				case 103:
					return (int) val + BalancerMain.accplus103;
				case 104:
					return (int) val + BalancerMain.accplus104;
				case 105:
					return (int) val + BalancerMain.accplus105;
				case 106:
					return (int) val + BalancerMain.accplus106;
				case 107:
					return (int) val + BalancerMain.accplus107;
				case 108:
					return (int) val + BalancerMain.accplus108;
				case 109:
					return (int) val + BalancerMain.accplus109;
				case 110:
					return (int) val + BalancerMain.accplus110;
				case 111:
					return (int) val + BalancerMain.accplus111;
				case 112:
					return (int) val + BalancerMain.accplus112;
				case 113:
					return (int) val + BalancerMain.accplus113;
				case 114:
					return (int) val + BalancerMain.accplus114;
				case 115:
					return (int) val + BalancerMain.accplus115;
				case 116:
					return (int) val + BalancerMain.accplus116;
				case 117:
					return (int) val + BalancerMain.accplus117;
				case 118:
					return (int) val + BalancerMain.accplus118;
			}
		}
		return (int) val;
	}

	public L2Character getActiveChar()
	{
		return _activeChar;
	}

	/**
	 * Return the Attack Speed multiplier (base+modifier) of the L2Character to
	 * get proper animations.
	 */
	public final float getAttackSpeedMultiplier()
	{
		if (_activeChar == null)
			return 1;

		return (float) ((1.1) * getPAtkSpd() / _activeChar.getTemplate().basePAtkSpd);
	}

	/** Return the Critical Hit rate (base+modifier) of the L2Character. */
	public int getCriticalHit(L2Character target, L2Skill skill)
	{
		if (_activeChar == null)
			return 1;

		int criticalHit = (int) (calcStat(Stats.CRITICAL_RATE, _activeChar.getTemplate().baseCritRate, target, skill) * 10.0 + 0.5);
		criticalHit /= 10;
		// Set a cap of Critical Hit at 500
		if (criticalHit > Config.MAX_PCRIT_RATE)
		{
			criticalHit = Config.MAX_PCRIT_RATE;
		}

		return criticalHit;
	}

	/** Return the Magic Critical Hit rate (base+modifier) of the L2Character. */
	public final int getMCriticalHit(L2Character target, L2Skill skill)
	{
		if (_activeChar == null)
			return 1;

		double mrate = calcStat(Stats.MCRITICAL_RATE, _activeChar.getTemplate().baseMCritRate, target, skill);
		if (mrate > Config.MAX_MCRIT_RATE)
		{
			mrate = Config.MAX_MCRIT_RATE;
		}
		return (int) mrate;
	}

	/**
	 * Return the Critical Damage rate
	 * (base+modifier) of the L2Character.
	 */
	public final double getCriticalDmg(L2Character target, double init)
	{
		return calcStat(Stats.CRITICAL_DAMAGE, init, target, null);
	}

	/**
	 * Return the MEN
	 * of the L2Character (base+modifier).
	 */
	public final int getMEN()
	{
		if (_activeChar == null)
			return 1;

		return (int) calcStat(Stats.STAT_MEN, _activeChar.getTemplate().baseMEN, null, null);
	}

	/**
	 * Return the STR
	 * of the L2Character (base+modifier).
	 */
	public final int getSTR()
	{
		if (_activeChar == null)
			return 1;

		return (int) calcStat(Stats.STAT_STR, _activeChar.getTemplate().baseSTR, null, null);
	}

	/**
	 * Return the WIT
	 * of the L2Character (base+modifier).
	 */
	public final int getWIT()
	{
		if (_activeChar == null)
			return 1;

		return (int) calcStat(Stats.STAT_WIT, _activeChar.getTemplate().baseWIT, null, null);
	}

	/**
	 * Return the CON
	 * of the L2Character (base+modifier).
	 */
	public final int getCON()
	{
		if (_activeChar == null)
			return 1;

		return (int) calcStat(Stats.STAT_CON, _activeChar.getTemplate().baseCON, null, null);
	}

	/**
	 * Return the DEX
	 * of the L2Character (base+modifier).
	 */
	public final int getDEX()
	{
		if (_activeChar == null)
			return 1;

		return (int) calcStat(Stats.STAT_DEX, _activeChar.getTemplate().baseDEX, null, null);
	}

	/**
	 * Return the INT
	 * of the L2Character (base+modifier).
	 */
	public int getINT()
	{
		if (_activeChar == null)
			return 1;

		return (int) calcStat(Stats.STAT_INT, _activeChar.getTemplate().baseINT, null, null);
	}

	/**
	 * Return the Attack Evasion
	 * rate (base+modifier) of the L2Character.
	 */
	public int getEvasionRate(L2Character target)
	{
		if (_activeChar == null)
			return 1;

		double val = (calcStat(Stats.EVASION_RATE, 0, target, null) / _activeChar.getArmourExpertisePenalty());
		if (_activeChar instanceof L2PcInstance)
		{
			switch (((L2PcInstance) _activeChar).getClassId().getId())
			{
				case 88:
					return (int) val + BalancerMain.evasionplus88;
				case 89:
					return (int) val + BalancerMain.evasionplus89;
				case 90:
					return (int) val + BalancerMain.evasionplus90;
				case 91:
					return (int) val + BalancerMain.evasionplus91;
				case 92:
					return (int) val + BalancerMain.evasionplus92;
				case 93:
					return (int) val + BalancerMain.evasionplus93;
				case 94:
					return (int) val + BalancerMain.evasionplus94;
				case 95:
					return (int) val + BalancerMain.evasionplus95;
				case 96:
					return (int) val + BalancerMain.evasionplus96;
				case 97:
					return (int) val + BalancerMain.evasionplus97;
				case 98:
					return (int) val + BalancerMain.evasionplus98;
				case 99:
					return (int) val + BalancerMain.evasionplus99;
				case 100:
					return (int) val + BalancerMain.evasionplus100;
				case 101:
					return (int) val + BalancerMain.evasionplus101;
				case 102:
					return (int) val + BalancerMain.evasionplus102;
				case 103:
					return (int) val + BalancerMain.evasionplus103;
				case 104:
					return (int) val + BalancerMain.evasionplus104;
				case 105:
					return (int) val + BalancerMain.evasionplus105;
				case 106:
					return (int) val + BalancerMain.evasionplus106;
				case 107:
					return (int) val + BalancerMain.evasionplus107;
				case 108:
					return (int) val + BalancerMain.evasionplus108;
				case 109:
					return (int) val + BalancerMain.evasionplus109;
				case 110:
					return (int) val + BalancerMain.evasionplus110;
				case 111:
					return (int) val + BalancerMain.evasionplus111;
				case 112:
					return (int) val + BalancerMain.evasionplus112;
				case 113:
					return (int) val + BalancerMain.evasionplus113;
				case 114:
					return (int) val + BalancerMain.evasionplus114;
				case 115:
					return (int) val + BalancerMain.evasionplus115;
				case 116:
					return (int) val + BalancerMain.evasionplus116;
				case 117:
					return (int) val + BalancerMain.evasionplus117;
				case 118:
					return (int) val + BalancerMain.evasionplus118;
			}
		}
		return (int) val;
	}

	public long getExp()
	{
		return _exp;
	}

	public void setExp(long value)
	{
		_exp = value;
	}

	public byte getLevel()
	{
		return _level;
	}

	public void setLevel(byte value)
	{
		_level = value;
	}

	/** Return the Magical Attack range (base+modifier) of the L2Character. */
	public final int getMagicalAttackRange(L2Skill skill)
	{
		if (skill != null)
			return (int) calcStat(Stats.MAGIC_ATTACK_RANGE, skill.getCastRange(), null, skill);

		if (_activeChar == null)
			return 1;

		return _activeChar.getTemplate().baseAtkRange;
	}

	public int getMaxCp()
	{
		if (_activeChar == null)
			return 1;

		double val = calcStat(Stats.MAX_CP, _activeChar.getTemplate().baseCpMax, null, null);
		if (_activeChar instanceof L2PcInstance)
		{
			switch (((L2PcInstance) _activeChar).getClassId().getId())
			{
				case 88:
					return (int) val + BalancerMain.cpplus88;
				case 89:
					return (int) val + BalancerMain.cpplus89;
				case 90:
					return (int) val + BalancerMain.cpplus90;
				case 91:
					return (int) val + BalancerMain.cpplus91;
				case 92:
					return (int) val + BalancerMain.cpplus92;
				case 93:
					return (int) val + BalancerMain.cpplus93;
				case 94:
					return (int) val + BalancerMain.cpplus94;
				case 95:
					return (int) val + BalancerMain.cpplus95;
				case 96:
					return (int) val + BalancerMain.cpplus96;
				case 97:
					return (int) val + BalancerMain.cpplus97;
				case 98:
					return (int) val + BalancerMain.cpplus98;
				case 99:
					return (int) val + BalancerMain.cpplus99;
				case 100:
					return (int) val + BalancerMain.cpplus100;
				case 101:
					return (int) val + BalancerMain.cpplus101;
				case 102:
					return (int) val + BalancerMain.cpplus102;
				case 103:
					return (int) val + BalancerMain.cpplus103;
				case 104:
					return (int) val + BalancerMain.cpplus104;
				case 105:
					return (int) val + BalancerMain.cpplus105;
				case 106:
					return (int) val + BalancerMain.cpplus106;
				case 107:
					return (int) val + BalancerMain.cpplus107;
				case 108:
					return (int) val + BalancerMain.cpplus108;
				case 109:
					return (int) val + BalancerMain.cpplus109;
				case 110:
					return (int) val + BalancerMain.cpplus110;
				case 111:
					return (int) val + BalancerMain.cpplus111;
				case 112:
					return (int) val + BalancerMain.cpplus112;
				case 113:
					return (int) val + BalancerMain.cpplus113;
				case 114:
					return (int) val + BalancerMain.cpplus114;
				case 115:
					return (int) val + BalancerMain.cpplus115;
				case 116:
					return (int) val + BalancerMain.cpplus116;
				case 117:
					return (int) val + BalancerMain.cpplus117;
				case 118:
					return (int) val + BalancerMain.cpplus118;
			}
		}
		return (int) val;
	}

	public int getMaxHp()
	{
		if (_activeChar == null)
			return 1;

		double val = calcStat(Stats.MAX_HP, _activeChar.getTemplate().baseHpMax, null, null);
		if (_activeChar instanceof L2PcInstance)
		{
			switch (((L2PcInstance) _activeChar).getClassId().getId())
			{
				case 88:
					return (int) val + BalancerMain.hpplus88;
				case 89:
					return (int) val + BalancerMain.hpplus89;
				case 90:
					return (int) val + BalancerMain.hpplus90;
				case 91:
					return (int) val + BalancerMain.hpplus91;
				case 92:
					return (int) val + BalancerMain.hpplus92;
				case 93:
					return (int) val + BalancerMain.hpplus93;
				case 94:
					return (int) val + BalancerMain.hpplus94;
				case 95:
					return (int) val + BalancerMain.hpplus95;
				case 96:
					return (int) val + BalancerMain.hpplus96;
				case 97:
					return (int) val + BalancerMain.hpplus97;
				case 98:
					return (int) val + BalancerMain.hpplus98;
				case 99:
					return (int) val + BalancerMain.hpplus99;
				case 100:
					return (int) val + BalancerMain.hpplus100;
				case 101:
					return (int) val + BalancerMain.hpplus101;
				case 102:
					return (int) val + BalancerMain.hpplus102;
				case 103:
					return (int) val + BalancerMain.hpplus103;
				case 104:
					return (int) val + BalancerMain.hpplus104;
				case 105:
					return (int) val + BalancerMain.hpplus105;
				case 106:
					return (int) val + BalancerMain.hpplus106;
				case 107:
					return (int) val + BalancerMain.hpplus107;
				case 108:
					return (int) val + BalancerMain.hpplus108;
				case 109:
					return (int) val + BalancerMain.hpplus109;
				case 110:
					return (int) val + BalancerMain.hpplus110;
				case 111:
					return (int) val + BalancerMain.hpplus111;
				case 112:
					return (int) val + BalancerMain.hpplus112;
				case 113:
					return (int) val + BalancerMain.hpplus113;
				case 114:
					return (int) val + BalancerMain.hpplus114;
				case 115:
					return (int) val + BalancerMain.hpplus115;
				case 116:
					return (int) val + BalancerMain.hpplus116;
				case 117:
					return (int) val + BalancerMain.hpplus117;
				case 118:
					return (int) val + BalancerMain.hpplus118;
			}
		}
		return (int) val;
	}

	public int getMaxMp()
	{
		if (_activeChar == null)
			return 1;

		double val = calcStat(Stats.MAX_MP, _activeChar.getTemplate().baseMpMax, null, null);
		if (_activeChar instanceof L2PcInstance)
		{
			switch (((L2PcInstance) _activeChar).getClassId().getId())
			{
				case 88:
					return (int) val + BalancerMain.mpplus88;
				case 89:
					return (int) val + BalancerMain.mpplus89;
				case 90:
					return (int) val + BalancerMain.mpplus90;
				case 91:
					return (int) val + BalancerMain.mpplus91;
				case 92:
					return (int) val + BalancerMain.mpplus92;
				case 93:
					return (int) val + BalancerMain.mpplus93;
				case 94:
					return (int) val + BalancerMain.mpplus94;
				case 95:
					return (int) val + BalancerMain.mpplus95;
				case 96:
					return (int) val + BalancerMain.mpplus96;
				case 97:
					return (int) val + BalancerMain.mpplus97;
				case 98:
					return (int) val + BalancerMain.mpplus98;
				case 99:
					return (int) val + BalancerMain.mpplus99;
				case 100:
					return (int) val + BalancerMain.mpplus100;
				case 101:
					return (int) val + BalancerMain.mpplus101;
				case 102:
					return (int) val + BalancerMain.mpplus102;
				case 103:
					return (int) val + BalancerMain.mpplus103;
				case 104:
					return (int) val + BalancerMain.mpplus104;
				case 105:
					return (int) val + BalancerMain.mpplus105;
				case 106:
					return (int) val + BalancerMain.mpplus106;
				case 107:
					return (int) val + BalancerMain.mpplus107;
				case 108:
					return (int) val + BalancerMain.mpplus108;
				case 109:
					return (int) val + BalancerMain.mpplus109;
				case 110:
					return (int) val + BalancerMain.mpplus110;
				case 111:
					return (int) val + BalancerMain.mpplus111;
				case 112:
					return (int) val + BalancerMain.mpplus112;
				case 113:
					return (int) val + BalancerMain.mpplus113;
				case 114:
					return (int) val + BalancerMain.mpplus114;
				case 115:
					return (int) val + BalancerMain.mpplus115;
				case 116:
					return (int) val + BalancerMain.mpplus116;
				case 117:
					return (int) val + BalancerMain.mpplus117;
				case 118:
					return (int) val + BalancerMain.mpplus118;
			}
		}

		return (int) val;
	}

	/**
	 * Return the MAtk (base+modifier) of the L2Character for a skill used in
	 * function of abnormal effects in progress.<BR>
	 * <BR>
	 * <B><U> Example of use </U> :</B><BR>
	 * <BR>
	 * <li>Calculate Magic damage</li> <BR>
	 * <BR>
	 *
	 * @param target
	 *        The L2Character targeted by the skill
	 * @param skill
	 *        The L2Skill used against the target
	 */
	@SuppressWarnings("incomplete-switch")
	public int getMAtk(L2Character target, L2Skill skill)
	{
		if (_activeChar == null)
			return 1;
		float bonusAtk = 1;
		if (_activeChar.isChampion())
		{
			bonusAtk = Config.CHAMPION_ATK;
		}
		double attack = _activeChar.getTemplate().baseMAtk * bonusAtk;
		// Get the skill type to calculate its effect in function of base stats
		// of the L2Character target
		Stats stat = skill == null ? null : skill.getStat();

		if (stat != null)
		{
			switch (stat)
			{
				case AGGRESSION:
					attack += _activeChar.getTemplate().baseAggression;
				break;
				case BLEED:
					attack += _activeChar.getTemplate().baseBleed;
				break;
				case POISON:
					attack += _activeChar.getTemplate().basePoison;
				break;
				case STUN:
					attack += _activeChar.getTemplate().baseStun;
				break;
				case ROOT:
					attack += _activeChar.getTemplate().baseRoot;
				break;
				case MOVEMENT:
					attack += _activeChar.getTemplate().baseMovement;
				break;
				case CONFUSION:
					attack += _activeChar.getTemplate().baseConfusion;
				break;
				case SLEEP:
					attack += _activeChar.getTemplate().baseSleep;
				break;
				case FIRE:
					attack += _activeChar.getTemplate().baseFire;
				break;
				case WIND:
					attack += _activeChar.getTemplate().baseWind;
				break;
				case WATER:
					attack += _activeChar.getTemplate().baseWater;
				break;
				case EARTH:
					attack += _activeChar.getTemplate().baseEarth;
				break;
				case HOLY:
					attack += _activeChar.getTemplate().baseHoly;
				break;
				case DARK:
					attack += _activeChar.getTemplate().baseDark;
				break;
			}
		}

		// Add the power of the skill to the attack effect
		if (skill != null)
		{
			attack += skill.getPower();
		}

		// Calculate modifiers Magic Attack
		double val = calcStat(Stats.MAGIC_ATTACK, attack, target, skill);
		if (_activeChar instanceof L2PcInstance)
		{
			switch (((L2PcInstance) _activeChar).getClassId().getId())
			{
				case 88:
					return (int) val + BalancerMain.matkplus88;
				case 89:
					return (int) val + BalancerMain.matkplus89;
				case 90:
					return (int) val + BalancerMain.matkplus90;
				case 91:
					return (int) val + BalancerMain.matkplus91;
				case 92:
					return (int) val + BalancerMain.matkplus92;
				case 93:
					return (int) val + BalancerMain.matkplus93;
				case 94:
					return (int) val + BalancerMain.matkplus94;
				case 95:
					return (int) val + BalancerMain.matkplus95;
				case 96:
					return (int) val + BalancerMain.matkplus96;
				case 97:
					return (int) val + BalancerMain.matkplus97;
				case 98:
					return (int) val + BalancerMain.matkplus98;
				case 99:
					return (int) val + BalancerMain.matkplus99;
				case 100:
					return (int) val + BalancerMain.matkplus100;
				case 101:
					return (int) val + BalancerMain.matkplus101;
				case 102:
					return (int) val + BalancerMain.matkplus102;
				case 103:
					return (int) val + BalancerMain.matkplus103;
				case 104:
					return (int) val + BalancerMain.matkplus104;
				case 105:
					return (int) val + BalancerMain.matkplus105;
				case 106:
					return (int) val + BalancerMain.matkplus106;
				case 107:
					return (int) val + BalancerMain.matkplus107;
				case 108:
					return (int) val + BalancerMain.matkplus108;
				case 109:
					return (int) val + BalancerMain.matkplus109;
				case 110:
					return (int) val + BalancerMain.matkplus110;
				case 111:
					return (int) val + BalancerMain.matkplus111;
				case 112:
					return (int) val + BalancerMain.matkplus112;
				case 113:
					return (int) val + BalancerMain.matkplus113;
				case 114:
					return (int) val + BalancerMain.matkplus114;
				case 115:
					return (int) val + BalancerMain.matkplus115;
				case 116:
					return (int) val + BalancerMain.matkplus116;
				case 117:
					return (int) val + BalancerMain.matkplus117;
				case 118:
					return (int) val + BalancerMain.matkplus118;
			}
		}

		// Calculate modifiers Magic Attack
		return (int) val;
	}

	/**
	 * Return the MAtk Speed (base+modifier) of the L2Character in function of
	 * the Armour Expertise Penalty.
	 */
	public int getMAtkSpd()
	{
		if (_activeChar == null)
			return 1;
		float bonusSpdAtk = 1;
		if (_activeChar.isChampion())
		{
			bonusSpdAtk = Config.CHAMPION_SPD_ATK;
		}
		double val = calcStat(Stats.MAGIC_ATTACK_SPEED, _activeChar.getTemplate().baseMAtkSpd * bonusSpdAtk, null, null);
		val /= _activeChar.getArmourExpertisePenalty();
		if (_activeChar instanceof L2PcInstance)
		{
			switch (((L2PcInstance) _activeChar).getClassId().getId())
			{
				case 88:
					return (int) val + BalancerMain.matksplus88;
				case 89:
					return (int) val + BalancerMain.matksplus89;
				case 90:
					return (int) val + BalancerMain.matksplus90;
				case 91:
					return (int) val + BalancerMain.matksplus91;
				case 92:
					return (int) val + BalancerMain.matksplus92;
				case 93:
					return (int) val + BalancerMain.matksplus93;
				case 94:
					return (int) val + BalancerMain.matksplus94;
				case 95:
					return (int) val + BalancerMain.matksplus95;
				case 96:
					return (int) val + BalancerMain.matksplus96;
				case 97:
					return (int) val + BalancerMain.matksplus97;
				case 98:
					return (int) val + BalancerMain.matksplus98;
				case 99:
					return (int) val + BalancerMain.matksplus99;
				case 100:
					return (int) val + BalancerMain.matksplus100;
				case 101:
					return (int) val + BalancerMain.matksplus101;
				case 102:
					return (int) val + BalancerMain.matksplus102;
				case 103:
					return (int) val + BalancerMain.matksplus103;
				case 104:
					return (int) val + BalancerMain.matksplus104;
				case 105:
					return (int) val + BalancerMain.matksplus105;
				case 106:
					return (int) val + BalancerMain.matksplus106;
				case 107:
					return (int) val + BalancerMain.matksplus107;
				case 108:
					return (int) val + BalancerMain.matksplus108;
				case 109:
					return (int) val + BalancerMain.matksplus109;
				case 110:
					return (int) val + BalancerMain.matksplus110;
				case 111:
					return (int) val + BalancerMain.matksplus111;
				case 112:
					return (int) val + BalancerMain.matksplus112;
				case 113:
					return (int) val + BalancerMain.matksplus113;
				case 114:
					return (int) val + BalancerMain.matksplus114;
				case 115:
					return (int) val + BalancerMain.matksplus115;
				case 116:
					return (int) val + BalancerMain.matksplus116;
				case 117:
					return (int) val + BalancerMain.matksplus117;
				case 118:
					return (int) val + BalancerMain.matksplus118;
			}
		}
		return (int) val;
	}

	/**
	 * Return the MDef (base+modifier) of the L2Character against a skill in
	 * function of abnormal effects in progress.<BR>
	 * <BR>
	 * <B><U> Example of use </U> :</B><BR>
	 * <BR>
	 * <li>Calculate Magic damage</li> <BR>
	 *
	 * @param target
	 *        The L2Character targeted by the skill
	 * @param skill
	 *        The L2Skill used against the target
	 */
	public int getMDef(L2Character target, L2Skill skill)
	{
		if (_activeChar == null)
			return 1;

		// Get the base MDef of the L2Character
		double defence = _activeChar.getTemplate().baseMDef;

		// Calculate modifier for Raid Bosses
		if (_activeChar.isRaid() || _activeChar.isBoss())
		{
			defence *= Config.RAID_M_DEFENCE_MULTIPLIER;
		}

		// Calculate modifiers Magic Attack
		double val = calcStat(Stats.MAGIC_DEFENCE, defence, target, skill);
		if (_activeChar instanceof L2PcInstance)
		{
			switch (((L2PcInstance) _activeChar).getClassId().getId())
			{
				case 88:
					return (int) val + BalancerMain.mdefplus88;
				case 89:
					return (int) val + BalancerMain.mdefplus89;
				case 90:
					return (int) val + BalancerMain.mdefplus90;
				case 91:
					return (int) val + BalancerMain.mdefplus91;
				case 92:
					return (int) val + BalancerMain.mdefplus92;
				case 93:
					return (int) val + BalancerMain.mdefplus93;
				case 94:
					return (int) val + BalancerMain.mdefplus94;
				case 95:
					return (int) val + BalancerMain.mdefplus95;
				case 96:
					return (int) val + BalancerMain.mdefplus96;
				case 97:
					return (int) val + BalancerMain.mdefplus97;
				case 98:
					return (int) val + BalancerMain.mdefplus98;
				case 99:
					return (int) val + BalancerMain.mdefplus99;
				case 100:
					return (int) val + BalancerMain.mdefplus100;
				case 101:
					return (int) val + BalancerMain.mdefplus101;
				case 102:
					return (int) val + BalancerMain.mdefplus102;
				case 103:
					return (int) val + BalancerMain.mdefplus103;
				case 104:
					return (int) val + BalancerMain.mdefplus104;
				case 105:
					return (int) val + BalancerMain.mdefplus105;
				case 106:
					return (int) val + BalancerMain.mdefplus106;
				case 107:
					return (int) val + BalancerMain.mdefplus107;
				case 108:
					return (int) val + BalancerMain.mdefplus108;
				case 109:
					return (int) val + BalancerMain.mdefplus109;
				case 110:
					return (int) val + BalancerMain.mdefplus110;
				case 111:
					return (int) val + BalancerMain.mdefplus111;
				case 112:
					return (int) val + BalancerMain.mdefplus112;
				case 113:
					return (int) val + BalancerMain.mdefplus113;
				case 114:
					return (int) val + BalancerMain.mdefplus114;
				case 115:
					return (int) val + BalancerMain.mdefplus115;
				case 116:
					return (int) val + BalancerMain.mdefplus116;
				case 117:
					return (int) val + BalancerMain.mdefplus117;
				case 118:
					return (int) val + BalancerMain.mdefplus118;
			}
		}
		// Calculate modifiers Magic Attack
		return (int) val;
	}

	public float getMovementSpeedMultiplier()
	{
		if (_activeChar == null)
			return 1;

		return getRunSpeed() * 1f / _activeChar.getTemplate().baseRunSpd;
	}

	/**
	 * Return the RunSpeed (base+modifier) or WalkSpeed (base+modifier) of the
	 * L2Character in function of the movement type.
	 */
	public final float getMoveSpeed()
	{
		if (_activeChar == null)
			return 1;

		if (_activeChar.isRunning())
			return getRunSpeed();
		return getWalkSpeed();
	}

	/** Return the MReuse rate (base+modifier) of the L2Character. */
	public final double getMReuseRate(L2Skill skill)
	{
		if (_activeChar == null)
			return 1;

		return calcStat(Stats.MAGIC_REUSE_RATE, _activeChar.getTemplate().baseMReuseRate, null, skill);
	}

	/** Return the PReuse rate (base+modifier) of the L2Character. */
	public final double getPReuseRate(L2Skill skill)
	{
		if (_activeChar == null)
			return 1;

		return calcStat(Stats.P_REUSE, _activeChar.getTemplate().baseMReuseRate, null, skill);
	}

	/** Return the PAtk (base+modifier) of the L2Character. */
	public int getPAtk(L2Character target)
	{
		if (_activeChar == null)
			return 1;
		float bonusAtk = 1;
		if (_activeChar.isChampion())
		{
			bonusAtk = Config.CHAMPION_ATK;
		}
		double val = calcStat(Stats.POWER_ATTACK, _activeChar.getTemplate().basePAtk * bonusAtk, target, null);
		if (_activeChar instanceof L2PcInstance)
		{
			switch (((L2PcInstance) _activeChar).getClassId().getId())
			{
				case 88:
					return (int) val + BalancerMain.patkplus88;
				case 89:
					return (int) val + BalancerMain.patkplus89;
				case 90:
					return (int) val + BalancerMain.patkplus90;
				case 91:
					return (int) val + BalancerMain.patkplus91;
				case 92:
					return (int) val + BalancerMain.patkplus92;
				case 93:
					return (int) val + BalancerMain.patkplus93;
				case 94:
					return (int) val + BalancerMain.patkplus94;
				case 95:
					return (int) val + BalancerMain.patkplus95;
				case 96:
					return (int) val + BalancerMain.patkplus96;
				case 97:
					return (int) val + BalancerMain.patkplus97;
				case 98:
					return (int) val + BalancerMain.patkplus98;
				case 99:
					return (int) val + BalancerMain.patkplus99;
				case 100:
					return (int) val + BalancerMain.patkplus100;
				case 101:
					return (int) val + BalancerMain.patkplus101;
				case 102:
					return (int) val + BalancerMain.patkplus102;
				case 103:
					return (int) val + BalancerMain.patkplus103;
				case 104:
					return (int) val + BalancerMain.patkplus104;
				case 105:
					return (int) val + BalancerMain.patkplus105;
				case 106:
					return (int) val + BalancerMain.patkplus106;
				case 107:
					return (int) val + BalancerMain.patkplus107;
				case 108:
					return (int) val + BalancerMain.patkplus108;
				case 109:
					return (int) val + BalancerMain.patkplus109;
				case 110:
					return (int) val + BalancerMain.patkplus110;
				case 111:
					return (int) val + BalancerMain.patkplus111;
				case 112:
					return (int) val + BalancerMain.patkplus112;
				case 113:
					return (int) val + BalancerMain.patkplus113;
				case 114:
					return (int) val + BalancerMain.patkplus114;
				case 115:
					return (int) val + BalancerMain.patkplus115;
				case 116:
					return (int) val + BalancerMain.patkplus116;
				case 117:
					return (int) val + BalancerMain.patkplus117;
				case 118:
					return (int) val + BalancerMain.patkplus118;
			}
		}
		return (int) val;
	}

	/** Return the PAtk Modifier against animals. */
	public final double getPAtkAnimals(L2Character target)
	{
		return calcStat(Stats.PATK_ANIMALS, 1, target, null);
	}

	/** Return the PAtk Modifier against dragons. */
	public final double getPAtkDragons(L2Character target)
	{
		return calcStat(Stats.PATK_DRAGONS, 1, target, null);
	}

	/** Return the PAtk Modifier against insects. */
	public final double getPAtkInsects(L2Character target)
	{
		return calcStat(Stats.PATK_INSECTS, 1, target, null);
	}

	/** Return the PAtk Modifier against monsters. */
	public final double getPAtkMonsters(L2Character target)
	{
		return calcStat(Stats.PATK_BEAST, 1, target, null);
	}

	/** Return the PAtk Modifier against plants. */
	public final double getPAtkPlants(L2Character target)
	{
		return calcStat(Stats.PATK_PLANTS, 1, target, null);
	}

	public final double getPAtkGiants(L2Character target)
	{
		return calcStat(Stats.PATK_PLANTS, 1, target, null);
	}

	/**
	 * Return the PAtk Speed (base+modifier) of the L2Character in function of
	 * the Armour Expertise Penalty.
	 */
	public int getPAtkSpd()
	{
		if (_activeChar == null)
			return 1;
		float bonusAtk = 1;
		if (_activeChar.isChampion())
		{
			bonusAtk = Config.CHAMPION_SPD_ATK;
		}
		double val = (calcStat(Stats.POWER_ATTACK_SPEED, _activeChar.getTemplate().basePAtkSpd * bonusAtk, null, null) / _activeChar.getArmourExpertisePenalty());
		if (_activeChar instanceof L2PcInstance)
		{
			switch (((L2PcInstance) _activeChar).getClassId().getId())
			{
				case 88:
					return (int) val + BalancerMain.patksplus88;
				case 89:
					return (int) val + BalancerMain.patksplus89;
				case 90:
					return (int) val + BalancerMain.patksplus90;
				case 91:
					return (int) val + BalancerMain.patksplus91;
				case 92:
					return (int) val + BalancerMain.patksplus92;
				case 93:
					return (int) val + BalancerMain.patksplus93;
				case 94:
					return (int) val + BalancerMain.patksplus94;
				case 95:
					return (int) val + BalancerMain.patksplus95;
				case 96:
					return (int) val + BalancerMain.patksplus96;
				case 97:
					return (int) val + BalancerMain.patksplus97;
				case 98:
					return (int) val + BalancerMain.patksplus98;
				case 99:
					return (int) val + BalancerMain.patksplus99;
				case 100:
					return (int) val + BalancerMain.patksplus100;
				case 101:
					return (int) val + BalancerMain.patksplus101;
				case 102:
					return (int) val + BalancerMain.patksplus102;
				case 103:
					return (int) val + BalancerMain.patksplus103;
				case 104:
					return (int) val + BalancerMain.patksplus104;
				case 105:
					return (int) val + BalancerMain.patksplus105;
				case 106:
					return (int) val + BalancerMain.patksplus106;
				case 107:
					return (int) val + BalancerMain.patksplus107;
				case 108:
					return (int) val + BalancerMain.patksplus108;
				case 109:
					return (int) val + BalancerMain.patksplus109;
				case 110:
					return (int) val + BalancerMain.patksplus110;
				case 111:
					return (int) val + BalancerMain.patksplus111;
				case 112:
					return (int) val + BalancerMain.patksplus112;
				case 113:
					return (int) val + BalancerMain.patksplus113;
				case 114:
					return (int) val + BalancerMain.patksplus114;
				case 115:
					return (int) val + BalancerMain.patksplus115;
				case 116:
					return (int) val + BalancerMain.patksplus116;
				case 117:
					return (int) val + BalancerMain.patksplus117;
				case 118:
					return (int) val + BalancerMain.patksplus118;
			}
		}
		return (int) val;
	}

	/** Return the PAtk Modifier against undead. */
	public final double getPAtkUndead(L2Character target)
	{
		return calcStat(Stats.PATK_UNDEAD, 1, target, null);
	}

	public final double getPDefUndead(L2Character target)
	{
		return calcStat(Stats.PDEF_UNDEAD, 1, target, null);
	}

	/** Return the PDef (base+modifier) of the L2Character. */
	public int getPDef(L2Character target)
	{
		if (_activeChar == null)
			return 1;

		// Get the base PDef of the L2Character
		double defence = _activeChar.getTemplate().basePDef;

		// Calculate modifier for Raid Bosses
		if (_activeChar.isRaid() || _activeChar.isBoss())
		{
			defence *= Config.RAID_P_DEFENCE_MULTIPLIER;
		}

		// Calculate modifiers Magic Attack
		double val = calcStat(Stats.POWER_DEFENCE, defence, target, null);
		if (_activeChar instanceof L2PcInstance)
		{
			switch (((L2PcInstance) _activeChar).getClassId().getId())
			{
				case 88:
					return (int) val + BalancerMain.pdefplus88;
				case 89:
					return (int) val + BalancerMain.pdefplus89;
				case 90:
					return (int) val + BalancerMain.pdefplus90;
				case 91:
					return (int) val + BalancerMain.pdefplus91;
				case 92:
					return (int) val + BalancerMain.pdefplus92;
				case 93:
					return (int) val + BalancerMain.pdefplus93;
				case 94:
					return (int) val + BalancerMain.pdefplus94;
				case 95:
					return (int) val + BalancerMain.pdefplus95;
				case 96:
					return (int) val + BalancerMain.pdefplus96;
				case 97:
					return (int) val + BalancerMain.pdefplus97;
				case 98:
					return (int) val + BalancerMain.pdefplus98;
				case 99:
					return (int) val + BalancerMain.pdefplus99;
				case 100:
					return (int) val + BalancerMain.pdefplus100;
				case 101:
					return (int) val + BalancerMain.pdefplus101;
				case 102:
					return (int) val + BalancerMain.pdefplus102;
				case 103:
					return (int) val + BalancerMain.pdefplus103;
				case 104:
					return (int) val + BalancerMain.pdefplus104;
				case 105:
					return (int) val + BalancerMain.pdefplus105;
				case 106:
					return (int) val + BalancerMain.pdefplus106;
				case 107:
					return (int) val + BalancerMain.pdefplus107;
				case 108:
					return (int) val + BalancerMain.pdefplus108;
				case 109:
					return (int) val + BalancerMain.pdefplus109;
				case 110:
					return (int) val + BalancerMain.pdefplus110;
				case 111:
					return (int) val + BalancerMain.pdefplus111;
				case 112:
					return (int) val + BalancerMain.pdefplus112;
				case 113:
					return (int) val + BalancerMain.pdefplus113;
				case 114:
					return (int) val + BalancerMain.pdefplus114;
				case 115:
					return (int) val + BalancerMain.pdefplus115;
				case 116:
					return (int) val + BalancerMain.pdefplus116;
				case 117:
					return (int) val + BalancerMain.pdefplus117;
				case 118:
					return (int) val + BalancerMain.pdefplus118;
			}
		}
		return (int) val;

	}

	/** Return the Physical Attack range (base+modifier) of the L2Character. */
	public final int getPhysicalAttackRange()
	{
		if (_activeChar == null)
			return 1;

		return (int) calcStat(Stats.POWER_ATTACK_RANGE, _activeChar.getTemplate().baseAtkRange, null, null);
	}

	/** Return the weapon reuse modifier */
	public final double getWeaponReuseModifier(L2Character target)
	{
		return calcStat(Stats.ATK_REUSE, 1, target, null);
	}

	/**
	 * Return the RunSpeed (base+modifier) of the L2Character in function of the
	 * Armor Expertise Penalty.
	 */
	public int getRunSpeed()
	{
		if (_activeChar == null)
			return 1;

		// err we should be adding TO the persons run speed
		// not making it a constant
		//int val = (int) calcStat(Stats.RUN_SPEED, _activeChar.getTemplate().baseRunSpd, null, null) + Config.RUN_SPD_BOOST;
		double val = (calcStat(Stats.RUN_SPEED, _activeChar.getTemplate().baseRunSpd, null, null) + Config.RUN_SPD_BOOST);
		if (_activeChar instanceof L2PcInstance)
		{//FIXME
			switch (((L2PcInstance) _activeChar).getClassId().getId())
			{
				case 88:
					return (int) val + BalancerMain.walkplus88;
				case 89:
					return (int) val + BalancerMain.walkplus89;
				case 90:
					return (int) val + BalancerMain.walkplus90;
				case 91:
					return (int) val + BalancerMain.walkplus91;
				case 92:
					return (int) val + BalancerMain.walkplus92;
				case 93:
					return (int) val + BalancerMain.walkplus93;
				case 94:
					return (int) val + BalancerMain.walkplus94;
				case 95:
					return (int) val + BalancerMain.walkplus95;
				case 96:
					return (int) val + BalancerMain.walkplus96;
				case 97:
					return (int) val + BalancerMain.walkplus97;
				case 98:
					return (int) val + BalancerMain.walkplus98;
				case 99:
					return (int) val + BalancerMain.walkplus99;
				case 100:
					return (int) val + BalancerMain.walkplus100;
				case 101:
					return (int) val + BalancerMain.walkplus101;
				case 102:
					return (int) val + BalancerMain.walkplus102;
				case 103:
					return (int) val + BalancerMain.walkplus103;
				case 104:
					return (int) val + BalancerMain.walkplus104;
				case 105:
					return (int) val + BalancerMain.walkplus105;
				case 106:
					return (int) val + BalancerMain.walkplus106;
				case 107:
					return (int) val + BalancerMain.walkplus107;
				case 108:
					return (int) val + BalancerMain.walkplus108;
				case 109:
					return (int) val + BalancerMain.walkplus109;
				case 110:
					return (int) val + BalancerMain.walkplus110;
				case 111:
					return (int) val + BalancerMain.walkplus111;
				case 112:
					return (int) val + BalancerMain.walkplus112;
				case 113:
					return (int) val + BalancerMain.walkplus113;
				case 114:
					return (int) val + BalancerMain.walkplus114;
				case 115:
					return (int) val + BalancerMain.walkplus115;
				case 116:
					return (int) val + BalancerMain.walkplus116;
				case 117:
					return (int) val + BalancerMain.walkplus117;
				case 118:
					return (int) val + BalancerMain.walkplus118;
			}
		}
		if (_activeChar.isFlying())
		{
			val += Config.WYVERN_SPEED;
			return (int) val;
		}
		if (_activeChar.isRiding())
		{
			val += Config.STRIDER_SPEED;
			return (int) val;
		}
		val /= _activeChar.getArmourExpertisePenalty();

		// Apply max run speed cap.
		if (val > Config.MAX_RUN_SPEED)
		{
			val = Config.MAX_RUN_SPEED;
		}
		return (int) val;
	}

	/** Return the ShieldDef rate (base+modifier) of the L2Character. */
	public final int getShldDef()
	{
		return (int) calcStat(Stats.SHIELD_DEFENCE, 0, null, null);
	}

	public int getSp()
	{
		return _sp;
	}

	public void setSp(int value)
	{
		_sp = value;
	}

	/** Return the WalkSpeed (base+modifier) of the L2Character. */
	public int getWalkSpeed()
	{

		if (_activeChar == null)
			return 1;

		if (_activeChar instanceof L2PcInstance)
			return (getRunSpeed() * 70) / 100;
		else
			return (int) calcStat(Stats.WALK_SPEED, _activeChar.getTemplate().baseWalkSpd, null, null);
	}

	/** Return the mpConsume. */
	public final int getMpConsume(L2Skill skill)
	{
		if (skill == null)
			return 1;
		int mpconsume = skill.getMpConsume();
		if (skill.isDance() && _activeChar != null && _activeChar.getDanceCount() > 0)
		{
			mpconsume += _activeChar.getDanceCount() * skill.getNextDanceMpCost();
		}
		return (int) calcStat(Stats.MP_CONSUME, mpconsume, null, skill);
	}

	/** Return the mpInitialConsume. */
	public final int getMpInitialConsume(L2Skill skill)
	{
		if (skill == null)
			return 1;

		return (int) calcStat(Stats.MP_CONSUME, skill.getMpInitialConsume(), null, skill);
	}

	public int getAttackElement()
	{
		double tempVal = 0, stats[] =
		{
		_fire, _water, _wind, _earth, _holy, _dark
		};

		int returnVal = -2;
		_earth = (int) calcStat(Stats.EARTH_POWER, 0, null, null);
		_fire = (int) calcStat(Stats.FIRE_POWER, 0, null, null);
		_water = (int) calcStat(Stats.WATER_POWER, 0, null, null);
		_wind = (int) calcStat(Stats.WIND_POWER, 0, null, null);
		_holy = (int) calcStat(Stats.HOLY_POWER, 0, null, null);
		_dark = (int) calcStat(Stats.DARK_POWER, 0, null, null);

		for (int x = 0; x < stats.length; x++)
		{
			if (stats[x] > tempVal)
			{
				returnVal = x;
				tempVal = stats[x];
			}
		}

		return returnVal;
	}

	public int getAttackElementValue(int attackAttribute)
	{
		_earth = (int) calcStat(Stats.EARTH_POWER, 0, null, null);
		_fire = (int) calcStat(Stats.FIRE_POWER, 0, null, null);
		_water = (int) calcStat(Stats.WATER_POWER, 0, null, null);
		_wind = (int) calcStat(Stats.WIND_POWER, 0, null, null);
		_holy = (int) calcStat(Stats.HOLY_POWER, 0, null, null);
		_dark = (int) calcStat(Stats.DARK_POWER, 0, null, null);

		switch (attackAttribute)
		{
			case -2:
				return 0;
			case 0:
				return _fire;
			case 1:
				return _water;
			case 2:
				return _wind;
			case 3:
				return _earth;
			case 4:
				return _holy;
			case 5:
				return _dark;
		}

		return 0;
	}
}
