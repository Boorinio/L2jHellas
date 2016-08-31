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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.script.ScriptException;

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

	private final List<Quest> _quests = new ArrayList<Quest>();
	
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
		// Get quest by questId.
				Quest q = getQuest(questId);
				
				// Quest does not exist, return.
				if (q == null)
					return false;
				
				// Reload the quest.
				return q.reload();
	}
	public final void report()
	{
		_log.info(QuestManager.class.getSimpleName() + ": Loaded: " + _quests.size() + " quests.");
	}

	public final Quest getQuest(String name)
	{
		// Check all quests.
				for (Quest q : _quests)
				{
					// If quest found, return him.
					if (q.getName().equalsIgnoreCase(name))
						return q;
				}
				// Otherwise return null.
				return null;
	}

	public final Quest getQuest(int questId)
	{
		// Check all quests.
				for (Quest q : _quests)
				{
					// If quest found, return him.
					if (q.getQuestId() == questId)
						return q;
				}
				// Otherwise return null.
				return null;
	}

	public final void addQuest(Quest quest)
	{
		// Quest does not exist, return.
				if (quest == null)
					return;
				
				// Quest already loaded, unload id.
				Quest old = getQuest(quest.getQuestId());
				if (old != null && old.isRealQuest())
				{
					old.unload();
				//	_log.info("QuestManager: Replaced: (" + old.getName() + ") with a new version (" + quest.getName() + ").");
					
				}
				
				// Add new quest.
				_quests.add(quest);
	}

	public final boolean removeQuest(Quest q)
	{
		return _quests.remove(q);
	}

	/**
	 * @see com.l2jhellas.gameserver.scripting.ScriptManager#getAllManagedScripts()
	 */
	@Override
	public List<Quest> getAllManagedScripts()
	{
		return _quests;
	}

	@Override
	public boolean unload(Quest ms)
	{
		return this.removeQuest(ms);
	}

	@Override
	public String getScriptManagerName()
	{
		return "QuestManager";
	}

	public void reloadAllQuests()
	{
		_log.info("QuestManager: Reloading quests.");
		
		// Load all quests again.
			for (Quest quest : _quests)
			{
				if (quest != null)
					quest.unload(false);
			}
			
			// Clear the quest list.
			_quests.clear();
			
			final File file = new File(L2ScriptEngineManager.SCRIPT_FOLDER, "handlers/ScriptLoader.java");
			try
			{
				L2ScriptEngineManager.getInstance().executeScript(file);
			}
			catch (ScriptException e)
			{
			    L2ScriptEngineManager.reportScriptFileError(file, e);				
			}		
		
	}
}