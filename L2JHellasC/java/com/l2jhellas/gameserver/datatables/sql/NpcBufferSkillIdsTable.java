package com.l2jhellas.gameserver.datatables.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.skills.NpcBufferSkills;
import com.l2jhellas.util.database.L2DatabaseFactory;

public class NpcBufferSkillIdsTable
{
	protected static final Logger _log = Logger.getLogger(NpcBufferSkillIdsTable.class.getName());

	private static NpcBufferSkillIdsTable _instance = null;

	private final Map<Integer, NpcBufferSkills> _buffers = new HashMap<>();

	@SuppressWarnings("null")
	private NpcBufferSkillIdsTable()
	{
		int skillCount = 0;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT npc_id,skill_id,skill_level,skill_fee_id,skill_fee_amount FROM npc_buffer ORDER BY npc_id ASC");
			ResultSet rset = statement.executeQuery();

			int lastNpcId = 0;
			NpcBufferSkills skills = null;

			while (rset.next())
			{
				int npcId = rset.getInt("npc_id");
				int skillId = rset.getInt("skill_id");
				int skillLevel = rset.getInt("skill_level");
				int skillFeeId = rset.getInt("skill_fee_id");
				int skillFeeAmount = rset.getInt("skill_fee_amount");

				if (npcId != lastNpcId)
				{
					if (lastNpcId != 0)
						_buffers.put(lastNpcId, skills);

					skills = new NpcBufferSkills(npcId);
					skills.addSkill(skillId, skillLevel, skillFeeId, skillFeeAmount);
				}
				else
					skills.addSkill(skillId, skillLevel, skillFeeId, skillFeeAmount);

				lastNpcId = npcId;
				skillCount++;
			}

			_buffers.put(lastNpcId, skills);
			rset.close();
			statement.close();
		}
		catch (Exception e)
		{
			_log.warning(NpcBufferSkillIdsTable.class.getName() + ": Error reading npc_buffer_skill_ids table: ");
			if (Config.DEVELOPER)
				e.printStackTrace();
		}

		_log.info(NpcBufferSkillIdsTable.class.getSimpleName() + ": Loaded " + _buffers.size() + " buffers and " + skillCount + " skills.");
	}

	public static NpcBufferSkillIdsTable getInstance()
	{
		if (_instance == null)
			_instance = new NpcBufferSkillIdsTable();

		return _instance;
	}

	/** Reloads npc buffer **/
	public static void reload()
	{
		_instance = new NpcBufferSkillIdsTable();
	}

	public int[] getSkillInfo(int npcId, int skillId)
	{
		NpcBufferSkills skills = _buffers.get(npcId);

		if (skills == null)
			return null;

		return skills.getSkillInfo(skillId);
	}
}