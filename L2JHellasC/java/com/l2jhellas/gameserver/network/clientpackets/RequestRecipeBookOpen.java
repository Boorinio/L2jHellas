package com.l2jhellas.gameserver.network.clientpackets;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.controllers.RecipeController;

import java.util.logging.Logger;

public final class RequestRecipeBookOpen extends L2GameClientPacket
{
	private static Logger _log = Logger.getLogger(RequestRecipeBookOpen.class.getName());
	private static final String _C__AC_REQUESTRECIPEBOOKOPEN = "[C] AC RequestRecipeBookOpen";
	
	private boolean _isDwarvenCraft;
	
	@Override
	protected void readImpl()
	{
		_isDwarvenCraft = (readD() == 0);
		if (Config.DEBUG)
		{
			_log.info("RequestRecipeBookOpen : " + (_isDwarvenCraft ? "dwarvenCraft" : "commonCraft"));
		}
	}
	
	@Override
	protected void runImpl()
	{
		if (getClient().getActiveChar() == null)
			return;
		
		if (getClient().getActiveChar().getPrivateStoreType() != 0)
		{
			getClient().getActiveChar().sendMessage("Cannot use recipe book while trading");
			return;
		}
		
		RecipeController.getInstance().requestBookOpen(getClient().getActiveChar(), _isDwarvenCraft);
	}
	
	@Override
	public String getType()
	{
		return _C__AC_REQUESTRECIPEBOOKOPEN;
	}
}