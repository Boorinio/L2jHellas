/*
 * Copyright (C) 2012-2016 L2JHellasD
 * 
 * This file is part of L2JHellasD.
 * 
 * L2JHellasD is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2JHellasD is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quests.Q001_LettersOfLove.Q001_LettersOfLove;
import quests.Q002_WhatWomenWant.Q002_WhatWomenWant;
import quests.Q003_WillTheSealBeBroken.Q003_WillTheSealBeBroken;
import quests.Q004_LongliveThePaagrioLord.Q004_LongliveThePaagrioLord;
import quests.Q005_MinersFavor.Q005_MinersFavor;
import quests.Q006_StepIntoTheFuture.Q006_StepIntoTheFuture;
import quests.Q007_ATripBegins.Q007_ATripBegins;
import quests.Q008_AnAdventureBegins.Q008_AnAdventureBegins;
import quests.Q009_IntoTheCityOfHumans.Q009_IntoTheCityOfHumans;
import quests.Q010_IntoTheWorld.Q010_IntoTheWorld;
import quests.Q011_SecretMeetingWithKetraOrcs.Q011_SecretMeetingWithKetraOrcs;
import quests.Q012_SecretMeetingWithVarkaSilenos.Q012_SecretMeetingWithVarkaSilenos;
import quests.Q013_ParcelDelivery.Q013_ParcelDelivery;
import quests.Q014_WhereaboutsOfTheArchaeologist.Q014_WhereaboutsOfTheArchaeologist;
import quests.Q015_SweetWhispers.Q015_SweetWhispers;
import quests.Q016_TheComingDarkness.Q016_TheComingDarkness;
import quests.Q017_LightAndDarkness.Q017_LightAndDarkness;
import quests.Q018_MeetingWithTheGoldenRam.Q018_MeetingWithTheGoldenRam;
import quests.Q019_GoToThePastureland.Q019_GoToThePastureland;
import quests.Q020_BringUpWithLove.Q020_BringUpWithLove;
import quests.Q021_HiddenTruth.Q021_HiddenTruth;
import quests.Q022_TragedyInVonHellmannForest.Q022_TragedyInVonHellmannForest;
import quests.Q023_LidiasHeart.Q023_LidiasHeart;
import quests.Q027_ChestCaughtWithABaitOfWind.Q027_ChestCaughtWithABaitOfWind;
import quests.Q028_ChestCaughtWithABaitOfIcyAir.Q028_ChestCaughtWithABaitOfIcyAir;
import quests.Q029_ChestCaughtWithABaitOfEarth.Q029_ChestCaughtWithABaitOfEarth;
import quests.Q030_ChestCaughtWithABaitOfFire.Q030_ChestCaughtWithABaitOfFire;
import quests.Q031_SecretBuriedInTheSwamp.Q031_SecretBuriedInTheSwamp;
import quests.Q032_AnObviousLie.Q032_AnObviousLie;
import quests.Q033_MakeAPairOfDressShoes.Q033_MakeAPairOfDressShoes;
import quests.Q034_InSearchOfCloth.Q034_InSearchOfCloth;
import quests.Q035_FindGlitteringJewelry.Q035_FindGlitteringJewelry;
import quests.Q036_MakeASewingKit.Q036_MakeASewingKit;
import quests.Q037_MakeFormalWear.Q037_MakeFormalWear;
import quests.Q038_DragonFangs.Q038_DragonFangs;
import quests.Q039_RedEyedInvaders.Q039_RedEyedInvaders;
import quests.Q042_HelpTheUncle.Q042_HelpTheUncle;
import quests.Q043_HelpTheSister.Q043_HelpTheSister;
import quests.Q044_HelpTheSon.Q044_HelpTheSon;
import quests.Q045_ToTalkingIsland.Q045_ToTalkingIsland;
import quests.Q046_OnceMoreInTheArmsOfTheMotherTree.Q046_OnceMoreInTheArmsOfTheMotherTree;
import quests.Q047_IntoTheDarkForest.Q047_IntoTheDarkForest;
import quests.Q048_ToTheImmortalPlateau.Q048_ToTheImmortalPlateau;
import quests.Q049_TheRoadHome.Q049_TheRoadHome;
import quests.Q050_LanoscosSpecialBait.Q050_LanoscosSpecialBait;
import quests.Q051_OFullesSpecialBait.Q051_OFullesSpecialBait;
import quests.Q052_WilliesSpecialBait.Q052_WilliesSpecialBait;
import quests.Q053_LinnaeusSpecialBait.Q053_LinnaeusSpecialBait;
import quests.Q101_SwordOfSolidarity.Q101_SwordOfSolidarity;
import quests.Q102_SeaOfSporesFever.Q102_SeaOfSporesFever;
import quests.Q103_SpiritOfCraftsman.Q103_SpiritOfCraftsman;
import quests.Q104_SpiritOfMirrors.Q104_SpiritOfMirrors;
import quests.Q105_SkirmishWithTheOrcs.Q105_SkirmishWithTheOrcs;
import quests.Q106_ForgottenTruth.Q106_ForgottenTruth;
import quests.Q107_MercilessPunishment.Q107_MercilessPunishment;
import quests.Q108_JumbleTumbleDiamondFuss.Q108_JumbleTumbleDiamondFuss;
import quests.Q109_InSearchOfTheNest.Q109_InSearchOfTheNest;
import quests.Q110_ToThePrimevalIsle.Q110_ToThePrimevalIsle;
import quests.Q111_ElrokianHuntersProof.Q111_ElrokianHuntersProof;
import quests.Q112_WalkOfFate.Q112_WalkOfFate;
import quests.Q113_StatusOfTheBeaconTower.Q113_StatusOfTheBeaconTower;
import quests.Q116_BeyondTheHillsOfWinter.Q116_BeyondTheHillsOfWinter;
import quests.Q117_TheOceanOfDistantStars.Q117_TheOceanOfDistantStars;
import quests.Q119_LastImperialPrince.Q119_LastImperialPrince;
import quests.Q121_PavelTheGiant.Q121_PavelTheGiant;
import quests.Q122_OminousNews.Q122_OminousNews;
import quests.Q123_TheLeaderAndTheFollower.Q123_TheLeaderAndTheFollower;
import quests.Q124_MeetingTheElroki.Q124_MeetingTheElroki;
import quests.Q125_TheNameOfEvil_1.Q125_TheNameOfEvil_1;
import quests.Q126_TheNameOfEvil_2.Q126_TheNameOfEvil_2;
import quests.Q127_KamaelAWindowToTheFuture.Q127_KamaelAWindowToTheFuture;
import quests.Q151_CureForFeverDisease.Q151_CureForFeverDisease;
import quests.Q152_ShardsOfGolem.Q152_ShardsOfGolem;
import quests.Q153_DeliverGoods.Q153_DeliverGoods;
import quests.Q154_SacrificeToTheSea.Q154_SacrificeToTheSea;
import quests.Q155_FindSirWindawood.Q155_FindSirWindawood;
import quests.Q156_MillenniumLove.Q156_MillenniumLove;
import quests.Q157_RecoverSmuggledGoods.Q157_RecoverSmuggledGoods;
import quests.Q158_SeedOfEvil.Q158_SeedOfEvil;
import quests.Q159_ProtectTheWaterSource.Q159_ProtectTheWaterSource;
import quests.Q160_NerupasRequest.Q160_NerupasRequest;
import quests.Q161_FruitOfTheMotherTree.Q161_FruitOfTheMotherTree;
import quests.Q162_CurseOfTheUndergroundFortress.Q162_CurseOfTheUndergroundFortress;
import quests.Q163_LegacyOfThePoet.Q163_LegacyOfThePoet;
import quests.Q164_BloodFiend.Q164_BloodFiend;
import quests.Q165_ShilensHunt.Q165_ShilensHunt;
import quests.Q166_MassOfDarkness.Q166_MassOfDarkness;
import quests.Q167_DwarvenKinship.Q167_DwarvenKinship;
import quests.Q168_DeliverSupplies.Q168_DeliverSupplies;
import quests.Q169_OffspringOfNightmares.Q169_OffspringOfNightmares;
import quests.Q170_DangerousSeduction.Q170_DangerousSeduction;
import quests.Q171_ActsOfEvil.Q171_ActsOfEvil;
import quests.Q211_TrialOfTheChallenger.Q211_TrialOfTheChallenger;
import quests.Q212_TrialOfDuty.Q212_TrialOfDuty;
import quests.Q213_TrialOfTheSeeker.Q213_TrialOfTheSeeker;
import quests.Q214_TrialOfTheScholar.Q214_TrialOfTheScholar;
import quests.Q215_TrialOfThePilgrim.Q215_TrialOfThePilgrim;
import quests.Q216_TrialOfTheGuildsman.Q216_TrialOfTheGuildsman;
import quests.Q217_TestimonyOfTrust.Q217_TestimonyOfTrust;
import quests.Q218_TestimonyOfLife.Q218_TestimonyOfLife;
import quests.Q219_TestimonyOfFate.Q219_TestimonyOfFate;
import quests.Q220_TestimonyOfGlory.Q220_TestimonyOfGlory;
import quests.Q222_TestOfTheDuelist.Q222_TestOfTheDuelist;
import quests.Q223_TestOfTheChampion.Q223_TestOfTheChampion;
import quests.Q224_TestOfSagittarius.Q224_TestOfSagittarius;
import quests.Q225_TestOfTheSearcher.Q225_TestOfTheSearcher;
import quests.Q226_TestOfTheHealer.Q226_TestOfTheHealer;
import quests.Q228_TestOfMagus.Q228_TestOfMagus;
import quests.Q230_TestOfTheSummoner.Q230_TestOfTheSummoner;
import quests.Q231_TestOfTheMaestro.Q231_TestOfTheMaestro;
import quests.Q232_TestOfTheLord.Q232_TestOfTheLord;
import quests.Q233_TestOfTheWarSpirit.Q233_TestOfTheWarSpirit;
import quests.Q234_FatesWhisper.Q234_FatesWhisper;
import quests.Q235_MimirsElixir.Q235_MimirsElixir;
import quests.Q241_PossessorOfAPreciousSoul.Q241_PossessorOfAPreciousSoul;
import quests.Q242_PossessorOfAPreciousSoul.Q242_PossessorOfAPreciousSoul;
import quests.Q246_PossessorOfAPreciousSoul.Q246_PossessorOfAPreciousSoul;
import quests.Q247_PossessorOfAPreciousSoul.Q247_PossessorOfAPreciousSoul;
import quests.Q257_TheGuardIsBusy.Q257_TheGuardIsBusy;
import quests.Q258_BringWolfPelts.Q258_BringWolfPelts;
import quests.Q259_RanchersPlea.Q259_RanchersPlea;
import quests.Q260_HuntTheOrcs.Q260_HuntTheOrcs;
import quests.Q261_CollectorsDream.Q261_CollectorsDream;
import quests.Q262_TradeWithTheIvoryTower.Q262_TradeWithTheIvoryTower;
import quests.Q263_OrcSubjugation.Q263_OrcSubjugation;
import quests.Q264_KeenClaws.Q264_KeenClaws;
import quests.Q265_ChainsOfSlavery.Q265_ChainsOfSlavery;
import quests.Q266_PleasOfPixies.Q266_PleasOfPixies;
import quests.Q267_WrathOfVerdure.Q267_WrathOfVerdure;
import quests.Q271_ProofOfValor.Q271_ProofOfValor;
import quests.Q272_WrathOfAncestors.Q272_WrathOfAncestors;
import quests.Q273_InvadersOfTheHolyLand.Q273_InvadersOfTheHolyLand;
import quests.Q274_SkirmishWithTheWerewolves.Q274_SkirmishWithTheWerewolves;
import quests.Q275_DarkWingedSpies.Q275_DarkWingedSpies;
import quests.Q276_TotemOfTheHestui.Q276_TotemOfTheHestui;
import quests.Q277_GatekeepersOffering.Q277_GatekeepersOffering;
import quests.Q291_RevengeOfTheRedbonnet.Q291_RevengeOfTheRedbonnet;
import quests.Q292_BrigandsSweep.Q292_BrigandsSweep;
import quests.Q293_TheHiddenVeins.Q293_TheHiddenVeins;
import quests.Q294_CovertBusiness.Q294_CovertBusiness;
import quests.Q295_DreamingOfTheSkies.Q295_DreamingOfTheSkies;
import quests.Q296_TarantulasSpiderSilk.Q296_TarantulasSpiderSilk;
import quests.Q297_GatekeepersFavor.Q297_GatekeepersFavor;
import quests.Q298_LizardmensConspiracy.Q298_LizardmensConspiracy;
import quests.Q299_GatherIngredientsForPie.Q299_GatherIngredientsForPie;
import quests.Q300_HuntingLetoLizardman.Q300_HuntingLetoLizardman;
import quests.Q303_CollectArrowheads.Q303_CollectArrowheads;
import quests.Q306_CrystalsOfFireAndIce.Q306_CrystalsOfFireAndIce;
import quests.Q313_CollectSpores.Q313_CollectSpores;
import quests.Q316_DestroyPlagueCarriers.Q316_DestroyPlagueCarriers;
import quests.Q317_CatchTheWind.Q317_CatchTheWind;
import quests.Q319_ScentOfDeath.Q319_ScentOfDeath;
import quests.Q320_BonesTellTheFuture.Q320_BonesTellTheFuture;
import quests.Q324_SweetestVenom.Q324_SweetestVenom;
import quests.Q325_GrimCollector.Q325_GrimCollector;
import quests.Q326_VanquishRemnants.Q326_VanquishRemnants;
import quests.Q327_RecoverTheFarmland.Q327_RecoverTheFarmland;
import quests.Q328_SenseForBusiness.Q328_SenseForBusiness;
import quests.Q329_CuriosityOfADwarf.Q329_CuriosityOfADwarf;
import quests.Q330_AdeptOfTaste.Q330_AdeptOfTaste;
import quests.Q331_ArrowOfVengeance.Q331_ArrowOfVengeance;
import quests.Q337_AudienceWithTheLandDragon.Q337_AudienceWithTheLandDragon;
import quests.Q338_AlligatorHunter.Q338_AlligatorHunter;
import quests.Q340_SubjugationOfLizardmen.Q340_SubjugationOfLizardmen;
import quests.Q341_HuntingForWildBeasts.Q341_HuntingForWildBeasts;
import quests.Q344_1000YearsTheEndOfLamentation.Q344_1000YearsTheEndOfLamentation;
import quests.Q345_MethodToRaiseTheDead.Q345_MethodToRaiseTheDead;
import quests.Q347_GoGetTheCalculator.Q347_GoGetTheCalculator;
import quests.Q348_AnArrogantSearch.Q348_AnArrogantSearch;
import quests.Q350_EnhanceYourWeapon.Q350_EnhanceYourWeapon;
import quests.Q351_BlackSwan.Q351_BlackSwan;
import quests.Q352_HelpRoodRaiseANewPet.Q352_HelpRoodRaiseANewPet;
import quests.Q353_PowerOfDarkness.Q353_PowerOfDarkness;
import quests.Q354_ConquestOfAlligatorIsland.Q354_ConquestOfAlligatorIsland;
import quests.Q355_FamilyHonor.Q355_FamilyHonor;
import quests.Q356_DigUpTheSeaOfSpores.Q356_DigUpTheSeaOfSpores;
import quests.Q357_WarehouseKeepersAmbition.Q357_WarehouseKeepersAmbition;
import quests.Q358_IllegitimateChildOfAGoddess.Q358_IllegitimateChildOfAGoddess;
import quests.Q359_ForSleeplessDeadmen.Q359_ForSleeplessDeadmen;
import quests.Q360_PlunderTheirSupplies.Q360_PlunderTheirSupplies;
import quests.Q362_BardsMandolin.Q362_BardsMandolin;
import quests.Q363_SorrowfulSoundOfFlute.Q363_SorrowfulSoundOfFlute;
import quests.Q364_JovialAccordion.Q364_JovialAccordion;
import quests.Q365_DevilsLegacy.Q365_DevilsLegacy;
import quests.Q366_SilverHairedShaman.Q366_SilverHairedShaman;
import quests.Q367_ElectrifyingRecharge.Q367_ElectrifyingRecharge;
import quests.Q368_TrespassingIntoTheSacredArea.Q368_TrespassingIntoTheSacredArea;
import quests.Q369_CollectorOfJewels.Q369_CollectorOfJewels;
import quests.Q370_AnElderSowsSeeds.Q370_AnElderSowsSeeds;
import quests.Q371_ShriekOfGhosts.Q371_ShriekOfGhosts;
import quests.Q372_LegacyOfInsolence.Q372_LegacyOfInsolence;
import quests.Q373_SupplierOfReagents.Q373_SupplierOfReagents;
import quests.Q374_WhisperOfDreams_Part1.Q374_WhisperOfDreams_Part1;
import quests.Q375_WhisperOfDreams_Part2.Q375_WhisperOfDreams_Part2;
import quests.Q376_ExplorationOfTheGiantsCave_Part1.Q376_ExplorationOfTheGiantsCave_Part1;
import quests.Q377_ExplorationOfTheGiantsCave_Part2.Q377_ExplorationOfTheGiantsCave_Part2;
import quests.Q378_MagnificentFeast.Q378_MagnificentFeast;
import quests.Q379_FantasyWine.Q379_FantasyWine;
import quests.Q380_BringOutTheFlavorOfIngredients.Q380_BringOutTheFlavorOfIngredients;
import quests.Q381_LetsBecomeARoyalMember.Q381_LetsBecomeARoyalMember;
import quests.Q382_KailsMagicCoin.Q382_KailsMagicCoin;
import quests.Q383_SearchingForTreasure.Q383_SearchingForTreasure;
import quests.Q384_WarehouseKeepersPastime.Q384_WarehouseKeepersPastime;
import quests.Q385_YokeOfThePast.Q385_YokeOfThePast;
import quests.Q401_PathToAWarrior.Q401_PathToAWarrior;
import quests.Q402_PathToAHumanKnight.Q402_PathToAHumanKnight;
import quests.Q403_PathToARogue.Q403_PathToARogue;
import quests.Q404_PathToAHumanWizard.Q404_PathToAHumanWizard;
import quests.Q405_PathToACleric.Q405_PathToACleric;
import quests.Q406_PathToAnElvenKnight.Q406_PathToAnElvenKnight;
import quests.Q407_PathToAnElvenScout.Q407_PathToAnElvenScout;
import quests.Q408_PathToAnElvenWizard.Q408_PathToAnElvenWizard;
import quests.Q409_PathToAnElvenOracle.Q409_PathToAnElvenOracle;
import quests.Q410_PathToAPalusKnight.Q410_PathToAPalusKnight;
import quests.Q411_PathToAnAssassin.Q411_PathToAnAssassin;
import quests.Q412_PathToADarkWizard.Q412_PathToADarkWizard;
import quests.Q413_PathToAShillienOracle.Q413_PathToAShillienOracle;
import quests.Q414_PathToAnOrcRaider.Q414_PathToAnOrcRaider;
import quests.Q415_PathToAMonk.Q415_PathToAMonk;
import quests.Q416_PathToAnOrcShaman.Q416_PathToAnOrcShaman;
import quests.Q417_PathToBecomeAScavenger.Q417_PathToBecomeAScavenger;
import quests.Q418_PathToAnArtisan.Q418_PathToAnArtisan;
import quests.Q419_GetAPet.Q419_GetAPet;
import quests.Q420_LittleWing.Q420_LittleWing;
import quests.Q421_LittleWingsBigAdventure.Q421_LittleWingsBigAdventure;
import quests.Q422_RepentYourSins.Q422_RepentYourSins;
import quests.Q426_QuestForFishingShot.Q426_QuestForFishingShot;
import quests.Q431_WeddingMarch.Q431_WeddingMarch;
import quests.Q432_BirthdayPartySong.Q432_BirthdayPartySong;
import quests.Q508_AClansReputation.Q508_AClansReputation;
import quests.Q509_TheClansPrestige.Q509_TheClansPrestige;
import quests.Q510_AClansReputation.Q510_AClansReputation;
import quests.Q601_WatchingEyes.Q601_WatchingEyes;
import quests.Q602_ShadowOfLight.Q602_ShadowOfLight;
import quests.Q603_DaimonTheWhiteEyed_Part1.Q603_DaimonTheWhiteEyed_Part1;
import quests.Q604_DaimonTheWhiteEyed_Part2.Q604_DaimonTheWhiteEyed_Part2;
import quests.Q605_AllianceWithKetraOrcs.Q605_AllianceWithKetraOrcs;
import quests.Q606_WarWithVarkaSilenos.Q606_WarWithVarkaSilenos;
import quests.Q607_ProveYourCourage.Q607_ProveYourCourage;
import quests.Q608_SlayTheEnemyCommander.Q608_SlayTheEnemyCommander;
import quests.Q609_MagicalPowerOfWater_Part1.Q609_MagicalPowerOfWater_Part1;
import quests.Q610_MagicalPowerOfWater_Part2.Q610_MagicalPowerOfWater_Part2;
import quests.Q611_AllianceWithVarkaSilenos.Q611_AllianceWithVarkaSilenos;
import quests.Q612_WarWithKetraOrcs.Q612_WarWithKetraOrcs;
import quests.Q613_ProveYourCourage.Q613_ProveYourCourage;
import quests.Q614_SlayTheEnemyCommander.Q614_SlayTheEnemyCommander;
import quests.Q615_MagicalPowerOfFire_Part1.Q615_MagicalPowerOfFire_Part1;
import quests.Q616_MagicalPowerOfFire_Part2.Q616_MagicalPowerOfFire_Part2;
import quests.Q617_GatherTheFlames.Q617_GatherTheFlames;
import quests.Q618_IntoTheFlame.Q618_IntoTheFlame;
import quests.Q619_RelicsOfTheOldEmpire.Q619_RelicsOfTheOldEmpire;
import quests.Q620_FourGoblets.Q620_FourGoblets;
import quests.Q621_EggDelivery.Q621_EggDelivery;
import quests.Q622_SpecialtyLiquorDelivery.Q622_SpecialtyLiquorDelivery;
import quests.Q623_TheFinestFood.Q623_TheFinestFood;
import quests.Q624_TheFinestIngredients_Part1.Q624_TheFinestIngredients_Part1;
import quests.Q625_TheFinestIngredients_Part2.Q625_TheFinestIngredients_Part2;
import quests.Q626_ADarkTwilight.Q626_ADarkTwilight;
import quests.Q627_HeartInSearchOfPower.Q627_HeartInSearchOfPower;
import quests.Q628_HuntOfTheGoldenRamMercenaryForce.Q628_HuntOfTheGoldenRamMercenaryForce;
import quests.Q629_CleanUpTheSwampOfScreams.Q629_CleanUpTheSwampOfScreams;
import quests.Q631_DeliciousTopChoiceMeat.Q631_DeliciousTopChoiceMeat;
import quests.Q632_NecromancersRequest.Q632_NecromancersRequest;
import quests.Q633_InTheForgottenVillage.Q633_InTheForgottenVillage;
import quests.Q634_InSearchOfFragmentsOfDimension.Q634_InSearchOfFragmentsOfDimension;
import quests.Q636_TruthBeyondTheGate.Q636_TruthBeyondTheGate;
import quests.Q637_ThroughTheGateOnceMore.Q637_ThroughTheGateOnceMore;
import quests.Q638_SeekersOfTheHolyGrail.Q638_SeekersOfTheHolyGrail;
import quests.Q639_GuardiansOfTheHolyGrail.Q639_GuardiansOfTheHolyGrail;
import quests.Q640_TheZeroHour.Q640_TheZeroHour;
import quests.Q641_AttackSailren.Q641_AttackSailren;
import quests.Q642_APowerfulPrimevalCreature.Q642_APowerfulPrimevalCreature;
import quests.Q643_RiseAndFallOfTheElrokiTribe.Q643_RiseAndFallOfTheElrokiTribe;
import quests.Q644_GraveRobberAnnihilation.Q644_GraveRobberAnnihilation;
import quests.Q645_GhostsOfBatur.Q645_GhostsOfBatur;
import quests.Q646_SignsOfRevolt.Q646_SignsOfRevolt;
import quests.Q647_InfluxOfMachines.Q647_InfluxOfMachines;
import quests.Q649_ALooterAndARailroadMan.Q649_ALooterAndARailroadMan;
import quests.Q650_ABrokenDream.Q650_ABrokenDream;
import quests.Q651_RunawayYouth.Q651_RunawayYouth;
import quests.Q652_AnAgedExAdventurer.Q652_AnAgedExAdventurer;
import quests.Q653_WildMaiden.Q653_WildMaiden;
import quests.Q654_JourneyToASettlement.Q654_JourneyToASettlement;
import quests.Q659_IdRatherBeCollectingFairyBreath.Q659_IdRatherBeCollectingFairyBreath;
import quests.Q660_AidingTheFloranVillage.Q660_AidingTheFloranVillage;
import quests.Q661_MakingTheHarvestGroundsSafe.Q661_MakingTheHarvestGroundsSafe;
import quests.Q662_AGameOfCards.Q662_AGameOfCards;
import quests.Q663_SeductiveWhispers.Q663_SeductiveWhispers;
import quests.Q688_DefeatTheElrokianRaiders.Q688_DefeatTheElrokianRaiders;
import teleports.ElrokiTeleporters.ElrokiTeleporters;
import teleports.GatekeeperSpirit.GatekeeperSpirit;
import teleports.GrandBossTeleporters.GrandBossTeleporters;
import teleports.HuntingGroundsTeleport.HuntingGroundsTeleport;
import teleports.NewbieTravelToken.NewbieTravelToken;
import teleports.NoblesseTeleport.NoblesseTeleport;
import teleports.OracleTeleport.OracleTeleport;
import teleports.PaganTeleporters.PaganTeleporters;
import teleports.RaceTrack.RaceTrack;
import teleports.TeleportWithCharm.TeleportWithCharm;
import teleports.ToIVortex.ToIVortex;
import village_master.Alliance.Alliance;
import village_master.Clan.Clan;
import village_master.FirstClassChange.FirstClassChange;
import village_master.SecondClassChange.SecondClassChange;
import ai.group.AncientEGG;
import ai.group.CatsEyeBandit;
import ai.group.Chests;
import ai.group.DeluLizardmanSpecialAgent;
import ai.group.DeluLizardmanSpecialCommander;
import ai.group.FairyTrees;
import ai.group.FeedableBeasts;
import ai.group.FleeingNPCs;
import ai.group.FrenzyOnAttack;
import ai.group.FrozenLabyrinth;
import ai.group.GatekeeperZombies;
import ai.group.HotSpringDisease;
import ai.group.KarulBugbear;
import ai.group.L2AttackableAIScript;
import ai.group.Monastery;
import ai.group.OlMahumGeneral;
import ai.group.PlainsOfDion;
import ai.group.PolymorphingAngel;
import ai.group.PolymorphingOnAttack;
import ai.group.PrimevalIsle;
import ai.group.RetreatOnAttack;
import ai.group.ScarletStokateNoble;
import ai.group.SearchingMaster;
import ai.group.SeeThroughSilentMove;
import ai.group.SpeakingNPCs;
import ai.group.Splendor;
import ai.group.StakatoNest;
import ai.group.SummonMinions;
import ai.group.TimakOrcSupplier;
import ai.group.TimakOrcTroopLeader;
import ai.group.TurekOrcFootman;
import ai.group.TurekOrcOverlord;
import ai.group.TurekOrcWarlord;
import ai.group.VarkaKetraAlly;
import ai.individual.Antharas;
import ai.individual.Baium;
import ai.individual.Core;
import ai.individual.DrChaos;
import ai.individual.FleeNpc;
import ai.individual.Gordon;
import ai.individual.Orfen;
import ai.individual.QueenAnt;
import ai.individual.Sailren;
import ai.individual.Valakas;
import ai.individual.Zaken;
import custom.EchoCrystals.EchoCrystals;
import custom.HeroCirclet.HeroCirclet;
import custom.HeroWeapon.HeroWeapon;
import custom.KetraOrcSupport.KetraOrcSupport;
import custom.MissQueen.MissQueen;
import custom.NpcLocationInfo.NpcLocationInfo;
import custom.RaidbossInfo.RaidbossInfo;
import custom.ShadowWeapon.ShadowWeapon;
import custom.VarkaSilenosSupport.VarkaSilenosSupport;

/**
 * @author AbsolutePower
 */
public class ScriptLoader
{
	
	private static final Logger _log = LoggerFactory.getLogger(ScriptLoader.class);
	
	
	private static final Class<?>[] Quests =
	{
		//quests
		Q001_LettersOfLove.class,
		Q002_WhatWomenWant.class,
		Q003_WillTheSealBeBroken.class,
		Q004_LongliveThePaagrioLord.class,
		Q005_MinersFavor.class,
		Q006_StepIntoTheFuture.class,
		Q007_ATripBegins.class,
		Q008_AnAdventureBegins.class,
		Q009_IntoTheCityOfHumans.class,
		Q010_IntoTheWorld.class,
		Q011_SecretMeetingWithKetraOrcs.class,
		Q012_SecretMeetingWithVarkaSilenos.class,
		Q013_ParcelDelivery.class,
		Q014_WhereaboutsOfTheArchaeologist.class,
		Q015_SweetWhispers.class,
		Q016_TheComingDarkness.class,
		Q017_LightAndDarkness.class,
		Q018_MeetingWithTheGoldenRam.class,
		Q019_GoToThePastureland.class,
		Q020_BringUpWithLove.class,
		Q021_HiddenTruth.class,
		Q022_TragedyInVonHellmannForest.class,
		Q023_LidiasHeart.class,
		Q027_ChestCaughtWithABaitOfWind.class,
		Q028_ChestCaughtWithABaitOfIcyAir.class,
		Q029_ChestCaughtWithABaitOfEarth.class,
		Q030_ChestCaughtWithABaitOfFire.class,
		Q031_SecretBuriedInTheSwamp.class,
		Q032_AnObviousLie.class,
		Q033_MakeAPairOfDressShoes.class,
		Q034_InSearchOfCloth.class,
		Q035_FindGlitteringJewelry.class,
		Q036_MakeASewingKit.class,
		Q037_MakeFormalWear.class,
		Q038_DragonFangs.class,
		Q039_RedEyedInvaders.class,
		Q042_HelpTheUncle.class,
		Q043_HelpTheSister.class,
		Q044_HelpTheSon.class,
		Q045_ToTalkingIsland.class,
		Q046_OnceMoreInTheArmsOfTheMotherTree.class,
		Q047_IntoTheDarkForest.class,
		Q048_ToTheImmortalPlateau.class,
		Q049_TheRoadHome.class,
		Q050_LanoscosSpecialBait.class,
		Q051_OFullesSpecialBait.class,
		Q052_WilliesSpecialBait.class,
		Q053_LinnaeusSpecialBait.class,
		Q101_SwordOfSolidarity.class,
		Q102_SeaOfSporesFever.class,
		Q103_SpiritOfCraftsman.class,
		Q104_SpiritOfMirrors.class,
		Q105_SkirmishWithTheOrcs.class,
		Q106_ForgottenTruth.class,
		Q107_MercilessPunishment.class,
		Q108_JumbleTumbleDiamondFuss.class,
		Q109_InSearchOfTheNest.class,
		Q110_ToThePrimevalIsle.class,
		Q111_ElrokianHuntersProof.class,
		Q112_WalkOfFate.class,
		Q113_StatusOfTheBeaconTower.class,
		Q116_BeyondTheHillsOfWinter.class,
		Q117_TheOceanOfDistantStars.class,
		Q119_LastImperialPrince.class,
		Q121_PavelTheGiant.class,
		Q122_OminousNews.class,
		Q123_TheLeaderAndTheFollower.class,
		Q124_MeetingTheElroki.class,
		Q125_TheNameOfEvil_1.class,
		Q126_TheNameOfEvil_2.class,
		Q127_KamaelAWindowToTheFuture.class,
		Q151_CureForFeverDisease.class,
		Q152_ShardsOfGolem.class,
		Q153_DeliverGoods.class,
		Q154_SacrificeToTheSea.class,
		Q155_FindSirWindawood.class,
		Q156_MillenniumLove.class,
		Q157_RecoverSmuggledGoods.class,
		Q158_SeedOfEvil.class,
		Q159_ProtectTheWaterSource.class,
		Q160_NerupasRequest.class,
		Q161_FruitOfTheMotherTree.class,
		Q162_CurseOfTheUndergroundFortress.class,
		Q163_LegacyOfThePoet.class,
		Q164_BloodFiend.class,
		Q165_ShilensHunt.class,
		Q166_MassOfDarkness.class,
		Q167_DwarvenKinship.class,
		Q168_DeliverSupplies.class,
		Q169_OffspringOfNightmares.class,
		Q170_DangerousSeduction.class,
		Q171_ActsOfEvil.class,
		Q211_TrialOfTheChallenger.class,
		Q212_TrialOfDuty.class,
		Q213_TrialOfTheSeeker.class,
		Q214_TrialOfTheScholar.class,
		Q215_TrialOfThePilgrim.class,
		Q216_TrialOfTheGuildsman.class,
		Q217_TestimonyOfTrust.class,
		Q218_TestimonyOfLife.class,
		Q219_TestimonyOfFate.class,
		Q220_TestimonyOfGlory.class,
		Q222_TestOfTheDuelist.class,
		Q223_TestOfTheChampion.class,
		Q224_TestOfSagittarius.class,
		Q225_TestOfTheSearcher.class,
	    Q226_TestOfTheHealer.class,
		Q228_TestOfMagus.class,
		Q230_TestOfTheSummoner.class,
		Q231_TestOfTheMaestro.class,
		Q232_TestOfTheLord.class,
		Q233_TestOfTheWarSpirit.class,
		Q234_FatesWhisper.class,
		Q235_MimirsElixir.class,
		Q241_PossessorOfAPreciousSoul.class,
		Q242_PossessorOfAPreciousSoul.class,
		Q246_PossessorOfAPreciousSoul.class,
		Q247_PossessorOfAPreciousSoul.class,
		Q257_TheGuardIsBusy.class,
		Q258_BringWolfPelts.class,
		Q259_RanchersPlea.class,
		Q260_HuntTheOrcs.class,
		Q261_CollectorsDream.class,
		Q262_TradeWithTheIvoryTower.class,
		Q263_OrcSubjugation.class,
		Q264_KeenClaws.class,
		Q265_ChainsOfSlavery.class,
		Q266_PleasOfPixies.class,
		Q267_WrathOfVerdure.class,
		Q271_ProofOfValor.class,
		Q272_WrathOfAncestors.class,
		Q273_InvadersOfTheHolyLand.class,
		Q274_SkirmishWithTheWerewolves.class,
		Q275_DarkWingedSpies.class,
		Q276_TotemOfTheHestui.class,
		Q277_GatekeepersOffering.class,
		Q291_RevengeOfTheRedbonnet.class,
		Q292_BrigandsSweep.class,
		Q293_TheHiddenVeins.class,
		Q294_CovertBusiness.class,
		Q295_DreamingOfTheSkies.class,
		Q296_TarantulasSpiderSilk.class,
		Q297_GatekeepersFavor.class,
		Q298_LizardmensConspiracy.class,
		Q299_GatherIngredientsForPie.class,
		Q300_HuntingLetoLizardman.class,
		Q303_CollectArrowheads.class,
		Q306_CrystalsOfFireAndIce.class,
		Q313_CollectSpores.class,
		Q316_DestroyPlagueCarriers.class,
		Q317_CatchTheWind.class,
		Q319_ScentOfDeath.class,
		Q320_BonesTellTheFuture.class,
		Q324_SweetestVenom.class,
		Q325_GrimCollector.class,
		Q326_VanquishRemnants.class,
		Q327_RecoverTheFarmland.class,
		Q328_SenseForBusiness.class,
		Q329_CuriosityOfADwarf.class,
		Q330_AdeptOfTaste.class,
		Q331_ArrowOfVengeance.class,
		Q337_AudienceWithTheLandDragon.class,
		Q338_AlligatorHunter.class,
		Q340_SubjugationOfLizardmen.class,
		Q341_HuntingForWildBeasts.class,
		Q344_1000YearsTheEndOfLamentation.class,
		Q345_MethodToRaiseTheDead.class,
		Q347_GoGetTheCalculator.class,
		Q348_AnArrogantSearch.class,
		Q350_EnhanceYourWeapon.class,
		Q351_BlackSwan.class,
		Q352_HelpRoodRaiseANewPet.class,
		Q353_PowerOfDarkness.class,
		Q354_ConquestOfAlligatorIsland.class,
		Q355_FamilyHonor.class,
		Q356_DigUpTheSeaOfSpores.class,
		Q357_WarehouseKeepersAmbition.class,
		Q358_IllegitimateChildOfAGoddess.class,
		Q359_ForSleeplessDeadmen.class,
		Q360_PlunderTheirSupplies.class,
		Q362_BardsMandolin.class,
		Q363_SorrowfulSoundOfFlute.class,
		Q364_JovialAccordion.class,
		Q365_DevilsLegacy.class,
		Q366_SilverHairedShaman.class,
		Q367_ElectrifyingRecharge.class,
		Q368_TrespassingIntoTheSacredArea.class,
		Q369_CollectorOfJewels.class,
		Q370_AnElderSowsSeeds.class,
		Q371_ShriekOfGhosts.class,
		Q372_LegacyOfInsolence.class,
		Q373_SupplierOfReagents.class,
		Q374_WhisperOfDreams_Part1.class,
		Q375_WhisperOfDreams_Part2.class,
		Q376_ExplorationOfTheGiantsCave_Part1.class,
		Q377_ExplorationOfTheGiantsCave_Part2.class,
		Q378_MagnificentFeast.class,
		Q379_FantasyWine.class,
		Q380_BringOutTheFlavorOfIngredients.class,
		Q381_LetsBecomeARoyalMember.class,
		Q382_KailsMagicCoin.class,
		Q383_SearchingForTreasure.class,
		Q384_WarehouseKeepersPastime.class,
		Q385_YokeOfThePast.class,
		Q401_PathToAWarrior.class,
		Q402_PathToAHumanKnight.class,
		Q403_PathToARogue.class,
		Q404_PathToAHumanWizard.class,
		Q405_PathToACleric.class,
		Q406_PathToAnElvenKnight.class,
		Q407_PathToAnElvenScout.class,
		Q408_PathToAnElvenWizard.class,
		Q409_PathToAnElvenOracle.class,
		Q410_PathToAPalusKnight.class,
		Q411_PathToAnAssassin.class,
		Q412_PathToADarkWizard.class,
		Q413_PathToAShillienOracle.class,
		Q414_PathToAnOrcRaider.class,
		Q415_PathToAMonk.class,
		Q416_PathToAnOrcShaman.class,
		Q417_PathToBecomeAScavenger.class,
		Q418_PathToAnArtisan.class,
		Q419_GetAPet.class,
		Q420_LittleWing.class,
		Q421_LittleWingsBigAdventure.class,
		Q422_RepentYourSins.class,
		Q426_QuestForFishingShot.class,
		Q431_WeddingMarch.class,
		Q432_BirthdayPartySong.class,
		Q508_AClansReputation.class,
		Q509_TheClansPrestige.class,
		Q510_AClansReputation.class,
		Q601_WatchingEyes.class,
		Q602_ShadowOfLight.class,
		Q603_DaimonTheWhiteEyed_Part1.class,
		Q604_DaimonTheWhiteEyed_Part2.class,
		Q605_AllianceWithKetraOrcs.class,
		Q606_WarWithVarkaSilenos.class,
		Q607_ProveYourCourage.class,
		Q608_SlayTheEnemyCommander.class,
		Q609_MagicalPowerOfWater_Part1.class,
		Q610_MagicalPowerOfWater_Part2.class,
		Q611_AllianceWithVarkaSilenos.class,
		Q612_WarWithKetraOrcs.class,
		Q613_ProveYourCourage.class,
		Q614_SlayTheEnemyCommander.class,
		Q615_MagicalPowerOfFire_Part1.class,
		Q616_MagicalPowerOfFire_Part2.class,
		Q617_GatherTheFlames.class,
		Q618_IntoTheFlame.class,
		Q619_RelicsOfTheOldEmpire.class,
		Q620_FourGoblets.class,
		Q621_EggDelivery.class,
		Q622_SpecialtyLiquorDelivery.class,
		Q623_TheFinestFood.class,
		Q624_TheFinestIngredients_Part1.class,
		Q625_TheFinestIngredients_Part2.class,
		Q626_ADarkTwilight.class,
		Q627_HeartInSearchOfPower.class,
		Q628_HuntOfTheGoldenRamMercenaryForce.class,
		Q629_CleanUpTheSwampOfScreams.class,
		Q631_DeliciousTopChoiceMeat.class,
		Q632_NecromancersRequest.class,
		Q633_InTheForgottenVillage.class,
		Q634_InSearchOfFragmentsOfDimension.class,
		Q636_TruthBeyondTheGate.class,
		Q637_ThroughTheGateOnceMore.class,
		Q638_SeekersOfTheHolyGrail.class,
		Q639_GuardiansOfTheHolyGrail.class,
		Q640_TheZeroHour.class,
		Q641_AttackSailren.class,
		Q642_APowerfulPrimevalCreature.class,
		Q643_RiseAndFallOfTheElrokiTribe.class,
		Q644_GraveRobberAnnihilation.class,
		Q645_GhostsOfBatur.class,
		Q646_SignsOfRevolt.class,
		Q647_InfluxOfMachines.class,
		Q649_ALooterAndARailroadMan.class,
		Q650_ABrokenDream.class,
		Q651_RunawayYouth.class,
		Q652_AnAgedExAdventurer.class,
		Q653_WildMaiden.class,
		Q654_JourneyToASettlement.class,
		Q659_IdRatherBeCollectingFairyBreath.class,
		Q660_AidingTheFloranVillage.class,
		Q661_MakingTheHarvestGroundsSafe.class,
		Q662_AGameOfCards.class,
		Q663_SeductiveWhispers.class,
		Q688_DefeatTheElrokianRaiders.class,
		
		AncientEGG.class,
		CatsEyeBandit.class,
		Chests.class,
		DeluLizardmanSpecialAgent.class,
		DeluLizardmanSpecialCommander.class,
		FairyTrees.class,
		FeedableBeasts.class,
		FleeingNPCs.class,
		FrenzyOnAttack.class,
		FrozenLabyrinth.class,
		GatekeeperZombies.class,
		HotSpringDisease.class,
		KarulBugbear.class,
		L2AttackableAIScript.class,
		Monastery.class,
		OlMahumGeneral.class,
		PlainsOfDion.class,
		PolymorphingAngel.class,
		PolymorphingOnAttack.class,
		PrimevalIsle.class,
		RetreatOnAttack.class,
		ScarletStokateNoble.class,
		SearchingMaster.class,
		SeeThroughSilentMove.class,
		SpeakingNPCs.class,
		Splendor.class,
		StakatoNest.class,
		SummonMinions.class,
		TimakOrcSupplier.class,
		TimakOrcTroopLeader.class,
		TurekOrcFootman.class,
		TurekOrcOverlord.class,
		TurekOrcWarlord.class,
		VarkaKetraAlly.class,
		//customs
		EchoCrystals.class,
		HeroCirclet.class,
		HeroWeapon.class,
		KetraOrcSupport.class,
		MissQueen.class,
		NpcLocationInfo.class,
		RaidbossInfo.class,
		ShadowWeapon.class,
		VarkaSilenosSupport.class,
			
		//teleports	
		ElrokiTeleporters.class,
		GatekeeperSpirit.class,
		GrandBossTeleporters.class,
		HuntingGroundsTeleport.class,
		NewbieTravelToken.class,
		NoblesseTeleport.class,
		OracleTeleport.class,
		PaganTeleporters.class,
		RaceTrack.class,
		TeleportWithCharm.class,
		ToIVortex.class,
		
		//VilageMaster
		Alliance.class,
		Clan.class,
		FirstClassChange.class,
		SecondClassChange.class,
		
		//bosses
		Antharas.class,
		Baium.class,
		Core.class,
		DrChaos.class,
		FleeNpc.class,
		Gordon.class,
		// nor ready IceFairySirra.class,
		Orfen.class,
		QueenAnt.class,
		Sailren.class,
		Valakas.class,
		Zaken.class



	};
	
	public static void main(String[] args)
	{
		teleiwne();
	}
	
	private static void teleiwne()
	{
		for (Class<?> _qs : Quests)
		{
			try
			{
				_qs.newInstance();
			}
			catch (Exception e)
			{
				_log.error(ScriptLoader.class.getSimpleName() + ": Failed loading " + _qs.getSimpleName() + ":", e);
			}
		}
	}
}