package edu.unimagdalena.lms.domine.service.mapper;

import edu.unimagdalena.lms.api.dto.EnrollmentDto.*;
import edu.unimagdalena.lms.domine.entities.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student.id", source = "studentId")
    @Mapping(target = "course.id", source = "courseId")
    @Mapping(target = "enrolledAt", expression = "java(java.time.Instant.now())")
    Enrollment toEntity(EnrollmentCreateRequest req);

    Enrollment toEntity(EnrollmentUpdateRequest req);

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "courseId", source = "course.id")
    EnrollmentResponse toResponse(Enrollment entity);
}