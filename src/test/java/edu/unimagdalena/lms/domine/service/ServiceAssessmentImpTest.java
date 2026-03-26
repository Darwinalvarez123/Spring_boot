package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.AssessmentDto.*;
import edu.unimagdalena.lms.domine.entities.Assessment;
import edu.unimagdalena.lms.domine.entities.Course;
import edu.unimagdalena.lms.domine.entities.Student;
import edu.unimagdalena.lms.domine.repository.AssessmentRepository;
import edu.unimagdalena.lms.domine.service.mapper.AssessmentMapper;
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
class ServiceAssessmentImpTest {

    @Mock
    private AssessmentRepository repo;

    @Mock
    private AssessmentMapper mapper;

    @InjectMocks
    private ServiceAssessmentImp service;

    private Assessment assessment;
    private AssessmentResponse response;
    private UUID studentId;
    private UUID courseId;

    @BeforeEach
    void setUp() {
        studentId = UUID.randomUUID();
        courseId = UUID.randomUUID();
        Student student = Student.builder().id(studentId).fullName("Ana García").email("ana@email.com").build();
        Course course = Course.builder().id(courseId).title("Matemáticas").build();

        assessment = Assessment.builder()
                .id(UUID.randomUUID())
                .type("QUIZ")
                .score(85)
                .takenAt(Instant.now())
                .student(student)
                .course(course)
                .build();

        response = new AssessmentResponse(assessment.getId(), assessment.getType(), assessment.getScore(), assessment.getTakenAt(), studentId, courseId);
    }

    @Test
    @DisplayName("Debe crear una evaluación exitosamente")
    void create_ShouldReturnResponse() {
        AssessmentCreateRequest req = new AssessmentCreateRequest("QUIZ", 85, studentId, courseId);
        when(mapper.toEntity(req)).thenReturn(assessment);
        when(repo.save(any(Assessment.class))).thenReturn(assessment);
        when(mapper.toResponse(assessment)).thenReturn(response);

        AssessmentResponse result = service.create(req);

        assertThat(result).isNotNull();
        assertThat(result.type()).isEqualTo(req.type());
        verify(repo).save(any(Assessment.class));
    }

    @Test
    @DisplayName("Debe obtener una evaluación por id")
    void get_ShouldReturnResponse() {
        AssessmentIdRequest req = new AssessmentIdRequest(assessment.getId());
        when(repo.findById(assessment.getId())).thenReturn(Optional.of(assessment));
        when(mapper.toResponse(assessment)).thenReturn(response);

        AssessmentResponse result = service.get(req);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(assessment.getId());
    }

    @Test
    @DisplayName("Debe actualizar una evaluación exitosamente")
    void update_ShouldReturnResponse() {
        AssessmentUpdateRequest req = new AssessmentUpdateRequest(assessment.getId(), "FINAL EXAM", 95);
        Assessment updatedAssessment = Assessment.builder()
                .id(assessment.getId())
                .type(req.type())
                .score(req.score())
                .student(assessment.getStudent())
                .course(assessment.getCourse())
                .build();
        AssessmentResponse updatedResponse = new AssessmentResponse(updatedAssessment.getId(), updatedAssessment.getType(), updatedAssessment.getScore(), assessment.getTakenAt(), studentId, courseId);

        when(repo.findById(req.id())).thenReturn(Optional.of(assessment));
        when(mapper.toEntity(req)).thenReturn(updatedAssessment);
        when(repo.save(any(Assessment.class))).thenReturn(updatedAssessment);
        when(mapper.toResponse(updatedAssessment)).thenReturn(updatedResponse);

        AssessmentResponse result = service.update(req);

        assertThat(result).isNotNull();
        assertThat(result.type()).isEqualTo(req.type());
        assertThat(result.score()).isEqualTo(req.score());
        verify(repo).save(any(Assessment.class));
    }

    @Test
    @DisplayName("Debe obtener todas las evaluaciones")
    void getAll_ShouldReturnListOfResponses() {
        Assessment anotherAssessment = Assessment.builder()
                .id(UUID.randomUUID())
                .type("HOMEWORK")
                .score(90)
                .takenAt(Instant.now())
                .student(assessment.getStudent())
                .course(assessment.getCourse())
                .build();
        AssessmentResponse anotherResponse = new AssessmentResponse(anotherAssessment.getId(), anotherAssessment.getType(), anotherAssessment.getScore(), anotherAssessment.getTakenAt(), studentId, courseId);

        when(repo.findAll()).thenReturn(List.of(assessment, anotherAssessment));
        when(mapper.toResponse(assessment)).thenReturn(response);
        when(mapper.toResponse(anotherAssessment)).thenReturn(anotherResponse);

        List<AssessmentResponse> result = service.getAll();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(AssessmentResponse::type).containsExactlyInAnyOrder(assessment.getType(), anotherAssessment.getType());
    }

    @Test
    @DisplayName("Debe eliminar una evaluación por id")
    void delete_ShouldCallRepo() {
        UUID id = UUID.randomUUID();
        when(repo.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(repo).deleteById(id);
    }
}
