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

import javolution.text.TextBuilder;

import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.ai.CtrlIntention;
import com.l2jhellas.gameserver.network.serverpackets.MagicSkillUse;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jhellas.gameserver.network.serverpackets.SetupGauge;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;
import com.l2jhellas.util.Broadcast;
import com.l2jhellas.util.Rnd;

public class L2CasinoInstance extends L2NpcInstance
{
	public String filename;
	
	public L2CasinoInstance(int objectId, L2NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		if (player == null)
			return;
		
		if (command.startsWith("play1") && player.getInventory().getInventoryItemCount(9142, 0) >= 2)
			Casino1(player);
		if (command.startsWith("play2") && player.getInventory().getInventoryItemCount(9142, 0) >= 4)
			Casino2(player);
		if (command.startsWith("play3") && player.getInventory().getInventoryItemCount(9142, 0) >= 8)
			Casino3(player);
		if (command.startsWith("play4") && player.getInventory().getInventoryItemCount(9142, 0) >= 16)
			Casino4(player);
		if (command.startsWith("play5") && player.getInventory().getInventoryItemCount(57, 0) >= 500000)
			Casino5(player);
		if (command.startsWith("play6") && player.getInventory().getInventoryItemCount(57, 0) >= 1000000)
			Casino6(player);
		if (command.startsWith("play7") && player.getInventory().getInventoryItemCount(57, 0) >= 10000000)
			Casino7(player);
	}
	
	public static void displayCongrats(L2PcInstance player)
	{
		MagicSkillUse MSU = new MagicSkillUse(player, player, 2024, 1, 1, 0);
		player.broadcastPacket(MSU);
		player.sendMessage("Congratulations! you won");
	}
	
	@Override
	public void showChatWindow(L2PcInstance player, int val)
	{
		filename = (getHtmlPath(getNpcId(), val));
		NpcHtmlMessage msg = new NpcHtmlMessage(this.getObjectId());
		msg.setHtml(casinoWindow(player));
		msg.replace("%objectId%", String.valueOf(this.getObjectId()));
		player.sendPacket(msg);
	}
	
	private static String casinoWindow(L2PcInstance player)
	{
		TextBuilder tb = new TextBuilder();
		tb.append("<html><title>Casino Manager</title><body>");
		tb.append("<center>");
		tb.append("<br>");
		tb.append("<font color=\"999999\">Chance to win : 50%</font><br>");
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
		tb.append("<td><button value=\"2 Apiga\" action=\"bypass -h npc_%objectId%_play1\" back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\" width=95 height=21></td>");
		tb.append("<td><button value=\"4 Apiga\" action=\"bypass -h npc_%objectId%_play2\" back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\" width=95 height=21></td>");
		tb.append("</tr>");
		tb.append("<tr>");
		tb.append("<td><button value=\"8 Apiga\" action=\"bypass -h npc_%objectId%_play3\" back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\" width=95 height=21></td>");
		tb.append("<td><button value=\"16 Apiga\" action=\"bypass -h npc_%objectId%_play4\" back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\" width=95 height=21></td>");
		tb.append("</tr>");
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
		return tb.toString();
	}
	
	public static void Casino1(L2PcInstance player)
	{
		int unstuckTimer = 1000;
		player.setTarget(player);
		player.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		player.disableAllSkills();
		MagicSkillUse msk = new MagicSkillUse(player, 361, 1, unstuckTimer, 0);
		Broadcast.toSelfAndKnownPlayersInRadius(player, msk, 810000);
		SetupGauge sg = new SetupGauge(0, unstuckTimer);
		player.sendPacket(sg);
		
		Casino1 ef = new Casino1(player);
		ThreadPoolManager.getInstance().scheduleGeneral(ef, unstuckTimer);
	}
	
	static class Casino1 implements Runnable
	{
		private final L2PcInstance _player;
		
		Casino1(L2PcInstance player)
		{
			_player = player;
		}
		
		@Override
		public void run()
		{
			if (_player.isDead())
				return;
			
			_player.setIsIn7sDungeon(false);
			_player.enableAllSkills();
			int chance = Rnd.get(2);
			
			if (_player.getInventory().getInventoryItemCount(9142, 0) >= 2)
			{
				if (chance == 0)
				{
					displayCongrats(_player);
					_player.getInventory().addItem("Adena", 9142, 2, _player, null);
				}
				if (chance == 1)
				{
					_player.sendMessage("You lost the bet");
					_player.getInventory().destroyItemByItemId("Adena", 9142, 2, _player, null);
				}
			}
			else
				_player.sendMessage("You do not have enough items.");
		}
	}
	
	public static void Casino2(L2PcInstance player)
	{
		int unstuckTimer = 1000;
		player.setTarget(player);
		player.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		player.disableAllSkills();
		MagicSkillUse msk = new MagicSkillUse(player, 361, 1, unstuckTimer, 0);
		Broadcast.toSelfAndKnownPlayersInRadius(player, msk, 810000);
		SetupGauge sg = new SetupGauge(0, unstuckTimer);
		player.sendPacket(sg);
		
		Casino2 ef = new Casino2(player);
		ThreadPoolManager.getInstance().scheduleGeneral(ef, unstuckTimer);
	}
	
	static class Casino2 implements Runnable
	{
		private final L2PcInstance _player;
		
		Casino2(L2PcInstance player)
		{
			_player = player;
		}
		
		@Override
		public void run()
		{
			if (_player.isDead())
				return;
			
			_player.setIsIn7sDungeon(false);
			_player.enableAllSkills();
			int chance = Rnd.get(3);
			
			if (_player.getInventory().getInventoryItemCount(9142, 0) >= 4)
			{
				if (chance == 0)
				{
					displayCongrats(_player);
					_player.getInventory().addItem("Adena", 9142, 4, _player, null);
				}
				if (chance == 1)
				{
					_player.sendMessage("You lost the bet");
					_player.getInventory().destroyItemByItemId("Adena", 9142, 4, _player, null);
				}
				if (chance == 2)
				{
					_player.sendMessage("You lost the bet");
					_player.getInventory().destroyItemByItemId("Adena", 9142, 4, _player, null);
				}
			}
			else
				_player.sendMessage("You do not have enough items.");
		}
	}
	
	public static void Casino3(L2PcInstance player)
	{
		int unstuckTimer = 1000;
		player.setTarget(player);
		player.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		player.disableAllSkills();
		MagicSkillUse msk = new MagicSkillUse(player, 361, 1, unstuckTimer, 0);
		Broadcast.toSelfAndKnownPlayersInRadius(player, msk, 810000);
		SetupGauge sg = new SetupGauge(0, unstuckTimer);
		player.sendPacket(sg);
		
		Casino3 ef = new Casino3(player);
		ThreadPoolManager.getInstance().scheduleGeneral(ef, unstuckTimer);
	}
	
	static class Casino3 implements Runnable
	{
		private final L2PcInstance _player;
		
		Casino3(L2PcInstance player)
		{
			_player = player;
		}
		
		@Override
		public void run()
		{
			if (_player.isDead())
				return;
			
			_player.setIsIn7sDungeon(false);
			_player.enableAllSkills();
			int chance = Rnd.get(3);
			
			if (_player.getInventory().getInventoryItemCount(9142, 0) >= 8)
			{
				if (chance == 0)
				{
					displayCongrats(_player);
					_player.getInventory().addItem("Adena", 9142, 8, _player, null);
				}
				if (chance == 1)
				{
					_player.sendMessage("You lost the bet");
					_player.getInventory().destroyItemByItemId("Adena", 9142, 8, _player, null);
				}
				if (chance == 2)
				{
					_player.sendMessage("You lost the bet");
					_player.getInventory().destroyItemByItemId("Adena", 9142, 8, _player, null);
				}
			}
			else
				_player.sendMessage("You do not have enough items.");
		}
	}
	
	public static void Casino4(L2PcInstance player)
	{
		int unstuckTimer = 1000;
		player.setTarget(player);
		player.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		player.disableAllSkills();
		MagicSkillUse msk = new MagicSkillUse(player, 361, 1, unstuckTimer, 0);
		Broadcast.toSelfAndKnownPlayersInRadius(player, msk, 810000);
		SetupGauge sg = new SetupGauge(0, unstuckTimer);
		player.sendPacket(sg);
		
		Casino4 ef = new Casino4(player);
		ThreadPoolManager.getInstance().scheduleGeneral(ef, unstuckTimer);
	}
	
	static class Casino4 implements Runnable
	{
		private final L2PcInstance _player;
		
		Casino4(L2PcInstance player)
		{
			_player = player;
		}
		
		@Override
		public void run()
		{
			if (_player.isDead())
				return;
			
			_player.setIsIn7sDungeon(false);
			_player.enableAllSkills();
			int chance = Rnd.get(3);
			
			if (_player.getInventory().getInventoryItemCount(9142, 0) >= 16)
			{
				if (chance == 0)
				{
					displayCongrats(_player);
					_player.getInventory().addItem("Adena", 9142, 16, _player, null);
				}
				if (chance == 1)
				{
					_player.sendMessage("You lost the bet");
					_player.getInventory().destroyItemByItemId("Adena", 9142, 16, _player, null);
				}
				if (chance == 2)
				{
					_player.sendMessage("You lost the bet");
					_player.getInventory().destroyItemByItemId("Adena", 9142, 16, _player, null);
				}
			}
			else
				_player.sendMessage("You do not have enough items.");
		}
	}
	
	public static void Casino5(L2PcInstance player)
	{
		int unstuckTimer = 1000;
		player.setTarget(player);
		player.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		player.disableAllSkills();
		MagicSkillUse msk = new MagicSkillUse(player, 361, 1, unstuckTimer, 0);
		Broadcast.toSelfAndKnownPlayersInRadius(player, msk, 810000);
		SetupGauge sg = new SetupGauge(0, unstuckTimer);
		player.sendPacket(sg);
		
		Casino5 ef = new Casino5(player);
		ThreadPoolManager.getInstance().scheduleGeneral(ef, unstuckTimer);
	}
	
	static class Casino5 implements Runnable
	{
		private final L2PcInstance _player;
		
		Casino5(L2PcInstance player)
		{
			_player = player;
		}
		
		@Override
		public void run()
		{
			if (_player.isDead())
				return;
			
			_player.setIsIn7sDungeon(false);
			_player.enableAllSkills();
			int chance = Rnd.get(2);
			
			if (_player.getInventory().getInventoryItemCount(57, 0) >= 500000)
			{
				if (chance == 0)
				{
					displayCongrats(_player);
					_player.getInventory().addItem("Adena", 57, 500000, _player, null);
				}
				if (chance == 1)
				{
					_player.sendMessage("You lost the bet");
					_player.getInventory().destroyItemByItemId("Adena", 57, 500000, _player, null);
				}
			}
			else
				_player.sendMessage("You do not have enough items.");
		}
	}
	
	public static void Casino6(L2PcInstance player)
	{
		int unstuckTimer = 1000;
		player.setTarget(player);
		player.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		player.disableAllSkills();
		MagicSkillUse msk = new MagicSkillUse(player, 361, 1, unstuckTimer, 0);
		Broadcast.toSelfAndKnownPlayersInRadius(player, msk, 810000);
		SetupGauge sg = new SetupGauge(0, unstuckTimer);
		player.sendPacket(sg);
		
		Casino6 ef = new Casino6(player);
		ThreadPoolManager.getInstance().scheduleGeneral(ef, unstuckTimer);
	}
	
	static class Casino6 implements Runnable
	{
		private final L2PcInstance _player;
		
		Casino6(L2PcInstance player)
		{
			_player = player;
		}
		
		@Override
		public void run()
		{
			if (_player.isDead())
				return;
			
			_player.setIsIn7sDungeon(false);
			_player.enableAllSkills();
			int chance = Rnd.get(3);
			
			if (_player.getInventory().getInventoryItemCount(57, 0) >= 1000000)
			{
				if (chance == 0)
				{
					displayCongrats(_player);
					_player.getInventory().addItem("Adena", 57, 1000000, _player, null);
				}
				if (chance == 1)
				{
					_player.sendMessage("You lost the bet");
					_player.getInventory().destroyItemByItemId("Adena", 57, 1000000, _player, null);
				}
				if (chance == 2)
				{
					_player.sendMessage("You lost the bet");
					_player.getInventory().destroyItemByItemId("Adena", 57, 1000000, _player, null);
				}
			}
			else
				_player.sendMessage("You do not have enough items.");
		}
	}
	
	public static void Casino7(L2PcInstance player)
	{
		int unstuckTimer = 1000;
		player.setTarget(player);
		player.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		player.disableAllSkills();
		MagicSkillUse msk = new MagicSkillUse(player, 361, 1, unstuckTimer, 0);
		Broadcast.toSelfAndKnownPlayersInRadius(player, msk, 810000);
		SetupGauge sg = new SetupGauge(0, unstuckTimer);
		player.sendPacket(sg);
		
		Casino7 ef = new Casino7(player);
		ThreadPoolManager.getInstance().scheduleGeneral(ef, unstuckTimer);
	}
	
	static class Casino7 implements Runnable
	{
		private final L2PcInstance _player;
		
		Casino7(L2PcInstance player)
		{
			_player = player;
		}
		
		@Override
		public void run()
		{
			if (_player.isDead())
				return;
			
			_player.setIsIn7sDungeon(false);
			_player.enableAllSkills();
			int chance = Rnd.get(3);
			
			if (_player.getInventory().getInventoryItemCount(57, 0) >= 10000000)
			{
				if (chance == 0)
				{
					displayCongrats(_player);
					_player.getInventory().addItem("Adena", 57, 10000000, _player, null);
				}
				if (chance == 1)
				{
					_player.sendMessage("You lost the bet");
					_player.getInventory().destroyItemByItemId("Adena", 57, 10000000, _player, null);
				}
				if (chance == 2)
				{
					_player.sendMessage("You lost the bet");
					_player.getInventory().destroyItemByItemId("Adena", 57, 10000000, _player, null);
				}
			}
			else
				_player.sendMessage("You do not have enough items.");
		}
	}
}
