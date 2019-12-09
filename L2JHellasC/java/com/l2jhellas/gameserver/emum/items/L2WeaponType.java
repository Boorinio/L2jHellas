package com.l2jhellas.gameserver.emum.items;

public enum L2WeaponType
{
	
	NONE(1, "Shield",17), // Shields!!!
	SWORD(2, "Sword",17),
	BLUNT(3, "Blunt",17),
	DAGGER(4, "Dagger",17),
	BOW(5, "Bow",500),
	POLE(6, "Pole",25),
	ETC(7, "Etc",17),
	FIST(8, "Fist",17),
	DUAL(9, "Dual Sword",17),
	DUALFIST(10, "Dual Fist",17),
	BIGSWORD(11, "Big Sword",17), // Two Handed Swords
	PET(12, "Pet",17),
	ROD(13, "Rod",17),
	BIGBLUNT(14, "Big Blunt",17); // Two handed blunt
	
	private final int _id;
	private final String _name;
	private final int _range;
	
	private L2WeaponType(int id, String name,int range)
	{
		_id = id;
		_name = name;
		_range = range;
	}
	
	public int mask()
	{
		return 1 << _id;
	}
	
	public int getRange()
	{
		return _range;
	}
	
	@Override
	public String toString()
	{
		return _name;
	}
}