/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 * http://www.gnu.org/copyleft/gpl.html
 */
package Extensions.Balancer;

import javolution.text.TextBuilder;

import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;

public class Balancer implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_balance"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.equals("admin_balance") && activeChar.isGM())
		{
			mainHtml(activeChar, 0);
		}
		return true;
	}
	
	public static void mainHtml(L2PcInstance activeChar, int val)
	{
		NpcHtmlMessage nhm = new NpcHtmlMessage(5);
		TextBuilder tb = new TextBuilder("");
		
		tb.append("<html><head><title>Balance Stats</title></head><body>");
		tb.append("<center>");
		tb.append("<table width=260>");
		tb.append("<tr>");
		tb.append("<td><button value=\"Main\" action=\"bypass -h admin_admin\" width=50 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td>");
		tb.append("<td><button value=\"Game\" action=\"bypass -h admin_admin2\" width=50 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td>");
		tb.append("<td><button value=\"Effects\" action=\"bypass -h admin_admin3\" width=50 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td>");
		tb.append("<td><button value=\"Server\" action=\"bypass -h admin_admin4\" width=50 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td>");
		tb.append("<td><button value=\"Mods\" action=\"bypass -h admin_admin5\" width=50 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td>");
		tb.append("</tr>");
		tb.append("</table>");
		tb.append("<br1>");
		
		switch (val)
		{
			case 0:
				tb.append("<font color=\"FF6600\">Choose the 3rd class stats to edit.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit88\">Duelist</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit89\">DreadNought</a></td>");
				tb.append("</tr>");
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit90\">Phoenix Knight</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit91\">Hell Knight</a></td>");
				tb.append("</tr>");
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit92\">Sagittarius</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit93\">Adventurer</a></td>");
				tb.append("</tr>");
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit94\">Archmage</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit95\">Soultaker</a></td>");
				tb.append("</tr>");
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit96\">Arcana Lord</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit97\">Cardinal</a></td>");
				tb.append("</tr>");
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit98\">Hierophant</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit99\">Eva Templar</a></td>");
				tb.append("</tr>");
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit100\">Sword Muse</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit101\">Wind Rider</a></td>");
				tb.append("</tr>");
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit102\">Moonlight Sentinel</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit103\">Mystic Muse</a></td>");
				tb.append("</tr>");
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit104\">Elemental Master</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit105\">Eva Saint</a></td>");
				tb.append("</tr>");
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit106\">Shillien Templar</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit107\">Spectral Dancer</a></td>");
				tb.append("</tr>");
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit108\">Ghost Hunter</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit109\">Ghost Sentinel</a></td>");
				tb.append("</tr>");
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit110\">Storm Screamer</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit111\">Spectral Master</a></td>");
				tb.append("</tr>");
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit112\">Shillen Saint</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit113\">Titan</a></td>");
				tb.append("</tr>");
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit114\">Grand Khauatari</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit115\">Dominator</a></td>");
				tb.append("</tr>");
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit116\">Doomcryer</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit117\">Fortune Seeker</a></td>");
				tb.append("</tr>");
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance edit118\">Maestro</a></td>");
				
				tb.append("</tr>");
				
				tb.append("</table><br>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 88:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus88 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk88\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk88\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus88 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk88\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk88\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus88 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef88\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef88\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus88 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef88\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef88\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus88 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc88\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc88\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus88 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev88\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev88\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus88 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp88\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp88\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus88 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp88\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp88\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus88 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp88\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp88\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus88 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp88\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp88\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus88 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp88\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp88\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save88\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 89:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus89 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk89\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk89\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus89 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk89\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk89\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus89 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef89\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef89\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus89 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef89\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef89\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus89 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc89\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc89\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus89 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev89\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev89\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus89 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp89\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp89\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus89 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp89\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp89\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus89 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp89\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp89\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus89 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp89\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp89\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus89 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp89\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp89\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save89\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 90:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus90 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk90\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk90\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus90 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk90\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk90\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus90 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef90\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef90\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus90 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef90\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef90\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus90 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc90\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc90\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus90 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev90\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev90\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus90 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp90\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp90\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus90 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp90\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp90\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus90 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp90\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp90\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus90 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp90\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp90\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus90 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp90\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp90\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save90\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 91:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus91 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk91\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk91\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus91 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk91\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk91\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus91 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef91\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef91\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus91 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef91\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef91\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus91 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc91\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc91\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus91 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev91\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev91\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus91 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp91\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp91\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus91 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp91\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp91\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus91 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp91\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp91\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus91 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp91\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp91\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus91 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp91\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp91\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save91\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 92:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus92 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk92\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk92\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus92 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk92\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk92\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus92 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef92\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef92\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus92 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef92\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef92\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus92 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc92\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc92\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus92 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev92\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev92\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus92 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp92\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp92\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus92 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp92\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp92\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus92 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp92\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp92\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus92 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp92\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp92\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus92 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp92\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp92\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save92\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 93:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus93 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk93\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk93\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus93 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk93\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk93\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus93 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef93\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef93\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus93 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef93\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef93\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus93 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc93\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc93\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus93 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev93\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev93\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus93 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp93\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp93\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus93 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp93\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp93\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus93 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp93\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp93\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus93 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp93\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp93\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus93 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp93\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp93\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save93\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 94:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus94 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk94\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk94\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus94 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk94\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk94\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus94 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef94\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef94\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus94 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef94\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef94\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus94 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc94\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc94\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus94 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev94\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev94\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus94 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp94\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp94\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus94 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp94\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp94\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus94 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp94\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp94\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus94 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp94\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp94\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus94 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp94\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp94\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save94\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 95:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus95 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk95\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk95\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus95 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk95\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk95\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus95 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef95\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef95\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus95 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef95\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef95\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus95 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc95\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc95\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus95 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev95\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev95\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus95 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp95\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp95\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus95 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp95\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp95\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus95 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp95\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp95\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus95 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp95\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp95\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus95 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp95\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp95\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save95\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 96:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus96 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk96\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk96\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus96 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk96\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk96\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus96 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef96\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef96\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus96 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef96\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef96\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus96 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc96\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc96\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus96 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev96\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev96\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus96 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp96\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp96\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus96 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp96\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp96\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus96 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp96\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp96\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus96 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp96\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp96\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus96 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp96\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp96\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save96\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 97:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus97 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk97\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk97\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus97 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk97\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk97\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus97 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef97\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef97\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus97 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef97\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef97\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus97 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc97\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc97\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus97 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev97\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev97\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus97 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp97\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp97\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus97 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp97\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp97\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus97 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp97\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp97\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus97 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp97\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp97\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus97 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp97\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp97\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save97\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 98:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus98 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk98\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk98\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus98 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk98\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk98\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus98 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef98\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef98\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus98 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef98\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef98\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus98 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc98\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc98\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus98 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev98\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev98\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus98 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp98\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp98\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus98 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp98\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp98\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus98 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp98\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp98\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus98 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp98\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp98\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus98 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp98\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp98\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save98\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 99:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus99 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk99\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk99\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus99 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk99\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk99\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus99 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef99\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef99\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus99 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef99\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef99\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus99 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc99\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc99\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus99 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev99\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev99\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus99 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp99\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp99\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus99 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp99\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp99\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus99 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp99\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp99\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus99 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp99\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp99\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus99 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp99\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp99\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save99\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 100:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus100 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk100\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk100\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus100 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk100\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk100\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus100 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef100\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef100\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus100 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef100\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef100\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus100 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc100\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc100\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus100 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev100\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev100\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus100 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp100\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp100\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus100 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp100\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp100\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus100 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp100\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp100\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus100 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp100\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp100\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus100 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp100\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp100\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save100\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 101:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus101 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk101\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk101\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus101 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk101\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk101\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus101 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef101\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef101\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus101 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef101\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef101\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus101 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc101\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc101\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus101 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev101\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev101\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus101 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp101\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp101\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus101 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp101\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp101\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus101 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp101\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp101\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus101 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp101\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp101\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus101 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp101\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp101\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save101\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 102:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus102 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk102\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk102\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus102 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk102\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk102\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus102 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef102\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef102\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus102 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef102\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef102\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus102 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc102\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc102\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus102 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev102\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev102\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus102 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp102\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp102\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus102 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp102\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp102\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus102 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp102\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp102\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus102 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp102\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp102\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus102 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp102\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp102\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save102\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 103:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus103 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk103\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk103\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus103 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk103\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk103\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus103 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef103\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef103\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus103 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef103\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef103\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus103 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc103\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc103\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus103 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev103\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev103\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus103 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp103\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp103\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus103 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp103\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp103\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus103 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp103\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp103\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus103 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp103\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp103\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus103 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp103\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp103\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save103\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 104:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus104 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk104\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk104\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus104 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk104\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk104\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus104 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef104\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef104\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus104 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef104\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef104\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus104 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc104\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc104\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus104 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev104\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev104\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus104 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp104\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp104\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus104 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp104\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp104\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus104 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp104\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp104\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus104 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp104\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp104\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus104 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp104\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp104\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save104\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 105:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus105 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk105\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk105\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus105 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk105\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk105\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus105 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef105\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef105\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus105 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef105\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef105\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus105 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc105\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc105\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus105 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev105\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev105\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus105 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp105\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp105\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus105 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp105\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp105\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus105 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp105\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp105\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus105 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp105\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp105\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus105 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp105\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp105\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save105\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 106:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus106 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk106\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk106\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus106 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk106\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk106\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus106 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef106\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef106\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus106 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef106\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef106\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus106 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc106\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc106\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus106 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev106\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev106\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus106 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp106\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp106\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus106 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp106\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp106\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus106 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp106\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp106\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus106 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp106\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp106\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus106 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp106\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp106\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save106\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 107:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus107 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk107\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk107\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus107 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk107\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk107\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus107 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef107\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef107\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus107 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef107\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef107\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus107 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc107\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc107\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus107 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev107\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev107\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus107 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp107\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp107\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus107 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp107\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp107\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus107 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp107\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp107\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus107 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp107\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp107\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus107 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp107\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp107\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save107\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 108:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus108 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk108\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk108\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus108 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk108\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk108\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus108 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef108\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef108\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus108 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef108\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef108\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus108 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc108\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc108\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus108 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev108\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev108\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus108 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp108\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp108\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus108 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp108\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp108\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus108 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp108\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp108\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus108 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp108\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp108\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus108 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp108\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp108\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save108\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 109:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus109 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk109\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk109\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus109 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk109\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk109\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus109 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef109\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef109\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus109 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef109\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef109\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus109 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc109\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc109\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus109 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev109\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev109\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus109 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp109\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp109\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus109 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp109\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp109\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus109 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp109\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp109\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus109 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp109\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp109\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus109 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp109\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp109\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save109\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 110:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus110 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk110\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk110\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus110 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk110\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk110\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus110 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef110\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef110\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus110 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef110\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef110\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus110 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc110\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc110\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus110 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev110\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev110\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus110 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp110\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp110\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus110 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp110\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp110\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus110 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp110\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp110\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus110 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp110\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp110\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus110 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp110\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp110\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save110\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 111:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus111 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk111\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk111\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus111 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk111\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk111\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus111 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef111\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef111\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus111 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef111\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef111\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus111 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc111\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc111\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus111 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev111\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev111\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus111 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp111\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp111\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus111 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp111\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp111\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus111 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp111\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp111\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus111 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp111\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp111\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus111 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp111\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp111\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save111\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 112:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus112 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk112\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk112\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus112 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk112\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk112\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus112 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef112\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef112\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus112 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef112\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef112\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus112 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc112\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc112\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus112 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev112\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev112\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus112 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp112\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp112\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus112 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp112\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp112\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus112 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp112\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp112\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus112 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp112\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp112\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus112 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp112\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp112\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save112\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 113:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus113 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk113\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk113\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus113 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk113\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk113\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus113 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef113\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef113\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus113 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef113\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef113\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus113 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc113\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc113\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus113 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev113\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev113\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus113 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp113\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp113\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus113 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp113\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp113\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus113 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp113\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp113\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus113 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp113\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp113\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus113 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp113\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp113\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save113\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 114:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus114 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk114\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk114\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus114 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk114\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk114\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus114 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef114\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef114\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus114 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef114\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef114\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus114 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc114\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc114\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus114 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev114\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev114\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus114 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp114\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp114\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus114 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp114\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp114\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus114 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp114\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp114\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus114 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp114\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp114\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus114 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp114\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp114\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save114\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 115:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus115 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk115\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk115\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus115 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk115\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk115\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus115 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef115\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef115\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus115 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef115\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef115\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus115 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc115\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc115\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus115 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev115\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev115\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus115 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp115\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp115\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus115 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp115\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp115\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus115 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp115\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp115\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus115 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp115\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp115\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus115 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp115\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp115\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save115\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 116:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus116 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk116\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk116\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus116 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk116\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk116\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus116 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef116\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef116\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus116 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef116\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef116\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus116 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc116\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc116\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus116 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev116\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev116\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus116 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp116\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp116\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus116 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp116\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp116\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus116 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp116\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp116\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus116 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp116\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp116\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus116 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp116\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp116\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save116\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 117:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus117 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk117\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk117\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus117 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk117\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk117\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus117 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef117\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef117\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus117 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef117\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef117\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus117 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc117\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc117\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus117 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev117\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev117\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus117 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp117\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp117\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus117 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp117\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp117\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus117 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp117\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp117\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus117 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp117\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp117\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus117 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp117\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp117\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save117\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
			case 118:
				tb.append("<br><font color=\"FF6600\">Edit the stats here.</font><br1>");
				tb.append("<table width=\"300\" height=\"20\">");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Patk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patkplus118 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatk118\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatk118\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Matk</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matkplus118 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatk118\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatk118\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Pdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.pdefplus118 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpdef118\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempdef118\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mdef</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mdefplus118 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmdef118\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmdef118\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Acc</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.accplus118 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addacc118\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remacc118\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Eva</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.evasionplus118 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addev118\">+1</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remev118\">-1</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">AtkSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.patksplus118 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addpatksp118\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance rempatksp118\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">CastSp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.matksplus118 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmatksp118\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmatksp118\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Cp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.cpplus118 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addcp118\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remcp118\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Hp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.hpplus118 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addhp118\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remhp118\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("<tr>");
				tb.append("<td align=\"center\" width=\"75\">Mp</td>");
				tb.append("<td align=\"center\" width=\"75\">+" + BalancerMain.mpplus118 + "</td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance addmp118\">+100</a></td>");
				tb.append("<td align=\"center\" width=\"75\"><a action=\"bypass Balance remmp118\">-100</a></td>");
				tb.append("</tr>");
				
				tb.append("</table><a action=\"bypass -h admin_balance\">Back</a><br>");
				tb.append("<a action=\"bypass Balance save118\">Save Stats</a>");
				tb.append("</center>");
				tb.append("</body></html>");
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			break;
		}
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}

}