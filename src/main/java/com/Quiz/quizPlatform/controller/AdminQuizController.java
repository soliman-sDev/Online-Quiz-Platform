package com.Quiz.quizPlatform.controller;

import com.Quiz.quizPlatform.dto.QuestionRequest;
import com.Quiz.quizPlatform.dto.QuizRequest;
import com.Quiz.quizPlatform.entity.Question;
import com.Quiz.quizPlatform.entity.Quiz;
import com.Quiz.quizPlatform.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin/quizzes")
public class AdminQuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizRequest request) {
        return ResponseEntity.ok(quizService.createQuiz(request));
    }

    @PostMapping("/addQuestion")
    public ResponseEntity<Question> addQuestion(@RequestBody QuestionRequest request) {
        return ResponseEntity.ok(quizService.addQuestion(request));
    }

    @GetMapping
    public ResponseEntity<List<Quiz>> getAll() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @RequestBody QuizRequest request) {
        return ResponseEntity.ok(quizService.updateQuiz(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.ok("Deleted Successfully");
    }




}
