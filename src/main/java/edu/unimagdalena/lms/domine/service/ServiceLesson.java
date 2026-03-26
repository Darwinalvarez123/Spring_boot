package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.LessonDto.*;
import java.util.List;
import java.util.UUID;

public interface ServiceLesson {
    LessonResponse create(LessonCreateRequest req);
    LessonResponse get(LessonIdRequest req);
    LessonResponse update(LessonUpdateRequest req);
    List<LessonResponse> getAll();
    void delete(UUID id);
}