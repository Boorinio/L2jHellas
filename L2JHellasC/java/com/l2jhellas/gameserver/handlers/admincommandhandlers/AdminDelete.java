package com.l2jhellas.gameserver.handlers.admincommandhandlers;

import com.l2jhellas.gameserver.datatables.sql.SpawnTable;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.instancemanager.RaidBossSpawnManager;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.L2Spawn;
import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;

public class AdminDelete implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_delete"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.equals(ADMIN_COMMANDS[0]))
			handleDelete(activeChar);
		return true;
	}
	
	private static void handleDelete(L2PcInstance activeChar)
	{
		L2Object obj = activeChar.getTarget();
		if ((obj != null) && (obj instanceof L2Npc))
		{
			L2Npc target = (L2Npc) obj;
			target.deleteMe();
			
			L2Spawn spawn = target.getSpawn();
			if (spawn != null)
			{
				spawn.stopRespawn();
				
				if (RaidBossSpawnManager.getInstance().isDefined(spawn.getNpcid()))
					RaidBossSpawnManager.getInstance().deleteSpawn(spawn, true);
				else
					SpawnTable.getInstance().deleteSpawn(spawn, true);
			}
			
			SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_S2);
			sm.addString("Deleted " + target.getName() + " from " + target.getObjectId() + ".");
			activeChar.sendPacket(sm);
		}
		else
		{
			SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_S2);
			sm.addString("Incorrect target.");
			activeChar.sendPacket(sm);
		}
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}