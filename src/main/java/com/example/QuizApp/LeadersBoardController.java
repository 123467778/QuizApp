package com.example.QuizApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leadersboard")
@CrossOrigin("*")
public class LeadersBoardController {
    @Autowired
    private QuizService quizService;
    @GetMapping("/top")
    public List<LeaderBoardEntry> getLeaderBoard(){
        return quizService.getLeaderBoard();
    }
}
