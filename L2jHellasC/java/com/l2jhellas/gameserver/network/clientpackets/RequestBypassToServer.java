/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jhellas.gameserver.network.clientpackets;

import java.util.logging.Level;
import java.util.logging.Logger;

import Extensions.RankSystem.RankPvpSystemPlayerInfo;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.ai.CtrlIntention;
import com.l2jhellas.gameserver.communitybbs.CommunityBoard;
import com.l2jhellas.gameserver.handler.AdminCommandHandler;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.L2CharPosition;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2ClassMasterInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2NpcInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.entity.L2Event;
import com.l2jhellas.gameserver.model.entity.engines.CTF;
import com.l2jhellas.gameserver.model.entity.engines.DM;
import com.l2jhellas.gameserver.model.entity.engines.TvT;
import com.l2jhellas.gameserver.model.entity.engines.VIP;
import com.l2jhellas.gameserver.network.serverpackets.ActionFailed;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * This class ...
 * 
 * @version $Revision: 1.12.4.5 $ $Date: 2005/04/11 10:06:11 $
 */
public final class RequestBypassToServer extends L2GameClientPacket
{
	private static final String _C__21_REQUESTBYPASSTOSERVER = "[C] 21 RequestBypassToServer";
	private static Logger _log = Logger.getLogger(RequestBypassToServer.class.getName());

	// S
	private String _command;

	/**
	 * @param decrypt
	 */
	@Override
	protected void readImpl()
	{
		_command = readS();
	}

	@Override
	protected void runImpl()
	{
		L2PcInstance activeChar = getClient().getActiveChar();

		if (activeChar == null)
			return;
		
		if (!activeChar.getAntiFlood().getServerBypass().tryPerformAction(_command) && !activeChar.isGM())
			return;
		
		try
		{
			if (_command.startsWith("admin_")) // && activeChar.getAccessLevel()
												// >= Config.GM_ACCESSLEVEL)
			{
				if (Config.ALT_PRIVILEGES_ADMIN && !AdminCommandHandler.getInstance().checkPrivileges(activeChar, _command))
				{
					_log.info("<GM>" + activeChar + " does not have sufficient privileges for command '" + _command + "'.");
					return;
				}

				IAdminCommandHandler ach = AdminCommandHandler.getInstance().getAdminCommandHandler(_command);

				if (ach != null)
					ach.useAdminCommand(_command, activeChar);
				else
					_log.warning("No handler registered for bypass '" + _command + "'");
			}
			else if (_command.equals("come_here") && activeChar.getAccessLevel() >= Config.GM_ACCESSLEVEL)
			{
				comeHere(activeChar);
			}
			else if (_command.startsWith("player_help "))
			{
				playerHelp(activeChar, _command.substring(12));
			}
			else if (_command.startsWith("npc_"))
			{
				if (!activeChar.validateBypass(_command))
					return;

				int endOfId = _command.indexOf('_', 5);
				String id;
				if (endOfId > 0)
					id = _command.substring(4, endOfId);
				else
					id = _command.substring(4);
				try
				{
					L2Object object = L2World.getInstance().findObject(Integer.parseInt(id));
					
					if (_command.substring(endOfId + 1).startsWith("event_participate"))
						L2Event.inscribePlayer(activeChar);
					else if (_command.substring(endOfId + 1).startsWith("vip_joinVIPTeam"))
						VIP.addPlayerVIP(activeChar);
					else if (_command.substring(endOfId + 1).startsWith("vip_joinNotVIPTeam"))
						VIP.addPlayerNotVIP(activeChar);
					else if (_command.substring(endOfId + 1).startsWith("vip_finishVIP"))
						VIP.vipWin(activeChar);
					else if (_command.substring(endOfId + 1).startsWith("tvt_player_join "))
					{
						String teamName = _command.substring(endOfId + 1).substring(16);

						if (TvT._joining)
							TvT.addPlayer(activeChar, teamName);
						else
							activeChar.sendMessage("The event is already started. You can not join now!");
					}
					else if (_command.substring(endOfId + 1).startsWith("tvt_player_leave"))
					{
						if (TvT._joining)
							TvT.removePlayer(activeChar);
						else
							activeChar.sendMessage("The event is already started. You can not leave now!");
					}
					else if (_command.substring(endOfId + 1).startsWith("dmevent_player_join"))
					{
						if (DM._joining)
							DM.addPlayer(activeChar);
						else
							activeChar.sendMessage("The event is already started. You can not join now!");
					}
					else if (_command.substring(endOfId + 1).startsWith("dmevent_player_leave"))
					{
						if (DM._joining)
							DM.removePlayer(activeChar);
						else
							activeChar.sendMessage("The event is already started. You can not leave now!");
					}
					else if (_command.substring(endOfId + 1).startsWith("ctf_player_join "))
					{
						String teamName = _command.substring(endOfId + 1).substring(16);

						if (CTF._joining)
							CTF.addPlayer(activeChar, teamName);
						else
							activeChar.sendMessage("The event is already started. You can not join now!");
					}
					else if (_command.substring(endOfId + 1).startsWith("ctf_player_leave"))
					{
						if (CTF._joining)
							CTF.removePlayer(activeChar);
						else
							activeChar.sendMessage("The event is already started. You can not leave now!");
					}

					else if (((Config.ALLOW_REMOTE_CLASS_MASTER) && (object instanceof L2ClassMasterInstance)) || (object != null && object instanceof L2NpcInstance && endOfId > 0 && activeChar.isInsideRadius(object, L2NpcInstance.INTERACTION_DISTANCE, false, false)))
					{
						((L2NpcInstance) object).onBypassFeedback(activeChar, _command.substring(endOfId + 1));
					}
					activeChar.sendPacket(new ActionFailed());
				}
				catch (NumberFormatException nfe)
				{
				}
			}
			// Draw a Symbol
			else if (_command.equals("menu_select?ask=-16&reply=1"))
			{
				L2Object object = activeChar.getTarget();
				if (object instanceof L2NpcInstance)
				{
					((L2NpcInstance) object).onBypassFeedback(activeChar, _command);
				}
			}
			else if (_command.equals("menu_select?ask=-16&reply=2"))
			{
				L2Object object = activeChar.getTarget();
				if (object instanceof L2NpcInstance)
				{
					((L2NpcInstance) object).onBypassFeedback(activeChar, _command);
				}
			}
			// Navigate throught Manor windows
			else if (_command.startsWith("manor_menu_select?"))
			{
				L2Object object = activeChar.getTarget();
				if (object instanceof L2NpcInstance)
				{
					((L2NpcInstance) object).onBypassFeedback(activeChar, _command);
				}
			}
			else if (_command.startsWith("bbs_"))
			{
				CommunityBoard.getInstance().handleCommands(getClient(), _command);
			}
			else if (_command.startsWith("_bbs"))
			{
				CommunityBoard.getInstance().handleCommands(getClient(), _command);
			}
			else if (_command.startsWith("Quest "))
			{
				if (!activeChar.validateBypass(_command))
					return;

				L2PcInstance player = getClient().getActiveChar();
				if (player == null)
					return;

				String p = _command.substring(6).trim();
				int idx = p.indexOf(' ');
				if (idx < 0)
					player.processQuestEvent(p, "");
				else
					player.processQuestEvent(p.substring(0, idx), p.substring(idx).trim());
			}
			// Custom PvP System (CPS) by Masterio
			// --------------------------------------------
			else if (_command.equals("_cprs_equip"))
			{ // for "details" button
				try
				{
					if (activeChar._RankPvpSystemDeathMgr != null)
					{
						if (activeChar._RankPvpSystemDeathMgr.getKiller() != null)
						{
							activeChar._RankPvpSystemDeathMgr.sendVictimResponse();
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			else if (_command.equals("_cprs_info"))
			{ // for "back" button
				try
				{
					if (activeChar._RankPvpSystemDeathMgr != null)
					{
						// required for death manager, shows killer info:
						RankPvpSystemPlayerInfo playerInfo = new RankPvpSystemPlayerInfo();
						if (activeChar._RankPvpSystemDeathMgr.getKiller() != null)
						{
							playerInfo.sendPlayerResponse(activeChar, activeChar._RankPvpSystemDeathMgr.getKiller());
						}
						playerInfo = null;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			else if (_command.equals("_cprs_reward"))
			{ // for "get reward" button
				try
				{
					if (activeChar._RankPvpSystemPointsReward != null && activeChar._RankPvpSystemPointsReward._rankRewardsCount > 0)
					{
						activeChar._RankPvpSystemPointsReward.getRankPointsRewardToInventory();
						activeChar._RankPvpSystemPointsReward = null;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			// -------------------------------------------------------------------------------
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "Bad RequestBypassToServer: ", e);
		}
		
		// finally
		// {
		// activeChar.clearBypass();
		// }
	}

	/**
	 * @param client
	 */
	private void comeHere(L2PcInstance activeChar)
	{
		L2Object obj = activeChar.getTarget();
		if (obj == null)
			return;
		if (obj instanceof L2NpcInstance)
		{
			L2NpcInstance temp = (L2NpcInstance) obj;
			temp.setTarget(activeChar);
			temp.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new L2CharPosition(activeChar.getX(), activeChar.getY(), activeChar.getZ(), 0));
			// temp.moveTo(player.getX(),player.getY(), player.getZ(), 0 );
		}

	}

	private void playerHelp(L2PcInstance activeChar, String path)
	{
		if (path.indexOf("..") != -1)
			return;
		
		String filename = "data/html/help/" + path;
		NpcHtmlMessage html = new NpcHtmlMessage(1);
		html.setFile(filename);
		activeChar.sendPacket(html);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.l2jhellas.gameserver.clientpackets.ClientBasePacket#getType()
	 */
	@Override
	public String getType()
	{
		return _C__21_REQUESTBYPASSTOSERVER;
	}
}
