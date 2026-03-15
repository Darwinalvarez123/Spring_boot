package edu.unimagdalena.lms.repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.unimagdalena.lms.entities.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    Optional<Lesson> findByTitle(String title);
    List<Lesson> findByCourseIdOrderByOrderIndexAsc(UUID courseId);
    List<Lesson> findByTitleContainingIgnoreCase(String title);

}
