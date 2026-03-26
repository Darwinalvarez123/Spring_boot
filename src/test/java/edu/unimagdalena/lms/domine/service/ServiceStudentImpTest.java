package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.StudentDto.*;
import edu.unimagdalena.lms.domine.entities.Student;
import edu.unimagdalena.lms.domine.repository.StudentRepository;
import edu.unimagdalena.lms.domine.service.mapper.StudentMapper;
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
class ServiceStudentImpTest {

    @Mock
    private StudentRepository repo;

    @Mock
    private StudentMapper mapper;

    @InjectMocks
    private ServiceStudentImp service;

    private Student student;
    private StudentResponse response;

    @BeforeEach
    void setUp() {
        student = Student.builder()
                .id(UUID.randomUUID())
                .fullName("John Doe")
                .email("john@example.com")
                .build();

        response = new StudentResponse(student.getId(), student.getFullName(), student.getEmail());
    }

    @Test
    @DisplayName("Debe crear un estudiante exitosamente")
    void create_ShouldReturnResponse() {
        StudentCreateRequest req = new StudentCreateRequest("John Doe", "john@example.com");
        when(repo.findByEmail(req.email())).thenReturn(Optional.empty());
        when(mapper.toEntity(req)).thenReturn(student);
        when(repo.save(any(Student.class))).thenReturn(student);
        when(mapper.toResponse(student)).thenReturn(response);

        StudentResponse result = service.create(req);

        assertThat(result).isNotNull();
        assertThat(result.email()).isEqualTo(req.email());
        verify(repo).save(any(Student.class));
    }

    @Test
    @DisplayName("Debe lanzar una excepción cuando el email ya existe")
    void create_EmailExists_ShouldThrowException() {
        StudentCreateRequest req = new StudentCreateRequest("John Doe", "john@example.com");
        when(repo.findByEmail(req.email())).thenReturn(Optional.of(student));

        assertThatThrownBy(() -> service.create(req))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    @DisplayName("Debe obtener un estudiante por id")
    void get_ShouldReturnResponse() {
        StudentIdRequest req = new StudentIdRequest(student.getId());
        when(repo.findById(student.getId())).thenReturn(Optional.of(student));
        when(mapper.toResponse(student)).thenReturn(response);

        StudentResponse result = service.get(req);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(student.getId());
    }

    @Test
    @DisplayName("Debe eliminar un estudiante")
    void delete_ShouldCallRepo() {
        UUID id = UUID.randomUUID();
        when(repo.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(repo).deleteById(id);
    }
}
