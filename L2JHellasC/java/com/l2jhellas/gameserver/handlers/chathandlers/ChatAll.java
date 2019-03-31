package com.l2jhellas.gameserver.handlers.chathandlers;

import com.l2jhellas.gameserver.handler.IChatHandler;
import com.l2jhellas.gameserver.handler.IVoicedCommandHandler;
import com.l2jhellas.gameserver.handler.VoicedCommandHandler;
import com.l2jhellas.gameserver.model.BlockList;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.CreatureSay;

import java.util.Collection;
import java.util.StringTokenizer;
import java.util.logging.Logger;

public class ChatAll implements IChatHandler
{
	final Logger _log = Logger.getLogger(ChatAll.class.getName());
	private static final int[] COMMAND_IDS =
	{
		0
	};
	
	@Override
	public void handleChat(int type, L2PcInstance activeChar, String target, String text)
	{
		if (!activeChar.getAntiFlood().getGlobalChat().tryPerformAction("global chat") && !activeChar.isGM())
		{
			activeChar.sendMessage("dont spam! otherwise you will get chat ban!");
			return;
		}
		
		if (text.startsWith(".") && !text.startsWith(".."))
		{
			StringTokenizer st = new StringTokenizer(text);
			IVoicedCommandHandler vch;
			String command = "";
			
			if (st.countTokens() > 1)
			{
				command = st.nextToken().substring(1);
				target = text.substring(command.length() + 2);
				vch = VoicedCommandHandler.getInstance().getHandler(command);
			}
			else
			{
				command = text.substring(1);
				vch = VoicedCommandHandler.getInstance().getHandler(command);
			}
			
			if (vch != null)
				vch.useVoicedCommand(command, activeChar, target);				
		}
		else
		{
			
			boolean vcd_used = false;
			
			if (text.startsWith("."))
			{
				StringTokenizer st = new StringTokenizer(text);
				IVoicedCommandHandler vch;
				String command = "";
				String params = "";
				
				if (st.countTokens() > 1)
				{
					command = st.nextToken().substring(1);
					params = text.substring(command.length() + 2);
					vch = VoicedCommandHandler.getInstance().getHandler(command);
				}
				else
				{
					command = text.substring(1);
					vch = VoicedCommandHandler.getInstance().getHandler(command);
				}
				
				if (vch != null)
				{
					vch.useVoicedCommand(command, activeChar, params);
					vcd_used = true;
				}
				else
				{
					vcd_used = false;
				}
			}
			
			if (!vcd_used)
			{
				CreatureSay cs = new CreatureSay(activeChar.getObjectId(), type, activeChar.getName(), text);
				Collection<L2PcInstance> plrs = L2World.getInstance().getVisibleObjects(activeChar, L2PcInstance.class, 1250);
				
				for (L2PcInstance player : plrs)
				{
					if ((player != null) && !BlockList.isBlocked(player, activeChar))
						player.sendPacket(cs);
				}
				activeChar.sendPacket(cs);
			}
		}
	}
	
	@Override
	public int[] getChatTypeList()
	{
		return COMMAND_IDS;
	}
}