package com.orace.neo4j_springboot_reseau_social.repository;


import com.orace.neo4j_springboot_reseau_social.PostViewDTO;
import com.orace.neo4j_springboot_reseau_social.domain.Post;
import org.springframework.data.neo4j.repository.*;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Map;

public interface PostRepository extends Neo4jRepository<Post, Long> {
    @Query("""
  MATCH (p:Post)
  RETURN p.id AS id, p.content AS content, p.createdAt AS createdAt
  LIMIT 5
  """)
    List<Map<String, Object>> getAllPosts();
}
