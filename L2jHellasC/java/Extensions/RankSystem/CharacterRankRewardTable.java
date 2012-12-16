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
import java.sql.Statement;
import java.util.Calendar;
import java.util.logging.Logger;

import javolution.util.FastMap;

import com.l2jhellas.L2DatabaseFactory;

/**
 * @author Masterio
 */
public class CharacterRankRewardTable
{
	
	private static final Logger _log = Logger.getLogger(CharacterRankRewardTable.class.getName());
	
	private static CharacterRankRewardTable _instance = null;
	
	/** <id, CharacterRankReward> contains already taken rewards by players */
	private FastMap<Integer, CharacterRankReward> _characterRankRewardTable = new FastMap<Integer, CharacterRankReward>();
	
	private CharacterRankRewardTable()
	{
		Calendar c = Calendar.getInstance();
		long startTime = c.getTimeInMillis();
		
		load();
		
		c = Calendar.getInstance();
		long endTime = c.getTimeInMillis();
		System.out.println("CharacterRankRewardTable loaded " + (this.getCharacterRankRewardTable().size()) + " objects in " + (endTime - startTime) + " ms.");

	}
	
	public static CharacterRankRewardTable getInstance()
	{
		if (_instance == null)
		{
			_instance = new CharacterRankRewardTable();
		}
		
		return _instance;
	}

	/**
	 * @return the _characterRankRewardTable
	 */
	public FastMap<Integer, CharacterRankReward> getCharacterRankRewardTable()
	{
		return _characterRankRewardTable;
	}

	/**
	 * @param _characterRankRewardTable
	 *        the _characterRankRewardTable to set
	 */
	public void setCharacterRankRewardTable(FastMap<Integer, CharacterRankReward> _characterRankRewardTable)
	{
		this._characterRankRewardTable = _characterRankRewardTable;
	}
	
	public FastMap<Integer, CharacterRankReward> getRewardsList(int characterId, long characterRankPoints)
	{
		
		// 1. Get list of rewards taken by characterId:
		FastMap<Integer, Integer> playerRewardsTaken = new FastMap<Integer, Integer>();
		
		for (FastMap.Entry<Integer, CharacterRankReward> e = getCharacterRankRewardTable().head(), end = getCharacterRankRewardTable().tail(); (e = e.getNext()) != end;)
		{
			if (e != null)
			{
				
				if (e.getValue().getCharacterId() == characterId)
				{
					
					playerRewardsTaken.put(playerRewardsTaken.size() + 1, e.getValue().getRewardId());
				}
				
			}
		}

		// 2. Get list of rewards what will be awarded:
		FastMap<Integer, CharacterRankReward> playerRewardsAwarded = new FastMap<Integer, CharacterRankReward>();
		
		for (FastMap.Entry<Integer, RankReward> e = RankRewardTable.getInstance().getRankRewardTable().head(), end = RankRewardTable.getInstance().getRankRewardTable().tail(); (e = e.getNext()) != end;)
		{
			if (e != null)
			{
				
				// if player haven't this reward yet, add it to list:
				if (!playerRewardsTaken.containsValue(e.getValue().getRewardId()) && e.getValue().getMinRankPoints() <= characterRankPoints)
				{
					CharacterRankReward crr = new CharacterRankReward();
					crr.setCharacterId(characterId);
					crr.setRewardId(e.getValue().getRewardId());
					crr.setItemId(e.getValue().getItemId());
					crr.setItemAmount(e.getValue().getItemAmount());
					crr.setMinRankPoints(e.getValue().getMinRankPoints());
					
					playerRewardsAwarded.put(playerRewardsAwarded.size() + 1, crr);
				}
				
			}
		}
		
		return playerRewardsAwarded;
	}
	
	@SuppressWarnings("unused")
	public int getRewardsCount(int characterId, long characterRankPoints)
	{
		
		// 1. Get list of rewards by characterId:
		FastMap<Integer, Integer> playerRewardsTaken = new FastMap<Integer, Integer>();
		
		for (FastMap.Entry<Integer, CharacterRankReward> e = getCharacterRankRewardTable().head(), end = getCharacterRankRewardTable().tail(); (e = e.getNext()) != end;)
		{
			if (e != null)
			{
				
				if (e.getValue().getCharacterId() == characterId)
				{
					playerRewardsTaken.put(playerRewardsTaken.size() + 1, e.getValue().getRewardId());
				}
				
			}
		}

		// 2. Get list of rewards what will be awarded:
		int r = 0;
		FastMap<Integer, RankReward> playerRewardsAwarded = new FastMap<Integer, RankReward>();
		
		for (FastMap.Entry<Integer, RankReward> e = RankRewardTable.getInstance().getRankRewardTable().head(), end = RankRewardTable.getInstance().getRankRewardTable().tail(); (e = e.getNext()) != end;)
		{
			if (e != null)
			{
				
				if (!playerRewardsTaken.containsValue(e.getValue().getRewardId()) && e.getValue().getMinRankPoints() <= characterRankPoints)
				{
					r++;
				}
				
			}
		}
		
		return r;
	}
	
	public void addReward(CharacterRankReward characterRankReward)
	{
		
		getCharacterRankRewardTable().put(getCharacterRankRewardTable().size() + 1, characterRankReward);
		
	}
	
	private void load()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT * FROM custom_pvp_system_characters_rank_rewards");
			
			ResultSet rset = statement.executeQuery();
			
			while (rset.next())
			{
				CharacterRankReward crr = new CharacterRankReward();
				crr.setCharacterId(rset.getInt("charId"));
				crr.setRewardId(rset.getInt("reward_id"));
				
				_characterRankRewardTable.put(_characterRankRewardTable.size() + 1, crr);
			}

			rset.close();
			statement.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	public void insertCharacterRewardListIntoDB(String[] queries)
	{
		Connection con = null;
		Statement statement = null;
		
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			statement = con.createStatement();
			
			for (String query : queries)
			{
				statement.addBatch(query);
			}
			statement.executeBatch();

			statement.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
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
	
	public void printTableData()
	{
		_log.info("=========================================================");
		int i = 1;
		for (FastMap.Entry<Integer, CharacterRankReward> e = getCharacterRankRewardTable().head(), end = getCharacterRankRewardTable().tail(); (e = e.getNext()) != end;)
		{
			if (e != null)
			{
				_log.info("[" + i + "]" + " characterId:" + e.getValue().getCharacterId() + ", rewardId:" + e.getValue().getRewardId());
			}
			i++;
		}
		_log.info("=========================================================");
	}
}