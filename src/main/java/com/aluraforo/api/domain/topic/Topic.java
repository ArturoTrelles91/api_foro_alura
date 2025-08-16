package com.aluraforo.api.domain.topic;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "topics",
        uniqueConstraints = @UniqueConstraint(
                name = "ux_topics_title_message",
                columnNames = {"title","message"}
        ),
        indexes = {
                @Index(name = "ix_topics_author", columnList = "author"),
                @Index(name = "ix_topics_course", columnList = "course")
        }
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Topic {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 4000)
    private String message;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private TopicStatus status;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(nullable = false, length = 100)
    private String course;

    @PrePersist
    public void prePersist() {
        if (creationDate == null) creationDate = LocalDateTime.now();
        if (status == null) status = TopicStatus.OPEN;
    }
}
