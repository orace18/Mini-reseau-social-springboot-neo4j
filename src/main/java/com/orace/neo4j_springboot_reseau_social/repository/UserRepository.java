package com.orace.neo4j_springboot_reseau_social.repository;

import com.orace.neo4j_springboot_reseau_social.domain.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface UserRepository extends Neo4jRepository<User, String> {

    // Follow
    @Query("""
  MATCH (u:User {id:$userId}), (t:User {id:$targetId})
  MERGE (u)-[r:FOLLOWS]->(t)
  ON CREATE SET r.createdAt = datetime()
  RETURN count(r) > 0
  """)
    boolean follow(@Param("userId") String userId, @Param("targetId") String targetId);

    // Unfollow
    @Query("""
  MATCH (u:User {id:$userId})-[r:FOLLOWS]->(t:User {id:$targetId})
  DELETE r
  """)
    void unfollow(@Param("userId") String userId, @Param("targetId") String targetId);

    // Suggestions "friends-of-friends"
    @Query("""
  MATCH (me:User {id:$userId})-[:FOLLOWS]->(:User)-[:FOLLOWS]->(s:User)
  WHERE NOT (me)-[:FOLLOWS]->(s) AND s.id <> $userId
  RETURN s.id AS id, s.username AS username, s.name AS name, count(*) AS score
  ORDER BY score DESC
  LIMIT $limit
  """)
    List<Map<String,Object>> suggestions(@Param("userId") String userId, @Param("limit") int limit);
}
