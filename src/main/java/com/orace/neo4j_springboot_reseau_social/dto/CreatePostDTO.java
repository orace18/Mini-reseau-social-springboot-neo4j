package com.orace.neo4j_springboot_reseau_social.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePostDTO(
        @NotBlank String authorId,
        @NotBlank @Size(max=1000) String text
) {}


