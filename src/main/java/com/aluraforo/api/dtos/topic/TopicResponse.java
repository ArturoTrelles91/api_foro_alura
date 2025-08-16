package com.aluraforo.api.dtos.topic;

import com.aluraforo.api.domain.topic.Topic;
import com.aluraforo.api.domain.topic.TopicStatus;
import java.time.LocalDateTime;

public record TopicResponse(
        Long id, String title, String message,
        String author, String course,
        TopicStatus status, LocalDateTime creationDate
) {
    public static TopicResponse from(Topic t) {
        return new TopicResponse(
                t.getId(), t.getTitle(), t.getMessage(),
                t.getAuthor(), t.getCourse(),
                t.getStatus(), t.getCreationDate()
        );
    }
}