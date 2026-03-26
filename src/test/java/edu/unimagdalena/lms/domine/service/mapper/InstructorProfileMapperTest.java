package edu.unimagdalena.lms.domine.service.mapper;

import edu.unimagdalena.lms.api.dto.InstructorProfileDto.*;
import edu.unimagdalena.lms.domine.entities.Instructor;
import edu.unimagdalena.lms.domine.entities.InstructorProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InstructorProfileMapperTest {

    private InstructorProfileMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(InstructorProfileMapper.class);
    }

    @Test
    void toEntity_CreateRequest_ShouldMapCorrectly() {
        UUID instructorId = UUID.randomUUID();
        InstructorProfileCreateRequest req = new InstructorProfileCreateRequest("123456789", "Bio test", instructorId);
        InstructorProfile entity = mapper.toEntity(req);

        assertThat(entity).isNotNull();
        assertThat(entity.getPhone()).isEqualTo(req.phone());
        assertThat(entity.getBio()).isEqualTo(req.bio());
        assertThat(entity.getInstructor().getId()).isEqualTo(instructorId);
    }

    @Test
    void toResponse_Entity_ShouldMapCorrectly() {
        UUID instructorId = UUID.randomUUID();
        Instructor instructor = Instructor.builder().id(instructorId).build();
        InstructorProfile entity = InstructorProfile.builder()
                .id(UUID.randomUUID())
                .phone("987654321")
                .bio("Expert bio")
                .instructor(instructor)
                .build();
        InstructorProfileResponse response = mapper.toResponse(entity);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(entity.getId());
        assertThat(response.phone()).isEqualTo(entity.getPhone());
        assertThat(response.instructorId()).isEqualTo(instructorId);
    }
}
