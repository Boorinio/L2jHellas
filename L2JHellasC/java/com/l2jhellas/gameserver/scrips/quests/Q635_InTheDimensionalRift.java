package com.l2jhellas.gameserver.scrips.quests;

import com.l2jhellas.gameserver.model.Location;
import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.quest.Quest;
import com.l2jhellas.gameserver.model.quest.QuestState;


public class Q635_InTheDimensionalRift extends Quest
{
	private static final String qn = "Q635_InTheDimensionalRift";
	
	private static final int DIMENSION_FRAGMENT = 7079;

	private static final Location[] COORD =
	{
		new Location(-41572, 209731, -5087), // Necropolis of Sacrifice
		new Location(-52872, -250283, -7908), // Catacomb of the Heretic
		new Location(45256, 123906, -5411), // Pilgrim's Necropolis
		new Location(46192, 170290, -4981), // Catacomb of the Branded
		new Location(111273, 174015, -5437), // Necropolis of Worship
		new Location(-20604, -250789, -8165), // Catacomb of Apostate
		new Location(-21726, 77385, -5171), // Patriot's Necropolis
		new Location(140405, 79679, -5427), // Catacomb of the Witch
		new Location(-52366, 79097, -4741), // Necropolis of Devotion (ex Ascetics)
		new Location(118311, 132797, -4829), // Necropolis of Martyrdom
		new Location(172185, -17602, -4901), // Disciple's Necropolis
		new Location(83000, 209213, -5439), // Saint's Necropolis
		new Location(-19500, 13508, -4901), // Catacomb of Dark Omens
		new Location(113865, 84543, -6541), // Catacomb of the Forbidden Path
	};

	private static final int MAX_QUEST = 23;
	
	public Q635_InTheDimensionalRift()
	{
		super(635, qn, "In the Dimensional Rift");
		
		for (int i = 31494; i <= 31508; i++)
		{
			addTalkId(i);
			addStartNpc(i);
		}
		for (int i = 31488; i <= 31494; i++)
		{
			addTalkId(i);
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState qs = player.getQuestState(qn);
		
		if (qs == null)
			return htmltext;
		
		final int id = qs.getInt("id");
		final int count = qs.getInt("count");
		if (event == "5.htm")
		{
			if (id > 0)
			{
				if (count > 0)
					htmltext = "5a.htm";

				qs.set("count", "" + (count + 1));
				qs.setState(STATE_STARTED);
				qs.set("cond", "1");
				qs.getPlayer().teleToLocation(-114790, -180576, -6781);
			}
			else
			{
				htmltext = "What are you trying to do?";
				qs.exitQuest(true);
			}
		}
		else if (event == "6.htm")
			qs.exitQuest(true);
		
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg();
		final QuestState qs = player.getQuestState(qn);
		
		if (qs == null)
			return htmltext;
		
		final int npcId = npc.getNpcId();
		int id = qs.getInt("id");
		if ((npcId >= 31494) && (npcId <= 31508))
		{
			if (player.getLevel() < 20)
			{
				qs.exitQuest(true);
				htmltext = "1.htm";
			}
			else if (player.getAllActiveQuests(false).length > MAX_QUEST)
			{
				qs.exitQuest(true);
				htmltext = "1a.htm";
			}
			else if (!(qs.getQuestItemsCount(DIMENSION_FRAGMENT) > 0))
				htmltext = "3.htm";
			else
			{
				qs.setState(STATE_CREATED);
				id = (npcId - 31493);
				qs.set("id", "" + id);
				htmltext = "4.htm";
			}
		}
		else if (qs.getState() == STATE_STARTED)
		{
			if (id > 0)
			{
				player.teleToLocation(COORD[id], false);
				qs.unset("cond");
				qs.setState(STATE_CREATED);
				htmltext = "7.htm";
			}
			else
			{
				htmltext = "Where?";
				qs.exitQuest(true);
			}
		}
		
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q635_InTheDimensionalRift();
	}
}