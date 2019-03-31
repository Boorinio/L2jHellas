package Extensions.RaidEvent;

import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.model.L2Effect;
import com.l2jhellas.gameserver.model.actor.L2Summon;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.Ride;
import com.l2jhellas.gameserver.skills.SkillTable;

public class L2EventTeleporter implements Runnable
{
	private L2PcInstance _player = null;
	private int _coordinateX = 0;
	private int _coordinateY = 0;
	private int _coordinateZ = 0;
	boolean _removeBuffs;
	
	public L2EventTeleporter(L2PcInstance player, int coordinateX, int coordinateY, int coordinateZ, int delay, boolean removeBuffs)
	{
		_player = player;
		_coordinateX = coordinateX;
		_coordinateY = coordinateY;
		_coordinateZ = coordinateZ;
		_removeBuffs = removeBuffs;
		// Wait for the teleport
		long _delay = delay * 1000L;
		if (delay == 0)
			_delay = 0;
		ThreadPoolManager.getInstance().scheduleGeneral(this, _delay);
	}
	
	@Override
	public void run()
	{
		if (_player == null)
			return;
		
		if (_player.getActiveTradeList() != null)
			_player.cancelActiveTrade();
		
		if (_player.isMounted())
		{
			if (_player.isFlying())
				_player.removeSkill(SkillTable.getInstance().getInfo(4289, 1));
			Ride dismount = new Ride(_player.getObjectId(), Ride.ACTION_DISMOUNT, 0);
			_player.broadcastPacket(dismount);
			_player.setMountType(0);
			_player.setMountObjectID(0);
		}
		L2Summon summon = _player.getPet();
		if (_removeBuffs && summon != null)
			summon.unSummon(_player);
		if (_removeBuffs)
		{
			for (L2Effect effect : _player.getAllEffects())
			{
				if (effect != null)
					effect.exit();
			}
		}
		_player.setCurrentCp(_player.getMaxCp() + 5000);
		_player.setCurrentHp(_player.getMaxHp() + 5000);
		_player.setCurrentMp(_player.getMaxMp() + 5000);
		_player.teleToLocation(_coordinateX, _coordinateY, _coordinateZ, false);
		_player.broadcastStatusUpdate();
		_player.broadcastUserInfo();
	}
}