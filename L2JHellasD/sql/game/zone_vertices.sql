SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `zone_vertices`
-- ----------------------------
DROP TABLE IF EXISTS `zone_vertices`;

CREATE TABLE `zone_vertices` (
  `id` int(11) NOT NULL,
  `order` int(11) NOT NULL,
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  PRIMARY KEY  (`id`,`order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='L2jHellas Table';

-- ----------------------------
-- Records of `zone_vertices`
-- NOTE: Cuboid shapes only need 2 points in order to be defined.
-- That is their NorthWest and SouthEast corners suffice to descript a cuboid.
-- All other shapes require 3 or more vertices.
-- NOTE 2: The order is outmost important for many shapes and thus special
-- care is recommended.  Order doesn't really matter much for cuboids and prisms
-- (triangular base) but it is best to pay attention to the order there, too,
-- keeping the definitions as much as possible in a loop from NW to SE.
-- ----------------------------

INSERT INTO `zone_vertices` (`id`,`order`,`x`,`y`) VALUES 
(11000,0,-115600,-250700),
(11000,1,-113500,-248200),
(11001,0,11718,181534),
(11001,1,14400,182667),
(11001,2,13308,181332), 
(11001,3,14067,181614), 
(11001,4,13554,182616), 
(11001,5,14081,182916), 
(11001,6,11704,182593), 
(11001,7,13207,183017), 
(11001,8,12943,182948), 
(11001,9,13194,184257), 
(11001,10,11706,184010), 
(11001,11,13068,184261), 
(11001,12,11701,182895), 
(11001,13,11951,184103),
(11010,0,72498,142271),
(11010,1,73495,143255),
(11011,0,-88410,142728),
(11011,1,-87421,141730),
(11012,0,148014,45304),
(11012,1,150976,48020),
(11013,0,11948,183015),
(11013,1,12939,184015),
(11021,0,17248,19664),
(11020,0,80242,143523),
(11020,1,83676,143507),
(11020,2,83688,141483),
(11020,3,84080,141471),
(11020,4,84080,143510),
(11020,5,85036,143512),
(11020,6,85046,145750),
(11020,7,86136,145736),
(11020,8,86120,146905),
(11020,9,88416,146896),
(11020,10,90465,147173),
(11020,11,90494,147573),
(11020,12,86780,153625),
(11020,13,92601,153518),
(11020,14,79180,152845),
(11020,15,77166,149886),
(11020,16,77143,147395),
(11020,17,79157,144755),
(11021,1,6063,14019),
(11022,0,-87312,240096),
(11022,1,-81129,246345),
(11023,0,48294,52995),
(11023,1,42402,46155),
(11024,0,-42078,-109785),
(11024,1,-47648,-117366),
(11025,0,-84892,149075),
(11025,1,-76820,156125),
(11026,0,117395,-176766),
(11026,1,114650,-184347),
(11027,0,-11853,126610),
(11027,1,-16652,121003),
(11028,0,15300,141609),
(11028,1,21570,147635),
(11029,0,76696,57199),
(11029,1,84511,50120),
(11030,0,121308,73941),
(11030,1,114667,80383),
(11031,0,142312,32317),
(11031,1,152163,19708),
(11032,0,143444,-59854),
(11032,1,152043,-51601),
(11033,0,47150,-44815),
(11033,1,32531,-52045),
(11034,0,103598,216010),
(11034,1,118991,225905),
(11035,0,83881,-146500),
(11035,1,90908,-139486),
(11036,0,16712,169303),
(11036,1,18368,170893),
(11037,0,1991,-1259),
(11037,1,33365,-28913),
(11050,0,47600,38290),
(11050,1,44483,41745),
(11051,0,46249,50036),
(11051,1,44431,49176),
(11052,0,20580,51713),
(11052,1,21667,50393),
(11053,0,24993,80655),
(11053,1,25454,82314),
(11054,0,57052,85445),
(11054,1,57937,87057),
(11060,0,109448,10233),
(11060,1,118547,21446),
(11070,0,-19627,-19712),
(11070,1,-22024,-22322),
(11071,0,-119100,-223705),
(11071,1,-121484,-226316),
(11072,0,-103690,-210300),
(11072,1,-101325,-207724),
(11073,0,-119079,-206078),
(11073,1,-121438,-208668),
(11074,0,-88700,-226280),
(11074,1,-86351,-223722),
(11075,0,-80586,-211911),
(11075,1,-82939,-214487),
(11076,0,-88659,-208652),
(11076,1,-86297,-206075),
(11077,0,-95000,-219531),
(11077,1,-92632,-216950),
(11078,0,-75936,-217408),
(11078,1,-78306,-220017),
(11079,0,-68560,-207718),
(11079,1,-70933,-210312),
(11080,0,-78008,-202528),
(11080,1,-75663,-199943),
(11081,0,-108690,-217403),
(11081,1,-111072,-220023),
(11082,0,-127766,-219555),
(11082,1,-125394,-216946),
(11083,0,-108428,-199935),
(11083,1,-110796,-202541),
(11084,0,-88677,-241444),
(11084,1,-86294,-238836),
(11085,0,-82938,-247261),
(11085,1,-80580,-244668),
(11086,0,-75930,-250175),
(11086,1,-78298,-252779),
(11087,0,-70920,-243079),
(11087,1,-68547,-240473),
(11088,0,-75670,-232712),
(11088,1,-78027,-235326),
(11089,0,-92632,-249706),
(11089,1,-94999,-252316),
(11090,0,-87816,-254280),
(11090,1,-86332,-256466),
(11091,0,-113332,-211881),
(11091,1,-115713,-214513),
(11100,0,43151,108377),
(11100,1,43648,109399),
(11101,0,-16400,123275),
(11101,1,-15551,123850),
(11102,0,-15100,125350),
(11102,1,-14800,125800),
(11103,0,-14050,125050),
(11103,1,-13700,125700),
(11104,0,-12950,123900),
(11104,1,-12300,124250),
(11105,0,-84700,151550),
(11105,1,-84250,152350),
(11106,0,-84200,153050),
(11106,1,-83550,153600),
(11107,0,-84100,155300),
(11107,1,-83500,155700),
(11108,0,-80100,149400),
(11108,1,-79500,149850),
(11109,0,-79700,151350),
(11109,1,-79300,152250),
(11110,0,17400,144800),
(11110,1,18000,145350),
(11111,0,18850,143600),
(11111,1,18600,143100),
(11112,0,19950,146000),
(11112,1,20400,146300),
(11113,0,0,0),
(11113,1,0,0),
(11114,0,80738,-15914),
(11114,1,79627,-15054),
(11115,0,148844,22709),
(11115,1,149424,23569),
(11116,0,150343,23193),
(11116,1,150943,24113),
(11117,0,145362,24890),
(11117,1,145972,25820),
(11118,0,150460,26108),
(11118,1,151036,26972),
(11119,0,143701,26661),
(11119,1,144281,27521),
(11120,0,143704,27734),
(11120,1,144324,28670),
(11121,0,78059,147906),
(11121,1,79122,148296),
(11122,0,81859,144802),
(11122,1,82254,145870),
(11123,0,83195,144779),
(11123,1,83591,145847),
(11124,0,80773,151053),
(11124,1,81169,152121),
(11125,0,81903,151377),
(11125,1,82299,152445),
(11126,0,146399,-55682),
(11126,1,145652,-55386),
(11127,0,147238,-56636),
(11127,1,146564,-57078),
(11128,0,148479,-56473),
(11128,1,148479,-57275),
(11129,0,149717,-55824),
(11129,1,149063,-55350),
(11130,0,37461,-50973),
(11130,1,38006,-50589),
(11131,0,38401,-50516),
(11131,1,39054,-50404),
(11132,0,39173,-50020),
(11132,1,39774,-49340),
(11133,0,39426,-48619),
(11133,1,39820,-47871),
(11134,0,39437,-47141),
(11134,1,39760,-46668),
(11135,0,38433,-46322),
(11135,1,39062,-45731),
(11136,0,37437,-45872),
(11136,1,38024,-45460),
(11137,0,85426,-143448),
(11137,1,86069,-142769),
(11138,0,86162,-142094),
(11138,1,87003,-141727),
(11139,0,88600,-142111),
(11139,1,87724,-141750),
(11140,0,88500,-143500),
(11140,1,89500,-142880),
(11141,0,141414,-124508),
(11141,1,140590,-124706),
(11142,0,0,0),
(11142,1,0,0),
(11143,0,0,0),
(11143,1,0,0),
(11400,0,83327,143509),
(11400,1,83031,143121),
(11400,2,82475,142974),
(11400,3,82174,142486),
(11400,4,82333,141833),
(11400,5,82955,141507),
(11400,6,83288,141763),
(11400,7,83683,141814),
(11400,8,83680,143500),
(11200,0,-22615,104510),
(11200,1,-13313,116950),
(11201,0,17273,152750),
(11201,1,26575,165240),
(11202,0,107026,140571),
(11202,1,121358,149919),
(11203,0,75000,32666),
(11203,1,87434,42014),
(11204,0,142431,362),
(11204,1,152282,19750),
(11205,0,111224,241579),
(11205,1,120526,254019),
(11206,0,141457,-51510),
(11206,1,154500,-39107),
(11207,0,7000,-55500),
(11207,1,27250,-41716),
(11208,0,73000,-156600),
(11208,1,82560,-144750),
(12001,0,181241,-86443),
(12001,1,192134,-84575),
(12002,0,179551,-89844),
(12002,1,190342,-88042),
(12003,0,172354,-86630),
(12003,1,174073,-75565),
(12004,0,174697,-82707),
(12004,1,176500,-71885),
(12005,0,176232,-89041),
(12005,1,176389,-87010),
(12005,2,175052,-85863),
(12005,3,174433,-86080),
(12005,4,174061,-86077),
(12005,5,173427,-85859),
(12005,6,172084,-87017),
(12005,7,172087,-88692),
(12005,8,172242,-89038),
(12005,9,173417,-90026),
(12005,10,175054,-90033),
(12006,0,173439,110176),
(12006,1,187346,119469),
(12007,0,112987,14312),
(12007,1,112507,14623),
(12007,2,112129,15551),
(12007,3,111890,16230),
(12007,4,112067,16965),
(12007,5,112496,17482),
(12007,6,112863,18120),
(12007,7,113453,18561),
(12007,8,114135,18590),
(12007,9,114815,18811),
(12007,10,115549,18648),
(12007,11,116074,18205),
(12007,12,116776,17506),
(12007,13,117255,17013),
(12007,14,117371,16298),
(12007,15,117405,15925),
(12007,16,117236,15199),
(12007,17,117158,14895),
(12007,18,116784,14260),
(12007,19,116096,13902),
(12007,20,115159,13507),
(12007,21,114135,13518),
(12007,22,113203,13898),
(12008,0,196866,-130845),
(12008,1,229289,-130860),
(12008,2,229176,-98508),
(12008,3,196880,-98498),
(12009,0,183986,-13716),
(12009,1,186097,-11532),
(12010,0,184008,-10681),
(12010,1,186107,-8589),
(12011,0,50797,213515),
(12011,1,57617,213510),
(12011,2,57669,221683),
(12011,3,50773,221632),
(12012,0,-21490,178613),
(12012,1,-22258,178961),
(12012,2,-22890,182205),
(12012,3,-22770,183129),
(12012,4,-21594,183945),
(12012,5,-20570,183381),
(12012,6,-20310,182673),
(12012,7,-20762,179129),
(12013,0,43976,24664),
(12013,1,43284,18952),
(12013,2,42476,18164),
(12013,3,42480,16448),
(12013,4,43084,15944),
(12013,5,45140,15816),
(12013,6,48800,15112),
(12013,7,56556,12040),
(12013,8,59068,14288),
(12013,9,61064,18492),
(12013,10,60380,23672),
(12013,11,58724,27412),
(12013,12,55696,28496),
(12013,13,43976,24664),
(12014,0,-18176,-54972),
(12014,1,-14614,-51620),
(12015,0,101744,-130016),
(12015,1,111376,-130096),
(12015,2,114416,-126128),
(12015,3,115246,-125857),
(12015,4,115209,-125669),
(12015,5,113920,-125669),
(12015,6,102400,-121920),
(12015,7,99456,-126208),
(15200,0,17224,-48483),
(15200,1,17179,-48581),
(15200,2,17106,-48596),
(15200,3,16973,-48616),
(15200,4,16888,-48621),
(15200,5,16792,-48622),
(15200,6,16714,-48641),
(15200,7,16595,-48664),
(15200,8,16493,-48666),
(15200,9,16425,-48652),
(15200,10,16291,-48640),
(15200,11,16240,-48603),
(15200,12,16207,-48526),
(15200,13,16189,-48486),
(15200,14,16159,-48401),
(15200,15,16140,-48294),
(15200,16,16128,-48229),
(15200,17,16151,-48004),
(15200,18,16135,-47878),
(15200,19,16124,-47729),
(15200,20,16141,-47590),
(15200,21,16136,-47512),
(15200,22,16112,-47400),
(15200,23,16035,-47308),
(15200,24,16001,-47277),
(15200,25,15935,-47237),
(15200,26,15852,-47176),
(15200,27,15783,-47091),
(15200,28,15785,-47048),
(15200,29,15782,-46988),
(15200,30,15782,-46906),
(15200,31,15867,-46883),
(15200,32,16055,-46863),
(15200,33,16233,-46849),
(15200,34,16327,-46873),
(15200,35,16491,-46879),
(15200,36,16621,-46882),
(15200,37,16697,-46938),
(15200,38,16754,-46999),
(15200,39,16896,-47119),
(15200,40,16962,-47186),
(15200,41,17079,-47290),
(15200,42,17164,-47369),
(15200,43,17222,-47435),
(15200,44,17260,-47493),
(15200,45,17253,-47557),
(15200,46,17261,-47672),
(15200,47,17262,-47872),
(15200,48,17253,-47994),
(15200,49,17253,-48132),
(15200,50,17248,-48286),
(15200,51,17238,-48407),
(15200,52,17213,-48485),
(15201,0,17320,-48513),
(15201,1,17022,-47856),
(15201,2,17178,-49669),
(15201,3,17240,-49698),
(15201,4,17252,-49744),
(15201,5,17246,-49905),
(15201,6,17253,-50018),
(15201,7,17257,-50160),
(15201,8,17266,-50318),
(15201,9,17257,-50438),
(15201,10,17249,-50592),
(15201,11,17208,-50709),
(15201,12,17144,-50785),
(15201,13,17107,-50842),
(15201,14,17057,-50924),
(15201,15,17002,-50982),
(15201,16,16881,-51062),
(15201,17,16779,-51175),
(15201,18,16622,-51304),
(15201,19,16506,-51333),
(15201,20,16323,-51305),
(15201,21,16162,-51298),
(15201,22,15974,-51313),
(15201,23,15865,-51288),
(15201,24,15800,-51222),
(15201,25,15802,-51099),
(15201,26,15865,-51010),
(15201,27,15997,-50834),
(15201,28,16086,-50733),
(15201,29,16098,-50692),
(15201,30,16144,-50537),
(15201,31,16138,-50373),
(15201,32,16125,-50201),
(15201,33,16141,-50042),
(15201,34,16168,-49858),
(15201,35,16209,-49767),
(15201,36,16226,-49719),
(15201,37,16277,-49659),
(15201,38,16430,-49657),
(15201,39,16623,-49622),
(15201,40,16776,-49628),
(15201,41,16941,-49629),
(15201,42,17070,-49644),
(15201,43,17140,-49652),
(15201,44,17190,-49670),
(15201,45,17218,-49681),
(15201,46,17232,-49695),
(22001,0,82722,148839),
(22001,1,83019,149274),
(22004,0,82414,148486),
(22004,1,82619,148896);