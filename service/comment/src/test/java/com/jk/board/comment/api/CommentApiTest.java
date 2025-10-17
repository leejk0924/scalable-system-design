package com.jk.board.comment.api;

import com.jk.board.comment.service.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class CommentApiTest {
    RestClient restClient = RestClient.create("http://localhost:8081");

    @Test
    void create() throws Exception {
        // Given
        CommentResponse response1 = createComment(new CommentCreateRequest(1L, "content1", null, 1L));
        CommentResponse response2 = createComment(new CommentCreateRequest(1L, "content2", response1.getCommentId(), 1L));
        CommentResponse response3 = createComment(new CommentCreateRequest(1L, "content3", response1.getCommentId(), 1L));

        System.out.println("commentId=%s".formatted(response1.getCommentId()));
        System.out.println("\tcommentId=%s".formatted(response2.getCommentId()));
        System.out.println("\tcommentId=%s".formatted(response3.getCommentId()));
        // When

        // Then
    }

    CommentResponse createComment(CommentCreateRequest request) {
        return restClient.post()
                .uri("/v1/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }

    @Test
    void read() throws Exception {
        // Given
        CommentResponse response = restClient.get()
                .uri("/v1/comments/{commentId}", 237466238926086144L)
                .retrieve()
                .body(CommentResponse.class);
        System.out.println("response = " + response);
        // When

        // Then

    }

    @Test
    void delete() throws Exception {
        // Given
        restClient.delete()
                .uri("/v1/comments/{commentId}", 237466239165161472L)
                .retrieve();

        // When

        // Then

    }

    @Getter
    @AllArgsConstructor
    public static class CommentCreateRequest {
        private Long articleId;
        private String content;
        private Long parentCommentId;
        private Long writerId;
    }
}
