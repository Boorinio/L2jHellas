package com.l2jhellas.gameserver.model.entity.olympiad;

import com.l2jhellas.Config;

import java.util.List;

public class OlympiadGameNonClassed extends OlympiadGameNormal
{
	private OlympiadGameNonClassed(int id, Participant[] opponents)
	{
		super(id, opponents);
	}
	
	@Override
	public final CompetitionType getType()
	{
		return CompetitionType.NON_CLASSED;
	}
	
	@Override
	protected final int getDivider()
	{
		return Config.ALT_OLY_DIVIDER_NON_CLASSED;
	}
	
	@Override
	protected final int[][] getReward()
	{
		return Config.ALT_OLY_NONCLASSED_REWARD;
	}
	
	protected static final OlympiadGameNonClassed createGame(int id, List<String> list)
	{
		final Participant[] opponents = OlympiadGameNormal.createListOfParticipants(list);
		if (opponents == null)
			return null;
		return new OlympiadGameNonClassed(id, opponents);
	}
}