package com.Quiz.quizPlatform.dto;

import java.time.LocalDateTime;

public record QuizResultDto(int score, int totalQuestions,LocalDateTime startTime, LocalDateTime submittedAt, boolean finishedInTime) {}
