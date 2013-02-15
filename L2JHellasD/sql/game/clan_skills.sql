SET FOREIGN_KEY_CHECKS=0;
-- ---------------------------
-- Table structure for `clan_skills`
-- ---------------------------
DROP TABLE IF EXISTS `clan_skills`;

CREATE TABLE `clan_skills` (
  `clan_id` int(11) NOT NULL DEFAULT '0',
  `skill_id` int(11) NOT NULL DEFAULT '0',
  `skill_level` int(5) NOT NULL DEFAULT '0',
  `skill_name` varchar(26) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`clan_id`,`skill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='L2jHellas Table';