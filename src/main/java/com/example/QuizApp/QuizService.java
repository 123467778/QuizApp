package com.example.QuizApp;

import java.util.*;
import java.util.stream.Collectors;

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
	public ResponseEntity<Integer> calculateResult(Integer id,List< Response> response,User user) {
	Quiz quiz =quizDao.findById(id).get();
	List<Question>questions=quiz.getQuestion();
	int right=0;
//	int i=0;
//	for(Response responses:response) {
//		if(responses.getResponse().equals(questions.get(i).getRightAnswer())) {
//			right++;
//			i++;
//		}
//
//	}
		for(int i=0;i<response.size();i++){
			if(response.get(i).getResponse().equals(questions.get(i).getRightAnswer())){
				right++;
			}
		}

	quiz.setScore(right);
	quiz.setUser(user);
	quizDao.save(quiz);

	  return new ResponseEntity<Integer>(right,HttpStatus.OK);
	}



	//Leaders Board
public  List<LeaderBoardEntry> getLeaderBoard(){
		List<Quiz> quizzes =quizDao.findAll();

		//Map users -> highest scores

	Map<Integer,LeaderBoardEntry> userMaxScores =new HashMap<>();
	for(Quiz q :quizzes){
		User user =q.getUser();
		int score =q.getScore();

		if(user==null) continue;

		if(userMaxScores.containsKey(user.getId())){
			LeaderBoardEntry entry=userMaxScores.get(user.getId());
			entry.setScore(Math.max(entry.getScore(),score));
		}
		else{
			userMaxScores.put(user.getId(),new LeaderBoardEntry(user.getUsername(),score));
		}




	}
	return userMaxScores.values().stream().sorted((a,b)->b.getScore()-a.getScore()).collect(Collectors.toList());

}

}
