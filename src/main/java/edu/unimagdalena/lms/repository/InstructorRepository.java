package edu.unimagdalena.lms.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.unimagdalena.lms.entities.Instructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InstructorRepository extends JpaRepository<Instructor, UUID> {

    Optional<Instructor> findByEmail(String email);


    List<Instructor> findByFullNameContainingIgnoreCase(String fullName);


    @Query("SELECT i FROM Instructor i LEFT JOIN FETCH i.courses WHERE i.id = :id")
    Optional<Instructor> findByIdWithCourses(@Param("id") UUID id);
}
