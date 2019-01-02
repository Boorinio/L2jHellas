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
package com.l2jhellas.gameserver.ai;

import static com.l2jhellas.gameserver.ai.CtrlIntention.AI_INTENTION_ATTACK;
import static com.l2jhellas.gameserver.ai.CtrlIntention.AI_INTENTION_CAST;
import static com.l2jhellas.gameserver.ai.CtrlIntention.AI_INTENTION_FOLLOW;
import static com.l2jhellas.gameserver.ai.CtrlIntention.AI_INTENTION_IDLE;
import static com.l2jhellas.gameserver.ai.CtrlIntention.AI_INTENTION_INTERACT;
import static com.l2jhellas.gameserver.ai.CtrlIntention.AI_INTENTION_PICK_UP;

import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.L2Skill;
import com.l2jhellas.gameserver.model.L2SkillType;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.L2Character.AIAccessor;
import com.l2jhellas.gameserver.model.actor.L2Summon;

public class L2SummonAI extends L2CharacterAI
{
	private boolean _thinking; // to prevent recursive thinking

	public L2SummonAI(AIAccessor accessor)
	{
		super(accessor);
	}

	@Override
	protected void onIntentionIdle()
	{
		stopFollow();
		onIntentionActive();
	}

	@Override
	protected void onIntentionActive()
	{
		L2Summon summon = (L2Summon) _actor;
		if (summon.getFollowStatus())
			setIntention(AI_INTENTION_FOLLOW, summon.getOwner());
		else
			super.onIntentionActive();
	}

	private void thinkAttack()
	{
		if (checkTargetLostOrDead(getAttackTarget()))
		{
			setTarget(null);
			return;
		}
		
		if (maybeMoveToPawn(getAttackTarget(), _actor.getPhysicalAttackRange()))
			return;
		
		clientStopMoving(null);
		_actor.doAttack(getAttackTarget());
	}

	private void thinkCast()
	{
		L2Summon summon = (L2Summon) _actor;
		if (checkTargetLost(getCastTarget()))
		{
			setCastTarget(null);
			return;
		}
		if (maybeMoveToPawn(getCastTarget(), _actor.getMagicalAttackRange(_skill)))
			return;
		clientStopMoving(null);
		summon.setFollowStatus(false);
		setIntention(AI_INTENTION_IDLE);
		_actor.doCast(_skill);
		return;
	}

	private void thinkPickUp()
	{
		if (_actor.isAllSkillsDisabled())
			return;
		if (checkTargetLost(getTarget()))
			return;
		if (maybeMoveToPawn(getTarget(), 36))
			return;
		setIntention(AI_INTENTION_IDLE);
		((L2Summon.AIAccessor) _accessor).doPickupItem(getTarget());
		return;
	}

	private void thinkInteract()
	{
		if (checkTargetLost(getTarget()))
			return;
		
		if (maybeMoveToPawn(getTarget(), 36))
			return;
		
		setIntention(AI_INTENTION_IDLE);
	}
 
	@Override
	protected void onEvtAttacked(L2Character attacker)
	{
		final L2Summon summon = (L2Summon) _actor;
		
		if(summon!=null)
		   summon.getOwner().getAI().clientStartAutoAttack();
	}
	
	@Override
	protected void onEvtFinishCasting()
	{
		final L2Summon summon = (L2Summon) _actor;
		
		if (_skill.isOffensive() && !(_skill.getSkillType() == L2SkillType.UNLOCK) && !(_skill.getSkillType() == L2SkillType.DELUXE_KEY_UNLOCK))
		    summon.getOwner().getAI().clientStartAutoAttack();	      
		
		if (getCastTarget() == null)
			summon.setFollowStatus(((L2Summon) _actor).getFollowStatus());
		else
			setIntention(CtrlIntention.AI_INTENTION_ATTACK,getCastTarget());		
	}
	
	@Override
	protected void onIntentionCast(L2Skill skill, L2Object target)
	{
		super.onIntentionCast(skill, target);
	}

	@Override
	protected void onEvtThink()
	{
		if (_thinking || _actor.isCastingNow() || _actor.isAllSkillsDisabled())
			return;
		
		_thinking = true;
		try
		{
			if (getIntention() == AI_INTENTION_ATTACK)
				thinkAttack();
			else if (getIntention() == AI_INTENTION_CAST)
				thinkCast();
			else if (getIntention() == AI_INTENTION_PICK_UP)
				thinkPickUp();
			else if (getIntention() == AI_INTENTION_INTERACT)
				thinkInteract();
		}
		finally
		{
			_thinking = false;
		}
	}	
}