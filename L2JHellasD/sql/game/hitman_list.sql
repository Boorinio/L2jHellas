SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `hitman_list`
-- ----------------------------
DROP TABLE IF EXISTS `hitman_list`;

CREATE TABLE `hitman_list` (
  `targetId` int(16) NOT NULL DEFAULT '0',
  `clientId` int(16) NOT NULL DEFAULT '0',
  `target_name` varchar(24) COLLATE utf8_bin NOT NULL DEFAULT '',
  `bounty` int(30) NOT NULL DEFAULT '0',
  `pending_delete` int(16) NOT NULL DEFAULT '0',
  PRIMARY KEY (`targetId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='L2jHellas Table';