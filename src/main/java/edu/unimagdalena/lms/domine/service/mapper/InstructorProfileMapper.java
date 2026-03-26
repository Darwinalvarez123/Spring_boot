package edu.unimagdalena.lms.domine.service.mapper;

import edu.unimagdalena.lms.api.dto.InstructorProfileDto.*;
import edu.unimagdalena.lms.domine.entities.InstructorProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstructorProfileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instructor.id", source = "instructorId")
    InstructorProfile toEntity(InstructorProfileCreateRequest req);

    InstructorProfile toEntity(InstructorProfileUpdateRequest req);

    @Mapping(target = "instructorId", source = "instructor.id")
    InstructorProfileResponse toResponse(InstructorProfile entity);
}