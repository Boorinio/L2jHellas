package com.l2jhellas.gameserver.model.entity.olympiad;

import java.util.List;

import com.l2jhellas.Config;
import com.l2jhellas.util.Rnd;

public class OlympiadGameClassed extends OlympiadGameNormal
{
	private OlympiadGameClassed(int id, Participant[] opponents)
	{
		super(id, opponents);
	}
	
	@Override
	public final CompetitionType getType()
	{
		return CompetitionType.CLASSED;
	}
	
	@Override
	protected final int getDivider()
	{
		return Config.ALT_OLY_DIVIDER_CLASSED;
	}
	
	@Override
	protected final int[][] getReward()
	{
		return Config.ALT_OLY_CLASSED_REWARD;
	}
	
	protected static final OlympiadGameClassed createGame(int id, List<List<String>> readyClassed)
	{
		if (readyClassed == null || readyClassed.isEmpty())
			return null;
		
		List<String> list;
		Participant[] opponents;
		while (!readyClassed.isEmpty())
		{
			list = readyClassed.get(Rnd.get(readyClassed.size()));
			if (list == null || list.size() < 2)
			{
				readyClassed.remove(list);
				continue;
			}
			
			opponents = OlympiadGameNormal.createListOfParticipants(list);
			if (opponents == null)
			{
				readyClassed.remove(list);
				continue;
			}
			
			return new OlympiadGameClassed(id, opponents);
		}
		return null;
	}
}