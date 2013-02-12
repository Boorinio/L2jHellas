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
package ai.group_template;

import com.l2jhellas.gameserver.model.actor.instance.L2NpcInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.skills.SkillTable;
import com.l2jhellas.util.Rnd;

public class HotSprings extends L2AttackableAIScript
{
	public HotSprings(int questId, String name, String descr)
	{
		super(questId, name, descr);
		int[] mobs = {21316, 21321, 21314, 21319};
		this.registerMobs(mobs);
	}

	@Override
	public String onAttack (L2NpcInstance npc, L2PcInstance attacker, int damage, boolean isPet)
	{
		int HSBUFF = Rnd.get(5);
		if (npc.getNpcId() == 21316 ||
			npc.getNpcId() == 21321 ||
			npc.getNpcId() == 21314 ||
			npc.getNpcId() == 21319)
		{
			if (HSBUFF == 1)
			{
				if (Rnd.get(100) < 5)
				{
					if (attacker.getFirstEffect(4551) != null)
					{
						int rheumatism = attacker.getFirstEffect(4551).getLevel();
						if (rheumatism < 10)
						{
							int newrheumatism = (rheumatism + 1);
							npc.setTarget(attacker);
							npc.doCast(SkillTable.getInstance().getInfo(4551, newrheumatism));
						}
					}
					else
					{
						npc.setTarget(attacker);
						npc.doCast(SkillTable.getInstance().getInfo(4551, 1));
					}
				}
			}
			else if (HSBUFF == 2)
			{
				if (Rnd.get(100) < 5)
				{
					if (attacker.getFirstEffect(4552) != null)
					{
						int cholera = attacker.getFirstEffect(4552).getLevel();
						if (cholera < 10)
						{
							int newcholera = (cholera + 1);
							npc.setTarget(attacker);
							npc.doCast(SkillTable.getInstance().getInfo(4552, newcholera));
						}
					}
					else
					{
						npc.setTarget(attacker);
						npc.doCast(SkillTable.getInstance().getInfo(4552, 1));
					}
				}
			}
			else if (HSBUFF == 3)
			{
				if (Rnd.get(100) < 5)
				{
					if (attacker.getFirstEffect(4553) != null)
					{
						int flu = attacker.getFirstEffect(4553).getLevel();
						if (flu < 10)
						{
							int newflu = (flu + 1);
							npc.setTarget(attacker);
							npc.doCast(SkillTable.getInstance().getInfo(4553, newflu));
						}
					}
					else
					{
						npc.setTarget(attacker);
						npc.doCast(SkillTable.getInstance().getInfo(4553, 1));
					}
				}
			}
			else if (HSBUFF == 4)
			{
				if (Rnd.get(100) < 5)
				{
					if (attacker.getFirstEffect(4554) != null)
					{
						int malaria = attacker.getFirstEffect(4554).getLevel();
						if (malaria < 10)
						{
							int newmalaria = (malaria + 1);
							npc.setTarget(attacker);
							npc.doCast(SkillTable.getInstance().getInfo(4554, newmalaria));
						}
					}
					else
					{
						npc.setTarget(attacker);
						npc.doCast(SkillTable.getInstance().getInfo(4554, 1));
					}
				}
			}
		}
		return super.onAttack (npc, attacker, damage, isPet);
	}

	public static void main(String[] args)
	{
		new HotSprings(-1, "HotSprings", "ai");
	}
}