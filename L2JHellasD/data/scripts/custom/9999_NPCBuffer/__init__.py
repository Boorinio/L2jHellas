import sys
from com.l2jhellas.gameserver.model.actor.instance import L2PcInstance
from java.util import Iterator
from com.l2jhellas.gameserver.skills import SkillTable
from com.l2jhellas import L2DatabaseFactory
from com.l2jhellas.gameserver.model.quest import State
from com.l2jhellas.gameserver.model.quest import QuestState
from com.l2jhellas.gameserver.model.quest.jython import QuestJython as JQuest

qn = "9999_NPCBuffer"

NPC=[40006]
ADENA_ID=57
QuestId     = 9999
QuestName   = "NPCBuffer"
QuestDesc   = "custom"
InitialHtml = "1.htm"

class Quest (JQuest) :

	def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)


	def onEvent(self,event,st):
		htmltext = event
		count=st.getQuestItemsCount(ADENA_ID)
		if count < 150000  or st.getPlayer().getLevel() < 40 :
			htmltext = "<html><head><body>You dont have enough Adena,<br> or your level is too low. You must be 40 or higher.</body></html>"
		else:
			st.takeItems(ADENA_ID,0)
			st.getPlayer().setTarget(st.getPlayer())
			
			if event == "2":
				st.takeItems(ADENA_ID,160000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4360,3),False,False)
				st.getPlayer().restoreMP()		
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4359,3),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4358,3),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4357,2),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4354,4),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4353,6),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4350,4),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4347,6),False,False)
				st.getPlayer().restoreMP()			
				return "4.htm"
				st.setState(State.COMPLETED)

			if event == "3": 
				st.takeItems(ADENA_ID,80000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4352,2),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4351,6),False,False)	
				st.getPlayer().restoreMP()	
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4355,3),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4356,3),False,False)
				st.getPlayer().restoreMP()				
				return "4.htm"
				st.setState(State.COMPLETED)

			if event == "4":
				st.takeItems(ADENA_ID,140000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4346,4),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4342,2),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4343,3),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4344,3),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4345,3),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4349,2),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4348,6),False,False)
				st.getPlayer().restoreMP()				
				return "4.htm"
				st.setState(State.COMPLETED)
                        
			if event == "5":
				st.takeItems(ADENA_ID,400000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4360,3),False,False)	
				st.getPlayer().restoreMP()	
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4359,3),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4358,3),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4357,2),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4354,4),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4353,6),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4350,4),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4348,6),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4352,2),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4351,6),False,False)
				st.getPlayer().restoreMP()		
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4355,3),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4356,3),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4346,4),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4342,2),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4343,3),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4344,3),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4345,3),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4349,2),False,False)
				st.getPlayer().restoreMP()
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4347,6),False,False)	
				st.getPlayer().restoreMP()				
				return "4.htm"			
				st.setState(State.COMPLETED)

			if event == "6":
				st.takeItems(ADENA_ID,1000)
				st.getPlayer().restoreHP()
				return "1.htm"		
				st.setState(State.COMPLETED)

			#Wind Walk
			if event == "7":
				st.takeItems(ADENA_ID,25000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4342,2),False,False)
				st.getPlayer().restoreMP()
				return "5.htm"		
				st.setState(State.COMPLETED)

			#Decrease Weight
			if event == "8":
				st.takeItems(ADENA_ID,25000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4343,3),False,False)
				st.getPlayer().restoreMP()
				return "5.htm"		
				st.setState(State.COMPLETED)

			#Shield
			if event == "9":
				st.takeItems(ADENA_ID,25000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4344,3),False,False)
				st.getPlayer().restoreMP()
				return "5.htm"		
				st.setState(State.COMPLETED)

			#Might
			if event == "10":
				st.takeItems(ADENA_ID,25000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4345,3),False,False)
				st.getPlayer().restoreMP()
				return "5.htm"		
				st.setState(State.COMPLETED)

			#Mental Shield
			if event == "11":
				st.takeItems(ADENA_ID,25000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4346,4),False,False)
				st.getPlayer().restoreMP()
				return "5.htm"		
				st.setState(State.COMPLETED)

			#Bless the Body
			if event == "12":
				st.takeItems(ADENA_ID,25000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4347,6),False,False)
				st.getPlayer().restoreMP()
				return "5.htm"		
				st.setState(State.COMPLETED)

			#Bless the Soul
			if event == "13":
				st.takeItems(ADENA_ID,25000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4348,6),False,False)
				st.getPlayer().restoreMP()
				return "5.htm"		
				st.setState(State.COMPLETED)

			#Magic Barrier
			if event == "14":
				st.takeItems(ADENA_ID,25000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4349,2),False,False)
				st.getPlayer().restoreMP()
				return "5.htm"		
				st.setState(State.COMPLETED)

			#Resist Shock
			if event == "15":
				st.takeItems(ADENA_ID,25000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4350,4),False,False)
				st.getPlayer().restoreMP()
				return "5.htm"		
				st.setState(State.COMPLETED)

			#Concentration
			if event == "16":
				st.takeItems(ADENA_ID,25000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4351,6),False,False)
				st.getPlayer().restoreMP()
				return "5.htm"		
				st.setState(State.COMPLETED)

			#Berserker Spirit
			if event == "17":
				st.takeItems(ADENA_ID,25000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4352,2),False,False)
				st.getPlayer().restoreMP()
				return "5.htm"		
				st.setState(State.COMPLETED)

			#Bless Shield
			if event == "18":
				st.takeItems(ADENA_ID,25000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4353,6),False,False)
				st.getPlayer().restoreMP()
				return "5.htm"		
				st.setState(State.COMPLETED)

			#Vampiric Rage
			if event == "19":
				st.takeItems(ADENA_ID,25000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4354,4),False,False)
				st.getPlayer().restoreMP()
				return "5.htm"		
				st.setState(State.COMPLETED)

			#Acumen
			if event == "20":
				st.takeItems(ADENA_ID,25000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4355,3),False,False)
				st.getPlayer().restoreMP()
				return "5.htm"		
				st.setState(State.COMPLETED)

			#Empower
			if event == "21":
				st.takeItems(ADENA_ID,25000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4356,3),False,False)
				st.getPlayer().restoreMP()
				return "5.htm"		
				st.setState(State.COMPLETED)

			#Haste
			if event == "22":
				st.takeItems(ADENA_ID,25000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4357,2),False,False)
				st.getPlayer().restoreMP()
				return "5.htm"		
				st.setState(State.COMPLETED)

			#Guidance
			if event == "23":
				st.takeItems(ADENA_ID,25000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4358,3),False,False)
				st.getPlayer().restoreMP()
				return "5.htm"		
				st.setState(State.COMPLETED)

			#Focus
			if event == "24":
				st.takeItems(ADENA_ID,25000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4359,3),False,False)
				st.getPlayer().restoreMP()
				return "5.htm"		
				st.setState(State.COMPLETED)

			#Death Whisper
			if event == "25":
				st.takeItems(ADENA_ID,25000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(4360,3),False,False)
				st.getPlayer().restoreMP()
				return "5.htm"		
				st.setState(State.COMPLETED)

			if event == "26":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(271,1),False,False)	
				st.getPlayer().restoreMP()
				return "2.htm"

			if event == "27":
				st.takeItems(ADENA_ID,20000)	
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(272,1),False,False)
				st.getPlayer().restoreMP()
				return "2.htm"

			if event == "28":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(273,1),False,False)
				st.getPlayer().restoreMP()
				return "2.htm"

			if event == "29":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(274,1),False,False)
				st.getPlayer().restoreMP()
				return "2.htm"

			if event == "30":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(275,1),False,False)
				st.getPlayer().restoreMP()
				return "2.htm"

			if event == "31":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(276,1),False,False)
				st.getPlayer().restoreMP()
				return "2.htm"

			if event == "32":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(277,1),False,False)
				st.getPlayer().restoreMP()
				return "2.htm"

			if event == "33":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(307,1),False,False)
				st.getPlayer().restoreMP()
				return "2.htm"

			if event == "34":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(309,1),False,False)
				st.getPlayer().restoreMP()
				return "2.htm"

			if event == "35":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(310,1),False,False)
				st.getPlayer().restoreMP()
				return "2.htm"

			if event == "36":
				st.takeItems(ADENA_ID,20000)		
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(311,1),False,False)
				st.getPlayer().restoreMP()
				return "2.htm"

			if event == "37":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(366,1),False,False)
				st.getPlayer().restoreMP()
				return "2.htm"

			if event == "38":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(365,1),False,False)
				st.getPlayer().restoreMP()			
				return "2.htm"

			if event == "39":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(264,1),False,False)	
				st.getPlayer().restoreMP()
				return "3.htm"

			if event == "40":
				st.takeItems(ADENA_ID,20000)	
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(265,1),False,False)
				st.getPlayer().restoreMP()
				return "3.htm"

			if event == "41":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(266,1),False,False)
				st.getPlayer().restoreMP()
				return "3.htm"

			if event == "42":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(267,1),False,False)
				st.getPlayer().restoreMP()
				return "3.htm"

			if event == "43":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(268,1),False,False)
				st.getPlayer().restoreMP()
				return "3.htm"

			if event == "44":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(269,1),False,False)
				st.getPlayer().restoreMP()
				return "3.htm"

			if event == "45":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(270,1),False,False)
				st.getPlayer().restoreMP()
				return "3.htm"

			if event == "46":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(304,1),False,False)
				st.getPlayer().restoreMP()
				return "3.htm"

			if event == "47":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(305,1),False,False)
				st.getPlayer().restoreMP()
				return "3.htm"

			if event == "48":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(306,1),False,False)
				st.getPlayer().restoreMP()
				return "3.htm"	

			if event == "49":
				st.takeItems(ADENA_ID,20000)	
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(308,1),False,False)
				st.getPlayer().restoreMP()
				return "3.htm"

			if event == "50":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(363,1),False,False)
				st.getPlayer().restoreMP()
				return "3.htm"

			if event == "51":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(364,1),False,False)
				st.getPlayer().restoreMP()
				return "3.htm"	

			if event == "52":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(349,1),False,False)
				st.getPlayer().restoreMP()
				return "3.htm"		
				st.setState(State.COMPLETED)
				
				
			#Chant of Battle
			if event == "53":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(1007,3),False,False)
				st.getPlayer().restoreMP()
				return "7.htm"
				st.setState(State.COMPLETED)
				
			#Chant of Shielding
			if event == "54":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(1009,3),False,False)
				st.getPlayer().restoreMP()
				return "7.htm"
				st.setState(State.COMPLETED)
				
			#Chant of Fire
			if event == "55":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(1006,3),False,False)
				st.getPlayer().restoreMP()
				return "7.htm"
				st.setState(State.COMPLETED)
				
			#Chant of Flame
			if event == "56":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(1002,3),False,False)
				st.getPlayer().restoreMP()
				return "7.htm"
				st.setState(State.COMPLETED)
				
			#Chant of life
			if event == "57":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(1229,18),False,False)
				st.getPlayer().restoreMP()
				return "7.htm"
				st.setState(State.COMPLETED)
				
			#Chant of Fury
			if event == "58":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(1251,2),False,False)
				st.getPlayer().restoreMP()
				return "7.htm"
				st.setState(State.COMPLETED)
				
			#Chant of Evasion
			if event == "59":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(1252,3),False,False)
				st.getPlayer().restoreMP()
				return "7.htm"
				st.setState(State.COMPLETED)
				
			#Chant of Rage
			if event == "60":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(1253,3),False,False)
				st.getPlayer().restoreMP()
				return "7.htm"
				st.setState(State.COMPLETED)
				
			#Chant of Revenge
			if event == "61":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(1284,3),False,False)
				st.getPlayer().restoreMP()
				return "7.htm"
				st.setState(State.COMPLETED)
				
			#Chant of Vampire
			if event == "62":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(1310,4),False,False)
				st.getPlayer().restoreMP()
				return "7.htm"
				st.setState(State.COMPLETED)
				
			#Chant of Eagle
			if event == "63":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(1309,3),False,False)
				st.getPlayer().restoreMP()
				return "7.htm"
				st.setState(State.COMPLETED)
				
			#Chant of Predator
			if event == "64":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(1308,3),False,False)
				st.getPlayer().restoreMP()
				return "7.htm"
				st.setState(State.COMPLETED)
				
			#Chant of Spirit
			if event == "65":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(1362,1),False,False)
				st.getPlayer().restoreMP()
				return "7.htm"
				st.setState(State.COMPLETED)
				
			#Chant of Victory
			if event == "66":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(1363,1),False,False)
				st.getPlayer().restoreMP()
				return "7.htm"
				st.setState(State.COMPLETED)
				
			#chant of magnus
			if event == "67":
				st.takeItems(ADENA_ID,20000)
				st.getPlayer().useMagic(SkillTable.getInstance().getInfo(1413,1),False,False)
				st.getPlayer().restoreMP()
				return "7.htm"
				st.setState(State.COMPLETED)	
			
			#MPreg
			if event == "68":
				st.takeItems(ADENA_ID,1000)
				st.getPlayer().restoreMP()
				return "1.htm"		
				st.setState(State.COMPLETED)

			#CPHEAL
			if event == "69":
				st.takeItems(ADENA_ID,1000)
				st.getPlayer().restoreCP()
				return "1.htm"		
				st.setState(State.COMPLETED)			
				
			if htmltext != event:
				st.setState(State.COMPLETED)
				st.exitQuest(1)
		return htmltext


	def onTalk (self,npc,player):
	   st = player.getQuestState(qn)
	   htmltext = "<html><head><body>I have nothing to say to you</body></html>"
	   st.setState(State.STARTED)
	   return InitialHtml

QUEST       = Quest(QuestId,str(QuestId) + "_" + QuestName,QuestDesc)

for npcId in NPC:
 QUEST.addStartNpc(npcId)
 QUEST.addTalkId(npcId)
