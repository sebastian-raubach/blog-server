DROP VIEW IF EXISTS `image_details`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `image_details` AS SELECT
    `images`.`id` AS `image_id`,
    `images`.`path` AS `image_path`,
    `postimages`.`post_id` AS `post_id`,
    `postimages`.`is_primary` AS `is_primary`,
    `postimages`.`description` AS `description`
FROM
    `images`
        LEFT JOIN `postimages` ON `postimages`.`image_id` = `images`.`id`;

DROP VIEW IF EXISTS `view_sites`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_sites` AS SELECT sites.*, postsites.groundtype, postsites.post_id FROM sites LEFT JOIN postsites ON postsites.site_id = sites.id;
