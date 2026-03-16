package edu.unimagdalena.lms.domine.repository;

import edu.unimagdalena.lms.entities.Assessment;
import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Instructor;
import edu.unimagdalena.lms.entities.Student;
import edu.unimagdalena.lms.repository.AssessmentRepository;
import edu.unimagdalena.lms.repository.CourseRepository;
import edu.unimagdalena.lms.repository.InstructorRepository;
import edu.unimagdalena.lms.repository.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class AssessmentRepositoryTest extends AbstractRepositoryIT {

    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private InstructorRepository instructorRepository;


    @Test
    @DisplayName("Debe encontrar evaluaciones por ID de estudiante")
    void shouldFindByStudentId() {
        // Given
        Student student = studentRepository.save(Student.builder().fullName("Juan Perez").email("juan@email.com").build());

        Instructor instructor = instructorRepository.save(Instructor.builder().fullName("Profesor").email("profe@email.com").build());
        Course course1 = courseRepository.save(Course.builder().title("Matematicas").status("ACTIVE").active(true).instructor(instructor).build());
        Course course2 = courseRepository.save(Course.builder().title("Fisica").status("ACTIVE").active(true).instructor(instructor).build());

        assessmentRepository.save(Assessment.builder().type("PARCIAL").score(85).takenAt(Instant.now()).student(student).course(course1).build());
        assessmentRepository.save(Assessment.builder().type("FINAL").score(90).takenAt(Instant.now()).student(student).course(course2).build());

        // When
        List<Assessment> assessments = assessmentRepository.findByStudentId(student.getId());

        // Then
        assertThat(assessments).hasSize(2);
        assertThat(assessments).extracting(Assessment::getType).containsExactlyInAnyOrder("PARCIAL", "FINAL");
        assertThat(assessments).extracting(Assessment::getScore).containsExactlyInAnyOrder(85, 90);
    }


    @Test
    @DisplayName("Debe encontrar evaluaciones por ID de curso")
    void shouldFindByCourseId() {
        // Given
        Instructor instructor = instructorRepository.save(Instructor.builder().fullName("Profesor").email("profe@email.com").build());

        Course course = courseRepository.save(Course.builder().title("Matematicas").status("ACTIVE").active(true).instructor(instructor).build());

        Student student1 = studentRepository.save(Student.builder().fullName("Ana").email("ana@email.com").build());
        Student student2 = studentRepository.save(Student.builder().fullName("Carlos").email("carlos@email.com").build());

        assessmentRepository.save(Assessment.builder().type("PARCIAL").score(85).takenAt(Instant.now()).student(student1).course(course).build());
        assessmentRepository.save(Assessment.builder().type("PARCIAL").score(92).takenAt(Instant.now()).student(student2).course(course).build());

        // When
        List<Assessment> assessments = assessmentRepository.findByCourseId(course.getId());

        // Then
        assertThat(assessments).hasSize(2);
        assertThat(assessments).extracting(a -> a.getStudent().getFullName()).containsExactlyInAnyOrder("Ana", "Carlos");
    }

    // 3. Test findByStudentIdAndCourseId
    @Test
    @DisplayName("Debe encontrar evaluación por estudiante y curso")
    void shouldFindByStudentIdAndCourseId() {
        // Given
        Instructor instructor = instructorRepository.save(Instructor.builder().fullName("Profesor").email("profe@email.com").build());

        Course course = courseRepository.save(Course.builder().title("Matematicas").status("ACTIVE").active(true).instructor(instructor).build());

        Student student = studentRepository.save(Student.builder().fullName("Luisa").email("luisa@email.com").build());

        assessmentRepository.save(Assessment.builder().type("FINAL").score(95).takenAt(Instant.now()).student(student).course(course).build());

        // When
        Optional<Assessment> assessment = assessmentRepository.findByStudentIdAndCourseId(student.getId(), course.getId());

        // Then
        assertThat(assessment).isPresent();
        assertThat(assessment.get().getType()).isEqualTo("FINAL");
        assertThat(assessment.get().getScore()).isEqualTo(95);
        assertThat(assessment.get().getStudent().getFullName()).isEqualTo("Luisa");
        assertThat(assessment.get().getCourse().getTitle()).isEqualTo("Matematicas");
    }

    @Test
    @DisplayName("Debe retornar vacío cuando no existe evaluación para ese estudiante y curso")
    void shouldReturnEmptyWhenNotFound() {
        // Given
        Instructor instructor = instructorRepository.save(Instructor.builder().fullName("Profesor").email("profe@email.com").build());

        Course course = courseRepository.save(Course.builder().title("Matematicas").status("ACTIVE").active(true).instructor(instructor).build());

        Student student = studentRepository.save(Student.builder().fullName("Luisa").email("luisa@email.com").build());

        // When
        Optional<Assessment> assessment = assessmentRepository.findByStudentIdAndCourseId(student.getId(), course.getId());

        // Then
        assertThat(assessment).isEmpty();
    }
}