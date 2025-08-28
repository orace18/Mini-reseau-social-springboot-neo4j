package com.orace.neo4j_springboot_reseau_social.domain;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Node("User")
public class User {
    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;
    private String username;
    private String name;
    private Instant createdAt =  Instant.now();


    @Relationship(type = "FOLLOWS", direction = Relationship.Direction.OUTGOING)
    Set<Follow>  follows = new HashSet<>();
}
