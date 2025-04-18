package com.Quiz.quizPlatform.controller;

import com.Quiz.quizPlatform.dto.QuestionRequest;
import com.Quiz.quizPlatform.dto.QuizRequest;
import com.Quiz.quizPlatform.entity.Question;
import com.Quiz.quizPlatform.entity.Quiz;
import com.Quiz.quizPlatform.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Quizzes Management")
@RestController
@RequestMapping("/admin/quizzes")
public class AdminQuizController {

    @Autowired
    private QuizService quizService;

    @Operation(summary = "Create Quiz")
    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizRequest request) {
        return ResponseEntity.ok(quizService.createQuiz(request));
    }

    @Operation(summary = "Add Question")
    @PostMapping("/addQuestion")
    public ResponseEntity<Question> addQuestion(@RequestBody QuestionRequest request) {
        return ResponseEntity.ok(quizService.addQuestion(request));
    }

    @Operation(summary = "Get All Quizzes")
    @GetMapping
    public ResponseEntity<List<Quiz>> getAll() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    @Operation(summary = "Update Quiz")
    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @RequestBody QuizRequest request) {
        return ResponseEntity.ok(quizService.updateQuiz(id, request));
    }

    @Operation(summary = "Delete Quiz")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.ok("Deleted Successfully");
    }
}
