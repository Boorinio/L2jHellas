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
		
		ct.addChar(CharTemplateData.getInstance().getTemplate(0));
		ct.addChar(CharTemplateData.getInstance().getTemplate(ClassId.HUMAN_FIGHTER));
		ct.addChar(CharTemplateData.getInstance().getTemplate(ClassId.HUMAN_MYSTIC));
		ct.addChar(CharTemplateData.getInstance().getTemplate(ClassId.ELVEN_FIGHTER));
		ct.addChar(CharTemplateData.getInstance().getTemplate(ClassId.ELVEN_MYSTIC));
		ct.addChar(CharTemplateData.getInstance().getTemplate(ClassId.DARK_FIGHTER));
		ct.addChar(CharTemplateData.getInstance().getTemplate(ClassId.DARK_MYSTIC));
		ct.addChar(CharTemplateData.getInstance().getTemplate(ClassId.ORC_FIGHTER));
		ct.addChar(CharTemplateData.getInstance().getTemplate(ClassId.ORC_MYSTIC));
		ct.addChar(CharTemplateData.getInstance().getTemplate(ClassId.DWARVEN_FIGHTER));
		
		sendPacket(ct);
	}

	@Override
	public String getType()
	{
		return _C__0E_NEWCHARACTER;
	}
}