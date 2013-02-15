SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `questions`
-- ----------------------------
DROP TABLE IF EXISTS `questions`;

CREATE TABLE `questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` text COLLATE utf8_bin,
  `answer1` text COLLATE utf8_bin,
  `answer2` text COLLATE utf8_bin,
  `answer3` text COLLATE utf8_bin,
  `right_answer` text COLLATE utf8_bin,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='L2jHellas Table';

INSERT INTO `questions` VALUES 
  ('0','Whats the name of server admin?','Tom','Paul','John','Paul'),
  ('0','What MaxCheaters is?','website','animal','','website'),
  ('0','Is Pauler awesome?','Yes','No','Maybe','Yes'),
  ('0','Is quiz event cooool?','Yes','No','Whatever','Whatever');