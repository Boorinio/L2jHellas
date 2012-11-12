# Made by disKret
import sys
from com.l2jhellas.gameserver.model.quest import State
from com.l2jhellas.gameserver.model.quest import QuestState
from com.l2jhellas.gameserver.model.quest.jython import QuestJython as JQuest

qn = "13_ParcelDelivery"

#NPC
FUNDIN = 31274
VULCAN = 31539

#QUEST ITEM
PACKAGE = 7263

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
     self.questItemIds = [PACKAGE]

 def onEvent (self,event,st) :
   htmltext = event
   cond = st.getInt("cond")
   if event == "31274-2.htm" :
     if cond == 0 :
       st.set("cond","1")
       st.setState(State.STARTED)
       st.giveItems(PACKAGE,1)
       st.playSound("ItemSound.quest_accept")
   if event == "31539-1.htm" :
     if cond == 1 and st.getQuestItemsCount(PACKAGE) == 1 :
       st.takeItems(PACKAGE,1)
       st.giveItems(57,82000)
       st.exitQuest(False)
       st.set("cond","0")
       st.playSound("ItemSound.quest_finish")
     else :
       htmltext = "You don't have required items"
   return htmltext

 def onTalk (self,npc,player):
   htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   id = st.getState()
   cond = st.getInt("cond")
   if id == State.CREATED :
     st.set("cond","0")
   if npcId == FUNDIN and cond == 0 :
     if id == State.COMPLETED :
       htmltext = "<html><body>This quest has already been completed.</body></html>"
     elif player.getLevel() < 74 : 
       htmltext = "31274-1.htm"
       st.exitQuest(1)
     elif player.getLevel() >= 74 : 
       htmltext = "31274-0.htm"
   elif npcId == FUNDIN and cond == 1 :
     htmltext = "31274-2.htm"
   elif npcId == VULCAN and cond == 1 and id == State.STARTED:
     htmltext = "31539-0.htm"
   return htmltext

QUEST       = Quest(13,qn,"Parcel Delivery")

QUEST.addStartNpc(31274)
QUEST.addTalkId(31274)
QUEST.addTalkId(31539)