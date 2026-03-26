package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.AssessmentDto.*;
import edu.unimagdalena.lms.domine.entities.Assessment;
import edu.unimagdalena.lms.domine.repository.AssessmentRepository;
import edu.unimagdalena.lms.domine.service.mapper.AssessmentMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceAssessmentImp implements ServiceAssessment {

    private final AssessmentRepository repo;
    private final AssessmentMapper mapper;

    @Override
    public AssessmentResponse create(AssessmentCreateRequest req) {
        Assessment assessment = mapper.toEntity(req);
        return mapper.toResponse(repo.save(assessment));
    }

    @Override
    public AssessmentResponse get(AssessmentIdRequest req) {
        return repo.findById(req.id())
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));
    }

    @Override
    public AssessmentResponse update(AssessmentUpdateRequest req) {
        return repo.findById(req.id())
                .map(existing -> {
                    Assessment toUpdate = mapper.toEntity(req);
                    return mapper.toResponse(repo.save(toUpdate));
                })
                .orElseThrow(() -> new RuntimeException("Assessment not found to update"));
    }

    @Override
    public List<AssessmentResponse> getAll() {
        return repo.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Assessment not found to delete");
        }
        repo.deleteById(id);
    }
}