package edu.unimagdalena.lms.api.dto;

import java.io.Serializable;
import java.util.UUID;

public class InstructorDto {
    public record InstructorCreateReques(String email, String name) implements Serializable {
    };

    public record InstructorUpdateReques(UUID id, String email, String name) implements Serializable {
    };

    public record InstructorResponse(UUID id, String email, String name) implements Serializable {
    };

    public record InstructorIdReques(UUID id) implements Serializable {
    };

}