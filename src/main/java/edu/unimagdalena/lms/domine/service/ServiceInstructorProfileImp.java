package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.InstructorProfileDto.*;
import edu.unimagdalena.lms.domine.entities.InstructorProfile;
import edu.unimagdalena.lms.domine.repository.InstructorProfileRepository;
import edu.unimagdalena.lms.domine.service.mapper.InstructorProfileMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceInstructorProfileImp implements ServiceInstructorProfile {

    private final InstructorProfileRepository repo;
    private final InstructorProfileMapper mapper;

    @Override
    public InstructorProfileResponse create(InstructorProfileCreateRequest req) {
        InstructorProfile profile = mapper.toEntity(req);
        return mapper.toResponse(repo.save(profile));
    }

    @Override
    public InstructorProfileResponse get(InstructorProfileIdRequest req) {
        return repo.findById(req.id())
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("InstructorProfile not found"));
    }

    @Override
    public InstructorProfileResponse update(InstructorProfileUpdateRequest req) {
        return repo.findById(req.id())
                .map(existing -> {
                    InstructorProfile toUpdate = mapper.toEntity(req);
                    return mapper.toResponse(repo.save(toUpdate));
                })
                .orElseThrow(() -> new RuntimeException("InstructorProfile not found to update"));
    }

    @Override
    public List<InstructorProfileResponse> getAll() {
        return repo.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("InstructorProfile not found to delete");
        }
        repo.deleteById(id);
    }
}