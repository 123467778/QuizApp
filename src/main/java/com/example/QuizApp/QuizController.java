package com.example.QuizApp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("quiz")
@CrossOrigin("*")
public class QuizController {
	@Autowired
    QuizService quizService;

	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("create")
	
     public ResponseEntity<Integer>createQuiz(@RequestParam String category,@RequestParam int  numQ, @RequestParam String title){
		Integer quizId=quizService.createQuiz(category,numQ,title);
		return new ResponseEntity<>(quizId, HttpStatus.CREATED);
	}
	@GetMapping("/get/{id}")
	public ResponseEntity<List<QuestionWrapper>>getQuizQuestions (@PathVariable Integer id){
		return quizService.getQuizQuestions(id);
	}
//	@PostMapping("submit/{id}")
//	public ResponseEntity<Integer>submitQuiz(@PathVariable Integer id,@RequestBody List<Response> response,@RequestParam Integer userId){
//
//		User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found.."));
//
//		return quizService.calculateResult(id,response,user);
//	}

	@PostMapping("submit/{id}")
	public ResponseEntity<Map<String, Object>> submitQuiz(
			@PathVariable Integer id,
			@RequestBody List<Response> responses,
			@RequestParam Integer userId) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		// Calculate score
		ResponseEntity<Integer> scoreResponse = quizService.calculateResult(id, responses, user);
		int score = scoreResponse.getBody();

		// Calculate weak concepts
		List<Map<String, Object>> weakConcepts = quizService.getWeakConcepts(user, id, responses);

		// Prepare response for frontend
		Map<String, Object> result = new HashMap<>();
		result.put("score", score);
		result.put("totalQuestions", responses.size());
		result.put("weakConcepts", weakConcepts);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}


}
