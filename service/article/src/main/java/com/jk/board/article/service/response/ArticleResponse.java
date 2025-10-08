package com.jk.board.article.service.response;

import com.jk.board.article.entity.Article;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleResponse {
    private Long articleId;
    private String title;
    private String content;
    private Long boardId;
    private Long writerId;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public static ArticleResponse from(Article article) {
        return new ArticleResponse(article);
    }

    public ArticleResponse(Article article) {
        this.articleId = article.getArticleId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.boardId = article.getBoardId();
        this.writerId = article.getWriterId();
        this.createAt = article.getCreatedAt();
        this.modifiedAt = article.getModifiedAt();
    }
}
