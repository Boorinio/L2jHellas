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
package com.l2jhellas.gameserver.handlers.admincommandhandlers;

import java.util.StringTokenizer;

import com.l2jhellas.gameserver.emum.PolyType;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.UserInfo;

/**
 * This class handles following admin commands: polymorph
 */
public class AdminPolymorph implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_polymorph",
		"admin_unpolymorph",
		"admin_polymorph_menu",
		"admin_unpolymorph_menu"
	};

	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (activeChar.isMounted())
			return false;
		
		L2Object target = activeChar.getTarget();
		
		if (target == null)
			target = activeChar;
		
		if (command.startsWith("admin_polymorph"))
		{
			try
			{
				final StringTokenizer st = new StringTokenizer(command);				
				st.nextToken();
				
				PolyType info = PolyType.NPC;
				
				if (st.countTokens() > 1)
					info = Enum.valueOf(PolyType.class, st.nextToken().toUpperCase());
				
				final int npcId = Integer.parseInt(st.nextToken());

				if (!target.getPoly().polymorph(info, npcId))
				{
					activeChar.sendMessage("Something went wrong. try again.");
					return true;
				}

				if(target.isPlayer())
					target.getActingPlayer().sendPacket(new UserInfo(target.getActingPlayer()));
				
				target.broadcastInfo();
				
				activeChar.sendMessage("You successfully polymorphed: " + target.getName() + ".");
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Usage: //polymorph <id>");
			}
		}
		else if (command.startsWith("admin_unpolymorph"))
		{
			if (target.getPoly().getPolyType() == PolyType.DEFAULT)
			{
				activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
				return true;
			}
			
			target.getPoly().unpolymorph();
			
			if(target.isPlayer())
				target.getActingPlayer().sendPacket(new UserInfo(target.getActingPlayer()));
			
			target.broadcastInfo();
			
			activeChar.sendMessage("You successfully unpolymorphed: " + target.getName() + ".");
		}
		
		if (command.contains("menu"))
			showMainPage(activeChar);
		
		return true;
	}

	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	private void showMainPage(L2PcInstance activeChar)
	{
		AdminHelpPage.showHelpPage(activeChar, "effects_menu.htm");
	}
}