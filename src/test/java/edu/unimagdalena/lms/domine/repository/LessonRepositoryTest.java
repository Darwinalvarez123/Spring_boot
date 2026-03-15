package edu.unimagdalena.lms.domine.repository;


import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.entities.Instructor;
import edu.unimagdalena.lms.entities.Lesson;
import edu.unimagdalena.lms.entities.Student;
import edu.unimagdalena.lms.repository.CourseRepository;
import edu.unimagdalena.lms.repository.InstructorRepository;
import edu.unimagdalena.lms.repository.LessonRepository;
import edu.unimagdalena.lms.repository.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LessonRepositoryTest extends AbstractRepositoryIT {
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Test
    @DisplayName("Agregar una lesson")
    void shouldFindByTitle() {
        //Given
        lessonRepository.save(Lesson.builder().title("Ecuaciones").orderIndex(1).build());

        lessonRepository.save(Lesson.builder().title("Encapsulamiento").orderIndex(2).build());

        //When
        Optional<Lesson> matematicas = lessonRepository.findByTitle("Ecuaciones");
        Optional<Lesson> poo = lessonRepository.findByTitle("Encapsulamiento");
        Optional<Lesson> fisica = lessonRepository.findByTitle("Leyes de Newton");

        //Then

        assertThat(matematicas).isPresent();
        assertThat(matematicas.get().getTitle()).isEqualTo("Ecuaciones");

        assertThat(poo).isPresent();
        assertThat(poo.get().getTitle()).isEqualTo("Encapsulamiento");

        assertThat(fisica).isEmpty();
    }

    @Test
    @DisplayName("Debe buscar por course_id y ordenar por order_idex de forma acendente ")
    void shouldFindByCourseIdOrderByOrderIndexAsc() {
        //Given
        Instructor instructor = instructorRepository.save(Instructor.builder().fullName("instructor!").email("instructor1@gmail.com").build());
        Course course = courseRepository.save(Course.builder().title("Matemáticas").instructor(instructor).status("true").build());
        lessonRepository.save(Lesson.builder().title("Ecuaciones").orderIndex(3).course(course).build());
        lessonRepository.save(Lesson.builder().title("Numeros").orderIndex(1).course(course).build());
        lessonRepository.save(Lesson.builder().title("Geometria").orderIndex(2).course(course).build());

        //When
        List<Lesson> lessons = lessonRepository.findByCourseIdOrderByOrderIndexAsc(course.getId());

        //Then
        assertThat(lessons).hasSize(3);
        assertThat(lessons).extracting(Lesson::getOrderIndex).containsExactly(1, 2, 3);
        assertThat(lessons).extracting(Lesson::getTitle).containsExactly("Numeros", "Geometria", "Ecuaciones");


    }

    @Test
    @DisplayName("Debe buscar por título que contengan el texto e ignorar mayúsculas y minúsculas")
    void shouldFindByTitleContainingIgnoreCase() {
        // Given
        lessonRepository.save(Lesson.builder().title("Matemáticas Básicas").orderIndex(1).build());
        lessonRepository.save(Lesson.builder().title("Matemáticas Avanzadas").orderIndex(2).build());
        lessonRepository.save(Lesson.builder().title("Física Cuántica").orderIndex(3).build());
        lessonRepository.save(Lesson.builder().title("Programación en Java").orderIndex(4).build());

        // When
        List<Lesson> lessons1 = lessonRepository.findByTitleContainingIgnoreCase("MATEM");
        List<Lesson> lessons2 = lessonRepository.findByTitleContainingIgnoreCase("java");
        List<Lesson> lessons3 = lessonRepository.findByTitleContainingIgnoreCase("química");

        // Then
        assertThat(lessons1).hasSize(2);
        assertThat(lessons1).extracting(Lesson::getTitle).containsExactlyInAnyOrder("Matemáticas Básicas", "Matemáticas Avanzadas");
        assertThat(lessons2).hasSize(1);
        assertThat(lessons2.get(0).getTitle()).isEqualTo("Programación en Java");
        assertThat(lessons3).isEmpty();
    }



}
