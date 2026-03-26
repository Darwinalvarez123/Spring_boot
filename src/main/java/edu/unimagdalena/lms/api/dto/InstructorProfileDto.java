package edu.unimagdalena.lms.api.dto;

import java.io.Serializable;
import java.util.UUID;

public class InstructorProfileDto {
    public record InstructorProfileCreateRequest(
            String phone,
            String bio,
            UUID instructorId) implements Serializable {}

    public record InstructorProfileUpdateRequest(
            UUID id,
            String phone,
            String bio) implements Serializable {}

    public record InstructorProfileResponse(
            UUID id,
            String phone,
            String bio,
            UUID instructorId) implements Serializable {}

    public record InstructorProfileIdRequest(UUID id) implements Serializable {}
}