package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.CourseDto.*;
import edu.unimagdalena.lms.domine.entities.Course;
import edu.unimagdalena.lms.domine.entities.Instructor;
import edu.unimagdalena.lms.domine.repository.CourseRepository;
import edu.unimagdalena.lms.domine.service.mapper.CourseMapper;
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
class ServiceCourseImpTest {

    @Mock
    private CourseRepository repo;

    @Mock
    private CourseMapper mapper;

    @InjectMocks
    private ServiceCourseImp service;

    private Course course;
    private CourseResponse response;
    private UUID instructorId;

    @BeforeEach
    void setUp() {
        instructorId = UUID.randomUUID();
        Instructor instructor = Instructor.builder().id(instructorId).fullName("Test Instructor").email("test@example.com").build();

        course = Course.builder()
                .id(UUID.randomUUID())
                .title("Introduction to Programming")
                .status("ACTIVE")
                .active(true)
                .createdAt(Instant.now())
                .instructor(instructor)
                .build();

        response = new CourseResponse(course.getId(), course.getTitle(), course.getStatus(), course.isActive(), instructorId);
    }

    @Test
    @DisplayName("Debe crear un curso exitosamente")
    void create_ShouldReturnResponse() {
        CourseCreateRequest req = new CourseCreateRequest("Introduction to Programming", "ACTIVE", true, instructorId);
        when(mapper.toEntity(req)).thenReturn(course);
        when(repo.save(any(Course.class))).thenReturn(course);
        when(mapper.toResponse(course)).thenReturn(response);

        CourseResponse result = service.create(req);

        assertThat(result).isNotNull();
        assertThat(result.title()).isEqualTo(req.title());
        verify(repo).save(any(Course.class));
    }

    @Test
    @DisplayName("Debe obtener un curso por id")
    void get_ShouldReturnResponse() {
        CourseIdRequest req = new CourseIdRequest(course.getId());
        when(repo.findById(course.getId())).thenReturn(Optional.of(course));
        when(mapper.toResponse(course)).thenReturn(response);

        CourseResponse result = service.get(req);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(course.getId());
    }

    @Test
    @DisplayName("Debe lanzar una excepción cuando el curso no se encuentra al obtenerlo")
    void get_NotFound_ShouldThrowException() {
        CourseIdRequest req = new CourseIdRequest(UUID.randomUUID());
        when(repo.findById(req.id())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.get(req))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Course not found");
    }

    @Test
    @DisplayName("Debe actualizar un curso exitosamente")
    void update_ShouldReturnResponse() {
        CourseUpdateRequest req = new CourseUpdateRequest(course.getId(), "Updated Title", "INACTIVE", false);
        Course updatedCourse = Course.builder()
                .id(course.getId())
                .title(req.title())
                .status(req.status())
                .active(req.active())
                .instructor(course.getInstructor())
                .build();
        CourseResponse updatedResponse = new CourseResponse(updatedCourse.getId(), updatedCourse.getTitle(), updatedCourse.getStatus(), updatedCourse.isActive(), instructorId);

        when(repo.findById(req.id())).thenReturn(Optional.of(course));
        when(mapper.toEntity(req)).thenReturn(updatedCourse);
        when(repo.save(any(Course.class))).thenReturn(updatedCourse);
        when(mapper.toResponse(updatedCourse)).thenReturn(updatedResponse);

        CourseResponse result = service.update(req);

        assertThat(result).isNotNull();
        assertThat(result.title()).isEqualTo(req.title());
        assertThat(result.status()).isEqualTo(req.status());
        verify(repo).save(any(Course.class));
    }

    @Test
    @DisplayName("Debe lanzar una excepción cuando el curso no se encuentra para actualizar")
    void update_NotFound_ShouldThrowException() {
        CourseUpdateRequest req = new CourseUpdateRequest(UUID.randomUUID(), "Non Existent", "ACTIVE", true);
        when(repo.findById(req.id())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(req))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Course not found to update");
    }

    @Test
    @DisplayName("Debe obtener todos los cursos")
    void getAll_ShouldReturnListOfResponses() {
        Course anotherCourse = Course.builder()
                .id(UUID.randomUUID())
                .title("Advanced Programming")
                .status("ACTIVE")
                .active(true)
                .createdAt(Instant.now())
                .instructor(course.getInstructor())
                .build();
        CourseResponse anotherResponse = new CourseResponse(anotherCourse.getId(), anotherCourse.getTitle(), anotherCourse.getStatus(), anotherCourse.isActive(), instructorId);

        when(repo.findAll()).thenReturn(List.of(course, anotherCourse));
        when(mapper.toResponse(course)).thenReturn(response);
        when(mapper.toResponse(anotherCourse)).thenReturn(anotherResponse);

        List<CourseResponse> result = service.getAll();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(CourseResponse::title).containsExactlyInAnyOrder(course.getTitle(), anotherCourse.getTitle());
    }

    @Test
    @DisplayName("Debe eliminar un curso por id")
    void delete_ShouldCallRepo() {
        UUID id = UUID.randomUUID();
        when(repo.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(repo).deleteById(id);
    }

    @Test
    @DisplayName("Debe lanzar una excepción cuando el curso no se encuentra para eliminar")
    void delete_NotFound_ShouldThrowException() {
        UUID id = UUID.randomUUID();
        when(repo.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(id))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Course not found to delete");
    }
}
