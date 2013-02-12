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

import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.util.Util;

/**
 * @author Setekh - Modified By Urbanhack
 */
public class Engine
{
	protected static final Logger _log = Logger.getLogger(Engine.class.getName());

	public static void StartUp()
	{
		// checks for enabled auto tvt start up.
		// -------------------------------------
		if (Config.TVT_AUTO_STARTUP_ON_BOOT)
		{
			_log.info("TVT Engine Started");
			_log.info("TVT First event in: " + Config.FIRST_TVT_DELAY + " Minutes");
			ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
			{
				// loads data and start a new event.
				// -------------------------------------
				@Override
				public void run()
				{
					TvT.loadData();
					TvT.autoEvent();
				}
				// Config.FIRST_TVT_DELAY minutes till first event start it.
				// -------------------------------------
			}, Util.convertMinutesToMiliseconds(Config.FIRST_TVT_DELAY));
		}
	}
}
