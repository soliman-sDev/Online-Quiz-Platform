package com.Quiz.quizPlatform.service;

import com.Quiz.quizPlatform.dto.*;
import com.Quiz.quizPlatform.entity.*;
import com.Quiz.quizPlatform.repository.*;
import com.Quiz.quizPlatform.security.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepo;
    private final QuestionRepository questionRepo;
    private final UserRepository userRepo;
    private final QuizAttemptRepository attemptRepo;
    private final AnswerRepository answerRepo;
    private final AuthUtil authUtil;

    public Quiz createQuiz(QuizRequest request) {
        Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setTimeLimit(request.getTimeLimit());
        quiz.setPublished(request.isPublished());
        return quizRepo.save(quiz);
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepo.findAll();
    }

    public Quiz updateQuiz(Long id, QuizRequest request) {
        Quiz quiz = quizRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setTimeLimit(request.getTimeLimit());
        quiz.setPublished(request.isPublished());
        return quizRepo.save(quiz);
    }

    public void deleteQuiz(Long id) {
        quizRepo.deleteById(id);
    }

    public Question addQuestion(QuestionRequest request) {
        Quiz quiz = quizRepo.findById(request.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        Question question = new Question();
        question.setQuizId(quiz.getId());
        question.setText(request.getText());
        question.setOptions(request.getOptions());
        question.setCorrectAnswer(request.getCorrectAnswer());
        return questionRepo.save(question);
    }


    public List<QuizSummaryDto> getPublishedQuizzes() {
        return quizRepo.findAll().stream()
                .filter(Quiz::isPublished)
                .map(quiz -> new QuizSummaryDto(quiz.getId(), quiz.getTitle(), quiz.getTimeLimit()))
                .toList();
    }

    public QuizDto startQuiz(Long quizId) {
        Quiz quiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        QuizAttempt attempt = new QuizAttempt();
        attempt.setQuiz(quiz);
        attempt.setUser(authUtil.getCurrentUser());
        attempt.setStartTime(LocalDateTime.now());
        attemptRepo.save(attempt);
        List<QuestionDto> questions = quiz.getQuestions().stream()
                .map(q -> new QuestionDto(q.getId(), q.getText(), q.getOptions()))
                .toList();

        return new QuizDto(quiz.getId(), quiz.getTitle(), questions, quiz.getTimeLimit());
    }

    public QuizResultDto submitQuiz(Long quizId, SubmitQuizDto submission) {
        User user = authUtil.getCurrentUser();
        Quiz quiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        QuizAttempt attempt = attemptRepo.findTopByUserAndQuizOrderByStartTimeDesc(user,quiz)
                .orElseThrow(() -> new RuntimeException(("Quiz attempt not found")));

        attempt.setEndTime(LocalDateTime.now());
        Duration timeTaken = Duration.between(attempt.getStartTime(), attempt.getEndTime());
        boolean finishedInTime = timeTaken.toMinutes() <= quiz.getTimeLimit();
        attempt.setFinishedInTime(finishedInTime);


        int score = 0;
        List<Answer> answers = new ArrayList<>();

        for (AnswerDto dto : submission.getAnswers()) {
            Question question = questionRepo.findById(dto.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));
            Answer answer = new Answer();
            answer.setAttempt(attempt);
            answer.setQuestion(question);
            answer.setSelectedOption(dto.getSelectedOption());
            answers.add(answer);
            if (question.getCorrectAnswer().equals(dto.getSelectedOption())) {
                score++;
            }
        }

        if(!finishedInTime) {
            score = 0;
        }



        attempt.setScore(score);
        attempt = attemptRepo.save(attempt);
        for (Answer ans : answers) {
            ans.setAttempt(attempt);
        }
        answerRepo.saveAll(answers);

        return new QuizResultDto(score, quiz.getQuestions().size(),attempt.getStartTime() ,attempt.getEndTime(), finishedInTime);
    }








}
