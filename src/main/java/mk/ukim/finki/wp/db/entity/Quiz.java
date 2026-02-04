package mk.ukim.finki.wp.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quiz")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Integer quizId;

    @Column(name = "total_points", nullable = false)
    private Integer totalPoints;

    @Column(name = "passing_score", nullable = false)
    private Integer passingScore;

    @OneToOne(optional = false)
    @JoinColumn(name = "lesson_id", unique = true)
    private Lesson lesson;
}
