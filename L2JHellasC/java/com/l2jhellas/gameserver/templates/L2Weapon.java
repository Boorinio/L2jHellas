/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jhellas.gameserver.templates;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javolution.util.FastList;

import com.l2jhellas.gameserver.handler.ISkillHandler;
import com.l2jhellas.gameserver.handler.SkillHandler;
import com.l2jhellas.gameserver.model.L2Effect;
import com.l2jhellas.gameserver.model.L2ItemInstance;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.L2Skill;
import com.l2jhellas.gameserver.model.L2SkillType;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.quest.Quest;
import com.l2jhellas.gameserver.skills.Env;
import com.l2jhellas.gameserver.skills.SkillTable;
import com.l2jhellas.gameserver.skills.conditions.ConditionGameChance;
import com.l2jhellas.gameserver.skills.funcs.Func;
import com.l2jhellas.gameserver.skills.funcs.FuncTemplate;

public final class L2Weapon extends L2Item
{
	private final int _soulShotCount;
	private final int _spiritShotCount;
	private final int _pDam;
	private final int _rndDam;
	private final int _critical;
	private final double _hitModifier;
	private final int _avoidModifier;
	private final int _shieldDef;
	private final double _shieldDefRate;
	private final int _atkSpeed;
	private final int _atkReuse;
	private final int _mpConsume;
	private final int _mDam;
	private L2Skill _itemSkill = null; // for passive skill
	private L2Skill _enchant4Skill = null; // skill that activates when item is enchanted +4 (for duals)

	// Attached skills for Special Abilities
	protected L2Skill[] _skillsOnCast;
	protected L2Skill[] _skillsOnCrit;

	/**
	 * Constructor for Weapon.<BR>
	 * <BR>
	 * <U><I>Variables filled :</I></U><BR>
	 * <LI>_soulShotCount & _spiritShotCount</LI> <LI>_pDam & _mDam & _rndDam</LI> <LI>_critical</LI> <LI>_hitModifier</LI> <LI>_avoidModifier</LI> <LI>_shieldDes & _shieldDefRate</LI>
	 * <LI>_atkSpeed & _AtkReuse</LI> <LI>_mpConsume</LI>
	 * 
	 * @param type
	 *        : L2ArmorType designating the type of armor
	 * @param set
	 *        : StatsSet designating the set of couples (key,value) caracterizing the armor
	 * @see L2Item constructor
	 */
	public L2Weapon(L2WeaponType type, StatsSet set)
	{
		super(type, set);
		_soulShotCount = set.getInteger("soulshots");
		_spiritShotCount = set.getInteger("spiritshots");
		_pDam = set.getInteger("p_dam");
		_rndDam = set.getInteger("rnd_dam");
		_critical = set.getInteger("critical");
		_hitModifier = set.getDouble("hit_modify");
		_avoidModifier = set.getInteger("avoid_modify");
		_shieldDef = set.getInteger("shield_def");
		_shieldDefRate = set.getDouble("shield_def_rate");
		_atkSpeed = set.getInteger("atk_speed");
		_atkReuse = set.getInteger("atk_reuse", type == L2WeaponType.BOW ? 1500 : 0);
		_mpConsume = set.getInteger("mp_consume");
		_mDam = set.getInteger("m_dam");

		int sId = set.getInteger("item_skill_id");
		int sLv = set.getInteger("item_skill_lvl");
		if (sId > 0 && sLv > 0)
			_itemSkill = SkillTable.getInstance().getInfo(sId, sLv);

		sId = set.getInteger("enchant4_skill_id");
		sLv = set.getInteger("enchant4_skill_lvl");
		if (sId > 0 && sLv > 0)
			_enchant4Skill = SkillTable.getInstance().getInfo(sId, sLv);

		sId = set.getInteger("onCast_skill_id");
		sLv = set.getInteger("onCast_skill_lvl");
		int sCh = set.getInteger("onCast_skill_chance");
		if (sId > 0 && sLv > 0 && sCh > 0)
		{
			L2Skill skill = SkillTable.getInstance().getInfo(sId, sLv);
			skill.attach(new ConditionGameChance(sCh), true);
			attachOnCast(skill);
		}

		sId = set.getInteger("onCrit_skill_id");
		sLv = set.getInteger("onCrit_skill_lvl");
		sCh = set.getInteger("onCrit_skill_chance");
		if (sId > 0 && sLv > 0 && sCh > 0)
		{
			L2Skill skill = SkillTable.getInstance().getInfo(sId, sLv);
			skill.attach(new ConditionGameChance(sCh), true);
			attachOnCrit(skill);
		}
	}

	/**
	 * Returns the type of Weapon
	 * 
	 * @return L2WeaponType
	 */
	@Override
	public L2WeaponType getItemType()
	{
		return (L2WeaponType) super._type;
	}

	/**
	 * Returns the ID of the Etc item after applying the mask.
	 * 
	 * @return int : ID of the Weapon
	 */
	@Override
	public int getItemMask()
	{
		return getItemType().mask();
	}

	/**
	 * Returns the quantity of SoulShot used.
	 * 
	 * @return int
	 */
	public int getSoulShotCount()
	{
		return _soulShotCount;
	}

	/**
	 * Returns the quatity of SpiritShot used.
	 * 
	 * @return int
	 */
	public int getSpiritShotCount()
	{
		return _spiritShotCount;
	}

	/**
	 * Returns the physical damage.
	 * 
	 * @return int
	 */
	public int getPDamage()
	{
		return _pDam;
	}

	/**
	 * Returns the random damage inflicted by the weapon
	 * 
	 * @return int
	 */
	public int getRandomDamage()
	{
		return _rndDam;
	}

	/**
	 * Returns the attack speed of the weapon
	 * 
	 * @return int
	 */
	public int getAttackSpeed()
	{
		return _atkSpeed;
	}

	/**
	 * Return the Attack Reuse Delay of the L2Weapon.<BR>
	 * <BR>
	 * 
	 * @return int
	 */
	public int getAttackReuseDelay()
	{
		return _atkReuse;
	}

	/**
	 * Returns the avoid modifier of the weapon
	 * 
	 * @return int
	 */
	public int getAvoidModifier()
	{
		return _avoidModifier;
	}

	/**
	 * Returns the rate of critical hit
	 * 
	 * @return int
	 */
	public int getCritical()
	{
		return _critical;
	}

	/**
	 * Returns the hit modifier of the weapon
	 * 
	 * @return double
	 */
	public double getHitModifier()
	{
		return _hitModifier;
	}

	/**
	 * Returns the magical damage inflicted by the weapon
	 * 
	 * @return int
	 */
	public int getMDamage()
	{
		return _mDam;
	}

	/**
	 * Returns the MP consumption with the weapon
	 * 
	 * @return int
	 */
	public int getMpConsume()
	{
		return _mpConsume;
	}

	/**
	 * Returns the shield defense of the weapon
	 * 
	 * @return int
	 */
	public int getShieldDef()
	{
		return _shieldDef;
	}

	/**
	 * Returns the rate of shield defense of the weapon
	 * 
	 * @return double
	 */
	public double getShieldDefRate()
	{
		return _shieldDefRate;
	}

	/**
	 * Returns passive skill linked to that weapon
	 * 
	 * @return
	 */
	public L2Skill getSkill()
	{
		return _itemSkill;
	}

	/**
	 * Returns skill that player get when has equiped weapon +4 or more (for duals SA)
	 * 
	 * @return
	 */
	public L2Skill getEnchant4Skill()
	{
		return _enchant4Skill;
	}

	/**
	 * Returns array of Func objects containing the list of functions used by the weapon
	 * 
	 * @param instance
	 *        : L2ItemInstance pointing out the weapon
	 * @param player
	 *        : L2Character pointing out the player
	 * @return Func[] : array of functions
	 */
	@Override
	public Func[] getStatFuncs(L2ItemInstance instance, L2Character player)
	{
		List<Func> funcs = new FastList<Func>();
		if (_funcTemplates != null)
		{
			for (FuncTemplate t : _funcTemplates)
			{
				Env env = new Env();
				env.player = player;
				env.item = instance;
				Func f = t.getFunc(env, instance);
				if (f != null)
					funcs.add(f);
			}
		}
		return funcs.toArray(new Func[funcs.size()]);
	}

	/**
	 * Returns effects of skills associated with the item to be triggered onHit.
	 * 
	 * @param caster
	 *        : L2Character pointing out the caster
	 * @param target
	 *        : L2Character pointing out the target
	 * @param crit
	 *        : boolean tells whether the hit was critical
	 * @return L2Effect[] : array of effects generated by the skill
	 */
	public L2Effect[] getSkillEffects(L2Character caster, L2Character target, boolean crit)
	{
		if (_skillsOnCrit == null || !crit)
			return _emptyEffectSet;
		List<L2Effect> effects = new FastList<L2Effect>();

		for (L2Skill skill : _skillsOnCrit)
		{
			if (target.isRaid() && ((skill.getSkillType() == L2SkillType.CONFUSION) || (skill.getSkillType() == L2SkillType.MUTE) || (skill.getSkillType() == L2SkillType.PARALYZE) || (skill.getSkillType() == L2SkillType.ROOT)))
				continue; // These skills should not work on RaidBoss

			if (!skill.checkCondition(caster, target, true))
				continue; // Skill condition not met

			if (target.getFirstEffect(skill.getId()) != null)
				target.getFirstEffect(skill.getId()).exit();
			for (L2Effect e : skill.getEffects(caster, target))
				effects.add(e);
		}
		if (effects.size() == 0)
			return _emptyEffectSet;
		return effects.toArray(new L2Effect[effects.size()]);
	}

	/**
	 * Returns effects of skills associated with the item to be triggered onCast.
	 * 
	 * @param caster
	 *        : L2Character pointing out the caster
	 * @param target
	 *        : L2Character pointing out the target
	 * @param trigger
	 *        : L2Skill pointing out the skill triggering this action
	 * @return L2Effect[] : array of effects generated by the skill
	 */
	public L2Effect[] getSkillEffects(L2Character caster, L2Character target, L2Skill trigger)
	{
		if (_skillsOnCast == null)
			return _emptyEffectSet;
		List<L2Effect> effects = new FastList<L2Effect>();

		for (L2Skill skill : _skillsOnCast)
		{
			if (trigger.isOffensive() != skill.isOffensive())
				continue; // Trigger only same type of skill

			if (trigger.getId() >= 1320 && trigger.getId() <= 1322)
				continue; // No buff with Common and Dwarven Craft...

			if (target.isRaid() || target.isBoss() && (skill.getSkillType() == L2SkillType.CONFUSION || skill.getSkillType() == L2SkillType.MUTE || skill.getSkillType() == L2SkillType.PARALYZE || skill.getSkillType() == L2SkillType.ROOT))
				continue; // These skills should not work on RaidBoss

			if (trigger.isToggle() && skill.getSkillType() == L2SkillType.BUFF)
				continue; // No buffing with toggle skills

			if (!skill.checkCondition(caster, target, true))
				continue; // Skill condition not met

			try
			{
				// Get the skill handler corresponding to the skill type
				ISkillHandler handler = SkillHandler.getInstance().getSkillHandler(skill.getSkillType());

				L2Character[] targets = new L2Character[1];
				targets[0] = target;

				// Launch the magic skill and calculate its effects
				if (handler != null)
					handler.useSkill(caster, skill, targets);
				else
					skill.useSkill(caster, targets);

				// notify quests of a skill use
				if (caster instanceof L2PcInstance)
				{
					// Mobs in range 1000 see spell
					Collection<L2Object> objs = caster.getKnownList().getKnownObjects().values();
					//synchronized (caster.getKnownList().getKnownObjects())
					{
						for (L2Object spMob : objs)
						{
							if (spMob instanceof L2Npc)
							{
								L2Npc npcMob = (L2Npc) spMob;

								if (npcMob.getTemplate().getEventQuests(Quest.QuestEventType.ON_SKILL_SEE) != null)
									for (Quest quest : npcMob.getTemplate().getEventQuests(Quest.QuestEventType.ON_SKILL_SEE))
										quest.notifySkillSee(npcMob, (L2PcInstance) caster, _skillsOnCast[0], targets, false);//XXX not sure of this
							}
						}
					}
				}
			}
			catch (IOException e)
			{
			}
		}
		if (effects.size() == 0)
			return _emptyEffectSet;
		return effects.toArray(new L2Effect[effects.size()]);
	}

	/**
	 * Add the L2Skill skill to the list of skills generated by the item triggered by critical hit
	 * 
	 * @param skill
	 *        : L2Skill
	 */
	public void attachOnCrit(L2Skill skill)
	{
		if (_skillsOnCrit == null)
		{
			_skillsOnCrit = new L2Skill[]
			{
				skill
			};
		}
		else
		{
			int len = _skillsOnCrit.length;
			L2Skill[] tmp = new L2Skill[len + 1];
			// Definition : arraycopy(array source, begins copy at this position of source, array destination, begins copy at this position in dest,
			//                        number of components to be copied)
			System.arraycopy(_skillsOnCrit, 0, tmp, 0, len);
			tmp[len] = skill;
			_skillsOnCrit = tmp;
		}
	}

	/**
	 * Add the L2Skill skill to the list of skills generated by the item triggered by casting spell
	 * 
	 * @param skill
	 *        : L2Skill
	 */
	public void attachOnCast(L2Skill skill)
	{
		if (_skillsOnCast == null)
		{
			_skillsOnCast = new L2Skill[]
			{
				skill
			};
		}
		else
		{
			int len = _skillsOnCast.length;
			L2Skill[] tmp = new L2Skill[len + 1];
			// Definition : arraycopy(array source, begins copy at this position of source, array destination, begins copy at this position in dest,
			//                        number of components to be copied)
			System.arraycopy(_skillsOnCast, 0, tmp, 0, len);
			tmp[len] = skill;
			_skillsOnCast = tmp;
		}
	}
}