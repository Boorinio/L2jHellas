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
package com.l2jhellas.util;

import java.io.File;

import javolution.text.TextBuilder;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.PledgeCrest;

/**
 * @author Masterio
 */
public class ServerSideImage
{

	/**
	 * Generate Html tag for image loaded from server side, image packet is sended before generate the Html tag.
	 *
	 * @param player
	 *        - activeChar
	 * @param imgId
	 *        - any id
	 * @param src
	 *        - image name, example: image, no extention's, image should be in game/data/images folder. <br>
	 *        Adding folder is allowed.
	 * @param width
	 * @param height
	 * @return
	 */
	public static final TextBuilder putImgHtmlTag(L2PcInstance player, int imgId, String src, int width, int height)
	{
		TextBuilder tb = new TextBuilder();
		// Conversion from .png to .dds, and crest packed send
		try
		{
			File image = new File("data/images/" + src + ".png");
			PledgeCrest packet = new PledgeCrest(imgId, DDSConverter.convertToDDS(image).array());
			player.sendPacket(packet);

			tb.append("<img src=\"Crest.crest_" + Config.SERVER_ID + "_" + imgId + "\" width=" + width + " height=" + height + ">");

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return tb;

	}
}
