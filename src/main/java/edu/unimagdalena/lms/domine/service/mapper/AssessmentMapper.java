package edu.unimagdalena.lms.domine.service.mapper;

import edu.unimagdalena.lms.api.dto.AssessmentDto.*;
import edu.unimagdalena.lms.domine.entities.Assessment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssessmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student.id", source = "studentId")
    @Mapping(target = "course.id", source = "courseId")
    @Mapping(target = "takenAt", expression = "java(java.time.Instant.now())")
    Assessment toEntity(AssessmentCreateRequest req);

    Assessment toEntity(AssessmentUpdateRequest req);

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "courseId", source = "course.id")
    AssessmentResponse toResponse(Assessment entity);
}