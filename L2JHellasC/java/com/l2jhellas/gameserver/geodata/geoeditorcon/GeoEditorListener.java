package com.l2jhellas.gameserver.geodata.geoeditorcon;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeoEditorListener extends Thread
{
	private static GeoEditorListener _instance;
	private static final int PORT = 9011;
	private static Logger _log = Logger.getLogger(GeoEditorListener.class.getName());
	private final ServerSocket _serverSocket;
	private static GeoEditorThread _geoEditor;
	
	public static GeoEditorListener getInstance()
	{
		if (_instance == null)
			try
			{
				_instance = new GeoEditorListener();
				_instance.start();
				_log.info("GeoEditorListener Initialized.");
			}
			catch (IOException e)
			{
				_log.severe("Error creating geoeditor listener! " + e.getMessage());
				System.exit(1);
			}
		return _instance;
	}
	
	private GeoEditorListener() throws IOException
	{
		_serverSocket = new ServerSocket(PORT);
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
					_log.warning("Geoeditor already connected!");
					connection.close();
					continue;
				}
				_log.info("Received geoeditor connection from: " + connection.getInetAddress().getHostAddress());
				_geoEditor = new GeoEditorThread(connection);
				_geoEditor.start();
			}
		}
		catch (Exception e)
		{
			_log.info("GeoEditorListener: " + e.getMessage());
			try
			{
				if (connection != null)
					connection.close();
			}
			catch (Exception e2)
			{
			}
		}
		finally
		{
			try
			{
				if (connection != null)
					connection.close();
				
				_serverSocket.close();
			}
			catch (IOException io)
			{
				_log.log(Level.INFO, "", io);
			}
			_log.warning("GeoEditorListener Closed!");
		}
	}
}
