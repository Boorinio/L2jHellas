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
package com.l2jhellas.util.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jolbox.bonecp.BoneCPDataSource;
import com.l2jhellas.Config;
import com.l2jhellas.Server;
import com.l2jhellas.gameserver.ThreadPoolManager;

public class L2DatabaseFactory
{
	private static final Logger _log = Logger.getLogger(L2DatabaseFactory.class.getName());

	public static enum ProviderType
	{
		MySql, MsSql
	}

	private static L2DatabaseFactory _instance;
	private ProviderType _providerType;
	private BoneCPDataSource _source;
	private int database_partition_count = 3;
	private int database_timeout = 0;

	public L2DatabaseFactory()
	{
		try
		{
			if (Config.DATABASE_MAX_CONNECTIONS < 10)
			{
				Config.DATABASE_MAX_CONNECTIONS = 10;
				_log.warning("at least " + Config.DATABASE_MAX_CONNECTIONS + " db connections are required.");
			}

			if (database_partition_count > 4)
			{
				database_partition_count = 4;
				_log.warning("max {} db connections partitions. " + database_partition_count);
			}

			if (Config.DATABASE_MAX_CONNECTIONS * database_partition_count > 200)
			{
				_log.warning("Max Connections number is higher then 60.. Using Partition 2 and Connection 30");
				Config.DATABASE_MAX_CONNECTIONS = 50;
				database_partition_count = 4;
			}

			_source = new BoneCPDataSource();

			_source.getConfig().setDefaultAutoCommit(true);
			_source.getConfig().setPoolAvailabilityThreshold(10);
			_source.getConfig().setMinConnectionsPerPartition(10);
			_source.getConfig().setMaxConnectionsPerPartition(Config.DATABASE_MAX_CONNECTIONS);
			_source.getConfig().setPartitionCount(database_partition_count);

			_source.setAcquireRetryAttempts(0);
			_source.setAcquireRetryDelayInMs(500);
			_source.setAcquireIncrement(5);

			_source.setConnectionTimeoutInMs(database_timeout);

			_source.setIdleConnectionTestPeriodInMinutes(1);

			_source.setIdleMaxAgeInSeconds(1800);

			_source.setTransactionRecoveryEnabled(true);
			_source.setDriverClass(Config.DATABASE_DRIVER);
			_source.setJdbcUrl(Config.DATABASE_URL);
			_source.setUsername(Config.DATABASE_LOGIN);
			_source.setPassword(Config.DATABASE_PASSWORD);

			_source.getConnection().close();

			if (Config.DATABASE_DRIVER.toLowerCase().contains("microsoft"))
				_providerType = ProviderType.MsSql;
			else
				_providerType = ProviderType.MySql;
			_log.log(Level.INFO, "Database loaded.");
		}
		catch (Exception e)
		{
			throw new Error("L2DatabaseFactory: Failed to init database connections: " + e, e);
		}
	}

	public final String prepQuerySelect(String[] fields, String tableName, String whereClause, boolean returnOnlyTopRecord)
	{
		String msSqlTop1 = "";
		String mySqlTop1 = "";
		if (returnOnlyTopRecord)
		{
			if (getProviderType() == ProviderType.MsSql)
			{
				msSqlTop1 = " Top 1 ";
			}
			if (getProviderType() == ProviderType.MySql)
			{
				mySqlTop1 = " Limit 1 ";
			}
		}
		String query = "SELECT " + msSqlTop1 + safetyString(fields) + " FROM " + tableName + " WHERE " + whereClause + mySqlTop1;
		return query;
	}

	public void shutdown()
	{
		try
		{
			_source.close();
		}
		catch (Exception e)
		{
			_log.log(Level.INFO, "", e);
		}
		try
		{
			_source = null;
		}
		catch (Exception e)
		{
			_log.log(Level.INFO, "", e);
		}
	}

	public final String safetyString(String... whatToCheck)
	{
		// NOTE: Use brace as a safety precaution just in case name is a reserved word
		final char braceLeft;
		final char braceRight;

		if (getProviderType() == ProviderType.MsSql)
		{
			braceLeft = '[';
			braceRight = ']';
		}
		else
		{
			braceLeft = '`';
			braceRight = '`';
		}

		int length = 0;

		for (String word : whatToCheck)
		{
			length += word.length() + 4;
		}

		final StringBuilder sbResult = new StringBuilder(length);

		for (String word : whatToCheck)
		{
			if (sbResult.length() > 0)
			{
				sbResult.append(", ");
			}

			sbResult.append(braceLeft);
			sbResult.append(word);
			sbResult.append(braceRight);
		}

		return sbResult.toString();
	}

	public static L2DatabaseFactory getInstance()
	{
		if (_instance == null)
			_instance = new L2DatabaseFactory();

		return _instance;
	}

	/**
	 * Gets the connection.
	 * 
	 * @return the connection
	 */
	public Connection getConnection()
	{
		Connection con = null;
		while (con == null)
		{
			try
			{
				con = _source.getConnection();
				if (Server.serverMode == Server.MODE_GAMESERVER)
					ThreadPoolManager.getInstance().scheduleGeneral(new ConnectionCloser(con, new RuntimeException()), 60000);
			}
			catch (SQLException e)
			{
				_log.warning("L2DatabaseFactory: getConnection() failed, trying again " + e);
			}
		}
		return con;
	}

	private static class ConnectionCloser implements Runnable
	{
		private final Connection c;
		private final RuntimeException exp;

		public ConnectionCloser(Connection con, RuntimeException e)
		{
			c = con;
			exp = e;
		}

		@Override
		public void run()
		{
			try
			{
				//just remove this until the refactor
				if (!c.isClosed() && c != null && Config.DEBUG)
					_log.log(Level.WARNING, "Unclosed connection! Trace: " + exp.getStackTrace()[1], exp);					
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	public int getBusyConnectionCount()
	{
		return _source.getTotalLeased();
	}

	public final ProviderType getProviderType()
	{
		return _providerType;
	}
}