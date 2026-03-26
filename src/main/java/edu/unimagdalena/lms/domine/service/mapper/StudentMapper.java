package edu.unimagdalena.lms.domine.service.mapper;

import edu.unimagdalena.lms.api.dto.StudentDto.StudentCreateRequest;
import edu.unimagdalena.lms.api.dto.StudentDto.StudentResponse;
import edu.unimagdalena.lms.api.dto.StudentDto.StudentUpdateRequest;
import edu.unimagdalena.lms.domine.entities.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fullName", source = "name")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    Student toEntity(StudentCreateRequest req);

    @Mapping(target = "fullName", source = "name")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    Student toEntity(StudentUpdateRequest req);

    @Mapping(target = "name", source = "fullName")
    StudentResponse toResponse(Student entity);
}