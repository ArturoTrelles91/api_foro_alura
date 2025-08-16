package com.aluraforo.api.service.topic;

import com.aluraforo.api.domain.topic.Topic;
import com.aluraforo.api.dtos.topic.TopicCreateRequest;
import com.aluraforo.api.domain.topic.TopicRepository;
import com.aluraforo.api.dtos.topic.TopicUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TopicService {
    private final TopicRepository repo;
    public TopicService(TopicRepository repo) { this.repo = repo; }

    public List<Topic> listAll() { return repo.findAll(); }

    public Topic getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Topic not found"));
    }

    @Transactional
    public Topic create(TopicCreateRequest req) {
        Topic t = new Topic();
        t.setTitle(req.title());
        t.setMessage(req.message());
        t.setAuthor(req.author());
        t.setCourse(req.course());
        return repo.save(t);
    }

    @Transactional
    public Topic update(Long id, TopicUpdateRequest req) {
        Topic t = getById(id);
        t.setTitle(req.title());
        t.setMessage(req.message());
        t.setCourse(req.course());
        return t; // JPA dirty checking guarda el cambio
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new RuntimeException("Topic not found");
        repo.deleteById(id);
    }
}
