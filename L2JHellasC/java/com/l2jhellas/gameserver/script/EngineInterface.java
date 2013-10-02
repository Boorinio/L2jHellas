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
package com.l2jhellas.gameserver.script;

import com.l2jhellas.gameserver.Announcements;
import com.l2jhellas.gameserver.controllers.GameTimeController;
import com.l2jhellas.gameserver.controllers.RecipeController;
import com.l2jhellas.gameserver.datatables.LevelUpData;
import com.l2jhellas.gameserver.datatables.sql.CharNameTable;
import com.l2jhellas.gameserver.datatables.sql.ClanTable;
import com.l2jhellas.gameserver.datatables.sql.ItemTable;
import com.l2jhellas.gameserver.datatables.sql.MapRegionTable;
import com.l2jhellas.gameserver.datatables.sql.SpawnTable;
import com.l2jhellas.gameserver.datatables.xml.CharTemplateData;
import com.l2jhellas.gameserver.datatables.xml.NpcData;
import com.l2jhellas.gameserver.datatables.xml.SkillTreeData;
import com.l2jhellas.gameserver.datatables.xml.TeleportLocationData;
import com.l2jhellas.gameserver.idfactory.IdFactory;
import com.l2jhellas.gameserver.skills.SkillTable;

/**
 * @author Luis Arias
 */
public interface EngineInterface
{
	// * keep the references of Singletons to prevent garbage collection
	public CharNameTable charNametable = CharNameTable.getInstance();

	public IdFactory idFactory = IdFactory.getInstance();
	public ItemTable itemTable = ItemTable.getInstance();

	public SkillTable skillTable = SkillTable.getInstance();
	
	public RecipeController recipeController = RecipeController.getInstance();

	public SkillTreeData skillTreeTable = SkillTreeData.getInstance();
	public CharTemplateData charTemplates = CharTemplateData.getInstance();
	public ClanTable clanTable = ClanTable.getInstance();

	public NpcData npcTable = NpcData.getInstance();

	public TeleportLocationData teleTable = TeleportLocationData.getInstance();
	public LevelUpData levelUpData = LevelUpData.getInstance();
	//public L2World world = L2World.getInstance();
	public SpawnTable spawnTable = SpawnTable.getInstance();
	public GameTimeController gameTimeController = GameTimeController.getInstance();
	public Announcements announcements = Announcements.getInstance();
	public MapRegionTable mapRegions = MapRegionTable.getInstance();

	// public ArrayList getAllPlayers();
	// public Player getPlayer(String characterName);
	public void addQuestDrop(int npcID, int itemID, int min, int max, int chance, String questID, String[] states);

	public void addEventDrop(int[] items, int[] count, double chance, DateRange range);

	public void onPlayerLogin(String[] message, DateRange range);
}