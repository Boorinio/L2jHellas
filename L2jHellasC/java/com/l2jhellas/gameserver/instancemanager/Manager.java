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
		//ArenaManager.
		AuctionManager.getInstance().reload();
		//AwayManager.
		BoatManager.getInstance().reload();
		CastleManager.getInstance().reload();
		ClanHallManager.getInstance().reload();
		CoupleManager.getInstance().reload();
		CursedWeaponsManager.getInstance().reload();
		DayNightSpawnManager.getInstance().reload();
		//DimensionalRiftManager.getInstance().reload();
		//DuelManager
		//FishingZoneManager.
		//FourSepulchersManager.getInstance().
		GrandBossManager.getInstance().reload();
		ItemsOnGroundManager.getInstance().reload();
		MercTicketManager.getInstance().reload();
		//OlympiadStadiaManager.getInstance().reload();
		//PetitionManager.getInstance().
		//QuestManager.getInstance().reloadAllQuests();
		RaidBossPointsManager.getInstance().reload();
		RaidBossSpawnManager.getInstance().reload();
		//SiegeGuardManager.getInstance().
		//SiegeManager.getInstance().
		//SiegeReward.getInstance().
		//TownManager.getInstance().reload();
		//ZoneManager.getInstance().reload();
	}// osa exoun reload(); vgazoun NPE apo to deftero-trito reload kai meta
}// osa den exoun reload(); xreiazonte