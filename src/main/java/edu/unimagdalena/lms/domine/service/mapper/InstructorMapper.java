import java.time.Instant;

import edu.unimagdalena.lms.api.dto.InstructorDto;
import edu.unimagdalena.lms.domine.entities.Instructor;

public class InstructorMapper {
    public static Instructor toEntity(InstructorDto.InstructorCreateReques req) {
        return Instructor.builder().email(req.email()).fullName(req.name()).created_at(Instant.now()).build();

    }

    public static Instructor toEntity(InstructorDto.InstructorUpdateReques req) {

        return Instructor.builder().id(req.id()).email(req.email()).fullName(req.name()).updated_at(Instant.now())
                .build();
    }

    public static Instructor toResponse(Instructor entity) {
        return new InstructorDto.InstructorResponse(entity.getId(), entity.getFullName(), entity.getEmail());
    }
}