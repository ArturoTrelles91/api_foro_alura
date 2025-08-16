package com.aluraforo.api.controller.topic;

import com.aluraforo.api.domain.topic.Topic;
import com.aluraforo.api.dtos.topic.TopicCreateRequest;
import com.aluraforo.api.dtos.topic.TopicResponse;
import com.aluraforo.api.dtos.topic.TopicUpdateRequest;
import com.aluraforo.api.service.topic.TopicService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService service;
    public TopicController(TopicService service) { this.service = service; }

    @GetMapping
    public List<TopicResponse> list() {
        return service.listAll().stream().map(TopicResponse::from).toList();
    }

    @GetMapping("/{id}")
    public TopicResponse get(@PathVariable Long id) {
        return TopicResponse.from(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<TopicResponse> create(@RequestBody @Valid TopicCreateRequest req) {
        Topic created = service.create(req);
        return ResponseEntity
                .created(URI.create("/topics/" + created.getId())) // 201 + Location
                .body(TopicResponse.from(created));
    }

    @PutMapping("/{id}")
    public TopicResponse update(@PathVariable Long id, @RequestBody @Valid TopicUpdateRequest req) {
        return TopicResponse.from(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }
}

