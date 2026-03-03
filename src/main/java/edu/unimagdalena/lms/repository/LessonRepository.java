package edu.unimagdalena.lms.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.unimagdalena.lms.entities.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {

}
