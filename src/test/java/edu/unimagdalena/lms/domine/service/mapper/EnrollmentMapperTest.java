package edu.unimagdalena.lms.domine.service.mapper;

import edu.unimagdalena.lms.api.dto.EnrollmentDto.*;
import edu.unimagdalena.lms.domine.entities.Course;
import edu.unimagdalena.lms.domine.entities.Enrollment;
import edu.unimagdalena.lms.domine.entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class EnrollmentMapperTest {

    private EnrollmentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(EnrollmentMapper.class);
    }

    @Test
    void toEntity_CreateRequest_ShouldMapCorrectly() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        EnrollmentCreateRequest req = new EnrollmentCreateRequest("ACTIVE", studentId, courseId);
        Enrollment entity = mapper.toEntity(req);

        assertThat(entity).isNotNull();
        assertThat(entity.getStatus()).isEqualTo(req.status());
        assertThat(entity.getStudent().getId()).isEqualTo(studentId);
        assertThat(entity.getCourse().getId()).isEqualTo(courseId);
        assertThat(entity.getEnrolledAt()).isNotNull();
    }

    @Test
    void toResponse_Entity_ShouldMapCorrectly() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        Student student = Student.builder().id(studentId).build();
        Course course = Course.builder().id(courseId).build();
        Enrollment entity = Enrollment.builder()
                .id(UUID.randomUUID())
                .status("COMPLETED")
                .enrolledAt(Instant.now())
                .student(student)
                .course(course)
                .build();
        EnrollmentResponse response = mapper.toResponse(entity);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(entity.getId());
        assertThat(response.studentId()).isEqualTo(studentId);
        assertThat(response.courseId()).isEqualTo(courseId);
    }
}
