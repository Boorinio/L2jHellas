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
package Extensions.Balancer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.L2DatabaseFactory;

public class BalanceLoad
{
	protected static final Logger _log = Logger.getLogger(BalanceLoad.class.getName());

	private static final String RESTORE_BALANCE =
	"SELECT accplus88,evasionplus88,hpplus88,cpplus88,mpplus88,matkplus88,matksplus88,mdefplus88,patkplus88,patksplus88,pdefplus88,walkplus88," +
			"accplus89,evasionplus89,hpplus89,cpplus89,mpplus89,matkplus89,matksplus89,mdefplus89,patkplus89,patksplus89,pdefplus89,walkplus89, " +
			"accplus90,evasionplus90,hpplus90,cpplus90,mpplus90,matkplus90,matksplus90,mdefplus90,patkplus90,patksplus90,pdefplus90,walkplus90, " +
			"accplus91,evasionplus91,hpplus91,cpplus91,mpplus91,matkplus91,matksplus91,mdefplus91,patkplus91,patksplus91,pdefplus91,walkplus91, " +
			"accplus92,evasionplus92,hpplus92,cpplus92,mpplus92,matkplus92,matksplus92,mdefplus92,patkplus92,patksplus92,pdefplus92,walkplus92, " +
			"accplus93,evasionplus93,hpplus93,cpplus93,mpplus93,matkplus93,matksplus93,mdefplus93,patkplus93,patksplus93,pdefplus93,walkplus93, " +
			"accplus94,evasionplus94,hpplus94,cpplus94,mpplus94,matkplus94,matksplus94,mdefplus94,patkplus94,patksplus94,pdefplus94,walkplus94, " +
			"accplus95,evasionplus95,hpplus95,cpplus95,mpplus95,matkplus95,matksplus95,mdefplus95,patkplus95,patksplus95,pdefplus95,walkplus95, " +
			"accplus96,evasionplus96,hpplus96,cpplus96,mpplus96,matkplus96,matksplus96,mdefplus96,patkplus96,patksplus96,pdefplus96,walkplus96, " +
			"accplus97,evasionplus97,hpplus97,cpplus97,mpplus97,matkplus97,matksplus97,mdefplus97,patkplus97,patksplus97,pdefplus97,walkplus97, " +
			"accplus98,evasionplus98,hpplus98,cpplus98,mpplus98,matkplus98,matksplus98,mdefplus98,patkplus98,patksplus98,pdefplus98,walkplus98, " +
			"accplus99,evasionplus99,hpplus99,cpplus99,mpplus99,matkplus99,matksplus99,mdefplus99,patkplus99,patksplus99,pdefplus99,walkplus99, " +
			"accplus100,evasionplus100,hpplus100,cpplus100,mpplus100,matkplus100,matksplus100,mdefplus100,patkplus100,patksplus100,pdefplus100,walkplus100, " +
			"accplus101,evasionplus101,hpplus101,cpplus101,mpplus101,matkplus101,matksplus101,mdefplus101,patkplus101,patksplus101,pdefplus101,walkplus101, " +
			"accplus102,evasionplus102,hpplus102,cpplus102,mpplus102,matkplus102,matksplus102,mdefplus102,patkplus102,patksplus102,pdefplus102,walkplus102, " +
			"accplus103,evasionplus103,hpplus103,cpplus103,mpplus103,matkplus103,matksplus103,mdefplus103,patkplus103,patksplus103,pdefplus103,walkplus103, " +
			"accplus104,evasionplus104,hpplus104,cpplus104,mpplus104,matkplus104,matksplus104,mdefplus104,patkplus104,patksplus104,pdefplus104,walkplus104, " +
			"accplus105,evasionplus105,hpplus105,cpplus105,mpplus105,matkplus105,matksplus105,mdefplus105,patkplus105,patksplus105,pdefplus105,walkplus105, " +
			"accplus106,evasionplus106,hpplus106,cpplus106,mpplus106,matkplus106,matksplus106,mdefplus106,patkplus106,patksplus106,pdefplus106,walkplus106, " +
			"accplus107,evasionplus107,hpplus107,cpplus107,mpplus107,matkplus107,matksplus107,mdefplus107,patkplus107,patksplus107,pdefplus107,walkplus107, " +
			"accplus108,evasionplus108,hpplus108,cpplus108,mpplus108,matkplus108,matksplus108,mdefplus108,patkplus108,patksplus108,pdefplus108,walkplus108, " +
			"accplus109,evasionplus109,hpplus109,cpplus109,mpplus109,matkplus109,matksplus109,mdefplus109,patkplus109,patksplus109,pdefplus109,walkplus109, " +
			"accplus110,evasionplus110,hpplus110,cpplus110,mpplus110,matkplus110,matksplus110,mdefplus110,patkplus110,patksplus110,pdefplus110,walkplus110, " +
			"accplus111,evasionplus111,hpplus111,cpplus111,mpplus111,matkplus111,matksplus111,mdefplus111,patkplus111,patksplus111,pdefplus111,walkplus111, " +
			"accplus112,evasionplus112,hpplus112,cpplus112,mpplus112,matkplus112,matksplus112,mdefplus112,patkplus112,patksplus112,pdefplus112,walkplus112, " +
			"accplus113,evasionplus113,hpplus113,cpplus113,mpplus113,matkplus113,matksplus113,mdefplus113,patkplus113,patksplus113,pdefplus113,walkplus113, " +
			"accplus114,evasionplus114,hpplus114,cpplus114,mpplus114,matkplus114,matksplus114,mdefplus114,patkplus114,patksplus114,pdefplus114,walkplus114, " +
			"accplus115,evasionplus115,hpplus115,cpplus115,mpplus115,matkplus115,matksplus115,mdefplus115,patkplus115,patksplus115,pdefplus115,walkplus115, " +
			"accplus116,evasionplus116,hpplus116,cpplus116,mpplus116,matkplus116,matksplus116,mdefplus116,patkplus116,patksplus116,pdefplus116,walkplus116, " +
			"accplus117,evasionplus117,hpplus117,cpplus117,mpplus117,matkplus117,matksplus117,mdefplus117,patkplus117,patksplus117,pdefplus117,walkplus117, " +
			"accplus118,evasionplus118,hpplus118,cpplus118,mpplus118,matkplus118,matksplus118,mdefplus118,patkplus118,patksplus118,pdefplus118,walkplus118 " +
			"FROM balance";


	public static void loadBalance()
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement(RESTORE_BALANCE);
			final ResultSet rset = statement.executeQuery();

			while (rset.next())
			{
				BalancerMain.accplus88 = rset.getInt("accplus88");
				BalancerMain.evasionplus88 = rset.getInt("evasionplus88");
				BalancerMain.hpplus88 = rset.getInt("hpplus88");
				BalancerMain.cpplus88 = rset.getInt("cpplus88");
				BalancerMain.mpplus88 = rset.getInt("mpplus88");
				BalancerMain.matkplus88 = rset.getInt("matkplus88");
				BalancerMain.matksplus88 = rset.getInt("matksplus88");
				BalancerMain.mdefplus88 = rset.getInt("mdefplus88");
				BalancerMain.patkplus88 = rset.getInt("patkplus88");
				BalancerMain.patksplus88 = rset.getInt("patksplus88");
				BalancerMain.pdefplus88 = rset.getInt("pdefplus88");
				BalancerMain.walkplus88 = rset.getInt("walkplus88");

				BalancerMain.accplus89 = rset.getInt("accplus89");
				BalancerMain.evasionplus89 = rset.getInt("evasionplus89");
				BalancerMain.hpplus89 = rset.getInt("hpplus89");
				BalancerMain.cpplus89 = rset.getInt("cpplus89");
				BalancerMain.mpplus89 = rset.getInt("mpplus89");
				BalancerMain.matkplus89 = rset.getInt("matkplus89");
				BalancerMain.matksplus89 = rset.getInt("matksplus89");
				BalancerMain.mdefplus89 = rset.getInt("mdefplus89");
				BalancerMain.patkplus89 = rset.getInt("patkplus89");
				BalancerMain.patksplus89 = rset.getInt("patksplus89");
				BalancerMain.pdefplus89 = rset.getInt("pdefplus89");
				BalancerMain.walkplus89 = rset.getInt("walkplus89");

				BalancerMain.accplus90 = rset.getInt("accplus90");
				BalancerMain.evasionplus90 = rset.getInt("evasionplus90");
				BalancerMain.hpplus90 = rset.getInt("hpplus90");
				BalancerMain.cpplus90 = rset.getInt("cpplus90");
				BalancerMain.mpplus90 = rset.getInt("mpplus90");
				BalancerMain.matkplus90 = rset.getInt("matkplus90");
				BalancerMain.matksplus90 = rset.getInt("matksplus90");
				BalancerMain.mdefplus90 = rset.getInt("mdefplus90");
				BalancerMain.patkplus90 = rset.getInt("patkplus90");
				BalancerMain.patksplus90 = rset.getInt("patksplus90");
				BalancerMain.pdefplus90 = rset.getInt("pdefplus90");
				BalancerMain.walkplus90 = rset.getInt("walkplus90");

				BalancerMain.accplus91 = rset.getInt("accplus91");
				BalancerMain.evasionplus91 = rset.getInt("evasionplus91");
				BalancerMain.hpplus91 = rset.getInt("hpplus91");
				BalancerMain.cpplus91 = rset.getInt("cpplus91");
				BalancerMain.mpplus91 = rset.getInt("mpplus91");
				BalancerMain.matkplus91 = rset.getInt("matkplus91");
				BalancerMain.matksplus91 = rset.getInt("matksplus91");
				BalancerMain.mdefplus91 = rset.getInt("mdefplus91");
				BalancerMain.patkplus91 = rset.getInt("patkplus91");
				BalancerMain.patksplus91 = rset.getInt("patksplus91");
				BalancerMain.pdefplus91 = rset.getInt("pdefplus91");
				BalancerMain.walkplus91 = rset.getInt("walkplus91");

				BalancerMain.accplus92 = rset.getInt("accplus92");
				BalancerMain.evasionplus92 = rset.getInt("evasionplus92");
				BalancerMain.hpplus92 = rset.getInt("hpplus92");
				BalancerMain.cpplus92 = rset.getInt("cpplus92");
				BalancerMain.mpplus92 = rset.getInt("mpplus92");
				BalancerMain.matkplus92 = rset.getInt("matkplus92");
				BalancerMain.matksplus92 = rset.getInt("matksplus92");
				BalancerMain.mdefplus92 = rset.getInt("mdefplus92");
				BalancerMain.patkplus92 = rset.getInt("patkplus92");
				BalancerMain.patksplus92 = rset.getInt("patksplus92");
				BalancerMain.pdefplus92 = rset.getInt("pdefplus92");
				BalancerMain.walkplus92 = rset.getInt("walkplus92");

				BalancerMain.accplus93 = rset.getInt("accplus93");
				BalancerMain.evasionplus93 = rset.getInt("evasionplus93");
				BalancerMain.hpplus93 = rset.getInt("hpplus93");
				BalancerMain.cpplus93 = rset.getInt("cpplus93");
				BalancerMain.mpplus93 = rset.getInt("mpplus93");
				BalancerMain.matkplus93 = rset.getInt("matkplus93");
				BalancerMain.matksplus93 = rset.getInt("matksplus93");
				BalancerMain.mdefplus93 = rset.getInt("mdefplus93");
				BalancerMain.patkplus93 = rset.getInt("patkplus93");
				BalancerMain.patksplus93 = rset.getInt("patksplus93");
				BalancerMain.pdefplus93 = rset.getInt("pdefplus93");
				BalancerMain.walkplus93 = rset.getInt("walkplus93");

				BalancerMain.accplus94 = rset.getInt("accplus94");
				BalancerMain.evasionplus94 = rset.getInt("evasionplus94");
				BalancerMain.hpplus94 = rset.getInt("hpplus94");
				BalancerMain.cpplus94 = rset.getInt("cpplus94");
				BalancerMain.mpplus94 = rset.getInt("mpplus94");
				BalancerMain.matkplus94 = rset.getInt("matkplus94");
				BalancerMain.matksplus94 = rset.getInt("matksplus94");
				BalancerMain.mdefplus94 = rset.getInt("mdefplus94");
				BalancerMain.patkplus94 = rset.getInt("patkplus94");
				BalancerMain.patksplus94 = rset.getInt("patksplus94");
				BalancerMain.pdefplus94 = rset.getInt("pdefplus94");
				BalancerMain.walkplus94 = rset.getInt("walkplus94");

				BalancerMain.accplus95 = rset.getInt("accplus95");
				BalancerMain.evasionplus95 = rset.getInt("evasionplus95");
				BalancerMain.hpplus95 = rset.getInt("hpplus95");
				BalancerMain.cpplus95 = rset.getInt("cpplus95");
				BalancerMain.mpplus95 = rset.getInt("mpplus95");
				BalancerMain.matkplus95 = rset.getInt("matkplus95");
				BalancerMain.matksplus95 = rset.getInt("matksplus95");
				BalancerMain.mdefplus95 = rset.getInt("mdefplus95");
				BalancerMain.patkplus95 = rset.getInt("patkplus95");
				BalancerMain.patksplus95 = rset.getInt("patksplus95");
				BalancerMain.pdefplus95 = rset.getInt("pdefplus95");
				BalancerMain.walkplus95 = rset.getInt("walkplus95");

				BalancerMain.accplus96 = rset.getInt("accplus96");
				BalancerMain.evasionplus96 = rset.getInt("evasionplus96");
				BalancerMain.hpplus96 = rset.getInt("hpplus96");
				BalancerMain.cpplus96 = rset.getInt("cpplus96");
				BalancerMain.mpplus96 = rset.getInt("mpplus96");
				BalancerMain.matkplus96 = rset.getInt("matkplus96");
				BalancerMain.matksplus96 = rset.getInt("matksplus96");
				BalancerMain.mdefplus96 = rset.getInt("mdefplus96");
				BalancerMain.patkplus96 = rset.getInt("patkplus96");
				BalancerMain.patksplus96 = rset.getInt("patksplus96");
				BalancerMain.pdefplus96 = rset.getInt("pdefplus96");
				BalancerMain.walkplus96 = rset.getInt("walkplus96");

				BalancerMain.accplus97 = rset.getInt("accplus97");
				BalancerMain.evasionplus97 = rset.getInt("evasionplus97");
				BalancerMain.hpplus97 = rset.getInt("hpplus97");
				BalancerMain.cpplus97 = rset.getInt("cpplus97");
				BalancerMain.mpplus97 = rset.getInt("mpplus97");
				BalancerMain.matkplus97 = rset.getInt("matkplus97");
				BalancerMain.matksplus97 = rset.getInt("matksplus97");
				BalancerMain.mdefplus97 = rset.getInt("mdefplus97");
				BalancerMain.patkplus97 = rset.getInt("patkplus97");
				BalancerMain.patksplus97 = rset.getInt("patksplus97");
				BalancerMain.pdefplus97 = rset.getInt("pdefplus97");
				BalancerMain.walkplus97 = rset.getInt("walkplus97");

				BalancerMain.accplus98 = rset.getInt("accplus98");
				BalancerMain.evasionplus98 = rset.getInt("evasionplus98");
				BalancerMain.hpplus98 = rset.getInt("hpplus98");
				BalancerMain.cpplus98 = rset.getInt("cpplus98");
				BalancerMain.mpplus98 = rset.getInt("mpplus98");
				BalancerMain.matkplus98 = rset.getInt("matkplus98");
				BalancerMain.matksplus98 = rset.getInt("matksplus98");
				BalancerMain.mdefplus98 = rset.getInt("mdefplus98");
				BalancerMain.patkplus98 = rset.getInt("patkplus98");
				BalancerMain.patksplus98 = rset.getInt("patksplus98");
				BalancerMain.pdefplus98 = rset.getInt("pdefplus98");
				BalancerMain.walkplus98 = rset.getInt("walkplus98");

				BalancerMain.accplus99 = rset.getInt("accplus99");
				BalancerMain.evasionplus99 = rset.getInt("evasionplus99");
				BalancerMain.hpplus99 = rset.getInt("hpplus99");
				BalancerMain.cpplus99 = rset.getInt("cpplus99");
				BalancerMain.mpplus99 = rset.getInt("mpplus99");
				BalancerMain.matkplus99 = rset.getInt("matkplus99");
				BalancerMain.matksplus99 = rset.getInt("matksplus99");
				BalancerMain.mdefplus99 = rset.getInt("mdefplus99");
				BalancerMain.patkplus99 = rset.getInt("patkplus99");
				BalancerMain.patksplus99 = rset.getInt("patksplus99");
				BalancerMain.pdefplus99 = rset.getInt("pdefplus99");
				BalancerMain.walkplus99 = rset.getInt("walkplus99");

				BalancerMain.accplus100 = rset.getInt("accplus100");
				BalancerMain.evasionplus100 = rset.getInt("evasionplus100");
				BalancerMain.hpplus100 = rset.getInt("hpplus100");
				BalancerMain.cpplus100 = rset.getInt("cpplus100");
				BalancerMain.mpplus100 = rset.getInt("mpplus100");
				BalancerMain.matkplus100 = rset.getInt("matkplus100");
				BalancerMain.matksplus100 = rset.getInt("matksplus100");
				BalancerMain.mdefplus100 = rset.getInt("mdefplus100");
				BalancerMain.patkplus100 = rset.getInt("patkplus100");
				BalancerMain.patksplus100 = rset.getInt("patksplus100");
				BalancerMain.pdefplus100 = rset.getInt("pdefplus100");
				BalancerMain.walkplus100 = rset.getInt("walkplus100");

				BalancerMain.accplus101 = rset.getInt("accplus101");
				BalancerMain.evasionplus101 = rset.getInt("evasionplus101");
				BalancerMain.hpplus101 = rset.getInt("hpplus101");
				BalancerMain.cpplus101 = rset.getInt("cpplus101");
				BalancerMain.mpplus101 = rset.getInt("mpplus101");
				BalancerMain.matkplus101 = rset.getInt("matkplus101");
				BalancerMain.matksplus101 = rset.getInt("matksplus101");
				BalancerMain.mdefplus101 = rset.getInt("mdefplus101");
				BalancerMain.patkplus101 = rset.getInt("patkplus101");
				BalancerMain.patksplus101 = rset.getInt("patksplus101");
				BalancerMain.pdefplus101 = rset.getInt("pdefplus101");
				BalancerMain.walkplus101 = rset.getInt("walkplus101");

				BalancerMain.accplus102 = rset.getInt("accplus102");
				BalancerMain.evasionplus102 = rset.getInt("evasionplus102");
				BalancerMain.hpplus102 = rset.getInt("hpplus102");
				BalancerMain.cpplus102 = rset.getInt("cpplus102");
				BalancerMain.mpplus102 = rset.getInt("mpplus102");
				BalancerMain.matkplus102 = rset.getInt("matkplus102");
				BalancerMain.matksplus102 = rset.getInt("matksplus102");
				BalancerMain.mdefplus102 = rset.getInt("mdefplus102");
				BalancerMain.patkplus102 = rset.getInt("patkplus102");
				BalancerMain.patksplus102 = rset.getInt("patksplus102");
				BalancerMain.pdefplus102 = rset.getInt("pdefplus102");
				BalancerMain.walkplus102 = rset.getInt("walkplus102");

				BalancerMain.accplus103 = rset.getInt("accplus103");
				BalancerMain.evasionplus103 = rset.getInt("evasionplus103");
				BalancerMain.hpplus103 = rset.getInt("hpplus103");
				BalancerMain.cpplus103 = rset.getInt("cpplus103");
				BalancerMain.mpplus103 = rset.getInt("mpplus103");
				BalancerMain.matkplus103 = rset.getInt("matkplus103");
				BalancerMain.matksplus103 = rset.getInt("matksplus103");
				BalancerMain.mdefplus103 = rset.getInt("mdefplus103");
				BalancerMain.patkplus103 = rset.getInt("patkplus103");
				BalancerMain.patksplus103 = rset.getInt("patksplus103");
				BalancerMain.pdefplus103 = rset.getInt("pdefplus103");
				BalancerMain.walkplus103 = rset.getInt("walkplus103");

				BalancerMain.accplus104 = rset.getInt("accplus104");
				BalancerMain.evasionplus104 = rset.getInt("evasionplus104");
				BalancerMain.hpplus104 = rset.getInt("hpplus104");
				BalancerMain.cpplus104 = rset.getInt("cpplus104");
				BalancerMain.mpplus104 = rset.getInt("mpplus104");
				BalancerMain.matkplus104 = rset.getInt("matkplus104");
				BalancerMain.matksplus104 = rset.getInt("matksplus104");
				BalancerMain.mdefplus104 = rset.getInt("mdefplus104");
				BalancerMain.patkplus104 = rset.getInt("patkplus104");
				BalancerMain.patksplus104 = rset.getInt("patksplus104");
				BalancerMain.pdefplus104 = rset.getInt("pdefplus104");
				BalancerMain.walkplus104 = rset.getInt("walkplus104");

				BalancerMain.accplus105 = rset.getInt("accplus105");
				BalancerMain.evasionplus105 = rset.getInt("evasionplus105");
				BalancerMain.hpplus105 = rset.getInt("hpplus105");
				BalancerMain.cpplus105 = rset.getInt("cpplus105");
				BalancerMain.mpplus105 = rset.getInt("mpplus105");
				BalancerMain.matkplus105 = rset.getInt("matkplus105");
				BalancerMain.matksplus105 = rset.getInt("matksplus105");
				BalancerMain.mdefplus105 = rset.getInt("mdefplus105");
				BalancerMain.patkplus105 = rset.getInt("patkplus105");
				BalancerMain.patksplus105 = rset.getInt("patksplus105");
				BalancerMain.pdefplus105 = rset.getInt("pdefplus105");
				BalancerMain.walkplus105 = rset.getInt("walkplus105");

				BalancerMain.accplus106 = rset.getInt("accplus106");
				BalancerMain.evasionplus106 = rset.getInt("evasionplus106");
				BalancerMain.hpplus106 = rset.getInt("hpplus106");
				BalancerMain.cpplus106 = rset.getInt("cpplus106");
				BalancerMain.mpplus106 = rset.getInt("mpplus106");
				BalancerMain.matkplus106 = rset.getInt("matkplus106");
				BalancerMain.matksplus106 = rset.getInt("matksplus106");
				BalancerMain.mdefplus106 = rset.getInt("mdefplus106");
				BalancerMain.patkplus106 = rset.getInt("patkplus106");
				BalancerMain.patksplus106 = rset.getInt("patksplus106");
				BalancerMain.pdefplus106 = rset.getInt("pdefplus106");
				BalancerMain.walkplus106 = rset.getInt("walkplus106");

				BalancerMain.accplus107 = rset.getInt("accplus107");
				BalancerMain.evasionplus107 = rset.getInt("evasionplus107");
				BalancerMain.hpplus107 = rset.getInt("hpplus107");
				BalancerMain.cpplus107 = rset.getInt("cpplus107");
				BalancerMain.mpplus107 = rset.getInt("mpplus107");
				BalancerMain.matkplus107 = rset.getInt("matkplus107");
				BalancerMain.matksplus107 = rset.getInt("matksplus107");
				BalancerMain.mdefplus107 = rset.getInt("mdefplus107");
				BalancerMain.patkplus107 = rset.getInt("patkplus107");
				BalancerMain.patksplus107 = rset.getInt("patksplus107");
				BalancerMain.pdefplus107 = rset.getInt("pdefplus107");
				BalancerMain.walkplus107 = rset.getInt("walkplus107");

				BalancerMain.accplus108 = rset.getInt("accplus108");
				BalancerMain.evasionplus108 = rset.getInt("evasionplus108");
				BalancerMain.hpplus108 = rset.getInt("hpplus108");
				BalancerMain.cpplus108 = rset.getInt("cpplus108");
				BalancerMain.mpplus108 = rset.getInt("mpplus108");
				BalancerMain.matkplus108 = rset.getInt("matkplus108");
				BalancerMain.matksplus108 = rset.getInt("matksplus108");
				BalancerMain.mdefplus108 = rset.getInt("mdefplus108");
				BalancerMain.patkplus108 = rset.getInt("patkplus108");
				BalancerMain.patksplus108 = rset.getInt("patksplus108");
				BalancerMain.pdefplus108 = rset.getInt("pdefplus108");
				BalancerMain.walkplus108 = rset.getInt("walkplus108");

				BalancerMain.accplus109 = rset.getInt("accplus109");
				BalancerMain.evasionplus109 = rset.getInt("evasionplus109");
				BalancerMain.hpplus109 = rset.getInt("hpplus109");
				BalancerMain.cpplus109 = rset.getInt("cpplus109");
				BalancerMain.mpplus109 = rset.getInt("mpplus109");
				BalancerMain.matkplus109 = rset.getInt("matkplus109");
				BalancerMain.matksplus109 = rset.getInt("matksplus109");
				BalancerMain.mdefplus109 = rset.getInt("mdefplus109");
				BalancerMain.patkplus109 = rset.getInt("patkplus109");
				BalancerMain.patksplus109 = rset.getInt("patksplus109");
				BalancerMain.pdefplus109 = rset.getInt("pdefplus109");
				BalancerMain.walkplus109 = rset.getInt("walkplus109");

				BalancerMain.accplus110 = rset.getInt("accplus110");
				BalancerMain.evasionplus110 = rset.getInt("evasionplus110");
				BalancerMain.hpplus110 = rset.getInt("hpplus110");
				BalancerMain.cpplus110 = rset.getInt("cpplus110");
				BalancerMain.mpplus110 = rset.getInt("mpplus110");
				BalancerMain.matkplus110 = rset.getInt("matkplus110");
				BalancerMain.matksplus110 = rset.getInt("matksplus110");
				BalancerMain.mdefplus110 = rset.getInt("mdefplus110");
				BalancerMain.patkplus110 = rset.getInt("patkplus110");
				BalancerMain.patksplus110 = rset.getInt("patksplus110");
				BalancerMain.pdefplus110 = rset.getInt("pdefplus110");
				BalancerMain.walkplus110 = rset.getInt("walkplus110");

				BalancerMain.accplus111 = rset.getInt("accplus111");
				BalancerMain.evasionplus111 = rset.getInt("evasionplus111");
				BalancerMain.hpplus111 = rset.getInt("hpplus111");
				BalancerMain.cpplus111 = rset.getInt("cpplus111");
				BalancerMain.mpplus111 = rset.getInt("mpplus111");
				BalancerMain.matkplus111 = rset.getInt("matkplus111");
				BalancerMain.matksplus111 = rset.getInt("matksplus111");
				BalancerMain.mdefplus111 = rset.getInt("mdefplus111");
				BalancerMain.patkplus111 = rset.getInt("patkplus111");
				BalancerMain.patksplus111 = rset.getInt("patksplus111");
				BalancerMain.pdefplus111 = rset.getInt("pdefplus111");
				BalancerMain.walkplus111 = rset.getInt("walkplus111");

				BalancerMain.accplus112 = rset.getInt("accplus112");
				BalancerMain.evasionplus112 = rset.getInt("evasionplus112");
				BalancerMain.hpplus112 = rset.getInt("hpplus112");
				BalancerMain.cpplus112 = rset.getInt("cpplus112");
				BalancerMain.mpplus112 = rset.getInt("mpplus112");
				BalancerMain.matkplus112 = rset.getInt("matkplus112");
				BalancerMain.matksplus112 = rset.getInt("matksplus112");
				BalancerMain.mdefplus112 = rset.getInt("mdefplus112");
				BalancerMain.patkplus112 = rset.getInt("patkplus112");
				BalancerMain.patksplus112 = rset.getInt("patksplus112");
				BalancerMain.pdefplus112 = rset.getInt("pdefplus112");
				BalancerMain.walkplus112 = rset.getInt("walkplus112");

				BalancerMain.accplus113 = rset.getInt("accplus113");
				BalancerMain.evasionplus113 = rset.getInt("evasionplus113");
				BalancerMain.hpplus113 = rset.getInt("hpplus113");
				BalancerMain.cpplus113 = rset.getInt("cpplus113");
				BalancerMain.mpplus113 = rset.getInt("mpplus113");
				BalancerMain.matkplus113 = rset.getInt("matkplus113");
				BalancerMain.matksplus113 = rset.getInt("matksplus113");
				BalancerMain.mdefplus113 = rset.getInt("mdefplus113");
				BalancerMain.patkplus113 = rset.getInt("patkplus113");
				BalancerMain.patksplus113 = rset.getInt("patksplus113");
				BalancerMain.pdefplus113 = rset.getInt("pdefplus113");
				BalancerMain.walkplus113 = rset.getInt("walkplus113");

				BalancerMain.accplus114 = rset.getInt("accplus114");
				BalancerMain.evasionplus114 = rset.getInt("evasionplus114");
				BalancerMain.hpplus114 = rset.getInt("hpplus114");
				BalancerMain.cpplus114 = rset.getInt("cpplus114");
				BalancerMain.mpplus114 = rset.getInt("mpplus114");
				BalancerMain.matkplus114 = rset.getInt("matkplus114");
				BalancerMain.matksplus114 = rset.getInt("matksplus114");
				BalancerMain.mdefplus114 = rset.getInt("mdefplus114");
				BalancerMain.patkplus114 = rset.getInt("patkplus114");
				BalancerMain.patksplus114 = rset.getInt("patksplus114");
				BalancerMain.pdefplus114 = rset.getInt("pdefplus114");
				BalancerMain.walkplus114 = rset.getInt("walkplus114");

				BalancerMain.accplus115 = rset.getInt("accplus115");
				BalancerMain.evasionplus115 = rset.getInt("evasionplus115");
				BalancerMain.hpplus115 = rset.getInt("hpplus115");
				BalancerMain.cpplus115 = rset.getInt("cpplus115");
				BalancerMain.mpplus115 = rset.getInt("mpplus115");
				BalancerMain.matkplus115 = rset.getInt("matkplus115");
				BalancerMain.matksplus115 = rset.getInt("matksplus115");
				BalancerMain.mdefplus115 = rset.getInt("mdefplus115");
				BalancerMain.patkplus115 = rset.getInt("patkplus115");
				BalancerMain.patksplus115 = rset.getInt("patksplus115");
				BalancerMain.pdefplus115 = rset.getInt("pdefplus115");
				BalancerMain.walkplus115 = rset.getInt("walkplus115");

				BalancerMain.accplus116 = rset.getInt("accplus116");
				BalancerMain.evasionplus116 = rset.getInt("evasionplus116");
				BalancerMain.hpplus116 = rset.getInt("hpplus116");
				BalancerMain.cpplus116 = rset.getInt("cpplus116");
				BalancerMain.mpplus116 = rset.getInt("mpplus116");
				BalancerMain.matkplus116 = rset.getInt("matkplus116");
				BalancerMain.matksplus116 = rset.getInt("matksplus116");
				BalancerMain.mdefplus116 = rset.getInt("mdefplus116");
				BalancerMain.patkplus116 = rset.getInt("patkplus116");
				BalancerMain.patksplus116 = rset.getInt("patksplus116");
				BalancerMain.pdefplus116 = rset.getInt("pdefplus116");
				BalancerMain.walkplus116 = rset.getInt("walkplus116");

				BalancerMain.accplus117 = rset.getInt("accplus117");
				BalancerMain.evasionplus117 = rset.getInt("evasionplus117");
				BalancerMain.hpplus117 = rset.getInt("hpplus117");
				BalancerMain.cpplus117 = rset.getInt("cpplus117");
				BalancerMain.mpplus117 = rset.getInt("mpplus117");
				BalancerMain.matkplus117 = rset.getInt("matkplus117");
				BalancerMain.matksplus117 = rset.getInt("matksplus117");
				BalancerMain.mdefplus117 = rset.getInt("mdefplus117");
				BalancerMain.patkplus117 = rset.getInt("patkplus117");
				BalancerMain.patksplus117 = rset.getInt("patksplus117");
				BalancerMain.pdefplus117 = rset.getInt("pdefplus117");
				BalancerMain.walkplus117 = rset.getInt("walkplus117");

				BalancerMain.accplus118 = rset.getInt("accplus118");
				BalancerMain.evasionplus118 = rset.getInt("evasionplus118");
				BalancerMain.hpplus118 = rset.getInt("hpplus118");
				BalancerMain.cpplus118 = rset.getInt("cpplus118");
				BalancerMain.mpplus118 = rset.getInt("mpplus118");
				BalancerMain.matkplus118 = rset.getInt("matkplus118");
				BalancerMain.matksplus118 = rset.getInt("matksplus118");
				BalancerMain.mdefplus118 = rset.getInt("mdefplus118");
				BalancerMain.patkplus118 = rset.getInt("patkplus118");
				BalancerMain.patksplus118 = rset.getInt("patksplus118");
				BalancerMain.pdefplus118 = rset.getInt("pdefplus118");
				BalancerMain.walkplus118 = rset.getInt("walkplus118");
			}

			rset.close();
			statement.close();
		}
		catch(Exception e)
		{
			_log.log(Level.WARNING, "BalanceLoad: Problem loading class stats.");
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}
}