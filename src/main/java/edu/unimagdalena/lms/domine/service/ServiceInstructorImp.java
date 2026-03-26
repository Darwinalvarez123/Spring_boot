package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.InstructorDto.*;
import edu.unimagdalena.lms.domine.entities.Instructor;
import edu.unimagdalena.lms.domine.repository.InstructorRepository;
import edu.unimagdalena.lms.domine.service.mapper.InstructorMapper; // Asegúrate de que sea la interface
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceInstructorImp implements ServiceInstructor {

    private final InstructorRepository repo;
    private final InstructorMapper mapper;

    @Override
    public InstructorResponse create(InstructorCreateRequest req) {
        repo.findByEmail(req.email()).ifPresent(i -> {
            throw new RuntimeException("Instructor with email " + req.email() + " already exists");
        });

        Instructor instructor = mapper.toEntity(req);
        return mapper.toResponse(repo.save(instructor));
    }

    @Override
    public InstructorResponse get(InstructorIdRequest req) {
        return repo.findById(req.id())
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
    }

    @Override
    public InstructorResponse update(InstructorUpdateRequest req) {
        return repo.findById(req.id())
                .map(existing -> {
                    // Mapeamos los nuevos datos al objeto existente o creamos uno nuevo
                    Instructor toUpdate = mapper.toEntity(req);
                    return mapper.toResponse(repo.save(toUpdate));
                })
                .orElseThrow(() -> new RuntimeException("Instructor not found to update"));
    }

    @Override
    public List<InstructorResponse> list() {
        return repo.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Instructor not found to delete");
        }
        repo.deleteById(id);
    }
}