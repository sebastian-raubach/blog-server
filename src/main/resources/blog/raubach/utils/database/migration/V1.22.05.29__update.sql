ALTER TABLE `posts`
ADD COLUMN `view_count` int NOT NULL DEFAULT 0 AFTER `content`;