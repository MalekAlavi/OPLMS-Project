package mk.ukim.finki.wp.db.entity;


import jakarta.persistence.*;
import lombok.*;
import mk.ukim.finki.wp.db.entity.user.User;

import java.time.LocalDate;

@Entity
@Table(name = "quiz_attempt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attempt_id")
    private Integer attemptId;

    private Integer score;

    @Column(name = "attempt_date")
    private LocalDate attemptDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}
