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
package com.l2jhellas.gameserver.geodata.geoeditorcon;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2jhellas.Config;

public class GeoEditorListener extends Thread
{
	protected static final Logger _log = Logger.getLogger(GeoEditorListener.class.getName());

	private static final int PORT = Config.GEOEDITOR_PORT;

	private static final class SingletonHolder
	{
		protected static final GeoEditorListener INSTANCE = new GeoEditorListener();
	}

	public static GeoEditorListener getInstance()
	{
		return SingletonHolder.INSTANCE;
	}

	private ServerSocket _serverSocket;
	private GeoEditorThread _geoEditor;

	protected GeoEditorListener()
	{
		try
		{
			_serverSocket = new ServerSocket(PORT);
		}
		catch (IOException e)
		{
			if (Config.DEBUG)
				e.printStackTrace();

			_log.log(Level.SEVERE, "Error creating geoeditor listener! ", e);
			System.exit(1);
		}
		start();
		_log.log(Level.INFO, getClass().getSimpleName() + ": Initialized.");
	}

	public GeoEditorThread getThread()
	{
		return _geoEditor;
	}

	public String getStatus()
	{
		if (_geoEditor != null && _geoEditor.isWorking())
		{
			return "Geoeditor connected.";
		}
		return "Geoeditor not connected.";
	}

	@Override
	public void run()
	{
		Socket connection = null;
		try
		{
			while (true)
			{
				connection = _serverSocket.accept();
				if (_geoEditor != null && _geoEditor.isWorking())
				{
					_log.log(Level.WARNING, "Geoeditor already connected!");
					connection.close();
					continue;
				}
				_log.log(Level.INFO, getClass().getSimpleName() + ": Received geoeditor connection from: " + connection.getInetAddress().getHostAddress());
				_geoEditor = new GeoEditorThread(connection);
				_geoEditor.start();
			}
		}
		catch (Exception e)
		{
			if (Config.DEVELOPER)
			{
				e.printStackTrace();
			}
			_log.log(Level.WARNING, "GeoEditorListener: ", e);
			try
			{
				if (connection != null)
					connection.close();
			}
			catch (Exception e2)
			{
				if (Config.DEVELOPER)
				{
					e.printStackTrace();
				}
			}
		}
		finally
		{
			try
			{
				_serverSocket.close();
			}
			catch (IOException io)
			{
				if (Config.DEVELOPER)
				{
					io.printStackTrace();
				}
				_log.log(Level.WARNING, "", io);
			}
			_log.log(Level.WARNING, "GeoEditorListener Closed!");
		}
	}
}