INSERT INTO custom_npc
   (`id`, `idTemplate`, `name`, `serverSideName`, `title`, `serverSideTitle`, `class`, `collision_radius`, `collision_height`, `level`, `sex`, `type`, `attackrange`, `hp`, `mp`, `hpreg`, `mpreg`, `str`, `con`, `dex`, `int`, `wit`, `men`, `exp`, `sp`, `patk`, `pdef`, `matk`, `mdef`, `atkspd`, `aggro`, `matkspd`, `rhand`, `lhand`, `armor`, `walkspd`, `runspd`, `faction_id`, `faction_range`, `isUndead`, `absorb_level`, `absorb_type`)
VALUES
	('70005', '31309', 'Chris', '1', 'Account Manager', '1', 'NPC.a_traderD_Mhuman', '8.00', '25.30', '70', 'male', 'L2AccountManager', '40', '2444', '2444', '0.00', '0.00', '10', '10', '10', '10', '10', '10', '0', '0', '500', '500', '500', '500', '278', '0', '333', '9376', '0', '0', '30', '120', 'null', '0', '0', '0', 'LAST_HIT');

ALTER TABLE `characters` ADD `email` varchar(100) DEFAULT NULL;
ALTER TABLE `characters` ADD `emailcode` INT UNSIGNED DEFAULT 0;
ALTER TABLE `characters` ADD `hasSubEmail` INT UNSIGNED DEFAULT 0;
ALTER TABLE `characters` ADD `answer` varchar(100) DEFAULT NULL;
ALTER TABLE `characters` ADD `secCode` INT UNSIGNED DEFAULT 0;
ALTER TABLE `characters` ADD `emailchangecode` INT UNSIGNED DEFAULT 0;
ALTER TABLE `characters` ADD `hasSubSec` INT UNSIGNED DEFAULT 0;