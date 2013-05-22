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
package com.l2jhellas.gameserver.model.zone.type;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.datatables.sql.MapRegionTable;
import com.l2jhellas.gameserver.model.L2Character;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.zone.L2ZoneType;

/**
 * A Town zone
 * 
 * @author durgus
 */
public class L2TownZone extends L2ZoneType
{
	private String _townName;
	private int _townId;
	private int _redirectTownId;
	private int _taxById;
	private boolean _noPeace;
	private boolean _isPeaceZone;
	private final int[] _spawnLoc;

	public L2TownZone(int id)
	{
		super(id);

		_taxById = 0;
		_spawnLoc = new int[3];

		// Default to Giran
		_redirectTownId = 9;

		// Default peace zone
		_noPeace = false;
	}

	@Override
	public void setParameter(String name, String value)
	{
		if (name.equals("name"))
		{
			_townName = value;
		}
		else if (name.equals("townId"))
		{
			_townId = Integer.parseInt(value);
		}
		else if (name.equals("redirectTownId"))
		{
			_redirectTownId = Integer.parseInt(value);
		}
		else if (name.equals("taxById"))
		{
			_taxById = Integer.parseInt(value);
		}
		else if (name.equals("spawnX"))
		{
			_spawnLoc[0] = Integer.parseInt(value);
		}
		else if (name.equals("spawnY"))
		{
			_spawnLoc[1] = Integer.parseInt(value);
		}
		else if (name.equals("spawnZ"))
		{
			_spawnLoc[2] = Integer.parseInt(value);
		}
		else if (name.equals("noPeace"))
		{
			_noPeace = Boolean.parseBoolean(value);
		}
		else
			super.setParameter(name, value);
	}

	@Override
	protected void onEnter(L2Character character)
	{
		if (character instanceof L2PcInstance)
		{
			// PVP possible during siege, now for siege participants only
			// Could also check if this town is in siege, or if any siege is going on
			if (((L2PcInstance) character).getSiegeState() != 0 && Config.ZONE_TOWN == 1)
				return;

			//((L2PcInstance)character).sendMessage("You entered "+_townName);
			((L2PcInstance) character).setLastTownName(_townName);
		}

		if (!_noPeace && Config.ZONE_TOWN != 2)
			character.setInsideZone(L2Character.ZONE_PEACE, true);

	}

	@Override
	protected void onExit(L2Character character)
	{
		if (!_noPeace)
			character.setInsideZone(L2Character.ZONE_PEACE, false);

		// if (character instanceof L2PcInstance)
		//((L2PcInstance)character).sendMessage("You left "+_townName);

	}

	@Override
	public void onDieInside(L2Character character)
	{
	}

	@Override
	public void onReviveInside(L2Character character)
	{
	}

	/**
	 * Returns this town zones name
	 * 
	 * @return
	 */
	public String getName()
	{
		return MapRegionTable.getInstance().getClosestTownName(_townId);
	}

	/**
	 * Returns this zones town id (if any)
	 * 
	 * @return
	 */
	public int getTownId()
	{
		return _townId;
	}

	/**
	 * Gets the id for this town zones redir town
	 * 
	 * @return
	 */
	@Deprecated
	public int getRedirectTownId()
	{
		return _redirectTownId;
	}

	/**
	 * Returns this zones spawn location
	 * 
	 * @return
	 */
	public final int[] getSpawnLoc()
	{
		return _spawnLoc;
	}

	/**
	 * Returns this town zones castle id
	 * 
	 * @return
	 */
	public final int getTaxById()
	{
		return _taxById;
	}

	public final boolean isPeaceZone()
	{
		return _isPeaceZone;
	}
}