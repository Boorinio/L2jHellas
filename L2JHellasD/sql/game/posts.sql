SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `posts`
-- ----------------------------
DROP TABLE IF EXISTS `posts`;

CREATE TABLE `posts` (
  `post_id` int(8) NOT NULL DEFAULT '0',
  `post_owner_name` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `post_ownerid` int(8) NOT NULL DEFAULT '0',
  `post_date` decimal(20,0) NOT NULL DEFAULT '0',
  `post_topic_id` int(8) NOT NULL DEFAULT '0',
  `post_forum_id` int(8) NOT NULL DEFAULT '0',
  `post_txt` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='L2jHellas Table';