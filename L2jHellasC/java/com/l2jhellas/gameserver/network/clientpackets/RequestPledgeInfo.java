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
package com.l2jhellas.gameserver.network.clientpackets;

import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.datatables.sql.ClanTable;
import com.l2jhellas.gameserver.model.L2Clan;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.PledgeInfo;

public final class RequestPledgeInfo extends L2GameClientPacket
{
	private static Logger _log = Logger.getLogger(RequestPledgeInfo.class.getName());
	private static final String _C__66_REQUESTPLEDGEINFO = "[C] 66 RequestPledgeInfo";

	private int _clanId;

	@Override
	protected void readImpl()
	{
		_clanId = readD();
	}

	@Override
	protected void runImpl()
	{
		if (Config.DEBUG)
			_log.fine("infos for clan " + _clanId + " requested");

		L2PcInstance activeChar = getClient().getActiveChar();
		L2Clan clan = ClanTable.getInstance().getClan(_clanId);
		if (clan == null)
		{
			_log.warning("Clan data for clanId " + _clanId + " is missing");
			return; // we have no clan data ?!? should not happen
		}

		PledgeInfo pc = new PledgeInfo(clan);
		if (activeChar != null)
		{
			activeChar.sendPacket(pc);

			/*
			 * if (clan.getClanId() == activeChar.getClanId()) {
			 * activeChar.sendPacket(new PledgeShowMemberListDeleteAll());
			 * PledgeShowMemberListAll pm = new PledgeShowMemberListAll(clan,
			 * activeChar); activeChar.sendPacket(pm); }
			 */
		}
	}

	@Override
	public String getType()
	{
		return _C__66_REQUESTPLEDGEINFO;
	}
}