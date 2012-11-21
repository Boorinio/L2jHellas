SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `account_premium`
-- ----------------------------
DROP TABLE IF EXISTS `account_premium`;
CREATE TABLE `account_premium` (
  `account_name` varchar(45) NOT NULL DEFAULT '',
  `premium_service` int(1) NOT NULL DEFAULT '1',
  `enddate` decimal(20,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`account_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `characters` ADD `lastVoteHopzone` BIGINT UNSIGNED DEFAULT NULL;
ALTER TABLE `characters` ADD `lastVoteTopzone` BIGINT UNSIGNED DEFAULT NULL;
ALTER TABLE `characters` ADD `hasVotedHop` INT UNSIGNED DEFAULT 0;
ALTER TABLE `characters` ADD `hasVotedTop` INT UNSIGNED DEFAULT 0;
ALTER TABLE `characters` ADD `monthVotes` INT UNSIGNED DEFAULT 0;
ALTER TABLE `characters` ADD `totalVotes` INT UNSIGNED DEFAULT 0;
ALTER TABLE `characters` ADD `tries` INT UNSIGNED DEFAULT 3;

INSERT INTO custom_npc
   (`id`, `idTemplate`, `name`, `serverSideName`, `title`, `serverSideTitle`, `class`, `collision_radius`, `collision_height`, `level`, `sex`, `type`, `attackrange`, `hp`, `mp`, `hpreg`, `mpreg`, `str`, `con`, `dex`, `int`, `wit`, `men`, `exp`, `sp`, `patk`, `pdef`, `matk`, `mdef`, `atkspd`, `aggro`, `matkspd`, `rhand`, `lhand`, `armor`, `walkspd`, `runspd`, `faction_id`, `faction_range`, `isUndead`, `absorb_level`, `absorb_type`)
VALUES
	('70006', '31309', 'Raul', '1', 'Vote Manager', '1', 'NPC.a_traderD_Mhuman', '8.00', '25.30', '70', 'male', 'L2VoteManager', '40', '2444', '2444', '0.00', '0.00', '10', '10', '10', '10', '10', '10', '0', '0', '500', '500', '500', '500', '278', '0', '333', '9376', '0', '0', '30', '120', 'null', '0', '0', '0', 'LAST_HIT');