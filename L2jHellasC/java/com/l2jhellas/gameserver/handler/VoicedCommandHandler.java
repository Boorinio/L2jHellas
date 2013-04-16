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
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastMap;
import Extensions.RankSystem.IVoicedCommandHandlerPvpInfo;

import com.l2jhellas.Config;
import com.l2jhellas.ExternalConfig;
import com.l2jhellas.gameserver.handler.voicedcommandhandlers.Away;
import com.l2jhellas.gameserver.handler.voicedcommandhandlers.Banking;
import com.l2jhellas.gameserver.handler.voicedcommandhandlers.ChaosCmd;
import com.l2jhellas.gameserver.handler.voicedcommandhandlers.Cl;
import com.l2jhellas.gameserver.handler.voicedcommandhandlers.MailCmd;
import com.l2jhellas.gameserver.handler.voicedcommandhandlers.OnlinePlayers;
import com.l2jhellas.gameserver.handler.voicedcommandhandlers.Premium;
import com.l2jhellas.gameserver.handler.voicedcommandhandlers.QuizCmd;
import com.l2jhellas.gameserver.handler.voicedcommandhandlers.ServerRestartVote;
import com.l2jhellas.gameserver.handler.voicedcommandhandlers.VipTeleport;
import com.l2jhellas.gameserver.handler.voicedcommandhandlers.VoiceInfo;
import com.l2jhellas.gameserver.handler.voicedcommandhandlers.Wedding;
import com.l2jhellas.gameserver.handler.voicedcommandhandlers.ZodiacRegistration;
import com.l2jhellas.gameserver.handler.voicedcommandhandlers.pmoff;
import com.l2jhellas.gameserver.handler.voicedcommandhandlers.stat;
import com.l2jhellas.gameserver.handler.voicedcommandhandlers.tradeoff;
import com.l2jhellas.gameserver.handler.voicedcommandhandlers.version;

public class VoicedCommandHandler
{
	private static Logger _log = Logger.getLogger(VoicedCommandHandler.class.getName());

	private static VoicedCommandHandler _instance;
	private final Map<String, IVoicedCommandHandler> _datatable;
	public static VoicedCommandHandler getInstance()
	{
		if (_instance == null)
		{
			_instance = new VoicedCommandHandler();
		}
		return _instance;
	}

	private VoicedCommandHandler()
	{
		_datatable = new FastMap<String, IVoicedCommandHandler>();
		if (Config.MOD_ALLOW_WEDDING)
			registerVoicedCommandHandler(new Wedding());
		if (Config.ALLOW_AWAY_STATUS)
			registerVoicedCommandHandler(new Away());
		if (Config.ALLOW_TRADEOFF_COMMAND)
			registerVoicedCommandHandler(new tradeoff());
		if (Config.ALLOW_CLAN_LEADER_COMMAND)
			registerVoicedCommandHandler(new Cl());
		if (Config.ALLOW_VIPTELEPORT_COMMAND)
			registerVoicedCommandHandler(new VipTeleport());
		if (Config.ALLOW_INFO_COMMAND)
			registerVoicedCommandHandler(new VoiceInfo());
		if (Config.ALLOW_STAT_COMMAND)
			registerVoicedCommandHandler(new stat());
		if (Config.ALLOW_VERSION_COMMAND)
			registerVoicedCommandHandler(new version());
		if (Config.ALLOW_PLAYERS_REFUSAL)
			registerVoicedCommandHandler(new pmoff());
		if (ExternalConfig.ALLOW_SERVER_RESTART_COMMAND)
			registerVoicedCommandHandler(new ServerRestartVote());
		if (Config.ONLINE_VOICE_ALLOW)
			registerVoicedCommandHandler(new OnlinePlayers());
		if (Config.BANKING_SYSTEM_ENABLED)
			registerVoicedCommandHandler(new Banking());
		if (ExternalConfig.PVP_INFO_COMMAND_ENABLED && ExternalConfig.RANK_PVP_SYSTEM_ENABLED && !ExternalConfig.PVP_INFO_USER_COMMAND_ENABLED)
			registerVoicedCommandHandler(new IVoicedCommandHandlerPvpInfo());
		if (Config.ENABLED_MESSAGE_SYSTEM)
			registerVoicedCommandHandler(new MailCmd());
		if (Config.ENABLED_CHAOS_EVENT)
			registerVoicedCommandHandler(new ChaosCmd());
		if (Config.ZODIAC_ENABLE)
			registerVoicedCommandHandler(new ZodiacRegistration());
		registerVoicedCommandHandler(new Premium());
		registerVoicedCommandHandler(new QuizCmd());

		_log.log(Level.INFO, getClass().getSimpleName() + ": Loaded " + size() + " Handlers in total.");
	}

	public void registerVoicedCommandHandler(IVoicedCommandHandler handler)
	{
		String[] ids = handler.getVoicedCommandList();
		for (int i = 0; i < ids.length; i++)
		{
			if (Config.DEBUG)
				_log.log(Level.CONFIG, getClass().getName() + ": Adding handler for command " + ids[i]);
			_datatable.put(ids[i], handler);
		}
	}

	public IVoicedCommandHandler getVoicedCommandHandler(String voicedCommand)
	{
		String command = voicedCommand;
		if (voicedCommand.indexOf(" ") != -1)
		{
			command = voicedCommand.substring(0, voicedCommand.indexOf(" "));
		}
		if (Config.DEBUG)
			_log.log(Level.CONFIG, getClass().getName() + ": getting handler for command: " + command + " -> " + (_datatable.get(command) != null));
		return _datatable.get(command);
	}

	public int size()
	{
		return _datatable.size();
	}
}
