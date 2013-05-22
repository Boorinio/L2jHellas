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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;
import com.l2jhellas.util.database.L2DatabaseFactory;

public final class RequestFriendDel extends L2GameClientPacket
{
	private static Logger _log = Logger.getLogger(RequestFriendDel.class.getName());
	private static final String _C__61_REQUESTFRIENDDEL = "[C] 61 RequestFriendDel";

	private String _name;

	@Override
	protected void readImpl()
	{
		_name = readS();
	}

	@Override
	protected void runImpl()
	{
		SystemMessage sm;
		L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar == null)
			return;

		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			L2PcInstance friend = L2World.getInstance().getPlayer(_name);
			PreparedStatement statement;
			ResultSet rset;
			if (friend != null)
			{
				statement = con.prepareStatement("SELECT friend_id FROM character_friends WHERE char_id=? AND friend_id=?");
				statement.setInt(1, activeChar.getObjectId());
				statement.setInt(2, friend.getObjectId());
				rset = statement.executeQuery();
				if (!rset.next())
				{
					statement.close();
					// Player is not in your friendlist
					sm = new SystemMessage(SystemMessageId.S1_NOT_ON_YOUR_FRIENDS_LIST);
					sm.addString(_name);
					activeChar.sendPacket(sm);
					sm = null;
					return;
				}
			}
			else
			{
				// FIXME: Nightwolf check this selection
				statement = con.prepareStatement("SELECT friend_id FROM character_friends, characters WHERE char_id=? AND friend_id=obj_id AND char_name=?");
				statement.setInt(1, activeChar.getObjectId());
				statement.setString(2, _name);
				rset = statement.executeQuery();
				if (!rset.next())
				{
					statement.close();
					// Player is not in your friendlist
					sm = new SystemMessage(SystemMessageId.S1_NOT_ON_YOUR_FRIENDS_LIST);
					sm.addString(_name);
					activeChar.sendPacket(sm);
					sm = null;
					return;
				}
			}

			int objectId = rset.getInt("friend_id");
			statement.close();
			rset.close();

			statement = con.prepareStatement("DELETE FROM character_friends WHERE char_id=? AND friend_id=?");
			statement.setInt(1, activeChar.getObjectId());
			statement.setInt(2, objectId);
			statement.execute();
			// Player deleted from your friendlist
			sm = new SystemMessage(SystemMessageId.S1_HAS_BEEN_DELETED_FROM_YOUR_FRIENDS_LIST);
			sm.addString(_name);
			activeChar.sendPacket(sm);
			sm = null;

			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "could not del friend objectid: ", e);
		}
	}

	@Override
	public String getType()
	{
		return _C__61_REQUESTFRIENDDEL;
	}
}