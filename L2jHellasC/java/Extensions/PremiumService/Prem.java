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
package Extensions.PremiumService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.l2jhellas.Config;
import com.l2jhellas.L2DatabaseFactory;

public class Prem
{
	
	private long _end_pr_date;
	
	public static final Prem getInstance()
	{
		return SingletonHolder._instance;
	}

	public long getPremServiceData(String playerAcc)
	{
		
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT premium_service,enddate FROM account_premium WHERE account_name=?");
			statement.setString(1, playerAcc);
			ResultSet rset = statement.executeQuery();
			while (rset.next())
			{
				if (Config.USE_PREMIUMSERVICE)
				{
					_end_pr_date = rset.getLong("enddate");
				}
			}
			statement.close();
		}
		catch (Exception e)
		{

		}
		finally
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{
			}
		}
		return _end_pr_date;
	}

	@SuppressWarnings("synthetic-access")
	private static class SingletonHolder
	{
		protected static final Prem _instance = new Prem();
	}
}