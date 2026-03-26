package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.EnrollmentDto.*;
import java.util.List;
import java.util.UUID;

public interface ServiceEnrollment {
    EnrollmentResponse create(EnrollmentCreateRequest req);
    EnrollmentResponse get(EnrollmentIdRequest req);
    EnrollmentResponse update(EnrollmentUpdateRequest req);
    List<EnrollmentResponse> getAll();
    void delete(UUID id);
}