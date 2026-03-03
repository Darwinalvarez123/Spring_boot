package edu.unimagdalena.lms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.rmi.server.UID;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "enrollments")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String status;
    @Column(name = "enrolled_at", nullable = false)
    private Instant enrolledAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
}
