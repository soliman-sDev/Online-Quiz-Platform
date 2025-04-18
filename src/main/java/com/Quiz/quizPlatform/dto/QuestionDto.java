package com.Quiz.quizPlatform.dto;

import java.util.List;

public record QuestionDto(Long id, String text, List<String> options) {}
