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
package com.l2jhellas.gameserver.datatables.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.instancemanager.DayNightSpawnManager;
import com.l2jhellas.gameserver.model.L2Spawn;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;
import com.l2jhellas.util.Util;
import com.l2jhellas.util.database.L2DatabaseFactory;

public class SpawnTable
{
	private static Logger _log = Logger.getLogger(SpawnTable.class.getName());

	private static final SpawnTable _instance = new SpawnTable();

	private final Map<Integer, L2Spawn> _spawntable = new ConcurrentHashMap<Integer, L2Spawn>();

	private int _highestId;

	public static SpawnTable getInstance()
	{
		return _instance;
	}

	private SpawnTable()
	{
		if (!Config.ALT_DEV_NO_SPAWNS)
			fillSpawnTable();
	}

	public Map<Integer, L2Spawn> getSpawnTable()
	{
		return _spawntable;
	}

	private void fillSpawnTable()
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT * FROM spawnlist");
			ResultSet rset = statement.executeQuery();

			L2Spawn spawnDat;
			L2NpcTemplate template1;

			while (rset.next())
			{
				template1 = NpcData.getInstance().getTemplate(rset.getInt("npc_templateid"));
				if (template1 != null)
				{
					if (template1.type.equalsIgnoreCase("L2SiegeGuard"))
					{
						// Don't spawn
					}
					else if (template1.type.equalsIgnoreCase("L2RaidBoss"))
					{
						// Don't spawn raidboss
					}
					else if (!Config.ENABLE_HITMAN_EVENT && template1.type.equals("L2Hitman"))
					{
						// You got the idea...
					}
					else if (!Config.ALLOW_CLASS_MASTER && template1.type.equals("L2ClassMaster"))
					{
						// Dont' spawn class masters
					}
					else
					{
						spawnDat = new L2Spawn(template1);
						spawnDat.setId(rset.getInt("id"));
						spawnDat.setAmount(rset.getInt("count"));
						spawnDat.setLocx(rset.getInt("locx"));
						spawnDat.setLocy(rset.getInt("locy"));
						spawnDat.setLocz(rset.getInt("locz"));
						spawnDat.setHeading(rset.getInt("heading"));
						spawnDat.setRespawnDelay(rset.getInt("respawn_delay"));
						int loc_id = rset.getInt("loc_id");
						spawnDat.setLocation(loc_id);

						switch (rset.getInt("periodOfDay"))
						{
							case 0: // default				
								spawnDat.init();
							break;
							case 1: // Day
								DayNightSpawnManager.getInstance().addDayCreature(spawnDat);
							break;
							case 2: // Night
								DayNightSpawnManager.getInstance().addNightCreature(spawnDat);
							break;
						}

						_spawntable.put(spawnDat.getId(), spawnDat);
						if (spawnDat.getId() > _highestId)
							_highestId = spawnDat.getId();
					}
				}
				else
					_log.warning("SpawnTable: data missing in npc table for id: " + rset.getInt("npc_templateid") + ".");
			}
			rset.close();
			statement.close();
		}
		catch (Exception e)
		{
			_log.warning("SpawnTable: Spawn could not be initialized: " + e);
		}

		Util.printSection("Spawnlist");
		_log.info(SpawnTable.class.getSimpleName() + ": Loaded " + _spawntable.size() + " Npc Spawn Locations.");
	}

	public L2Spawn getTemplate(int id)
	{
		return _spawntable.get(id);
	}

	public void addNewSpawn(L2Spawn spawn, boolean storeInDb)
	{
		_highestId++;
		spawn.setId(_highestId);
		_spawntable.put(_highestId, spawn);

		if (storeInDb)
		{
			try (Connection con = L2DatabaseFactory.getInstance().getConnection())
			{
				PreparedStatement statement = con.prepareStatement("INSERT INTO spawnlist (id,count,npc_templateid,locx,locy,locz,heading,respawn_delay,loc_id) VALUES (?,?,?,?,?,?,?,?,?)");
				statement.setInt(1, spawn.getId());
				statement.setInt(2, spawn.getAmount());
				statement.setInt(3, spawn.getNpcid());
				statement.setInt(4, spawn.getLocx());
				statement.setInt(5, spawn.getLocy());
				statement.setInt(6, spawn.getLocz());
				statement.setInt(7, spawn.getHeading());
				statement.setInt(8, spawn.getRespawnDelay() / 1000);
				statement.setInt(9, spawn.getLocation());
				statement.execute();
				statement.close();
			}
			catch (Exception e)
			{
				// problem with storing spawn
				_log.warning(SpawnTable.class.getName() + ": Could not store spawn in the DB:");
				if (Config.DEVELOPER)
					e.printStackTrace();
			}
		}
	}

	public void deleteSpawn(L2Spawn spawn, boolean updateDb)
	{
		if (_spawntable.remove(spawn.getId()) == null)
			return;

		if (updateDb)
		{
			try (Connection con = L2DatabaseFactory.getInstance().getConnection())
			{
				PreparedStatement statement = con.prepareStatement("DELETE FROM spawnlist WHERE id=?");
				statement.setInt(1, spawn.getId());
				statement.execute();
				statement.close();
			}
			catch (Exception e)
			{
				// problem with deleting spawn
				_log.warning(SpawnTable.class.getName() + ": Spawn " + spawn.getId() + " could not be removed from DB: ");
				if (Config.DEVELOPER)
					e.printStackTrace();
			}
		}
	}

	// just wrapper
	public void reloadAll()
	{
		_spawntable.clear();
		fillSpawnTable();
	}

	/**
	 * Get all the spawn of a NPC<BR>
	 * <BR>
	 * 
	 * @param npcId
	 *        : ID of the NPC to find.
	 * @return
	 */
	public void findNPCInstances(L2PcInstance activeChar, int npcId, int teleportIndex)
	{
		int index = 0;
		for (L2Spawn spawn : _spawntable.values())
		{
			if (npcId == spawn.getNpcid())
			{
				index++;

				if (teleportIndex > -1)
				{
					if (teleportIndex == index)
						activeChar.teleToLocation(spawn.getLocx(), spawn.getLocy(), spawn.getLocz(), true);
				}
				else
				{
					activeChar.sendMessage(index + " - " + spawn.getTemplate().name + " (" + spawn.getId() + "): " + spawn.getLocx() + " " + spawn.getLocy() + " " + spawn.getLocz());
				}
			}
		}

		if (index == 0)
			activeChar.sendMessage("No current spawns found.");
	}
}