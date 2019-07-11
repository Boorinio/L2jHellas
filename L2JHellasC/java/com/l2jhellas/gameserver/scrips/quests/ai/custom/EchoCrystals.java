package com.l2jhellas.gameserver.scrips.quests.ai.custom;

import java.util.HashMap;
import java.util.Map;

import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.quest.Quest;
import com.l2jhellas.gameserver.model.quest.QuestState;
import com.l2jhellas.util.Util;

public class EchoCrystals extends Quest
{
	private static final String qn = "EchoCrystals";
	
	private static final int ADENA = 57;
	private static final int COST = 200;
	
	private static final Map<Integer, ScoreData> SCORES = new HashMap<>();
	{
		SCORES.put(4410, new ScoreData(4411, "01", "02", "03"));
		SCORES.put(4409, new ScoreData(4412, "04", "05", "06"));
		SCORES.put(4408, new ScoreData(4413, "07", "08", "09"));
		SCORES.put(4420, new ScoreData(4414, "10", "11", "12"));
		SCORES.put(4421, new ScoreData(4415, "13", "14", "15"));
		SCORES.put(4419, new ScoreData(4417, "16", "05", "06"));
		SCORES.put(4418, new ScoreData(4416, "17", "05", "06"));
	}
	
	public EchoCrystals()
	{
		super(-1, qn, "custom");
		
		addStartNpc(31042, 31043);
		addTalkId(31042, 31043);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(qn);
		
		if (st != null && Util.isDigit(event))
		{
			int score = Integer.parseInt(event);
			if (SCORES.containsKey(score))
			{
				int crystal = SCORES.get(score).getCrystalId();
				String ok = SCORES.get(score).getOkMsg();
				String noadena = SCORES.get(score).getNoAdenaMsg();
				String noscore = SCORES.get(score).getNoScoreMsg();
				
				if (st.getQuestItemsCount(score) == 0)
					htmltext = npc.getNpcId() + "-" + noscore + ".htm";
				else if (st.getQuestItemsCount(ADENA) < COST)
					htmltext = npc.getNpcId() + "-" + noadena + ".htm";
				else
				{
					st.takeItems(ADENA, COST);
					st.giveItems(crystal, 1);
					htmltext = npc.getNpcId() + "-" + ok + ".htm";
				}
			}
		}
		
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		return "1.htm";
	}
	
	private class ScoreData
	{
		private final int _crystalId;
		private final String _okMsg;
		private final String _noAdenaMsg;
		private final String _noScoreMsg;
		
		public ScoreData(int crystalId, String okMsg, String noAdenaMsg, String noScoreMsg)
		{
			_crystalId = crystalId;
			_okMsg = okMsg;
			_noAdenaMsg = noAdenaMsg;
			_noScoreMsg = noScoreMsg;
		}
		
		public int getCrystalId()
		{
			return _crystalId;
		}
		
		public String getOkMsg()
		{
			return _okMsg;
		}
		
		public String getNoAdenaMsg()
		{
			return _noAdenaMsg;
		}
		
		public String getNoScoreMsg()
		{
			return _noScoreMsg;
		}
	}
	
	public static void main(String[] args)
	{
		new EchoCrystals();
	}
}