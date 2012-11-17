CREATE TABLE `hitman_list` (
  `targetId` int(16) NOT NULL default '0',
  `clientId` int(16) NOT NULL default '0',
  `target_name` varchar(24) NOT NULL default '',
  `bounty` int(30) NOT NULL default '0',
  `pending_delete` int(16) NOT NULL default '0',
  PRIMARY KEY  (`targetId`)
);