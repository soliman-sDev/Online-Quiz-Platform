package com.Quiz.quizPlatform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Table(name = "quiz")
public class Quiz {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Integer timeLimit;
    private boolean published;

    @OneToMany(mappedBy = "quizId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;
}
