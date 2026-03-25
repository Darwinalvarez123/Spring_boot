package edu.unimagdalena.lms.domine.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.unimagdalena.lms.domine.entities.InstructorProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InstructorProfileRepository extends JpaRepository<InstructorProfile, UUID> {

    Optional<InstructorProfile> findByInstructorId(UUID instructorId);


    boolean existsByInstructorId(UUID instructorId);


    @Query("SELECT ip FROM InstructorProfile ip JOIN FETCH ip.instructor WHERE ip.id = :id")
    Optional<InstructorProfile> findByIdWithInstructor(@Param("id") UUID id);
}
