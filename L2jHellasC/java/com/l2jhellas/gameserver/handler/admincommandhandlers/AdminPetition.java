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
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.instancemanager.PetitionManager;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;

/**
 * This class handles commands for GMs to respond to petitions.
 *
 * @author Tempy
 *
 */
public class AdminPetition implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS = {"admin_view_petitions", "admin_view_petition",
		"admin_accept_petition", "admin_reject_petition", "admin_reset_petitions"};

	private static final int REQUIRED_LEVEL = Config.GM_MIN;

	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (!Config.ALT_PRIVILEGES_ADMIN)
			if (!(checkLevel(activeChar.getAccessLevel()) && activeChar.isGM()))
				return false;

		int petitionId = -1;

		try
		{
			petitionId = Integer.parseInt(command.split(" ")[1]);
		}
		catch (Exception e) {}

		if (command.equals("admin_view_petitions"))
			PetitionManager.getInstance().sendPendingPetitionList(activeChar);
		else if (command.startsWith("admin_view_petition"))
			PetitionManager.getInstance().viewPetition(activeChar, petitionId);
		else if (command.startsWith("admin_accept_petition"))
		{
			if (PetitionManager.getInstance().isPlayerInConsultation(activeChar))
			{
				activeChar.sendPacket( new SystemMessage(SystemMessageId.ONLY_ONE_ACTIVE_PETITION_AT_TIME));
				return true;
			}

			if (PetitionManager.getInstance().isPetitionInProcess(petitionId))
			{
				activeChar.sendPacket( new SystemMessage(SystemMessageId.PETITION_UNDER_PROCESS));
				return true;
			}

			if (!PetitionManager.getInstance().acceptPetition(activeChar, petitionId))
				activeChar.sendPacket( new SystemMessage(SystemMessageId.NOT_UNDER_PETITION_CONSULTATION));
		}
		else if (command.startsWith("admin_reject_petition"))
		{
			if (!PetitionManager.getInstance().rejectPetition(activeChar, petitionId))
				activeChar.sendPacket( new SystemMessage(SystemMessageId.FAILED_CANCEL_PETITION_TRY_LATER));
		}
		else if (command.equals("admin_reset_petitions"))
		{
			if (PetitionManager.getInstance().isPetitionInProcess())
			{
				activeChar.sendPacket( new SystemMessage(SystemMessageId.PETITION_UNDER_PROCESS));
				return false;
			}
			PetitionManager.getInstance().clearPendingPetitions();
		}
		return true;
	}

	public String[] getAdminCommandList() {
		return ADMIN_COMMANDS;
	}

	private boolean checkLevel(int level) {
		return (level >= REQUIRED_LEVEL);
	}
}
