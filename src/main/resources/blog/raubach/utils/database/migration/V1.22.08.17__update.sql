ALTER TABLE `posts`
MODIFY COLUMN `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL AFTER `title`,
ADD COLUMN `content_markdown` text NULL AFTER `content`;

ALTER TABLE `stories`
MODIFY COLUMN `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL AFTER `title`,
ADD COLUMN `content_markdown` text NULL AFTER `content`;