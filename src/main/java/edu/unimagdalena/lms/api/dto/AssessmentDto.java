package edu.unimagdalena.lms.api.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class AssessmentDto {
    public record AssessmentCreateRequest(
            String type,
            int score,
            UUID studentId,
            UUID courseId) implements Serializable {}

    public record AssessmentUpdateRequest(
            UUID id,
            String type,
            int score) implements Serializable {}

    public record AssessmentResponse(
            UUID id,
            String type,
            int score,
            Instant takenAt,
            UUID studentId,
            UUID courseId) implements Serializable {}

    public record AssessmentIdRequest(UUID id) implements Serializable {}
}