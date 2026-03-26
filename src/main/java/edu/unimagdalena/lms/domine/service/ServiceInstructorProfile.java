package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.InstructorProfileDto.*;
import java.util.List;
import java.util.UUID;

public interface ServiceInstructorProfile {
    InstructorProfileResponse create(InstructorProfileCreateRequest req);
    InstructorProfileResponse get(InstructorProfileIdRequest req);
    InstructorProfileResponse update(InstructorProfileUpdateRequest req);
    List<InstructorProfileResponse> getAll();
    void delete(UUID id);
}