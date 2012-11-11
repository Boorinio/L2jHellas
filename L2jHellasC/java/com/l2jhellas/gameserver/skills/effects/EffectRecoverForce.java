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

import com.l2jhellas.gameserver.model.L2Effect;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.EtcStatusUpdate;
import com.l2jhellas.gameserver.skills.Env;


public class EffectRecoverForce extends L2Effect
{
    public EffectRecoverForce(Env env, EffectTemplate template)
    {
        super(env, template);
    }

    @Override
    public EffectType getEffectType()
    {
        return EffectType.SIGNET_EFFECT;
    }

    @Override
    public boolean onActionTime()
    {
        if (getEffected() instanceof L2PcInstance)
        {
            EffectCharge effect = (EffectCharge)getEffected().getFirstEffect(EffectType.CHARGE);
            if (effect != null)
            {
                effect.addNumCharges(1);
                getEffected().sendPacket(new EtcStatusUpdate((L2PcInstance)getEffected()));
            }
        }
        return true;
    }
}
