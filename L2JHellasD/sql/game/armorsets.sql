SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for table `armorsets`
-- ----------------------------
DROP TABLE IF EXISTS `armorsets`;

CREATE TABLE `armorsets` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `chest` decimal(11,0) NOT NULL DEFAULT '0',
  `legs` decimal(11,0) NOT NULL DEFAULT '0',
  `head` decimal(11,0) NOT NULL DEFAULT '0',
  `gloves` decimal(11,0) NOT NULL DEFAULT '0',
  `feet` decimal(11,0) NOT NULL DEFAULT '0',
  `skill_id` decimal(11,0) NOT NULL DEFAULT '0',
  `skill_lvl` decimal(11,0) NOT NULL DEFAULT '0',
  `shield` decimal(11,0) NOT NULL DEFAULT '0',
  `shield_skill_id` decimal(11,0) NOT NULL DEFAULT '0',
  `enchant6skill` decimal(11,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`,`chest`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of `armorsets`
-- ----------------------------
INSERT INTO `armorsets` VALUES
--                 NO GRADE              -------------
-- ######################## id chest  legs head gloves feet    skill  skill_lvl shield sh_skill enchant6
(1,  23,   2386, 43,   0,    0,      3500,   1,   0,    0,    0),        -- Wooden Breastplate set (heavy)
(2,  1101, 1104, 44,   0,    0,      3501,   1,   0,    0,    0),        -- Devotion robe set (robe)

--                 D GRADE              -------------
-- ######################## id chest  legs head gloves feet    skill  skill_lvl shield sh_skill enchant6
(3,  58,   59,   47,   0,    0,      3502,   1,   628,  3543,  3611),    -- Mithril Breastplate set(heavy)
(4,  352,  2378, 2411, 0,    0,      3506,   1,   2493, 3544,  3611),    -- Brigandine Armor set

(5,  394,  416,  0,    0,    2422,   3503,   1,   0,    0,     3612),    -- Reinforced leather set
(6,  395,  417,  0,    0,    2424,   3505,   1,   0,    0,     3612),    -- Manticore skin set

(7,  436,  469,  0,    2447, 0,      3504,   1,   0,    0,     3613),    -- Tunic of knowledge set
(8,  437,  470,  0,    2450, 0,      3507,   1,   0,    0,     3613),    -- Mithril Tunic

--                 C GRADE              -------------
-- ######################## id chest  legs head gloves feet    skill  skill_lvl shield sh_skill enchant6
(9,  354,  381,  2413, 0,    0,      3509,   1,   2495, 3545,  3614),    -- Chain Mail Shirt set
(10, 60,   0,    517,  0,    0,      3512,   1,   107,  3546,  3614),    -- Composite Armor set
(11, 356,  0,    2414, 0,    0,      3516,   1,   2497, 3547,  3614),    -- Full Plate Armor set

(12, 397,  2387, 0,    0,    62,     3508,   1,   0,    0,     3615),    -- Mithrill shirt set
(13, 398,  418,  0,    0,    2431,   3511,   1,   0,    0,     3615),    -- Plated leather set
(14, 400,  420,  0,    0,    2436,   3514,   1,   0,    0,     3615),    -- Theca leather set
(15, 401,  0,    0,    0,    2437,   3515,   1,   0,    0,     3615),    -- Drake leather set

(16, 439,  471,  0,    2454, 0,      3510,   1,   0,    0,     3616),    -- Karmian robe set
(17, 441,  472,  0,    2459, 0,      3513,   1,   0,    0,     3616),    -- Demon robe set
(18, 442,  473,  0,    2463, 0,      3517,   1,   0,    0,     3616),    -- Divine robe set

--                 B GRADE              -------------
-- ######################## id chest  legs head gloves feet    skill  skill_lvl shield sh_skill enchant6
(19, 357,  383,  503,  5710, 5726,   3518,   1,   0,    0,     3617),    -- Zubei's Breastplate set
(20, 2384, 2388, 503,  5711, 5727,   3520,   1,   0,    0,     3618),    -- Zubei's leather set
(21, 2397, 2402, 503,  5712, 5728,   3522,   1,   0,    0,     3619),    -- Zubei robe set

(22, 2376, 2379, 2415, 5714, 5730,   3519,   1,   673,  3548,  3617),    -- Avadon heavy set
(23, 2390, 0,    2415, 5715, 5731,   3521,   1,   0,    0,     3618),    -- Avadon leather set
(24, 2406, 0,    2415, 5716, 5732,   3523,   1,   0,    0,     3619),    -- Avadon robe set

(25, 358,  2380, 2416, 5718, 5734,   3524,   1,   0,    0,     3617),    -- Blue Wolf's Breastplate set
(26, 2391, 0,    2416, 5719, 5735,   3526,   1,   0,    0,     3618),    -- Blue wolf leather set
(27, 2398, 2403, 2416, 5720, 5736,   3528,   1,   0,    0,     3619),    -- Blue Wolf robe set

(28, 2381, 0,    2417, 5722, 5738,   3525,   1,   110,  3549,  3617),    -- Doom plate heavy set
(29, 2392, 0,    2417, 5723, 5739,   3527,   1,   0,    0,     3618),    -- Doom leather set
(30, 2399, 2404, 2417, 5724, 5740,   3529,   1,   0,    0,     3619),    -- Doom robe set 

--                 A GRADE              -------------
-- ######################## id chest  legs head gloves feet    skill  skill_lvl shield sh_skill enchant6
(31, 365,  388,  512,  5765, 5777,   3530,   1,   641,  3550,  3620),    -- Dark Crystal Breastplate set
(32, 2385, 2389, 512,  5766, 5778,   3532,   1,   0,    0,     3621),    -- Dark Crystal leather set
(33, 2407, 0,    512,  5767, 5779,   3535,   1,   0,    0,     3622),    -- Dark Crystal robe set

(34, 2382, 0,    547,  5768, 5780,   3531,   1,   0,    0,     3620),    -- Tallum plate heavy set
(35, 2393, 0,    547,  5769, 5781,   3533,   1,   0,    0,     3621),    -- Tallum leather set
(36, 2400, 2405, 547,  5770, 5782,   3534,   1,   0,    0,     3622),    -- Tallum robe set

(37, 374,  0,    2418, 5771, 5783,   3536,   1,   2498, 3551,  3620),    -- Nightmare heavy set
(38, 2394, 0,    2418, 5772, 5784,   3538,   1,   0,    0,     3621),    -- Nightmare leather set
(39, 2408, 0,    2418, 5773, 5785,   3540,   1,   0,    0,     3622),    -- Robe of nightmare set

(40, 2383, 0,    2419, 5774, 5786,   3537,   1,   0,    0,     3620),    -- Majestic plate heavy set
(41, 2395, 0,    2419, 5775, 5787,   3539,   1,   0,    0,     3621),    -- Majestic leather set
(42, 2409, 0,    2419, 5776, 5788,   3541,   1,   0,    0,     3622),    -- Majestic robe set

--                 S GRADE              -------------
-- ######################## id chest  legs head gloves feet    skill  skill_lvl shield sh_skill enchant6
(43, 6373, 6374, 6378, 6375, 6376,   3553,   1,   6377, 3554,  3623),    -- Imperial crusader set
(44, 6379, 0,    6382, 6380, 6381,   3555,   1,   0,    0,     3624),    -- Draconic leather set
(45, 6383, 0,    6386, 6384, 6385,   3556,   1,   0,    0,     3625),    -- Major arcana robe set

--                Clan Sets              -------------
-- ######################## id chest  legs head gloves feet    skill  skill_lvl shield sh_skill enchant6
(46, 7851, 0,    7850, 7852, 7853,   3605,   1,   0,    0,     3611),    -- Clan oath Armor set (heavy)
(47, 7854, 0,    7850, 7855, 7856,   3606,   1,   0,    0,     3612),    -- Clan Oath Brigandine set (light)
(48, 7857, 0,    7850, 7858, 7859,   3607,   1,   0,    0,     3613),    -- Clan Oath Aketon set (robe)

(49, 7861, 0,    7860, 7862, 7863,   3608,   1,   0,    0,     3620),    -- Apella plate armor set (heavy) 
(50, 7864, 0,    7860, 7865, 7866,   3609,   1,   0,    0,     3621),    -- Apella Brigandine set (light) 
(51, 7867, 0,    7860, 7868, 7869,   3610,   1,   0,    0,     3622);    -- Apella Doublet set (robe) 