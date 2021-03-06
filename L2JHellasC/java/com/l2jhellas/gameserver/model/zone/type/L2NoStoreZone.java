package com.l2jhellas.gameserver.model.zone.type;

import com.l2jhellas.gameserver.enums.ZoneId;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.zone.L2ZoneType;

public class L2NoStoreZone extends L2ZoneType
{
	public L2NoStoreZone(final int id)
	{
		super(id);
	}
	
	@Override
	protected void onEnter(final L2Character character)
	{
		if (character instanceof L2PcInstance)
			character.setInsideZone(ZoneId.NO_STORE, true);
	}
	
	@Override
	protected void onExit(final L2Character character)
	{
		if (character instanceof L2PcInstance)
			character.setInsideZone(ZoneId.NO_STORE, false);
	}
	
	@Override
	public void onDieInside(final L2Character character)
	{
	}
	
	@Override
	public void onReviveInside(final L2Character character)
	{
	}
}