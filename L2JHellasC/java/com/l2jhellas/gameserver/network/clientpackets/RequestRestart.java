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
package com.l2jhellas.gameserver.network.clientpackets;

import com.l2jhellas.gameserver.SevenSignsFestival;
import com.l2jhellas.gameserver.communitybbs.Manager.RegionBBSManager;
import com.l2jhellas.gameserver.model.L2Party;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.L2Effect.EffectType;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.L2GameClient;
import com.l2jhellas.gameserver.network.L2GameClient.GameClientState;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.ActionFailed;
import com.l2jhellas.gameserver.network.serverpackets.CharSelectInfo;
import com.l2jhellas.gameserver.network.serverpackets.RestartResponse;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;
import com.l2jhellas.gameserver.skills.SkillTable;
import com.l2jhellas.gameserver.taskmanager.AttackStanceTaskManager;

public final class RequestRestart extends L2GameClientPacket
{
	private static final String _C__46_REQUESTRESTART = "[C] 46 RequestRestart";

	@Override
	protected void readImpl()
	{
		// trigger
	}

	@Override
	protected void runImpl()
	{
		L2PcInstance player = getClient().getActiveChar();
		if (player == null)
			return;
		if ((player.getOlympiadGameId() > 0) || player.isInOlympiadMode())
		{
			player.sendMessage("You can't logout in olympiad mode.");
			return;
		}
		if (player.isinZodiac)
		{
			player.sendMessage("You can't logout while in zodiac.");
			return;
		}
		if (player.isTeleporting())
		{
			player.abortCast();
			player.setIsTeleporting(false);
		}

		player.getInventory().updateDatabase();

		if (player.getPrivateStoreType() != 0)
		{
			player.sendMessage("You can't logout while trading.");
			return;
		}

		if (player.getActiveRequester() != null)
		{
			player.getActiveRequester().onTradeCancel(player);
			player.onTradeCancel(player.getActiveRequester());
		}

		if (AttackStanceTaskManager.getInstance().getAttackStanceTask(player) && !player.isGM())
		{
			player.sendPacket(SystemMessageId.CANT_RESTART_WHILE_FIGHTING);
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		if (player._inEventTvT)
		{
			player.sendMessage("You may not use an escape skill in a Event.");
			return;
		}

		if (player.getClan() != null && player.getFirstEffect(EffectType.CLAN_GATE) != null)
		{
			player.sendMessage("You can't logout while Clan Gate is active.");
		}
		// Prevent player from restarting if they are a festival participant
		// and it is in progress, otherwise notify party members that the player
		// is not longer a participant.
		if (player.isFestivalParticipant())
		{
			if (SevenSignsFestival.getInstance().isFestivalInitialized())
			{
				player.sendPacket(SystemMessage.sendString("You cannot restart while you are a participant in a festival."));
				player.sendPacket(ActionFailed.STATIC_PACKET);
				return;
			}
			L2Party playerParty = player.getParty();

			if (playerParty != null)
			{
				player.getParty().broadcastToPartyMembers(SystemMessage.sendString(player.getName() + " has been removed from the upcoming festival."));
			}
		}
		if (player.isFlying())
		{
			player.removeSkill(SkillTable.getInstance().getInfo(4289, 1));
		}
	
		player.endDuel();
		
		L2GameClient client = getClient();

		// Remove From Boss
		player.removeFromBossZone();
		
		player.sendPacket(ActionFailed.STATIC_PACKET);

		// detach the client from the char so that the connection isnt closed in the deleteMe
		player.setClient(null);

		RegionBBSManager.getInstance().changeCommunityBoard();

		L2World.removeFromAllPlayers(player);
		
		// removing player from the world
		player.deleteMe();
		L2GameClient.saveCharToDisk(client.getActiveChar());

		getClient().setActiveChar(null);

		// return the client to the authed status
		client.setState(GameClientState.AUTHED);

		RestartResponse response = new RestartResponse();
		sendPacket(response);

		// send char list
		CharSelectInfo cl = new CharSelectInfo(client.getAccountName(), client.getSessionId().playOkID1);
		sendPacket(cl);
		client.setCharSelection(cl.getCharInfo());
	}

	@Override
	public String getType()
	{
		return _C__46_REQUESTRESTART;
	}
}