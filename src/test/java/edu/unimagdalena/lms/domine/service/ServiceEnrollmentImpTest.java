package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.EnrollmentDto.*;
import edu.unimagdalena.lms.domine.entities.Course;
import edu.unimagdalena.lms.domine.entities.Enrollment;
import edu.unimagdalena.lms.domine.entities.Student;
import edu.unimagdalena.lms.domine.repository.EnrollmentRepository;
import edu.unimagdalena.lms.domine.service.mapper.EnrollmentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceEnrollmentImpTest {

    @Mock
    private EnrollmentRepository repo;

    @Mock
    private EnrollmentMapper mapper;

    @InjectMocks
    private ServiceEnrollmentImp service;

    private Enrollment enrollment;
    private EnrollmentResponse response;
    private UUID studentId;
    private UUID courseId;

    @BeforeEach
    void setUp() {
        studentId = UUID.randomUUID();
        courseId = UUID.randomUUID();
        Student student = Student.builder().id(studentId).fullName("Ana García").email("ana@email.com").build();
        Course course = Course.builder().id(courseId).title("Matemáticas").build();

        enrollment = Enrollment.builder()
                .id(UUID.randomUUID())
                .status("ACTIVE")
                .enrolledAt(Instant.now())
                .student(student)
                .course(course)
                .build();

        response = new EnrollmentResponse(enrollment.getId(), enrollment.getStatus(), enrollment.getEnrolledAt(), studentId, courseId);
    }

    @Test
    @DisplayName("Debe crear una inscripción exitosamente")
    void create_ShouldReturnResponse() {
        EnrollmentCreateRequest req = new EnrollmentCreateRequest("ACTIVE", studentId, courseId);
        when(mapper.toEntity(req)).thenReturn(enrollment);
        when(repo.save(any(Enrollment.class))).thenReturn(enrollment);
        when(mapper.toResponse(enrollment)).thenReturn(response);

        EnrollmentResponse result = service.create(req);

        assertThat(result).isNotNull();
        assertThat(result.status()).isEqualTo(req.status());
        verify(repo).save(any(Enrollment.class));
    }

    @Test
    @DisplayName("Debe obtener una inscripción por id")
    void get_ShouldReturnResponse() {
        EnrollmentIdRequest req = new EnrollmentIdRequest(enrollment.getId());
        when(repo.findById(enrollment.getId())).thenReturn(Optional.of(enrollment));
        when(mapper.toResponse(enrollment)).thenReturn(response);

        EnrollmentResponse result = service.get(req);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(enrollment.getId());
    }

    @Test
    @DisplayName("Debe actualizar una inscripción exitosamente")
    void update_ShouldReturnResponse() {
        EnrollmentUpdateRequest req = new EnrollmentUpdateRequest(enrollment.getId(), "COMPLETED");
        Enrollment updatedEnrollment = Enrollment.builder()
                .id(enrollment.getId())
                .status(req.status())
                .student(enrollment.getStudent())
                .course(enrollment.getCourse())
                .build();
        EnrollmentResponse updatedResponse = new EnrollmentResponse(updatedEnrollment.getId(), updatedEnrollment.getStatus(), enrollment.getEnrolledAt(), studentId, courseId);

        when(repo.findById(req.id())).thenReturn(Optional.of(enrollment));
        when(mapper.toEntity(req)).thenReturn(updatedEnrollment);
        when(repo.save(any(Enrollment.class))).thenReturn(updatedEnrollment);
        when(mapper.toResponse(updatedEnrollment)).thenReturn(updatedResponse);

        EnrollmentResponse result = service.update(req);

        assertThat(result).isNotNull();
        assertThat(result.status()).isEqualTo(req.status());
        verify(repo).save(any(Enrollment.class));
    }

    @Test
    @DisplayName("Debe obtener todas las inscripciones")
    void getAll_ShouldReturnListOfResponses() {
        Enrollment anotherEnrollment = Enrollment.builder()
                .id(UUID.randomUUID())
                .status("INACTIVE")
                .enrolledAt(Instant.now())
                .student(enrollment.getStudent())
                .course(enrollment.getCourse())
                .build();
        EnrollmentResponse anotherResponse = new EnrollmentResponse(anotherEnrollment.getId(), anotherEnrollment.getStatus(), anotherEnrollment.getEnrolledAt(), studentId, courseId);

        when(repo.findAll()).thenReturn(List.of(enrollment, anotherEnrollment));
        when(mapper.toResponse(enrollment)).thenReturn(response);
        when(mapper.toResponse(anotherEnrollment)).thenReturn(anotherResponse);

        List<EnrollmentResponse> result = service.getAll();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(EnrollmentResponse::status).containsExactlyInAnyOrder(enrollment.getStatus(), anotherEnrollment.getStatus());
    }

    @Test
    @DisplayName("Debe eliminar una inscripción por id")
    void delete_ShouldCallRepo() {
        UUID id = UUID.randomUUID();
        when(repo.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(repo).deleteById(id);
    }
}
