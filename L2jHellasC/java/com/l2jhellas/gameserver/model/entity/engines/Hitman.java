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
package com.l2jhellas.gameserver.model.entity.engines;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

import javolution.util.FastMap;

import com.l2jhellas.Config;
import com.l2jhellas.L2DatabaseFactory;
import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.datatables.CharNameTable;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.entity.PlayerToAssasinate;

public class Hitman
{
	private static Hitman _instance;
	private FastMap<Integer, PlayerToAssasinate> _targets;
	private final Logger _log = Logger.getLogger(Hitman.class.getName());

	// Data Strings
	private static String SQL_SELECT = "SELECT targetId, clientId, target_name, bounty, pending_delete FROM hitman_list";
	private static String SQL_DELETE = "DELETE FROM hitman_list WHERE targetId=?";
	private static String SQL_SAVEING = "REPLACE INTO `hitman_list` VALUES (?, ?, ?, ?, ?)";
	private static String[] SQL_OFFLINE =
	{
	"SELECT * FROM characters WHERE char_name=?", "SELECT * from characters WHERE obj_Id=?"
	};

	// Clean every 15 mins ^^
	private final int MIN_MAX_CLEAN_RATE = 15 * 60000;

	// Fancy lookin
	public static boolean start()
	{
		if (Config.ENABLE_HITMAN_EVENT)
			getInstance();

		return _instance != null;
	}

	public static Hitman getInstance()
	{
		if (_instance == null)
			_instance = new Hitman();

		return _instance;
	}

	public Hitman()
	{
		_targets = load();
		ThreadPoolManager.getInstance().scheduleAiAtFixedRate(new AISystem(), MIN_MAX_CLEAN_RATE, MIN_MAX_CLEAN_RATE);
	}

	private FastMap<Integer, PlayerToAssasinate> load()
	{
		FastMap<Integer, PlayerToAssasinate> map = new FastMap<Integer, PlayerToAssasinate>();

		try
		{
			Connection con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement st = con.prepareStatement(SQL_SELECT);
			ResultSet rs = st.executeQuery();

			while (rs.next())
			{
				int targetId = rs.getInt("targetId");
				int clientId = rs.getInt("clientId");
				String target_name = rs.getString("target_name");
				int bounty = rs.getInt("bounty");
				boolean pending = rs.getInt("pending_delete") == 1;

				if (pending)
					removeTarget(targetId, false);
				else
					map.put(targetId, new PlayerToAssasinate(targetId, clientId, bounty, target_name));
			}
			_log.info("Hitman: Loaded " + map.size() + " Assassination Target(s)");
			rs.close();
			st.close();
			con.close();
		}
		catch (Exception e)
		{
			_log.warning("Hitman: " + e.getCause());
			return new FastMap<Integer, PlayerToAssasinate>();
		}

		return map;
	}

	public void onDeath(L2PcInstance assassin, L2PcInstance target)
	{
		if (_targets.containsKey(target.getObjectId()))
		{
			PlayerToAssasinate pta = _targets.get(target.getObjectId());
			String name = getOfflineData(null, pta.getClientId())[1];
			L2PcInstance client = L2World.getInstance().getPlayer(name);

			target.sendMessage("You have been assassinated. Your bounty is 0.");

			if (client != null)
			{
				client.sendMessage("Your assassination request to kill " + target.getName() + " has been fulfilled.");
				client.setHitmanTarget(0);
			}

			assassin.sendMessage("You assassinated " + target.getName() + ", his bounty will be converted in Adena!");
			assassin.addAdena("Hitman", pta.getBounty(), target, true);
			removeTarget(pta.getObjectId(), true);
		}
	}

	public void onEnterWorld(L2PcInstance activeChar)
	{
		if (_targets.containsKey(activeChar.getObjectId()))
		{
			PlayerToAssasinate pta = _targets.get(activeChar.getObjectId());
			activeChar.sendMessage("There is a hit on you. Worth " + pta.getBounty() + " Adena(s).");
		}

		if (activeChar.getHitmanTarget() > 0)
		{
			if (!_targets.containsKey(activeChar.getHitmanTarget()))
			{
				activeChar.sendMessage("Your target has been eliminated. Have a nice day.");
				activeChar.setHitmanTarget(0);
			}
			else
				activeChar.sendMessage("Your target is still at large.");
		}
	}

	public void save()
	{
		try
		{
			for (PlayerToAssasinate pta : _targets.values())
			{
				Connection con = L2DatabaseFactory.getInstance().getConnection();
				PreparedStatement st = con.prepareStatement(SQL_SAVEING);
				st.setInt(1, pta.getObjectId());
				st.setInt(2, pta.getClientId());
				st.setString(3, pta.getName());
				st.setInt(4, pta.getBounty());
				st.setInt(5, pta.isPendingDelete() ? 1 : 0);
				st.executeQuery();
				st.close();
				con.close();
			}
		}
		catch (Exception e)
		{
			_log.warning("Hitman: " + e);
		}

	}

	public void putHitOn(L2PcInstance client, String playerName, int bounty)
	{
		L2PcInstance player = L2World.getInstance().getPlayer(playerName);

		if (client.getHitmanTarget() > 0)
		{
			client.sendMessage("You are already a client here, you can place a request only for a single player.");
			return;
		}
		else if (client.getAdena() < bounty)
		{
			client.sendMessage("Not enough adena.");
			return;
		}
		else if (player == null && CharNameTable.getInstance().doesCharNameExist(playerName))
		{
			Integer targetId = Integer.parseInt(getOfflineData(playerName, 0)[0]);

			if (_targets.containsKey(targetId))
			{
				client.sendMessage("There is already a hit on that player.");
				return;
			}
			_targets.put(targetId, new PlayerToAssasinate(targetId, client.getObjectId(), bounty, playerName));
			client.reduceAdena("Hitman", bounty, client, true);
			client.setHitmanTarget(targetId);
		}
		else if (player != null && CharNameTable.getInstance().doesCharNameExist(playerName))
		{
			if (_targets.containsKey(player.getObjectId()))
			{
				client.sendMessage("There is already a hit on that player.");
				return;
			}
			player.sendMessage("There is a hit on you. Worth " + bounty + " Adena(s).");
			_targets.put(player.getObjectId(), new PlayerToAssasinate(player, client.getObjectId(), bounty));
			client.reduceAdena("Hitman", bounty, client, true);
			client.setHitmanTarget(player.getObjectId());
		}
		else
			client.sendMessage("Player name invalid. The user does not exist.");
	}

	public class AISystem implements Runnable
	{
		@Override
		public void run()
		{
			if (Config.DEBUG)
				_log.info("Cleaning sequance initiated.");

			for (PlayerToAssasinate target : _targets.values())
			{
				if (target.isPendingDelete())
					removeTarget(target.getObjectId(), true);
			}
			save();
		}
	}

	public void removeTarget(int obId, boolean live)
	{
		try
		{
			Connection con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement st = con.prepareStatement(SQL_DELETE);
			st.setInt(1, obId);
			st.execute();
			st.close();
			con.close();

			if (live)
				_targets.remove(obId);
		}
		catch (Exception e)
		{
			_log.warning("Hitman: " + e);
		}
	}

	public void cancelAssasination(String name, L2PcInstance client)
	{
		L2PcInstance target = L2World.getInstance().getPlayer(name);

		if (client.getHitmanTarget() <= 0)
		{
			client.sendMessage("You don't own a hit.");
			return;
		}
		else if (target == null && CharNameTable.getInstance().doesCharNameExist(name))
		{
			PlayerToAssasinate pta = _targets.get(client.getHitmanTarget());

			if (!_targets.containsKey(pta.getObjectId()))
				client.sendMessage("There is no hit on that player.");
			else if (pta.getClientId() == client.getObjectId())
			{
				removeTarget(pta.getObjectId(), true);
				client.sendMessage("The hit has been canceled.");
				client.setHitmanTarget(0);
			}
			else
				client.sendMessage("You are not the actual owner of that target!.");
		}
		else if (target != null && CharNameTable.getInstance().doesCharNameExist(name))
		{
			PlayerToAssasinate pta = _targets.get(target.getObjectId());

			if (!_targets.containsKey(pta.getObjectId()))
				client.sendMessage("There is no hit on that player.");
			else if (pta.getClientId() == client.getObjectId())
			{
				removeTarget(pta.getObjectId(), true);
				client.sendMessage("The hit has been canceled.");
				target.sendMessage("The hit on you has been canceled.");
				client.setHitmanTarget(0);
			}
			else
				client.sendMessage("You are not the actual owner of that target!.");
		}
		else
			client.sendMessage("Player name invalid. The user does not exist.");
	}

	/**
	 * Its useing a array in case in a future update more values will be added
	 *
	 * @param name
	 */
	public String[] getOfflineData(String name, int objId)
	{
		String[] set = new String[2];
		try
		{
			Connection con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement st = con.prepareStatement(objId > 0 ? SQL_OFFLINE[1] : SQL_OFFLINE[0]);

			if (objId > 0)
				st.setInt(1, objId);
			else
				st.setString(1, name);

			ResultSet rs = st.executeQuery();

			while (rs.next())
			{
				set[0] = String.valueOf(rs.getInt("charId"));
				set[1] = rs.getString("char_name");
			}

			rs.close();
			st.close();
			con.close();
		}
		catch (Exception e)
		{
			_log.warning("Hitman: " + e);
		}

		return set;
	}

	public boolean exists(int objId)
	{
		return _targets.containsKey(objId);
	}

	public PlayerToAssasinate getTarget(int objId)
	{
		return _targets.get(objId);
	}

	/**
	 * @return the _targets
	 */
	public FastMap<Integer, PlayerToAssasinate> getTargets()
	{
		return _targets;
	}

	/**
	 * @param targets
	 *        the _targets to set
	 */
	public void set_targets(FastMap<Integer, PlayerToAssasinate> targets)
	{
		_targets = targets;
	}
}