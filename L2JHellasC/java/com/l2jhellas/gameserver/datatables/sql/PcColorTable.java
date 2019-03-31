package com.l2jhellas.gameserver.datatables.sql;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.model.PcColorContainer;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.util.database.L2DatabaseFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class PcColorTable
{
	private static Logger _log = Logger.getLogger(PcColorTable.class.getName());
	
	private static Map<String, PcColorContainer> _pcColors = new HashMap<>();
	
	PcColorTable()
	{
		_pcColors.clear();
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			List<String> deleteNames = new ArrayList<>();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM character_colors");
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				long regTime = rs.getLong("reg_time"), time = rs.getLong("time");
				String charName = rs.getString("char_name");
				int color = rs.getInt("color");
				
				if (time == 0 || regTime + time > System.currentTimeMillis())
				{
					_pcColors.put(charName, new PcColorContainer(color, regTime, time));
				}
				else
					deleteNames.add(charName);
			}
			
			ps.close();
			rs.close();
			
			for (String deleteName : deleteNames)
			{
				PreparedStatement psDel = con.prepareStatement("DELETE FROM character_colors WHERE char_name=?");
				psDel.setString(1, deleteName);
				psDel.executeUpdate();
				psDel.close();
			}
			
			_log.info(PcColorTable.class.getSimpleName() + ": Loaded " + _pcColors.size() + " and " + deleteNames.size() + " expired deleted!");
			deleteNames.clear();
		}
		catch (Exception e)
		{
			_log.warning(PcColorTable.class.getName() + ": Error while loading data from DB!");
			if (Config.DEVELOPER)
				e.printStackTrace();
		}
	}
	
	public synchronized static void process(L2PcInstance activeChar)
	{
		PcColorContainer colorContainer = _pcColors.get(activeChar.getName());
		
		if (colorContainer == null)
			return;
		
		long time = colorContainer.getTime();
		
		if (time == 0 || colorContainer.getRegTime() + time > System.currentTimeMillis())
			activeChar.getAppearance().setNameColor(colorContainer.getColor());
		else
			delete(activeChar.getName());
	}
	
	public synchronized void add(L2PcInstance activeChar, int color, long regTime, long time)
	{
		String charName = activeChar.getName();
		PcColorContainer colorContainer = _pcColors.get(charName);
		
		if (colorContainer != null)
		{
			if (!delete(charName))
				return;
		}
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement psIns = con.prepareStatement("INSERT INTO character_colors VALUES (?,?,?,?)");
			psIns.setString(1, charName);
			psIns.setInt(2, color);
			psIns.setLong(3, regTime);
			psIns.setLong(4, time);
			psIns.executeUpdate();
			psIns.close();
			_pcColors.put(activeChar.getName(), new PcColorContainer(color, regTime, time));
			activeChar.getAppearance().setNameColor(color);
			activeChar.broadcastUserInfo();
			activeChar.sendMessage("Your name color has been changed by a GM!");
		}
		catch (Exception e)
		{
			_log.warning(PcColorTable.class.getName() + ": Error while add " + charName + "'s color to DB!");
			if (Config.DEVELOPER)
				e.printStackTrace();
		}
	}
	
	public synchronized static boolean delete(String charName)
	{
		PcColorContainer colorContainer = _pcColors.get(charName);
		
		if (colorContainer == null)
			return false;
		
		colorContainer = null;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement psDel = con.prepareStatement("DELETE FROM character_colors WHERE char_name=?");
			psDel.setString(1, charName);
			psDel.executeUpdate();
			psDel.close();
			_pcColors.remove(charName);
		}
		catch (Exception e)
		{
			_log.warning(PcColorTable.class.getSimpleName() + ": PcColorTable: Error while delete " + charName + "'s color from DB!");
			if (Config.DEVELOPER)
				e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static PcColorTable getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final PcColorTable _instance = new PcColorTable();
	}
}