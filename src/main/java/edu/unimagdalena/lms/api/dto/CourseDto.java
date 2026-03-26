package edu.unimagdalena.lms.api.dto;

import java.io.Serializable;
import java.util.UUID;

public class CourseDto {
    public record CourseCreateRequest(
            String title,
            String status,
            boolean active,
            UUID instructorId) implements Serializable {}

    public record CourseUpdateRequest(
            UUID id,
            String title,
            String status,
            boolean active) implements Serializable {}

    public record CourseResponse(
            UUID id,
            String title,
            String status,
            boolean active,
            UUID instructorId) implements Serializable {}

    public record CourseIdRequest(UUID id) implements Serializable {}
}