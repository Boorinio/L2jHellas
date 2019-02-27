package com.l2jhellas.gameserver.emum;

public enum L2ArmorType
{
	NONE  ( 1, "None"),
	LIGHT ( 2, "Light"),
	HEAVY ( 3, "Heavy"),
	MAGIC ( 4, "Magic"),
    PET   ( 5, "Pet");

	final int _id;
	final String _name;

	/**
	 * @param id: int designating the ID of the ArmorType
	 * @param name: String designating the name of the ArmorType
	 */
	L2ArmorType(int id, String name)
	{
		_id = id;
		_name = name;
	}

	/**
	 * Return the ID of the ArmorType after applying a mask.
	 */
	public int mask()
	{
		return 1 << (_id + 16);
	}

	/**
	 * Return the name of the ArmorType
	 */
	@Override
	public String toString()
	{
		return _name;
	}
}