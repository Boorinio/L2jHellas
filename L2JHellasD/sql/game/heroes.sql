SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `heroes`
-- ----------------------------
DROP TABLE IF EXISTS `heroes`;

CREATE TABLE `heroes` (
  `char_id` decimal(11,0) NOT NULL DEFAULT '0',
  `char_name` varchar(45) COLLATE utf8_bin NOT NULL DEFAULT '',
  `class_id` decimal(3,0) NOT NULL DEFAULT '0',
  `count` decimal(3,0) NOT NULL DEFAULT '0',
  `played` decimal(1,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`char_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;