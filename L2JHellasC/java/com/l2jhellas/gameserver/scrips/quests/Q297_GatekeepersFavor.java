package com.l2jhellas.gameserver.scrips.quests;

import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.quest.Quest;
import com.l2jhellas.gameserver.model.quest.QuestState;

public class Q297_GatekeepersFavor extends Quest
{
	private static final String qn = "Q297_GatekeepersFavor";
	
	// Item
	private static final int STARSTONE = 1573;
	
	// Reward
	private static final int GATEKEEPER_TOKEN = 1659;
	
	public Q297_GatekeepersFavor()
	{
		super(297, qn, "Gatekeeper's Favor");
		
		setItemsIds(STARSTONE);
		
		addStartNpc(30540); // Wirphy
		addTalkId(30540);
		
		addKillId(20521); // Whinstone Golem
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;
		
		if (event.equalsIgnoreCase("30540-03.htm"))
		{
			st.setState(STATE_STARTED);
			st.set("cond", "1");
			st.playSound(QuestState.SOUND_ACCEPT);
		}
		
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(qn);
		String htmltext = getNoQuestMsg();
		if (st == null)
			return htmltext;
		
		switch (st.getState())
		{
			case STATE_CREATED:
				htmltext = (player.getLevel() < 15) ? "30540-01.htm" : "30540-02.htm";
				break;
			
			case STATE_STARTED:
				if (st.getInt("cond") == 1)
					htmltext = "30540-04.htm";
				else
				{
					htmltext = "30540-05.htm";
					st.takeItems(STARSTONE, -1);
					st.rewardItems(GATEKEEPER_TOKEN, 2);
					st.playSound(QuestState.SOUND_FINISH);
					st.exitQuest(true);
				}
				break;
		}
		
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = checkPlayerCondition(player, npc, "cond", "1");
		if (st == null)
			return null;
		
		if (st.dropItems(STARSTONE, 1, 20, 500000))
			st.set("cond", "2");
		
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q297_GatekeepersFavor();
	}
}