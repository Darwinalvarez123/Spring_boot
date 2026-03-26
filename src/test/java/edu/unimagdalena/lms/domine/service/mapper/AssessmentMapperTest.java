package edu.unimagdalena.lms.domine.service.mapper;

import edu.unimagdalena.lms.api.dto.AssessmentDto.*;
import edu.unimagdalena.lms.domine.entities.Assessment;
import edu.unimagdalena.lms.domine.entities.Course;
import edu.unimagdalena.lms.domine.entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AssessmentMapperTest {

    private AssessmentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(AssessmentMapper.class);
    }

    @Test
    void toEntity_CreateRequest_ShouldMapCorrectly() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        AssessmentCreateRequest req = new AssessmentCreateRequest("QUIZ", 90, studentId, courseId);
        Assessment entity = mapper.toEntity(req);

        assertThat(entity).isNotNull();
        assertThat(entity.getType()).isEqualTo(req.type());
        assertThat(entity.getStudent().getId()).isEqualTo(studentId);
        assertThat(entity.getCourse().getId()).isEqualTo(courseId);
        assertThat(entity.getTakenAt()).isNotNull();
    }

    @Test
    void toResponse_Entity_ShouldMapCorrectly() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        Student student = Student.builder().id(studentId).build();
        Course course = Course.builder().id(courseId).build();
        Assessment entity = Assessment.builder()
                .id(UUID.randomUUID())
                .type("EXAM")
                .score(85)
                .takenAt(Instant.now())
                .student(student)
                .course(course)
                .build();
        AssessmentResponse response = mapper.toResponse(entity);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(entity.getId());
        assertThat(response.studentId()).isEqualTo(studentId);
        assertThat(response.courseId()).isEqualTo(courseId);
    }
}
