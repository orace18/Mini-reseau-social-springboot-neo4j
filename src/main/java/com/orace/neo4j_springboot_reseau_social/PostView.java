package com.orace.neo4j_springboot_reseau_social;

import java.time.Instant;

public interface PostView {
    String getId();
    String getText();
    Instant getCreatedAt();
    String getAuthorId();
    String getAuthorName();
    Long getLikes();
    Long getComments();
    Double getScore();
}

