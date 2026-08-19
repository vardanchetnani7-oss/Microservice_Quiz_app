package com.microservices.questionservice.service;

import com.microservices.questionservice.Model.Question;
import com.microservices.questionservice.Model.QuestionWrapper;
import com.microservices.questionservice.Model.Response;
import com.microservices.questionservice.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question> >getallQuestions() {

        try{
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question> > getallQuestionsByCategory(String category) {
       try {
           return new ResponseEntity<>( questionDao.findByCategory(category), HttpStatus.OK);
       } catch (Exception e) {
           e.printStackTrace();
       }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(Question question) {
       try {
           questionDao.save(question);
           return new ResponseEntity<>("success", HttpStatus.CREATED);
       } catch (Exception e) {
           e.printStackTrace();
       }
        return new ResponseEntity<>(new String(), HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {

        List<Integer>questions = questionDao.findRandomQuestionByCategory(categoryName,numQuestions);
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(List<Integer> questionsIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question>questions = new ArrayList<>();

        for(Integer id : questionsIds){
            questions.add(questionDao.findById(id).get());
        }
        for(Question question:questions){
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            wrappers.add(wrapper);
        }

        return new ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int right = 0;

        for(Response response :responses){
            Question question = questionDao.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightAnswer()))
                right++;
        }

        return new ResponseEntity<>(right,HttpStatus.OK);
    }
    }
