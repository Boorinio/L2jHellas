package Extensions.RaidEvent;

import java.util.List;

import com.l2jhellas.gameserver.model.L2Effect;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public class L2EventChecks
{
	
	private static boolean checkIfOtherEvent(L2PcInstance player)
	{
		if (player.inSoloEvent || player.inPartyEvent || player.inClanEvent)
		{
			player.sendMessage("You're alredy registered in another event.");
			return true;
		}
		return false;
	}
	
	public static boolean checkPlayer(L2PcInstance player, int eventType, int points, int minPeople, List<L2PcInstance> _eventPlayers)
	{
		int eventPoints = 0;
		// Let's avoid NPEs
		if (player == null)
			return false;
		// If there's not enough clan members online to fill the MinPeople
		// requirement
		// return false.
		if (_eventPlayers.size() <= minPeople && eventType == (2 | 3))
		{
			// Notify to the requester.
			player.sendMessage("Not enough " + eType(eventType) + " members are connected.Minimum:" + minPeople);
			return false;
		}
		for (L2PcInstance member : _eventPlayers)
		{
			
			if (member == null)
				continue;
			// Let's check if any of the members is in another Event.
			if (checkIfOtherEvent(member))
			{
				
				String badRequestor = member.getName();
				notifyBadRequestor(player, badRequestor, 2, _eventPlayers);
				return false;
			}
			
			// TODO: Add a Check asking members of the clan/party (ONLY)
			// ACTUALLY WANT TO PARTICIPATE or not.
			
			switch (eventType)
			{
				case 2:
				{
					if (_eventPlayers.contains(player) && member.getClan().getName().equals(player.getClan().getName()))
						eventPoints += member.getEventPoints();
					break;
				}
				case 3:
				{
					// Let's add the points of each member to the Party General
					// Clan Score.
					eventPoints += member.getEventPoints();
					break;
				}
				default:
				{
					eventPoints = member.getEventPoints();
					break;
				}
			}
		}
		
		if (eventPoints >= points)
		{
			for (L2PcInstance member : _eventPlayers)
			{
				// Deletion of all the Buffs from all the Clan members
				for (L2Effect effect : member.getAllEffects())
				{
					if (effect != null)
						effect.exit();
				}
			}
			return true;
		}
		// Else The Clan doesn't have enough event points to participate.
		else if (eventType != 1)
		{
			player.sendMessage("The totality of your " + eType(eventType) + " members don't have enough Event Points to participate.");
			return false;
		}
		else
		{
			player.sendMessage("Not enough Event Points to participate into the Event.");
			return false;
		}
	}
	
	private static void notifyBadRequestor(L2PcInstance player, String badRequestor, int type, List<L2PcInstance> _eventPlayers)
	{
		if (type == 2)
		{
			for (L2PcInstance member : _eventPlayers)
			{
				member.sendMessage("You can't access the event while " + badRequestor + "is singed up for another event.");
			}
		}
		if (type == 3)
		{
			for (L2PcInstance member : _eventPlayers)
			{
				member.sendMessage("You can't access the event while " + badRequestor + "is singed up for another event.");
			}
		}
	}
	
	public static boolean usualChecks(L2PcInstance player, int minLevel)
	{
		if (player.getLevel() < minLevel)
		{
			player.sendMessage("The minimum level to participate in this Event is " + minLevel + ". You cannot participate.");
			return false;
		}
		if (player.inClanEvent || player.inPartyEvent || player.inSoloEvent)
		{
			player.sendMessage("You're alredy registered in another Event.");
			return false;
		}
		if (player.isCursedWeaponEquiped())
		{
			player.sendMessage("You can Not register while Having a Cursed Weapon.");
			return false;
		}
		if (player.isInStoreMode())
		{
			player.sendMessage("Cannot Participate while in Store Mode.");
			return false;
		}
		if (player.isInJail())
		{
			player.sendMessage("Cannot Participate while in Jail.");
			return false;
		}
		if (player.isOlympiadStart())
		{
			player.sendMessage("Cannot participate while olympiad is running");
			return false;
		}
		return true;
	}
	
	public static String eType(int type)
	{
		String sType;
		if (type == 1)
			sType = "Single";
		else if (type == 2)
			sType = "Clan";
		else if (type == 3)
			sType = "Party";
		else
			sType = "error ocurred while getting type of Event.";
		return sType;
	}
}