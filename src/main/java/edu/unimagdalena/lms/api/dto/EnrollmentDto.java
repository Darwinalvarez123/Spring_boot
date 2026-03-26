package edu.unimagdalena.lms.api.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class EnrollmentDto {
    public record EnrollmentCreateRequest(
            String status,
            UUID studentId,
            UUID courseId) implements Serializable {}

    public record EnrollmentUpdateRequest(
            UUID id,
            String status) implements Serializable {}

    public record EnrollmentResponse(
            UUID id,
            String status,
            Instant enrolledAt,
            UUID studentId,
            UUID courseId) implements Serializable {}

    public record EnrollmentIdRequest(UUID id) implements Serializable {}
}