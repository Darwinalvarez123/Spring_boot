import java.time.Instant;

import edu.unimagdalena.lms.domine.entities.Instructor;

public class InstructorMapper {
    public static Instructor toEntity(InstructorCreateReques req) {
        return Instructor.builder().email(req.mail()).fullName(req.name()).created_at(Instant.now()).build();

    }

    public static Instructor toEntity(InstructorUpdateReques req) {

        return Instructor.builder().id(req.id()).email(req.mail()).fullName(req.name()).updated_at(Instant.now())
                .build();

    }

    public static instructor toResponse(Instructor entity) {
        return new InstructorResponse(entity.getId(), entity.getFullName(), entity.getEmail());
    }
}