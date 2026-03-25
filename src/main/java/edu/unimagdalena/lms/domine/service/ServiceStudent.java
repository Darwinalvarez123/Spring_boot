package edu.unimagdalena.lms.domine.service;

import edu.unimagdalena.lms.api.dto.StudentDto;

import java.util.List;
import java.util.UUID;

public interface ServiceStudent {
    StudentDto.StudentResponse create(StudentDto.StudentCreateRequest req);
    StudentDto.StudentResponse get(StudentDto.StudentIdRequest req);
    StudentDto.StudentResponse update(StudentDto.StudentUpdateRequest req);
    List<StudentDto.StudentResponse> getAll();
    void delete(UUID id);
}
