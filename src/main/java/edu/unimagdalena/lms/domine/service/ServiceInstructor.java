package edu.unimagdalena.lms.domine.service;

import java.util.List;
import java.util.UUID;
import edu.unimagdalena.lms.api.dto.InstructorDto.*;

public interface ServiceInstructor {


    InstructorResponse create(InstructorCreateRequest req);

    InstructorResponse get(InstructorIdRequest req);

    InstructorResponse update(InstructorUpdateRequest req);

    List<InstructorResponse> list();

    void delete(UUID id);
}