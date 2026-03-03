package edu.unimagdalena.lms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "crated_at")
    private Instant createdAt;
    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "student")
    private Set<Assessment> assessments;

    @OneToMany(mappedBy = "student")
    private Set<Enrollment> enrollments;
}
