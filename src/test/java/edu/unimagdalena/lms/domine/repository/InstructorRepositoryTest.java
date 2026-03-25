package edu.unimagdalena.lms.domine.repository;

import edu.unimagdalena.lms.domine.entities.Instructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class InstructorRepositoryTest extends AbstractRepositoryIT {
    @Autowired
    private InstructorRepository instructorRepository;

    @Test
    @DisplayName("Debe encontrar instructor por email")
    void shouldFindByEmail() {
        // Given
        Instructor instructor = instructorRepository.save(Instructor.builder().fullName("Juan Perez").email("juan.perez@email.com").createdAt(Instant.now()).build());

        // When
        Optional<Instructor> instructor1 = instructorRepository.findByEmail("juan.perez@email.com");

        // Then
        assertThat(instructor1).isPresent();
        assertThat(instructor1.get().getFullName()).isEqualTo("Juan Perez");
    }

    @Test
    @DisplayName("Debe retornar vacío cuando email no existe")
    void shouldReturnEmptyWhenEmailNotFound() {
        // When
        Optional<Instructor> found = instructorRepository.findByEmail("noexiste@email.com");

        // Then
        assertThat(found).isEmpty();
    }

    // 2. Test findByFullNameContainingIgnoreCase
    @Test
    @DisplayName("Debe buscar instructores por nombre que contenga texto")
    void shouldFindByFullNameContainingIgnoreCase() {
        // Given
        instructorRepository.save(Instructor.builder().fullName("Maria Gonzalez").email("maria.gonzalez@email.com").createdAt(Instant.now()).build());
        instructorRepository.save(Instructor.builder().fullName("Jose Gonzalez").email("jose.gonzalez@email.com").createdAt(Instant.now()).build());
        instructorRepository.save(Instructor.builder().fullName("Ana Lopez").email("ana.lopez@email.com").createdAt(Instant.now()).build());

        // When
        List<Instructor> instructor1 = instructorRepository.findByFullNameContainingIgnoreCase("GONZALEZ");
        List<Instructor> instructor2 = instructorRepository.findByFullNameContainingIgnoreCase("lopez");
        List<Instructor> instructor3 = instructorRepository.findByFullNameContainingIgnoreCase("Ramirez");

        // Then
        assertThat(instructor1).hasSize(2);
        assertThat(instructor1).extracting(Instructor::getFullName).containsExactlyInAnyOrder("Maria Gonzalez", "Jose Gonzalez");

        assertThat(instructor2).hasSize(1);
        assertThat(instructor2.getFirst().getFullName()).isEqualTo("Ana Lopez");

        assertThat(instructor3).isEmpty();
    }
}
