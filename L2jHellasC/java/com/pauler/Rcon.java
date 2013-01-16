package com.pauler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.LoginServerThread;
import com.l2jhellas.gameserver.Shutdown;
import com.l2jhellas.gameserver.communitybbs.Manager.RegionBBSManager;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;

public class Rcon {
	
	String rconPassword = Config.RCON_PASSWORD;
	
	String adminUsername = "";
	
	MiniServer mini;
	
	public Rcon() {
		
		try {
			
			@SuppressWarnings("resource")
			final ServerSocket serverSocket = new ServerSocket(Config.RCON_PORT);
			
			new Thread(new Runnable() {

				@Override
				public void run() {

					while(true) {
						
						Socket clientSocket = null;
						
						try {
							
							clientSocket = serverSocket.accept();
							
						} catch (IOException e) {
							
							e.printStackTrace();
						}
						
						mini = new MiniServer(clientSocket);
						mini.start();
					
					}
					
				}
				
			}).start();
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public static void getInstance() {
		
		new Rcon();
		
		InetAddress thisIp = null;
		
		try {
			
			thisIp = InetAddress.getLocalHost();
			
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
			
		}
		
		System.out.println("[RCON] RCON Tool By Pauler. Ip: " + thisIp.getHostAddress().toString() + " Port: " + "7779" );
		
	}
	
	public class MiniServer extends Thread{
		
		Socket socket;
		ObjectOutputStream output;
		ObjectInputStream input;
		
		public MiniServer(Socket clientSocket) {
			
			this.socket = clientSocket;
			
		}
		
		public void run() {					
				
			try {
				
				output = new ObjectOutputStream(socket.getOutputStream());
				output.flush();
				
				input = new ObjectInputStream(socket.getInputStream());
					
					try {
						
						output.writeObject("");
						
						String _command = (String) input.readObject();
						
						StringTokenizer str = new StringTokenizer(_command);
						
						if (_command.startsWith(rconPassword)) {
							
							str.nextToken();
							
							adminUsername = str.nextToken();
							
							System.out.println("[RCON] Connected rcon user ["+ adminUsername + "] called: " + _command);
							
							//Register Commands Here
							
							if (str.nextToken().equalsIgnoreCase("test")) {
								
								System.out.println("Test Command.");
								
							}else if (str.nextToken().equalsIgnoreCase("ban")) {
								
								if (str.hasMoreTokens()) {
									
									try {
										
										L2PcInstance player = L2World.getInstance().getPlayer(str.nextToken());
										
										player.setAccountAccesslevel(-100);
										RegionBBSManager.getInstance().changeCommunityBoard();
										player.logout();
										player.sendMessage("You have been banned.");
									
									}
									catch(Exception e) {
										
										e.printStackTrace();
										
									}
								}
								
							}else if (str.nextToken().equalsIgnoreCase("unban")) {
								
								try {
									
									LoginServerThread.getInstance().sendAccessLevel(str.nextToken(), 0);
								
								}catch(Exception e) {
									
									e.printStackTrace();
									
								}
								
							}else if (str.nextToken().equalsIgnoreCase("shutdown")) {
								
								try {
									
									Shutdown.getInstance().startShutdown(L2World.getInstance().getPlayer(adminUsername), 20, false);
								
								}catch(Exception e) {
									
									e.printStackTrace();
									
								}
								
							}else if (str.nextToken().equalsIgnoreCase("restart")) {
								
								try {
									
									Shutdown.getInstance().startShutdown(L2World.getInstance().getPlayer(adminUsername), 20, true);
								
								}catch(Exception e) {
									
									e.printStackTrace();
									
								}
								
							}
							
						}
							
					} catch (Exception e) {

						System.out.println("[RCON] User Disconnected.");
						closeCrap();
						
					}
				
				} catch (Exception e) {
				
					e.printStackTrace();
				
				}
			
		}
		
		public void closeCrap() {
			
			try {
				
				output.close();
				input.close();
				socket.close();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			
		}
		
	}
	
}


