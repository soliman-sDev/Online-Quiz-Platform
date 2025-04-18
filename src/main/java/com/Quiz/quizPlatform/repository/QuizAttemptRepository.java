package com.Quiz.quizPlatform.repository;

import com.Quiz.quizPlatform.entity.Quiz;
import com.Quiz.quizPlatform.entity.QuizAttempt;
import com.Quiz.quizPlatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {

    Optional<QuizAttempt> findTopByUserAndQuizOrderByStartTimeDesc(User user, Quiz quiz);
}
