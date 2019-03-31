package com.l2jhellas.gameserver.model.actor.instance;

import com.l2jhellas.gameserver.datatables.sql.BuffTemplateTable;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.L2Skill;
import com.l2jhellas.gameserver.model.L2SkillType;
import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.skills.SkillTable;
import com.l2jhellas.gameserver.templates.L2BuffTemplate;

import java.util.ArrayList;

public class L2EventBufferInstance
{
	static L2PcInstance selfBuffer;
	static L2Npc npcBuffer;
	
	public static void makeBuffs(L2PcInstance player, int _templateId, L2Object efector, boolean paymentRequired)
	{
		if (player == null)
			return;
		
		ArrayList<L2BuffTemplate> _templateBuffs = new ArrayList<>();
		_templateBuffs = BuffTemplateTable.getInstance().getBuffTemplate(_templateId);
		
		if ((_templateBuffs == null) || (_templateBuffs.size() == 0))
			return;
		
		int _priceTotal = 0;
		int _pricePoints = 0;
		
		for (L2BuffTemplate _buff : _templateBuffs)
		{
			if (paymentRequired)
			{
				if (!_buff.checkPrice(player))
				{
					player.sendMessage("Not enough adena.");
					return;
				}
				if (!_buff.checkPoints(player))
				{
					player.sendMessage("Not enough Event Points.");
					return;
				}
			}
			
			if (_buff.checkPlayer(player) && _buff.checkPrice(player))
			{
				
				player.setCurrentHpMp(player.getMaxHp() + 5000, player.getMaxMp() + 5000);
				
				L2Skill skill = SkillTable.getInstance().getInfo(_buff.getSkillId(), _buff.getSkillLevel());
				if (skill != null)
				{
					skill.getEffects(player, player);
				}
				
				if (_buff.getSkill().getSkillType() == L2SkillType.SUMMON)
				{
					player.doCast(_buff.getSkill());
				}
				
				try
				{
					Thread.sleep(50);
				}
				catch (Exception e)
				{
				}
				
			}
		}
		
		if (paymentRequired && (_pricePoints > 0 || _priceTotal > 0))
		{
			if (_pricePoints > 0)
			{
				int previousPoints = player.getEventPoints();
				player.setEventPoints(player.getEventPoints() - _pricePoints);
				player.sendMessage("You had " + previousPoints + " Event Points, and now you have " + player.getEventPoints() + " Event Points.");
			}
			if (_priceTotal > 0)
				player.reduceAdena("NpcBuffer", _priceTotal, player.getLastFolkNPC(), true);
		}
	}
}