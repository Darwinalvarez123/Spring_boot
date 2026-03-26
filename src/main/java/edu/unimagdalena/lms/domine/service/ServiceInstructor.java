import java.util.List;

import edu.unimagdalena.lms.api.dto.InstructorDto;

public interface ServiceInstructor {
    InstructorResponse create(InstructorCreateReques req);

    InstructorResponse get(InstructorIdReques req);

    InstructorResponse update(InstructorUpdateReques req);

    List<InstructorResponse> list();

    void delete(UUID id);

}