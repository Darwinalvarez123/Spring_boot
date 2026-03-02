package edu.unimagdalena.lms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "instructors")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "update_at")
    private Instant updatedAt;
    @OneToMany(mappedBy = "instructor")
    private Set<Course> courses;

    @OneToOne(mappedBy = "instructor")
    private InstructorProfile instructorProfile;


}
