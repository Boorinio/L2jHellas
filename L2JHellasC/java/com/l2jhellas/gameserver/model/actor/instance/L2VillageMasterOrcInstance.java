package com.l2jhellas.gameserver.model.actor.instance;

import com.l2jhellas.gameserver.emum.ClassRace;
import com.l2jhellas.gameserver.model.base.ClassId;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;

public final class L2VillageMasterOrcInstance extends L2VillageMasterInstance
{
	public L2VillageMasterOrcInstance(int objectId, L2NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	protected final boolean checkVillageMasterRace(ClassId pclass)
	{
		if (pclass == null)
			return false;
		
		return pclass.getRace() == ClassRace.ORC;
	}
}