package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.LessonDto.*;
import edu.unimagdalena.lms.domine.entities.Course;
import edu.unimagdalena.lms.domine.entities.Lesson;
import edu.unimagdalena.lms.domine.repository.LessonRepository;
import edu.unimagdalena.lms.domine.service.mapper.LessonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceLessonImpTest {

    @Mock
    private LessonRepository repo;

    @Mock
    private LessonMapper mapper;

    @InjectMocks
    private ServiceLessonImp service;

    private Lesson lesson;
    private LessonResponse response;
    private UUID courseId;

    @BeforeEach
    void setUp() {
        courseId = UUID.randomUUID();
        Course course = Course.builder().id(courseId).title("Test Course").build();

        lesson = Lesson.builder()
                .id(UUID.randomUUID())
                .title("Introduction to Java")
                .orderIndex(1)
                .course(course)
                .build();

        response = new LessonResponse(lesson.getId(), lesson.getTitle(), lesson.getOrderIndex(), courseId);
    }

    @Test
    @DisplayName("Debe crear una lección exitosamente")
    void create_ShouldReturnResponse() {
        LessonCreateRequest req = new LessonCreateRequest("Introduction to Java", 1, courseId);
        when(mapper.toEntity(req)).thenReturn(lesson);
        when(repo.save(any(Lesson.class))).thenReturn(lesson);
        when(mapper.toResponse(lesson)).thenReturn(response);

        LessonResponse result = service.create(req);

        assertThat(result).isNotNull();
        assertThat(result.title()).isEqualTo(req.title());
        verify(repo).save(any(Lesson.class));
    }

    @Test
    @DisplayName("Debe obtener una lección por id")
    void get_ShouldReturnResponse() {
        LessonIdRequest req = new LessonIdRequest(lesson.getId());
        when(repo.findById(lesson.getId())).thenReturn(Optional.of(lesson));
        when(mapper.toResponse(lesson)).thenReturn(response);

        LessonResponse result = service.get(req);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(lesson.getId());
    }

    @Test
    @DisplayName("Debe lanzar una excepción cuando la lección no se encuentra al obtenerla")
    void get_NotFound_ShouldThrowException() {
        LessonIdRequest req = new LessonIdRequest(UUID.randomUUID());
        when(repo.findById(req.id())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.get(req))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Lesson not found");
    }

    @Test
    @DisplayName("Debe actualizar una lección exitosamente")
    void update_ShouldReturnResponse() {
        LessonUpdateRequest req = new LessonUpdateRequest(lesson.getId(), "Updated Lesson Title", 2);
        Lesson updatedLesson = Lesson.builder()
                .id(lesson.getId())
                .title(req.title())
                .orderIndex(req.orderIndex())
                .course(lesson.getCourse())
                .build();
        LessonResponse updatedResponse = new LessonResponse(updatedLesson.getId(), updatedLesson.getTitle(), updatedLesson.getOrderIndex(), courseId);

        when(repo.findById(req.id())).thenReturn(Optional.of(lesson));
        when(mapper.toEntity(req)).thenReturn(updatedLesson);
        when(repo.save(any(Lesson.class))).thenReturn(updatedLesson);
        when(mapper.toResponse(updatedLesson)).thenReturn(updatedResponse);

        LessonResponse result = service.update(req);

        assertThat(result).isNotNull();
        assertThat(result.title()).isEqualTo(req.title());
        assertThat(result.orderIndex()).isEqualTo(req.orderIndex());
        verify(repo).save(any(Lesson.class));
    }

    @Test
    @DisplayName("Debe obtener todas las lecciones")
    void getAll_ShouldReturnListOfResponses() {
        Lesson anotherLesson = Lesson.builder()
                .id(UUID.randomUUID())
                .title("Next Lesson")
                .orderIndex(2)
                .course(lesson.getCourse())
                .build();
        LessonResponse anotherResponse = new LessonResponse(anotherLesson.getId(), anotherLesson.getTitle(), anotherLesson.getOrderIndex(), courseId);

        when(repo.findAll()).thenReturn(List.of(lesson, anotherLesson));
        when(mapper.toResponse(lesson)).thenReturn(response);
        when(mapper.toResponse(anotherLesson)).thenReturn(anotherResponse);

        List<LessonResponse> result = service.getAll();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(LessonResponse::title).containsExactlyInAnyOrder(lesson.getTitle(), anotherLesson.getTitle());
    }

    @Test
    @DisplayName("Debe eliminar una lección por id")
    void delete_ShouldCallRepo() {
        UUID id = UUID.randomUUID();
        when(repo.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(repo).deleteById(id);
    }
}
