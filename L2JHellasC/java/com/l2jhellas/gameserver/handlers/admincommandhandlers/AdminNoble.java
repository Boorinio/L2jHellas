package com.l2jhellas.gameserver.handlers.admincommandhandlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.datatables.xml.AdminData;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;
import com.l2jhellas.util.database.L2DatabaseFactory;

public class AdminNoble implements IAdminCommandHandler
{
	protected static final Logger _log = Logger.getLogger(AdminNoble.class.getName());
	
	private static String[] _adminCommands =
	{
		"admin_setnoble"
	};

	private L2Object target = null;
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_setnoble"))
		{
			target = activeChar.getTarget();
			L2PcInstance player = null;
			SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_S2);
			if (target != null && target instanceof L2PcInstance)
				player = (L2PcInstance) target;
			else
				player = activeChar;
			
			if (player.isNoble())
			{
				player.setNoble(false);
				sm.addString("You are no longer a server noble.");
				AdminData.getInstance().broadcastMessageToGMs("GM " + activeChar.getName() + " removed noble stat of player" + target.getName());
				try (Connection con = L2DatabaseFactory.getInstance().getConnection();
					PreparedStatement statement = con.prepareStatement("SELECT obj_Id FROM characters WHERE char_name=?"))
				{
					statement.setString(1, target.getName());
					try (ResultSet rset = statement.executeQuery())
					{
						int objId = 0;
						if (rset.next())
							objId = rset.getInt(1);
						if (objId == 0)
							return false;
						try (PreparedStatement statement1 = con.prepareStatement("UPDATE characters SET nobless=0 WHERE obj_Id=?"))
						{
							statement1.setInt(1, objId);
							statement1.execute();
						}
					}
					
				}
				catch (Exception e)
				{
					_log.warning(AdminNoble.class.getName() + ": could not set noble stats of char:");
					if (Config.DEVELOPER)
						e.printStackTrace();
				}
			}
			else
			{
				player.setNoble(true);
				sm.addString("You are now a server noble, congratulations!");
				AdminData.getInstance().broadcastMessageToGMs("GM " + activeChar.getName() + " has given noble stat for player " + target.getName() + ".");
				try (Connection con = L2DatabaseFactory.getInstance().getConnection();
					PreparedStatement statement = con.prepareStatement("SELECT obj_Id FROM characters WHERE char_name=?"))
				{
					statement.setString(1, target.getName());
					try (ResultSet rset = statement.executeQuery())
					{
						int objId = 0;
						if (rset.next())
							objId = rset.getInt(1);
						if (objId == 0)
							return false;
						try (PreparedStatement statement1 = con.prepareStatement("UPDATE characters SET nobless=1 WHERE obj_Id=?"))
						{
							statement1.setInt(1, objId);
							statement1.execute();
						}
					}
				}
				catch (Exception e)
				{
					_log.warning(AdminNoble.class.getName() + ": could not set noble stats of char:");
					if (Config.DEVELOPER)
						e.printStackTrace();
				}
			}
			player.sendPacket(sm);
			player.broadcastUserInfo();
		}
		return false;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return _adminCommands;
	}
}