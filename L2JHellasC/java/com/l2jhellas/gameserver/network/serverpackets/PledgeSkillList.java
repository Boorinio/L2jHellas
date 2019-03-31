package com.l2jhellas.gameserver.network.serverpackets;

import com.l2jhellas.gameserver.model.L2Clan;
import com.l2jhellas.gameserver.model.L2Skill;

public class PledgeSkillList extends L2GameServerPacket
{
	private static final String _S__FE_39_PLEDGESKILLLIST = "[S] FE:39 PledgeSkillList";
	private final L2Clan _clan;
	
	public PledgeSkillList(L2Clan clan)
	{
		_clan = clan;
	}
	
	@Override
	protected void writeImpl()
	{
		L2Skill[] skills = _clan.getAllSkills();
		
		writeC(0xfe);
		writeH(0x39);
		writeD(skills.length);
		for (L2Skill sk : skills)
		{
			writeD(sk.getId());
			writeD(sk.getLevel());
		}
	}
	
	@Override
	public String getType()
	{
		return _S__FE_39_PLEDGESKILLLIST;
	}
}