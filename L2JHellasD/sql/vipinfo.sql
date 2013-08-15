SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `vipinfo`
-- ----------------------------
DROP TABLE IF EXISTS `vipinfo`;

CREATE TABLE `vipinfo` (
  `teamID` int(11) NOT NULL DEFAULT '0',
  `endx` int(11) NOT NULL DEFAULT '0',
  `endy` int(11) NOT NULL DEFAULT '0',
  `endz` int(11) NOT NULL DEFAULT '0',
  `startx` int(11) NOT NULL DEFAULT '0',
  `starty` int(11) NOT NULL DEFAULT '0',
  `startz` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`teamID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPRESSED COMMENT='L2jHellas Table';

-- ----------------------------
-- Records of `vipinfo`
-- ----------------------------
-- VIP End x y z, Start x y z 
-- ----------------------------
INSERT INTO `vipinfo` VALUES 
('1', '-84583', '242788', '-3735', '-115354', '221134', '-3010'),
('2', '45714', '49703', '-3065', '55782', '81597', '-3610'),
('3', '11249', '16890', '-4667', '-22732', '12586', '-2996'),
('4', '-44737', '-113582', '-204', '15890', '-102649', '-2079'),
('5', '116047', '-179059', '-1026', '121145', '-215673', '-3571');
