SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `mods_couples`
-- ----------------------------
DROP TABLE IF EXISTS `mods_wedding`;

CREATE TABLE `mods_wedding` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player1Id` int(11) NOT NULL DEFAULT '0',
  `player2Id` int(11) NOT NULL DEFAULT '0',
  `married` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `affianceDate` decimal(20,0) DEFAULT '0',
  `weddingDate` decimal(20,0) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='L2jHellas Table';