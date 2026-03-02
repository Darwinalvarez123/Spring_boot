package edu.unimagdalena.lms.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "instructorProfiles")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class InstructorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String bio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false, unique = true)
    private Instructor instructor;


}
