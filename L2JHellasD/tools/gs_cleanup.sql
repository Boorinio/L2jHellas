-- These queries are meant to delete any gameserver table you may have from
-- previous L2JHellas installations.
--
-- Queries that create these tables and/or populate them apropiately are
-- available in the ../sql/ folder. See the documentation.txt file
-- for more details.
-- ----------------------------
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
DROP TABLE IF EXISTS
-- MOD TABLES
balance,
buff_templates,
characters_colors,
ctf_teams,
ctf,
dm,
hitman_list,
mods_buffer,
mods_buffer_schemes,
mods_buffer_skills,
mods_wedding,
npc_buffer,
raid_prizes,
raid_event_spawnlist,
reward_list,
tvt_teams,
tvt,
vipinfo,
-- CUSTOM TABLES
custom_armor,
custom_droplist,
custom_etcitem,
custom_npc,
custom_pvp_system,
custom_pvp_system_characters_rank_rewards,
custom_pvp_system_rank_reward,
custom_teleport,
custom_weapon,
-- GAME SERVER TABLES
account_premium,
admin_command_access_rights,
armor,
auction,
auction_bid,
auction_watch,
augmentations,
auto_announcements,
auto_chat,
auto_chat_text,
boxaccess,
boxes,
castle,
castle_door,
castle_doorupgrade,
castle_manor_procure,
castle_manor_production,
castle_siege_guards,
char_templates,
character_friends,
character_hennas,
character_macroses,
character_quests,
character_raid_points,
character_recipebook,
character_recommends,
character_shortcuts,
character_skills,
character_skills_save,
character_subclasses,
characters,
clan_data,
clan_notices,
clan_privs,
clan_skills,
clan_subpledges,
clan_wars,
clanhall,
clanhall_functions,
class_list,
cursed_weapons,
dimensional_rift,
droplist,
enchant_skill_trees,
etcitem,
fish,
fishing_skill_trees,
four_sepulchers_spawnlist,
games,
global_tasks,
grandboss_data,
grandboss_list,
helper_buff_list,
henna,
henna_trees,
heroes,
items,
itemsonground,
locations,
lvlupgain,
mails,
mapregion,
max_poly,
merchant_areas_list,
merchant_buylists,
merchant_lease,
merchant_shopids,
merchants,
minions,
npc,
npcskills,
olympiad_nobles,
pets,
pets_stats,
pledge_skill_trees,
quest_global_data,
raidboss_spawnlist,
random_spawn,
random_spawn_loc,
seven_signs,
seven_signs_festival,
seven_signs_status,
siege_clans,
skill_learn,
skill_spellbooks,
skill_trees,
spawnlist,
teleport,
walker_routes,
weapon,
zone,
zone_vertices,
-- CS
topic,
forums,
posts;