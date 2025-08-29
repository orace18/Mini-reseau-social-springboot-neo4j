package com.orace.neo4j_springboot_reseau_social.domain;

import org.springframework.data.neo4j.core.schema.*;

@Node
public class Comment {

    @Id @GeneratedValue
    private Long id;

    private String text;

    @Property("createdAt")
    private String createdAt;

    @Relationship(type = "COMMENTED_BY", direction = Relationship.Direction.OUTGOING)
    private User author;

    public Comment() {}
    public Comment(String text, User author, String createdAt) {
        this.text = text;
        this.author = author;
        this.createdAt = createdAt;
    }

    // getters/setters
    public Long getId() { return id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }
}
