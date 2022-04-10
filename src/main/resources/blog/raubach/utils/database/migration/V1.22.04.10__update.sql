CREATE TABLE `relationships`  (
  `post_a_id` int(11) NOT NULL,
  `post_b_id` int(11) NOT NULL,
  `created_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`post_a_id`, `post_b_id`),
  FOREIGN KEY (`post_a_id`) REFERENCES `blog`.`posts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`post_b_id`) REFERENCES `blog`.`posts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);