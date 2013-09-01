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
package com.l2jhellas.gameserver.model.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastList;
import javolution.util.FastMap;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.Announcements;
import com.l2jhellas.gameserver.CastleUpdater;
import com.l2jhellas.gameserver.SevenSigns;
import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.datatables.csv.DoorTable;
import com.l2jhellas.gameserver.datatables.sql.ClanTable;
import com.l2jhellas.gameserver.instancemanager.CastleManager;
import com.l2jhellas.gameserver.instancemanager.CastleManorManager;
import com.l2jhellas.gameserver.instancemanager.CastleManorManager.CropProcure;
import com.l2jhellas.gameserver.instancemanager.CastleManorManager.SeedProduction;
import com.l2jhellas.gameserver.model.L2Clan;
import com.l2jhellas.gameserver.model.L2Manor;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.zone.type.L2CastleZone;
import com.l2jhellas.gameserver.network.serverpackets.PledgeShowInfoUpdate;
import com.l2jhellas.util.database.L2DatabaseFactory;

public class Castle
{
	protected static Logger _log = Logger.getLogger(Castle.class.getName());

	private FastList<CropProcure> _procure = new FastList<CropProcure>();
	private FastList<SeedProduction> _production = new FastList<SeedProduction>();
	private FastList<CropProcure> _procureNext = new FastList<CropProcure>();
	private FastList<SeedProduction> _productionNext = new FastList<SeedProduction>();
	private boolean _isNextPeriodApproved = false;

	private static final String CASTLE_MANOR_DELETE_PRODUCTION = "DELETE FROM castle_manor_production WHERE castle_id=?;";
	private static final String CASTLE_MANOR_DELETE_PRODUCTION_PERIOD = "DELETE FROM castle_manor_production WHERE castle_id=? AND period=?;";
	private static final String CASTLE_MANOR_DELETE_PROCURE = "DELETE FROM castle_manor_procure WHERE castle_id=?;";
	private static final String CASTLE_MANOR_DELETE_PROCURE_PERIOD = "DELETE FROM castle_manor_procure WHERE castle_id=? AND period=?;";
	private static final String CASTLE_UPDATE_CROP = "UPDATE castle_manor_procure SET can_buy=? WHERE crop_id=? AND castle_id=? AND period=?";
	private static final String CASTLE_UPDATE_SEED = "UPDATE castle_manor_production SET can_produce=? WHERE seed_id=? AND castle_id=? AND period=?";

	private int _castleId = 0;
	private final List<L2DoorInstance> _doors = new FastList<L2DoorInstance>();
	private final List<String> _doorDefault = new FastList<String>();
	private String _name = "";
	private int _ownerId = 0;
	private Siege _siege = null;
	private Calendar _siegeDate;
	private int _siegeDayOfWeek = 7; // Default to Saturday
	private int _siegeHourOfDay = 20; // Default to 8 pm server time
	private int _taxPercent = 0;
	private double _taxRate = 0;
	private int _treasury = 0;
	private boolean _showNpcCrest = false;
	private L2CastleZone _zone;
	private L2Clan _formerOwner = null;
	private int _nbArtifact = 1;
	private final Map<Integer, Integer> _engrave = new FastMap<Integer, Integer>();

	public Castle(int castleId)
	{
		_castleId = castleId;
		if (_castleId == 7 || castleId == 9) // Goddard and Schuttgart
			_nbArtifact = 2;
		load();
		loadDoor();
	}

	public void Engrave(L2Clan clan, int objId)
	{
		_engrave.put(objId, clan.getClanId());
		if (_engrave.size() == _nbArtifact)
		{
			boolean rst = true;
			for (int id : _engrave.values())
			{
				if (id != clan.getClanId())
					rst = false;
			}
			if (rst)
			{
				_engrave.clear();
				setOwner(clan);
			}
			else
				getSiege().announceToPlayer("Clan " + clan.getName() + " has finished to engrave one of the rulers.", true);
		}
		else
			getSiege().announceToPlayer("Clan " + clan.getName() + " has finished to engrave one of the rulers.", true);
	}

	// This method add to the treasury
	/** Add amount to castle instance's treasury (warehouse). */
	public void addToTreasury(int amount)
	{
		if (getOwnerId() <= 0)
			return;

		if (_name.equalsIgnoreCase("Schuttgart") || _name.equalsIgnoreCase("Goddard"))
		{
			Castle rune = CastleManager.getInstance().getCastle("rune");
			if (rune != null)
			{
				int runeTax = (int) (amount * rune.getTaxRate());
				if (rune.getOwnerId() > 0)
					rune.addToTreasury(runeTax);
				amount -= runeTax;
			}
		}
		if (!_name.equalsIgnoreCase("aden") && !_name.equalsIgnoreCase("Rune") && !_name.equalsIgnoreCase("Schuttgart") && !_name.equalsIgnoreCase("Goddard"))
		// If current castle instance is not Aden, Rune, Goddard or Schuttgart.
		{
			Castle aden = CastleManager.getInstance().getCastle("aden");
			if (aden != null)
			{
				int adenTax = (int) (amount * aden.getTaxRate());        // Find out what Aden gets FROM the current castle instance's income
				if (aden.getOwnerId() > 0)
					aden.addToTreasury(adenTax); // Only bother to really add the tax to the treasury if not npc owned

				amount -= adenTax; // Subtract Aden's income FROM current castle instance's income
			}
		}
		addToTreasuryNoTax(amount);
	}

	/** Add amount to castle instance's treasury (warehouse), no tax paying. */
	public boolean addToTreasuryNoTax(int amount)
	{
		if (getOwnerId() <= 0)
			return false;

		if (amount < 0)
		{
			amount *= -1;
			if (_treasury < amount)
				return false;
			_treasury -= amount;
		}
		else
		{
			if ((long) _treasury + amount > Integer.MAX_VALUE)
				_treasury = Integer.MAX_VALUE;
			else
				_treasury += amount;
		}

		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("UPDATE castle SET treasury=? WHERE id=?");
			statement.setInt(1, getTreasury());
			statement.setInt(2, getCastleId());
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
		}
		return true;
	}

	/**
	 * Move non clan members off castle area and to nearest town.<BR>
	 * <BR>
	 */
	public void banishForeigners()
	{
		_zone.banishForeigners(getOwnerId());
	}

	/**
	 * Return true if object is inside the zone
	 */
	public boolean checkIfInZone(int x, int y, int z)
	{
		boolean isInside = false;
		
		if(_zone!=null)
			isInside = _zone.isInsideZone(x, y, z);
		
		return isInside;
	}

	/**
	 * Sets this castles zone
	 * 
	 * @param zone
	 */
	public void setZone(L2CastleZone zone)
	{
		_zone = zone;
	}

	public L2CastleZone getZone()
	{
		return _zone;
	}

	/**
	 * Get the objects distance to this castle
	 * 
	 * @param obj
	 * @return
	 */
	public double getDistance(L2Object obj)
	{
		
		double dist = 0 ;
		
		if(_zone != null && obj != null)
		{
			dist = _zone.getDistanceToZone(obj);
		}
		
		return dist;
	}

	public void closeDoor(L2PcInstance activeChar, int doorId)
	{
		openCloseDoor(activeChar, doorId, false);
	}

	public void openDoor(L2PcInstance activeChar, int doorId)
	{
		openCloseDoor(activeChar, doorId, true);
	}

	public void openCloseDoor(L2PcInstance activeChar, int doorId, boolean open)
	{
		if (activeChar.getClanId() != getOwnerId())
			return;

		L2DoorInstance door = getDoor(doorId);
		if (door != null)
		{
			if (open)
				door.openMe();
			else
				door.closeMe();
		}
	}

	// This method is used to begin removing all castle upgrades
	public void removeUpgrade()
	{
		removeDoorUpgrade();
	}

	// This method updates the castle tax rate
	public void setOwner(L2Clan clan)
	{
		// Remove old owner
		if ((getOwnerId() > 0) && ((clan == null) || (clan.getClanId() != getOwnerId())))
		{
			L2Clan oldOwner = ClanTable.getInstance().getClan(getOwnerId());
			if (oldOwner != null)
			{
				if (_formerOwner == null)
				{
					_formerOwner = oldOwner;
					if (Config.REMOVE_CASTLE_CIRCLETS)
					{
						CastleManager.getInstance().removeCirclet(_formerOwner, getCastleId());
					}
				}
				oldOwner.setHasCastle(0);// Unset has castle flag for old owner
				new Announcements().announceToAll(oldOwner.getName() + " has lost " + getName() + " castle!");
			}
		}

		updateOwnerInDB(clan);// Update in database

		if (getSiege().getIsInProgress())// If siege in progress
			getSiege().midVictory();// Mid victory phase of siege

		updateClansReputation();
	}

	public void removeOwner(L2Clan clan)
	{
		if (clan != null)
		{
			_formerOwner = clan;
			if (Config.REMOVE_CASTLE_CIRCLETS)
			{
				CastleManager.getInstance().removeCirclet(_formerOwner, getCastleId());
			}
			clan.setHasCastle(0);
			new Announcements().announceToAll(clan.getName() + " has lost " + getName() + " castle");
			clan.broadcastToOnlineMembers(new PledgeShowInfoUpdate(clan));
		}

		updateOwnerInDB(null);
		if (getSiege().getIsInProgress())
			getSiege().midVictory();

		updateClansReputation();
	}

	// This method updates the castle tax rate
	public void setTaxPercent(L2PcInstance activeChar, int taxPercent)
	{
		int maxTax;
		switch (SevenSigns.getInstance().getSealOwner(SevenSigns.SEAL_STRIFE))
		{
			case SevenSigns.CABAL_DAWN:
				maxTax = 25;
			break;
			case SevenSigns.CABAL_DUSK:
				maxTax = 5;
			break;
			default: // no owner
				maxTax = 15;
		}

		if (taxPercent < 0 || taxPercent > maxTax)
		{
			activeChar.sendMessage("Tax value must be between 0 and " + maxTax + ".");
			return;
		}

		setTaxPercent(taxPercent);
		activeChar.sendMessage(getName() + " castle tax changed to " + taxPercent + "%.");
	}

	public void setTaxPercent(int taxPercent)
	{
		_taxPercent = taxPercent;
		_taxRate = _taxPercent / 100.0;

		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("UPDATE castle SET taxPercent=? WHERE id=?");
			statement.setInt(1, taxPercent);
			statement.setInt(2, getCastleId());
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Error updating TaxPercent" + e);
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Respawn all doors on castle grounds<BR>
	 * <BR>
	 */
	public void spawnDoor()
	{
		spawnDoor(false);
	}

	/**
	 * Respawn all doors on castle grounds<BR>
	 * <BR>
	 */
	public void spawnDoor(boolean isDoorWeak)
	{
		for (int i = 0; i < getDoors().size(); i++)
		{
			L2DoorInstance door = getDoors().get(i);
			if (door.getCurrentHp() <= 0)
			{
				door.decayMe();	// Kill current if not killed already
				door = DoorTable.parseList(_doorDefault.get(i));
				if (isDoorWeak)
					door.setCurrentHp(door.getMaxHp() / 2);
				door.spawnMe(door.getX(), door.getY(), door.getZ());
				getDoors().set(i, door);
			}
			else if (door.getOpen())
				door.closeMe();
		}
		loadDoorUpgrade(); // Check for any upgrade the doors may have
	}

	// This method upgrade door
	public void upgradeDoor(int doorId, int hp, int pDef, int mDef)
	{
		L2DoorInstance door = getDoor(doorId);
		if (door == null)
			return;

		if (door != null && door.getDoorId() == doorId)
		{
			door.setCurrentHp(door.getMaxHp() + hp);

			saveDoorUpgrade(doorId, hp, pDef, mDef);
			return;
		}
	}

	// This method loads castle
	private void load()
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement;
			ResultSet rs;

			statement = con.prepareStatement("SELECT * FROM castle WHERE id=?");
			statement.setInt(1, getCastleId());
			rs = statement.executeQuery();

			while (rs.next())
			{
				_name = rs.getString("name");
				// _OwnerId = rs.getInt("ownerId");

				_siegeDate = Calendar.getInstance();
				_siegeDate.setTimeInMillis(rs.getLong("siegeDate"));

				_siegeDayOfWeek = rs.getInt("siegeDayOfWeek");
				if (_siegeDayOfWeek < 1 || _siegeDayOfWeek > 7)
					_siegeDayOfWeek = 7;

				_siegeHourOfDay = rs.getInt("siegeHourOfDay");
				if (_siegeHourOfDay < 0 || _siegeHourOfDay > 23)
					_siegeHourOfDay = 20;

				_taxPercent = rs.getInt("taxPercent");
				_treasury = rs.getInt("treasury");
				int showNpcCrest = rs.getInt("showNpcCrest");
				if (showNpcCrest == 1)
					_showNpcCrest = true;
				
			}

			statement.close();

			_taxRate = _taxPercent / 100.0;

			statement = con.prepareStatement("SELECT clan_id FROM clan_data WHERE hasCastle=?");
			statement.setInt(1, getCastleId());
			rs = statement.executeQuery();

			while (rs.next())
			{
				_ownerId = rs.getInt("clan_id");
			}

			if (getOwnerId() > 0)
			{
				L2Clan clan = ClanTable.getInstance().getClan(getOwnerId());// Try to find clan instance
				ThreadPoolManager.getInstance().scheduleGeneral(new CastleUpdater(clan, 1), 3600000);// Schedule owner tasks to start running
			}

			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Exception: loadCastleData(): " + e.getMessage());
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	// This method loads castle door data FROM database
	private void loadDoor()
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT * FROM castle_door WHERE castleId=?");
			statement.setInt(1, getCastleId());
			ResultSet rs = statement.executeQuery();

			while (rs.next())
			{
				// Create list of the door default for use when respawning dead doors
				_doorDefault.add(rs.getString("name") + ";" + rs.getInt("id") + ";" + rs.getInt("x") + ";" + rs.getInt("y") + ";" + rs.getInt("z") + ";" + rs.getInt("range_xmin") + ";" + rs.getInt("range_ymin") + ";" + rs.getInt("range_zmin") + ";" + rs.getInt("range_xmax") + ";" + rs.getInt("range_ymax") + ";" + rs.getInt("range_zmax") + ";" + rs.getInt("hp") + ";" + rs.getInt("pDef") + ";" + rs.getInt("mDef"));

				L2DoorInstance door = DoorTable.parseList(_doorDefault.get(_doorDefault.size() - 1));
				door.spawnMe(door.getX(), door.getY(), door.getZ());
				_doors.add(door);
				DoorTable.getInstance().putDoor(door);
			}

			statement.close();
		}
		catch (Exception e)
		{
			System.out.println("Exception: loadCastleDoor(): " + e.getMessage());
			e.printStackTrace();
		}
	}

	// This method loads castle door upgrade data FROM database
	private void loadDoorUpgrade()
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT * FROM castle_doorupgrade WHERE doorId IN (SELECT Id FROM castle_door WHERE castleId=?)");
			statement.setInt(1, getCastleId());
			ResultSet rs = statement.executeQuery();

			while (rs.next())
			{
				upgradeDoor(rs.getInt("id"), rs.getInt("hp"), rs.getInt("pDef"), rs.getInt("mDef"));
			}

			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Exception: loadCastleDoorUpgrade(): " + e.getMessage());
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	private void removeDoorUpgrade()
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("DELETE FROM castle_doorupgrade WHERE doorId IN (SELECT id FROM castle_door WHERE castleId=?)");
			statement.setInt(1, getCastleId());
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Exception: removeDoorUpgrade(): " + e.getMessage());
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	private void saveDoorUpgrade(int doorId, int hp, int pDef, int mDef)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("INSERT INTO castle_doorupgrade (doorId, hp, pDef, mDef) VALUES (?,?,?,?)");
			statement.setInt(1, doorId);
			statement.setInt(2, hp);
			statement.setInt(3, pDef);
			statement.setInt(4, mDef);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Exception: saveDoorUpgrade(int doorId, int hp, int pDef, int mDef): " + e.getMessage());
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	private void updateOwnerInDB(L2Clan clan)
	{
		if (clan != null)
			_ownerId = clan.getClanId();// Update owner id property
		else
			_ownerId = 0;				// Remove owner

		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement;

			// ============================================================================
			// NEED TO REMOVE HAS CASTLE FLAG FROM CLAN_DATA
			// SHOULD BE CHECKED FROM CASTLE TABLE
			statement = con.prepareStatement("UPDATE clan_data SET hasCastle=0 WHERE hasCastle=?");
			statement.setInt(1, getCastleId());
			statement.execute();
			statement.close();

			statement = con.prepareStatement("UPDATE clan_data SET hasCastle=? WHERE clan_id=?");
			statement.setInt(1, getCastleId());
			statement.setInt(2, getOwnerId());
			statement.execute();
			statement.close();
			// ============================================================================

			// Announce to clan members
			if (clan != null)
			{
				clan.setHasCastle(getCastleId()); // Set has castle flag for new owner
				new Announcements().announceToAll(clan.getName() + " has taken " + getName() + " castle!");
				clan.broadcastToOnlineMembers(new PledgeShowInfoUpdate(clan));

				ThreadPoolManager.getInstance().scheduleGeneral(new CastleUpdater(clan, 1), 3600000);// Schedule owner tasks to start running
			}
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Exception: updateOwnerInDB(L2Clan clan): " + e.getMessage());
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	public final int getCastleId()
	{
		return _castleId;
	}

	public final L2DoorInstance getDoor(int doorId)
	{
		if (doorId <= 0)
			return null;

		for (int i = 0; i < getDoors().size(); i++)
		{
			L2DoorInstance door = getDoors().get(i);
			if (door.getDoorId() == doorId)
				return door;
		}
		return null;
	}

	public final List<L2DoorInstance> getDoors()
	{
		return _doors;
	}

	public final String getName()
	{
		return _name;
	}

	public final int getOwnerId()
	{
		return _ownerId;
	}

	public final Siege getSiege()
	{
		if (_siege == null)
			_siege = new Siege(new Castle[]
			{
				this
			});
		return _siege;
	}

	public final Calendar getSiegeDate()
	{
		return _siegeDate;
	}

	public final int getSiegeDayOfWeek()
	{
		return _siegeDayOfWeek;
	}

	public final int getSiegeHourOfDay()
	{
		return _siegeHourOfDay;
	}

	public final int getTaxPercent()
	{
		return _taxPercent;
	}

	public final double getTaxRate()
	{
		return _taxRate;
	}

	public final int getTreasury()
	{
		return _treasury;
	}

	public final boolean getShowNpcCrest()
	{
		return _showNpcCrest;
	}

	public final void setShowNpcCrest(boolean showNpcCrest)
	{
		if (_showNpcCrest != showNpcCrest)
		{
			_showNpcCrest = showNpcCrest;
			updateShowNpcCrest();
		}
	}

	public FastList<SeedProduction> getSeedProduction(int period)
	{
		return (period == CastleManorManager.PERIOD_CURRENT ? _production : _productionNext);
	}

	public FastList<CropProcure> getCropProcure(int period)
	{
		return (period == CastleManorManager.PERIOD_CURRENT ? _procure : _procureNext);
	}

	public void setSeedProduction(FastList<SeedProduction> seed, int period)
	{
		if (period == CastleManorManager.PERIOD_CURRENT)
			_production = seed;
		else
			_productionNext = seed;
	}

	public void setCropProcure(FastList<CropProcure> crop, int period)
	{
		if (period == CastleManorManager.PERIOD_CURRENT)
			_procure = crop;
		else
			_procureNext = crop;
	}

	public synchronized SeedProduction getSeed(int seedId, int period)
	{
		for (SeedProduction seed : getSeedProduction(period))
		{
			if (seed.getId() == seedId)
			{
				return seed;
			}
		}
		return null;
	}

	public synchronized CropProcure getCrop(int cropId, int period)
	{
		for (CropProcure crop : getCropProcure(period))
		{
			if (crop.getId() == cropId)
			{
				return crop;
			}
		}
		return null;
	}

	public int getManorCost(int period)
	{
		FastList<CropProcure> procure;
		FastList<SeedProduction> production;

		if (period == CastleManorManager.PERIOD_CURRENT)
		{
			procure = _procure;
			production = _production;
		}
		else
		{
			procure = _procureNext;
			production = _productionNext;
		}

		int total = 0;
		if (production != null)
		{
			for (SeedProduction seed : production)
			{
				total += L2Manor.getInstance().getSeedBuyPrice(seed.getId()) * seed.getStartProduce();
			}
		}
		if (procure != null)
		{
			for (CropProcure crop : procure)
			{
				total += crop.getPrice() * crop.getStartAmount();
			}
		}
		return total;
	}

	// save manor production data
	public void saveSeedData()
	{
		PreparedStatement statement;

		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			statement = con.prepareStatement(CASTLE_MANOR_DELETE_PRODUCTION);
			statement.setInt(1, getCastleId());

			statement.execute();
			statement.close();

			if (_production != null)
			{
				int count = 0;
				String query = "INSERT INTO castle_manor_production VALUES ";
				String values[] = new String[_production.size()];
				for (SeedProduction s : _production)
				{
					values[count] = "(" + getCastleId() + "," + s.getId() + "," + s.getCanProduce() + "," + s.getStartProduce() + "," + s.getPrice() + "," + CastleManorManager.PERIOD_CURRENT + ")";
					count++;
				}
				if (values.length > 0)
				{
					query += values[0];
					for (int i = 1; i < values.length; i++)
					{
						query += "," + values[i];
					}
					statement = con.prepareStatement(query);
					statement.execute();
					statement.close();
				}
			}

			if (_productionNext != null)
			{
				int count = 0;
				String query = "INSERT INTO castle_manor_production VALUES ";
				String values[] = new String[_productionNext.size()];
				for (SeedProduction s : _productionNext)
				{
					values[count] = "(" + getCastleId() + "," + s.getId() + "," + s.getCanProduce() + "," + s.getStartProduce() + "," + s.getPrice() + "," + CastleManorManager.PERIOD_NEXT + ")";
					count++;
				}
				if (values.length > 0)
				{
					query += values[0];
					for (int i = 1; i < values.length; i++)
					{
						query += "," + values[i];
					}
					statement = con.prepareStatement(query);
					statement.execute();
					statement.close();
				}
			}
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Error adding seed production data for castle " + getName() + ": " + e.getMessage());
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	// save manor production data for specified period
	public void saveSeedData(int period)
	{
		PreparedStatement statement;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			statement = con.prepareStatement(CASTLE_MANOR_DELETE_PRODUCTION_PERIOD);
			statement.setInt(1, getCastleId());
			statement.setInt(2, period);
			statement.execute();
			statement.close();

			FastList<SeedProduction> prod = null;
			prod = getSeedProduction(period);

			if (prod != null)
			{
				int count = 0;
				String query = "INSERT INTO castle_manor_production VALUES ";
				String values[] = new String[prod.size()];
				for (SeedProduction s : prod)
				{
					values[count] = "(" + getCastleId() + "," + s.getId() + "," + s.getCanProduce() + "," + s.getStartProduce() + "," + s.getPrice() + "," + period + ")";
					count++;
				}
				if (values.length > 0)
				{
					query += values[0];
					for (int i = 1; i < values.length; i++)
					{
						query += "," + values[i];
					}
					statement = con.prepareStatement(query);
					statement.execute();
					statement.close();
				}
			}
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Error adding seed production data for castle " + getName() + ": " + e.getMessage());
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	// save crop procure data
	public void saveCropData()
	{
		PreparedStatement statement;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			statement = con.prepareStatement(CASTLE_MANOR_DELETE_PROCURE);
			statement.setInt(1, getCastleId());
			statement.execute();
			statement.close();
			if (_procure != null)
			{
				int count = 0;
				String query = "INSERT INTO castle_manor_procure VALUES ";
				String values[] = new String[_procure.size()];
				for (CropProcure cp : _procure)
				{
					values[count] = "(" + getCastleId() + "," + cp.getId() + "," + cp.getAmount() + "," + cp.getStartAmount() + "," + cp.getPrice() + "," + cp.getReward() + "," + CastleManorManager.PERIOD_CURRENT + ")";
					count++;
				}
				if (values.length > 0)
				{
					query += values[0];
					for (int i = 1; i < values.length; i++)
					{
						query += "," + values[i];
					}
					statement = con.prepareStatement(query);
					statement.execute();
					statement.close();
				}
			}
			if (_procureNext != null)
			{
				int count = 0;
				String query = "INSERT INTO castle_manor_procure VALUES ";
				String values[] = new String[_procureNext.size()];
				for (CropProcure cp : _procureNext)
				{
					values[count] = "(" + getCastleId() + "," + cp.getId() + "," + cp.getAmount() + "," + cp.getStartAmount() + "," + cp.getPrice() + "," + cp.getReward() + "," + CastleManorManager.PERIOD_NEXT + ")";
					count++;
				}
				if (values.length > 0)
				{
					query += values[0];
					for (int i = 1; i < values.length; i++)
					{
						query += "," + values[i];
					}
					statement = con.prepareStatement(query);
					statement.execute();
					statement.close();
				}
			}
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Error adding crop data for castle " + getName() + ": " + e.getMessage());
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	// save crop procure data for specified period
	public void saveCropData(int period)
	{
		PreparedStatement statement;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			statement = con.prepareStatement(CASTLE_MANOR_DELETE_PROCURE_PERIOD);
			statement.setInt(1, getCastleId());
			statement.setInt(2, period);
			statement.execute();
			statement.close();

			FastList<CropProcure> proc = null;
			proc = getCropProcure(period);

			if (proc != null)
			{
				int count = 0;
				String query = "INSERT INTO castle_manor_procure VALUES ";
				String values[] = new String[proc.size()];

				for (CropProcure cp : proc)
				{
					values[count] = "(" + getCastleId() + "," + cp.getId() + "," + cp.getAmount() + "," + cp.getStartAmount() + "," + cp.getPrice() + "," + cp.getReward() + "," + period + ")";
					count++;
				}
				if (values.length > 0)
				{
					query += values[0];
					for (int i = 1; i < values.length; i++)
					{
						query += "," + values[i];
					}
					statement = con.prepareStatement(query);
					statement.execute();
					statement.close();
				}
			}
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Error adding crop data for castle " + getName() + ": " + e.getMessage());
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	public void updateCrop(int cropId, int amount, int period)
	{
		PreparedStatement statement;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			statement = con.prepareStatement(CASTLE_UPDATE_CROP);
			statement.setInt(1, amount);
			statement.setInt(2, cropId);
			statement.setInt(3, getCastleId());
			statement.setInt(4, period);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Error adding crop data for castle " + getName() + ": " + e.getMessage());
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	public void updateSeed(int seedId, int amount, int period)
	{
		PreparedStatement statement;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			statement = con.prepareStatement(CASTLE_UPDATE_SEED);
			statement.setInt(1, amount);
			statement.setInt(2, seedId);
			statement.setInt(3, getCastleId());
			statement.setInt(4, period);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Error adding seed production data for castle " + getName() + ": " + e.getMessage());
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	public void updateShowNpcCrest()
	{
		PreparedStatement statement;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			statement = con.prepareStatement("UPDATE castle SET showNpcCrest=? WHERE id=?");
			statement.setString(1, String.valueOf(getShowNpcCrest()));
			statement.setInt(2, getCastleId());
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Error saving showNpcCrest for castle " + getName() + ": " + e.getMessage());
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	public boolean isNextPeriodApproved()
	{
		return _isNextPeriodApproved;
	}

	public void setNextPeriodApproved(boolean val)
	{
		_isNextPeriodApproved = val;
	}

	public void updateClansReputation()
	{
		if (_formerOwner != null)
		{
			if (_formerOwner != ClanTable.getInstance().getClan(getOwnerId()))
			{
				int maxreward = Math.max(0, _formerOwner.getReputationScore());
				_formerOwner.setReputationScore(_formerOwner.getReputationScore() - 1000, true);
				L2Clan owner = ClanTable.getInstance().getClan(getOwnerId());
				if (owner != null)
				{
					owner.setReputationScore(owner.getReputationScore() + Math.min(1000, maxreward), true);
					owner.broadcastToOnlineMembers(new PledgeShowInfoUpdate(owner));
				}
			}
			else
				_formerOwner.setReputationScore(_formerOwner.getReputationScore() + 500, true);

			_formerOwner.broadcastToOnlineMembers(new PledgeShowInfoUpdate(_formerOwner));
		}
		else
		{
			L2Clan owner = ClanTable.getInstance().getClan(getOwnerId());
			if (owner != null)
			{
				owner.setReputationScore(owner.getReputationScore() + 1000, true);
				owner.broadcastToOnlineMembers(new PledgeShowInfoUpdate(owner));
			}
		}
	}
}