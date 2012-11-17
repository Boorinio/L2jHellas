/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package Extensions.RankSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.l2jhellas.ExternalConfig;
import com.l2jhellas.L2DatabaseFactory;
import com.l2jhellas.gameserver.datatables.ItemTable;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.UserInfo;
import com.l2jhellas.gameserver.templates.L2Item;

/**
 * @author Masterio
 */
public class RankPvpSystem
{
	
	private static final Logger _log = Logger.getLogger(RankPvpSystem.class.getName());
	
	private static final boolean DEBUG_MODE = false;
	
	/**
	 * Executed when kill player (from victim side)
	 * 
	 * @param killer
	 * @param victim
	 */
	public void doRankPvp(L2PcInstance killer, L2PcInstance victim)
	{
		int kills = 0;
		int kills_today = 0;
		int kills_legal = 0;
		long kill_time = 0;
		long victim_rank_points = 0;
		// long victim_rank_points_today = 0; //unused now
		
		long sys_time = System.currentTimeMillis();
		long prot_time = (1000 * 60 * ExternalConfig.RANK_PVP_PROTECTION_RESET);
		
		if (checkBasicConditions(killer, victim))
		{
			
			int isLegal = checkIsLegalKill(killer, victim);
			int alttEnabled = 0;
			if (ExternalConfig.RANK_PVP_LEGAL_COUNTER_ALTT_ENABLED)
			{
				alttEnabled = 1;
			}
			
			Connection con = null;
			PreparedStatement statement = null;
			ResultSet rset = null;
			
			try
			{
				con = L2DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("CALL CPS_add(?,?,?,?,?,?,?)");
				// query returns updated killer data, it's mean add +1 to
				// 'kills' and 'kills_today' if 'kill_time' is correct.
				statement.setInt(1, killer.getObjectId());
				statement.setInt(2, victim.getObjectId());
				statement.setLong(3, sys_time);
				statement.setLong(4, prot_time);
				statement.setInt(5, isLegal);
				statement.setInt(6, alttEnabled);
				if (victim.getClan() != null && killer.getClan() != null && victim.getClan().isAtWarWith(killer.getClan().getClanId()))
				{
					statement.setInt(7, 1);
				}
				else
				{
					statement.setInt(7, 0);
				}
				
				rset = statement.executeQuery();
				
				while (rset.next())
				{
					kills = rset.getInt("kills");
					kills_today = rset.getInt("kills_today");
					kills_legal = rset.getInt("kills_legal");
					kill_time = rset.getLong("kill_time");
					victim_rank_points = rset.getLong("rank_points");
					// victim_rank_points_today =
					// rset.getLong("rank_points_today");
					
					break;
				}
			}
			catch (SQLException e)
			{
				_log.info("SQL CustomPvpRewardSystem.doCustomPvp:");
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if (rset != null)
					{
						rset.close();
						rset = null;
					}
					if (statement != null)
					{
						statement.close();
						statement = null;
					}
					if (con != null)
					{
						con.close();
						con = null;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			
			// update killer Alt-T info.
			if (ExternalConfig.RANK_PVP_LEGAL_COUNTER_ALTT_ENABLED)
			{
				killer.setPvpKills(kills_legal);
				killer.sendPacket(new UserInfo(killer));
				killer.broadcastUserInfo();
				// killer.sendPacket(new ExBrExtraUserInfo(killer));
			}
			
			killer.sendMessage("----------------------------------------------------------------");
			victim.sendMessage("----------------------------------------------------------------");
			
			// execute PvP reward script if enabled:
			if (checkItemRewardConditions(killer, victim, kills_today, calculateTimeToString(sys_time, kill_time)))
			{
				addItemRewardForKiller(killer, victim);
			}

			// execute rank points script if enabled:
			if (checkRankPointsConditions(killer, victim, kills_today, calculateTimeToString(sys_time, kill_time)))
			{
				addRankPointsForKiller(killer, victim, getPointsForKill(kills_today, victim_rank_points), victim_rank_points);
			}

			// show current kills / kills_today info:
			if (kills > 1)
			{
				String timeStr1 = " times";
				if (kills_today == 1)
				{
					timeStr1 = "st time";
				}
				String msgVictim1 = killer.getName() + " killed you " + kills + " times.";
				String msgVictim2 = killer.getName() + " killed you " + kills + " times ( " + kills_today + "" + timeStr1 + " today ).";
				String msgKiller1 = "You have killed " + victim.getName() + " " + kills + " times.";
				String msgKiller2 = "You have killed " + victim.getName() + " " + kills + " times ( " + kills_today + "" + timeStr1 + " today ).";
				
				if (ExternalConfig.RANK_PVP_PROTECTION_RESET == 0)
				{
					victim.sendMessage(msgVictim1);
					killer.sendMessage(msgKiller1);
				}
				else
				{
					victim.sendMessage(msgVictim2);
					killer.sendMessage(msgKiller2);
				}
			}
			else
			{
				victim.sendMessage("This is the first time you have been killed by " + killer.getName() + ".");
				killer.sendMessage("You have killed " + victim.getName() + " for the first time.");
			}

			killer.sendMessage("----------------------------------------------------------------");
			victim.sendMessage("----------------------------------------------------------------");
			
			if (ExternalConfig.RANK_PVP_DEATH_MANAGER_DETAILS_ENABLED)
			{
				// victim._customPvpSystemDeathMgr = null;
				victim._RankPvpSystemDeathMgr = new RankPvpSystemDeathMgr(killer, victim);
			}
			
			if (ExternalConfig.RANK_PVP_INFO_COMMAND_ON_DEATH_ENABLED)
			{
				if (!RankPvpSystemDeathMgr.isInRestrictedZone(killer))
				{
					RankPvpSystemPlayerInfo playerInfo = new RankPvpSystemPlayerInfo();
					playerInfo.sendPlayerResponse(victim, killer);
				}
			}
		}
	}

	private void addRankPointsForKiller(L2PcInstance killer, L2PcInstance victim, int victim_kill_points, long victim_rank_points)
	{
		long rank_points = 0;
		long rank_points_today = 0;
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rset = null;
		
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			statement = con.prepareStatement("CALL CPS_add_rank_points(?,?,?)"); // query
																					// update
																					// points
			statement.setInt(1, killer.getObjectId());
			statement.setInt(2, victim.getObjectId());
			statement.setLong(3, victim_kill_points); // send information about
														// points for kill
														// victim
			
			rset = statement.executeQuery();
			
			while (rset.next())
			{
				rank_points = rset.getInt("rank_points");
				rank_points_today = rset.getInt("rank_points_today");
				break;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				if (statement != null)
				{
					statement.close();
					statement = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		if (ExternalConfig.RANK_PVP_REWARD_ENABLED)
		{
			int reward_pointer = getRankId(victim_rank_points);
			if (reward_pointer >= 0)
			{
				L2Item reward = ItemTable.getInstance().getTemplate(ExternalConfig.RANK_PVP_REWARD_IDS.get(reward_pointer));
				killer.addItem("Rank Reward", ExternalConfig.RANK_PVP_REWARD_IDS.get(reward_pointer), ExternalConfig.RANK_PVP_REWARD_AMOUNTS.get(reward_pointer), killer, false);
				killer.sendMessage("You have earned " + ExternalConfig.RANK_PVP_REWARD_AMOUNTS.get(reward_pointer) + "x " + reward.getName() + ".");
			}
			else
			{
				_log.info("CPRS.addRankPointsForKiller()::Error in pvp property fields.");
			}

		}
		
		if (ExternalConfig.RANK_PVP_SHOUT_INFO)
		{
			killer.sendMessage("You earned " + victim_kill_points + " Rank Points for kill " + victim.getName() + " (" + getRankName(victim_rank_points) + ")");
			killer.sendMessage("Your Rank Points: " + rank_points + " (" + rank_points_today + " today), current Rank: " + getRankName(rank_points));
			victim.sendMessage("You have been killed by " + killer.getName() + " (" + getRankName(rank_points) + ")");
		}
	}
	
	/**
	 * Adds the item reward.
	 * 
	 * @param killer
	 *        Player who killed.
	 * @param victim
	 *        victim Player.
	 */
	private void addItemRewardForKiller(L2PcInstance killer, L2PcInstance victim)
	{
		if (killer != null)
		{
			L2Item reward = ItemTable.getInstance().getTemplate(ExternalConfig.RANK_PVP_REWARD_ID);
			killer.addItem("PvP Reward", ExternalConfig.RANK_PVP_REWARD_ID, ExternalConfig.RANK_PVP_REWARD_AMOUNT, killer, false);
			killer.sendMessage("You have earned " + ExternalConfig.RANK_PVP_REWARD_AMOUNT + "x " + reward.getName() + ".");
		}
	}
	
	public static final String calculateTimeToString(long sys_time, long kill_time)
	{
		long TimeToRewardInMilli = ((kill_time + (1000 * 60 * ExternalConfig.RANK_PVP_PROTECTION_RESET)) - sys_time);
		long TimeToRewardHours = TimeToRewardInMilli / (60 * 60 * 1000);
		long TimeToRewardMinutes = (TimeToRewardInMilli % (60 * 60 * 1000)) / (60 * 1000);
		long TimeToRewardSeconds = (TimeToRewardInMilli % (60 * 1000)) / (1000);
		
		String H = Long.toString(TimeToRewardHours);
		String M = Long.toString(TimeToRewardMinutes);
		String S = Long.toString(TimeToRewardSeconds);
		if (TimeToRewardHours <= 9)
		{
			H = "0" + H;
		}
		if (TimeToRewardMinutes <= 9)
		{
			M = "0" + M;
		}
		if (TimeToRewardSeconds <= 9)
		{
			S = "0" + S;
		}
		
		return H + ":" + M + ":" + S;
	}
	
	/**
	 * Calculate rank points awarded for kill victim.
	 * 
	 * @param kills_today
	 *        - killer kills_today (mysql::sum(kills_today) ... WHERE victim_id
	 *        = victimId()).
	 * @param rank_points
	 *        - victim total rank_points (mysql::sum(rank_points)).
	 * @return Rank points for kill victim.
	 */
	private int getPointsForKill(int kills_today, long rank_points)
	{
		if (kills_today <= ExternalConfig.RANK_PVP_PROTECTION || ExternalConfig.RANK_PVP_PROTECTION == 0)
		{
			double war_ratio = 1.0;
			if (ExternalConfig.RANK_PVP_WAR_ENABLED)
			{
				war_ratio = ExternalConfig.RANK_PVP_WAR_RP_RATIO;
			}
			if (ExternalConfig.RANK_PVP_KILL_POINTS_DOWN_ENABLED)
			{
				for (int i = 0; i < ExternalConfig.RANK_PVP_KILL_POINTS_DOWN.size(); i++)
				{
					if (i + 1 == kills_today)
					{
						return (int) Math.round((ExternalConfig.RANK_PVP_KILL_POINTS_DOWN.get(i) * war_ratio));
					}
				}
			}
			else
			{
				if (ExternalConfig.RANK_PVP_MIN_POINTS.size() > 0 && ExternalConfig.RANK_PVP_POINTS_FOR_KILL.size() > 0 && ExternalConfig.RANK_PVP_MIN_POINTS.size() == ExternalConfig.RANK_PVP_POINTS_FOR_KILL.size())
				{
					if (rank_points >= ExternalConfig.RANK_PVP_MIN_POINTS.get(0))
					{
						return (int) Math.round(ExternalConfig.RANK_PVP_POINTS_FOR_KILL.get(0) * war_ratio);
					}
					for (int i = ExternalConfig.RANK_PVP_MIN_POINTS.size() - 1; i >= 0; i--)
					{
						if (rank_points < ExternalConfig.RANK_PVP_MIN_POINTS.get(i))
						{
							return (int) Math.round(ExternalConfig.RANK_PVP_POINTS_FOR_KILL.get(i) * war_ratio);
						}
					}
				}
				else
				{
					_log.info("getPointsForKill()::Wrong Rank property size.");
				}
			}
		}
		return 0;
	}
	
	private String getRankName(long rank_points)
	{
		if (ExternalConfig.RANK_PVP_MIN_POINTS.size() > 0 && ExternalConfig.RANK_PVP_NAME.size() > 0 && ExternalConfig.RANK_PVP_MIN_POINTS.size() == ExternalConfig.RANK_PVP_NAME.size())
		{
			if (rank_points >= ExternalConfig.RANK_PVP_MIN_POINTS.get(0))
			{
				return ExternalConfig.RANK_PVP_NAME.get(0);
			}
			for (int i = ExternalConfig.RANK_PVP_MIN_POINTS.size() - 1; i >= 0; i--)
			{
				if (rank_points < ExternalConfig.RANK_PVP_MIN_POINTS.get(i))
				{
					return ExternalConfig.RANK_PVP_NAME.get(i);
				}
			}
		}
		else
		{
			_log.info("getRankName()::Wrong Rank property size.");
		}
		return "Empty";
	}
	
	/**
	 * Special method used for obtain position of rank in property array.<br>
	 * Positions: [0 .. (size()-1)].
	 * 
	 * @param rank_points
	 * @return
	 */
	private int getRankId(long rank_points)
	{
		if (ExternalConfig.RANK_PVP_MIN_POINTS.size() > 0 && ExternalConfig.RANK_PVP_NAME.size() > 0 && ExternalConfig.RANK_PVP_MIN_POINTS.size() == ExternalConfig.RANK_PVP_NAME.size())
		{
			if (rank_points >= ExternalConfig.RANK_PVP_MIN_POINTS.get(0))
			{
				return 0;
			}
			for (int i = ExternalConfig.RANK_PVP_MIN_POINTS.size() - 1; i >= 0; i--)
			{
				if (rank_points < ExternalConfig.RANK_PVP_MIN_POINTS.get(i))
				{
					return i;
				}
			}
		}
		else
		{
			_log.info("getRankName()::Wrong Rank property size.");
		}
		return -1;
	}
	
	/**
	 * Check Basic conditions for CPRS, it's mean check if can I add +1 into
	 * kills and kills_today.<br>
	 * Basic mean: if killer is: in olympiad, in event, in restricted zone, etc.
	 * 
	 * @param killer
	 * @param victim
	 * @return TRUE if conditions are correct.
	 */
	private boolean checkBasicConditions(L2PcInstance killer, L2PcInstance victim)
	{
		
		if (killer == null || victim == null)
		{
			if (DEBUG_MODE)
			{
				_log.info("checkBasicConditions: 1");
			}
			return false;
		}
		
		if (killer.isDead() || killer.isAlikeDead())
		{
			if (DEBUG_MODE)
			{
				_log.info("checkBasicConditions: 2");
			}
			return false;
		}
		
		if (killer.isInOlympiadMode() || killer.isOlympiadStart())
		{
			if (DEBUG_MODE)
			{
				_log.info("checkBasicConditions: 3");
			}
			return false;
		}
		
		if (killer.atEvent || killer._inEventCTF || killer._inEventDM || killer._inEventTvT || killer._inEventVIP)
		{
			if (DEBUG_MODE)
			{
				_log.info("checkBasicConditions: 4");
			}
			return false;
		}
		
		if (isInRestrictedZone(killer))
		{
			if (DEBUG_MODE)
			{
				_log.info("checkBasicConditions: 5");
			}
			if (ExternalConfig.RANK_PVP_REWARD_ENABLED && ExternalConfig.RANK_PVP_ENABLED)
			{
				killer.sendMessage("You can't get Reward and Rank Points in restricted zone");
				return false;
			}
			if (ExternalConfig.RANK_PVP_REWARD_ENABLED)
			{
				killer.sendMessage("You can't get Reward in restricted zone");
				return false;
			}
			if (ExternalConfig.RANK_PVP_ENABLED)
			{
				killer.sendMessage("You can't rise Rank Points in restricted zone");
				return false;
			}
			return false;
		}
		
		if (!antiFarmCheck(killer, victim))
		{
			if (DEBUG_MODE)
			{
				_log.info("checkBasicConditions: 6");
			}
			return false;
		}

		return true;
	}
	
	private boolean checkItemRewardConditions(L2PcInstance killer, L2PcInstance victim, int killer_kills_today, String nextRewardTime)
	{
		
		if (!ExternalConfig.RANK_PVP_REWARD_ENABLED)
		{
			return false;
		}
		
		if (ExternalConfig.RANK_PVP_PROTECTION_RESET == 0 && killer_kills_today > ExternalConfig.RANK_PVP_REWARD_PROTECTION && ExternalConfig.RANK_PVP_REWARD_PROTECTION > 0)
		{
			if (DEBUG_MODE)
			{
				_log.info("checkItemRewardConditions: 2");
			}
			killer.sendMessage("Reward has been awarded for kill this player!");
			return false;
		}
		
		if (ExternalConfig.RANK_PVP_PROTECTION_RESET > 0 && killer_kills_today > ExternalConfig.RANK_PVP_REWARD_PROTECTION && ExternalConfig.RANK_PVP_REWARD_PROTECTION > 0)
		{
			if (DEBUG_MODE)
			{
				_log.info("checkItemRewardConditions: 3");
			}
			killer.sendMessage("Reward has been awarded for kill this player today!");
			if (!ExternalConfig.RANK_PVP_ENABLED)
			{
				killer.sendMessage("Next for " + nextRewardTime);
			}
			return false;
		}
		
		if (!ExternalConfig.RANK_PVP_REWARD_PK_MODE_ENABLED && killer.getKarma() != 0)
		{
			if (DEBUG_MODE)
			{
				_log.info("checkItemRewardConditions: 5");
			}
			killer.sendMessage("Reward is awarded only for non Karma players!");
			return false;
		}
		
		if (!((killer.getKarma() == 0) && (killer.getPvpFlag() > 0 && !ExternalConfig.RANK_PVP_REWARD_PK_MODE_ENABLED) || ((ExternalConfig.RANK_PVP_REWARD_PK_KILLER_AWARD) && (killer.getKarma() == 0) && (victim.getKarma() > 0) && !ExternalConfig.RANK_PVP_REWARD_PK_MODE_ENABLED)))
		{
			if (DEBUG_MODE)
			{
				_log.info("checkItemRewardConditions: 6");
			}
			killer.sendMessage("Reward is awarded for kill Flagged players!");
			return false;
		}
		
		if ((ExternalConfig.RANK_PVP_REWARD_MIN_LVL > victim.getLevel()) || (ExternalConfig.RANK_PVP_REWARD_MIN_LVL > killer.getLevel()))
		{
			if (DEBUG_MODE)
			{
				_log.info("checkItemRewardConditions: 7");
			}
			killer.sendMessage("Rewards avaliable on " + ExternalConfig.RANK_PVP_REWARD_MIN_LVL + "+ level.");
			return false;
		}

		return true;
	}
	
	private boolean checkRankPointsConditions(L2PcInstance killer, L2PcInstance victim, int killer_kills_today, String nextRewardTime)
	{
		
		if (!ExternalConfig.RANK_PVP_ENABLED)
			return false;
		
		if (!((ExternalConfig.RANK_PVP_MIN_LVL <= victim.getLevel()) && (ExternalConfig.RANK_PVP_MIN_LVL <= killer.getLevel())))
		{
			if (DEBUG_MODE)
			{
				_log.info("checkRankPointsConditions: 1");
			}
			killer.sendMessage("You or your target have not required level!");
			return false;
		}
		
		if (ExternalConfig.RANK_PVP_PROTECTION_RESET == 0 && killer_kills_today > ExternalConfig.RANK_PVP_PROTECTION && ExternalConfig.RANK_PVP_PROTECTION > 0)
		{
			if (DEBUG_MODE)
			{
				_log.info("checkItemRewardConditions: 2");
			}
			killer.sendMessage("Rank Points has been awarded for kill this player!");
			return false;
		}
		
		if (ExternalConfig.RANK_PVP_PROTECTION_RESET > 0 && killer_kills_today > ExternalConfig.RANK_PVP_PROTECTION && ExternalConfig.RANK_PVP_PROTECTION > 0)
		{
			if (DEBUG_MODE)
			{
				_log.info("checkItemRewardConditions: 3");
			}
			killer.sendMessage("Rank Points has been awarded for kill this player today!");
			
			killer.sendMessage("Next for " + nextRewardTime);

			return false;
		}

		// if PK mode is OFF: (but can kill Flagged players)
		if ((!ExternalConfig.RANK_PVP_PK_MODE_ENABLED) && (killer.getKarma() > 0) && (victim.getPvpFlag() == 0) && victim.getKarma() == 0)
		{
			if (DEBUG_MODE)
			{
				_log.info("checkRankPointsConditions: 5");
			}
			killer.sendMessage("You can't earn Rank Points on innocent players!");
			return false;
		}
		
		// if points for PK kill is OFF:
		if ((!ExternalConfig.RANK_PVP_PK_KILLER_AWARD) && (victim.getKarma() > 0))
		{
			if (DEBUG_MODE)
			{
				_log.info("checkRankPointsConditions: 6");
			}
			killer.sendMessage("No Rank Points for kill player with Karma!");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Return 1 if legal action, 0 illegal.
	 * 
	 * @param killer
	 * @param victim
	 * @return
	 */
	private int checkIsLegalKill(L2PcInstance killer, L2PcInstance victim)
	{
		if ((ExternalConfig.RANK_PVP_LEGAL_MIN_LVL > victim.getLevel()) || (ExternalConfig.RANK_PVP_LEGAL_MIN_LVL > killer.getLevel()))
		{
			if (DEBUG_MODE)
			{
				_log.info("checkIsLegalKill: 1");
			}
			return 0;
		}
		
		if ((!ExternalConfig.RANK_PVP_LEGAL_PK_MODE_ENABLED && killer.getKarma() > 0))
		{
			if (DEBUG_MODE)
			{
				_log.info("checkIsLegalKill: 2");
			}
			return 0;
		}
		
		if (!ExternalConfig.RANK_PVP_LEGAL_PK_KILLER_AWARD && killer.getKarma() == 0 && victim.getKarma() > 0)
		{
			if (DEBUG_MODE)
			{
				_log.info("checkIsLegalKill: 3");
			}
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * Returns true if character is in restricted zone.
	 * 
	 * @param activeChar
	 * @return
	 */
	private boolean isInRestrictedZone(L2PcInstance activeChar)
	{
		for (int i = 0; i < ExternalConfig.RANK_PVP_RESTRICTED_ZONES_IDS.size(); i++)
		{
			try
			{
				if (activeChar.isInsideZone(ExternalConfig.RANK_PVP_RESTRICTED_ZONES_IDS.get(i).byteValue()))
				{
					return true;
				}
			}
			catch (Exception e)
			{
				return false;
			}
		}
		return false;
	}
	
	/**
	 * If returns TRUE is OK (no farming detected).<BR>
	 * Checking: Party, Clan/Ally, IP, self-kill.
	 * 
	 * @param player1
	 * @param player2
	 * @return
	 */
	public static final boolean antiFarmCheck(L2PcInstance player1, L2PcInstance player2)
	{
		
		if (player1 == null || player2 == null)
		{
			return true;
		}
		
		if (player1.equals(player2))
		{
			return false;
		}
		
		// Anti FARM Clan - Ally
		if (ExternalConfig.RANK_PVP_ANTI_FARM_CLAN_ALLY_ENABLED && (player1.getClan() != null && player2.getClan() != null) && (player1.getClan().getClanId() > 0 && player2.getClan().getClanId() > 0 && player1.getClan().getClanId() == player2.getClan().getClanId()) || (player1.getAllyId() > 0 && player2.getAllyId() > 0 && player1.getAllyId() == player2.getAllyId()))
		{
			player1.sendMessage("PvP Farm is not allowed!");
			_log.warning("PVP POINT FARM ATTEMPT, " + player1.getName() + " and " + player2.getName() + ". CLAN or ALLY.");
			return false;
		}
		
		// Anti FARM Party
		if (ExternalConfig.RANK_PVP_ANTI_FARM_PARTY_ENABLED && player1.getParty() != null && player2.getParty() != null && player1.getParty().equals(player2.getParty()))
		{
			player1.sendMessage("PvP Farm is not allowed!");
			_log.warning("PVP POINT FARM ATTEMPT, " + player1.getName() + " and " + player2.getName() + ". SAME PARTY.");
			return false;
		}
		
		// Anti FARM same IP
		if (ExternalConfig.RANK_PVP_ANTI_FARM_IP_ENABLED)
		{
			
			if (player1.getClient() != null && player2.getClient() != null)
			{
				String ip1 = player1.getClient().getConnection().getInetAddress().getHostAddress();
				String ip2 = player2.getClient().getConnection().getInetAddress().getHostAddress();
				
				if (ip1.equals(ip2))
				{
					player1.sendMessage("PvP Farm is not allowed!");
					_log.warning("PVP POINT FARM ATTEMPT: " + player1.getName() + " and " + player2.getName() + ". SAME IP.");
					return false;
				}
			}
		}
		return true;
	}
}