package edu.unimagdalena.lms.domine.service.mapper;

import edu.unimagdalena.lms.api.dto.CourseDto.*;
import edu.unimagdalena.lms.domine.entities.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instructor.id", source = "instructorId")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    Course toEntity(CourseCreateRequest req);

    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    Course toEntity(CourseUpdateRequest req);

    @Mapping(target = "instructorId", source = "instructor.id")
    CourseResponse toResponse(Course entity);
}