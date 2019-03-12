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
package com.l2jhellas.gameserver.model.actor.poly;


import com.l2jhellas.gameserver.datatables.sql.ItemTable;
import com.l2jhellas.gameserver.datatables.sql.NpcData;
import com.l2jhellas.gameserver.emum.PolyType;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;

public class ObjectPoly
{
	private final L2Object _activeObject;
	private L2NpcTemplate _polyTemplate;
	private int _polyId;

	private PolyType _polyType = PolyType.DEFAULT;

	public ObjectPoly(L2Object activeObject)
	{
		_activeObject = activeObject;
	}

	public boolean polymorph(PolyType type, int id)
	{
		if (type == PolyType.NPC)
		{
			final L2NpcTemplate template = NpcData.getInstance().getTemplate(id);
			if (template == null)
				return false;
			
			_polyTemplate = template;
		}
		else if (type == PolyType.ITEM)
		{
			if (ItemTable.getInstance().getTemplate(id) == null)
				return false;
		}
		else if (type == PolyType.DEFAULT)
			return false;
		
		_polyType = type;
		_polyId = id;
		
		getActiveObject().decayMe();
		getActiveObject().spawnMe();
		
		return true;
	}
	
	public void unpolymorph()
	{
		_polyTemplate = null;
		_polyType = PolyType.DEFAULT;
		_polyId = 0;
		
		getActiveObject().decayMe();
		getActiveObject().spawnMe();
	}
	
	public final L2NpcTemplate getPolyTemplate()
	{
		return _polyTemplate;
	}
	
	public void setPolyInfo(PolyType polyType, int polyId)
	{
		setPolyId(polyId);
		setPolyType(polyType);
	}

	public final L2Object getActiveObject()
	{
		return _activeObject;
	}

	public final boolean isMorphed()
	{
		return getPolyType() != PolyType.DEFAULT;
	}

	public final int getPolyId()
	{
		return _polyId;
	}

	public final void setPolyId(int value)
	{
		_polyId = value;
	}

	public final PolyType getPolyType()
	{
		return _polyType;
	}
	
	public final void setPolyType(PolyType value)
	{
		_polyType = value;
	}
}