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
package com.l2jhellas.gameserver.taskmanager;

import java.util.Map;
import java.util.logging.Logger;

import javolution.util.FastMap;

import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.network.serverpackets.AutoAttackStop;

/**
 * @author Luca Baldi
 */
public class AttackStanceTaskManager
{
	protected static final Logger _log = Logger.getLogger(AttackStanceTaskManager.class.getName());

	protected Map<L2Character, Long> _attackStanceTasks = new FastMap<L2Character, Long>().setShared(true);

	private static AttackStanceTaskManager _instance;

	public AttackStanceTaskManager()
	{
		ThreadPoolManager.getInstance().scheduleAiAtFixedRate(new FightModeScheduler(), 0, 1000);
	}

	public static AttackStanceTaskManager getInstance()
	{
		if (_instance == null)
			_instance = new AttackStanceTaskManager();

		return _instance;
	}

	public void addAttackStanceTask(L2Character actor)
	{
		_attackStanceTasks.put(actor, System.currentTimeMillis());
	}

	public void removeAttackStanceTask(L2Character actor)
	{
		_attackStanceTasks.remove(actor);
	}

	public boolean getAttackStanceTask(L2Character actor)
	{
		return _attackStanceTasks.containsKey(actor);
	}

	private class FightModeScheduler implements Runnable
	{
		protected FightModeScheduler()
		{
			// Do nothing
		}

		@Override
		public void run()
		{
			Long current = System.currentTimeMillis();
			try
			{
				if (_attackStanceTasks != null)
					synchronized (this)
					{
						for (L2Character actor : _attackStanceTasks.keySet())
						{
							if ((current - _attackStanceTasks.get(actor)) > 15000)
							{
								actor.broadcastPacket(new AutoAttackStop(actor.getObjectId()));
								actor.getAI().setAutoAttacking(false);
								_attackStanceTasks.remove(actor);
							}
						}
					}
			}
			catch (Throwable e)
			{
				// TODO
				_log.warning(e.toString());
			}
		}
	}
}