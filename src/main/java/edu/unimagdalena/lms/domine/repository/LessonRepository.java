package edu.unimagdalena.lms.domine.repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.unimagdalena.lms.domine.entities.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    Optional<Lesson> findByTitle(String title);
    List<Lesson> findByCourseIdOrderByOrderIndexAsc(UUID courseId);
    List<Lesson> findByTitleContainingIgnoreCase(String title);

}
