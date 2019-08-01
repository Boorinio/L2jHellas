package com.l2jhellas.gameserver.handlers.admincommandhandlers;

import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.instance.L2ControllableMobInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.taskmanager.DecayTaskManager;

public class AdminRes implements IAdminCommandHandler
{
	private static Logger _log = Logger.getLogger(AdminRes.class.getName());
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_res",
		"admin_res_monster"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_res "))
		{
			handleRes(activeChar, command.split(" ")[1]);
		}
		else if (command.equals("admin_res"))
		{
			handleRes(activeChar);
		}
		else if (command.startsWith("admin_res_monster "))
		{
			handleNonPlayerRes(activeChar, command.split(" ")[1]);
		}
		else if (command.equals("admin_res_monster"))
		{
			handleNonPlayerRes(activeChar);
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
	
	private static void handleRes(L2PcInstance activeChar, String resParam)
	{
		L2Object obj = activeChar.getTarget();
		
		if (resParam != null)
		{
			// Check if a player name was specified as a param.
			L2PcInstance plyr = L2World.getInstance().getPlayer(resParam);
			
			if (plyr != null)
			{
				obj = plyr;
			}
			else
			{
				// Otherwise, check if the param was a radius.
				try
				{
					int radius = Integer.parseInt(resParam);
					
					for (L2Character knownPlayer : L2World.getInstance().getVisibleObjects(activeChar, L2Character.class, radius))
					{
						doResurrect(knownPlayer);
					}
					
					activeChar.sendMessage("Resurrected all players within a " + radius + " unit radius.");
					return;
				}
				catch (NumberFormatException e)
				{
					activeChar.sendMessage("Enter a valid player name or radius.");
					return;
				}
			}
		}
		
		if (obj == null)
		{
			obj = activeChar;
		}
		
		if (obj instanceof L2ControllableMobInstance)
		{
			activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
			return;
		}
		
		if (obj instanceof L2PcInstance)
		{
			_log.warning(AdminRes.class.getSimpleName() + ": GM " + activeChar.getName() + "(" + activeChar.getObjectId() + ")" + " resurrected character " + obj.getName() + "(" + obj.getObjectId() + ")");
		}
		else if (obj instanceof L2MonsterInstance)
		{
			if (((L2MonsterInstance) obj).isChampion() && Config.CHAMPION_ENABLE)
			{
				_log.warning(AdminRes.class.getSimpleName() + ": GM " + activeChar.getName() + "(" + activeChar.getObjectId() + ")" + " resurrected champion " + obj.getName() + "(" + obj.getObjectId() + ")");
			}
			else if (((L2MonsterInstance) obj).isBoss())
			{
				_log.warning(AdminRes.class.getSimpleName() + ": GM " + activeChar.getName() + "(" + activeChar.getObjectId() + ")" + " resurrected grand boss " + obj.getName() + "(" + obj.getObjectId() + ")");
			}
			else if (((L2MonsterInstance) obj).isRaidMinion())
			{
				_log.warning(AdminRes.class.getSimpleName() + ": GM " + activeChar.getName() + "(" + activeChar.getObjectId() + ")" + " resurrected raid minion " + obj.getName() + "(" + obj.getObjectId() + ")");
			}
			else if (((L2MonsterInstance) obj).isRaid())
			{
				_log.warning(AdminRes.class.getSimpleName() + ": GM " + activeChar.getName() + "(" + activeChar.getObjectId() + ")" + " resurrected raid " + obj.getName() + "(" + obj.getObjectId() + ")");
			}
			else if (((L2MonsterInstance) obj).isMob())
			{
				_log.warning(AdminRes.class.getSimpleName() + ": GM " + activeChar.getName() + "(" + activeChar.getObjectId() + ")" + " resurrected monster " + obj.getName() + "(" + obj.getObjectId() + ")");
			}
			else
			{
				_log.warning(AdminRes.class.getSimpleName() + ": GM " + activeChar.getName() + "(" + activeChar.getObjectId() + ")" + " resurrected etc " + obj.getName() + "(" + obj.getObjectId() + ")");
			}
		}
		doResurrect((L2Character) obj);
	}
	
	private static void handleNonPlayerRes(L2PcInstance activeChar)
	{
		handleNonPlayerRes(activeChar, "");
	}
	
	private static void handleNonPlayerRes(L2PcInstance activeChar, String radiusStr)
	{
		L2Object obj = activeChar.getTarget();
		
		try
		{
			int radius = 0;
			
			if (!radiusStr.equals(""))
			{
				radius = Integer.parseInt(radiusStr);
				
				for (L2Character knownChar : L2World.getInstance().getVisibleObjects(activeChar, L2Character.class, radius))
					if (!(knownChar instanceof L2PcInstance) && !(knownChar instanceof L2ControllableMobInstance))
					{
						doResurrect(knownChar);
					}
				
				activeChar.sendMessage("Resurrected all non-players within a " + radius + " unit radius.");
			}
		}
		catch (NumberFormatException e)
		{
			activeChar.sendMessage("Enter a valid radius.");
			return;
		}
		
		if (obj == null || obj instanceof L2PcInstance || obj instanceof L2ControllableMobInstance)
		{
			activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
			return;
		}
		
		doResurrect((L2Character) obj);
	}
	
	private static void doResurrect(L2Character targetChar)
	{
		if (!targetChar.isDead())
			return;
		
		// If the target is a player, then restore the XP lost on death.
		if (targetChar instanceof L2PcInstance)
		{
			((L2PcInstance) targetChar).restoreExp(100.0);
		}
		else
		{
			DecayTaskManager.getInstance().cancelDecayTask(targetChar);
		}
		
		targetChar.doRevive();
	}
}