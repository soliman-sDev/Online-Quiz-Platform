package com.Quiz.quizPlatform.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "attempt")
public class QuizAttempt {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Quiz quiz;

    private int score;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean finishedInTime;

    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL)
    private List<Answer> answers;

}
