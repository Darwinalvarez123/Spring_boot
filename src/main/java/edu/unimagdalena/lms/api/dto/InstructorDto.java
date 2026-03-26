package edu.unimagdalena.lms.api.dto;

import java.io.Serializable;
import java.util.UUID;

public class InstructorDto {
    public record InstructorCreateRequest(String email, String name) implements Serializable {
    };

    public record InstructorUpdateRequest(UUID id, String email, String name) implements Serializable {
    };

    public record InstructorResponse(UUID id, String email, String name) implements Serializable {
    };

    public record InstructorIdRequest(UUID id) implements Serializable {
    };

}