package edu.unimagdalena.lms.domine.service.mapper;

import edu.unimagdalena.lms.api.dto.CourseDto.*;
import edu.unimagdalena.lms.domine.entities.Course;
import edu.unimagdalena.lms.domine.entities.Instructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CourseMapperTest {

    private CourseMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(CourseMapper.class);
    }

    @Test
    void toEntity_CreateRequest_ShouldMapCorrectly() {
        UUID instructorId = UUID.randomUUID();
        CourseCreateRequest req = new CourseCreateRequest("Title", "ACTIVE", true, instructorId);
        Course entity = mapper.toEntity(req);

        assertThat(entity).isNotNull();
        assertThat(entity.getTitle()).isEqualTo(req.title());
        assertThat(entity.getInstructor().getId()).isEqualTo(instructorId);
        assertThat(entity.getCreatedAt()).isNotNull();
    }

    @Test
    void toResponse_Entity_ShouldMapCorrectly() {
        UUID instructorId = UUID.randomUUID();
        Instructor instructor = Instructor.builder().id(instructorId).build();
        Course entity = Course.builder()
                .id(UUID.randomUUID())
                .title("Course Title")
                .status("ACTIVE")
                .active(true)
                .instructor(instructor)
                .build();
        CourseResponse response = mapper.toResponse(entity);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(entity.getId());
        assertThat(response.instructorId()).isEqualTo(instructorId);
    }
}
