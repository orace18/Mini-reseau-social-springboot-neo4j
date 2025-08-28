package com.orace.neo4j_springboot_reseau_social.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentDTO(@NotBlank String userId, @NotBlank String text) {}

