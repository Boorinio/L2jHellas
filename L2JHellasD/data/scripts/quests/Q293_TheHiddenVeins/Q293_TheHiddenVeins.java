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
package quests.Q293_TheHiddenVeins;

import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.base.Race;
import com.l2jhellas.gameserver.model.quest.Quest;
import com.l2jhellas.gameserver.model.quest.QuestState;

public class Q293_TheHiddenVeins extends Quest
{
	private static final String qn = "Q293_TheHiddenVeins";
	
	// Items
	private static final int CHRYSOLITE_ORE = 1488;
	private static final int TORN_MAP_FRAGMENT = 1489;
	private static final int HIDDEN_VEIN_MAP = 1490;
	
	// Reward
	private static final int SOULSHOT_FOR_BEGINNERS = 5789;
	
	// NPCs
	private static final int FILAUR = 30535;
	private static final int CHINCHIRIN = 30539;
	
	// Mobs
	private static final int UTUKU_ORC = 20446;
	private static final int UTUKU_ARCHER = 20447;
	private static final int UTUKU_GRUNT = 20448;
	
	private static final int[][] DROPLIST =
	{
		{
			CHRYSOLITE_ORE,
			1,
			0,
			500000
		},
		{
			TORN_MAP_FRAGMENT,
			1,
			0,
			100000
		}
	};
	
	public Q293_TheHiddenVeins()
	{
		super(293, qn, "The Hidden Veins");
		
		setItemsIds(CHRYSOLITE_ORE, TORN_MAP_FRAGMENT, HIDDEN_VEIN_MAP);
		
		addStartNpc(FILAUR);
		addTalkId(FILAUR, CHINCHIRIN);
		
		addKillId(UTUKU_ORC, UTUKU_ARCHER, UTUKU_GRUNT);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;
		
		if (event.equalsIgnoreCase("30535-03.htm"))
		{
			st.setState(STATE_STARTED);
			st.set("cond", "1");
			st.playSound(QuestState.SOUND_ACCEPT);
		}
		else if (event.equalsIgnoreCase("30535-06.htm"))
		{
			st.playSound(QuestState.SOUND_FINISH);
			st.exitQuest(true);
		}
		else if (event.equalsIgnoreCase("30539-02.htm"))
		{
			if (st.getQuestItemsCount(TORN_MAP_FRAGMENT) >= 4)
			{
				htmltext = "30539-03.htm";
				st.playSound(QuestState.SOUND_ITEMGET);
				st.takeItems(TORN_MAP_FRAGMENT, 4);
				st.giveItems(HIDDEN_VEIN_MAP, 1);
			}
		}
		
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg();
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;
		
		switch (st.getState())
		{
			case STATE_CREATED:
				if (player.getRace() != Race.dwarf)
					htmltext = "30535-00.htm";
				else if (player.getLevel() < 6)
					htmltext = "30535-01.htm";
				else
					htmltext = "30535-02.htm";
				break;
			
			case STATE_STARTED:
				switch (npc.getNpcId())
				{
					case FILAUR:
						final int chrysoliteOres = st.getQuestItemsCount(CHRYSOLITE_ORE);
						final int hiddenVeinMaps = st.getQuestItemsCount(HIDDEN_VEIN_MAP);
						
						if (chrysoliteOres + hiddenVeinMaps == 0)
							htmltext = "30535-04.htm";
						else
						{
							if (hiddenVeinMaps > 0)
							{
								if (chrysoliteOres > 0)
									htmltext = "30535-09.htm";
								else
									htmltext = "30535-08.htm";
							}
							else
								htmltext = "30535-05.htm";
							
							int reward = (chrysoliteOres * 5) + (hiddenVeinMaps * 500) + ((chrysoliteOres >= 10) ? 2000 : 0);
							
							st.takeItems(CHRYSOLITE_ORE, -1);
							st.takeItems(HIDDEN_VEIN_MAP, -1);
							st.rewardItems(57, reward);
							
							if (player.isNewbie() && st.getInt("Reward") == 0)
							{
								st.giveItems(SOULSHOT_FOR_BEGINNERS, 6000);
								st.playTutorialVoice("tutorial_voice_026");
								st.set("Reward", "1");
							}
						}
						break;
					
					case CHINCHIRIN:
						htmltext = "30539-01.htm";
						break;
				}
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
		
		st.dropMultipleItems(DROPLIST);
		
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q293_TheHiddenVeins();
	}
}