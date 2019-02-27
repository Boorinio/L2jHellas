package com.l2jhellas.gameserver.emum;

public enum L2WeaponType
{

	NONE      ( 1, "Shield"), // Shields!!!
	SWORD     ( 2, "Sword"),
	BLUNT     ( 3, "Blunt"),
	DAGGER    ( 4, "Dagger"),
	BOW       ( 5, "Bow"),
	POLE      ( 6, "Pole"),
	ETC       ( 7, "Etc"),
	FIST      ( 8, "Fist"),
	DUAL      ( 9, "Dual Sword"),
	DUALFIST  (10, "Dual Fist"),
    BIGSWORD  (11, "Big Sword"), // Two Handed Swords
    PET       (12, "Pet"),
	ROD       (13, "Rod"),
	BIGBLUNT  (14, "Big Blunt"); // Two handed blunt

	private final int _id;
	private final String _name;

	private L2WeaponType(int id, String name)
	{
		_id = id;
		_name = name;
	}

	public int mask()
	{
		return 1 << _id;
	}

	@Override
	public String toString()
	{
		return _name;
	}
}