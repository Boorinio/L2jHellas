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

import com.l2jhellas.gameserver.model.L2Party;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;



public class ExMPCCShowPartyMemberInfo extends L2GameServerPacket
{
	private static final String _S__FE_4A_EXMPCCSHOWPARTYMEMBERINFO = "[S] FE:4A ExMPCCShowPartyMemberInfo";
	private final L2Party _party;
	
	public ExMPCCShowPartyMemberInfo(L2Party party)
	{
		_party = party;
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0xfe);
		writeH(0x4a);
		
		writeD(_party.getMemberCount());
		
		for (L2PcInstance member : _party.getPartyMembers())
		{
			if(member==null)
				continue;
			
			writeS(member.getName());
			writeD(member.getObjectId());
			writeD(member.getClassId().getId());
		}
	}

	@Override
	public String getType()
	{
		return _S__FE_4A_EXMPCCSHOWPARTYMEMBERINFO;
	}
}