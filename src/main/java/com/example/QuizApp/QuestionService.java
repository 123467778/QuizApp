package com.example.QuizApp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



@Service
public class QuestionService {
	@Autowired
	private QuestionDao questionDao;
	
	//Get All QUESTION
	public List<Question> getAllQuestions(){
		return questionDao.findAll();
		
	}
  //Get QUESTION BY CATEGORY

	public List<Question> findAllByCategory(String category) {
		
		return questionDao.findAllByCategory(category);
	}

	public String addQuestion(Question question) {
		questionDao.save(question);
		return "success";
		
	}

	public String updateQuestion(Question question) {
		questionDao.save(question);
		return "success";
	}

	public String deleteQuestion(Integer id) {
		questionDao.deleteById(id);
		return "Deleted";
	}
   
}
