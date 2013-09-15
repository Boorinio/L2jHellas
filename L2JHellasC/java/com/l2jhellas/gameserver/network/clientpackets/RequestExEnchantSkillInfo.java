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

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.datatables.sql.SkillTreeTable;
import com.l2jhellas.gameserver.model.L2EnchantSkillLearn;
import com.l2jhellas.gameserver.model.L2Skill;
import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2NpcInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.ExEnchantSkillInfo;
import com.l2jhellas.gameserver.skills.SkillTable;

/**
 * Format chdd<BR>
 * c: (id) 0xD0<BR>
 * h: (subid) 0x06<BR>
 * d: skill id<BR>
 * d: skill lvl
 * 
 * @author -Wooden-
 */
public final class RequestExEnchantSkillInfo extends L2GameClientPacket
{
	private static final String _C__D0_06_REQUESTEXENCHANTSKILLINFO = "[C] D0:06 RequestExEnchantSkillInfo";
	private int _skillId;
	private int _skillLvl;

	@Override
	protected void readImpl()
	{
		_skillId = readD();
		_skillLvl = readD();
	}

	@Override
	protected void runImpl()
	{
		L2PcInstance activeChar = getClient().getActiveChar();

		if (activeChar == null)
			return;

		if (activeChar.getLevel() < 76)
			return;

		L2NpcInstance trainer = activeChar.getLastFolkNPC();

		if (((trainer == null) || !activeChar.isInsideRadius(trainer, L2Npc.INTERACTION_DISTANCE, false, false)) && !activeChar.isGM())
			return;

		L2Skill skill = SkillTable.getInstance().getInfo(_skillId, _skillLvl);

		boolean canteach = false;

		if ((skill == null) || (skill.getId() != _skillId))
		{
			_log.warning("enchant skill id " + _skillId + " level " + _skillLvl + " is undefined. aquireEnchantSkillInfo failed. report this to http://l2jhellas.eu/ forum.");
			activeChar.sendMessage("This skill doesn't yet have enchant info in Datapack");
			return;
		}

		if (!trainer.getTemplate().canTeach(activeChar.getClassId()))
			return; // cheater

		L2EnchantSkillLearn[] skills = SkillTreeTable.getInstance().getAvailableEnchantSkills(activeChar);

		for (L2EnchantSkillLearn s : skills)
		{
			if (s.getId() == _skillId && s.getLevel() == _skillLvl)
			{
				canteach = true;
				break;
			}
		}

		if (!canteach)
			return; // cheater

		int requiredSp = SkillTreeTable.getInstance().getSkillSpCost(activeChar, skill);
		int requiredExp = SkillTreeTable.getInstance().getSkillExpCost(activeChar, skill);
		byte rate = SkillTreeTable.getInstance().getSkillRate(activeChar, skill);
		ExEnchantSkillInfo asi = new ExEnchantSkillInfo(skill.getId(), skill.getLevel(), requiredSp, requiredExp, rate);

		if (Config.ES_SP_BOOK_NEEDED && (skill.getLevel() == 101 || skill.getLevel() == 141)) // only first lvl requires book
		{
			int spbId = 6622;
			asi.addRequirement(4, spbId, 1, 0);
		}
		sendPacket(asi);
	}

	@Override
	public String getType()
	{
		return _C__D0_06_REQUESTEXENCHANTSKILLINFO;
	}
}