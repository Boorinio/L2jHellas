package Extensions.AchievmentsEngine.base;

import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class Achievement
{
	private static Logger _log = Logger.getLogger(Achievement.class.getName());
	private final int _id;
	private final String _name;
	private final String _reward;
	private String _description = "No Description!";
	private final boolean _repeatable;
	
	private final HashMap<Integer, Long> _rewardList;
	private final List<Condition> _conditions;
	
	public Achievement(int id, String name, String description, String reward, boolean repeatable, ArrayList<Condition> conditions)
	{
		_rewardList = new HashMap<>();
		_id = id;
		_name = name;
		_description = description;
		_reward = reward;
		_conditions = conditions;
		_repeatable = repeatable;
		
		createRewardList();
	}
	
	private void createRewardList()
	{
		for (String s : _reward.split(";"))
		{
			if (s == null || s.isEmpty())
				continue;
			
			String[] split = s.split(",");
			Integer item = 0;
			Long count = new Long(0);
			try
			{
				item = Integer.valueOf(split[0]);
				count = Long.valueOf(split[1]);
			}
			catch (NumberFormatException nfe)
			{
				_log.warning(Achievement.class.getName() + ": Error wrong reward " + nfe);
			}
			_rewardList.put(item, count);
		}
	}
	
	public boolean meetAchievementRequirements(L2PcInstance player)
	{
		for (Condition c : getConditions())
		{
			if (!c.meetConditionRequirements(player))
			{
				return false;
			}
		}
		return true;
	}
	
	public int getID()
	{
		return _id;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public String getDescription()
	{
		return _description;
	}
	
	public String getReward()
	{
		return _reward;
	}
	
	public boolean isRepeatable()
	{
		return _repeatable;
	}
	
	public HashMap<Integer, Long> getRewardList()
	{
		return _rewardList;
	}
	
	public List<Condition> getConditions()
	{
		return _conditions;
	}
}