package edu.unimagdalena.lms.api.dto;

import java.io.Serializable;
import java.util.UUID;

public class LessonDto {
    public record LessonCreateRequest(
            String title,
            int orderIndex,
            UUID courseId) implements Serializable {}

    public record LessonUpdateRequest(
            UUID id,
            String title,
            int orderIndex) implements Serializable {}

    public record LessonResponse(
            UUID id,
            String title,
            int orderIndex,
            UUID courseId) implements Serializable {}

    public record LessonIdRequest(UUID id) implements Serializable {}
}