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
package com.l2jhellas.gameserver.datatables.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.PackRoot;
import com.l2jhellas.Config;
import com.l2jhellas.gameserver.cache.InfoCache;
import com.l2jhellas.gameserver.model.L2DropData;
import com.l2jhellas.gameserver.model.L2MinionData;
import com.l2jhellas.gameserver.model.L2Skill;
import com.l2jhellas.gameserver.model.base.ClassId;
import com.l2jhellas.gameserver.skills.SkillTable;
import com.l2jhellas.gameserver.skills.Stats;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;
import com.l2jhellas.gameserver.templates.StatsSet;
import com.l2jhellas.util.L2HashMap;
import com.l2jhellas.util.database.L2DatabaseFactory;

public class NpcData
{
	protected static Logger _log = Logger.getLogger(NpcData.class.getName());

	// SQL Queries
	private static final String RESTORE_SELECT_NPC = "SELECT * FROM npc";
	private static final String RESTORE_SELECT_CUSTOM_NPC = "SELECT * FROM custom_npc";
	private static final String RESTORE_NPC_SKILLS = "SELECT * FROM npcskills";
	private static final String RESTORE_DROPLIST = "SELECT * FROM droplist ORDER BY mobId, chance DESC";
	private static final String RESTORE_CUSTOM_DROPLIST = "SELECT * FROM custom_droplist ORDER BY mobId, chance DESC";
	private static final String RELOAD_NPC = "SELECT * FROM npc WHERE id=?";
	private static final String RELOAD_CUSTOM_NPC = "SELECT * FROM custom_npc WHERE id=?";
	
	private static NpcData _instance;

	private final ConcurrentMap<Integer, L2NpcTemplate> _npcs;

	public static NpcData getInstance()
	{
		if (_instance == null)
		{
			_instance = new NpcData();
		}

		return _instance;
	}

	private NpcData()
	{
		_npcs = new L2HashMap<Integer, L2NpcTemplate>();

		restoreNpcData();
	}

	private void restoreNpcData()
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			try
			{
				PreparedStatement statement = con.prepareStatement(RESTORE_SELECT_NPC);
				ResultSet npcdata = statement.executeQuery();
				fillNpcTable(npcdata, false);
				npcdata.close();
				npcdata = null;
				statement.close();
				statement = null;
			}
			catch (Exception e)
			{
				_log.warning("NPCTable: Error creating NPC table");
			}

			try
			{
				PreparedStatement statement = con.prepareStatement(RESTORE_NPC_SKILLS);
				ResultSet npcskills = statement.executeQuery();
				L2NpcTemplate npcDat = null;
				L2Skill npcSkill = null;

				while (npcskills.next())
				{
					int mobId = npcskills.getInt("npcid");
					npcDat = _npcs.get(mobId);

					if (npcDat == null)
					{
						continue;
					}

					int skillId = npcskills.getInt("skillid");
					int level = npcskills.getInt("level");

					if (npcDat.race == null && skillId == 4416)
					{
						npcDat.setRace(level);
						continue;
					}

					npcSkill = SkillTable.getInstance().getInfo(skillId, level);

					if (npcSkill == null)
					{
						continue;
					}

					npcDat.addSkill(npcSkill);
					npcSkill = null;
				}

				npcskills.close();
				npcskills = null;
				statement.close();
				statement = null;
			}
			catch (Exception e)
			{
				_log.warning("NPCTable: Error reading NPC skills table");
			}

			try
			{
				PreparedStatement statement2 = con.prepareStatement(RESTORE_DROPLIST);
				ResultSet dropData = statement2.executeQuery();
				L2DropData dropDat = null;
				L2NpcTemplate npcDat = null;

				while (dropData.next())
				{
					int mobId = dropData.getInt("mobId");

					npcDat = _npcs.get(mobId);

					if (npcDat == null)
					{
						_log.warning("NPCTable: No npc correlating with id: " + mobId);
						continue;
					}

					dropDat = new L2DropData();

					dropDat.setItemId(dropData.getInt("itemId"));
					dropDat.setMinDrop(dropData.getInt("min"));
					dropDat.setMaxDrop(dropData.getInt("max"));
					dropDat.setChance(dropData.getInt("chance"));

					int category = dropData.getInt("category");

					npcDat.addDropData(dropDat, category);
					dropDat = null;
				}

				dropData.close();
				dropData = null;
				statement2.close();
				statement2 = null;
			}
			catch (Exception e)
			{
				_log.warning("NPCTable: Error reading NPC drop data");
			}

			try
			{
				PreparedStatement statement;
				statement = con.prepareStatement(RESTORE_SELECT_CUSTOM_NPC);
				ResultSet npcdata = statement.executeQuery();

				fillNpcTable(npcdata, true);
				npcdata.close();
				npcdata = null;
				statement.close();
				statement = null;
			}
			catch (Exception e)
			{
				_log.info("NPCTable: Error creating custom NPC table: " + e);
			}

			try
			{
				PreparedStatement statement = con.prepareStatement(RESTORE_NPC_SKILLS);
				ResultSet npcskills = statement.executeQuery();
				L2NpcTemplate npcDat = null;
				L2Skill npcSkill = null;

				while (npcskills.next())
				{
					int mobId = npcskills.getInt("npcid");
					npcDat = _npcs.get(mobId);

					if (npcDat == null)
					{
						continue;
					}

					int skillId = npcskills.getInt("skillid");
					int level = npcskills.getInt("level");

					if (npcDat.race == null && skillId == 4416)
					{
						npcDat.setRace(level);
						continue;
					}

					npcSkill = SkillTable.getInstance().getInfo(skillId, level);

					if (npcSkill == null)
					{
						continue;
					}

					npcDat.addSkill(npcSkill);
					npcSkill = null;
				}

				npcskills.close();
				npcskills = null;
				statement.close();
				statement = null;
			}
			catch (Exception e)
			{
				_log.info("NPCTable: Error reading NPC skills table: " + e);
			}

			try
			{
				PreparedStatement statement2 = con.prepareStatement(RESTORE_CUSTOM_DROPLIST);
				ResultSet dropData = statement2.executeQuery();
				L2DropData dropDat = null;
				L2NpcTemplate npcDat = null;

				int cCount = 0;

				while (dropData.next())
				{
					int mobId = dropData.getInt("mobId");

					npcDat = _npcs.get(mobId);

					if (npcDat == null)
					{
						_log.warning("NPCTable: CUSTOM DROPLIST No npc correlating with id : " + mobId);
						continue;
					}

					dropDat = new L2DropData();
					dropDat.setItemId(dropData.getInt("itemId"));
					dropDat.setMinDrop(dropData.getInt("min"));
					dropDat.setMaxDrop(dropData.getInt("max"));
					dropDat.setChance(dropData.getInt("chance"));

					int category = dropData.getInt("category");

					npcDat.addDropData(dropDat, category);
					cCount++;
					dropDat = null;
				}
				dropData.close();
				dropData = null;
				statement2.close();
				statement2 = null;
				_log.info("CustomDropList: Loaded " + cCount + " custom droplist.");

				if (Config.ENABLE_CACHE_INFO)
				{
					FillDropList();
				}
			}
			catch (Exception e)
			{
				_log.info("NPCTable: Error reading NPC CUSTOM drop data: " + e);
			}

			try
			{
				PreparedStatement statement2 = con.prepareStatement(RESTORE_DROPLIST);
				ResultSet dropData = statement2.executeQuery();
				L2DropData dropDat = null;
				L2NpcTemplate npcDat = null;

				while (dropData.next())
				{
					int mobId = dropData.getInt("mobId");

					npcDat = _npcs.get(mobId);

					if (npcDat == null)
					{
						_log.info("NPCTable: No npc correlating with id : " + mobId);
						continue;
					}

					dropDat = new L2DropData();

					dropDat.setItemId(dropData.getInt("itemId"));
					dropDat.setMinDrop(dropData.getInt("min"));
					dropDat.setMaxDrop(dropData.getInt("max"));
					dropDat.setChance(dropData.getInt("chance"));

					int category = dropData.getInt("category");

					npcDat.addDropData(dropDat, category);
					dropDat = null;
				}

				dropData.close();
				dropData = null;
				statement2.close();
				statement2 = null;
			}
			catch (Exception e)
			{
				_log.info("NPCTable: Error reading NPC drop data: " + e);
			}

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setIgnoringComments(true);
			int th = 0;
			File f = new File(PackRoot.DATAPACK_ROOT, "data/xml/skill_learn.xml");
			if (!f.exists())
			{
				_log.warning("skill_learn.xml could not be loaded: file not found");
				return;
			}
			try
			{
				InputSource in = new InputSource(new InputStreamReader(new FileInputStream(f), "UTF-8"));
				in.setEncoding("UTF-8");
				Document doc = factory.newDocumentBuilder().parse(in);
				for (Node n = doc.getFirstChild(); n != null; n = n.getNextSibling())
				{
					if (n.getNodeName().equalsIgnoreCase("list"))
					{
						for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
						{
							if (d.getNodeName().equalsIgnoreCase("learn"))
							{
								int npcId = Integer.valueOf(d.getAttributes().getNamedItem("npc_id").getNodeValue());
								int classId = Integer.valueOf(d.getAttributes().getNamedItem("class_id").getNodeValue());
								L2NpcTemplate npc = _npcs.get(npcId);

								if (npc == null)
								{
									_log.warning("NPCTable: Error getting NPC template ID " + npcId + " while trying to load skill trainer data.");
									continue;
								}

								npc.addTeachInfo(ClassId.values()[classId]);
								th++;
							}
						}
					}
				}
			}
			catch (SAXException e)
			{
				_log.warning("NPCTable: Error reading NPC trainer data");
			}
			catch (IOException e)
			{
				_log.warning("NPCTable: Error reading NPC trainer data");
			}
			catch (ParserConfigurationException e)
			{
				_log.warning("NPCTable: Error reading NPC trainer data");
			}
			_log.info("NpcTable: Loaded " + th + " teachers.");

			DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
			factory1.setValidating(false);
			factory1.setIgnoringComments(true);
			int cnt = 0;
			File f1 = new File(PackRoot.DATAPACK_ROOT, "data/xml/minion.xml");
			if (!f1.exists())
			{
				_log.warning("minion.xml could not be loaded: file not found");
				return;
			}
			try
			{
				InputSource in1 = new InputSource(new InputStreamReader(new FileInputStream(f1), "UTF-8"));
				in1.setEncoding("UTF-8");
				Document doc1 = factory1.newDocumentBuilder().parse(in1);
				L2MinionData minionDat = null;
				L2NpcTemplate npcDat = null;
				for (Node n1 = doc1.getFirstChild(); n1 != null; n1 = n1.getNextSibling())
				{
					if (n1.getNodeName().equalsIgnoreCase("list"))
					{
						for (Node d1 = n1.getFirstChild(); d1 != null; d1 = d1.getNextSibling())
						{
							if (d1.getNodeName().equalsIgnoreCase("minion"))
							{
								int raidId = Integer.valueOf(d1.getAttributes().getNamedItem("boss_id").getNodeValue());
								int mid = Integer.valueOf(d1.getAttributes().getNamedItem("minion_id").getNodeValue());
								int mmin = Integer.valueOf(d1.getAttributes().getNamedItem("amount_min").getNodeValue());
								int mmax = Integer.valueOf(d1.getAttributes().getNamedItem("amount_max").getNodeValue());

								npcDat = _npcs.get(raidId);
								minionDat = new L2MinionData();

								minionDat.setMinionId(mid);
								minionDat.setAmountMin(mmin);
								minionDat.setAmountMax(mmax);
								npcDat.addRaidData(minionDat);
								cnt++;
								minionDat = null;
							}
						}
					}
				}
			}
			catch (SAXException e)
			{
				_log.warning("Error loading minion data");
			}
			catch (IOException e)
			{
				_log.warning("Error loading minion data");
			}
			catch (ParserConfigurationException e)
			{
				_log.warning("Error loading minion data");
			}
			_log.info("NpcTable: Loaded " + cnt + " minions.");
		}
		catch (Exception e)
		{
		} //never happen, 4finally
	}

	private void fillNpcTable(ResultSet NpcData, boolean customData) throws Exception
	{
		while (NpcData.next())
		{
			StatsSet npcDat = new StatsSet();

			int id = NpcData.getInt("id");
			npcDat.set("npcId", id);

			npcDat.set("idTemplate", NpcData.getInt("idTemplate"));

			int level = NpcData.getInt("level");
			npcDat.set("level", level);
			npcDat.set("jClass", NpcData.getString("class"));
			npcDat.set("baseShldDef", 0);
			npcDat.set("baseShldRate", 0);
			npcDat.set("baseCritRate", 38);

			npcDat.set("name", NpcData.getString("name"));
			npcDat.set("serverSideName", NpcData.getBoolean("serverSideName"));
			//npcDat.set("name", "");
			npcDat.set("title", NpcData.getString("title"));
			npcDat.set("serverSideTitle", NpcData.getBoolean("serverSideTitle"));
			npcDat.set("collision_radius", NpcData.getDouble("collision_radius"));
			npcDat.set("collision_height", NpcData.getDouble("collision_height"));
			npcDat.set("sex", NpcData.getString("sex"));
			npcDat.set("type", NpcData.getString("type"));
			npcDat.set("baseAtkRange", NpcData.getInt("attackrange"));
			npcDat.set("rewardExp", NpcData.getInt("exp"));
			npcDat.set("rewardSp", NpcData.getInt("sp"));
			npcDat.set("basePAtkSpd", NpcData.getInt("atkspd"));
			npcDat.set("baseMAtkSpd", NpcData.getInt("matkspd"));
			npcDat.set("aggroRange", NpcData.getInt("aggro"));
			npcDat.set("rhand", NpcData.getInt("rhand"));
			npcDat.set("lhand", NpcData.getInt("lhand"));
			npcDat.set("armor", NpcData.getInt("armor"));
			npcDat.set("baseWalkSpd", NpcData.getInt("walkspd"));
			npcDat.set("baseRunSpd", NpcData.getInt("runspd"));

			// constants, until we have stats in DB
			npcDat.set("baseSTR", NpcData.getInt("str"));
			npcDat.set("baseCON", NpcData.getInt("con"));
			npcDat.set("baseDEX", NpcData.getInt("dex"));
			npcDat.set("baseINT", NpcData.getInt("int"));
			npcDat.set("baseWIT", NpcData.getInt("wit"));
			npcDat.set("baseMEN", NpcData.getInt("men"));

			npcDat.set("baseHpMax", NpcData.getInt("hp"));
			npcDat.set("baseCpMax", 0);
			npcDat.set("baseMpMax", NpcData.getInt("mp"));
			npcDat.set("baseHpReg", NpcData.getFloat("hpreg") > 0 ? NpcData.getFloat("hpreg") : 1.5 + ((level - 1) / 10.0));
			npcDat.set("baseMpReg", NpcData.getFloat("mpreg") > 0 ? NpcData.getFloat("mpreg") : 0.9 + 0.3 * ((level - 1) / 10.0));
			npcDat.set("basePAtk", NpcData.getInt("patk"));
			npcDat.set("basePDef", NpcData.getInt("pdef"));
			npcDat.set("baseMAtk", NpcData.getInt("matk"));
			npcDat.set("baseMDef", NpcData.getInt("mdef"));

			npcDat.set("factionId", NpcData.getString("faction_id"));
			npcDat.set("factionRange", NpcData.getInt("faction_range"));

			npcDat.set("isUndead", NpcData.getString("isUndead"));

			npcDat.set("absorb_level", NpcData.getString("absorb_level"));
			npcDat.set("absorb_type", NpcData.getString("absorb_type"));
			L2NpcTemplate template = new L2NpcTemplate(npcDat);
			template.addVulnerability(Stats.BOW_WPN_VULN, 1);
			template.addVulnerability(Stats.BLUNT_WPN_VULN, 1);
			template.addVulnerability(Stats.DAGGER_WPN_VULN, 1);
			_npcs.put(id, template);
		}

		_log.info("NpcTable: Loaded " + _npcs.size() + " npc templates.");
	}

	public void reloadNpc(int id)
	{
		try
		{
			// save a copy of the old data
			L2NpcTemplate old = getTemplate(id);
			Map<Integer, L2Skill> skills = new FastMap<Integer, L2Skill>();

			if (old.getSkills() != null)
				skills.putAll(old.getSkills());

			ClassId[] classIds = null;

			if (old.getTeachInfo() != null)
				classIds = old.getTeachInfo().clone();

			List<L2MinionData> minions = new ArrayList<L2MinionData>();

			if (old.getMinionData() != null)
				minions.addAll(old.getMinionData());

			try (Connection con = L2DatabaseFactory.getInstance().getConnection())
			{
				// reload the NPC base data
				if (old.isCustom())
				{
					final PreparedStatement st = con.prepareStatement(RELOAD_CUSTOM_NPC);
					st.setInt(1, id);
					final ResultSet rs = st.executeQuery();
					fillNpcTable(rs, true);
					rs.close();
					st.close();
				}
				else
				{
					final PreparedStatement st = con.prepareStatement(RELOAD_NPC);
					st.setInt(1, id);
					final ResultSet rs = st.executeQuery();
					fillNpcTable(rs, false);
					rs.close();
					st.close();
				}
			}
			catch (Exception e)
			{

			}

			// restore additional data from saved copy
			L2NpcTemplate created = getTemplate(id);

			for (L2Skill skill : skills.values())
			{
				created.addSkill(skill);
			}

			skills = null;

			if (classIds != null)
			{
				for (ClassId classId : classIds)
				{
					created.addTeachInfo(classId);
				}
			}

			for (L2MinionData minion : minions)
			{
				created.addRaidData(minion);
			}

			created = null;
			minions = null;
			classIds = null;
		}
		catch (Exception e)
		{
			_log.warning("NPCTable: Could not reload data for NPC " + id);
		}
	}

	public void reloadAllNpc()
	{
		restoreNpcData();
	}

	public void saveNpc(StatsSet npc)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			Map<String, Object> set = npc.getSet();

			String name = "";
			String values = "";

			final L2NpcTemplate old = getTemplate(npc.getInteger("npcId"));

			for (Object obj : set.keySet())
			{
				name = (String) obj;

				if (!name.equalsIgnoreCase("npcId"))
				{
					if (values != "")
					{
						values += ", ";
					}

					values += name + " = '" + set.get(name) + "'";
				}
			}

			PreparedStatement statement;
			if (old.isCustom())
			{
				statement = con.prepareStatement("UPDATE custom_npc SET " + values + " WHERE id=?");
			}
			else
			{
				statement = con.prepareStatement("UPDATE npc SET " + values + " WHERE id=?");
			}
			statement.setInt(1, npc.getInteger("npcId"));
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.warning("NPCTable: Could not store new NPC data in database");
		}
	}

	public void replaceTemplate(L2NpcTemplate npc)
	{
		_npcs.replace(npc.npcId, npc);
	}

	public L2NpcTemplate getTemplate(int id)
	{
		return _npcs.get(id);
	}

	public L2NpcTemplate getTemplateByName(String name)
	{
		for (L2NpcTemplate npcTemplate : _npcs.values())
			if (npcTemplate.name.equalsIgnoreCase(name))
				return npcTemplate;

		return null;
	}

	public L2NpcTemplate[] getAllOfLevel(int lvl)
	{
		List<L2NpcTemplate> list = new FastList<L2NpcTemplate>();

		for (L2NpcTemplate t : _npcs.values())
			if (t.level == lvl)
			{
				list.add(t);
			}

		return list.toArray(new L2NpcTemplate[list.size()]);
	}

	public L2NpcTemplate[] getAllMonstersOfLevel(int lvl)
	{
		List<L2NpcTemplate> list = new FastList<L2NpcTemplate>();

		for (L2NpcTemplate t : _npcs.values())
			if (t.level == lvl && "L2Monster".equals(t.type))
			{
				list.add(t);
			}

		return list.toArray(new L2NpcTemplate[list.size()]);
	}

	public L2NpcTemplate[] getAllNpcStartingWith(String letter)
	{
		List<L2NpcTemplate> list = new FastList<L2NpcTemplate>();

		for (L2NpcTemplate t : _npcs.values())
			if (t.name.startsWith(letter) && "L2Npc".equals(t.type))
			{
				list.add(t);
			}

		return list.toArray(new L2NpcTemplate[list.size()]);
	}

	public Map<Integer, L2NpcTemplate> getAllTemplates()
	{
		return _npcs;
	}

	public void FillDropList()
	{
		for (L2NpcTemplate npc : _npcs.values())
		{
			InfoCache.addToDroplistCache(npc.npcId, npc.getAllDropData());
		}

		_log.info("Players droplist was cached.");
	}

	public L2NpcTemplate[] getAllNpcOfClassType(String classType)
	{
		List<L2NpcTemplate> list = new FastList<L2NpcTemplate>();

		for (Object t : _npcs.values())
			if (classType.equals(((L2NpcTemplate) t).type))
				list.add((L2NpcTemplate) t);

		return list.toArray(new L2NpcTemplate[list.size()]);
	}

	public Collection<L2NpcTemplate> getAllNpcs()
	{
		return _npcs.values();
	}
}