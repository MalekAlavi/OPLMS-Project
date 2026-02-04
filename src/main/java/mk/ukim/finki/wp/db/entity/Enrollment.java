package mk.ukim.finki.wp.db.entity;


import jakarta.persistence.*;
import lombok.*;
import mk.ukim.finki.wp.db.entity.user.User;

import java.time.LocalDate;

@Entity
@Table(name = "enrollment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Integer enrollmentId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "enroll_date", nullable = false)
    private LocalDate enrollDate;

    @Column(name = "completion_status", length = 30)
    private String completionStatus;

    @Column(name = "progress_percentage")
    private Integer progressPercentage;
}
