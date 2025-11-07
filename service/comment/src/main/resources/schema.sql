CREATE TABLE IF NOT EXISTS comment (
    comment_id BIGINT NOT NULL PRIMARY KEY,
    content VARCHAR(3000) NOT NULL,
    article_id BIGINT NOT NULL,
    parent_comment_id BIGINT NOT NULL,
    writer_id BIGINT NOT NULL,
    deleted BOOL NOT NULL,
    created_at DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS comment_v2 (
                                          comment_id bigint NOT NULL PRIMARY KEY ,
                                          content VARCHAR(3000) NOT NULL,
                                          article_id BIGINT NOT NULL,
                                          writer_id BIGINT NOT NULL,
                                          path VARCHAR(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT  NULL,
                                          deleted BOOL NOT NULL,
                                          created_at DATETIME NOT NULL
);

CREATE INDEX idx_article_id_parent_comment_id_comment_id ON comment (
        article_id ASC,
        parent_comment_id ASC,
        comment_id ASC
);

CREATE UNIQUE INDEX idx_article_id_path ON comment_v2(
        article_id ASC, path ASC
    );

SELECT TABLE_NAME, COLUMN_NAME, COLLATION_NAME
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = 'comment' AND TABLE_NAME = 'comment_v2' AND COLUMN_NAME = 'path';
