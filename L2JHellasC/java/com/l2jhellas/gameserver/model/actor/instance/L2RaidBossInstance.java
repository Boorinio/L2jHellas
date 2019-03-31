package com.l2jhellas.gameserver.model.actor.instance;

import com.l2jhellas.gameserver.instancemanager.RaidBossSpawnManager;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.L2Playable;
import com.l2jhellas.gameserver.model.entity.Hero;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;

public final class L2RaidBossInstance extends L2MonsterInstance
{
	private RaidBossSpawnManager.StatusEnum _raidStatus;
	
	public L2RaidBossInstance(int objectId, L2NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onSpawn()
	{
		setIsRaid(true);
		super.onSpawn();
	}
	
	@Override
	public boolean doDie(L2Character killer)
	{
		if (!super.doDie(killer))
			return false;
		if (killer instanceof L2Playable)
		{
			if (killer.isInParty())
			{
				for (L2PcInstance member : killer.getParty().getPartyMembers())
				{
					Hero.getInstance().setRBkilled(member.getObjectId(), getNpcId());
				}
			}
			else
			{
				Hero.getInstance().setRBkilled(killer.getObjectId(), getNpcId());
			}
			SystemMessage msg = SystemMessage.getSystemMessage(SystemMessageId.RAID_WAS_SUCCESSFUL);
			broadcastPacket(msg);
		}
		
		RaidBossSpawnManager.getInstance().updateStatus(this, true);
		return true;
	}
	
	public void setRaidStatus(RaidBossSpawnManager.StatusEnum status)
	{
		_raidStatus = status;
	}
	
	public RaidBossSpawnManager.StatusEnum getRaidStatus()
	{
		return _raidStatus;
	}
	
	@Override
	public void reduceCurrentHp(double damage, L2Character attacker, boolean awake)
	{
		super.reduceCurrentHp(damage, attacker, awake);
	}
	
	public void healFull()
	{
		super.setCurrentHp(super.getMaxHp());
		super.setCurrentMp(super.getMaxMp());
	}
}