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
package Extensions.RankSystem;

import javolution.util.FastList;
import javolution.util.FastMap;

import com.l2jhellas.ExternalConfig;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author Masterio
 */
public class RankPvpSystemZoneChecker
{
	/**
	 * Returns true if character is in allowed zone.
	 * 
	 * @param activeChar
	 * @return
	 */
	public static final boolean isInPvpAllowedZone(L2PcInstance activeChar)
	{
		for (FastList.Node<Integer> n = ExternalConfig.CUSTOM_PVP_ALLOWED_ZONES_IDS.head(), end = ExternalConfig.CUSTOM_PVP_ALLOWED_ZONES_IDS.tail(); (n = n.getNext()) != end;)
		{
			if (activeChar.isInsideZone(n.getValue().byteValue()))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns true if character is in restricted zone.
	 * 
	 * @param activeChar
	 * @return
	 */
	public static final boolean isInPvpRestrictedZone(L2PcInstance activeChar)
	{
		for (FastList.Node<Integer> n = ExternalConfig.CUSTOM_PVP_RESTRICTED_ZONES_IDS.head(), end = ExternalConfig.CUSTOM_PVP_RESTRICTED_ZONES_IDS.tail(); (n = n.getNext()) != end;)
		{
			if (activeChar.isInsideZone(n.getValue().byteValue()))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns true if character is in restricted zone for death manager.
	 * 
	 * @param activeChar
	 * @return
	 */
	public static final boolean isInDMRestrictedZone(L2PcInstance activeChar)
	{
		for (FastList.Node<Integer> n = ExternalConfig.CUSTOM_PVP_DEATH_MANAGER_RESTRICTED_ZONES_IDS.head(), end = ExternalConfig.CUSTOM_PVP_DEATH_MANAGER_RESTRICTED_ZONES_IDS.tail(); (n = n.getNext()) != end;)
		{
			if (activeChar.isInsideZone(n.getValue().byteValue()))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns true if character is in Bonus Ratio zone.
	 * 
	 * @param player
	 * @return
	 */
	public static final double getZoneBonusRatio(L2PcInstance player)
	{
		for (FastMap.Entry<Integer, Double> e = ExternalConfig.CUSTOM_PVP_RANK_POINTS_BONUS_ZONES.head(), end = ExternalConfig.CUSTOM_PVP_RANK_POINTS_BONUS_ZONES.tail(); (e = e.getNext()) != end;)
		{
			if (player.isInsideZone(e.getKey().byteValue()))
			{
				return e.getValue();
			}
		}
		return 1.0;
	}
}
