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
package com.l2jhellas.gameserver.handler;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastMap;
import Extensions.RankSystem.IUserCommandHandlerPvpInfo;

import com.l2jhellas.Config;
import com.l2jhellas.ExternalConfig;
import com.l2jhellas.gameserver.handler.usercommandhandlers.ChannelDelete;
import com.l2jhellas.gameserver.handler.usercommandhandlers.ChannelLeave;
import com.l2jhellas.gameserver.handler.usercommandhandlers.ChannelListUpdate;
import com.l2jhellas.gameserver.handler.usercommandhandlers.ClanPenalty;
import com.l2jhellas.gameserver.handler.usercommandhandlers.ClanWarsList;
import com.l2jhellas.gameserver.handler.usercommandhandlers.DisMount;
import com.l2jhellas.gameserver.handler.usercommandhandlers.Escape;
import com.l2jhellas.gameserver.handler.usercommandhandlers.Loc;
import com.l2jhellas.gameserver.handler.usercommandhandlers.Mount;
import com.l2jhellas.gameserver.handler.usercommandhandlers.OlympiadStat;
import com.l2jhellas.gameserver.handler.usercommandhandlers.PartyInfo;
import com.l2jhellas.gameserver.handler.usercommandhandlers.Time;

public class UserCommandHandler
{
	private static Logger _log = Logger.getLogger(UserCommandHandler.class.getName());

	private static UserCommandHandler _instance;
	private final Map<Integer, IUserCommandHandler> _datatable;

	public static UserCommandHandler getInstance()
	{
		if (_instance == null)
		{
			_instance = new UserCommandHandler();
		}
		return _instance;
	}

	public IUserCommandHandler getUserCommandHandler(int userCommand)
	{
		if (Config.DEBUG)
			_log.log(Level.CONFIG, getClass().getName() + ": getting handler for user command: " + userCommand);
		registerUserCommandHandler(new ClanPenalty());
		registerUserCommandHandler(new ClanWarsList());
		registerUserCommandHandler(new DisMount());
		registerUserCommandHandler(new Escape());
		registerUserCommandHandler(new Loc());
		registerUserCommandHandler(new Mount());
		registerUserCommandHandler(new PartyInfo());
		registerUserCommandHandler(new Time());
		registerUserCommandHandler(new OlympiadStat());
		registerUserCommandHandler(new ChannelLeave());
		registerUserCommandHandler(new ChannelDelete());
		registerUserCommandHandler(new ChannelListUpdate());
		if (ExternalConfig.PVP_INFO_USER_COMMAND_ENABLED && ExternalConfig.PVP_INFO_COMMAND_ENABLED)
			registerUserCommandHandler(new IUserCommandHandlerPvpInfo());

		_log.log(Level.FINE, getClass().getSimpleName() + ": Loaded " + size() + " Handlers in total.");
		return _datatable.get(new Integer(userCommand));
	}

	private UserCommandHandler()
	{
		_datatable = new FastMap<Integer, IUserCommandHandler>();
	}

	public void registerUserCommandHandler(IUserCommandHandler handler)
	{
		int[] ids = handler.getUserCommandList();
		for (int i = 0; i < ids.length; i++)
		{
			if (Config.DEBUG)
				_log.log(Level.CONFIG, getClass().getName() + ": Adding handler for user command " + ids[i]);
			_datatable.put(new Integer(ids[i]), handler);
		}
	}

	public int size()
	{
		return _datatable.size();
	}
}