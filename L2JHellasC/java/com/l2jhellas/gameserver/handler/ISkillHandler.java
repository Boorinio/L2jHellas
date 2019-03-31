package com.l2jhellas.gameserver.handler;

import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.L2Skill;
import com.l2jhellas.gameserver.model.L2SkillType;
import com.l2jhellas.gameserver.model.actor.L2Character;

import java.io.IOException;

public interface ISkillHandler
{
	
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets) throws IOException;
	
	public L2SkillType[] getSkillIds();
}