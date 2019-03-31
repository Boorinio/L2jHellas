package com.l2jhellas.gameserver.network.clientpackets;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.network.serverpackets.KeyPacket;

import java.util.logging.Logger;

public final class ProtocolVersion extends L2GameClientPacket
{
	static Logger _log = Logger.getLogger(ProtocolVersion.class.getName());
	private static final String _C__00_PROTOCOLVERSION = "[C] 00 ProtocolVersion";
	
	private int _version;
	
	@Override
	protected void readImpl()
	{
		_version = readD();
	}
	
	@Override
	protected void runImpl()
	{
		// this packet is never encrypted
		if (_version == -2)
		{
			if (Config.DEBUG)
				_log.info("Ping received.");
			// this is just a ping attempt from the new C2 client
			getClient().closeNow();
		}
		else if (_version < 1 || _version > 999)
		{
			_log.info("Client: " + getClient().toString() + " -> Protocol Revision: " + _version + " is invalid. Minimum is " + 1 + " and Maximum is " + 999 + " are supported. Closing connection.");
			_log.warning(ProtocolVersion.class.getName() + ": Wrong Protocol Version " + _version);
			getClient().closeNow();
		}
		else
		{
			if (Config.DEBUG)
			{
				_log.fine("Client Protocol Revision is ok: " + _version);
			}
			
			KeyPacket pk = new KeyPacket(getClient().enableCrypt());
			getClient().sendPacket(pk);
		}
	}
	
	@Override
	public String getType()
	{
		return _C__00_PROTOCOLVERSION;
	}
}