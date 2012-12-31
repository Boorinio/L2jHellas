SET FOREIGN_KEY_CHECKS=0;
-- ---------------------------
-- Table structure for `clan_notices`
-- ---------------------------
DROP TABLE IF EXISTS `clan_notices`;

CREATE TABLE `clan_notices` (
  `clan_id` int(32) NOT NULL,
  `notice` varchar(512) COLLATE utf8_bin NOT NULL,
  `enabled` varchar(5) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`clan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;