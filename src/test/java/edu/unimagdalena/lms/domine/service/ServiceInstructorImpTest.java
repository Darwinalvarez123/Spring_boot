package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.InstructorDto.*;
import edu.unimagdalena.lms.domine.entities.Instructor;
import edu.unimagdalena.lms.domine.repository.InstructorRepository;
import edu.unimagdalena.lms.domine.service.mapper.InstructorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceInstructorImpTest {

    @Mock
    private InstructorRepository repo;

    @Mock
    private InstructorMapper mapper;

    @InjectMocks
    private ServiceInstructorImp service;

    private Instructor instructor;
    private InstructorResponse response;

    @BeforeEach
    void setUp() {
        instructor = Instructor.builder()
                .id(UUID.randomUUID())
                .fullName("Jane Doe")
                .email("jane@example.com")
                .build();

        response = new InstructorResponse(instructor.getId(), instructor.getEmail(), instructor.getFullName());
    }

    @Test
    @DisplayName("Debe crear un instructor exitosamente")
    void create_ShouldReturnResponse() {
        InstructorCreateRequest req = new InstructorCreateRequest("jane@example.com", "Jane Doe");
        when(repo.findByEmail(req.email())).thenReturn(Optional.empty());
        when(mapper.toEntity(req)).thenReturn(instructor);
        when(repo.save(any(Instructor.class))).thenReturn(instructor);
        when(mapper.toResponse(instructor)).thenReturn(response);

        InstructorResponse result = service.create(req);

        assertThat(result).isNotNull();
        assertThat(result.email()).isEqualTo(req.email());
        verify(repo).save(any(Instructor.class));
    }

    @Test
    @DisplayName("Debe lanzar una excepción cuando el email ya existe")
    void create_EmailExists_ShouldThrowException() {
        InstructorCreateRequest req = new InstructorCreateRequest("jane@example.com", "Jane Doe");
        when(repo.findByEmail(req.email())).thenReturn(Optional.of(instructor));

        assertThatThrownBy(() -> service.create(req))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    @DisplayName("Debe eliminar un instructor")
    void delete_ShouldCallRepo() {
        UUID id = UUID.randomUUID();
        when(repo.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(repo).deleteById(id);
    }
}
