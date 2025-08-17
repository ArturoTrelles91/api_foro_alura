package com.aluraforo.api.service.topic;

import com.aluraforo.api.domain.topic.Topic;
import com.aluraforo.api.domain.topic.TopicRepository;
import com.aluraforo.api.domain.topic.TopicStatus;
import com.aluraforo.api.dtos.topic.TopicCreateRequest;
import com.aluraforo.api.dtos.topic.TopicUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TopicService {
    private final TopicRepository repo;
    public TopicService(TopicRepository repo) { this.repo = repo; }

    @Transactional(readOnly = true)
    public List<Topic> listAll() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public Topic getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
    }

    @Transactional
    public Topic create(TopicCreateRequest req) {
        // Regla: no duplicados (título + mensaje)
        if (repo.existsByTitleAndMessage(req.title(), req.message())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate topic (same title and message)");
        }

        Topic t = new Topic();
        t.setTitle(req.title());
        t.setMessage(req.message());
        t.setAuthor(req.author());
        t.setCourse(req.course());
        t.setStatus(TopicStatus.OPEN);
        t.setCreationDate(LocalDateTime.now());

        return repo.save(t);
    }

    @Transactional
    public Topic update(Long id, TopicUpdateRequest req) {
        Topic t = getById(id);

        // Regla: no duplicados (título + mensaje) excluyendo el propio id
        if (repo.existsByTitleAndMessageAndIdNot(req.title(), req.message(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate topic (same title and message)");
        }

        t.setTitle(req.title());
        t.setMessage(req.message());
        t.setCourse(req.course());
        return t; // dirty checking
    }

//    @Transactional
//    public Topic update(Long id, TopicUpdateRequest req) {
//        Topic t = getById(id);
//        t.setTitle(req.title());
//        t.setMessage(req.message());
//        t.setCourse(req.course());
//        // si tu DTO permite parciales, aquí harías null-checks antes de setear
//        return t; // dirty checking
//    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found");
        }
        repo.deleteById(id);
    }
}
