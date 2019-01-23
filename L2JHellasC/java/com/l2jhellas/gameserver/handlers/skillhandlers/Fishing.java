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
package com.l2jhellas.gameserver.handlers.skillhandlers;


import com.l2jhellas.Config;
import com.l2jhellas.gameserver.geodata.GeoEngine;
import com.l2jhellas.gameserver.handler.ISkillHandler;
import com.l2jhellas.gameserver.instancemanager.ZoneManager;
import com.l2jhellas.gameserver.model.Inventory;
import com.l2jhellas.gameserver.model.L2ItemInstance;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.L2Skill;
import com.l2jhellas.gameserver.model.L2SkillType;
import com.l2jhellas.gameserver.model.Location;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.zone.ZoneId;
import com.l2jhellas.gameserver.model.zone.type.L2FishingZone;
import com.l2jhellas.gameserver.model.zone.type.L2WaterZone;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.templates.L2WeaponType;
import com.l2jhellas.util.Rnd;
import com.l2jhellas.util.Util;

public class Fishing implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.FISHING
	};

	@Override
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		if (activeChar == null || !(activeChar instanceof L2PcInstance))
			return;

		final L2PcInstance player = (L2PcInstance) activeChar;

		if (!Config.ALLOWFISHING && !player.isGM())
		{
			player.sendMessage("Fishing is off");
			return;
		}
		
		if (player.isFishing())
		{
			if (player.GetFishCombat() != null)
				player.GetFishCombat().doDie(false);
			else
				player.EndFishing(false);
			
			player.sendPacket(SystemMessageId.FISHING_ATTEMPT_CANCELLED);
			return;
		}
		
		if (player.getActiveWeaponItem()!=null && player.getActiveWeaponItem().getItemType() != L2WeaponType.ROD)
		{
			player.sendPacket(SystemMessageId.FISHING_POLE_NOT_EQUIPPED);
			return;
		}

		final L2ItemInstance lure = player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_LHAND);
		
		if (lure == null)
		{
			player.sendPacket(SystemMessageId.BAIT_ON_HOOK_BEFORE_FISHING);
			return;
		}
		
		player.SetLure(lure);
		
		if (player.isInBoat())
		{
			player.sendPacket(SystemMessageId.CANNOT_FISH_ON_BOAT);
			return;
		}
		
		if (player.isInCraftMode() || player.isInStoreMode())
		{
			player.sendPacket(SystemMessageId.CANNOT_FISH_WHILE_USING_RECIPE_BOOK);
			return;
		}
		
		if (player.isInsideZone(ZoneId.WATER))
		{
			player.sendPacket(SystemMessageId.CANNOT_FISH_UNDER_WATER);
			return;
		}

		int distance = Rnd.get(90, 250);
		final double angle = Util.convertHeadingToDegree(player.getHeading());
		final double radian = Math.toRadians(angle);
		final double sin = Math.sin(radian);
		final double cos = Math.cos(radian);
		int x = (int) (player.getX() + (cos * distance));
		int y = (int) (player.getY() + (sin * distance));
		int z = 0;
		boolean allowFish = false;
		 
		L2FishingZone aimingTo = null;
		L2WaterZone water = null;
		
		//Avoid using expensive operation
		if(player.isInsideZone(ZoneId.FISHING))
		{
			aimingTo  = ZoneManager.getInstance().getZone(player.getX(), player.getY(),player.getZ(), L2FishingZone.class);						
		    water = ZoneManager.getInstance().getClosestWaterZone(player);
		}
		
		if (aimingTo != null && water!=null) 
		{
			z = water.getWaterZ();
			
			if( GeoEngine.canSeeTarget(player.getZ(),z))
			    allowFish = true;
		}
		
		if (!player.destroyItem("Consume", lure.getObjectId(), 1, player, false))
		{
			player.sendPacket(SystemMessageId.NOT_ENOUGH_BAIT);
			return;
		}
		
		if (allowFish)
			player.startFishing(new Location(x, y, z));
		else
			player.sendPacket(SystemMessageId.CANNOT_FISH_HERE);				
	}
	
	
	@Override
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}