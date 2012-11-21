SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `characters`
-- ----------------------------
DROP TABLE IF EXISTS `characters`;
CREATE TABLE `characters` (
  `account_name` varchar(45) DEFAULT NULL,
  `obj_Id` decimal(11,0) NOT NULL DEFAULT '0',
  `char_name` varchar(35) NOT NULL,
  `level` decimal(11,0) DEFAULT NULL,
  `maxHp` decimal(11,0) DEFAULT NULL,
  `curHp` decimal(18,0) DEFAULT NULL,
  `maxCp` decimal(11,0) DEFAULT NULL,
  `curCp` decimal(18,0) DEFAULT NULL,
  `maxMp` decimal(11,0) DEFAULT NULL,
  `curMp` decimal(18,0) DEFAULT NULL,
  `acc` decimal(11,0) DEFAULT NULL,
  `crit` decimal(10,0) DEFAULT NULL,
  `evasion` decimal(11,0) DEFAULT NULL,
  `mAtk` decimal(11,0) DEFAULT NULL,
  `mDef` decimal(11,0) DEFAULT NULL,
  `mSpd` decimal(11,0) DEFAULT NULL,
  `pAtk` decimal(11,0) DEFAULT NULL,
  `pDef` decimal(11,0) DEFAULT NULL,
  `pSpd` decimal(11,0) DEFAULT NULL,
  `runSpd` decimal(11,0) DEFAULT NULL,
  `walkSpd` decimal(11,0) DEFAULT NULL,
  `str` decimal(11,0) DEFAULT NULL,
  `con` decimal(11,0) DEFAULT NULL,
  `dex` decimal(11,0) DEFAULT NULL,
  `_int` decimal(11,0) DEFAULT NULL,
  `men` decimal(11,0) DEFAULT NULL,
  `wit` decimal(11,0) DEFAULT NULL,
  `face` decimal(11,0) DEFAULT NULL,
  `hairStyle` decimal(11,0) DEFAULT NULL,
  `hairColor` decimal(11,0) DEFAULT NULL,
  `sex` decimal(11,0) DEFAULT NULL,
  `heading` decimal(11,0) DEFAULT NULL,
  `x` decimal(11,0) DEFAULT NULL,
  `y` decimal(11,0) DEFAULT NULL,
  `z` decimal(11,0) DEFAULT NULL,
  `movement_multiplier` decimal(9,8) DEFAULT NULL,
  `attack_speed_multiplier` decimal(10,9) DEFAULT NULL,
  `colRad` decimal(10,3) DEFAULT NULL,
  `colHeight` decimal(10,3) DEFAULT NULL,
  `exp` decimal(20,0) DEFAULT NULL,
  `expBeforeDeath` decimal(20,0) DEFAULT '0',
  `sp` decimal(11,0) DEFAULT NULL,
  `karma` decimal(11,0) DEFAULT NULL,
  `pvpkills` decimal(11,0) DEFAULT NULL,
  `pkkills` decimal(11,0) DEFAULT NULL,
  `clanid` decimal(11,0) DEFAULT NULL,
  `maxload` decimal(11,0) DEFAULT NULL,
  `race` decimal(11,0) DEFAULT NULL,
  `classid` decimal(11,0) DEFAULT NULL,
  `base_class` int(2) NOT NULL DEFAULT '0',
  `deletetime` decimal(20,0) DEFAULT NULL,
  `cancraft` decimal(11,0) DEFAULT NULL,
  `title` varchar(16) DEFAULT NULL,
  `rec_have` int(3) NOT NULL DEFAULT '0',
  `rec_left` int(3) NOT NULL DEFAULT '0',
  `accesslevel` decimal(4,0) DEFAULT NULL,
  `online` decimal(1,0) DEFAULT NULL,
  `onlinetime` decimal(20,0) DEFAULT NULL,
  `char_slot` decimal(1,0) DEFAULT NULL,
  `newbie` decimal(1,0) DEFAULT '1',
  `lastAccess` decimal(20,0) DEFAULT NULL,
  `clan_privs` int(11) DEFAULT '0',
  `wantspeace` decimal(1,0) DEFAULT '0',
  `isin7sdungeon` decimal(1,0) NOT NULL DEFAULT '0',
  `in_jail` decimal(1,0) DEFAULT '0',
  `jail_timer` decimal(20,0) DEFAULT '0',
  `power_grade` decimal(11,0) DEFAULT NULL,
  `nobless` decimal(1,0) NOT NULL DEFAULT '0',
  `subpledge` int(1) NOT NULL DEFAULT '0',
  `last_recom_date` decimal(20,0) NOT NULL DEFAULT '0',
  `lvl_joined_academy` int(1) NOT NULL DEFAULT '0',
  `apprentice` int(1) NOT NULL DEFAULT '0',
  `sponsor` int(1) NOT NULL DEFAULT '0',
  `varka_ketra_ally` int(1) NOT NULL DEFAULT '0',
  `clan_join_expiry_time` decimal(20,0) NOT NULL DEFAULT '0',
  `clan_create_expiry_time` decimal(20,0) NOT NULL DEFAULT '0',
  `death_penalty_level` int(2) NOT NULL DEFAULT '0',
  `hero` decimal(1,0) DEFAULT '0',
  `donator` decimal(1,0) DEFAULT '0',
  `chatban_timer` decimal(20,0) DEFAULT '0',
  `chatban_reason` varchar(255) NOT NULL DEFAULT '',
  `chat_filter_count` int(1) DEFAULT '0',
  `good` int(1) DEFAULT '0',
  `evil` int(1) DEFAULT '0',
  `hitman_target` int(16) NOT NULL DEFAULT '0',
  `email` varchar(100) DEFAULT NULL,
  `emailcode` int(10) unsigned DEFAULT '0',
  `hasSubEmail` int(10) unsigned DEFAULT '0',
  `answer` varchar(100) DEFAULT NULL,
  `secCode` int(10) unsigned DEFAULT '0',
  `emailchangecode` int(10) unsigned DEFAULT '0',
  `hasSubSec` int(10) unsigned DEFAULT '0',
  `lastVoteHopzone` bigint(20) unsigned DEFAULT NULL,
  `lastVoteTopzone` bigint(20) unsigned DEFAULT NULL,
  `hasVotedHop` int(10) unsigned DEFAULT '0',
  `hasVotedTop` int(10) unsigned DEFAULT '0',
  `monthVotes` int(10) unsigned DEFAULT '0',
  `totalVotes` int(10) unsigned DEFAULT '0',
  `tries` int(10) unsigned DEFAULT '3',
  PRIMARY KEY (`obj_Id`),
  KEY `clanid` (`clanid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of characters
-- ----------------------------
INSERT INTO `characters` VALUES ('admin', '268478914', 'Administrator', '80', '6399', '6399', '5119', '5119', '2208', '2208', '33', '40', '33', '3', '47', '213', '4', '72', '330', '126', '88', '40', '43', '30', '21', '25', '11', '1', '4', '2', '0', '13800', '-71527', '258186', '-3109', '1.00000000', '1.000000000', '9.000', '23.000', '4200000000', '0', '0', '0', '0', '0', '0', '108674', '0', '89', '89', '0', '0', 'l2jhellas', '0', '9', '100', '0', '26956', null, '1', '1353337050921', '0', '0', '0', '0', '0', '0', '0', '0', '1353324267322', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '', '0', '0', '0', '0', null, '0', '0', null, '0', '0', '0');
