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
package com.l2jhellas.gameserver.skills;

import java.util.NoSuchElementException;

/**
 * Enum of basic stats.
 *
 * @author l2jhellas
 */
public enum Stats
{
	// ****BASE STATS****

	// HP And MP
	MAX_HP("maxHp"),
	MAX_MP("maxMp"),
    MAX_CP("maxCp"),
	REGENERATE_HP_RATE("regHp"),
    REGENERATE_CP_RATE("regCp"),
	REGENERATE_MP_RATE("regMp"),
    RECHARGE_MP_RATE("gainMp"),
	HEAL_EFFECTIVNESS("gainHp"),
	HEAL_PROFICIENCY("giveHp"),

	// ATK And DEF
	POWER_DEFENCE("pDef"),
	MAGIC_DEFENCE("mDef"),
	POWER_ATTACK("pAtk"),
	MAGIC_ATTACK("mAtk"),
	POWER_ATTACK_SPEED("pAtkSpd"),
	MAGIC_ATTACK_SPEED("mAtkSpd"),
	MAGIC_REUSE_RATE("mReuse"),
	SHIELD_DEFENCE("sDef"),
	CRITICAL_DAMAGE("cAtk"),
	CRITICAL_DAMAGE_ADD("cAtkAdd"),
	PVP_PHYSICAL_DMG("pvpPhysDmg"),
	PVP_MAGICAL_DMG("pvpMagicalDmg"),
	PVP_PHYS_SKILL_DMG("pvpPhysSkillsDmg"),

	// ATK And DEF Rates
	EVASION_RATE("rEvas"),
	P_SKILL_EVASION("pSkillEvas"),
	SHIELD_RATE("rShld"),
	CRITICAL_RATE("rCrit"),
	BLOW_RATE("blowRate"),
	LETHAL_RATE("lethalRate"),
	MCRITICAL_RATE("mCritRate"),
    EXPSP_RATE("rExp"),
	ATTACK_CANCEL("cancel"),

	// ACC And RANGE
	ACCURACY_COMBAT("accCombat"),
	POWER_ATTACK_RANGE("pAtkRange"),
	MAGIC_ATTACK_RANGE("mAtkRange"),
    POWER_ATTACK_ANGLE("pAtkAngle"),
    ATTACK_COUNT_MAX("atkCountMax"),

    // STEPS SPEED
	RUN_SPEED("runSpd"),
	WALK_SPEED("walkSpd"),

	// STATS
	STAT_STR("STR"),
	STAT_CON("CON"),
	STAT_DEX("DEX"),
	STAT_INT("INT"),
	STAT_WIT("WIT"),
	STAT_MEN("MEN"),

	// ****SPECIAL STATS****

	// STATS Of ABILITIES
	BREATH("breath"),
	AGGRESSION("aggression"),
	BLEED("bleed"),
	POISON("poison"),
	STUN("stun"),
	ROOT("root"),
	MOVEMENT("movement"),
	CONFUSION("confusion"),
	SLEEP("sleep"),
	FIRE("fire"),
	WIND("wind"),
	WATER("water"),
	EARTH("earth"),
	HOLY("holy"),
	DARK("dark"),

	BUFF_VULN("buffVuln"),
	FALL_VULN("fallVuln"),
	AGGRESSION_VULN("aggressionVuln"),
	BLEED_VULN("bleedVuln"),
	POISON_VULN("poisonVuln"),
	STUN_VULN("stunVuln"),
	PARALYZE_VULN("paralyzeVuln"),
	ROOT_VULN("rootVuln"),
	SLEEP_VULN("sleepVuln"),
	CONFUSION_VULN("confusionVuln"),
	MOVEMENT_VULN("movementVuln"),
	FIRE_VULN("fireVuln"),
	WIND_VULN("windVuln"),
	WATER_VULN("waterVuln"),
	EARTH_VULN("earthVuln"),
	HOLY_VULN("holyVuln"),
	DARK_VULN("darkVuln"),
	CANCEL_VULN("cancelVuln"),
	DERANGEMENT_VULN("derangementVuln"),
	DEBUFF_VULN("debuffVuln"),
	CRIT_VULN("critVuln"),

	NONE_WPN_VULN("noneWpnVuln"),
	SWORD_WPN_VULN("swordWpnVuln"),
	BLUNT_WPN_VULN("bluntWpnVuln"),
	DAGGER_WPN_VULN("daggerWpnVuln"),
	BOW_WPN_VULN("bowWpnVuln"),
	POLE_WPN_VULN("poleWpnVuln"),
	ETC_WPN_VULN("etcWpnVuln"),
	FIST_WPN_VULN("fistWpnVuln"),
	DUAL_WPN_VULN("dualWpnVuln"),
	DUALFIST_WPN_VULN("dualFistWpnVuln"),
	BIGSWORD_WPN_VULN("bigSwordWpnVuln"),

	REFLECT_DAMAGE_PERCENT("reflectDam"),
	REFLECT_SKILL_MAGIC("reflectSkillMagic"),
	REFLECT_SKILL_PHYSIC("reflectSkillPhysic"),
	REFLECT_SKILL_MELEE_PHYSIC("reflectSkillMeleePhysic"),
	ABSORB_DAMAGE_PERCENT("absorbDam"),
    TRANSFER_DAMAGE_PERCENT("transDam"),

	MAX_LOAD("maxLoad"),

	PATK_PLANTS("pAtk-plant"),
	PATK_INSECTS("pAtk-insect"),
	PATK_ANIMALS("pAtk-animal"),
	PATK_BEAST("pAtk-beast"),
	PATK_DRAGONS("pAtk-dragon"),
	PATK_UNDEAD("pAtk-undead"),
    PATK_MAGIC("pAtk-magic"),

    // PDEF
    PDEF_PLANTS("pDef-plants"),
    PDEF_INSECTS("pDef-insects"),
    PDEF_ANIMALS("pDef-animals"),
    PDEF_MONSTERS("pDef-monsters"),
    PDEF_DRAGONS("pDef-dragons"),
    PDEF_UNDEAD("pDef-undead"),
    PDEF_GIANTS("pDef-giants"),
    PDEF_MAGIC("pDef-magic"),

    // VALAKAS
    PATK_VALAKAS("pAtk-valakas"),
    PDEF_VALAKAS("pDef-valakas"),

	ATK_REUSE("atkReuse"),
	P_REUSE("pReuse"),

	//ExSkill
	INV_LIM("inventoryLimit"),
	WH_LIM("whLimit"),
	FREIGHT_LIM("FreightLimit"),
	P_SELL_LIM("PrivateSellLimit"),
	P_BUY_LIM("PrivateBuyLimit"),
	REC_D_LIM("DwarfRecipeLimit"),
	REC_C_LIM("CommonRecipeLimit"),

	// ELEMENTS POWER
 	FIRE_POWER("firePower"),
 	WATER_POWER("waterPower"),
 	WIND_POWER("windPower"),
 	EARTH_POWER("earthPower"),
 	HOLY_POWER("holyPower"),
 	DARK_POWER("darkPower"),

    //C4 STATS
    MP_CONSUME_RATE("MpConsumeRate"),
    DANCE_CONSUME_RATE("DanceMpConsumeRate"),
    HP_CONSUME_RATE("HpConsumeRate"),
    MP_CONSUME("MpConsume"),
    SOULSHOT_COUNT ("soulShotCount"),

    // MASTERY
	SKILL_MASTERY("skillMastery");

	;

	public static final int NUM_STATS = values().length;

	private String _value;

	public String getValue()
	{
		return _value;
	}

	private Stats(String s)
	{
		_value = s;
	}

	public static Stats valueOfXml(String name)
	{
		name = name.intern();
		for (Stats s : values())
		{
			if (s.getValue().equals(name))
				return s;
		}

		throw new NoSuchElementException("Unknown name '" + name + "' for enum BaseStats");
	}
}