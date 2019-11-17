package com.l2jhellas.gameserver.network.serverpackets;

import com.l2jhellas.gameserver.model.L2RecipeList;

public class RecipeBookItemList extends L2GameServerPacket
{
	private static final String _S__D6_RECIPEBOOKITEMLIST = "[S] D6 RecipeBookItemList";
	private L2RecipeList[] _recipes;
	private final boolean _isDwarvenCraft;
	private final int _maxMp;
	
	public RecipeBookItemList(boolean isDwarvenCraft, int maxMp)
	{
		_isDwarvenCraft = isDwarvenCraft;
		_maxMp = maxMp;
	}
	
	public void addRecipes(L2RecipeList[] recipeBook)
	{
		_recipes = recipeBook;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0xD6);
		
		writeD(_isDwarvenCraft ? 0x00 : 0x01); // 0 = Dwarven - 1 = Common
		writeD(_maxMp);
		
		if (_recipes == null)
			writeD(0);
		else
		{
			writeD(_recipes.length);// number of items in recipe book
			
			int i = 0;
			for (L2RecipeList recipe : _recipes)
			{		
				writeD(recipe.getId());
				writeD(++i);
			}
		}
	}
	
	@Override
	public String getType()
	{
		return _S__D6_RECIPEBOOKITEMLIST;
	}
}