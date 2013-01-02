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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

import com.l2jhellas.ExternalConfig;

public class Restart
{
	private static Restart _bitching = null;
	protected static final Logger _log = Logger.getLogger(Restart.class.getName());
	private Calendar NextRestart;
	private final SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	
	public static Restart getInstance()
	{
		if (_bitching == null)
			_bitching = new Restart();
		return _bitching;
	}
	
	public String getRestartNextTime()
	{
		if (NextRestart.getTime() != null)
			return format.format(NextRestart.getTime());
		else
			return "Something went wrong";
	}
	
	private Restart()
	{
		
	}
	
	public void StartCalculationOfNextRestartTime()
	{
		_log.info("[Restart System]: System actived");
		try
		{
			Calendar currentTime = Calendar.getInstance();
			Calendar StartTime = null;
			long flush2 = 0, datime = 0;
			int count = 0;
			for (String timeOfDay : ExternalConfig.RESTART_INTERVAL_BY_TIME_OF_DAY)
			{
				StartTime = Calendar.getInstance();
				StartTime.setLenient(true);
				String[] splitTimeOfDay = timeOfDay.split(":");
				StartTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(splitTimeOfDay[0]));
				StartTime.set(Calendar.MINUTE, Integer.parseInt(splitTimeOfDay[1]));
				StartTime.set(Calendar.SECOND, 00);
				if (StartTime.getTimeInMillis() < currentTime.getTimeInMillis())
				{
					StartTime.add(Calendar.DAY_OF_MONTH, 1);
				}
				datime = StartTime.getTimeInMillis() - currentTime.getTimeInMillis();
				if (count == 0)
				{
					flush2 = datime;
					NextRestart = StartTime;
				}
				if (datime < flush2)
				{
					flush2 = datime;
					NextRestart = StartTime;
				}
				count++;
			}
			_log.info("[Restart System]: Next Restart Time: " + NextRestart.getTime().toString());
			ThreadPoolManager.getInstance().scheduleGeneral(new StartRestartTask(), flush2);
		}
		catch (Exception e)
		{
			System.out.println("[Restart System]: The automatic restart system presented error while loading the configs");
		}
	}
	
	class StartRestartTask implements Runnable
	{
		@Override
		public void run()
		{
			_log.info("Start automated restart GameServer.");
			Shutdown.getInstance().autoRestart(ExternalConfig.RESTART_SECONDS);
		}
	}
}