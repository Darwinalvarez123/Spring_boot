package edu.unimagdalena.lms.domine.service.mapper;

import edu.unimagdalena.lms.api.dto.LessonDto.*;
import edu.unimagdalena.lms.domine.entities.Course;
import edu.unimagdalena.lms.domine.entities.Lesson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LessonMapperTest {

    private LessonMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(LessonMapper.class);
    }

    @Test
    void toEntity_CreateRequest_ShouldMapCorrectly() {
        UUID courseId = UUID.randomUUID();
        LessonCreateRequest req = new LessonCreateRequest("Lesson Title", 1, courseId);
        Lesson entity = mapper.toEntity(req);

        assertThat(entity).isNotNull();
        assertThat(entity.getTitle()).isEqualTo(req.title());
        assertThat(entity.getCourse().getId()).isEqualTo(courseId);
    }

    @Test
    void toResponse_Entity_ShouldMapCorrectly() {
        UUID courseId = UUID.randomUUID();
        Course course = Course.builder().id(courseId).build();
        Lesson entity = Lesson.builder()
                .id(UUID.randomUUID())
                .title("Lesson Title")
                .orderIndex(1)
                .course(course)
                .build();
        LessonResponse response = mapper.toResponse(entity);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(entity.getId());
        assertThat(response.courseId()).isEqualTo(courseId);
    }
}
