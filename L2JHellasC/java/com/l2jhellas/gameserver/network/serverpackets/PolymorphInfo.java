package com.l2jhellas.gameserver.network.serverpackets;

import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;

public class PolymorphInfo extends L2GameServerPacket
{
	protected int _objId, _polyId, _x, _y, _z, _heading;
	protected int _mAtkSpd, _pAtkSpd;
	protected int _runSpd, _walkSpd;
	protected int _rhand, _lhand, _abnormalEffect, _enchantEffect;
	protected int _swimSpd;
	protected float _movementSpeedMulti, _attackspeedmulti;
	protected double _collisionHeight, _collisionRadius;
	
	protected String _name = "", _title = "";
	protected boolean _isRunning, _isInCombat, _isAlikeDead;
	
	private final L2NpcTemplate _template;
	
	public PolymorphInfo(L2PcInstance cha, L2NpcTemplate template)
	{
		_objId = cha.getObjectId();
		_polyId = cha.getPoly().getPolyId();
		_x = cha.getX();
		_y = cha.getY();
		_z = cha.getZ();
		_heading = cha.getHeading();
		_mAtkSpd = cha.getMAtkSpd();
		_pAtkSpd = cha.getPAtkSpd();
		_runSpd = cha.getStat().getRunSpeed();
		_walkSpd = cha.getStat().getWalkSpeed();
		_movementSpeedMulti = cha.getStat().getMovementSpeedMultiplier();
		_attackspeedmulti = cha.getStat().getAttackSpeedMultiplier();
		_template = template;
		_swimSpd = cha.getStat().getRunSpeed() / 2;
		_rhand = _template.rhand;
		_lhand = _template.lhand;
		_collisionHeight = _template.collisionHeight;
		_collisionRadius = _template.collisionRadius;
		_isRunning = cha.isRunning();
		_isInCombat = cha.isInCombat();
		_isAlikeDead = cha.isAlikeDead();
		_name = cha.getName();
		_title = cha.getTitle();
		_abnormalEffect = cha.getAbnormalEffect();
		_enchantEffect = cha.getEnchantEffect();
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0x16);
		writeD(_objId);
		writeD(_polyId + 1000000);
		writeD(1);
		writeD(_x);
		writeD(_y);
		writeD(_z);
		writeD(_heading);
		writeD(0x00);
		writeD(_mAtkSpd);
		writeD(_pAtkSpd);
		writeD(_runSpd);
		writeD(_walkSpd);
		writeD(_swimSpd);
		writeD(_swimSpd);
		writeD(_runSpd);
		writeD(_walkSpd);
		writeD(_runSpd);
		writeD(_walkSpd);
		writeF(_movementSpeedMulti);
		writeF(_attackspeedmulti);
		writeF(_collisionRadius);
		writeF(_collisionHeight);
		writeD(_rhand);
		writeD(0);
		writeD(_lhand);
		writeC(1);
		writeC(_isRunning ? 1 : 0);
		writeC(_isInCombat ? 1 : 0);
		writeC(_isAlikeDead ? 1 : 0);
		writeC(0);
		writeS(_name);
		writeS(_title);
		writeD(0x00);
		writeD(0x00);
		writeD(0x00);
		writeD(_abnormalEffect);
		writeD(0x00);
		writeD(0x00);
		writeD(0x00);
		writeD(0x00);
		writeC(0x00);
		writeC(0x00);
		writeF(_collisionRadius);
		writeF(_collisionHeight);
		writeD(_enchantEffect);
		writeD(0x00);
	}
}