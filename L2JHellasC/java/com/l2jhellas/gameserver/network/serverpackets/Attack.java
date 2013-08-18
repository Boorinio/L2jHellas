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
package com.l2jhellas.gameserver.network.serverpackets;

import com.l2jhellas.gameserver.model.L2Character;
import com.l2jhellas.gameserver.model.L2Object;

/**
 * @formatter:off<BR>
 *                    sample<BR>
 *                    06 8f19904b 2522d04b 00000000 80 950c0000 4af50000 08f2ffff 0000 - 0 damage (missed 0x80)<BR>
 *                    06 85071048 bc0e504b 32000000 10 fc41ffff fd240200 a6f5ffff 0100 bc0e504b 33000000 10 3....<BR>
 *                    format
 *                    dddc dddh (ddc)
 * @formatter:on
 */
public class Attack extends L2GameServerPacket
{
	private class Hit
	{
		protected int _targetId;
		protected int _damage;
		protected int _flags;

		Hit(L2Object target, int damage, boolean miss, boolean crit, byte shld)
		{
			_targetId = target.getObjectId();
			_damage = damage;
			if (soulshot)
				_flags |= 0x10 | _grade;
			if (crit)
				_flags |= 0x20;
			if (shld > 0)
				_flags |= 0x40;
			if (miss)
				_flags |= 0x80;
		}
	}

	// dh
	private static final String _S__06_ATTACK = "[S] 06 Attack";
	protected final int _attackerObjId;
	public final boolean soulshot;
	protected int _grade;
	private final int _x;
	private final int _y;
	private final int _z;
	private final int _tx;
	private final int _ty;
	private final int _tz;
	L2Object _defender;
	private Hit[] _hits;

	/**
	 * @param attacker
	 *        the attacker L2Character
	 * @param ss
	 *        true if using SoulShots
	 */
	public Attack(L2Character attacker, L2Object target, boolean ss, int grade)
	{
		_attackerObjId = attacker.getObjectId();
		soulshot = ss;
		_grade = grade;
		_x = attacker.getX();
		_y = attacker.getY();
		_z = attacker.getZ();
		_tx = target.getX();
		_ty = target.getY();
		_tz = target.getZ();
		_defender = target;
		_hits = new Hit[0];
	}

	/**
	 * Add this hit (target, damage, miss, critical, shield) to the Server-Client packet Attack.
	 */
	public void addHit(L2Object target, int damage, boolean miss, boolean crit, byte shld1)
	{
		// Get the last position in the hits table
		int pos = _hits.length;

		// Create a new Hit object
		Hit[] tmp = new Hit[pos + 1];

		// Add the new Hit object to hits table
		for (int i = 0; i < _hits.length; i++)
			tmp[i] = _hits[i];
		tmp[pos] = new Hit(target, damage, miss, crit, shld1);
		_hits = tmp;
	}

	/**
	 * Return True if the Server-Client packet Attack conatins at least 1 hit.
	 */
	public boolean hasHits()
	{
		return _hits.length > 0;
	}

	@Override
	protected final void writeImpl()
	{
		writeC(0x05);

		writeD(_attackerObjId);
		writeD(_defender.getObjectId());
		writeD(_hits[0]._damage);
		writeC(_hits[0]._flags);
		writeD(_x);
		writeD(_y);
		writeD(_z);
		writeH(_hits.length - 1);

		if (_hits.length > 1) // prevent sending useless packet while there is only one target.
		{
			for (Hit temp : _hits)
			{
				writeD(temp._targetId);
				writeD(temp._damage);
				writeC(temp._flags);
			}
		}

		writeD(_tx);
		writeD(_ty);
		writeD(_tz);
	}

	@Override
	public String getType()
	{
		return _S__06_ATTACK;
	}
}