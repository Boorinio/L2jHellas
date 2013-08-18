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

import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.datatables.sql.PetDataTable;
import com.l2jhellas.gameserver.handler.IItemHandler;
import com.l2jhellas.gameserver.handler.ItemHandler;
import com.l2jhellas.gameserver.model.L2ItemInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2PetInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.PetItemList;
import com.l2jhellas.gameserver.network.serverpackets.SystemMessage;
import com.l2jhellas.gameserver.templates.L2Item;

public final class RequestPetUseItem extends L2GameClientPacket
{
	private static Logger _log = Logger.getLogger(RequestPetUseItem.class.getName());
	private static final String _C__8A_REQUESTPETUSEITEM = "[C] 8a RequestPetUseItem";

	private int _objectId;

	@Override
	protected void readImpl()
	{
		_objectId = readD();
	}

	@Override
	protected void runImpl()
	{
		L2PcInstance activeChar = getClient().getActiveChar();

		if (activeChar == null)
			return;

		L2PetInstance pet = (L2PetInstance) activeChar.getPet();

		if (pet == null)
			return;

		L2ItemInstance item = pet.getInventory().getItemByObjectId(_objectId);

		if (item == null)
			return;

		if (item.isWear())
			return;

		int itemId = item.getItemId();

		if (activeChar.isAlikeDead() || pet.isDead())
		{
			SystemMessage sm = new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED);
			sm.addItemName(item.getItemId());
			activeChar.sendPacket(sm);
			sm = null;
			return;
		}

		if (Config.DEBUG)
			_log.finest(activeChar.getObjectId() + ": pet use item " + _objectId);

		// check if the item matches the pet
		if (item.isEquipable())
		{
			if (PetDataTable.isWolf(pet.getNpcId()) && // wolf
			item.getItem().isForWolf())
			{
				useItem(pet, item, activeChar);
				return;
			}
			else if (PetDataTable.isHatchling(pet.getNpcId()) && // hatchlings
			item.getItem().isForHatchling())
			{
				useItem(pet, item, activeChar);
				return;
			}
			else if (PetDataTable.isStrider(pet.getNpcId()) && // striders
			item.getItem().isForStrider())
			{
				useItem(pet, item, activeChar);
				return;
			}
			else if (PetDataTable.isBaby(pet.getNpcId()) && // baby pets (buffalo, cougar, kookaboora)
			item.getItem().isForBabyPet())
			{
				useItem(pet, item, activeChar);
				return;
			}
			else
			{
				activeChar.sendPacket(new SystemMessage(SystemMessageId.ITEM_NOT_FOR_PETS));
				return;
			}
		}
		else if (PetDataTable.isPetFood(itemId))
		{
			if (PetDataTable.isWolf(pet.getNpcId()) && PetDataTable.isWolfFood(itemId))
			{
				feed(activeChar, pet, item);
				return;
			}
			if (PetDataTable.isSinEater(pet.getNpcId()) && PetDataTable.isSinEaterFood(itemId))
			{
				feed(activeChar, pet, item);
				return;
			}
			else if (PetDataTable.isHatchling(pet.getNpcId()) && PetDataTable.isHatchlingFood(itemId))
			{
				feed(activeChar, pet, item);
				return;
			}
			else if (PetDataTable.isStrider(pet.getNpcId()) && PetDataTable.isStriderFood(itemId))
			{
				feed(activeChar, pet, item);
				return;
			}
			else if (PetDataTable.isWyvern(pet.getNpcId()) && PetDataTable.isWyvernFood(itemId))
			{
				feed(activeChar, pet, item);
				return;
			}
			else if (PetDataTable.isBaby(pet.getNpcId()) && PetDataTable.isBabyFood(itemId))
			{
				feed(activeChar, pet, item);
			}
		}

		IItemHandler handler = ItemHandler.getInstance().getItemHandler(item.getItemId());

		if (handler != null)
		{
			useItem(pet, item, activeChar);
		}
		else
		{
			SystemMessage sm = new SystemMessage(SystemMessageId.ITEM_NOT_FOR_PETS);
			activeChar.sendPacket(sm);
		}

		return;
	}

	private synchronized void useItem(L2PetInstance pet, L2ItemInstance item, L2PcInstance activeChar)
	{
		if (item.isEquipable())
		{
			if (item.isEquipped())
			{
				pet.getInventory().unEquipItemInSlot(item.getLocationSlot());
				switch (item.getItem().getBodyPart())
				{
					case L2Item.SLOT_R_HAND:
						pet.setWeapon(0);
					break;
					case L2Item.SLOT_CHEST:
						pet.setArmor(0);
					break;
					case L2Item.SLOT_NECK:
						pet.setJewel(0);
					break;
				}
			}
			else
			{
				pet.getInventory().equipItem(item);
				switch (item.getItem().getBodyPart())
				{
					case L2Item.SLOT_R_HAND:
						pet.setWeapon(item.getItemId());
					break;
					case L2Item.SLOT_CHEST:
						pet.setArmor(item.getItemId());
					break;
					case L2Item.SLOT_NECK:
						pet.setJewel(item.getItemId());
					break;
				}

			}

			PetItemList pil = new PetItemList(pet);
			activeChar.sendPacket(pil);

			pet.updateAndBroadcastStatus(1);
		}
		else
		{
			// _log.finest("item not equipable id:"+ item.getItemId());
			IItemHandler handler = ItemHandler.getInstance().getItemHandler(item.getItemId());

			if (handler == null)
				_log.warning("no itemhandler registered for itemId:" + item.getItemId());
			else
			{
				handler.useItem(pet, item);
				pet.updateAndBroadcastStatus(1);
			}
		}
	}

	/**
	 * When fed by owner double click on food from pet inventory. <BR>
	 * <BR>
	 * <FONT COLOR=#FF0000><B> <U>Caution</U> : 1 food = 100 points of currentFed</B></FONT><BR>
	 * <BR>
	 */
	private void feed(L2PcInstance player, L2PetInstance pet, L2ItemInstance item)
	{
		// if pet has food in inventory
		if (pet.destroyItem("Feed", item.getObjectId(), 1, pet, false))
			pet.setCurrentFed(pet.getCurrentFed() + 100);
		pet.broadcastStatusUpdate();
	}

	@Override
	public String getType()
	{
		return _C__8A_REQUESTPETUSEITEM;
	}
}