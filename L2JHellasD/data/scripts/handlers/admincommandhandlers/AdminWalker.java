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
package handlers.admincommandhandlers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.handler.IAdminCommandHandler;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jhellas.util.database.L2DatabaseFactory;

/**
 * @author Blaze,Nightwolf
 */
public class AdminWalker implements IAdminCommandHandler
{
	private static Logger _log = Logger.getLogger(AdminWalker.class.getName());

	// walker
	private static int _npcid = 0;
	private static int _point = 1;
	private static String _text = "";
	private static int _mode = 1;
	private static int _routeid = 0;
	private static int _delay = 150;

	// Insert new npc
	//private static final String CUSTOM_NPC = "INSERT INTO custom_npc (id,idTemplate,name,serverSideName,title,serverSideTitle,class,collision_radius,collision_height,level,sex,type,attackrange,hp,mp,hpreg,mpreg,str,con,dex,int,wit,men,exp,sp,patk,pdef,matk,mdef,atkspd,aggro,matkspd,rhand,lhand,armor,walkspd,runspd,faction_id,faction_range,isUndead,absorb_level,absorb_type) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String INSERT = "INSERT INTO custom_npc (id,idTemplate,name,serverSideName,title,serverSideTitle,class,collision_radius,collision_height,level,sex,type,attackrange,hp,mp,hpreg,mpreg,str,con,dex,wit,men,exp,sp,patk,pdef,matk,mdef,atkspd,aggro,matkspd,rhand,lhand,armor,walkspd,runspd,faction_id,faction_range,isUndead,absorb_level,absorb_type) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	// Select npc gia test na sigrinw gia to koumpi save
	//private static final String SELECT = "SELECT id FROM custom_npc WHERE id=" + _npcid + "";

	private static final String[] ADMIN_COMMANDS =
	{/** @formatter:off */
		"admin_walker_setmessage",
		"admin_walker_menu",
		"admin_walker_setnpc",
		"admin_walker_setpoint",
		"admin_walker_setmode",
		"admin_walker_addpoint",
		"admin_walker_createnpc"
	};/** @formatter:on */

	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		/*
		 * if (!AdminCommandAccessRights.getInstance().hasAccess(command, activeChar.getAccessLevel()))
		 * return false;
		 *
		 * if (Config.GMAUDIT)
		 * {
		 * Logger _logAudit = Logger.getLogger("gmaudit");
		 * LogRecord record = new LogRecord(Level.INFO, command);
		 * record.setParameters(new Object[]
		 * {
		 * "GM: " + activeChar.getName(), " to target [" + activeChar.getTarget() + "] "
		 * });
		 * _logAudit.log(record);
		 * }
		 */
		try
		{
			String[] parts = command.split(" ");

			if (command.startsWith("admin_walker_menu"))
			{
				mainMenu(activeChar);
			}
			else if (command.startsWith("admin_walker_createnpc"))
			{
				save(activeChar, command);
			}
			else if (command.startsWith("admin_walker_setnpc "))
			{
				try
				{
					_npcid = Integer.parseInt(parts[1]);

					try (Connection con = L2DatabaseFactory.getInstance().getConnection())
					{
						PreparedStatement statement = con.prepareStatement("SELECT route_id FROM walker_routes WHERE npc_id=" + _npcid + "");
						ResultSet rset = statement.executeQuery();

						if (rset.next())
						{
							activeChar.sendMessage("Such NPC already exist, we add routes..");
							_routeid = rset.getInt("route_id");
						}
						else
						{
							statement = con.prepareStatement("SELECT MAX(`route_id`) AS max FROM walker_routes");
							ResultSet rset1 = statement.executeQuery();

							if (rset1.next())
							{
								_routeid = rset1.getInt("max") + 1;
							}

							rset1.close();
							rset1 = null;
						}

						statement.close();
						statement = null;
					}
					catch (Exception e)
					{
						_log.log(Level.WARNING, getClass().getName() + ": could not select walker_routes from database." + e.getMessage());
						if (Config.DEVELOPER)
						{
							e.printStackTrace();
						}
					}

					_point = 1;
				}
				catch (NumberFormatException e)
				{
					if (Config.DEVELOPER)
					{
						e.printStackTrace();
					}

					activeChar.sendMessage("Incorrect identifier.");
				}

				mainMenu(activeChar);
			}
			else if (command.startsWith("admin_walker_addpoint"))
			{
				addMenu(activeChar);
				activeChar.sendMessage("New NPC WALKER created please create in data/html/default/" + _npcid + ".htm and add your text.");
			}
			else if (command.startsWith("admin_walker_setmode"))
			{
				if (_mode == 1)
				{
					_mode = 0;
				}
				else
				{
					_mode = 1;
				}

				addMenu(activeChar);
			}
			else if (command.startsWith("admin_walker_setmessage"))
			{
				_text = command.substring(24);
				addMenu(activeChar);
			}
			else if (command.startsWith("admin_walker_setpoint"))
			{
				setPoint(activeChar.getX(), activeChar.getY(), activeChar.getZ());
				_point++;
				addMenu(activeChar);
			}

			parts = null;
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": error1." + e.getMessage());
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}

	private void save(L2PcInstance activeChar, String command)
	{
		// Create new custom npc if not exists
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement;
			statement = con.prepareStatement(INSERT);
			statement.setInt(1, _npcid); // id
			statement.setInt(2, 31309); // idTemplate
			statement.setString(3, "NPC"); // name
			statement.setInt(4, 1); // serverSideName
			statement.setString(5, "NEW WALKER"); // title
			statement.setInt(6, 1); // serverSideTitle
			statement.setString(7, "NPC.a_traderD_Mhuman"); // class
			statement.setInt(8, 8); // collision_radius
			statement.setInt(9, 25); // collision_height
			statement.setInt(10, 80); // level
			statement.setString(11, "male"); // sex
			statement.setString(12, "L2Npc"); // type
			statement.setInt(13, 40); // attackrange
			statement.setInt(14, 2444); // hp
			statement.setInt(15, 2444); // mp
			statement.setInt(16, 0); // hpreg
			statement.setInt(17, 0); // mpreg
			statement.setInt(18, 10); // str
			statement.setInt(19, 10); // con
			statement.setInt(20, 10); // dex
			/* statement.setInt(21, 10); // int */
			statement.setInt(21, 10); // wit
			statement.setInt(22, 10); // men
			statement.setInt(23, 0); // exp
			statement.setInt(24, 0); // sp
			statement.setInt(25, 500); // patk
			statement.setInt(26, 500); // pdef
			statement.setInt(27, 500); // matk
			statement.setInt(28, 500); // mdef
			statement.setInt(29, 258); // atkspd
			statement.setInt(30, 0); // aggro
			statement.setInt(31, 333); // matkspd
			statement.setInt(32, 9376); // rhand
			statement.setInt(33, 0); // lhand
			statement.setInt(34, 0); // armor
			statement.setInt(35, 30); // walkspd
			statement.setInt(36, 120); // runspd
			statement.setString(37, ""); // faction_id
			statement.setInt(38, 0); // faction_range
			statement.setInt(39, 0); // isUndead
			statement.setInt(40, 0); // absorb_level
			statement.setString(41, "LAST_HIT"); // absorb_type
			statement.execute();
			statement.close();

			StringTokenizer st = new StringTokenizer(command);
			st.nextToken();
			String message = "";
			try
			{
				while (st.hasMoreTokens())
				{
					message = message + st.nextToken() + " ";
				}

				String fname = "data/html/default/" + _npcid + ".htm";
				File file = new File(fname);
				boolean exist = file.createNewFile();
				if (!exist)
					return;

				FileWriter fstream = new FileWriter(fname);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write("<html><body>\r\nL2jhellas Walker<br>\r\nchange me in data/html/default/" + _npcid + ".htm\r\n</body></html>");

				_log.log(Level.INFO, getClass().getSimpleName() + ": Created data/html/default/" + _npcid + ".htm for Walker NPC.");
				out.close();
			}
			catch (Exception e)
			{
				_log.log(Level.WARNING, getClass().getName() + ": could not create data/html/default/" + _npcid + ".htm for Walker NPC.");
			}

			mainMenu(activeChar);
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": could not create new custom_npc with id:" + _npcid + e.getMessage());
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	private void setPoint(int x, int y, int z)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement;

			if (_text.isEmpty())
			{
				statement = con.prepareStatement("INSERT INTO walker_routes (route_id,npc_id,move_point,chatText,move_x,move_y,move_z,delay,running) VALUES (?,?,?,NULL,?,?,?,?,?)");
				statement.setInt(1, _routeid);
				statement.setInt(2, _npcid);
				statement.setInt(3, _point);
				statement.setInt(4, x);
				statement.setInt(5, y);
				statement.setInt(6, z);
				statement.setInt(7, _delay);
				statement.setInt(8, _mode);
			}
			else
			{
				statement = con.prepareStatement("INSERT INTO walker_routes (route_id,npc_id,move_point,chatText,move_x,move_y,move_z,delay,running) VALUES (?,?,?,?,?,?,?,?,?)");
				statement.setInt(1, _routeid);
				statement.setInt(2, _npcid);
				statement.setInt(3, _point);
				statement.setString(4, _text);
				statement.setInt(5, x);
				statement.setInt(6, y);
				statement.setInt(7, z);
				statement.setInt(8, _delay);
				statement.setInt(9, _mode);
			}

			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": could not insert walker_routes into database." + e.getMessage());
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param activeChar
	 */
	private void mainMenu(L2PcInstance activeChar)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(activeChar.getObjectId());
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head><title>Walker Routes</title></head><body><center>");
		sb.append("<table width=260>");
		sb.append("<tr>");
		sb.append("<td><button value=\"Main\" action=\"bypass -h admin_admin\" width=50 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td>");
		sb.append("<td><button value=\"Game\" action=\"bypass -h admin_admin2\" width=50 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td>");
		sb.append("<td><button value=\"Effects\" action=\"bypass -h admin_admin3\" width=50 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td>");
		sb.append("<td><button value=\"Server\" action=\"bypass -h admin_admin4\" width=50 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td>");
		sb.append("<td><button value=\"Mods\" action=\"bypass -h admin_admin5\" width=50 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td>");
		sb.append("</tr>");
		sb.append("</table><br1>");
		sb.append("<font color=\"009900\">Editing Walkers</font><br>");
		if (_npcid > 0)
		{
			sb.append("<table width=256><tr><td width=110>Chosen NPC ID: <font color=FF0000>" + _npcid + "</font></td><td width=110><a action=\"bypass -h admin_walker_createnpc\">Create NPC</a> with ID:<font color=FF0000>" + _npcid + "</font>.</td></tr>");
		}
		else
		{
			sb.append("<table width=256><tr><td>Chosen NPC ID: <font color=FF0000>" + _npcid + "</font></td></tr>");
		}
		sb.append("<tr><td>Current point: " + _point + "</td></tr>");
		sb.append("<tr><td><edit var=\"id\" width=80 height=15></td></tr>");
		sb.append("<tr><td><button value=\"New NPC\" action=\"bypass -h admin_walker_setnpc $id\" width=80 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td></tr>");
		sb.append("<tr><td><button value=\"Add a point\" action=\"bypass -h admin_walker_addpoint\" width=80 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td></tr>");
		sb.append("</table>");
		sb.append("</center>");
		sb.append("NOTES:<br1>");
		sb.append("*To add a new walker NPC type a number in the box and press \"New NPC\".<br1>");
		sb.append("Then you will see that Chosen NPC ID is your new NPC walker now press add a point.<br1>");
		sb.append("You can create a new custom_npc by clicking \"Create\".<br1>");
		sb.append("*To select an existing Walker NPC press the number in the box and press \"Add a point\".");
		sb.append("*The X Y Z is automaticly given by where you stand in each point you add.<br1>");
		sb.append("<br><br>Server Software: Created by L2J-L2JHellas Team.");
		sb.append("</body></html>");
		html.setHtml(sb.toString());
		activeChar.sendPacket(html);
	}

	/**
	 * @param activeChar
	 */
	private void addMenu(L2PcInstance activeChar)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(activeChar.getObjectId());
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head><title>Walker Routes</title></head><body><center>");
		sb.append("<table width=260>");
		sb.append("<tr>");
		sb.append("<td><button value=\"Main\" action=\"bypass -h admin_admin\" width=50 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td>");
		sb.append("<td><button value=\"Game\" action=\"bypass -h admin_admin2\" width=50 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td>");
		sb.append("<td><button value=\"Effects\" action=\"bypass -h admin_admin3\" width=50 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td>");
		sb.append("<td><button value=\"Server\" action=\"bypass -h admin_admin4\" width=50 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td>");
		sb.append("<td><button value=\"Mods\" action=\"bypass -h admin_admin5\" width=50 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td>");
		sb.append("</tr>");
		sb.append("</table><br1>");
		sb.append("<font color=\"009900\">Editing Walkers</font><br>");
		sb.append("<br><table width=256>");
		sb.append("<tr><td>Chosen NPC ID: <font color=FF0000>" + _npcid + "</font></td></tr>");
		sb.append("<tr><td>Number of the current point: " + _point + "</td></tr>");
		sb.append("<tr><td>Number of the current route: " + _routeid + "</td></tr>");

		if (_mode == 1)
		{
			sb.append("<tr><td>Mode: Run</td></tr>");
		}
		else
		{
			sb.append("<tr><td>Mode: Step</td></tr>");
		}

		if (_text.isEmpty())
		{
			sb.append("<tr><td>This NPC has no text given.</td></tr>");
		}
		else
		{
			sb.append("<tr><td>This NPC text is: " + _text + "</td></tr>");
		}

		sb.append("<tr><td><edit var=\"id\" width=80 height=15></td></tr>");
		sb.append("<tr><td><button value=\"Add Text\" action=\"bypass -h admin_walker_setmessage $id\" width=80 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td></tr>");
		sb.append("<tr><td><button value=\"Add Point\" action=\"bypass -h admin_walker_setpoint\" width=80 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td></tr>");
		sb.append("<tr><td><button value=\"Walk/Run\" action=\"bypass -h admin_walker_setmode\" width=80 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td></tr>");
		sb.append("<tr><td><button value=\"back\" action=\"bypass -h admin_walker_menu\" width=80 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td></tr>");
		sb.append("</table>");
		sb.append("</center>");
		sb.append("NOTES:<br1>");
		sb.append("*Add Text: The text the Walker NPC will say.<br1>");
		sb.append("*Add Point: The 1,2,3,4 moving routes locations that npc will move,also saves the route in db.<br1>");
		sb.append("*Walk/Run: The mode of walker.<br1>");
		sb.append("<br>Server Software: Created by L2J-L2JHellas Team.");
		sb.append("</body></html>");
		html.setHtml(sb.toString());
		activeChar.sendPacket(html);
	}
}