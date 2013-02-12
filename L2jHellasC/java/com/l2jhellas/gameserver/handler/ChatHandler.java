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

import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastMap;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.handler.chathandlers.ChatAll;
import com.l2jhellas.gameserver.handler.chathandlers.ChatAlliance;
import com.l2jhellas.gameserver.handler.chathandlers.ChatClan;
import com.l2jhellas.gameserver.handler.chathandlers.ChatHeroVoice;
import com.l2jhellas.gameserver.handler.chathandlers.ChatParty;
import com.l2jhellas.gameserver.handler.chathandlers.ChatPartyRoomAll;
import com.l2jhellas.gameserver.handler.chathandlers.ChatPartyRoomCommander;
import com.l2jhellas.gameserver.handler.chathandlers.ChatPetition;
import com.l2jhellas.gameserver.handler.chathandlers.ChatShout;
import com.l2jhellas.gameserver.handler.chathandlers.ChatTell;
import com.l2jhellas.gameserver.handler.chathandlers.ChatTrade;

/**
 * This class handles all chat handlers
 *
 * @author durgus
 */
public class ChatHandler
{
	private static Logger _log = Logger.getLogger(ChatHandler.class.getName());

	private static ChatHandler _instance;

	private final FastMap<Integer, IChatHandler> _datatable;

	public static ChatHandler getInstance()
	{
		if (_instance == null)
		{
			_instance = new ChatHandler();
		}
		return _instance;
	}

	/**
	 * Singleton constructor
	 */
	private ChatHandler()
	{
		_datatable = new FastMap<Integer, IChatHandler>();
		registerChatHandler(new ChatAll());
		registerChatHandler(new ChatAlliance());
		registerChatHandler(new ChatClan());
		registerChatHandler(new ChatHeroVoice());
		registerChatHandler(new ChatParty());
		registerChatHandler(new ChatPartyRoomAll());
		registerChatHandler(new ChatPartyRoomCommander());
		registerChatHandler(new ChatPetition());
		registerChatHandler(new ChatShout());
		registerChatHandler(new ChatTell());
		registerChatHandler(new ChatTrade());
		_log.log(Level.INFO, getClass().getSimpleName() + ": Loaded " + size() + " Handlers in total.");
	}

	/**
	 * Register a new chat handler
	 *
	 * @param handler
	 */
	public void registerChatHandler(IChatHandler handler)
	{
		int[] ids = handler.getChatTypeList();
		for (int i = 0; i < ids.length; i++)
		{
			if (Config.DEBUG)
				_log.fine("Adding handler for chat type " + ids[i]);
			_datatable.put(ids[i], handler);
		}
	}

	/**
	 * Get the chat handler for the given chat type
	 *
	 * @param chatType
	 * @return
	 */
	public IChatHandler getChatHandler(int chatType)
	{
		return _datatable.get(chatType);
	}

	/**
	 * @return the size()
	 */
	public int size()
	{
		return _datatable.size();
	}
}
