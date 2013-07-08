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
package com.l2jhellas.gameserver.datatables.csv;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.model.L2ExtractableItem;
import com.l2jhellas.gameserver.model.L2ExtractableProductItem;

public class ExtractableItemsData
{
	protected static final Logger _log = Logger.getLogger(ExtractableItemsData.class.getName());

	// Map<itemid, L2ExtractableItem>
	private HashMap<Integer, L2ExtractableItem> _items;

	private static ExtractableItemsData _instance = null;

	public static ExtractableItemsData getInstance()
	{
		if (_instance == null)
			_instance = new ExtractableItemsData();

		return _instance;
	}

	@SuppressWarnings("resource")
	public ExtractableItemsData()
	{
		_items = new HashMap<Integer, L2ExtractableItem>();

		Scanner s = null;

		try
		{
			s = new Scanner(new File(Config.DATAPACK_ROOT, "data/csv/extractable_items.csv"));
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Extractable items data: Can not find 'data/extractable_items.csv'" + e);
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
			return;
		}

		int lineCount = 0;

		while (s.hasNextLine())
		{
			lineCount++;

			String line = s.nextLine();

			if (line.startsWith("#"))
				continue;
			else if (line.equals(""))
				continue;

			String[] lineSplit = line.split(";");
			int itemID = 0;

			try
			{
				itemID = Integer.parseInt(lineSplit[0]);
			}
			catch (Exception e)
			{
				_log.log(Level.WARNING, getClass().getName() + ": Extractable items data: Error in line " + lineCount + " -> invalid item id or wrong seperator after item id!");
				_log.log(Level.WARNING, getClass().getName() + ": 		" + line, e);
				if (Config.DEVELOPER)
				{
					e.printStackTrace();
				}
				return;
			}

			List<L2ExtractableProductItem> product_temp = new ArrayList<L2ExtractableProductItem>(lineSplit.length);

			for (int i = 0; i < lineSplit.length - 1; i++)
			{
				String[] lineSplit2 = lineSplit[i + 1].split(",");

				if (lineSplit2.length != 3)
				{
					_log.log(Level.WARNING, getClass().getName() + ": Extractable items data: Error in line " + lineCount + " -> wrong seperator!");
					_log.log(Level.WARNING, getClass().getName() + ": 		" + line);
					continue;
				}

				int production = 0, amount = 0, chance = 0;

				try
				{
					production = Integer.parseInt(lineSplit2[0]);
					amount = Integer.parseInt(lineSplit2[1]);
					chance = Integer.parseInt(lineSplit2[2]);
				}
				catch (Exception e)
				{
					_log.log(Level.WARNING, getClass().getName() + ": Extractable items data: Error in line " + lineCount + " -> incomplete/invalid production data or wrong seperator!");
					_log.log(Level.WARNING, getClass().getName() + ": 		" + line, e);
					continue;
				}

				product_temp.add(new L2ExtractableProductItem(production, amount, chance));
			}

			int fullChances = 0;

			for (L2ExtractableProductItem Pi : product_temp)
				fullChances += Pi.getChance();

			if (fullChances > 100)
			{
				_log.log(Level.WARNING, getClass().getName() + ": Extractable items data: Error in line " + lineCount + " -> all chances together are more then 100!");
				_log.log(Level.WARNING, getClass().getName() + ": 		" + line);
				continue;
			}
			_items.put(itemID, new L2ExtractableItem(itemID, product_temp));
		}

		s.close();
		_log.log(Level.INFO, getClass().getSimpleName() + ": Loaded " + _items.size() + " extractable items!");
	}

	public L2ExtractableItem getExtractableItem(int itemID)
	{
		return _items.get(itemID);
	}

	public int[] itemIDs()
	{
		int size = _items.size();
		int[] result = new int[size];
		int i = 0;
		for (L2ExtractableItem ei : _items.values())
		{
			result[i] = ei.getItemId();
			i++;
		}
		return result;
	}
}