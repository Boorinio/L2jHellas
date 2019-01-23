/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jhellas.gameserver.datatables.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.PackRoot;
import com.l2jhellas.gameserver.engines.DocumentParser;

public class ExperienceData implements DocumentParser
{
	private static Logger _log = Logger.getLogger(ExperienceData.class.getName());

	private byte MAX_LEVEL;
	private byte MAX_PET_LEVEL;

	private final Map<Integer, Long> _expTable = new HashMap<Integer, Long>();

	private ExperienceData()
	{
		load();
	}

	@Override
	public void load()
	{
		_expTable.clear();
		parseFile(new File(PackRoot.DATAPACK_ROOT, "data/xml/experience.xml"));
		_log.info(getClass().getSimpleName() + ": Loaded " + _expTable.size() + " levels");
		_log.info(getClass().getSimpleName() + ": Max Player Level is: " + (MAX_LEVEL - 1));
		_log.info(getClass().getSimpleName() + ": Max Pet Level is: " + (MAX_PET_LEVEL - 1));	}
	
	@Override
	public void parseDocument(Document doc)
	{
		final Node table = doc.getFirstChild();
		final NamedNodeMap tableAttr = table.getAttributes();

		MAX_LEVEL = (byte) (Byte.parseByte(tableAttr.getNamedItem("maxLevel").getNodeValue()) + 1);
		MAX_PET_LEVEL = (byte) (Byte.parseByte(tableAttr.getNamedItem("maxPetLevel").getNodeValue()) + 1);

		_expTable.clear();

		NamedNodeMap attrs;
		Integer level;
		Long exp;
		for (Node experience = table.getFirstChild(); experience != null; experience = experience.getNextSibling())
		{
			if (experience.getNodeName().equals("experience"))
			{
				attrs = experience.getAttributes();
				level = Integer.valueOf(attrs.getNamedItem("level").getNodeValue());
				exp = Long.valueOf(attrs.getNamedItem("tolevel").getNodeValue());
				_expTable.put(level, exp);
			}
		}

	}
	public long getExpForLevel(int level)
	{
		return _expTable.get(level);
	}

	public byte getMaxLevel()
	{
		return MAX_LEVEL;
	}

	public byte getMaxPetLevel()
	{
		return MAX_PET_LEVEL;
	}

	public static ExperienceData getInstance()
	{
		return SingletonHolder._instance;
	}

	
	private static class SingletonHolder
	{
		protected static final ExperienceData _instance = new ExperienceData();
	}
}