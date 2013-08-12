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
package Extensions.Vote.Tasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.util.database.L2DatabaseFactory;

public class MonthlyResetTask
{
	protected static final Logger _log = Logger.getLogger(MonthlyResetTask.class.getName());

	public MonthlyResetTask()
	{
	}

	public static void getInstance()
	{
		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			@Override
			public void run()
			{
				try (Connection con = L2DatabaseFactory.getInstance().getConnection())
				{
					PreparedStatement statement = con.prepareStatement("UPDATE characters SET monthVotes=?");

					statement.setInt(1, 0);
					statement.execute();
					statement.close();
				}
				catch (Exception e)
				{
					_log.log(Level.WARNING, getClass().getName() + ": could not update database " + e);
					if (Config.DEVELOPER)
					{
						e.printStackTrace();
					}
				}
			}
		}, getValidationTime());
	}

	private static long getValidationTime()
	{
		Calendar cld = Calendar.getInstance();
		cld.set(5, 1);
		long time = cld.getTimeInMillis();
		if ((System.currentTimeMillis() - time) <= 0L)
		{
			long delay = cld.getTimeInMillis() - System.currentTimeMillis();
			return delay;
		}
		return 0L;
	}
}