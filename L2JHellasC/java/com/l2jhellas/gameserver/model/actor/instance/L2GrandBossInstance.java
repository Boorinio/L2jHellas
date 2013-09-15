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

import com.l2jhellas.gameserver.ai.CtrlIntention;
import com.l2jhellas.gameserver.instancemanager.RaidBossPointsManager;
import com.l2jhellas.gameserver.model.L2Skill;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.L2Summon;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;
import com.l2jhellas.util.Rnd;

public final class L2GrandBossInstance extends L2MonsterInstance
{
	private static final int BOSS_MAINTENANCE_INTERVAL = 10000;
	private boolean _teleportedToNest;

	protected boolean _isInSocialAction = false;

	public boolean IsInSocialAction()
	{
		return _isInSocialAction;
	}

	public void setIsInSocialAction(boolean value)
	{
		_isInSocialAction = value;
	}

	/**
	 * Constructor for L2GrandBossInstance. This represent all grandbosses.
	 * 
	 * @param objectId
	 *        ID of the instance
	 * @param template
	 *        L2NpcTemplate of the instance
	 */
	public L2GrandBossInstance(int objectId, L2NpcTemplate template)
	{
		super(objectId, template);
	}

	@Override
	protected int getMaintenanceInterval()
	{
		return BOSS_MAINTENANCE_INTERVAL;
	}

	/**
	 * Used by Orfen to set 'teleported' flag, when hp goes to <50%
	 * 
	 * @param flag
	 */
	public void setTeleported(boolean flag)
	{
		_teleportedToNest = flag;
	}

	public boolean getTeleported()
	{
		return _teleportedToNest;
	}

	@Override
	public void onSpawn()
	{
		if (getNpcId() == 29028) // baium and valakas are all the time in passive mode, theirs attack AI handled in AI scripts
			super.disableCoreAI(true);
		super.onSpawn();
	}

	/**
	 * Reduce the current HP of the L2Attackable, update its _aggroList and launch the doDie Task if necessary.<BR>
	 * <BR>
	 */
	@Override
	public void reduceCurrentHp(double damage, L2Character attacker, boolean awake)
	{
		switch (getTemplate().npcId)
		{
			case 29014: // Orfen
				if ((getCurrentHp() - damage) < getMaxHp() / 2 && !getTeleported())
				{
					clearAggroList();
					getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
					teleToLocation(43577, 15985, -4396, false);
					setTeleported(true);
					setCanReturnToSpawnPoint(false);
				}
			break;
			default:
		}
		if (IsInSocialAction() || isInvul())
			return;
		super.reduceCurrentHp(damage, attacker, awake);
	}

	@Override
	public boolean doDie(L2Character killer)
	{
		if (!super.doDie(killer))
			return false;
		L2PcInstance player = null;

		if (killer instanceof L2PcInstance)
			player = (L2PcInstance) killer;
		else if (killer instanceof L2Summon)
			player = ((L2Summon) killer).getOwner();

		if (player != null)
		{
			broadcastPacket(SystemMessage.getSystemMessage(SystemMessageId.RAID_WAS_SUCCESSFUL));
			if (player.getParty() != null)
			{
				for (L2PcInstance member : player.getParty().getPartyMembers())
				{
					RaidBossPointsManager.addPoints(member, getNpcId(), (getLevel() / 2) + Rnd.get(-5, 5));
				}
			}
			else
				RaidBossPointsManager.addPoints(player, getNpcId(), (getLevel() / 2) + Rnd.get(-5, 5));
		}
		return true;
	}

	@Override
	public void doAttack(L2Character target)
	{
		if (_isInSocialAction)
			return;
		else
			super.doAttack(target);
	}

	@Override
	public void doCast(L2Skill skill)
	{
		if (_isInSocialAction)
			return;
		else
			super.doCast(skill);
	}

	/**
	 * Check if the server allows Random Animation.<BR>
	 * <BR>
	 */
	@Override
	public boolean hasRandomAnimation()
	{
		return false;
	}

	@Override
	public boolean isBoss()
	{
		return true;
	}
}