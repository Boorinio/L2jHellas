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
package com.l2jhellas;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import com.l2jhellas.gameserver.LoginServerThread;
import com.l2jhellas.gameserver.Shutdown;
import com.l2jhellas.gameserver.communitybbs.Manager.RegionBBSManager;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public class RemoteConnector
{
	String rconPassword = ExternalConfig.RCON_PASSWORD;
	String adminUsername = "";
	MiniServer mini;

	public RemoteConnector()
	{
		try
		{
			@SuppressWarnings("resource")
			final ServerSocket serverSocket = new ServerSocket(ExternalConfig.RCON_PORT);

			new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					while (true)
					{
						Socket clientSocket = null;

						try
						{
							clientSocket = serverSocket.accept();
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
						mini = new MiniServer(clientSocket);
						mini.start();
					}
				}
			}).start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void getInstance()
	{
		new RemoteConnector();
		InetAddress thisIp = null;

		try
		{
			thisIp = InetAddress.getLocalHost();
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		System.out.println("[REMOTE] IP: " + thisIp.getHostAddress().toString() + " Port: " + "7779");

	}

	public class MiniServer extends Thread
	{
		Socket socket;
		ObjectOutputStream output;
		ObjectInputStream input;

		public MiniServer(Socket clientSocket)
		{
			this.socket = clientSocket;
		}

		@Override
		public void run()
		{
			try
			{
				output = new ObjectOutputStream(socket.getOutputStream());
				output.flush();
				input = new ObjectInputStream(socket.getInputStream());

				try
				{
					output.writeObject("");
					String _command = (String) input.readObject();
					StringTokenizer str = new StringTokenizer(_command);

					if (_command.startsWith(rconPassword))
					{
						str.nextToken();
						adminUsername = str.nextToken();
						System.out.println("[REMOTE] Connected user [" + adminUsername + "] called: " + _command);

						// Register Commands Here
						if (str.nextToken().equalsIgnoreCase("test"))
						{
							System.out.println("Test Command.");
						}
						else if (str.nextToken().equalsIgnoreCase("ban"))
						{
							if (str.hasMoreTokens())
							{
								try
								{
									L2PcInstance player = L2World.getPlayer(str.nextToken());

									player.setAccountAccesslevel(-100);
									RegionBBSManager.getInstance().changeCommunityBoard();
									player.logout();
									player.sendMessage("You have been banned.");
								}
								catch (Exception e)
								{
									e.printStackTrace();
								}
							}
						}
						else if (str.nextToken().equalsIgnoreCase("unban"))
						{
							try
							{
								LoginServerThread.getInstance().sendAccessLevel(str.nextToken(), 0);
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}
						else if (str.nextToken().equalsIgnoreCase("shutdown"))
						{
							try
							{
								Shutdown.getInstance().startShutdown(L2World.getPlayer(adminUsername), 120, false);
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}
						else if (str.nextToken().equalsIgnoreCase("restart"))
						{
							try
							{
								Shutdown.getInstance().startShutdown(L2World.getPlayer(adminUsername), 120, true);
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}
					}
				}
				catch (Exception e)
				{
					System.out.println("[REMOTE] User Disconnected.");
					closeCrap();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		public void closeCrap()
		{
			try
			{
				output.close();
				input.close();
				socket.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}