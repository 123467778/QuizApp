package com.example.QuizApp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionDao extends JpaRepository<Question,Integer> {
 
	//Get All Question By Category
	List<Question> findAllByCategory(String category);
	
	
	//GET QUESTIONS FROM DB
     @Query(value="select * FROM  Question q where q.category=:category ORDER BY RAND() LIMIT :numQ",nativeQuery=true)
	List<Question> findRandomQuestion(String category, int numQ);
	

}
