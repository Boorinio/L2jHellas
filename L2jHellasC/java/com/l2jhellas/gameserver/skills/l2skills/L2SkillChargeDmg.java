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
package com.l2jhellas.gameserver.skills.l2skills;

import com.l2jhellas.gameserver.model.L2Character;
import com.l2jhellas.gameserver.model.L2Effect;
import com.l2jhellas.gameserver.model.L2ItemInstance;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.L2Skill;
import com.l2jhellas.gameserver.model.L2SkillTargetType;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.EtcStatusUpdate;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;
import com.l2jhellas.gameserver.skills.Formulas;
import com.l2jhellas.gameserver.skills.effects.EffectCharge;
import com.l2jhellas.gameserver.templates.L2WeaponType;
import com.l2jhellas.gameserver.templates.StatsSet;

public class L2SkillChargeDmg extends L2Skill
{
	final int chargeSkillId;

	public L2SkillChargeDmg(StatsSet set)
	{
		super(set);
		chargeSkillId = set.getInteger("charge_skill_id");
	}

	@Override
	public boolean checkCondition(L2Character activeChar, L2Object target, boolean itemOrWeapon)
	{
		if (activeChar instanceof L2PcInstance)
		{
			L2PcInstance player = (L2PcInstance) activeChar;
			EffectCharge e = (EffectCharge) player.getFirstEffect(chargeSkillId);
			if (e == null || e.numCharges < getNumCharges())
			{
				SystemMessage sm = new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED);
				sm.addSkillName(getId());
				activeChar.sendPacket(sm);
				return false;
			}
		}
		return super.checkCondition(activeChar, target, itemOrWeapon);
	}

	@Override
	public void useSkill(L2Character caster, L2Object[] targets)
	{
		if (caster.isAlikeDead())
		{
			return;
		}

		// get the effect
		EffectCharge effect = (EffectCharge) caster.getFirstEffect(chargeSkillId);
		if (effect == null || effect.numCharges < getNumCharges())
		{
			SystemMessage sm = new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED);
			sm.addSkillName(getId());
			caster.sendPacket(sm);
			return;
		}
		double modifier = 0;
		modifier = 0.8 + 0.201 * (getNumCharges() + effect.numCharges); // thanks Diego Vargas of L2Guru: 70*((0.8+0.201*No.Charges) * (PATK+POWER)) / PDEF
		if (getTargetType() != L2SkillTargetType.TARGET_AREA && getTargetType() != L2SkillTargetType.TARGET_MULTIFACE)
			effect.numCharges -= getNumCharges();
		if (caster instanceof L2PcInstance)
			caster.sendPacket(new EtcStatusUpdate((L2PcInstance) caster));
		if (effect.numCharges == 0)
		{
			effect.exit();
		}
		for (int index = 0; index < targets.length; index++)
		{
			L2ItemInstance weapon = caster.getActiveWeaponInstance();
			L2Character target = (L2Character) targets[index];
			if (target.isAlikeDead())
				continue;

			// Calculate skill evasion
			boolean skillIsEvaded = Formulas.calcPhysicalSkillEvasion(target, this);
			if (skillIsEvaded)
			{
				if (caster instanceof L2PcInstance)
				{
					SystemMessage sm = new SystemMessage(SystemMessageId.S1_DODGES_ATTACK);
					sm.addString(target.getName());
					((L2PcInstance) caster).sendPacket(sm);
				}
				if (target instanceof L2PcInstance)
				{
					SystemMessage sm = new SystemMessage(SystemMessageId.AVOIDED_S1_ATTACK2);
					sm.addString(caster.getName());
					((L2PcInstance) target).sendPacket(sm);
				}

				// no futher calculations needed.
				continue;
			}

			// TODO: should we use dual or not?
			// because if so, damage are lowered but we dont do anything special with dual then
			// like in doAttackHitByDual which in fact does the calcPhysDam call twice

			// boolean dual = caster.isUsingDualWeapon();
			byte shld = Formulas.calcShldUse(caster, target);
			boolean crit = Formulas.calcCrit(caster.getCriticalHit(target, this));
			boolean soul = (weapon != null && weapon.getChargedSoulshot() == L2ItemInstance.CHARGED_SOULSHOT && weapon.getItemType() != L2WeaponType.DAGGER);

			// damage calculation, crit is static 2x
			int damage = (int) Formulas.calcPhysDam(caster, target, this, shld, false, false, soul);
			if (crit)
				damage *= 2;

			if (damage > 0)
			{
				double finalDamage = damage * modifier;
				target.reduceCurrentHp(finalDamage, caster);

				caster.sendDamageMessage(target, (int) finalDamage, false, crit, false);

				if (soul && weapon != null)
					weapon.setChargedSoulshot(L2ItemInstance.CHARGED_NONE);
			}
			else
			{
				caster.sendDamageMessage(target, 0, false, false, true);
			}
		}
		// effect self :]
		L2Effect seffect = caster.getFirstEffect(getId());
		if (seffect != null && seffect.isSelfEffect())
		{
			// Replace old effect with new one.
			seffect.exit();
		}
		// cast self effect if any
		getEffectsSelf(caster);
	}
}