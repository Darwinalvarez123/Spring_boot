package edu.unimagdalena.lms.domine.repository;

import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Enrollment;
import edu.unimagdalena.lms.entities.Instructor;
import edu.unimagdalena.lms.entities.Student;
import edu.unimagdalena.lms.repository.CourseRepository;
import edu.unimagdalena.lms.repository.EnrollmentRepository;
import edu.unimagdalena.lms.repository.InstructorRepository;
import edu.unimagdalena.lms.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentRepositoryTest extends AbstractRepositoryIT {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private InstructorRepository instructorRepository;



    @Test
    @DisplayName("Debe encontrar los email si existe")
    void shouldFindByEmail() {
        //Given
        Student student = studentRepository.save(Student.builder().fullName("student").email("student1@gmail.com").build());

        //When
        Optional<Student> student1 = studentRepository.findByEmail("student1@gmail.com");

        //Then
        assertThat(student1).isPresent();
        assertThat(student1.get().getFullName()).isEqualTo("student");
        assertThat(student1.get().getEmail()).isEqualTo("student1@gmail.com");
    }

    @Test
    @DisplayName("Debe encontrar estudiantes inscritos en un curso")
    void shouldFindByCourseId() {
        // Given
        Student student1 = studentRepository.save(Student.builder().fullName("Ana García").email("ana@email.com").build());
        Student student2 = studentRepository.save(Student.builder().fullName("Carlos López").email("carlos@email.com").build());
        Student student3 = studentRepository.save(Student.builder().fullName("Luisa Pérez").email("luisa@email.com").build());

        Instructor instructor = instructorRepository.save(Instructor.builder().fullName("Profesor").email("profe@email.com").build());
        Course course = courseRepository.save(Course.builder().title("Matemáticas").instructor(instructor).active(true).status("ACTIVE").build());

        enrollmentRepository.save(Enrollment.builder().student(student1).course(course).status("ACTIVE").enrolledAt(Instant.now()).build());
        enrollmentRepository.save(Enrollment.builder().student(student2).course(course).status("ACTIVE").enrolledAt(Instant.now()).build());

        // When
        List<Student> studentsInCourse = studentRepository.findByCourseId(course.getId());

        // Then
        assertThat(studentsInCourse).hasSize(2);
        assertThat(studentsInCourse).extracting(Student::getEmail).containsExactlyInAnyOrder("ana@email.com", "carlos@email.com");
        assertThat(studentsInCourse).extracting(Student::getFullName).doesNotContain("Luisa Pérez");
    }

    @Test
    @DisplayName("Debe buscar estudiantes por nombre que contenga el texto ignorando mayúsculas/minúsculas")
    void shouldFindByFullNameContainingIgnoreCase() {
        // Given
        studentRepository.save(Student.builder().fullName("Maria Gonzalez").email("maria.gonzalez@email.com").build());
        studentRepository.save(Student.builder().fullName("Jose Gonzalez").email("jose.gonzalez@email.com").build());
        studentRepository.save(Student.builder().fullName("Ana Lopez").email("ana.lopez@email.com").build());
        studentRepository.save(Student.builder().fullName("Carlos Perez").email("carlos.perez@email.com").build());

        // When
        List<Student> students1 = studentRepository.findByFullNameContainingIgnoreCase("GONZALEZ");
        List<Student> students2 = studentRepository.findByFullNameContainingIgnoreCase("lopez");
        List<Student> students3 = studentRepository.findByFullNameContainingIgnoreCase("Ramirez");

        // Then
        assertThat(students1).hasSize(2);
        assertThat(students1).extracting(Student::getFullName).containsExactlyInAnyOrder("Maria Gonzalez", "Jose Gonzalez");
        assertThat(students2).hasSize(1);
        assertThat(students2.getFirst().getFullName()).isEqualTo("Ana Lopez");
        assertThat(students3).isEmpty();
    }
}