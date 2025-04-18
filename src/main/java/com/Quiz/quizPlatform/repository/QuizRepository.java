package com.Quiz.quizPlatform.repository;

import com.Quiz.quizPlatform.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {}
