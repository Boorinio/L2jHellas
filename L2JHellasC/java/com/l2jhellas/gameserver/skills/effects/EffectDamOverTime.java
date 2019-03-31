package com.l2jhellas.gameserver.skills.effects;

import com.l2jhellas.gameserver.model.L2Effect;
import com.l2jhellas.gameserver.model.L2SkillTargetType;
import com.l2jhellas.gameserver.model.actor.L2Attackable;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;
import com.l2jhellas.gameserver.skills.Env;

public final class EffectDamOverTime extends L2Effect
{
	public EffectDamOverTime(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public EffectType getEffectType()
	{
		return EffectType.DMG_OVER_TIME;
	}
	
	@Override
	public boolean onActionTime()
	{
		if (getEffected().isDead())
			return false;
		
		double damage = calc();
		
		if (damage >= getEffected().getCurrentHp())
		{
			if (getSkill().isToggle())
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.SKILL_REMOVED_DUE_LACK_HP);
				getEffected().sendPacket(sm);
				return false;
			}
			
			// ** This is just hotfix, needs better solution *
			// 1947: "DOT skills shouldn't kill"
			// Well, some of them should ;-)
			if (getSkill().getId() != 4082)
				damage = getEffected().getCurrentHp() - 1;
		}
		
		boolean awake = !(getEffected() instanceof L2Attackable) && !(getSkill().getTargetType() == L2SkillTargetType.TARGET_SELF && getSkill().isToggle());
		
		getEffected().reduceCurrentHp(damage, getEffector(), awake);
		
		return true;
	}
}