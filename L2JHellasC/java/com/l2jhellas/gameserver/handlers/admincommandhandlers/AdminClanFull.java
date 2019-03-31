package com.l2jhellas.gameserver.handlers.admincommandhandlers;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.EtcStatusUpdate;
import com.l2jhellas.gameserver.network.serverpackets.PledgeShowInfoUpdate;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;

public class AdminClanFull implements IAdminCommandHandler
{
	private static final String ADMIN_COMMANDS[] =
	{
		"admin_clanfull"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_clanfull"))
		{
			try
			{
				adminAddClanSkill(activeChar);
				activeChar.sendMessage("Sucessfull usage //clanfull !");
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Usage: //clanfull");
			}
		}
		return true;
	}
	
	private static void adminAddClanSkill(L2PcInstance activeChar)
	{
		L2Object target = activeChar.getTarget();
		L2PcInstance player = null;
		
		if (target == null)
			target = activeChar;
		
		if (target instanceof L2PcInstance)
		{
			player = (L2PcInstance) target;
		}
		else
		{
			activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
			return;
		}
		
		if (!player.isClanLeader())
		{
			player.sendPacket(SystemMessageId.S1_IS_NOT_A_CLAN_LEADER);
			SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_IS_NOT_A_CLAN_LEADER);
			sm.addString(player.getName());
			player.sendPacket(sm);
			return;
		}
		player.getClan().changeLevel(Config.CLAN_LEVEL);
		player.ClanSkills();
		player.sendPacket(new EtcStatusUpdate(activeChar));
		player.sendPacket(new PledgeShowInfoUpdate(player.getClan()));
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}