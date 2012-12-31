SET FOREIGN_KEY_CHECKS=0;
-- ---------------------------
-- Table structure for `character_quests`
-- ---------------------------
DROP TABLE IF EXISTS `character_quests`;

CREATE TABLE `character_quests` (
  `char_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `var` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '',
  `value` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `class_index` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`char_id`,`name`,`var`,`class_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;