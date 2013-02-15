SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `account_data`
-- ----------------------------
DROP TABLE IF EXISTS `account_data`;

CREATE TABLE `account_data` (
  `account_name` varchar(45) COLLATE utf8_bin NOT NULL DEFAULT '',
  `var` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '',
  `value` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`account_name`,`var`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;