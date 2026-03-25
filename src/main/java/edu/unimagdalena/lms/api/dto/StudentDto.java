package edu.unimagdalena.lms.api.dto;

import java.io.Serializable;
import java.util.UUID;

public class StudentDto {


    public record StudentCreateRequest(
            String name,
            String email
    ) implements Serializable {
    }


    public record StudentUpdateRequest(
            UUID id,
            String name,
            String email
    ) implements Serializable {
    }


    public record StudentResponse(
            UUID id,
            String name,
            String email
    ) implements Serializable {}


    public record StudentIdRequest(
            UUID id
    ) implements Serializable {
    }
}