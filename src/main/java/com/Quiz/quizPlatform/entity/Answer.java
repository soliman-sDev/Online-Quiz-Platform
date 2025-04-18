package com.Quiz.quizPlatform.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "answer")
public class Answer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private QuizAttempt attempt;

    @ManyToOne
    private Question question;

    private String selectedOption;
}
