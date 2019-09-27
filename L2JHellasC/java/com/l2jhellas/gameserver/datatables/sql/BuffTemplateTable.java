package com.l2jhellas.gameserver.datatables.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.templates.L2BuffTemplate;
import com.l2jhellas.gameserver.templates.StatsSet;
import com.l2jhellas.util.database.L2DatabaseFactory;

public class BuffTemplateTable
{
	protected static final Logger _log = Logger.getLogger(BuffTemplateTable.class.getName());
	
	private static BuffTemplateTable _instance;
	
	private final List<L2BuffTemplate> _buffs = new ArrayList<>();
	
	public static BuffTemplateTable getInstance()
	{
		if (_instance == null)
		{
			_instance = new BuffTemplateTable();
		}
		return _instance;
	}
	
	public BuffTemplateTable()
	{		
		ReloadBuffTemplates();
	}
	
	public void ReloadBuffTemplates()
	{
		_buffs.clear();
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT * FROM buff_templates ORDER BY id, skill_order");
			ResultSet rset = statement.executeQuery();
			
			int _buffTemplates = 0;
			int templateId = -1;
			
			while (rset.next())
			{
				StatsSet Buff = new StatsSet();
				
				if (templateId != rset.getInt("id"))
					_buffTemplates++;
				templateId = rset.getInt("id");
				
				Buff.set("id", templateId);
				Buff.set("name", rset.getString("name"));
				Buff.set("skillId", rset.getInt("skill_id"));
				Buff.set("skillLevel", rset.getInt("skill_level"));
				Buff.set("skillOrder", rset.getInt("skill_order"));
				Buff.set("forceCast", rset.getInt("skill_force"));
				Buff.set("minLevel", rset.getInt("char_min_level"));
				Buff.set("maxLevel", rset.getInt("char_max_level"));
				Buff.set("race", rset.getInt("char_race"));
				Buff.set("class", rset.getInt("char_class"));
				Buff.set("faction", rset.getInt("char_faction"));
				Buff.set("adena", rset.getInt("price_adena"));
				Buff.set("points", rset.getInt("price_points"));
				
				// Add this buff to the Table.
				L2BuffTemplate template = new L2BuffTemplate(Buff);
				if (template.getSkill() == null)
				{
					_log.warning(BuffTemplateTable.class.getName() + ": Error while loading buff template Id " + template.getId() + " skill Id " + template.getSkillId());
				}
				else
					_buffs.add(template);
			}
			
			_log.info(BuffTemplateTable.class.getSimpleName() + ": Loaded " + _buffTemplates + " Buff Templates.");
			
			rset.close();
			statement.close();
		}
		catch (Exception e)
		{
			_log.warning(BuffTemplateTable.class.getName() + ": Error while loading buff templates ");
			if (Config.DEVELOPER)
				e.printStackTrace();
		}
		
	}
	
	public List<L2BuffTemplate> getBuffTemplate(int Id)
	{
		return _buffs.stream().filter(buff -> (buff.getId() == Id)).collect(Collectors.toList());
	}
	
	public int getTemplateIdByName(String _name)
	{
		return  _buffs.stream().filter(buname -> buname.getName() !=null && buname.getName().equals(_name)).mapToInt(bu -> bu.getId()).findFirst().orElse(0);
	}
	
	public int getLowestLevel(int Id)
	{
		return  _buffs.stream().filter(lowest -> lowest.getId() == Id).mapToInt(bu -> bu.getMinLevel()).findFirst().orElse(0);
	}
	
	public int getHighestLevel(int Id)
	{
		return  _buffs.stream().filter(highest -> highest.getId() == Id).mapToInt(bu -> bu.getMaxLevel()).findFirst().orElse(0);
	}
	
	public List<L2BuffTemplate> getBuffTemplateTable()
	{
		return _buffs;
	}
}