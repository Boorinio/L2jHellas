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
package com.l2jhellas.gameserver.datatables.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.l2jhellas.gameserver.engines.DocumentParser;
import com.l2jhellas.gameserver.templates.L2HelperBuff;
import com.l2jhellas.gameserver.templates.StatsSet;

public class HelperBuffData implements DocumentParser
{
	protected static final Logger _log = Logger.getLogger(HelperBuffData.class.getName());

	private List<L2HelperBuff> _helperBuff = new ArrayList<>();

	private int _magicClassLowestLevel = 100;
	private int _physicClassLowestLevel = 100;

	private int _magicClassHighestLevel = 1;
	private int _physicClassHighestLevel = 1;



	private HelperBuffData()
	{
       load();
	}

	@Override
	public void load()
	{
		_helperBuff.clear();
		parseDatapackFile("data/xml/helper_buff_list.xml");
		_log.info("HelperBuffTable: Loaded " + _helperBuff.size() + " buffs.");	
	}
	
	@Override
	public void parseDocument(Document doc)
	{
		for (Node n = doc.getFirstChild(); n != null; n = n.getNextSibling())
		{
			if (n.getNodeName().equalsIgnoreCase("list"))
			{
				for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
				{
					if (d.getNodeName().equalsIgnoreCase("buff"))
					{
						int id = Integer.valueOf(d.getAttributes().getNamedItem("id").getNodeValue());
						int skill_id = Integer.valueOf(d.getAttributes().getNamedItem("skill_id").getNodeValue());
						int skill_level = Integer.valueOf(d.getAttributes().getNamedItem("skill_level").getNodeValue());
						int lower_level = Integer.valueOf(d.getAttributes().getNamedItem("lower_level").getNodeValue());
						int upper_level = Integer.valueOf(d.getAttributes().getNamedItem("upper_level").getNodeValue());
						boolean is_magic_class = Boolean.valueOf(d.getAttributes().getNamedItem("is_magic_class").getNodeValue());

						StatsSet helperBuffDat = new StatsSet();

						helperBuffDat.set("id", id);
						helperBuffDat.set("skillID", skill_id);
						helperBuffDat.set("skillLevel", skill_level);
						helperBuffDat.set("lowerLevel", lower_level);
						helperBuffDat.set("upperLevel", upper_level);
						helperBuffDat.set("isMagicClass", is_magic_class);

						if (is_magic_class == false)
						{
							if (lower_level < _physicClassLowestLevel)
							{
								_physicClassLowestLevel = lower_level;
							}

							if (upper_level > _physicClassHighestLevel)
							{
								_physicClassHighestLevel = upper_level;
							}
						}
						else
						{
							if (lower_level < _magicClassLowestLevel)
							{
								_magicClassLowestLevel = lower_level;
							}

							if (upper_level > _magicClassHighestLevel)
							{
								_magicClassHighestLevel = upper_level;
							}
						}

						// Add this Helper Buff to the Helper Buff List
						L2HelperBuff template = new L2HelperBuff(helperBuffDat);
						_helperBuff.add(template);
					}
				}
			}
		}		
	}
	public List<L2HelperBuff> getHelperBuffTable()
	{
		return _helperBuff;
	}

	public int getMagicClassHighestLevel()
	{
		return _magicClassHighestLevel;
	}

	public int getMagicClassLowestLevel()
	{
		return _magicClassLowestLevel;
	}

	public int getPhysicClassHighestLevel()
	{
		return _physicClassHighestLevel;
	}

	public int getPhysicClassLowestLevel()
	{
		return _physicClassLowestLevel;
	}

	public static HelperBuffData getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final HelperBuffData _instance = new HelperBuffData();
	}
}