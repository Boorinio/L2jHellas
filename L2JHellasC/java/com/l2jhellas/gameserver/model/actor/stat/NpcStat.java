package com.l2jhellas.gameserver.model.actor.stat;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.skills.Stats;

public class NpcStat extends CharStat
{
	public NpcStat(L2Npc activeChar)
	{
		super(activeChar);
		
		setLevel(getActiveChar().getTemplate().level);
	}
	
	@Override
	public L2Npc getActiveChar()
	{
		return (L2Npc) super.getActiveChar();
	}
	
	@Override
	public final int getMaxHp()
	{
		return (int) calcStat(Stats.MAX_HP, getActiveChar().getTemplate().baseHpMax * (getActiveChar().isChampion() ? Config.CHAMPION_HP : 1), null, null);
	}
	
	@Override
	public int getWalkSpeed()
	{
		return getRunSpeed() / 2;
	}
	
	@Override
	public float getMovementSpeedMultiplier()
	{
		if (getActiveChar() == null)
			return 1;
		float val = getRunSpeed() * 1f / getActiveChar().getTemplate().baseRunSpd;
		if (!getActiveChar().isRunning())
			val = val / 2;
		return val;
	}
}