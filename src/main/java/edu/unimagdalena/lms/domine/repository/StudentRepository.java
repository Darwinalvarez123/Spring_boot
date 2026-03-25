package edu.unimagdalena.lms.domine.repository;

import edu.unimagdalena.lms.domine.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByEmail(String email);
    @Query("SELECT s FROM Student s JOIN s.enrollments e WHERE e.course.id = :courseId")
    List<Student> findByCourseId(@Param("courseId") UUID courseId);

    List<Student> findByFullNameContainingIgnoreCase(String fullName);

}
