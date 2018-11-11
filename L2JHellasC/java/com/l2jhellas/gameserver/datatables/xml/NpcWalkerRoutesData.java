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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.l2jhellas.gameserver.model.L2NpcWalkerNode;
import com.l2jhellas.util.XMLDocumentFactory;

public class NpcWalkerRoutesData
{
	private static final Logger _log = Logger.getLogger(NpcWalkerRoutesData.class.getName());
	
	private final Map<Integer, List<L2NpcWalkerNode>> _routes = new HashMap<>();
	
	public static NpcWalkerRoutesData getInstance()
	{
		return SingletonHolder._instance;
	}
	
	protected NpcWalkerRoutesData()
	{
		load();
	}
	
	public void reload()
	{
		_routes.clear();
		load();
	}
	
	public void load()
	{
		try
		{
			File f = new File("./data/xml/walker_routes.xml");
			Document doc = XMLDocumentFactory.getInstance().loadDocument(f);
			
			Node n = doc.getFirstChild();
			for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
			{
				if (d.getNodeName().equals("walker"))
				{
					List<L2NpcWalkerNode> list = new ArrayList<>();
					int npcId = Integer.parseInt(d.getAttributes().getNamedItem("npcId").getNodeValue());
					boolean running = Boolean.parseBoolean(d.getAttributes().getNamedItem("run").getNodeValue());
					
					for (Node r = d.getFirstChild(); r != null; r = r.getNextSibling())
					{
						if (r.getNodeName().equals("route"))
						{
							// Additional parameters are "defaulted" here.
							String chat = "";
							int delay = 0;
							
							NamedNodeMap attrs = r.getAttributes();
							int id = Integer.parseInt(attrs.getNamedItem("id").getNodeValue());
							int x = Integer.parseInt(attrs.getNamedItem("X").getNodeValue());
							int y = Integer.parseInt(attrs.getNamedItem("Y").getNodeValue());
							int z = Integer.parseInt(attrs.getNamedItem("Z").getNodeValue());
							
							// Additional parameters : message && delay
							for (Node c = r.getFirstChild(); c != null; c = c.getNextSibling())
							{
								if ("delay".equalsIgnoreCase(c.getNodeName()))
									delay = Integer.parseInt(c.getAttributes().getNamedItem("val").getNodeValue());
								else if ("chat".equalsIgnoreCase(c.getNodeName()))
									chat = c.getAttributes().getNamedItem("val").getNodeValue();
							}
							list.add(new L2NpcWalkerNode(id, x, y, z, running, delay, chat));
						}
					}
					
					_routes.put(npcId, list);
				}
			}
		}
		catch (Exception e)
		{
			_log.log(Level.SEVERE, "WalkerRoutesTable: Error while loading routes: " + e);
		}
		
		_log.info("WalkerRoutesTable: Loaded " + _routes.size() + " NpcWalker routes.");
	}
	
	public List<L2NpcWalkerNode> getRouteForNpc(int id)
	{
		return _routes.get(id);
	}
	
	public Collection<List<L2NpcWalkerNode>> getWalkers()
	{
		return _routes.values();
	}
	
	private static class SingletonHolder
	{
		protected static final NpcWalkerRoutesData _instance = new NpcWalkerRoutesData();
	}
}