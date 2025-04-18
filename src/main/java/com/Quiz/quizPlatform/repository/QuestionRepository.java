package com.Quiz.quizPlatform.repository;

import com.Quiz.quizPlatform.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {}
