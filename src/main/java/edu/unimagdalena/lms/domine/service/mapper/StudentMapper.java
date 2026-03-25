package edu.unimagdalena.lms.domine.service.mapper;

import edu.unimagdalena.lms.api.dto.StudentDto.*;
import edu.unimagdalena.lms.domine.entities.Student;
import java.time.Instant;

public class StudentMapper {

    public static Student toEntity(StudentCreateRequest req) {
        return Student.builder()
                .fullName(req.name())
                .email(req.email())
                .createdAt(Instant.now())
                .build();
    }

    public static Student toEntity(StudentUpdateRequest req) {
        return Student.builder()
                .id(req.id())
                .fullName(req.name())
                .email(req.email())
                .updatedAt(Instant.now())
                .build();
    }


    public static StudentResponse toResponse(Student entity) {
        return new StudentResponse(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail()
        );
    }
}