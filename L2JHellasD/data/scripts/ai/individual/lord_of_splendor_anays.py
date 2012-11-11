import sys
from com.l2jhellas.gameserver.model.quest.jython import QuestJython as JQuest
from com.l2jhellas.gameserver.network.serverpackets import PlaySound
from com.l2jhellas.gameserver.ai import CtrlIntention
from com.l2jhellas.util import Rnd
from java.lang import System

LORD_OF_SPLENDOR_ANAYS = 29096

# Boss: Lord_of_Splendor_Anays
class lord_of_splendor_anays(JQuest):

  def __init__(self,id,name,descr):
    JQuest.__init__(self,id,name,descr)

  def init_LoadGlobalData(self) :
    # load the respawn date and time for Lord_of_Splendor_Anays from DB
    temp = self.loadGlobalQuestVar("respawnDatetime")
    if temp == "" :
      self.addSpawn(LORD_OF_SPLENDOR_ANAYS,113151,-76938,9,0,False,0)
    else :
      # the respawn time has not yet expired. Setup a timer to fire at the correct time
      # (calculate the time between now and the respawn time,
      # setup a timer to fire after that many msec)
      temp = long(temp) - System.currentTimeMillis()
      if temp > 0 :
        self.startQuestTimer("respawn", temp, None, None)
      else :
        # the time has already expired while the server was offline.  Delete the saved time and
        # immediately spawn Lord_of_Splendor_Anays.
        self.deleteGlobalQuestVar("respawnDatetime")
        self.addSpawn(LORD_OF_SPLENDOR_ANAYS,113151,-76938,9,0,False,0)
    return

  def onAdvEvent (self,event,npc,player):
    objId=0
    if event == "respawn" :
      self.deleteGlobalQuestVar("respawnDatetime")
      self.addSpawn(LORD_OF_SPLENDOR_ANAYS,113151,-76938,9,0,False,0)
    return

  def onKill(self,npc,player,isPet):
    objId=npc.getObjectId()
    npc.broadcastPacket(PlaySound(1, "BS02_D", 1, objId, npc.getX(), npc.getY(), npc.getZ()))
    # set respawn time for Lord_of_Splendor_Anays for 2 days and 1 to 24 hours [i.e. 172,800,000 +  1*3,600,000  random-less-than(24*3,600,000) millisecs]
    respawnTime = 176400000 + Rnd.get(24*3600000)
    self.startQuestTimer("respawn", respawnTime, None, None)
    # also save the respawn time so that the info is maintained past reboots
    self.saveGlobalQuestVar("respawnDatetime",str(System.currentTimeMillis() + respawnTime))
    return

# Quest class and state definition
QUEST       = lord_of_splendor_anays(-1,"lord_of_splendor_anays","ai")

# Quest NPC starter initialization
QUEST.addKillId(29096)