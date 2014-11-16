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
package handlers.itemhandlers;

import com.l2jhellas.gameserver.handler.IItemHandler;
import com.l2jhellas.gameserver.model.L2ItemInstance;
import com.l2jhellas.gameserver.model.actor.L2Playable;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;

public class MapForestOfTheDead implements IItemHandler
{
	public MapForestOfTheDead()
	{}

	private static int _itemIds[] =
	{
		7063
	};

	@Override
	public void useItem(L2Playable playable, L2ItemInstance item)
	{
		if(!(playable instanceof L2PcInstance))
		{
			return;
		}

		int itemId = item.getItemId();
		if(itemId == 7063)
		{
			NpcHtmlMessage html = new NpcHtmlMessage(5);
			StringBuilder map = new StringBuilder("<html><title>Map - Forest of the Dead</title>");
			map.append("<body>");
			map.append("<br>");
			map.append("Map :");
			map.append("<br>");
			map.append("<table>");
			map.append("<tr><td>");
			map.append("<img src=\"icon.Quest_deadperson_forest_t00\" width=255 height=255>");
			map.append("</td></tr>");
			map.append("</table>");
			map.append("</body></html>");
			html.setHtml(map.toString());
			playable.sendPacket(html);
		}
	}

	@Override
	public int[] getItemIds()
	{
		return _itemIds;
	}
}