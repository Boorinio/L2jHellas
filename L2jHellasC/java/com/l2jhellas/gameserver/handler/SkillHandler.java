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
import java.util.logging.Logger;

import com.l2jhellas.gameserver.handler.skillhandlers.BalanceLife;
import com.l2jhellas.gameserver.handler.skillhandlers.BeastFeed;
import com.l2jhellas.gameserver.handler.skillhandlers.Blow;
import com.l2jhellas.gameserver.handler.skillhandlers.Charge;
import com.l2jhellas.gameserver.handler.skillhandlers.CombatPointHeal;
import com.l2jhellas.gameserver.handler.skillhandlers.Continuous;
import com.l2jhellas.gameserver.handler.skillhandlers.CpDam;
import com.l2jhellas.gameserver.handler.skillhandlers.Craft;
import com.l2jhellas.gameserver.handler.skillhandlers.DeluxeKey;
import com.l2jhellas.gameserver.handler.skillhandlers.Disablers;
import com.l2jhellas.gameserver.handler.skillhandlers.DrainSoul;
import com.l2jhellas.gameserver.handler.skillhandlers.Fishing;
import com.l2jhellas.gameserver.handler.skillhandlers.FishingSkill;
import com.l2jhellas.gameserver.handler.skillhandlers.GetPlayer;
import com.l2jhellas.gameserver.handler.skillhandlers.Harvest;
import com.l2jhellas.gameserver.handler.skillhandlers.Heal;
import com.l2jhellas.gameserver.handler.skillhandlers.ManaHeal;
import com.l2jhellas.gameserver.handler.skillhandlers.Manadam;
import com.l2jhellas.gameserver.handler.skillhandlers.Mdam;
import com.l2jhellas.gameserver.handler.skillhandlers.Pdam;
import com.l2jhellas.gameserver.handler.skillhandlers.Recall;
import com.l2jhellas.gameserver.handler.skillhandlers.Resurrect;
import com.l2jhellas.gameserver.handler.skillhandlers.SiegeFlag;
import com.l2jhellas.gameserver.handler.skillhandlers.Sow;
import com.l2jhellas.gameserver.handler.skillhandlers.Spoil;
import com.l2jhellas.gameserver.handler.skillhandlers.StrSiegeAssault;
import com.l2jhellas.gameserver.handler.skillhandlers.SummonFriend;
import com.l2jhellas.gameserver.handler.skillhandlers.SummonTreasureKey;
import com.l2jhellas.gameserver.handler.skillhandlers.Sweep;
import com.l2jhellas.gameserver.handler.skillhandlers.TakeCastle;
import com.l2jhellas.gameserver.handler.skillhandlers.Unlock;
import com.l2jhellas.gameserver.model.L2SkillType;

/**
 * This class ...
 * 
 * @version $Revision: 1.1.4.4 $ $Date: 2005/04/03 15:55:06 $
 */
public class SkillHandler
{
	private static Logger _log = Logger.getLogger(SkillHandler.class.getName());
	
	private static SkillHandler _instance;
	
	private final Map<L2SkillType, ISkillHandler> _datatable;
	
	public static SkillHandler getInstance()
	{
		if (_instance == null)
		{
			_instance = new SkillHandler();
		}
		return _instance;
	}
	
	private SkillHandler()
	{
		_datatable = new TreeMap<L2SkillType, ISkillHandler>();
		registerSkillHandler(new Blow());
		registerSkillHandler(new Pdam());
		registerSkillHandler(new Mdam());
		registerSkillHandler(new CpDam());
		registerSkillHandler(new Manadam());
		registerSkillHandler(new Heal());
		registerSkillHandler(new CombatPointHeal());
		registerSkillHandler(new ManaHeal());
		registerSkillHandler(new BalanceLife());
		registerSkillHandler(new Charge());
		registerSkillHandler(new Continuous());
		registerSkillHandler(new Resurrect());
		registerSkillHandler(new Spoil());
		registerSkillHandler(new Sweep());
		registerSkillHandler(new StrSiegeAssault());
		registerSkillHandler(new SummonFriend());
		registerSkillHandler(new SummonTreasureKey());
		registerSkillHandler(new Disablers());
		registerSkillHandler(new Recall());
		registerSkillHandler(new SiegeFlag());
		registerSkillHandler(new TakeCastle());
		registerSkillHandler(new Unlock());
		registerSkillHandler(new DrainSoul());
		registerSkillHandler(new Craft());
		registerSkillHandler(new Fishing());
		registerSkillHandler(new FishingSkill());
		registerSkillHandler(new BeastFeed());
		registerSkillHandler(new DeluxeKey());
		registerSkillHandler(new Sow());
		registerSkillHandler(new Harvest());
		registerSkillHandler(new GetPlayer());
		
		_log.config("SkillHandler: Loaded " + _datatable.size() + " handlers in total.");
	}
	
	public void registerSkillHandler(ISkillHandler handler)
	{
		L2SkillType[] types = handler.getSkillIds();
		for (L2SkillType t : types)
		{
			_datatable.put(t, handler);
		}
	}
	
	public ISkillHandler getSkillHandler(L2SkillType skillType)
	{
		return _datatable.get(skillType);
	}
	
	/**
	 * @return
	 */
	public int size()
	{
		return _datatable.size();
	}
}
