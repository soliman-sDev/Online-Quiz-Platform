package com.Quiz.quizPlatform.dto;

import java.util.List;

public record QuizDto(Long id, String title, List<QuestionDto> questions, int timeLimit) {
}
