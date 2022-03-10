ALTER TABLE `posthills`
ADD COLUMN `successful` tinyint(1) NOT NULL DEFAULT 1 AFTER `hill_id`;