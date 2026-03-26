package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.AssessmentDto.*;
import java.util.List;
import java.util.UUID;

public interface ServiceAssessment {
    AssessmentResponse create(AssessmentCreateRequest req);
    AssessmentResponse get(AssessmentIdRequest req);
    AssessmentResponse update(AssessmentUpdateRequest req);
    List<AssessmentResponse> getAll();
    void delete(UUID id);
}