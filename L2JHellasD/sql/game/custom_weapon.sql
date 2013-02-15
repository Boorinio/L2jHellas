SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `custom_weapon`
-- ----------------------------
DROP TABLE IF EXISTS `custom_weapon`;

CREATE TABLE `custom_weapon` (
  `item_id` decimal(11,0) NOT NULL DEFAULT '0',
  `name` varchar(70) COLLATE utf8_bin DEFAULT NULL,
  `bodypart` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `crystallizable` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `weight` decimal(4,0) DEFAULT NULL,
  `soulshots` decimal(2,0) DEFAULT NULL,
  `spiritshots` decimal(1,0) DEFAULT NULL,
  `material` varchar(11) COLLATE utf8_bin DEFAULT NULL,
  `crystal_type` varchar(4) COLLATE utf8_bin DEFAULT NULL,
  `p_dam` decimal(5,0) DEFAULT NULL,
  `rnd_dam` decimal(2,0) DEFAULT NULL,
  `weaponType` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  `critical` decimal(2,0) DEFAULT NULL,
  `hit_modify` decimal(6,5) DEFAULT NULL,
  `avoid_modify` decimal(2,0) DEFAULT NULL,
  `shield_def` decimal(3,0) DEFAULT NULL,
  `shield_def_rate` decimal(2,0) DEFAULT NULL,
  `atk_speed` decimal(3,0) DEFAULT NULL,
  `mp_consume` decimal(2,0) DEFAULT NULL,
  `m_dam` decimal(3,0) DEFAULT NULL,
  `duration` decimal(3,0) DEFAULT NULL,
  `price` decimal(11,0) DEFAULT NULL,
  `crystal_count` int(4) DEFAULT NULL,
  `sellable` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `dropable` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `destroyable` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `tradeable` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `item_skill_id` decimal(11,0) NOT NULL DEFAULT '0',
  `item_skill_lvl` decimal(11,0) NOT NULL DEFAULT '0',
  `enchant4_skill_id` decimal(11,0) NOT NULL DEFAULT '0',
  `enchant4_skill_lvl` decimal(11,0) NOT NULL DEFAULT '0',
  `onCast_skill_id` decimal(11,0) NOT NULL DEFAULT '0',
  `onCast_skill_lvl` decimal(11,0) NOT NULL DEFAULT '0',
  `onCast_skill_chance` decimal(11,0) NOT NULL DEFAULT '0',
  `onCrit_skill_id` decimal(11,0) NOT NULL DEFAULT '0',
  `onCrit_skill_lvl` decimal(11,0) NOT NULL DEFAULT '0',
  `onCrit_skill_chance` decimal(11,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='L2jHellas Table';