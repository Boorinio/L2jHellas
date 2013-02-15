SET FOREIGN_KEY_CHECKS=0;
-- ---------------------------
-- Table structure for `character_macroses`
-- ---------------------------
DROP TABLE IF EXISTS `character_macroses`;

CREATE TABLE `character_macroses` (
  `char_obj_id` int(11) NOT NULL DEFAULT '0',
  `id` int(11) NOT NULL DEFAULT '0',
  `icon` int(11) DEFAULT NULL,
  `name` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `descr` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  `acronym` varchar(4) COLLATE utf8_bin DEFAULT NULL,
  `commands` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`char_obj_id`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='L2jHellas Table';