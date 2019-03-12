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

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.datatables.sql.NpcData;
import com.l2jhellas.gameserver.emum.AbnormalEffect;
import com.l2jhellas.gameserver.instancemanager.CursedWeaponsManager;
import com.l2jhellas.gameserver.model.Inventory;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

/**
 * 0000: 03 32 15 00 00 44 fe 00 00 80 f1 ff ff 00 00 00 .2...D..........
 * <p>
 * 0010: 00 6b b4 c0 4a 45 00 6c 00 6c 00 61 00 6d 00 69 .k..JE.l.l.a.m.i
 * <p>
 * 0020: 00 00 00 01 00 00 00 01 00 00 00 12 00 00 00 00 ................
 * <p>
 * 0030: 00 00 00 2a 00 00 00 42 00 00 00 71 02 00 00 31 ...*...B...q...1
 * <p>
 * 0040: 00 00 00 18 00 00 00 1f 00 00 00 25 00 00 00 00 ...........%....
 * <p>
 * 0050: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 f9 ................
 * <p>
 * 0060: 00 00 00 b3 01 00 00 00 00 00 00 00 00 00 00 7d ...............}
 * <p>
 * 0070: 00 00 00 5a 00 00 00 32 00 00 00 32 00 00 00 00 ...Z...2...2....
 * <p>
 * 0080: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 67 ...............g
 * <p>
 * 0090: 66 66 66 66 66 f2 3f 5f 63 97 a8 de 1a f9 3f 00 fffff.?_c.....?.
 * <p>
 * 00a0: 00 00 00 00 00 1e 40 00 00 00 00 00 00 37 40 01 .............7..
 * <p>
 * 00b0: 00 00 00 01 00 00 00 01 00 00 00 00 00 c1 0c 00 ................
 * <p>
 * 00c0: 00 00 00 00 00 00 00 00 00 01 01 00 00 00 00 00 ................
 * <p>
 * 00d0: 00 00
 * <p>
 * <p>
 * dddddSdddddddddddddddddddddddddddffffdddSdddccccccc (h)
 * <p>
 * dddddSdddddddddddddddddddddddddddffffdddSdddddccccccch dddddSddddddddddddddddddddddddddddffffdddSdddddccccccch (h) c (dchd) ddc dcc c cddd d
 * dddddSdddddddddddddddhhhhhhhhhhhhhhhhhhhhhhhhddddddddddddddffffdddSdddddccccccch [h] c (ddhd) ddc c ddc cddd d d dd d d d
 */
public class CharInfo extends L2GameServerPacket
{
	private static final String _S__03_CHARINFO = "[S] 03 CharInfo";
	private L2PcInstance _activeChar;
	private Inventory _inv;
	private int _x, _y, _z, _heading;
	private int _mAtkSpd, _pAtkSpd;
	private int _runSpd, _walkSpd, _swimRunSpd, _swimWalkSpd, _flRunSpd, _flWalkSpd, _flyRunSpd, _flyWalkSpd;
	private float _moveMultiplier;
	private int _maxCp;

	/**
	 * @param cha
	 */
	public CharInfo(L2PcInstance cha)
	{
		_activeChar = cha;
		_inv = cha.getInventory();
		_x = _activeChar.getX();
		_y = _activeChar.getY();
		_z = _activeChar.getZ();
		_heading = _activeChar.getHeading();
		_mAtkSpd = _activeChar.getMAtkSpd();
		_pAtkSpd = _activeChar.getPAtkSpd();
		_moveMultiplier = _activeChar.getMovementSpeedMultiplier();
		_runSpd = (int) (_activeChar.getRunSpeed() / _moveMultiplier);
		_walkSpd = (int) (_activeChar.getWalkSpeed() / _moveMultiplier);
		_swimRunSpd = _flRunSpd = _flyRunSpd = _runSpd;
		_swimWalkSpd = _flWalkSpd = _flyWalkSpd = _walkSpd;
		_maxCp = _activeChar.getMaxCp();
	}

	@Override
	protected final void writeImpl()
	{
		boolean canSeeInvis = false;
		
		if (_activeChar.getAppearance().getInvisible())
		{
			L2PcInstance tmp = getClient().getActiveChar();
			
			if (tmp != null && tmp.isGM())
				canSeeInvis = true;
		}

		writeC(0x03);
		writeD(_x);
		writeD(_y);
		writeD(_z);
		writeD(_heading);
		writeD(_activeChar.getObjectId());
		writeS(_activeChar.getName());
		writeD(_activeChar.getRace().ordinal());
		writeD(_activeChar.getAppearance().getSex().ordinal());
		
		writeD(_activeChar.getClassIndex() == 0?_activeChar.getClassId().getId():_activeChar.getBaseClass());

		writeD(_inv.getPaperdollItemId(Inventory.PAPERDOLL_DHAIR));
		writeD(_inv.getPaperdollItemId(Inventory.PAPERDOLL_HEAD));
		writeD(_inv.getPaperdollItemId(Inventory.PAPERDOLL_RHAND));
		writeD(_inv.getPaperdollItemId(Inventory.PAPERDOLL_LHAND));
		writeD(_inv.getPaperdollItemId(Inventory.PAPERDOLL_GLOVES));
		writeD(_inv.getPaperdollItemId(Inventory.PAPERDOLL_CHEST));
		writeD(_inv.getPaperdollItemId(Inventory.PAPERDOLL_LEGS));
		writeD(_inv.getPaperdollItemId(Inventory.PAPERDOLL_FEET));
		writeD(_inv.getPaperdollItemId(Inventory.PAPERDOLL_BACK));
		writeD(_inv.getPaperdollItemId(Inventory.PAPERDOLL_LRHAND));
		writeD(_inv.getPaperdollItemId(Inventory.PAPERDOLL_HAIR));
		writeD(_inv.getPaperdollItemId(Inventory.PAPERDOLL_FACE));

		// c6 new h's
		writeH(0x00);
		writeH(0x00);
		writeH(0x00);
		writeH(0x00);
		writeD(_inv.getPaperdollAugmentationId(Inventory.PAPERDOLL_RHAND));
		writeH(0x00);
		writeH(0x00);
		writeH(0x00);
		writeH(0x00);
		writeH(0x00);
		writeH(0x00);
		writeH(0x00);
		writeH(0x00);
		writeH(0x00);
		writeH(0x00);
		writeH(0x00);
		writeH(0x00);
		writeD(_inv.getPaperdollAugmentationId(Inventory.PAPERDOLL_LRHAND));
		writeH(0x00);
		writeH(0x00);
		writeH(0x00);
		writeH(0x00);

		writeD(_activeChar.getPvpFlag());
		writeD(_activeChar.getKarma());

		writeD(_mAtkSpd);
		writeD(_pAtkSpd);

		writeD(_activeChar.getPvpFlag());
		writeD(_activeChar.getKarma());

		writeD(_runSpd);
		writeD(_walkSpd);
		writeD(_swimRunSpd); // swimspeed
		writeD(_swimWalkSpd); // swimspeed
		writeD(_flRunSpd);
		writeD(_flWalkSpd);
		writeD(_flyRunSpd);
		writeD(_flyWalkSpd);
		writeF(_activeChar.getMovementSpeedMultiplier()); // _activeChar.getProperMultiplier()
		writeF(_activeChar.getAttackSpeedMultiplier()); // _activeChar.getAttackSpeedMultiplier()
		
		
		if (_activeChar.getMountType() != 0)
		{
			writeF(NpcData.getInstance().getTemplate(_activeChar.getMountObjectID()).getCollisionRadius());
			writeF(NpcData.getInstance().getTemplate(_activeChar.getMountObjectID()).getCollisionHeight());
		}
		else
		{
			writeF(_activeChar.getBaseTemplate().getCollisionRadius(_activeChar.getAppearance().getSex()));
			writeF(_activeChar.getBaseTemplate().getCollisionHeight(_activeChar.getAppearance().getSex()));
		}

		writeD(_activeChar.getAppearance().getHairStyle());
		writeD(_activeChar.getAppearance().getHairColor());
		writeD(_activeChar.getAppearance().getFace());

		writeS(canSeeInvis ? "Invisible" : _activeChar.getTitle());

		writeD(_activeChar.getClanId());
		writeD(_activeChar.getClanCrestId());
		writeD(_activeChar.getAllyId());
		writeD(_activeChar.getAllyCrestId());
		// In UserInfo leader rights and siege flags, but here found nothing??
		// Therefore RelationChanged packet with that info is required
		writeD(0);

		writeC(_activeChar.isSitting() ? 0 : 1); // standing = 1  sitting = 0
		writeC(_activeChar.isRunning() ? 1 : 0); // running = 1   walking = 0
		writeC(_activeChar.isInCombat() ? 1 : 0);
		writeC(_activeChar.isAlikeDead() ? 1 : 0);

		writeC(canSeeInvis ? 0 : _activeChar.getAppearance().getInvisible() ? 1 : 0);

		writeC(_activeChar.getMountType()); // 1 on strider   2 on wyvern   0 no mount
		writeC(_activeChar.getPrivateStoreType()); //  1 - sellshop

		writeH(_activeChar.getCubics().size());
		
		for (int id : _activeChar.getCubics().keySet())
			writeH(id);
		
		writeC(_activeChar.isInPartyMatchRoom() ? 1 : 0);

		writeD(canSeeInvis ? (_activeChar.getAbnormalEffect() | AbnormalEffect.STEALTH.getMask()) : _activeChar.getAbnormalEffect());
	
		writeC(_activeChar.getRecomLeft()); //Changed by Thorgrim
		writeH(_activeChar.getRecomHave()); //Blue value for name (0 = white, 255 = pure blue)
		writeD(_activeChar.getClassId().getId());

		writeD(_maxCp);
		writeD((int) _activeChar.getCurrentCp());
		writeC(_activeChar.isMounted() ? 0 : _activeChar.getEnchantEffect());

		writeC(_activeChar.getTeam().getId());

		writeD(_activeChar.getClanCrestLargeId());
		writeC(_activeChar.isNoble() ? 1 : 0); // Symbol on char menu ctrl+I
		writeC((_activeChar.isHero() || (_activeChar.isGM() && Config.GM_HERO_AURA)) ? 1 : 0); // Hero Aura

		writeC(_activeChar.isFishing() ? 1 : 0); //0x01: Fishing Mode (Cant be undone by setting back to 0)
		writeD(_activeChar.GetFishx());
		writeD(_activeChar.GetFishy());
		writeD(_activeChar.GetFishz());

		writeD(_activeChar.getAppearance().getNameColor());

		writeD(_heading);

		writeD(_activeChar.getPledgeClass());
		writeD(_activeChar.getPledgeType());

		writeD(_activeChar.getAppearance().getTitleColor());

		writeC(_activeChar.isCursedWeaponEquiped() ? CursedWeaponsManager.getInstance().getLevel(_activeChar.getCursedWeaponEquipedId()) : 0);

		writeD(_activeChar.getClanId() > 0 && _activeChar.getClan() != null ? _activeChar.getClan().getReputationScore() : 0);
	}

	@Override
	public String getType()
	{
		return _S__03_CHARINFO;
	}
}