SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `mails`
-- ----------------------------
DROP TABLE IF EXISTS `mails`;

CREATE TABLE `mails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from` text,
  `to` text,
  `title` text,
  `message` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPRESSED COMMENT='L2jHellas Table';