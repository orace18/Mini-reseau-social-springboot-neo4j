package com.orace.neo4j_springboot_reseau_social.domain;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.Instant;

@RelationshipProperties
public class Follow {
    @Id
    @GeneratedValue
    private Long id;
    private Instant createdAt = Instant.now();

    @TargetNode
    private User target;
}
