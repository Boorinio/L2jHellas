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
package com.l2jhellas.gameserver.handlers.skillhandlers;

import com.l2jhellas.gameserver.handler.ISkillHandler;
import com.l2jhellas.gameserver.model.L2Fishing;
import com.l2jhellas.gameserver.model.L2ItemInstance;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.L2Skill;
import com.l2jhellas.gameserver.model.L2SkillType;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.ActionFailed;
import com.l2jhellas.gameserver.templates.L2WeaponType;

public class FishingSkill implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
	L2SkillType.PUMPING, L2SkillType.REELING
	};

	@Override
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		if (activeChar == null || !(activeChar instanceof L2PcInstance))
			return;

		final L2PcInstance player = (L2PcInstance) activeChar;
	
		final boolean isReelingSkill = skill.getSkillType() == L2SkillType.REELING;
		
		final L2Fishing fish = player.GetFishCombat();
		
		if (fish == null)
		{
			player.sendPacket((isReelingSkill) ? SystemMessageId.CAN_USE_REELING_ONLY_WHILE_FISHING : SystemMessageId.CAN_USE_PUMPING_ONLY_WHILE_FISHING);
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		final L2ItemInstance fishingRod = activeChar.getActiveWeaponInstance();
		
		if (fishingRod == null || fishingRod.getItem().getItemType() != L2WeaponType.ROD)
			return;
		
		final int ssBonus = (fishingRod.getChargedFishshot()) ? 2 : 1;
		final double gradeBonus = 1 + fishingRod.getItem().getCrystalType() * 0.1;
		int damage = (int) (skill.getPower() * gradeBonus * ssBonus);
		int penalty = 0;
		
		if (skill.getLevel() - player.getSkillLevel(1315) >= 3)
		{
			penalty = 50;
			damage -= penalty;
			
			player.sendPacket(SystemMessageId.REELING_PUMPING_3_LEVELS_HIGHER_THAN_FISHING_PENALTY);
		}
		
		if (ssBonus > 1)
		{
			fishingRod.setChargedFishshot(false);
		}
		
		if (isReelingSkill)
		{
			fish.useRealing(damage, penalty);
		}
		else
		{
			fish.usePomping(damage, penalty);
		}
	}

	@Override
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}