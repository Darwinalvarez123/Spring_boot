package edu.unimagdalena.lms.domine.repository;

import edu.unimagdalena.lms.domine.entities.Course;
import edu.unimagdalena.lms.domine.entities.Instructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CourseRepositoryTest extends AbstractRepositoryIT {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private InstructorRepository instructorRepository;

    @Test
    @DisplayName("Debe encontrar cursos por ID de instructor")
    void shouldFindByInstructorId() {
        // Given
        Instructor instructor1 = instructorRepository.save(Instructor.builder().fullName("Profesor 1").email("profe1@email.com").build());
        Instructor instructor2 = instructorRepository.save(Instructor.builder().fullName("Profesor 2").email("profe2@email.com").build());

        courseRepository.save(Course.builder().title("Curso 1").status("ACTIVE").active(true).instructor(instructor1).build());
        courseRepository.save(Course.builder().title("Curso 2").status("ACTIVE").active(true).instructor(instructor1).build());
        courseRepository.save(Course.builder().title("Curso 3").status("ACTIVE").active(true).instructor(instructor2).build());

        // When
        List<Course> instructor1Courses = courseRepository.findByInstructorId(instructor1.getId());

        // Then
        assertThat(instructor1Courses).hasSize(2);
        assertThat(instructor1Courses).extracting(Course::getTitle).containsExactlyInAnyOrder("Curso 1", "Curso 2");
    }


    @Test
    @DisplayName("Debe encontrar cursos activos")
    void shouldFindByActiveTrue() {
        // Given
        Instructor instructor = instructorRepository.save(Instructor.builder().fullName("Profesor").email("profe@email.com").build());

        courseRepository.save(Course.builder().title("Curso Activo 1").status("ACTIVE").active(true).instructor(instructor).build());
        courseRepository.save(Course.builder().title("Curso Activo 2").status("ACTIVE").active(true).instructor(instructor).build());
        courseRepository.save(Course.builder().title("Curso Inactivo").status("INACTIVE").active(false).instructor(instructor).build());

        // When
        List<Course> activeCourses = courseRepository.findByActiveTrue();

        // Then
        assertThat(activeCourses).hasSize(2);
        assertThat(activeCourses).extracting(Course::getTitle).containsExactlyInAnyOrder("Curso Activo 1", "Curso Activo 2");
    }


    @Test
    @DisplayName("Debe encontrar curso por título")
    void shouldFindByTitle() {
        // Given
        Instructor instructor = instructorRepository.save(Instructor.builder().fullName("Profesor").email("profe@email.com").build());

        courseRepository.save(Course.builder().title("Matematicas").status("ACTIVE").active(true).instructor(instructor).build());

        // When
        Optional<Course> found = courseRepository.findByTitle("Matematicas");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Matematicas");
    }

    @Test
    @DisplayName("Debe retornar vacío cuando título no existe")
    void shouldReturnEmptyWhenTitleNotFound() {
        // When
        Optional<Course> found = courseRepository.findByTitle("NoExiste");

        // Then
        assertThat(found).isEmpty();
    }
}