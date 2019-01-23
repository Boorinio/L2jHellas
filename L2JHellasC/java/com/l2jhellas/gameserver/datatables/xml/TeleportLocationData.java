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
import org.w3c.dom.Node;

import com.l2jhellas.gameserver.engines.DocumentParser;
import com.l2jhellas.gameserver.model.L2TeleportLocation;

public class TeleportLocationData implements DocumentParser
{
	private static Logger _log = Logger.getLogger(TeleportLocationData.class.getName());

	private Map<Integer, L2TeleportLocation> _teleports = new HashMap<>();

	private TeleportLocationData()
	{
		load();
	}
	
	@Override
	public void load()
	{
		_teleports.clear();
		parseDatapackDirectory("data/teleports", true);	
		_log.info("[" + getClass().getSimpleName() + "]: Loaded: " + _teleports.size() + " Location Templates.");
	}
	
	@Override
	public void parseDocument(Document doc)
	{
		L2TeleportLocation teleport = null;
		
		for (Node n = doc.getFirstChild(); n != null; n = n.getNextSibling())
		{
			if ("list".equalsIgnoreCase(n.getNodeName()))
			{
				for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
				{
					if ("teleport".equalsIgnoreCase(d.getNodeName()))
					{
						int id = Integer.parseInt(d.getAttributes().getNamedItem("id").getNodeValue());
						int x = Integer.parseInt(d.getAttributes().getNamedItem("X").getNodeValue());
						int y = Integer.parseInt(d.getAttributes().getNamedItem("Y").getNodeValue());
						int z = Integer.parseInt(d.getAttributes().getNamedItem("Z").getNodeValue());

						boolean isNoble = false;
						boolean isGM = false;
						boolean isClanHallTp = false;
						boolean isFortTp = false;
						boolean isCastleTp = false;
						List<int[]> items = new ArrayList<int[]>(1);
						int minLevel = 1;
						int maxLevel = 85;

						Node node = d.getAttributes().getNamedItem("isNoble");
						if (node != null)
						{
							isNoble = Boolean.parseBoolean(node.getNodeValue());
						}
						node = d.getAttributes().getNamedItem("isGM");
						if (node != null)
						{
							isGM = Boolean.parseBoolean(node.getNodeValue());
						}
						node = d.getAttributes().getNamedItem("isClanHallTp");
						if (node != null)
						{
							isClanHallTp = Boolean.parseBoolean(node.getNodeValue());
						}
						node = d.getAttributes().getNamedItem("isFortTp");
						if (node != null)
						{
							isFortTp = Boolean.parseBoolean(node.getNodeValue());
						}
						node = d.getAttributes().getNamedItem("isCastleTp");
						if (node != null)
						{
							isCastleTp = Boolean.parseBoolean(node.getNodeValue());
						}
						node = d.getAttributes().getNamedItem("price");
						if (node != null)
						{
							try
							{

								String[] values = node.getNodeValue().split(";");
								for (String value : values)
								{
									String[] data = value.split(",");
									int[] item = new int[2];
									item[0] = Integer.parseInt(data[0]);
									item[1] = Integer.parseInt(data[1]);
									items.add(item);
								}
							}
							catch (Exception e)
							{
								_log.warning(TeleportLocationData.class.getSimpleName() + ": [" + getClass().getSimpleName() + "]: Error while parsing price for teleport id: " + id);
								continue;
							}
						}
						node = d.getAttributes().getNamedItem("minMaxLevel");
						if (node != null)
						{
							try
							{
								String[] values = node.getNodeValue().split("-");
								minLevel = Integer.parseInt(values[0]);
								maxLevel = Integer.parseInt(values[1]);
							}
							catch (Exception e)
							{
								_log.warning(TeleportLocationData.class.getSimpleName() + ": [" + getClass().getSimpleName() + "]: Error while parsing minMaxLevel for teleport id: " + id);
								continue;
							}
						}

						if (_teleports.containsKey(id))
						{
							L2TeleportLocation loc = _teleports.get(id);
							_log.info(TeleportLocationData.class.getName() + "[" + getClass().getSimpleName() + "]: Duplicate teleport found! \nid: " + id + " X: " + x + " Y: " + y + " Z: " + z + " \nExisting: X: " + loc.getLocX() + " Y: " + loc.getLocY() + " Z: " + loc.getLocZ());
						}

						teleport = new L2TeleportLocation();
						teleport.setTeleId(id);
						teleport.setLocX(x);
						teleport.setLocY(y);
						teleport.setLocZ(z);
						teleport.setItemsList(items);
						teleport.setIsForNoble(isNoble);
						teleport.setIsForGM(isGM);
						teleport.setIsForClanHall(isClanHallTp);
						teleport.setIsForFort(isFortTp);
						teleport.setIsForCastle(isCastleTp);
						teleport.setMinLevel(minLevel);
						teleport.setMaxLevel(maxLevel);

						_teleports.put(teleport.getTeleId(), teleport);
					}
				}
			}
		}		
	}

	/**
	 * @param template
	 *        id
	 * @return
	 */
	public L2TeleportLocation getTemplate(int id)
	{
		return _teleports.get(id);
	}
	
	public static TeleportLocationData getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final TeleportLocationData _instance = new TeleportLocationData();
	}

}