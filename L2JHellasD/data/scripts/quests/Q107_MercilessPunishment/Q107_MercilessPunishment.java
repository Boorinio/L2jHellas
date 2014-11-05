/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.Q107_MercilessPunishment;

import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.base.Race;
import com.l2jhellas.gameserver.model.quest.Quest;
import com.l2jhellas.gameserver.model.quest.QuestState;

public class Q107_MercilessPunishment extends Quest
{
	private static final String qn = "Q107_MercilessPunishment";
	
	// NPCs
	private static final int HATOS = 30568;
	private static final int PARUGON = 30580;
	
	// Items
	private static final int HATOS_ORDER_1 = 1553;
	private static final int HATOS_ORDER_2 = 1554;
	private static final int HATOS_ORDER_3 = 1555;
	private static final int LETTER_TO_HUMAN = 1557;
	private static final int LETTER_TO_DARKELF = 1556;
	private static final int LETTER_TO_ELF = 1558;
	
	// Rewards
	private static final int BUTCHER_SWORD = 1510;
	private static final int SPIRITSHOT_FOR_BEGINNERS = 5790;
	private static final int SOULSHOT_FOR_BEGINNERS = 5789;
	private static final int ECHO_BATTLE = 4412;
	private static final int ECHO_LOVE = 4413;
	private static final int ECHO_SOLITUDE = 4414;
	private static final int ECHO_FEAST = 4415;
	private static final int ECHO_CELEBRATION = 4416;
	private static final int LESSER_HEALING_POTION = 1060;
	
	public Q107_MercilessPunishment()
	{
		super(107, qn, "Merciless Punishment");
		
		setItemsIds(HATOS_ORDER_1, HATOS_ORDER_2, HATOS_ORDER_3, LETTER_TO_HUMAN, LETTER_TO_DARKELF, LETTER_TO_ELF);
		
		addStartNpc(HATOS);
		addTalkId(HATOS, PARUGON);
		
		addKillId(27041); // Baranka's Messenger
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(qn);
		String htmltext = event;
		if (st == null)
			return htmltext;
		
		if (event.equalsIgnoreCase("30568-03.htm"))
		{
			st.setState(STATE_STARTED);
			st.set("cond", "1");
			st.playSound(QuestState.SOUND_ACCEPT);
			st.giveItems(HATOS_ORDER_1, 1);
		}
		else if (event.equalsIgnoreCase("30568-06.htm"))
		{
			st.playSound(QuestState.SOUND_GIVEUP);
			st.exitQuest(true);
		}
		else if (event.equalsIgnoreCase("30568-07.htm"))
		{
			st.set("cond", "4");
			st.playSound(QuestState.SOUND_MIDDLE);
			st.takeItems(HATOS_ORDER_1, 1);
			st.giveItems(HATOS_ORDER_2, 1);
		}
		else if (event.equalsIgnoreCase("30568-09.htm"))
		{
			st.set("cond", "6");
			st.playSound(QuestState.SOUND_MIDDLE);
			st.takeItems(HATOS_ORDER_2, 1);
			st.giveItems(HATOS_ORDER_3, 1);
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
				if (player.getRace() != Race.orc)
					htmltext = "30568-00.htm";
				else if (player.getLevel() < 12)
					htmltext = "30568-01.htm";
				else
					htmltext = "30568-02.htm";
				break;
			
			case STATE_STARTED:
				int cond = st.getInt("cond");
				switch (npc.getNpcId())
				{
					case HATOS:
						if (cond == 1 || cond == 2)
							htmltext = "30568-04.htm";
						else if (cond == 3)
							htmltext = "30568-05.htm";
						else if (cond == 4 || cond == 6)
							htmltext = "30568-09.htm";
						else if (cond == 5)
							htmltext = "30568-08.htm";
						else if (cond == 7)
						{
							htmltext = "30568-10.htm";
							st.takeItems(HATOS_ORDER_3, -1);
							st.takeItems(LETTER_TO_DARKELF, -1);
							st.takeItems(LETTER_TO_HUMAN, -1);
							st.takeItems(LETTER_TO_ELF, -1);
							
							st.giveItems(BUTCHER_SWORD, 1);
							st.giveItems(LESSER_HEALING_POTION, 100);
							
							if (player.isNewbie())
							{
								st.showQuestionMark(26);
								if (player.isMageClass())
								{
									st.playTutorialVoice("tutorial_voice_027");
									st.giveItems(SPIRITSHOT_FOR_BEGINNERS, 3000);
								}
								else
								{
									st.playTutorialVoice("tutorial_voice_026");
									st.giveItems(SOULSHOT_FOR_BEGINNERS, 6000);
								}
							}
							
							st.giveItems(ECHO_BATTLE, 10);
							st.giveItems(ECHO_LOVE, 10);
							st.giveItems(ECHO_SOLITUDE, 10);
							st.giveItems(ECHO_FEAST, 10);
							st.giveItems(ECHO_CELEBRATION, 10);
							st.playSound(QuestState.SOUND_FINISH);
							st.exitQuest(false);
						}
						break;
					
					case PARUGON:
						htmltext = "30580-01.htm";
						if (cond == 1)
						{
							st.set("cond", "2");
							st.playSound(QuestState.SOUND_MIDDLE);
						}
						break;
				}
				break;
			
			case STATE_COMPLETED:
				htmltext = getAlreadyCompletedMsg();
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
		
		int cond = st.getInt("cond");
		
		if (cond == 2)
		{
			st.set("cond", "3");
			st.playSound(QuestState.SOUND_MIDDLE);
			st.giveItems(LETTER_TO_HUMAN, 1);
		}
		else if (cond == 4)
		{
			st.set("cond", "5");
			st.playSound(QuestState.SOUND_MIDDLE);
			st.giveItems(LETTER_TO_DARKELF, 1);
		}
		else if (cond == 6)
		{
			st.set("cond", "7");
			st.playSound(QuestState.SOUND_MIDDLE);
			st.giveItems(LETTER_TO_ELF, 1);
		}
		
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q107_MercilessPunishment();
	}
}