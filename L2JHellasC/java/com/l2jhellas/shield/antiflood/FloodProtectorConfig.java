package com.l2jhellas.shield.antiflood;

public final class FloodProtectorConfig
{
	
	public String FLOOD_PROTECTOR_TYPE;
	
	public int FLOOD_PROTECTION_INTERVAL;
	
	public boolean LOG_FLOODING;
	
	public int PUNISHMENT_LIMIT;
	
	public String PUNISHMENT_TYPE;
	
	public int PUNISHMENT_TIME;
	
	public FloodProtectorConfig(final String floodProtectorType)
	{
		super();
		FLOOD_PROTECTOR_TYPE = floodProtectorType;
	}
}