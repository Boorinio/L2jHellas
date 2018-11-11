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
package com.l2jhellas.gameserver.model.actor.instance;

import com.l2jhellas.gameserver.ai.L2CharacterAI;
import com.l2jhellas.gameserver.ai.L2NpcWalkerAI;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;

/**
 * This class manages some npcs can walk in the city. <br>
 * It inherits all methods from L2NpcInstance.
 * 
 * @author Rayan
 */
public class L2NpcWalkerInstance extends L2Npc
{

	public L2NpcWalkerInstance(int objectId, L2NpcTemplate template)
	{
		super(objectId, template);
		setAI(new L2NpcWalkerAI(new L2NpcWalkerAIAccessor()));
	}

	@Override
	public void setAI(L2CharacterAI newAI)
	{
		if (_ai == null)
			super.setAI(newAI);
	}
	
	@Override
	public void reduceCurrentHp(double i, L2Character attacker, boolean awake)
	{
	}

	@Override
	public boolean doDie(L2Character killer)
	{
		return false;
	}

	@Override
	public L2CharacterAI getAI()
	{
		return super.getAI();
	}

	protected class L2NpcWalkerAIAccessor extends L2Character.AIAccessor
	{
		/**
		 * AI can't be detached.
		 */
		@Override
		public void detachAI()
		{
		}
	}
}