package ai.group;

import ai.AbstractNpcAI;

import com.l2jhellas.gameserver.datatables.sql.SpawnTable;
import com.l2jhellas.gameserver.model.L2Spawn;
import com.l2jhellas.gameserver.model.actor.L2Attackable;
import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.util.Util;

public class SeeThroughSilentMove extends AbstractNpcAI
{
	private static final int[] MOBIDS = {18001,18002,22199,22215,22216,22217,29009,29010,29011,29012,29013};
	
	public SeeThroughSilentMove(int questId, String name, String descr)
	{
		super(name, descr);
		for (L2Spawn npc : SpawnTable.getInstance().getSpawnTable().values())
			if (Util.contains(MOBIDS,npc.getNpcid()) && npc.getLastSpawn() != null && npc.getLastSpawn() instanceof L2Attackable)
				((L2Attackable)npc.getLastSpawn()).seeThroughSilentMove(true);
		for (int npcId : MOBIDS)
			this.addSpawnId(npcId);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		if (npc instanceof L2Attackable)
			((L2Attackable)npc).seeThroughSilentMove(true);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new SeeThroughSilentMove(-1, "SeeThroughSilentMove", "ai");
	}
}