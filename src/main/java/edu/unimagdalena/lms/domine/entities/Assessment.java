package edu.unimagdalena.lms.domine.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "assessments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private int score;
    @Column(name = "taken_at", nullable = false)
    private Instant takenAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;


}




