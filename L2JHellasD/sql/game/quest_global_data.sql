SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for global quest data (i.e. quest data not particular to a player)
-- Note: We had considered using character_quests with char_id = 0 for this, but decided 
-- against it, primarily for aesthetic purposes, cleaningness of code, expectability, and
-- to keep char-related data purely as char-related data, global purely as global.
-- ----------------------------
DROP TABLE IF EXISTS `quest_global_data`;

CREATE TABLE `quest_global_data` (
  `quest_name` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '',
  `var` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '',
  `value` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`quest_name`,`var`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;