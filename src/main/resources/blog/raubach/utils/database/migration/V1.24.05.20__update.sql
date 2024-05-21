CREATE TABLE `individuals`  (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `user_id` int(11) NULL,
    `photo` mediumblob NULL,
    `created_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE `hill_individuals`  (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `hill_id` int(11) NOT NULL,
     `individual_id` int(11) NOT NULL,
     `created_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
     `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     PRIMARY KEY (`id`),
     FOREIGN KEY (`hill_id`) REFERENCES `hills` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
     FOREIGN KEY (`individual_id`) REFERENCES `individuals` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);