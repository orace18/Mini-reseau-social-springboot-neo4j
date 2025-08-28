package com.orace.neo4j_springboot_reseau_social.repository;

import com.orace.neo4j_springboot_reseau_social.PostView;
import com.orace.neo4j_springboot_reseau_social.domain.Post;
import org.springframework.data.neo4j.repository.*;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PostRepository extends Neo4jRepository<Post, String> {

    @Query("""
  MATCH (me:User {id: $userId})
  OPTIONAL MATCH (me)-[:FOLLOWS]->(a:User)
  WITH collect(a) + me AS authors

  MATCH (a)-[:POSTED]->(p:Post)
  WHERE a IN authors

  // métriques
  OPTIONAL MATCH (p)<-[:LIKES]-(:User)
  WITH p, a, count(*) AS likes
  OPTIONAL MATCH (p)<-[:COMMENTED_ON]-(:Comment)
  WITH p, a, likes, count(*) AS comments

  // score = (likes + 2*comments) / (1 + ageHours)^1.5
  WITH p, a, likes, comments,
       duration.inSeconds(p.createdAt, datetime()).seconds AS ageSec
  WITH p, a, likes, comments,
       (likes*1.0 + comments*2.0) / pow(1 + (ageSec/3600.0), 1.5) AS score

  RETURN p.id AS id, p.text AS text, p.createdAt AS createdAt,
         a.id AS authorId, a.name AS authorName,
         likes AS likes, comments AS comments, score AS score
  ORDER BY score DESC, p.createdAt DESC
  SKIP $skip LIMIT $limit
  """)
    List<PostView> homeFeed(@Param("userId") String userId,
                            @Param("skip") long skip,
                            @Param("limit") int limit);
}
