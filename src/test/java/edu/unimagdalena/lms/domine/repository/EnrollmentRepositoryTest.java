package edu.unimagdalena.lms.domine.repository;

import edu.unimagdalena.lms.domine.entities.Course;
import edu.unimagdalena.lms.domine.entities.Enrollment;
import edu.unimagdalena.lms.domine.entities.Instructor;
import edu.unimagdalena.lms.domine.entities.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class EnrollmentRepositoryTest extends AbstractRepositoryIT {

    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private InstructorRepository instructorRepository;



    @Test
    @DisplayName("Debe encontrar inscripciones por ID de estudiante")
    void shouldFindByStudentId() {
        // Given
        Student student = studentRepository.save(Student.builder().fullName("Juan").email("juan@email.com").build());

        Instructor instructor1 = instructorRepository.save(Instructor.builder().fullName("Profesor Matematicas").email("mate@email.com").build());
        Course course1 = courseRepository.save(Course.builder().title("Matematicas").instructor(instructor1).active(true).status("ACTIVE").build());

        Instructor instructor2 = instructorRepository.save(Instructor.builder().fullName("Profesor Fisica").email("fisica@email.com").build());
        Course course2 = courseRepository.save(Course.builder().title("Fisica").instructor(instructor2).active(true).status("ACTIVE").build());

        enrollmentRepository.save(Enrollment.builder().student(student).course(course1).status("ACTIVE").enrolledAt(Instant.now()).build());
        enrollmentRepository.save(Enrollment.builder().student(student).course(course2).status("ACTIVE").enrolledAt(Instant.now()).build());

        // When
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(student.getId());

        // Then
        assertThat(enrollments).hasSize(2);
        assertThat(enrollments).extracting(e -> e.getCourse().getTitle()).containsExactlyInAnyOrder("Matematicas", "Fisica");
    }

    @Test
    @DisplayName("Debe encontrar inscripciones por ID de curso")
    void shouldFindByCourseId() {
        // Given
        Instructor instructor = instructorRepository.save(Instructor.builder().fullName("Profesor Programacion").email("profe@email.com").build());
        Course course = courseRepository.save(Course.builder().title("Programacion").instructor(instructor).active(true).status("ACTIVE").build());

        Student student1 = studentRepository.save(Student.builder().fullName("Ana").email("ana@email.com").build());
        Student student2 = studentRepository.save(Student.builder().fullName("Carlos").email("carlos@email.com").build());

        enrollmentRepository.save(Enrollment.builder().student(student1).course(course).status("ACTIVE").enrolledAt(Instant.now()).build());
        enrollmentRepository.save(Enrollment.builder().student(student2).course(course).status("ACTIVE").enrolledAt(Instant.now()).build());

        // When
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(course.getId());

        // Then
        assertThat(enrollments).hasSize(2);
        assertThat(enrollments).extracting(e -> e.getStudent().getFullName()).containsExactlyInAnyOrder("Ana", "Carlos");
    }

    @Test
    @DisplayName("Debe encontrar inscripción por estudiante y curso")
    void shouldFindByStudentIdAndCourseId() {
        // Given
        Instructor instructor = instructorRepository.save(Instructor.builder().fullName("Profesor Historia").email("historia@email.com").build());
        Course course = courseRepository.save(Course.builder().title("Historia").instructor(instructor).active(true).status("ACTIVE").build());

        Student student = studentRepository.save(Student.builder().fullName("Luisa").email("luisa@email.com").build());

        enrollmentRepository.save(Enrollment.builder().student(student).course(course).status("ACTIVE").enrolledAt(Instant.now()).build());

        // When
        Optional<Enrollment> enrollment = enrollmentRepository.findByStudentIdAndCourseId(student.getId(), course.getId());

        // Then
        assertThat(enrollment).isPresent();
        assertThat(enrollment.get().getStudent().getFullName()).isEqualTo("Luisa");
        assertThat(enrollment.get().getCourse().getTitle()).isEqualTo("Historia");
    }

    @Test
    @DisplayName("Debe verificar si estudiante está inscrito en curso")
    void shouldCheckIfExistsByStudentIdAndCourseId() {
        // Given
        Instructor instructor1 = instructorRepository.save(Instructor.builder().fullName("Profesor Matematicas").email("mate@email.com").build());
        Course course1 = courseRepository.save(Course.builder().title("Matematicas").instructor(instructor1).active(true).status("ACTIVE").build());

        Instructor instructor2 = instructorRepository.save(Instructor.builder().fullName("Profesor Fisica").email("fisica@email.com").build());
        Course course2 = courseRepository.save(Course.builder().title("Fisica").instructor(instructor2).active(true).status("ACTIVE").build());

        Student student = studentRepository.save(Student.builder().fullName("Pedro").email("pedro@email.com").build());

        enrollmentRepository.save(Enrollment.builder().student(student).course(course1).status("ACTIVE").enrolledAt(Instant.now()).build());

        // When
        boolean exists = enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), course1.getId());
        boolean notExists = enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), course2.getId());

        // Then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    @DisplayName("Debe contar inscripciones por curso")
    void shouldCountByCourseId() {
        // Given
        Instructor instructor = instructorRepository.save(Instructor.builder().fullName("Profesor Ingles").email("ingles@email.com").build());
        Course course = courseRepository.save(Course.builder().title("Ingles").instructor(instructor).active(true).status("ACTIVE").build());

        Student student1 = studentRepository.save(Student.builder().fullName("Sofia").email("sofia@email.com").build());
        Student student2 = studentRepository.save(Student.builder().fullName("Diego").email("diego@email.com").build());
        Student student3 = studentRepository.save(Student.builder().fullName("Valentina").email("valentina@email.com").build());

        enrollmentRepository.save(Enrollment.builder().student(student1).course(course).status("ACTIVE").enrolledAt(Instant.now()).build());
        enrollmentRepository.save(Enrollment.builder().student(student2).course(course).status("ACTIVE").enrolledAt(Instant.now()).build());

        // When
        long count = enrollmentRepository.countByCourseId(course.getId());

        // Then
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("Debe encontrar inscripciones por estado")
    void shouldFindByStatus() {
        // Given
        Instructor instructor = instructorRepository.save(Instructor.builder().fullName("Profesor Arte").email("arte@email.com").build());
        Course course = courseRepository.save(Course.builder().title("Arte").instructor(instructor).active(true).status("ACTIVE").build());

        Student student1 = studentRepository.save(Student.builder().fullName("Andres").email("andres@email.com").build());
        Student student2 = studentRepository.save(Student.builder().fullName("Camila").email("camila@email.com").build());
        Student student3 = studentRepository.save(Student.builder().fullName("Gabriela").email("gabriela@email.com").build());

        enrollmentRepository.save(Enrollment.builder().student(student1).course(course).status("ACTIVE").enrolledAt(Instant.now()).build());
        enrollmentRepository.save(Enrollment.builder().student(student2).course(course).status("ACTIVE").enrolledAt(Instant.now()).build());
        enrollmentRepository.save(Enrollment.builder().student(student3).course(course).status("COMPLETED").enrolledAt(Instant.now()).build());

        // When
        List<Enrollment> activeEnrollments = enrollmentRepository.findByStatus("ACTIVE");
        List<Enrollment> completedEnrollments = enrollmentRepository.findByStatus("COMPLETED");

        // Then
        assertThat(activeEnrollments).hasSize(2);
        assertThat(completedEnrollments).hasSize(1);
    }
}