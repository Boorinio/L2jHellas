/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jhellas.gameserver.handler.skillhandlers;

import com.l2jhellas.gameserver.handler.ISkillHandler;
import com.l2jhellas.gameserver.model.L2Character;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.L2Skill;
import com.l2jhellas.gameserver.model.L2SkillType;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.StatusUpdate;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;

/**
 * This class ...
 * 
 * @version $Revision: 1.1.2.2.2.1 $ $Date: 2005/03/02 15:38:36 $
 */

public class CombatPointHeal implements ISkillHandler
{
	// private static Logger _log =
	// Logger.getLogger(CombatPointHeal.class.getName());
	
	/*
	 * (non-Javadoc)
	 * @see com.l2jhellas.gameserver.handler.IItemHandler#useItem(com.l2jhellas.
	 * gameserver.model.L2PcInstance,
	 * com.l2jhellas.gameserver.model.L2ItemInstance)
	 */
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.COMBATPOINTHEAL
	};
	
	/*
	 * (non-Javadoc)
	 * @see com.l2jhellas.gameserver.handler.IItemHandler#useItem(com.l2jhellas.
	 * gameserver.model.L2PcInstance,
	 * com.l2jhellas.gameserver.model.L2ItemInstance)
	 */
	public void useSkill(L2Character actChar, L2Skill skill, L2Object[] targets)
	{
		// L2Character activeChar = actChar;
		
		L2Character target = null;
		
		for (int index = 0; index < targets.length; index++)
		{
			target = (L2Character) targets[index];
			
			double cp = skill.getPower();
			// int cLev = activeChar.getLevel();
			// hp += skill.getPower()/*+(Math.sqrt(cLev)*cLev)+cLev*/;
			SystemMessage sm = new SystemMessage(SystemMessageId.S1_CP_WILL_BE_RESTORED);
			sm.addNumber((int) cp);
			target.sendPacket(sm);
			target.setCurrentCp(cp + target.getCurrentCp());
			StatusUpdate sump = new StatusUpdate(target.getObjectId());
			sump.addAttribute(StatusUpdate.CUR_CP, (int) target.getCurrentCp());
			target.sendPacket(sump);
		}
	}
	
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}
