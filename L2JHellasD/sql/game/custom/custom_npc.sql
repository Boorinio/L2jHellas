SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `custom_npc`
-- ----------------------------
DROP TABLE IF EXISTS `custom_npc`;

CREATE TABLE `custom_npc` (
  `id` decimal(11,0) NOT NULL DEFAULT '0',
  `idTemplate` int(11) NOT NULL DEFAULT '0',
  `name` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `serverSideName` int(1) DEFAULT '0',
  `title` varchar(45) COLLATE utf8_bin DEFAULT '',
  `serverSideTitle` int(1) DEFAULT '0',
  `class` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `collision_radius` decimal(5,2) DEFAULT NULL,
  `collision_height` decimal(5,2) DEFAULT NULL,
  `level` decimal(2,0) DEFAULT NULL,
  `sex` varchar(6) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `attackrange` int(11) DEFAULT NULL,
  `hp` decimal(8,0) DEFAULT NULL,
  `mp` decimal(5,0) DEFAULT NULL,
  `hpreg` decimal(8,2) DEFAULT NULL,
  `mpreg` decimal(5,2) DEFAULT NULL,
  `str` decimal(7,0) DEFAULT NULL,
  `con` decimal(7,0) DEFAULT NULL,
  `dex` decimal(7,0) DEFAULT NULL,
  `int` decimal(7,0) DEFAULT NULL,
  `wit` decimal(7,0) DEFAULT NULL,
  `men` decimal(7,0) DEFAULT NULL,
  `exp` decimal(9,0) DEFAULT NULL,
  `sp` decimal(8,0) DEFAULT NULL,
  `patk` decimal(5,0) DEFAULT NULL,
  `pdef` decimal(5,0) DEFAULT NULL,
  `matk` decimal(5,0) DEFAULT NULL,
  `mdef` decimal(5,0) DEFAULT NULL,
  `atkspd` decimal(3,0) DEFAULT NULL,
  `aggro` decimal(6,0) DEFAULT NULL,
  `matkspd` decimal(4,0) DEFAULT NULL,
  `rhand` decimal(4,0) DEFAULT NULL,
  `lhand` decimal(4,0) DEFAULT NULL,
  `armor` decimal(1,0) DEFAULT NULL,
  `walkspd` decimal(3,0) DEFAULT NULL,
  `runspd` decimal(3,0) DEFAULT NULL,
  `faction_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `faction_range` decimal(4,0) DEFAULT NULL,
  `isUndead` int(11) DEFAULT '0',
  `absorb_level` decimal(2,0) DEFAULT '0',
  `absorb_type` enum('FULL_PARTY','LAST_HIT','PARTY_ONE_RANDOM') COLLATE utf8_bin NOT NULL DEFAULT 'LAST_HIT',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of `custom_npc`
-- ----------------------------
INSERT INTO `custom_npc` VALUES ('51', '30767', 'Maria', '1', 'Npc Buffer', '1', 'Monster2.queen_of_cat', '8.00', '22.00', '70', 'etc', 'L2NpcBuffer', '40', '3892', '1567', '23.00', '1.00', '40', '40', '40', '40', '40', '40', '0', '0', '2314', '2341', '324', '234', '234', '0', '333', '0', '0', '0', '65', '123', null, '0', '0', '0', 'LAST_HIT');
INSERT INTO `custom_npc` VALUES ('52', '30767', 'Agent Guy', '1', 'Hitman Manager', '1', 'Monster2.queen_of_cat', '8.00', '22.00', '70', 'etc', 'L2Hitman', '40', '3892', '1567', '23.00', '1.00', '40', '40', '40', '40', '40', '40', '0', '0', '2314', '2341', '324', '234', '234', '0', '333', '0', '0', '0', '65', '123', null, '0', '0', '0', 'LAST_HIT');
INSERT INTO `custom_npc` VALUES ('54', '30767', 'Marion', '1', 'Faction Manager', '1', 'Monster2.queen_of_cat', '8.00', '22.00', '70', 'etc', 'L2Faction', '40', '3892', '1567', '23.00', '1.00', '40', '40', '40', '40', '40', '40', '0', '0', '2314', '2341', '324', '234', '234', '0', '333', '0', '0', '0', '65', '123', null, '0', '0', '0', 'LAST_HIT');
INSERT INTO `custom_npc` VALUES ('25846', '30314', 'Raut', '1', 'Donate Info', '1', 'NPC.a_traderB_MHuman', '8.00', '25.00', '99', 'male', 'L2Npc', '40', '999', '40', '40.00', '40.00', '999', '999', '999', '999', '999', '999', '40', '40', '40', '999', '40', '999', '40', '0', '40', '0', '0', '0', '80', '120', null, '0', '0', '0', 'LAST_HIT');
INSERT INTO `custom_npc` VALUES ('50007', '31324', 'Andromeda', '1', 'Wedding Manager', '1', 'NPC.a_casino_FDarkElf', '8.00', '23.00', '70', 'female', 'L2WeddingManager', '40', '3862', '1493', '11.85', '2.78', '40', '43', '30', '21', '20', '10', '0', '0', '1314', '470', '780', '382', '278', '0', '333', '316', '0', '0', '55', '132', null, '0', '1', '0', 'LAST_HIT');
INSERT INTO `custom_npc` VALUES ('70004', '31309', 'Leon', '1', 'CTF Event Manager', '1', 'NPC.a_traderD_Mhuman', '8.00', '25.30', '70', 'male', 'L2Npc', '40', '2444', '2444', '0.00', '0.00', '10', '10', '10', '10', '10', '10', '0', '0', '500', '500', '500', '500', '278', '0', '333', '9376', '0', '0', '30', '120', null, '0', '0', '0', 'LAST_HIT');
INSERT INTO `custom_npc` VALUES ('70005', '31309', 'Chris', '1', 'Account Manager', '1', 'NPC.a_traderD_Mhuman', '8.00', '25.30', '70', 'male', 'L2AccountManager', '40', '2444', '2444', '0.00', '0.00', '10', '10', '10', '10', '10', '10', '0', '0', '500', '500', '500', '500', '278', '0', '333', '9376', '0', '0', '30', '120', null, '0', '0', '0', 'LAST_HIT');
INSERT INTO `custom_npc` VALUES ('70006', '31309', 'Raul', '1', 'Vote Manager', '1', 'NPC.a_traderD_Mhuman', '8.00', '25.30', '70', 'male', 'L2VoteManager', '40', '2444', '2444', '0.00', '0.00', '10', '10', '10', '10', '10', '10', '0', '0', '500', '500', '500', '500', '278', '0', '333', '9376', '0', '0', '30', '120', null, '0', '0', '0', 'LAST_HIT');
INSERT INTO `custom_npc` VALUES ('70007', '31309', 'Paul', '1', 'Casino', '1', 'NPC.a_traderD_Mhuman', '8.00', '25.30', '70', 'male', 'L2Casino', '40', '2444', '2444', '0.00', '0.00', '10', '10', '10', '10', '10', '10', '0', '0', '500', '500', '500', '500', '278', '0', '333', '9376', '0', '0', '30', '120', '', '0', '0', '0', 'LAST_HIT');
INSERT INTO `custom_npc` VALUES ('70008', '31309', 'Zaon', '1', 'Donate Manager', '1', 'NPC.a_traderD_Mhuman', '8.00', '25.30', '70', 'male', 'L2Donate', '40', '2444', '2444', '0.00', '0.00', '10', '10', '10', '10', '10', '10', '0', '0', '500', '500', '500', '500', '278', '0', '333', '9376', '0', '0', '30', '120', '', '0', '0', '0', 'LAST_HIT');
INSERT INTO `custom_npc` VALUES ('70009', '31309', 'Philip', '1', 'Boss Info', '1', 'NPC.a_traderD_Mhuman', '8.00', '25.30', '70', 'male', 'L2BossSpawn', '40', '2444', '2444', '0.00', '0.00', '10', '10', '10', '10', '10', '10', '0', '0', '500', '500', '500', '500', '278', '0', '333', '9376', '0', '0', '30', '120', '', '0', '0', '0', 'LAST_HIT');
INSERT INTO `custom_npc` VALUES ('70010', '31309', 'Aioria', '1', 'TvT Manager', '1', 'NPC.a_traderD_Mhuman', '8.00', '25.30', '70', 'male', 'L2Npc', '40', '2444', '2444', '0.00', '0.00', '10', '10', '10', '10', '10', '10', '0', '0', '500', '500', '500', '500', '278', '0', '333', '9376', '0', '0', '30', '120', '', '0', '0', '0', 'LAST_HIT');
INSERT INTO `custom_npc` VALUES ('70011', '31309', 'Lee', '1', 'Noble Manager', '1', 'NPC.a_traderD_Mhuman', '8.00', '25.30', '70', 'male', 'L2CharNobles', '40', '2444', '2444', '0.00', '0.00', '10', '10', '10', '10', '10', '10', '0', '0', '500', '500', '500', '500', '278', '0', '333', '9376', '0', '0', '30', '120', '', '0', '0', '0', 'LAST_HIT');
INSERT INTO `custom_npc` VALUES ('70012', '31309', 'Event Manager', '1', 'Raid Event', '1', 'NPC.a_traderD_Mhuman', '8.00', '25.30', '70', 'male', 'L2EventManager', '40', '2444', '2444', '0.00', '0.00', '10', '10', '10', '10', '10', '10', '0', '0', '500', '500', '500', '500', '278', '0', '333', '9376', '0', '0', '30', '120', '', '0', '0', '0', 'LAST_HIT');
INSERT INTO `custom_npc` VALUES ('70013', '30767', 'Antonia', '1', 'Protector', '1', 'Monster2.queen_of_cat', '8.00', '22.00', '70', 'etc', 'L2Protector', '40', '3892', '1567', '23.00', '1.00', '40', '40', '40', '40', '40', '40', '0', '0', '2314', '2341', '324', '234', '234', '0', '333', '0', '0', '0', '65', '123', null, '0', '0', '0', 'LAST_HIT');