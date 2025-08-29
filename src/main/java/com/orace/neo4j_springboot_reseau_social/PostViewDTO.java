package com.orace.neo4j_springboot_reseau_social;

public class PostViewDTO {
    private Long id;
    private String content;
    private String createdAt;
    private Long authorId;
    private String authorName;
    private Integer likes;
    private Integer comments;

    // Constructeurs
    public PostViewDTO() {}

    public PostViewDTO(Long id, String content, String createdAt, Long authorId, String authorName, Integer likes, Integer comments) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.authorName = authorName;
        this.likes = likes;
        this.comments = comments;
    }

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public Integer getLikes() { return likes; }
    public void setLikes(Integer likes) { this.likes = likes; }

    public Integer getComments() { return comments; }
    public void setComments(Integer comments) { this.comments = comments; }
}