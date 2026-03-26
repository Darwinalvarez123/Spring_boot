package edu.unimagdalena.lms.domine.service.mapper;

import edu.unimagdalena.lms.api.dto.InstructorDto.InstructorCreateRequest;
import edu.unimagdalena.lms.api.dto.InstructorDto.InstructorResponse;
import edu.unimagdalena.lms.api.dto.InstructorDto.InstructorUpdateRequest;
import edu.unimagdalena.lms.domine.entities.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstructorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fullName", source = "name")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    Instructor toEntity(InstructorCreateRequest req);

    @Mapping(target = "fullName", source = "name")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    Instructor toEntity(InstructorUpdateRequest req);

    @Mapping(target = "name", source = "fullName")
    InstructorResponse toResponse(Instructor entity);
}