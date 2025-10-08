package com.jk.board.article.controller;

import com.jk.board.article.service.response.ArticleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

class ArticleControllerTest {

    RestClient restClient = RestClient.create("http://localhost:8080");

    @Test
    void createTest() {
        ArticleResponse response = create(new ArticleCreateRequest("test", "content", 1L, 1L));
        System.out.println("response = " + response);
    }

    ArticleResponse create(ArticleCreateRequest request) {
        return restClient.post()
                .uri("/v1/articles")
                .body(request)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void readTest() {
        ArticleResponse response = read(234105273186234368L);
        System.out.println("response = " + response);
    }
    ArticleResponse read(Long articleId) {
        return restClient.get()
                .uri("/v1/articles/{articleId}", articleId)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void updateTest() {
        update(234105273186234368L);
        ArticleResponse response = read(234105273186234368L);
        System.out.println("response = " + response);
    }
    void update(Long articleId) {
        restClient.put()
                .uri("/v1/articles/{articleId}", articleId)
                .body(new ArticleUpdateRequest("test1", "test2"))
                .retrieve();
    }


    @Test
    void deleteTest() {
        restClient.delete()
                .uri("/v1/articles/{articleId}", 234105273186234368L)
                .retrieve();
    }


    @Getter
    @AllArgsConstructor
    static class ArticleCreateRequest {
        private String title;
        private String content;
        private Long writerId;
        private Long boardId;
    }

    @Getter
    @AllArgsConstructor
    static public class ArticleUpdateRequest {
        private String title;
        private String content;
    }
}