package com.l2jhellas.gameserver.scrips.quests.ai.group;

import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.CreatureSay;
import com.l2jhellas.gameserver.scrips.quests.ai.AbstractNpcAI;
import com.l2jhellas.util.Rnd;

public class TurekOrcOverlord extends AbstractNpcAI
{
	private static final int TUREKO = 20588;
	
	private static boolean _FirstAttacked;
	
	public TurekOrcOverlord()
	{
		super("TurekOrcOverlord", "ai");
		int[] mobs =
		{
			TUREKO
		};
		registerMobs(mobs);
		_FirstAttacked = false;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet)
	{
		if (npc.getNpcId() == TUREKO)
		{
			if (_FirstAttacked)
			{
				if (Rnd.get(100) == 50)
					attacker.sendPacket(new CreatureSay(npc.getObjectId(), 0, npc.getName(), "Dear ultimate power!!!"));
			}
			else
			{
				_FirstAttacked = true;
			}
		}
		return super.onAttack(npc, attacker, damage, isPet);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		int npcId = npc.getNpcId();
		if (npcId == TUREKO)
		{
			_FirstAttacked = false;
		}
		return super.onKill(npc, killer, isPet);
	}
	
	public static void main(String[] args)
	{
		new TurekOrcOverlord();
	}
}