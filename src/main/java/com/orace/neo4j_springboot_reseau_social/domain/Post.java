package com.orace.neo4j_springboot_reseau_social.domain;

import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.Set;

@Node
public class Post {

    @Id @GeneratedValue
    private Long id;

    private String content;

    @Property("createdAt")
    private String createdAt;

    @Relationship(type = "CREATED_BY", direction = Relationship.Direction.OUTGOING)
    private User author;



    @Relationship(type = "LIKED_BY", direction = Relationship.Direction.INCOMING)
    private Set<User> likes = new HashSet<>();

    @Relationship(type = "COMMENTED_BY", direction = Relationship.Direction.INCOMING)
    private Set<Comment> comments = new HashSet<>();

    public Post() {}
    public Post(String content, User author, String createdAt) {
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
    }

    // getters/setters
    public Long getId() { return id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }
    public Set<User> getLikes() { return likes; }
    public Set<Comment> getComments() { return comments; }
}
