/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jhellas.gameserver.network.clientpackets;

import com.l2jhellas.gameserver.datatables.xml.CharTemplateData;
import com.l2jhellas.gameserver.model.base.ClassId;
import com.l2jhellas.gameserver.network.serverpackets.CharTemplates;
import com.l2jhellas.gameserver.templates.L2PcTemplate;

public final class NewCharacter extends L2GameClientPacket
{
	private static final String _C__0E_NEWCHARACTER = "[C] 0E NewCharacter";

	@Override
	protected void readImpl()
	{

	}

	@Override
	protected void runImpl()
	{
		CharTemplates ct = new CharTemplates();

		L2PcTemplate template = CharTemplateData.getInstance().getTemplate(0);
		ct.addChar(template);
		// human fighter
		template = CharTemplateData.getInstance().getTemplate(ClassId.fighter);
		ct.addChar(template);
		// human mage
		template = CharTemplateData.getInstance().getTemplate(ClassId.mage);
		ct.addChar(template);
		// elf fighter
		template = CharTemplateData.getInstance().getTemplate(ClassId.elvenFighter);
		ct.addChar(template);
		// elf mage
		template = CharTemplateData.getInstance().getTemplate(ClassId.elvenMage);
		ct.addChar(template);
		// dark elf fighter
		template = CharTemplateData.getInstance().getTemplate(ClassId.darkFighter);
		ct.addChar(template);
		// dark elf mage
		template = CharTemplateData.getInstance().getTemplate(ClassId.darkMage);
		ct.addChar(template);
		// orc fighter
		template = CharTemplateData.getInstance().getTemplate(ClassId.orcFighter);
		ct.addChar(template);
		// orc mage
		template = CharTemplateData.getInstance().getTemplate(ClassId.orcMage);
		ct.addChar(template);
		// dwarf fighter
		template = CharTemplateData.getInstance().getTemplate(ClassId.dwarvenFighter);
		ct.addChar(template);

		sendPacket(ct);
	}

	@Override
	public String getType()
	{
		return _C__0E_NEWCHARACTER;
	}
}