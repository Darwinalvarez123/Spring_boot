package edu.unimagdalena.lms.domine.service.mapper;

import edu.unimagdalena.lms.api.dto.StudentDto.*;
import edu.unimagdalena.lms.domine.entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class StudentMapperTest {

    private StudentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(StudentMapper.class);
    }

    @Test
    void toEntity_CreateRequest_ShouldMapCorrectly() {
        StudentCreateRequest req = new StudentCreateRequest("John Doe", "john@example.com");
        Student entity = mapper.toEntity(req);

        assertThat(entity).isNotNull();
        assertThat(entity.getFullName()).isEqualTo(req.name());
        assertThat(entity.getEmail()).isEqualTo(req.email());
        assertThat(entity.getCreatedAt()).isNotNull();
    }

    @Test
    void toResponse_Entity_ShouldMapCorrectly() {
        Student entity = Student.builder()
                .id(UUID.randomUUID())
                .fullName("Jane Doe")
                .email("jane@example.com")
                .build();
        StudentResponse response = mapper.toResponse(entity);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(entity.getId());
        assertThat(response.name()).isEqualTo(entity.getFullName());
        assertThat(response.email()).isEqualTo(entity.getEmail());
    }
}
