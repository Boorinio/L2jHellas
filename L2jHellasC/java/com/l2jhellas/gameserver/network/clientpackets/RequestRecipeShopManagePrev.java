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

import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.ActionFailed;
import com.l2jhellas.gameserver.network.serverpackets.RecipeShopSellList;

public final class RequestRecipeShopManagePrev extends L2GameClientPacket
{
	private static final String _C__B7_RequestRecipeShopPrev = "[C] b7 RequestRecipeShopPrev";

	@Override
	protected void readImpl()
	{
		// trigger
	}

	@Override
	protected void runImpl()
	{
		L2PcInstance player = getClient().getActiveChar();
		if (player == null || player.getTarget() == null)
			return;

		// Player shouldn't be able to set stores if he/she is alike dead (dead or fake death)
		if (player.isAlikeDead())
		{
			sendPacket(new ActionFailed());
			return;
		}

		if (!(player.getTarget() instanceof L2PcInstance))
			return;
		L2PcInstance target = (L2PcInstance) player.getTarget();
		player.sendPacket(new RecipeShopSellList(player, target));
	}

	@Override
	public String getType()
	{
		return _C__B7_RequestRecipeShopPrev;
	}
}