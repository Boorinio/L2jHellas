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

import com.l2jhellas.gameserver.instancemanager.CastleManager;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2StaticObjectInstance;
import com.l2jhellas.gameserver.network.serverpackets.ActionFailed;
import com.l2jhellas.gameserver.network.serverpackets.ChairSit;

public final class ChangeWaitType2 extends L2GameClientPacket
{
	private static final String _C__1D_CHANGEWAITTYPE2 = "[C] 1D ChangeWaitType2";
	private boolean _typeStand;

	@Override
	protected void readImpl()
	{
		_typeStand = (readD() == 1);
	}

	@Override
	protected void runImpl()
	{
		L2PcInstance player = getClient().getActiveChar();
		L2Object target = player.getTarget();
		if (getClient() != null && player != null)
		{
			if (player.isOutOfControl())
			{
				player.sendPacket(ActionFailed.STATIC_PACKET);
				return;
			}

			if (player.getMountType() != 0) // prevent sit/stand if you riding
				return;

			if ((target != null)
/** @formatter:off */
					&& !player.isSitting()
					&& target instanceof L2StaticObjectInstance
					&& (((L2StaticObjectInstance)target).getType() == 1)
					&& (CastleManager.getInstance().getCastle(target) != null)
					&& player.isInsideRadius(target, L2StaticObjectInstance.INTERACTION_DISTANCE, false, false)
					/** @formatter:on */
			)
			{
				ChairSit cs = new ChairSit(player, ((L2StaticObjectInstance) target).getStaticObjectId());
				player.sendPacket(cs);
				player.sitDown();
				player.broadcastPacket(cs);
			}
			if (_typeStand)
				player.standUp();
			else
				player.sitDown();
		}
	}

	@Override
	public String getType()
	{
		return _C__1D_CHANGEWAITTYPE2;
	}
}