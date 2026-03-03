package edu.unimagdalena.lms.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.unimagdalena.lms.entities.InstructorProfile;

public interface InstructorProfileRepository extends JpaRepository<InstructorProfile, UUID> {

}
