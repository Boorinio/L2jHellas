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
package Extensions.AchievmentsEngine.conditions;

import Extensions.AchievmentsEngine.base.Condition;

import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

import java.util.StringTokenizer;

public class ItemsCount extends Condition
{
	public ItemsCount(Object value)
	{
		super(value);
		setName("Items Count");
	}

	@Override
	public boolean meetConditionRequirements(L2PcInstance player)
	{
		if (getValue() == null)
			return false;
		else
		{
			String s = getValue().toString();
			StringTokenizer st = new StringTokenizer(s, ",");
			int id = 0;
			int ammount = 0;
			
			try
			{
				id = Integer.parseInt(st.nextToken());
				ammount = Integer.parseInt(st.nextToken());
				if (player.getInventory().getInventoryItemCount(id, 0) >= ammount)
					return true;
			}
			catch (NumberFormatException nfe)
			{
				nfe.printStackTrace();
			}
		}
		return false;
	}
}