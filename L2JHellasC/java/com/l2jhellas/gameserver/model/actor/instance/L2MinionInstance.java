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
package com.l2jhellas.gameserver.model.actor.instance;

import com.l2jhellas.gameserver.ai.L2AttackableAI;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.L2WorldRegion;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;

/**
 * This class manages all Minions.
 * In a group mob, there are one master called RaidBoss and several slaves called Minions.
 */
public final class L2MinionInstance extends L2MonsterInstance
{

	public L2MinionInstance(int objectId, L2NpcTemplate template)
	{
		super(objectId, template);
	}

	@Override
	public void onSpawn()
	{
		super.onSpawn();
		// Notify Leader that Minion has Spawned
		getLeader().getMinionList().onMinionSpawn(this);
		
		if (getLeader().isRaid())
		{
			setIsRaidMinion(true);
		}

		// check the region where this mob is, do not activate the AI if region is inactive.
		final L2WorldRegion region = L2World.getInstance().getRegion(getX(), getY(),getZ());
		if ((region != null) && (!region.isActive()))
			((L2AttackableAI) getAI()).stopAITask();
	}


	@Override
	public L2MonsterInstance getLeader()
	{
		return super.getLeader();
	}

	@Override
	public boolean doDie(L2Character killer)
	{
		if (!super.doDie(killer))
			return false;
		
		if (getLeader() != null)
			getLeader().getMinionList().onMinionDie(this,getLeader().getSpawn().getRespawnDelay() / 2);
		
		return true;
	}
}