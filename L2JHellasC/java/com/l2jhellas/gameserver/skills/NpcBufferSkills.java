package com.l2jhellas.gameserver.skills;

import java.util.HashMap;
import java.util.Map;

public class NpcBufferSkills
{
	private int _npcId = 0;
	private final Map<Integer, Integer> _skillLevels = new HashMap<>();
	private final Map<Integer, Integer> _skillFeeIds = new HashMap<>();
	private final Map<Integer, Integer> _skillFeeAmounts = new HashMap<>();
	
	public NpcBufferSkills(int npcId)
	{
		_npcId = npcId;
	}
	
	public void addSkill(int skillId, int skillLevel, int skillFeeId, int skillFeeAmount)
	{
		_skillLevels.put(skillId, skillLevel);
		_skillFeeIds.put(skillId, skillFeeId);
		_skillFeeAmounts.put(skillId, skillFeeAmount);
	}
	
	public int[] getSkillInfo(int skillId)
	{
		Integer skillLevel = _skillLevels.get(skillId);
		Integer skillFeeId = _skillFeeIds.get(skillId);
		Integer skillFeeAmount = _skillFeeAmounts.get(skillId);
		
		if ((skillLevel == null) || (skillFeeId == null) || (skillFeeAmount == null))
			return null;
		
		return new int[]
		{
			skillLevel,
			skillFeeId,
			skillFeeAmount
		};
	}
	
	public int getNpcId()
	{
		return _npcId;
	}
}