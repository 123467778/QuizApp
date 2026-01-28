package com.example.QuizApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;




@Service
public class QuizService {
	@Autowired
    QuestionDao questionDao;
	
	@Autowired
	QuizDao quizDao;
	//Creating a Quiz
	public Integer createQuiz( String category,int numQ, String title) {
		Quiz quiz =new Quiz();
		List<Question>question = questionDao.findRandomQuestion(category,numQ);
		
		quiz.setTitle(title);
		quiz.setQuestion(question);
		quizDao.save(quiz);
		return  quiz.getId();
	}
	//Fetching QuizQuestions
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
		Optional <Quiz> quiz= quizDao.findById(id);
		List<Question>questionFromDB=quiz.get().getQuestion();
		List<QuestionWrapper>questionForUser=new ArrayList<>();
		for(Question q:questionFromDB) {
			QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
	       questionForUser.add(qw);
		}
		return new ResponseEntity<>(questionForUser,HttpStatus.OK);
	}
	
	//Calculating Quiz Result
	public ResponseEntity<Integer> calculateResult(Integer id,List< Response> response) {
	Quiz quiz =quizDao.findById(id).get();
	List<Question>questions=quiz.getQuestion();
	int right=0;
	int i=0;
	for(Response responses:response) {
		if(responses.getResponse().equals(questions.get(i).getRightAnswer())) {
			right++;
			i++;
		}
			
	}
	  return new ResponseEntity<Integer>(right,HttpStatus.OK);
	}
    
}
