package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.StudentDto;
import edu.unimagdalena.lms.domine.entities.Student;
import edu.unimagdalena.lms.domine.repository.StudentRepository;
import edu.unimagdalena.lms.domine.service.mapper.StudentMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional

public class ServiceStudentImp implements ServiceStudent {

    private final StudentRepository repo;
    private final StudentMapper mapper;

    @Override
    public StudentDto.StudentResponse create(StudentDto.StudentCreateRequest req) {

        repo.findByEmail(req.email()).ifPresent(s -> {
            throw new RuntimeException("Email: " + req.email() + " already exists");
        });

        // Mapeo -> Guardado -> Mapeo de respuesta
        Student student = mapper.toEntity(req);
        Student saved = repo.save(student);
        return mapper.toResponse(saved);
    }

    @Override
    public StudentDto.StudentResponse get(StudentDto.StudentIdRequest req) {
        // Uso de Optional para evitar el if-else manual
        return repo.findById(req.id())
                .map(mapper::toResponse) // Equivale a s -> mapper.toResponse(s)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public StudentDto.StudentResponse update(StudentDto.StudentUpdateRequest req) {
        return repo.findById(req.id())
                .map(existing -> {
                    // Actualizamos los datos
                    Student updated = mapper.toEntity(req);
                    return mapper.toResponse(repo.save(updated));
                })
                .orElseThrow(() -> new RuntimeException("Student not found to update"));
    }

    @Override
    public List<StudentDto.StudentResponse> getAll() {
        return repo.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Student not found to delete");
        }
        repo.deleteById(id);
    }
}