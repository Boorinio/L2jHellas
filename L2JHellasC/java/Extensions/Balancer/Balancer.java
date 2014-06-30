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
package Extensions.Balancer;

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
			NpcHtmlMessage htm = new NpcHtmlMessage(0);
			htm.setFile("./data/html/admin/balance/main.htm");
			activeChar.sendPacket(htm);
		}
		return true;
	}

	public static void sendBalanceWindow(int classId, L2PcInstance p)
	{
		NpcHtmlMessage htm = new NpcHtmlMessage(0);
		htm.setFile("./data/html/admin/balance/balance.htm");
		
		htm.replace("%classId%", classId + "");
		htm.replace("%Patk%", BalanceLoad.loadPAtk(classId) + "");
		htm.replace("%Matk%", BalanceLoad.loadMAtk(classId) + "");
		htm.replace("%Pdef%", BalanceLoad.loadPDef(classId) + "");
		htm.replace("%Mdef%", BalanceLoad.loadMDef(classId) + "");
		htm.replace("%Acc%", BalanceLoad.loadAccuracy(classId) + "");
		htm.replace("%Eva%", BalanceLoad.loadEvasion(classId) + "");
		htm.replace("%AtkSp%", BalanceLoad.loadPAtkSpd(classId) + "");
		htm.replace("%CastSp%", BalanceLoad.loadMAtkSpd(classId) + "");
		htm.replace("%Cp%", BalanceLoad.loadCP(classId) + "");
		htm.replace("%Hp%", BalanceLoad.loadHP(classId) + "");
		htm.replace("%Mp%", BalanceLoad.loadMP(classId) + "");
		htm.replace("%Speed%", BalanceLoad.loadSpeed(classId) + "");
		htm.replace("%Critical%", BalanceLoad.loadCritical(classId) + "");
		
		p.sendPacket(htm);
	}

	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}