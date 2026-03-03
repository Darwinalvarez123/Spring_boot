package edu.unimagdalena.lms.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.unimagdalena.lms.entities.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, UUID> {

}
