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
package com.l2jhellas.gameserver.instancemanager;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastMap;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.model.quest.Quest;
import com.l2jhellas.gameserver.scripting.L2ScriptEngineManager;
import com.l2jhellas.gameserver.scripting.ScriptManager;

public class QuestManager extends ScriptManager<Quest>
{
	protected static final Logger _log = Logger.getLogger(QuestManager.class.getName());

	private static QuestManager _instance;

	public static final QuestManager getInstance()
	{
		if (_instance == null)
		{
			_instance = new QuestManager();
		}
		return _instance;
	}

	private final Map<String, Quest> _quests = new FastMap<String, Quest>();

	public QuestManager()
	{
	}

	public final boolean reload(String questFolder)
	{
		Quest q = getQuest(questFolder);
		if (q == null)
			return false;
		return q.reload();
	}

	/**
	 * Reloads a the quest given by questId.<BR>
	 * <B>NOTICE: Will only work if the quest name is equal the quest folder name</B>
	 *
	 * @param questId
	 *        The id of the quest to be reloaded
	 * @return true if reload was successful, false otherwise
	 */
	public final boolean reload(int questId)
	{
		Quest q = this.getQuest(questId);
		if (q == null)
			return false;
		return q.reload();
	}

	public final void reloadAllQuests()
	{
		_log.log(Level.INFO, getClass().getSimpleName() + ": Reloading Server Scripts.");
		try
		{
			// unload all scripts
			for (Quest quest : _quests.values())
			{
				if (quest != null)
				{
					quest.unload();
				}
			}
			// now load all scripts
			File scripts = new File(Config.DATAPACK_ROOT + "/data/scripts.cfg");
			L2ScriptEngineManager.getInstance().executeScriptList(scripts);
			QuestManager.getInstance().report();
		}
		catch (IOException ioe)
		{
			_log.log(Level.SEVERE, getClass().getSimpleName() + ": Failed loading scripts.cfg, no script going to be loaded.");
		}
	}

	public final void report()
	{
		_log.log(Level.INFO, getClass().getSimpleName() + ": Loaded: " + _quests.size() + " quests.");
	}

	public final void save()
	{
		for (Quest q : _quests.values())
		{
			q.saveGlobalData();
		}
	}

	public final Quest getQuest(String name)
	{
		return _quests.get(name);
	}

	public final Quest getQuest(int questId)
	{
		for (Quest q : _quests.values())
		{
			if (q.getQuestIntId() == questId)
				return q;
		}
		return null;
	}

	public final void addQuest(Quest newQuest)
	{
		if (newQuest == null)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Quest argument cannot be null");
		}
		Quest old = _quests.get(newQuest.getName());

		// FIXME: unloading the old quest at this point is a tad too late.
		// the new quest has already initialized itself and read the data, starting
		// an unpredictable number of tasks with that data. The old quest will now
		// save data which will never be read.
		// However, requesting the newQuest to re-read the data is not necessarily a
		// good option, since the newQuest may have already started timers, spawned NPCs
		// or taken any other action which it might re-take by re-reading the data.
		// the current solution properly closes the running tasks of the old quest but
		// ignores the data; perhaps the least of all evils...
		if (old != null)
		{
			old.unload();
			_log.log(Level.INFO, getClass().getSimpleName() + ": Replaced: (" + old.getName() + ") with (" + newQuest.getName() + ")");
		}
		_quests.put(newQuest.getName(), newQuest);
	}

	public final boolean removeQuest(Quest q)
	{
		return _quests.remove(q.getName()) != null;
	}

	@Override
	public Iterable<Quest> getAllManagedScripts()
	{
		return _quests.values();
	}

	@Override
	public boolean unload(Quest ms)
	{
		ms.saveGlobalData();
		return this.removeQuest(ms);
	}

	@Override
	public String getScriptManagerName()
	{
		return "QuestManager";
	}
}