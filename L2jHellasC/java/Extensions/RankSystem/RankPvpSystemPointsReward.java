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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.l2jhellas.L2DatabaseFactory;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author Masterio
 */
public class RankPvpSystemPointsReward
{
	/** Important for reward integrity */
	public L2PcInstance _player = null;
	public int _currentRankPoints = 0;
	public int _rankRewardsCount = 0;
	
	public RankPvpSystemPointsReward(L2PcInstance player)
	{
		_player = player;
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rset = null;
		
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			statement = con.prepareStatement("CALL CPS_get_rank_reward_count(?)");
			statement.setInt(1, player.getObjectId());
			rset = statement.executeQuery();
			
			while (rset.next())
			{
				_rankRewardsCount = rset.getInt("rank_reward_count");
				_currentRankPoints = rset.getInt("current_rank_points");
				break;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				if (statement != null)
				{
					statement.close();
					statement = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void getRankPointsRewardToInventory()
	{
		
		if (_player.getObjectId() > 0)
		{
			
			Connection con = null;
			PreparedStatement statement = null;
			ResultSet rset = null;
			
			try
			{
				con = L2DatabaseFactory.getInstance().getConnection();
				// get item list:
				statement = con.prepareStatement("CALL CPS_get_rank_reward_list(?,?)");
				statement.setInt(1, _player.getObjectId());
				statement.setInt(2, _currentRankPoints);
				rset = statement.executeQuery();
				
				while (rset.next())
				{
					_player.addItem("RankReward", rset.getInt("item_id"), rset.getInt("item_amount"), null, true);
				}
				
				// save information about given items:
				statement = con.prepareStatement("CALL CPS_add_rank_reward(?,?)");
				statement.setInt(1, _player.getObjectId());
				statement.setInt(2, _currentRankPoints);
				rset = statement.executeQuery();
				
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if (rset != null)
					{
						rset.close();
						rset = null;
					}
					if (statement != null)
					{
						statement.close();
						statement = null;
					}
					if (con != null)
					{
						con.close();
						con = null;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}