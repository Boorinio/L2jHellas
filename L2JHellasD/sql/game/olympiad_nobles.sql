SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `olympiad_nobles`
-- ----------------------------
DROP TABLE IF EXISTS `olympiad_nobles`;

CREATE TABLE `olympiad_nobles` (
  `char_id` decimal(11,0) NOT NULL DEFAULT '0',
  `class_id` decimal(3,0) NOT NULL DEFAULT '0',
  `char_name` varchar(45) COLLATE utf8_bin NOT NULL DEFAULT '',
  `olympiad_points` decimal(10,0) NOT NULL DEFAULT '0',
  `competitions_done` decimal(3,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`char_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;