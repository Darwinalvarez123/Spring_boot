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
public class ServiceStudentImp implements ServiceStudent{
    private  final StudentRepository repo;

    @Override
    public StudentDto.StudentResponse create(StudentDto.StudentCreateRequest req) {

        if (repo.findByEmail(req.email()).isPresent())
        {
            throw new RuntimeException("Email already exists");
        }
        Student student = repo.save(StudentMapper.toEntity(req));
        return StudentMapper.toResponse(student);
    }

    @Override
    public StudentDto.StudentResponse get(StudentDto.StudentIdRequest req) {
        var studentOptional = repo.findById(req.id());
        if (studentOptional.isEmpty()) {
            throw new RuntimeException("Student not found");
        }
        Student student = studentOptional.get();
        return StudentMapper.toResponse(student);
    }

    @Override
    public StudentDto.StudentResponse update(StudentDto.StudentUpdateRequest req) {
        return null;
    }

    @Override
    public List<StudentDto.StudentResponse> getAll() {
        return List.of();
    }

    @Override
    public void delete(UUID id) {

    }
}
