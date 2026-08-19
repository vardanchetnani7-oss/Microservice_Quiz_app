package com.microservices.questionservice.dao;


import com.microservices.questionservice.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question,Integer> {

    List<Question> findByCategory(String category);

    @Query(value="SELECT q.id FROM question q WHere q.category=:category ORDER BY RANDOM() LIMIT :numQ ",nativeQuery = true)
    List<Integer>findRandomQuestionByCategory(String category,int numQ);
}
