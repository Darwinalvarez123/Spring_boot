package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.CourseDto.*;
import edu.unimagdalena.lms.domine.entities.Course;
import edu.unimagdalena.lms.domine.repository.CourseRepository;
import edu.unimagdalena.lms.domine.service.mapper.CourseMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceCourseImp implements ServiceCourse {

    private final CourseRepository repo;
    private final CourseMapper mapper;

    @Override
    public CourseResponse create(CourseCreateRequest req) {
        Course course = mapper.toEntity(req);
        return mapper.toResponse(repo.save(course));
    }

    @Override
    public CourseResponse get(CourseIdRequest req) {
        return repo.findById(req.id())
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public CourseResponse update(CourseUpdateRequest req) {
        return repo.findById(req.id())
                .map(existing -> {
                    Course toUpdate = mapper.toEntity(req);
                    return mapper.toResponse(repo.save(toUpdate));
                })
                .orElseThrow(() -> new RuntimeException("Course not found to update"));
    }

    @Override
    public List<CourseResponse> getAll() {
        return repo.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Course not found to delete");
        }
        repo.deleteById(id);
    }
}