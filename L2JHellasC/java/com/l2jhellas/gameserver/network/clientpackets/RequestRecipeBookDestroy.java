package com.l2jhellas.gameserver.network.clientpackets;

import com.l2jhellas.gameserver.datatables.xml.RecipeData;
import com.l2jhellas.gameserver.model.L2RecipeList;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.RecipeBookItemList;

public final class RequestRecipeBookDestroy extends L2GameClientPacket
{
	private static final String _C__AC_REQUESTRECIPEBOOKDESTROY = "[C] AD RequestRecipeBookDestroy";
	
	private int _recipeID;
	
	@Override
	protected void readImpl()
	{
		_recipeID = readD();
	}
	
	@Override
	protected void runImpl()
	{
		L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar != null)
		{
			L2RecipeList rp = RecipeData.getInstance().getRecipeList(_recipeID - 1);
			if (rp == null)
				return;
			activeChar.unregisterRecipeList(_recipeID);
			
			RecipeBookItemList response = new RecipeBookItemList(rp.isDwarvenRecipe(), activeChar.getMaxMp());
			if (rp.isDwarvenRecipe())
				response.addRecipes(activeChar.getDwarvenRecipeBook());
			else
				response.addRecipes(activeChar.getCommonRecipeBook());
			
			activeChar.sendPacket(response);
		}
	}
	
	@Override
	public String getType()
	{
		return _C__AC_REQUESTRECIPEBOOKDESTROY;
	}
}