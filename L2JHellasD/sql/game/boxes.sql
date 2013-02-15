SET FOREIGN_KEY_CHECKS=0;
-- ---------------------------
-- Table structure for table `boxes`
-- ---------------------------
DROP TABLE IF EXISTS `boxes`;

CREATE TABLE `boxes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `spawn` decimal(11,0) DEFAULT NULL,
  `npcid` decimal(11,0) DEFAULT NULL,
  `drawer` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `itemid` decimal(11,0) DEFAULT NULL,
  `name` varchar(32) COLLATE utf8_bin DEFAULT '',
  `count` decimal(11,0) DEFAULT NULL,
  `enchant` decimal(2,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='L2jHellas Table';