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
package com.l2jhellas.gameserver.network.clientpackets;

import java.nio.BufferUnderflowException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.handler.ChatHandler;
import com.l2jhellas.gameserver.handler.IChatHandler;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.CreatureSay;
import com.l2jhellas.util.database.L2DatabaseFactory;

public final class Say2 extends L2GameClientPacket
{
	private static final String _C__38_SAY2 = "[C] 38 Say2";
	private static Logger _log = Logger.getLogger(Say2.class.getName());
	private static Logger _logChat = Logger.getLogger("chat");

	public final static int ALL = 0;
	public final static int SHOUT = 1; // !
	public final static int TELL = 2;
	public final static int PARTY = 3; // #
	public final static int CLAN = 4;  // @
	public final static int GM = 5;
	public final static int PETITION_PLAYER = 6; // used for petition
	public final static int PETITION_GM = 7; // * used for petition
	public final static int TRADE = 8; // +
	public final static int ALLIANCE = 9; // $
	public final static int ANNOUNCEMENT = 10;
	public final static int PARTYROOM_ALL = 16; // (Red)
	public final static int PARTYROOM_COMMANDER = 15; // (Yellow)
	public final static int HERO_VOICE = 17;

	private final static String[] CHAT_NAMES =
	{
	/** @formatter:off */
		"ALL  ",
		"SHOUT",
		"TELL ",
		"PARTY",
		"CLAN ",
		"GM   ",
		"PETITION_PLAYER",
		"PETITION_GM",
		"TRADE",
		"ALLIANCE",
		"ANNOUNCEMENT", //10
		"WILLCRASHCLIENT:)",
		"FAKEALL?",
		"FAKEALL?",
		"FAKEALL?",
		"PARTYROOM_ALL",
		"PARTYROOM_COMMANDER",
		"HERO_VOICE"
		/** @formatter:on */
	};

	private String _text;
	private int _type;
	private String _target;

	@Override
	protected void readImpl()
	{
		_text = readS();
		try
		{
			_type = readD();
		}
		catch (BufferUnderflowException e)
		{
			_type = CHAT_NAMES.length;
		}
		_target = (_type == TELL) ? readS() : null;
	}

	@Override
	protected void runImpl()
	{
		if (Config.DEBUG)
			_log.info("Say2: Msg Type = '" + _type + "' Text = '" + _text + "'.");

		if (_type < 0 || _type >= CHAT_NAMES.length)
		{
			_log.warning("Say2: Invalid type: " + _type);
			return;
		}

		if (_text.length() >= 100)
		{
			_log.warning("Max input limit exceeded.");
			return;
		}

		L2PcInstance activeChar = getClient().getActiveChar();

		if (activeChar == null)
		{
			_log.warning("[Say2.java] Active Character is null.");
			return;
		}

		if ((activeChar != null) && activeChar instanceof L2PcInstance)
		{
		}

		if (_text.length() > Config.MAX_CHAT_LENGTH)
		{
			_log.info("Say2: Msg Type = '" + _type + "' Text length more than " + Config.MAX_CHAT_LENGTH + " truncate them.");
			_text = _text.substring(0, Config.MAX_CHAT_LENGTH);
			return;
		}

		// Say Filter implementation
		if (Config.USE_SAY_FILTER)
			checkText(activeChar);

		if (activeChar.isChatBanned())
		{
			if (_type == ALL || _type == SHOUT || _type == TRADE || _type == HERO_VOICE)
			{
				activeChar.sendMessage("You may not chat while a chat ban is in effect.");
				return;
			}
		}

		if (activeChar.isInJail() && Config.JAIL_DISABLE_CHAT)
		{
			if (_type == TELL || _type == SHOUT || _type == TRADE || _type == HERO_VOICE)
			{
				activeChar.sendMessage("You can not chat with players outside of the jail.");
				return;
			}
		}

		if (_type == PETITION_PLAYER && activeChar.isGM())
			_type = PETITION_GM;

		if (Config.LOG_CHAT)
		{
			LogRecord record = new LogRecord(Level.INFO, _text);
			record.setLoggerName("chat");

			if (_type == TELL)
				record.setParameters(new Object[]
				{
				CHAT_NAMES[_type], "[" + activeChar.getName() + " to " + _target + "]"
				});
			else
				record.setParameters(new Object[]
				{
				CHAT_NAMES[_type], "[" + activeChar.getName() + "]"
				});

			_logChat.log(record);
		}

		// CreatureSay cs = new CreatureSay(activeChar.getObjectId(),_type, activeChar.getName(), _text);

		L2Object saymode = activeChar.getSayMode();
		if (saymode != null)
		{
			String name = saymode.getName();
			int actor = saymode.getObjectId();
			_type = 0;
			Collection<L2Object> list = saymode.getKnownList().getKnownObjects().values();

			CreatureSay cs = new CreatureSay(actor, _type, name, _text);
			for (L2Object obj : list)
			{
				if ((obj == null) || !(obj instanceof L2Character))
					continue;
				L2Character chara = (L2Character) obj;
				chara.sendPacket(cs);
			}
			return;
		}
		IChatHandler handler = ChatHandler.getInstance().getChatHandler(_type);
		if (handler != null)
		{
			handler.handleChat(_type, activeChar, _target, _text);
		}
	}

	private void checkText(L2PcInstance activeChar)
	{
		if (Config.USE_SAY_FILTER)
		{
			String filteredText = _text;

			for (String pattern : Config.FILTER_LIST)
			{
				filteredText = filteredText.replaceAll("(?i)" + pattern, Config.CHAT_FILTER_CHARS);
			}

			if (Config.CHAT_FILTER_PUNISHMENT.equalsIgnoreCase("jail") && _text != filteredText)
			{
				int punishmentLength = 0;
				if (Config.CHAT_FILTER_PUNISHMENT_PARAM2 == 0)
				{
					punishmentLength = Config.CHAT_FILTER_PUNISHMENT_PARAM1;
				}
				else
				{
					try (Connection con = L2DatabaseFactory.getInstance().getConnection())
					{
						PreparedStatement statement;

						statement = con.prepareStatement("SELECT value FROM account_data WHERE (account_name=?) AND (var='jail_time')");
						statement.setString(1, activeChar.getAccountName());
						ResultSet rset = statement.executeQuery();

						if (!rset.next())
						{
							punishmentLength = Config.CHAT_FILTER_PUNISHMENT_PARAM1;
							PreparedStatement statement1;
							statement1 = con.prepareStatement("INSERT INTO account_data (account_name, var, value) VALUES (?, 'jail_time', ?)");
							statement1.setString(1, activeChar.getAccountName());
							statement1.setInt(2, punishmentLength);
							statement1.executeUpdate();
							statement1.close();
						}
						else
						{
							punishmentLength = rset.getInt("value") + Config.CHAT_FILTER_PUNISHMENT_PARAM2;
							PreparedStatement statement1;
							statement1 = con.prepareStatement("UPDATE account_data SET value=? WHERE (account_name=?) AND (var='jail_time')");
							statement1.setInt(1, punishmentLength);
							statement1.setString(2, activeChar.getAccountName());
							statement1.executeUpdate();
							statement1.close();
						}
						rset.close();
						statement.close();
					}
					catch (SQLException e)
					{
						_log.log(Level.WARNING, getClass().getName() + " Could not check character for chat filter punishment data: " + e);
						if (Config.DEVELOPER)
						{
							e.printStackTrace();
						}
					}
				}
				activeChar.setPunishLevel(L2PcInstance.PunishLevel.JAIL, punishmentLength);
			}
			_text = filteredText;
		}
	}

	@Override
	public String getType()
	{
		return _C__38_SAY2;
	}
}