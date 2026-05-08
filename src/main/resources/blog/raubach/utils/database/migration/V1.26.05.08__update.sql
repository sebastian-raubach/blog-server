ALTER TABLE `postsites`
    ADD COLUMN `groundtype` enum('paved','grass','gravel','sand') NOT NULL DEFAULT 'grass' AFTER `site_id`;

UPDATE `postsites` SET `groundtype` = (SELECT `groundtype` FROM `sites` WHERE `sites`.`id` = `postsites`.`site_id`);

ALTER TABLE `sites`
    DROP COLUMN `groundtype`;