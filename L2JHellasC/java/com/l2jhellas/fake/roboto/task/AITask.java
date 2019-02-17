package com.l2jhellas.fake.roboto.task;

import java.util.List;

import com.l2jhellas.fake.roboto.FakePlayer;
import com.l2jhellas.fake.roboto.FakePlayerManager;

public class AITask implements Runnable
{
	private int _from;
	private int _to;

	public AITask(int from, int to)
	{
		_from = from;
		_to = to;
	}

	@Override
	public void run()
	{
		adjustPotentialIndexOutOfBounds();
		
		List<FakePlayer> fakePlayers = FakePlayerManager.INSTANCE.getFakePlayers().subList(_from, _to);
		
		for (FakePlayer fpl : fakePlayers)
		{			
			if(fpl==null)
			   continue;
			
			if (fpl.getFakeAi() !=null && !fpl.getFakeAi().isBusyThinking()) 
				fpl.getFakeAi().thinkAndAct();
		}
	}

	private void adjustPotentialIndexOutOfBounds()
	{
		if (_to > FakePlayerManager.INSTANCE.getFakePlayersCount())
			_to = FakePlayerManager.INSTANCE.getFakePlayersCount();
	}
}
