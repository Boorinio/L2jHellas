/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jhellas.gameserver.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastList;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.datatables.xml.AugmentationData;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.skills.SkillTable;
import com.l2jhellas.gameserver.skills.Stats;
import com.l2jhellas.gameserver.skills.funcs.FuncAdd;
import com.l2jhellas.gameserver.skills.funcs.LambdaConst;
import com.l2jhellas.util.database.L2DatabaseFactory;

/**
 * Used to store an augmentation and its boni
 * 
 * @author durgus
 */
public final class L2Augmentation
{
	private static final Logger _log = Logger.getLogger(L2Augmentation.class.getName());

	private final L2ItemInstance _item;
	private int _effectsId = 0;
	private augmentationStatBoni _boni = null;
	private L2Skill _skill = null;

	public L2Augmentation(L2ItemInstance item, int effects, L2Skill skill, boolean save)
	{
		_item = item;
		_effectsId = effects;
		_boni = new augmentationStatBoni(_effectsId);
		_skill = skill;

		// write to DB if save is true
		if (save)
			saveAugmentationData();
	}

	public L2Augmentation(L2ItemInstance item, int effects, int skill, int skillLevel, boolean save)
	{
		this(item, effects, SkillTable.getInstance().getInfo(skill, skillLevel), save);
	}

	public class augmentationStatBoni
	{
		private final Stats _stats[];
		private final float _values[];
		private boolean _active;

		public augmentationStatBoni(int augmentationId)
		{
			_active = false;
			FastList<AugmentationData.AugStat> as = AugmentationData.getInstance().getAugStatsById(augmentationId);

			_stats = new Stats[as.size()];
			_values = new float[as.size()];

			int i = 0;
			for (AugmentationData.AugStat aStat : as)
			{
				_stats[i] = aStat.getStat();
				_values[i] = aStat.getValue();
				i++;
			}
		}

		public void applyBoni(L2PcInstance player)
		{
			// make sure the boni are not applyed twice..
			if (_active)
				return;

			for (int i = 0; i < _stats.length; i++)
				((L2Character) player).addStatFunc(new FuncAdd(_stats[i], 0x40, this, new LambdaConst(_values[i])));

			_active = true;
		}

		public void removeBoni(L2PcInstance player)
		{
			// make sure the boni is not removed twice
			if (!_active)
				return;

			((L2Character) player).removeStatsOwner(this);

			_active = false;
		}
	}

	private void saveAugmentationData()
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("INSERT INTO augmentations (item_id,attributes,skill,level) VALUES (?,?,?,?)");
			statement.setInt(1, _item.getObjectId());
			statement.setInt(2, _effectsId);
			if (_skill != null)
			{
				statement.setInt(3, _skill.getId());
				statement.setInt(4, _skill.getLevel());
			}
			else
			{
				statement.setInt(3, 0);
				statement.setInt(4, 0);
			}

			statement.executeUpdate();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.SEVERE, getClass().getName() + ": Could not save augmentation for item: " + _item.getObjectId() + " from DB:" + e);
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	public void deleteAugmentationData()
	{
		if (!_item.isAugmented())
			return;

		// delete the augmentation from the database
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("DELETE FROM augmentations WHERE item_id=?");
			statement.setInt(1, _item.getObjectId());
			statement.executeUpdate();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.SEVERE, getClass().getName() + ": Could not delete augmentation for item: " + _item.getObjectId() + " from DB:" + e);
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get the augmentation "id" used in serverpackets.
	 * 
	 * @return augmentationId
	 */
	public int getAugmentationId()
	{
		return _effectsId;
	}

	public L2Skill getSkill()
	{
		return _skill;
	}

	/**
	 * Applys the boni to the player.
	 * 
	 * @param player
	 */
	public void applyBoni(L2PcInstance player)
	{
		_boni.applyBoni(player);

		// add the skill if any
		if (_skill != null)
		{
			player.addSkill(_skill);
			player.sendSkillList();

			// Iterate through all effects currently on the character.
			for (L2Effect currenteffect : player.getAllEffects())
			{
				L2Skill effectSkill = currenteffect.getSkill();

				if (effectSkill.getId() == _skill.getId())
				{
					player.sendMessage("You feel the power of " + effectSkill.getName() + " leaving yourself.");
					currenteffect.exit();
				}
			}
		}
	}

	/**
	 * Removes the augmentation boni from the player.
	 * 
	 * @param player
	 */
	public void removeBoni(L2PcInstance player)
	{
		_boni.removeBoni(player);

		// remove the skill if any
		if (_skill != null)
		{
			player.removeSkill(_skill);
			player.sendSkillList();
		}
	}
}