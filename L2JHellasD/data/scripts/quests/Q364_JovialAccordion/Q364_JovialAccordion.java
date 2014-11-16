package quests.Q364_JovialAccordion;

import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.quest.Quest;
import com.l2jhellas.gameserver.model.quest.QuestState;
import com.l2jhellas.util.Rnd;

public class Q364_JovialAccordion extends Quest
{
	private static final String qn = "Q364_JovialAccordion";
	
	// NPCs
	private static final int BARBADO = 30959;
	private static final int SWAN = 30957;
	private static final int SABRIN = 30060;
	private static final int XABER = 30075;
	private static final int CLOTH_CHEST = 30961;
	private static final int BEER_CHEST = 30960;
	
	// Items
	private static final int KEY_1 = 4323;
	private static final int KEY_2 = 4324;
	private static final int STOLEN_BEER = 4321;
	private static final int STOLEN_CLOTHES = 4322;
	private static final int ECHO = 4421;
	
	public Q364_JovialAccordion(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		setItemsIds(KEY_1, KEY_2, STOLEN_BEER, STOLEN_CLOTHES);
		
		addStartNpc(BARBADO);
		addTalkId(BARBADO, SWAN, SABRIN, XABER, CLOTH_CHEST, BEER_CHEST);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;
		
		if (event.equalsIgnoreCase("30959-02.htm"))
		{
			st.set("cond", "1");
			st.set("items", "0");
			st.setState(STATE_STARTED);
			st.playSound(QuestState.SOUND_ACCEPT);
		}
		else if (event.equalsIgnoreCase("30957-02.htm"))
		{
			st.set("cond", "2");
			st.giveItems(KEY_1, 1);
			st.giveItems(KEY_2, 1);
			st.playSound(QuestState.SOUND_MIDDLE);
		}
		else if (event.equalsIgnoreCase("30960-04.htm"))
		{
			if (st.getQuestItemsCount(KEY_2) == 1)
			{
				st.takeItems(KEY_2, 1);
				if (Rnd.get(10) < 5)
				{
					htmltext = "30960-02.htm";
					st.giveItems(STOLEN_BEER, 1);
					st.playSound(QuestState.SOUND_ITEMGET);
				}
			}
		}
		else if (event.equalsIgnoreCase("30961-04.htm"))
		{
			if (st.getQuestItemsCount(KEY_1) == 1)
			{
				st.takeItems(KEY_1, 1);
				if (Rnd.get(10) < 5)
				{
					htmltext = "30961-02.htm";
					st.giveItems(STOLEN_CLOTHES, 1);
					st.playSound(QuestState.SOUND_ITEMGET);
				}
			}
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
				if (player.getLevel() >= 15)
					htmltext = "30959-01.htm";
				else
				{
					htmltext = "30959-00.htm";
					st.exitQuest(true);
				}
				break;
			
			case STATE_STARTED:
				int cond = st.getInt("cond");
				int stolenItems = st.getInt("items");
				
				switch (npc.getNpcId())
				{
					case BARBADO:
						if (cond == 1 || cond == 2)
							htmltext = "30959-03.htm";
						else if (cond == 3)
						{
							htmltext = "30959-04.htm";
							st.giveItems(ECHO, 1);
							st.playSound(QuestState.SOUND_FINISH);
							st.exitQuest(true);
						}
						break;
					
					case SWAN:
						if (cond == 1)
							htmltext = "30957-01.htm";
						else if (cond == 2)
						{
							if (stolenItems > 0)
							{
								st.set("cond", "3");
								st.playSound(QuestState.SOUND_MIDDLE);
								
								if (stolenItems == 2)
								{
									htmltext = "30957-04.htm";
									st.rewardItems(57, 100);
								}
								else
									htmltext = "30957-05.htm";
							}
							else
							{
								if (st.getQuestItemsCount(KEY_1) == 0 && st.getQuestItemsCount(KEY_2) == 0)
								{
									htmltext = "30957-06.htm";
									st.playSound(QuestState.SOUND_FINISH);
									st.exitQuest(true);
								}
								else
									htmltext = "30957-03.htm";
							}
						}
						else if (cond == 3)
							htmltext = "30957-07.htm";
						break;
					
					case BEER_CHEST:
						htmltext = "30960-03.htm";
						if (cond == 2 && st.getQuestItemsCount(KEY_2) == 1)
							htmltext = "30960-01.htm";
						break;
					
					case CLOTH_CHEST:
						htmltext = "30961-03.htm";
						if (cond == 2 && st.getQuestItemsCount(KEY_1) == 1)
							htmltext = "30961-01.htm";
						break;
					
					case SABRIN:
						if (st.getQuestItemsCount(STOLEN_BEER) == 1)
						{
							htmltext = "30060-01.htm";
							st.takeItems(STOLEN_BEER, 1);
							st.playSound(QuestState.SOUND_ITEMGET);
							st.set("items", String.valueOf(stolenItems + 1));
						}
						else
							htmltext = "30060-02.htm";
						break;
					
					case XABER:
						if (st.getQuestItemsCount(STOLEN_CLOTHES) == 1)
						{
							htmltext = "30075-01.htm";
							st.takeItems(STOLEN_CLOTHES, 1);
							st.playSound(QuestState.SOUND_ITEMGET);
							st.set("items", String.valueOf(stolenItems + 1));
						}
						else
							htmltext = "30075-02.htm";
						break;
				}
				break;
		}
		
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q364_JovialAccordion(364, qn, "Jovial Accordion");
	}
}