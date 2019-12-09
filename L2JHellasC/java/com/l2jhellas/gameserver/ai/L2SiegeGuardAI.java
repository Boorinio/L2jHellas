package com.l2jhellas.gameserver.ai;

import static com.l2jhellas.gameserver.ai.CtrlIntention.AI_INTENTION_ACTIVE;
import static com.l2jhellas.gameserver.ai.CtrlIntention.AI_INTENTION_ATTACK;
import static com.l2jhellas.gameserver.ai.CtrlIntention.AI_INTENTION_IDLE;

import java.util.concurrent.Future;
import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.controllers.GameTimeController;
import com.l2jhellas.gameserver.emum.skills.L2SkillType;
import com.l2jhellas.gameserver.geodata.GeoEngine;
import com.l2jhellas.gameserver.model.L2Effect;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.L2Skill;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.L2Attackable;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.L2Summon;
import com.l2jhellas.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2NpcInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2SiegeGuardInstance;
import com.l2jhellas.util.Rnd;

public class L2SiegeGuardAI extends L2CharacterAI implements Runnable
{
	protected static final Logger _log = Logger.getLogger(L2SiegeGuardAI.class.getName());
	
	private static final int MAX_ATTACK_TIMEOUT = 300; // int ticks, i.e. 30 seconds
	
	private Future<?> _aiTask;
	
	private int _attackTimeout;
	
	private int _globalAggro;
	
	private boolean _thinking; // to prevent recursive thinking
	
	private final int _attackRange;
	
	public L2SiegeGuardAI(L2Character.AIAccessor accessor)
	{
		super(accessor);
		
		_attackTimeout = Integer.MAX_VALUE;
		_globalAggro = -10; // 10 seconds timeout of ATTACK after respawn
		
		_attackRange = ((L2Attackable) _actor).getPhysicalAttackRange();
	}
	
	@Override
	public void run()
	{
		// Launch actions corresponding to the Event Think
		onEvtThink();
		
	}
	
	private boolean autoAttackCondition(L2Character target)
	{
		// Check if the target isn't another guard, folk or a door
		if (target == null || target instanceof L2SiegeGuardInstance || target instanceof L2NpcInstance || target instanceof L2DoorInstance || target.isAlikeDead() || target.isInvul())
			return false;
		
		// Get the owner if the target is a summon
		if (target instanceof L2Summon)
		{
			L2PcInstance owner = ((L2Summon) target).getOwner();
			if (_actor.isInsideRadius(owner, 1000, true, false))
				target = owner;
		}
		
		// Check if the target is a L2PcInstance
		if (target instanceof L2PcInstance)
		{
			// Check if the target isn't in silent move mode AND too far (>100)
			if (((L2PcInstance) target).isSilentMoving() && !_actor.isInsideRadius(target, 250, false, false))
				return false;
		}
		// Los Check Here
		return (_actor.isAutoAttackable(target) && GeoEngine.canSeeTarget(_actor, target, false));
		
	}
	
	@Override
	synchronized void changeIntention(CtrlIntention intention, Object arg0, Object arg1)
	{
		if (Config.DEBUG)
			_log.config(L2SiegeGuardAI.class.getName() + ".changeIntention(" + intention + ", " + arg0 + ", " + arg1 + ")");
		((L2Attackable) _actor).setIsReturningToSpawnPoint(false);
		
		if (intention == AI_INTENTION_IDLE) // active becomes idle if only a summon is present
		{
			// Check if actor is not dead
			if (!_actor.isAlikeDead())
			{
				L2Attackable npc = (L2Attackable) _actor;
				// If its _knownPlayer isn't empty set the Intention to AI_INTENTION_ACTIVE
				if (L2World.getInstance().getVisibleObjects(npc, L2PcInstance.class).size() > 0)
					intention = AI_INTENTION_ACTIVE;
				else
					intention = AI_INTENTION_IDLE;
			}
			
			if (intention == AI_INTENTION_IDLE)
			{
				// Set the Intention of this L2AttackableAI to AI_INTENTION_IDLE
				super.changeIntention(AI_INTENTION_IDLE, null, null);
				
				// Stop AI task and detach AI from NPC
				if (_aiTask != null)
				{
					_aiTask.cancel(true);
					_aiTask = null;
				}
				
				// Cancel the AI
				_accessor.detachAI();
				
				return;
			}
		}
		
		// Set the Intention of this L2AttackableAI to intention
		super.changeIntention(intention, arg0, arg1);
		
		// If not idle - create an AI task (schedule onEvtThink repeatedly)
		if (_aiTask == null)
		{
			_aiTask = ThreadPoolManager.getInstance().scheduleAiAtFixedRate(this, 1000, 1000);
		}
	}
	
	@Override
	protected void onIntentionAttack(L2Character target)
	{
		// Calculate the attack timeout
		_attackTimeout = MAX_ATTACK_TIMEOUT + GameTimeController.getInstance().getGameTicks();
		
		// Manage the Attack Intention : Stop current Attack (if necessary), Start a new Attack and Launch Think Event
		// if (_actor.getTarget() != null)
		super.onIntentionAttack(target);
	}
	
	private void thinkActive()
	{
		L2Attackable npc = (L2Attackable) _actor;
		
		// Update every 1s the _globalAggro counter to come close to 0
		if (_globalAggro != 0)
		{
			if (_globalAggro < 0)
				_globalAggro++;
			else
				_globalAggro--;
		}
		
		// Add all autoAttackable L2Character in L2Attackable Aggro Range to its _aggroList with 0 damage and 1 hate
		// A L2Attackable isn't aggressive during 10s after its spawn because _globalAggro is set to -10
		if (_globalAggro >= 0)
		{
			for (L2Character target : L2World.getInstance().getVisibleObjects(npc, L2Character.class, _attackRange))
			{
				if (target == null)
					continue;
				if (autoAttackCondition(target)) // check aggression
				{
					// Get the hate level of the L2Attackable against this L2Character target contained in _aggroList
					int hating = npc.getHating(target);
					
					// Add the attacker to the L2Attackable _aggroList with 0 damage and 1 hate
					if (hating == 0)
						npc.addDamageHate(target, 0, 1);
				}
			}
			
			// Chose a target from its aggroList
			L2Character hated;
			if (_actor.isConfused())
				hated = _attackTarget; // Force mobs to attak anybody if confused
			else
				hated = npc.getMostHated();
			
			// Order to the L2Attackable to attack the target
			if (hated != null)
			{
				// Get the hate level of the L2Attackable against this L2Character target contained in _aggroList
				int aggro = npc.getHating(hated);
				
				if (aggro + _globalAggro > 0)
				{
					// Set the L2Character movement type to run and send Server->Client packet ChangeMoveType to all others L2PcInstance
					if (!_actor.isRunning())
						_actor.setRunning();
					
					// Set the AI Intention to AI_INTENTION_ATTACK
					setIntention(CtrlIntention.AI_INTENTION_ATTACK, hated, null);
				}
				
				return;
			}
			
		}
		// Order to the L2SiegeGuardInstance to return to its home location because there's no target to attack
		((L2SiegeGuardInstance) _actor).returnHome();
		return;
		
	}
	
	private void attackPrepare()
	{
		// Get all information needed to chose between physical or magical attack
		L2Skill[] skills = null;
		double dist_2 = 0;
		int range = 0;
		L2SiegeGuardInstance sGuard = (L2SiegeGuardInstance) _actor;
		
		try
		{
			_actor.setTarget(_attackTarget);
			skills = _actor.getAllSkills();
			dist_2 = _actor.getPlanDistanceSq(_attackTarget.getX(), _attackTarget.getY());
			range = _actor.getPhysicalAttackRange() + _actor.getTemplate().collisionRadius + _attackTarget.getTemplate().collisionRadius;
		}
		catch (NullPointerException e)
		{
			_log.warning(L2SiegeGuardAI.class.getName() + "-> AttackableAI: Attack target is NULL.");
			_actor.setTarget(null);
			setIntention(AI_INTENTION_IDLE, null, null);
			return;
		}
		
		// never attack defenders
		if (_attackTarget instanceof L2PcInstance && sGuard.getCastle().getSiege().checkIsDefender(((L2PcInstance) _attackTarget).getClan()))
		{
			// Cancel the target
			sGuard.stopHating(_attackTarget);
			_actor.setTarget(null);
			setIntention(AI_INTENTION_IDLE, null, null);
			return;
		}
		
		if (!GeoEngine.canSeeTarget(_actor, _attackTarget, false))
		{
			// Siege guards differ from normal mobs currently:
			// If target cannot seen, don't attack any more
			sGuard.stopHating(_attackTarget);
			_actor.setTarget(null);
			setIntention(AI_INTENTION_IDLE, null, null);
			return;
		}
		
		// Check if the actor isn't muted and if it is far from target
		if (!_actor.isMuted() && dist_2 > (range + 20) * (range + 20))
		{
			// check for long ranged skills and heal/buff skills
			if (!Config.ALT_GAME_MOB_ATTACK_AI || (_actor instanceof L2MonsterInstance && Rnd.nextInt(100) <= 5))
				for (L2Skill sk : skills)
				{
					int castRange = sk.getCastRange();
					
					if (((sk.getSkillType() == L2SkillType.BUFF || sk.getSkillType() == L2SkillType.HEAL) || (dist_2 >= castRange * castRange / 9) && (dist_2 <= castRange * castRange) && (castRange > 70)) && !_actor.isSkillDisabled(sk.getId()) && _actor.getCurrentMp() >= _actor.getStat().getMpConsume(sk) && !sk.isPassive())
					{
						L2Object OldTarget = _actor.getTarget();
						if (sk.getSkillType() == L2SkillType.BUFF || sk.getSkillType() == L2SkillType.HEAL)
						{
							boolean useSkillSelf = true;
							if (sk.getSkillType() == L2SkillType.HEAL && _actor.getCurrentHp() > (int) (_actor.getMaxHp() / 1.5))
							{
								useSkillSelf = false;
								break;
							}
							if (sk.getSkillType() == L2SkillType.BUFF)
							{
								L2Effect[] effects = _actor.getAllEffects();
								for (int i = 0; effects != null && i < effects.length; i++)
								{
									L2Effect effect = effects[i];
									if (effect.getSkill() == sk)
									{
										useSkillSelf = false;
										break;
									}
								}
							}
							if (useSkillSelf)
								_actor.setTarget(_actor);
						}
						
						clientStopMoving(null);
						_accessor.doCast(sk);
						_actor.setTarget(OldTarget);
						return;
					}
				}
			
			// Check if the L2SiegeGuardInstance is attacking, knows the target and can't run
			if (!(_actor.isAttackingNow()) && (_actor.getRunSpeed() == 0) && (_actor.isInSurroundingRegion(_attackTarget)))
			{
				// Cancel the target
				_actor.setTarget(null);
				setIntention(AI_INTENTION_IDLE, null, null);
			}
			else
			{
				double dx = _actor.getX() - _attackTarget.getX();
				double dy = _actor.getY() - _attackTarget.getY();
				double dz = _actor.getZ() - _attackTarget.getZ();
				double homeX = _attackTarget.getX() - sGuard.getHomeX();
				double homeY = _attackTarget.getY() - sGuard.getHomeY();
				
				// Check if the L2SiegeGuardInstance isn't too far from it's home location
				if ((dx * dx + dy * dy > 10000) && (homeX * homeX + homeY * homeY > 3240000)) // 1800 * 1800))
				{
					// Cancel the target
					
					_actor.setTarget(null);
					setIntention(AI_INTENTION_IDLE, null, null);
				}
				else
				// Move the actor to Pawn server side AND client side by sending Server->Client packet MoveToPawn (broadcast)
				{
					// Temporary hack for preventing guards jumping off towers,
					// before replacing this with effective geodata checks and AI modification
					if (dz * dz < 170 * 170) // normally 130 if guard z coordinates correct
						moveToPawn(_attackTarget, range);
				}
			}
			
			return;
			
		}
		// Else, if the actor is muted and far from target, just "move to pawn"
		else if (_actor.isMuted() && dist_2 > (range + 20) * (range + 20))
		{
			// Temporary hack for preventing guards jumping off towers,
			// before replacing this with effective geodata checks and AI modification
			double dz = _actor.getZ() - _attackTarget.getZ();
			if (dz * dz < 170 * 170) // normally 130 if guard z coordinates correct
				moveToPawn(_attackTarget, range);
			return;
		}
		// Else, if this is close enough to attack
		else if (dist_2 <= (range + 20) * (range + 20))
		{
			// Force mobs to attak anybody if confused
			L2Character hated = null;
			if (_actor.isConfused())
				hated = _attackTarget;
			else
				hated = ((L2Attackable) _actor).getMostHated();
			
			if (hated == null)
			{
				setIntention(AI_INTENTION_ACTIVE, null, null);
				return;
			}
			if (hated != _attackTarget)
				_attackTarget = hated;
			
			_attackTimeout = MAX_ATTACK_TIMEOUT + GameTimeController.getInstance().getGameTicks();
			
			// check for close combat skills && heal/buff skills
			if (!_actor.isMuted() && Rnd.nextInt(100) <= 5)
			{
				for (L2Skill sk : skills)
				{
					int castRange = sk.getCastRange();
					
					if (castRange * castRange >= dist_2 && castRange <= 70 && !sk.isPassive() && _actor.getCurrentMp() >= _actor.getStat().getMpConsume(sk) && !_actor.isSkillDisabled(sk.getId()))
					{
						L2Object OldTarget = _actor.getTarget();
						if (sk.getSkillType() == L2SkillType.BUFF || sk.getSkillType() == L2SkillType.HEAL)
						{
							boolean useSkillSelf = true;
							if (sk.getSkillType() == L2SkillType.HEAL && _actor.getCurrentHp() > (int) (_actor.getMaxHp() / 1.5))
							{
								useSkillSelf = false;
								break;
							}
							if (sk.getSkillType() == L2SkillType.BUFF)
							{
								L2Effect[] effects = _actor.getAllEffects();
								for (int i = 0; effects != null && i < effects.length; i++)
								{
									L2Effect effect = effects[i];
									if (effect.getSkill() == sk)
									{
										useSkillSelf = false;
										break;
									}
								}
							}
							if (useSkillSelf)
								_actor.setTarget(_actor);
						}
						
						clientStopMoving(null);
						_accessor.doCast(sk);
						_actor.setTarget(OldTarget);
						return;
					}
				}
			}
			// Finally, do the physical attack itself
			_accessor.doAttack(_attackTarget);
		}
	}
	
	private void thinkAttack()
	{
		if (Config.DEBUG)
			_log.config(L2SiegeGuardAI.class.getName() + ".thinkAttack(); timeout=" + (_attackTimeout - GameTimeController.getInstance().getGameTicks()));
		
		if (_attackTimeout < GameTimeController.getInstance().getGameTicks())
		{
			// Check if the actor is running
			if (_actor.isRunning())
			{
				// Set the actor movement type to walk and send Server->Client packet ChangeMoveType to all others L2PcInstance
				_actor.setWalking();
				
				// Calculate a new attack timeout
				_attackTimeout = MAX_ATTACK_TIMEOUT + GameTimeController.getInstance().getGameTicks();
			}
		}
		
		// Check if target is dead or if timeout is expired to stop this attack
		if (_attackTarget == null || _attackTarget.isAlikeDead() || _attackTimeout < GameTimeController.getInstance().getGameTicks())
		{
			// Stop hating this target after the attack timeout or if target is dead
			if (_attackTarget != null)
			{
				L2Attackable npc = (L2Attackable) _actor;
				npc.stopHating(_attackTarget);
			}
			
			// Cancel target and timeout
			_attackTimeout = Integer.MAX_VALUE;
			_attackTarget = null;
			
			// Set the AI Intention to AI_INTENTION_ACTIVE
			setIntention(AI_INTENTION_ACTIVE, null, null);
			
			_actor.setWalking();
			return;
		}
		
		attackPrepare();
		factionNotify();
	}
	
	private final void factionNotify()
	{
		// Call all L2Object of its Faction inside the Faction Range
		if (((L2Npc) _actor).getFactionId() == null || _attackTarget == null || _actor == null)
			return;
		
		if (_attackTarget.isInvul())
			return;
		
		String faction_id = ((L2Npc) _actor).getFactionId();
		
		// Go through all L2Object that belong to its faction
		for (L2Character cha : L2World.getInstance().getVisibleObjects(_actor, L2Character.class, 1000))
		{
			if (cha == null)
				continue;
			
			if (!(cha instanceof L2Npc))
				continue;
			
			L2Npc npc = (L2Npc) cha;
			
			if (faction_id != npc.getFactionId())
				continue;
			
			// Check if the L2Object is inside the Faction Range of the actor
			if ((npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_IDLE || npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_ACTIVE) && _actor.isInsideRadius(npc, npc.getFactionRange(), false, true) && npc.getTarget() == null && _attackTarget.isInsideRadius(npc, npc.getFactionRange(), false, true))
			{
				if (Config.GEODATA)
				{
					if (GeoEngine.canSeeTarget(npc, _attackTarget, false))
						// Notify the L2Object AI with EVT_AGGRESSION
						npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, _attackTarget, 1);
				}
				else
				{
					if (!npc.isDead() && Math.abs(_attackTarget.getZ() - npc.getZ()) < 600)
						
						// Notify the L2Object AI with EVT_AGGRESSION
						npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, _attackTarget, 1);
				}
			}
		}
	}
	
	@Override
	protected void onEvtThink()
	{
		// if(getIntention() != AI_INTENTION_IDLE && (!_actor.isVisible() || !_actor.hasAI() || !_actor.isKnownPlayers()))
		// setIntention(AI_INTENTION_IDLE);
		
		// Check if the actor can't use skills and if a thinking action isn't already in progress
		if (_thinking || _actor.isAllSkillsDisabled())
			return;
		
		// Start thinking action
		_thinking = true;
		
		try
		{
			// Manage AI thinks of a L2Attackable
			if (getIntention() == AI_INTENTION_ACTIVE)
				thinkActive();
			else if (getIntention() == AI_INTENTION_ATTACK)
				thinkAttack();
		}
		finally
		{
			// Stop thinking action
			_thinking = false;
		}
	}
	
	@Override
	protected void onEvtAttacked(L2Character attacker)
	{
		// Calculate the attack timeout
		_attackTimeout = MAX_ATTACK_TIMEOUT + GameTimeController.getInstance().getGameTicks();
		
		// Set the _globalAggro to 0 to permit attack even just after spawn
		if (_globalAggro < 0)
			_globalAggro = 0;
		
		// Add the attacker to the _aggroList of the actor
		((L2Attackable) _actor).addDamageHate(attacker, 0, 1);
		
		// Set the L2Character movement type to run and send Server->Client packet ChangeMoveType to all others L2PcInstance
		if (!_actor.isRunning())
			_actor.setRunning();
		
		// Set the Intention to AI_INTENTION_ATTACK
		if (getIntention() != AI_INTENTION_ATTACK)
		{
			setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker, null);
		}
		
		super.onEvtAttacked(attacker);
	}
	
	@Override
	protected void onEvtAggression(L2Character target, int aggro)
	{
		if (_actor == null)
			return;
		L2Attackable me = (L2Attackable) _actor;
		
		if (target != null)
		{
			// Add the target to the actor _aggroList or update hate if already present
			me.addDamageHate(target, 0, aggro);
			
			// Get the hate of the actor against the target
			aggro = me.getHating(target);
			
			if (aggro <= 0)
			{
				if (me.getMostHated() == null)
				{
					_globalAggro = -25;
					me.clearAggroList();
					setIntention(AI_INTENTION_IDLE, null, null);
				}
				return;
			}
			
			// Set the actor AI Intention to AI_INTENTION_ATTACK
			if (getIntention() != CtrlIntention.AI_INTENTION_ATTACK)
			{
				// Set the L2Character movement type to run and send Server->Client packet ChangeMoveType to all others L2PcInstance
				if (!_actor.isRunning())
					_actor.setRunning();
				
				L2SiegeGuardInstance sGuard = (L2SiegeGuardInstance) _actor;
				double homeX = target.getX() - sGuard.getHomeX();
				double homeY = target.getY() - sGuard.getHomeY();
				
				// Check if the L2SiegeGuardInstance is not too far from its home location
				if (homeX * homeX + homeY * homeY < 3240000) // 1800 * 1800
					setIntention(CtrlIntention.AI_INTENTION_ATTACK, target, null);
			}
		}
		else
		{
			// currently only for setting lower general aggro
			if (aggro >= 0)
				return;
			
			L2Character mostHated = me.getMostHated();
			if (mostHated == null)
			{
				_globalAggro = -25;
				return;
			}
			for (L2Character aggroed : me.getAggroList().keySet())
				me.addDamageHate(aggroed, 0, aggro);
			
			aggro = me.getHating(mostHated);
			if (aggro <= 0)
			{
				_globalAggro = -25;
				me.clearAggroList();
				setIntention(AI_INTENTION_IDLE, null, null);
			}
		}
	}
	
	@Override
	protected void onEvtDead()
	{
		stopAITask();
		super.onEvtDead();
	}
	
	@Override
	public void stopAITask()
	{
		if (_aiTask != null)
		{
			_aiTask.cancel(false);
			_aiTask = null;
		}
		_accessor.detachAI();
	}
}