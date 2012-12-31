SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `walker_routes`
-- ----------------------------
DROP TABLE IF EXISTS `walker_routes`;

CREATE TABLE `walker_routes` (
  `route_id` int(11) NOT NULL DEFAULT '0',
  `npc_id` int(11) NOT NULL DEFAULT '0',
  `move_point` int(9) NOT NULL,
  `chatText` varchar(255) DEFAULT NULL,
  `move_x` int(9) NOT NULL DEFAULT '0',
  `move_y` int(9) NOT NULL DEFAULT '0',
  `move_z` int(9) NOT NULL DEFAULT '0',
  `delay` int(9) NOT NULL DEFAULT '0',
  `running` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`route_id`,`npc_id`,`move_point`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of `walker_routes`
-- ----------------------------
INSERT INTO `walker_routes` VALUES ('1', '25846', '1', 'Remember to Vote for us every 12 hours!', '45989', '186690', '-3486', '150', '1');
INSERT INTO `walker_routes` VALUES ('1', '25846', '2', null, '46503', '187469', '-3486', '150', '1');
INSERT INTO `walker_routes` VALUES ('1', '25846', '3', 'For Donate Info visit our Site www.l2jhellas.eu', '47780', '186886', '-3486', '150', '1');
INSERT INTO `walker_routes` VALUES ('1', '25846', '4', null, '47363', '186027', '-3486', '150', '1');
