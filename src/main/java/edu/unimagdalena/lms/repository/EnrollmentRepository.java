package edu.unimagdalena.lms.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.unimagdalena.lms.entities.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {

}
