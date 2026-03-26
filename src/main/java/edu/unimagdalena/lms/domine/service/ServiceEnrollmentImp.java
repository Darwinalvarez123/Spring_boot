package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.EnrollmentDto.*;
import edu.unimagdalena.lms.domine.entities.Enrollment;
import edu.unimagdalena.lms.domine.repository.EnrollmentRepository;
import edu.unimagdalena.lms.domine.service.mapper.EnrollmentMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceEnrollmentImp implements ServiceEnrollment {

    private final EnrollmentRepository repo;
    private final EnrollmentMapper mapper;

    @Override
    public EnrollmentResponse create(EnrollmentCreateRequest req) {
        Enrollment enrollment = mapper.toEntity(req);
        return mapper.toResponse(repo.save(enrollment));
    }

    @Override
    public EnrollmentResponse get(EnrollmentIdRequest req) {
        return repo.findById(req.id())
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
    }

    @Override
    public EnrollmentResponse update(EnrollmentUpdateRequest req) {
        return repo.findById(req.id())
                .map(existing -> {
                    Enrollment toUpdate = mapper.toEntity(req);
                    return mapper.toResponse(repo.save(toUpdate));
                })
                .orElseThrow(() -> new RuntimeException("Enrollment not found to update"));
    }

    @Override
    public List<EnrollmentResponse> getAll() {
        return repo.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Enrollment not found to delete");
        }
        repo.deleteById(id);
    }
}