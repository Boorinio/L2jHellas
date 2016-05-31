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
package com.l2jhellas.gameserver.model.actor.instance;

import java.util.Iterator;
import java.util.Set;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.datatables.sql.ClanTable;
import com.l2jhellas.gameserver.datatables.xml.CharTemplateData;
import com.l2jhellas.gameserver.datatables.xml.SkillTreeData;
import com.l2jhellas.gameserver.instancemanager.CastleManager;
import com.l2jhellas.gameserver.instancemanager.SiegeManager;
import com.l2jhellas.gameserver.model.L2Clan;
import com.l2jhellas.gameserver.model.L2Clan.SubPledge;
import com.l2jhellas.gameserver.model.L2ClanMember;
import com.l2jhellas.gameserver.model.L2PledgeSkillLearn;
import com.l2jhellas.gameserver.model.base.ClassId;
import com.l2jhellas.gameserver.model.base.ClassType;
import com.l2jhellas.gameserver.model.base.PlayerClass;
import com.l2jhellas.gameserver.model.base.PlayerRace;
import com.l2jhellas.gameserver.model.base.SubClass;
import com.l2jhellas.gameserver.model.entity.Castle;
import com.l2jhellas.gameserver.model.quest.QuestState;
import com.l2jhellas.gameserver.model.zone.ZoneId;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.ActionFailed;
import com.l2jhellas.gameserver.network.serverpackets.AquireSkillList;
import com.l2jhellas.gameserver.network.serverpackets.InventoryUpdate;
import com.l2jhellas.gameserver.network.serverpackets.ItemList;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;
import com.l2jhellas.gameserver.network.serverpackets.UserInfo;
import com.l2jhellas.gameserver.templates.L2Item;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;
import com.l2jhellas.util.Util;

public class L2VillageMasterInstance extends L2NpcInstance
{
	/**
	 * @param template
	 */
	public L2VillageMasterInstance(int objectId, L2NpcTemplate template)
	{
		super(objectId, template);
	}
	
	final int[] slots =
	{
	L2Item.SLOT_LR_HAND, L2Item.SLOT_R_HAND, L2Item.SLOT_L_HAND, L2Item.SLOT_GLOVES, L2Item.SLOT_HEAD, L2Item.SLOT_FEET, L2Item.SLOT_LEGS, L2Item.SLOT_FULL_ARMOR, L2Item.SLOT_R_EAR, L2Item.SLOT_L_EAR, L2Item.SLOT_NECK, L2Item.SLOT_R_FINGER, L2Item.SLOT_L_FINGER
	};
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		String[] commandStr = command.split(" ");
		String actualCommand = commandStr[0]; // Get actual command

		String cmdParams = "";
		String cmdParams2 = "";
		NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
		if (commandStr.length >= 2)
			cmdParams = commandStr[1];
		if (commandStr.length >= 3)
			cmdParams2 = commandStr[2];

		if (player.isLearningSkill())
			return;
		
		if (actualCommand.equalsIgnoreCase("create_clan"))
		{
			ClanTable.getInstance().createClan(player, cmdParams);
		}
		else if (actualCommand.equalsIgnoreCase("create_academy"))
		{
			if (cmdParams.isEmpty())
				return;

			createSubPledge(player, cmdParams, null, L2Clan.SUBUNIT_ACADEMY, 5);
		}
		else if (actualCommand.equalsIgnoreCase("create_royal"))
		{
			if (cmdParams.isEmpty())
				return;

			createSubPledge(player, cmdParams, cmdParams2, L2Clan.SUBUNIT_ROYAL1, 6);
		}
		else if (actualCommand.equalsIgnoreCase("create_knight"))
		{
			if (cmdParams.isEmpty())
				return;

			createSubPledge(player, cmdParams, cmdParams2, L2Clan.SUBUNIT_KNIGHT1, 7);
		}
		else if (actualCommand.equalsIgnoreCase("assign_subpl_leader"))
		{
			if (cmdParams.isEmpty())
				return;

			assignSubPledgeLeader(player, cmdParams, cmdParams2);
		}
		else if (actualCommand.equalsIgnoreCase("create_ally"))
		{
			if (cmdParams.isEmpty())
				return;

			if (!player.isClanLeader())
			{
				player.sendPacket(SystemMessageId.ONLY_CLAN_LEADER_CREATE_ALLIANCE);
				html.setFile("data/html/villagemaster/AllyNo.htm");
				player.sendPacket(html);				
				return;
			}
			player.getClan().createAlly(player, cmdParams);
		}
		else if (actualCommand.equalsIgnoreCase("dissolve_ally"))
		{
			if (!player.isClanLeader())
			{
				player.sendPacket(SystemMessageId.FEATURE_ONLY_FOR_ALLIANCE_LEADER);
				return;
			}
			player.getClan().dissolveAlly(player);
		}
		else if (actualCommand.equalsIgnoreCase("dissolve_clan"))
		{
			
			if(player.isClanLeader())
				dissolveClan(player, player.getClanId());
			else
			{
				html.setFile("data/html/villagemaster/Clan-DisNo.htm");
				player.sendPacket(html);		
				return;
			}
		}
		else if (actualCommand.equalsIgnoreCase("change_clan_leader"))
		{
			if (cmdParams.isEmpty())
				return;
			if(player.isClanLeader())
				changeClanLeader(player, cmdParams);
			else
			{
				html.setFile("data/html/villagemaster/Clan-LeaNo.htm");
				player.sendPacket(html);
				return;
			}
		}
		else if (actualCommand.equalsIgnoreCase("recover_clan"))
		{
			if(player.isClanLeader())
				recoverClan(player, player.getClanId());
			else
			{
				html.setFile("data/html/villagemaster/Clan-RestNo.htm");
				player.sendPacket(html);
				return;
			}
		}
		else if (actualCommand.equalsIgnoreCase("increase_clan_level"))
		{
			if (!player.isClanLeader())
			{
				html.setFile("data/html/villagemaster/Clan-LvlNo.htm");
				player.sendPacket(html);
				return;
			}
			player.getClan().levelUpClan(player);
		}
		else if (actualCommand.equalsIgnoreCase("learn_clan_skills"))
		{
			showPledgeSkillList(player);
		}
		else if (command.startsWith("Subclass"))
		{
			if(player.isFlying())
				return;
			
			// Subclasses may not be changed while a skill is in use.
			if (player.isCastingNow() || player.isAllSkillsDisabled() || player.isLearningSkill())
			{
				player.sendPacket(SystemMessageId.SUBCLASS_NO_CHANGE_OR_CREATE_WHILE_SKILL_IN_USE);
				return;
			}
			if (player.isInCombat())
			{
				player.sendMessage("Sub classes may not be created or changed while being in combat.");
				return;
			}
			if (player.isCursedWeaponEquiped())
			{
				player.sendMessage("You can`t change Subclass while Cursed weapon equiped!");
				return;
			}
			

			
			StringBuilder content = new StringBuilder("<html><body>");
			Set<PlayerClass> subsAvailable;

			int paramOne = 0;
			int paramTwo = 0;
			
			int cmdChoice = Integer.parseInt(command.substring(9, 10).trim());

			try
			{
				int endIndex = command.length();

				if (command.length() > 13)
				{
					endIndex = 13;
					paramTwo = Integer.parseInt(command.substring(13).trim());
				}

				paramOne = Integer.parseInt(command.substring(11, endIndex).trim());
			}
			catch (Exception NumberFormatException)
			{
			}

			switch (cmdChoice)
			{
				case 1: 
					
					// Add Subclass - Initial
					
					// Subclasses may not be added while a summon is active.
					if (player.getPet() != null)
					{
						player.sendPacket(SystemMessageId.CANT_SUBCLASS_WITH_SUMMONED_SERVITOR);
						return;
					}
					
					// Subclasses may not be added while you are over your weight limit.
					if (player.GetInventoryLimit() * 0.8 <= player.getInventory().getSize() || player.getWeightPenalty() > 0)
					{
						player.sendPacket(SystemMessageId.NOT_SUBCLASS_WHILE_OVERWEIGHT);
						return;
					}
					
					// Avoid giving player an option to add a new sub class, if they have three already.
					if (player.getTotalSubClasses() == Config.MAX_SUBCLASS)
					{
						player.sendMessage("You can now only change one of your current sub classes.");
						return;
					}

					subsAvailable = getAvailableSubClasses(player);

					if ((subsAvailable != null) && !subsAvailable.isEmpty())
					{
						content.append("Add Subclass:<br>Which sub class do you wish to add?<br>");

						for (PlayerClass subClass : subsAvailable)
							content.append("<a action=\"bypass -h npc_" + getObjectId() + "_Subclass 4 " + subClass.ordinal() + "\" msg=\"1268;" + formatClassForDisplay(subClass) + "\">" + formatClassForDisplay(subClass) + "</a><br>");
					}
					else
					{
						player.sendMessage("There are no sub classes available at this time.");
						return;
					}
				break;
				case 2: // Change Class - Initial
					content.append("Change Subclass:<br>");

					final int baseClassId = player.getBaseClass();

					// Subclasses may not be changed while a summon is active.
					if (player.getPet() != null)
					{
						player.sendPacket(SystemMessageId.CANT_SUBCLASS_WITH_SUMMONED_SERVITOR);
						return;
					}
					
					// Subclasses may not be changed while a you are over your weight limit.
					if (player.GetInventoryLimit() * 0.8 <= player.getInventory().getSize() || player.getWeightPenalty() > 0)
					{
						player.sendPacket(SystemMessageId.NOT_SUBCLASS_WHILE_OVERWEIGHT);
						return;
					}
					
					if (player.getSubClasses().isEmpty())
					{
						content.append("You can't change sub classes when you don't have a sub class to begin with.<br>" + "<a action=\"bypass -h npc_" + getObjectId() + "_Subclass 1\">Add subclass.</a>");
					}
					else
					{
						content.append("Which class would you like to switch to?<br>");

						if (baseClassId == player.getActiveClass())
							content.append(CharTemplateData.getInstance().getClassNameById(baseClassId) + "&nbsp;<font color=\"LEVEL\">(Base Class)</font><br><br>");
						else
							content.append("<a action=\"bypass -h npc_" + getObjectId() + "_Subclass 5 0\">" + CharTemplateData.getInstance().getClassNameById(baseClassId) + "</a>&nbsp;" + "<font color=\"LEVEL\">(Base Class)</font><br><br>");

						for (Iterator<SubClass> subList = iterSubClasses(player); subList.hasNext();)
						{
							SubClass subClass = subList.next();
							int subClassId = subClass.getClassId();

							if (subClassId == player.getActiveClass())
								content.append(CharTemplateData.getInstance().getClassNameById(subClassId) + "<br>");
							else
								content.append("<a action=\"bypass -h npc_" + getObjectId() + "_Subclass 5 " + subClass.getClassIndex() + "\">" + CharTemplateData.getInstance().getClassNameById(subClassId) + "</a><br>");
						}
					}
				break;
				case 3: // Change/Cancel Subclass - Initial
					content.append("Change Subclass:<br>Which of the following sub classes would you like to change?<br>");
					int classIndex = 1;

					if (player.getSubClasses() == null || player.getSubClasses().isEmpty())
					{
						html.setFile("data/html/villagemaster/SubClass_ModifyEmpty.htm");
						break;
					}
					if ((player.getOlympiadGameId() > 0) || player.isInOlympiadMode())
						player.sendPacket(SystemMessageId.YOU_HAVE_ALREADY_BEEN_REGISTERED_IN_A_WAITING_LIST_OF_AN_EVENT);
					else
					{
						for (Iterator<SubClass> subList = iterSubClasses(player); subList.hasNext();)
						{
							SubClass subClass = subList.next();

							content.append("Sub-class " + classIndex + "<br1>");
							content.append("<a action=\"bypass -h npc_" + getObjectId() + "_Subclass 6 " + subClass.getClassIndex() + "\">" + CharTemplateData.getInstance().getClassNameById(subClass.getClassId()) + "</a><br>");

							classIndex++;
						}
					}
					content.append("<br>If you change a sub class, you'll start at level 40 after the 2nd class transfer.");
				break;
				case 4: // Add Subclass - Action (Subclass 4 x[x])
					
					if (player.isLearningSkill())
						return;

					boolean allowAddition = true;

					if (!player.getAntiFlood().getSubclass().tryPerformAction("add subclass"))
					{
						_log.warning(L2VillageMasterInstance.class.getName() + ": Player " + player.getName() + " has performed a subclass change too fast");
						return;
					}
			
					if (!ItemRestriction(player))
						return;//Check the player for items during subclass..to avoid bugs

					/*
					 * If the character is less than level 75 on any of their previously chosen
					 * classes then disallow them to change to their most recently added sub-class choice.
					 */
					if (player.getLevel() < 75)
					{
						player.sendMessage("You may not add a new sub class before you are level 75 on your previous class.");
						allowAddition = false;
					}

					if (player._inEventCTF || player._inEventDM || player._inEventTvT || player._inEventVIP)
					{
						player.sendMessage("You may not add a new sub class while being registered on event.");
						return;
					}

					if ((player.getOlympiadGameId() > 0) || player.isInOlympiadMode())		
					{				
						player.sendPacket(SystemMessageId.YOU_HAVE_ALREADY_BEEN_REGISTERED_IN_A_WAITING_LIST_OF_AN_EVENT);
						return;
					}

					if (allowAddition)
					{
						if (!player.getSubClasses().isEmpty())
						{
							for (Iterator<SubClass> subList = iterSubClasses(player); subList.hasNext();)
							{
								SubClass subClass = subList.next();

								if (subClass.getLevel() < 75)
								{
									player.sendMessage("You may not add a new sub class before you are level 75 on your previous sub class.");
									allowAddition = false;
									break;
								}
							}
						}
					}

					/*
					 * If quest checking is enabled, verify if the character has completed the Mimir's Elixir (Path to Subclass)
					 * and Fate's Whisper (A Grade Weapon) quests by checking for instances of their unique reward items.
					 * 
					 * If they both exist, remove both unique items and continue with adding the sub-class.
					 */

					if (!Config.ALT_GAME_SUBCLASS_WITHOUT_QUESTS)
					{
						QuestState qs = player.getQuestState("235_MimirsElixir");
						if ((qs == null) || !qs.isCompleted())
						{
							player.sendMessage("You must have completed the Mimir's Elixir quest to continue adding your sub class.");
							return;
						}
						qs = player.getQuestState("234_FatesWhisper");
						if ((qs == null) || !qs.isCompleted())
						{
							player.sendMessage("You must have completed the Fate's Whisper quest to continue adding your sub class.");
							return;
						}
					}
					if (allowAddition & isValidNewSubClass(player, paramOne))
					{
						String className = CharTemplateData.getInstance().getClassNameById(paramOne);

						if (!player.addSubClass(paramOne, player.getTotalSubClasses() + 1))
						{			
							player.sendMessage("The sub class could not be added.");
							return;
						}

						player.setActiveClass(player.getTotalSubClasses());

						content.append("Add Subclass:<br>The sub class of <font color=\"LEVEL\">" + className + "</font> has been added.");
						player.sendPacket(SystemMessageId.CLASS_TRANSFER); // Transfer to new class.
					}
					else
					{
						html.setFile("data/html/villagemaster/SubClass_Fail.htm");
					}
				break;

				case 5: // Change Class - Action
					/*
					 * If the character is less than level 75 on any of their previously chosen
					 * classes then disallow them to change to their most recently added sub-class choice.
					 * 
					 * Note: paramOne = classIndex
					 */

					if (player.isLearningSkill())
						return;

					if (!player.getAntiFlood().getSubclass().tryPerformAction("add subclass"))
					{
						_log.warning(L2VillageMasterInstance.class.getName() + ": Player " + player.getName() + " has performed a subclass change too fast");
						return;
					}
					
		
					
					if (player._inEventCTF || player._inEventDM || player._inEventTvT || player._inEventVIP)
					{
						player.sendMessage("You may not add a new sub class while being registered on event.");
						return;
					}

					if (!ItemRestriction(player))
						return;//Check the player for items during subclass..to avoid bugs

					if ((player.getOlympiadGameId() > 0) || player.isInOlympiadMode())
					{
						player.sendPacket(SystemMessageId.YOU_HAVE_ALREADY_BEEN_REGISTERED_IN_A_WAITING_LIST_OF_AN_EVENT);
						return;
					}

					if (player.getClassIndex() == paramOne)
					{
						html.setFile("data/html/villagemaster/SubClass_Current.htm");
						break;
					}
					
					if (paramOne == 0)
					{
						if (!checkVillageMaster(player.getBaseClass()))
							return;
					}
					else
					{
						try
						{
							if (!checkVillageMaster(player.getSubClasses().get(paramOne).getClassDefinition()))
								return;
						}
						catch (NullPointerException e)
						{
							return;
						}
					}
					
					player.setActiveClass(paramOne);

					content.append("Change Subclass:<br>Your active sub class is now a <font color=\"LEVEL\">" + CharTemplateData.getInstance().getClassNameById(player.getActiveClass()) + "</font>.");

					player.sendPacket(SystemMessageId.SUBCLASS_TRANSFER_COMPLETED); // Transfer completed.
				break;
				case 6: // Change/Cancel Subclass - Choice

					if (paramOne < 1 || paramOne > 3)
						return;
					
					content.append("Please choose a sub class to change to. If the one you are looking for is not here, " + "please seek out the appropriate master for that class.<br>" + "<font color=\"LEVEL\">Warning!</font> All classes and skills for this class will be removed.<br><br>");

					
					subsAvailable = getAvailableSubClasses(player);

					// another validity check
					if (subsAvailable == null || subsAvailable.isEmpty())
					{
						player.sendMessage("There are no sub classes available at this time.");
						return;
					}
					
					if ((player.getOlympiadGameId() > 0) || player.isInOlympiadMode())					{
						player.sendPacket(SystemMessageId.YOU_HAVE_ALREADY_BEEN_REGISTERED_IN_A_WAITING_LIST_OF_AN_EVENT);
					}
					else if (subsAvailable != null && !subsAvailable.isEmpty())
					{
						for (PlayerClass subClass : subsAvailable)
							content.append("<a action=\"bypass -h npc_" + getObjectId() + "_Subclass 7 " + paramOne + " " + subClass.ordinal() + "\">" + formatClassForDisplay(subClass) + "</a><br>");
					}
					else
					{
						player.sendMessage("There are no sub classes available at this time.");
						return;
					}
				break;
				case 7: // Change Subclass - Action
					// check player skills
					/*
					 * Warning: the information about this subclass will be removed from the
					 * subclass list even if false!
					 */
					if (player.isLearningSkill())
						return;
					
					if (!isValidNewSubClass(player, paramTwo))
						return;
					
					if (!player.getAntiFlood().getSubclass().tryPerformAction("change class"))
					{
						_log.warning(L2VillageMasterInstance.class.getName() + ": Player " + player.getName() + " has performed a subclass change too fast");
						return;
					}
					

					
					if (Config.CHECK_SKILLS_ON_ENTER && !Config.ALT_GAME_SKILL_LEARN)
						player.checkAllowedSkills();

					if ((player.getOlympiadGameId() > 0) || player.isInOlympiadMode())
					{
						player.sendPacket(SystemMessageId.YOU_HAVE_ALREADY_BEEN_REGISTERED_IN_A_WAITING_LIST_OF_AN_EVENT);
						return;
					}
					if (!ItemRestriction(player))
						return;//Check the player for items during subclass..to avoid bugs

					else if (player.modifySubClass(paramOne, paramTwo))
					{
						player.setActiveClass(paramOne);

						content.append("Change Subclass:<br>Your sub class has been changed to <font color=\"LEVEL\">" + CharTemplateData.getInstance().getClassNameById(paramTwo) + "</font>.");

						player.sendPacket(SystemMessageId.ADD_NEW_SUBCLASS); // Subclass added.
						// check player skills
						if (Config.CHECK_SKILLS_ON_ENTER && !Config.ALT_GAME_SKILL_LEARN)
							player.checkAllowedSkills();
					}
					else
					{
						/*
						 * This isn't good! modifySubClass() removed subclass from memory
						 * we must update _classIndex! Else IndexOutOfBoundsException can turn
						 * up some place down the line along with other seemingly unrelated
						 * problems.
						 */
						player.setActiveClass(0); // Also updates _classIndex plus switching _classid to baseclass.

						player.sendMessage("The sub class could not be added, you have been reverted to your base class.");
						return;
					}
				break;
			}

			content.append("</body></html>");

			// If the content is greater than for a basic blank page,
			// then assume no external HTML file was assigned.
			if (content.length() > 26)
				html.setHtml(content.toString());

			player.sendPacket(html);
		}
		else
		{
			// this class don't know any other commands, let forward
			// the command to the parent class
			super.onBypassFeedback(player, command);
		}
	}

	/*
	 * Check new subclass classId for validity (villagemaster race/type is not contains in previous subclasses, but in allowed subclasses) Base class not added into allowed subclasses.
	 */
	private final boolean isValidNewSubClass(L2PcInstance player, int classId)
	{
		if (!checkVillageMaster(classId))
			return false;
		
		final ClassId cid = ClassId.values()[classId];
		for (Iterator<SubClass> subList = iterSubClasses(player); subList.hasNext();)
		{
			SubClass sub = subList.next();
			ClassId subClassId = ClassId.values()[sub.getClassId()];
			
			if (subClassId.equalsOrChildOf(cid))
				return false;
		}
		
		// get player base class
		final int currentBaseId = player.getBaseClass();
		final ClassId baseCID = ClassId.values()[currentBaseId];
		
		// we need 2nd occupation ID
		final int baseClassId;
		if (baseCID.level() > 2)
			baseClassId = baseCID.getParent().ordinal();
		else
			baseClassId = currentBaseId;
		
		Set<PlayerClass> availSubs = PlayerClass.values()[baseClassId].getAvailableSubclasses(player);
		if (availSubs == null || availSubs.isEmpty())
			return false;
		
		boolean found = false;
		for (PlayerClass pclass : availSubs)
		{
			if (pclass.ordinal() == classId)
			{
				found = true;
				break;
			}
		}
		
		return found;
	}
	
	@Override
	public String getHtmlPath(int npcId, int val)
	{
		String pom = "";

		if (val == 0)
			pom = "" + npcId;
		else
			pom = npcId + "-" + val;

		return "data/html/villagemaster/" + pom + ".htm";
	}

	//Private stuff
	public void dissolveClan(L2PcInstance player, int clanId)
	{
		if (Config.DEBUG)
			_log.fine(player.getObjectId() + "(" + player.getName() + ") requested dissolve a clan from " + getObjectId() + "(" + getName() + ")");

		if (!player.isClanLeader())
		{
			player.sendPacket(SystemMessageId.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
			return;
		}
		/*
		 * Until proper clan leader change support is done, this is a little
		 * exploit fix (leader, while flying wyvern changes clan leader and the new leader
		 * can ride the wyvern too)
		 * DrHouse
		 */
		if (player.isFlying())
		{
			player.sendMessage("Please, stop flying");
			return;
		}
		L2Clan clan = player.getClan();
		if (clan.getAllyId() != 0)
		{
			player.sendPacket(SystemMessageId.CANNOT_DISPERSE_THE_CLANS_IN_ALLY);
			return;
		}
		if (clan.isAtWar() != false)
		{
			player.sendPacket(SystemMessageId.CANNOT_DISSOLVE_WHILE_IN_WAR);
			return;
		}
		if (clan.hasCastle() != 0 || clan.hasHideout() != 0)
		{
			player.sendPacket(SystemMessageId.CANNOT_DISSOLVE_WHILE_OWNING_CLAN_HALL_OR_CASTLE);
			return;
		}
		for (Castle castle : CastleManager.getInstance().getCastles())
		{
			if (SiegeManager.getInstance().checkIsRegistered(clan, castle.getCastleId()))
			{
				player.sendPacket(SystemMessageId.CANNOT_DISSOLVE_CAUSE_CLAN_WILL_PARTICIPATE_IN_CASTLE_SIEGE);
				return;
			}
		}
		if (player.isInsideZone(ZoneId.SIEGE))
		{
			player.sendPacket(SystemMessageId.CANNOT_DISSOLVE_WHILE_IN_SIEGE);
			return;
		}
		if (clan.getDissolvingExpiryTime() > System.currentTimeMillis())
		{
			player.sendPacket(SystemMessageId.DISSOLUTION_IN_PROGRESS);
			return;
		}

		clan.setDissolvingExpiryTime(System.currentTimeMillis() + Config.ALT_CLAN_DISSOLVE_DAYS * 86400000L); //24*60*60*1000 = 86400000
		clan.updateClanInDB();

		ClanTable.getInstance().scheduleRemoveClan(clan.getClanId());

		// The clan leader should take the XP penalty of a full death.
		player.deathPenalty(false);
	}

	public void recoverClan(L2PcInstance player, int clanId)
	{
		if (Config.DEBUG)
			_log.fine(player.getObjectId() + "(" + player.getName() + ") requested recover a clan from " + getObjectId() + "(" + getName() + ")");

		if (!player.isClanLeader())
		{
			player.sendPacket(SystemMessageId.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
			return;
		}
		L2Clan clan = player.getClan();

		clan.setDissolvingExpiryTime(0);
		clan.updateClanInDB();
	}

	public void changeClanLeader(L2PcInstance player, String target)
	{
		if (Config.DEBUG)
			_log.fine(player.getObjectId() + "(" + player.getName() + ") requested change a clan leader from " + getObjectId() + "(" + getName() + ")");

		if (!player.isClanLeader())
		{
			player.sendPacket(SystemMessageId.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
			return;
		}
		if (player.getName().equalsIgnoreCase(target))
		{
			return;
		}
		L2Clan clan = player.getClan();

		L2ClanMember member = clan.getClanMember(target);
		if (member == null)
		{
			SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_DOES_NOT_EXIST);
			sm.addString(target);
			player.sendPacket(sm);
			sm = null;
			return;
		}
		if (!member.isOnline())
		{
			player.sendPacket(SystemMessageId.INVITED_USER_NOT_ONLINE);
			return;
		}

		clan.setNewLeader(member, player , false);
	}

	public void createSubPledge(L2PcInstance player, String clanName, String leaderName, int pledgeType, int minClanLvl)
	{
		if (Config.DEBUG)
			_log.fine(player.getObjectId() + "(" + player.getName() + ") requested sub clan creation from " + getObjectId() + "(" + getName() + ")");

		if (!player.isClanLeader())
		{
			player.sendPacket(SystemMessageId.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
			return;
		}

		L2Clan clan = player.getClan();
		if (clan.getLevel() < minClanLvl)
		{
			if (pledgeType == L2Clan.SUBUNIT_ACADEMY)
				player.sendPacket(SystemMessageId.YOU_DO_NOT_MEET_CRITERIA_IN_ORDER_TO_CREATE_A_CLAN_ACADEMY);
			else
				player.sendPacket(SystemMessageId.YOU_DO_NOT_MEET_CRITERIA_IN_ORDER_TO_CREATE_A_MILITARY_UNIT);

			return;
		}
		if (!Util.isAlphaNumeric(clanName) || 2 > clanName.length())
		{
			player.sendPacket(SystemMessageId.CLAN_NAME_INVALID);
			return;
		}
		if (clanName.length() > 16)
		{
			player.sendPacket(SystemMessageId.CLAN_NAME_LENGTH_INCORRECT);
			return;
		}
		for (L2Clan tempClan : ClanTable.getInstance().getClans())
		{
			if (tempClan.getSubPledge(clanName) != null)
			{
				if (pledgeType == L2Clan.SUBUNIT_ACADEMY)
				{
					SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_ALREADY_EXISTS);
					sm.addString(clanName);
					player.sendPacket(sm);
					sm = null;
				}
				else
				{
					player.sendPacket(SystemMessageId.ANOTHER_MILITARY_UNIT_IS_ALREADY_USING_THAT_NAME);
				}
				return;
			}

		}

		if (pledgeType != L2Clan.SUBUNIT_ACADEMY)
			if (clan.getClanMember(leaderName) == null || clan.getClanMember(leaderName).getPledgeType() != 0)
			{
				if (pledgeType >= L2Clan.SUBUNIT_KNIGHT1)
					player.sendPacket(SystemMessageId.CAPTAIN_OF_ORDER_OF_KNIGHTS_CANNOT_BE_APPOINTED);
				else if (pledgeType >= L2Clan.SUBUNIT_ROYAL1)
					player.sendPacket(SystemMessageId.CAPTAIN_OF_ROYAL_GUARD_CANNOT_BE_APPOINTED);

				return;
			}

		if (clan.createSubPledge(player, pledgeType, leaderName, clanName) == null)
			return;

		SystemMessage sm;
		if (pledgeType == L2Clan.SUBUNIT_ACADEMY)
		{
			sm = SystemMessage.getSystemMessage(SystemMessageId.THE_S1S_CLAN_ACADEMY_HAS_BEEN_CREATED);
			sm.addString(player.getClan().getName());
		}
		else if (pledgeType >= L2Clan.SUBUNIT_KNIGHT1)
		{
			sm = SystemMessage.getSystemMessage(SystemMessageId.THE_KNIGHTS_OF_S1_HAVE_BEEN_CREATED);
			sm.addString(player.getClan().getName());
		}
		else if (pledgeType >= L2Clan.SUBUNIT_ROYAL1)
		{
			sm = SystemMessage.getSystemMessage(SystemMessageId.THE_ROYAL_GUARD_OF_S1_HAVE_BEEN_CREATED);
			sm.addString(player.getClan().getName());
		}
		else
			sm = SystemMessage.getSystemMessage(SystemMessageId.CLAN_CREATED);

		player.sendPacket(sm);
		if (pledgeType != L2Clan.SUBUNIT_ACADEMY)
		{
			L2ClanMember leaderSubPledge = clan.getClanMember(leaderName);
			if (leaderSubPledge.getPlayerInstance() == null)
				return;
			leaderSubPledge.getPlayerInstance().setPledgeClass(leaderSubPledge.calculatePledgeClass(leaderSubPledge.getPlayerInstance()));
			leaderSubPledge.getPlayerInstance().sendPacket(new UserInfo(leaderSubPledge.getPlayerInstance()));
		}
	}

	public void assignSubPledgeLeader(L2PcInstance player, String clanName, String leaderName)
	{
		if (Config.DEBUG)
			_log.fine(player.getObjectId() + "(" + player.getName() + ") requested to assign sub clan" + clanName + "leader " + "(" + leaderName + ")");

		if (!player.isClanLeader())
		{
			player.sendPacket(SystemMessageId.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
			return;
		}

		if (leaderName.length() > 16)
		{
			player.sendPacket(SystemMessageId.NAMING_CHARNAME_UP_TO_16CHARS);
			return;
		}

		if (player.getName().equals(leaderName))
		{
			player.sendPacket(SystemMessageId.CAPTAIN_OF_ROYAL_GUARD_CANNOT_BE_APPOINTED);
			return;
		}

		L2Clan clan = player.getClan();
		SubPledge subPledge = player.getClan().getSubPledge(clanName);

		if (null == subPledge)
		{
			player.sendPacket(SystemMessageId.CLAN_NAME_INVALID);
			return;
		}
		if (subPledge.getId() == L2Clan.SUBUNIT_ACADEMY)
		{
			player.sendPacket(SystemMessageId.CLAN_NAME_INVALID);
			return;
		}

		if ((clan.getClanMember(leaderName) == null) || (clan.getClanMember(leaderName).getPledgeType() != 0))
		{
			if (subPledge.getId() >= L2Clan.SUBUNIT_KNIGHT1)
			{
				player.sendPacket(SystemMessageId.CAPTAIN_OF_ORDER_OF_KNIGHTS_CANNOT_BE_APPOINTED);
			}
			else if (subPledge.getId() >= L2Clan.SUBUNIT_ROYAL1)
			{
				player.sendPacket(SystemMessageId.CAPTAIN_OF_ROYAL_GUARD_CANNOT_BE_APPOINTED);
			}
			return;
		}

		subPledge.setLeaderName(leaderName);
		clan.updateSubPledgeInDB(subPledge.getId());
		L2ClanMember leaderSubPledge = clan.getClanMember(leaderName);
		leaderSubPledge.getPlayerInstance().setPledgeClass(leaderSubPledge.calculatePledgeClass(leaderSubPledge.getPlayerInstance()));
		leaderSubPledge.getPlayerInstance().sendPacket(new UserInfo(leaderSubPledge.getPlayerInstance()));
		clan.broadcastClanStatus();
		SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_HAS_BEEN_SELECTED_AS_CAPTAIN_OF_S2);
		sm.addString(leaderName);
		sm.addString(clanName);
		clan.broadcastToOnlineMembers(sm);
		sm = null;
	}

	private final Set<PlayerClass> getAvailableSubClasses(L2PcInstance player)
	{
		int charClassId = player.getBaseClass();

		if (charClassId >= 88)
			charClassId = ClassId.values()[charClassId].getParent().getId();

		final PlayerRace npcRace = getVillageMasterRace();
		final ClassType npcTeachType = getVillageMasterTeachType();

		PlayerClass currClass = PlayerClass.values()[charClassId];

		/**
		 * If the race of your main class is Elf or Dark Elf,
		 * you may not select each class as a subclass to the other class,
		 * and you may not select Overlord and Warsmith class as a subclass.
		 * You may not select a similar class as the subclass.
		 * The occupations classified as similar classes are as follows:
		 * Treasure Hunter, Plainswalker and Abyss Walker
		 * Hawkeye, Silver Ranger and Phantom Ranger
		 * Paladin, Dark Avenger, Temple Knight and Shillien Knight
		 * Warlocks, Elemental Summoner and Phantom Summoner
		 * Elder and Shillien Elder
		 * Swordsinger and Bladedancer
		 * Sorcerer, Spellsinger and Spellhowler
		 */
		Set<PlayerClass> availSubs = currClass.getAvailableSubclasses(player);

		if (availSubs != null)
		{
			for (PlayerClass availSub : availSubs)
			{
				for (Iterator<SubClass> subList = iterSubClasses(player); subList.hasNext();)
				{
					SubClass prevSubClass = subList.next();
					int subClassId = prevSubClass.getClassId();
					if (subClassId >= 88)
						subClassId = ClassId.values()[subClassId].getParent().getId();

					if (availSub.ordinal() == subClassId || availSub.ordinal() == charClassId)
						availSubs.remove(PlayerClass.values()[availSub.ordinal()]);
				}

				if ((npcRace == PlayerRace.Human || npcRace == PlayerRace.LightElf))
				{
					// If the master is human or light elf, ensure that fighter-type
					// masters only teach fighter classes, and priest-type masters
					// only teach priest classes etc.
					if (!availSub.isOfType(npcTeachType))
						availSubs.remove(availSub);

					// Remove any non-human or light elf classes.
					else if (!availSub.isOfRace(PlayerRace.Human) && !availSub.isOfRace(PlayerRace.LightElf))
						availSubs.remove(availSub);
				}
				else
				{
					// If the master is not human and not light elf,
					// then remove any classes not of the same race as the master.
					if ((npcRace != PlayerRace.Human && npcRace != PlayerRace.LightElf) && !availSub.isOfRace(npcRace))
						availSubs.remove(availSub);
				}
			}
		}

		return availSubs;
	}

	/**
	 * this displays PledgeSkillList to the player.
	 * 
	 * @param player
	 */
	public void showPledgeSkillList(L2PcInstance player)
	{

		if (Config.DEBUG)
			_log.fine("PledgeSkillList activated on: " + getObjectId());
		if (player.getClan() == null)
			return;

		L2PledgeSkillLearn[] skills = SkillTreeData.getInstance().getAvailablePledgeSkills(player);
		AquireSkillList asl = new AquireSkillList(AquireSkillList.skillType.Clan);
		int counts = 0;

		for (L2PledgeSkillLearn s : skills)
		{
			int cost = s.getRepCost();
			counts++;

			asl.addSkill(s.getId(), s.getLevel(), s.getLevel(), cost, 0);
		}

		if (counts == 0)
		{
			NpcHtmlMessage html = new NpcHtmlMessage(1);

			if (player.getClan().getLevel() < 8)
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.DO_NOT_HAVE_FURTHER_SKILLS_TO_LEARN_S1);
				sm.addNumber(player.getClan().getLevel() + 1);
				player.sendPacket(sm);
			}
			else
			{
				StringBuilder sb = new StringBuilder();
				sb.append("<html><body>");
				sb.append("You've learned all skills available for your Clan.<br>");
				sb.append("</body></html>");
				html.setHtml(sb.toString());
				player.sendPacket(html);
			}
		}
		else
		{
			player.sendPacket(asl);
		}

		player.sendPacket(ActionFailed.STATIC_PACKET);
	}

	private final String formatClassForDisplay(PlayerClass className)
	{
		String classNameStr = className.toString();
		char[] charArray = classNameStr.toCharArray();

		for (int i = 1; i < charArray.length; i++)
			if (Character.isUpperCase(charArray[i]))
				classNameStr = classNameStr.substring(0, i) + " " + classNameStr.substring(i);

		return classNameStr;
	}

	private final PlayerRace getVillageMasterRace()
	{
		String npcClass = getTemplate().getStatsSet().getString("jClass").toLowerCase();

		if (npcClass.indexOf("human") > -1)
			return PlayerRace.Human;

		if (npcClass.indexOf("darkelf") > -1)
			return PlayerRace.DarkElf;

		if (npcClass.indexOf("elf") > -1)
			return PlayerRace.LightElf;

		if (npcClass.indexOf("orc") > -1)
			return PlayerRace.Orc;

		return PlayerRace.Dwarf;
	}

	private final ClassType getVillageMasterTeachType()
	{
		String npcClass = getTemplate().getStatsSet().getString("jClass");

		if (npcClass.indexOf("sanctuary") > -1 || npcClass.indexOf("clergyman") > -1)
			return ClassType.Priest;

		if (npcClass.indexOf("mageguild") > -1 || npcClass.indexOf("patriarch") > -1)
			return ClassType.Mystic;

		return ClassType.Fighter;
	}

	private boolean ItemRestriction(L2PcInstance player)
	{
		if (player.getActiveWeaponInstance() != null || player.getActiveChestArmorItem() != null)
		{
			for (int slot : slots)
			{
				player.getInventory().unEquipItemInBodySlotAndRecord(slot);
			}
			player.getInventory().reloadEquippedItems();
			player.sendPacket(new InventoryUpdate());
			player.sendPacket(new ItemList(player, false));
			player.store();
			player.sendMessage("Your items have been removed!");
		}
		return true;
	}

	private Iterator<SubClass> iterSubClasses(L2PcInstance player)
	{
		return player.getSubClasses().values().iterator();
	}
	
	protected boolean checkVillageMasterRace(PlayerClass pclass)
	{
		return true;
	}
	
	protected boolean checkVillageMasterTeachType(PlayerClass pclass)
	{
		return true;
	}
	
	/*
	 * Returns true if this classId allowed for master
	 */
	public final boolean checkVillageMaster(int classId)
	{
		return checkVillageMaster(PlayerClass.values()[classId]);
	}
	
	/*
	 * Returns true if this PlayerClass is allowed for master
	 */
	public final boolean checkVillageMaster(PlayerClass pclass)
	{
		return checkVillageMasterRace(pclass) && checkVillageMasterTeachType(pclass);
	}
	
}