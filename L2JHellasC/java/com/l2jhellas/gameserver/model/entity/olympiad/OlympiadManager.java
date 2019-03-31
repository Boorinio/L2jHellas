package com.l2jhellas.gameserver.model.entity.olympiad;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;
import com.l2jhellas.gameserver.templates.StatsSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class OlympiadManager
{
	private final List<String> _nonClassBasedRegisters;
	private final Map<Integer, List<String>> _classBasedRegisters;
	
	protected OlympiadManager()
	{
		_nonClassBasedRegisters = new CopyOnWriteArrayList<>();
		_classBasedRegisters = new ConcurrentHashMap<>();
	}
	
	public static final OlympiadManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	public final List<String> getRegisteredNonClassBased()
	{
		return _nonClassBasedRegisters;
	}
	
	public final Map<Integer, List<String>> getRegisteredClassBased()
	{
		return _classBasedRegisters;
	}
	
	protected final List<List<String>> hasEnoughRegisteredClassed()
	{
		List<List<String>> result = null;
		for (Entry<Integer, List<String>> classList : _classBasedRegisters.entrySet())
		{
			if (classList.getValue() != null && classList.getValue().size() >= Config.ALT_OLY_CLASSED)
			{
				if (result == null)
					result = new ArrayList<>();
				
				result.add(classList.getValue());
			}
		}
		return result;
	}
	
	protected final boolean hasEnoughRegisteredNonClassed()
	{
		return _nonClassBasedRegisters.size() >= Config.ALT_OLY_NONCLASSED;
	}
	
	protected final void clearRegistered()
	{
		_nonClassBasedRegisters.clear();
		_classBasedRegisters.clear();
	}
	
	public final boolean isRegistered(L2PcInstance noble)
	{
		return isRegistered(noble, false);
	}
	
	private final boolean isRegistered(L2PcInstance player, boolean showMessage)
	{
		if (_nonClassBasedRegisters.contains(player.getName()))
		{
			if (showMessage)
				player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.YOU_HAVE_ALREADY_BEEN_REGISTERED_IN_A_WAITING_LIST_OF_AN_EVENT));
			
			return true;
		}
		
		final List<String> classed = _classBasedRegisters.get(player.getBaseClass());
		if (classed != null && classed.contains(player.getName()))
		{
			if (showMessage)
				player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.YOU_HAVE_ALREADY_BEEN_REGISTERED_IN_A_WAITING_LIST_OF_AN_EVENT));
			
			return true;
		}
		
		return false;
	}
	
	public final boolean isRegisteredInComp(L2PcInstance noble)
	{
		return isRegistered(noble, false) || isInCompetition(noble, false);
	}
	
	private final static boolean isInCompetition(L2PcInstance player, boolean showMessage)
	{
		if (!Olympiad._inCompPeriod)
			return false;
		
		AbstractOlympiadGame game;
		for (int i = OlympiadGameManager.getInstance().getNumberOfStadiums(); --i >= 0;)
		{
			game = OlympiadGameManager.getInstance().getOlympiadTask(i).getGame();
			if (game == null)
				continue;
			
			if (game.containsParticipant(player.getObjectId()))
			{
				if (!showMessage)
					return true;
				
				player.sendPacket(SystemMessageId.YOU_HAVE_ALREADY_BEEN_REGISTERED_IN_A_WAITING_LIST_OF_AN_EVENT);
				return true;
			}
		}
		return false;
	}
	
	public final boolean registerNoble(L2PcInstance player, CompetitionType type)
	{
		if (!Olympiad._inCompPeriod)
		{
			player.sendPacket(SystemMessageId.THE_OLYMPIAD_GAME_IS_NOT_CURRENTLY_IN_PROGRESS);
			return false;
		}
		
		if (Olympiad.getInstance().getMillisToCompEnd() < 600000)
		{
			player.sendPacket(SystemMessageId.GAME_REQUEST_CANNOT_BE_MADE);
			return false;
		}
		
		switch (type)
		{
			case CLASSED:
			{
				if (!checkNoble(player))
					return false;
				
				List<String> classed = _classBasedRegisters.get(player.getBaseClass());
				if (classed != null)
					classed.add(player.getName().toLowerCase());
				else
				{
					classed = new CopyOnWriteArrayList<>();
					classed.add(player.getName().toLowerCase());
					_classBasedRegisters.put(player.getBaseClass(), classed);
				}
				
				player.sendPacket(SystemMessageId.YOU_HAVE_BEEN_REGISTERED_IN_A_WAITING_LIST_OF_CLASSIFIED_GAMES);
				break;
			}
			
			case NON_CLASSED:
			{
				if (!checkNoble(player))
					return false;
				
				_nonClassBasedRegisters.add(player.getName().toLowerCase());
				player.sendPacket(SystemMessageId.YOU_HAVE_BEEN_REGISTERED_IN_A_WAITING_LIST_OF_NO_CLASS_GAMES);
				break;
			}
		}
		return true;
	}
	
	public final boolean unRegisterNoble(L2PcInstance noble)
	{
		if (!Olympiad._inCompPeriod)
		{
			noble.sendPacket(SystemMessageId.THE_OLYMPIAD_GAME_IS_NOT_CURRENTLY_IN_PROGRESS);
			return false;
		}
		
		if (!noble.isNoble())
		{
			noble.sendPacket(SystemMessageId.NOBLESSE_ONLY);
			return false;
		}
		
		if (!isRegistered(noble, false))
		{
			noble.sendPacket(SystemMessageId.YOU_HAVE_NOT_BEEN_REGISTERED_IN_A_WAITING_LIST_OF_A_GAME);
			return false;
		}
		
		if (isInCompetition(noble, false))
			return false;
		
		String player = noble.getName().toLowerCase();
		if (_nonClassBasedRegisters.remove(player))
		{
			noble.sendPacket(SystemMessageId.YOU_HAVE_BEEN_DELETED_FROM_THE_WAITING_LIST_OF_A_GAME);
			return true;
		}
		
		final List<String> classed = _classBasedRegisters.get(noble.getBaseClass());
		if (classed != null && classed.remove(player))
		{
			_classBasedRegisters.remove(noble.getBaseClass());
			_classBasedRegisters.put(noble.getBaseClass(), classed);
			
			noble.sendPacket(SystemMessageId.YOU_HAVE_BEEN_DELETED_FROM_THE_WAITING_LIST_OF_A_GAME);
			return true;
		}
		
		return false;
	}
	
	public final void removeDisconnectedCompetitor(L2PcInstance player)
	{
		final OlympiadGameTask task = OlympiadGameManager.getInstance().getOlympiadTask(player.getOlympiadGameId());
		if (task != null && task.isGameStarted())
			task.getGame().handleDisconnect(player);
		
		if (_nonClassBasedRegisters.remove(player.getName()))
			return;
		
		final List<String> classed = _classBasedRegisters.get(player.getBaseClass());
		if (classed != null && classed.remove(player.getName()))
			return;
	}
	
	private final boolean checkNoble(L2PcInstance player)
	{
		if (!player.isNoble())
		{
			player.sendPacket(SystemMessageId.ONLY_NOBLESS_CAN_PARTICIPATE_IN_THE_OLYMPIAD);
			return false;
		}
		
		if (player.isSubClassActive())
		{
			player.sendPacket(SystemMessageId.YOU_CANT_JOIN_THE_OLYMPIAD_WITH_A_SUB_JOB_CHARACTER);
			return false;
		}
		
		if (player.isCursedWeaponEquiped())
		{
			player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.CANNOT_JOIN_OLYMPIAD_POSSESSING_S1).addItemName(player.getCursedWeaponEquipedId()));
			return false;
		}
		
		if (isRegistered(player, true))
			return false;
		
		if (isInCompetition(player, true))
			return false;
		
		StatsSet statDat = Olympiad.getNobleStats(player.getObjectId());
		if (statDat == null)
		{
			statDat = new StatsSet();
			statDat.set(Olympiad.CLASS_ID, player.getBaseClass());
			statDat.set(Olympiad.CHAR_NAME, player.getName());
			statDat.set(Olympiad.POINTS, Olympiad.DEFAULT_POINTS);
			statDat.set(Olympiad.COMP_DONE, 0);
			statDat.set(Olympiad.COMP_WON, 0);
			statDat.set(Olympiad.COMP_LOST, 0);
			statDat.set(Olympiad.COMP_DRAWN, 0);
			statDat.set("to_save", true);
			
			Olympiad.addNobleStats(player.getObjectId(), statDat);
		}
		
		final int points = Olympiad.getInstance().getNoblePoints(player.getObjectId());
		if (points <= 0)
		{
			NpcHtmlMessage message = new NpcHtmlMessage(0);
			message.setFile("data/html/olympiad/noble_nopoints1.htm");
			message.replace("%objectId%", String.valueOf(player.getTargetId()));
			player.sendPacket(message);
			return false;
		}
		
		return true;
	}
	
	private static class SingletonHolder
	{
		protected static final OlympiadManager _instance = new OlympiadManager();
	}
}