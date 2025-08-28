package com.orace.neo4j_springboot_reseau_social.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class InteractionService {
    private final org.springframework.data.neo4j.core.Neo4jClient neo4j;

    @Transactional
    public void like(String userId, String postId) {
        neo4j.query("""
      MATCH (u:User {id:$userId}), (p:Post {id:$postId})
      MERGE (u)-[r:LIKES]->(p)
      ON CREATE SET r.createdAt = datetime()
      """).bindAll(Map.of("userId", userId, "postId", postId)).run();
    }

    @Transactional
    public void unlike(String userId, String postId) {
        neo4j.query("""
      MATCH (u:User {id:$userId})-[r:LIKES]->(p:Post {id:$postId})
      DELETE r
      """).bindAll(Map.of("userId", userId, "postId", postId)).run();
    }

    @Transactional
    public String comment(String userId, String postId, String text) {
        return neo4j.query("""
      MATCH (u:User {id:$userId}), (p:Post {id:$postId})
      CREATE (c:Comment {id: randomUUID(), text:$text, createdAt: datetime()})
      MERGE (u)-[:AUTHORED]->(c)
      MERGE (c)-[:COMMENTED_ON]->(p)
      RETURN c.id AS id
      """).bindAll(Map.of("userId", userId, "postId", postId, "text", text))
                .fetch().one()
                .map(m -> (String)m.get("id")).orElseThrow();
    }
}

