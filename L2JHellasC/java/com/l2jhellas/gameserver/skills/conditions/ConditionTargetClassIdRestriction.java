package com.l2jhellas.gameserver.skills.conditions;

import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.skills.Env;

import java.util.ArrayList;

public class ConditionTargetClassIdRestriction extends Condition
{
	private final ArrayList<Integer> _classIds;
	
	public ConditionTargetClassIdRestriction(ArrayList<Integer> array)
	{
		_classIds = array;
	}
	
	@Override
	public boolean testImpl(Env env)
	{
		if (!(env.target instanceof L2PcInstance))
			return true;
		return (!_classIds.contains(((L2PcInstance) env.target).getClassId().getId()));
	}
}