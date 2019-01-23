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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.l2jhellas.gameserver.engines.DocumentParser;
import com.l2jhellas.gameserver.model.FishData;
import com.l2jhellas.gameserver.templates.StatsSet;

public class FishTable implements DocumentParser
{
	private static final Logger _log = Logger.getLogger(FishTable.class.getName());
	
	private final Map<Integer, FishData> _fishNormal = new HashMap<>();
	private final Map<Integer, FishData> _fishEasy = new HashMap<>();
	private final Map<Integer, FishData> _fishHard = new HashMap<>();

	
	protected FishTable()
	{
      load();
	}
	
	@Override
	public void load()
	{
		_fishEasy.clear();
		_fishNormal.clear();
		_fishHard.clear();
		parseDatapackFile("data/xml/fishes.xml");
		_log.info(FishTable.class.getSimpleName() + ": Loaded " +(_fishEasy.size() + _fishNormal.size() + _fishHard.size())+ " fishes.");
	}
	
	@Override
	public void parseDocument(Document doc)
	{
		for (Node n = doc.getFirstChild(); n != null; n = n.getNextSibling())
		{
			if ("list".equalsIgnoreCase(n.getNodeName()))
			{
				for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
				{
					if ("fish".equalsIgnoreCase(d.getNodeName()))
					{
						final NamedNodeMap attrs = d.getAttributes();
						
						final StatsSet set = new StatsSet();
						for (int i = 0; i < attrs.getLength(); i++)
						{
							final Node att = attrs.item(i);
							set.set(att.getNodeName(), att.getNodeValue());
						}

						final FishData fish = new FishData(set);
						switch (fish.getFishGrade())
						{
							case 0:
							{
								_fishEasy.put(fish.getFishId(), fish);
								break;
							}
							case 1:
							{
								_fishNormal.put(fish.getFishId(), fish);
								break;
							}
							case 2:
							{
								_fishHard.put(fish.getFishId(), fish);
								break;
							}
						}
					}
				}
			}
		}
	}
	public List<FishData> getFish(int level, int group, int grade)
	{
		final List<FishData> result = new ArrayList<>();
		Map<Integer, FishData> fish = null;
		switch (grade)
		{
			case 0:
			{
				fish = _fishEasy;
				break;
			}
			case 1:
			{
				fish = _fishNormal;
				break;
			}
			case 2:
			{
				fish = _fishHard;
				break;
			}
			default:
			{
				LOG.warn("{}: Unmanaged fish grade!", getClass().getSimpleName());
				return result;
			}
		}
		
		for (FishData f : fish.values())
		{
			if ((f.getFishLevel() != level) || (f.getFishGroup() != group))
			{
				continue;
			}
			result.add(f);
		}
		
		if (result.isEmpty())
		{
			LOG.warn("{}: Cannot find any fish for level: {} group: {} and grade: {}!", getClass().getSimpleName(), level, group, grade);
		}
		return result;
	}
	
	
	public static FishTable getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final FishTable _instance = new FishTable();
	}
}