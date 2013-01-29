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
package com.l2jhellas.gameserver.handler;

import java.util.Map;
import java.util.logging.Logger;

import javolution.util.FastMap;
import Extensions.RankSystem.IUserCommandHandlerPvpInfo;

import com.l2jhellas.Config;
import com.l2jhellas.ExternalConfig;
import com.l2jhellas.gameserver.handler.usercommandhandlers.*;

/**
 * This class ...
 * 
 * @version $Revision: 1.1.2.1.2.5 $ $Date: 2005/03/27 15:30:09 $
 */
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
				_log.fine("Adding handler for user command " + ids[i]);
			_datatable.put(new Integer(ids[i]), handler);
		}
	}
	
	public IUserCommandHandler getUserCommandHandler(int userCommand)
	{
		if (Config.DEBUG)
			_log.fine("getting handler for user command: " + userCommand);
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
		if (ExternalConfig.CUSTOM_PVP_INFO_USER_COMMAND_ENABLED && ExternalConfig.CUSTOM_PVP_INFO_COMMAND_ENABLED)
			registerUserCommandHandler(new IUserCommandHandlerPvpInfo());
		if(Config.DEBUG)
		_log.info("UserCommandHandler: Loaded " + _datatable.size() + " handlers in total.");
		
		return _datatable.get(new Integer(userCommand));
	}
	
	/**
	 * @return
	 */
	public int size()
	{
		return _datatable.size();
	}
}
