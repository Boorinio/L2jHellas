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
package com.l2jhellas.gameserver;

import java.util.logging.Level;
import java.util.logging.Logger;

import Extensions.RankSystem.PvpTable;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.instancemanager.CastleManorManager;
import com.l2jhellas.gameserver.instancemanager.CursedWeaponsManager;
import com.l2jhellas.gameserver.instancemanager.GrandBossManager;
import com.l2jhellas.gameserver.instancemanager.ItemsOnGroundManager;
import com.l2jhellas.gameserver.instancemanager.QuestManager;
import com.l2jhellas.gameserver.instancemanager.RaidBossSpawnManager;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.entity.Olympiad;
import com.l2jhellas.gameserver.model.entity.engines.Hitman;
import com.l2jhellas.gameserver.network.L2GameClient;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.gameserverpackets.ServerStatus;
import com.l2jhellas.gameserver.network.serverpackets.ServerClose;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;
import com.l2jhellas.util.Broadcast;
import com.l2jhellas.util.database.L2DatabaseFactory;

/**
 * This class provides the functions for shutting down and restarting the server
 * It closes all open client connections and saves all data.
 */
public class Shutdown extends Thread
{
	private static Logger _log = Logger.getLogger(Shutdown.class.getName());
	private static Shutdown _instance;
	private static Shutdown _counterInstance = null;

	private int _secondsShut;

	private int _shutdownMode;
	public static final int SIGTERM = 0;
	public static final int GM_SHUTDOWN = 1;
	public static final int GM_RESTART = 2;
	public static final int ABORT = 3;
	private static final String[] MODE_TEXT =
	{
	"SIGTERM", "shutting down", "restarting", "aborting"
	};

	/**
	 * This function starts a shutdown countdown from Telnet
	 * (Copied from function startShutdown())
	 * 
	 * @param ip
	 *        IP Which issued shutdown command
	 * @param seconds
	 *        seconds until shutdown
	 * @param restart
	 *        true if the server will restart after shutdown
	 */
	public void startTelnetShutdown(String IP, int seconds, boolean restart)
	{
		Announcements _an = Announcements.getInstance();
		_log.log(Level.WARNING, getClass().getSimpleName() + ": IP: " + IP + " issued shutdown command. " + MODE_TEXT[_shutdownMode] + " in " + seconds + " seconds!");
		// _an.announceToAll("Server is " + _modeText[shutdownMode] + " in "+seconds+ " seconds!");

		if (restart)
		{
			_shutdownMode = GM_RESTART;
		}
		else
		{
			_shutdownMode = GM_SHUTDOWN;
		}

		if (_shutdownMode > 0)
		{
			_an.announceToAll("Attention players!");
			_an.announceToAll("Server " + Config.ABORT_RR + " aborts" + MODE_TEXT[_shutdownMode] + " in " + seconds + " seconds!");
			if (_shutdownMode == 1 || _shutdownMode == 2)
			{
				_an.announceToAll("Please, avoid to use Gatekeepers/SoE");
				_an.announceToAll("during server " + MODE_TEXT[_shutdownMode] + " procedure.");
			}
		}

		if (_counterInstance != null)
		{
			_counterInstance._abort();
		}
		_counterInstance = new Shutdown(seconds, restart);
		_counterInstance.start();
	}

	/**
	 * This function aborts a running countdown
	 *
	 * @param IP
	 *        IP Which Issued shutdown command
	 */
	public void telnetAbort(String IP)
	{
		Announcements _an = Announcements.getInstance();
		_log.log(Level.WARNING, getClass().getSimpleName() + ": IP: " + IP + " issued shutdown ABORT. " + MODE_TEXT[_shutdownMode] + " has been stopped!");
		_an.announceToAll("Server " + Config.ABORT_RR + " aborts " + MODE_TEXT[_shutdownMode] + " and continues normal operation!");

		if (_counterInstance != null)
		{
			_counterInstance._abort();
		}
	}

	/**
	 * Default constructor is only used internal to create the shutdown-hook instance
	 */
	public Shutdown()
	{
		_secondsShut = -1;
		_shutdownMode = SIGTERM;
	}

	/**
	 * This creates a countdown instance of Shutdown.
	 *
	 * @param seconds
	 *        how many seconds until shutdown
	 * @param restart
	 *        true is the server shall restart after shutdown
	 */
	public Shutdown(int seconds, boolean restart)
	{
		if (seconds < 0)
		{
			seconds = 0;
		}
		_secondsShut = seconds;
		if (restart)
		{
			_shutdownMode = GM_RESTART;
		}
		else
		{
			_shutdownMode = GM_SHUTDOWN;
		}
	}

	public void autoRestart(int time)
	{
		_secondsShut = time;
		countdown();
		_shutdownMode = GM_RESTART;
		_instance.setMode(GM_RESTART);
		System.exit(2);
	}

	/**
	 * get the shutdown-hook instance
	 * the shutdown-hook instance is created by the first call of this function,
	 * but it has to be registered externally.
	 *
	 * @return instance of Shutdown, to be used as shutdown hook
	 */
	public static Shutdown getInstance()
	{
		if (_instance == null)
		{
			_instance = new Shutdown();
		}

		return _instance;
	}

	/**
	 * this function is called, when a new thread starts
	 * if this thread is the thread of getInstance, then this is the shutdown hook
	 * and we save all data and disconnect all clients.
	 * after this thread ends, the server will completely exit
	 * if this is not the thread of getInstance, then this is a countdown thread.
	 * we start the countdown, and when we finished it, and it was not aborted,
	 * we tell the shutdown-hook why we call exit, and then call exit
	 * when the exit status of the server is 1, startServer.sh / startServer.bat
	 * will restart the server.
	 */
	@Override
	public void run()
	{
		if (this == _instance)
		{
			// ensure all services are stopped
			try
			{
				GameTimeController.getInstance().stopTimer();
			}
			catch (Throwable t)
			{
				// ignore
			}

			// stop all thread polls
			try
			{
				ThreadPoolManager.getInstance().shutdown();
			}
			catch (Throwable t)
			{
				// ignore
			}

			// last bye-bye, save all data and quit this server
			// logging doesn't work here :(
			saveData();

			try
			{
				LoginServerThread.getInstance().interrupt();
			}
			catch (Throwable t)
			{
				// ignore
			}

			// saveData sends messages to exit players, so shutdown selector
			// after it
			try
			{
				GameServer.gameServer.getSelectorThread().shutdown();
				GameServer.gameServer.getSelectorThread().setDaemon(true);
			}
			catch (Throwable t)
			{
				// ignore
			}

			// commit data, last chance
			try
			{
				L2DatabaseFactory.getInstance().shutdown();
			}
			catch (Throwable t)
			{

			}

			// server will quit, when this function ends.
			if (_instance._shutdownMode == GM_RESTART)
			{
				Runtime.getRuntime().halt(2);
			}
			else
			{
				Runtime.getRuntime().halt(0);
			}
		}
		else
		{
			// GM shutdown: send warnings and then call exit to start shutdown
			// sequence
			countdown();
			// last point where logging is operational.
			_log.log(Level.WARNING, getClass().getSimpleName() + ": GM shutdown countdown is over. " + MODE_TEXT[_shutdownMode] + " NOW!");
			switch (_shutdownMode)
			{
				case GM_SHUTDOWN:
					_instance.setMode(GM_SHUTDOWN);
					System.exit(0);
				break;
				case GM_RESTART:
					_instance.setMode(GM_RESTART);
					System.exit(2);
				break;
			}
		}
	}

	/**
	 * This functions starts a shutdown countdown
	 *
	 * @param activeChar
	 *        GM who issued the shutdown command
	 * @param seconds
	 *        seconds until shutdown
	 * @param restart
	 *        true if the server will restart after shutdown
	 */
	public void startShutdown(L2PcInstance activeChar, int seconds, boolean restart)
	{
		Announcements _an = Announcements.getInstance();
		_log.log(Level.WARNING, getClass().getSimpleName() + ": GM: " + activeChar.getName() + "(" + activeChar.getObjectId() + ") issued shutdown command. " + MODE_TEXT[_shutdownMode] + " in " + seconds + " seconds!");

		if (restart)
		{
			_shutdownMode = GM_RESTART;
		}
		else
		{
			_shutdownMode = GM_SHUTDOWN;
		}

		if (_shutdownMode > 0)
		{
			_an.announceToAll("Attention players!");
			_an.announceToAll("Server " + Config.ABORT_RR + " is " + MODE_TEXT[_shutdownMode] + " in " + seconds + " seconds!");
			if (_shutdownMode == 1 || _shutdownMode == 2)
			{
				_an.announceToAll("Please, avoid to use Gatekeepers/SoE");
				_an.announceToAll("during server " + MODE_TEXT[_shutdownMode] + " procedure.");
			}
		}

		if (_counterInstance != null)
		{
			_counterInstance._abort();
		}

		// the main instance should only run for shutdown hook, so we start a new instance
		_counterInstance = new Shutdown(seconds, restart);
		_counterInstance.start();
	}

	/**
	 * This function aborts a running countdown
	 *
	 * @param activeChar
	 *        GM who issued the abort command
	 */
	public void abort(L2PcInstance activeChar)
	{
		Announcements _an = Announcements.getInstance();
		_log.log(Level.WARNING, getClass().getSimpleName() + ": GM " + activeChar.getName() + "(" + activeChar.getObjectId() + ") issued shutdown ABORT. " + MODE_TEXT[_shutdownMode] + " has been stopped!");
		_an.announceToAll("Server " + Config.ABORT_RR + " aborts " + MODE_TEXT[_shutdownMode] + " and continues normal operation!");

		if (_counterInstance != null)
		{
			_counterInstance._abort();
		}
	}

	/**
	 * set the shutdown mode
	 *
	 * @param mode
	 *        what mode shall be set
	 */
	private void setMode(int mode)
	{
		_shutdownMode = mode;
	}

	/**
	 * set shutdown mode to ABORT
	 */
	private void _abort()
	{
		_shutdownMode = ABORT;
	}

	/**
	 * this counts the countdown and reports it to all players
	 * countdown is aborted if mode changes to ABORT
	 */
	private void countdown()
	{
		Announcements _an = Announcements.getInstance();

		try
		{
			while (_secondsShut > 0)
			{

				switch (_secondsShut)
				{
					case 540:
						_an.announceToAll("Server " + Config.ABORT_RR + " is " + MODE_TEXT[_shutdownMode] + " in 9 minutes.");
					break;
					case 480:
						_an.announceToAll("Server " + Config.ABORT_RR + " is " + MODE_TEXT[_shutdownMode] + " in 8 minutes.");
					break;
					case 420:
						_an.announceToAll("Server " + Config.ABORT_RR + " is " + MODE_TEXT[_shutdownMode] + " in 7 minutes.");
					break;
					case 360:
						_an.announceToAll("Server " + Config.ABORT_RR + " is " + MODE_TEXT[_shutdownMode] + " in 6 minutes.");
					break;
					case 300:
						_an.announceToAll("Server " + Config.ABORT_RR + " is " + MODE_TEXT[_shutdownMode] + " in 5 minutes.");
					break;
					case 240:
						_an.announceToAll("Server " + Config.ABORT_RR + " is " + MODE_TEXT[_shutdownMode] + " in 4 minutes.");
					break;
					case 180:
						_an.announceToAll("Server " + Config.ABORT_RR + " is " + MODE_TEXT[_shutdownMode] + " in 3 minutes.");
					break;
					case 120:
						_an.announceToAll("Server " + Config.ABORT_RR + " is " + MODE_TEXT[_shutdownMode] + " in 2 minutes.");
					break;
					case 60:
						LoginServerThread.getInstance().setServerStatus(ServerStatus.STATUS_DOWN); // avoids new players from logging in
						_an.announceToAll("Server " + Config.ABORT_RR + " is " + MODE_TEXT[_shutdownMode] + " in 1 minute.");
					break;
					case 30:
					{
						_an.announceToAll("Server " + MODE_TEXT[_shutdownMode] + " will be shut down in 30 seconds, please log off!");
						SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.THE_SERVER_WILL_BE_COMING_DOWN_IN_S1_SECONDS);
						sm.addString("30");
						Broadcast.toAllOnlinePlayers(sm);
						break;
					}
					case 10:
						_an.announceToAll("Server " + Config.ABORT_RR + " is " + MODE_TEXT[_shutdownMode] + " in 10 seconds, please delog NOW !");
					case 3:
						_an.announceToAll("Server " + Config.ABORT_RR + " is " + MODE_TEXT[_shutdownMode] + " in 3 seconds, the end has come!!");
					break;
				}

				_secondsShut--;

				int delay = 1000; // milliseconds
				Thread.sleep(delay);

				if (_shutdownMode == ABORT)
				{
					break;
				}
			}
		}
		catch (InterruptedException e)
		{
			// this will never happen
		}
	}

	/**
	 * this sends a last bye-bye, disconnects all players and saves data
	 */
	private void saveData()
	{
		Announcements _an = Announcements.getInstance();
		switch (_shutdownMode)
		{
			case SIGTERM:
				System.err.println("SIGTERM received. Shutting down NOW!");
			break;
			case GM_SHUTDOWN:
				System.err.println("GM shutdown received. Shutting down NOW!");
			break;
			case GM_RESTART:
				System.err.println("GM restart received. Restarting NOW!");
			break;

		}
		if (Config.ACTIVATE_POSITION_RECORDER)
		{
			Universe.getInstance().implode(true);
		}
		try
		{
			_an.announceToAll("Server " + Config.ABORT_RR + " is " + MODE_TEXT[_shutdownMode] + " NOW!");
		}
		catch (Throwable t)
		{
			System.out.println("Failed to " + MODE_TEXT[_shutdownMode] + t);
		}
		TimeCounter tc = new TimeCounter();
		TimeCounter tc1 = new TimeCounter();
		// we can't abort shutdown anymore, so i removed the "if"
		disconnectAllCharacters();

		// Seven Signs data is now saved along with Festival data.
		if (!SevenSigns.getInstance().isSealValidationPeriod())
		{
			SevenSignsFestival.getInstance().saveFestivalData(false);
		}

		// Save Seven Signs data before closing. :)
		SevenSigns.getInstance().saveSevenSignsData(null, true);

		// Save all raidboss and GrandBoss status ^_^
		RaidBossSpawnManager.getInstance().cleanUp();
		System.out.println("All RaidBoss info saved in (" + tc.getEstimatedTimeAndRestartCounter() + "ms).");
		GrandBossManager.getInstance().cleanUp();
		System.out.println("All GrandBoss info saved in (" + tc.getEstimatedTimeAndRestartCounter() + "ms).");
		TradeController.getInstance().dataCountStore();
		System.out.println("All Items saved in (" + tc.getEstimatedTimeAndRestartCounter() + "ms).");
		try
		{
			Olympiad.getInstance().save();
		}
		catch (Exception e)
		{
			System.err.println("Failed to save olympiad." + e);
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
		System.out.println("All Olympiad Data saved in (" + tc.getEstimatedTimeAndRestartCounter() + "ms).");
		// Save Cursed Weapons data before closing.
		CursedWeaponsManager.getInstance().saveData();
		System.out.println("All Cursed weapons Data saved in (" + tc.getEstimatedTimeAndRestartCounter() + "ms).");
		// Save all manor data
		CastleManorManager.getInstance().save();
		System.out.println("All Manor Data saved in (" + tc.getEstimatedTimeAndRestartCounter() + "ms).");
		// Save all global (non-player specific) Quest data that needs to
		// persist after reboot
		QuestManager.getInstance().save();
		// Start Hitman Event.
		if (Hitman.start())
		{
			Hitman.getInstance().save();
			System.out.println("All Hitman Lists saved in (" + tc.getEstimatedTimeAndRestartCounter() + "ms).");
		}

		// Rank PvP System by Masterio:
		if (Config.RANK_PVP_SYSTEM_ENABLED)
		{
			PvpTable.getInstance().updateDB();
			System.out.println("All Rank System Data saved in (" + tc.getEstimatedTimeAndRestartCounter() + "ms).");
		}

		// Save items on ground before closing
		if (Config.SAVE_DROPPED_ITEM)
		{
			ItemsOnGroundManager.getInstance().saveInDb();
			ItemsOnGroundManager.getInstance().cleanUp();
			System.out.println("All items on ground saved in (" + tc.getEstimatedTimeAndRestartCounter() + "ms).");
		}
		System.out.println("Data saved. All players disconnected, shutting down.(" + tc.getEstimatedTimeAndRestartCounter() + "ms).");
		System.out.println("The server has been successfully shut down in " + tc1.getEstimatedTime() + " ms.");

		try
		{
			int delay = 5000;
			Thread.sleep(delay);
		}
		catch (InterruptedException e)
		{
			// never happens :p
		}
	}

	/**
	 * A simple class used to track down the estimated time of method
	 * executions.
	 * Once this class is created, it saves the start time, and when you want to
	 * get
	 * the estimated time, use the getEstimatedTime() method.
	 */
	private static final class TimeCounter
	{
		private long _startTime;

		private TimeCounter()
		{
			restartCounter();
		}

		private void restartCounter()
		{
			_startTime = System.currentTimeMillis();
		}

		private long getEstimatedTimeAndRestartCounter()
		{
			final long toReturn = System.currentTimeMillis() - _startTime;
			restartCounter();
			return toReturn;
		}

		private long getEstimatedTime()
		{
			return System.currentTimeMillis() - _startTime;
		}
	}

	/**
	 * this disconnects all clients from the server
	 */
	private void disconnectAllCharacters()
	{
		for (L2PcInstance player : L2World.getAllPlayers())
		{
			// Logout Character
			try
			{
				L2GameClient.saveCharToDisk(player);
				ServerClose ql = new ServerClose();
				player.sendPacket(ql);
			}
			catch (Throwable t)
			{
			}
		}
		try
		{
			wait(1000);
		}
		catch (Throwable t)
		{
			_log.log(Level.INFO, "", t);
		}

		for (L2PcInstance player : L2World.getAllPlayers())
		{
			try
			{
				player.closeNetConnection();
			}
			catch (Throwable t)
			{
				// just to make sure we try to kill the connection
			}
		}
	}
}