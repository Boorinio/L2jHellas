package com.l2jhellas.gameserver.scrips.quests.ai.group;

import com.l2jhellas.gameserver.ai.CtrlIntention;
import com.l2jhellas.gameserver.model.L2CharPosition;
import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.quest.QuestEventType;
import com.l2jhellas.gameserver.scrips.quests.ai.AbstractNpcAI;
import com.l2jhellas.util.Rnd;

public class FleeingNPCs extends AbstractNpcAI
{
	// Victims and elpies
	private final int[] _npcId =
	{
		18150,
		18151,
		18152,
		18153,
		18154,
		18155,
		18156,
		18157,
		20432
	};
	
	public FleeingNPCs()
	{
		super(FleeingNPCs.class.getSimpleName(), "ai/group");
		
		for (int element : _npcId)
			addEventId(element, QuestEventType.ON_ATTACK);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet)
	{
		if (npc.getNpcId() >= 18150 && npc.getNpcId() <= 18157)
		{
			npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new L2CharPosition((npc.getX() + Rnd.get(-40, 40)), (npc.getY() + Rnd.get(-40, 40)), npc.getZ(), npc.getHeading()));
			npc.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE, null, null);
			return null;
		}
		else if (npc.getNpcId() == 20432)
		{
			if (Rnd.get(3) == 2)
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new L2CharPosition((npc.getX() + Rnd.get(-200, 200)), (npc.getY() + Rnd.get(-200, 200)), npc.getZ(), npc.getHeading()));
			
			npc.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE, null, null);
			return null;
		}
		return super.onAttack(npc, attacker, damage, isPet);
	}
	
	public static void main(String[] args)
	{
		new FleeingNPCs();
	}
}