package com.aluraforo.api.domain.topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    boolean existsByTitleAndMessage(String title, String message);
    // opcional si quieres ignorar mayúsculas/minúsculas:
    boolean existsByTitleIgnoreCaseAndMessageIgnoreCase(String title, String message);
    boolean existsByTitleAndMessageAndIdNot(String title, String message, Long id);
}
