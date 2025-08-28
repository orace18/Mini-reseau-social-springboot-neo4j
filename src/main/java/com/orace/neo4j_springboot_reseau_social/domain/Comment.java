package com.orace.neo4j_springboot_reseau_social.domain;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.Instant;

@Node("Comment")
public class Comment {
    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;
    private String text;
    private Instant createdAt = Instant.now();

    @Relationship(type = "AUTHORED", direction = Relationship.Direction.INCOMING)
    private User author;

    @Relationship(type = "COMMENT_ON")
    private Post post;
}
