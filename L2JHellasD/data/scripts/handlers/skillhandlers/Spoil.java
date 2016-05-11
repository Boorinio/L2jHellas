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
package handlers.skillhandlers;

import com.l2jhellas.gameserver.ai.CtrlEvent;
import com.l2jhellas.gameserver.handler.ISkillHandler;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.L2Skill;
import com.l2jhellas.gameserver.model.L2SkillType;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;
import com.l2jhellas.gameserver.skills.Formulas;

/**
 * @author AbsolutePower
 */
public class Spoil implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.SPOIL
	};

	@Override
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] t)
	{
		if (!(activeChar instanceof L2PcInstance))
			return;
		
		if (t == null)
		{
			activeChar.getActingPlayer().sendMessage("No target found");
			return;
		}
		
		for (L2Object spoiledtarget : t)
		{
			boolean isMonster = spoiledtarget instanceof L2MonsterInstance;

			if(!isMonster)
				continue;
			
			final L2MonsterInstance target = (L2MonsterInstance) spoiledtarget;
			boolean isDead = target.isDead();
			
			if (isDead)
				continue;
			
			boolean alreadySpoiled = target.getIsSpoiledBy() != 0;
			
			if (alreadySpoiled)
			{
				activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.ALREADY_SPOILED));
				continue;
			}
			
			if (Formulas.getInstance().calcMagicSuccess(activeChar, (L2Character) spoiledtarget, skill))
			{
				target.setIsSpoiledBy(activeChar.getObjectId());
				activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.SPOIL_SUCCESS));
			}
			else
				activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.S1_RESISTED_YOUR_S2).addCharName(target).addSkillName(skill.getId()));
			
			target.getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, activeChar);
		}
	}

	@Override
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}