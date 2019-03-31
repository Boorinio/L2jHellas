package com.l2jhellas.gameserver.geodata;

import com.l2jhellas.gameserver.geometry.Polygon;

import java.util.HashMap;

public interface GeoControl
{
	public abstract Polygon getGeoPos();
	
	public abstract void setGeoPos(Polygon value);
	
	public abstract HashMap<Long, Byte> getGeoAround();
	
	public abstract void setGeoAround(HashMap<Long, Byte> value);
	
	public abstract boolean isGeoCloser();
}
