package com.l2jhellas.gameserver.scrips.quests;

import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.quest.Quest;
import com.l2jhellas.gameserver.model.quest.QuestState;
import com.l2jhellas.util.Rnd;

public class Q661_MakingTheHarvestGroundsSafe extends Quest
{
	private static final String qn = "Q661_MakingTheHarvestGroundsSafe";
	
	// NPC
	private static final int NORMAN = 30210;
	
	// Items
	private static final int STING_OF_GIANT_PB = 8283;
	private static final int CLOUDY_GEM = 8284;
	private static final int TALON_OF_YA = 8285;
	
	// Reward
	private static final int ADENA = 57;
	
	// Monsters
	private static final int GIANT_PB = 21095;
	private static final int CLOUDY_BEAST = 21096;
	private static final int YOUNG_ARANEID = 21097;
	
	public Q661_MakingTheHarvestGroundsSafe()
	{
		super(661, qn, "Making the Harvest Grounds Safe");
		
		setItemsIds(STING_OF_GIANT_PB, CLOUDY_GEM, TALON_OF_YA);
		
		addStartNpc(NORMAN);
		addTalkId(NORMAN);
		
		addKillId(GIANT_PB, CLOUDY_BEAST, YOUNG_ARANEID);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;
		
		if (event.equalsIgnoreCase("30210-02.htm"))
		{
			st.set("cond", "1");
			st.setState(STATE_STARTED);
			st.playSound(QuestState.SOUND_ACCEPT);
		}
		else if (event.equalsIgnoreCase("30210-04.htm"))
		{
			int item1 = st.getQuestItemsCount(STING_OF_GIANT_PB);
			int item2 = st.getQuestItemsCount(CLOUDY_GEM);
			int item3 = st.getQuestItemsCount(TALON_OF_YA);
			int sum = 0;
			
			sum = (item1 * 57) + (item2 * 56) + (item3 * 60);
			
			if (item1 + item2 + item3 >= 10)
				sum += 2871;
			
			st.takeItems(STING_OF_GIANT_PB, item1);
			st.takeItems(CLOUDY_GEM, item2);
			st.takeItems(TALON_OF_YA, item3);
			st.rewardItems(ADENA, sum);
		}
		else if (event.equalsIgnoreCase("30210-06.htm"))
			st.exitQuest(true);
		
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
				if (player.getLevel() >= 21)
					htmltext = "30210-01.htm";
				else
				{
					htmltext = "30210-01a.htm";
					st.exitQuest(true);
				}
				break;
			
			case STATE_STARTED:
				if (st.hasAtLeastOneQuestItem(STING_OF_GIANT_PB, CLOUDY_GEM, TALON_OF_YA))
					htmltext = "30210-03.htm";
				else
					htmltext = "30210-05.htm";
				break;
		}
		
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = checkPlayerState(player, npc, STATE_STARTED);
		if (st == null)
			return null;
		
		if (Rnd.get(100) < 50)
		{
			switch (npc.getNpcId())
			{
				case GIANT_PB:
					st.giveItems(STING_OF_GIANT_PB, 1);
					break;
				case CLOUDY_BEAST:
					st.giveItems(CLOUDY_GEM, 1);
					break;
				case YOUNG_ARANEID:
					st.giveItems(TALON_OF_YA, 1);
					break;
			}
			st.playSound(QuestState.SOUND_ITEMGET);
		}
		
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q661_MakingTheHarvestGroundsSafe();
	}
}