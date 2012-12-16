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
import java.util.Calendar;
import java.util.logging.Logger;

import javolution.util.FastMap;

import com.l2jhellas.ExternalConfig;
import com.l2jhellas.L2DatabaseFactory;
import com.l2jhellas.gameserver.ThreadPoolManager;

/**
 * @author Masterio
 */
public class TopTable
{
	@SuppressWarnings("unused")
	private static final Logger _log = Logger.getLogger(TopTable.class.getName());
	
	private static TopTable _instance = null;
	
	private boolean _isUpdating = false;
	
	/** <position, Field> contains top list */
	private FastMap<Integer, TopField> _topKillsTable = new FastMap<Integer, TopField>();
	/** <position, PvpStats> contains top list */
	private FastMap<Integer, TopField> _topGatherersTable = new FastMap<Integer, TopField>();
	
	private TopTable()
	{
		if (ExternalConfig.COMMUNITY_BOARD_TOP_LIST_ENABLED)
		{
			load();
			ThreadPoolManager.getInstance().scheduleGeneral(new TopTableSchedule(), ExternalConfig.TOP_TABLE_UPDATE_INTERVAL);
		}
	}
	
	public static TopTable getInstance()
	{
		if (_instance == null)
		{
			_instance = new TopTable();
		}
		
		return _instance;
	}
	
	private void load()
	{
		Calendar c = Calendar.getInstance();
		long startTime = c.getTimeInMillis();

		updateTopTable();

		c = Calendar.getInstance();
		long endTime = c.getTimeInMillis();
		System.out.println("TopTable loaded " + (getTopKillsTable().size() + getTopGatherersTable().size()) + " objects in " + (endTime - startTime) + " ms.");
	}
	
	public void updateTopTable()
	{
		
		// lock table:
		setUpdating(true);
		
		// clear tables:
		getTopKillsTable().clear();
		getTopGatherersTable().clear();
		
		// load Tables:
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rset = null;
		
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			
			// get top killers:
			statement = con.prepareStatement("SELECT killer_id, char_name, level, base_class, sum(kills_legal) as col5 FROM custom_pvp_system JOIN characters ON characters.obj_Id = custom_pvp_system.killer_id GROUP BY killer_id HAVING col5 > 0 ORDER BY col5 DESC;");
			rset = statement.executeQuery();
			
			while (rset.next())
			{
				
				TopField tf = new TopField();
				tf.setCharacterId(rset.getInt("killer_id"));
				tf.setCharacterName(rset.getString("char_name"));
				tf.setCharacterLevel(rset.getInt("level"));
				tf.setCharacterBaseClassId(rset.getInt("base_class"));
				tf.setCharacterPoints(rset.getLong("col5"));

				// get killer pvp stats:
				getTopKillsTable().put(getTopKillsTable().size() + 1, tf);
				
			}
			
			// get top RP gatherers:
			statement = con.prepareStatement("SELECT killer_id, char_name, level, base_class, sum(rank_points) as col5 FROM custom_pvp_system JOIN characters ON characters.obj_Id = custom_pvp_system.killer_id GROUP BY killer_id HAVING col5 > 0 ORDER BY col5 DESC;");
			rset = statement.executeQuery();
			
			while (rset.next())
			{

				TopField tf = new TopField();
				tf.setCharacterId(rset.getInt("killer_id"));
				tf.setCharacterName(rset.getString("char_name"));
				tf.setCharacterLevel(rset.getInt("level"));
				tf.setCharacterBaseClassId(rset.getInt("base_class"));
				tf.setCharacterPoints(rset.getLong("col5"));
				
				// get killer pvp stats:
				getTopGatherersTable().put(getTopGatherersTable().size() + 1, tf);
				
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
					con = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		// unlock table:
		setUpdating(false);
	}
	
	private static class TopTableSchedule implements Runnable
	{
		@SuppressWarnings("unused")
		private static final Logger _log = Logger.getLogger(TopTableSchedule.class.getName());

		public TopTableSchedule()
		{
			
		}

		@Override
		public void run()
		{
			if (true)
			{
				TopTable.getInstance().updateTopTable();
			}
			ThreadPoolManager.getInstance().scheduleGeneral(new TopTableSchedule(), ExternalConfig.TOP_TABLE_UPDATE_INTERVAL);
		}
	}

	/**
	 * @return the _topKillsTable
	 */
	public FastMap<Integer, TopField> getTopKillsTable()
	{
		return _topKillsTable;
	}

	/**
	 * @param _topKillsTable
	 *        the _topKillsTable to set
	 */
	public void setTopKillsTable(FastMap<Integer, TopField> _topKillsTable)
	{
		this._topKillsTable = _topKillsTable;
	}

	/**
	 * @return the _topGatherersTable
	 */
	public FastMap<Integer, TopField> getTopGatherersTable()
	{
		return _topGatherersTable;
	}

	/**
	 * @param _topGatherersTable
	 *        the _topGatherersTable to set
	 */
	public void setTopGatherersTable(FastMap<Integer, TopField> _topGatherersTable)
	{
		this._topGatherersTable = _topGatherersTable;
	}

	/**
	 * @return the _isUpdating
	 */
	public boolean isUpdating()
	{
		return _isUpdating;
	}

	/**
	 * @param _isUpdating
	 *        the _isUpdating to set
	 */
	public void setUpdating(boolean _isUpdating)
	{
		this._isUpdating = _isUpdating;
	}
	
}