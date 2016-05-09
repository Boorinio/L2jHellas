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
package ai.group;

import java.util.HashMap;

import ai.AbstractNpcAI;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.ai.CtrlIntention;
import com.l2jhellas.gameserver.model.actor.L2Attackable;
import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.quest.QuestEventType;
import com.l2jhellas.util.Rnd;

public class Splendor extends AbstractNpcAI
{
	private static boolean AlwaysSpawn;

	private static HashMap<Integer, int[]> SplendorId = new HashMap<Integer, int[]>();
	public Splendor(int questId, String name, String descr)
	{
		super(name, descr);

		AlwaysSpawn = false;

		SplendorId.put(21521, new int[] {21522,5,1});
		SplendorId.put(21524, new int[] {21525,5,1});
		SplendorId.put(21527, new int[] {21528,5,1});
		SplendorId.put(21537, new int[] {21538,5,1});
		SplendorId.put(21539, new int[] {21540,100,2});

		for(int NPC_ID: SplendorId.keySet())
		{
			addEventId(NPC_ID, QuestEventType.ON_ATTACK);
			addEventId(NPC_ID, QuestEventType.ON_KILL);
		}
	}
	@Override
	public String onAttack (L2Npc npc, L2PcInstance attacker, int damage, boolean isPet)
	{
		int npcId = npc.getNpcId();
		int NewMob = SplendorId.get(npcId)[0];
		int chance = SplendorId.get(npcId)[1];
		int ModeSpawn = SplendorId.get(npcId)[2];
		if(Rnd.get(100) <= chance * Config.RATE_DROP_QUEST)
		{
			if(SplendorId.containsKey(npcId))
			{
				if(ModeSpawn == 1)
				{
					npc.deleteMe();
					L2Attackable newNpc = (L2Attackable) addSpawn(NewMob, npc,false, 0, false);
					newNpc.addDamageHate(attacker,0,999);
					newNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
				}
				else if(AlwaysSpawn)
				{
					return super.onAttack(npc, attacker, damage, isPet);
				}
				else if(ModeSpawn == 2)
				{
					AlwaysSpawn = true;
					L2Attackable newNpc1 = (L2Attackable) addSpawn(NewMob, npc,false,0,false);
					newNpc1.addDamageHate(attacker,0,999);
					newNpc1.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
				}
			}
		}

		return super.onAttack(npc, attacker, damage, isPet);
	}

	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		int npcId = npc.getNpcId();
		int ModeSpawn = SplendorId.get(npcId)[2];
		if(SplendorId.containsKey(npcId))
		{
			if(ModeSpawn == 2)
			{
				AlwaysSpawn = false;
			}
		}

		return super.onKill(npc,killer,isPet);
	}
	public static void main(String[] args)
	{
		new Splendor(-1, "Splendor", "ai");
	}

}