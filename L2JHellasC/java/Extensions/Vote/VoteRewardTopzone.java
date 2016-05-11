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
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.Announcements;
import com.l2jhellas.gameserver.Gui;
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
	private static Logger _log = Logger.getLogger(VoteRewardTopzone.class.getName());
	public static int LastVotes = 0, CurrentVotes = 0, GoalVotes = 0, AllowedRewards = Config.TOPZONE_BOXES_ALLOWED;
	public static List<String> Boxes = new ArrayList<>();
	
	public static void LoadTopZone()
	{
		CurrentVotes = getVotes();
		if (CurrentVotes == -1)
		{
			if (CurrentVotes == -1)
				_log.log(Level.WARNING, "There was a problem on getting Topzone server votes.");
			return;
		}
		LastVotes = CurrentVotes;
		GoalVotes = CurrentVotes + Config.TOPZONE_VOTES_DIFFERENCE;
		_log.log(Level.INFO, "Topzone - Vote reward system initialized.");
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
				_log.log(Level.WARNING, "There was a problem on getting Topzone server votes.");
			return;
		}
		if ((CurrentVotes >= LastVotes && CurrentVotes < GoalVotes) || CurrentVotes == LastVotes)
		{
			for (L2PcInstance player : L2World.getInstance().getAllPlayers().values())
				player.sendPacket(new ExShowScreenMessage("TopZone Votes: " + CurrentVotes, 4000, SMPOS.BOTTOM_RIGHT, true));
			Announcements.getInstance().announceToAll("TopZone Votes: " + CurrentVotes);
			Announcements.getInstance().announceToAll("Next Reward in: " + GoalVotes + " Votes.");
			waitSecs(5);
			for (L2PcInstance player : L2World.getInstance().getAllPlayers().values())
				player.sendPacket(new ExShowScreenMessage("Next Reward in: " + GoalVotes + " Votes.", 4000, SMPOS.BOTTOM_RIGHT, true));
		}
		if (CurrentVotes >= GoalVotes)
		{
			RewardPlayers();
			for (L2PcInstance player : L2World.getInstance().getAllPlayers().values())
			{
				player.sendPacket(new ExShowScreenMessage("TopZone Rewarded!", 4000, SMPOS.BOTTOM_RIGHT, true));
			}
			Announcements.getInstance().announceToAll("All players Rewarded!");
			GoalVotes = CurrentVotes + Config.TOPZONE_VOTES_DIFFERENCE;
			Announcements.getInstance().announceToAll("TopZone Votes: " + CurrentVotes);
			Announcements.getInstance().announceToAll("Next Reward in : " + GoalVotes + " Votes.");
			waitSecs(5);
			for (L2PcInstance player : L2World.getInstance().getAllPlayers().values())
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
			if (i.equals(itemToCheck))
				count++;
		return count;
	}
	
	public static void RewardPlayers()
	{
		for (L2PcInstance player : L2World.getInstance().getAllPlayers().values())
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
	
	public static int getVotes()
	{
		int votes = -1;
		try
		{
			if(!Config.TOPZONE_SERVER_LINK.endsWith(".html"))
				Config.TOPZONE_SERVER_LINK+=".html";
			
			URLConnection con = new URL(Config.TOPZONE_SERVER_LINK).openConnection();			
			con.addRequestProperty("User-L2Topzone", "Mozilla/4.76");
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String line;
			while ((line = br.readLine()) != null)
			{
				if (line.contains("fa fa-fw fa-lg fa-thumbs-up"))
				{
					
					final String letsSliptIt = line;
					final String[] nowLetsFindTheVote = letsSliptIt.split("</i>");
					final String nowLetsSplitTheVote = nowLetsFindTheVote[1];
					final String[] vote = nowLetsSplitTheVote.split("</span>");
					final String votess = vote[0];
					
				    votes = Integer.valueOf(votess);
                    Gui.topzone.setText("TopZone Votes: " + votes);

				return votes;	
		
				}
			}
			
			br.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
			System.out.println("Error while getting server vote count on TOPZONE.");
		}
	
	  return -1;
	}
		
}