SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `global_tasks`
-- ----------------------------
DROP TABLE IF EXISTS `global_tasks`;

CREATE TABLE `global_tasks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
  `type` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
  `last_activation` decimal(20,0) NOT NULL DEFAULT '0',
  `param1` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '',
  `param2` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '',
  `param3` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;