package com.aluraforo.api.domain.topic;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "topics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String message;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    private TopicStatus status;

    private String author;

    private String course;

    // Metodo auxiliar opcional para inicializar valores por defecto
    @PrePersist
    public void prePersist() {
        if (creationDate == null) {
            creationDate = LocalDateTime.now();
        }
        if (status == null) {
            status = TopicStatus.OPEN;
        }
    }

}
