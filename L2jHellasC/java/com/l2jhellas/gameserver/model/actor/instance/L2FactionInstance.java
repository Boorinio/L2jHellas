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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.ai.CtrlIntention;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.network.serverpackets.ActionFailed;
import com.l2jhellas.gameserver.network.serverpackets.MyTargetSelected;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jhellas.gameserver.network.serverpackets.SocialAction;
import com.l2jhellas.gameserver.network.serverpackets.ValidateLocation;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;
import com.l2jhellas.util.Rnd;
import com.l2jhellas.util.database.L2DatabaseFactory;

/**
 * Mod Faction Good vs Evil
 */
public class L2FactionInstance extends L2FolkInstance
{
	private final static Logger _log = Logger.getLogger(L2FactionInstance.class.getName());

	public L2FactionInstance(int objectId, L2NpcTemplate template)
	{
		super(objectId, template);
	}

	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		player.sendPacket(ActionFailed.STATIC_PACKET);
		StringTokenizer st = new StringTokenizer(command, " ");
		String actualCommand = st.nextToken();
		@SuppressWarnings("unused")
		String val = "";
		if (st.countTokens() >= 1)
		{
			val = st.nextToken();
		}

		else if (actualCommand.equalsIgnoreCase("setgood"))
		{
			setTarget(player);
			if (player.isgood())
			{
				player.sendMessage("You already are in " + Config.MOD_GVE_NAME_TEAM_GOOD + " faction ");
				player.sendPacket(ActionFailed.STATIC_PACKET);
			}
			else
			{
				if (player.isevil())
				{
					player.sendMessage("You cant change faction.");
					player.sendPacket(ActionFailed.STATIC_PACKET);
				}
				else
				{
					int getevils = L2World.getAllevilPlayers().size();
					int getgoods = L2World.getAllgoodPlayers().size();
					if (getgoods > getevils)
					{
						player.sendMessage("You Cant Use " + Config.MOD_GVE_NAME_TEAM_GOOD + " Faction because Online number of " + Config.MOD_GVE_NAME_TEAM_EVIL + " is smaller.");
						player.sendPacket(ActionFailed.STATIC_PACKET);
					}
					else
					{
						player.setgood(true);
						try (Connection con = L2DatabaseFactory.getInstance().getConnection())
						{
							PreparedStatement statement = con.prepareStatement("SELECT obj_Id FROM characters WHERE char_name=?");
							statement.setString(1, player.getName());
							ResultSet rset = statement.executeQuery();
							int objId = 0;
							if (rset.next())
							{
								objId = rset.getInt(1);
							}
							rset.close();
							statement.close();
							if (objId == 0)
							{
								con.close();
								return;
							}
							statement = con.prepareStatement("UPDATE characters SET good=1 WHERE obj_Id=?");
							statement.setInt(1, objId);
							statement.execute();
							statement.close();
							con.close();
						}
						catch (Exception e)
						{
							_log.log(Level.WARNING, getClass().getName() + ": could not set good status of char:");
							if (Config.DEVELOPER)
							{
								e.printStackTrace();
							}
						}
						_log.log(Level.INFO, getClass().getSimpleName() + ": ##GvE Engine## : player " + player.getName() + " Has Choose " + Config.MOD_GVE_NAME_TEAM_GOOD + " Faction.");
						if (player.isgood() == true)
						{
							player.broadcastUserInfo();
							player.sendMessage("You Are fighting now for " + Config.MOD_GVE_NAME_TEAM_GOOD + " faction ");
							player.getAppearance().setNameColor(Config.MOD_GVE_COLOR_NAME_GOOD);
							player.teleToLocation(Config.GOODX, Config.GOODY, Config.GOODZ);
							player.setTitle(Config.MOD_GVE_NAME_TEAM_GOOD);
						}
					}
				}
			}
		}
		else if (actualCommand.equalsIgnoreCase("setevil"))
		{
			setTarget(player);
			if (player.isevil())
			{
				player.sendMessage("You already are in " + Config.MOD_GVE_NAME_TEAM_EVIL + " faction.");
				player.sendPacket(ActionFailed.STATIC_PACKET);
			}
			else
			{
				if (player.isgood())
				{
					player.sendMessage("You can't change faction.");
					player.sendPacket(ActionFailed.STATIC_PACKET);
				}
				else
				{
					int getevils = L2World.getAllevilPlayers().size();
					int getgoods = L2World.getAllgoodPlayers().size();
					if (getevils > getgoods)
					{
						player.sendMessage("You cant use " + Config.MOD_GVE_NAME_TEAM_EVIL + " faction because Online number of " + Config.MOD_GVE_NAME_TEAM_EVIL + " is smaller.");
						player.sendPacket(ActionFailed.STATIC_PACKET);
					}
					else
					{
						player.setevil(true);
						try (Connection con = L2DatabaseFactory.getInstance().getConnection())
						{
							PreparedStatement statement = con.prepareStatement("SELECT obj_Id FROM characters WHERE char_name=?");
							statement.setString(1, player.getName());
							ResultSet rset = statement.executeQuery();
							int objId = 0;
							if (rset.next())
							{
								objId = rset.getInt(1);
							}
							rset.close();
							statement.close();
							if (objId == 0)
							{
								con.close();
								return;
							}
							statement = con.prepareStatement("UPDATE characters SET evil=1 WHERE obj_Id=?");
							statement.setInt(1, objId);
							statement.execute();
							statement.close();
							con.close();
						}
						catch (Exception e)
						{
							_log.log(Level.WARNING, getClass().getName() + ": could not set evil status of char:" + e);
							if (Config.DEVELOPER)
							{
								e.printStackTrace();
							}
						}
						_log.log(Level.INFO, getClass().getSimpleName() + ": ##GvE Engine## : player " + player.getName() + " Has Choose " + Config.MOD_GVE_NAME_TEAM_EVIL + " Faction");
						if (player.isevil() == true)
						{
							player.broadcastUserInfo();
							player.sendMessage("You Are fighting Now for " + Config.MOD_GVE_NAME_TEAM_EVIL + " Faction.");
							player.getAppearance().setNameColor(Config.MOD_GVE_COLOR_NAME_EVIL);
							player.teleToLocation(Config.EVILX, Config.EVILY, Config.EVILZ);
							player.setTitle(Config.MOD_GVE_NAME_TEAM_EVIL);
						}
					}
				}
			}
		}
		else if (actualCommand.equalsIgnoreCase("setnobless"))
		{
			if (!(player instanceof L2PcInstance))
				return;
			L2PcInstance activeChar = player;
			if (activeChar.isNoble())
				activeChar.sendMessage("You are already Noblesse!.");
			else
			{
				activeChar.broadcastPacket(new SocialAction(activeChar.getObjectId(), 16));
				activeChar.setNoble(true);
				activeChar.sendMessage("You are now Noble, you are granted with Noblesse status , and Noblesse skills.");
				activeChar.broadcastUserInfo();
				activeChar.getInventory().addItem("Tiara", 7694, 1, activeChar, null);
			}
		}
		else
		{
			super.onBypassFeedback(player, command);
		}
	}

	@Override
	public void onAction(L2PcInstance player)
	{
		if (this != player.getTarget())
		{
			player.setTarget(this);
			player.sendPacket(new MyTargetSelected(getObjectId(), player.getLevel() - getLevel()));
			player.sendPacket(new ValidateLocation(this));
		}
		else if (isInsideRadius(player, INTERACTION_DISTANCE, false, false))
		{
			SocialAction sa = new SocialAction(getObjectId(), Rnd.get(8));
			broadcastPacket(sa);
			player.setLastFolkNPC(this);
			showMessageWindow(player);
			player.sendPacket(ActionFailed.STATIC_PACKET);
		}
		else
		{
			player.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, this);
			player.sendPacket(ActionFailed.STATIC_PACKET);
		}
	}

	private void showMessageWindow(L2PcInstance player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(1);
		html.setFile("data/html/mods/AvD_faction.htm");
		html.replace("%objectId%", String.valueOf(getObjectId()));
		html.replace("%npcname%", getName());
		player.sendPacket(html);
	}
}