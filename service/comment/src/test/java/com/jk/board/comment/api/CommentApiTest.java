package com.jk.board.comment.api;

import com.jk.board.comment.service.response.CommentPageResponse;
import com.jk.board.comment.service.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

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
    }

    CommentResponse createComment(CommentCreateRequest request) {
        return restClient.post()
                .uri("/v2/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }

    @Test
    void read() throws Exception {
        // Given
        CommentResponse response = restClient.get()
                .uri("/v2/comments/{commentId}", 237466238926086144L)
                .retrieve()
                .body(CommentResponse.class);
        System.out.println("response = " + response);
    }

    @Test
    void delete() throws Exception {
        restClient.delete()
                .uri("/v2/comments/{commentId}", 237466239165161472L)
                .retrieve();
    }

    @Test
    void readAll() throws Exception {
        CommentPageResponse response = restClient.get()
                .uri("/v2/comments?articleId=1&page=1&pageSize=10")
                .retrieve()
                .body(CommentPageResponse.class);

        System.out.println("response.getCommentCount() = " + response.getCommentCount());
        for (CommentResponse comment : response.getComments()) {
            if (!comment.getCommentId().equals(comment.getParentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("comment.getCommentId() = " + comment.getCommentId());
        }
    }

    /**
     * 1번 페이지 수행 결과
     * comment.getCommentId() = 237470119514697728
     * 	comment.getCommentId() = 237470119565029399
     * comment.getCommentId() = 237470119514697729
     * 	comment.getCommentId() = 237470119560835075
     * comment.getCommentId() = 237470119514697730
     * 	comment.getCommentId() = 237470119560835101
     * comment.getCommentId() = 237470119514697731
     * 	comment.getCommentId() = 237470119569223697
     * comment.getCommentId() = 237470119514697732
     * 	comment.getCommentId() = 237470119560835072
     */

    @Test
    void readAllInfiniteScroll() throws Exception {
        List<CommentResponse> response1 = restClient.get()
                .uri("/v2/comments/infinite-scroll?articleId=1&pageSize=5")
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("firstPage");
        for (CommentResponse response : response1) {
            if (!response.getCommentId().equals(response.getParentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("response.getCommentId() = " + response.getCommentId());
        }

        Long lastParentCommentId = response1.getLast().getParentCommentId();
        Long lastCommentId = response1.getLast().getCommentId();


        List<CommentResponse> response2 = restClient.get()
                .uri("/v1/comments/infinite-scroll?articleId=1&pageSize=5&lastParentCommentId=%s&lastCommentId=%s"
                        .formatted(lastParentCommentId, lastCommentId))
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("secondPage");
        for (CommentResponse response : response2) {
            if (!response.getCommentId().equals(response.getParentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("response.getCommentId() = " + response.getCommentId());
        }
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
