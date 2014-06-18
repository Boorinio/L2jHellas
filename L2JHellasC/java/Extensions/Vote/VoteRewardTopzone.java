/*
 * 
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
package Extensions.Vote;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.Announcements;
import com.l2jhellas.gameserver.ThreadPoolManager;
import com.l2jhellas.gameserver.model.L2World;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.ExShowScreenMessage;
import com.l2jhellas.gameserver.network.serverpackets.ExShowScreenMessage.SMPOS;

/**
 * @author Boorinio
 */
public class VoteRewardTopzone
{
	public static int LastVotes = 0, CurrentVotes = 0, GoalVotes = 0, AllowedRewards = Config.TOPZONE_BOXES_ALLOWED;
	public static List<String> Boxes = new ArrayList<>();
	
	public static void LoadTopZone()
	{
		CurrentVotes = getVotes();
		if (CurrentVotes == -1)
		{
			if (CurrentVotes == -1)
			{
				System.out.println("There was a problem on getting Topzone server votes.");
			}
			
			return;
		}
		LastVotes = CurrentVotes;
		GoalVotes = CurrentVotes + Config.TOPZONE_VOTES_DIFFERENCE;
		System.out.println("Topzone - Vote reward system initialized.");
		ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new Runnable()
		{
			@Override
			public void run()
			{
				RunEngine();
			}
		}, 60 * 1000 * Config.TOPZONE_REWARD_CHECK_TIME, 60 * 1000 * Config.TOPZONE_REWARD_CHECK_TIME);
	}
	
	static void RunEngine()
	{
		CurrentVotes = getVotes();
		if (CurrentVotes == -1)
		{
			if (CurrentVotes == -1)
			{
				System.out.println("There was a problem on getting Topzone server votes.");
			}
			
			return;
		}
		if ((CurrentVotes >= LastVotes && CurrentVotes < GoalVotes) || CurrentVotes == LastVotes)
		{
			for (L2PcInstance player : L2World.getAllPlayers())
			{
				player.sendPacket(new ExShowScreenMessage("TopZone Votes: " + CurrentVotes, 4000, SMPOS.BOTTOM_RIGHT, true));
			}
			Announcements.getInstance().announceToAll("TopZone Votes: " + CurrentVotes);
			Announcements.getInstance().announceToAll("Next Reward in: " + GoalVotes + " Votes.");
			waitSecs(5);
			for (L2PcInstance player : L2World.getAllPlayers())
			{
				player.sendPacket(new ExShowScreenMessage("Next Reward in: " + GoalVotes + " Votes.", 4000, SMPOS.BOTTOM_RIGHT, true));
			}
		}
		if (CurrentVotes >= GoalVotes)
		{
			RewardPlayers();
			for (L2PcInstance player : L2World.getAllPlayers())
			{
				player.sendPacket(new ExShowScreenMessage("TopZone Rewarded!", 4000, SMPOS.BOTTOM_RIGHT, true));
			}
			Announcements.getInstance().announceToAll("All players Rewarded!");
			GoalVotes = CurrentVotes + Config.TOPZONE_VOTES_DIFFERENCE;
			Announcements.getInstance().announceToAll("TopZone Votes: " + CurrentVotes);
			Announcements.getInstance().announceToAll("Next Reward in : " + GoalVotes + " Votes.");
			waitSecs(5);
			for (L2PcInstance player : L2World.getAllPlayers())
			{
				player.sendPacket(new ExShowScreenMessage("Next Reward in: " + GoalVotes + " Votes.", 4000, SMPOS.BOTTOM_RIGHT, true));
			}
		}
		LastVotes = CurrentVotes;
		
	}
	
	public static int countNumberEqual(String itemToCheck)
	{
		int count = 0;
		for (String i : Boxes)
		{
			if (i.equals(itemToCheck))
			{
				count++;
			}
		}
		return count;
	}
	
	public static void RewardPlayers()
	{
		for (L2PcInstance player : L2World.getAllPlayers())
		{
			String temp = player.getClient().getConnection().getInetAddress().getHostAddress();
			if (Config.TOPZONE_BOXES_ALLOWED != 0 && Boxes.contains(temp))
			{
				int count1 = countNumberEqual(temp);
				if (count1 >= Config.TOPZONE_BOXES_ALLOWED)
				{
					player.sendMessage("You have already been rewarded more than the admin wants!");
				}
				else
				{
					for (int i = 0; i < Config.TOPZONE_REWARD.length; i++)
					{
						player.addItem("Vote reward.", Config.TOPZONE_REWARD[i][0], Config.TOPZONE_REWARD[i][1], player, true);
						player.sendMessage("You have been rewarded check your inventory");
					}
					Boxes.add(temp);
				}
				
			}
			else
			{
				Boxes.add(temp);
				for (int i = 0; i < Config.TOPZONE_REWARD.length; i++)
				{
					player.addItem("Vote reward.", Config.TOPZONE_REWARD[i][0], Config.TOPZONE_REWARD[i][1], player, true);
					player.sendMessage("You have been rewarded check your inventory");
				}
			}
		}
		Boxes.clear();
	}
	
	public static void waitSecs(int i)
	{
		try
		{
			Thread.sleep(i * 1000);
		}
		catch (InterruptedException ie)
		{
			ie.printStackTrace();
		}
	}
	
	private static int getVotes()
	{
		String TopzoneLink = Config.TOPZONE_SERVER_LINK;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try
		{
			URLConnection con = new URL(TopzoneLink).openConnection();
			con.addRequestProperty("User-Agent", "L2TopZone");
			isr = new InputStreamReader(con.getInputStream());
			br = new BufferedReader(isr);
			
			String line;
			while ((line = br.readLine()) != null)
			{
				if(line.contains("Votes:"))
				{
					int votes = Integer.valueOf(line.split(">")[3].replace("</div", " "));
					
				}
				
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
			System.out.println("Error while getting server vote count.");
			System.out.println("Your URL is:" + TopzoneLink);
			System.out.println("Test in a browser to see if it correct!");
		}
		
		return -1;
	}
}