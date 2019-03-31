package com.l2jhellas.gameserver.skills.effects;

import com.l2jhellas.gameserver.model.L2Effect;
import com.l2jhellas.gameserver.model.L2Skill;
import com.l2jhellas.gameserver.skills.Env;
import com.l2jhellas.gameserver.skills.SkillTable;

public final class EffectBestowSkill extends L2Effect
{
	public EffectBestowSkill(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public EffectType getEffectType()
	{
		return EffectType.BUFF;
	}
	
	@Override
	public boolean onStart()
	{
		L2Skill tempSkill = SkillTable.getInstance().getInfo(getSkill().getTriggeredId(), getSkill().getTriggeredLevel());
		if (tempSkill != null)
		{
			getEffected().addSkill(tempSkill);
			return true;
		}
		return false;
	}
	
	@Override
	public void onExit()
	{
		getEffected().removeSkill(getSkill().getTriggeredId());
	}
	
	@Override
	public boolean onActionTime()
	{
		return false;
	}
}