--
-- Table structure for table `custom_npc`
-- 
DROP TABLE IF EXISTS `custom_npc`;
CREATE TABLE `custom_npc`(
  `id` decimal(11,0) NOT NULL default '0',
  `idTemplate` int(11) NOT NULL default '0',
  `name` varchar(200) default NULL,
  `serverSideName` int(1) default '0',
  `title` varchar(45) default '',
  `serverSideTitle` int(1) default '0',
  `class` varchar(200) default NULL,
  `collision_radius` decimal(5,2) default NULL,
  `collision_height` decimal(5,2) default NULL,
  `level` decimal(2,0) default NULL,
  `sex` varchar(6) default NULL,
  `type` varchar(20) default NULL,
  `attackrange` int(11) default NULL,
  `hp` decimal(8,0) default NULL,
  `mp` decimal(5,0) default NULL,
  `hpreg` decimal(8,2) default NULL,
  `mpreg` decimal(5,2) default NULL,
  `str` decimal(7,0) default NULL,
  `con` decimal(7,0) default NULL,
  `dex` decimal(7,0) default NULL,
  `int` decimal(7,0) default NULL,
  `wit` decimal(7,0) default NULL,
  `men` decimal(7,0) default NULL,
  `exp` decimal(9,0) default NULL,
  `sp` decimal(8,0) default NULL,
  `patk` decimal(5,0) default NULL,
  `pdef` decimal(5,0) default NULL,
  `matk` decimal(5,0) default NULL,
  `mdef` decimal(5,0) default NULL,
  `atkspd` decimal(3,0) default NULL,
  `aggro` decimal(6,0) default NULL,
  `matkspd` decimal(4,0) default NULL,
  `rhand` decimal(4,0) default NULL,
  `lhand` decimal(4,0) default NULL,
  `armor` decimal(1,0) default NULL,
  `walkspd` decimal(3,0) default NULL,
  `runspd` decimal(3,0) default NULL,
  `faction_id` varchar(40) default NULL,
  `faction_range` decimal(4,0) default NULL,
  `isUndead` int(11) default 0,
  `absorb_level` decimal(2,0) default 0,
  `absorb_type` enum('FULL_PARTY','LAST_HIT','PARTY_ONE_RANDOM') DEFAULT 'LAST_HIT' NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM;
-- 
-- Dumping data for table `npc`
-- 
INSERT INTO custom_npc
   (`id`, `idTemplate`, `name`, `serverSideName`, `title`, `serverSideTitle`, `class`, `collision_radius`, `collision_height`, `level`, `sex`, `type`, `attackrange`, `hp`, `mp`, `hpreg`, `mpreg`, `str`, `con`, `dex`, `int`, `wit`, `men`, `exp`, `sp`, `patk`, `pdef`, `matk`, `mdef`, `atkspd`, `aggro`, `matkspd`, `rhand`, `lhand`, `armor`, `walkspd`, `runspd`, `faction_id`, `faction_range`, `isUndead`, `absorb_level`, `absorb_type`)
VALUES
	(50007,31324,'Andromeda',1,'Wedding Manager',1,'NPC.a_casino_FDarkElf',8.00,23.00,70,'female','L2WeddingManager',40,3862,1493,11.85,2.78,40,43,30,21,20,10,0,0,1314,470,780,382,278,0,333,316,0,0,55,132,NULL,0,1,0,'LAST_HIT'),
	('54', '30767', 'Marion', '1', 'Faction Manager', '1', 'Monster2.queen_of_cat', '8.00', '22.00', '70', 'etc', 'L2Faction', '40', '3892', '1567', '23.00', '1.00', '40', '40', '40', '40', '40', '40', '0', '0', '2314', '2341', '324', '234', '234', '0', '333', '0', '0', '0', '65', '123', null, '0', '0', '0', 'LAST_HIT'),
	('70010', '31309', 'Aioria', '1', 'TvT Manager', '1', 'NPC.a_traderD_Mhuman', '8.00', '25.30', '70', 'male', 'L2Npc', '40', '3862', '1493', '11.85', '2.78', '40', '43', '30', '21', '20', '10', '0', '0', '1314', '470', '780', '382', '278', '0', '333', '0', '0', '0', '28', '132', null, '0', '0', '0', 'LAST_HIT'),
	('51', '30767', 'Maria', '1', 'Npc Buffer', '1', 'Monster2.queen_of_cat', '8.00', '22.00', '70', 'etc', 'L2NpcBuffer', '40', '3892', '1567', '23.00', '1.00', '40', '40', '40', '40', '40', '40', '0', '0', '2314', '2341', '324', '234', '234', '0', '333', '0', '0', '0', '65', '123', null, '0', '0', '0', 'LAST_HIT'),
	('52', '30767', 'Agent Guy', '1', 'Hitman Manager', '1', 'Monster2.queen_of_cat', '8.00', '22.00', '70', 'etc', 'L2Hitman', '40', '3892', '1567', '23.00', '1.00', '40', '40', '40', '40', '40', '40', '0', '0', '2314', '2341', '324', '234', '234', '0', '333', '0', '0', '0', '65', '123', null, '0', '0', '0', 'LAST_HIT'),
    ('25846', '30314', 'Raut', '1', 'Donate Info', '1', 'NPC.a_traderB_MHuman', '8.00', '25.00', '99', 'male', 'L2NpcWalker', '40', '999', '40', '40.00', '40.00', '999', '999', '999', '999', '999', '999', '40', '40', '40', '999', '40', '999', '40', '0', '40', '0', '0', '0', '80', '120', null, null, '0', '0', 'LAST_HIT'),
	('70004', '31309', 'Leon', '1', 'CTF Event Manager', '1', 'NPC.a_traderD_Mhuman', '8.00', '25.30', '70', 'male', 'L2Npc', '40', '2444', '2444', '0.00', '0.00', '10', '10', '10', '10', '10', '10', '0', '0', '500', '500', '500', '500', '278', '0', '333', '9376', '0', '0', '30', '120', 'null', '0', '0', '0', 'LAST_HIT'),
	('70005', '31309', 'Chris', '1', 'Account Manager', '1', 'NPC.a_traderD_Mhuman', '8.00', '25.30', '70', 'male', 'L2AccountManager', '40', '2444', '2444', '0.00', '0.00', '10', '10', '10', '10', '10', '10', '0', '0', '500', '500', '500', '500', '278', '0', '333', '9376', '0', '0', '30', '120', 'null', '0', '0', '0', 'LAST_HIT');
	