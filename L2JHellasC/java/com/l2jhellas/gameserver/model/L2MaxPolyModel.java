package com.l2jhellas.gameserver.model;

import com.l2jhellas.gameserver.datatables.sql.ClanTable;
import com.l2jhellas.gameserver.datatables.xml.ArmorSetsData;
import com.l2jhellas.gameserver.datatables.xml.ArmorSetsData.ArmorDummy;
import com.l2jhellas.gameserver.datatables.xml.CharTemplateData;
import com.l2jhellas.gameserver.emum.ClassRace;
import com.l2jhellas.gameserver.templates.StatsSet;

public class L2MaxPolyModel
{
	// Base
	private String _name;
	private String _title;
	private final ClassRace _race;
	private int _sex;
	private int _hair;
	private int _hairColor;
	private int _face;
	private int _classId;
	private int _npcId;
	
	// Item related
	private int _weaponIdRH;
	private int _weaponIdLH;
	private int _weaponIdEnc;
	private int _armorId; // all others p_dolls will be set auto if the value is a valid armor set id
	private int _head; // not seen
	private int _hats;
	private int _faces;
	private int _chest;
	private int _legs;
	private int _gloves;
	private int _feet;
	
	// Misc
	private int _abnormalEffect;
	private int _pvpFlag;
	private int _karma;
	private int _recom;
	private L2Clan _clan;
	private int _isHero;
	private int _pledge;
	private int _nameColor = 0xFFFFFF;
	private int _titleColor = 0xFFFF77;
	
	public L2MaxPolyModel(StatsSet data)
	{
		_name = data.getString("name");
		_title = data.getString("title");
		_sex = data.getInteger("sex");
		_hair = data.getInteger("hair");
		_hairColor = data.getInteger("hairColor");
		_face = data.getInteger("face");
		_classId = data.getInteger("classId");
		_npcId = data.getInteger("npcId");
		_weaponIdRH = data.getInteger("weaponIdRH");
		_weaponIdLH = data.getInteger("weaponIdLH");
		_weaponIdEnc = data.getInteger("weaponIdEnc");
		_armorId = data.getInteger("armorId");
		_head = data.getInteger("head");
		_hats = data.getInteger("hats");
		_faces = data.getInteger("faces");
		_chest = data.getInteger("chest");
		_legs = data.getInteger("legs");
		_gloves = data.getInteger("gloves");
		_feet = data.getInteger("feet");
		_abnormalEffect = data.getInteger("abnormalEffect");
		_pvpFlag = data.getInteger("pvpFlag");
		_karma = data.getInteger("karma");
		_recom = data.getInteger("recom");
		_clan = ClanTable.getInstance().getClan(data.getInteger("clan"));
		_isHero = data.getInteger("isHero");
		_pledge = data.getInteger("pledge");
		
		if (data.getInteger("nameColor") > 0)
			_nameColor = data.getInteger("nameColor");
		if (data.getInteger("titleColor") > 0)
			_titleColor = data.getInteger("titleColor");
		
		_race = CharTemplateData.getInstance().getTemplate(_classId).getRace();
		
		ArmorDummy armor = ArmorSetsData.getInstance().getCusArmorSets(_armorId);
		
		if (armor != null)
		{
			_head = armor.getHead();
			_chest = armor.getChest();
			_legs = armor.getLegs();
			_gloves = armor.getGloves();
			_feet = armor.getFeet();
		}
	}
	
	public String getName()
	{
		return _name;
	}
	
	public void setName(String name)
	{
		_name = name;
	}
	
	public String getTitle()
	{
		return _title;
	}
	
	public void setTitle(String title)
	{
		_title = title;
	}
	
	public int getSex()
	{
		return _sex;
	}
	
	public void setSex(int sex)
	{
		_sex = sex;
	}
	
	public int getHair()
	{
		return _hair;
	}
	
	public void setHair(int hair)
	{
		_hair = hair;
	}
	
	public int getHairColor()
	{
		return _hairColor;
	}
	
	public void setHairColor(int hairColor)
	{
		_hairColor = hairColor;
	}
	
	public int getFace()
	{
		return _face;
	}
	
	public void setFace(int face)
	{
		_face = face;
	}
	
	public int getClassId()
	{
		return _classId;
	}
	
	public void setClassId(int classId)
	{
		_classId = classId;
	}
	
	public int getWeaponIdRH()
	{
		return _weaponIdRH;
	}
	
	public int getWeaponIdLH()
	{
		return _weaponIdLH;
	}
	
	public void setWeaponIdRH(int weaponIdRH)
	{
		_weaponIdRH = weaponIdRH;
	}
	
	public void setWeaponIdLH(int weaponIdLH)
	{
		_weaponIdLH = weaponIdLH;
	}
	
	public int getWeaponIdEnc()
	{
		return _weaponIdEnc;
	}
	
	public void setWeaponIdEnc(int weaponIdEnc)
	{
		_weaponIdEnc = weaponIdEnc;
	}
	
	public int getArmorId()
	{
		return _armorId;
	}
	
	public void setArmorId(int armorId)
	{
		_armorId = armorId;
	}
	
	public int getHead()
	{
		return _head;
	}
	
	public void setHead(int head)
	{
		_head = head;
	}
	
	public int getHats()
	{
		return _hats;
	}
	
	public void setHats(int hats)
	{
		_hats = hats;
	}
	
	public int getFaces()
	{
		return _faces;
	}
	
	public void setFaces(int faces)
	{
		_faces = faces;
	}
	
	public int getChest()
	{
		return _chest;
	}
	
	public void setChest(int chest)
	{
		_chest = chest;
	}
	
	public int getLegs()
	{
		return _legs;
	}
	
	public void setLegs(int legs)
	{
		_legs = legs;
	}
	
	public int getGloves()
	{
		return _gloves;
	}
	
	public void setGloves(int gloves)
	{
		_gloves = gloves;
	}
	
	public int getFeet()
	{
		return _feet;
	}
	
	public void setFeet(int feet)
	{
		_feet = feet;
	}
	
	public int getAbnormalEffect()
	{
		return _abnormalEffect;
	}
	
	public void setAbnormalEffect(int abnormalEffect)
	{
		_abnormalEffect = abnormalEffect;
	}
	
	public int getPvpFlag()
	{
		return _pvpFlag;
	}
	
	public void setPvpFlag(int pvpFlag)
	{
		_pvpFlag = pvpFlag;
	}
	
	public int getKarma()
	{
		return _karma;
	}
	
	public void setKarma(int karma)
	{
		_karma = karma;
	}
	
	public int getRecom()
	{
		return _recom;
	}
	
	public void setRecom(int recom)
	{
		_recom = recom;
	}
	
	public L2Clan getClan()
	{
		return _clan;
	}
	
	public void setClan(L2Clan clan)
	{
		_clan = clan;
	}
	
	public int getIsHero()
	{
		return _isHero;
	}
	
	public void setIsHero(int isHero)
	{
		_isHero = isHero;
	}
	
	public int getPledge()
	{
		return _pledge;
	}
	
	public void setPledge(int pledge)
	{
		_pledge = pledge;
	}
	
	public int getNameColor()
	{
		return _nameColor;
	}
	
	public void setNameColor(int nameColor)
	{
		_nameColor = nameColor;
	}
	
	public int getTitleColor()
	{
		return _titleColor;
	}
	
	public void setTitleColor(int titleColor)
	{
		_titleColor = titleColor;
	}
	
	public void setNpcId(int npcId)
	{
		_npcId = npcId;
	}
	
	public int getNpcId()
	{
		return _npcId;
	}
	
	public ClassRace getRace()
	{
		return _race;
	}
}