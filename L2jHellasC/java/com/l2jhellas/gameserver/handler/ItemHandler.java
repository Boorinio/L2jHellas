/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jhellas.gameserver.handler;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.handler.itemhandlers.BeastSoulShot;
import com.l2jhellas.gameserver.handler.itemhandlers.BeastSpice;
import com.l2jhellas.gameserver.handler.itemhandlers.BeastSpiritShot;
import com.l2jhellas.gameserver.handler.itemhandlers.BlessedSpiritShot;
import com.l2jhellas.gameserver.handler.itemhandlers.Book;
import com.l2jhellas.gameserver.handler.itemhandlers.CharChangePotions;
import com.l2jhellas.gameserver.handler.itemhandlers.ChestKey;
import com.l2jhellas.gameserver.handler.itemhandlers.ChristmasTree;
import com.l2jhellas.gameserver.handler.itemhandlers.CompBlessedSpiritShotPacks;
import com.l2jhellas.gameserver.handler.itemhandlers.CompShotPacks;
import com.l2jhellas.gameserver.handler.itemhandlers.CompSpiritShotPacks;
import com.l2jhellas.gameserver.handler.itemhandlers.CrystalCarol;
import com.l2jhellas.gameserver.handler.itemhandlers.EnchantScrolls;
import com.l2jhellas.gameserver.handler.itemhandlers.EnergyStone;
import com.l2jhellas.gameserver.handler.itemhandlers.ExtractableItems;
import com.l2jhellas.gameserver.handler.itemhandlers.Firework;
import com.l2jhellas.gameserver.handler.itemhandlers.FishShots;
import com.l2jhellas.gameserver.handler.itemhandlers.Harvester;
import com.l2jhellas.gameserver.handler.itemhandlers.HeroCustomItem;
import com.l2jhellas.gameserver.handler.itemhandlers.Maps;
import com.l2jhellas.gameserver.handler.itemhandlers.MercTicket;
import com.l2jhellas.gameserver.handler.itemhandlers.MysteryPotion;
import com.l2jhellas.gameserver.handler.itemhandlers.PaganKeys;
import com.l2jhellas.gameserver.handler.itemhandlers.Potions;
import com.l2jhellas.gameserver.handler.itemhandlers.Recipes;
import com.l2jhellas.gameserver.handler.itemhandlers.Remedy;
import com.l2jhellas.gameserver.handler.itemhandlers.RollingDice;
import com.l2jhellas.gameserver.handler.itemhandlers.ScrollOfEscape;
import com.l2jhellas.gameserver.handler.itemhandlers.ScrollOfResurrection;
import com.l2jhellas.gameserver.handler.itemhandlers.Scrolls;
import com.l2jhellas.gameserver.handler.itemhandlers.Seed;
import com.l2jhellas.gameserver.handler.itemhandlers.SevenSignsRecord;
import com.l2jhellas.gameserver.handler.itemhandlers.SoulCrystals;
import com.l2jhellas.gameserver.handler.itemhandlers.SoulShots;
import com.l2jhellas.gameserver.handler.itemhandlers.SpecialXMas;
import com.l2jhellas.gameserver.handler.itemhandlers.SpiritShot;
import com.l2jhellas.gameserver.handler.itemhandlers.SummonItems;

/**
 * This class manages handlers of items
 *
 * @version $Revision: 1.1.4.3 $ $Date: 2005/03/27 15:30:09 $
 */
public class ItemHandler
{
	private static final Logger _log = Logger.getLogger(ItemHandler.class.getName());

	private static ItemHandler _instance;

	private final Map<Integer, IItemHandler> _datatable;

	/**
	 * Create ItemHandler if doesn't exist and returns ItemHandler
	 *
	 * @return ItemHandler
	 */
	public static ItemHandler getInstance()
	{
		if (_instance == null)
		{
			_instance = new ItemHandler();
		}
		return _instance;
	}

	/**
	 * Returns the number of elements contained in datatable
	 *
	 * @return int : Size of the datatable
	 */
	public int size()
	{
		return _datatable.size();
	}

	/**
	 * Constructor of ItemHandler
	 */
	private ItemHandler()
	{
		_datatable = new TreeMap<Integer, IItemHandler>();
		registerItemHandler(new ScrollOfEscape());
		registerItemHandler(new ScrollOfResurrection());
		registerItemHandler(new SoulShots());
		registerItemHandler(new SpiritShot());
		registerItemHandler(new BlessedSpiritShot());
		registerItemHandler(new BeastSoulShot());
		registerItemHandler(new BeastSpiritShot());
		registerItemHandler(new ChestKey());
		registerItemHandler(new ChristmasTree());
		registerItemHandler(new CompBlessedSpiritShotPacks());
		registerItemHandler(new CompBlessedSpiritShotPacks());
		registerItemHandler(new CompShotPacks());
		registerItemHandler(new CompSpiritShotPacks());
		if(Config.HERO_CUSTOM_ITEMS)
		registerItemHandler(new HeroCustomItem());
		registerItemHandler(new PaganKeys());
		registerItemHandler(new Maps());
		registerItemHandler(new Potions());
		registerItemHandler(new Recipes());
		registerItemHandler(new RollingDice());
		registerItemHandler(new MysteryPotion());
		registerItemHandler(new EnchantScrolls());
		registerItemHandler(new EnergyStone());
		registerItemHandler(new Book());
		registerItemHandler(new Remedy());
		registerItemHandler(new Scrolls());
		registerItemHandler(new CrystalCarol());
		registerItemHandler(new SoulCrystals());
		registerItemHandler(new SevenSignsRecord());
		registerItemHandler(new CharChangePotions());
		registerItemHandler(new Firework());
		registerItemHandler(new Seed());
		registerItemHandler(new Harvester());
		registerItemHandler(new MercTicket());
		registerItemHandler(new FishShots());
		registerItemHandler(new ExtractableItems());
		registerItemHandler(new SpecialXMas());
		registerItemHandler(new SummonItems());
		registerItemHandler(new BeastSpice());

		_log.log(Level.INFO, getClass().getSimpleName() + ": Loaded " + size() + " Handlers in total.");
	}

	/**
	 * Adds handler of item type in <I>datatable</I>.<BR>
	 * <BR>
	 * <B><I>Concept :</I></U><BR>
	 * This handler is put in <I>datatable</I> Map &lt;Integer ; IItemHandler
	 * &gt; for each ID corresponding to an item type
	 * (existing in classes of package itemhandlers) sets as key of the Map.
	 *
	 * @param handler
	 *        (IItemHandler)
	 */
	public void registerItemHandler(IItemHandler handler)
	{
		// Get all ID corresponding to the item type of the handler
		int[] ids = handler.getItemIds();
		// Add handler for each ID found
		for (int i = 0; i < ids.length; i++)
		{
			_datatable.put(new Integer(ids[i]), handler);
		}
	}

	/**
	 * Returns the handler of the item
	 *
	 * @param itemId
	 *        : int designating the itemID
	 * @return IItemHandler
	 */
	public IItemHandler getItemHandler(int itemId)
	{
		return _datatable.get(new Integer(itemId));
	}
}
