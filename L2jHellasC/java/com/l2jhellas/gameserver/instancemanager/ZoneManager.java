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
package com.l2jhellas.gameserver.instancemanager;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;

import javolution.util.FastList;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.model.L2Character;
import com.l2jhellas.gameserver.model.L2Object;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.L2WorldRegion;
import com.l2jhellas.gameserver.model.zone.L2ZoneType;
import com.l2jhellas.gameserver.model.zone.form.ZoneCuboid;
import com.l2jhellas.gameserver.model.zone.form.ZoneCylinder;
import com.l2jhellas.gameserver.model.zone.form.ZoneNPoly;
import com.l2jhellas.gameserver.model.zone.type.L2ArenaZone;
import com.l2jhellas.gameserver.model.zone.type.L2BigheadZone;
import com.l2jhellas.gameserver.model.zone.type.L2BossZone;
import com.l2jhellas.gameserver.model.zone.type.L2CastleZone;
import com.l2jhellas.gameserver.model.zone.type.L2ClanHallZone;
import com.l2jhellas.gameserver.model.zone.type.L2DamageZone;
import com.l2jhellas.gameserver.model.zone.type.L2DerbyTrackZone;
import com.l2jhellas.gameserver.model.zone.type.L2FishingZone;
import com.l2jhellas.gameserver.model.zone.type.L2JailZone;
import com.l2jhellas.gameserver.model.zone.type.L2MotherTreeZone;
import com.l2jhellas.gameserver.model.zone.type.L2NoLandingZone;
import com.l2jhellas.gameserver.model.zone.type.L2OlympiadStadiumZone;
import com.l2jhellas.gameserver.model.zone.type.L2PeaceZone;
import com.l2jhellas.gameserver.model.zone.type.L2TownZone;
import com.l2jhellas.util.database.L2DatabaseFactory;

/**
 * This class manages the augmentation data and can also create new
 * augmentations.
 * 
 * @author durgus
 */
public class ZoneManager
{
	private static final Logger _log = Logger.getLogger(ZoneManager.class.getName());

	private final FastList<L2ZoneType> _zones = new FastList<L2ZoneType>();

	public static final ZoneManager getInstance()
	{
		return SingletonHolder._instance;
	}

	private ZoneManager()
	{
		load();
	}

	public void reload()
	{
		// int zoneCount = 0;

		// Get the world regions
		int count = 0;
		L2WorldRegion[][] worldRegions = L2World.getAllWorldRegions();
		for (int x = 0; x < worldRegions.length; x++)
		{
			for (int y = 0; y < worldRegions[x].length; y++)
			{
				worldRegions[x][y].getZones().clear();
				count++;
			}
		}
		GrandBossManager.getInstance().getZones().clear();
		_log.log(Level.INFO, getClass().getSimpleName() + ": Removed zones in " + count + " regions.");
		// Load the zones
		load();
	}

	private final void load()
	{
		int zoneCount = 0;
		_zones.clear();

		// Get the world regions
		L2WorldRegion[][] worldRegions = L2World.getAllWorldRegions();

		// Load the zone xml
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setIgnoringComments(true);

			File file = new File(Config.DATAPACK_ROOT + "/data/xml/zone.xml");
			if (!file.exists())
			{
				_log.log(Level.WARNING, getClass().getName() + ": The zone.xml file is missing.");
				return;
			}

			Document doc = factory.newDocumentBuilder().parse(file);

			for (Node n = doc.getFirstChild(); n != null; n = n.getNextSibling())
			{
				if ("list".equalsIgnoreCase(n.getNodeName()))
				{
					for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
					{
						if ("zone".equalsIgnoreCase(d.getNodeName()))
						{
							NamedNodeMap attrs = d.getAttributes();
							int zoneId = Integer.parseInt(attrs.getNamedItem("id").getNodeValue());
							int minZ = Integer.parseInt(attrs.getNamedItem("minZ").getNodeValue());
							int maxZ = Integer.parseInt(attrs.getNamedItem("maxZ").getNodeValue());
							String zoneType = attrs.getNamedItem("type").getNodeValue();
							String zoneShape = attrs.getNamedItem("shape").getNodeValue();

							// Create the zone
							L2ZoneType temp = null;

							if (zoneType.equals("FishingZone"))
								temp = new L2FishingZone(zoneId);
							else if (zoneType.equals("ClanHallZone"))
								temp = new L2ClanHallZone(zoneId);
							else if (zoneType.equals("PeaceZone"))
								temp = new L2PeaceZone(zoneId);
							else if (zoneType.equals("Town"))
								temp = new L2TownZone(zoneId);
							else if (zoneType.equals("OlympiadStadium"))
								temp = new L2OlympiadStadiumZone(zoneId);
							else if (zoneType.equals("CastleZone"))
								temp = new L2CastleZone(zoneId);
							else if (zoneType.equals("DamageZone"))
								temp = new L2DamageZone(zoneId);
							else if (zoneType.equals("Arena"))
								temp = new L2ArenaZone(zoneId);
							else if (zoneType.equals("MotherTree"))
								temp = new L2MotherTreeZone(zoneId);
							else if (zoneType.equals("BigheadZone"))
								temp = new L2BigheadZone(zoneId);
							else if (zoneType.equals("NoLandingZone"))
								temp = new L2NoLandingZone(zoneId);
							else if (zoneType.equals("JailZone"))
								temp = new L2JailZone(zoneId);
							else if (zoneType.equals("DerbyTrackZone"))
								temp = new L2DerbyTrackZone(zoneId);
							else if (zoneType.equals("BossZone"))
								temp = new L2BossZone(zoneId);
							else if (zoneType.equals("WaterZone"))

								// Check for unknown type
								if (temp == null)
								{
									_log.log(Level.WARNING, getClass().getName() + ": No such zone type: " + zoneType);
									continue;
								}

							// Get the zone shape from sql
							try
							{
								PreparedStatement statement = null;

								// Set the correct query
								statement = con.prepareStatement("SELECT x,y FROM zone_vertices WHERE id=? ORDER BY 'order' ASC ");

								statement.setInt(1, zoneId);
								ResultSet rset = statement.executeQuery();

								// Create this zone. Parsing for cuboids is a
								// bit different than for other polygons
								// cuboids need exactly 2 points to be defined.
								// Other polygons need at least 3 (one per
								// vertex)
								if (zoneShape.equalsIgnoreCase("Cuboid"))
								{
									int[] x =
									{
									0, 0
									};
									int[] y =
									{
									0, 0
									};
									boolean successfulLoad = true;

									for (int i = 0; i < 2; i++)
									{
										if (rset.next())
										{
											x[i] = rset.getInt("x");
											y[i] = rset.getInt("y");
										}
										else
										{
											_log.log(Level.WARNING, getClass().getName() + ": Missing cuboid vertex in sql data for zone: " + zoneId);
											rset.close();
											statement.close();
											successfulLoad = false;
											break;
										}
									}

									if (successfulLoad)
										temp.setZone(new ZoneCuboid(x[0], x[1], y[0], y[1], minZ, maxZ));
									else
										continue;
								}
								else if (zoneShape.equalsIgnoreCase("NPoly"))
								{
									FastList<Integer> fl_x = new FastList<Integer>(), fl_y = new FastList<Integer>();

									// Load the rest
									while (rset.next())
									{
										fl_x.add(rset.getInt("x"));
										fl_y.add(rset.getInt("y"));
									}

									// An nPoly needs to have at least 3
									// vertices
									if ((fl_x.size() == fl_y.size()) && (fl_x.size() > 2))
									{
										// Create arrays
										int[] aX = new int[fl_x.size()];
										int[] aY = new int[fl_y.size()];

										// This runs only at server startup so
										// dont complain :>
										for (int i = 0; i < fl_x.size(); i++)
										{
											aX[i] = fl_x.get(i);
											aY[i] = fl_y.get(i);
										}

										// Create the zone
										temp.setZone(new ZoneNPoly(aX, aY, minZ, maxZ));
									}
									else
									{
										_log.log(Level.WARNING, getClass().getName() + ": Bad sql data for zone: " + zoneId);
										rset.close();
										statement.close();
										continue;
									}
								}
								else if (zoneShape.equalsIgnoreCase("Cylinder"))
								{
									// A Cylinder zone requires a centre point
									// at x,y and a radius
									int zoneRad = Integer.parseInt(attrs.getNamedItem("rad").getNodeValue());
									if (rset.next() && zoneRad > 0)
									{
										int zoneX = rset.getInt("x");
										int zoneY = rset.getInt("y");

										// create the zone
										temp.setZone(new ZoneCylinder(zoneX, zoneY, minZ, maxZ, zoneRad));
									}
									else
									{
										_log.log(Level.WARNING, getClass().getName() + ": Bad sql data for zone: " + zoneId);
										rset.close();
										statement.close();
										continue;
									}
								}
								else
								{
									_log.log(Level.WARNING, getClass().getName() + ": Unknown shape: " + zoneShape);
									rset.close();
									statement.close();
									continue;
								}

								rset.close();
								statement.close();
							}
							catch (Exception e)
							{
								_log.log(Level.WARNING, getClass().getName() + ": Failed to load zone coordinates: " + e);
								if (Config.DEVELOPER)
								{
									e.printStackTrace();
								}
							}

							// Check for aditional parameters
							for (Node cd = d.getFirstChild(); cd != null; cd = cd.getNextSibling())
							{
								if ("stat".equalsIgnoreCase(cd.getNodeName()))
								{
									attrs = cd.getAttributes();
									String name = attrs.getNamedItem("name").getNodeValue();
									String val = attrs.getNamedItem("val").getNodeValue();

									temp.setParameter(name, val);
								}
							}
							addZone(temp);

							// Register the zone into any world region it
							// intersects with...
							// currently 11136 test for each zone :>
							int ax, ay, bx, by;
							for (int x = 0; x < worldRegions.length; x++)
							{
								for (int y = 0; y < worldRegions[x].length; y++)
								{
									ax = (x - L2World.OFFSET_X) << L2World.SHIFT_BY;
									bx = ((x + 1) - L2World.OFFSET_X) << L2World.SHIFT_BY;
									ay = (y - L2World.OFFSET_Y) << L2World.SHIFT_BY;
									by = ((y + 1) - L2World.OFFSET_Y) << L2World.SHIFT_BY;

									if (temp.getZone().intersectsRectangle(ax, bx, ay, by))
									{
										if (Config.DEBUG)
											_log.log(Level.CONFIG, getClass().getName() + ": Zone (" + zoneId + ") added to: " + x + " " + y);

										worldRegions[x][y].addZone(temp);
									}
								}
							}

							// Special managers for granbosses...
							if (temp instanceof L2BossZone)
								GrandBossManager.getInstance().addZone((L2BossZone) temp);

							// Increase the counter
							zoneCount++;
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			_log.log(Level.SEVERE, getClass().getName() + ": Error while loading zones." + e);
			return;
		}

		_log.log(Level.FINE, getClass().getSimpleName() + ": loaded " + zoneCount + " zones.");
	}

	/**
	 * Add new zone
	 * 
	 * @param zone
	 */
	public void addZone(L2ZoneType zone)
	{
		_zones.add(zone);
	}

	/**
	 * Returns all zones registered with the ZoneManager.
	 * To minimise iteration processing retrieve zones from L2WorldRegion for a specific location instead.
	 * 
	 * @return zones
	 */
	public FastList<L2ZoneType> getAllZones()
	{
		return _zones;
	}

	/**
	 * Returns all zones from where the object is located
	 * 
	 * @param object
	 * @return zones
	 */
	public FastList<L2ZoneType> getZones(L2Object object)
	{
		return getZones(object.getX(), object.getY(), object.getZ());
	}

	/**
	 * Returns all zones from given coordinates (plane)
	 * 
	 * @param x
	 * @param y
	 * @return zones
	 */
	public FastList<L2ZoneType> getZones(int x, int y)
	{
		L2WorldRegion region = L2World.getRegion(x, y);
		FastList<L2ZoneType> temp = new FastList<L2ZoneType>();
		for (L2ZoneType zone : region.getZones())
		{
			if (zone.isInsideZone(x, y))
				temp.add(zone);
		}
		return temp;
	}

	/**
	 * Returns all zones from given coordinates
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return zones
	 */
	public FastList<L2ZoneType> getZones(int x, int y, int z)
	{
		L2WorldRegion region = L2World.getRegion(x, y);
		FastList<L2ZoneType> temp = new FastList<L2ZoneType>();
		for (L2ZoneType zone : region.getZones())
		{
			if (zone.isInsideZone(x, y, z))
				temp.add(zone);
		}
		return temp;
	}

	public final L2ArenaZone getArena(L2Character character)
	{
		if (character == null)
			return null;

		for (L2ZoneType temp : ZoneManager.getInstance().getZones(character.getX(), character.getY(), character.getZ()))
		{
			if (temp instanceof L2ArenaZone && temp.isCharacterInZone(character))
				return ((L2ArenaZone) temp);
		}

		return null;
	}

	public final L2OlympiadStadiumZone getOlympiadStadium(L2Character character)
	{
		if (character == null)
			return null;

		for (L2ZoneType temp : ZoneManager.getInstance().getZones(character.getX(), character.getY(), character.getZ()))
		{
			if (temp instanceof L2OlympiadStadiumZone && temp.isCharacterInZone(character))
				return ((L2OlympiadStadiumZone) temp);
		}
		return null;
	}

	@SuppressWarnings("synthetic-access")
	private static class SingletonHolder
	{
		protected static final ZoneManager _instance = new ZoneManager();
	}
}