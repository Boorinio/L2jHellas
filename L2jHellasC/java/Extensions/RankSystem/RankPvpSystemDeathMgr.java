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
package Extensions.RankSystem;

import java.util.logging.Logger;

import javolution.text.TextBuilder;

import com.l2jhellas.ExternalConfig;
import com.l2jhellas.gameserver.model.L2ItemInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jhellas.gameserver.templates.L2Item;

/**
 * @author Masterio
 */
public class RankPvpSystemDeathMgr
{
	private static Logger _log = Logger.getLogger(RankPvpSystemDeathMgr.class.getName());
	
	private L2PcInstance _killer = null;
	private L2PcInstance _victim = null;
	
	// store killer data, (anti-change target protection).
	// data updating on every kill.
	private int _killerLevel = 0;
	private int _killerClassId = 0;
	private int _killerCurrentCP = 0;
	private int _killerCurrentHP = 0;
	private int _killerCurrentMP = 0;
	
	private int _killerMaxCP = 0;
	private int _killerMaxHP = 0;
	private int _killerMaxMP = 0;
	
	/**
	 * [0 - itemSlotId] [1 - itemName] [2 - itemEnchantLevel].
	 */
	private final KillerItem[] _killerItems = new KillerItem[18];
	
	/**
	 * Always use this constructor as default!
	 * 
	 * @param killer
	 * @param victim
	 */
	public RankPvpSystemDeathMgr(L2PcInstance killer, L2PcInstance victim)
	{
		_killer = killer;
		_victim = victim;
		
		_killerLevel = killer.getLevel();
		_killerClassId = killer.getClassId().getId();
		_killerCurrentCP = (int) killer.getCurrentCp();
		_killerCurrentHP = (int) killer.getCurrentHp();
		_killerCurrentMP = (int) killer.getCurrentMp();
		
		_killerMaxCP = killer.getMaxCp();
		_killerMaxHP = killer.getMaxHp();
		_killerMaxMP = killer.getMaxMp();
		
		// load item list:
		if (_killer != null && ExternalConfig.CUSTOM_PVP_DEATH_MANAGER_SHOW_ITEMS)
		{
			int j = 0;
			for (int i = 0; i < _killerItems.length; i++)
			{
				_killerItems[j] = new KillerItem();
				L2ItemInstance item = _killer.getInventory().getItemByObjectId(_killer.getInventory().getPaperdollObjectId(i));
				if (item != null && !isItemInsideKillerItemsArray(item))
				{
					int b = item.getItem().getBodyPart();
					// item slot id:
					_killerItems[j]._slotId = b;
					// item Name:
					_killerItems[j]._itemName = item.getItemName();
					// item Enchant:
					_killerItems[j]._itemEnchantLevel = item.getEnchantLevel();
					// item object id:
					_killerItems[j]._itemObjId = item.getObjectId();
					
					// item group:
					if (b == L2Item.SLOT_R_HAND || b == L2Item.SLOT_L_HAND || b == L2Item.SLOT_LR_HAND || b == L2Item.SLOT_LR_HAND)
					{
						_killerItems[j]._group = 1;
					}
					else if (b == L2Item.SLOT_UNDERWEAR || b == L2Item.SLOT_HEAD || b == L2Item.SLOT_GLOVES || b == L2Item.SLOT_CHEST || b == L2Item.SLOT_LEGS || b == L2Item.SLOT_FEET || b == L2Item.SLOT_BACK || b == L2Item.SLOT_FULL_ARMOR)
					{
						_killerItems[j]._group = 2;
					}
					else if (b == L2Item.SLOT_R_EAR || b == L2Item.SLOT_L_EAR || b == 6 || b == L2Item.SLOT_R_FINGER || b == L2Item.SLOT_L_FINGER || b == 48 || b == L2Item.SLOT_NECK)
					{
						_killerItems[j]._group = 3;
					}
					else
					{
						_killerItems[j]._group = 4;
					}

					j++;
				}
			}
		}
	}
	
	/**
	 * Return true if item exists. Searched by item id.
	 * 
	 * @param item
	 * @return
	 */
	private boolean isItemInsideKillerItemsArray(L2ItemInstance item)
	{
		for (int i = 0; i < _killerItems.length; i++)
		{
			if (_killerItems[i] != null)
			{
				if (_killerItems[i]._itemObjId == item.getObjectId())
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Send HTML response to dead player (victim).
	 */
	public void sendVictimResponse()
	{
		NpcHtmlMessage n = new NpcHtmlMessage(0);
		n.setHtml(victimHtmlResponse().toString());
		_victim.sendPacket(n);
	}
	
	private TextBuilder victimHtmlResponse()
	{
		TextBuilder tb = new TextBuilder();
		tb.append("<html><title>" + _killer.getName() + " Equipment informations</title><body><center>");
		
		tb.append("<table width=270 border=0 cellspacing=0 cellpadding=2 bgcolor=000000>");
		tb.append("<tr><td width=270 height=18 align=center><font color=ae9977>Killer Name (lvl):</font> " + getKiller().getName() + " (" + _killerLevel + ")</td></tr>");
		tb.append("<tr><td width=270 height=18 align=center><font color=ae9977>Killer Class:</font> " + RankPvpSystemUtil.getClassName(_killerClassId) + "</td></tr>");
		tb.append("<tr><td FIXWIDTH=270 HEIGHT=4><img src=\"L2UI.Squaregray\" width=\"270\" height=\"1\"></td></tr>");
		tb.append("</table>");
		
		tb.append("<table width=270 border=0 cellspacing=0 cellpadding=2 bgcolor=000000>");
		tb.append("<tr><td width=270 height=18 align=center><font color=ae9977>CP:</font> <font color=FFF000>" + _killerCurrentCP + " / " + _killerMaxCP + "</font></td></tr>");
		tb.append("<tr><td width=270 height=18 align=center><font color=ae9977>HP:</font> <font color=FF0000>" + _killerCurrentHP + " / " + _killerMaxHP + "</font><font color=ae9977>, MP:</font> <font color=2080D0>" + _killerCurrentMP + "/" + _killerMaxMP + "</font></td></tr>");
		tb.append("<tr><td FIXWIDTH=270 HEIGHT=4><img src=\"L2UI.Squaregray\" width=\"270\" height=\"1\"></td></tr>");
		tb.append("<tr><td width=270 height=12></td></tr>");
		tb.append("</table>");
		
		// show item list:
		if (ExternalConfig.CUSTOM_PVP_DEATH_MANAGER_SHOW_ITEMS)
		{
			tb.append("<table width=270 border=0 cellspacing=0 cellpadding=2 bgcolor=000000>");
			if (getKiller() != null)
			{
				for (int group = 1; group <= 4; group++)
				{
					if (group == 1)
					{
						tb.append("<tr><td width=270 height=18 align=center><font color=2080D0>Weapon / Shield</font></td></tr>");
					}
					else if (group == 2)
					{
						tb.append("<tr><td width=270 height=18 align=center><font color=2080D0>Armor</font></td></tr>");
					}
					else if (group == 3)
					{
						tb.append("<tr><td width=270 height=18 align=center><font color=2080D0>Jewellery</font></td></tr>");
					}
					else
					{
						tb.append("<tr><td width=270 height=18 align=center><font color=2080D0>Other</font></td></tr>");
					}
					tb.append("<tr><td FIXWIDTH=270 HEIGHT=3><img src=\"L2UI.Squaregray\" width=\"270\" height=\"1\"></td></tr>");
					for (int i = 0; i < _killerItems.length; i++)
					{
						if (_killerItems[i] != null && _killerItems[i]._group == group && _killerItems[i]._itemName != null)
						{
							tb.append("<tr><td width=270 height=16 align=center><font color=808080>" + _killerItems[i]._itemName + " (</font><font color=FF8000>+" + _killerItems[i]._itemEnchantLevel + "</font><font color=808080>)</font></td></tr>");
							tb.append("<tr><td width=270 HEIGHT=3><img src=\"L2UI.Squaregray\" width=\"270\" height=\"1\"></td></tr>");
						}
					}
				}
			}
			else
			{
				tb.append("<tr><td>I can't load Killer Data!</td></tr>");
				_log.info("DeathManager::killer is null!");
			}
			tb.append("</table>");
		}
		
		// footer and back button:
		tb.append("<table border=0 cellspacing=0 cellpadding=0>");
		tb.append("<tr><td width=270 height=12 align=center><font color=808080>- killer state in kill moment -</font></td></tr>");
		tb.append("<tr><td width=270 height=12></td></tr>");
		tb.append("<tr><td width=270 align=center><button value=\"Back\" action=\"bypass -h _cprs_info\" back=\"l2ui_ch3.smallbutton2_down\" width=65 height=20 fore=\"l2ui_ch3.smallbutton2\"></td></tr>");
		tb.append("</table>");
		
		tb.append("</center></body></html>");
		return tb;
	}

	/**
	 * @return the _killer
	 */
	public L2PcInstance getKiller()
	{
		return _killer;
	}

	/**
	 * @param killer
	 *        the killer to set
	 */
	public void setKiller(L2PcInstance killer)
	{
		this._killer = killer;
	}
	
	class KillerItem
	{
		int _slotId = -1;
		String _itemName = null;
		int _itemEnchantLevel = 0;
		int _itemObjId = 0;
		/** Groups like WEAPON(1), ARMOR(2), JEWELLERY(3), OTHER(4) */
		int _group = 4;
	}
}