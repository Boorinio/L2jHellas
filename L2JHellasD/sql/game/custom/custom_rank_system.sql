-- INFO about the root`@`localhost for procedures you must add your host if its not local.

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `custom_pvp_system`
-- ----------------------------
DROP TABLE IF EXISTS `custom_pvp_system`;
CREATE TABLE `custom_pvp_system` (
`killer_id`  int(10) NOT NULL ,
`victim_id`  int(10) NOT NULL ,
`kills`  int(10) NOT NULL DEFAULT 0 ,
`kills_today`  int(10) NOT NULL DEFAULT 0 ,
`kills_legal`  int(10) NOT NULL DEFAULT 0 ,
`kills_today_legal`  int(10) NOT NULL DEFAULT 0 ,
`kill_time`  bigint(18) NOT NULL DEFAULT 0 ,
`rank_points`  bigint(18) NOT NULL DEFAULT 0 ,
`rank_points_today`  bigint(18) NOT NULL DEFAULT 0 ,
`war_kills_legal`  int(10) NOT NULL DEFAULT 0 ,
`war_kills`  int(10) NOT NULL DEFAULT 0 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `custom_pvp_system_characters_rank_rewards`
-- ----------------------------
DROP TABLE IF EXISTS `custom_pvp_system_characters_rank_rewards`;
CREATE TABLE `custom_pvp_system_characters_rank_rewards` (
`charId`  int(10) NOT NULL ,
`reward_id`  int(10) NOT NULL ,
`is_taken`  tinyint(1) NOT NULL DEFAULT 0 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `custom_pvp_system_rank_reward`
-- ----------------------------
DROP TABLE IF EXISTS `custom_pvp_system_rank_reward`;
CREATE TABLE `custom_pvp_system_rank_reward` (
`reward_id`  int(10) NOT NULL AUTO_INCREMENT ,
`item_id`  int(10) NOT NULL ,
`item_amount`  bigint(18) NOT NULL ,
`min_rank_points`  int(10) NOT NULL COMMENT 'Should be the same like in: ExternalConfig:RankPvpRankMinPoints' ,
PRIMARY KEY (`reward_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=43

;

-- ----------------------------
-- Records of custom_pvp_system_rank_reward
-- ----------------------------
BEGIN;
INSERT INTO `custom_pvp_system_rank_reward` VALUES ('1', '57', '50000', '1'), ('2', '57', '100000', '2'), ('3', '57', '150000', '5'), ('4', '57', '200000', '10'), ('5', '57', '250000', '20'), ('6', '57', '300000', '30'), ('7', '57', '400000', '50'), ('8', '57', '450000', '90'), ('9', '57', '500000', '120'), ('10', '57', '550000', '200'), ('11', '57', '600000', '400'), ('12', '57', '650000', '600'), ('13', '57', '50000', '900'), ('14', '57', '50000', '1200'), ('15', '57', '50000', '5000'), ('16', '57', '50000', '6000'), ('17', '57', '50000', '8000'), ('18', '57', '50000', '10000'), ('19', '57', '50000', '11111'), ('20', '57', '50000', '12222'), ('21', '57', '50000', '13333'), ('22', '57', '50000', '14444'), ('23', '57', '50000', '15555'), ('24', '57', '50000', '16666'), ('25', '57', '50000', '17777'), ('26', '57', '50000', '18888'), ('27', '57', '50000', '19999'), ('28', '57', '50000', '100000'), ('29', '57', '50000', '111111'), ('30', '57', '50000', '122222'), ('31', '57', '50000', '133333'), ('32', '57', '50000', '144444'), ('33', '57', '50000', '155555'), ('34', '57', '50000', '166666'), ('35', '57', '50000', '177777'), ('36', '57', '50000', '188888'), ('37', '57', '50000', '199999'), ('38', '57', '50000', '111111'), ('39', '57', '50000', '1111111'), ('40', '57', '50000', '1111222'), ('41', '57', '50000', '1666666'), ('42', '57', '50000', '1999999');
COMMIT;

-- ----------------------------
-- Procedure structure for `CPS_add`
-- ----------------------------
DROP PROCEDURE IF EXISTS `CPS_add`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `CPS_add`(IN `p_killer_id` int,IN `p_victim_id` int,IN `p_kill_time` bigint, IN `p_protection_time` bigint, IN `p_legal` int, IN `p_altt_enabled` int,  IN `p_is_war` int)
BEGIN

	/* Custom PvP Reward System (CPRS) */
	DECLARE c_kills INTEGER;
	DECLARE c_kills_today INTEGER;
	DECLARE c_kills_legal INTEGER;
	DECLARE c_kills_today_legal INTEGER;
	DECLARE c_kill_time BIGINT; 
	DECLARE v_rank_points BIGINT; /* victim total points, required to calculate points reward for killer, must be in result set */
	DECLARE v_rank_points_today BIGINT; /* victim total points, required to calculate points reward for killer, must be in result set */

	DECLARE c_war_kills_legal INTEGER;
	DECLARE c_war_kills INTEGER;

	/* get killer data */
	SELECT custom_pvp_system.kills, custom_pvp_system.kills_today, custom_pvp_system.kill_time, custom_pvp_system.kills_legal, custom_pvp_system.kills_today_legal, custom_pvp_system.war_kills_legal, custom_pvp_system.war_kills
	INTO c_kills, c_kills_today, c_kill_time, c_kills_legal, c_kills_today_legal, c_war_kills_legal, c_war_kills FROM custom_pvp_system
	WHERE killer_id = p_killer_id AND victim_id = p_victim_id;

	IF(c_kills is not null)THEN
		IF(p_kill_time > c_kill_time + p_protection_time)THEN

			SET c_kills_today = 1;
			SET c_kill_time = p_kill_time;

			IF(p_legal = 1)THEN
				SET c_kills_legal = c_kills_legal + 1;
				SET c_kills_today_legal = 1;
			END IF;

			IF(p_is_war = 1)THEN
				SET c_war_kills_legal = c_war_kills_legal + 1;
				SET c_war_kills = c_war_kills + 1;
			END IF;
			/* update query */
			UPDATE custom_pvp_system 
			SET kills=(kills+1), kills_today=1, kill_time=p_kill_time, rank_points_today=0, kills_legal=c_kills_legal, kills_today_legal=c_kills_today_legal, war_kills_legal=c_war_kills_legal, war_kills=c_war_kills
			WHERE killer_id = p_killer_id AND victim_id = p_victim_id;
			/* sets rank_points_today = 0 because it will be updated in CPS_add_rank_points() */
			/* sets kills_legal only here because it will give real kill times */
		ELSE

			SET c_kills_today = c_kills_today + 1;
			IF(p_legal = 1)THEN
				SET c_kills_today_legal = c_kills_today_legal + 1;
			END IF;

			IF(p_is_war = 1)THEN
				SET c_war_kills = c_war_kills + 1;
			END IF;

			/* update query */
			UPDATE custom_pvp_system 
			SET kills=(kills+1), kills_today=kills_today+1, kills_today_legal=c_kills_today_legal, war_kills=c_war_kills
			WHERE killer_id = p_killer_id AND victim_id = p_victim_id;

		END IF;
		
		SET c_kills = c_kills + 1;

	ELSE

		SET c_war_kills_legal = 0;
		SET c_war_kills = 0;

		/* insert query */
		IF(p_legal = 1)THEN
			IF(p_is_war = 1)THEN
				SET c_war_kills_legal = c_war_kills_legal + 1;
				SET c_war_kills = c_war_kills + 1;
			END IF;

			INSERT INTO custom_pvp_system (killer_id, victim_id, kills, kills_today, kill_time, rank_points, rank_points_today, kills_legal, kills_today_legal, war_kills_legal, war_kills)
			VALUES (p_killer_id, p_victim_id, 1, 1, p_kill_time, 0, 0, 1, 1, c_war_kills_legal, c_war_kills);
			SET c_kills_legal = 1;
			SET c_kills_today_legal = 1;
		ELSE
			IF(p_is_war = 1)THEN
				SET c_war_kills = c_war_kills + 1;
			END IF;

			INSERT INTO custom_pvp_system (killer_id, victim_id, kills, kills_today, kill_time, rank_points, rank_points_today, kills_legal, kills_today_legal, war_kills_legal, war_kills)
			VALUES (p_killer_id, p_victim_id, 1, 1, p_kill_time, 0, 0, 0, 0, c_war_kills_legal, c_war_kills);
			SET c_kills_legal = 0;
			SET c_kills_today_legal = 0;
		END IF;

		SET c_kills = 1;
		SET c_kills_today = 1;

	END IF;

	/* get victim total, daily rank points sum */
	SELECT sum(rank_points), sum(rank_points_today) INTO v_rank_points, v_rank_points_today FROM custom_pvp_system
	WHERE killer_id = p_victim_id;

	IF(v_rank_points is null)THEN
		SET v_rank_points = 0;
		SET v_rank_points_today = 0;
	END IF;

	/* Update characters table if p_altt_enabled */ /* Can be deleted, but not tested yet. */
	IF(p_altt_enabled = 1)THEN
		UPDATE characters SET pvpkills = c_kills_legal WHERE obj_Id = p_killer_id; 
	END IF;

	/* result set */
	SELECT c_kills as kills, c_kills_today as kills_today, c_kill_time as kill_time, v_rank_points as rank_points, v_rank_points_today as rank_points_today, c_kills_legal as kills_legal, c_kills_today_legal as kills_today_legal;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `CPS_add_rank_points`
-- ----------------------------
DROP PROCEDURE IF EXISTS `CPS_add_rank_points`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `CPS_add_rank_points`(IN `p_killer_id` int,IN `p_victim_id` int,IN `p_victim_kill_points` BIGINT)
BEGIN
	/* Custom PvP Reward System (CPRS) + Rank Points System */
	/* IMPORTANT: This method should be executed after CPRS_add(). */

	/* Points today are reset in CPRS_add() */
	/* update query */
	UPDATE custom_pvp_system
	SET rank_points=rank_points+p_victim_kill_points, rank_points_today=rank_points_today+p_victim_kill_points
	WHERE killer_id = p_killer_id AND victim_id = p_victim_id;

	SELECT sum(rank_points) as rank_points, sum(rank_points_today) as rank_points_today FROM custom_pvp_system
	WHERE killer_id = p_killer_id;


END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `CPS_add_rank_reward`
-- ----------------------------
DROP PROCEDURE IF EXISTS `CPS_add_rank_reward`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `CPS_add_rank_reward`(IN p_charId int, IN p_current_rank_points  int)
BEGIN
	/*
		Add retrived reward informations to custom_pvp_system_rank_reward.
	*/

	DECLARE c_reward_id INTEGER;
	DECLARE c_count INTEGER;
	SET c_reward_id = 0;


	SELECT reward_id, count(*) INTO c_reward_id, c_count FROM custom_pvp_system_rank_reward WHERE custom_pvp_system_rank_reward.min_rank_points <= p_current_rank_points
	AND reward_id NOT IN(SELECT reward_id FROM custom_pvp_system_characters_rank_rewards WHERE charId = p_charId AND is_taken = 1)
	LIMIT 1;


	WHILE (c_count > 0) DO
		BEGIN
			INSERT INTO custom_pvp_system_characters_rank_rewards (charId, reward_id, is_taken) VALUES (p_charId, c_reward_id, 1);
			
			SELECT reward_id, count(*) INTO c_reward_id, c_count FROM custom_pvp_system_rank_reward WHERE min_rank_points  <= p_current_rank_points 
			AND reward_id NOT IN(SELECT reward_id FROM custom_pvp_system_characters_rank_rewards WHERE charId = p_charId AND is_taken = 1)
			LIMIT 1;

		END;
	END WHILE;



END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `CPS_BBS_top`
-- ----------------------------
DROP PROCEDURE IF EXISTS `CPS_BBS_top`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `CPS_BBS_top`(IN `page` int,IN `date_from` bigint)
BEGIN
	/* Custom PvP Reward System (CPRS), BBS Comunity Board Data: Top Pvp / Rank Points*/

	/* If page = 1, return rank points table, else return kills table */
	SET @rowcount = 0;

	IF(page <> 1)THEN

		SELECT killer_id, char_name, level, base_class, sum(kills_legal) as col5, (CPS_BBS_get_daily_kills(killer_id,date_from,1)) as col6,(@rowcount := @rowcount + 1) as idx 
		FROM custom_pvp_system
		JOIN characters ON characters.obj_Id = custom_pvp_system.killer_id
		GROUP BY killer_id
		ORDER BY col5 DESC, level DESC, col6 DESC;

	ELSE

		SELECT killer_id, char_name, level, base_class, sum(rank_points) as col5, (CPS_BBS_get_daily_kills(killer_id,date_from,2)) as col6, (@rowcount := @rowcount + 1) as idx 
		FROM custom_pvp_system
		JOIN characters ON characters.obj_Id = custom_pvp_system.killer_id
		GROUP BY killer_id
		ORDER BY col5 DESC, level DESC, col6 DESC;

	END IF;


END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `CPS_get_rank_reward_count`
-- ----------------------------
DROP PROCEDURE IF EXISTS `CPS_get_rank_reward_count`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `CPS_get_rank_reward_count`(IN `p_charId` int)
BEGIN
	DECLARE current_rank_points INTEGER;
	DECLARE rank_reward_count INTEGER;
	
	SELECT sum(rank_points) INTO current_rank_points FROM custom_pvp_system WHERE killer_id = p_charId;
	IF(current_rank_points is null)THEN
		SET current_rank_points = 0;
	END IF;

	SELECT count(*) INTO rank_reward_count FROM custom_pvp_system_rank_reward
	WHERE min_rank_points  <= current_rank_points 
	AND reward_id NOT IN(SELECT reward_id FROM custom_pvp_system_characters_rank_rewards WHERE charId = p_charId AND is_taken = 1);

	SELECT rank_reward_count as rank_reward_count, current_rank_points as current_rank_points;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `CPS_get_rank_reward_list`
-- ----------------------------
DROP PROCEDURE IF EXISTS `CPS_get_rank_reward_list`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `CPS_get_rank_reward_list`(IN `p_charId` int, IN `p_current_rank_points` int)
BEGIN
	/*
		Add taken reward and set is_taken on 1, anyway if record exists its mean, the reward is already taken;
		is_taken field is implemented for future, choose reward list maybe ;)
		IMPORTANT p_current_rank_points is required for SECURITY (otherwise some rewards can disappears if player rise rank points when check reward list).
	*/

	SELECT * FROM custom_pvp_system_rank_reward
	WHERE min_rank_points  <= p_current_rank_points 
	AND reward_id NOT IN(SELECT reward_id FROM custom_pvp_system_characters_rank_rewards WHERE charId = p_charId AND is_taken = 1);

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `CPS_pvpinfo`
-- ----------------------------
DROP PROCEDURE IF EXISTS `CPS_pvpinfo`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `CPS_pvpinfo`(IN `p_killer_id` int, IN `p_victim_id` int, IN `time` BIGINT)
BEGIN

		DECLARE victim_kill_time BIGINT;
		DECLARE killer_kills_today INTEGER;
		DECLARE victim_killer_kills INTEGER;
		DECLARE victim_killer_kills_legal INTEGER;

		SELECT kill_time INTO victim_kill_time FROM custom_pvp_system WHERE killer_id = p_killer_id AND victim_id = p_victim_id; /* 100% */

		SELECT kills, kills_legal INTO victim_killer_kills, victim_killer_kills_legal FROM custom_pvp_system WHERE killer_id = p_victim_id AND victim_id = p_killer_id; /* 100% */

		IF(victim_kill_time > time)THEN /* time = sys time - prot time */
			/* if protect set on 1 should return 1 */
			SELECT custom_pvp_system.kills_today INTO killer_kills_today FROM custom_pvp_system WHERE killer_id = p_killer_id AND victim_id = p_victim_id; /* 100% */

		ELSE /* If time is okay */
	
			SET killer_kills_today = 0;

		END IF;
		
		/* get target summary */ /* 100% */
		SELECT sum(kills) as col1, sum(kills_today) as col2, sum(kills_legal) as col3, sum(kills_today_legal) as col4, sum(rank_points) as col5, sum(rank_points_today) as col6, killer_kills_today, victim_kill_time, victim_killer_kills, victim_killer_kills_legal, sum(war_kills_legal) as war_kills_legal, sum(war_kills) as war_kills
		FROM custom_pvp_system
		JOIN characters ON characters.obj_Id = custom_pvp_system.killer_id
		WHERE custom_pvp_system.killer_id = p_victim_id;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `CPS_BBS_get_daily_kills`
-- ----------------------------
DROP FUNCTION IF EXISTS `CPS_BBS_get_daily_kills`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `CPS_BBS_get_daily_kills`(p_killer_id int,from_date bigint,page int) RETURNS int(10)
    DETERMINISTIC
BEGIN
DECLARE wynik INT;

IF(page = 1)THEN
	SELECT sum(kills_today_legal) INTO wynik FROM custom_pvp_system where p_killer_id = killer_id and kill_time >= from_date ;
ELSE
	SELECT sum(rank_points_today) INTO wynik FROM custom_pvp_system where p_killer_id = killer_id and kill_time >= from_date ;
END IF;

RETURN wynik;
END
;;
DELIMITER ;

-- ----------------------------
-- Auto increment value for `custom_pvp_system_rank_reward`
-- ----------------------------
ALTER TABLE `custom_pvp_system_rank_reward` AUTO_INCREMENT=43;
