SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `merchant_areas_list`
-- ----------------------------
DROP TABLE IF EXISTS `merchant_areas_list`;

CREATE TABLE `merchant_areas_list` (
  `merchant_area_id` int(10) unsigned NOT NULL DEFAULT '0',
  `merchant_area_name` varchar(25) NOT NULL DEFAULT '',
  `tax` double(3,2) unsigned NOT NULL DEFAULT '0.00',
  `Chaotic` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`merchant_area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPRESSED COMMENT='L2jHellas Table';

-- ----------------------------
-- Records of `merchant_areas_list`
-- ----------------------------
INSERT INTO `merchant_areas_list` VALUES 
(1,'Starter Town',0.15,0),
(2,'West of Alter of Rights',0.50,1),
(3,'Gludin',0.20,0),
(4,'Gludio',0.20,0),
(5,'South Wastelands',0.50,1),
(6,'Dion',0.20,0),
(7,'Floran',0.50,1),
(8,'Hunters Village',0.30,0),
(9,'Giran',0.10,0),
(10,'Oren',0.15,0),
(11,'Ivory Tower',0.15,0),
(12,'Harden\'s Ac.',0.20,0),
(13,'Aden',0.20,0),
(14,'Castle',0.0,0);