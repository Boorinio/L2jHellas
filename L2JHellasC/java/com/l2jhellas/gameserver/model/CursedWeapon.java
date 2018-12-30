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
import java.sql.SQLException;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.instancemanager.CursedWeaponsManager;
import com.l2jhellas.gameserver.model.actor.L2Attackable;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.entity.engines.CTF;
import com.l2jhellas.gameserver.model.entity.engines.TvT;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.Earthquake;
import com.l2jhellas.gameserver.network.serverpackets.ExRedSky;
import com.l2jhellas.gameserver.network.serverpackets.InventoryUpdate;
import com.l2jhellas.gameserver.network.serverpackets.ItemList;
import com.l2jhellas.gameserver.network.serverpackets.Ride;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;
import com.l2jhellas.gameserver.skills.SkillTable;
import com.l2jhellas.util.Rnd;
import com.l2jhellas.util.database.L2DatabaseFactory;

public class CursedWeapon
{
	private static final Logger _log = Logger.getLogger(CursedWeapon.class.getName());

	private final String _name;
	private final int _itemId;
	private final int _skillId;
	private final int _skillMaxLevel;
	private int _dropRate;
	private int _duration;
	private int _durationLost;
	private int _disapearChance;
	private int _stageKills;

	private boolean _isDropped = false;
	private boolean _isActivated = false;
	private ScheduledFuture<?> _removeTask;

	private int _nbKills = 0;
	private long _endTime = 0;

	private int _playerId = 0;
	private L2PcInstance _player = null;
	private L2ItemInstance _item = null;
	private int _playerKarma = 0;
	private int _playerPkKills = 0;

	public CursedWeapon(int itemId, int skillId, String name)
	{
		_name = name;
		_itemId = itemId;
		_skillId = skillId;
		_skillMaxLevel = SkillTable.getInstance().getMaxLevel(_skillId, 0);
	}

	public void endOfLife()
	{
		L2ItemInstance item = _player.getInventory().getItemByItemId( _itemId);

		if (_isActivated)
		{
			if (item != null && _player != null && _player.isOnline() == 1)
			{
				// Remove from player
				_log.info(CursedWeapon.class.getSimpleName() + ": " + _name + " being removed online.");

				_player.abortAttack();

				_player.setKarma(_playerKarma);
				_player.setPkKills(_playerPkKills);
				_player.setCursedWeaponEquipedId(0);
				removeSkill();


				// Remove
				_player.getInventory().unEquipItemInBodySlotAndRecord(item);
				_player.store();

				// Destroy
				L2ItemInstance removedItem = _player.getInventory().destroyItemByItemId("", _itemId, 1, _player, null);
				if (!Config.FORCE_INVENTORY_UPDATE)
				{
					InventoryUpdate iu = new InventoryUpdate();
					if (removedItem.getCount() == 0)
						iu.addRemovedItem(removedItem);
					else
						iu.addModifiedItem(removedItem);

					_player.sendPacket(iu);
				}
				else
					_player.sendPacket(new ItemList(_player, true));

				_player.broadcastUserInfo();
			}
			else
			{
				// Remove from Db
				_log.info(CursedWeapon.class.getSimpleName() + ": " + _name + " being removed offline.");

				try (Connection con = L2DatabaseFactory.getInstance().getConnection())
				{
					// Delete the item
					PreparedStatement statement = con.prepareStatement("DELETE FROM items WHERE owner_id=? AND item_id=?");
					statement.setInt(1, _playerId);
					statement.setInt(2, _itemId);
					if (statement.executeUpdate() != 1)
					{
						_log.warning(CursedWeapon.class.getName() + ": Error while deleting itemId " + _itemId + " from userId " + _playerId);
					}
					statement.close();
					/*
					 * Yesod: Skill is not stored into database any more.
					 * // Delete the skill
					 * statement = con.prepareStatement("DELETE FROM character_skills WHERE char_obj_id=? AND skill_id=?");
					 * statement.setInt(1, _playerId);
					 * statement.setInt(2, _skillId);
					 * if (statement.executeUpdate() != 1)
					 * {
					 * _log.warning(CursedWeapon.class.getName() + ": Error while deleting skillId "+ _skillId +" from userId "+_playerId);
					 * }
					 */
					// Restore the karma
					statement = con.prepareStatement("UPDATE characters SET karma=?, pkkills=? WHERE obj_Id=?");
					statement.setInt(1, _playerKarma);
					statement.setInt(2, _playerPkKills);
					statement.setInt(3, _playerId);
					if (statement.executeUpdate() != 1)
					{
						_log.warning(CursedWeapon.class.getName() + ": Error while updating karma & pkkills for userId " + _playerId);
					}

					statement.close();
				}
				catch (SQLException e)
				{
					_log.warning(CursedWeapon.class.getName() + ": Could not delete from db : ");
					if (Config.DEVELOPER)
						e.printStackTrace();
				}
			}
		}
		else
		{
			// either this cursed weapon is in the inventory of someone who has another cursed weapon equipped,
			// OR this cursed weapon is on the ground.
			if ((_player != null) && (_player.getInventory().getItemByItemId(_itemId) != null))
			{
				// Destroy
				L2ItemInstance removedItem = _player.getInventory().destroyItemByItemId("", _itemId, 1, _player, null);
				if (!Config.FORCE_INVENTORY_UPDATE)
				{
					InventoryUpdate iu = new InventoryUpdate();
					if (removedItem.getCount() == 0)
						iu.addRemovedItem(removedItem);
					else
						iu.addModifiedItem(removedItem);

					_player.sendPacket(iu);
				}
				else
					_player.sendPacket(new ItemList(_player, true));

				_player.broadcastUserInfo();
			}
			// is dropped on the ground
			else if (_item != null)
			{
				_item.decayMe();
				L2World.getInstance().removeObject(_item);
				_log.info(CursedWeapon.class.getSimpleName() + ": " + _name + " item has been removed from World.");
			}
		}

		// Delete infos from table if any
		CursedWeaponsManager.removeFromDb(_itemId);

		SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_HAS_DISAPPEARED);
		sm.addItemName(_itemId);
		CursedWeaponsManager.announce(sm);

		// Reset state
		cancelTask();
		_isActivated = false;
		_isDropped = false;
		_endTime = 0;
		_player = null;
		_playerId = 0;
		_playerKarma = 0;
		_playerPkKills = 0;
		_item = null;
		_nbKills = 0;
	}

	private void cancelTask()
	{
		if (_removeTask != null)
		{
			_removeTask.cancel(true);
			_removeTask = null;
		}
	}

	private class RemoveTask implements Runnable
	{
		protected RemoveTask()
		{
		}

		@Override
		public void run()
		{
			if (System.currentTimeMillis() >= getEndTime())
				endOfLife();
		}
	}

	private void dropIt(L2Attackable attackable, L2PcInstance player)
	{
		dropIt(attackable, player, null, true);
	}

	private void dropIt(L2Attackable attackable, L2PcInstance player, L2Character killer, boolean fromMonster)
	{
		_isActivated = false;

		if (fromMonster)
		{
			_item = attackable.dropItem(player, _itemId, 1);
			_item.setDropTime(0); // Prevent item from being removed by ItemsAutoDestroy

			// RedSky and Earthquake
			ExRedSky packet = new ExRedSky(10);
			Earthquake eq = new Earthquake(player.getX(), player.getY(), player.getZ(), 14, 3);
			for (L2PcInstance aPlayer : L2World.getInstance().getAllPlayers().values())
			{
				aPlayer.sendPacket(packet);
				aPlayer.sendPacket(eq);
			}
		}
		else
		{
			_player.dropItem("DieDrop", _item, killer, true);
			_player.setKarma(_playerKarma);
			_player.setPkKills(_playerPkKills);
			_player.setCursedWeaponEquipedId(0);
			removeSkill();
			_player.abortAttack();
			// L2ItemInstance item = _player.getInventory().getItemByItemId(_itemId);
			// _player.getInventory().dropItem("DieDrop", item, _player, null);
			// _player.getInventory().getItemByItemId(_itemId).dropMe(_player, _player.getX(), _player.getY(), _player.getZ());
		}

		_isDropped = true;
		SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S2_WAS_DROPPED_IN_THE_S1_REGION);
		if (player != null)
			sm.addZoneName(player.getX(), player.getY(), player.getZ()); // Region Name
		else if (_player != null)
			sm.addZoneName(_player.getX(), _player.getY(), _player.getZ()); // Region Name
		else
			sm.addZoneName(killer.getX(), killer.getY(), killer.getZ()); // Region Name
		sm.addItemName(_itemId);
		CursedWeaponsManager.announce(sm); // in the Hot Spring region
	}

	/**
	 * Yesod:<br>
	 * Rebind the passive skill belonging to the CursedWeapon. Invoke this
	 * method if the weapon owner switches to a subclass.
	 */
	public void giveSkill()
	{
		int level = 1 + (_nbKills / _stageKills);
		if (level > _skillMaxLevel)
			level = _skillMaxLevel;

		L2Skill skill = SkillTable.getInstance().getInfo(_skillId, level);
		// Yesod:
		// To properly support subclasses this skill can not be stored.
		_player.addSkill(skill, false);

		// Void Burst, Void Flow
		skill = SkillTable.getInstance().getInfo(3630, 1);
		_player.addSkill(skill, false);
		skill = SkillTable.getInstance().getInfo(3631, 1);
		_player.addSkill(skill, false);

		if (Config.DEBUG)
			_log.config(CursedWeapon.class.getName() + ": Player " + _player.getName() + " has been awarded with skill " + skill);
		_player.sendSkillList();
	}

	public void removeSkill()
	{
		_player.removeSkill(SkillTable.getInstance().getInfo(_skillId, _player.getSkillLevel(_skillId)), false);
		_player.removeSkill(SkillTable.getInstance().getInfo(3630, 1), false);
		_player.removeSkill(SkillTable.getInstance().getInfo(3631, 1), false);
		_player.sendSkillList();
	}

	public void reActivate()
	{
		_isActivated = true;
		if (_endTime - System.currentTimeMillis() <= 0)
			endOfLife();
		else
			_removeTask = ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new RemoveTask(), _durationLost * 12000L, _durationLost * 12000L);

	}

	public boolean checkDrop(L2Attackable attackable, L2PcInstance player)
	{
		if (Rnd.get(100000) < _dropRate)
		{
			// Drop the item
			dropIt(attackable, player);

			// Start the Life Task
			_endTime = System.currentTimeMillis() + _duration * 60000L;
			_removeTask = ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new RemoveTask(), _durationLost * 12000L, _durationLost * 12000L);

			return true;
		}
		return false;
	}

	public void activate(L2PcInstance player, L2ItemInstance item)
	{
		// if the player is mounted, attempt to unmount first. Only allow picking up
		// the zariche if unmounting is successful.
		if (player.isMounted())
		{
			if (_player.setMountType(0))
			{
				Ride dismount = new Ride(_player.getObjectId(), Ride.ACTION_DISMOUNT, 0);
				_player.broadcastPacket(dismount);
				_player.setMountObjectID(0);
			}
			else
			{
				// TODO: correct this custom message.
				player.sendMessage("You may not pick up this item while riding in this territory");
				return;
			}
		}

		if ((player._inEventTvT && !Config.TVT_JOIN_CURSED) || (player._inEventCTF && !Config.CTF_JOIN_CURSED))
		{
			if (player._inEventTvT)
				TvT.removePlayer(player);
			if (player._inEventCTF)
				CTF.removePlayer(player);
		}

		_isActivated = true;

		// Player holding it data
		_player = player;
		_playerId = _player.getObjectId();
		_playerKarma = _player.getKarma();
		_playerPkKills = _player.getPkKills();
		saveData();

		// Change player stats
		_player.setCursedWeaponEquipedId(_itemId);
		_player.setKarma(9999999);
		_player.setPkKills(0);
		if (_player.isInParty())
			_player.getParty().removePartyMember(_player);

		// Add skill
		giveSkill();

		// Equip with the weapon
		_item = item;
		// L2ItemInstance[] items =
		_player.getInventory().equipItemAndRecord(_item);
		SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_EQUIPPED);
		sm.addItemName(_item.getItemId());
		_player.sendPacket(sm);

		// Fully heal player
		_player.setCurrentHpMp(_player.getMaxHp(), _player.getMaxMp());
		_player.setCurrentCp(_player.getMaxCp());

		// Refresh inventory
		if (!Config.FORCE_INVENTORY_UPDATE)
		{
			InventoryUpdate iu = new InventoryUpdate();
			iu.addItem(_item);
			// iu.addItems(Arrays.asList(items));
			_player.sendPacket(iu);
		}
		else
			_player.sendPacket(new ItemList(_player, false));

		// Refresh player stats
		_player.broadcastUserInfo();

		_player.broadcastSocialActionInRadius(17);

		sm = SystemMessage.getSystemMessage(SystemMessageId.THE_OWNER_OF_S2_HAS_APPEARED_IN_THE_S1_REGION);
		sm.addZoneName(_player.getX(), _player.getY(), _player.getZ()); // Region Name
		sm.addItemName(_item.getItemId());
		CursedWeaponsManager.announce(sm);
	}

	public void saveData()
	{
		if (Config.DEBUG)
			_log.config(CursedWeapon.class.getName() + ": Saving data to disk.");

		PreparedStatement statement = null;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			// Delete previous datas
			statement = con.prepareStatement("DELETE FROM cursed_weapons WHERE itemId = ?");
			statement.setInt(1, _itemId);
			statement.executeUpdate();

			if (_isActivated)
			{
				statement = con.prepareStatement("INSERT INTO cursed_weapons (itemId, playerId, playerKarma, playerPkKills, nbKills, endTime) VALUES (?, ?, ?, ?, ?, ?)");
				statement.setInt(1, _itemId);
				statement.setInt(2, _playerId);
				statement.setInt(3, _playerKarma);
				statement.setInt(4, _playerPkKills);
				statement.setInt(5, _nbKills);
				statement.setLong(6, _endTime);
				statement.executeUpdate();
			}

			statement.close();
		}
		catch (SQLException e)
		{
			_log.warning(CursedWeapon.class.getName() + " Failed to save data: ");
			if (Config.DEVELOPER)
				e.printStackTrace();
		}
	}

	public void dropIt(L2Character killer)
	{
		if (Rnd.get(100) <= _disapearChance)
		{
			// Remove it
			endOfLife();
		}
		else
		{
			// Unequip & Drop
			dropIt(null, null, killer, false);
			// Reset player stats
			_player.setKarma(_playerKarma);
			_player.setPkKills(_playerPkKills);
			_player.setCursedWeaponEquipedId(0);
			removeSkill();

			_player.abortAttack();

			// Unequip weapon
			// _player.getInventory().unEquipItemInSlot(Inventory.PAPERDOLL_LRHAND);

			_player.broadcastUserInfo();
		}
	}

	public void increaseKills()
	{
		_nbKills++;

		_player.setPkKills(_nbKills);
		_player.broadcastUserInfo();

		if (_nbKills % _stageKills == 0 && _nbKills <= _stageKills * (_skillMaxLevel - 1))
		{
			giveSkill();
		}

		// Reduce time-to-live
		_endTime -= _durationLost * 60000L;
		saveData();
	}

	public void setDisapearChance(int disapearChance)
	{
		_disapearChance = disapearChance;
	}

	public void setDropRate(int dropRate)
	{
		_dropRate = dropRate;
	}

	public void setDuration(int duration)
	{
		_duration = duration;
	}

	public void setDurationLost(int durationLost)
	{
		_durationLost = durationLost;
	}

	public void setStageKills(int stageKills)
	{
		_stageKills = stageKills;
	}

	public void setNbKills(int nbKills)
	{
		_nbKills = nbKills;
	}

	public void setPlayerId(int playerId)
	{
		_playerId = playerId;
	}

	public void setPlayerKarma(int playerKarma)
	{
		_playerKarma = playerKarma;
	}

	public void setPlayerPkKills(int playerPkKills)
	{
		_playerPkKills = playerPkKills;
	}

	public void setActivated(boolean isActivated)
	{
		_isActivated = isActivated;
	}

	public void setDropped(boolean isDropped)
	{
		_isDropped = isDropped;
	}

	public void setEndTime(long endTime)
	{
		_endTime = endTime;
	}

	public void setPlayer(L2PcInstance player)
	{
		_player = player;
	}

	public void setItem(L2ItemInstance item)
	{
		_item = item;
	}

	public boolean isActivated()
	{
		return _isActivated;
	}

	public boolean isDropped()
	{
		return _isDropped;
	}

	public long getEndTime()
	{
		return _endTime;
	}

	public String getName()
	{
		return _name;
	}

	public int getItemId()
	{
		return _itemId;
	}

	public int getSkillId()
	{
		return _skillId;
	}

	public int getPlayerId()
	{
		return _playerId;
	}

	public L2PcInstance getPlayer()
	{
		return _player;
	}

	public int getPlayerKarma()
	{
		return _playerKarma;
	}

	public int getPlayerPkKills()
	{
		return _playerPkKills;
	}

	public int getNbKills()
	{
		return _nbKills;
	}

	public int getStageKills()
	{
		return _stageKills;
	}

	public boolean isActive()
	{
		return _isActivated || _isDropped;
	}

	public int getLevel()
	{
		if (_nbKills > _stageKills * _skillMaxLevel)
		{
			return _skillMaxLevel;
		}
		else
		{
			return (_nbKills / _stageKills);
		}
	}

	public long getTimeLeft()
	{
		return _endTime - System.currentTimeMillis();
	}

	public void goTo(L2PcInstance player)
	{
		if (player == null)
			return;

		if (_isActivated)
		{
			// Go to player holding the weapon
			player.teleToLocation(_player.getX(), _player.getY(), _player.getZ() + 20, true);
		}
		else if (_isDropped)
		{
			// Go to item on the ground
			player.teleToLocation(_item.getX(), _item.getY(), _item.getZ() + 20, true);
		}
		else
		{
			player.sendMessage(_name + " isn't in the World.");
		}
	}

	public Location getWorldPosition()
	{
		if (_isActivated && _player != null)
			return _player.getPosition().getWorldPosition();

		if (_isDropped && _item != null)
			return _item.getPosition().getWorldPosition();

		return null;
	}
}