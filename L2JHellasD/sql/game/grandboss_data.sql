SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `grandboss_data`
-- ----------------------------
DROP TABLE IF EXISTS `grandboss_data`;

CREATE TABLE `grandboss_data` (
  `boss_id` int(11) NOT NULL DEFAULT '0',
  `loc_x` int(11) NOT NULL DEFAULT '0',
  `loc_y` int(11) NOT NULL DEFAULT '0',
  `loc_z` int(11) NOT NULL DEFAULT '0',
  `heading` int(11) NOT NULL DEFAULT '0',
  `respawn_time` bigint(20) NOT NULL DEFAULT '0',
  `currentHP` decimal(8,0) DEFAULT NULL,
  `currentMP` decimal(8,0) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`boss_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPRESSED COMMENT='L2jHellas Table';

-- ----------------------------
-- Records of `grandboss_data`
-- ----------------------------
INSERT IGNORE INTO `grandboss_data` VALUES
(29019, 185708,114298,-8221,32768, 0, 13090000, 22197, 0),-- Antharas
(29020, 115213,16623,10080,41740, 0, 790857, 3347, 0),-- Baium
(29001, -21610, 181594, -5734, 0, 0, 229898, 667, 0), -- Queen Ant
(29006, 17726, 108915, -6480, 0, 0, 162561, 575, 0), -- Core
(29014, 43728, 17220, -4342, 10126, 0, 325124, 1660, 0),-- Orfen
(29022, 55312, 219168, -3223, 0, 0, 858518, 1975, 0),-- Zaken
(29028, -105200,-253104,-15264,0, 0, 16660000, 22197, 0),-- Valakas
(29065, -123348, -248881, -15537, 44732, 0, 1532678, 4255, 0); -- Check this