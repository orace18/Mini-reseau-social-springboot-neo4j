package com.orace.neo4j_springboot_reseau_social.controller;


import com.orace.neo4j_springboot_reseau_social.PostViewDTO;
import com.orace.neo4j_springboot_reseau_social.domain.*;
import com.orace.neo4j_springboot_reseau_social.dto.PostRequest;
import com.orace.neo4j_springboot_reseau_social.exeception.ApiResponse;
import com.orace.neo4j_springboot_reseau_social.service.FeedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @PostMapping("/{userId}/posts")
    public ResponseEntity<?> createPost(@PathVariable Long userId, @RequestBody PostRequest request) {
        Post post = feedService.createPost(userId, request.content);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Post créé",
                "data", post
        ));
    }


    @PostMapping("/{userId}/posts/{postId}/like")
    public ResponseEntity<ApiResponse<String>> likePost(@PathVariable Long userId, @PathVariable Long postId) {
        feedService.likePost(postId, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Post liké", null));
    }

    @PostMapping("/{userId}/posts/{postId}/comment")
    public ResponseEntity<ApiResponse<Comment>> commentPost(@PathVariable Long userId,
                                                            @PathVariable Long postId,
                                                            @RequestBody String text) {
        Comment comment = feedService.commentPost(postId, userId, text);
        return ResponseEntity.ok(new ApiResponse<>(true, "Commentaire ajouté", comment));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getFeed(@PathVariable Long userId) {
        List<Map<String, Object>> feed = feedService.getFeed(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Feed récupéré", feed));
    }
}
