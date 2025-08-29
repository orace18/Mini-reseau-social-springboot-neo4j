package com.orace.neo4j_springboot_reseau_social.repository;

import com.orace.neo4j_springboot_reseau_social.domain.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserRepository extends Neo4jRepository<User, Long> {

    // Follow
    @Query("""
    MATCH (u:User {userId:$userId}), (t:User {userId:$targetId})
    MERGE (u)-[r:FOLLOWS]->(t)
    ON CREATE SET r.createdAt = datetime()
    RETURN count(r) > 0
""")
    boolean follow(@Param("userId") String userId, @Param("targetId") String targetId);

    // Unfollow
    @Query("""
    MATCH (u:User {userId:$userId})-[r:FOLLOWS]->(t:User {userId:$targetId})
    DELETE r
""")
    void unfollow(@Param("userId") String userId, @Param("targetId") String targetId);

    // Suggestions "friends-of-friends"
    @Query("""
MATCH (u:User {id: $userId})-[:FRIEND_WITH*1..2]-(suggested:User)
WHERE NOT (u)-[:FRIEND_WITH]-(suggested)
  AND u.id <> suggested.id
RETURN DISTINCT suggested
LIMIT 10
""")
    List<User> suggestFriends(@Param("userId") Long userId);

}
