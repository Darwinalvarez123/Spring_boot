package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.LessonDto.*;
import edu.unimagdalena.lms.domine.entities.Lesson;
import edu.unimagdalena.lms.domine.repository.LessonRepository;
import edu.unimagdalena.lms.domine.service.mapper.LessonMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceLessonImp implements ServiceLesson {

    private final LessonRepository repo;
    private final LessonMapper mapper;

    @Override
    public LessonResponse create(LessonCreateRequest req) {
        Lesson lesson = mapper.toEntity(req);
        return mapper.toResponse(repo.save(lesson));
    }

    @Override
    public LessonResponse get(LessonIdRequest req) {
        return repo.findById(req.id())
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
    }

    @Override
    public LessonResponse update(LessonUpdateRequest req) {
        return repo.findById(req.id())
                .map(existing -> {
                    Lesson toUpdate = mapper.toEntity(req);
                    return mapper.toResponse(repo.save(toUpdate));
                })
                .orElseThrow(() -> new RuntimeException("Lesson not found to update"));
    }

    @Override
    public List<LessonResponse> getAll() {
        return repo.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Lesson not found to delete");
        }
        repo.deleteById(id);
    }
}