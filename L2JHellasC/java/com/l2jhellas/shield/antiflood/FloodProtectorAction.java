package com.l2jhellas.shield.antiflood;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import com.l2jhellas.gameserver.controllers.GameTimeController;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.util.StringUtil;

public final class FloodProtectorAction
{
	
	private static final Logger _log = Logger.getLogger(FloodProtectorAction.class.getName());
	
	private final L2PcInstance _player;
	
	private final FloodProtectorConfig _config;
	
	private volatile int _nextGameTick = GameTimeController.getInstance().getGameTicks();
	
	private final AtomicInteger _count = new AtomicInteger(0);
	
	private boolean _logged;
	
	private volatile boolean _punishmentInProgress;
	
	public FloodProtectorAction(final L2PcInstance player, final FloodProtectorConfig config)
	{
		super();
		_player = player;
		_config = config;
	}
	
	public boolean tryPerformAction(final String command)
	{
		final int curTick = GameTimeController.getInstance().getGameTicks();
		
		if (curTick < _nextGameTick || _punishmentInProgress)
		{
			if (_config.LOG_FLOODING && !_logged)
			{
				_log.warning(FloodProtectorAction.class.getName() + ": " + StringUtil.concat(_config.FLOOD_PROTECTOR_TYPE, ": Player [", _player.getName(), "] called command [", command, "] [~", String.valueOf((_config.FLOOD_PROTECTION_INTERVAL - (_nextGameTick - curTick)) * GameTimeController.MILLIS_IN_TICK), " ms] after previous command"));
				_logged = true;
			}
			
			_count.incrementAndGet();
			
			if (!_punishmentInProgress && _config.PUNISHMENT_LIMIT > 0 && _count.get() > _config.PUNISHMENT_LIMIT && _config.PUNISHMENT_TYPE != null)
			{
				_punishmentInProgress = true;
				
				if ("kick".equals(_config.PUNISHMENT_TYPE))
				{
					kickPlayer();
				}
				else if ("ban".equals(_config.PUNISHMENT_TYPE))
				{
					banAccount();
				}
				else if ("jail".equals(_config.PUNISHMENT_TYPE))
				{
					jailChar();
				}
				
				_punishmentInProgress = false;
			}
			
			return false;
		}
		
		if (_count.get() > 0)
		{
			if (_config.LOG_FLOODING)
				_log.warning(FloodProtectorAction.class.getName() + ": " + StringUtil.concat(_config.FLOOD_PROTECTOR_TYPE, ": Player [", _player.getName(), "] issued [", String.valueOf(_count), "] extra requests within [~", String.valueOf(_config.FLOOD_PROTECTION_INTERVAL * GameTimeController.MILLIS_IN_TICK), " ms]"));
		}
		
		_nextGameTick = curTick + _config.FLOOD_PROTECTION_INTERVAL;
		_logged = false;
		_count.set(0);
		
		return true;
	}
	
	private void kickPlayer()
	{
		_player.closeNetConnection(false);
		_log.warning(FloodProtectorAction.class.getName() + ": " + StringUtil.concat(_config.FLOOD_PROTECTOR_TYPE, ": Account [", _player.getAccountName(), "] kicked for flooding [char ", _player.getName(), "]"));
	}
	
	private void banAccount()
	{
		_player.setAccountAccesslevel(-100);
		_log.warning(FloodProtectorAction.class.getName() + ": " + StringUtil.concat(_config.FLOOD_PROTECTOR_TYPE, ": Account [", _player.getAccountName(), "] banned for flooding [char ", _player.getName(), "] ", _config.PUNISHMENT_TIME <= 0 ? "forever" : "for " + _config.PUNISHMENT_TIME + " mins"));
		_player.closeNetConnection(true);
	}
	
	private void jailChar()
	{
		_player.setInJail(true, _config.PUNISHMENT_TIME);
		_log.warning(FloodProtectorAction.class.getName() + ": " + StringUtil.concat(_config.FLOOD_PROTECTOR_TYPE, ": Player [", _player.getName(), "] jailed for flooding [char ", _player.getName(), "] ", _config.PUNISHMENT_TIME <= 0 ? "forever" : "for " + _config.PUNISHMENT_TIME + " mins"));
	}
}