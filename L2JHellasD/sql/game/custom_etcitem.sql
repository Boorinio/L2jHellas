SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `custom_etcitem`
-- ----------------------------
DROP TABLE IF EXISTS `custom_etcitem`;

CREATE TABLE `custom_etcitem` (
  `item_id` decimal(11,0) NOT NULL DEFAULT '0',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `crystallizable` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `item_type` varchar(12) COLLATE utf8_bin DEFAULT NULL,
  `weight` decimal(4,0) DEFAULT NULL,
  `consume_type` varchar(9) COLLATE utf8_bin DEFAULT NULL,
  `material` varchar(11) COLLATE utf8_bin DEFAULT NULL,
  `crystal_type` varchar(4) COLLATE utf8_bin DEFAULT NULL,
  `duration` decimal(3,0) DEFAULT NULL,
  `price` decimal(11,0) DEFAULT NULL,
  `crystal_count` int(4) DEFAULT NULL,
  `sellable` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `dropable` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `destroyable` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `tradeable` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `oldname` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '',
  `oldtype` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='L2jHellas Table';