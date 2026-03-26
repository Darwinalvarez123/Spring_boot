package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.StudentDto;

import java.util.List;
import java.util.UUID;

public interface ServiceStudent {
    StudentResponse create(StudentCreateRequest req);
    StudentResponse get(StudentIdRequest req);
    StudentResponse update(StudentUpdateRequest req);
    List<StudentResponse> getAll();
    void delete(UUID id);
}
