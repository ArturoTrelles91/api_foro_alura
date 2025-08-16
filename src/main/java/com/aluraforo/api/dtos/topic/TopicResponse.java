package com.aluraforo.api.dtos.topic;

import com.aluraforo.api.domain.topic.Topic;
import com.aluraforo.api.domain.topic.TopicStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;

@JsonPropertyOrder({ "id", "title", "message", "creationDate", "status", "author", "course" })
public record TopicResponse(
        Long id,
        String title,
        String message,
        LocalDateTime creationDate,
        TopicStatus status,
        String author,
        String course
) {
    public static TopicResponse from(Topic t) {
        return new TopicResponse(
                t.getId(),
                t.getTitle(),
                t.getMessage(),
                t.getCreationDate(),
                t.getStatus(),
                t.getAuthor(),
                t.getCourse()
        );
    }
}
