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
package com.l2jhellas.gameserver.model.actor.instance;

import java.io.File;

import javolution.text.TextBuilder;
import Extensions.Vote.VoteMain;

import com.l2jhellas.Config;
import com.l2jhellas.ExternalConfig;
import com.l2jhellas.gameserver.ai.CtrlIntention;
import com.l2jhellas.gameserver.datatables.ItemTable;
import com.l2jhellas.gameserver.network.serverpackets.MyTargetSelected;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jhellas.gameserver.network.serverpackets.PledgeCrest;
import com.l2jhellas.gameserver.network.serverpackets.ValidateLocation;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;
import com.l2jhellas.util.DDSConverter;

public class L2VoteManagerInstance extends L2NpcInstance
{
	public L2VoteManagerInstance(int objectId, L2NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onBypassFeedback(final L2PcInstance player, String command)
	{
		if (player == null)
		{
			return;
		}
		
		if (command.startsWith("votehopzone"))
		{
			VoteMain.hopvote(player);
		}
		
		if (command.startsWith("votetopzone"))
		{
			VoteMain.topvote(player);
		}
		
		if (command.startsWith("rewards"))
		{
			showRewardsHtml(player);
		}
		
		if (command.startsWith("reward1"))
		{
			player.getInventory().addItem("reward", ExternalConfig.VOTE_REWARD_ID1, ExternalConfig.VOTE_REWARD_AMOUNT1, player, null);
			player.sendMessage("Wise choise!");
			VoteMain.setHasNotVotedHop(player);
			VoteMain.setHasNotVotedTop(player);
			VoteMain.setTries(player, VoteMain.getTries(player) + 1);
		}
		
		if (command.startsWith("reward2"))
		{
			player.getInventory().addItem("reward", ExternalConfig.VOTE_REWARD_ID2, ExternalConfig.VOTE_REWARD_AMOUNT2, player, null);
			player.sendMessage("Wise choise!");
			VoteMain.setHasNotVotedHop(player);
			VoteMain.setHasNotVotedTop(player);
			VoteMain.setTries(player, VoteMain.getTries(player) + 1);
		}
		
		if (command.startsWith("reward3"))
		{
			player.getInventory().addItem("reward", ExternalConfig.VOTE_REWARD_ID3, ExternalConfig.VOTE_REWARD_AMOUNT3, player, null);
			player.sendMessage("Wise choise!");
			VoteMain.setHasNotVotedHop(player);
			VoteMain.setHasNotVotedTop(player);
			VoteMain.setTries(player, VoteMain.getTries(player) + 1);
		}
		
		if (command.startsWith("reward4"))
		{
			player.getInventory().addItem("reward", ExternalConfig.VOTE_REWARD_ID4, ExternalConfig.VOTE_REWARD_AMOUNT4, player, null);
			player.sendMessage("Wise choise!");
			VoteMain.setHasNotVotedHop(player);
			VoteMain.setHasNotVotedTop(player);
			VoteMain.setTries(player, VoteMain.getTries(player) + 1);
		}
	}
	
	@Override
	public void showChatWindow(L2PcInstance player)
	{
		if (this != player.getTarget())
		{
			player.setTarget(this);
			
			player.sendPacket(new MyTargetSelected(getObjectId(), 0));
			
			player.sendPacket(new ValidateLocation(this));
		}
		else if (!canInteract(player))
		{
			player.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, this);
		}
		else
		{
			showHtmlWindow(player);
		}
	}
	
	public void showHtmlWindow(L2PcInstance activeChar)
	{
		generateLogo(activeChar, 1821);
		generateLogo(activeChar, 11888);
		generateLogo(activeChar, 65531);
		generateLogo(activeChar, 65532);
		generateLogo(activeChar, 65533);
		VoteMain.hasVotedHop(activeChar);
		VoteMain.hasVotedTop(activeChar);
		
		NpcHtmlMessage nhm = new NpcHtmlMessage(5);
		TextBuilder tb = new TextBuilder("");
		
		tb.append("<html><head><title>Vote reward Panel</title></head><body>");
		tb.append("<center>");
		tb.append("<table width=\"250\" cellpadding=\"5\" bgcolor=\"000000\">");
		tb.append("<tr>");
		tb.append("<td width=\"45\" valign=\"top\" align=\"center\"><img src=\"L2ui_ch3.menubutton4\" width=\"38\" height=\"38\"></td>");
		tb.append("<td valign=\"top\"><font color=\"FF6600\">Vote Panel</font>");
		tb.append("<br1><font color=\"00FF00\">" + activeChar.getName() + "</font>, use this menu to Vote for our server.<br1></td>");
		tb.append("</tr>");
		tb.append("</table>");
		tb.append("</center>");
		tb.append("<center>");
		tb.append("<table bgcolor=\"FFFFFF\"><tr><td align=\"center\"><font color=\"00ff99\">Who's voting now: </font>" + VoteMain.whosVoting() + "</td></tr>");
		tb.append("<tr><td align=\"center\"><font color=\"00ffff\">Tries left: </font>" + VoteMain.getTries(activeChar) + "</td></tr>");
		tb.append("<tr><td align=\"center\"><font color=\"FF6600\">You can vote in Hopzone at " + VoteMain.hopCd(activeChar) + "</font></td></tr>");
		tb.append("<tr><td align=\"center\"><font color=\"FF6600\">You can vote in Topzone at " + VoteMain.topCd(activeChar) + "</font></td></tr></table>");
		tb.append("</center>");
		if (!VoteMain.hasVotedHop() || !VoteMain.hasVotedTop())
		{
			tb.append("<table width=\"250\" cellpadding=\"5\" bgcolor=\"000000\">");
			tb.append("<tr>");
			tb.append("<td width=\"45\" valign=\"top\" align=\"center\"><button action=\"bypass -h npc_" + getObjectId() + "_votehopzone\" width=256 height=64 back=\"Crest.crest_" + Config.SERVER_ID + "_" + 1821 + "\" fore=\"Crest.crest_" + Config.SERVER_ID + "_" + 1821 + "\"></td>");
			tb.append("</tr>");
			tb.append("</table>");
			tb.append("<table width=\"250\" cellpadding=\"5\" bgcolor=\"000000\">");
			tb.append("<tr>");
			tb.append("<td width=\"45\" valign=\"top\" align=\"center\"><button action=\"bypass -h npc_" + getObjectId() + "_votetopzone\" width=256 height=64 back=\"Crest.crest_" + Config.SERVER_ID + "_" + 11888 + "\" fore=\"Crest.crest_" + Config.SERVER_ID + "_" + 11888 + "\"></td>");
			tb.append("</tr>");
			tb.append("</table>");
		}
		if (VoteMain.hasVotedHop() && VoteMain.hasVotedTop())
		{
			tb.append("<table width=\"250\" cellpadding=\"5\" bgcolor=\"FFFFFF\">");
			tb.append("<tr>");
			tb.append("<td width=\"45\" valign=\"top\" align=\"center\"><button action=\"bypass -h npc_" + getObjectId() + "_rewards\" width=256 height=64 back=\"Crest.crest_" + Config.SERVER_ID + "_" + 65531 + "\" fore=\"Crest.crest_" + Config.SERVER_ID + "_" + 65531 + "\"></td>");
			tb.append("</tr>");
			tb.append("</table>");
		}
		tb.append("<center><table width=\"250\" cellpadding=\"5\" bgcolor=\"000000\">");
		if (!VoteMain.hasVotedHop())
		{
			tb.append("<tr><td width=\"45\" valign=\"top\" align=\"center\"><font color=\"FF6600\">Hopzone Status: </font><img src=\"Crest.crest_" + Config.SERVER_ID + "_" + 65533 + "\" width=32 height=32>");
		}
		if (VoteMain.hasVotedHop())
		{
			tb.append("<tr><td width=\"45\" valign=\"top\" align=\"center\"><font color=\"FF6600\">Hopzone Status: </font><img src=\"Crest.crest_" + Config.SERVER_ID + "_" + 65532 + "\" width=32 height=32>");
		}
		if (!VoteMain.hasVotedTop())
		{
			tb.append("<br1><font color=\"FF6600\">Topzone Status: </font><img src=\"Crest.crest_" + Config.SERVER_ID + "_" + 65533 + "\" width=32 height=32></td></tr>");
		}
		if (VoteMain.hasVotedTop())
		{
			tb.append("<br1><font color=\"FF6600\">Topzone Status: </font><img src=\"Crest.crest_" + Config.SERVER_ID + "_" + 65532 + "\" width=32 height=32></td></tr>");
		}
		tb.append("</table></center>");
		tb.append("<center>");
		tb.append("<table bgcolor=\"000000\"><tr><td align=\"center\"><font color=\"FF6600\">Your votes this month: </font>" + VoteMain.getMonthVotes(activeChar) + "</td></tr>");
		tb.append("<tr><td align=\"center\"><font color=\"FF6600\">Your total votes in general: </font>" + VoteMain.getTotalVotes(activeChar) + "</td></tr>");
		tb.append("<tr><td align=\"center\"><font color=\"FF6600\">Players voted this month: </font>" + VoteMain.getBigMonthVotes(activeChar) + "</td></tr>");
		tb.append("<tr><td align=\"center\"><font color=\"FF6600\">Players voted in general: </font>" + VoteMain.getBigTotalVotes(activeChar) + "</td></tr></table>");
		tb.append("</center>");
		tb.append("</body></html>");
		
		nhm.setHtml(tb.toString());
		activeChar.sendPacket(nhm);
	}
	
	public static void generateLogo(L2PcInstance activeChar, int imgId)
	{
		try
		{
			if (imgId == 1821)
			{
				File captcha = new File("data/images/hopzone.png");
				PledgeCrest packet = new PledgeCrest(imgId, DDSConverter.convertToDDS(captcha).array());
				activeChar.sendPacket(packet);
			}
			
			if (imgId == 11888)
			{
				File captcha = new File("data/images/topzone.png");
				PledgeCrest packet = new PledgeCrest(imgId, DDSConverter.convertToDDS(captcha).array());
				activeChar.sendPacket(packet);
			}
			
			if (imgId == 65531)
			{
				File captcha = new File("data/images/rewards.png");
				PledgeCrest packet = new PledgeCrest(imgId, DDSConverter.convertToDDS(captcha).array());
				activeChar.sendPacket(packet);
			}
			
			if (imgId == 65532)
			{
				File captcha = new File("data/images/check.png");
				PledgeCrest packet = new PledgeCrest(imgId, DDSConverter.convertToDDS(captcha).array());
				activeChar.sendPacket(packet);
			}
			
			if (imgId == 65533)
			{
				File captcha = new File("data/images/noncheck.png");
				PledgeCrest packet = new PledgeCrest(imgId, DDSConverter.convertToDDS(captcha).array());
				activeChar.sendPacket(packet);
			}
		}
		catch (Exception e)
		{
		}
		
	}
	
	public void showRewardsHtml(L2PcInstance player)
	{
		TextBuilder tb = new TextBuilder();
		NpcHtmlMessage html = new NpcHtmlMessage(1);
		
		tb.append("<html><head><title>Vote Reward Panel</title></head><body>");
		tb.append("<center>");
		tb.append("<table width=\"250\" cellpadding=\"5\" bgcolor=\"000000\">");
		tb.append("<tr>");
		tb.append("<td width=\"45\" valign=\"top\" align=\"center\"><img src=\"L2ui_ch3.menubutton4\" width=\"38\" height=\"38\"></td>");
		tb.append("<td valign=\"top\"><font color=\"FF6600\">Vote Panel</font>");
		tb.append("<br1><font color=\"00FF00\">" + player.getName() + "</font>, get your reward here.</td>");
		tb.append("</tr>");
		tb.append("</table>");
		tb.append("</center>");
		tb.append("<center>");
		tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your reward " + player.getName() + ".</font>");
		tb.append("<button value=\"Item:" + ItemTable.getInstance().getTemplate(ExternalConfig.VOTE_REWARD_ID1).getName() + "   Amount:" + ExternalConfig.VOTE_REWARD_AMOUNT1 + "\" action=\"bypass -h npc_" + getObjectId() + "_reward1\" width=204 height=20>");
		tb.append("<button value=\"Item:" + ItemTable.getInstance().getTemplate(ExternalConfig.VOTE_REWARD_ID2).getName() + "   Amount:" + ExternalConfig.VOTE_REWARD_AMOUNT2 + "\" action=\"bypass -h npc_" + getObjectId() + "_reward2\" width=204 height=20>");
		tb.append("<button value=\"Item:" + ItemTable.getInstance().getTemplate(ExternalConfig.VOTE_REWARD_ID3).getName() + "   Amount:" + ExternalConfig.VOTE_REWARD_AMOUNT3 + "\" action=\"bypass -h npc_" + getObjectId() + "_reward3\" width=204 height=20>");
		if (VoteMain.getTotalVotes(player) >= ExternalConfig.EXTRA_REW_VOTE_AM)
		{
			tb.append("<font color=\"FF6600\">Due to your votes you now have a 4th choise!</font><br><button value=\"Item:" + ItemTable.getInstance().getTemplate(ExternalConfig.VOTE_REWARD_ID4).getName() + "   Amount:" + ExternalConfig.VOTE_REWARD_AMOUNT4 + "\" action=\"bypass -h npc_" + getObjectId() + "_reward4\" width=204 height=20>");
		}
		tb.append("</center>");
		
		tb.append("</body></html>");
		
		html.setHtml(tb.toString());
		player.sendPacket(html);
	}
	
}