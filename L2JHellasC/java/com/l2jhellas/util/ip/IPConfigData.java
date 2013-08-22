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
package com.l2jhellas.util.ip;

import info.tak11.subnet.Subnet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet6Address;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IPConfigData
{
	protected static final Logger _log = Logger.getLogger(IPConfigData.class.getName());
	
	private static final List<String> _subnets = new ArrayList<>(5);
	private static final List<String> _hosts = new ArrayList<>(5);
	
	private static String externalIp = "127.0.0.1";
	private static Subnet sub = new Subnet();

	public static void load()
	{
		Properties IPSettings = new Properties();
		final File server = new File("./config/Network/AutomaticIP.ini");
		boolean AUTO_IP;
		try (InputStream is = new FileInputStream(server))
		{
			IPSettings.load(is);
		}
		catch (Exception e)
		{
			_log.log(Level.SEVERE, "Error while " + server + " settings!", e);
		}
		AUTO_IP = Boolean.parseBoolean(IPSettings.getProperty("AutomaticIP", "True"));

		if (AUTO_IP == false)
		{
			System.out.println("Network Config: Manual Configuration.");
		}
		else
		// Auto configuration...
		{
			autoIpConfig();
		}
	}

	public static void autoIpConfig()
	{
		try
		{
			URL autoIp = new URL("http://api.externalip.net/ip/");
			try (BufferedReader in = new BufferedReader(new InputStreamReader(autoIp.openStream())))
			{
				externalIp = in.readLine();
			}
		}
		catch (IOException e)
		{
			System.err.println("Network Config: Failed to connect to api.externalip.net please check your internet connection using 127.0.0.1!");
			externalIp = "127.0.0.1";
		}

		try
		{
			Enumeration<NetworkInterface> niList = NetworkInterface.getNetworkInterfaces();

			while (niList.hasMoreElements())
			{
				NetworkInterface ni = niList.nextElement();

				if (!ni.isUp() || ni.isVirtual())
				{
					continue;
				}

				if (!ni.isLoopback() && ((ni.getHardwareAddress() == null) || (ni.getHardwareAddress().length != 6)))
				{
					continue;
				}

				for (InterfaceAddress ia : ni.getInterfaceAddresses())
				{
					if (ia.getAddress() instanceof Inet6Address)
					{
						continue;
					}

					sub.setIPAddress(ia.getAddress().getHostAddress());
					sub.setMaskedBits(ia.getNetworkPrefixLength());
					String subnet = sub.getSubnetAddress() + '/' + sub.getMaskedBits();
					if (!_subnets.contains(subnet) && !subnet.equals("0.0.0.0/0"))
					{
						_subnets.add(subnet);
						_hosts.add(sub.getIPAddress());
						System.out.println("Network Config: Adding new subnet: " + subnet + " address: " + sub.getIPAddress());
					}
				}
			}

			// External host and subnet
			_hosts.add(externalIp);
			_subnets.add("0.0.0.0/0");
			System.out.println("Network Config: Adding new subnet: 0.0.0.0/0 address: " + externalIp);

			// game server
			try
			{
				String fname = "../gameserver/config/Network/IPConfig/IPGameServer.ini";
				File file = new File(fname);
				file.createNewFile();

				FileWriter fstream = new FileWriter(fname);
				BufferedWriter out = new BufferedWriter(fstream);
				/** @formatter:off */
							out.write(
							"##########################################################################################\r\n"+
							"# This is the server configuration file. Here you can set up your server.                #\r\n"+
							"# * you can use the NO-IP system for dynamic DNS > http://www.no-ip.com/                 #\r\n"+
							"# * if you want to restore default settings delete this file. and run the server         #\r\n"+
							"##########################################################################################\r\n"+
							"\r\n"+
							"# Bind IP of the gameserver, use * to bind on all available IPs\r\n"+
							"GameserverHostname = *\r\n"+
							"GameserverPort = 7777\r\n"+
							"\r\n"+
							"# This is transmitted to the clients connecting from an external network, so it has to be a public IP or resolvable hostname\r\n"+
							"# If this IP is resolvable by Login just leave *\r\n"+
							"# Default: *\r\n"+
							"ExternalHostname = " + externalIp + "\r\n" +
							"\r\n"+
							"# This is transmitted to the client from the same network, so it has to be a local IP or resolvable hostname\r\n"+
							"# If this IP is resolvable by Login just leave *\r\n"+
							"# Default: *\r\n"+
							"InternalHostname = " + sub.getIPAddress() + "\r\n"+
							"\r\n"+
							"# The Loginserver host and port\r\n"+
							"LoginPort = 9014\r\n"+
							"LoginHost = " + sub.getIPAddress()+ "\r\n");
							/** @formatter:on */
				out.close();
			}
			catch (Exception e)
			{
				System.err.println("Network Config: could not create gameserver/config/Network/IPConfig/IPGameServer.ini");
			}
			// login server
			try
			{
				String fname = "../login/config/Network/IPConfig/IPLoginServer.ini";
				File file = new File(fname);
				file.createNewFile();

				FileWriter fstream = new FileWriter(fname);
				BufferedWriter out = new BufferedWriter(fstream);
				/** @formatter:off */
							out.write(
							"##########################################################################################\r\n"+
							"# Server configuration file. Here you can set up the connection for your server.         #\r\n"+
							"# = you can use the NO-IP system for dynamic DNS > http://www.no-ip.com/                 #\r\n"+
							"# * if you want to restore default settings delete this file. and run the server         #\r\n"+
							"##########################################################################################\r\n"+
							"# This is transmitted to the clients connecting from an external network,\r\n"+
							"# so it has to be a public IP or resolvable hostname\r\n"+
							"ExternalHostname = " + externalIp + "\r\n" +
							"\r\n"+
							"# This is transmitted to the client from the same network, so it has to be a local IP or resolvable hostname\r\n"+
							"InternalHostname = " + sub.getIPAddress() + "\r\n"+
							"\r\n"+
							"# Bind ip of the LoginServer, use * to bind on all available IPs\r\n"+
							"LoginserverHostname = *\r\n"+
							"LoginserverPort = 2106\r\n"+
							"\r\n"+
							"# The address on which login will listen for GameServers, use * to bind on all available IPs\r\n"+
							"LoginHostname = *\r\n"+
							"\r\n"+
							"# The port on which login will listen for GameServers\r\n"+
							"LoginPort = 9014");
							/** @formatter:on */
				out.close();
			}
			catch (Exception e)
			{
				System.err.println("Network Config: could not create gameserver/config/Network/IPConfig/IPGameServer.ini");
			}
		}
		catch (SocketException e)
		{
			System.err.println("Network Config: Configuration failed please configure manually using ipconfig.xml");
			e.printStackTrace();
			System.exit(0);
		}
	}

	protected List<String> getSubnets()
	{
		if (_subnets.isEmpty())
		{
			return Arrays.asList("0.0.0.0/0");
		}
		return _subnets;
	}

	protected List<String> getHosts()
	{
		if (_hosts.isEmpty())
		{
			return Arrays.asList("127.0.0.1");
		}
		return _hosts;
	}
}