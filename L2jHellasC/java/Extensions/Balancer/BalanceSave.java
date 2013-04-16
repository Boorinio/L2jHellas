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
package Extensions.Balancer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.L2DatabaseFactory;

public class BalanceSave
{
	protected static final Logger _log = Logger.getLogger(BalanceSave.class.getName());

	private final static String UPDATE_BALANCE_88 = "UPDATE balance SET accplus88=?,evasionplus88=?,hpplus88=?,cpplus88=?,mpplus88=?,matkplus88=?,matksplus88=?,mdefplus88=?,patkplus88=?,patksplus88=?,pdefplus88=?,walkplus88=?";

	public static void storeBalance88()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_88);
			statement.setInt(1, BalancerMain.accplus88);
			statement.setInt(2, BalancerMain.evasionplus88);
			statement.setInt(3, BalancerMain.hpplus88);
			statement.setInt(4, BalancerMain.cpplus88);
			statement.setInt(5, BalancerMain.mpplus88);
			statement.setInt(6, BalancerMain.matkplus88);
			statement.setInt(7, BalancerMain.matksplus88);
			statement.setInt(8, BalancerMain.mdefplus88);
			statement.setInt(9, BalancerMain.patkplus88);
			statement.setInt(10, BalancerMain.patksplus88);
			statement.setInt(11, BalancerMain.pdefplus88);
			statement.setInt(12, BalancerMain.walkplus88);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_89 = "UPDATE balance SET accplus89=?,evasionplus89=?,hpplus89=?,cpplus89=?,mpplus89=?,matkplus89=?,matksplus89=?,mdefplus89=?,patkplus89=?,patksplus89=?,pdefplus89=?,walkplus89=?";

	public static void storeBalance89()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_89);
			statement.setInt(1, BalancerMain.accplus89);
			statement.setInt(2, BalancerMain.evasionplus89);
			statement.setInt(3, BalancerMain.hpplus89);
			statement.setInt(4, BalancerMain.cpplus89);
			statement.setInt(5, BalancerMain.mpplus89);
			statement.setInt(6, BalancerMain.matkplus89);
			statement.setInt(7, BalancerMain.matksplus89);
			statement.setInt(8, BalancerMain.mdefplus89);
			statement.setInt(9, BalancerMain.patkplus89);
			statement.setInt(10, BalancerMain.patksplus89);
			statement.setInt(11, BalancerMain.pdefplus89);
			statement.setInt(12, BalancerMain.walkplus89);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_90 = "UPDATE balance SET accplus90=?,evasionplus90=?,hpplus90=?,cpplus90=?,mpplus90=?,matkplus90=?,matksplus90=?,mdefplus90=?,patkplus90=?,patksplus90=?,pdefplus90=?,walkplus90=?";

	public static void storeBalance90()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_90);
			statement.setInt(1, BalancerMain.accplus90);
			statement.setInt(2, BalancerMain.evasionplus90);
			statement.setInt(3, BalancerMain.hpplus90);
			statement.setInt(4, BalancerMain.cpplus90);
			statement.setInt(5, BalancerMain.mpplus90);
			statement.setInt(6, BalancerMain.matkplus90);
			statement.setInt(7, BalancerMain.matksplus90);
			statement.setInt(8, BalancerMain.mdefplus90);
			statement.setInt(9, BalancerMain.patkplus90);
			statement.setInt(10, BalancerMain.patksplus90);
			statement.setInt(11, BalancerMain.pdefplus90);
			statement.setInt(12, BalancerMain.walkplus90);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_91 = "UPDATE balance SET accplus91=?,evasionplus91=?,hpplus91=?,cpplus91=?,mpplus91=?,matkplus91=?,matksplus91=?,mdefplus91=?,patkplus91=?,patksplus91=?,pdefplus91=?,walkplus91=?";

	public static void storeBalance91()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_91);
			statement.setInt(1, BalancerMain.accplus91);
			statement.setInt(2, BalancerMain.evasionplus91);
			statement.setInt(3, BalancerMain.hpplus91);
			statement.setInt(4, BalancerMain.cpplus91);
			statement.setInt(5, BalancerMain.mpplus91);
			statement.setInt(6, BalancerMain.matkplus91);
			statement.setInt(7, BalancerMain.matksplus91);
			statement.setInt(8, BalancerMain.mdefplus91);
			statement.setInt(9, BalancerMain.patkplus91);
			statement.setInt(10, BalancerMain.patksplus91);
			statement.setInt(11, BalancerMain.pdefplus91);
			statement.setInt(12, BalancerMain.walkplus91);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_92 = "UPDATE balance SET accplus92=?,evasionplus92=?,hpplus92=?,cpplus92=?,mpplus92=?,matkplus92=?,matksplus92=?,mdefplus92=?,patkplus92=?,patksplus92=?,pdefplus92=?,walkplus92=?";

	public static void storeBalance92()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_92);
			statement.setInt(1, BalancerMain.accplus92);
			statement.setInt(2, BalancerMain.evasionplus92);
			statement.setInt(3, BalancerMain.hpplus92);
			statement.setInt(4, BalancerMain.cpplus92);
			statement.setInt(5, BalancerMain.mpplus92);
			statement.setInt(6, BalancerMain.matkplus92);
			statement.setInt(7, BalancerMain.matksplus92);
			statement.setInt(8, BalancerMain.mdefplus92);
			statement.setInt(9, BalancerMain.patkplus92);
			statement.setInt(10, BalancerMain.patksplus92);
			statement.setInt(11, BalancerMain.pdefplus92);
			statement.setInt(12, BalancerMain.walkplus92);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_93 = "UPDATE balance SET accplus93=?,evasionplus93=?,hpplus93=?,cpplus93=?,mpplus93=?,matkplus93=?,matksplus93=?,mdefplus93=?,patkplus93=?,patksplus93=?,pdefplus93=?,walkplus93=?";

	public static void storeBalance93()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_93);
			statement.setInt(1, BalancerMain.accplus93);
			statement.setInt(2, BalancerMain.evasionplus93);
			statement.setInt(3, BalancerMain.hpplus93);
			statement.setInt(4, BalancerMain.cpplus93);
			statement.setInt(5, BalancerMain.mpplus93);
			statement.setInt(6, BalancerMain.matkplus93);
			statement.setInt(7, BalancerMain.matksplus93);
			statement.setInt(8, BalancerMain.mdefplus93);
			statement.setInt(9, BalancerMain.patkplus93);
			statement.setInt(10, BalancerMain.patksplus93);
			statement.setInt(11, BalancerMain.pdefplus93);
			statement.setInt(12, BalancerMain.walkplus93);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_94 = "UPDATE balance SET accplus94=?,evasionplus94=?,hpplus94=?,cpplus94=?,mpplus94=?,matkplus94=?,matksplus94=?,mdefplus94=?,patkplus94=?,patksplus94=?,pdefplus94=?,walkplus94=?";

	public static void storeBalance94()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_94);
			statement.setInt(1, BalancerMain.accplus94);
			statement.setInt(2, BalancerMain.evasionplus94);
			statement.setInt(3, BalancerMain.hpplus94);
			statement.setInt(4, BalancerMain.cpplus94);
			statement.setInt(5, BalancerMain.mpplus94);
			statement.setInt(6, BalancerMain.matkplus94);
			statement.setInt(7, BalancerMain.matksplus94);
			statement.setInt(8, BalancerMain.mdefplus94);
			statement.setInt(9, BalancerMain.patkplus94);
			statement.setInt(10, BalancerMain.patksplus94);
			statement.setInt(11, BalancerMain.pdefplus94);
			statement.setInt(12, BalancerMain.walkplus94);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_95 = "UPDATE balance SET accplus95=?,evasionplus95=?,hpplus95=?,cpplus95=?,mpplus95=?,matkplus95=?,matksplus95=?,mdefplus95=?,patkplus95=?,patksplus95=?,pdefplus95=?,walkplus95=?";

	public static void storeBalance95()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_95);
			statement.setInt(1, BalancerMain.accplus95);
			statement.setInt(2, BalancerMain.evasionplus95);
			statement.setInt(3, BalancerMain.hpplus95);
			statement.setInt(4, BalancerMain.cpplus95);
			statement.setInt(5, BalancerMain.mpplus95);
			statement.setInt(6, BalancerMain.matkplus95);
			statement.setInt(7, BalancerMain.matksplus95);
			statement.setInt(8, BalancerMain.mdefplus95);
			statement.setInt(9, BalancerMain.patkplus95);
			statement.setInt(10, BalancerMain.patksplus95);
			statement.setInt(11, BalancerMain.pdefplus95);
			statement.setInt(12, BalancerMain.walkplus95);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_96 = "UPDATE balance SET accplus96=?,evasionplus96=?,hpplus96=?,cpplus96=?,mpplus96=?,matkplus96=?,matksplus96=?,mdefplus96=?,patkplus96=?,patksplus96=?,pdefplus96=?,walkplus96=?";

	public static void storeBalance96()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_96);
			statement.setInt(1, BalancerMain.accplus96);
			statement.setInt(2, BalancerMain.evasionplus96);
			statement.setInt(3, BalancerMain.hpplus96);
			statement.setInt(4, BalancerMain.cpplus96);
			statement.setInt(5, BalancerMain.mpplus96);
			statement.setInt(6, BalancerMain.matkplus96);
			statement.setInt(7, BalancerMain.matksplus96);
			statement.setInt(8, BalancerMain.mdefplus96);
			statement.setInt(9, BalancerMain.patkplus96);
			statement.setInt(10, BalancerMain.patksplus96);
			statement.setInt(11, BalancerMain.pdefplus96);
			statement.setInt(12, BalancerMain.walkplus96);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_97 = "UPDATE balance SET accplus97=?,evasionplus97=?,hpplus97=?,cpplus97=?,mpplus97=?,matkplus97=?,matksplus97=?,mdefplus97=?,patkplus97=?,patksplus97=?,pdefplus97=?,walkplus97=?";

	public static void storeBalance97()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_97);
			statement.setInt(1, BalancerMain.accplus97);
			statement.setInt(2, BalancerMain.evasionplus97);
			statement.setInt(3, BalancerMain.hpplus97);
			statement.setInt(4, BalancerMain.cpplus97);
			statement.setInt(5, BalancerMain.mpplus97);
			statement.setInt(6, BalancerMain.matkplus97);
			statement.setInt(7, BalancerMain.matksplus97);
			statement.setInt(8, BalancerMain.mdefplus97);
			statement.setInt(9, BalancerMain.patkplus97);
			statement.setInt(10, BalancerMain.patksplus97);
			statement.setInt(11, BalancerMain.pdefplus97);
			statement.setInt(12, BalancerMain.walkplus97);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_98 = "UPDATE balance SET accplus98=?,evasionplus98=?,hpplus98=?,cpplus98=?,mpplus98=?,matkplus98=?,matksplus98=?,mdefplus98=?,patkplus98=?,patksplus98=?,pdefplus98=?,walkplus98=?";

	public static void storeBalance98()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_98);
			statement.setInt(1, BalancerMain.accplus98);
			statement.setInt(2, BalancerMain.evasionplus98);
			statement.setInt(3, BalancerMain.hpplus98);
			statement.setInt(4, BalancerMain.cpplus98);
			statement.setInt(5, BalancerMain.mpplus98);
			statement.setInt(6, BalancerMain.matkplus98);
			statement.setInt(7, BalancerMain.matksplus98);
			statement.setInt(8, BalancerMain.mdefplus98);
			statement.setInt(9, BalancerMain.patkplus98);
			statement.setInt(10, BalancerMain.patksplus98);
			statement.setInt(11, BalancerMain.pdefplus98);
			statement.setInt(12, BalancerMain.walkplus98);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_99 = "UPDATE balance SET accplus99=?,evasionplus99=?,hpplus99=?,cpplus99=?,mpplus99=?,matkplus99=?,matksplus99=?,mdefplus99=?,patkplus99=?,patksplus99=?,pdefplus99=?,walkplus99=?";

	public static void storeBalance99()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_99);
			statement.setInt(1, BalancerMain.accplus99);
			statement.setInt(2, BalancerMain.evasionplus99);
			statement.setInt(3, BalancerMain.hpplus99);
			statement.setInt(4, BalancerMain.cpplus99);
			statement.setInt(5, BalancerMain.mpplus99);
			statement.setInt(6, BalancerMain.matkplus99);
			statement.setInt(7, BalancerMain.matksplus99);
			statement.setInt(8, BalancerMain.mdefplus99);
			statement.setInt(9, BalancerMain.patkplus99);
			statement.setInt(10, BalancerMain.patksplus99);
			statement.setInt(11, BalancerMain.pdefplus99);
			statement.setInt(12, BalancerMain.walkplus99);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_100 = "UPDATE balance SET accplus100=?,evasionplus100=?,hpplus100=?,cpplus100=?,mpplus100=?,matkplus100=?,matksplus100=?,mdefplus100=?,patkplus100=?,patksplus100=?,pdefplus100=?,walkplus100=?";

	public static void storeBalance100()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_100);
			statement.setInt(1, BalancerMain.accplus100);
			statement.setInt(2, BalancerMain.evasionplus100);
			statement.setInt(3, BalancerMain.hpplus100);
			statement.setInt(4, BalancerMain.cpplus100);
			statement.setInt(5, BalancerMain.mpplus100);
			statement.setInt(6, BalancerMain.matkplus100);
			statement.setInt(7, BalancerMain.matksplus100);
			statement.setInt(8, BalancerMain.mdefplus100);
			statement.setInt(9, BalancerMain.patkplus100);
			statement.setInt(10, BalancerMain.patksplus100);
			statement.setInt(11, BalancerMain.pdefplus100);
			statement.setInt(12, BalancerMain.walkplus100);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_101 = "UPDATE balance SET accplus101=?,evasionplus101=?,hpplus101=?,cpplus101=?,mpplus101=?,matkplus101=?,matksplus101=?,mdefplus101=?,patkplus101=?,patksplus101=?,pdefplus101=?,walkplus101=?";

	public static void storeBalance101()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_101);
			statement.setInt(1, BalancerMain.accplus101);
			statement.setInt(2, BalancerMain.evasionplus101);
			statement.setInt(3, BalancerMain.hpplus101);
			statement.setInt(4, BalancerMain.cpplus101);
			statement.setInt(5, BalancerMain.mpplus101);
			statement.setInt(6, BalancerMain.matkplus101);
			statement.setInt(7, BalancerMain.matksplus101);
			statement.setInt(8, BalancerMain.mdefplus101);
			statement.setInt(9, BalancerMain.patkplus101);
			statement.setInt(10, BalancerMain.patksplus101);
			statement.setInt(11, BalancerMain.pdefplus101);
			statement.setInt(12, BalancerMain.walkplus101);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_102 = "UPDATE balance SET accplus102=?,evasionplus102=?,hpplus102=?,cpplus102=?,mpplus102=?,matkplus102=?,matksplus102=?,mdefplus102=?,patkplus102=?,patksplus102=?,pdefplus102=?,walkplus102=?";

	public static void storeBalance102()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_102);
			statement.setInt(1, BalancerMain.accplus102);
			statement.setInt(2, BalancerMain.evasionplus102);
			statement.setInt(3, BalancerMain.hpplus102);
			statement.setInt(4, BalancerMain.cpplus102);
			statement.setInt(5, BalancerMain.mpplus102);
			statement.setInt(6, BalancerMain.matkplus102);
			statement.setInt(7, BalancerMain.matksplus102);
			statement.setInt(8, BalancerMain.mdefplus102);
			statement.setInt(9, BalancerMain.patkplus102);
			statement.setInt(10, BalancerMain.patksplus102);
			statement.setInt(11, BalancerMain.pdefplus102);
			statement.setInt(12, BalancerMain.walkplus102);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_103 = "UPDATE balance SET accplus103=?,evasionplus103=?,hpplus103=?,cpplus103=?,mpplus103=?,matkplus103=?,matksplus103=?,mdefplus103=?,patkplus103=?,patksplus103=?,pdefplus103=?,walkplus103=?";

	public static void storeBalance103()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_103);
			statement.setInt(1, BalancerMain.accplus103);
			statement.setInt(2, BalancerMain.evasionplus103);
			statement.setInt(3, BalancerMain.hpplus103);
			statement.setInt(4, BalancerMain.cpplus103);
			statement.setInt(5, BalancerMain.mpplus103);
			statement.setInt(6, BalancerMain.matkplus103);
			statement.setInt(7, BalancerMain.matksplus103);
			statement.setInt(8, BalancerMain.mdefplus103);
			statement.setInt(9, BalancerMain.patkplus103);
			statement.setInt(10, BalancerMain.patksplus103);
			statement.setInt(11, BalancerMain.pdefplus103);
			statement.setInt(12, BalancerMain.walkplus103);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_104 = "UPDATE balance SET accplus104=?,evasionplus104=?,hpplus104=?,cpplus104=?,mpplus104=?,matkplus104=?,matksplus104=?,mdefplus104=?,patkplus104=?,patksplus104=?,pdefplus104=?,walkplus104=?";

	public static void storeBalance104()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_104);
			statement.setInt(1, BalancerMain.accplus104);
			statement.setInt(2, BalancerMain.evasionplus104);
			statement.setInt(3, BalancerMain.hpplus104);
			statement.setInt(4, BalancerMain.cpplus104);
			statement.setInt(5, BalancerMain.mpplus104);
			statement.setInt(6, BalancerMain.matkplus104);
			statement.setInt(7, BalancerMain.matksplus104);
			statement.setInt(8, BalancerMain.mdefplus104);
			statement.setInt(9, BalancerMain.patkplus104);
			statement.setInt(10, BalancerMain.patksplus104);
			statement.setInt(11, BalancerMain.pdefplus104);
			statement.setInt(12, BalancerMain.walkplus104);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_105 = "UPDATE balance SET accplus105=?,evasionplus105=?,hpplus105=?,cpplus105=?,mpplus105=?,matkplus105=?,matksplus105=?,mdefplus105=?,patkplus105=?,patksplus105=?,pdefplus105=?,walkplus105=?";

	public static void storeBalance105()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_105);
			statement.setInt(1, BalancerMain.accplus105);
			statement.setInt(2, BalancerMain.evasionplus105);
			statement.setInt(3, BalancerMain.hpplus105);
			statement.setInt(4, BalancerMain.cpplus105);
			statement.setInt(5, BalancerMain.mpplus105);
			statement.setInt(6, BalancerMain.matkplus105);
			statement.setInt(7, BalancerMain.matksplus105);
			statement.setInt(8, BalancerMain.mdefplus105);
			statement.setInt(9, BalancerMain.patkplus105);
			statement.setInt(10, BalancerMain.patksplus105);
			statement.setInt(11, BalancerMain.pdefplus105);
			statement.setInt(12, BalancerMain.walkplus105);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_106 = "UPDATE balance SET accplus106=?,evasionplus106=?,hpplus106=?,cpplus106=?,mpplus106=?,matkplus106=?,matksplus106=?,mdefplus106=?,patkplus106=?,patksplus106=?,pdefplus106=?,walkplus106=?";

	public static void storeBalance106()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_106);
			statement.setInt(1, BalancerMain.accplus106);
			statement.setInt(2, BalancerMain.evasionplus106);
			statement.setInt(3, BalancerMain.hpplus106);
			statement.setInt(4, BalancerMain.cpplus106);
			statement.setInt(5, BalancerMain.mpplus106);
			statement.setInt(6, BalancerMain.matkplus106);
			statement.setInt(7, BalancerMain.matksplus106);
			statement.setInt(8, BalancerMain.mdefplus106);
			statement.setInt(9, BalancerMain.patkplus106);
			statement.setInt(10, BalancerMain.patksplus106);
			statement.setInt(11, BalancerMain.pdefplus106);
			statement.setInt(12, BalancerMain.walkplus106);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_107 = "UPDATE balance SET accplus107=?,evasionplus107=?,hpplus107=?,cpplus107=?,mpplus107=?,matkplus107=?,matksplus107=?,mdefplus107=?,patkplus107=?,patksplus107=?,pdefplus107=?,walkplus107=?";

	public static void storeBalance107()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_107);
			statement.setInt(1, BalancerMain.accplus107);
			statement.setInt(2, BalancerMain.evasionplus107);
			statement.setInt(3, BalancerMain.hpplus107);
			statement.setInt(4, BalancerMain.cpplus107);
			statement.setInt(5, BalancerMain.mpplus107);
			statement.setInt(6, BalancerMain.matkplus107);
			statement.setInt(7, BalancerMain.matksplus107);
			statement.setInt(8, BalancerMain.mdefplus107);
			statement.setInt(9, BalancerMain.patkplus107);
			statement.setInt(10, BalancerMain.patksplus107);
			statement.setInt(11, BalancerMain.pdefplus107);
			statement.setInt(12, BalancerMain.walkplus107);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_108 = "UPDATE balance SET accplus108=?,evasionplus108=?,hpplus108=?,cpplus108=?,mpplus108=?,matkplus108=?,matksplus108=?,mdefplus108=?,patkplus108=?,patksplus108=?,pdefplus108=?,walkplus108=?";

	public static void storeBalance108()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_108);
			statement.setInt(1, BalancerMain.accplus108);
			statement.setInt(2, BalancerMain.evasionplus108);
			statement.setInt(3, BalancerMain.hpplus108);
			statement.setInt(4, BalancerMain.cpplus108);
			statement.setInt(5, BalancerMain.mpplus108);
			statement.setInt(6, BalancerMain.matkplus108);
			statement.setInt(7, BalancerMain.matksplus108);
			statement.setInt(8, BalancerMain.mdefplus108);
			statement.setInt(9, BalancerMain.patkplus108);
			statement.setInt(10, BalancerMain.patksplus108);
			statement.setInt(11, BalancerMain.pdefplus108);
			statement.setInt(12, BalancerMain.walkplus108);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_109 = "UPDATE balance SET accplus109=?,evasionplus109=?,hpplus109=?,cpplus109=?,mpplus109=?,matkplus109=?,matksplus109=?,mdefplus109=?,patkplus109=?,patksplus109=?,pdefplus109=?,walkplus109=?";

	public static void storeBalance109()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_109);
			statement.setInt(1, BalancerMain.accplus109);
			statement.setInt(2, BalancerMain.evasionplus109);
			statement.setInt(3, BalancerMain.hpplus109);
			statement.setInt(4, BalancerMain.cpplus109);
			statement.setInt(5, BalancerMain.mpplus109);
			statement.setInt(6, BalancerMain.matkplus109);
			statement.setInt(7, BalancerMain.matksplus109);
			statement.setInt(8, BalancerMain.mdefplus109);
			statement.setInt(9, BalancerMain.patkplus109);
			statement.setInt(10, BalancerMain.patksplus109);
			statement.setInt(11, BalancerMain.pdefplus109);
			statement.setInt(12, BalancerMain.walkplus109);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_110 = "UPDATE balance SET accplus110=?,evasionplus110=?,hpplus110=?,cpplus110=?,mpplus110=?,matkplus110=?,matksplus110=?,mdefplus110=?,patkplus110=?,patksplus110=?,pdefplus110=?,walkplus110=?";

	public static void storeBalance110()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_110);
			statement.setInt(1, BalancerMain.accplus110);
			statement.setInt(2, BalancerMain.evasionplus110);
			statement.setInt(3, BalancerMain.hpplus110);
			statement.setInt(4, BalancerMain.cpplus110);
			statement.setInt(5, BalancerMain.mpplus110);
			statement.setInt(6, BalancerMain.matkplus110);
			statement.setInt(7, BalancerMain.matksplus110);
			statement.setInt(8, BalancerMain.mdefplus110);
			statement.setInt(9, BalancerMain.patkplus110);
			statement.setInt(10, BalancerMain.patksplus110);
			statement.setInt(11, BalancerMain.pdefplus110);
			statement.setInt(12, BalancerMain.walkplus110);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_111 = "UPDATE balance SET accplus111=?,evasionplus111=?,hpplus111=?,cpplus111=?,mpplus111=?,matkplus111=?,matksplus111=?,mdefplus111=?,patkplus111=?,patksplus111=?,pdefplus111=?,walkplus111=?";

	public static void storeBalance111()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_111);
			statement.setInt(1, BalancerMain.accplus111);
			statement.setInt(2, BalancerMain.evasionplus111);
			statement.setInt(3, BalancerMain.hpplus111);
			statement.setInt(4, BalancerMain.cpplus111);
			statement.setInt(5, BalancerMain.mpplus111);
			statement.setInt(6, BalancerMain.matkplus111);
			statement.setInt(7, BalancerMain.matksplus111);
			statement.setInt(8, BalancerMain.mdefplus111);
			statement.setInt(9, BalancerMain.patkplus111);
			statement.setInt(10, BalancerMain.patksplus111);
			statement.setInt(11, BalancerMain.pdefplus111);
			statement.setInt(12, BalancerMain.walkplus111);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_112 = "UPDATE balance SET accplus112=?,evasionplus112=?,hpplus112=?,cpplus112=?,mpplus112=?,matkplus112=?,matksplus112=?,mdefplus112=?,patkplus112=?,patksplus112=?,pdefplus112=?,walkplus112=?";

	public static void storeBalance112()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_112);
			statement.setInt(1, BalancerMain.accplus112);
			statement.setInt(2, BalancerMain.evasionplus112);
			statement.setInt(3, BalancerMain.hpplus112);
			statement.setInt(4, BalancerMain.cpplus112);
			statement.setInt(5, BalancerMain.mpplus112);
			statement.setInt(6, BalancerMain.matkplus112);
			statement.setInt(7, BalancerMain.matksplus112);
			statement.setInt(8, BalancerMain.mdefplus112);
			statement.setInt(9, BalancerMain.patkplus112);
			statement.setInt(10, BalancerMain.patksplus112);
			statement.setInt(11, BalancerMain.pdefplus112);
			statement.setInt(12, BalancerMain.walkplus112);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_113 = "UPDATE balance SET accplus113=?,evasionplus113=?,hpplus113=?,cpplus113=?,mpplus113=?,matkplus113=?,matksplus113=?,mdefplus113=?,patkplus113=?,patksplus113=?,pdefplus113=?,walkplus113=?";

	public static void storeBalance113()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_113);
			statement.setInt(1, BalancerMain.accplus113);
			statement.setInt(2, BalancerMain.evasionplus113);
			statement.setInt(3, BalancerMain.hpplus113);
			statement.setInt(4, BalancerMain.cpplus113);
			statement.setInt(5, BalancerMain.mpplus113);
			statement.setInt(6, BalancerMain.matkplus113);
			statement.setInt(7, BalancerMain.matksplus113);
			statement.setInt(8, BalancerMain.mdefplus113);
			statement.setInt(9, BalancerMain.patkplus113);
			statement.setInt(10, BalancerMain.patksplus113);
			statement.setInt(11, BalancerMain.pdefplus113);
			statement.setInt(12, BalancerMain.walkplus113);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_114 = "UPDATE balance SET accplus114=?,evasionplus114=?,hpplus114=?,cpplus114=?,mpplus114=?,matkplus114=?,matksplus114=?,mdefplus114=?,patkplus114=?,patksplus114=?,pdefplus114=?,walkplus115=?";

	public static void storeBalance114()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_114);
			statement.setInt(1, BalancerMain.accplus114);
			statement.setInt(2, BalancerMain.evasionplus114);
			statement.setInt(3, BalancerMain.hpplus114);
			statement.setInt(4, BalancerMain.cpplus114);
			statement.setInt(5, BalancerMain.mpplus114);
			statement.setInt(6, BalancerMain.matkplus114);
			statement.setInt(7, BalancerMain.matksplus114);
			statement.setInt(8, BalancerMain.mdefplus114);
			statement.setInt(9, BalancerMain.patkplus114);
			statement.setInt(10, BalancerMain.patksplus114);
			statement.setInt(11, BalancerMain.pdefplus114);
			statement.setInt(12, BalancerMain.walkplus114);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_115 = "UPDATE balance SET accplus115=?,evasionplus115=?,hpplus115=?,cpplus115=?,mpplus115=?,matkplus115=?,matksplus115=?,mdefplus115=?,patkplus115=?,patksplus115=?,pdefplus115=?,walkplus115=?";

	public static void storeBalance115()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_115);
			statement.setInt(1, BalancerMain.accplus115);
			statement.setInt(2, BalancerMain.evasionplus115);
			statement.setInt(3, BalancerMain.hpplus115);
			statement.setInt(4, BalancerMain.cpplus115);
			statement.setInt(5, BalancerMain.mpplus115);
			statement.setInt(6, BalancerMain.matkplus115);
			statement.setInt(7, BalancerMain.matksplus115);
			statement.setInt(8, BalancerMain.mdefplus115);
			statement.setInt(9, BalancerMain.patkplus115);
			statement.setInt(10, BalancerMain.patksplus115);
			statement.setInt(11, BalancerMain.pdefplus115);
			statement.setInt(12, BalancerMain.walkplus115);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_116 = "UPDATE balance SET accplus116=?,evasionplus116=?,hpplus116=?,cpplus116=?,mpplus116=?,matkplus116=?,matksplus116=?,mdefplus116=?,patkplus116=?,patksplus116=?,pdefplus116=?,walkplus116=?";

	public static void storeBalance116()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_116);
			statement.setInt(1, BalancerMain.accplus116);
			statement.setInt(2, BalancerMain.evasionplus116);
			statement.setInt(3, BalancerMain.hpplus116);
			statement.setInt(4, BalancerMain.cpplus116);
			statement.setInt(5, BalancerMain.mpplus116);
			statement.setInt(6, BalancerMain.matkplus116);
			statement.setInt(7, BalancerMain.matksplus116);
			statement.setInt(8, BalancerMain.mdefplus116);
			statement.setInt(9, BalancerMain.patkplus116);
			statement.setInt(10, BalancerMain.patksplus116);
			statement.setInt(11, BalancerMain.pdefplus116);
			statement.setInt(12, BalancerMain.walkplus116);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_117 = "UPDATE balance SET accplus117=?,evasionplus117=?,hpplus117=?,cpplus117=?,mpplus117=?,matkplus117=?,matksplus117=?,mdefplus117=?,patkplus117=?,patksplus117=?,pdefplus117=?,walkplus117=?";

	public static void storeBalance117()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_117);
			statement.setInt(1, BalancerMain.accplus117);
			statement.setInt(2, BalancerMain.evasionplus117);
			statement.setInt(3, BalancerMain.hpplus117);
			statement.setInt(4, BalancerMain.cpplus117);
			statement.setInt(5, BalancerMain.mpplus117);
			statement.setInt(6, BalancerMain.matkplus117);
			statement.setInt(7, BalancerMain.matksplus117);
			statement.setInt(8, BalancerMain.mdefplus117);
			statement.setInt(9, BalancerMain.patkplus117);
			statement.setInt(10, BalancerMain.patksplus117);
			statement.setInt(11, BalancerMain.pdefplus117);
			statement.setInt(12, BalancerMain.walkplus117);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}

	private final static String UPDATE_BALANCE_118 = "UPDATE balance SET accplus118=?,evasionplus118=?,hpplus118=?,cpplus118=?,mpplus118=?,matkplus118=?,matksplus118=?,mdefplus118=?,patkplus118=?,patksplus118=?,pdefplus118=?,walkplus118=?";

	public static void storeBalance118()
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(UPDATE_BALANCE_118);
			statement.setInt(1, BalancerMain.accplus118);
			statement.setInt(2, BalancerMain.evasionplus118);
			statement.setInt(3, BalancerMain.hpplus118);
			statement.setInt(4, BalancerMain.cpplus118);
			statement.setInt(5, BalancerMain.mpplus118);
			statement.setInt(6, BalancerMain.matkplus118);
			statement.setInt(7, BalancerMain.matksplus118);
			statement.setInt(8, BalancerMain.mdefplus118);
			statement.setInt(9, BalancerMain.patkplus118);
			statement.setInt(10, BalancerMain.patksplus118);
			statement.setInt(11, BalancerMain.pdefplus118);
			statement.setInt(12, BalancerMain.walkplus118);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "BalanceSave: Problem saving class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
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
	}
}
