package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.CourseDto.*;
import java.util.List;
import java.util.UUID;

public interface ServiceCourse {
    CourseResponse create(CourseCreateRequest req);
    CourseResponse get(CourseIdRequest req);
    CourseResponse update(CourseUpdateRequest req);
    List<CourseResponse> getAll();
    void delete(UUID id);
}