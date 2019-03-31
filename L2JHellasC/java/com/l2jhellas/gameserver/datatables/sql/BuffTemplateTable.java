package com.l2jhellas.gameserver.datatables.sql;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.templates.L2BuffTemplate;
import com.l2jhellas.gameserver.templates.StatsSet;
import com.l2jhellas.util.database.L2DatabaseFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Logger;

public class BuffTemplateTable
{
	protected static final Logger _log = Logger.getLogger(BuffTemplateTable.class.getName());
	
	private static BuffTemplateTable _instance;
	
	private final ArrayList<L2BuffTemplate> _buffs;
	
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
		_buffs = new ArrayList<>();
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
	
	public ArrayList<L2BuffTemplate> getBuffTemplate(int Id)
	{
		ArrayList<L2BuffTemplate> _templateBuffs = new ArrayList<>();
		
		for (L2BuffTemplate _bt : _buffs)
		{
			if (_bt.getId() == Id)
			{
				_templateBuffs.add(_bt);
			}
		}
		
		return _templateBuffs;
	}
	
	public int getTemplateIdByName(String _name)
	{
		
		int _id = 0;
		
		for (L2BuffTemplate _bt : _buffs)
		{
			if (_bt.getName().equals(_name))
			{
				_id = _bt.getId();
				break;
			}
		}
		
		return _id;
	}
	
	public int getLowestLevel(int Id)
	{
		int _lowestLevel = 255;
		
		for (L2BuffTemplate _bt : _buffs)
		{
			if ((_bt.getId() == Id) && (_lowestLevel > _bt.getMinLevel()))
			{
				_lowestLevel = _bt.getMinLevel();
			}
		}
		
		return _lowestLevel;
	}
	
	public int getHighestLevel(int Id)
	{
		int _highestLevel = 0;
		
		for (L2BuffTemplate _bt : _buffs)
		{
			if ((_bt.getId() == Id) && (_highestLevel < _bt.getMaxLevel()))
			{
				_highestLevel = _bt.getMaxLevel();
			}
		}
		
		return _highestLevel;
	}
	
	public ArrayList<L2BuffTemplate> getBuffTemplateTable()
	{
		return _buffs;
	}
}