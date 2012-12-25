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

import java.util.StringTokenizer;

import javolution.text.TextBuilder;
import javolution.util.FastList;

import com.l2jhellas.Config;
import com.l2jhellas.ExternalConfig;
import com.l2jhellas.gameserver.datatables.BufferSkillsTable;
import com.l2jhellas.gameserver.model.L2Character;
import com.l2jhellas.gameserver.model.L2Skill;
import com.l2jhellas.gameserver.network.L2GameClient;
import com.l2jhellas.gameserver.network.serverpackets.MyTargetSelected;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jhellas.gameserver.network.serverpackets.ValidateLocation;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;

/**
 * NPC Buffer instance handler
 * 
 * This class contains some methods that can be sorted by different types and functions:<br><br>
 * 
 * - Methods that overrides to superclass' (L2FolkInstance):
 * onAction
 * onBypassFeedback 
 * onActionShift
 *  
 * - Methods to show html windows: 
 * showGiveBuffsWindow
 * showManageSchemeWindow
 * showEditSchemeWindow
 * 
 * - Methods to get and build info (Strings, future html content) from character schemes, state, etc.
 * getPlayerSchemeListFrame: Returns a table with player's schemes names
 * getGroupSkillListFrame: Returns a table with skills available in the skill_group
 * getPlayerSkillListFrame: Returns a table with skills already in player's scheme (scheme_key)
 * 
 * @author  House
 */

public class L2BufferInstance extends L2FolkInstance
{
	private static final String PARENT_DIR = "data/html/mods/buffer/";
	
	public L2BufferInstance(int objectId, L2NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		StringTokenizer st = new StringTokenizer(command, " ");
		String currentCommand = st.nextToken();
		
		// initial menu
		if (currentCommand.startsWith("menu"))
		{
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			html.setFile(PARENT_DIR+"menu.htm");
			sendHtmlMessage(player, html);
		}
		
		// handles giving effects {support player, support pet, givebuffs}
		else if (currentCommand.startsWith("support"))
		{
			String targettype = st.nextToken();			
			showGiveBuffsWindow(player, targettype);
		}
		else if (currentCommand.startsWith("givebuffs"))
		{
			String targettype = st.nextToken();
			@SuppressWarnings("unused")
			String scheme_key = st.nextToken();
			int cost = Integer.parseInt(st.nextToken());
			
			if (cost == 0 || cost <= player.getInventory().getAdena()) 
			{
				L2Character target = player;
				
				if (targettype.equalsIgnoreCase("pet") && target != null)
				target = player.getPet();				
		}
		}
	}
	
	@Override
	public void onAction(L2PcInstance player)
	{
		
		player.setLastFolkNPC(this);
		
		if (!canTarget(player)) return;

		// Check if the L2PcInstance already target the L2NpcInstance
		if (this != player.getTarget())
		{
			// Set the target of the L2PcInstance player
			player.setTarget(this);

			// Send a Server->Client packet MyTargetSelected to the L2PcInstance player
			MyTargetSelected my = new MyTargetSelected(getObjectId(), 0);
			player.sendPacket(my);

			// Send a Server->Client packet ValidateLocation to correct the L2NpcInstance position and heading on the client
			player.sendPacket(new ValidateLocation(this));
		}
		else
		{
			// Calculate the distance between the L2PcInstance and the L2NpcInstance
			if (!canInteract(player))
			{
				// Notify the L2PcInstance AI with AI_INTENTION_INTERACT
				// note: commented out so the player must stand close
				//player.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, this);
			}
			else
			{
				NpcHtmlMessage html = new NpcHtmlMessage(1);
				html.setFile(PARENT_DIR+"menu.htm");
				sendHtmlMessage(player, html);
			}
		}
		// Send a Server->Client ActionFailed to the L2PcInstance in order to avoid that the client wait another packet
	}
	
	@Override
	public void onActionShift(L2GameClient client)
	{
		L2PcInstance player = client.getActiveChar();
		if (player == null) return;
		
		if (player.getAccessLevel() >= Config.GM_ACCESSLEVEL)
		{
			TextBuilder tb = new TextBuilder();
			tb.append("<html><title>NPC Buffer - Admin</title>");
			tb.append("<body>Changing buffs feature is not implemented yet. :)<br>");
			tb.append("<br>Please report any bug/impression/suggestion/etc at http://l2jserver.com/forum. " +
					"<br>Contact <font color=\"00FF00\">House</font></body></html>");
			
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			html.setHtml(tb.toString());
			sendHtmlMessage(player, html);
			
		}
	}
	
	private void sendHtmlMessage(L2PcInstance player, NpcHtmlMessage html)
    {
        html.replace("%objectId%", String.valueOf(getObjectId()));
        html.replace("%npcId%", String.valueOf(getNpcId()));
        player.sendPacket(html);
    }
	
	/**
	 * Sends an html packet to player with Give Buffs menu info for player and pet,
	 * depending on targettype parameter {player, pet}
	 * 
	 * @param player
	 * @param targettype
	 */	
	private void showGiveBuffsWindow(L2PcInstance player, String targettype)
	{
		TextBuilder tb = new TextBuilder();
		
		tb.append("<html><title>Buffer - Giving buffs to "+targettype+"</title>");
		tb.append("<body> Here are your defined profiles and their fee, just click on it to receive effects<br>");
			
		NpcHtmlMessage html = new NpcHtmlMessage(1);
		html.setHtml(tb.toString());
		sendHtmlMessage(player, html);
	}
	
	/**
	 * 
	 * @param scheme_key
	 * @return an string with skill_groups table.
	 */
	@SuppressWarnings("unused")
	private String getTypesFrame(String scheme_key)
	{
		TextBuilder tb = new TextBuilder();
		tb.append("<table>");
		int count = 0;
		
		if (scheme_key == null) scheme_key = "unselected";
		
		for (String s : BufferSkillsTable.getInstance().getSkillsTypeList())
		{
			if (count == 0) tb.append("<tr>");
			tb.append("<td width=\"90\"><a action=\"bypass -h npc_%objectId%_editscheme "+s+" "+scheme_key+"\">"+s+"</a></td>");
			if (count == 2)
			{
				tb.append("</tr>");
				count = -1;
			}
			count++;			
		}
		if (!tb.toString().endsWith("</tr>")) tb.append("</tr>");
		tb.append("</table>");
		
		return tb.toString();
	}
	
	/**
	 * 
	 * @param list
	 * @return fee for all skills contained in list. 
	 */
	@SuppressWarnings("unused")
	private int getFee(FastList<L2Skill> list)
	{
		int fee = 0;
		if (ExternalConfig.NPCBUFFER_STATIC_BUFF_COST >= 0) 
			return (list.size()* ExternalConfig.NPCBUFFER_STATIC_BUFF_COST);
		else
		{
			for (L2Skill sk : list)
			{
				fee += BufferSkillsTable.getInstance().getSkillFee(sk.getId());
			}		
		return fee;
		}
	}
}