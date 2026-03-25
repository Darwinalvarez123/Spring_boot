package edu.unimagdalena.lms.domine.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.unimagdalena.lms.domine.entities.Assessment;

public interface AssessmentRepository extends JpaRepository<Assessment, UUID> {

    List<Assessment> findByStudentId(UUID studentId);
    List<Assessment> findByCourseId(UUID courseId);
    Optional<Assessment> findByStudentIdAndCourseId(UUID studentId, UUID courseId);
}
