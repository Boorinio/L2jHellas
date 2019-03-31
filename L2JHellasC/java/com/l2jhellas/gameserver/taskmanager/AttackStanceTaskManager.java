package com.l2jhellas.gameserver.taskmanager;

import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.L2Playable;
import com.l2jhellas.gameserver.model.actor.L2Summon;
import com.l2jhellas.gameserver.model.actor.instance.L2CubicInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.AutoAttackStop;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AttackStanceTaskManager implements Runnable
{
	private static final long ATTACK_STANCE_PERIOD = 15000; // 15 seconds
	
	private final Map<L2Character, Long> _characters = new ConcurrentHashMap<>();
	
	public static final AttackStanceTaskManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	protected AttackStanceTaskManager()
	{
		// Run task each second.
		ThreadPoolManager.getInstance().scheduleAiAtFixedRate(this, 1000, 1000);
	}
	
	public final void add(L2Character character)
	{
		if (character instanceof L2Playable)
		{
			if (character.getActingPlayer() != null && !character.getActingPlayer().getCubics().isEmpty())
			{
				for (L2CubicInstance cubic : character.getActingPlayer().getCubics().values())
					if (cubic != null && cubic.getId() != L2CubicInstance.LIFE_CUBIC)
						cubic.doAction(character.getActingPlayer());
			}
		}
		
		_characters.put(character, System.currentTimeMillis() + ATTACK_STANCE_PERIOD);
	}
	
	public final void remove(L2Character character)
	{
		if (character instanceof L2Summon)
			character = character.getActingPlayer();
		
		_characters.remove(character);
	}
	
	public final boolean isInAttackStance(L2Character character)
	{
		if (character instanceof L2Summon)
			character = character.getActingPlayer();
		
		return _characters.containsKey(character);
	}
	
	@Override
	public final void run()
	{
		// List is empty, skip.
		if (_characters.isEmpty())
			return;
		
		// Get current time.
		final long time = System.currentTimeMillis();
		
		// Loop all characters.
		for (Map.Entry<L2Character, Long> entry : _characters.entrySet())
		{
			// Time hasn't passed yet, skip.
			if (time < entry.getValue())
				continue;
			
			// Get character.
			final L2Character character = entry.getKey();
			
			// Stop character attack stance animation.
			character.broadcastPacket(new AutoAttackStop(character.getObjectId()));
			
			// Stop pet attack stance animation.
			if (character instanceof L2PcInstance && ((L2PcInstance) character).getPet() != null)
				((L2PcInstance) character).getPet().broadcastPacket(new AutoAttackStop(((L2PcInstance) character).getPet().getObjectId()));
			
			// Inform character AI and remove task.
			character.getAI().setAutoAttacking(false);
			_characters.remove(character);
		}
	}
	
	private static class SingletonHolder
	{
		protected static final AttackStanceTaskManager _instance = new AttackStanceTaskManager();
	}
}