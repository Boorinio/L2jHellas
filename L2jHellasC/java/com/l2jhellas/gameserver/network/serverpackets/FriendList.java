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
package com.l2jhellas.gameserver.network.serverpackets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.util.database.L2DatabaseFactory;

/**
 * Support for "Chat with Friends" dialog.<BR>
 * <BR>
 * Format: ch (hdSdh)<BR>
 * h: Total Friend Count<BR>
 * <BR>
 * h: Unknown<BR>
 * d: Player Object ID<BR>
 * S: Friend Name<BR>
 * d: Online/Offline<BR>
 * h: Unknown<BR>
 * <BR>
 * 
 * @author Tempy
 */
public class FriendList extends L2GameServerPacket
{
	private static Logger _log = Logger.getLogger(FriendList.class.getName());
	private static final String _S__FA_FRIENDLIST = "[S] FA FriendList";

	private final L2PcInstance _activeChar;

	public FriendList(L2PcInstance character)
	{
		_activeChar = character;
	}

	@Override
	protected final void writeImpl()
	{
		if (_activeChar == null)
			return;

		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			String sqlQuery = "SELECT friend_id, friend_name FROM character_friends WHERE char_id=" + _activeChar.getObjectId() + " ORDER BY friend_name ASC";

			PreparedStatement statement = con.prepareStatement(sqlQuery);
			ResultSet rset = statement.executeQuery(sqlQuery);

			// Obtain the total number of friend entries for this player.
			rset.last();

			if (rset.getRow() > 0)
			{
				writeC(0xfa);
				writeH(rset.getRow());

				rset.beforeFirst();

				while (rset.next())
				{
					int friendId = rset.getInt("friend_id");
					String friendName = rset.getString("friend_name");

					if (friendId == _activeChar.getObjectId())
						continue;

					L2PcInstance friend = L2World.getInstance().getPlayer(friendName);

					writeH(0); // ??
					writeD(friendId);
					writeS(friendName);

					if (friend == null)
						writeD(0); // offline
					else
						writeD(1); // online

					writeH(0); // ??
				}
			}

			rset.close();
			statement.close();
		}
		catch (Exception e)
		{
			_log.warning("Error found in " + _activeChar.getName() + "'s FriendList: " + e);
		}
	}

	@Override
	public String getType()
	{
		return _S__FA_FRIENDLIST;
	}
}