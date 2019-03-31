package com.l2jhellas.gameserver.handlers.voicedcommandhandlers;

import com.l2jhellas.gameserver.datatables.xml.MapRegionTable;
import com.l2jhellas.gameserver.handler.IVoicedCommandHandler;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.zone.L2ZoneType;

public class OnlinePlayersCmd implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"online"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if (command.startsWith(VOICED_COMMANDS[0]))
		{
			showPlayers(activeChar);
			
			final L2ZoneType town = MapRegionTable.getTown(activeChar.getX(), activeChar.getY(), activeChar.getZ());
			
			if (town != null)
			{
				int count = town.getCharactersInside().size();
				activeChar.sendMessage("There are " + count + " characters inside that town");
			}
		}
		return true;
	}
	
	public void showPlayers(L2PcInstance player)
	{
		player.sendMessage("====================\n");
		player.sendMessage("There are " + L2World.getInstance().getAllPlayers().size() + " players online\n");
		player.sendMessage("====================");
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
}