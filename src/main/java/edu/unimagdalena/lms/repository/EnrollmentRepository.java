package edu.unimagdalena.lms.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.unimagdalena.lms.entities.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {

    List<Enrollment> findByStudentId(UUID studentId);
    List<Enrollment> findByCourseId(UUID courseId);
    Optional<Enrollment> findByStudentIdAndCourseId(UUID studentId, UUID courseId);
    boolean existsByStudentIdAndCourseId(UUID studentId, UUID courseId);
    long countByCourseId(UUID courseId);
    List<Enrollment> findByStatus(String status);
}
