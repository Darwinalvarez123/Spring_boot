package edu.unimagdalena.lms.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.unimagdalena.lms.entities.Assessment;

public interface AssessmentRepository extends JpaRepository<Assessment, UUID> {

}
