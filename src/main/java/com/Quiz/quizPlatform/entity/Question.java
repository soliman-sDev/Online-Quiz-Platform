package com.Quiz.quizPlatform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Table(name = "question")
public class Question {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quizId;

    private String text;

    @ElementCollection
    private List<String> options;

    private String correctAnswer;
}
