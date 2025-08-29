package com.orace.neo4j_springboot_reseau_social.service;

import com.orace.neo4j_springboot_reseau_social.dto.CreatePostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostService {
    private final Neo4jClient neo4j;

    @Transactional
    public String create(CreatePostDTO dto) {
        return neo4j.query("""
        MATCH (u:User {id:$authorId})
        CREATE (p:Post {id: randomUUID(), content:$text, createdAt: datetime()})
        MERGE (u)-[:CREATED_BY]->(p)
        RETURN p.id AS id
    """).bindAll(Map.of("authorId", dto.authorId(), "text", dto.text()))
                .fetch().one().map(r -> (String) r.get("id")).orElseThrow();
    }

}

