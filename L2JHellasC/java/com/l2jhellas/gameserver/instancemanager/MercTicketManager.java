package com.l2jhellas.gameserver.instancemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.datatables.sql.NpcData;
import com.l2jhellas.gameserver.enums.items.ItemLocation;
import com.l2jhellas.gameserver.idfactory.IdFactory;
import com.l2jhellas.gameserver.model.AutoChatHandler;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.actor.instance.L2SiegeGuardInstance;
import com.l2jhellas.gameserver.model.actor.item.L2ItemInstance;
import com.l2jhellas.gameserver.model.entity.Castle;
import com.l2jhellas.gameserver.templates.L2NpcTemplate;
import com.l2jhellas.util.database.L2DatabaseFactory;

public class MercTicketManager
{
	protected static Logger _log = Logger.getLogger(MercTicketManager.class.getName());
	
	private static MercTicketManager _instance;
	
	public static final MercTicketManager getInstance()
	{
		// CastleManager.getInstance();
		if (_instance == null)
		{
			_instance = new MercTicketManager();
			_instance.load();
		}
		return _instance;
	}
	
	private List<L2ItemInstance> _droppedTickets; // to keep track of items on the ground
	
	// TODO move all these values into siege.ini
	// max tickets per merc type = 10 + (castleid * 2)?
	// max ticker per castle = 40 + (castleid * 20)?
	private static final int[] MAX_MERC_PER_TYPE =
	{
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10, // Gludio
		15,
		15,
		15,
		15,
		15,
		15,
		15,
		15,
		15,
		15,
		15,
		15,
		15,
		15,
		15,
		15,
		15,
		15,
		15,
		15, // Dion
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10, // Giran
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10,
		10, // Oren
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20, // Aden
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20, // Innadril
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20, // Goddard
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20, // Rune
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20,
		20
	// Schuttgart
	};
	private static final int[] MERCS_MAX_PER_CASTLE =
	{
		100, // Gludio
		150, // Dion
		200, // Giran
		300, // Oren
		400, // Aden
		400, // Innadril
		400, // Goddard
		400, // Rune
		400
	// Schuttgart
	};
	
	private static final int[] ITEM_IDS =
	{
		3960,
		3961,
		3962,
		3963,
		3964,
		3965,
		3966,
		3967,
		3968,
		3969,
		6038,
		6039,
		6040,
		6041,
		6042,
		6043,
		6044,
		6045,
		6046,
		6047, // Gludio
		3973,
		3974,
		3975,
		3976,
		3977,
		3978,
		3979,
		3980,
		3981,
		3982,
		6051,
		6052,
		6053,
		6054,
		6055,
		6056,
		6057,
		6058,
		6059,
		6060, // Dion
		3986,
		3987,
		3988,
		3989,
		3990,
		3991,
		3992,
		3993,
		3994,
		3995,
		6064,
		6065,
		6066,
		6067,
		6068,
		6069,
		6070,
		6071,
		6072,
		6073, // Giran
		3999,
		4000,
		4001,
		4002,
		4003,
		4004,
		4005,
		4006,
		4007,
		4008,
		6077,
		6078,
		6079,
		6080,
		6081,
		6082,
		6083,
		6084,
		6085,
		6086, // Oren
		4012,
		4013,
		4014,
		4015,
		4016,
		4017,
		4018,
		4019,
		4020,
		4021,
		6090,
		6091,
		6092,
		6093,
		6094,
		6095,
		6096,
		6097,
		6098,
		6099, // Aden
		5205,
		5206,
		5207,
		5208,
		5209,
		5210,
		5211,
		5212,
		5213,
		5214,
		6105,
		6106,
		6107,
		6108,
		6109,
		6110,
		6111,
		6112,
		6113,
		6114, // Innadril
		6779,
		6780,
		6781,
		6782,
		6783,
		6784,
		6785,
		6786,
		6787,
		6788,
		6792,
		6793,
		6794,
		6795,
		6796,
		6797,
		6798,
		6799,
		6800,
		6801, // Goddard
		7973,
		7974,
		7975,
		7976,
		7977,
		7978,
		7979,
		7980,
		7981,
		7982,
		7988,
		7989,
		7990,
		7991,
		7992,
		7993,
		7994,
		7995,
		7996,
		7997, // Rune
		7918,
		7919,
		7920,
		7921,
		7922,
		7923,
		7924,
		7925,
		7926,
		7927,
		7931,
		7932,
		7933,
		7934,
		7935,
		7936,
		7937,
		7938,
		7939,
		7940
	// Schuttgart
	};
	
	private static final int[] NPC_IDS =
	{
		35010,
		35011,
		35012,
		35013,
		35014,
		35015,
		35016,
		35017,
		35018,
		35019,
		35030,
		35031,
		35032,
		35033,
		35034,
		35035,
		35036,
		35037,
		35038,
		35039, // Gludio
		35010,
		35011,
		35012,
		35013,
		35014,
		35015,
		35016,
		35017,
		35018,
		35019,
		35030,
		35031,
		35032,
		35033,
		35034,
		35035,
		35036,
		35037,
		35038,
		35039, // Dion
		35010,
		35011,
		35012,
		35013,
		35014,
		35015,
		35016,
		35017,
		35018,
		35019,
		35030,
		35031,
		35032,
		35033,
		35034,
		35035,
		35036,
		35037,
		35038,
		35039, // Giran
		35010,
		35011,
		35012,
		35013,
		35014,
		35015,
		35016,
		35017,
		35018,
		35019,
		35030,
		35031,
		35032,
		35033,
		35034,
		35035,
		35036,
		35037,
		35038,
		35039, // Oren
		35010,
		35011,
		35012,
		35013,
		35014,
		35015,
		35016,
		35017,
		35018,
		35019,
		35030,
		35031,
		35032,
		35033,
		35034,
		35035,
		35036,
		35037,
		35038,
		35039, // Aden
		35010,
		35011,
		35012,
		35013,
		35014,
		35015,
		35016,
		35017,
		35018,
		35019,
		35030,
		35031,
		35032,
		35033,
		35034,
		35035,
		35036,
		35037,
		35038,
		35039, // Innadril
		35010,
		35011,
		35012,
		35013,
		35014,
		35015,
		35016,
		35017,
		35018,
		35019,
		35030,
		35031,
		35032,
		35033,
		35034,
		35035,
		35036,
		35037,
		35038,
		35039, // Goddard
		35010,
		35011,
		35012,
		35013,
		35014,
		35015,
		35016,
		35017,
		35018,
		35019,
		35030,
		35031,
		35032,
		35033,
		35034,
		35035,
		35036,
		35037,
		35038,
		35039, // Rune
		35010,
		35011,
		35012,
		35013,
		35014,
		35015,
		35016,
		35017,
		35018,
		35019,
		35030,
		35031,
		35032,
		35033,
		35034,
		35035,
		35036,
		35037,
		35038,
		35039
	// Schuttgart
	};
	
	public MercTicketManager()
	{
	}
	
	// returns the castleId for the passed ticket item id
	public int getTicketCastleId(int itemId)
	{
		if ((itemId >= ITEM_IDS[0] && itemId <= ITEM_IDS[9]) || (itemId >= ITEM_IDS[10] && itemId <= ITEM_IDS[19]))
			return 1; // Gludio
		if ((itemId >= ITEM_IDS[20] && itemId <= ITEM_IDS[29]) || (itemId >= ITEM_IDS[30] && itemId <= ITEM_IDS[39]))
			return 2; // Dion
		if ((itemId >= ITEM_IDS[40] && itemId <= ITEM_IDS[49]) || (itemId >= ITEM_IDS[50] && itemId <= ITEM_IDS[59]))
			return 3; // Giran
		if ((itemId >= ITEM_IDS[60] && itemId <= ITEM_IDS[69]) || (itemId >= ITEM_IDS[70] && itemId <= ITEM_IDS[79]))
			return 4; // Oren
		if ((itemId >= ITEM_IDS[80] && itemId <= ITEM_IDS[89]) || (itemId >= ITEM_IDS[90] && itemId <= ITEM_IDS[99]))
			return 5; // Aden
		if ((itemId >= ITEM_IDS[100] && itemId <= ITEM_IDS[109]) || (itemId >= ITEM_IDS[110] && itemId <= ITEM_IDS[119]))
			return 6; // Innadril
		if ((itemId >= ITEM_IDS[120] && itemId <= ITEM_IDS[129]) || (itemId >= ITEM_IDS[130] && itemId <= ITEM_IDS[139]))
			return 7; // Goddard
		if ((itemId >= ITEM_IDS[140] && itemId <= ITEM_IDS[149]) || (itemId >= ITEM_IDS[150] && itemId <= ITEM_IDS[159]))
			return 8; // Rune
		if ((itemId >= ITEM_IDS[160] && itemId <= ITEM_IDS[169]) || (itemId >= ITEM_IDS[170] && itemId <= ITEM_IDS[179]))
			return 9; // Schuttgart
		return -1;
	}
	
	public void reload()
	{
		getDroppedTickets().clear();
		load();
	}
	
	private final void load()
	{
		// load merc tickets into the world
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT * FROM castle_siege_guards WHERE isHired=1");
			ResultSet rs = statement.executeQuery();
			
			int npcId;
			int itemId;
			int x, y, z;
			// start index to begin the search for the itemId corresponding to this NPC
			// this will help with:
			// a) skip unnecessary iterations in the search loop
			// b) avoid finding the wrong itemId whenever tickets of different spawn the same npc!
			int startindex = 0;
			
			while (rs.next())
			{
				npcId = rs.getInt("npcId");
				x = rs.getInt("x");
				y = rs.getInt("y");
				z = rs.getInt("z");
				Castle castle = CastleManager.getInstance().getCastle(x, y, z);
				if (castle != null)
					startindex = 10 * (castle.getCastleId() - 1);
				
				// find the FIRST ticket itemId with spawns the saved NPC in the saved location
				for (int i = startindex; i < NPC_IDS.length; i++)
					if (NPC_IDS[i] == npcId) // Find the index of the item used
					{
						// only handle tickets if a siege is not ongoing in this npc's castle
						
						if ((castle != null) && !(castle.getSiege().getIsInProgress()))
						{
							itemId = ITEM_IDS[i];
							// create the ticket in the gameworld
							L2ItemInstance dropticket = new L2ItemInstance(IdFactory.getInstance().getNextId(), itemId);
							dropticket.setLocation(ItemLocation.INVENTORY);
							dropticket.dropMe(null, x, y, z);
							dropticket.setDropTime(0); // avoids it from beeing removed by the auto item destroyer
							L2World.getInstance().storeObject(dropticket);
							getDroppedTickets().add(dropticket);
						}
						break;
					}
			}
			rs.close();
			statement.close();
			
			_log.info(MercTicketManager.class.getSimpleName() + ": Loaded " + getDroppedTickets().size() + " Mercenary Tickets.");
		}
		catch (Exception e)
		{
			_log.warning(MercTicketManager.class.getName() + ": loadMercenaryData(): ");
			if (Config.DEVELOPER)
				e.printStackTrace();
		}
	}
	
	public boolean isAtTypeLimit(int itemId)
	{
		int limit = -1;
		// find the max value for this item
		for (int i = 0; i < ITEM_IDS.length; i++)
			if (ITEM_IDS[i] == itemId) // Find the index of the item used
			{
				limit = MAX_MERC_PER_TYPE[i];
				break;
			}
		
		if (limit <= 0)
			return true;
		
		int count = 0;
		L2ItemInstance ticket;
		for (int i = 0; i < getDroppedTickets().size(); i++)
		{
			ticket = getDroppedTickets().get(i);
			if (ticket != null && ticket.getItemId() == itemId)
				count++;
		}
		if (count >= limit)
			return true;
		return false;
	}
	
	public boolean isAtCasleLimit(int itemId)
	{
		int castleId = getTicketCastleId(itemId);
		if (castleId <= 0)
			return true;
		int limit = MERCS_MAX_PER_CASTLE[castleId - 1];
		if (limit <= 0)
			return true;
		
		int count = 0;
		L2ItemInstance ticket;
		for (int i = 0; i < getDroppedTickets().size(); i++)
		{
			ticket = getDroppedTickets().get(i);
			if ((ticket != null) && (getTicketCastleId(ticket.getItemId()) == castleId))
				count++;
		}
		if (count >= limit)
			return true;
		return false;
	}
	
	public int addTicket(int itemId, L2PcInstance activeChar, String[] messages)
	{
		int x = activeChar.getX();
		int y = activeChar.getY();
		int z = activeChar.getZ();
		int heading = activeChar.getHeading();
		
		Castle castle = CastleManager.getInstance().getCastle(activeChar);
		if (castle == null) // this should never happen at this point
			return -1;
		
		// check if this item can be added here
		for (int i = 0; i < ITEM_IDS.length; i++)
		{
			if (ITEM_IDS[i] == itemId) // Find the index of the item used
			{
				spawnMercenary(NPC_IDS[i], x, y, z, 3000, messages, 0);
				
				// Hire merc for this caslte. NpcId is at the same index as the item used.
				SiegeGuardManager.getInstance().hireMerc(castle,x, y, z, heading, NPC_IDS[i]);
				// create the ticket in the gameworld
				L2ItemInstance dropticket = new L2ItemInstance(IdFactory.getInstance().getNextId(), itemId);
				dropticket.setLocation(ItemLocation.INVENTORY);
				dropticket.dropMe(null, x, y, z);
				dropticket.setDropTime(0); // avoids it from beeing removed by the auto item destroyer
				L2World.getInstance().storeObject(dropticket); // add to the world
				// and keep track of this ticket in the list
				_droppedTickets.add(dropticket);
				
				return NPC_IDS[i];
			}
		}
		return -1;
	}
	
	private static void spawnMercenary(int npcId, int x, int y, int z, int despawnDelay, String[] messages, int chatDelay)
	{
		L2NpcTemplate template = NpcData.getInstance().getTemplate(npcId);
		if (template != null)
		{
			final L2SiegeGuardInstance npc = new L2SiegeGuardInstance(IdFactory.getInstance().getNextId(), template);
			npc.setCurrentHpMp(npc.getMaxHp(), npc.getMaxMp());
			npc.setDecayed(false);
			npc.spawnMe(x, y, (z + 20));
			
			if (messages != null && messages.length > 0)
				AutoChatHandler.getInstance().registerChat(npc, messages, chatDelay);
			
			if (despawnDelay > 0)
			{
				ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
				{
					@Override
					public void run()
					{
						npc.deleteMe();
					}
				}, despawnDelay);
			}
		}
	}
	
	public void deleteTickets(int castleId)
	{
		int i = 0;
		while (i < getDroppedTickets().size())
		{
			L2ItemInstance item = getDroppedTickets().get(i);
			if ((item != null) && (getTicketCastleId(item.getItemId()) == castleId))
			{
				item.decayMe();
				L2World.getInstance().removeObject(item);
				
				// remove from the list
				getDroppedTickets().remove(i);
			}
			else
				i++;
		}
	}
	
	public void removeTicket(L2ItemInstance item)
	{
		int itemId = item.getItemId();
		int npcId = -1;
		
		// find the FIRST ticket itemId with spawns the saved NPC in the saved location
		for (int i = 0; i < ITEM_IDS.length; i++)
			if (ITEM_IDS[i] == itemId) // Find the index of the item used
			{
				npcId = NPC_IDS[i];
				break;
			}
		// find the castle where this item is
		Castle castle = CastleManager.getInstance().getCastleById(getTicketCastleId(itemId));
		
		if (npcId > 0 && castle != null)
			SiegeGuardManager.getInstance().removeMerc(npcId, item.getX(), item.getY(), item.getZ());
		
		getDroppedTickets().remove(item);
	}
	
	public int[] getItemIds()
	{
		return ITEM_IDS;
	}
	
	public final List<L2ItemInstance> getDroppedTickets()
	{
		if (_droppedTickets == null)
			_droppedTickets = new ArrayList<>();
		return _droppedTickets;
	}
}