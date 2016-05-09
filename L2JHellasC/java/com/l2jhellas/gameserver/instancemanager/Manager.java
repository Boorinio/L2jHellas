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
package com.l2jhellas.gameserver.instancemanager;

public class Manager
{
	public static void reloadAll()
	{
		ZoneManager.getInstance().reload();
		TownManager.getInstance();
		ItemsOnGroundManager.getInstance().reload();
		FishingZoneManager.getInstance();	
		AuctionManager.getInstance().reload();
		BoatManager.getInstance();
		CastleManager.getInstance().reload();
		ClanHallManager.getInstance().reload();
		CoupleManager.getInstance().reload();
		CursedWeaponsManager.reload();
		DayNightSpawnManager.getInstance().reload();
		DimensionalRiftManager.getInstance().reload();
		DuelManager.getInstance();	
		FourSepulchersManager.getInstance();
		GrandBossManager.getInstance().reload();	
		MercTicketManager.getInstance().reload();
		OlympiadStadiaManager.getInstance();
		PetitionManager.getInstance();
		RaidBossSpawnManager.getInstance().reloadBosses();
		SiegeManager.getInstance();
		SiegeReward.getInstance();
		
	}
}