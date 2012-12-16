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

import javolution.util.FastMap;

import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author Masterio
 */
public class RankPvpSystemPointsReward
{
	/** Important for reward integrity */
	private L2PcInstance _player = null;

	private long _rankPoints = 0;
	private int _rankRewardsCount = 0;
	
	public RankPvpSystemPointsReward(L2PcInstance player)
	{
		// set handler:
		_player = player;
		
		// get total rank points:
		setRankPoints(PvpTable.getInstance().getPvpStats(_player.getObjectId()).getTotalRankPoints());
		
		// get reward list length:
		setRankRewardsCount(CharacterRankRewardTable.getInstance().getRewardsCount(player.getObjectId(), _rankPoints));
	}
	
	public void addRankRewardsToInventory()
	{

		// get reward list:
		FastMap<Integer, CharacterRankReward> rewardsAwarded = CharacterRankRewardTable.getInstance().getRewardsList(_player.getObjectId(), getRankPoints());
		
		// queries:
		String[] queries = new String[rewardsAwarded.size() + 1];
		
		// give rewards and update information about that in singleton:
		int i = 0;
		for (FastMap.Entry<Integer, CharacterRankReward> e = rewardsAwarded.head(), end = rewardsAwarded.tail(); (e = e.getNext()) != end;)
		{
			
			_player.addItem("RankReward", e.getValue().getItemId(), e.getValue().getItemAmount(), null, true);
			CharacterRankRewardTable.getInstance().addReward(e.getValue());
			
			queries[i] = "INSERT INTO custom_pvp_system_characters_rank_rewards (charId, reward_id) values (" + getPlayer().getObjectId() + ", " + e.getValue().getRewardId() + ")";
			i++;
		}
		
		// update database:
		CharacterRankRewardTable.getInstance().insertCharacterRewardListIntoDB(queries);
		
	}
	
	/**
	 * @return the _player
	 */
	public L2PcInstance getPlayer()
	{
		return _player;
	}

	/**
	 * @param _player
	 *        the _player to set
	 */
	public void setPlayer(L2PcInstance _player)
	{
		this._player = _player;
	}

	/**
	 * @return the _rankPoints
	 */
	public long getRankPoints()
	{
		return _rankPoints;
	}

	/**
	 * @param _rankPoints
	 *        the _rankPoints to set
	 */
	public void setRankPoints(long _rankPoints)
	{
		this._rankPoints = _rankPoints;
	}

	/**
	 * @return the _rankRewardsCount
	 */
	public int getRankRewardsCount()
	{
		return _rankRewardsCount;
	}

	/**
	 * @param _rankRewardsCount
	 *        the _rankRewardsCount to set
	 */
	public void setRankRewardsCount(int _rankRewardsCount)
	{
		this._rankRewardsCount = _rankRewardsCount;
	}
}