package com.aluraforo.api.dtos.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TopicUpdateRequest(
        @NotBlank @Size(max=140) String title,
        @NotBlank @Size(max=4000) String message,
        @NotBlank @Size(max=120) String course
) {}
