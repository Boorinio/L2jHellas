SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `achievements` and `heroes_diary`
-- ----------------------------
DROP TABLE IF EXISTS `heroes_diary`;

CREATE TABLE `heroes_diary` (
  `char_id` int(10) unsigned NOT NULL,
  `time` bigint(13) unsigned NOT NULL DEFAULT '0',
  `action` tinyint(2) unsigned NOT NULL DEFAULT '0',
  `param` int(11) unsigned NOT NULL DEFAULT '0',
  KEY `char_id` (`char_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPRESSED COMMENT='L2jHellas Table';

DROP TABLE IF EXISTS `achievements`;

CREATE TABLE `achievements` (
  `owner_id` int(11) NOT NULL DEFAULT '0',
  `a1` int(11) DEFAULT '0',
  `a2` int(11) DEFAULT '0',
  `a3` int(11) DEFAULT '0',
  `a4` int(11) DEFAULT '0',
  `a5` int(11) DEFAULT '0',
  `a6` int(11) DEFAULT '0',
  `a7` int(11) DEFAULT '0',
  `a8` int(11) DEFAULT '0',
  `a9` int(11) DEFAULT '0',
  `a10` int(11) DEFAULT '0',
  `a11` int(11) DEFAULT '0',
  `a12` int(11) DEFAULT '0',
  `a13` int(11) DEFAULT '0',
  `a14` int(11) DEFAULT '0',
  `a15` int(11) DEFAULT '0',
  `a16` int(11) DEFAULT '0',
  `a17` int(11) DEFAULT '0',
  `a18` int(11) DEFAULT '0',
  `a19` int(11) DEFAULT '0',
  `a20` int(11) DEFAULT '0',
  `a21` int(11) DEFAULT '0',
  `a22` int(11) DEFAULT '0',
  `a23` int(11) DEFAULT '0',
  `a24` int(11) DEFAULT '0',
  PRIMARY KEY (`owner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPRESSED COMMENT='L2jHellas Table';

