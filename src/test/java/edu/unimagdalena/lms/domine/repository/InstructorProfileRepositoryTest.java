package edu.unimagdalena.lms.domine.repository;

import edu.unimagdalena.lms.domine.entities.Instructor;
import edu.unimagdalena.lms.domine.entities.InstructorProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class InstructorProfileRepositoryTest extends AbstractRepositoryIT {

    @Autowired
    private InstructorProfileRepository instructorProfileRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @BeforeEach
    void setUp() {
        instructorProfileRepository.deleteAll();
        instructorRepository.deleteAll();
    }


    @Test
    @DisplayName("Debe encontrar perfil por ID de instructor")
    void shouldFindByInstructorId() {
        // Given
        Instructor instructor = instructorRepository.save(Instructor.builder().fullName("Juan Perez").email("juan@email.com").build());

        InstructorProfile profile = instructorProfileRepository.save(InstructorProfile.builder().phone("123456789").bio("Profesor de matematicas").instructor(instructor).build());

        // When
        Optional<InstructorProfile> instructorProfile1 = instructorProfileRepository.findByInstructorId(instructor.getId());

        // Then
        assertThat(instructorProfile1).isPresent();
        assertThat(instructorProfile1.get().getPhone()).isEqualTo("123456789");
        assertThat(instructorProfile1.get().getBio()).isEqualTo("Profesor de matematicas");
        assertThat(instructorProfile1.get().getInstructor().getFullName()).isEqualTo("Juan Perez");
    }


    @Test
    @DisplayName("Debe verificar si instructor tiene perfil")
    void shouldCheckIfExistsByInstructorId() {
        // Given
        Instructor instructor1 = instructorRepository.save(Instructor.builder().fullName("Juan").email("juan@email.com").build());

        Instructor instructor2 = instructorRepository.save(Instructor.builder().fullName("Ana").email("ana@email.com").build());

        instructorProfileRepository.save(InstructorProfile.builder().phone("123456789").bio("Profesor").instructor(instructor1).build());

        // When
        boolean exists = instructorProfileRepository.existsByInstructorId(instructor1.getId());
        boolean notExists = instructorProfileRepository.existsByInstructorId(instructor2.getId());

        // Then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }


    @Test
    @DisplayName("Debe encontrar perfil con instructor cargado")
    void shouldFindByIdWithInstructor() {
        // Given
        Instructor instructor = instructorRepository.save(Instructor.builder().fullName("Carlos Lopez").email("carlos@email.com").build());

        InstructorProfile profile = instructorProfileRepository.save(InstructorProfile.builder().phone("987654321").bio("Instructor de física").instructor(instructor).build());

        // When
        Optional<InstructorProfile> instructorProfile1 = instructorProfileRepository.findByIdWithInstructor(profile.getId());

        // Then
        assertThat(instructorProfile1).isPresent();
        assertThat(instructorProfile1.get().getInstructor().getFullName()).isEqualTo("Carlos Lopez");
    }


    @Test
    @DisplayName("Debe retornar vacio cuando el instructor no tiene perfil")
    void shouldReturnEmptyWhenProfileNotFound() {
        // Given
        Instructor instructor = instructorRepository.save(Instructor.builder().fullName("Test").email("test@email.com").build());

        // When
        Optional<InstructorProfile> instructorProfile1 = instructorProfileRepository.findByInstructorId(instructor.getId());

        // Then
        assertThat(instructorProfile1).isEmpty();
    }
}