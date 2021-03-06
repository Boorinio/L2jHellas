package Extensions.AchievmentsEngine.conditions;

import Extensions.AchievmentsEngine.base.Condition;

import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public class CompleteAchievements extends Condition
{
	public CompleteAchievements(Object value)
	{
		super(value);
		setName("Complete Achievements");
	}
	
	@Override
	public boolean meetConditionRequirements(L2PcInstance player)
	{
		if (getValue() == null)
			return false;
		
		int val = Integer.parseInt(getValue().toString());
		
		if (player.getCompletedAchievements().size() >= val)
			return true;
		
		return false;
	}
}