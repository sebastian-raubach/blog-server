UPDATE `postvideos`
SET video_path = SUBSTRING_INDEX( SUBSTRING_INDEX( SUBSTRING( video_path, LOCATE( '/embed/', video_path ) + 7 ), '?', 1 ), '/', 1 )
WHERE video_path LIKE '%/embed/%';
