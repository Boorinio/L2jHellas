SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `questions`
-- ----------------------------

CREATE TABLE `questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` text,
  `answer1` text,
  `answer2` text,
  `answer3` text,
  `right_answer` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPRESSED COMMENT='L2jHellas Table';

INSERT INTO `questions` VALUES 
  ('0','Whats the name of server admin?','Tom','Paul','John','Paul'),
  ('0','What MaxCheaters is?','forum','animal','','forum'),
  ('0','Is l2jhellas awesome?','Yes','No','Maybe','Yes'),
  ('0','Is quiz event cooool?','Yes','No','Whatever','Whatever');