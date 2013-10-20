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
package com.l2jhellas.gameserver.handler.admincommandhandlers;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.instancemanager.GrandBossManager;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.ConfirmDlg;
import com.l2jhellas.util.Util;

public class AdminRecallAll implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_recallall"
	};
	public static boolean isAdminSummoning = false;
	public static int x = 0;
	public static int y = 0;
	public static int z = 0;

	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_recallall"))
		{
			x = activeChar.getX();
			y = activeChar.getY();
			z = activeChar.getZ();
			isAdminSummoning = true;

			for (L2PcInstance player : L2World.getAllPlayers())
			{
				try
				{/** @formatter:off */
					if (player.isAlikeDead()
							|| player.isinZodiac
							|| player._inEventCTF
							|| player._inEventDM
							|| player._inEventTvT
							|| player._inEventVIP
							|| player.isInStoreMode()
							|| (GrandBossManager.getInstance().getZone(player) != null && !player.isGM())
							|| player.isFestivalParticipant()
							|| player.isInsideZone(L2Character.ZONE_PVP)
							|| player.isAway()
							|| player.isCursedWeaponEquiped()
							|| player.isInCombat()
							|| player.isInDuel()
							|| player.isInFunEvent()
							|| player.isInJail()
							|| player.isInOlympiadMode()
							|| player.isInCraftMode()
							|| player.isVoting()
							|| player.isTeleporting()
							|| player.isStunned()
							|| player.isSittingTaskLunched()
							|| player.isSitting()
							|| player.isRooted()
							|| player.isOverloaded())
						continue;
					/** @formatter:on */
					if (!Util.checkIfInRange(0, activeChar, player, false))
					{
						ThreadPoolManager.getInstance().scheduleGeneral(new Restore(), 15000);
						ConfirmDlg confirm = new ConfirmDlg(SystemMessageId.S1_WISHES_TO_SUMMON_YOU_FROM_S2_DO_YOU_ACCEPT.getId());
						confirm.addString(activeChar.getName());
						confirm.addZoneName(activeChar.getX(), activeChar.getY(), activeChar.getZ());
						confirm.addTime(15000);
						confirm.addRequesterId(activeChar.getObjectId());
						player.sendPacket(confirm);
					}
					player = null;
				}
				catch (Throwable e)
				{
					if (Config.DEBUG)
						e.printStackTrace();
				}
			}

		}
		return false;

	}

	class Restore implements Runnable
	{
		public void run()
		{
			x = 0;
			y = 0;
			z = 0;
			isAdminSummoning = false;
		}

	}

	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}