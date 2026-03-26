package edu.unimagdalena.lms.domine.service.mapper;

import edu.unimagdalena.lms.api.dto.LessonDto.*;
import edu.unimagdalena.lms.domine.entities.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course.id", source = "courseId")
    Lesson toEntity(LessonCreateRequest req);

    Lesson toEntity(LessonUpdateRequest req);

    @Mapping(target = "courseId", source = "course.id")
    LessonResponse toResponse(Lesson entity);
}