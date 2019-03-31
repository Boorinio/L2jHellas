package com.l2jhellas.gameserver.handlers.admincommandhandlers;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.datatables.xml.AdminData;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;
import com.l2jhellas.util.database.L2DatabaseFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AdminDonator implements IAdminCommandHandler
{
	protected static final Logger _log = Logger.getLogger(AdminDonator.class.getName());
	
	private static String[] _adminCommands =
	{
		"admin_setdonator"
	};

	private L2Object target;
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_setdonator"))
		{
			target = activeChar.getTarget();
			L2PcInstance player = null;
			SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_S2);
			
			if (target != null && target instanceof L2PcInstance)
				player = (L2PcInstance) target;
			else
				player = activeChar;
			
			if (player.isDonator())
			{
				player.setDonator(false);
				player.updateNameTitleColor();
				sm.addString("You are no longer a server donator.");
				AdminData.getInstance().broadcastMessageToGMs("GM " + activeChar.getName() + " removed donator stat of player" + target.getName());
				try (Connection con = L2DatabaseFactory.getInstance().getConnection();
					PreparedStatement statement = con.prepareStatement("SELECT obj_Id FROM characters WHERE char_name=?"))
				{
					statement.setString(1, target.getName());
					try (ResultSet rset = statement.executeQuery())
					{
						int objId = 0;
						if (rset.next())
						{
							objId = rset.getInt(1);
						}
						rset.close();
						statement.close();
						
						if (objId == 0)
						{
							return false;
						}
						try (PreparedStatement statement1 = con.prepareStatement("UPDATE characters SET donator=0 WHERE obj_Id=?"))
						{
							statement1.setInt(1, objId);
							statement1.execute();
						}
					}
				}
				catch (SQLException e)
				{
					_log.warning(AdminDonator.class.getName() + ": could not set donator stats of char:");
					if (Config.DEVELOPER)
						e.printStackTrace();
				}
			}
			else
			{
				player.setDonator(true);
				player.updateNameTitleColor();
				sm.addString("You are now a server donator, congratulations!");
				AdminData.getInstance().broadcastMessageToGMs("GM " + activeChar.getName() + " has given donator stat for player " + target.getName() + ".");
				try (Connection con = L2DatabaseFactory.getInstance().getConnection();
					PreparedStatement statement = con.prepareStatement("SELECT obj_Id FROM characters WHERE char_name=?"))
				{
					statement.setString(1, target.getName());
					try (ResultSet rset = statement.executeQuery())
					{
						int objId = 0;
						if (rset.next())
						{
							objId = rset.getInt(1);
						}
						rset.close();
						statement.close();
						
						if (objId == 0)
						{
							return false;
						}
						
						try (PreparedStatement statement1 = con.prepareStatement("UPDATE characters SET donator=1 WHERE obj_Id=?"))
						{
							statement1.setInt(1, objId);
							statement1.execute();
						}
					}
				}
				catch (SQLException e)
				{
					_log.warning(AdminDonator.class.getName() + ": could not set donator stats of char:");
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