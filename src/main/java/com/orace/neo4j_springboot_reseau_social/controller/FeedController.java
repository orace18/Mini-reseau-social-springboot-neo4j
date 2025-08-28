package com.orace.neo4j_springboot_reseau_social.controller;

import com.orace.neo4j_springboot_reseau_social.PostView;
import com.orace.neo4j_springboot_reseau_social.dto.CommentDTO;
import com.orace.neo4j_springboot_reseau_social.dto.CreatePostDTO;
import com.orace.neo4j_springboot_reseau_social.repository.PostRepository;
import com.orace.neo4j_springboot_reseau_social.repository.UserRepository;
import com.orace.neo4j_springboot_reseau_social.service.InteractionService;
import com.orace.neo4j_springboot_reseau_social.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FeedController {
    private final PostRepository postRepo;
    private final PostService postService;
    private final InteractionService interactionService;
    private final UserRepository userRepo;

    @PostMapping("/posts")
    public Map<String,String> create(@Valid @RequestBody CreatePostDTO dto) {
        return Map.of("id", postService.create(dto));
    }

    @GetMapping("/feed/{userId}")
    public List<PostView> home(@PathVariable String userId,
                               @RequestParam(defaultValue="0") long skip,
                               @RequestParam(defaultValue="20") int limit) {
        return postRepo.homeFeed(userId, skip, limit);
    }

    @PostMapping("/posts/{postId}/like")
    public void like(@PathVariable String postId, @RequestParam String userId) {
        interactionService.like(userId, postId);
    }

    @DeleteMapping("/posts/{postId}/like")
    public void unlike(@PathVariable String postId, @RequestParam String userId) {
        interactionService.unlike(userId, postId);
    }

    @PostMapping("/posts/{postId}/comments")
    public Map<String,String> comment(@PathVariable String postId, @Valid @RequestBody CommentDTO dto) {
        return Map.of("id", interactionService.comment(dto.userId(), postId, dto.text()));
    }

    @PostMapping("/users/{userId}/follow/{targetId}")
    public void follow(@PathVariable String userId, @PathVariable String targetId) {
        userRepo.follow(userId, targetId);
    }

    @DeleteMapping("/users/{userId}/follow/{targetId}")
    public void unfollow(@PathVariable String userId, @PathVariable String targetId) {
        userRepo.unfollow(userId, targetId);
    }

    @GetMapping("/users/{userId}/suggestions")
    public List<Map<String,Object>> suggestions(@PathVariable String userId,
                                                @RequestParam(defaultValue="10") int limit) {
        return userRepo.suggestions(userId, limit);
    }
}

