package com.l2jhellas.gameserver.datatables.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.model.L2MaxPolyModel;
import com.l2jhellas.gameserver.templates.StatsSet;
import com.l2jhellas.util.database.L2DatabaseFactory;

public class PolymporphTable
{
	private final Logger _log = Logger.getLogger(PolymporphTable.class.getName());
	
	private final Map<Integer, L2MaxPolyModel> _map = new HashMap<>();
	
	private final String SQL_SELECT = "SELECT * FROM max_poly";
	
	public PolymporphTable()
	{
		_map.clear();
		load();
	}
	
	private void load()
	{
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement st = con.prepareStatement(SQL_SELECT);
			ResultSet rs = st.executeQuery();
			restore(rs);
			rs.close();
			st.close();
		}
		catch (Exception e)
		{
			_log.warning(PolymporphTable.class.getName() + ": Error loading DB ");
			if (Config.DEVELOPER)
				e.printStackTrace();
		}
	}
	
	private void restore(ResultSet data) throws SQLException
	{
		StatsSet set = new StatsSet();
		
		while (data.next())
		{
			set.set("name", data.getString("name"));
			set.set("title", data.getString("title"));
			set.set("sex", data.getInt("sex"));
			set.set("hair", data.getInt("hair"));
			set.set("hairColor", data.getInt("hairColor"));
			set.set("face", data.getInt("face"));
			set.set("classId", data.getInt("classId"));
			set.set("npcId", data.getInt("npcId"));
			set.set("weaponIdRH", data.getInt("weaponIdRH"));
			set.set("weaponIdLH", data.getInt("weaponIdLH"));
			set.set("weaponIdEnc", data.getInt("weaponIdEnc"));
			set.set("armorId", data.getInt("armorId"));
			set.set("head", data.getInt("head"));
			set.set("hats", data.getInt("hats"));
			set.set("faces", data.getInt("faces"));
			set.set("chest", data.getInt("chest"));
			set.set("legs", data.getInt("legs"));
			set.set("gloves", data.getInt("gloves"));
			set.set("feet", data.getInt("feet"));
			set.set("abnormalEffect", data.getInt("abnormalEffect"));
			set.set("pvpFlag", data.getInt("pvpFlag"));
			set.set("karma", data.getInt("karma"));
			set.set("recom", data.getInt("recom"));
			set.set("clan", data.getInt("clan"));
			set.set("isHero", data.getInt("isHero"));
			set.set("pledge", data.getInt("pledge"));
			set.set("nameColor", data.getInt("nameColor"));
			set.set("titleColor", data.getInt("titleColor"));
			
			L2MaxPolyModel poly = new L2MaxPolyModel(set);
			_map.put(poly.getNpcId(), poly);// xD
		}
		_log.info(PolymporphTable.class.getSimpleName() + ": Loaded " + _map.size() + " npc to pc entries.");
	}
	
	public L2MaxPolyModel getModelForID(int key)
	{
		return _map.get(key);
	}
	
	public static PolymporphTable getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final PolymporphTable _instance = new PolymporphTable();
	}
}