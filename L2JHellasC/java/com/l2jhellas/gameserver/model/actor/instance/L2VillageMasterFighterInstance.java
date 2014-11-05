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
package com.l2jhellas.gameserver.model.actor.instance;

import com.l2jhellas.gameserver.model.base.ClassType;
import com.l2jhellas.gameserver.model.base.PlayerClass;
import com.l2jhellas.gameserver.model.base.PlayerRace;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;

public final class L2VillageMasterFighterInstance extends L2VillageMasterInstance
{
	public L2VillageMasterFighterInstance(int objectId, L2NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	protected final boolean checkVillageMasterRace(PlayerClass pclass)
	{
		if (pclass == null)
			return false;
		
		return pclass.isOfRace(PlayerRace.Human) || pclass.isOfRace(PlayerRace.LightElf);
	}
	
	@Override
	protected final boolean checkVillageMasterTeachType(PlayerClass pclass)
	{
		if (pclass == null)
			return false;
		
		return pclass.isOfType(ClassType.Fighter);
	}
}