SET FOREIGN_KEY_CHECKS=0;
-- ---------------------------
-- Table structure for `auction_bid`
-- ---------------------------
DROP TABLE IF EXISTS `auction_bid`;

CREATE TABLE `auction_bid` (
  `id` int(11) NOT NULL DEFAULT '0',
  `auctionId` int(11) NOT NULL DEFAULT '0',
  `bidderId` int(11) NOT NULL DEFAULT '0',
  `bidderName` varchar(50) COLLATE utf8_bin NOT NULL,
  `clan_name` varchar(50) COLLATE utf8_bin NOT NULL,
  `maxBid` int(11) NOT NULL DEFAULT '0',
  `time_bid` decimal(20,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`auctionId`,`bidderId`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='L2jHellas Table';