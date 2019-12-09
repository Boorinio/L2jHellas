package com.l2jhellas.gameserver.skills.effects;

import com.l2jhellas.gameserver.ai.CtrlIntention;
import com.l2jhellas.gameserver.model.L2Effect;
import com.l2jhellas.gameserver.model.actor.instance.L2NpcInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2SiegeFlagInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2SiegeGuardInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2SiegeSummonInstance;
import com.l2jhellas.gameserver.model.actor.position.Location;
import com.l2jhellas.gameserver.skills.Env;

public final class EffectFear extends L2Effect
{
	public static final int FEAR_RANGE = 500;
	
	public EffectFear(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public EffectType getEffectType()
	{
		return EffectType.FEAR;
	}
	
	@Override
	public boolean onStart()
	{
		if (!getEffected().isAfraid())
		{
			// Fear skills cannot be used l2pcinstance to l2pcinstance. Heroic Dread, Curse: Fear, Fear and Horror are the exceptions.
			if (getEffected() instanceof L2PcInstance && getEffector() instanceof L2PcInstance && getSkill().getId() != 1376 && getSkill().getId() != 1169 && getSkill().getId() != 65 && getSkill().getId() != 1092)
				return false;
			if (getEffected() instanceof L2NpcInstance)
				return false;
			if (getEffected() instanceof L2SiegeGuardInstance)
				return false;
			// Fear skills cannot be used on Headquarters Flag.
			if (getEffected() instanceof L2SiegeFlagInstance)
				return false;
			
			if (getEffected() instanceof L2SiegeSummonInstance)
				return false;
			
			getEffected().startFear();
			
			int posX = getEffected().getX();
			int posY = getEffected().getY();
			int posZ = getEffected().getZ();
			
			int signx = -1;
			int signy = -1;
			if (getEffected().getX() > getEffector().getX())
				signx = 1;
			if (getEffected().getY() > getEffector().getY())
				signy = 1;
			posX += signx * FEAR_RANGE;
			posY += signy * FEAR_RANGE;
			
			getEffected().setRunning();
			getEffected().getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new Location(posX, posY, posZ, 0));
			return true;
		}
		return false;
	}
	
	@Override
	public boolean onActionTime()
	{
		getEffected().stopFear(this);
		return true;
	}
}