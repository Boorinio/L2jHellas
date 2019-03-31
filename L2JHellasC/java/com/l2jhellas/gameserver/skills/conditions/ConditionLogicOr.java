package com.l2jhellas.gameserver.skills.conditions;

import com.l2jhellas.gameserver.skills.Env;

public class ConditionLogicOr extends Condition
{
	
	private static Condition[] _emptyConditions = new Condition[0];
	public Condition[] conditions = _emptyConditions;
	
	public void add(Condition condition)
	{
		if (condition == null)
			return;
		if (getListener() != null)
			condition.setListener(this);
		final int len = conditions.length;
		final Condition[] tmp = new Condition[len + 1];
		System.arraycopy(conditions, 0, tmp, 0, len);
		tmp[len] = condition;
		conditions = tmp;
	}
	
	@Override
	void setListener(ConditionListener listener)
	{
		if (listener != null)
		{
			for (Condition c : conditions)
				c.setListener(this);
		}
		else
		{
			for (Condition c : conditions)
				c.setListener(null);
		}
		super.setListener(listener);
	}
	
	@Override
	public boolean testImpl(Env env)
	{
		for (Condition c : conditions)
		{
			if (c.test(env))
				return true;
		}
		return false;
	}
}