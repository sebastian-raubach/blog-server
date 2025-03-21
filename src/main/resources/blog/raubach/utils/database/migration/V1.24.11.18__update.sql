DROP TABLE IF EXISTS `hill_individuals`;

CREATE TABLE `post_individuals`  (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `post_id` int(11) NOT NULL,
     `individual_id` int(11) NOT NULL,
     `created_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
     `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     PRIMARY KEY (`id`),
     FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
     FOREIGN KEY (`individual_id`) REFERENCES `individuals` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);