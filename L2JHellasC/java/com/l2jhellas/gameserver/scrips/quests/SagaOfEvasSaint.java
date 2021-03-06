package com.l2jhellas.gameserver.scrips.quests;

public class SagaOfEvasSaint extends SagasSuperClass
{
	public static String qn1 = "Q087_SagaOfEvasSaint";
	public static int qnu = 87;
	public static String qna = "Saga of Eva's Saint";
	
	public SagaOfEvasSaint()
	{
		super(qnu, qn1, qna);
		
		NPC = new int[]
		{
			30191,
			31626,
			31588,
			31280,
			31620,
			31646,
			31649,
			31653,
			31654,
			31655,
			31657,
			31280
		};
		
		Items = new int[]
		{
			7080,
			7524,
			7081,
			7502,
			7285,
			7316,
			7347,
			7378,
			7409,
			7440,
			7088,
			0
		};
		
		Mob = new int[]
		{
			27266,
			27236,
			27276
		};
		
		qn = qn1;
		classid = 105;
		prevclass = 0x1e;
		
		X = new int[]
		{
			164650,
			46087,
			46066
		};
		
		Y = new int[]
		{
			-74121,
			-36372,
			-36396
		};
		
		Z = new int[]
		{
			-2871,
			-1685,
			-1685
		};
		
		registerNPCs();
	}
}