package com.microservices.quizservice.Model;

import lombok.Data;

@Data
public class QuizDto {

    String categoryName;
    Integer numQuestions;
    String title;
}
