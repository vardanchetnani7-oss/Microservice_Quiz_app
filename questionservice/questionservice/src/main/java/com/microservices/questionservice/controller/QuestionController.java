package com.microservices.questionservice.controller;


import com.microservices.questionservice.Model.Question;
import com.microservices.questionservice.Model.QuestionWrapper;
import com.microservices.questionservice.Model.Response;
import com.microservices.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getallQuestions(){
      return questionService.getallQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>>getQuestionsByCategory(@PathVariable String category){
            return questionService.getallQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
       return questionService.addQuestion(question);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>>getQuestionsForQuiz(@RequestParam String categoryName,@RequestParam Integer numQuestions){
        return questionService.getQuestionsForQuiz(categoryName,numQuestions);
    }
    //getQuestions{questionid}
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer>questionsIds){
        return questionService.getQuestionFromId(questionsIds);
    }
    //getscore
    @PostMapping("getScore")
    public ResponseEntity<Integer>getScore(@RequestBody List<Response>responses){
        return questionService.getScore(responses);
    }

}
