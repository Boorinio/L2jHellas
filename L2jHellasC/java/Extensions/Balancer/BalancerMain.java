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

import com.l2jhellas.gameserver.model.L2Character;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.actor.stat.CharStat;
import com.l2jhellas.gameserver.network.L2GameClient;

public class BalancerMain extends CharStat
{

	/**
	 * @param activeChar
	 */
	public BalancerMain(L2Character activeChar)
	{
		super(activeChar);
	}
	
	L2PcInstance activeChar = getActiveChar();
	
	//Duelist mods
	public static int accplus88 = 0;
	public static int evasionplus88 = 0;
	public static int hpplus88 = 0;
	public static int cpplus88 = 0;
	public static int mpplus88 = 0;
	public static int matkplus88 = 0;
	public static int matksplus88 = 0;
	public static int mdefplus88 = 0;
	public static int patkplus88 = 0;
	public static int patksplus88 = 0;
	public static int pdefplus88 = 0;
	
	public static void setAccplus88(int val)
	{
		accplus88 = val;
	}
	 	
	public static void setEvasionplus88(int val)
	{
		evasionplus88 = val;
	}

	public static void setCpplus88(int val)
	{
		cpplus88 = val;
	}
	
	public static void setHpplus88(int val)
	{
		hpplus88 = val;
	}
	
	public static void setMpplus88(int val)
	{
		mpplus88 = val;
	}
	
	public static void setMatkplus88(int val)
	{
		matkplus88 = val;
	}

	public static void setMatksplus88(int val)
	{
		matksplus88 = val;
	}

	public static void setMdefplus88(int val)
	{
		mdefplus88 = val;
	}

	public static void setPatkplus88(int val)
	{
		patkplus88 = val;
	}

	public static void setPatksplus88(int val)
	{
		patksplus88 = val;
	}

	public static void setPdefplus88(int val)
	{
		pdefplus88 = val;
	}

	//DreadNought mods
	public static int accplus89 = 0;
	public static int evasionplus89 = 0;
	public static int hpplus89 = 0;
	public static int cpplus89 = 0;
	public static int mpplus89 = 0;
	public static int matkplus89 = 0;
	public static int matksplus89 = 0;
	public static int mdefplus89 = 0;
	public static int patkplus89 = 0;
	public static int patksplus89 = 0;
	public static int pdefplus89 = 0;
	
	public static void setAccplus89(int val)
	{
		accplus89 = val;
	}
	 	
	public static void setEvasionplus89(int val)
	{
		evasionplus89 = val;
	}

	public static void setCpplus89(int val)
	{
		cpplus89 = val;
	}
	
	public static void setHpplus89(int val)
	{
		hpplus89 = val;
	}
	
	public static void setMpplus89(int val)
	{
		mpplus89 = val;
	}
	
	public static void setMatkplus89(int val)
	{
		matkplus89 = val;
	}

	public static void setMatksplus89(int val)
	{
		matksplus89 = val;
	}

	public static void setMdefplus89(int val)
	{
		mdefplus89 = val;
	}

	public static void setPatkplus89(int val)
	{
		patkplus89 = val;
	}

	public static void setPatksplus89(int val)
	{
		patksplus89 = val;
	}

	public static void setPdefplus89(int val)
	{
		pdefplus89 = val;
	}
	
	//Phoenix Knight mods
	public static int accplus90 = 0;
	public static int evasionplus90 = 0;
	public static int hpplus90 = 0;
	public static int cpplus90 = 0;
	public static int mpplus90 = 0;
	public static int matkplus90 = 0;
	public static int matksplus90 = 0;
	public static int mdefplus90 = 0;
	public static int patkplus90 = 0;
	public static int patksplus90 = 0;
	public static int pdefplus90 = 0;
	
	public static void setAccplus90(int val)
	{
		accplus90 = val;
	}
	 	
	public static void setEvasionplus90(int val)
	{
		evasionplus90 = val;
	}

	public static void setCpplus90(int val)
	{
		cpplus90 = val;
	}
	
	public static void setHpplus90(int val)
	{
		hpplus90 = val;
	}
	
	public static void setMpplus90(int val)
	{
		mpplus90 = val;
	}
	
	public static void setMatkplus90(int val)
	{
		matkplus90 = val;
	}

	public static void setMatksplus90(int val)
	{
		matksplus90 = val;
	}

	public static void setMdefplus90(int val)
	{
		mdefplus90 = val;
	}

	public static void setPatkplus90(int val)
	{
		patkplus90 = val;
	}

	public static void setPatksplus90(int val)
	{
		patksplus90 = val;
	}

	public static void setPdefplus90(int val)
	{
		pdefplus90 = val;
	}
	
	//Hell Knight mods
	public static int accplus91 = 0;
	public static int evasionplus91 = 0;
	public static int hpplus91 = 0;
	public static int cpplus91 = 0;
	public static int mpplus91 = 0;
	public static int matkplus91 = 0;
	public static int matksplus91 = 0;
	public static int mdefplus91 = 0;
	public static int patkplus91 = 0;
	public static int patksplus91 = 0;
	public static int pdefplus91 = 0;
	
	public static void setAccplus91(int val)
	{
		accplus91 = val;
	}
	 	
	public static void setEvasionplus91(int val)
	{
		evasionplus91 = val;
	}

	public static void setCpplus91(int val)
	{
		cpplus91 = val;
	}
	
	public static void setHpplus91(int val)
	{
		hpplus91 = val;
	}
	
	public static void setMpplus91(int val)
	{
		mpplus91 = val;
	}
	
	public static void setMatkplus91(int val)
	{
		matkplus91 = val;
	}

	public static void setMatksplus91(int val)
	{
		matksplus91 = val;
	}

	public static void setMdefplus91(int val)
	{
		mdefplus91 = val;
	}

	public static void setPatkplus91(int val)
	{
		patkplus91 = val;
	}

	public static void setPatksplus91(int val)
	{
		patksplus91 = val;
	}

	public static void setPdefplus91(int val)
	{
		pdefplus91 = val;
	}

	//Sagittarius mods
	public static int accplus92 = 0;
	public static int evasionplus92 = 0;
	public static int hpplus92 = 0;
	public static int cpplus92 = 0;
	public static int mpplus92 = 0;
	public static int matkplus92 = 0;
	public static int matksplus92 = 0;
	public static int mdefplus92 = 0;
	public static int patkplus92 = 0;
	public static int patksplus92 = 0;
	public static int pdefplus92 = 0;
	
	public static void setAccplus92(int val)
	{
		accplus92 = val;
	}
	 	
	public static void setEvasionplus92(int val)
	{
		evasionplus92 = val;
	}

	public static void setCpplus92(int val)
	{
		cpplus92 = val;
	}
	
	public static void setHpplus92(int val)
	{
		hpplus92 = val;
	}
	
	public static void setMpplus92(int val)
	{
		mpplus92 = val;
	}
	
	public static void setMatkplus92(int val)
	{
		matkplus92 = val;
	}

	public static void setMatksplus92(int val)
	{
		matksplus92 = val;
	}

	public static void setMdefplus92(int val)
	{
		mdefplus92 = val;
	}

	public static void setPatkplus92(int val)
	{
		patkplus92 = val;
	}

	public static void setPatksplus92(int val)
	{
		patksplus92 = val;
	}

	public static void setPdefplus92(int val)
	{
		pdefplus92 = val;
	}
	
	//Adventurer mods
	public static int accplus93 = 0;
	public static int evasionplus93 = 0;
	public static int hpplus93 = 0;
	public static int cpplus93 = 0;
	public static int mpplus93 = 0;
	public static int matkplus93 = 0;
	public static int matksplus93 = 0;
	public static int mdefplus93 = 0;
	public static int patkplus93 = 0;
	public static int patksplus93 = 0;
	public static int pdefplus93 = 0;
	
	public static void setAccplus93(int val)
	{
		accplus93 = val;
	}
	 	
	public static void setEvasionplus93(int val)
	{
		evasionplus93 = val;
	}

	public static void setCpplus93(int val)
	{
		cpplus93 = val;
	}
	
	public static void setHpplus93(int val)
	{
		hpplus93 = val;
	}
	
	public static void setMpplus93(int val)
	{
		mpplus93 = val;
	}
	
	public static void setMatkplus93(int val)
	{
		matkplus93 = val;
	}

	public static void setMatksplus93(int val)
	{
		matksplus93 = val;
	}

	public static void setMdefplus93(int val)
	{
		mdefplus93 = val;
	}

	public static void setPatkplus93(int val)
	{
		patkplus93 = val;
	}

	public static void setPatksplus93(int val)
	{
		patksplus93 = val;
	}

	public static void setPdefplus93(int val)
	{
		pdefplus93 = val;
	}
	
	//Archmage mods
	public static int accplus94 = 0;
	public static int evasionplus94 = 0;
	public static int hpplus94 = 0;
	public static int cpplus94 = 0;
	public static int mpplus94 = 0;
	public static int matkplus94 = 0;
	public static int matksplus94 = 0;
	public static int mdefplus94 = 0;
	public static int patkplus94 = 0;
	public static int patksplus94 = 0;
	public static int pdefplus94 = 0;
	
	public static void setAccplus94(int val)
	{
		accplus94 = val;
	}
	 	
	public static void setEvasionplus94(int val)
	{
		evasionplus94 = val;
	}

	public static void setCpplus94(int val)
	{
		cpplus94 = val;
	}
	
	public static void setHpplus94(int val)
	{
		hpplus94 = val;
	}
	
	public static void setMpplus94(int val)
	{
		mpplus94 = val;
	}
	
	public static void setMatkplus94(int val)
	{
		matkplus94 = val;
	}

	public static void setMatksplus94(int val)
	{
		matksplus94 = val;
	}

	public static void setMdefplus94(int val)
	{
		mdefplus94 = val;
	}

	public static void setPatkplus94(int val)
	{
		patkplus94 = val;
	}

	public static void setPatksplus94(int val)
	{
		patksplus94 = val;
	}

	public static void setPdefplus94(int val)
	{
		pdefplus94 = val;
	}
	
	//Soultaker mods
	public static int accplus95 = 0;
	public static int evasionplus95 = 0;
	public static int hpplus95 = 0;
	public static int cpplus95 = 0;
	public static int mpplus95 = 0;
	public static int matkplus95 = 0;
	public static int matksplus95 = 0;
	public static int mdefplus95 = 0;
	public static int patkplus95 = 0;
	public static int patksplus95 = 0;
	public static int pdefplus95 = 0;
	
	public static void setAccplus95(int val)
	{
		accplus95 = val;
	}
	 	
	public static void setEvasionplus95(int val)
	{
		evasionplus95 = val;
	}

	public static void setCpplus95(int val)
	{
		cpplus95 = val;
	}
	
	public static void setHpplus95(int val)
	{
		hpplus95 = val;
	}
	
	public static void setMpplus95(int val)
	{
		mpplus95 = val;
	}
	
	public static void setMatkplus95(int val)
	{
		matkplus95 = val;
	}

	public static void setMatksplus95(int val)
	{
		matksplus95 = val;
	}

	public static void setMdefplus95(int val)
	{
		mdefplus95 = val;
	}

	public static void setPatkplus95(int val)
	{
		patkplus95 = val;
	}

	public static void setPatksplus95(int val)
	{
		patksplus95 = val;
	}

	public static void setPdefplus95(int val)
	{
		pdefplus95 = val;
	}
	
	//Arcana Lord mods
	public static int accplus96 = 0;
	public static int evasionplus96 = 0;
	public static int hpplus96 = 0;
	public static int cpplus96 = 0;
	public static int mpplus96 = 0;
	public static int matkplus96 = 0;
	public static int matksplus96 = 0;
	public static int mdefplus96 = 0;
	public static int patkplus96 = 0;
	public static int patksplus96 = 0;
	public static int pdefplus96 = 0;
	
	public static void setAccplus96(int val)
	{
		accplus96 = val;
	}
	 	
	public static void setEvasionplus96(int val)
	{
		evasionplus96 = val;
	}

	public static void setCpplus96(int val)
	{
		cpplus96 = val;
	}
	
	public static void setHpplus96(int val)
	{
		hpplus96 = val;
	}
	
	public static void setMpplus96(int val)
	{
		mpplus96 = val;
	}
	
	public static void setMatkplus96(int val)
	{
		matkplus96 = val;
	}

	public static void setMatksplus96(int val)
	{
		matksplus96 = val;
	}

	public static void setMdefplus96(int val)
	{
		mdefplus96 = val;
	}

	public static void setPatkplus96(int val)
	{
		patkplus96 = val;
	}

	public static void setPatksplus96(int val)
	{
		patksplus96 = val;
	}

	public static void setPdefplus96(int val)
	{
		pdefplus96 = val;
	}
	
	//Cardinal mods
	public static int accplus97 = 0;
	public static int evasionplus97 = 0;
	public static int hpplus97 = 0;
	public static int cpplus97 = 0;
	public static int mpplus97 = 0;
	public static int matkplus97 = 0;
	public static int matksplus97 = 0;
	public static int mdefplus97 = 0;
	public static int patkplus97 = 0;
	public static int patksplus97 = 0;
	public static int pdefplus97 = 0;
	
	public static void setAccplus97(int val)
	{
		accplus97 = val;
	}
	 	
	public static void setEvasionplus97(int val)
	{
		evasionplus97 = val;
	}

	public static void setCpplus97(int val)
	{
		cpplus97 = val;
	}
	
	public static void setHpplus97(int val)
	{
		hpplus97 = val;
	}
	
	public static void setMpplus97(int val)
	{
		mpplus97 = val;
	}
	
	public static void setMatkplus97(int val)
	{
		matkplus97 = val;
	}

	public static void setMatksplus97(int val)
	{
		matksplus97 = val;
	}

	public static void setMdefplus97(int val)
	{
		mdefplus97 = val;
	}

	public static void setPatkplus97(int val)
	{
		patkplus97 = val;
	}

	public static void setPatksplus97(int val)
	{
		patksplus97 = val;
	}

	public static void setPdefplus97(int val)
	{
		pdefplus97 = val;
	}
	
	//Hierophant mods
	public static int accplus98 = 0;
	public static int evasionplus98 = 0;
	public static int hpplus98 = 0;
	public static int cpplus98 = 0;
	public static int mpplus98 = 0;
	public static int matkplus98 = 0;
	public static int matksplus98 = 0;
	public static int mdefplus98 = 0;
	public static int patkplus98 = 0;
	public static int patksplus98 = 0;
	public static int pdefplus98 = 0;
	
	public static void setAccplus98(int val)
	{
		accplus98 = val;
	}
	 	
	public static void setEvasionplus98(int val)
	{
		evasionplus98 = val;
	}

	public static void setCpplus98(int val)
	{
		cpplus98 = val;
	}
	
	public static void setHpplus98(int val)
	{
		hpplus98 = val;
	}
	
	public static void setMpplus98(int val)
	{
		mpplus98 = val;
	}
	
	public static void setMatkplus98(int val)
	{
		matkplus98 = val;
	}

	public static void setMatksplus98(int val)
	{
		matksplus98 = val;
	}

	public static void setMdefplus98(int val)
	{
		mdefplus98 = val;
	}

	public static void setPatkplus98(int val)
	{
		patkplus98 = val;
	}

	public static void setPatksplus98(int val)
	{
		patksplus98 = val;
	}

	public static void setPdefplus98(int val)
	{
		pdefplus98 = val;
	}
	
	//Eva Templar mods
	public static int accplus99 = 0;
	public static int evasionplus99 = 0;
	public static int hpplus99 = 0;
	public static int cpplus99 = 0;
	public static int mpplus99 = 0;
	public static int matkplus99 = 0;
	public static int matksplus99 = 0;
	public static int mdefplus99 = 0;
	public static int patkplus99 = 0;
	public static int patksplus99 = 0;
	public static int pdefplus99 = 0;
	
	public static void setAccplus99(int val)
	{
		accplus99 = val;
	}
	 	
	public static void setEvasionplus99(int val)
	{
		evasionplus99 = val;
	}

	public static void setCpplus99(int val)
	{
		cpplus99 = val;
	}
	
	public static void setHpplus99(int val)
	{
		hpplus99 = val;
	}
	
	public static void setMpplus99(int val)
	{
		mpplus99 = val;
	}
	
	public static void setMatkplus99(int val)
	{
		matkplus99 = val;
	}

	public static void setMatksplus99(int val)
	{
		matksplus99 = val;
	}

	public static void setMdefplus99(int val)
	{
		mdefplus99 = val;
	}

	public static void setPatkplus99(int val)
	{
		patkplus99 = val;
	}

	public static void setPatksplus99(int val)
	{
		patksplus99 = val;
	}

	public static void setPdefplus99(int val)
	{
		pdefplus99 = val;
	}
	
	//Sword Muse mods
	public static int accplus100 = 0;
	public static int evasionplus100 = 0;
	public static int hpplus100 = 0;
	public static int cpplus100 = 0;
	public static int mpplus100 = 0;
	public static int matkplus100 = 0;
	public static int matksplus100 = 0;
	public static int mdefplus100 = 0;
	public static int patkplus100 = 0;
	public static int patksplus100 = 0;
	public static int pdefplus100 = 0;
	
	public static void setAccplus100(int val)
	{
		accplus100 = val;
	}
	 	
	public static void setEvasionplus100(int val)
	{
		evasionplus100 = val;
	}

	public static void setCpplus100(int val)
	{
		cpplus100 = val;
	}
	
	public static void setHpplus100(int val)
	{
		hpplus100 = val;
	}
	
	public static void setMpplus100(int val)
	{
		mpplus100 = val;
	}
	
	public static void setMatkplus100(int val)
	{
		matkplus100 = val;
	}

	public static void setMatksplus100(int val)
	{
		matksplus100 = val;
	}

	public static void setMdefplus100(int val)
	{
		mdefplus100 = val;
	}

	public static void setPatkplus100(int val)
	{
		patkplus100 = val;
	}

	public static void setPatksplus100(int val)
	{
		patksplus100 = val;
	}

	public static void setPdefplus100(int val)
	{
		pdefplus100 = val;
	}
	
	//Wind Rider mods
	public static int accplus101 = 0;
	public static int evasionplus101 = 0;
	public static int hpplus101 = 0;
	public static int cpplus101 = 0;
	public static int mpplus101 = 0;
	public static int matkplus101 = 0;
	public static int matksplus101 = 0;
	public static int mdefplus101 = 0;
	public static int patkplus101 = 0;
	public static int patksplus101 = 0;
	public static int pdefplus101 = 0;
	
	public static void setAccplus101(int val)
	{
		accplus101 = val;
	}
	 	
	public static void setEvasionplus101(int val)
	{
		evasionplus101 = val;
	}

	public static void setCpplus101(int val)
	{
		cpplus101 = val;
	}
	
	public static void setHpplus101(int val)
	{
		hpplus101 = val;
	}
	
	public static void setMpplus101(int val)
	{
		mpplus101 = val;
	}
	
	public static void setMatkplus101(int val)
	{
		matkplus101 = val;
	}

	public static void setMatksplus101(int val)
	{
		matksplus101 = val;
	}

	public static void setMdefplus101(int val)
	{
		mdefplus101 = val;
	}

	public static void setPatkplus101(int val)
	{
		patkplus101 = val;
	}

	public static void setPatksplus101(int val)
	{
		patksplus101 = val;
	}

	public static void setPdefplus101(int val)
	{
		pdefplus101 = val;
	}
	
	//Moonlight Sentinel mods
	public static int accplus102 = 0;
	public static int evasionplus102 = 0;
	public static int hpplus102 = 0;
	public static int cpplus102 = 0;
	public static int mpplus102 = 0;
	public static int matkplus102 = 0;
	public static int matksplus102 = 0;
	public static int mdefplus102 = 0;
	public static int patkplus102 = 0;
	public static int patksplus102 = 0;
	public static int pdefplus102 = 0;
	
	public static void setAccplus102(int val)
	{
		accplus102 = val;
	}
	 	
	public static void setEvasionplus102(int val)
	{
		evasionplus102 = val;
	}

	public static void setCpplus102(int val)
	{
		cpplus102 = val;
	}
	
	public static void setHpplus102(int val)
	{
		hpplus102 = val;
	}
	
	public static void setMpplus102(int val)
	{
		mpplus102 = val;
	}
	
	public static void setMatkplus102(int val)
	{
		matkplus102 = val;
	}

	public static void setMatksplus102(int val)
	{
		matksplus102 = val;
	}

	public static void setMdefplus102(int val)
	{
		mdefplus102 = val;
	}

	public static void setPatkplus102(int val)
	{
		patkplus102 = val;
	}

	public static void setPatksplus102(int val)
	{
		patksplus102 = val;
	}

	public static void setPdefplus102(int val)
	{
		pdefplus102 = val;
	}
	
	//Mystic Muse mods
	public static int accplus103 = 0;
	public static int evasionplus103 = 0;
	public static int hpplus103 = 0;
	public static int cpplus103 = 0;
	public static int mpplus103 = 0;
	public static int matkplus103 = 0;
	public static int matksplus103 = 0;
	public static int mdefplus103 = 0;
	public static int patkplus103 = 0;
	public static int patksplus103 = 0;
	public static int pdefplus103 = 0;
	
	public static void setAccplus103(int val)
	{
		accplus103 = val;
	}
	 	
	public static void setEvasionplus103(int val)
	{
		evasionplus103 = val;
	}

	public static void setCpplus103(int val)
	{
		cpplus103 = val;
	}
	
	public static void setHpplus103(int val)
	{
		hpplus103 = val;
	}
	
	public static void setMpplus103(int val)
	{
		mpplus103 = val;
	}
	
	public static void setMatkplus103(int val)
	{
		matkplus103 = val;
	}

	public static void setMatksplus103(int val)
	{
		matksplus103 = val;
	}

	public static void setMdefplus103(int val)
	{
		mdefplus103 = val;
	}

	public static void setPatkplus103(int val)
	{
		patkplus103 = val;
	}

	public static void setPatksplus103(int val)
	{
		patksplus103 = val;
	}

	public static void setPdefplus103(int val)
	{
		pdefplus103 = val;
	}
	
	//Elemental Master mods
	public static int accplus104 = 0;
	public static int evasionplus104 = 0;
	public static int hpplus104 = 0;
	public static int cpplus104 = 0;
	public static int mpplus104 = 0;
	public static int matkplus104 = 0;
	public static int matksplus104 = 0;
	public static int mdefplus104 = 0;
	public static int patkplus104 = 0;
	public static int patksplus104 = 0;
	public static int pdefplus104 = 0;
	
	public static void setAccplus104(int val)
	{
		accplus104 = val;
	}
	 	
	public static void setEvasionplus104(int val)
	{
		evasionplus104 = val;
	}

	public static void setCpplus104(int val)
	{
		cpplus104 = val;
	}
	
	public static void setHpplus104(int val)
	{
		hpplus104 = val;
	}
	
	public static void setMpplus104(int val)
	{
		mpplus104 = val;
	}
	
	public static void setMatkplus104(int val)
	{
		matkplus104 = val;
	}

	public static void setMatksplus104(int val)
	{
		matksplus104 = val;
	}

	public static void setMdefplus104(int val)
	{
		mdefplus104 = val;
	}

	public static void setPatkplus104(int val)
	{
		patkplus104 = val;
	}

	public static void setPatksplus104(int val)
	{
		patksplus104 = val;
	}

	public static void setPdefplus104(int val)
	{
		pdefplus104 = val;
	}
	
	//Eva Saint mods
	public static int accplus105 = 0;
	public static int evasionplus105 = 0;
	public static int hpplus105 = 0;
	public static int cpplus105 = 0;
	public static int mpplus105 = 0;
	public static int matkplus105 = 0;
	public static int matksplus105 = 0;
	public static int mdefplus105 = 0;
	public static int patkplus105 = 0;
	public static int patksplus105 = 0;
	public static int pdefplus105 = 0;
	
	public static void setAccplus105(int val)
	{
		accplus105 = val;
	}
	 	
	public static void setEvasionplus105(int val)
	{
		evasionplus105 = val;
	}

	public static void setCpplus105(int val)
	{
		cpplus105 = val;
	}
	
	public static void setHpplus105(int val)
	{
		hpplus105 = val;
	}
	
	public static void setMpplus105(int val)
	{
		mpplus105 = val;
	}
	
	public static void setMatkplus105(int val)
	{
		matkplus105 = val;
	}

	public static void setMatksplus105(int val)
	{
		matksplus105 = val;
	}

	public static void setMdefplus105(int val)
	{
		mdefplus105 = val;
	}

	public static void setPatkplus105(int val)
	{
		patkplus105 = val;
	}

	public static void setPatksplus105(int val)
	{
		patksplus105 = val;
	}

	public static void setPdefplus105(int val)
	{
		pdefplus105 = val;
	}
	
	//Shillien Templar mods
	public static int accplus106 = 0;
	public static int evasionplus106 = 0;
	public static int hpplus106 = 0;
	public static int cpplus106 = 0;
	public static int mpplus106 = 0;
	public static int matkplus106 = 0;
	public static int matksplus106 = 0;
	public static int mdefplus106 = 0;
	public static int patkplus106 = 0;
	public static int patksplus106 = 0;
	public static int pdefplus106 = 0;
	
	public static void setAccplus106(int val)
	{
		accplus106 = val;
	}
	 	
	public static void setEvasionplus106(int val)
	{
		evasionplus106 = val;
	}

	public static void setCpplus106(int val)
	{
		cpplus106 = val;
	}
	
	public static void setHpplus106(int val)
	{
		hpplus106 = val;
	}
	
	public static void setMpplus106(int val)
	{
		mpplus106 = val;
	}
	
	public static void setMatkplus106(int val)
	{
		matkplus106 = val;
	}

	public static void setMatksplus106(int val)
	{
		matksplus106 = val;
	}

	public static void setMdefplus106(int val)
	{
		mdefplus106 = val;
	}

	public static void setPatkplus106(int val)
	{
		patkplus106 = val;
	}

	public static void setPatksplus106(int val)
	{
		patksplus106 = val;
	}

	public static void setPdefplus106(int val)
	{
		pdefplus106 = val;
	}
	
	//Spectral Dancer mods
	public static int accplus107 = 0;
	public static int evasionplus107 = 0;
	public static int hpplus107 = 0;
	public static int cpplus107 = 0;
	public static int mpplus107 = 0;
	public static int matkplus107 = 0;
	public static int matksplus107 = 0;
	public static int mdefplus107 = 0;
	public static int patkplus107 = 0;
	public static int patksplus107 = 0;
	public static int pdefplus107 = 0;
	
	public static void setAccplus107(int val)
	{
		accplus107 = val;
	}
	 	
	public static void setEvasionplus107(int val)
	{
		evasionplus107 = val;
	}

	public static void setCpplus107(int val)
	{
		cpplus107 = val;
	}
	
	public static void setHpplus107(int val)
	{
		hpplus107 = val;
	}
	
	public static void setMpplus107(int val)
	{
		mpplus107 = val;
	}
	
	public static void setMatkplus107(int val)
	{
		matkplus107 = val;
	}

	public static void setMatksplus107(int val)
	{
		matksplus107 = val;
	}

	public static void setMdefplus107(int val)
	{
		mdefplus107 = val;
	}

	public static void setPatkplus107(int val)
	{
		patkplus107 = val;
	}

	public static void setPatksplus107(int val)
	{
		patksplus107 = val;
	}

	public static void setPdefplus107(int val)
	{
		pdefplus107 = val;
	}
	
	//Ghost Hunter mods
	public static int accplus108 = 0;
	public static int evasionplus108 = 0;
	public static int hpplus108 = 0;
	public static int cpplus108 = 0;
	public static int mpplus108 = 0;
	public static int matkplus108 = 0;
	public static int matksplus108 = 0;
	public static int mdefplus108 = 0;
	public static int patkplus108 = 0;
	public static int patksplus108 = 0;
	public static int pdefplus108 = 0;
	
	public static void setAccplus108(int val)
	{
		accplus108 = val;
	}
	 	
	public static void setEvasionplus108(int val)
	{
		evasionplus108 = val;
	}

	public static void setCpplus108(int val)
	{
		cpplus108 = val;
	}
	
	public static void setHpplus108(int val)
	{
		hpplus108 = val;
	}
	
	public static void setMpplus108(int val)
	{
		mpplus108 = val;
	}
	
	public static void setMatkplus108(int val)
	{
		matkplus108 = val;
	}

	public static void setMatksplus108(int val)
	{
		matksplus108 = val;
	}

	public static void setMdefplus108(int val)
	{
		mdefplus108 = val;
	}

	public static void setPatkplus108(int val)
	{
		patkplus108 = val;
	}

	public static void setPatksplus108(int val)
	{
		patksplus108 = val;
	}

	public static void setPdefplus108(int val)
	{
		pdefplus108 = val;
	}
	
	//Ghost Sentinel mods
	public static int accplus109 = 0;
	public static int evasionplus109 = 0;
	public static int hpplus109 = 0;
	public static int cpplus109 = 0;
	public static int mpplus109 = 0;
	public static int matkplus109 = 0;
	public static int matksplus109 = 0;
	public static int mdefplus109 = 0;
	public static int patkplus109 = 0;
	public static int patksplus109 = 0;
	public static int pdefplus109 = 0;
	
	public static void setAccplus109(int val)
	{
		accplus109 = val;
	}
	 	
	public static void setEvasionplus109(int val)
	{
		evasionplus109 = val;
	}

	public static void setCpplus109(int val)
	{
		cpplus109 = val;
	}
	
	public static void setHpplus109(int val)
	{
		hpplus109 = val;
	}
	
	public static void setMpplus109(int val)
	{
		mpplus109 = val;
	}
	
	public static void setMatkplus109(int val)
	{
		matkplus109 = val;
	}

	public static void setMatksplus109(int val)
	{
		matksplus109 = val;
	}

	public static void setMdefplus109(int val)
	{
		mdefplus109 = val;
	}

	public static void setPatkplus109(int val)
	{
		patkplus109 = val;
	}

	public static void setPatksplus109(int val)
	{
		patksplus109 = val;
	}

	public static void setPdefplus109(int val)
	{
		pdefplus109 = val;
	}
	
	//Storm Screamer mods
	public static int accplus110 = 0;
	public static int evasionplus110 = 0;
	public static int hpplus110 = 0;
	public static int cpplus110 = 0;
	public static int mpplus110 = 0;
	public static int matkplus110 = 0;
	public static int matksplus110 = 0;
	public static int mdefplus110 = 0;
	public static int patkplus110 = 0;
	public static int patksplus110 = 0;
	public static int pdefplus110 = 0;
	
	public static void setAccplus110(int val)
	{
		accplus110 = val;
	}
	 	
	public static void setEvasionplus110(int val)
	{
		evasionplus110 = val;
	}

	public static void setCpplus110(int val)
	{
		cpplus110 = val;
	}
	
	public static void setHpplus110(int val)
	{
		hpplus110 = val;
	}
	
	public static void setMpplus110(int val)
	{
		mpplus110 = val;
	}
	
	public static void setMatkplus110(int val)
	{
		matkplus110 = val;
	}

	public static void setMatksplus110(int val)
	{
		matksplus110 = val;
	}

	public static void setMdefplus110(int val)
	{
		mdefplus110 = val;
	}

	public static void setPatkplus110(int val)
	{
		patkplus110 = val;
	}

	public static void setPatksplus110(int val)
	{
		patksplus110 = val;
	}

	public static void setPdefplus110(int val)
	{
		pdefplus110 = val;
	}
	
	//Spectral Master mods
	public static int accplus111 = 0;
	public static int evasionplus111 = 0;
	public static int hpplus111 = 0;
	public static int cpplus111 = 0;
	public static int mpplus111 = 0;
	public static int matkplus111 = 0;
	public static int matksplus111 = 0;
	public static int mdefplus111 = 0;
	public static int patkplus111 = 0;
	public static int patksplus111 = 0;
	public static int pdefplus111 = 0;
	
	public static void setAccplus111(int val)
	{
		accplus111 = val;
	}
	 	
	public static void setEvasionplus111(int val)
	{
		evasionplus111 = val;
	}

	public static void setCpplus111(int val)
	{
		cpplus111 = val;
	}
	
	public static void setHpplus111(int val)
	{
		hpplus111 = val;
	}
	
	public static void setMpplus111(int val)
	{
		mpplus111 = val;
	}
	
	public static void setMatkplus111(int val)
	{
		matkplus111 = val;
	}

	public static void setMatksplus111(int val)
	{
		matksplus111 = val;
	}

	public static void setMdefplus111(int val)
	{
		mdefplus111 = val;
	}

	public static void setPatkplus111(int val)
	{
		patkplus111 = val;
	}

	public static void setPatksplus111(int val)
	{
		patksplus111 = val;
	}

	public static void setPdefplus111(int val)
	{
		pdefplus111 = val;
	}
	
	//Shillen Saint mods
	public static int accplus112 = 0;
	public static int evasionplus112 = 0;
	public static int hpplus112 = 0;
	public static int cpplus112 = 0;
	public static int mpplus112 = 0;
	public static int matkplus112 = 0;
	public static int matksplus112 = 0;
	public static int mdefplus112 = 0;
	public static int patkplus112 = 0;
	public static int patksplus112 = 0;
	public static int pdefplus112 = 0;
	
	public static void setAccplus112(int val)
	{
		accplus112 = val;
	}
	 	
	public static void setEvasionplus112(int val)
	{
		evasionplus112 = val;
	}

	public static void setCpplus112(int val)
	{
		cpplus112 = val;
	}
	
	public static void setHpplus112(int val)
	{
		hpplus112 = val;
	}
	
	public static void setMpplus112(int val)
	{
		mpplus112 = val;
	}
	
	public static void setMatkplus112(int val)
	{
		matkplus112 = val;
	}

	public static void setMatksplus112(int val)
	{
		matksplus112 = val;
	}

	public static void setMdefplus112(int val)
	{
		mdefplus112 = val;
	}

	public static void setPatkplus112(int val)
	{
		patkplus112 = val;
	}

	public static void setPatksplus112(int val)
	{
		patksplus112 = val;
	}

	public static void setPdefplus112(int val)
	{
		pdefplus112 = val;
	}
	
	//Titan mods
	public static int accplus113 = 0;
	public static int evasionplus113 = 0;
	public static int hpplus113 = 0;
	public static int cpplus113 = 0;
	public static int mpplus113 = 0;
	public static int matkplus113 = 0;
	public static int matksplus113 = 0;
	public static int mdefplus113 = 0;
	public static int patkplus113 = 0;
	public static int patksplus113 = 0;
	public static int pdefplus113 = 0;
	
	public static void setAccplus113(int val)
	{
		accplus113 = val;
	}
	 	
	public static void setEvasionplus113(int val)
	{
		evasionplus113 = val;
	}

	public static void setCpplus113(int val)
	{
		cpplus113 = val;
	}
	
	public static void setHpplus113(int val)
	{
		hpplus113 = val;
	}
	
	public static void setMpplus113(int val)
	{
		mpplus113 = val;
	}
	
	public static void setMatkplus113(int val)
	{
		matkplus113 = val;
	}

	public static void setMatksplus113(int val)
	{
		matksplus113 = val;
	}

	public static void setMdefplus113(int val)
	{
		mdefplus113 = val;
	}

	public static void setPatkplus113(int val)
	{
		patkplus113 = val;
	}

	public static void setPatksplus113(int val)
	{
		patksplus113 = val;
	}

	public static void setPdefplus113(int val)
	{
		pdefplus113 = val;
	}
	
	//Grand Khauatari mods
	public static int accplus114 = 0;
	public static int evasionplus114 = 0;
	public static int hpplus114 = 0;
	public static int cpplus114 = 0;
	public static int mpplus114 = 0;
	public static int matkplus114 = 0;
	public static int matksplus114 = 0;
	public static int mdefplus114 = 0;
	public static int patkplus114 = 0;
	public static int patksplus114 = 0;
	public static int pdefplus114 = 0;
	
	public static void setAccplus114(int val)
	{
		accplus114 = val;
	}
	 	
	public static void setEvasionplus114(int val)
	{
		evasionplus114 = val;
	}

	public static void setCpplus114(int val)
	{
		cpplus114 = val;
	}
	
	public static void setHpplus114(int val)
	{
		hpplus114 = val;
	}
	
	public static void setMpplus114(int val)
	{
		mpplus114 = val;
	}
	
	public static void setMatkplus114(int val)
	{
		matkplus114 = val;
	}

	public static void setMatksplus114(int val)
	{
		matksplus114 = val;
	}

	public static void setMdefplus114(int val)
	{
		mdefplus114 = val;
	}

	public static void setPatkplus114(int val)
	{
		patkplus114 = val;
	}

	public static void setPatksplus114(int val)
	{
		patksplus114 = val;
	}

	public static void setPdefplus114(int val)
	{
		pdefplus114 = val;
	}
	
	//Dominator mods
	public static int accplus115 = 0;
	public static int evasionplus115 = 0;
	public static int hpplus115 = 0;
	public static int cpplus115 = 0;
	public static int mpplus115 = 0;
	public static int matkplus115 = 0;
	public static int matksplus115 = 0;
	public static int mdefplus115 = 0;
	public static int patkplus115 = 0;
	public static int patksplus115 = 0;
	public static int pdefplus115 = 0;
	
	public static void setAccplus115(int val)
	{
		accplus115 = val;
	}
	 	
	public static void setEvasionplus115(int val)
	{
		evasionplus115 = val;
	}

	public static void setCpplus115(int val)
	{
		cpplus115 = val;
	}
	
	public static void setHpplus115(int val)
	{
		hpplus115 = val;
	}
	
	public static void setMpplus115(int val)
	{
		mpplus115 = val;
	}
	
	public static void setMatkplus115(int val)
	{
		matkplus115 = val;
	}

	public static void setMatksplus115(int val)
	{
		matksplus115 = val;
	}

	public static void setMdefplus115(int val)
	{
		mdefplus115 = val;
	}

	public static void setPatkplus115(int val)
	{
		patkplus115 = val;
	}

	public static void setPatksplus115(int val)
	{
		patksplus115 = val;
	}

	public static void setPdefplus115(int val)
	{
		pdefplus115 = val;
	}
	
	//Doomcryer mods
	public static int accplus116 = 0;
	public static int evasionplus116 = 0;
	public static int hpplus116 = 0;
	public static int cpplus116 = 0;
	public static int mpplus116 = 0;
	public static int matkplus116 = 0;
	public static int matksplus116 = 0;
	public static int mdefplus116 = 0;
	public static int patkplus116 = 0;
	public static int patksplus116 = 0;
	public static int pdefplus116 = 0;
	
	public static void setAccplus116(int val)
	{
		accplus116 = val;
	}
	 	
	public static void setEvasionplus116(int val)
	{
		evasionplus116 = val;
	}

	public static void setCpplus116(int val)
	{
		cpplus116 = val;
	}
	
	public static void setHpplus116(int val)
	{
		hpplus116 = val;
	}
	
	public static void setMpplus116(int val)
	{
		mpplus116 = val;
	}
	
	public static void setMatkplus116(int val)
	{
		matkplus116 = val;
	}

	public static void setMatksplus116(int val)
	{
		matksplus116 = val;
	}

	public static void setMdefplus116(int val)
	{
		mdefplus116 = val;
	}

	public static void setPatkplus116(int val)
	{
		patkplus116 = val;
	}

	public static void setPatksplus116(int val)
	{
		patksplus116 = val;
	}

	public static void setPdefplus116(int val)
	{
		pdefplus116 = val;
	}
	
	//Fortune Seeker mods
	public static int accplus117 = 0;
	public static int evasionplus117 = 0;
	public static int hpplus117 = 0;
	public static int cpplus117 = 0;
	public static int mpplus117 = 0;
	public static int matkplus117 = 0;
	public static int matksplus117 = 0;
	public static int mdefplus117 = 0;
	public static int patkplus117 = 0;
	public static int patksplus117 = 0;
	public static int pdefplus117 = 0;
	
	public static void setAccplus117(int val)
	{
		accplus117 = val;
	}
	 	
	public static void setEvasionplus117(int val)
	{
		evasionplus117 = val;
	}

	public static void setCpplus117(int val)
	{
		cpplus117 = val;
	}
	
	public static void setHpplus117(int val)
	{
		hpplus117 = val;
	}
	
	public static void setMpplus117(int val)
	{
		mpplus117 = val;
	}
	
	public static void setMatkplus117(int val)
	{
		matkplus117 = val;
	}

	public static void setMatksplus117(int val)
	{
		matksplus117 = val;
	}

	public static void setMdefplus117(int val)
	{
		mdefplus117 = val;
	}

	public static void setPatkplus117(int val)
	{
		patkplus117 = val;
	}

	public static void setPatksplus117(int val)
	{
		patksplus117 = val;
	}

	public static void setPdefplus117(int val)
	{
		pdefplus117 = val;
	}
	
	//Maestro mods
	public static int accplus118 = 0;
	public static int evasionplus118 = 0;
	public static int hpplus118 = 0;
	public static int cpplus118 = 0;
	public static int mpplus118 = 0;
	public static int matkplus118 = 0;
	public static int matksplus118 = 0;
	public static int mdefplus118 = 0;
	public static int patkplus118 = 0;
	public static int patksplus118 = 0;
	public static int pdefplus118 = 0;
	
	public static void setAccplus118(int val)
	{
		accplus118 = val;
	}
	 	
	public static void setEvasionplus118(int val)
	{
		evasionplus118 = val;
	}

	public static void setCpplus118(int val)
	{
		cpplus118 = val;
	}
	
	public static void setHpplus118(int val)
	{
		hpplus118 = val;
	}
	
	public static void setMpplus118(int val)
	{
		mpplus118 = val;
	}
	
	public static void setMatkplus118(int val)
	{
		matkplus118 = val;
	}

	public static void setMatksplus118(int val)
	{
		matksplus118 = val;
	}

	public static void setMdefplus118(int val)
	{
		mdefplus118 = val;
	}

	public static void setPatkplus118(int val)
	{
		patkplus118 = val;
	}

	public static void setPatksplus118(int val)
	{
		patksplus118 = val;
	}

	public static void setPdefplus118(int val)
	{
		pdefplus118 = val;
	}
	
	public static void handleCommands(L2GameClient client, String command)
	{
		final L2PcInstance activeChar = client.getActiveChar();
		if (activeChar == null)
			return;
		
		if (command.equals("addmatk88"))
 		{
			setMatkplus88(matkplus88 + 100);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk88"))
 		{
			setPatkplus88(patkplus88 + 100);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef88"))
 		{
			setMdefplus88(mdefplus88 + 100);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef88"))
 		{
			setPdefplus88(pdefplus88 + 100);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc88"))
 		{
			setAccplus88(accplus88 + 1);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev88"))
 		{
			setEvasionplus88(evasionplus88 + 1);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp88"))
 		{
			setMatksplus88(matksplus88 + 100);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp88"))
 		{
			setPatksplus88(patksplus88 + 100);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp88"))
 		{
			setCpplus88(cpplus88 + 100);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp88"))
 		{
			setHpplus88(hpplus88 + 100);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp88"))
 		{
			setMpplus88(mpplus88 + 100);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk88"))
 		{
			setMatkplus88(matkplus88 - 100);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk88"))
 		{
			setPatkplus88(patkplus88 - 100);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef88"))
 		{
			setMdefplus88(mdefplus88 - 100);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef88"))
 		{
			setPdefplus88(pdefplus88 - 100);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc88"))
 		{
			setAccplus88(accplus88 - 1);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev88"))
 		{
			setEvasionplus88(evasionplus88 - 1);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp88"))
 		{
			setMatksplus88(matksplus88 - 100);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp88"))
 		{
			setPatksplus88(patksplus88 - 100);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp88"))
 		{
			setCpplus88(cpplus88 - 100);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp88"))
 		{
			setHpplus88(hpplus88 - 100);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp88"))
 		{
			setMpplus88(mpplus88 - 100);
			Balancer.mainHtml(activeChar, 88);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatk89"))
 		{
			setMatkplus89(matkplus89 + 100);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk89"))
 		{
			setPatkplus89(patkplus89 + 100);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef89"))
 		{
			setMdefplus89(mdefplus89 + 100);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef89"))
 		{
			setPdefplus89(pdefplus89 + 100);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc89"))
 		{
			setAccplus89(accplus89 + 1);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev89"))
 		{
			setEvasionplus89(evasionplus89 + 1);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp89"))
 		{
			setMatksplus89(matksplus89 + 100);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp89"))
 		{
			setPatksplus89(patksplus89 + 100);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp89"))
 		{
			setCpplus89(cpplus89 + 100);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp89"))
 		{
			setHpplus89(hpplus89 + 100);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp89"))
 		{
			setMpplus89(mpplus89 + 100);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk89"))
 		{
			setMatkplus89(matkplus89 - 100);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk89"))
 		{
			setPatkplus89(patkplus89 - 100);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef89"))
 		{
			setMdefplus89(mdefplus89 - 100);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef89"))
 		{
			setPdefplus89(pdefplus89 - 100);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc89"))
 		{
			setAccplus89(accplus89 - 1);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev89"))
 		{
			setEvasionplus89(evasionplus89 - 1);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp89"))
 		{
			setMatksplus89(matksplus89 - 100);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp89"))
 		{
			setPatksplus89(patksplus89 - 100);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp89"))
 		{
			setCpplus89(cpplus89 - 100);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp89"))
 		{
			setHpplus89(hpplus89 - 100);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp89"))
 		{
			setMpplus89(mpplus89 - 100);
			Balancer.mainHtml(activeChar, 89);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk90"))
 		{
			setMatkplus90(matkplus90 + 100);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk90"))
 		{
			setPatkplus90(patkplus90 + 100);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef90"))
 		{
			setMdefplus90(mdefplus90 + 100);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef90"))
 		{
			setPdefplus90(pdefplus90 + 100);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc90"))
 		{
			setAccplus90(accplus90 + 1);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev90"))
 		{
			setEvasionplus90(evasionplus90 + 1);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp90"))
 		{
			setMatksplus90(matksplus90 + 100);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp90"))
 		{
			setPatksplus90(patksplus90 + 100);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp90"))
 		{
			setCpplus90(cpplus90 + 100);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp90"))
 		{
			setHpplus90(hpplus90 + 100);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp90"))
 		{
			setMpplus90(mpplus90 + 100);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk90"))
 		{
			setMatkplus90(matkplus90 - 100);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk90"))
 		{
			setPatkplus90(patkplus90 - 100);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef90"))
 		{
			setMdefplus90(mdefplus90 - 100);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef90"))
 		{
			setPdefplus90(pdefplus90 - 100);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc90"))
 		{
			setAccplus90(accplus90 - 1);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev90"))
 		{
			setEvasionplus90(evasionplus90 - 1);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp90"))
 		{
			setMatksplus90(matksplus90 - 100);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp90"))
 		{
			setPatksplus90(patksplus90 - 100);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp90"))
 		{
			setCpplus90(cpplus90 - 100);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp90"))
 		{
			setHpplus90(hpplus90 - 100);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp90"))
 		{
			setMpplus90(mpplus90 - 100);
			Balancer.mainHtml(activeChar, 90);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk91"))
 		{
			setMatkplus91(matkplus91 + 100);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk91"))
 		{
			setPatkplus91(patkplus91 + 100);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef91"))
 		{
			setMdefplus91(mdefplus91 + 100);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef91"))
 		{
			setPdefplus91(pdefplus91 + 100);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc91"))
 		{
			setAccplus91(accplus91 + 1);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev91"))
 		{
			setEvasionplus91(evasionplus91 + 1);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp91"))
 		{
			setMatksplus91(matksplus91 + 100);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp91"))
 		{
			setPatksplus91(patksplus91 + 100);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp91"))
 		{
			setCpplus91(cpplus91 + 100);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp91"))
 		{
			setHpplus91(hpplus91 + 100);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp91"))
 		{
			setMpplus91(mpplus91 + 100);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk91"))
 		{
			setMatkplus91(matkplus91 - 100);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk91"))
 		{
			setPatkplus91(patkplus91 - 100);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef91"))
 		{
			setMdefplus91(mdefplus91 - 100);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef91"))
 		{
			setPdefplus91(pdefplus91 - 100);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc91"))
 		{
			setAccplus91(accplus91 - 1);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev91"))
 		{
			setEvasionplus91(evasionplus91 - 1);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp91"))
 		{
			setMatksplus91(matksplus91 - 100);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp91"))
 		{
			setPatksplus91(patksplus91 - 100);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp91"))
 		{
			setCpplus91(cpplus91 - 100);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp91"))
 		{
			setHpplus91(hpplus91 - 100);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp91"))
 		{
			setMpplus91(mpplus91 - 100);
			Balancer.mainHtml(activeChar, 91);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk92"))
 		{
			setMatkplus92(matkplus92 + 100);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk92"))
 		{
			setPatkplus92(patkplus92 + 100);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef92"))
 		{
			setMdefplus92(mdefplus92 + 100);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef92"))
 		{
			setPdefplus92(pdefplus92 + 100);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc92"))
 		{
			setAccplus92(accplus92 + 1);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev92"))
 		{
			setEvasionplus92(evasionplus92 + 1);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp92"))
 		{
			setMatksplus92(matksplus92 + 100);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp92"))
 		{
			setPatksplus92(patksplus92 + 100);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp92"))
 		{
			setCpplus92(cpplus92 + 100);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp92"))
 		{
			setHpplus92(hpplus92 + 100);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp92"))
 		{
			setMpplus92(mpplus92 + 100);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk92"))
 		{
			setMatkplus92(matkplus92 - 100);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk92"))
 		{
			setPatkplus92(patkplus92 - 100);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef92"))
 		{
			setMdefplus92(mdefplus92 - 100);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef92"))
 		{
			setPdefplus92(pdefplus92 - 100);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc92"))
 		{
			setAccplus92(accplus92 - 1);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev92"))
 		{
			setEvasionplus92(evasionplus92 - 1);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp92"))
 		{
			setMatksplus92(matksplus92 - 100);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp92"))
 		{
			setPatksplus92(patksplus92 - 100);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp92"))
 		{
			setCpplus92(cpplus92 - 100);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp92"))
 		{
			setHpplus92(hpplus92 - 100);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp92"))
 		{
			setMpplus92(mpplus92 - 100);
			Balancer.mainHtml(activeChar, 92);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk93"))
 		{
			setMatkplus93(matkplus93 + 100);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk93"))
 		{
			setPatkplus93(patkplus93 + 100);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef93"))
 		{
			setMdefplus93(mdefplus93 + 100);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef93"))
 		{
			setPdefplus93(pdefplus93 + 100);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc93"))
 		{
			setAccplus93(accplus93 + 1);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev93"))
 		{
			setEvasionplus93(evasionplus93 + 1);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp93"))
 		{
			setMatksplus93(matksplus93 + 100);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp93"))
 		{
			setPatksplus93(patksplus93 + 100);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp93"))
 		{
			setCpplus93(cpplus93 + 100);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp93"))
 		{
			setHpplus93(hpplus93 + 100);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp93"))
 		{
			setMpplus93(mpplus93 + 100);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk93"))
 		{
			setMatkplus93(matkplus93 - 100);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk93"))
 		{
			setPatkplus93(patkplus93 - 100);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef93"))
 		{
			setMdefplus93(mdefplus93 - 100);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef93"))
 		{
			setPdefplus93(pdefplus93 - 100);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc93"))
 		{
			setAccplus93(accplus93 - 1);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev93"))
 		{
			setEvasionplus93(evasionplus93 - 1);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp93"))
 		{
			setMatksplus93(matksplus93 - 100);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp93"))
 		{
			setPatksplus93(patksplus93 - 100);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp93"))
 		{
			setCpplus93(cpplus93 - 100);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp93"))
 		{
			setHpplus93(hpplus93 - 100);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp93"))
 		{
			setMpplus93(mpplus93 - 100);
			Balancer.mainHtml(activeChar, 93);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk94"))
 		{
			setMatkplus94(matkplus94 + 100);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk94"))
 		{
			setPatkplus94(patkplus94 + 100);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef94"))
 		{
			setMdefplus94(mdefplus94 + 100);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef94"))
 		{
			setPdefplus94(pdefplus94 + 100);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc94"))
 		{
			setAccplus94(accplus94 + 1);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev94"))
 		{
			setEvasionplus94(evasionplus94 + 1);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp94"))
 		{
			setMatksplus94(matksplus94 + 100);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp94"))
 		{
			setPatksplus94(patksplus94 + 100);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp94"))
 		{
			setCpplus94(cpplus94 + 100);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp94"))
 		{
			setHpplus94(hpplus94 + 100);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp94"))
 		{
			setMpplus94(mpplus94 + 100);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk94"))
 		{
			setMatkplus94(matkplus94 - 100);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk94"))
 		{
			setPatkplus94(patkplus94 - 100);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef94"))
 		{
			setMdefplus94(mdefplus94 - 100);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef94"))
 		{
			setPdefplus94(pdefplus94 - 100);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc94"))
 		{
			setAccplus94(accplus94 - 1);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev94"))
 		{
			setEvasionplus94(evasionplus94 - 1);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp94"))
 		{
			setMatksplus94(matksplus94 - 100);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp94"))
 		{
			setPatksplus94(patksplus94 - 100);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp94"))
 		{
			setCpplus94(cpplus94 - 100);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp94"))
 		{
			setHpplus94(hpplus94 - 100);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp94"))
 		{
			setMpplus94(mpplus94 - 100);
			Balancer.mainHtml(activeChar, 94);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk95"))
 		{
			setMatkplus95(matkplus95 + 100);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk95"))
 		{
			setPatkplus95(patkplus95 + 100);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef95"))
 		{
			setMdefplus95(mdefplus95 + 100);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef95"))
 		{
			setPdefplus95(pdefplus95 + 100);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc95"))
 		{
			setAccplus95(accplus95 + 1);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev95"))
 		{
			setEvasionplus95(evasionplus95 + 1);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp95"))
 		{
			setMatksplus95(matksplus95 + 100);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp95"))
 		{
			setPatksplus95(patksplus95 + 100);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp95"))
 		{
			setCpplus95(cpplus95 + 100);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp95"))
 		{
			setHpplus95(hpplus95 + 100);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp95"))
 		{
			setMpplus95(mpplus95 + 100);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk95"))
 		{
			setMatkplus95(matkplus95 - 100);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk95"))
 		{
			setPatkplus95(patkplus95 - 100);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef95"))
 		{
			setMdefplus95(mdefplus95 - 100);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef95"))
 		{
			setPdefplus95(pdefplus95 - 100);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc95"))
 		{
			setAccplus95(accplus95 - 1);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev95"))
 		{
			setEvasionplus95(evasionplus95 - 1);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp95"))
 		{
			setMatksplus95(matksplus95 - 100);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp95"))
 		{
			setPatksplus95(patksplus95 - 100);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp95"))
 		{
			setCpplus95(cpplus95 - 100);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp95"))
 		{
			setHpplus95(hpplus95 - 100);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp95"))
 		{
			setMpplus95(mpplus95 - 100);
			Balancer.mainHtml(activeChar, 95);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk96"))
 		{
			setMatkplus96(matkplus96 + 100);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk96"))
 		{
			setPatkplus96(patkplus96 + 100);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef96"))
 		{
			setMdefplus96(mdefplus96 + 100);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef96"))
 		{
			setPdefplus96(pdefplus96 + 100);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc96"))
 		{
			setAccplus96(accplus96 + 1);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev96"))
 		{
			setEvasionplus96(evasionplus96 + 1);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp96"))
 		{
			setMatksplus96(matksplus96 + 100);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp96"))
 		{
			setPatksplus96(patksplus96 + 100);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp96"))
 		{
			setCpplus96(cpplus96 + 100);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp96"))
 		{
			setHpplus96(hpplus96 + 100);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp96"))
 		{
			setMpplus96(mpplus96 + 100);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk96"))
 		{
			setMatkplus96(matkplus96 - 100);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk96"))
 		{
			setPatkplus96(patkplus96 - 100);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef96"))
 		{
			setMdefplus96(mdefplus96 - 100);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef96"))
 		{
			setPdefplus96(pdefplus96 - 100);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc96"))
 		{
			setAccplus96(accplus96 - 1);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev96"))
 		{
			setEvasionplus96(evasionplus96 - 1);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp96"))
 		{
			setMatksplus96(matksplus96 - 100);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp96"))
 		{
			setPatksplus96(patksplus96 - 100);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp96"))
 		{
			setCpplus96(cpplus96 - 100);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp96"))
 		{
			setHpplus96(hpplus96 - 100);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp96"))
 		{
			setMpplus96(mpplus96 - 100);
			Balancer.mainHtml(activeChar, 96);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk97"))
 		{
			setMatkplus97(matkplus97 + 100);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk97"))
 		{
			setPatkplus97(patkplus97 + 100);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef97"))
 		{
			setMdefplus97(mdefplus97 + 100);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef97"))
 		{
			setPdefplus97(pdefplus97 + 100);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc97"))
 		{
			setAccplus97(accplus97 + 1);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev97"))
 		{
			setEvasionplus97(evasionplus97 + 1);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp97"))
 		{
			setMatksplus97(matksplus97 + 100);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp97"))
 		{
			setPatksplus97(patksplus97 + 100);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp97"))
 		{
			setCpplus97(cpplus97 + 100);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp97"))
 		{
			setHpplus97(hpplus97 + 100);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp97"))
 		{
			setMpplus97(mpplus97 + 100);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk97"))
 		{
			setMatkplus97(matkplus97 - 100);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk97"))
 		{
			setPatkplus97(patkplus97 - 100);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef97"))
 		{
			setMdefplus97(mdefplus97 - 100);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef97"))
 		{
			setPdefplus97(pdefplus97 - 100);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc97"))
 		{
			setAccplus97(accplus97 - 1);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev97"))
 		{
			setEvasionplus97(evasionplus97 - 1);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp97"))
 		{
			setMatksplus97(matksplus97 - 100);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp97"))
 		{
			setPatksplus97(patksplus97 - 100);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp97"))
 		{
			setCpplus97(cpplus97 - 100);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp97"))
 		{
			setHpplus97(hpplus97 - 100);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp97"))
 		{
			setMpplus97(mpplus97 - 100);
			Balancer.mainHtml(activeChar, 97);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk98"))
 		{
			setMatkplus98(matkplus98 + 100);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk98"))
 		{
			setPatkplus98(patkplus98 + 100);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef98"))
 		{
			setMdefplus98(mdefplus98 + 100);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef98"))
 		{
			setPdefplus98(pdefplus98 + 100);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc98"))
 		{
			setAccplus98(accplus98 + 1);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev98"))
 		{
			setEvasionplus98(evasionplus98 + 1);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp98"))
 		{
			setMatksplus98(matksplus98 + 100);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp98"))
 		{
			setPatksplus98(patksplus98 + 100);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp98"))
 		{
			setCpplus98(cpplus98 + 100);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp98"))
 		{
			setHpplus98(hpplus98 + 100);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp98"))
 		{
			setMpplus98(mpplus98 + 100);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk98"))
 		{
			setMatkplus98(matkplus98 - 100);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk98"))
 		{
			setPatkplus98(patkplus98 - 100);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef98"))
 		{
			setMdefplus98(mdefplus98 - 100);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef98"))
 		{
			setPdefplus98(pdefplus98 - 100);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc98"))
 		{
			setAccplus98(accplus98 - 1);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev98"))
 		{
			setEvasionplus98(evasionplus98 - 1);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp98"))
 		{
			setMatksplus98(matksplus98 - 100);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp98"))
 		{
			setPatksplus98(patksplus98 - 100);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp98"))
 		{
			setCpplus98(cpplus98 - 100);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp98"))
 		{
			setHpplus98(hpplus98 - 100);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp98"))
 		{
			setMpplus98(mpplus98 - 100);
			Balancer.mainHtml(activeChar, 98);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk99"))
 		{
			setMatkplus99(matkplus99 + 100);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk99"))
 		{
			setPatkplus99(patkplus99 + 100);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef99"))
 		{
			setMdefplus99(mdefplus99 + 100);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef99"))
 		{
			setPdefplus99(pdefplus99 + 100);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc99"))
 		{
			setAccplus99(accplus99 + 1);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev99"))
 		{
			setEvasionplus99(evasionplus99 + 1);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp99"))
 		{
			setMatksplus99(matksplus99 + 100);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp99"))
 		{
			setPatksplus99(patksplus99 + 100);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp99"))
 		{
			setCpplus99(cpplus99 + 100);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp99"))
 		{
			setHpplus99(hpplus99 + 100);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp99"))
 		{
			setMpplus99(mpplus99 + 100);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk99"))
 		{
			setMatkplus99(matkplus99 - 100);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk99"))
 		{
			setPatkplus99(patkplus99 - 100);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef99"))
 		{
			setMdefplus99(mdefplus99 - 100);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef99"))
 		{
			setPdefplus99(pdefplus99 - 100);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc99"))
 		{
			setAccplus99(accplus99 - 1);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev99"))
 		{
			setEvasionplus99(evasionplus99 - 1);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp99"))
 		{
			setMatksplus99(matksplus99 - 100);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp99"))
 		{
			setPatksplus99(patksplus99 - 100);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp99"))
 		{
			setCpplus99(cpplus99 - 100);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp99"))
 		{
			setHpplus99(hpplus99 - 100);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp99"))
 		{
			setMpplus99(mpplus99 - 100);
			Balancer.mainHtml(activeChar, 99);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk100"))
 		{
			setMatkplus100(matkplus100 + 100);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk100"))
 		{
			setPatkplus100(patkplus100 + 100);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef100"))
 		{
			setMdefplus100(mdefplus100 + 100);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef100"))
 		{
			setPdefplus100(pdefplus100 + 100);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc100"))
 		{
			setAccplus100(accplus100 + 1);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev100"))
 		{
			setEvasionplus100(evasionplus100 + 1);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp100"))
 		{
			setMatksplus100(matksplus100 + 100);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp100"))
 		{
			setPatksplus100(patksplus100 + 100);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp100"))
 		{
			setCpplus100(cpplus100 + 100);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp100"))
 		{
			setHpplus100(hpplus100 + 100);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp100"))
 		{
			setMpplus100(mpplus100 + 100);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk100"))
 		{
			setMatkplus100(matkplus100 - 100);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk100"))
 		{
			setPatkplus100(patkplus100 - 100);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef100"))
 		{
			setMdefplus100(mdefplus100 - 100);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef100"))
 		{
			setPdefplus100(pdefplus100 - 100);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc100"))
 		{
			setAccplus100(accplus100 - 1);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev100"))
 		{
			setEvasionplus100(evasionplus100 - 1);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp100"))
 		{
			setMatksplus100(matksplus100 - 100);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp100"))
 		{
			setPatksplus100(patksplus100 - 100);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp100"))
 		{
			setCpplus100(cpplus100 - 100);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp100"))
 		{
			setHpplus100(hpplus100 - 100);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp100"))
 		{
			setMpplus100(mpplus100 - 100);
			Balancer.mainHtml(activeChar, 100);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk101"))
 		{
			setMatkplus101(matkplus101 + 100);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk101"))
 		{
			setPatkplus101(patkplus101 + 100);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef101"))
 		{
			setMdefplus101(mdefplus101 + 100);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef101"))
 		{
			setPdefplus101(pdefplus101 + 100);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc101"))
 		{
			setAccplus101(accplus101 + 1);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev101"))
 		{
			setEvasionplus101(evasionplus101 + 1);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp101"))
 		{
			setMatksplus101(matksplus101 + 100);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp101"))
 		{
			setPatksplus101(patksplus101 + 100);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp101"))
 		{
			setCpplus101(cpplus101 + 100);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp101"))
 		{
			setHpplus101(hpplus101 + 100);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp101"))
 		{
			setMpplus101(mpplus101 + 100);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk101"))
 		{
			setMatkplus101(matkplus101 - 100);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk101"))
 		{
			setPatkplus101(patkplus101 - 100);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef101"))
 		{
			setMdefplus101(mdefplus101 - 100);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef101"))
 		{
			setPdefplus101(pdefplus101 - 100);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc101"))
 		{
			setAccplus101(accplus101 - 1);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev101"))
 		{
			setEvasionplus101(evasionplus101 - 1);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp101"))
 		{
			setMatksplus101(matksplus101 - 100);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp101"))
 		{
			setPatksplus101(patksplus101 - 100);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp101"))
 		{
			setCpplus101(cpplus101 - 100);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp101"))
 		{
			setHpplus101(hpplus101 - 100);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp101"))
 		{
			setMpplus101(mpplus101 - 100);
			Balancer.mainHtml(activeChar, 101);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk102"))
 		{
			setMatkplus102(matkplus102 + 100);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk102"))
 		{
			setPatkplus102(patkplus102 + 100);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef102"))
 		{
			setMdefplus102(mdefplus102 + 100);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef102"))
 		{
			setPdefplus102(pdefplus102 + 100);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc102"))
 		{
			setAccplus102(accplus102 + 1);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev102"))
 		{
			setEvasionplus102(evasionplus102 + 1);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp102"))
 		{
			setMatksplus102(matksplus102 + 100);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp102"))
 		{
			setPatksplus102(patksplus102 + 100);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp102"))
 		{
			setCpplus102(cpplus102 + 100);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp102"))
 		{
			setHpplus102(hpplus102 + 100);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp102"))
 		{
			setMpplus102(mpplus102 + 100);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk102"))
 		{
			setMatkplus102(matkplus102 - 100);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk102"))
 		{
			setPatkplus102(patkplus102 - 100);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef102"))
 		{
			setMdefplus102(mdefplus102 - 100);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef102"))
 		{
			setPdefplus102(pdefplus102 - 100);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc102"))
 		{
			setAccplus102(accplus102 - 1);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev102"))
 		{
			setEvasionplus102(evasionplus102 - 1);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp102"))
 		{
			setMatksplus102(matksplus102 - 100);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp102"))
 		{
			setPatksplus102(patksplus102 - 100);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp102"))
 		{
			setCpplus102(cpplus102 - 100);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp102"))
 		{
			setHpplus102(hpplus102 - 100);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp102"))
 		{
			setMpplus102(mpplus102 - 100);
			Balancer.mainHtml(activeChar, 102);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk103"))
 		{
			setMatkplus103(matkplus103 + 100);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk103"))
 		{
			setPatkplus103(patkplus103 + 100);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef103"))
 		{
			setMdefplus103(mdefplus103 + 100);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef103"))
 		{
			setPdefplus103(pdefplus103 + 100);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc103"))
 		{
			setAccplus103(accplus103 + 1);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev103"))
 		{
			setEvasionplus103(evasionplus103 + 1);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp103"))
 		{
			setMatksplus103(matksplus103 + 100);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp103"))
 		{
			setPatksplus103(patksplus103 + 100);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp103"))
 		{
			setCpplus103(cpplus103 + 100);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp103"))
 		{
			setHpplus103(hpplus103 + 100);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp103"))
 		{
			setMpplus103(mpplus103 + 100);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk103"))
 		{
			setMatkplus103(matkplus103 - 100);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk103"))
 		{
			setPatkplus103(patkplus103 - 100);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef103"))
 		{
			setMdefplus103(mdefplus103 - 100);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef103"))
 		{
			setPdefplus103(pdefplus103 - 100);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc103"))
 		{
			setAccplus103(accplus103 - 1);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev103"))
 		{
			setEvasionplus103(evasionplus103 - 1);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp103"))
 		{
			setMatksplus103(matksplus103 - 100);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp103"))
 		{
			setPatksplus103(patksplus103 - 100);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp103"))
 		{
			setCpplus103(cpplus103 - 100);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp103"))
 		{
			setHpplus103(hpplus103 - 100);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp103"))
 		{
			setMpplus103(mpplus103 - 100);
			Balancer.mainHtml(activeChar, 103);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk104"))
 		{
			setMatkplus104(matkplus104 + 100);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk104"))
 		{
			setPatkplus104(patkplus104 + 100);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef104"))
 		{
			setMdefplus104(mdefplus104 + 100);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef104"))
 		{
			setPdefplus104(pdefplus104 + 100);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc104"))
 		{
			setAccplus104(accplus104 + 1);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev104"))
 		{
			setEvasionplus104(evasionplus104 + 1);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp104"))
 		{
			setMatksplus104(matksplus104 + 100);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp104"))
 		{
			setPatksplus104(patksplus104 + 100);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp104"))
 		{
			setCpplus104(cpplus104 + 100);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp104"))
 		{
			setHpplus104(hpplus104 + 100);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp104"))
 		{
			setMpplus104(mpplus104 + 100);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk104"))
 		{
			setMatkplus104(matkplus104 - 100);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk104"))
 		{
			setPatkplus104(patkplus104 - 100);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef104"))
 		{
			setMdefplus104(mdefplus104 - 100);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef104"))
 		{
			setPdefplus104(pdefplus104 - 100);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc104"))
 		{
			setAccplus104(accplus104 - 1);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev104"))
 		{
			setEvasionplus104(evasionplus104 - 1);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp104"))
 		{
			setMatksplus104(matksplus104 - 100);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp104"))
 		{
			setPatksplus104(patksplus104 - 100);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp104"))
 		{
			setCpplus104(cpplus104 - 100);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp104"))
 		{
			setHpplus104(hpplus104 - 100);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp104"))
 		{
			setMpplus104(mpplus104 - 100);
			Balancer.mainHtml(activeChar, 104);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk105"))
 		{
			setMatkplus105(matkplus105 + 100);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk105"))
 		{
			setPatkplus105(patkplus105 + 100);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef105"))
 		{
			setMdefplus105(mdefplus105 + 100);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef105"))
 		{
			setPdefplus105(pdefplus105 + 100);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc105"))
 		{
			setAccplus105(accplus105 + 1);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev105"))
 		{
			setEvasionplus105(evasionplus105 + 1);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp105"))
 		{
			setMatksplus105(matksplus105 + 100);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp105"))
 		{
			setPatksplus105(patksplus105 + 100);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp105"))
 		{
			setCpplus105(cpplus105 + 100);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp105"))
 		{
			setHpplus105(hpplus105 + 100);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp105"))
 		{
			setMpplus105(mpplus105 + 100);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk105"))
 		{
			setMatkplus105(matkplus105 - 100);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk105"))
 		{
			setPatkplus105(patkplus105 - 100);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef105"))
 		{
			setMdefplus105(mdefplus105 - 100);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef105"))
 		{
			setPdefplus105(pdefplus105 - 100);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc105"))
 		{
			setAccplus105(accplus105 - 1);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev105"))
 		{
			setEvasionplus105(evasionplus105 - 1);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp105"))
 		{
			setMatksplus105(matksplus105 - 100);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp105"))
 		{
			setPatksplus105(patksplus105 - 100);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp105"))
 		{
			setCpplus105(cpplus105 - 100);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp105"))
 		{
			setHpplus105(hpplus105 - 100);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp105"))
 		{
			setMpplus105(mpplus105 - 100);
			Balancer.mainHtml(activeChar, 105);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk106"))
 		{
			setMatkplus106(matkplus106 + 100);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk106"))
 		{
			setPatkplus106(patkplus106 + 100);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef106"))
 		{
			setMdefplus106(mdefplus106 + 100);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef106"))
 		{
			setPdefplus106(pdefplus106 + 100);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc106"))
 		{
			setAccplus106(accplus106 + 1);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev106"))
 		{
			setEvasionplus106(evasionplus106 + 1);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp106"))
 		{
			setMatksplus106(matksplus106 + 100);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp106"))
 		{
			setPatksplus106(patksplus106 + 100);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp106"))
 		{
			setCpplus106(cpplus106 + 100);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp106"))
 		{
			setHpplus106(hpplus106 + 100);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp106"))
 		{
			setMpplus106(mpplus106 + 100);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk106"))
 		{
			setMatkplus106(matkplus106 - 100);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk106"))
 		{
			setPatkplus106(patkplus106 - 100);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef106"))
 		{
			setMdefplus106(mdefplus106 - 100);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef106"))
 		{
			setPdefplus106(pdefplus106 - 100);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc106"))
 		{
			setAccplus106(accplus106 - 1);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev106"))
 		{
			setEvasionplus106(evasionplus106 - 1);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp106"))
 		{
			setMatksplus106(matksplus106 - 100);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp106"))
 		{
			setPatksplus106(patksplus106 - 100);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp106"))
 		{
			setCpplus106(cpplus106 - 100);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp106"))
 		{
			setHpplus106(hpplus106 - 100);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp106"))
 		{
			setMpplus106(mpplus106 - 100);
			Balancer.mainHtml(activeChar, 106);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk107"))
 		{
			setMatkplus107(matkplus107 + 100);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk107"))
 		{
			setPatkplus107(patkplus107 + 100);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef107"))
 		{
			setMdefplus107(mdefplus107 + 100);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef107"))
 		{
			setPdefplus107(pdefplus107 + 100);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc107"))
 		{
			setAccplus107(accplus107 + 1);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev107"))
 		{
			setEvasionplus107(evasionplus107 + 1);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp107"))
 		{
			setMatksplus107(matksplus107 + 100);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp107"))
 		{
			setPatksplus107(patksplus107 + 100);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp107"))
 		{
			setCpplus107(cpplus107 + 100);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp107"))
 		{
			setHpplus107(hpplus107 + 100);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp107"))
 		{
			setMpplus107(mpplus107 + 100);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk107"))
 		{
			setMatkplus107(matkplus107 - 100);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk107"))
 		{
			setPatkplus107(patkplus107 - 100);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef107"))
 		{
			setMdefplus107(mdefplus107 - 100);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef107"))
 		{
			setPdefplus107(pdefplus107 - 100);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc107"))
 		{
			setAccplus107(accplus107 - 1);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev107"))
 		{
			setEvasionplus107(evasionplus107 - 1);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp107"))
 		{
			setMatksplus107(matksplus107 - 100);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp107"))
 		{
			setPatksplus107(patksplus107 - 100);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp107"))
 		{
			setCpplus107(cpplus107 - 100);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp107"))
 		{
			setHpplus107(hpplus107 - 100);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp107"))
 		{
			setMpplus107(mpplus107 - 100);
			Balancer.mainHtml(activeChar, 107);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk108"))
 		{
			setMatkplus108(matkplus108 + 100);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk108"))
 		{
			setPatkplus108(patkplus108 + 100);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef108"))
 		{
			setMdefplus108(mdefplus108 + 100);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef108"))
 		{
			setPdefplus108(pdefplus108 + 100);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc108"))
 		{
			setAccplus108(accplus108 + 1);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev108"))
 		{
			setEvasionplus108(evasionplus108 + 1);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp108"))
 		{
			setMatksplus108(matksplus108 + 100);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp108"))
 		{
			setPatksplus108(patksplus108 + 100);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp108"))
 		{
			setCpplus108(cpplus108 + 100);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp108"))
 		{
			setHpplus108(hpplus108 + 100);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp108"))
 		{
			setMpplus108(mpplus108 + 100);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk108"))
 		{
			setMatkplus108(matkplus108 - 100);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk108"))
 		{
			setPatkplus108(patkplus108 - 100);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef108"))
 		{
			setMdefplus108(mdefplus108 - 100);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef108"))
 		{
			setPdefplus108(pdefplus108 - 100);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc108"))
 		{
			setAccplus108(accplus108 - 1);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev108"))
 		{
			setEvasionplus108(evasionplus108 - 1);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp108"))
 		{
			setMatksplus108(matksplus108 - 100);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp108"))
 		{
			setPatksplus108(patksplus108 - 100);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp108"))
 		{
			setCpplus108(cpplus108 - 100);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp108"))
 		{
			setHpplus108(hpplus108 - 100);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp108"))
 		{
			setMpplus108(mpplus108 - 100);
			Balancer.mainHtml(activeChar, 108);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk109"))
 		{
			setMatkplus109(matkplus109 + 100);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk109"))
 		{
			setPatkplus109(patkplus109 + 100);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef109"))
 		{
			setMdefplus109(mdefplus109 + 100);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef109"))
 		{
			setPdefplus109(pdefplus109 + 100);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc109"))
 		{
			setAccplus109(accplus109 + 1);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev109"))
 		{
			setEvasionplus109(evasionplus109 + 1);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp109"))
 		{
			setMatksplus109(matksplus109 + 100);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp109"))
 		{
			setPatksplus109(patksplus109 + 100);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp109"))
 		{
			setCpplus109(cpplus109 + 100);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp109"))
 		{
			setHpplus109(hpplus109 + 100);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp109"))
 		{
			setMpplus109(mpplus109 + 100);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk109"))
 		{
			setMatkplus109(matkplus109 - 100);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk109"))
 		{
			setPatkplus109(patkplus109 - 100);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef109"))
 		{
			setMdefplus109(mdefplus109 - 100);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef109"))
 		{
			setPdefplus109(pdefplus109 - 100);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc109"))
 		{
			setAccplus109(accplus109 - 1);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev109"))
 		{
			setEvasionplus109(evasionplus109 - 1);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp109"))
 		{
			setMatksplus109(matksplus109 - 100);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp109"))
 		{
			setPatksplus109(patksplus109 - 100);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp109"))
 		{
			setCpplus109(cpplus109 - 100);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp109"))
 		{
			setHpplus109(hpplus109 - 100);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp109"))
 		{
			setMpplus109(mpplus109 - 100);
			Balancer.mainHtml(activeChar, 109);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk110"))
 		{
			setMatkplus110(matkplus110 + 100);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk110"))
 		{
			setPatkplus110(patkplus110 + 100);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef110"))
 		{
			setMdefplus110(mdefplus110 + 100);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef110"))
 		{
			setPdefplus110(pdefplus110 + 100);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc110"))
 		{
			setAccplus110(accplus110 + 1);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev110"))
 		{
			setEvasionplus110(evasionplus110 + 1);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp110"))
 		{
			setMatksplus110(matksplus110 + 100);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp110"))
 		{
			setPatksplus110(patksplus110 + 100);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp110"))
 		{
			setCpplus110(cpplus110 + 100);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp110"))
 		{
			setHpplus110(hpplus110 + 100);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp110"))
 		{
			setMpplus110(mpplus110 + 100);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk110"))
 		{
			setMatkplus110(matkplus110 - 100);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk110"))
 		{
			setPatkplus110(patkplus110 - 100);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef110"))
 		{
			setMdefplus110(mdefplus110 - 100);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef110"))
 		{
			setPdefplus110(pdefplus110 - 100);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc110"))
 		{
			setAccplus110(accplus110 - 1);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev110"))
 		{
			setEvasionplus110(evasionplus110 - 1);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp110"))
 		{
			setMatksplus110(matksplus110 - 100);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp110"))
 		{
			setPatksplus110(patksplus110 - 100);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp110"))
 		{
			setCpplus110(cpplus110 - 100);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp110"))
 		{
			setHpplus110(hpplus110 - 100);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp110"))
 		{
			setMpplus110(mpplus110 - 100);
			Balancer.mainHtml(activeChar, 110);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk111"))
 		{
			setMatkplus111(matkplus111 + 100);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk111"))
 		{
			setPatkplus111(patkplus111 + 100);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef111"))
 		{
			setMdefplus111(mdefplus111 + 100);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef111"))
 		{
			setPdefplus111(pdefplus111 + 100);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc111"))
 		{
			setAccplus111(accplus111 + 1);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev111"))
 		{
			setEvasionplus111(evasionplus111 + 1);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp111"))
 		{
			setMatksplus111(matksplus111 + 100);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp111"))
 		{
			setPatksplus111(patksplus111 + 100);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp111"))
 		{
			setCpplus111(cpplus111 + 100);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp111"))
 		{
			setHpplus111(hpplus111 + 100);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp111"))
 		{
			setMpplus111(mpplus111 + 100);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk111"))
 		{
			setMatkplus111(matkplus111 - 100);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk111"))
 		{
			setPatkplus111(patkplus111 - 100);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef111"))
 		{
			setMdefplus111(mdefplus111 - 100);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef111"))
 		{
			setPdefplus111(pdefplus111 - 100);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc111"))
 		{
			setAccplus111(accplus111 - 1);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev111"))
 		{
			setEvasionplus111(evasionplus111 - 1);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp111"))
 		{
			setMatksplus111(matksplus111 - 100);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp111"))
 		{
			setPatksplus111(patksplus111 - 100);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp111"))
 		{
			setCpplus111(cpplus111 - 100);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp111"))
 		{
			setHpplus111(hpplus111 - 100);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp111"))
 		{
			setMpplus111(mpplus111 - 100);
			Balancer.mainHtml(activeChar, 111);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk112"))
 		{
			setMatkplus112(matkplus112 + 100);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk112"))
 		{
			setPatkplus112(patkplus112 + 100);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef112"))
 		{
			setMdefplus112(mdefplus112 + 100);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef112"))
 		{
			setPdefplus112(pdefplus112 + 100);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc112"))
 		{
			setAccplus112(accplus112 + 1);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev112"))
 		{
			setEvasionplus112(evasionplus112 + 1);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp112"))
 		{
			setMatksplus112(matksplus112 + 100);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp112"))
 		{
			setPatksplus112(patksplus112 + 100);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp112"))
 		{
			setCpplus112(cpplus112 + 100);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp112"))
 		{
			setHpplus112(hpplus112 + 100);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp112"))
 		{
			setMpplus112(mpplus112 + 100);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk112"))
 		{
			setMatkplus112(matkplus112 - 100);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk112"))
 		{
			setPatkplus112(patkplus112 - 100);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef112"))
 		{
			setMdefplus112(mdefplus112 - 100);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef112"))
 		{
			setPdefplus112(pdefplus112 - 100);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc112"))
 		{
			setAccplus112(accplus112 - 1);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev112"))
 		{
			setEvasionplus112(evasionplus112 - 1);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp112"))
 		{
			setMatksplus112(matksplus112 - 100);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp112"))
 		{
			setPatksplus112(patksplus112 - 100);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp112"))
 		{
			setCpplus112(cpplus112 - 100);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp112"))
 		{
			setHpplus112(hpplus112 - 100);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp112"))
 		{
			setMpplus112(mpplus112 - 100);
			Balancer.mainHtml(activeChar, 112);
 			activeChar.broadcastUserInfo();
 		}
 		
		else if (command.equals("addmatk113"))
 		{
			setMatkplus113(matkplus113 + 100);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk113"))
 		{
			setPatkplus113(patkplus113 + 100);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef113"))
 		{
			setMdefplus113(mdefplus113 + 100);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef113"))
 		{
			setPdefplus113(pdefplus113 + 100);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc113"))
 		{
			setAccplus113(accplus113 + 1);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev113"))
 		{
			setEvasionplus113(evasionplus113 + 1);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp113"))
 		{
			setMatksplus113(matksplus113 + 100);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp113"))
 		{
			setPatksplus113(patksplus113 + 100);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp113"))
 		{
			setCpplus113(cpplus113 + 100);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp113"))
 		{
			setHpplus113(hpplus113 + 100);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp113"))
 		{
			setMpplus113(mpplus113 + 100);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk113"))
 		{
			setMatkplus113(matkplus113 - 100);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk113"))
 		{
			setPatkplus113(patkplus113 - 100);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef113"))
 		{
			setMdefplus113(mdefplus113 - 100);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef113"))
 		{
			setPdefplus113(pdefplus113 - 100);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc113"))
 		{
			setAccplus113(accplus113 - 1);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev113"))
 		{
			setEvasionplus113(evasionplus113 - 1);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp113"))
 		{
			setMatksplus113(matksplus113 - 100);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp113"))
 		{
			setPatksplus113(patksplus113 - 100);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp113"))
 		{
			setCpplus113(cpplus113 - 100);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp113"))
 		{
			setHpplus113(hpplus113 - 100);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp113"))
 		{
			setMpplus113(mpplus113 - 100);
			Balancer.mainHtml(activeChar, 113);
 			activeChar.broadcastUserInfo();
 		}
		
		else if (command.equals("addmatk114"))
 		{
			setMatkplus114(matkplus114 + 100);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk114"))
 		{
			setPatkplus114(patkplus114 + 100);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef114"))
 		{
			setMdefplus114(mdefplus114 + 100);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef114"))
 		{
			setPdefplus114(pdefplus114 + 100);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc114"))
 		{
			setAccplus114(accplus114 + 1);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev114"))
 		{
			setEvasionplus114(evasionplus114 + 1);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp114"))
 		{
			setMatksplus114(matksplus114 + 100);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp114"))
 		{
			setPatksplus114(patksplus114 + 100);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp114"))
 		{
			setCpplus114(cpplus114 + 100);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp114"))
 		{
			setHpplus114(hpplus114 + 100);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp114"))
 		{
			setMpplus114(mpplus114 + 100);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk114"))
 		{
			setMatkplus114(matkplus114 - 100);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk114"))
 		{
			setPatkplus114(patkplus114 - 100);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef114"))
 		{
			setMdefplus114(mdefplus114 - 100);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef114"))
 		{
			setPdefplus114(pdefplus114 - 100);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc114"))
 		{
			setAccplus114(accplus114 - 1);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev114"))
 		{
			setEvasionplus114(evasionplus114 - 1);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp114"))
 		{
			setMatksplus114(matksplus114 - 100);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp114"))
 		{
			setPatksplus114(patksplus114 - 100);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp114"))
 		{
			setCpplus114(cpplus114 - 100);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp114"))
 		{
			setHpplus114(hpplus114 - 100);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp114"))
 		{
			setMpplus114(mpplus114 - 100);
			Balancer.mainHtml(activeChar, 114);
 			activeChar.broadcastUserInfo();
 		}
		
		else if (command.equals("addmatk115"))
 		{
			setMatkplus115(matkplus115 + 100);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk115"))
 		{
			setPatkplus115(patkplus115 + 100);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef115"))
 		{
			setMdefplus115(mdefplus115 + 100);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef115"))
 		{
			setPdefplus115(pdefplus115 + 100);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc115"))
 		{
			setAccplus115(accplus115 + 1);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev115"))
 		{
			setEvasionplus115(evasionplus115 + 1);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp115"))
 		{
			setMatksplus115(matksplus115 + 100);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp115"))
 		{
			setPatksplus115(patksplus115 + 100);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp115"))
 		{
			setCpplus115(cpplus115 + 100);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp115"))
 		{
			setHpplus115(hpplus115 + 100);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp115"))
 		{
			setMpplus115(mpplus115 + 100);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk115"))
 		{
			setMatkplus115(matkplus115 - 100);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk115"))
 		{
			setPatkplus115(patkplus115 - 100);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef115"))
 		{
			setMdefplus115(mdefplus115 - 100);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef115"))
 		{
			setPdefplus115(pdefplus115 - 100);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc115"))
 		{
			setAccplus115(accplus115 - 1);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev115"))
 		{
			setEvasionplus115(evasionplus115 - 1);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp115"))
 		{
			setMatksplus115(matksplus115 - 100);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp115"))
 		{
			setPatksplus115(patksplus115 - 100);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp115"))
 		{
			setCpplus115(cpplus115 - 100);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp115"))
 		{
			setHpplus115(hpplus115 - 100);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp115"))
 		{
			setMpplus115(mpplus115 - 100);
			Balancer.mainHtml(activeChar, 115);
 			activeChar.broadcastUserInfo();
 		}
		
		else if (command.equals("addmatk116"))
 		{
			setMatkplus116(matkplus116 + 100);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk116"))
 		{
			setPatkplus116(patkplus116 + 100);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef116"))
 		{
			setMdefplus116(mdefplus116 + 100);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef116"))
 		{
			setPdefplus116(pdefplus116 + 100);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc116"))
 		{
			setAccplus116(accplus116 + 1);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev116"))
 		{
			setEvasionplus116(evasionplus116 + 1);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp116"))
 		{
			setMatksplus116(matksplus116 + 100);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp116"))
 		{
			setPatksplus116(patksplus116 + 100);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp116"))
 		{
			setCpplus116(cpplus116 + 100);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp116"))
 		{
			setHpplus116(hpplus116 + 100);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp116"))
 		{
			setMpplus116(mpplus116 + 100);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk116"))
 		{
			setMatkplus116(matkplus116 - 100);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk116"))
 		{
			setPatkplus116(patkplus116 - 100);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef116"))
 		{
			setMdefplus116(mdefplus116 - 100);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef116"))
 		{
			setPdefplus116(pdefplus116 - 100);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc116"))
 		{
			setAccplus116(accplus116 - 1);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev116"))
 		{
			setEvasionplus116(evasionplus116 - 1);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp116"))
 		{
			setMatksplus116(matksplus116 - 100);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp116"))
 		{
			setPatksplus116(patksplus116 - 100);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp116"))
 		{
			setCpplus116(cpplus116 - 100);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp116"))
 		{
			setHpplus116(hpplus116 - 100);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp116"))
 		{
			setMpplus116(mpplus116 - 100);
			Balancer.mainHtml(activeChar, 116);
 			activeChar.broadcastUserInfo();
 		}
		
		else if (command.equals("addmatk117"))
 		{
			setMatkplus117(matkplus117 + 100);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk117"))
 		{
			setPatkplus117(patkplus117 + 100);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef117"))
 		{
			setMdefplus117(mdefplus117 + 100);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef117"))
 		{
			setPdefplus117(pdefplus117 + 100);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc117"))
 		{
			setAccplus117(accplus117 + 1);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev117"))
 		{
			setEvasionplus117(evasionplus117 + 1);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp117"))
 		{
			setMatksplus117(matksplus117 + 100);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp117"))
 		{
			setPatksplus117(patksplus117 + 100);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp117"))
 		{
			setCpplus117(cpplus117 + 100);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp117"))
 		{
			setHpplus117(hpplus117 + 100);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp117"))
 		{
			setMpplus117(mpplus117 + 100);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk117"))
 		{
			setMatkplus117(matkplus117 - 100);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk117"))
 		{
			setPatkplus117(patkplus117 - 100);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef117"))
 		{
			setMdefplus117(mdefplus117 - 100);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef117"))
 		{
			setPdefplus117(pdefplus117 - 100);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc117"))
 		{
			setAccplus117(accplus117 - 1);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev117"))
 		{
			setEvasionplus117(evasionplus117 - 1);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp117"))
 		{
			setMatksplus117(matksplus117 - 100);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp117"))
 		{
			setPatksplus117(patksplus117 - 100);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp117"))
 		{
			setCpplus117(cpplus117 - 100);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp117"))
 		{
			setHpplus117(hpplus117 - 100);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp117"))
 		{
			setMpplus117(mpplus117 - 100);
			Balancer.mainHtml(activeChar, 117);
 			activeChar.broadcastUserInfo();
 		}
		
		else if (command.equals("addmatk118"))
 		{
			setMatkplus118(matkplus118 + 100);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatk118"))
 		{
			setPatkplus118(patkplus118 + 100);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmdef118"))
 		{
			setMdefplus118(mdefplus118 + 100);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpdef118"))
 		{
			setPdefplus118(pdefplus118 + 100);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addacc118"))
 		{
			setAccplus118(accplus118 + 1);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addev118"))
 		{
			setEvasionplus118(evasionplus118 + 1);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmatksp118"))
 		{
			setMatksplus118(matksplus118 + 100);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addpatksp118"))
 		{
			setPatksplus118(patksplus118 + 100);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addcp118"))
 		{
			setCpplus118(cpplus118 + 100);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addhp118"))
 		{
			setHpplus118(hpplus118 + 100);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("addmp118"))
 		{
			setMpplus118(mpplus118 + 100);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatk118"))
 		{
			setMatkplus118(matkplus118 - 100);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatk118"))
 		{
			setPatkplus118(patkplus118 - 100);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmdef118"))
 		{
			setMdefplus118(mdefplus118 - 100);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempdef118"))
 		{
			setPdefplus118(pdefplus118 - 100);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remacc118"))
 		{
			setAccplus118(accplus118 - 1);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remev118"))
 		{
			setEvasionplus118(evasionplus118 - 1);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmatksp118"))
 		{
			setMatksplus118(matksplus118 - 100);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("rempatksp118"))
 		{
			setPatksplus118(patksplus118 - 100);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remcp118"))
 		{
			setCpplus118(cpplus118 - 100);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remhp118"))
 		{
			setHpplus118(hpplus118 - 100);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
		else if (command.equals("remmp118"))
 		{
			setMpplus118(mpplus118 - 100);
			Balancer.mainHtml(activeChar, 118);
 			activeChar.broadcastUserInfo();
 		}
 		
		// return main html
 		else if(command.equals("edit88"))
 		{
 			Balancer.mainHtml(activeChar, 88);
 		}
 		else if(command.equals("edit89"))
 		{
 			Balancer.mainHtml(activeChar, 89);
 		}
 		else if(command.equals("edit90"))
 		{
 			Balancer.mainHtml(activeChar, 90);
 		}
 		else if(command.equals("edit91"))
 		{
 			Balancer.mainHtml(activeChar, 91);
 		}
 		else if(command.equals("edit92"))
 		{
 			Balancer.mainHtml(activeChar, 92);
 		}
 		else if(command.equals("edit93"))
 		{
 			Balancer.mainHtml(activeChar, 93);
 		}
 		else if(command.equals("edit94"))
 		{
 			Balancer.mainHtml(activeChar, 94);
 		}
 		else if(command.equals("edit95"))
 		{
 			Balancer.mainHtml(activeChar, 95);
 		}
 		else if(command.equals("edit96"))
 		{
 			Balancer.mainHtml(activeChar, 96);
 		}
 		else if(command.equals("edit97"))
 		{
 			Balancer.mainHtml(activeChar, 97);
 		}
 		else if(command.equals("edit98"))
 		{
 			Balancer.mainHtml(activeChar, 98);
 		}
 		else if(command.equals("edit99"))
 		{
 			Balancer.mainHtml(activeChar, 99);
 		}
 		else if(command.equals("edit100"))
 		{
 			Balancer.mainHtml(activeChar, 100);
 		}
 		else if(command.equals("edit101"))
 		{
 			Balancer.mainHtml(activeChar, 101);
 		}
 		else if(command.equals("edit102"))
 		{
 			Balancer.mainHtml(activeChar, 102);
 		}
 		else if(command.equals("edit103"))
 		{
 			Balancer.mainHtml(activeChar, 103);
 		}
 		else if(command.equals("edit104"))
 		{
 			Balancer.mainHtml(activeChar, 104);
 		}
 		else if(command.equals("edit105"))
 		{
 			Balancer.mainHtml(activeChar, 105);
 		}
 		else if(command.equals("edit106"))
 		{
 			Balancer.mainHtml(activeChar, 106);
 		}
 		else if(command.equals("edit107"))
 		{
 			Balancer.mainHtml(activeChar, 107);
 		}
 		else if(command.equals("edit108"))
 		{
 			Balancer.mainHtml(activeChar, 108);
 		}
 		else if(command.equals("edit109"))
 		{
 			Balancer.mainHtml(activeChar, 109);
 		}
 		else if(command.equals("edit110"))
 		{
 			Balancer.mainHtml(activeChar, 110);
 		}
 		else if(command.equals("edit111"))
 		{
 			Balancer.mainHtml(activeChar, 111);
 		}
 		else if(command.equals("edit112"))
 		{
 			Balancer.mainHtml(activeChar, 112);
 		}
 		else if(command.equals("edit113"))
 		{
 			Balancer.mainHtml(activeChar, 113);
 		}
 		else if(command.equals("edit114"))
 		{
 			Balancer.mainHtml(activeChar, 114);
 		}
 		else if(command.equals("edit115"))
 		{
 			Balancer.mainHtml(activeChar, 115);
 		}
 		else if(command.equals("edit116"))
 		{
 			Balancer.mainHtml(activeChar, 116);
 		}
 		else if(command.equals("edit117"))
 		{
 			Balancer.mainHtml(activeChar, 117);
 		}
 		else if(command.equals("edit118"))
 		{
 			Balancer.mainHtml(activeChar, 118);
 		}
		// save
 		else if(command.equals("save88"))
 		{
 			BalanceSave.storeBalance88();
			activeChar.sendMessage("Stats saved for Duelist classes.");
 			Balancer.mainHtml(activeChar, 88);
 		}
 		else if(command.equals("save89"))
 		{
 			BalanceSave.storeBalance89();
			activeChar.sendMessage("Stats saved for DreadNought class.");
 			Balancer.mainHtml(activeChar, 89);
 		}
 		else if(command.equals("save90"))
 		{
 			BalanceSave.storeBalance90();
			activeChar.sendMessage("Stats saved for Phoenix Knight class.");
 			Balancer.mainHtml(activeChar, 90);
 		}
 		else if(command.equals("save91"))
 		{
 			BalanceSave.storeBalance91();
			activeChar.sendMessage("Stats saved for Hell Knight class.");
 			Balancer.mainHtml(activeChar, 91);
 		}
 		else if(command.equals("save92"))
 		{
 			BalanceSave.storeBalance92();
			activeChar.sendMessage("Stats saved for Sagittarius class.");
 			Balancer.mainHtml(activeChar, 92);
 		}
 		else if(command.equals("save93"))
 		{
 			BalanceSave.storeBalance93();
			activeChar.sendMessage("Stats saved for Adventurer class.");
 			Balancer.mainHtml(activeChar, 93);
 		}
 		else if(command.equals("save94"))
 		{
 			BalanceSave.storeBalance94();
			activeChar.sendMessage("Stats saved for Archmage class.");
 			Balancer.mainHtml(activeChar, 94);
 		}
 		else if(command.equals("save95"))
 		{
 			BalanceSave.storeBalance95();
			activeChar.sendMessage("Stats saved for Soultaker class.");
 			Balancer.mainHtml(activeChar, 95);
 		}
 		else if(command.equals("save96"))
 		{
 			BalanceSave.storeBalance96();
			activeChar.sendMessage("Stats saved for Arcana Lord class.");
 			Balancer.mainHtml(activeChar, 96);
 		}
 		else if(command.equals("save97"))
 		{
 			BalanceSave.storeBalance97();
			activeChar.sendMessage("Stats saved for Cardinal class.");
 			Balancer.mainHtml(activeChar, 97);
 		}
 		else if(command.equals("save98"))
 		{
 			BalanceSave.storeBalance98();
			activeChar.sendMessage("Stats saved for Hierophant class.");
 			Balancer.mainHtml(activeChar, 98);
 		}
 		else if(command.equals("save99"))
 		{
 			BalanceSave.storeBalance99();
			activeChar.sendMessage("Stats saved for Eva Templar class.");
 			Balancer.mainHtml(activeChar, 99);
 		}
 		else if(command.equals("save100"))
 		{
 			BalanceSave.storeBalance100();
			activeChar.sendMessage("Stats saved for Sword Muse class.");
 			Balancer.mainHtml(activeChar, 100);
 		}
 		else if(command.equals("save101"))
 		{
 			BalanceSave.storeBalance101();
			activeChar.sendMessage("Stats saved for Wind Rider class.");
 			Balancer.mainHtml(activeChar, 101);
 		}
 		else if(command.equals("save102"))
 		{
 			BalanceSave.storeBalance102();
			activeChar.sendMessage("Stats saved for Moonlight Sentinel class.");
 			Balancer.mainHtml(activeChar, 102);
 		}
 		else if(command.equals("save103"))
 		{
 			BalanceSave.storeBalance103();
			activeChar.sendMessage("Stats saved for Mystic Muse class.");
 			Balancer.mainHtml(activeChar, 103);
 		}
 		else if(command.equals("save104"))
 		{
 			BalanceSave.storeBalance104();
			activeChar.sendMessage("Stats saved for Elemental Master class.");
 			Balancer.mainHtml(activeChar, 104);
 		}
 		else if(command.equals("save105"))
 		{
 			BalanceSave.storeBalance105();
			activeChar.sendMessage("Stats saved for Eva Saint class.");
 			Balancer.mainHtml(activeChar, 105);
 		}
 		else if(command.equals("save106"))
 		{
 			BalanceSave.storeBalance106();
			activeChar.sendMessage("Stats saved for Shillien Templar class.");
 			Balancer.mainHtml(activeChar, 106);
 		}
 		else if(command.equals("save107"))
 		{
 			BalanceSave.storeBalance107();
			activeChar.sendMessage("Stats saved for Spectral Dancer class.");
 			Balancer.mainHtml(activeChar, 107);
 		}
 		else if(command.equals("save108"))
 		{
 			BalanceSave.storeBalance108();
			activeChar.sendMessage("Stats saved for Ghost Hunter class.");
 			Balancer.mainHtml(activeChar, 108);
 		}
 		else if(command.equals("save109"))
 		{
 			BalanceSave.storeBalance109();
			activeChar.sendMessage("Stats saved for Ghost Sentinel class.");
 			Balancer.mainHtml(activeChar, 109);
 		}
 		else if(command.equals("save110"))
 		{
 			BalanceSave.storeBalance110();
			activeChar.sendMessage("Stats saved for Storm Screamer class.");
 			Balancer.mainHtml(activeChar, 110);
 		}
 		else if(command.equals("save111"))
 		{
 			BalanceSave.storeBalance111();
			activeChar.sendMessage("Stats saved for Spectral Master class.");
 			Balancer.mainHtml(activeChar, 111);
 		}
 		else if(command.equals("save112"))
 		{
 			BalanceSave.storeBalance112();
			activeChar.sendMessage("Stats saved for Shillen Saint class.");
 			Balancer.mainHtml(activeChar, 112);
 		}
 		else if(command.equals("save113"))
 		{
 			BalanceSave.storeBalance113();
			activeChar.sendMessage("Stats saved for Titan class.");
 			Balancer.mainHtml(activeChar, 113);
 		}
 		else if(command.equals("save114"))
 		{
 			BalanceSave.storeBalance114();
			activeChar.sendMessage("Stats saved for Grand Khauatari class.");
 			Balancer.mainHtml(activeChar, 114);
 		}
 		else if(command.equals("save115"))
 		{
 			BalanceSave.storeBalance115();
			activeChar.sendMessage("Stats saved for Dominator class.");
 			Balancer.mainHtml(activeChar, 115);
 		}
 		else if(command.equals("save116"))
 		{
 			BalanceSave.storeBalance116();
			activeChar.sendMessage("Stats saved for Doomcryer class.");
 			Balancer.mainHtml(activeChar, 116);
 		}
 		else if(command.equals("save117"))
 		{
 			BalanceSave.storeBalance117();
			activeChar.sendMessage("Stats saved for Fortune Seeker class.");
 			Balancer.mainHtml(activeChar, 117);
 		}
 		else if(command.equals("save118"))
 		{
 			BalanceSave.storeBalance118();
			activeChar.sendMessage("Stats saved for Maestro class.");
 			Balancer.mainHtml(activeChar, 118);
 		}
 	}
 	
	@Override
	public final L2PcInstance getActiveChar()
	{
		return (L2PcInstance) super.getActiveChar();
	}
}