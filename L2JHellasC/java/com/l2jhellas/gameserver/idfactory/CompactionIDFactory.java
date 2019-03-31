package com.l2jhellas.gameserver.idfactory;

import com.l2jhellas.Config;
import com.l2jhellas.util.database.L2DatabaseFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class CompactionIDFactory extends IdFactory
{
	private static Logger _log = Logger.getLogger(CompactionIDFactory.class.getName());
	private int _curOID;
	private final int _freeSize;
	
	protected CompactionIDFactory()
	{
		super();
		_curOID = FIRST_OID;
		_freeSize = 0;
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			// TODO check this con.createStatement().execute("drop table if exists tmp_obj_id");
			
			int[] tmp_obj_ids = extractUsedObjectIDTable();
			
			int N = tmp_obj_ids.length;
			for (int idx = 0; idx < N; idx++)
			{
				N = insertUntil(tmp_obj_ids, idx, N, con);
			}
			_curOID++;
			_log.info(CompactionIDFactory.class.getSimpleName() + ": Next usable Object ID is: " + _curOID);
			_initialized = true;
		}
		catch (SQLException e)
		{
			_log.warning(CompactionIDFactory.class.getName() + ": ID Factory could not be initialized correctly:");
			if (Config.DEVELOPER)
				e.printStackTrace();
		}
	}
	
	private int insertUntil(int[] tmp_obj_ids, int idx, int N, java.sql.Connection con) throws SQLException
	{
		int id = tmp_obj_ids[idx];
		if (id == _curOID)
		{
			_curOID++;
			return N;
		}
		// check these IDs not present in DB
		if (Config.BAD_ID_CHECKING)
		{
			for (String check : ID_CHECKS)
			{
				PreparedStatement ps = con.prepareStatement(check);
				ps.setInt(1, _curOID);
				ps.setInt(2, id);
				ResultSet rs = ps.executeQuery();
				while (rs.next())
				{
					int badId = rs.getInt(1);
					_log.severe(CompactionIDFactory.class.getName() + ": Bad ID " + badId + " in DB found by: " + check);
					throw new RuntimeException();
				}
				rs.close();
				ps.close();
			}
		}
		
		int hole = id - _curOID;
		if (hole > N - idx)
			hole = N - idx;
		for (int i = 1; i <= hole; i++)
		{
			id = tmp_obj_ids[N - i];
			_log.info(CompactionIDFactory.class.getSimpleName() + ": Compacting DB object ID=" + id + " into " + (_curOID));
			for (String update : ID_UPDATES)
			{
				PreparedStatement ps = con.prepareStatement(update);
				ps.setInt(1, _curOID);
				ps.setInt(2, id);
				ps.execute();
				ps.close();
			}
			_curOID++;
		}
		if (hole < N - idx)
			_curOID++;
		return N - hole;
	}
	
	@Override
	public synchronized int getNextId()
	{
		return _curOID++;
		
	}
	
	@Override
	public synchronized void releaseId(int id)
	{
		// dont release ids until we are sure it isnt messing up
		
	}
	
	@Override
	public int size()
	{
		return _freeSize + LAST_OID - FIRST_OID;
	}
}