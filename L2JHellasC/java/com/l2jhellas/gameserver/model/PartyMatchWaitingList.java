package com.l2jhellas.gameserver.model;

import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

import java.util.ArrayList;
import java.util.List;

public class PartyMatchWaitingList
{
	private final List<L2PcInstance> _members;
	
	protected PartyMatchWaitingList()
	{
		_members = new ArrayList<>();
	}
	
	public void addPlayer(L2PcInstance player)
	{
		if (!_members.contains(player))
			_members.add(player);
	}
	
	public void removePlayer(L2PcInstance player)
	{
		if (_members.contains(player))
			_members.remove(player);
	}
	
	public List<L2PcInstance> getPlayers()
	{
		return _members;
	}
	
	public static PartyMatchWaitingList getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final PartyMatchWaitingList _instance = new PartyMatchWaitingList();
	}
}