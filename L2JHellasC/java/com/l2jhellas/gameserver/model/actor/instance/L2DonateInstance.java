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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.StringTokenizer;

import javolution.text.TextBuilder;

import com.l2jhellas.gameserver.ai.CtrlIntention;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.network.serverpackets.ActionFailed;
import com.l2jhellas.gameserver.network.serverpackets.MyTargetSelected;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jhellas.gameserver.network.serverpackets.SocialAction;
import com.l2jhellas.gameserver.network.serverpackets.ValidateLocation;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;
import com.l2jhellas.util.Rnd;

/**
 * @author Autos! 1-0
 */
public class L2DonateInstance extends L2FolkInstance
{
	public L2DonateInstance(int objectId, L2NpcTemplate template)
	{
		super(objectId, template);
	}

	@Override
	public void onBypassFeedback(final L2PcInstance player, String command)
	{
		if (player == null)
			return;

		if (command.startsWith("dlist"))
			info(player);
		if (command.startsWith("donate"))
		{
			StringTokenizer st = new StringTokenizer(command);
			st.nextToken();
			String amount = null;
			int pin1 = 0;
			int pin2 = 0;
			int pin3 = 0;
			int pin4 = 0;
			String message = "";

			try
			{
				amount = st.nextToken();
				pin1 = Integer.parseInt(st.nextToken());
				pin2 = Integer.parseInt(st.nextToken());
				pin3 = Integer.parseInt(st.nextToken());
				pin4 = Integer.parseInt(st.nextToken());
				while (st.hasMoreTokens())
					message = message + st.nextToken() + " ";

				String fname = "data/donates/" + player.getName() + ".txt";
				File file = new File(fname);
				boolean exist = file.createNewFile();
				if (!exist)
				{
					player.sendMessage("Please wait until your last donation is checked!");
					return;
				}
				FileWriter fstream = new FileWriter(fname);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write("Character Info: [Character: " + player.getName() + "[" + player.getObjectId() + "] - Account: " + player.getAccountName() + " - IP: " + player.getClient().getConnection().getInetAddress().getHostAddress() + "]\nMessage : donate " + amount + " " + message + " " + pin1 + " " + pin2 + " " + pin3 + " " + pin4);
				out.close();
				player.sendMessage("Donation sent.Now you have to wait until a gm checks your donation!");

				Collection<L2PcInstance> pls = L2World.getAllPlayers();
				for (L2PcInstance gms : pls)
				{
					if (gms.isGM())
						gms.sendMessage(player.getName() + " sent a donation.");
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
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
			showHtmlWindow(player);
			player.sendPacket(ActionFailed.STATIC_PACKET);
		}
		else
		{
			player.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, this);
			player.sendPacket(ActionFailed.STATIC_PACKET);
		}

	}

	private void showHtmlWindow(L2PcInstance activeChar)
	{
		TextBuilder tb = new TextBuilder();
		NpcHtmlMessage html = new NpcHtmlMessage(1);

		tb.append("<html><head><title>Donation Manager</title></head><body><center><table width=\"250\" bgcolor=\"000000\"><tr><td align=center><font color=\"6fd3d1\">Easy Donation With Paysafe Card</font></td></tr></table>_______________________________________<br><br><table width=\"250\"><tr><td><font color=\"ddc16d\">Select Donation amount:</font></td><td><combobox width=80 height=17 var=amount list=10-Euro;25-Euro;50-Euro;100-Euro;></td></tr></table><br><br><font color=\"ddc16d\">Paysafe Card Pin:</font><table width=\"250\"><tr><td><edit var=\"pin1\" width=50 height=12 type=number></td><td><edit var=\"pin2\" width=50 height=12 type=number></td><td><edit var=\"pin3\" width=50 height=12 type=number></td><td><edit var=\"pin4\" width=50 height=12 type=number></td></table><br><br><multiedit var=\"message\" width=240 height=40><br><br><button value=\"Donate!\" action=\"bypass -h npc_" + getObjectId() + "_donate $amount $pin1 $pin2 $pin3 $pin4 $message\" width=95 height=21 back=\"bigbutton_over\" fore=\"bigbutton\"><br><button value=\"Donation List\" action=\"bypass -h npc_" + getObjectId() + "_dlist\" width=95 height=21 back=\"bigbutton_over\" fore=\"bigbutton\"><br><font color=\"a1df64\">L2jHellas Donation Manager</font></center></body></html>");

		html.setHtml(tb.toString());
		activeChar.sendPacket(html);
	}

	private void info(L2PcInstance activeChar)
	{
		TextBuilder tb = new TextBuilder();
		NpcHtmlMessage html = new NpcHtmlMessage(1);

		tb.append("<html><head><title>Donation Manager</title></head><body><center>Info goes here</center></body></html>");
		html.setHtml(tb.toString());
		activeChar.sendPacket(html);
	}
}