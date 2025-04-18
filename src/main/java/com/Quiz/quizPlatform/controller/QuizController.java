package com.Quiz.quizPlatform.controller;

import com.Quiz.quizPlatform.dto.QuizDto;
import com.Quiz.quizPlatform.dto.QuizResultDto;
import com.Quiz.quizPlatform.dto.QuizSummaryDto;
import com.Quiz.quizPlatform.dto.SubmitQuizDto;
import com.Quiz.quizPlatform.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping
    public ResponseEntity<List<QuizSummaryDto>> getAvailableQuizzes() {
        return ResponseEntity.ok(quizService.getPublishedQuizzes());
    }

    @PostMapping("/{quizId}/start")
    public ResponseEntity<QuizDto> startQuiz(@PathVariable Long quizId) {
        return ResponseEntity.ok(quizService.startQuiz(quizId));
    }

    @PostMapping("/{quizId}/submit")
    public ResponseEntity<QuizResultDto> submitQuiz(@PathVariable Long quizId, @RequestBody SubmitQuizDto submission) {
        return ResponseEntity.ok(quizService.submitQuiz(quizId, submission));
    }
}
