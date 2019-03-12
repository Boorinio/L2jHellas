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

import static com.l2jhellas.gameserver.ai.CtrlIntention.AI_INTENTION_ACTIVE;
import static com.l2jhellas.gameserver.ai.CtrlIntention.AI_INTENTION_ATTACK;
import static com.l2jhellas.gameserver.ai.CtrlIntention.AI_INTENTION_CAST;
import static com.l2jhellas.gameserver.ai.CtrlIntention.AI_INTENTION_IDLE;
import static com.l2jhellas.gameserver.ai.CtrlIntention.AI_INTENTION_INTERACT;
import static com.l2jhellas.gameserver.ai.CtrlIntention.AI_INTENTION_PICK_UP;
import static com.l2jhellas.gameserver.ai.CtrlIntention.AI_INTENTION_REST;

import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.emum.DuelState;
import com.l2jhellas.gameserver.model.L2CharPosition;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.L2SkillTargetType;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.L2Character.AIAccessor;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2StaticObjectInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;


public class L2PlayerAI extends L2CharacterAI
{
	protected static final Logger _log = Logger.getLogger(L2PlayerAI.class.getName());

	private boolean _thinking; // to prevent recursive thinking
	
	private IntentionCommand _nextIntention = null;
	
	void saveNextIntention(CtrlIntention intention, Object arg0, Object arg1)
	{
		_nextIntention = new IntentionCommand(intention, arg0, arg1);
	}

	public L2PlayerAI(AIAccessor accessor)
	{
		super(accessor);
	}

	/**
	 * Saves the current Intention for this L2PlayerAI if necessary and calls changeIntention in AbstractAI.<BR>
	 * <BR>
	 * 
	 * @param intention
	 *        The new Intention to set to the AI
	 * @param arg0
	 *        The first parameter of the Intention
	 * @param arg1
	 *        The second parameter of the Intention
	 */
	@Override
	synchronized void changeIntention(CtrlIntention intention, Object arg0, Object arg1)
	{
		if (intention != AI_INTENTION_CAST || (arg0 != null &&  _skill.isOffensive()))
		{
			_nextIntention = null;
			super.changeIntention(intention, arg0, arg1);
			return;
		}	
		// do nothing if next intention is same as current one.
		if ((intention == _intention) && (arg0 == _intentionArg0) && (arg1 == _intentionArg1))
		{
			super.changeIntention(intention, arg0, arg1);
			return;
		}					
		// save current intention so it can be used after cast
		if ((_intention != AI_INTENTION_ACTIVE) && (_intention != AI_INTENTION_IDLE))
		     saveNextIntention(_intention, _intentionArg0, _intentionArg1);
		
		super.changeIntention(intention, arg0, arg1);
	}

	@Override
	protected void onEvtAttacked(L2Character target)
	{		
		if(target == null || _actor.isDead())
			return;

		if (_actor.getActingPlayer().getPet()!=null)
		{
			_actor.getActingPlayer().getPet().getAI().clientStartAutoAttack();
		}
	}
	
	/**
	 * Finalize the casting of a skill. This method overrides L2CharacterAI method.<BR>
	 * <BR>
	 * <B>What it does:</B>
	 * Check if actual intention is set to CAST and, if so, retrieves latest intention
	 * before the actual CAST and set it as the current intention for the player
	 */
	@Override
	protected void onEvtFinishCasting()
	{
		if (getIntention() == CtrlIntention.AI_INTENTION_CAST)
		{
			if (_nextIntention !=null && _nextIntention.getCtrlIntention() != CtrlIntention.AI_INTENTION_CAST) 
				setIntention(_nextIntention.getCtrlIntention(), _nextIntention._arg0, _nextIntention._arg1);
			else
				setIntention(CtrlIntention.AI_INTENTION_IDLE);
		}
	}

	/**
	 * Launch actions corresponding to the Event ReadyToAct.<BR>
	 * <BR>
	 * <B><U> Actions</U> :</B><BR>
	 * <BR>
	 * <li>Launch actions corresponding to the Event Think</li><BR>
	 * <BR>
	 */
	@Override
	protected void onEvtReadyToAct()
	{
		if (_nextIntention != null)
		{
			setIntention(_nextIntention._crtlIntention, _nextIntention._arg0, _nextIntention._arg1);
			_nextIntention = null;
		}
		
		super.onEvtReadyToAct();
	}
	
	@Override
	protected void onEvtCancel()
	{
		_nextIntention = null;
		super.onEvtCancel();
	}
	
	@Override
	protected void onIntentionRest()
	{
		if (getIntention() != AI_INTENTION_REST)
		{
			changeIntention(AI_INTENTION_REST, null, null);
			setTarget(null);
			if (getAttackTarget() != null)
			{
				setAttackTarget(null);
			}
			clientStopMoving(null);
		}
	}

	@Override
	protected void onIntentionActive()
	{
		if (getIntention() != AI_INTENTION_ACTIVE)
		{
			final IntentionCommand nextIntention = _nextIntention;
			if (nextIntention != null)
			{
				_nextIntention = null;
				setIntention(nextIntention._crtlIntention, nextIntention._arg0, nextIntention._arg1);
			}
			else
			{
				changeIntention(AI_INTENTION_ACTIVE, null, null);
			}
		}
	}

	@Override
	protected void clientNotifyDead()
	{
		_clientMovingToPawnOffset = 0;
		_clientMoving = false;

		super.clientNotifyDead();
	}

	private void thinkAttack()
	{
		L2Character target = getAttackTarget();

		if (target == null)
		{
			setTarget(null);
			setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
			return;
		}

		if (maybeMoveToPawn(target, _actor.getPhysicalAttackRange()))
			return;
		
		if (checkTargetLostOrDead(target))
			return;
		
		clientStopMoving(null);
		_actor.doAttack(target);
	}

	private void thinkCast()
	{
		L2Character target = getCastTarget();

		if (Config.DEBUG)
			_log.warning(L2PlayerAI.class.getName() + ": thinkCast -> Start");

		if (_skill.getTargetType() == L2SkillTargetType.TARGET_SIGNET_GROUND && _actor instanceof L2PcInstance)
		{
			if (maybeMoveToPosition(((L2PcInstance) _actor).getCurrentSkillWorldPosition(), _actor.getMagicalAttackRange(_skill)))
				return;
		}
		else
		{
			if (checkTargetLost(target))
			{
				if (_skill.isOffensive() && getAttackTarget() != null)
				{
					// Notify the target
					setCastTarget(null);
				}
				return;
			}

			if (target != null && maybeMoveToPawn(target, _actor.getMagicalAttackRange(_skill)))
				return;
		}

		if (_skill.getHitTime() > 50)
			clientStopMoving(null);

		L2Object oldTarget = _actor.getTarget();
		if (oldTarget != null)
		{
			// Replace the current target by the cast target
			if (target != null && oldTarget != target)
				_actor.setTarget(getCastTarget());

			// Launch the Cast of the skill
			_actor.doCast(_skill);

			// Restore the initial target
			if (target != null && oldTarget != target)
				_actor.setTarget(oldTarget);
		}
		else
			_actor.doCast(_skill);

		return;
	}


	
	/**
	 * Manage the Move To Intention : Stop current Attack and Launch a Move to Location Task.<BR>
	 * <BR>
	 * <B><U> Actions</U> : </B><BR>
	 * <BR>
	 * <li>Stop the actor auto-attack server side AND client side by sending Server->Client packet AutoAttackStop (broadcast)</li>
	 * <li>Set the Intention of this AI to MOVE_TO</li>
	 * <li>Move the actor to Location (x,y,z) server side AND client side by sending Server->Client packet MoveToLocation (broadcast)</li><BR>
	 * <BR>
	 */
	@Override
	protected void onIntentionMoveTo(L2CharPosition loc)
	{
		if (getIntention() == AI_INTENTION_REST)
		{
			// Cancel action client side by sending Server->Client packet ActionFailed to the L2PcInstance actor
			clientActionFailed();
			return;
		}
		if (_actor.getActingPlayer().getDuelState() == DuelState.DEAD)
		{
			clientActionFailed();
			_actor.getActingPlayer().sendPacket(SystemMessage.getSystemMessage(SystemMessageId.CANNOT_MOVE_FROZEN));
			return;
		}
		if (_actor.isAllSkillsDisabled() || _actor.isCastingNow() || _actor.isAttackingNow())
		{
			clientActionFailed();
			saveNextIntention(CtrlIntention.AI_INTENTION_MOVE_TO, loc, null);
			return;
		}
		
		// Set the Intention of this AbstractAI to AI_INTENTION_MOVE_TO
		changeIntention(CtrlIntention.AI_INTENTION_MOVE_TO, loc, null);
		
		// Stop the actor auto-attack client side by sending Server->Client packet AutoAttackStop (broadcast)
		clientStopAutoAttack();
		
		// Abort the attack of the L2Character and send Server->Client ActionFailed packet
		_actor.abortAttack();
		
		// Move the actor to Location (x,y,z) server side AND client side by sending Server->Client packet CharMoveToLocation (broadcast)
		moveTo(loc.x, loc.y, loc.z);
	}
	
	private void thinkPickUp()
	{
		if (_actor.isAllSkillsDisabled())
			return;
		L2Object target = getTarget();
		if (checkTargetLost(target))
			return;
		if (maybeMoveToPawn(target, 36))
			return;
		setIntention(AI_INTENTION_IDLE);
		((L2PcInstance.AIAccessor) _accessor).doPickupItem(target);
		return;
	}

	@Override
	protected void onIntentionInteract(L2Object object)
	{
		if (getIntention() == CtrlIntention.AI_INTENTION_REST)
		{
			clientActionFailed();
			return;
		}
		
		if (_actor.isAllSkillsDisabled() || _actor.isCastingNow())
		{
			clientActionFailed();
			return;
		}
		
		changeIntention(CtrlIntention.AI_INTENTION_INTERACT, object, null);
		
		setTarget(object);
		
		moveToPawn(object, 60);
	}
	
	private void thinkInteract()
	{
		if (_actor.isAllSkillsDisabled() || _actor.isCastingNow())
			return;
		
		L2Object target = getTarget();
		
		if (checkTargetLost(target))
			return;
		
		if (maybeMoveToPawn(target, 36))
			return;
		
		if (!(target instanceof L2StaticObjectInstance))
		_actor.getActingPlayer().doInteract((L2Character) target);
		
		setIntention(AI_INTENTION_IDLE);
	}

	@Override
	protected void onEvtThink()
	{

		if (_thinking || _actor.isCastingNow() || _actor.isAllSkillsDisabled())
			return;
		
		if (Config.DEBUG)
			_log.warning(L2PlayerAI.class.getName() + ": onEvtThink -> Check intention");

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

	@Override
	protected void onEvtArrivedRevalidate()
	{
		//_actor.getKnownList().refreshInfos();
		super.onEvtArrivedRevalidate();
	}
	
	@Override
	protected void onEvtForgetObject(L2Object object)
	{
		super.onEvtForgetObject(object);
	}
	
	@Override
	protected void onEvtArrived()
	{
		if (_nextIntention != null)
		{
			setIntention(_nextIntention._crtlIntention, _nextIntention._arg0, _nextIntention._arg1);
			_nextIntention = null;
		}

		super.onEvtArrived();
	}	
}