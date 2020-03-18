package com.l2jhellas.gameserver.scrips.quests;

import com.l2jhellas.gameserver.enums.player.ClassRace;
import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.quest.Quest;
import com.l2jhellas.gameserver.model.quest.QuestState;
import com.l2jhellas.util.Rnd;

public class Q276_TotemOfTheHestui extends Quest
{
	private static final String qn = "Q276_TotemOfTheHestui";
	
	// Items
	private static final int KASHA_PARASITE = 1480;
	private static final int KASHA_CRYSTAL = 1481;
	
	// Rewards
	private static final int HESTUI_TOTEM = 1500;
	private static final int LEATHER_PANTS = 29;
	
	public Q276_TotemOfTheHestui()
	{
		super(276, qn, "Totem of the Hestui");
		
		setItemsIds(KASHA_PARASITE, KASHA_CRYSTAL);
		
		addStartNpc(30571); // Tanapi
		addTalkId(30571);
		
		addKillId(20479, 27044);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;
		
		if (event.equalsIgnoreCase("30571-03.htm"))
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
				if (player.getRace() != ClassRace.ORC)
					htmltext = "30571-00.htm";
				else if (player.getLevel() < 15)
					htmltext = "30571-01.htm";
				else
					htmltext = "30571-02.htm";
				break;
			
			case STATE_STARTED:
				if (st.getInt("cond") == 1)
					htmltext = "30571-04.htm";
				else
				{
					htmltext = "30571-05.htm";
					st.takeItems(KASHA_CRYSTAL, -1);
					st.takeItems(KASHA_PARASITE, -1);
					st.giveItems(HESTUI_TOTEM, 1);
					st.giveItems(LEATHER_PANTS, 1);
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
		
		if (!st.hasQuestItems(KASHA_CRYSTAL))
		{
			switch (npc.getNpcId())
			{
				case 20479:
					final int count = st.getQuestItemsCount(KASHA_PARASITE);
					final int random = Rnd.get(100);
					
					if (count >= 79 || (count >= 69 && random <= 20) || (count >= 59 && random <= 15) || (count >= 49 && random <= 10) || (count >= 39 && random < 2))
					{
						addSpawn(27044, npc, true, 0, true);
						st.takeItems(KASHA_PARASITE, count);
					}
					else
					{
						st.playSound(QuestState.SOUND_ITEMGET);
						st.giveItems(KASHA_PARASITE, 1);
					}
					break;
				
				case 27044:
					st.set("cond", "2");
					st.playSound(QuestState.SOUND_MIDDLE);
					st.giveItems(KASHA_CRYSTAL, 1);
					break;
			}
		}
		
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q276_TotemOfTheHestui();
	}
}