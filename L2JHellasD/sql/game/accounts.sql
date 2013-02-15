SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `accounts`
-- ----------------------------
DROP TABLE IF EXISTS `accounts`;

CREATE TABLE `accounts` (
  `login` varchar(45) COLLATE utf8_bin NOT NULL DEFAULT '',
  `password` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `lastactive` decimal(20,0) DEFAULT NULL,
  `access_level` int(11) DEFAULT NULL,
  `lastIP` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `lastServer` int(4) DEFAULT '1',
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
