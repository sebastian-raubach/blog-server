DROP TABLE IF EXISTS `sites`;
CREATE TABLE `sites` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `description` text NULL,
    `sitetype` enum('campsite','wildcamp') NOT NULL DEFAULT 'campsite',
    `groundtype` enum('paved','grass','gravel','sand') NOT NULL DEFAULT 'grass',
    `latitude` double(64, 10) NOT NULL,
    `longitude` double(64, 10) NOT NULL,
    `elevation` double(64, 10) NULL,
    `rating` json NOT NULL,
    `facilities` json NOT NULL,
    `created_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `postsites`;
CREATE TABLE `postsites`  (
    `post_id` int(11) NOT NULL,
    `site_id` int(11) NOT NULL,
    PRIMARY KEY (`post_id`, `site_id`),
    FOREIGN KEY (`site_id`) REFERENCES `sites` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);