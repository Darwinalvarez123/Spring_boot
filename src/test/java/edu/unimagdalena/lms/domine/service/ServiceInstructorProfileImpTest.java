package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.InstructorProfileDto.*;
import edu.unimagdalena.lms.domine.entities.Instructor;
import edu.unimagdalena.lms.domine.entities.InstructorProfile;
import edu.unimagdalena.lms.domine.repository.InstructorProfileRepository;
import edu.unimagdalena.lms.domine.service.mapper.InstructorProfileMapper;
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
class ServiceInstructorProfileImpTest {

    @Mock
    private InstructorProfileRepository repo;

    @Mock
    private InstructorProfileMapper mapper;

    @InjectMocks
    private ServiceInstructorProfileImp service;

    private InstructorProfile profile;
    private InstructorProfileResponse response;
    private UUID instructorId;

    @BeforeEach
    void setUp() {
        instructorId = UUID.randomUUID();
        Instructor instructor = Instructor.builder().id(instructorId).fullName("Jane Doe").build();

        profile = InstructorProfile.builder()
                .id(UUID.randomUUID())
                .phone("123456789")
                .bio("Expert in Java development")
                .instructor(instructor)
                .build();

        response = new InstructorProfileResponse(profile.getId(), profile.getPhone(), profile.getBio(), instructorId);
    }

    @Test
    @DisplayName("Debe crear un perfil de instructor exitosamente")
    void create_ShouldReturnResponse() {
        InstructorProfileCreateRequest req = new InstructorProfileCreateRequest("123456789", "Expert in Java development", instructorId);
        when(mapper.toEntity(req)).thenReturn(profile);
        when(repo.save(any(InstructorProfile.class))).thenReturn(profile);
        when(mapper.toResponse(profile)).thenReturn(response);

        InstructorProfileResponse result = service.create(req);

        assertThat(result).isNotNull();
        assertThat(result.phone()).isEqualTo(req.phone());
        verify(repo).save(any(InstructorProfile.class));
    }

    @Test
    @DisplayName("Debe obtener un perfil de instructor por id")
    void get_ShouldReturnResponse() {
        InstructorProfileIdRequest req = new InstructorProfileIdRequest(profile.getId());
        when(repo.findById(profile.getId())).thenReturn(Optional.of(profile));
        when(mapper.toResponse(profile)).thenReturn(response);

        InstructorProfileResponse result = service.get(req);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(profile.getId());
    }

    @Test
    @DisplayName("Debe actualizar un perfil de instructor exitosamente")
    void update_ShouldReturnResponse() {
        InstructorProfileUpdateRequest req = new InstructorProfileUpdateRequest(profile.getId(), "987654321", "New bio content");
        InstructorProfile updatedProfile = InstructorProfile.builder()
                .id(profile.getId())
                .phone(req.phone())
                .bio(req.bio())
                .instructor(profile.getInstructor())
                .build();
        InstructorProfileResponse updatedResponse = new InstructorProfileResponse(updatedProfile.getId(), updatedProfile.getPhone(), updatedProfile.getBio(), instructorId);

        when(repo.findById(req.id())).thenReturn(Optional.of(profile));
        when(mapper.toEntity(req)).thenReturn(updatedProfile);
        when(repo.save(any(InstructorProfile.class))).thenReturn(updatedProfile);
        when(mapper.toResponse(updatedProfile)).thenReturn(updatedResponse);

        InstructorProfileResponse result = service.update(req);

        assertThat(result).isNotNull();
        assertThat(result.phone()).isEqualTo(req.phone());
        assertThat(result.bio()).isEqualTo(req.bio());
        verify(repo).save(any(InstructorProfile.class));
    }

    @Test
    @DisplayName("Debe eliminar un perfil de instructor por id")
    void delete_ShouldCallRepo() {
        UUID id = UUID.randomUUID();
        when(repo.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(repo).deleteById(id);
    }
}
