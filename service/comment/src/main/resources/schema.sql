CREATE TABLE IF NOT EXISTS comment (
    comment_id BIGINT NOT NULL PRIMARY KEY,
    content VARCHAR(3000) NOT NULL,
    article_id BIGINT NOT NULL,
    parent_comment_id BIGINT NOT NULL,
    writer_id BIGINT NOT NULL,
    deleted BOOL NOT NULL,
    created_at DATETIME NOT NULL
);

CREATE INDEX idx_article_id_parent_comment_id_comment_id ON comment (
        article_id ASC,
        parent_comment_id ASC,
        comment_id ASC
);