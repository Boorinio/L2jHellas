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
package com.l2jhellas.gameserver.skills.effects;

import com.l2jhellas.gameserver.ai.CtrlIntention;
import com.l2jhellas.gameserver.model.L2Effect;
import com.l2jhellas.gameserver.network.serverpackets.MyTargetSelected;
import com.l2jhellas.gameserver.skills.Env;

/**
 * @author -Nemesiss-
 */
public class EffectTargetMe extends L2Effect
{
	public EffectTargetMe(Env env, EffectTemplate template)
	{
		super(env, template);
	}

	@Override
	public EffectType getEffectType()
	{
		return EffectType.TARGET_ME;
	}

	@Override
	public boolean onStart()
	{
		getEffected().setTarget(getEffector());
		MyTargetSelected my = new MyTargetSelected(getEffector().getObjectId(), 0);
		getEffected().sendPacket(my);
		getEffected().getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, getEffector());
		return true;
	}

	@Override
	public void onExit()
	{
		// nothing
	}

	@Override
	public boolean onActionTime()
	{
		// nothing
		return false;
	}
}