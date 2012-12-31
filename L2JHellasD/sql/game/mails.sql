SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `mails`
-- ----------------------------
DROP TABLE IF EXISTS `mails`;

CREATE TABLE `mails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from` text COLLATE utf8_bin,
  `to` text COLLATE utf8_bin,
  `title` text COLLATE utf8_bin,
  `message` text COLLATE utf8_bin,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;