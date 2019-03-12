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
package com.l2jhellas.gameserver.network.serverpackets;

import com.l2jhellas.gameserver.model.L2Clan;
import com.l2jhellas.gameserver.model.L2ClanMember;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

/**
 * sample
 * 0000: 68
 * b1010000
 * 48 00 61 00 6d 00 62 00 75 00 72 00 67 00 00 00 H.a.m.b.u.r.g...
 * 43 00 61 00 6c 00 61 00 64 00 6f 00 6e 00 00 00 C.a.l.a.d.o.n...
 * 00000000 crestid | not used (nuocnam)
 * 00000000 00000000 00000000 00000000
 * 22000000 00000000 00000000
 * 00000000 ally id
 * 00 00 ally name
 * 00000000 ally crrest id
 * 02000000
 * 6c 00 69 00 74 00 68 00 69 00 75 00 6d 00 31 00 00 00 l.i.t.h.i.u.m...
 * 0d000000 level
 * 12000000 class id
 * 00000000
 * 01000000 offline 1=true
 * 00000000
 * 45 00 6c 00 61 00 6e 00 61 00 00 00 E.l.a.n.a...
 * 08000000
 * 19000000
 * 01000000
 * 01000000
 * 00000000
 * format dSS dddddddddSdd d (Sddddd)
 * dddSS dddddddddSdd d (Sdddddd)
 */
public class PledgeShowMemberListAll extends L2GameServerPacket
{
	// private static Logger _log = Logger.getLogger(PledgeShowMemberListAll.class.getName());
	private static final String _S__68_PLEDGESHOWMEMBERLISTALL = "[S] 53 PledgeShowMemberListAll";
	
	private final L2Clan _clan;
	private int _pledgeType;
	private final String _pledgeName;
	
	public PledgeShowMemberListAll(L2Clan clan, int pledgeType)
	{
		_clan = clan;
		_pledgeType = pledgeType;
		
		if (_pledgeType == 0) 
		{
			_pledgeName = clan.getName();
		}
		else if (_clan.getSubPledge(_pledgeType) != null)
		{
			_pledgeName = _clan.getSubPledge(_pledgeType).getName();
		}
		else
		{
			_pledgeName = "";
		}
	}

	@Override
	protected final void writeImpl()
	{
		writeC(0x53);
		
		writeD((_pledgeType == 0) ? 0 : 1);
		writeD(_clan.getClanId());
		writeD(_pledgeType);
		writeS(_pledgeName);;
		writeS(_clan.getSubPledge(_pledgeType) != null ? _clan.getSubPledge(_pledgeType).getLeaderName() :_clan.getLeaderName());
			
		writeD(_clan.getCrestId());
		writeD(_clan.getLevel());
		writeD(_clan.hasCastle());
		writeD(_clan.hasHideout());
		writeD(_clan.getRank());
		writeD(_clan.getReputationScore());
		writeD(0);
		writeD(0);
		writeD(_clan.getAllyId());
		writeS(_clan.getAllyName());
		writeD(_clan.getAllyCrestId());
		writeD(_clan.isAtWar() ? 1 : 0);
		writeD(_clan.getSubPledgeMembersCount(_pledgeType));
		
		for (L2ClanMember m : _clan.getMembers())
		{
			if (m.getPledgeType() != _pledgeType)
				continue;
			
			writeS(m.getName());
			writeD(m.getLevel());
			writeD(m.getClassId());
			
			L2PcInstance player = m.getPlayerInstance();
			
			if (player != null)
			{
				writeD(player.getAppearance().getSex().ordinal()); 
				writeD(player.getRace().ordinal());
			}
			else
			{
				writeD(0x01); 
				writeD(0x01);
			}
			
			writeD((m.isOnline()) ? m.getObjectId() : 0);
			writeD((m.getSponsor() != 0 || m.getApprentice() != 0) ? 1 : 0);
		}
	}

	@Override
	public String getType()
	{
		return _S__68_PLEDGESHOWMEMBERLISTALL;
	}
}