ALTER TABLE `posts`
ADD COLUMN `visible` tinyint(1) NOT NULL DEFAULT 1 AFTER `content_markdown`;