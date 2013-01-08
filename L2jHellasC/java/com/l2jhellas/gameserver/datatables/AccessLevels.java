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
package com.l2jhellas.gameserver.datatables;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;

import javolution.util.FastMap;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.model.L2AccessLevel;

/**
 * @author FBIagent<br>
 */
public class AccessLevels
{
	private static Logger _log = Logger.getLogger(AccessLevels.class.getName());
	
	public static final int _masterAccessLevelNum = Config.MASTERACCESS_LEVEL;
	public static L2AccessLevel _masterAccessLevel = new L2AccessLevel(_masterAccessLevelNum, "Master Access", Config.MASTERACCESS_NAME_COLOR, Config.MASTERACCESS_TITLE_COLOR, null, true, true, true, true, true, true, true, true);
	
	public static final int _userAccessLevelNum = 0;
	public static L2AccessLevel _userAccessLevel = new L2AccessLevel(_userAccessLevelNum, "User", -1, -1, null, false, false, false, true, false, true, true, true);
	
	private Map<Integer, L2AccessLevel> _accessLevels;
	
	/**
	 * Returns the one and only instance of this class<br><br>
	 * 
	 * @return AccessLevels: the one and only instance of this class<br>
	 */
	public static AccessLevels getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private AccessLevels()
	{
		loadAccessLevels();
		_accessLevels.put(_userAccessLevelNum, _userAccessLevel);
	}
	
	/**
	 * Loads the access levels from database<br>
	 */
	private void loadAccessLevels()
	{
		_accessLevels = new FastMap<Integer, L2AccessLevel>();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setIgnoringComments(true);
		File f = new File("./data/stats/access_levels.xml");
		if (!f.exists())
		{
			_log.severe("AccessLevels: access_levels.xml could not be loaded: file not found");
			return;
		}
		
		try
		{
			InputSource in = new InputSource(new InputStreamReader(new FileInputStream(f), "UTF-8"));
			in.setEncoding("UTF-8");
			Document doc = factory.newDocumentBuilder().parse(in);
			for (Node n = doc.getFirstChild(); n != null; n = n.getNextSibling())
			{
				if (n.getNodeName().equalsIgnoreCase("list"))
				{
					for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
					{
						if (d.getNodeName().equalsIgnoreCase("accessLevel"))
						{
							int level = Integer.valueOf(d.getAttributes().getNamedItem("level").getNodeValue());
							String name = String.valueOf(d.getAttributes().getNamedItem("name").getNodeValue());
							int nameColor = Integer.decode("0x" + (d.getAttributes().getNamedItem("nameColor").getNodeValue()));
							int titleColor = Integer.decode("0x" + (d.getAttributes().getNamedItem("titleColor").getNodeValue()));
							String childAccess = String.valueOf(d.getAttributes().getNamedItem("childAccess").getNodeValue());
							boolean isGm = Boolean.valueOf(d.getAttributes().getNamedItem("isGm").getNodeValue());
							boolean allowPeaceAttack = Boolean.valueOf(d.getAttributes().getNamedItem("allowPeaceAttack").getNodeValue());
							boolean allowFixedRes = Boolean.valueOf(d.getAttributes().getNamedItem("allowFixedRes").getNodeValue());
							boolean allowTransaction = Boolean.valueOf(d.getAttributes().getNamedItem("allowTransaction").getNodeValue());
							boolean allowAltg = Boolean.valueOf(d.getAttributes().getNamedItem("allowAltg").getNodeValue());
							boolean giveDamage = Boolean.valueOf(d.getAttributes().getNamedItem("giveDamage").getNodeValue());
							boolean takeAggro = Boolean.valueOf(d.getAttributes().getNamedItem("takeAggro").getNodeValue());
							boolean gainExp = Boolean.valueOf(d.getAttributes().getNamedItem("gainExp").getNodeValue());
							
							_accessLevels.put(level, new L2AccessLevel(level, name, nameColor, titleColor, childAccess.isEmpty() ? null : childAccess, isGm, allowPeaceAttack, allowFixedRes, allowTransaction, allowAltg, giveDamage, takeAggro, gainExp));
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "AccessLevels: Error loading:" + e.getMessage(), e);
		}
		_log.info("AccessLevels: Loaded " + _accessLevels.size() + " from access_levels.xml.");
	}
	
	/**
	 * Returns the access level by characterAccessLevel<br><br>
	 * 
	 * @param accessLevelNum as int<br><br>
	 *
	 * @return AccessLevel: AccessLevel instance by char access level<br>
	 */
	public L2AccessLevel getAccessLevel(int accessLevelNum)
	{
		L2AccessLevel accessLevel = null;
		
		synchronized (_accessLevels)
		{
			accessLevel = _accessLevels.get(accessLevelNum);
		}
		return accessLevel;
	}
	
	public void addBanAccessLevel(int accessLevel)
	{
		synchronized (_accessLevels)
		{
			if (accessLevel > -1)
				return;
			
			_accessLevels.put(accessLevel, new L2AccessLevel(accessLevel, "Banned", -1, -1, null, false, false, false, false, false, false, false, false));
		}
	}
	
	public void reloadAccessLevels()
	{
		loadAccessLevels();
	}
	
	@SuppressWarnings("synthetic-access")
	private static class SingletonHolder
	{
		protected static final AccessLevels _instance = new AccessLevels();
	}
}