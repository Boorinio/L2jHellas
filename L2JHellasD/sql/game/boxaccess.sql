SET FOREIGN_KEY_CHECKS=0;
-- ---------------------------
-- Table structure for table `boxaccess`
-- ---------------------------
DROP TABLE IF EXISTS `boxaccess`;

CREATE TABLE `boxaccess` (
  `spawn` decimal(11,0) DEFAULT NULL,
  `charname` varchar(32) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;