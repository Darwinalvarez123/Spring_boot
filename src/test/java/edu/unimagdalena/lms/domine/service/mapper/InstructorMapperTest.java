package edu.unimagdalena.lms.domine.service.mapper;

import edu.unimagdalena.lms.api.dto.InstructorDto.*;
import edu.unimagdalena.lms.domine.entities.Instructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InstructorMapperTest {

    private InstructorMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(InstructorMapper.class);
    }

    @Test
    void toEntity_CreateRequest_ShouldMapCorrectly() {
        InstructorCreateRequest req = new InstructorCreateRequest("john@example.com", "John Doe");
        Instructor entity = mapper.toEntity(req);

        assertThat(entity).isNotNull();
        assertThat(entity.getFullName()).isEqualTo(req.name());
        assertThat(entity.getEmail()).isEqualTo(req.email());
        assertThat(entity.getCreatedAt()).isNotNull();
    }

    @Test
    void toResponse_Entity_ShouldMapCorrectly() {
        Instructor entity = Instructor.builder()
                .id(UUID.randomUUID())
                .fullName("Jane Doe")
                .email("jane@example.com")
                .build();
        InstructorResponse response = mapper.toResponse(entity);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(entity.getId());
        assertThat(response.name()).isEqualTo(entity.getFullName());
        assertThat(response.email()).isEqualTo(entity.getEmail());
    }
}
