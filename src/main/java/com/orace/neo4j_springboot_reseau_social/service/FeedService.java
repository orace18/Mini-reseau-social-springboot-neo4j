package com.orace.neo4j_springboot_reseau_social.service;


import com.orace.neo4j_springboot_reseau_social.PostViewDTO;
import com.orace.neo4j_springboot_reseau_social.domain.*;
import com.orace.neo4j_springboot_reseau_social.repository.*;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final Neo4jClient neo4jClient;

    public FeedService(UserRepository userRepository,
                       PostRepository postRepository,
                       CommentRepository commentRepository,
                       Neo4jClient neo4jClient) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.neo4jClient = neo4jClient;
    }

    // Créer un post
    @Transactional
    public Post createPost(Long userId, String content) {
        User author = userRepository.findById(userId).orElseThrow();
        Post post = new Post(content, author, LocalDateTime.now().toString());
        // Sauvegarde du post : SDN va créer automatiquement la relation CREATED_BY
        return postRepository.save(post);
    }


    // Liker un post
    @Transactional
    public void likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post introuvable"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        if (!post.getLikes().contains(user)) {
            post.getLikes().add(user);
            postRepository.save(post);
        }
    }

    // Commenter un post
    @Transactional
    public Comment commentPost(Long postId, Long userId, String text) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post introuvable"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Comment comment = new Comment(text, user, LocalDateTime.now().toString());
        commentRepository.save(comment);

        post.getComments().add(comment);
        postRepository.save(post);

        return comment;
    }

    public List<Map<String, Object>> getFeed(Long userId) {
        return (List<Map<String, Object>>) neo4jClient.query("""
            MATCH (p:Post)
            RETURN p.id AS id, p.content AS content, p.createdAt AS createdAt
            LIMIT 5
            """)
                .fetch()
                .all();
    }
}
