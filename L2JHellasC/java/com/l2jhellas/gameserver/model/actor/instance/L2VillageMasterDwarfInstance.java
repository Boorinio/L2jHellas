package com.l2jhellas.gameserver.model.actor.instance;

import com.l2jhellas.gameserver.emum.player.ClassId;
import com.l2jhellas.gameserver.emum.player.ClassRace;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;

public final class L2VillageMasterDwarfInstance extends L2VillageMasterInstance
{
	public L2VillageMasterDwarfInstance(int objectId, L2NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	protected final boolean checkVillageMasterRace(ClassId pclass)
	{
		if (pclass == null)
			return false;
		
		return pclass.getRace() == ClassRace.DWARF;
	}
}