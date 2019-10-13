package com.l2jhellas.gameserver.model.actor.instance;

import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.network.serverpackets.ActionFailed;
import com.l2jhellas.gameserver.network.serverpackets.ItemList;
import com.l2jhellas.gameserver.network.serverpackets.MagicSkillUse;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;
import com.l2jhellas.util.Rnd;

public class L2CasinoInstance extends L2Npc
{
	protected String filename;
	protected String notenought = "You don't have enough items.";
	protected int adena = 57;
	
	public L2CasinoInstance(int objectId, L2NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		if (player == null)
			return;
		
		switch (command)
		{
			case "play5":
				if (player.getInventory().getInventoryItemCount(adena, 0) >= 500000)
					casino(player,5);
				else
					player.sendMessage(notenought);
				break;
			case "play6":
				if (player.getInventory().getInventoryItemCount(adena, 0) >= 1000000)
					casino(player,6);
				else
					player.sendMessage(notenought);
				break;
			case "play7":
				if (player.getInventory().getInventoryItemCount(adena, 0) >= 10000000)
					casino(player,7);
				else
					player.sendMessage(notenought);
				break;
			default:
				player.sendPacket(new ActionFailed());
				break;
		}
	}
		
	@Override
	public void showChatWindow(L2PcInstance player, int val)
	{
		filename = (getHtmlPath(getNpcId(), val));
		NpcHtmlMessage msg = new NpcHtmlMessage(getObjectId());
		msg.setHtml(casinoWindow(player));
		msg.replace("%objectId%", String.valueOf(getObjectId()));
		player.sendPacket(msg);
	}
	
	protected String casinoWindow(L2PcInstance player)
	{
		StringBuilder tb = new StringBuilder();
		tb.append("<html><title>Casino Manager</title><body>");
		tb.append("<center>");
		tb.append("<br>");
		tb.append("<font color=\"999999\">Chance to win : 45%</font><br>");
		tb.append("<img src=\"L2UI.SquareGray\" width=\"200\" height=\"1\"><br>");
		tb.append("Welcome " + player.getName() + "<br>");
		tb.append("<tr><td>Double or Nothing ?</td></tr><br>");
		tb.append("<img src=\"L2UI.SquareGray\" width=\"280\" height=\"1\"></center><br>");
		tb.append("<center>");
		tb.append("Place your bets");
		tb.append("</center>");
		tb.append("<img src=\"L2UI.SquareGray\" width=\"280\" height=\"1\"></center><br>");
		tb.append("<br>");
		tb.append("<center>");
		tb.append("<tr>");
		tb.append("<td><button value=\"500k\" action=\"bypass -h npc_%objectId%_play5\" back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\" width=95 height=21></td>");
		tb.append("<td><button value=\"1kk\" action=\"bypass -h npc_%objectId%_play6\" back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\" width=95 height=21></td>");
		tb.append("</tr>");
		tb.append("<tr>");
		tb.append("<td><button value=\"10kk\" action=\"bypass -h npc_%objectId%_play7\" back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\" width=95 height=21></td>");
		tb.append("</tr>");
		tb.append("</center>");
		tb.append("<center><img src=\"L2UI.SquareGray\" width=\"280\" height=\"1\">");
		tb.append("</body></html>");
		player.sendPacket(ActionFailed.STATIC_PACKET);
		return tb.toString();
	}
	
	protected void displayCongrats(L2PcInstance player)
	{
		MagicSkillUse MSU = new MagicSkillUse(player, player, 2024, 1, 1, 0);
		player.broadcastPacket(MSU);
		player.sendMessage("Congratulations, you won!");
	}

	protected void casino(L2PcInstance player,int bet)
	{
		if (player.isDead())
			return;		
		
		int chance = Rnd.get(3);
		
		switch (bet)
		{
			case 5:
				if (player.getInventory().getInventoryItemCount(adena, 0) >= 500000)
				{
					if (chance == 0)
					{
						displayCongrats(player);
						player.getInventory().addItem("Adena", adena, 500000, player, null);
					}
					else
					{
						player.sendMessage("You lost the bet.");
						player.getInventory().destroyItemByItemId("Adena", adena, 500000, player, null);
					}
				}
				else
					player.sendMessage(notenought);
				break;
			case 6:
				if (player.getInventory().getInventoryItemCount(adena, 0) >= 1000000)
				{
					if (chance == 0)
					{
						displayCongrats(player);
						player.getInventory().addItem("Adena", adena, 1000000, player, null);
					}
					else
					{
						player.sendMessage("You lost the bet.");
						player.getInventory().destroyItemByItemId("Adena",adena, 1000000, player, null);
					}

				}
				else
					player.sendMessage(notenought);
				break;	
			case 7:
				if (player.getInventory().getInventoryItemCount(adena, 0) >= 10000000)
				{
					if (chance == 0)
					{
						displayCongrats(player);
						player.getInventory().addItem("Adena", adena, 10000000, player, null);
					}
					else
					{
						player.sendMessage("You lost the bet.");
						player.getInventory().destroyItemByItemId("Adena",adena, 10000000, player, null);
					}
				}
				else
					player.sendMessage(notenought);
				break;			
		}
		player.sendPacket(new ItemList(player, true));
	}
}