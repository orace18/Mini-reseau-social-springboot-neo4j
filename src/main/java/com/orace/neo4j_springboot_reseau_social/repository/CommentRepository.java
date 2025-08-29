package com.orace.neo4j_springboot_reseau_social.repository;

import com.orace.neo4j_springboot_reseau_social.domain.Comment;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CommentRepository extends Neo4jRepository<Comment, Long> {
}
