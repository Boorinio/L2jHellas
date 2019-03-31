package com.l2jhellas.gameserver.handlers.admincommandhandlers;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;

import java.util.logging.Logger;

public class AdminHeal implements IAdminCommandHandler
{
	protected static final Logger _log = Logger.getLogger(AdminHeal.class.getName());
	
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_heal"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.equals("admin_heal"))
		{
			handleRes(activeChar);
		}
		else if (command.startsWith("admin_heal"))
		{
			try
			{
				String healTarget = command.substring(11);
				handleRes(activeChar, healTarget);
			}
			catch (StringIndexOutOfBoundsException e)
			{
				_log.warning(AdminHeal.class.getName() + ": Heal error: " + e);
				if (Config.DEVELOPER)
					e.printStackTrace();
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_S2);
				sm.addString("Incorrect target/radius specified.");
				activeChar.sendPacket(sm);
			}
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	private static void handleRes(L2PcInstance activeChar)
	{
		handleRes(activeChar, null);
	}
	
	private static void handleRes(L2PcInstance activeChar, String player)
	{
		L2Object obj = activeChar.getTarget();
		if (player != null)
		{
			L2PcInstance plyr = L2World.getInstance().getPlayer(player);
			
			if (plyr != null)
			{
				obj = plyr;
			}
			else
			{
				try
				{
					int radius = Integer.parseInt(player);
					for (L2Character object : L2World.getInstance().getVisibleObjects(activeChar, L2Character.class, radius))
					{
						L2Character character = object;
						character.setCurrentHpMp(character.getMaxHp(), character.getMaxMp());
						if (object instanceof L2PcInstance)
						{
							character.setCurrentCp(character.getMaxCp());
						}
					}
					activeChar.sendMessage("Healed within " + radius + " unit radius.");
					return;
				}
				catch (NumberFormatException nbe)
				{
				}
			}
		}
		if (obj == null)
		{
			obj = activeChar;
			activeChar.sendMessage("No target heal self casted.");
		}
		if ((obj instanceof L2Character))
		{
			L2Character target = (L2Character) obj;
			if (target instanceof L2PcInstance && !target.isDead())
			{
				target.setCurrentHpMp(target.getMaxHp(), target.getMaxMp());
				target.setCurrentCp(target.getMaxCp());
				_log.warning(AdminHeal.class.getSimpleName() + ": GM " + activeChar.getName() + "(" + activeChar.getObjectId() + ") healed character " + target.getName() + "(" + target.getObjectId() + ")");
			}
			else if (target instanceof L2MonsterInstance)
			{
				if (target.isChampion() && Config.CHAMPION_ENABLE)
				{
					_log.warning(AdminHeal.class.getSimpleName() + ": GM " + activeChar.getName() + "(" + activeChar.getObjectId() + ")" + " healed champion " + target.getName() + "(" + target.getObjectId() + ")");
				}
				if (target.isBoss())
				{
					_log.warning(AdminHeal.class.getSimpleName() + ": GM " + activeChar.getName() + "(" + activeChar.getObjectId() + ")" + " healed grand boss " + target.getName() + "(" + target.getObjectId() + ")");
				}
				if (target.isRaidMinion())
				{
					_log.warning(AdminHeal.class.getSimpleName() + ": GM " + activeChar.getName() + "(" + activeChar.getObjectId() + ")" + " healed raid minion " + target.getName() + "(" + target.getObjectId() + ")");
				}
				else if (target.isRaid())
				{
					_log.warning(AdminHeal.class.getSimpleName() + ": GM " + activeChar.getName() + "(" + activeChar.getObjectId() + ")" + " healed raid " + target.getName() + "(" + target.getObjectId() + ")");
				}
				else if (((L2MonsterInstance) target).isMob())
				{
					_log.warning(AdminHeal.class.getSimpleName() + ": GM " + activeChar.getName() + "(" + activeChar.getObjectId() + ")" + " healed monster " + target.getName() + "(" + target.getObjectId() + ")");
				}
				else
				{
					_log.warning(AdminHeal.class.getSimpleName() + ": GM " + activeChar.getName() + "(" + activeChar.getObjectId() + ")" + " healed etc " + target.getName() + "(" + target.getObjectId() + ")");
				}
			}
		}
	}
}